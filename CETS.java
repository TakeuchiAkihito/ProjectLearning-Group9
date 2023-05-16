import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;
public class CETS {
    public static void main(String[] args) {
    	String cmd;
    	String id = null;
    	String pw = null;
    	String q = null;
    	String idx;
    	String pwx;
    	String qx;
    	
    	
        try{
            ServerSocket server = new ServerSocket(3838, 5);
            while(true){
                System.out.println("サーバーは稼働しています。");
                Socket socket = server.accept();
                Scanner input = new Scanner(socket.getInputStream());
                PrintWriter output = new PrintWriter(socket.getOutputStream());
                
                cmd = input.nextLine();
                while( true ) {
                	switch(cmd) {
                	case "1":
                		System.out.print(cmd);
                		idx = input.nextLine();
                		pwx = input.nextLine();
                		System.out.print("ログインID: " + idx );
                		System.out.print("ログインPW: " + pwx);
                		if(idx.equals(id) && pwx.equals(pw)) {
                			output.println("Success");
                			output.flush();
                		}else {
                			output.println("Not");
                			output.flush();
                		}
                		break;
                		
                	case "2":
                		System.out.print(cmd);
                		id = input.nextLine();
                		pw = input.nextLine();
                		q = input.nextLine();
                		System.out.print("新規ID: " + id );
                		System.out.print("新規PW: " + pw);
                		System.out.print("新規Q : " + q );
                		
                		break;
                		
                	case "5":
                		System.out.print(cmd);
                		idx = input.nextLine();
                		qx = input.nextLine();
                		System.out.print("リセットID: " + idx );
                		System.out.print("リセットQ : " + qx);
                		if( idx.equals(id) && qx.equals(q)) {
                			output.println("Success");
                			output.flush();
                			pw = input.nextLine();
                			System.out.println("新PW :" + pw);
                			output.println("Success");
                			output.flush();
                			
                				
                		}else {
                			output.println("Not");
                			output.flush();
                		}
                		
                		break;
                		
                	}
                	
                	cmd = input.nextLine();
                }
                  
                
            }
        } catch (Exception e){
            System.out.println(e);
        }
    }
}