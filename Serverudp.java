package udp;
import java.io.*;
import java.net.*;
import java.util.Scanner;

class Serverudp {
    public static void main(String args[]) throws Exception
    {
        Scanner scan = new Scanner(System.in);
        try
        {
            System.out.println("IP: "+ InetAddress.getLocalHost().getHostAddress());
            System.out.println("Insira a porta:");
            int i = scan.nextInt();
            DatagramSocket serverSocket = new DatagramSocket(i);

            byte[] receiveData = new byte[1024];
            byte[] sendData  = new byte[1024];

            while(true)
            {

                receiveData = new byte[1024];

                DatagramPacket receivePacket =
                        new DatagramPacket(receiveData, receiveData.length);

                System.out.println ("Waiting for datagram packet");

                serverSocket.receive(receivePacket);

                String sentence = new String(receivePacket.getData());
                sentence = sentence.toUpperCase();
                sentence = sentence.trim();
                InetAddress IPAddress = receivePacket.getAddress();

                int port = receivePacket.getPort();

                System.out.println ("From: " + IPAddress + ":" + port);
                System.out.println ("Message: " + sentence);

                String capitalizedSentence = sentence.toUpperCase();

                sendData = capitalizedSentence.getBytes();

                DatagramPacket sendPacket =
                        new DatagramPacket(sendData, sendData.length, IPAddress,
                                port);

                serverSocket.send(sendPacket);

            }

        }
        catch (SocketException ex) {
            System.out.println("UDP Port 9876 is occupied.");
            System.exit(1);
        }

    }
}
