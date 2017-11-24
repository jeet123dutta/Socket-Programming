import java.net.*;
import java.io.*;
import java.util.Scanner;
class MyClient
{
	public static void main(String args[]) throws Exception
	{
		try{
		Scanner scanner = new Scanner(System.in);
		System.out.print("Enter the hostname :");
		String hostname = scanner.nextLine();
		System.out.print("Enter the port :");
		int port = scanner.nextInt();
		
		//InetAddress address = InetAddress.getByName(hostname); 
		Socket client = new Socket(hostname,port); 
		
		DataInputStream din= new DataInputStream(client.getInputStream());
		DataOutputStream dout= new DataOutputStream(client.getOutputStream());
		BufferedReader br= new BufferedReader(new InputStreamReader(System.in));
		
		String str1=" ",str2=" ";
		while(true){
			System.out.print("Enter the command :\n\t\t0 for date\n\t\t1 for who am i\n\t\t-1 to exit\n");
			str1=br.readLine();
			dout.writeUTF(str1);
			dout.flush();
			str2=din.readUTF();
			System.out.println("\nThe Server has sent : "+str2);
			if(str2.equals("-1")){
				break;
			}

		}
		
		scanner.close();
		client.close();
	}catch (Exception e){}
	}
}