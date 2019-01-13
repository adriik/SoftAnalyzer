package box;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.Set;
import java.util.Collection;

public class Project {

	private String sciezka;
	private String nazwa;

	public int liczbaPlikow;
	public ArrayList<Plik> listaPlikow;
	public ArrayList<Katalog> listaKatalogow;
	public Map<String, Integer> liczbaPlikowDanegoRozszerzenia = new HashMap<String, Integer>();
	public int liczbaDanychWejsciowych;
	public Map<String, Integer> liczbaPlikowDanegoTypu = new HashMap<String, Integer>();
	public int liczbaAtrybutow;
	public int liczbaMetod;
	public String jezyk;
	public int liczbaZnakow;
	public Map<String, Integer> liczbaZmiennychDanegoTypu = new HashMap<String, Integer>();
	public String paradygmat;
	public Boolean wielowatkowosc;
	public String jezykInterfejsu;

	public ArrayList<String> listaCech = new ArrayList<String>();

	public Project(String nazwa, String sciezka) {
		this.nazwa = nazwa;
		this.sciezka = sciezka;

		this.setListaNazwPlikow(sciezka);
		this.setListaNazwKatalogow(sciezka);
		this.setLiczbaPlikow();
		this.setWieloWatkowosc();
		this.setParadygmat();
		this.setLiczbaPlikowDanegoTypu();
		this.setJezykProgramowania();
		this.setLiczbaAtrybutow();
		this.setLiczbaZnakow();
		this.setJezykInterfejsu();
		this.setLiczbaMetod();
		this.setliczbaPlikowDanegoRozszerzenia();
		this.setliczbaZmiennychDanegoTypu();
		this.setLiczbaDanychWejsciowych();
	}

	private void setLiczbaDanychWejsciowych() {
		for (Plik plik : listaPlikow) {
			this.liczbaDanychWejsciowych += plik.liczbaDanychWejsciowych;
		}
	}

