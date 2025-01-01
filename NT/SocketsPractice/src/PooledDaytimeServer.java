import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class PooledDaytimeServer {
    public static final int PORT = 13;

    public static void main(String[] args) {
        try (ExecutorService pool = Executors.newFixedThreadPool(50)) {
            try (ServerSocket server = new ServerSocket(PORT)) {
                System.out.println("Listening for connections on port " + PORT + " ...");
                while (true) {
                    Socket connection = server.accept();
                    System.out.println(
                            "Client connected from " + connection.getInetAddress() + ":" + connection.getPort());
                    Runnable task = new DaytimeTask(connection);
                    pool.submit(task);
                }
            } catch (Exception e) {
                // TODO: handle exception
            }

        } catch (Exception e) {
            // TODO: handle exception
        }
    }

    private static class DaytimeTask implements Runnable {
        private Socket connection;

        DaytimeTask(Socket connection) {
            this.connection = connection;
        }

        @Override
        public void run() {
            try {
                Thread.sleep(500);
                System.out.println("Thread: " + Thread.currentThread().getName());
                Writer out = new OutputStreamWriter(connection.getOutputStream());
                Date now = new Date();
                out.write(now.toString() + "\r\n");
                out.flush();
            } catch (Exception e) {
                System.err.println(e);

            }
        }

    }
}
