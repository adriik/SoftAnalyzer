package box;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.tika.Tika;
import org.apache.tika.langdetect.OptimaizeLangDetector;
import org.apache.tika.language.detect.LanguageDetector;
import org.apache.tika.language.detect.LanguageResult;
import org.apache.velocity.shaded.commons.io.FilenameUtils;

/**
 * Klasa Plik s³u¿y do przechowywania i uzyskiwania informacji o cechach danego pliku
 */

public class Plik extends Katalog{

	public long rozmiar;
	public long liczbaLiniiKodu = 0;
	public String hash;
	public String rozszerzenie;
	public boolean wczytywaniePlikow = false;
	public ArrayList<String> zbiorBibliotek = new ArrayList<String>();
	public int liczbaAtrybutow = 0;
	public int liczbaZnakow = 0;
	public String jezykInterfejsu = "";
	public int liczbaDanychWejsciowych = 0;
	private Tika tika;
	public String typeMIME;
	
	public Plik(String nazwa, String sciezka) {
		super(nazwa, sciezka);
		this.setMIMEType();		
		this.setRozmiar();		
		this.setHash();
		this.setRozszerzenie();
		
		if(!typeMIME.equals("application/octet-stream")) {
			this.setLiczbaLiniiKodu();
			this.setWczytywaniePlikow();
			this.setZbiorBibliotek();
			this.setLiczbaAtrybutow();
			this.setLiczbaZnakow();
			this.setJezykInterfejsu();
			this.setLiczbaDanychWejsciowych();
		}
	}
	/*
	 * Metoda setMIMEType() s³u¿y do ustawienia identyfikatora MIME dla danego pliku
	 */
	private void setMIMEType() {
		tika = TikaDetector.getInstance();
		try {
			typeMIME = tika.detect(new File(this.sciezka));
			
		} catch (IOException e) {
			System.out.println("BÅ‚Ä…d przy rozpoznawaniu typu pliku - binarki");
		}
	}

	/**
	 * Metoda setRozmiar() s³u¿y do ustawienia informacji o rozmiarze danego pliku
	 */
	private void setRozmiar() {
		this.rozmiar = new File(this.sciezka).length();
	}
	
	/**
	 * Metoda setLiczbaLiniiKodu() s³uzy do ustawienia informacji o liczbie linii kodu wystêpuj¹cych w danym pliku
	 */
	private void setLiczbaLiniiKodu() {
	
		try {
			BufferedReader reader = new BufferedReader(new FileReader(sciezka));
			while (reader.readLine() != null) {
				liczbaLiniiKodu++;
			}
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Cos nie tak przy liczeniu linii kodu");
		}
		
	}
	/**
	 * Metoda setLiczbaZnakow() s³uzy do ustawienia informacji o liczbie znakow wystêpujacych w danym pliku
	 */
	private void setLiczbaZnakow() {
		
		try {
			BufferedReader reader = new BufferedReader(new FileReader(sciezka));
			String line;
			liczbaZnakow = 0;
			
			while ((line = reader.readLine()) != null) {
				liczbaZnakow += line.toString().length();
			}
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Cos nie tak przy liczeniu liczby znakow");
		}
		
	}
	
	/**
	 * Metoda setJEzykInterfejsu() sluzy do uzyskania informacji o prefiks jezyka interfejsu (wedlug standardu ISO 639-1) danego pliku
	 */
	
