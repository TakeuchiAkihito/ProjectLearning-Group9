package J1;

import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;
public class J3 {
    public static void main(String[] args) {
        try{
            ServerSocket server = new ServerSocket(3838, 5);
            while(true){
                System.out.println("サーバーは稼働しています。");
                Socket socket = server.accept();
                Scanner input = new Scanner(socket.getInputStream());
                PrintWriter output = new PrintWriter(socket.getOutputStream());
                System.out.print(input.nextLine());
                System.out.print(input.nextLine());
                output.println("1");
                output.flush();
                
                input.nextLine();
                output.println("first");
                output.flush();
                
                input.nextLine();
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
        } catch (Exception e){
            System.out.println(e);
        }
    }
}