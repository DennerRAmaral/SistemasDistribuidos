package Cliente;
//Ad Astra
import java.io.*;
import java.net.*;
import java.util.*;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class EchoClient {
    public static void main(String[] args) throws IOException {
        Scanner scan = new Scanner(System.in);
        System.out.println("insira o ip do server:");
        String serverHostname = scan.nextLine();
        ObjectMapper mapper = new ObjectMapper();
        if (args.length > 0)
            serverHostname = args[0];
        System.out.println("Attemping to connect to host " + serverHostname);

        Socket echoSocket = null;
        PrintWriter out = null;
        BufferedReader in = null;

        try {
            echoSocket = new Socket(serverHostname, 22222);
            out = new PrintWriter(echoSocket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(
                    echoSocket.getInputStream()));
        } catch (UnknownHostException e) {
            System.err.println("host desconhecido: " + serverHostname);
            System.exit(1);
        } catch (IOException e) {
            System.err.println("Couldn't get I/O for the connection to: " + serverHostname);
            System.exit(1);
        }


        BufferedReader stdIn = new BufferedReader(
                new InputStreamReader(System.in));
        /*
        String userInput;

        System.out.print ("input: ");
        while ((userInput = stdIn.readLine()) != null) {
            out.println(userInput);
            System.out.println("echo: " + in.readLine());
            System.out.print ("input: ");


        }
*/
        int inputoption = -1;

        while (inputoption != 0) {
            System.out.println("Insira funcao desejada:\n 1 - login");
            inputoption = scan.nextInt();
            switch (inputoption) {
                case 1:
                    login(scan, mapper, in, out);
                    break;
                case 0:
                    System.out.println("Encerrando...");
                    break;
                default:
                    System.out.println("opcao invalida");
            }
        }


        out.close();
        in.close();
        stdIn.close();
        echoSocket.close();
    }

    public static void login(Scanner scan, ObjectMapper mapper, BufferedReader in, PrintWriter out) {
        scan.nextLine();
        System.out.println("Insira email");
        String email = scan.nextLine();
        System.out.println("Insira senha");
        String senha = scan.nextLine();
        LoginDados logindado = new LoginDados(email, senha);
        String jason;
        try {
            jason = mapper.writeValueAsString(logindado);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        out.println(jason);
        try {
            System.out.println("echo: " + in.readLine());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

