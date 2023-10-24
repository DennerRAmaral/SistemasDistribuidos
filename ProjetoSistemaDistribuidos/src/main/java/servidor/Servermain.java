package servidor;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.mindrot.jbcrypt.BCrypt;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;



public class Servermain extends Thread {

    private static boolean serverContinue = true;
    private Socket clientSocket;
    private static Usuarios[] usuariosCadastrados = new Usuarios[20];

    public Servermain(Socket clientSocket) {
        this.clientSocket = clientSocket;
        start();
    }

    public static void main(String[] args) throws IOException {
        Usuarios admin = new Usuarios(0, "admin", "admin01", "admin01@email.com", BCrypt.hashpw("adminteste", BCrypt.gensalt()));
        usuariosCadastrados[0] = admin;
        int port = getPortFromUserInput();

        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Socket de conexão criado");
            while (serverContinue) {
                serverSocket.setSoTimeout(10000);
                System.out.println("Aguardando conexão...");
                try {
                    new Servermain(serverSocket.accept());
                } catch (SocketTimeoutException ste) {
                    System.out.println("Ocorreu Timeout");
                }
            }
        } catch (IOException e) {
            System.err.println("Accept failed.");
            System.exit(1);
        }
    }

    public void run() {
        System.out.println("Nova comunicação iniciada");
        ObjectMapper objectmapper = new ObjectMapper();
        try (PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
                BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()))) {

            String inputLine;
            
            while ((inputLine = in.readLine()) != null) {
                System.out.println("Server: " + inputLine);
                JsonNode jnode = objectmapper.readTree(inputLine);
                String action;
                if (jnode.has("action")) {
                    Controladordeacao controlaction;
                    controlaction = new Controladordeacao(jnode,usuariosCadastrados);
                    out.println(controlaction.seletor());
                    out.flush();
                }else{
                    System.out.println("Action indefinida");
                }
                
                if (inputLine.equals("End Server.")) {
                    serverContinue = false;
                }
            }
        } catch (IOException e) {
            System.err.println("Problem with Communication Server");
            System.exit(1);
        }
    }

    private static int getPortFromUserInput() {
        int port = 0;
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
            System.out.println("Insira a Porta de acesso do server:");
            port = Integer.parseInt(reader.readLine());
        } catch (IOException e) {
            System.err.println("Could not read port input.");
            System.exit(1);
        }
        return port;
    }
}
