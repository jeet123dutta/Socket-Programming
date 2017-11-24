import java.net.*;
import java.io.*;
import java.util.Scanner;

class MyServer{

	public static void main(String args[]) throws Exception
	{
		Scanner scanner = new Scanner(System.in);
		System.out.print("Enter the port : ");
		int port = scanner.nextInt();
		ServerSocket serverSocket = new ServerSocket(port);
		Socket server = serverSocket.accept();  //waits for the connection to be established
		
		System.out.println("Client and server are connected");
		
		DataInputStream din= new DataInputStream(server.getInputStream());
		DataOutputStream dout= new DataOutputStream(server.getOutputStream());
		
		String commandReceived, commandResult="";
		while(true){

				commandReceived=din.readUTF();
		System.out.println("Client has sent : "+commandReceived);
		String command="";
		if(commandReceived.equals("0"))
		{
			command="cmd /c date";
			try 
			{
				Process process = Runtime.getRuntime().exec(command);
				BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
				commandResult=(reader.readLine());
				reader.close();
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
		}
		else if(commandReceived.equals("1"))
		{
			command="cmd /c whoami";
			try 
			{
				Process process = Runtime.getRuntime().exec(command);
				BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
				String line;
				commandResult=(reader.readLine());
 
				reader.close();
	
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
		}
		else if (commandReceived.equals("-1")){

				break;
		}
		else
		{
			commandResult = "Invalid Command!";
		}
			dout.writeUTF(commandResult);

		}
		
		dout.flush();
		scanner.close();
		serverSocket.close();
	}
}
