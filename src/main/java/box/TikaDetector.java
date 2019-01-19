package box;

import org.apache.tika.Tika;
import org.apache.tika.mime.MimeTypes;

public class TikaDetector {
	 
    private TikaDetector() {
    }
 
    public static Tika getInstance() {
        return TikaDetectorHolder.INSTANCE;
    }
 
    private static class TikaDetectorHolder {
        private static final Tika INSTANCE = new Tika(new MimeTypes());
    }
}
