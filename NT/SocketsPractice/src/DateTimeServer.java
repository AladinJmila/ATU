import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;

public class DateTimeServer {
    public final static int PORT = 113;

    public static void main(String[] args) {
        try (ServerSocket server = new ServerSocket(PORT)) {
            while (true) {
                System.out.println("Listening for connections on port " + PORT + " ...");
                try (Socket connection = server.accept()) {
                    Writer output = new OutputStreamWriter(connection.getOutputStream());
                    Date now = new Date();
                    output.write("AJ " + now + "\r\n");
                    output.flush();

                } catch (IOException e) {
                    // TODO: handle exception
                }
            }

        } catch (IOException e) {
            System.err.println(e);
        }
    }
}
