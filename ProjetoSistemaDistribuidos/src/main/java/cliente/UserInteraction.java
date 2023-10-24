package cliente;

import org.mindrot.jbcrypt.BCrypt;

import java.io.BufferedReader;
import java.io.IOException;

public class UserInteraction {
    private BufferedReader reader;
    private int action;

    public UserInteraction(BufferedReader reader) {
        this.reader = reader;
    }

    public void menuacao(SocketManager socketManager) throws RuntimeException {
        action = -1;
        while (action != 0) {
            try {
                action = getActionChoice();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            switch (action) {
                case 1:
                    try {
                        loginrun(socketManager);
                    } catch (IOException e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                case 0:
                    System.out.println("encerrando");
                    break;
                default:
                    System.out.println("Comando inválido. Insira novamente.");
            }
        }
    }

    public int getActionChoice() throws IOException {
        System.out.println("Selecione sua ação:\n 1- Login\n 2- Cadastro\n 8- Logout\n 0- Fechar");
        return Integer.parseInt(reader.readLine());
    }

    public String getEmail() throws IOException {
        System.out.println("Insira seu email:");
        return reader.readLine();
    }

    public String getPassword() throws IOException {
        System.out.println("Insira sua senha:");
        return reader.readLine();
    }

    public void loginrun(SocketManager socketManager) throws IOException {
        String email = getEmail();
        String password = getPassword();
        String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());
        String json = "{\"action\": \"login\",\"data\": {\"email\": \"" + email + "\",\"password\": \""
                + hashedPassword + "\"}}";
        System.out.println("Enviando JSON:\n" + json);
        socketManager.sendMessage(json);
    }
}

