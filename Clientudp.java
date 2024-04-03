package udp;
import java.io.*;
import java.net.*;
import java.util.Scanner;

class Clientudp {
    public static void main(String args[]) throws Exception
    {
        try {
            Scanner scan = new Scanner(System.in);
            System.out.println("insira o ip do server:");
            String serverHostname = scan.nextLine();

            if (args.length > 0)
                serverHostname = args[0];

            BufferedReader inFromUser =
                    new BufferedReader(new InputStreamReader(System.in));

            DatagramSocket clientSocket = new DatagramSocket();

            InetAddress IPAddress = InetAddress.getByName(serverHostname);
            System.out.println("Insira a porta:");
            int i = scan.nextInt();

            byte[] sendData = new byte[1024];
            byte[] receiveData = new byte[1024];
            do {

                System.out.print("Enter Message: ");
                String sentence = inFromUser.readLine();
                sendData = sentence.getBytes();
                if (sentence.equals("fim"))
                    break;
                System.out.println ("Sending data to " + sendData.length +
                        " bytes to server.");
                DatagramPacket sendPacket =
                        new DatagramPacket(sendData, sendData.length, IPAddress, i);

                clientSocket.send(sendPacket);

                DatagramPacket receivePacket =
                        new DatagramPacket(receiveData, receiveData.length);

                System.out.println ("Waiting for return packet");
                clientSocket.setSoTimeout(10000);

                try {
                    clientSocket.receive(receivePacket);
                    String modifiedSentence =
                            new String(receivePacket.getData());
                    modifiedSentence = modifiedSentence.trim();
                    InetAddress returnIPAddress = receivePacket.getAddress();

                    int port = receivePacket.getPort();

                    System.out.println ("From server at: " + returnIPAddress +
                            ":" + port);
                    System.out.println("Message: " + modifiedSentence);

                }
                catch (SocketTimeoutException ste)
                {
                    System.out.println ("Timeout Occurred: Packet assumed lost");
                }

            }while (true);

            clientSocket.close();
        }
        catch (UnknownHostException ex) {
            System.err.println(ex);
        }
        catch (IOException ex) {
            System.err.println(ex);
        }
    }
}