	private void setListaNazwPlikow(String sciezka) {
		listaPlikow = new ArrayList<Plik>();
		System.out.println("Sciezka zla: " + sciezka);
		try (Stream<Path> paths = Files.walk(Paths.get(sciezka))) {
			listaPlikow = (ArrayList<Plik>) paths.filter(Files::isRegularFile)
					.map(p -> new Plik(
							p.toString().substring(p.toString().lastIndexOf("\\") + 1, p.toString().length()),
							p.toString()))
					.collect(Collectors.toList());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void setListaNazwKatalogow(String sciezka) {
		listaKatalogow = new ArrayList<Katalog>();
		try (Stream<Path> paths = Files.walk(Paths.get(sciezka))) {
			listaKatalogow = (ArrayList<Katalog>) paths.filter(Files::isDirectory)
					.map(p -> new Katalog(
							p.toString().substring(p.toString().lastIndexOf("\\") + 1, p.toString().length()),
							p.toString()))
					.collect(Collectors.toList());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void setLiczbaPlikowDanegoTypu() {
		// zmienne do przechowywania liczby plików danego typu
		int liczbaPlikowTekstowych = 0, liczbaPlikowMultimedialnych = 0, liczbaPlikowWykonywalnych = 0,
				liczbaPlikowPozostalych = 0;

		// tablice do przechowywania rozszerzen plikow danego typu
		String[] tekstoweRozszerzeniePlikow = { "1st", "602", "_doc", "_docx", "abw", "act", "adoc", "aim", "ans",
				"apkg", "apt", "asc", "ascii", "ase", "aty", "awp", "awt", "aww", "bad", "bbs", "bdp", "bdr", "bean",
				"bib", "bibtex", "bml", "bna", "boc", "brx", "btd", "bzabw", "calca", "charset", "chart", "chord",
				"cnm", "cod", "crwl", "cws", "cyi", "dca", "dfti", "dgs", "diz", "dne", "doc", "docm", "docx", "docxml",
				"docz", "dox", "dropbox", "dsc", "dvi", "dwd", "dx", "dxb", "dxp", "eio", "eit", "emf", "emlx", "epp",
				"err", "etf", "etx", "euc", "faq", "fbl", "fcf", "fdf", "fdr", "fds", "fdt", "fdx", "fdxt", "fft",
				"fgs", "flr", "fodt", "fountain", "fpt", "frt", "fwdn", "gmd", "gpd", "gpn", "gsd", "gthr", "gv", "hbk",
				"hht", "hs", "hwp", "hz", "idx", "iil", "ipf", "ipsport", "jarvis", "jis", "jnp", "joe", "jp1", "jrtf",
				"jtd", "kes", "klg", "knt", "kon", "kwd", "latex", "lbt", "lis", "lnt", "log", "lp2", "lst", "lst",
				"ltr", "ltx", "lue", "luf", "lwp", "lxfml", "lyt", "lyx", "man", "mbox", "mcw", "me", "mell", "mellel",
				"min", "mnt", "msg", "mw", "mwd", "mwp", "nb", "ndoc", "nfo", "ngloss", "njx", "note", "notes", "now",
				"nwctxt", "nwm", "nwp", "ocr", "odif", "odm", "odo", "odt", "ofl", "opeico", "openbsd", "ort", "ott",
				"p7s", "pages", "pages-tef", "pdpcmd", "pfx", "pjt", "plain", "plantuml", "pmo", "prt", "psw", "psw",
				"pu", "pvj", "pvm", "pwd", "pwdp", "pwdpl", "pwi", "pwr", "qdl", "qpf", "rad", "readme", "rft", "ris",
				"rpt", "rst", "rtd", "rtf", "rtfd", "rtx", "run", "rvf", "rzk", "rzn", "saf", "safetext", "sam", "save",
				"scc", "scm", "scriv", "scrivx", "sct", "scw", "sdm", "sdoc", "sdw", "se", "session", "sgm", "sig",
				"skcard", "sla", "smf", "sms", "ssa", "story", "strings", "stw", "sty", "sxg", "sxw", "tab", "tdf",
				"template", "tex", "text", "textclipping", "thp", "tlb", "tm", "tmd", "tmdx", "tmv", "tmvx", "tpc",
				"trelby", "tvj", "txt", "u3i", "unauth", "unx", "uof", "uot", "upd", "utf8", "utxt", "vct", "vnt", "vw",
				"wbk", "webdoc", "wn", "wp", "wp4", "wp5", "wp6", "wp7", "wpa", "wpd", "wpl", "wps", "wpt", "wpw",
				"wri", "wsd", "wtt", "wtx", "xbdoc", "xbplate", "xdl", "xwp", "xy", "xy3", "xyp", "xyw", "zabw", "zrtf",
				"zw" };
		String[] multimedialneeRozszerzeniePlikow = { "jpeg", "jpg", "jpe", "jif", "jfif", "jfi", "jp2", "jpx", "jmp",
				"mj2", "tif", "tiff", "png", "gif", "bmp", "dib", "svg", "svgz", "eps", "3gp", "aa", "aac", "aax",
				"act", "aiff", "amr", "ape", "au", "awb", "dct", "dss", "dvf", "flac", "gsm", "iklax", "ivs", "m4a",
				"m4b", "m4p", "mmf", "mp3", "mpc", "msv", "nsf", "ogg", "oga", "mogg", "opus", "ra", "rm", "raw", "sln",
				"tta", "vox", "wav", "wma", "wv", "webm", "8svx", "3gp", "asf", "avi", "dvr-ms", "flv", "f4v", "f4p",
				"f4a", "f4b", "iff", "mkv", "mj2", "mov", "qt", "mpg", "mpeg", "m2p", "ps", "ts", "tsv", "tsa", "mp4",
				"m4a", "m4p", "m4b", "m4r", "m4v", "ogg", "ogv", "oga", "ogx", "ogm", "ogm", "spx", "opus", "rm" };
		String[] wykonywalneRozszerzeniePlikow = { "0xe", "73k", "89k", "8ck", "a6p", "a7r", "ac", "acc", "acr", "actc",
				"action", "actm", "ahk", "air", "apk", "app", "applescript", "arscript", "asb", "azw2", "ba_", "bat",
				"beam", "bin", "btm", "caction", "cel", "celx", "cgi", "cmd", "cof", "coffee", "com", "command", "csh",
				"cyw", "dek", "dld", "dmc", "ds", "dxl", "e_e", "ear", "ebm", "ebs", "ebs2", "ecf", "eham", "elf",
				"epk", "epk", "es", "esh", "ex4", "ex5", "ex_", "exe", "exe1", "exopc", "ezs", "ezt", "fas", "fky",
				"fpi", "frs", "fxp", "gadget", "gpe", "gs", "ham", "hms", "hpf", "hta", "icd", "iim", "ipa", "ipf",
				"isu", "ita", "jar", "js", "jse", "jsf", "jsx", "kix", "ksh", "kx", "lo", "ls", "m3g", "mac", "mam",
				"mcr", "mel", "mem", "mio", "mlx", "mm", "mpx", "mrc", "mrp", "ms", "msl", "mxe", "n", "nexe", "ore",
				"osx", "otm", "out", "paf", "pex", "phar", "pif", "plsc", "plx", "prc", "prg", "ps1", "pvd", "pwc",
				"pyc", "pyo", "qit", "qpx", "rbf", "rbx", "rfu", "rgs", "rox", "rpj", "run", "rxe", "s2a", "sbs", "sca",
				"scar", "scb", "scpt", "scptd", "scr", "script", "sct", "seed", "server", "shb", "smm", "spr", "tcp",
				"thm", "tiapp", "tms", "u3p", "udf", "upx", "vbe", "vbs", "vbscript", "vdo", "vexe", "vlx", "vmp",
				"vxp", "wcm", "widget", "wiz", "workflow", "wpk", "wpm", "ws", "wsf", "wsh", "x86", "xap", "xbap",
				"xlm", "xqt", "xqt", "xys", "zl9" };

		for (Plik plik : listaPlikow) {

			// flaga
			boolean isRozpoznaneRozszerzenie = false;

			// dla plików tekstowych
			for (String rozszerzenie : tekstoweRozszerzeniePlikow) {
				if (plik.rozszerzenie.toLowerCase().equals(rozszerzenie)) {
					liczbaPlikowTekstowych++;
					isRozpoznaneRozszerzenie = true;
				}

			}
			// dla plików graficznych
			if (!isRozpoznaneRozszerzenie) {
				for (String rozszerzenie : multimedialneeRozszerzeniePlikow) {
					if (plik.rozszerzenie.toLowerCase().equals(rozszerzenie)) {
						liczbaPlikowMultimedialnych++;
						isRozpoznaneRozszerzenie = true;
					}

				}
			}
			// dla plików dŸwiêkowych
			if (!isRozpoznaneRozszerzenie) {
				for (String rozszerzenie : wykonywalneRozszerzeniePlikow) {
					if (plik.rozszerzenie.toLowerCase().equals(rozszerzenie)) {
						liczbaPlikowWykonywalnych++;
						isRozpoznaneRozszerzenie = true;
					}

				}
			}
			// dla plików filmowych
			if (!isRozpoznaneRozszerzenie) {
				liczbaPlikowPozostalych++;
				isRozpoznaneRozszerzenie = true;
			}

		}

		System.out.println("Liczba plikow tekstowych: " + liczbaPlikowTekstowych + "\nLiczba plikow multimedialnych: "
				+ liczbaPlikowMultimedialnych + "\nLiczba plikow dzwiekowych: " + liczbaPlikowWykonywalnych
				+ "\nLiczba pozostalych plikow: " + liczbaPlikowPozostalych);

		liczbaPlikowDanegoTypu.put("Liczba plikow tekstowych: ", liczbaPlikowTekstowych);
		liczbaPlikowDanegoTypu.put("Liczba plikow multimedialnych: ", liczbaPlikowMultimedialnych);
		liczbaPlikowDanegoTypu.put("Liczba plikow wykonywalnych: ", liczbaPlikowWykonywalnych);
		liczbaPlikowDanegoTypu.put("Liczba pozostalych plikow: ", liczbaPlikowPozostalych);

	}

	private void setJezykProgramowania() {
		String[] cRozszerzeniaPlikow = { "c", "h" };
		String[] cppRozszerzeniaPlikow = { "cpp", "cxx", "hxx" };
		String[] csRozszerzeniaPlikow = { "cs" };
		String[] javaRozszerzeniaPlikow = { "java" };

		int plikiC = 0;
		int plikiJava = 0;
		int plikiCpp = 0;
		int plikiCsharp = 0;
		
		for (Plik plik : listaPlikow) {
			for (String rozszerzenie : cRozszerzeniaPlikow) {
				if(plik.rozszerzenie.equals(rozszerzenie)) {
					plikiC++;
					break;
				}
			}
			for (String rozszerzenie : cppRozszerzeniaPlikow) {
				if(plik.rozszerzenie.equals(rozszerzenie)) {
					plikiCpp++;
					break;
				}
			}
			for (String rozszerzenie : csRozszerzeniaPlikow) {
				if(plik.rozszerzenie.equals(rozszerzenie)) {
					plikiCsharp++;
					break;
				}
			}
			for (String rozszerzenie : javaRozszerzeniaPlikow) {
				if(plik.rozszerzenie.equals(rozszerzenie)) {
					plikiJava++;
					break;
				}
			}
		}
		
		if(plikiC >= plikiJava && plikiC >= plikiCpp && plikiC >= plikiCsharp)
			jezyk = "C";
		else if(plikiJava >= plikiC && plikiJava >= plikiCpp && plikiJava >= plikiCsharp)
			jezyk = "Java";
		else if(plikiCpp >= plikiC && plikiCpp >= plikiJava && plikiCpp >= plikiCsharp)
			jezyk = "C++";
		else if(plikiCsharp >= plikiC && plikiCsharp >= plikiJava && plikiCsharp >= plikiCpp)
			jezyk = "C#";
	}

	private void setLiczbaPlikow() {
		this.liczbaPlikow = listaPlikow.size();
	}
	
	private void setliczbaPlikowDanegoRozszerzenia() {
		//public Map<String, Integer> liczbaPlikowDanegoRozszerzenia = new HashMap<String, Integer>();
		
		for (Plik plik : listaPlikow) {
			String roz = plik.rozszerzenie;
			if (liczbaPlikowDanegoRozszerzenia.containsKey(roz))
				liczbaPlikowDanegoRozszerzenia.put(roz, liczbaPlikowDanegoRozszerzenia.get(roz) + 1);
			else
				liczbaPlikowDanegoRozszerzenia.put(roz, 1);
		}
		
		System.out.println("liczba rozszerzen: " + liczbaPlikowDanegoRozszerzenia.size());
		
		Set<String> keySet = liczbaPlikowDanegoRozszerzenia.keySet();
		System.out.println("Klucze:\n" + keySet);
		Collection<Integer> values = liczbaPlikowDanegoRozszerzenia.values();
		System.out.println("Wartosci:\n" + values);
		
		Set<Entry<String,Integer>> entrySet = liczbaPlikowDanegoRozszerzenia.entrySet();
		for(Entry<String, Integer> entry: entrySet) {
			System.out.println(entry.getKey() + " : " + entry.getValue());
		}
	}
	
	private void setliczbaZmiennychDanegoTypu() {
		//public Map<String, Integer> liczbaZmiennychDanegoTypu = new HashMap<String, Integer>();

		String[] typyZmiennych = { "BYTE", "SHORT", "INT", "LONG", "DOUBLE", "FLOAT", "STRING", "BOOLEAN", "BOOL", "CHAR", "DECIMAL" };
		int[] liczbaZmiennych =   {     0,       0,     0,      0,        0,       0,        0,         0,      0,      0,         0 };
		int i = 0;
		
		for (Plik plik : listaPlikow)
		{
			
			try {
				BufferedReader reader = new BufferedReader(new FileReader(plik.sciezka));
				String line;
				
				while ((line=reader.readLine())!=null){
					i = 0;
					for(String a : typyZmiennych)
			        {
						line=line.toUpperCase();
						a=a.toUpperCase();
			
						Pattern pattern = Pattern.compile("("+a+"\\s\\S+(;|,|=| =))");
			    	    Matcher matcher = pattern.matcher(line);
			    	    while(matcher.find()) 
			    	    	liczbaZmiennych[i]++;
			    	    
			    	    i++;
					}
				   }                           
				reader.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				System.out.println("Cos nie tak przy wyliczaniu liczby zmiennych danego typu");
			}
		}
		liczbaZmiennychDanegoTypu.put("BYTE: ",liczbaZmiennych[0] );
		liczbaZmiennychDanegoTypu.put("SHORT: ",liczbaZmiennych[1] );
		liczbaZmiennychDanegoTypu.put("INT: ",liczbaZmiennych[2] );
		liczbaZmiennychDanegoTypu.put("LONG: ",liczbaZmiennych[3] );
		liczbaZmiennychDanegoTypu.put("DOUBLE: ",liczbaZmiennych[4] );
		liczbaZmiennychDanegoTypu.put("FLOAT: ",liczbaZmiennych[5] );
		liczbaZmiennychDanegoTypu.put("STRING: ",liczbaZmiennych[6] );
		liczbaZmiennychDanegoTypu.put("BOOLEAN: ",liczbaZmiennych[7] );
		liczbaZmiennychDanegoTypu.put("BOOL: ",liczbaZmiennych[8] );
		liczbaZmiennychDanegoTypu.put("CHAR: ",liczbaZmiennych[9] );
		liczbaZmiennychDanegoTypu.put("DECIMAL: ",liczbaZmiennych[10] );
		
		System.out.println("liczba rozwzanych zmiennych: " + liczbaZmiennychDanegoTypu.size());
		
		Set<String> keySet = liczbaZmiennychDanegoTypu.keySet();
		System.out.println("Klucze:\n" + keySet);
		Collection<Integer> values = liczbaZmiennychDanegoTypu.values();
		System.out.println("Wartosci:\n" + values);
		
		Set<Entry<String,Integer>> entrySet = liczbaZmiennychDanegoTypu.entrySet();
		for(Entry<String, Integer> entry: entrySet) {
			System.out.println(entry.getKey() + " : " + entry.getValue());
		}
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
		String[] cRozszerzeniaPlikow = { "c", "h" };
		String[] cppRozszerzeniaPlikow = { "cpp", "cxx", "hxx" };
		String[] csRozszerzeniaPlikow = { "cs" };
		String[] javaRozszerzeniaPlikow = { "java" };

		String[] cStringThreads = { "pthread_t", "pthread_create", "pthread_cancel", "pthread_join" };
		String[] cppStringThreads = { "std::thread", "thread(", "thread (" };
		String[] csStringThreads = { "using System.Threading" };
		String[] javaStringThreads = { "new Thread(", "new Thread (", "extends Thread", "" };

		long multiWatkowosc = 0;

		for (Plik plik : listaPlikow) {

			boolean isMultiWatkowosc = false;
			boolean isRozpoznaneRozszerzenie = false;

			// Dla C:
			for (String rozszerzenie : cRozszerzeniaPlikow) {

				if (plik.rozszerzenie.toLowerCase().equals(rozszerzenie)) {

					isRozpoznaneRozszerzenie = true;
					try {
						BufferedReader reader = new BufferedReader(new FileReader(plik.sciezka));
						String linia = reader.readLine();
						while (linia != null && !isMultiWatkowosc) {
							for (String cString : cStringThreads) {
								if (linia.contains(cString)) {
									// JEST MULTIWATKOWOSC
									multiWatkowosc++;
									isMultiWatkowosc = true;
									break;
								}
							}

							linia = reader.readLine();
						}
						reader.close();
						if (!isMultiWatkowosc) {
							multiWatkowosc--;
						}

					} catch (IOException e) {
						System.out.println("Problem w odczycie pliku do badania wielowatkowosci w jezyku C");
						e.printStackTrace();
					}

				}
			}

			// Dla C++:
			if (!isRozpoznaneRozszerzenie) {
				for (String rozszerzenie : cppRozszerzeniaPlikow) {
					if (plik.rozszerzenie.toLowerCase().equals(rozszerzenie)) {

						isRozpoznaneRozszerzenie = true;
						try {
							BufferedReader reader = new BufferedReader(new FileReader(plik.sciezka));
							String linia = reader.readLine();
							while (linia != null && !isMultiWatkowosc) {
								for (String cppString : cppStringThreads) {
									if (linia.contains(cppString)) {
										// JEST MULTIWATKOWOSC
										multiWatkowosc++;
										isMultiWatkowosc = true;
										break;
									}
								}

								linia = reader.readLine();
							}
							reader.close();
							if (!isMultiWatkowosc) {
								multiWatkowosc--;
							}

						} catch (IOException e) {
							System.out.println("Problem w odczycie pliku do badania wielowatkowosci w jezyku C++");
							e.printStackTrace();
						}

					}
				}
			}

			// Dla C#:
			if (!isRozpoznaneRozszerzenie) {
				for (String rozszerzenie : csRozszerzeniaPlikow) {
					if (plik.rozszerzenie.toLowerCase().equals(rozszerzenie)) {

						isRozpoznaneRozszerzenie = true;
						try {
							BufferedReader reader = new BufferedReader(new FileReader(plik.sciezka));
							String linia = reader.readLine();
							while (linia != null && !isMultiWatkowosc) {
								for (String csString : csStringThreads) {
									if (linia.contains(csString)) {
										// JEST MULTIWATKOWOSC
										multiWatkowosc++;
										isMultiWatkowosc = true;
										break;
									}
								}

								linia = reader.readLine();
							}
							reader.close();
							if (!isMultiWatkowosc) {
								multiWatkowosc--;
							}

						} catch (IOException e) {
							System.out.println("Problem w odczycie pliku do badania wielowatkowsci w jezyku C#");
							e.printStackTrace();
						}

					}
				}
			}

			// Dla Java:
			if (!isRozpoznaneRozszerzenie) {
				for (String rozszerzenie : javaRozszerzeniaPlikow) {
					if (plik.rozszerzenie.toLowerCase().equals(rozszerzenie)) {

						isRozpoznaneRozszerzenie = true;
						try {
							BufferedReader reader = new BufferedReader(new FileReader(plik.sciezka));
							String linia = reader.readLine();
							while (linia != null && !isMultiWatkowosc) {
								for (String javaString : javaStringThreads) {
									if (linia.contains(javaString)) {
										// JEST MULTIWATKOWOSC
										multiWatkowosc++;
										isMultiWatkowosc = true;
										break;
									}
								}

								linia = reader.readLine();
							}
							reader.close();
							if (!isMultiWatkowosc) {
								multiWatkowosc--;
							}

						} catch (IOException e) {
							System.out.println("Problem w odczycie pliku do badania wielowatkowosci w jezyku Java");
							e.printStackTrace();
						}

					}
				}
			}
		}

		if (multiWatkowosc > 0) {
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

		String[] cRozszerzeniaPlikow = { "c", "h" };
		String[] otherRozszerzeniaPlikow = { "cpp", "cxx", "hxx", "cs", "java" };

		String[] classStrings = { "class" };

		for (Plik plik : listaPlikow) {

			boolean isParadygmat = false;
			boolean isRozpoznaneRozszerzenie = false;

			// Dla C:
			for (String rozszerzenie : cRozszerzeniaPlikow) {

				if (plik.rozszerzenie.toLowerCase().equals(rozszerzenie)) {
					// Rozpoznano jêzyk C, który nie jest obiektowy
					isRozpoznaneRozszerzenie = true;
					liczbaParadygmat--;
				}
			}

			if (!isRozpoznaneRozszerzenie) {
				// Dla C++, C#, Java:
				for (String rozszerzenie : otherRozszerzeniaPlikow) {

					if (plik.rozszerzenie.toLowerCase().equals(rozszerzenie)) {

						isRozpoznaneRozszerzenie = true;
						try {
							BufferedReader reader = new BufferedReader(new FileReader(plik.sciezka));
							String linia = reader.readLine();
							while (linia != null && !isParadygmat) {
								for (String classString : classStrings) {
									if (linia.contains(classString)) {
										// Jest obiektowosc
										liczbaParadygmat++;
										isParadygmat = true;
										break;
									}
								}

								linia = reader.readLine();
							}
							reader.close();
							if (!isParadygmat) {
								liczbaParadygmat--;
							}

						} catch (IOException e) {
							System.out.println("Problem w odczycie pliku do badania paradygmatu w jezyku C");
							e.printStackTrace();
						}

					}
				}
			}
		}

		System.out.println("Paradygmat: " + liczbaParadygmat);
		if (liczbaParadygmat > 0) {
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
			liczbaAtrybutow = liczbaAtrybutow + plik.liczbaAtrybutow;
		}
	}

	public int getLiczbaAtrybutow() {
		return liczbaAtrybutow;
	}

	public void setLiczbaZnakow() {
		liczbaZnakow = 0;

		for (Plik plik : listaPlikow) {
			liczbaZnakow = liczbaZnakow + plik.liczbaZnakow;
		}
	}

	public int getLiczbaZnakow() {
		return liczbaZnakow;
	}

	public void setJezykInterfejsu() {
		jezykInterfejsu = "";
		HashMap<String, Integer> map = new HashMap<String, Integer>();

		// Rozpoznanie jezyka w kazdym pliku i dodanie go do mapy wraz z krotnoscia
		for (Plik plik : listaPlikow) {
			String lng = plik.jezykInterfejsu;
			if (map.containsKey(lng))
				map.put(lng, map.get(lng) + 1);
			else
				map.put(lng, 1);
		}

		// Wyszukanie najczestszego jezyka
		int max = Collections.max(map.values());
		for (Entry<String, Integer> entry : map.entrySet()) {
			if (entry.getValue() == max) {
				jezykInterfejsu = entry.getKey();
			}
		}

	}

	public void setLiczbaMetod() {

		Pattern pattern1 = Pattern.compile(
				"^\\s?\\{? *\\)[a-zA-Z0-9<>\\[\\]._?, \\n\\*]*\\( *([a-zA-Z0-9<>._?,\\*]+){1} +([a-zA-Z0-9_\\*]+){1} *(CITATS|LANIF|TCARTSBA|DEZINORHCNYS|CILBUP|ETAVIRP|DETCETORP){0,1} *(CITATS|LANIF|TCARTSBA|DEZINORHCNYS|CILBUP|ETAVIRP|DETCETORP){0,1}\\s*$");
		for (Plik plik : listaPlikow) {
			try {
				if (plik.rozszerzenie.equals("java") || plik.rozszerzenie.equals("c") || plik.rozszerzenie.equals("cpp")
						|| plik.rozszerzenie.equals("cs")) {

					BufferedReader reader = new BufferedReader(new FileReader(plik.sciezka));
					String line;

					while ((line = reader.readLine()) != null) {

						line = new StringBuilder().append(line.toUpperCase()).reverse().toString();

						Matcher matcher1 = pattern1.matcher(line);

						if (!line.contains("NRUTER") && !line.contains("GNISU") && matcher1.find()) {
							liczbaMetod++;
						}
					}
					reader.close();
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				System.out.println("Cos nie tak przy wyliczaniu liczby metod");
			}
		}
	}

	public String getJezykInterfejsu() {
		return jezykInterfejsu;
	}

}