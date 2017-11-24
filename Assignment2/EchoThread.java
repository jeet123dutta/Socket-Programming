import  java.io.*;
import  java.net.*;
import  java.lang.*;
import java.util.*;

 public class EchoThread extends Thread {
    protected Socket socket;
    private HashMap <String ,  String > memory =  new   HashMap < String , String > ();
    public static HashMap <String , HashMap <String ,  String > > ManagerAccount =   new HashMap <String ,HashMap <String ,  String > > ();
    public EchoThread(Socket clientSocket) {
        this.socket = clientSocket;
    }

    public void run() {
        System.out.println("Inside run()!!");
        InputStream inp = null;
        BufferedReader brinp = null;
        DataOutputStream out = null;
        DataInputStream din = null;
        Scanner  s  = new Scanner(System.in);
        try {
            inp = socket.getInputStream();
            //brinp = new BufferedReader(new InputStreamReader(inp));
             din = new DataInputStream(inp);
            out = new DataOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            return;
        }
        String line,usrname;
        while (true) {
            try {
                line = din.readUTF();
                System.out.println("command recieved : "+ line);
                if ((line == null) || line.equalsIgnoreCase("QUIT")) {
                    socket.close();
                    return;
                } 

                else {

                         String [] cmds =  line.split(",");
                         //System.out.println(cmds[0]+" "+cmds[1]+ " "+ cmds[2]+" "+cmds[3]+" "+cmds[4]);
                         usrname = cmds[0];
                         if (usrname.equalsIgnoreCase("manager")) {
                             //System.out.print("Enter password :");
                            // String p = s.nextLine();
                             if (cmds[4].equalsIgnoreCase("HARI")) {
                                    String tempuser = cmds[5];
                                    if (cmds[1].equalsIgnoreCase("put")) {
                                         String key,value;
                                         key =  cmds[2];
                                         value = cmds[3];
                                         
                                         ManagerAccount.get(tempuser).put(key,value);
                                         out.writeUTF("Data stored successfully!!");
                                     }
                                     else if(cmds[1].equalsIgnoreCase("get")){
                                        //System.out.println("Enter the usrname whose data u want :");
                                        //String v =  s.nextLine();

                                        String key =  cmds[2];
                                        String ans = ManagerAccount.get(tempuser).get(key);
                                        if (ans != null) {
                                            out.writeUTF(key +"::"+ ans);
                                        }
                                        else
                                            out.writeUTF("No data found!!");
                                    }
                                    else{
                                         out.writeUTF("Invalid command format enter ipaddr , port ,  get/set , key,value!!");
                                        }
                             }

                         }
                         else{
                             if (cmds[1].equalsIgnoreCase("put")) {
                                 String key,value;
                                 key =  cmds[2];
                                 value = cmds[3];
                                 memory.put(key,value);
                                 ManagerAccount.put(usrname,memory);
                                 out.writeUTF("Data stored successfully!!");
                            }
                             else if(cmds[1].equalsIgnoreCase("get")){
                                String key =  cmds[2];
                                String ans = memory.get(key);
                                if (ans != null) {
                                    out.writeUTF(key +"::"+ ans);
                                }
                                else
                                    out.writeUTF("No data found!!");
                            }
                            else{
                                    out.writeUTF("Invalid command format enter ipaddr , port ,  get/set , key,value!!");
                                }
                         }
                         
                    
                        //out.writeBytes(line + "\n\r");
                        out.flush();
                }
            } catch (IOException e) {
                e.printStackTrace();
                return;
            }
        }
    }
}