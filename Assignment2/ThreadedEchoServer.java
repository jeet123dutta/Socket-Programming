import  java.io.*;
import  java.net.*;
import  java.lang.*;
import java.util.*;
public class ThreadedEchoServer {

    

    public static void main(String args[]) {
        ServerSocket serverSocket = null;
        Socket socket = null;
        Scanner s = new Scanner (System.in);
		System.out.println("Enter the port number : ");
		int port ;
		port = s.nextInt();
        try {
            serverSocket = new ServerSocket(port);
        } 
        catch (IOException e) {
            e.printStackTrace();
        }
        while (true) {
            try {
                socket = serverSocket.accept();
                System.out.println("Connected!!");
            } catch (IOException e) {
                System.out.println("I/O error: " + e);
            }
            // new thread for a client
            new EchoThread(socket).start();
            System.out.println("thread started !");

        }
    }
}



	
