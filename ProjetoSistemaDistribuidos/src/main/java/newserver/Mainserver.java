package newserver;


import java.io.*;
import java.net.*;

import org.apache.commons.codec.digest.DigestUtils;

public class Mainserver extends Thread {
    private static boolean serverContinue = true;
    private Socket clientSocket;
    private static Usuarios usuariosCadastrados[] = new Usuarios[20];

    public Mainserver(Socket clientSocket) {
        this.clientSocket = clientSocket;
        start();
    }

    public static void main(String[] args) throws IOException {
        Usuarios admin = new Usuarios(0, "admin", "admin01", "admin01@email.com", DigestUtils.md5Hex("admteste"));
        usuariosCadastrados[0] = admin;

        int port = getPortFromUserInput();

        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Socket de conexão criado");
            while (serverContinue) {
                serverSocket.setSoTimeout(10000);
                System.out.println("Aguardando conexão...");
                try {
                    new Mainserver(serverSocket.accept());
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

        try (PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
             BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream())) ) {

            String inputLine;

            while ((inputLine = in.readLine()) != null) {
                System.out.println("Server: " + inputLine);
                
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