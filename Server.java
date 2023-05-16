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
        int this_acc=0;
		try {
			  input = new Scanner(socket.getInputStream());
			  PrintWriter output = new PrintWriter(socket.getOutputStream());

		        while(true) {
		        	switch(input.nextInt()) {
		        	case 1://ログイン
		        		Case1:{
		        			input.nextLine();
		        			for(int i=0;i<Server.num_account;i++) {
		        				System.out.println("ID:"+Server.account[i].ID+" PW:"+Server.account[i].PW);
			        			if((Server.account[i].ID.equals(input.nextLine())) && (Server.account[i].PW.equals(input.nextLine()))) {
			        				System.out.println("ok");
			        				output.println("Success");
			        				output.flush();
			        				System.out.println("ID:"+Server.account[i].ID+" PW:"+Server.account[i].PW);
			        				this_acc=i;
			        				break Case1;
			        			}		        			
			        		}
		        			output.println("0");
		        			break;
		        		}
		        		break;
		        	case 2://新規アカウント作成
		        		input.nextLine();
		        		System.out.println("ok");
		        		if(Server.num_account<10000) {
		        			System.out.println();
		        			Server.account[Server.num_account].ID=input.nextLine();
			        		Server.account[Server.num_account].PW=input.nextLine();
			        		Server.account[Server.num_account].Q_sec=input.nextLine();
			        		System.out.println("ID:"+Server.account[Server.num_account].ID+" PW:"+Server.account[Server.num_account].PW+" Q_sec:"+Server.account[Server.num_account].Q_sec);
			        		Server.num_account++;
			        		output.println("Success");
			        		output.flush();
		        		}else {
		        			output.println("0");
		        		}break;
		        	case 3://対局
		        	case 4://対局成績
		        		int []this_record=new int[4];
		        		this_record=Server.account[this_acc].get_record();
		        		for(int i=0;i<4;i++) {
		        			output.println(this_record[i]);
		        			output.flush();
		        		}
		        	case 5://パスワード再設定
		        		Case5:{
		        			input.nextLine();
		        			for(int i=0;i<Server.num_account;i++) {
			        			if((Server.account[i].ID.equals(input.nextLine())) && (Server.account[i].Q_sec.equals(input.nextLine()))) {
			        				System.out.println("ok");
			        				output.println("Success");
			        				output.flush();
			        				System.out.println("ID:"+Server.account[i].ID+" Q_sec:"+Server.account[i].Q_sec);
			        				this_acc=i;
			        				break Case5;
			        			}		        			
			        		}
		        			output.println("0");
		        			break;
		        		}
		        		
		        	case 6:
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
	
	static int []record=new int[4];
	Account(){
		record[0]=0;//勝ち
		record[1]=0;//負け
		record[2]=0;//引き分け
		record[3]=0;//投了
	}
	
	public void set_record(int result) {
		record[result]++;
	}
	public int[] get_record() {
		return record;
	}
	
}