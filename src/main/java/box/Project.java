package box;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Project {

	private String sciezka;
	private String nazwa;
	
	public int liczbaPlikow;
	public Map<String,Integer> rozmiaryPlikow = new HashMap<String,Integer>();
	public ArrayList<Plik> listaPlikow; 
	public ArrayList<Katalog> listaKatalogow; 
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
	
	public Project(String nazwa, String sciezka){
		this.nazwa = nazwa;
		this.sciezka = sciezka;
		
		this.setListaNazwPlikow(sciezka);
		this.setListaNazwKatalogow(sciezka);
		this.setLiczbaPlikow();
		
		
	}
	
	private void setListaNazwPlikow(String sciezka) {
		listaPlikow = new ArrayList<Plik>();
		try (Stream<Path> paths = Files.walk(Paths.get(sciezka))) {
			listaPlikow = (ArrayList<Plik>) paths
		        .filter(Files::isRegularFile)
		        .map(p -> new Plik(p.toString().substring(p.toString().lastIndexOf("\\")+1, p.toString().length()),p.toString()))
		        .collect(Collectors.toList());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void setListaNazwKatalogow(String sciezka) {
		listaKatalogow = new ArrayList<Katalog>();
		try (Stream<Path> paths = Files.walk(Paths.get(sciezka))) {
			listaKatalogow = (ArrayList<Katalog>) paths
		        .filter(Files::isDirectory)
		        .map(p -> new Katalog(p.toString().substring(p.toString().lastIndexOf("\\")+1, p.toString().length()),p.toString()))
		        .collect(Collectors.toList());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void setLiczbaPlikow() {
		this.liczbaPlikow = listaPlikow.size();
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
