package cliente;

import java.io.BufferedReader;
import java.io.IOException;

public class UserInteraction {
    private BufferedReader reader;

    public UserInteraction(BufferedReader reader) {
        this.reader = reader;
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
}