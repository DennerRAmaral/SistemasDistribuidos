package Servidor;
//Per Aspera

import java.io.*;
import java.net.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;


public class EchoServer extends Thread {
    static final String JDBC_URL = "jdbc:mysql://localhost:3306/sistemadistribuidodb";
    static final String USUARIO = "root";
    static final String SENHA = "";
    protected static boolean serverContinue = true;
    protected Socket clientSocket;

    public static void main(String[] args) throws RuntimeException {
       /* CriarBanco criabanco = new CriarBanco();
        criabanco.run();*/
        Connection conn = null;
        Statement stmt = null;
        try {
            // Passo 1: Registrar o driver JDBC
            Class.forName("com.mysql.cj.jdbc.Driver");
            // Passo 2: Abrir uma conexão
            System.out.println("Conectando ao banco de dados...");
            conn = DriverManager.getConnection(JDBC_URL, USUARIO, SENHA);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {


            ServerSocket serverSocket = null;
            try {
                System.out.println("IP: " + InetAddress.getLocalHost().getHostAddress());
                System.out.println("Porta: 22222");
                serverSocket = new ServerSocket(22222);
                System.out.println("Connection Socket Created");
                try {
                    while (serverContinue) {
                        serverSocket.setSoTimeout(10000);
                        System.out.println("Waiting for Connection");
                        try {
                            new EchoServer(serverSocket.accept());
                        } catch (SocketTimeoutException ste) {
                            System.out.println("Timeout Occurred");
                        }
                    }
                } catch (IOException e) {
                    System.err.println("Accept failed.");
                    System.exit(1);
                }
            } catch (IOException e) {
                System.err.println("Could not listen on port: 10008.");
                System.exit(1);
            } finally {
                try {
                    System.out.println("Closing Server Connection Socket");
                    assert serverSocket != null;
                    serverSocket.close();
                } catch (IOException e) {
                    System.err.println("Could not close port: 10008.");
                    System.exit(1);
                }
            }
            try {
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private EchoServer(Socket clientSoc) {
        clientSocket = clientSoc;
        start();
    }

    public void run() {
        System.out.println("New Communication Thread Started");

        try {
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(),
                    true);
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(clientSocket.getInputStream()));

            String inputLine;

            while ((inputLine = in.readLine()) != null) {
                System.out.println("Server: " + inputLine);

                if (inputLine.equals("?"))
                    inputLine = "\"Bye.\" ends Client, " +
                            "\"End Server.\" ends Server";

                out.println(inputLine);

                if (inputLine.equals("Bye."))
                    break;

                if (inputLine.equals("End Server."))
                    serverContinue = false;
            }

            out.close();
            in.close();
            clientSocket.close();
        } catch (IOException e) {
            System.err.println("Problem with Communication Server");
            System.exit(1);
        }
    }
}