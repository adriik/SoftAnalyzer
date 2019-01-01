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
		this.setLiczbaPlikowDanegoTypu();
		this.setJezykProgramowania();
		
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
	
	private void setLiczbaPlikowDanegoTypu() {
		//zmienne do przechowywania liczby plików danego typu 
		int liczbaPlikowTekstowych =0 , liczbaPlikowMultimedialnych = 0, liczbaPlikowWykonywalnych = 0, liczbaPlikowPozostalych = 0;
		
		//tablice do przechowywania rozszerzen plikow danego typu
		String[] tekstoweRozszerzeniePlikow = 		{"1st","602","_doc","_docx","abw","act","adoc","aim","ans","apkg","apt","asc","ascii","ase","aty","awp","awt","aww",
													 "bad","bbs","bdp","bdr","bean","bib","bibtex","bml","bna","boc","brx","btd","bzabw","calca","charset","chart","chord",
													 "cnm","cod","crwl","cws","cyi","dca","dfti","dgs","diz","dne","doc","docm","docx","docxml","docz","dox","dropbox","dsc",
													 "dvi","dwd","dx","dxb","dxp","eio","eit","emf","emlx","epp","err","etf","etx","euc","faq","fbl","fcf","fdf","fdr","fds",
													 "fdt","fdx","fdxt","fft","fgs","flr","fodt","fountain","fpt","frt","fwdn","gmd","gpd","gpn","gsd","gthr","gv","hbk","hht",
													 "hs","hwp","hz","idx","iil","ipf","ipsport","jarvis","jis","jnp","joe","jp1","jrtf","jtd","kes","klg","knt","kon","kwd",
													 "latex","lbt","lis","lnt","log","lp2","lst","lst","ltr","ltx","lue","luf","lwp","lxfml","lyt","lyx","man","mbox","mcw",
													 "me","mell","mellel","min","mnt","msg","mw","mwd","mwp","nb","ndoc","nfo","ngloss","njx","note","notes","now","nwctxt",
													 "nwm","nwp","ocr","odif","odm","odo","odt","ofl","opeico","openbsd","ort","ott","p7s","pages","pages-tef","pdpcmd","pfx",
													 "pjt","plain","plantuml","pmo","prt","psw","psw","pu","pvj","pvm","pwd","pwdp","pwdpl","pwi","pwr","qdl","qpf","rad","readme",
													 "rft","ris","rpt","rst","rtd","rtf","rtfd","rtx","run","rvf","rzk","rzn","saf","safetext","sam","save","scc","scm","scriv",
													 "scrivx","sct","scw","sdm","sdoc","sdw","se","session","sgm","sig","skcard","sla","smf","sms","ssa","story","strings","stw",
													 "sty","sxg","sxw","tab","tdf","template","tex","text","textclipping","thp","tlb","tm","tmd","tmdx","tmv","tmvx","tpc","trelby",
													 "tvj","txt","u3i","unauth","unx","uof","uot","upd","utf8","utxt","vct","vnt","vw","wbk","webdoc","wn","wp","wp4","wp5","wp6",
													 "wp7","wpa","wpd","wpl","wps","wpt","wpw","wri","wsd","wtt","wtx","xbdoc","xbplate","xdl","xwp","xy","xy3","xyp","xyw","zabw",
													 "zrtf","zw"};
		String[] multimedialneeRozszerzeniePlikow = {"jpeg","jpg","jpe","jif","jfif","jfi","jp2","jpx","jmp","mj2","tif","tiff","png","gif","bmp","dib","svg","svgz",
													 "eps", "3gp","aa","aac","aax","act","aiff","amr","ape","au","awb","dct","dss","dvf","flac","gsm","iklax","ivs",
													 "m4a","m4b","m4p","mmf","mp3","mpc","msv","nsf","ogg","oga","mogg","opus","ra","rm","raw","sln","tta","vox","wav",
													 "wma","wv","webm","8svx", "3gp","asf","avi","dvr-ms","flv","f4v","f4p","f4a","f4b","iff","mkv","mj2","mov","qt","mpg",
													 "mpeg","m2p","ps","ts","tsv","tsa","mp4","m4a","m4p","m4b","m4r","m4v","ogg","ogv","oga","ogx","ogm","ogm","spx","opus",
													 "rm"};
		String[] wykonywalneRozszerzeniePlikow = 	{"0xe","73k","89k","8ck","a6p","a7r","ac","acc","acr","actc","action","actm","ahk","air","apk","app","applescript","arscript",
													 "asb","azw2","ba_","bat","beam","bin","btm","caction","cel","celx","cgi","cmd","cof","coffee","com","command","csh","cyw","dek",
													 "dld","dmc","ds","dxl","e_e","ear","ebm","ebs","ebs2","ecf","eham","elf","epk","epk","es","esh","ex4","ex5","ex_","exe","exe1",
													 "exopc","ezs","ezt","fas","fky","fpi","frs","fxp","gadget","gpe","gs","ham","hms","hpf","hta","icd","iim","ipa","ipf","isu",
													 "ita","jar","js","jse","jsf","jsx","kix","ksh","kx","lo","ls","m3g","mac","mam","mcr","mel","mem","mio","mlx","mm","mpx","mrc",
													 "mrp","ms","msl","mxe","n","nexe","ore","osx","otm","out","paf","pex","phar","pif","plsc","plx","prc","prg","ps1","pvd","pwc",
													 "pyc","pyo","qit","qpx","rbf","rbx","rfu","rgs","rox","rpj","run","rxe","s2a","sbs","sca","scar","scb","scpt","scptd","scr",
													 "script","sct","seed","server","shb","smm","spr","tcp","thm","tiapp","tms","u3p","udf","upx","vbe","vbs","vbscript","vdo",
													 "vexe","vlx","vmp","vxp","wcm","widget","wiz","workflow","wpk","wpm","ws","wsf","wsh","x86","xap","xbap","xlm","xqt","xqt",
													 "xys","zl9"};
		
		for (Plik plik : listaPlikow) {
		
			//flaga
			boolean isRozpoznaneRozszerzenie = false;
			
			//dla plików tekstowych
			for(String rozszerzenie : tekstoweRozszerzeniePlikow) {
				if(plik.rozszerzenie.toLowerCase().equals(rozszerzenie)) {
					liczbaPlikowTekstowych++;
					isRozpoznaneRozszerzenie = true;
				}
				
			}
			//dla plików graficznych
			if(!isRozpoznaneRozszerzenie) {
				for(String rozszerzenie : multimedialneeRozszerzeniePlikow) {
					if(plik.rozszerzenie.toLowerCase().equals(rozszerzenie)) {
						liczbaPlikowMultimedialnych++;
						isRozpoznaneRozszerzenie = true;
					}
					
				}
			}
			//dla plików dŸwiêkowych
			if(!isRozpoznaneRozszerzenie) {
				for(String rozszerzenie : wykonywalneRozszerzeniePlikow) {
					if(plik.rozszerzenie.toLowerCase().equals(rozszerzenie)) {
						liczbaPlikowWykonywalnych++;
						isRozpoznaneRozszerzenie = true;
					}
					
				}
			}
			//dla plików filmowych
			if(!isRozpoznaneRozszerzenie) {
						liczbaPlikowPozostalych++;
						isRozpoznaneRozszerzenie = true;
					}
			
		}
		
		System.out.println("Liczba plików tekstowych: "    + liczbaPlikowTekstowych +
						   "\nLiczba plików multimedialnych: " + liczbaPlikowMultimedialnych +
						   "\nLiczba plików dzwiêkowych: " + liczbaPlikowWykonywalnych +
						   "\nLiczba pozosta³ych plików: "   + liczbaPlikowPozostalych);
		
		liczbaPlikowDanegoTypu.put("Liczba plików tekstowych: ", liczbaPlikowTekstowych);
		liczbaPlikowDanegoTypu.put("Liczba plików multimedialnych: ", liczbaPlikowMultimedialnych);
		liczbaPlikowDanegoTypu.put("Liczba plików wykonywalnych: ", liczbaPlikowWykonywalnych);
		liczbaPlikowDanegoTypu.put("Liczba pozosta³ych plików: ", liczbaPlikowPozostalych);
		
		
	}
	
	private void setJezykProgramowania() {
		String[] cRozszerzeniaPlikow = {"c", "h"};
		String[] cppRozszerzeniaPlikow = {"cpp", "cxx", "hxx"};
		String[] csRozszerzeniaPlikow = {"cs"};
		String[] javaRozszerzeniaPlikow = {"java"};
		
		for (Plik plik : listaPlikow) {
			boolean isRozpoznaneRozszerzenie = false;
			
			for(String rozszerzenie : cRozszerzeniaPlikow) {
				if(plik.rozszerzenie.toLowerCase().equals(rozszerzenie)) {
					isRozpoznaneRozszerzenie = true;
					jezyk = "C";
				}
				
			}
			if(!isRozpoznaneRozszerzenie) {
				for(String rozszerzenie : cppRozszerzeniaPlikow) {
					if(plik.rozszerzenie.toLowerCase().equals(rozszerzenie)) {
						isRozpoznaneRozszerzenie = true;
						jezyk = "C++";
					}
					
				}
			}
			
			if(!isRozpoznaneRozszerzenie) {
				for(String rozszerzenie : csRozszerzeniaPlikow) {
					if(plik.rozszerzenie.toLowerCase().equals(rozszerzenie)) {
						isRozpoznaneRozszerzenie = true;
						jezyk = "C#";
					}
					
				}
			}
			
			if(!isRozpoznaneRozszerzenie) {
				for(String rozszerzenie : javaRozszerzeniaPlikow) {
					if(plik.rozszerzenie.toLowerCase().equals(rozszerzenie)) {
						isRozpoznaneRozszerzenie = true;
						jezyk = "Java";
					}
					
				}
			}
			
			if(!isRozpoznaneRozszerzenie) {
				isRozpoznaneRozszerzenie = true;
				jezyk = "Inny";
			}
			
			
		}
	}
	
	private String getJezykProgramowania() {
		return jezyk;
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
}
