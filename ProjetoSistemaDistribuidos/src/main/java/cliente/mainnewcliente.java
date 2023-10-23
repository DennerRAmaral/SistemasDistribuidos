package cliente;

import java.io.*;
import org.mindrot.jbcrypt.BCrypt;

public class mainnewcliente {
    public static void main(String[] args) throws IOException {
        BufferedReader consoleReader = new BufferedReader(new InputStreamReader(System.in));
        UserInteraction userInteraction = new UserInteraction(consoleReader);

        System.out.println("Insira o IP do servidor desejado:");
        String ip = consoleReader.readLine();
        System.out.println("Insira a porta de acesso do servidor:");
        int port = Integer.parseInt(consoleReader.readLine());

        SocketManager socketManager = new SocketManager(ip, port);

        int action;
        while ((action = userInteraction.getActionChoice()) != 0) {
            switch (action) {
                case 1:
                    String email = userInteraction.getEmail();
                    String password = userInteraction.getPassword();
                    String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());
                    String json = "{\"action\": \"login\",\"data\": {\"email\": \"" + email + "\",\"password\": \"" + hashedPassword + "\"}}";
                    System.out.println("Enviando JSON:\n" + json);
                    socketManager.sendMessage(json);
                    break;
                default:
                    System.out.println("Comando inválido. Insira novamente.");
            }
        }

        socketManager.close();
    }
}
