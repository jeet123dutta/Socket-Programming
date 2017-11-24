import java.net.*;
import java.io.*;
import java.lang.*;
import java.util.*; 
 public class Clients {
 	
 	public static void main(String[] args) {
 		String ipadrr = args[0];
 		Integer hostNum = Integer.valueOf(args[1]).intValue();
 		String fun= "" ;
 		String key = "";
 		String value = "";
 		String res="";
 		Socket client =null;
 		try{
 		  client  = new Socket(ipadrr,hostNum);
 		}catch(Exception e ){}


 		Scanner s = new Scanner(System.in);
 		System.out.print("Enter your usrname (manager/ or other name) : ");
 		String usrname = s.nextLine();
 		String pswrd="" ;
 		if (usrname.equalsIgnoreCase("manager")) {
 			System.out.print("Enter your password : ");
 			pswrd = s.nextLine();
 		}
 		while(true){
 		System.out.println("Enter the function (get/put) : ");
 		fun = s.nextLine();
 		if (fun.equalsIgnoreCase("put")) {
 			System.out.println("Enter the key and value: ");
 			key =  s.nextLine();
 			value =s.nextLine();
 		}
 		else{
 			System.out.println("Enter the key to get : ");
 			key =  s.nextLine();
 		}
 		
 		try{
 			DataOutputStream  dos  =  new DataOutputStream(client.getOutputStream());
 			DataInputStream  din = new DataInputStream(client.getInputStream());
 			BufferedReader br  =  new BufferedReader(new InputStreamReader(System.in));
 			if (usrname.equalsIgnoreCase("manager")) {
 				 System.out.println("Enter the usr whose data want to modify : ");
 				 String modUsr = s.nextLine();
 				 dos.writeUTF(usrname+","+fun+","+key+","+value+","+pswrd+","+modUsr);
 			}
 			else
 				dos.writeUTF(usrname+","+fun+","+key+","+value);
 			res = din.readUTF();
 			System.out.println(res);

 		}catch(Exception e ){}


 		}
 		
 		
 		//client.close();
 	}

 }