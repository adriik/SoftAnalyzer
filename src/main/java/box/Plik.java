package box;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;

import org.apache.commons.codec.digest.DigestUtils;

public class Plik extends Katalog{

	public long rozmiar;
	public long liczbaLiniiKodu;
	public String hash;
	
	public Plik(String nazwa, String sciezka) {
		super(nazwa, sciezka);
		this.setRozmiar();
		this.setLiczbaLiniiKodu();
		this.setHash();
		
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
}
