package box;

/**
 * Klasa katalog zawiera informacje o nazwie i sciezce danego katalogu
 * @author Adrian Plichta
 */

public class Katalog {

	public String nazwa;
	public String sciezka;
	
	
	/**
	 * Konstruktor klasy Katalog do zainicjowania obiektu
	 * @param nazwa - nazwa danego katalogu
	 * @param sciezka - sciezka do danego katalogu
	 */
	public Katalog(String nazwa, String sciezka) {
		this.nazwa = nazwa;
		this.sciezka = sciezka;
	}
}
