package box;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
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
	
	
	public ArrayList<String> listaCech = new ArrayList<String>();
	
	public Project(String nazwa, String sciezka){
		this.nazwa = nazwa;
		this.sciezka = sciezka;
		
		this.setListaNazwPlikow(sciezka);
		this.setListaNazwKatalogow(sciezka);
		this.setLiczbaPlikow();
		this.setWieloWatkowosc();
		this.setParadygmat();
		this.setLiczbaAtrybutow();
		this.setLiczbaMetod();
		
		
	}
	
	private void setListaNazwPlikow(String sciezka) {
		listaPlikow = new ArrayList<Plik>();
		System.out.println("Sciezka zla: " + sciezka);
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

	public void setWieloWatkowosc() {
		String[] cRozszerzeniaPlikow = {"c", "h"};
		String[] cppRozszerzeniaPlikow = {"cpp", "cxx", "hxx"};
		String[] csRozszerzeniaPlikow = {"cs"};
		String[] javaRozszerzeniaPlikow = {"java"};
		
		String[] cStringThreads = {"pthread_t", "pthread_create", "pthread_cancel", "pthread_join"};
		String[] cppStringThreads = {"std::thread", "thread(", "thread ("};
		String[] csStringThreads = {"using System.Threading"};
		String[] javaStringThreads = {"new Thread(", "new Thread (", "extends Thread", ""};
		
		long multiWatkowosc = 0;
		
		for (Plik plik : listaPlikow) {
			
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
						reader.close();
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
							reader.close();
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
							reader.close();
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
							reader.close();
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
		}
		
		System.out.println("Wielowatkowosc = "+multiWatkowosc);
		if(multiWatkowosc > 0) {
			wielowatkowosc = true;
		} else {
			wielowatkowosc = false;
		}
	}
	
	public boolean getWieloWatkowosc() {
		return wielowatkowosc;
	}
	
	
	public void setParadygmat() {
		
		long liczbaParadygmat = 0;
		
		String[] cRozszerzeniaPlikow = {"c", "h"};
		String[] otherRozszerzeniaPlikow = {"cpp", "cxx", "hxx", "cs", "java"};
		
		String[] classStrings = {"class"};
		
		
		for (Plik plik : listaPlikow) {
		
			boolean isParadygmat = false;
			boolean isRozpoznaneRozszerzenie = false;
			
			//Dla C:
			for(String rozszerzenie : cRozszerzeniaPlikow) {
				
				if(plik.rozszerzenie.toLowerCase().equals(rozszerzenie)) {
					
					//Rozpoznano jêzyk C, który nie jest obiektowy
					isRozpoznaneRozszerzenie = true;
					liczbaParadygmat--;
					System.out.println("C obiektowoœæ-- = "+liczbaParadygmat);
				}
			}
			
			if(!isRozpoznaneRozszerzenie) {
				//Dla C++, C#, Java:
				for(String rozszerzenie : otherRozszerzeniaPlikow) {
					
					if(plik.rozszerzenie.toLowerCase().equals(rozszerzenie)) {
						
						isRozpoznaneRozszerzenie = true;
						try {
							BufferedReader reader = new BufferedReader(new FileReader(plik.sciezka));
							String linia = reader.readLine();
							while (linia != null && !isParadygmat) {
								for(String classString : classStrings) {
									if(linia.contains(classString)) {
										//Jest obiektowoœæ
										liczbaParadygmat++;
										System.out.println("obiektowoœæ++ = "+liczbaParadygmat);
										isParadygmat = true;
										break;
									}
								}
								
								linia = reader.readLine();
							}
							reader.close();
							if(!isParadygmat) {
								liczbaParadygmat--;
								System.out.println("obiektowoœæ-- = "+liczbaParadygmat);
							}
							
						} catch (IOException e) {
							System.out.println("Problem w odczycie pliku do badania paradygmatu w jêzyku C");
							e.printStackTrace();
						}
						
					}
				}
			}
		}
		
		System.out.println("Paradygmat: "+liczbaParadygmat);
		if(liczbaParadygmat > 0) {
			paradygmat = "obiektowy";
		} else {
			paradygmat = "strukturalny";
		}
	}
	
	String getParadygmat() {
		return paradygmat;
	}
	
	public void setLiczbaAtrybutow() {
		liczbaAtrybutow = 0;
		
		for (Plik plik : listaPlikow) {
			liczbaAtrybutow=liczbaAtrybutow+plik.liczbaAtrybutow;
			System.out.println("Liczba:"+liczbaAtrybutow);
		}
	}
	
	public void setLiczbaMetod() {
		
		for (Plik plik : listaPlikow) {
			try {
				BufferedReader reader = new BufferedReader(new FileReader(plik.sciezka));
				String line;
				
				ArrayList<String> keywords_infile = new ArrayList<String>();
				keywords_infile.add("byte");
				keywords_infile.add("short");
				keywords_infile.add("int");
				keywords_infile.add("long");
				keywords_infile.add("double");
				keywords_infile.add("float");
				keywords_infile.add("string");
				keywords_infile.add("boolean");
				keywords_infile.add("bool");
				keywords_infile.add("char");
				keywords_infile.add("decimal");
				keywords_infile.add("void");

				while ((line=reader.readLine())!=null){
					for(String a : keywords_infile)
			        {
						line=line.toUpperCase();
						a=a.toUpperCase();
			
						Pattern pattern1 = Pattern.compile("\\A"+a+"\\s.*\\(.*\\)");
			    	    Matcher matcher1 = pattern1.matcher(line);
			    	    while(matcher1.find()) 
			    	    	liczbaMetod++;
			    	    
			    	    Pattern pattern2 = Pattern.compile("\\A\\D+"+a+"\\s.*\\(.*\\)");
			    	    Matcher matcher2 = pattern2.matcher(line);
			    	    while(matcher2.find()) 
			    	    	liczbaMetod++;
					}
				   }                           
				reader.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				System.out.println("Cos nie tak przy wyliczaniu liczby metod");
			}
		}
	}
	
}



