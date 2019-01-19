package box;

import org.apache.tika.Tika;
import org.apache.tika.mime.MimeTypes;

/**
 * Singleton klasy Apache Tika
 * @author Adrian Plichta
 *
 */
public class TikaDetector {
	 
    private TikaDetector() {
    }
 
    /**
     * Metoda zwracająca instancję Apache Tika
     * @return obiekt Tika
     */
    public static Tika getInstance() {
        return TikaDetectorHolder.INSTANCE;
    }
 
    /**
     * Wewnętrzna klasa realizująca zadanie singletonu
     * @author Adrian Plichta
     *
     */
    private static class TikaDetectorHolder {
        private static final Tika INSTANCE = new Tika(new MimeTypes());
    }
}
