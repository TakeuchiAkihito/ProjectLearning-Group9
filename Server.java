package pl_1;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Server {
	public static Account []account=new Account[10000];
    public static int num_account=0;
    static ServerSocket serverS;
    public static void main(String[] args) {
    	for(int i=0;i<10000;i++) {
    		account[i]=new Account();
    	}
        try{
            while(true){
                System.out.println("サーバーは稼働しています。");
                serverS = new ServerSocket(3838);
                Socket socket = serverS.accept();
                new ServThread(socket).start();
                
            }
        } catch (Exception e){
            System.out.println(e);
        }
    }
}

class ServThread extends Thread{
	private Socket socket;
	public ServThread(Socket socket) {
		this.socket=socket;
		System.out.println("接続されました"+socket.getRemoteSocketAddress());
	}
	
	public void run() {
        Scanner input;
		try {
			  input = new Scanner(socket.getInputStream());
			  PrintWriter output = new PrintWriter(socket.getOutputStream());
			  Integer port =Server.serverS.getLocalPort();
			  output.println(port.toString());

		        while(true) {
		        	switch(input.nextInt()) {
		        	case 1:
		        		Case1:{
		        			for(int i=0;i<Server.num_account;i++) {
			        			if((Server.account[i].ID == input.nextLine()) && (Server.account[i].PW == input.nextLine())) {
			        				output.println("1");
			        				System.out.println(Server.account[i].ID);
			        				break Case1;
			        			}		        			
			        		}
		        			output.println("0");
		        			break;
		        		}
		        		break;
		        	case 2:
		        		input.nextLine();
		        		System.out.println("ok");
		        		if(Server.num_account<10000) {
		        			System.out.println();
		        			Server.account[Server.num_account].ID=input.nextLine();
			        		Server.account[Server.num_account].PW=input.nextLine();
			        		Server.account[Server.num_account].Q_sec=input.nextLine();
			        		System.out.println("ID:"+Server.account[Server.num_account].ID+" PW:"+Server.account[Server.num_account].PW+" Q_sec:"+Server.account[Server.num_account].Q_sec);
			        		output.println("Success");
		        		}else {
		        			output.println("0");
		        		}break;
		        	case 3:
		        	case 4:
		        	case 5:
		        	}
		        	
		        }
		        
		        //output.close();     // PrintWriterはclose()で閉じるのが基本
		       // socket.close();     // Socketはclose()で閉じるのが基本
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}

class Account{
	String ID="null";
	String PW="null";
	String Q_sec="null";
	
	static int []record=new int[3];
	Account(){
		record[0]=0;//勝ち
		record[1]=0;//負け
		record[2]=0;//投了
	}
	
	public void set_record(int result) {
		record[result]++;
	}
	public int[] get_record() {
		return record;
	}
	
}