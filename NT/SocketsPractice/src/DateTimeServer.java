import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;

public class DateTimeServer {
    public final static int PORT = 13;

    public static void main(String[] args) {
        try (ServerSocket server = new ServerSocket(PORT)) {
            while (true) {
                System.out.println("Listening for connections on port " + PORT + " ...");
                try (Socket connection = server.accept()) {
                    System.out.println("Client connected from host " + connection.getInetAddress() + ", port "
                            + connection.getPort());
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
