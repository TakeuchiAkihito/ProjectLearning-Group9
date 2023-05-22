import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class OthelloServer {
	public static Account []account=new Account[10000];
    public static int num_account=0;
    static ServerSocket serverS;
    
    static ServerSocket test;
    static Socket testS;
    static Scanner input;
    static PrintWriter output;
    
    public static void main(String[] args) {
    	for(int i=0;i<10000;i++) {
    		account[i]=new Account();
    	}
        try{
        	serverS = new ServerSocket(3838,10);
            while(true){          	
            	
                System.out.println("サーバーは稼働しています。");
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
        Scanner input = null;
        PrintWriter output = null;
        int this_acc=0;
        String this_id;
		String this_pw;
		String this_qsec;
		Account ac = null;
		
		GameIn gI = null;
		Queue q = null;
		long st;
		long et;
		
		
		try {
			  input = new Scanner(socket.getInputStream());
			  output = new PrintWriter(socket.getOutputStream());
			  
			  
			  

		        while(true) {
		        	switch(input.nextInt()) {
		        	case 1://ログイン
		        		Case1:{
		        			System.out.println("ログイン");
		        			input.nextLine();
		        			this_id=input.nextLine();
		        			this_pw=input.nextLine();
		        			if(OthelloServer.num_account==0) {
		        				output.println("0");
		        				output.flush();
		        				break Case1;
		        			}
		        			for(int i=0;i<OthelloServer.num_account;i++) {
		        				
			        			if((OthelloServer.account[i].ID.equals(this_id) && (OthelloServer.account[i].PW.equals(this_pw)))){
			        				System.out.println("ok");
			        				output.println("Success");
			        				output.flush();
			        				// 試作
			        				ac = OthelloServer.account[i];
			        				
			        				System.out.println("ID:"+OthelloServer.account[i].ID+" PW:"+OthelloServer.account[i].PW);
			        				this_acc=i;
			        				break Case1;
			        			}
			        		}
		        			output.println("0");
		        			output.flush();
		        			break;
		        		}
		        		break;
		        	case 2://新規アカウント作成
		        		System.out.println(input.nextLine());
		        		System.out.println("ok");
		        		if(OthelloServer.num_account<10000) {
		        			boolean flag = false;
		        			String idNew = input.nextLine();
		        			OthelloServer.account[OthelloServer.num_account].ID=idNew;
			        		OthelloServer.account[OthelloServer.num_account].PW=input.nextLine();
			        		OthelloServer.account[OthelloServer.num_account].Q_sec=input.nextLine();
			        		
			        		for(int i = 0; i < OthelloServer.num_account; i++) {
			        			if( OthelloServer.account[i].ID.equals(idNew)) {
			        				flag = true;
			        			}
			        		}
			        		
			        		System.out.println("ID:"+OthelloServer.account[OthelloServer.num_account].ID+" PW:"+OthelloServer.account[OthelloServer.num_account].PW+" Q_sec:"+OthelloServer.account[OthelloServer.num_account].Q_sec);
			        		
			        		if( flag ) {
			        			output.println("0");
				        		output.flush();
			        		}else {
			        			OthelloServer.num_account++;
				        		
				        		output.println("Success");
				        		output.flush();
			        		}
			        		
			        		
		        		}else {
		        			output.println("0");
		        			output.flush();
		        		}
		        		
		        		break;
		        	case 3://対局
		        		System.out.println(input.nextLine());
		        		//Queue q = new Queue(ac, socket);
		        		boolean nflag = false;
		        		q = new Queue(ac, input, output);
		        		gI = new GameIn();
		        		gI.entry(q, 0);
		        		System.out.println("エントリー");
		        		st = System.currentTimeMillis();
		        		while( !q.sflag ) {
		        			et = System.currentTimeMillis();
		        			if( (et - st) > 30000 ) {
		        				gI.entry(q, 1);
		        				nflag = true;
		        				break;
		        			}
		        		}
		        		if( !nflag ) {
			        		while( !q.gflag ) {
			        			sleep(1000);
			        			;
			        		}
			        		System.out.println("Fin");
		        		}else {
		        			output.println("NoEnemy");
		        			output.flush();
		        		}
		        		break;
		        			
		        	case 4://対局成績
		        		input.nextLine();
		        		int []this_record=new int[4];
		        		this_record=OthelloServer.account[this_acc].get_record();
		        		for(int i=0;i<4;i++) {
		        			output.println(this_record[i]);
		        			output.flush();
		        		}
		        		break;
		        	case 5://秘密の質問
		        		Case5:{
		        			input.nextLine();
		        			this_id=input.nextLine();
		        			this_qsec=input.nextLine();
		        			if(OthelloServer.num_account==0) {
		        				output.println("0");
		        				break Case5;
		        			}
		        			for(int i=0;i<OthelloServer.num_account;i++) {
			        			if((OthelloServer.account[i].ID.equals(this_id)) && (OthelloServer.account[i].Q_sec.equals(this_qsec))) {
			        				System.out.println("ok");
			        				output.println("Success");
			        				output.flush();
			        				System.out.println("ID:"+OthelloServer.account[i].ID+" Q_sec:"+OthelloServer.account[i].Q_sec);
			        				this_acc=i;
			        				break Case5;
			        			}		        			
			        		}
		        			output.println("0");
		        			output.flush();
		        			break;
		        		}
		        		
		        	case 6://パスワード再設定
		        		input.nextLine();
		        		OthelloServer.account[this_acc].PW=input.nextLine();
		        		output.println("Success");
        				output.flush();
        				System.out.println("ID:"+OthelloServer.account[this_acc].ID+" Q_sec:"+OthelloServer.account[this_acc].PW);
        				break;
		        	}
		        	
		        	
		        	
		        }
		        
		} catch (IOException e) {
			e.printStackTrace();
		}catch(NoSuchElementException e1) {
			
			output.close();
			input.close();	
			
		} catch (InterruptedException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		} 
		
	}
	
	
}

class Account{
	String ID="null";
	String PW="null";
	String Q_sec="null";
	
	int []record=new int[4];
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

class Queue{
	Account ac;
	Socket sc;
	Scanner input;
	PrintWriter output;
	boolean gflag = false;
	boolean sflag = false;
	
	Queue(Account ac, Scanner input, PrintWriter output){
		this.ac = ac;
		this.input = input;
		this.output = output;
	}
	
	public Socket getSocket() {
		return sc;
	}
	
	public Account getAccount() {
		return ac;
	}
	
	
}

class GameIn{
	static ArrayList<Queue> waitList = new ArrayList<Queue>();
	
	public synchronized boolean entry(Queue q2, int mode) {
		boolean flag = false;
		
		Queue q1;
		
		if( mode == 0 ) {
			if(waitList.isEmpty()) {
				waitList.add(q2);
				flag = false;
			}else {
				q1 = waitList.get(0);
				waitList.remove(0);
				q1.sflag = true;
				q2.sflag = true;
				(new Game(q1, q2)).start();
				flag = true;
			}
		}else {
			for(int i = 0; i < waitList.size(); i++) {
				if( q2.getAccount().ID.equals(waitList.get(i).getAccount().ID) ) {
					waitList.remove(i);
					flag = true;
				}
				
			}
		}
		
		return flag;
	}
	
	
}

class Game extends Thread{
	Queue q1;
	Queue q2;
	Scanner input1;
	Scanner input2;
	PrintWriter output1;
	PrintWriter output2;
	
	Game(Queue q1, Queue q2){
		this.q1 = q1;
		this.q2 = q2;
		
		input1 = q1.input;
		output1 = q1.output;
		input2 = q2.input;
		output2 = q2.output;
		
	}
	
	
	
	public void run() {
		String cmd1;
		String cmd2;
		boolean flag = false;
		int o1 = 3;
		int o2 = 3;
		boolean turn = false;
		
		
		
		try {
		
		output1.println(Set.FIRST);
        output1.flush();
        output1.println(q2.getAccount().ID);
        output1.flush();
        output2.println(Set.REAR);
        output2.flush();
        output2.println(q1.getAccount().ID);
        output2.flush();
        
        System.out.println("対局開始");
       
        cmd1 = input1.nextLine();
        while( !flag ) {
        	if( cmd1.equals(Set.RETIRE) ) {
        		System.out.println("1: r");
        		output2.println(cmd1);
                output2.flush();
                o1 = 3;
                o2 = 0;
                flag = true;
               
        	}else if( cmd1.equals(Set.WIN)) {
        		System.out.println("1: w");
        		cmd2 = input2.nextLine();
        		o1 = 0;
        		o2 = 1;
        		flag = true;
        		
        	}else if( cmd1.equals(Set.LOSE)) {
        		System.out.println("1: l");
        		cmd2 = input2.nextLine();
        		o1 = 1;
        		o2 = 0;
        		flag = true;
        		
        	}else if( cmd1.equals(Set.DRAW)) {
        		System.out.println("1: d");
        		cmd2 = input2.nextLine();
        		o1 = 2;
        		o2 = 2;
        		flag = true;
        		
        	}else {
        		output2.println(cmd1);
                output2.flush();
                
                turn = true;    
                cmd2 = input2.nextLine();
                if( cmd2.equals(Set.RETIRE) ) {
                	System.out.println("2: r");
            		output1.println(cmd2);
                    output1.flush();
                    o1 = 0;
                    o2 = 3;
                    flag = true;
                   
            	}else if( cmd2.equals(Set.WIN)) {
            		System.out.println("2: w");
            		cmd1 = input1.nextLine();
            		o1 = 1;
            		o2 = 0;
            		flag = true;
            		
            	}else if( cmd2.equals(Set.LOSE)) {
            		System.out.println("2: l");
            		cmd1 = input1.nextLine();
            		o1 = 0;
            		o2 = 1;
            		flag = true;
            		
            	}else if( cmd2.equals(Set.DRAW)) {
            		System.out.println("2: d");
            		cmd1 = input1.nextLine();
            		o1 = 2;
            		o2 = 2;
            		flag = true;
            		
            	}else {
            		output1.println(cmd2);
                    output1.flush();
                    
                    turn = false;
                    cmd1 = input1.nextLine();
            	}
        	}
        	
        }
        
        q1.getAccount().set_record(o1);
		q2.getAccount().set_record(o2);
 		
        q1.gflag = true;
        q2.gflag = true;
        
      
        
		}catch(NoSuchElementException e) {
			
			System.out.println("接続遮断");
			if( !turn ) {
				output2.println("ConnectError");
				output2.flush();
				q1.getAccount().set_record(3);
                q2.getAccount().set_record(0);
				
			}else if( turn ){
				output1.println("ConnectError");
				output1.flush();
				q1.getAccount().set_record(0);
                q2.getAccount().set_record(3);
			}
			
			q1.gflag = true;
	        q2.gflag = true;
			
		
		}
	}    
	
}

class Set{
	public static final String FIRST = "first";
	public static final String REAR = "rear";
	public static final String RETIRE = "retire";
	public static final String PASS = "pass";
	public static final String WIN = "win";
	public static final String LOSE = "lose";
	public static final String DRAW = "draw";
}