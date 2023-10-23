package mainserver;
import java.net.*;
import java.io.*;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.codec.digest.DigestUtils;

public class mainserver extends Thread
{
    protected static boolean serverContinue = true;
    protected Socket clientSocket;

    public static void main(String[] args) throws IOException {
        Usuarios usuarioscadastrados[] = new Usuarios[20];
        usuarioscadastrados[0] = new Usuarios(0,"admin","admin01","admin01@email.com", DigestUtils.md5Hex("admteste"));
        ServerSocket serverSocket = null;
        BufferedReader leitorbuffer = new BufferedReader(new InputStreamReader(System.in));
        int port = 0;
        try {
            System.out.println(" Insira a Porta de acesso do server");
            port = Integer.parseInt(leitorbuffer.readLine());
            serverSocket = new ServerSocket(port);
            System.out.println("Socket de conecao criado");
            try {
                while (serverContinue) {
                    serverSocket.setSoTimeout(10000);
                    System.out.println("Aguardando conexao...");
                    try {
                        new mainserver(serverSocket.accept());
                    } catch (SocketTimeoutException ste) {
                        System.out.println("Ocorreu Timeout");
                    }
                }
            } catch (IOException e) {
                System.err.println("Accept failed.");
                System.exit(1);
            }
        } catch (IOException e) {
            System.err.println("Could not listen on port: " + port);
            System.exit(1);
        } finally {
            try {
                System.out.println("Closing Server Connection Socket");
                serverSocket.close();
            } catch (IOException e) {
                System.err.println("Could not close port:"+port);
                System.exit(1);
            }
        }
    }

    private mainserver (Socket clientSoc)
    {
        clientSocket = clientSoc;
        start();
    }

    public void run()
    {
        System.out.println ("Nova cominicacao iniciada");

        try {
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(),
                    true);
            BufferedReader in = new BufferedReader(
                    new InputStreamReader( clientSocket.getInputStream()));

            String inputLine;

            while ((inputLine = in.readLine()) != null)
            {
                System.out.println ("Server: " + inputLine);






                if (inputLine.equals("End Server."))
                    serverContinue = false;
            }

            out.close();
            in.close();
            clientSocket.close();
        }
        catch (IOException e)
        {
            System.err.println("Problem with Communication Server");
            System.exit(1);
        }
    }
}