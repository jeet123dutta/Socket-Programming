import java.io.*;
import java.net.*;

class Client {
    public static void main(String args[]) throws Exception {
        BufferedReader inFromUser
                = new BufferedReader(new InputStreamReader(System.in));
        
        DatagramSocket clientSocket = new DatagramSocket();
        
        byte[] receiveData = new byte[1024];
        
        String input = inFromUser.readLine();
        InetAddress ip = InetAddress.getByName(input);
        
        DatagramPacket sendPacket = new DatagramPacket(input.getBytes(), input.getBytes().length, ip, 9000);
        clientSocket.send(sendPacket);
        DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
        clientSocket.receive(receivePacket);
        String mac = new String(receivePacket.getData());
        System.out.println(mac);
        clientSocket.close();
    }
}