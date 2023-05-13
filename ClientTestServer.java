import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.NoSuchElementException;
import java.util.Scanner;
public class ClientTestServer {
    public static void main(String[] args) {
    	try {
    		 System.out.println("サーバーは稼働しています。");
        	boolean flag = false;
        	ServerSocket server1 = new ServerSocket(0);
            ServerSocket server2 = new ServerSocket(0);
            ServerSocket test;
            PrintWriter outputT;
            Socket testS;
            Integer port;
        	
        		test = new ServerSocket(3838);
        		testS = test.accept();
        		port = server1.getLocalPort();
        		outputT = new PrintWriter(testS.getOutputStream());
        		outputT.println(port.toString());
        		test.close();
        		outputT.close();
        		testS.close();
        		
        		test = new ServerSocket(3838);
        		testS = test.accept();
        		port = server2.getLocalPort();
        		outputT = new PrintWriter(testS.getOutputStream());
        		outputT.println(port.toString());
        		test.close();
        		outputT.close();
        		testS.close();
        	
        	
        	
            
            
            String cmd1, cmd2;
            //String name1, name2;
            //boolean gameEnd = false;
            //boolean trun = true;
            
           
            Socket socket1 = server1.accept();
            Socket socket2 = server2.accept();
            System.out.println("2台接続完了");
            Scanner input1 = new Scanner(socket1.getInputStream());
            PrintWriter output1 = new PrintWriter(socket1.getOutputStream());
            Scanner input2 = new Scanner(socket2.getInputStream());
            PrintWriter output2 = new PrintWriter(socket2.getOutputStream());
            
            System.out.println(input1.nextLine());
            System.out.println(input1.nextLine());
            System.out.println(input2.nextLine());
            System.out.println(input2.nextLine());
            
            output1.println("1");
            output1.flush();
            
            output2.println("1");
            output2.flush();
            
            while(true){
            	
               try {
                
                input1.nextLine();
                input2.nextLine();
                
                output1.println(Set.FIRST);
                output1.flush();
                output2.println(Set.REAR);
                output2.flush();
                System.out.println("対局開始");
                
                
                cmd1 = input1.nextLine();
                
                while( !cmd1.equals(Set.RETIRE) && !cmd1.equals(Set.WIN) && !cmd1.equals(Set.LOSE) && !cmd1.equals(Set.DRAW)  ) {
                	
                	System.out.println(cmd1);
                		
                	output2.println(cmd1);
                    output2.flush();
                        
                    cmd2 = input2.nextLine();
                    
                	if( cmd2.equals(Set.RETIRE)) {
                    	//name2 = input2.nextLine();
                		System.out.println(cmd2);
                    	output1.println(cmd2);
                        output1.flush();
                        flag = true;
                        break;
                            
                    }else if(cmd2.equals(Set.WIN) || cmd2.equals(Set.LOSE) || cmd2.equals(Set.DRAW)){
                    	//name2 = input1.nextLine();
                    	System.out.println(cmd2);
                    	cmd1 = input1.nextLine();
                    	flag = true;
                    	break;
                    	
                    }else {
                    	System.out.println(cmd2);
                    	output1.println(cmd2);
                        output1.flush();
                        cmd1 = input1.nextLine();
                    }
                		
                	
                }
                if( !flag ) {
                if( cmd1.equals(Set.RETIRE)) {
            		//name1 = input1.nextLine();
            		System.out.println(cmd1);
            		output2.println(cmd1);
                    output2.flush();
                    
                   
            	}else if(cmd1.equals(Set.WIN) || cmd1.equals(Set.LOSE) || cmd1.equals(Set.DRAW)){
            		//name1 = input1.nextLine();
            		
            		System.out.println(cmd1);
            		cmd2 = input2.nextLine();
            		System.out.println(cmd2);
            		
                    
            	}else {
            		System.out.println(cmd1);
            	}
                }
                flag = false;
                
                /*
                output1.close();     // PrintWriterはclose()で閉じるのが基本
                socket1.close();     // Socketはclose()で閉じるのが基本
                output2.close();     // PrintWriterはclose()で閉じるのが基本
                socket2.close();     // Socketはclose()で閉じるのが基本
                */
               }catch (NoSuchElementException e){
            	   //System.out.println((e.getClass()).toString());
            	   //output1.println("ConnectError");
                   //output1.flush();
                   //output2.println("ConnectError");
                   //output2.flush();
               }
                
            }
        } catch (Exception e){
            System.out.println(e);
        }
    	
    }
/*    
    public static synchronized void send() {
    	
    }
}

class ServerThread extends Thread{
	Scanner input;
	PrintWriter output;
	String cmd;
	
	ServerThread(Scanner input, PrintWriter output){
		this.input = input;
		this.output = output;
	}
	
	public void run() {
		 while(true){
             System.out.print(input.nextLine());
             System.out.print(input.nextLine());
             output.println("1");
             output.flush();
             
             if(input.nextLine()=="c1") {
            	 output.println("first");
                 output.flush();
             }else {
            	 output.println("rear");
                 output.flush();
             }
             
             
             cmd = input.nextLine();
             output.println("46");
             output.flush();
             
             input.nextLine();
             output.println("43");
             output.flush();
             
             input.nextLine();
             output.println("retire");
             output.flush();
             
             output.close();     // PrintWriterはclose()で閉じるのが基本
             socket.close();     // Socketはclose()で閉じるのが基本
         }
	}*/
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