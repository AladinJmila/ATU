import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.charset.StandardCharsets;

public class DateTimeClient {
    public static void main(String[] args) {
        String host = "time.nist.gov";

        try (Socket socket = new Socket("localhost", 113)) {
            socket.setSoTimeout(15000); // Reduced timeout to 15 seconds
            InputStream input = socket.getInputStream();
            byte[] inputBytes = input.readAllBytes();
            String time = new String(inputBytes, StandardCharsets.US_ASCII);
            System.out.println(time);
        } catch (UnknownHostException e) {
            System.out.println("Can't connect to host");
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
