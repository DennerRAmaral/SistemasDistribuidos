package Maincliente;
import java.io.*;
import java.net.*;
import java.util.*;
import com.fasterxml.jackson.databind.ObjectMapper;

public class maincliente {
    public static void main(String[] args) throws IOException {
        BufferedReader leitorbuffer = new BufferedReader(new InputStreamReader(System.in));
        System.out.println(" Insira o ip do server desejado:");
        String ip = leitorbuffer.readLine();
        System.out.println(" Insira a Porta de acesso do server");
        int port = Integer.parseInt(leitorbuffer.readLine());

        if (args.length > 0)
            ip = args[0];
        System.out.println ("Tentando conectar  " +
                ip + " na porta "+port);

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
        String userInput;

        System.out.println ("Type Message (\"Bye.\" to quit)");
        while ((userInput = stdIn.readLine()) != null)
        {
            out.println(userInput);

            // end loop
            if (userInput.equals("Bye."))
                break;

            System.out.println("echo: " + in.readLine());
        }

        out.close();
        in.close();
        stdIn.close();
        echoSocket.close();
    }
}