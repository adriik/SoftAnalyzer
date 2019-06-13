package webService;

import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.xml.ws.soap.Addressing;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;

import box.Cechy;
import box.Katalog;
import box.Pack;
import box.Plik;
import box.Project;
import classUpload.AtrybutyPlikow;
import classUpload.HashePlikow;
import classUpload.LiczbaLiniiKodu;
import classUpload.RozmiaryPlikow;
import classUpload.RozszerzeniaPlikow;
import classUpload.TypyPlikow;
import classUpload.TypyZmiennych;
import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;

/**
 * Glowna klasa przedstawiajaca interfejsy wystawiane w WebService
 * @author Adrian Plichta
 * @author Maciej Wyszyński
 * @author Mateusz Stolarski
 * @author Daniel Laskowski
 * @author Michał Pruchniewski
 */
@WebService(targetNamespace = "http://webService/", portName = "ServiceSAPort", serviceName = "ServiceSAService")
@Addressing(enabled = false, required = false)
public class ServiceSA {
	
	Pack paczkaProjektow = new Pack();
    
	/**
	 * Metoda przeslijPlik() zapisuje oraz wypakowuje podane archiwum z projektem pod odpowiednia sciezka na serwerze
	 * oraz czyta plik XMI i sprawdza jakie cechy wybrano do porwnania dla tego projektu
	 * @param link - parametrem wejsciowym jest link do archiwum projektu
	 * @return metoda zwraca liste cech mozliwych do pobrania z serwisu dla podanego projektu
	 */
	@WebMethod(operationName = "przeslijPlik", action = "urn:PrzeslijPlik")
	public ArrayList<String> przeslijPlik(@WebParam(name = "arg0") String link) {

		try {
			String[] wynik1 = link.split("/");

			String sciezka = this.getClass().getClassLoader().getResource("").getPath().split("WEB-INF")[0]
					.substring(1).replace("%20", " ");
			if(!paczkaProjektow.contains(wynik1[wynik1.length - 1])) {
				URL website = new URL(link);
				ReadableByteChannel rbc = Channels.newChannel(website.openStream());
				System.out.println("Zapisalem projekt pod sciezka:\n" + sciezka + "archiwum/" + wynik1[wynik1.length - 1]); // pelna
			
			
				FileOutputStream fos = new FileOutputStream(sciezka + "archiwum/" + wynik1[wynik1.length - 1]);
				fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
				fos.close();
	
				try {
					ZipFile zipFile = new ZipFile(sciezka + "archiwum/" + wynik1[wynik1.length - 1]);
					zipFile.extractAll(sciezka + "archiwum" + "/"
							+ wynik1[wynik1.length - 1].substring(0, wynik1[wynik1.length - 1].lastIndexOf('.')));
				} catch (ZipException e) {
					//e.printStackTrace();
					System.out.println("Cos poszlo nie tak podczas wypakowania! - pominąłem niektóre pliki");
				}
				
				Project projekt = new Project(wynik1[wynik1.length - 1], sciezka + "archiwum/"
						+ wynik1[wynik1.length - 1].substring(0, wynik1[wynik1.length - 1].lastIndexOf('.')));
				paczkaProjektow.addProject(projekt); // projekt o nazwie XXXXX.zip
			
				SAXReader reader = new SAXReader();
				Document document = null;
				try {
					document = reader.read(sciezka + "uploads/SAML.xmi");
				} catch (DocumentException e) {
					System.out.println("Błąd podczas wczytywania pliku XMI");
					e.printStackTrace();
				}
	
				List<Node> nodeList = document.selectNodes("//*[name() = 'ownedAttribute']");
	
				for (Node node : nodeList) {
	
					Element element = (Element) node;
					for (Cechy st : Cechy.values()) {
						if (element.attributeValue("name").equals(st.name())) {
							projekt.listaCech.add(st.name());
							System.out.println(st.name());
							break;
						}
					}
				}
//			new Thread(new Runnable() {
//	            @Override
//	            public void run() {
//	            	while(!projekt.czyWyczyszczony) {
//		            	try {
//		            		Thread.sleep(100000);
//							FileUtils.forceDelete(new File(projekt.getSciezka()));
//							FileUtils.forceDelete(new File(sciezka + "archiwum/" + wynik1[wynik1.length - 1]));
//							projekt.czyWyczyszczony = true;
//						} catch (IOException e) {
//							e.printStackTrace();
//							System.out.println("Blad podczas Thread");
//						} catch (InterruptedException e) {
//							e.printStackTrace();
//							System.out.println("Błąd podczas sleepa");
//						}
//	            	}
//	            	System.out.println("Udało się usunąć");
//	            }
//	        }).start();
			
				return projekt.listaCech;
			}else {
				System.out.println("Był już taki projekt - zwróciłem wcześniejsze wyniki");
				return paczkaProjektow.getProject(wynik1[wynik1.length - 1]).listaCech;
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Metoda getLiczbaPlikow() odpowiada za wyodrebnienie z projektu wartosci dla cechy LiczbaPlikow
	 * @param nazwaProjektu - parametrem wejsciowym jest nazwa projektu
	 * @return metoda zwraca liczbe plikow w calym projekcie
	 */
	@WebMethod(operationName = "getLiczbaPlikow", action = "urn:GetLiczbaPlikow")
	public int getLiczbaPlikow(@WebParam(name = "arg0") String nazwaProjektu) {
		if(paczkaProjektow.getProject(nazwaProjektu) != null && paczkaProjektow.getProject(nazwaProjektu).listaCech.contains(Cechy.LiczbaPlikowProperty.name())) {
			return paczkaProjektow.getProject(nazwaProjektu).liczbaPlikow;
		}else {
			return 0;
		}
	}

	/**
	 * Metoda getRozmiaryPlikowKodow() odpowiada za wyodrebnienie z projektu wartosci dla cechy RozmiaryPlikowKodow
	 * @param nazwaProjektu - parametrem wejsciowym jest nazwa projektu
	 * @return metoda zwraca liste uporzadkowana zawierajaca nazwe i rozmiar dla danych plikow w projekcie
	 */
	@WebMethod(operationName = "getRozmiaryPlikowKodow", action = "urn:GetRozmiaryPlikowKodow")
	public LinkedList<RozmiaryPlikow> getRozmiaryPlikowKodow(@WebParam(name = "arg0") String nazwaProjektu) {
		if(paczkaProjektow.getProject(nazwaProjektu) != null && paczkaProjektow.getProject(nazwaProjektu).listaCech.contains(Cechy.RozmiaryPlikowKodowZrodlowychProperty.name())) {
			LinkedList<RozmiaryPlikow> lista = new LinkedList<RozmiaryPlikow>();
			
			for (Plik plik : paczkaProjektow.getProject(nazwaProjektu).listaPlikow) {
				RozmiaryPlikow rp = new RozmiaryPlikow();
				rp.nazwa = plik.nazwa;
				rp.rozmiar = Math.toIntExact(plik.rozmiar);
				lista.add(rp);
			}
			return lista;
		}else {
			return null;
		}
	}

	/**
	 * Metoda getListaNazwPlikow() odpowiada za wyodrebnienie z projektu wartosci dla cechy ListaNazwPlikow
	 * @param nazwaProjektu - parametrem wejsciowym jest nazwa projektu
	 * @return metoda zwraca liste nazw plikow w calym projekcie
	 */
	@WebMethod(operationName = "getListaNazwPlikow", action = "urn:GetListaNazwPlikow")
	public ArrayList<String> getListaNazwPlikow(@WebParam(name = "arg0") String nazwaProjektu) {
		if (paczkaProjektow.getProject(nazwaProjektu) != null && paczkaProjektow.getProject(nazwaProjektu).listaCech.contains(Cechy.ListaNazwPlikowIKatalogow.name())) {
			ArrayList<String> listaNazwPlikow = new ArrayList<String>();
			for (Plik plik : paczkaProjektow.getProject(nazwaProjektu).listaPlikow) {
				listaNazwPlikow.add(plik.nazwa);
			}
			return listaNazwPlikow;
		} else {
			return null;
		}
	}

	/**
	 * Metoda getListaNazwKatalogow() odpowiada za wyodrebnienie z projektu wartosci dla cechy ListaNazwKatalogow
	 * @param nazwaProjektu - parametrem wejsciowym jest nazwa projektu
	 * @return metoda zwraca liste nazw katalogow w calym projekcie
	 */
	@WebMethod(operationName = "getListaNazwKatalogow", action = "urn:GetListaNazwKatalogow")
	public ArrayList<String> getListaNazwKatalogow(@WebParam(name = "arg0") String nazwaProjektu) {
		if (paczkaProjektow.getProject(nazwaProjektu) != null && paczkaProjektow.getProject(nazwaProjektu).listaCech.contains(Cechy.ListaNazwPlikowIKatalogow.name())) {
			ArrayList<String> listaNazwKatalogow = new ArrayList<String>();
			for (Katalog katalog : paczkaProjektow.getProject(nazwaProjektu).listaKatalogow) {
				listaNazwKatalogow.add(katalog.nazwa);
			}
			return listaNazwKatalogow;
		} else {
			return null;
		}
	}
	
	/**
	 * Metoda getLiczbaLiniiKodu() odpowiada za wyodrebnienie z projektu wartosci dla cechy LiczbaLiniiKodu
	 * @param nazwaProjektu - parametrem wejsciowym jest nazwa projektu
	 * @return metoda zwraca liste uporzadkowana zawierajaca nazwe i liczbe linii dla danych plikow w projekcie
	 */
	@WebMethod(operationName = "getLiczbaLiniiKodu", action = "urn:GetLiczbaLiniiKodu")
	public LinkedList<LiczbaLiniiKodu> getLiczbaLiniiKodu(@WebParam(name = "arg0") String nazwaProjektu){
		if(paczkaProjektow.getProject(nazwaProjektu) != null && paczkaProjektow.getProject(nazwaProjektu).listaCech.contains(Cechy.LiczbaLiniiKoduProperty.name())) {
			LinkedList<LiczbaLiniiKodu> lista = new LinkedList<LiczbaLiniiKodu>();
			
			for (Plik plik : paczkaProjektow.getProject(nazwaProjektu).listaPlikow) {
				LiczbaLiniiKodu ll = new LiczbaLiniiKodu();
				ll.nazwa = plik.nazwa;
				ll.liczbaLinii = Math.toIntExact(plik.liczbaLiniiKodu);
				lista.add(ll);
			}
			System.out.println("Udalo sie");
			return lista;
		}else {
			return null;
		}
	}
	
	/**
	 * Metoda getSkrotyPlikow() odpowiada za wyodrebnienie z projektu wartosci dla cechy SkrotyPlikow
	 * @param nazwaProjektu - parametrem wejsciowym jest nazwa projektu
	 * @return metoda zwraca liste uporzadkowana zawierajaca nazwe i hash(skrot) dla danych plikow w projekcie
	 */
	@WebMethod(operationName = "getSkrotyPlikow", action = "urn:GetSkrotyPlikow")
	public LinkedList<HashePlikow> getSkrotyPlikow(@WebParam(name = "arg0") String nazwaProjektu){
		if(paczkaProjektow.getProject(nazwaProjektu) != null && paczkaProjektow.getProject(nazwaProjektu).listaCech.contains(Cechy.SkrotyPlikowProperty.name())) {
			LinkedList<HashePlikow> lista = new LinkedList<HashePlikow>();
			
			for (Plik plik : paczkaProjektow.getProject(nazwaProjektu).listaPlikow) {
				HashePlikow ll = new HashePlikow();
				ll.nazwa = plik.nazwa;
				ll.hash = plik.hash;
				lista.add(ll);
			}
			System.out.println("Udalo sie");
			return lista;
		}else {
			return null;
		}
    }
	
	/**
	 * Metoda getLiczbaPlikowDanegoRozszerzenia() odpowiada za wyodrebnienie z projektu wartosci dla cechy LiczbaPlikowDanegoRozszerzenia
	 * @param nazwaProjektu - parametrem wejsciowym jest nazwa projektu
	 * @return metoda zwraca liste uporzadkowana zawierajaca rozszerzenie i liczbe plikow o takim rozszerzeniu w calym projekcie
	 */
	@WebMethod(operationName = "getLiczbaPlikowDanegoRozszerzenia", action = "urn:GetLiczbaPlikowDanegoRozszerzenia")
	public LinkedList<RozszerzeniaPlikow> getLiczbaPlikowDanegoRozszerzenia(@WebParam(name = "arg0") String nazwaProjektu){
		if(paczkaProjektow.getProject(nazwaProjektu) != null && paczkaProjektow.getProject(nazwaProjektu).listaCech.contains(Cechy.LiczbaPlikowODanymRozszerzeniuProperty.name())) {
			LinkedList<RozszerzeniaPlikow> lista = new LinkedList<RozszerzeniaPlikow>();
			
			paczkaProjektow.getProject(nazwaProjektu).liczbaPlikowDanegoRozszerzenia.forEach((k,v)->{
				RozszerzeniaPlikow rp = new RozszerzeniaPlikow();
				rp.rozszerzenie = k;
				rp.liczba = v;
				lista.add(rp);}
			);
			System.out.println("Udalo sie");
			return lista;
		}else {
			return null;
		}
	}
	
	/**
	 * Metoda getLiczbaDanychWejsciowych() odpowiada za wyodrebnienie z projektu wartosci dla cechy LiczbaDanychWejsciowych
	 * @param nazwaProjektu - parametrem wejsciowym jest nazwa projektu
	 * @return metoda zwraca liczbe odwolan do instrukcji, ktore sa uzywane do wprowadzania danych
	 */
	@WebMethod(operationName = "getLiczbaDanychWejsciowych", action = "urn:GetLiczbaDanychWejsciowych")
	public int getLiczbaDanychWejsciowych(@WebParam(name = "arg0") String nazwaProjektu){
		if(paczkaProjektow.getProject(nazwaProjektu) != null && paczkaProjektow.getProject(nazwaProjektu).listaCech.contains(Cechy.LiczbaDanychWejsciowychProperty.name())) {
			return paczkaProjektow.getProject(nazwaProjektu).liczbaDanychWejsciowych;
		}else {
			return 0;
		}
	}
	
	/**
	 * Metoda getLiczbaPlikowDanegoTypu() odpowiada za wyodrebnienie z projektu wartosci dla cechy LiczbaPlikowDanegoTypu
	 * @param nazwaProjektu - parametrem wejsciowym jest nazwa projektu
	 * @return metoda zwraca liste uporzadkowana zawierajaca typ i liczbe plikow o takim typie w calym projekcie
	 */
	@WebMethod(operationName = "getLiczbaPlikowDanegoTypu", action = "urn:GetLiczbaPlikowDanegoTypu")
	public LinkedList<TypyPlikow> getLiczbaPlikowDanegoTypu(@WebParam(name = "arg0") String nazwaProjektu){
		if(paczkaProjektow.getProject(nazwaProjektu) != null && paczkaProjektow.getProject(nazwaProjektu).listaCech.contains(Cechy.LiczbaPlikowDanegoTypuProperty.name())) {
			LinkedList<TypyPlikow> lista = new LinkedList<TypyPlikow>();
			
			paczkaProjektow.getProject(nazwaProjektu).liczbaPlikowDanegoTypu.forEach((k,v)->{
			TypyPlikow tp = new TypyPlikow();
			tp.typ = k;
			tp.liczba = v;
			lista.add(tp);}
			);
			return lista;
		}else {
			return null;
		}
	}
	
	/**
	 * Metoda getLiczbaAtrybutow() odpowiada za wyodrebnienie z projektu wartosci dla cechy LiczbaAtrybutow
	 * @param nazwaProjektu - parametrem wejsciowym jest nazwa projektu
	 * @return metoda zwraca liczbe atrybutow w calym projekcie
	 */
	@WebMethod(operationName = "getLiczbaAtrybutow", action = "urn:GetLiczbaAtrybutow")
	public int getLiczbaAtrybutow(@WebParam(name = "arg0") String nazwaProjektu){
		if(paczkaProjektow.getProject(nazwaProjektu) != null && paczkaProjektow.getProject(nazwaProjektu).listaCech.contains(Cechy.LiczbaAtrybutowProperty.name())) {
			return paczkaProjektow.getProject(nazwaProjektu).liczbaAtrybutow;
		}else{
			return 0;
		}
	}
	
	/**
	 * Metoda getLiczbaMetod() odpowiada za wyodrebnienie z projektu wartosci dla cechy LiczbaMetod
	 * @param nazwaProjektu - parametrem wejsciowym jest nazwa projektu
	 * @return metoda zwraca liczbe metod w calym projekcie
	 */
	@WebMethod(operationName = "getLiczbaMetod", action = "urn:GetLiczbaMetod")
	public int getLiczbaMetod(@WebParam(name = "arg0") String nazwaProjektu){
		if(paczkaProjektow.getProject(nazwaProjektu) != null && paczkaProjektow.getProject(nazwaProjektu).listaCech.contains(Cechy.LiczbaMetodProperty.name())) {
			return paczkaProjektow.getProject(nazwaProjektu).liczbaMetod;
		}else {
			return 0;
		}
		
	}
	
	/**
	 * Metoda getLiczbaAtrybutowWKlasach() odpowiada za wyodrebnienie z projektu wartosci dla cechy LiczbaAtrybutowWKlasach
	 * @param nazwaProjektu - parametrem wejsciowym jest nazwa projektu
	 * @return metoda zwraca liste uporzadkowana zawierajaca zawierajaca nazwe i liczbe atrybutow dla danych plikow w projekcie
	 */
	@WebMethod(operationName = "getLiczbaAtrybutowWKlasach", action = "urn:GetLiczbaAtrybutowWKlasach")
	public LinkedList<AtrybutyPlikow> getLiczbaAtrybutowWKlasach(@WebParam(name = "arg0") String nazwaProjektu){
		if(paczkaProjektow.getProject(nazwaProjektu) != null && paczkaProjektow.getProject(nazwaProjektu).listaCech.contains(Cechy.LiczbaAtrybutowWKlasach.name())) {
			LinkedList<AtrybutyPlikow> lista = new LinkedList<AtrybutyPlikow>();
			
			for (Plik plik : paczkaProjektow.getProject(nazwaProjektu).listaPlikow) {
				ArrayList<String> cRozszerzeniaPlikow = new ArrayList<String>(Arrays.asList("c", "h", "cpp", "cxx", "hxx", "cs", "java"));

				if(cRozszerzeniaPlikow.contains(plik.rozszerzenie)) {
					AtrybutyPlikow ap = new AtrybutyPlikow();
					ap.nazwa = plik.nazwa;
					ap.liczba = plik.liczbaAtrybutow;
					lista.add(ap);
				}
			}
			System.out.println("Udalo sie");
			return lista;
		}else {
			return null;
		}
	}
	
	/**
	 * Metoda getZbiorBibliotek() odpowiada za wyodrebnienie z projektu wartosci dla cechy ZbiorBibliotek
	 * @param nazwaProjektu - parametrem wejsciowym jest nazwa projektu
	 * @return metoda zwraca liste zalaczonych do kodu bibliotek bez powtorzen w calym projekcie
	 */
	@WebMethod(operationName = "getZbiorBibliotek", action = "urn:GetZbiorBibliotek")
	public ArrayList<String> getZbiorBibliotek(@WebParam(name = "arg0") String nazwaProjektu){
		if(paczkaProjektow.getProject(nazwaProjektu) != null && paczkaProjektow.getProject(nazwaProjektu).listaCech.contains(Cechy.ZbiorBibliotekProperty.name())) {
			ArrayList<String> lista = new ArrayList<String>();
			
			for (Plik plik : paczkaProjektow.getProject(nazwaProjektu).listaPlikow) {
				for(String s : plik.zbiorBibliotek)
		        {
					if (!lista.contains(s)) 
						lista.add(s);
		        }
			}
			return lista;
		}else {
			return null;
		}
	}
	
	@WebMethod(operationName = "getZbiorWykorzystywanychPlikow", action = "urn:GetZbiorWykorzystywanychPlikow")
	public ArrayList<String> getZbiorWykorzystywanychPlikow(@WebParam(name = "arg0") String nazwaProjektu){
		if(paczkaProjektow.getProject(nazwaProjektu) != null && paczkaProjektow.getProject(nazwaProjektu).listaCech.contains(Cechy.ZbiorBibliotekProperty.name())) {
			ArrayList<String> lista = new ArrayList<String>();
			
			for (Plik plik : paczkaProjektow.getProject(nazwaProjektu).listaPlikow) {
				for(String s : plik.zbiorWykorzystywanychPlikow)
		        {
					if (!lista.contains(s)) 
						lista.add(s);
		        }
			}

			return lista;
		}else {
			return null;
		}
	}
	
	@WebMethod(operationName = "getZbiorWykorzystywanychAdresow", action = "urn:GetZbiorWykorzystywanychAdresow")
	public ArrayList<String> getZbiorWykorzystywanychAdresow(@WebParam(name = "arg0") String nazwaProjektu){
		
		if(paczkaProjektow.getProject(nazwaProjektu) != null && paczkaProjektow.getProject(nazwaProjektu).listaCech.contains(Cechy.ZbiorBibliotekProperty.name())) {
			ArrayList<String> lista = new ArrayList<String>();
			for (Plik plik : paczkaProjektow.getProject(nazwaProjektu).listaPlikow) {
				for(String s : plik.zbiorWykorzystywanychAdresow)
		        {
					if (!lista.contains(s))
						lista.add(s);
		        }
			}

			return lista;
		}else {
			return null;
		}
	}
	
	@WebMethod(operationName = "getZbiorWykorzystywanychPortow", action = "urn:GetZbiorWykorzystywanychPortow")
	public ArrayList<String> getZbiorWykorzystywanychPortow(@WebParam(name = "arg0") String nazwaProjektu){
		if(paczkaProjektow.getProject(nazwaProjektu) != null && paczkaProjektow.getProject(nazwaProjektu).listaCech.contains(Cechy.ZbiorBibliotekProperty.name())) {
			ArrayList<String> lista = new ArrayList<String>();
			
			for (Plik plik : paczkaProjektow.getProject(nazwaProjektu).listaPlikow) {
				for(String s : plik.zbiorWykorzystywanychPortow)
		        {
					if (!lista.contains(s)) 
						lista.add(s);
		        }
			}

			return lista;
		}else {
			return null;
		}
	}
	
	/**
	 * Metoda getJezykProgramowania() odpowiada za wyodrebnienie z projektu wartosci dla cechy JezykProgramowania
	 * @param nazwaProjektu - parametrem wejsciowym jest nazwa projektu
	 * @return metoda zwraca nazwe jezyka programowania(Java, C, C#, C++) dla projektu
	 */
	@WebMethod(operationName = "getJezykProgramowania", action = "urn:GetJezykProgramowania")
	public String getJezykProgramowania(@WebParam(name = "arg0") String nazwaProjektu){
		if(paczkaProjektow.getProject(nazwaProjektu) != null && paczkaProjektow.getProject(nazwaProjektu).listaCech.contains(Cechy.JezykProgramowaniaProperty.name())) {
			return paczkaProjektow.getProject(nazwaProjektu).jezyk;
		}else {
			return null;
		}
	}
	
	/**
	 * Metoda getLiczbaZnakow() odpowiada za wyodrebnienie z projektu wartosci dla cechy LiczbaZnakow
	 * @param nazwaProjektu - parametrem wejsciowym jest nazwa projektu
	 * @return metoda zwraca liczbe znakow w calym projekcie
	 */
	@WebMethod(operationName = "getLiczbaZnakow", action = "urn:GetLiczbaZnakow")
	public int getLiczbaZnakow(@WebParam(name = "arg0") String nazwaProjektu){
		if(paczkaProjektow.getProject(nazwaProjektu) != null) {
			return paczkaProjektow.getProject(nazwaProjektu).liczbaZnakow;
		}else {
			return 0;
		}
	}
	
	/**
	 * Metoda getLiczbaZmiennychDanegoTypu() odpowiada za wyodrebnienie z projektu wartosci dla cechy LiczbaZmiennychDanegoTypu
	 * @param nazwaProjektu - parametrem wejsciowym jest nazwa projektu
	 * @return metoda zwraca liste uporzadkowana zawierajaca typ i liczbe zmiennych o takim typie w calym projekcie
	 */
	@WebMethod(operationName = "getLiczbaZmiennychDanegoTypu", action = "urn:GetLiczbaZmiennychDanegoTypu")
	public LinkedList<TypyZmiennych> getLiczbaZmiennychDanegoTypu(@WebParam(name = "arg0") String nazwaProjektu){
		if(paczkaProjektow.getProject(nazwaProjektu) != null && paczkaProjektow.getProject(nazwaProjektu).listaCech.contains(Cechy.LiczbaZmiennychDanegoTypuProperty.name())) {
			LinkedList<TypyZmiennych> lista = new LinkedList<TypyZmiennych>();
			
			paczkaProjektow.getProject(nazwaProjektu).liczbaZmiennychDanegoTypu.forEach((k,v)->{
				TypyZmiennych tz = new TypyZmiennych();
				tz.typ = k;
				tz.liczba = v;
				lista.add(tz);}
			);
			System.out.println("Udalo sie");
			return lista;
		}else {
			return null;
		}
	}
	
	/**
	 * Metoda getParadygmat() odpowiada za wyodrebnienie z projektu wartosci dla cechy ParadygmatProgramowania
	 * @param nazwaProjektu - parametrem wejsciowym jest nazwa projektu
	 * @return metoda zwraca nazwe paradygmatu programowania(obiektowy, strukturalny) przewazajacego w calym projekcie
	 */
	@WebMethod(operationName = "getParadygmat", action = "urn:GetParadygmat")
	public String getParadygmat(@WebParam(name = "arg0") String nazwaProjektu){
		if(paczkaProjektow.getProject(nazwaProjektu) != null && paczkaProjektow.getProject(nazwaProjektu).listaCech.contains(Cechy.ParadygmatProperty.name())) {
			return paczkaProjektow.getProject(nazwaProjektu).paradygmat;
		}else {
			return null;
		}
	}
	
	/**
	 * Metoda getWykorzystanieWielowatkowosci() odpowiada za wyodrebnienie z projektu wartosci dla cechy WykorzystanieWielowatkowosci
	 * @param nazwaProjektu - parametrem wejsciowym jest nazwa projektu
	 * @return metoda zwraca informacje(true, false) czy w calym projekcie wystapilo wykorzystanie wielowatkowosci
	 */
	@WebMethod(operationName = "getWykorzystanieWielowatkowosci", action = "urn:GetWykorzystanieWielowatkowosci")
	public Boolean getWykorzystanieWielowatkowosci(@WebParam(name = "arg0") String nazwaProjektu){
		if(paczkaProjektow.getProject(nazwaProjektu) != null && paczkaProjektow.getProject(nazwaProjektu).listaCech.contains(Cechy.MechanizmWielowatkowosciProperty.name())) {
			return paczkaProjektow.getProject(nazwaProjektu).wielowatkowosc;
		}else {
			return null;
		}
	}
	
	/**
	 * Metoda getMozliwosciWczytywaniaPlikow() odpowiada za wyodrebnienie z projektu wartosci dla cechy MozliwosciWczytywaniaPlikow
	 * @param nazwaProjektu - parametrem wejsciowym jest nazwa projektu
	 * @return metoda zwraca informacje(true, false) czy w calym projekcie wystapilo odwolanie do instrukcji, ktore sa uzywane do wprowadzania danych
	 */
	@WebMethod(operationName = "getMozliwosciWczytywaniaPlikow", action = "urn:GetMozliwosciWczytywaniaPlikow")
	public Boolean getMozliwosciWczytywaniaPlikow(@WebParam(name = "arg0") String nazwaProjektu){
		if(paczkaProjektow.getProject(nazwaProjektu) != null && paczkaProjektow.getProject(nazwaProjektu).listaCech.contains(Cechy.MozliwoscWczytywaniaPlikowProperty.name())) {
			boolean wczytywaniePlikow = false;
			
			for (Plik plik : paczkaProjektow.getProject(nazwaProjektu).listaPlikow) {
				if(plik.wczytywaniePlikow == true)
					wczytywaniePlikow = true;
					System.out.println(plik.sciezka);
					break;
			}
			System.out.println("Udalo sie");
			return wczytywaniePlikow;
		}else {
			return null;
		}
	}
	
	/**
	 * Metoda getJezykInterfejsu() odpowiada za wyodrebnienie z projektu wartosci dla cechy JezykInterfejsu
	 * @param nazwaProjektu - parametrem wejsciowym jest nazwa projektu
	 * @return metoda zwraca prefiks jezyka interfejsu(wedlug standardu ISO 639-1, np. pl, en) przewazajacego w calym projekcie
	 */
	@WebMethod(operationName = "getJezykInterfejsu", action = "urn:GetJezykInterfejsu")
	public String getJezykInterfejsu(@WebParam(name = "arg0") String nazwaProjektu){
		if(paczkaProjektow.getProject(nazwaProjektu) != null && paczkaProjektow.getProject(nazwaProjektu).listaCech.contains(Cechy.JezykInterfejsuProperty.name())) {
			return paczkaProjektow.getProject(nazwaProjektu).jezykInterfejsu;
		}else {
			System.out.println("Zwrocilem null przy jezykach");
			return null;
		}
	}
}
