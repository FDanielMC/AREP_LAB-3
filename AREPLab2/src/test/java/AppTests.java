import com.escuelaing.edu.co.areplab3.MovieClient;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
/**
 *
 * @author moren
 */
public class AppTests extends TestCase {

    public AppTests(String name) {
        super(name);
    }
    
    /**
     * Test que verifica que el tamaño de la imágen sea el correcto
     */
    public void testImage(){
        byte[] lengthImageOne = MovieClient.getImage("/img/img_1.png");
        byte[] lengthImageTwo = MovieClient.getImage("/img/R.jpg");
        assertEquals(13626, lengthImageOne.length);
        assertEquals(176795, lengthImageTwo.length);
    }
    
    /**
     * Test que verifica que según el archivo el Content-type sea correcto
     */
    public void testContentType() {
        assertEquals("image/png", MovieClient.getFormatFile("cinema.png"));
        assertEquals("text/html", MovieClient.getFormatFile("client.html"));
        assertEquals("text/css", MovieClient.getFormatFile("client.css"));
        assertEquals("image/jpeg", MovieClient.getFormatFile("cinema.jpg"));
    }
    
}
