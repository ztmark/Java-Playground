import java.io.IOException;

/**
 * Author: Mark
 * Date  : 15/11/24.
 */
public class SimpleHttpServerTest {

    public static void main(String[] args) {
        SimpleHttpServer.setPort(8680);
        SimpleHttpServer.setBasePath("/Users/Mark/");
        try {
            SimpleHttpServer.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
