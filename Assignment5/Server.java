import java.net.*;

class Server {

    public static void main(String args[]) throws Exception {
        DatagramSocket serverSocket = new DatagramSocket(9000);
        byte[] receiveData = new byte[1024];
        while (true) {
            DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
            serverSocket.receive(receivePacket);
            
            InetAddress ip = receivePacket.getAddress();
            int port = receivePacket.getPort();
            
            NetworkInterface ni = NetworkInterface.getByInetAddress(ip);
			byte[] mac = ni.getHardwareAddress();
			System.out.println("QUERY :- " + mac);
			
            DatagramPacket sendPacket = new DatagramPacket(mac, mac.length, ip, port);
            serverSocket.send(sendPacket);
        }
    }
}