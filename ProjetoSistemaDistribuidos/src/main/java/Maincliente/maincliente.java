package Maincliente;

import java.io.*;
import java.net.*;
import java.util.*;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.apache.commons.codec.digest.DigestUtils;
import org.mindrot.jbcrypt.BCrypt;

public class maincliente {

    public static void main(String[] args) throws IOException {
        BufferedReader leitorbuffer = new BufferedReader(new InputStreamReader(System.in));
        System.out.println(" Insira o ip do server desejado:");
        String ip = leitorbuffer.readLine();
        System.out.println(" Insira a Porta de acesso do server");
        int port = Integer.parseInt(leitorbuffer.readLine());

        if (args.length > 0)
            ip = args[0];
        System.out.println("Tentando conectar  " +
                ip + " na porta " + port);

        Socket echoSocket = null;
        PrintWriter out = null;
        BufferedReader in = null;

        try {
            echoSocket = new Socket(ip, port);
            out = new PrintWriter(echoSocket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(
                    echoSocket.getInputStream()));
        } catch (UnknownHostException e) {
            System.err.println("Host desconhecido: " + ip);
            System.exit(1);
        } catch (IOException e) {
            System.err.println("Couldn't get I/O for "
                    + "the connection to: " + ip);
            System.exit(1);
        }

        BufferedReader stdIn = new BufferedReader(
                new InputStreamReader(System.in));
        int acao;
        acao = -1;
        String userInput;
        while (acao != 0) {
            System.out.println("Selecione sua acao:\n 1- Login\n 2- cadastro\n 8- logout \n 0- fechar");
            acao = Integer.parseInt(leitorbuffer.readLine());
            switch (acao) {
                case 0:
                    System.out.println("fechando sistema");

                    break;
                case 1:
                    String email;
                    String senha;
                    System.out.println("Insira seu email:");
                    email = leitorbuffer.readLine();
                    System.out.println("Insira sua senha:");
                    senha = leitorbuffer.readLine();
                    String senhaMD5 = BCrypt.hashpw(senha, BCrypt.gensalt());
                    String jsonString = "{\"action\": \"login\",\"data\": {\"email\": \"" + email + "\",\"password\": \"" + senhaMD5 + "\"}" ;
                    System.out.println("enviando json:\n" + jsonString);
                    try {
                        ObjectMapper objectMapper = new ObjectMapper();
                        JsonNode jsonNode = objectMapper.readTree(jsonString);
                        out.println(jsonNode);
                    } catch (JsonProcessingException e) {
                        e.printStackTrace();
                    }


                    break;
                default:
                    System.out.println("Comando invalida.\n insisra novamente\n\n");
            }


        }

        out.close();
        in.close();
        stdIn.close();
        echoSocket.close();
    }
}