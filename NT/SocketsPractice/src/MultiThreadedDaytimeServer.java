import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;
import java.io.OutputStreamWriter;
import java.io.Writer;

public class MultiThreadedDaytimeServer {
    public final static int PORT = 13;

    public static void main(String[] args) {
        try (ServerSocket server = new ServerSocket(PORT)) {
            System.out.println("Listening for connections on port " + PORT + " ...");
            while (true) {
                Socket connection = server.accept();
                System.out.println("Client connected from " + connection.getInetAddress() + ":" + connection.getPort());
                Thread task = new DaytimeThread(connection);
                task.start();
            }
        } catch (Exception e) {
            // TODO: handle exception
        }
    }

    private static class DaytimeThread extends Thread {
        private Socket connection;

        DaytimeThread(Socket connection) {
            this.connection = connection;
        }

        @Override
        public void run() {
            try {
                Writer out = new OutputStreamWriter(connection.getOutputStream());
                Date now = new Date();
                out.write(now.toString() + "\r\n");
                out.flush();

            } catch (IOException e) {
                System.err.println(e);
            } finally {
                try {
                    connection.close();
                } catch (IOException e) {
                    // TODO: handle exception
                }
            }
        }
    }
}
