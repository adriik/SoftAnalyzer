package webService;

import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.xml.ws.soap.Addressing;

import org.tempuri.PlagiatonWCF;

import com.microsoft.schemas._2003._10.serialization.arrays.ArrayOfstring;
import com.microsoft.schemas._2003._10.serialization.arrays.ObjectFactory;

import box.Pack;
import box.Project;
import box.Ziomek;


@WebService(targetNamespace = "http://webService/", portName = "ServiceSAPort", serviceName = "ServiceSAService")
@Addressing(enabled=false, required=false)
public class ServiceSA {
	
	Pack paczkaProjektow = new Pack();
	

	@WebMethod(operationName = "przeslijPlik", action = "urn:PrzeslijPlik")
	public void przeslijPlik(@WebParam(name = "arg0") String link) {

		try {
			String[] wynik1 = link.split("/");
			
			String sciezka = this.getClass().getClassLoader().getResource("").getPath().split("WEB-INF")[0].substring(1);
			
			URL website = new URL(link);
			ReadableByteChannel rbc = Channels.newChannel(website.openStream());
			System.out.println("Zapisalem projekt pod sciezka:\n" + sciezka + "archiwum/" + wynik1[wynik1.length-1]);
			FileOutputStream fos = new FileOutputStream(sciezka + "archiwum/" + wynik1[wynik1.length-1]);
			fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
			fos.close();
			
			paczkaProjektow.addProject(new Project(wynik1[wynik1.length-1]));
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	

//	@WebMethod(operationName = "getLiczbaPlikow", action = "urn:GetLiczbaPlikow")
//	public int getLiczbaPlikow(@WebParam(name = "arg0") String nazwaProjektu) {
//		return paczkaProjektow.getProject(nazwaProjektu).liczbaPlikow;
//	}
	


	public LinkedList<Ziomek> getRozmiaryPlikowKodow(String nazwaProjektu){
//		Response resp = new Response();
//		Project projekt = new Project("nowy");
//		projekt.rozmiaryPlikow.put("plik", 3);
//		paczkaProjektow.addProject(projekt);
//		
//        resp.setMapProperty(paczkaProjektow.getProject(nazwaProjektu).rozmiaryPlikow);
//        System.out.println("Byełem 3");
//		return resp;
		
		Map<String, Integer> books = new HashMap<String, Integer>();

        books.put("halo",1);
        books.put("halo2",2);
        
        LinkedList<Ziomek> lista=new LinkedList<Ziomek>();
        Ziomek ziomek = new Ziomek();
        ziomek.imie = "Adrian";
        ziomek.wartosc = 5;
        
        lista.add(ziomek);
        //Response resp = new Response();
        //resp.setMapProperty(books);
        
		ObjectFactory factory = new ObjectFactory();
		ArrayOfstring tablica = factory.createArrayOfstring();

		
		tablica.getString().add("liczbaZnakow");
		tablica.getString().add("jezyk");
		tablica.getString().add("jezykInterfejsu");
		
		
		PlagiatonWCF nowiutka = new PlagiatonWCF();
		
	
		String odpowiedz = nowiutka.getBasicHttpBindingIPlagiatonWCF().setAvailableMethods("nowy", tablica);
		
		System.out.println("Odpowiedz: " + odpowiedz);
        
        return lista;
	}
	

//	public ArrayList<String> getListaNazwPlikowKatalogow(String nazwaProjektu){
//		return paczkaProjektow.getProject(nazwaProjektu).listaNazwPlikow;
//		
//	}
//	
//
//	public Map<String,Integer> getLiczbaPlikowDanegoRozszerzenia(String nazwaProjektu){
//		return paczkaProjektow.getProject(nazwaProjektu).liczbaPlikowDanegoRozszerzenia;
//		
//	}
//	
//
//	public int getLiczbaDanychWejsciowych(String nazwaProjektu){
//		return paczkaProjektow.getProject(nazwaProjektu).liczbaDanychWejsciowych;
//		
//	}
//	
//
//	public Map<String,String> getSkrotyPlikow(String nazwaProjektu){
//		return paczkaProjektow.getProject(nazwaProjektu).skrotyPlikow;
//		
//	}
//	
//
//	public Map<String,Integer> getLiczbaPlikowDanegoTypu(String nazwaProjektu){
//		return paczkaProjektow.getProject(nazwaProjektu).liczbaPlikowDanegoTypu;
//		
//	}
//	
//
//	public int getLiczbaAtrybutow(String nazwaProjektu){
//		return paczkaProjektow.getProject(nazwaProjektu).liczbaAtrybutow;
//		
//	}
//	
//
//	public int getLiczbaMetod(String nazwaProjektu){
//		return paczkaProjektow.getProject(nazwaProjektu).liczbaMetod;
//		
//	}
//	
//
//	public Map<String,Integer> getLiczbaAtrybutowWKlasach(String nazwaProjektu){
//		return paczkaProjektow.getProject(nazwaProjektu).liczbaAtrybutowWKlasach;
//		
//	}
//	
//
//	public ArrayList<String> getZbiorBibliotek(String nazwaProjektu){
//		return paczkaProjektow.getProject(nazwaProjektu).zbiorBibliotek;
//		
//	}
//	
//
//	public int getLiczbaLiniiKodu(String nazwaProjektu){
//		return paczkaProjektow.getProject(nazwaProjektu).liczbaLiniiKodu;
//		
//	}
//	
//
//	public String getJezyk(String nazwaProjektu){
//		return paczkaProjektow.getProject(nazwaProjektu).jezyk;
//		
//	}
//	
//
//	public int getLiczbaZnakow(String nazwaProjektu){
//		return paczkaProjektow.getProject(nazwaProjektu).liczbaZnakow;
//		
//	}
//	
//
//	public Map<String,Integer> getLiczbaZmiennychDanegoTypu(String nazwaProjektu){
//		return paczkaProjektow.getProject(nazwaProjektu).liczbaZmiennychDanegoTypu;
//		
//	}
//	
//
//	public String getParadygmat(String nazwaProjektu){
//		return paczkaProjektow.getProject(nazwaProjektu).paradygmat;
//		
//	}
//	
//
//	public Boolean getWykorzystanieWielowatkowosci(String nazwaProjektu){
//		return paczkaProjektow.getProject(nazwaProjektu).wielowatkowosc;
//		
//	}
//	
//
//	public Boolean getMozliwosciWczytywaniaPlikow(String nazwaProjektu){
//		return paczkaProjektow.getProject(nazwaProjektu).wczytywaniePlikow;
//		
//	}
//	
//
//	public String getJezykInterfejsu(String nazwaProjektu){
//		return paczkaProjektow.getProject(nazwaProjektu).jezykInterfejsu;
//		
//	}
}
