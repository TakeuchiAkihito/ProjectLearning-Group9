import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Random;
import java.util.Scanner;

public class Server_editing {
    static Account[] acc = new Account[10000];
    static int accNum = 0;
    public static void main(String[] args) {
        try{
            ServerSocket server = new ServerSocket(3838, 10);
            while(true)
            {
                Socket socket = server.accept();
                new LoginThread(socket).start();
            }
        } catch (Exception e){
            System.out.println(e);
        }
    }
}

class LoginThread extends Thread
{
    private Socket socket;
    private Scanner input;
    private PrintWriter output;
    public LoginThread(Socket socket)
    {
        this.socket = socket;
        try {
            output = new PrintWriter(socket.getOutputStream());
            input = new Scanner(socket.getInputStream());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.out.println("接続されました " + socket.getRemoteSocketAddress());
    }

    public void run()
    {
        try
        {
            while(true) {
                System.out.println(input.nextLine());
                if (input.equals("1"))// login
                {
                    String ID = input.nextLine();
                    String password = input.nextLine();
                    for (int i = 0; i < Server_editing.accNum; i++) {
                        if (Server_editing.acc[i].getID().equals(ID) && Server_editing.acc[i].getPassword().equals(password)) {
                            output.println("Success");
                            Queue queue = new Queue();
                            while (true) {
                                String str = input.nextLine();
                                if (str.equals("1"))// match
                                {
                                    queue.addQueue(Server_editing.acc[i], socket);
                                    break;
                                } else if (str.equals("2"))// logout
                                {
                                    break;
                                }
                            }
                        } else {
                            output.println("NG");
                        }
                    }
                }
                else if (input.equals("2"))// sakusei
                {
                    //System.out.println("hello!!");
                    String ID = input.nextLine();
                    String password = input.nextLine();
                    String Himitsu = input.nextLine();
                    Server_editing.acc[Server_editing.accNum] = new Account(ID, password, Himitsu);
                    Server_editing.accNum++;
                } else if (input.equals("3"))// wasureta
                {
                    String ID = input.nextLine();

                    for (int i = 0; i < Server_editing.accNum; i++) {
                        if (Server_editing.acc[i].getID().equals(ID)) {
                            output.println(Server_editing.acc[i].getHimitsu());
                            String answer = input.nextLine();
                            if (answer.equals(Server_editing.acc[i].getHimitsu())) {
                                output.println("Success");
                            } else {
                                output.println("Try again");
                            }
                        } else {
                            output.println("Try again");
                        }
                    }
                }
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}

class GameThread extends Thread
{
    private Socket socket1, socket2;
    private Scanner input1, input2;
    private PrintWriter output1, output2;

    private AccountInGame acc1, acc2;
    public GameThread(Socket socket1, Socket socket2, AccountInGame acc1, AccountInGame acc2)
    {
        this.socket1 = socket1;
        this.socket2 = socket2;
        this.acc1 = acc1;
        this.acc2 = acc2;
        try {
            output1 = new PrintWriter(socket1.getOutputStream());
            input1 = new Scanner(socket1.getInputStream());
            output2 = new PrintWriter(socket2.getOutputStream());
            input2 = new Scanner(socket2.getInputStream());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.out.println("ゲームへ接続されました " + socket1.getRemoteSocketAddress());
    }

    public void run()
    {
        try
        {
            output1.println(acc2.getID());//send opponent's ID
            output1.println(acc2.getColor());//send opponent's color
            output2.println(acc1.getID());
            output2.println(acc1.getColor());
            output1.flush();
            output2.flush();
            while(true)//game
            {
                String str = input1.nextLine();
                output2.println(str);
                output2.flush();
                str = input2.nextLine();
                output1.println(str);
                output1.flush();
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}

class Queue
{
    private static final int SIZE = 2;
    int waiting = 0;
    Account waitingAcc[] = new Account[SIZE];
    Socket waitingSocket[] = new Socket[SIZE];

    synchronized void addQueue(Account acc, Socket socket)
    {
        while(waiting == SIZE)
        {
            try
            {
                wait();
            }
            catch(InterruptedException e)
            {
                e.printStackTrace();
            }
        }
        waitingAcc[waiting] = acc;
        waitingSocket[waiting] = socket;
        waiting++;
        if(waiting == SIZE)//match found
        {
            preSettings(waitingSocket[0], waitingSocket[1], waitingAcc[0], waitingAcc[1]);
            waitingAcc[0] = null;
            waitingAcc[1] = null;
            waitingSocket[0] = null;
            waitingSocket[1] = null;
            waiting = 0;
        }
        notifyAll();
    }

    synchronized void removeQueue(Account acc)
    {
        while(waiting == 0)
        {
            try
            {
                wait();
            }
            catch(InterruptedException e)
            {
                e.printStackTrace();
            }
        }
        for(int i = 0; i < 2; i++)
        {
            if(waitingAcc[i].equals(acc))
            {
                waitingAcc[i] = null;
                waiting--;
            }
        }
        notifyAll();
    }

    public void preSettings(Socket s1, Socket s2, Account acc1, Account acc2)
    {
        int rand;
        Random random = new Random();
        rand = random.nextInt(2);
        AccountInGame account1 = new AccountInGame(acc1.getID(), rand);
        AccountInGame account2 = new AccountInGame(acc2.getID(), (rand+1)%2);
        new GameThread(s1, s2, account1, account2).start();
    }
}

class Account
{
    String ID;
    String password;
    String Himitsu;
    int[] wld = {0, 0, 0};// win lose draw

    public Account(String ID, String password, String Himitsu)
    {
        this.ID = ID;
        this.password = password;
        this.Himitsu = Himitsu;
    }

    // getter & setter
    public String getID()
    {
        return ID;
    }
    public String getPassword()
    {
        return password;
    }
    public String getHimitsu()
    {
        return Himitsu;
    }
    public double getWinRate()
    {
        return (double)wld[0]/(double)(wld[0]+wld[1]+wld[2]);
    }
    public int getWin()
    {
        return wld[0];
    }
    public int getLose()
    {
        return wld[1];
    }
    public int getDraw()
    {
        return wld[2];
    }
    public void setID(String ID)
    {
        this.ID = ID;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public void addWin()
    {
        wld[0]++;
    }
    public void addLose()
    {
        wld[1]++;
    }
    public void addDraw()
    {
        wld[2]++;
    }
}

class AccountInGame
{
    String ID;
    int color; // 0: black 1: white
    int[][] Pos = new int[8][8];
    public AccountInGame(String ID, int color)
    {
        this.ID = ID;
        this.color = color;
        if(color == 0) //black
        {
            Pos[3][4] = 0;
            Pos[4][3] = 0;
        }
        else //white
        {
            Pos[3][3] = 1;
            Pos[4][4] = 1;
        }
    }
    public String getID()
    {
        return ID;
    }
    public int getColor()
    {
        return color;
    }
    public int[][] getPos()
    {
        return Pos;
    }
}