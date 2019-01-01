package webService;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.Map.Entry;

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

@WebService(targetNamespace = "http://webService/", portName = "ServiceSAPort", serviceName = "ServiceSAService")
@Addressing(enabled = false, required = false)
public class ServiceSA {

	//https://plagiaton.blob.core.windows.net/blob-projects/cWPZPVBstlPozJwC3SW4bt9nPmU51YZD6OlNTh37s5ulyML7oJX05fiTWBjCbGBEvlrUxTSVZZMf8XYcLWQra9I1FB0s7B5VEZ0n.zip
	//https://repo.gentics.com/artifactory/lan.releases/com/gentics/mesh/mesh-demo/0.6.18/mesh-demo-0.6.18-dump.zip
	//https://github.com/jorgeacetozi/java-threads-examples/archive/master.zip
	
	Pack paczkaProjektow = new Pack();

	@WebMethod(operationName = "przeslijPlik", action = "urn:PrzeslijPlik")
	public void przeslijPlik(@WebParam(name = "arg0") String link) {

		try {
			String[] wynik1 = link.split("/");

			String sciezka = this.getClass().getClassLoader().getResource("").getPath().split("WEB-INF")[0]
					.substring(1).replace("%20", " ");

			URL website = new URL(link);
			ReadableByteChannel rbc = Channels.newChannel(website.openStream());
			System.out.println("Zapisalem projekt pod sciezka:\n" + sciezka + "archiwum/" + wynik1[wynik1.length - 1]); // pelna
																														// sciezka
			FileOutputStream fos = new FileOutputStream(sciezka + "archiwum/" + wynik1[wynik1.length - 1]);
			fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
			fos.close();

			try {
				ZipFile zipFile = new ZipFile(sciezka + "archiwum/" + wynik1[wynik1.length - 1]);
				zipFile.extractAll(sciezka + "archiwum" + "/"
						+ wynik1[wynik1.length - 1].substring(0, wynik1[wynik1.length - 1].lastIndexOf('.')));

			} catch (ZipException e) {
				e.printStackTrace();
				System.out.println("Cos poszlo nie tak podczas wypakowania!");
			}
			
			Project projekt = new Project(wynik1[wynik1.length - 1], sciezka + "archiwum/"
					+ wynik1[wynik1.length - 1].substring(0, wynik1[wynik1.length - 1].lastIndexOf('.')));
			paczkaProjektow.addProject(projekt); // projekt o nazwie XXXXX.zip

//			SAXReader reader = new SAXReader();
//			Document document = null;
//			try {
//				document = reader.read(sciezka + "uploads/SAML.xmi");
//			} catch (DocumentException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//
//			List<Node> nodeList = document.selectNodes("//*[name() = 'ownedAttribute']");
//
//			for (Node node : nodeList) {
//
//				Element element = (Element) node;
//				for (Cechy st : Cechy.values()) {
//					if (element.attributeValue("name").equals(st.name())) {
//						projekt.listaCech.add(st.name());
//						break;
//					}
//				}
//			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@WebMethod(operationName = "getLiczbaPlikow", action = "urn:GetLiczbaPlikow")
	public int getLiczbaPlikow(@WebParam(name = "arg0") String nazwaProjektu) {
		return paczkaProjektow.getProject(nazwaProjektu).liczbaPlikow;
	}

	@WebMethod(operationName = "getRozmiaryPlikowKodow", action = "urn:GetRozmiaryPlikowKodow")
	public LinkedList<RozmiaryPlikow> getRozmiaryPlikowKodow(@WebParam(name = "arg0") String nazwaProjektu) {
		if(paczkaProjektow.getProject(nazwaProjektu) != null) {
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

	@WebMethod(operationName = "getListaNazwPlikow", action = "urn:GetListaNazwPlikow")
	public ArrayList<String> getListaNazwPlikow(@WebParam(name = "arg0") String nazwaProjektu) {

		ArrayList<String> listaNazwPlikow = new ArrayList<String>();
		if (paczkaProjektow.getProject(nazwaProjektu) != null) {
			for (Plik plik : paczkaProjektow.getProject(nazwaProjektu).listaPlikow) {
				listaNazwPlikow.add(plik.nazwa);
			}
			return listaNazwPlikow;
		} else {
			return null;
		}

	}

	@WebMethod(operationName = "getListaNazwKatalogow", action = "urn:GetListaNazwKatalogow")
	public ArrayList<String> getListaNazwKatalogow(@WebParam(name = "arg0") String nazwaProjektu) {

		ArrayList<String> listaNazwKatalogow = new ArrayList<String>();
		if (paczkaProjektow.getProject(nazwaProjektu) != null) {
			for (Katalog katalog : paczkaProjektow.getProject(nazwaProjektu).listaKatalogow) {
				listaNazwKatalogow.add(katalog.nazwa);
			}
			return listaNazwKatalogow;
		} else {
			return null;
		}
	}
	
	@WebMethod(operationName = "getLiczbaLiniiKodu", action = "urn:GetLiczbaLiniiKodu")
	public LinkedList<LiczbaLiniiKodu> getLiczbaLiniiKodu(@WebParam(name = "arg0") String nazwaProjektu){
		if(paczkaProjektow.getProject(nazwaProjektu) != null) {
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
	
	@WebMethod(operationName = "getSkrotyPlikow", action = "urn:GetSkrotyPlikow")
	public LinkedList<HashePlikow> getSkrotyPlikow(@WebParam(name = "arg0") String nazwaProjektu){
		if(paczkaProjektow.getProject(nazwaProjektu) != null) {
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
	

	@WebMethod(operationName = "getLiczbaPlikowDanegoRozszerzenia", action = "urn:GetLiczbaPlikowDanegoRozszerzenia")
	public LinkedList<RozszerzeniaPlikow> getLiczbaPlikowDanegoRozszerzenia(@WebParam(name = "arg0") String nazwaProjektu){
		return null;
	}
	

	@WebMethod(operationName = "getLiczbaDanychWejsciowych", action = "urn:GetLiczbaDanychWejsciowych")
	public int getLiczbaDanychWejsciowych(@WebParam(name = "arg0") String nazwaProjektu){
		return paczkaProjektow.getProject(nazwaProjektu).liczbaDanychWejsciowych;
	}
	


	

	@WebMethod(operationName = "getLiczbaPlikowDanegoTypu", action = "urn:GetLiczbaPlikowDanegoTypu")
	public LinkedList<TypyPlikow> getLiczbaPlikowDanegoTypu(@WebParam(name = "arg0") String nazwaProjektu){
		if(paczkaProjektow.getProject(nazwaProjektu) != null) {
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
	

	@WebMethod(operationName = "getLiczbaAtrybutow", action = "urn:GetLiczbaAtrybutow")
	public int getLiczbaAtrybutow(@WebParam(name = "arg0") String nazwaProjektu){
		return paczkaProjektow.getProject(nazwaProjektu).liczbaAtrybutow;
		
	}
	

	@WebMethod(operationName = "getLiczbaMetod", action = "urn:GetLiczbaMetod")
	public int getLiczbaMetod(@WebParam(name = "arg0") String nazwaProjektu){
		return paczkaProjektow.getProject(nazwaProjektu).liczbaMetod;
		
	}
	

	@WebMethod(operationName = "getLiczbaAtrybutowWKlasach", action = "urn:GetLiczbaAtrybutowWKlasach")
	public LinkedList<AtrybutyPlikow> getLiczbaAtrybutowWKlasach(@WebParam(name = "arg0") String nazwaProjektu){
		return null;
	}
	

	@WebMethod(operationName = "getZbiorBibliotek", action = "urn:GetZbiorBibliotek")
	public ArrayList<String> getZbiorBibliotek(@WebParam(name = "arg0") String nazwaProjektu){
		if(paczkaProjektow.getProject(nazwaProjektu) != null) {
			ArrayList<String> lista = new ArrayList<String>();
			
			for (Plik plik : paczkaProjektow.getProject(nazwaProjektu).listaPlikow) {
				for(String s : plik.zbiorBibliotek)
		        {
					if (!lista.contains(s)) 
						lista.add(s);
		        }
			}
			System.out.println("Udalo sie");
			return lista;
		}else {
			return null;
		}
	}
	

	@WebMethod(operationName = "getJezykProgramowania", action = "urn:GetJezykProgramowania")
	public String getJezykProgramowania(@WebParam(name = "arg0") String nazwaProjektu){
		return paczkaProjektow.getProject(nazwaProjektu).jezyk;
	}
	

	@WebMethod(operationName = "getLiczbaZnakow", action = "urn:GetLiczbaZnakow")
	public int getLiczbaZnakow(@WebParam(name = "arg0") String nazwaProjektu){
		return paczkaProjektow.getProject(nazwaProjektu).liczbaZnakow;
		
	}
	

	@WebMethod(operationName = "getLiczbaZmiennychDanegoTypu", action = "urn:GetLiczbaZmiennychDanegoTypu")
	public LinkedList<TypyZmiennych> getLiczbaZmiennychDanegoTypu(@WebParam(name = "arg0") String nazwaProjektu){
		return null;
	}
	
	//Adam
	@WebMethod(operationName = "getParadygmat", action = "urn:GetParadygmat")
	public String getParadygmat(@WebParam(name = "arg0") String nazwaProjektu){

		
		return paczkaProjektow.getProject(nazwaProjektu).paradygmat;
	}
	
	//Adam
	@WebMethod(operationName = "getWykorzystanieWielowatkowosci", action = "urn:GetWykorzystanieWielowatkowosci")
	public Boolean getWykorzystanieWielowatkowosci(@WebParam(name = "arg0") String nazwaProjektu){
		
		/*
		String[] cRozszerzeniaPlikow = {"c", "h"};
		String[] cppRozszerzeniaPlikow = {"cpp", "cxx", "hxx"};
		String[] csRozszerzeniaPlikow = {"cs"};
		String[] javaRozszerzeniaPlikow = {"java"};
		
		String[] cStringThreads = {"pthread_t", "pthread_create", "pthread_cancel", "pthread_join"};
		String[] cppStringThreads = {"std::thread", "thread(", "thread ("};
		String[] csStringThreads = {"using System.Threading"};
		String[] javaStringThreads = {"new Thread(", "new Thread (", "extends Thread", ""};
		
		long multiWatkowosc = 0;
		
		for (Plik plik : paczkaProjektow.getProject(nazwaProjektu).listaPlikow) {
			
			boolean isMultiWatkowosc = false;
			boolean isRozpoznaneRozszerzenie = false;
			
			//Dla C:
			for(String rozszerzenie : cRozszerzeniaPlikow) {
				
				if(plik.rozszerzenie.toLowerCase().equals(rozszerzenie)) {
					
					isRozpoznaneRozszerzenie = true;
					try {
						BufferedReader reader = new BufferedReader(new FileReader(plik.sciezka));
						String linia = reader.readLine();
						while (linia != null && !isMultiWatkowosc) {
							for(String cString : cStringThreads) {
								if(linia.contains(cString)) {
									//JEST MULTIWATKOWOSC
									multiWatkowosc++;
									isMultiWatkowosc = true;
									break;
								}
							}
							
							linia = reader.readLine();
						}
						if(!isMultiWatkowosc) {
							multiWatkowosc--;
						}
						
					} catch (IOException e) {
						System.out.println("Problem w odczycie pliku do badania wielow¹tkowoœci w jêzyku C");
						e.printStackTrace();
					}
					
				}
			}
			
			//Dla C++:
			if(!isRozpoznaneRozszerzenie) {
				for(String rozszerzenie : cppRozszerzeniaPlikow) {
					if(plik.rozszerzenie.toLowerCase().equals(rozszerzenie)) {
						
						isRozpoznaneRozszerzenie = true;
						try {
							BufferedReader reader = new BufferedReader(new FileReader(plik.sciezka));
							String linia = reader.readLine();
							while (linia != null && !isMultiWatkowosc) {
								for(String cppString : cppStringThreads) {
									if(linia.contains(cppString)) {
										//JEST MULTIWATKOWOSC
										multiWatkowosc++;
										isMultiWatkowosc = true;
										break;
									}
								}
								
								linia = reader.readLine();
							}
							if(!isMultiWatkowosc) {
								multiWatkowosc--;
							}
							
						} catch (IOException e) {
							System.out.println("Problem w odczycie pliku do badania wielow¹tkowoœci w jêzyku C++");
							e.printStackTrace();
						}
						
					}
				}
			}
			
			//Dla C#:
			if(!isRozpoznaneRozszerzenie) {
				for(String rozszerzenie : csRozszerzeniaPlikow) {
					if(plik.rozszerzenie.toLowerCase().equals(rozszerzenie)) {
						
						isRozpoznaneRozszerzenie = true;
						try {
							BufferedReader reader = new BufferedReader(new FileReader(plik.sciezka));
							String linia = reader.readLine();
							while (linia != null && !isMultiWatkowosc) {
								for(String csString : csStringThreads) {
									if(linia.contains(csString)) {
										//JEST MULTIWATKOWOSC
										multiWatkowosc++;
										isMultiWatkowosc = true;
										break;
									}
								}
								
								linia = reader.readLine();
							}
							if(!isMultiWatkowosc) {
								multiWatkowosc--;
							}
							
						} catch (IOException e) {
							System.out.println("Problem w odczycie pliku do badania wielow¹tkowoœci w jêzyku C#");
							e.printStackTrace();
						}
						
					}
				}
			}
			
			//Dla Java:
			if(!isRozpoznaneRozszerzenie) {
				for(String rozszerzenie : javaRozszerzeniaPlikow) {
					if(plik.rozszerzenie.toLowerCase().equals(rozszerzenie)) {
						
						isRozpoznaneRozszerzenie = true;
						try {
							BufferedReader reader = new BufferedReader(new FileReader(plik.sciezka));
							String linia = reader.readLine();
							while (linia != null && !isMultiWatkowosc) {
								for(String javaString : javaStringThreads) {
									if(linia.contains(javaString)) {
										//JEST MULTIWATKOWOSC
										multiWatkowosc++;
										isMultiWatkowosc = true;
										break;
									}
								}
								
								linia = reader.readLine();
							}
							if(!isMultiWatkowosc) {
								multiWatkowosc--;
							}
							
						} catch (IOException e) {
							System.out.println("Problem w odczycie pliku do badania wielow¹tkowoœci w jêzyku Java");
							e.printStackTrace();
						}
						
					}
				}
			}
			
			
			System.out.println("Wielowatkowosc = "+multiWatkowosc);
			if(multiWatkowosc > 0) {
				paczkaProjektow.getProject(nazwaProjektu).wielowatkowosc = true;
			} else {
				paczkaProjektow.getProject(nazwaProjektu).wielowatkowosc = false;
			}
		}
		*/
		
		return paczkaProjektow.getProject(nazwaProjektu).wielowatkowosc;
	}
	

	@WebMethod(operationName = "getMozliwosciWczytywaniaPlikow", action = "urn:GetMozliwosciWczytywaniaPlikow")
	public Boolean getMozliwosciWczytywaniaPlikow(@WebParam(name = "arg0") String nazwaProjektu){
		if(paczkaProjektow.getProject(nazwaProjektu) != null) {
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
	

	@WebMethod(operationName = "getJezykInterfejsu", action = "urn:GetJezykInterfejsu")
	public String getJezykInterfejsu(@WebParam(name = "arg0") String nazwaProjektu){
		return paczkaProjektow.getProject(nazwaProjektu).jezykInterfejsu;
	}
}
