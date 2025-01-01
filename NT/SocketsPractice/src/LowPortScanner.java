import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class LowPortScanner {
    public static final int MAX_PORT = 1024;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the hostname: ");
        String input = scanner.nextLine();

        String host = (input.length() > 0) ? input : "localhost";
        System.out.println("Scanning ports...");

        for (int port = 1; port < MAX_PORT; port++) {
            try (Socket socket = new Socket()) {
                socket.connect(new InetSocketAddress(host, port), 10);
                System.out.println("There is a server of port " + port + " of " + host);
            } catch (UnknownHostException e) {
                System.err.println(e);
                break;
            } catch (IOException e) {

            }
        }
        System.out.println("Finished Scan");
    }
}