	private void setJezykInterfejsu() {
		try {
			
			BufferedReader reader = new BufferedReader(new FileReader(sciezka));
			String line;
			HashMap<String,Integer> map = new HashMap<String,Integer>();
			LanguageDetector langDetector = new OptimaizeLangDetector().loadModels();
	        			
			
			//Rozpoznanie jezyka w kazdym (" ... ") i dodanie go do mapy wraz z krotnoscia
			while ((line = reader.readLine()) != null) {
			     Matcher m = Pattern.compile("\\(\"([^\"]*)\"\\)").matcher(line);
			     while(m.find()) {
			    	 LanguageResult lang = langDetector.detect(m.group(1).toString());
			    	 //System.out.println(m.group(1).toString() + " " + lang.getLanguage());
				     String lng = lang.getLanguage();
			    	 if (map.containsKey(lng))
			    		 map.put(lng, map.get(lng) + 1);
			    	 else
			    		 map.put(lng, 1); 
			     }
			}
			reader.close();
			
			//Wyszukanie najczestszego jezyka
			if (!map.isEmpty()) {
				int max = Collections.max(map.values());			
				for (Entry<String, Integer> entry : map.entrySet()) {
				    if (entry.getValue()==max) {
				        jezykInterfejsu = entry.getKey();
				    }
				}
			}
			else
			{
				jezykInterfejsu = "brak";
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Cos nie tak przy wyznaczaniu jezyka interfejsu");
		}
	}
	
	/**
	 * Metoda setHash() s³uzy do ustawienia informacji o hashu danego pliku
	 */
	
	private void setHash() {
		try {
			this.hash = DigestUtils.sha256Hex(new FileInputStream(this.sciezka));
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Cos nie tak przy wyliczaniu Hash");
		}
	}
	/**
	 * Metoda setRozszerzenie() s³uzy do ustawienia informacji o rozszerzeniu danego pliku
	 */
	private void setRozszerzenie() {
		rozszerzenie = FilenameUtils.getExtension(nazwa);
	}
	/**
	 * Metoda setWczytywaniePlikow() sluzy do ustawienia informacji o tym czy do danego pliku jest wczytywany inny plik
	 */
	private void setWczytywaniePlikow() {
		
		try {
			BufferedReader reader = new BufferedReader(new FileReader(sciezka));
			String line;
			
			ArrayList<String> keywords_infile = new ArrayList<String>();
			keywords_infile.add("File ");
			keywords_infile.add("fopen");
			keywords_infile.add("ifstream");
			keywords_infile.add("fstream");
			keywords_infile.add("FileStream");
			keywords_infile.add("StreamReader");
			keywords_infile.add("BinaryReader");
			keywords_infile.add("InputStream");
			keywords_infile.add("FileInputStream");
			keywords_infile.add("FileReader");
			keywords_infile.add("getFile");

			while ((line=reader.readLine())!=null){
				for(String a : keywords_infile)
		        {
			      if(line.contains(a))
			    	  wczytywaniePlikow = true;
				}
			   }                           
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Cos nie tak przy sprawdzaniu wczytywania plikow");
		}
		
	}
	/**
	 * Metoda setLiczbaDanychWejsciowych sluzy do ustawienia informacji o liczbie danych wejsciowych danego pliku
	 */
	private void setLiczbaDanychWejsciowych() {

		try {
			BufferedReader reader = new BufferedReader(new FileReader(sciezka));
			String line;
			
			ArrayList<String> keywords_infile = new ArrayList<String>();
			keywords_infile.add("File ");
			keywords_infile.add("fopen");
			keywords_infile.add("ifstream");
			keywords_infile.add("fstream");
			keywords_infile.add("FileStream");
			keywords_infile.add("InputStream");
			keywords_infile.add("FileInputStream");
			keywords_infile.add("read");
			keywords_infile.add("get");
			keywords_infile.add("argv");
			

			while ((line=reader.readLine())!=null){
				for(String a : keywords_infile)
		        {
					line = line.toUpperCase();
					a = a.toUpperCase();
			      if(line.contains(a))
			    	  this.liczbaDanychWejsciowych++;
				}
			   }                           
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Cos nie tak przy sprawdzaniu wczytywania danych wejsciowych");
		}
		
	}
	
	/**
	 * Metoda setZbiorBibliotek() sluzy do uzyskanie informacji o zbiorze bibliotek wykorzystywanych w danym pliku
	 */
	private void setZbiorBibliotek() {
		
		try {
			BufferedReader reader = new BufferedReader(new FileReader(sciezka));
			String line;
			
			ArrayList<String> keywords_lib = new ArrayList<String>();
			keywords_lib.add("import");
			keywords_lib.add("#include");
			keywords_lib.add("using");


			while ((line=reader.readLine())!=null){
				for (int i = 0; i<keywords_lib.size();i++)
				{
			      if(line.contains(keywords_lib.get(i)))
			    	  if (i==0) {
			    		  Pattern pattern = Pattern.compile("(import\\s(.*?\\.*)\\;)");
			    	      Matcher matcher = pattern.matcher(line);
			    	      while(matcher.find())
			    	        {
			    	    	  	zbiorBibliotek.add(matcher.group().split(" ")[1].substring(0,matcher.group().split(" ")[1].length()-1));
			    	        }
			    	  }
			    	  else if (i==1) {
			    		  Pattern pattern = Pattern.compile("(#include\\s\\<(.*?\\.*)\\>)");
			    	      Matcher matcher = pattern.matcher(line);
			    	      while(matcher.find())
			    	        {
			    	    	  	zbiorBibliotek.add(matcher.group().split(" ")[1].substring(1,matcher.group().split(" ")[1].length()-1));
			    	        }
			    	  }
			    	  else {
			    		  Pattern pattern = Pattern.compile("(using\\s(.*?\\.*)\\;)");
			    	      Matcher matcher = pattern.matcher(line);
			    	      while(matcher.find())
			    	        {
			    	    	  	zbiorBibliotek.add(matcher.group().split(" ")[1].substring(0,matcher.group().split(" ")[1].length()-1));
			    	        }
			    	  }
				}
			   }                           
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Cos nie tak przy sprawdzaniu zbioru bibliotek");
		}
	}
	/**
	 * Metoda setLiczbaAtrybutow() sluzy do ustawienia liczby atrybutow wystepujacych w danym pliku
	 */
	
	private void setLiczbaAtrybutow() {
		
		try {
			BufferedReader reader = new BufferedReader(new FileReader(sciezka));
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

			while ((line=reader.readLine())!=null){
				for(String a : keywords_infile)
		        {
					line=line.toUpperCase();
					a=a.toUpperCase();
		
					Pattern pattern = Pattern.compile("("+a+"\\s\\S+(;|,|=| =))");
		    	    Matcher matcher = pattern.matcher(line);
		    	    while(matcher.find()) 
		    	    	liczbaAtrybutow++;
				}
			   }                           
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Cos nie tak przy wyliczaniu liczby atrybutow");
		}
		
	}
}
