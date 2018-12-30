package box;

import java.io.BufferedReader;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.velocity.shaded.commons.io.FilenameUtils;

public class Plik extends Katalog{

	public long rozmiar;
	public long liczbaLiniiKodu;
	public String hash;
	public String rozszerzenie;
	public boolean wczytywaniePlikow = false;
	public ArrayList<String> zbiorBibliotek = new ArrayList<String>();

	
	public Plik(String nazwa, String sciezka) {
		super(nazwa, sciezka);
		this.setRozmiar();
		this.setLiczbaLiniiKodu();
		this.setHash();
		this.setRozszerzenie();
		this.setWczytywaniePlikow();
		this.setZbiorBibliotek();
		
		System.out.println("Hash: " + hash + " nazwa pliku: " + nazwa);
	}

	private void setRozmiar() {
		this.rozmiar = new File(this.sciezka).length();
	}
	
	private void setLiczbaLiniiKodu() {
	
		try {
			BufferedReader reader = new BufferedReader(new FileReader(sciezka));
			while (reader.readLine() != null) {
				liczbaLiniiKodu++;
			}
			reader.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("Cos nie tak przy liczeniu linii kodu");
		}
		
	}
	
	private void setHash() {
		try {
			this.hash = DigestUtils.sha256Hex(new FileInputStream(this.sciezka));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("Cos nie tak przy wyliczaniu Hash");
		}
	}
	
	private void setRozszerzenie() {
		System.out.println(nazwa);
		rozszerzenie = FilenameUtils.getExtension(nazwa);
	}
	
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
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("Cos nie tak przy sprawdzaniu wczytywania plikow");
		}
		
	}
	
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
			    		  Pattern pattern = Pattern.compile("(import\\s(.*?\\D+)\\;)");
			    	      Matcher matcher = pattern.matcher(line);
			    	      while(matcher.find())
			    	        {
			    	    	  	zbiorBibliotek.add(matcher.group().split(" ")[1].substring(0,matcher.group().split(" ")[1].length()-1));
			    	    	  	System.out.println(matcher.group());
			    	        }
			    	  }
			    	  else if (i==1) {
			    		  Pattern pattern = Pattern.compile("(#include\\s\\<(.*?\\D+)\\>)");
			    	      Matcher matcher = pattern.matcher(line);
			    	      while(matcher.find())
			    	        {
			    	    	  	zbiorBibliotek.add(matcher.group().split(" ")[1].substring(1,matcher.group().split(" ")[1].length()-1));
			    	    	  	System.out.println(matcher.group());
			    	        }
			    	  }
			    	  else {
			    		  Pattern pattern = Pattern.compile("(using\\s(.*?\\D+)\\;)");
			    	      Matcher matcher = pattern.matcher(line);
			    	      while(matcher.find())
			    	        {
			    	    	  	zbiorBibliotek.add(matcher.group().split(" ")[1].substring(0,matcher.group().split(" ")[1].length()-1));
			    	    	  	System.out.println(matcher.group());
			    	        }
			    	  }
				}
			   }                           
			reader.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("Cos nie tak przy sprawdzaniu zbioru bibliotek");
		}
	}
}
