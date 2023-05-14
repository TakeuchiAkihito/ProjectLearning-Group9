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
            String name1, name2;
            //boolean gameEnd = false;
            //boolean trun = true;
            
           
            Socket socket1 = server1.accept();
            Socket socket2 = server2.accept();
            System.out.println("2台接続完了");
            
            Scanner input1 = new Scanner(socket1.getInputStream());
            PrintWriter output1 = new PrintWriter(socket1.getOutputStream());
            name1 = input1.nextLine();
            System.out.println(name1);
            System.out.println(input1.nextLine());
            output1.println("1");
            output1.flush();

            Scanner input2 = new Scanner(socket2.getInputStream());
            PrintWriter output2 = new PrintWriter(socket2.getOutputStream());
            name2 = input2.nextLine();
            System.out.println(name2);
            System.out.println(input2.nextLine());
            output2.println("1");
            output2.flush();
            
            while(true){
            	
               try {
                
                input1.nextLine();
                input2.nextLine();
                
                output1.println(Set.FIRST);
                output1.flush();
                output1.println(name2);
                output1.flush();
                output2.println(Set.REAR);
                output2.flush();
                output2.println(name1);
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