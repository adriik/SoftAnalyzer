package box;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Project {

	private String sciezka;
	private String nazwa;
	
	public int liczbaPlikow;
	public Map<String,Integer> rozmiaryPlikow = new HashMap<String,Integer>();
	public ArrayList<String> listaNazwPlikow; // TODO: katalogow?
	public Map<String,Integer> liczbaPlikowDanegoRozszerzenia = new HashMap<String,Integer>();
	public int liczbaDanychWejsciowych;
	public Map<String,String> skrotyPlikow = new HashMap<String,String>();
	public Map<String,Integer> liczbaPlikowDanegoTypu = new HashMap<String,Integer>();
	public int liczbaAtrybutow;
	public int liczbaMetod;
	public Map<String,Integer> liczbaAtrybutowWKlasach = new HashMap<String,Integer>();
	public ArrayList<String> zbiorBibliotek;
	public int liczbaLiniiKodu;
	public String jezyk;
	public int liczbaZnakow;
	public Map<String,Integer> liczbaZmiennychDanegoTypu = new HashMap<String,Integer>();
	public String paradygmat;
	public Boolean wielowatkowosc;
	public Boolean wczytywaniePlikow;
	public String jezykInterfejsu;
	
	public Project(String nazwa){
		this.nazwa = nazwa;
	}
	
	public String getSciezka() {
		return sciezka;
	}
	public void setSciezka(String sciezka) {
		this.sciezka = sciezka;
	}
	public String getNazwa() {
		return nazwa;
	}
	public void setNazwa(String nazwa) {
		this.nazwa = nazwa;
	}

	
	
}
