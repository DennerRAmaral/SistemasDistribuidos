package cliente;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Clientemain {
    public static void main(String[] args) throws IOException {
        BufferedReader consoleReader = new BufferedReader(new InputStreamReader(System.in));
        UserInteraction userInteraction = new UserInteraction(consoleReader);

        System.out.println("Insira o IP do servidor desejado:");
        String ip = consoleReader.readLine();
        System.out.println("Insira a porta de acesso do servidor:");
        int port = Integer.parseInt(consoleReader.readLine());

        SocketManager socketManager = new SocketManager(ip, port);
        userInteraction.menuacao(socketManager);
        socketManager.close();
    }
}
