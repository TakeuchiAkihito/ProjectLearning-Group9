import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.Timer;
import javax.swing.border.LineBorder;

public class Client extends JFrame implements ActionListener{
	int color;
	int rgb;
    JPanel cardPanel;
    CardLayout layout;
    String ID ="";
    String name_enemy;
    int win = 1,lose = 1,cast;
    JTextField id_input;
    Othello stone[][] = new Othello[10][10];
    JLabel uh_mes;
    JLabel aut_mes;
    JLabel turn;
    JLabel fault;
    JLabel fault2;
    JLabel label_late;
    JLabel label_win;
    JLabel label_lose;
    JLabel label_cast;
    JTextField id_renew_input ;
    JTextField sq_input;
    JTextField pw_input;
    JTextField id_new_input;
    JTextField pw_new_input;
    JTextField Q_sec_input;
    JTextField outcomeField;
    JButton button11;
    static Mul ta; 
    static Add tb; 
    static Socket socket;
    static PrintWriter output;
    static Scanner input;
    
   
    JButton[][] square = new JButton[8][8];
    Icon brackStone = new BrackStone();
    Icon whiteStone = new WhiteStone();
    Othello othello = new Othello();
   
    Timer tm;
	private JButton button12;
    
    public static void main(String[] args) {
    	try {
    		//socket = new Socket("127.0.0.1", 3838);
    		//output = new PrintWriter(socket.getOutputStream());
    		//input = new Scanner(socket.getInputStream());
    		Client frame = new Client("BorderLayoutDemo");
    		frame.setTitle("Othello");
            frame.setSize(310, 485);
            frame.setLocationRelativeTo(null);
            //frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
            frame.setVisible(true);
    		//ta = new Mul(); 
            //tb = new Add(); 
            //ta.start();
            //tb.start();
       }catch (Exception e) {
            e.printStackTrace();
            
       }
    }

    public Client(String title) {
    	super(title);

        // ログインホーム
        JPanel panel01 = new JPanel();
        panel01.setLayout(null);
        panel01.setPreferredSize(new Dimension(200, 100));
        panel01.setBackground(new Color(255,255,255));
        
        JLabel label=new JLabel("Othello");
        Font f = new Font("Serif", Font.PLAIN, 30);
        label.setFont(f);
        label.setBounds(105, 0, 300, 80);
        
        aut_mes =new JLabel("");
        aut_mes.setBounds(40, 250, 300, 80);
        
        JLabel id = new JLabel("ID");
        id.setBounds(10, 100, 300, 80);
        panel01.add(id);
        
        id_input = new JTextField(16);
        id_input.setBounds(170, 125, 120, 30);
        panel01.add(id_input);
        
        JLabel pw = new JLabel("パスワード");
        pw.setBounds(10, 150, 300, 80);
        panel01.add(pw);
        
        pw_input = new JTextField(16);
        pw_input.setBounds(170, 175, 120, 30);
        panel01.add(pw_input);
        
        JButton button1 = new JButton("ログイン");
        button1.setBounds(0, 220, 300, 50);
        button1.setBackground(Color.BLACK);
        button1.setForeground(Color.WHITE);
        button1.setActionCommand("A");
        button1.addActionListener(this);

        JButton button2 = new JButton("新規アカウント作成");
        button2.setBounds(0, 350, 300, 50);
        button2.setBackground(Color.BLACK);
        button2.setForeground(Color.WHITE);
        button2.setActionCommand("B");
        button2.addActionListener(this);

        JButton button3 = new JButton("パスワードを忘れた");
        button3.setBounds(0, 400, 300, 50);
        button3.setBackground(Color.BLACK);
        button3.setForeground(Color.WHITE);
        button3.setActionCommand("C");
        button3.addActionListener(this);

        panel01.add(label);
        panel01.add(aut_mes);
        panel01.add(button1);
        panel01.add(button2);
        panel01.add(button3);

        // 新規アカウント作成
        JPanel panel02 = new JPanel();
        panel02.setLayout(null);
        panel02.setPreferredSize(new Dimension(200, 100));
        panel02.setBackground(new Color(255,255,255));
        
        fault = new JLabel("");
        fault.setBounds(10, 500, 300, 80);
        panel02.add(fault);
        
        JLabel id_new = new JLabel("ID");
        id_new.setBounds(10, 100, 300, 80);
        panel02.add(id_new);
        
        id_new_input = new JTextField(16);
        id_new_input.setBounds(170, 125, 120, 30);
        panel02.add(id_new_input);
        
        JLabel pw_new = new JLabel("パスワード");
        pw_new.setBounds(10, 150, 300, 80);
        panel02.add(pw_new);
        
        pw_new_input = new JTextField(16);
        pw_new_input.setBounds(170, 175, 120, 30);
        panel02.add(pw_new_input);
        
        JLabel Q_sec = new JLabel("生年月日(例:1月1日は0101)");
        Q_sec.setBounds(10, 200, 300, 80);
        panel02.add(Q_sec);
        
        Q_sec_input = new JTextField(16);
        Q_sec_input.setBounds(170, 225, 120, 30);
        panel02.add(Q_sec_input);
        
        JButton button4 = new JButton("新規アカウント作成");
        button4.setBounds(0, 350, 300, 50);
        button4.setBackground(Color.BLACK);
        button4.setForeground(Color.WHITE);
        button4.setActionCommand("D");
        button4.addActionListener(this);
        panel02.add(button4);

        JButton button5 = new JButton("ホームに戻る");
        button5.setBounds(0, 400, 300, 50);
        button5.setBackground(Color.BLACK);
        button5.setForeground(Color.WHITE);
        button5.setActionCommand("D");
        button5.addActionListener(this);
        panel02.add(button5);
        
        // パスワード再設定認証画面
        JPanel panel03 = new JPanel();
        panel03.setLayout(null);
        panel03.setPreferredSize(new Dimension(200, 100));
        panel03.setBackground(new Color(255,255,255));
        
        fault2 = new JLabel("");
        fault2.setBounds(10, 500, 300, 80);
        panel03.add(fault2);
        
        JLabel id_renew = new JLabel("ID");
        id_renew.setBounds(10, 100, 300, 80);
        panel03.add(id_renew);
        
        id_renew_input = new JTextField(16);
        id_renew_input.setBounds(170, 125, 120, 30);
        panel03.add(id_renew_input);
        
        JLabel sq = new JLabel("秘密の質問");
        sq.setBounds(10, 150, 300, 80);
        panel03.add(sq);
        
        sq_input = new JTextField(16);
        sq_input.setBounds(170, 175, 120, 30);
        panel03.add(sq_input);
        
        JButton button6 = new JButton("パスワードを再設定する");
        button6.setBounds(0, 220, 300, 50);
        button6.setBackground(Color.BLACK);
        button6.setForeground(Color.WHITE);
        button6.setActionCommand("E");
        button6.addActionListener(this);
        panel03.add(button6);
        
        JButton button7 = new JButton("ホームに戻る");
        button7.setBounds(0, 400, 300, 50);
        button7.setBackground(Color.BLACK);
        button7.setForeground(Color.WHITE);
        button7.setActionCommand("D");
        button7.addActionListener(this);
        panel03.add(button7);
        
        // ユーザーホーム
        JPanel panel04 = new JPanel();
        panel04.setLayout(null);
        panel04.setPreferredSize(new Dimension(200, 100));
        panel04.setBackground(new Color(255,255,255));
        
        uh_mes = new JLabel("ようこそ"+ID+"さん");
        uh_mes.setBounds(10, 10, 300, 80);
        Font f2 = new Font("Serif", Font.PLAIN, 20);
        uh_mes.setFont(f2);
        panel04.add(uh_mes);
        
        JButton button8 = new JButton("対局する");
        button8.setBounds(0, 150, 300, 50);
        button8.setBackground(Color.BLACK);
        button8.setForeground(Color.WHITE);
        button8.setActionCommand("F");
        button8.addActionListener(this);
        panel04.add(button8);
        
        JButton button9 = new JButton("対局成績を見る");
        button9.setBounds(0, 250, 300, 50);
        button9.setBackground(Color.BLACK);
        button9.setForeground(Color.WHITE);
        button9.setActionCommand("G");
        button9.addActionListener(this);
        panel04.add(button9);
        
        JButton button10 = new JButton("ログアウト");
        button10.setBounds(0, 350, 300, 50);
        button10.setBackground(Color.BLACK);
        button10.setForeground(Color.WHITE);
        button10.setActionCommand("D");
        button10.addActionListener(this);
        panel04.add(button10);
        
     // 対局画面
        JPanel panel05 = new JPanel();
        panel05.setLayout(null);
        panel05.setPreferredSize(new Dimension(200, 100));
        panel05.setBackground(new Color(255,255,255));
        
        JPanel gp = new JPanel();
        
        gp.setLayout(new GridLayout(8, 8));
        for (int i = 0; i < square.length; i++) {
        	for(int j = 0; j < square.length; j++) {
        		square[i][j] = new JButton();
        		square[i][j].setBackground(Color.GREEN);
        		square[i][j].setActionCommand(""+((i+1)*10+(j+1)));
                square[i][j].setBorder(new LineBorder(Color.BLACK, 1, false));
                square[i][j].addActionListener(this);
                square[i][j].setEnabled(false);
            	gp.add(square[i][j]);
            	
        	}
        }   
        
        square[3][3].setIcon(whiteStone);
        square[4][4].setIcon(whiteStone);
        square[3][4].setIcon(brackStone);
        square[4][3].setIcon(brackStone);
        
        
        gp.setBackground(Color.BLACK);
        gp.setBounds(22, 10, 256, 256);
        //panel05.add(gp);
        
        turn = new JLabel("");
        turn.setBounds(110, 245, 300, 80);
        panel05.add(turn);
        
        JLabel enemy = new JLabel("対戦相手 : "+name_enemy);
        enemy.setBounds(110, 280, 300, 80);
        panel05.add(enemy);
        
        button11 = new JButton("投了する");
        button11.setBounds(0, 400, 300, 50);
        button11.setBackground(Color.BLACK);
        button11.setForeground(Color.WHITE);
        button11.setActionCommand("H");
        button11.addActionListener(this);
        panel05.add(button11);
        
        button12 = new JButton("パスする");
        button12.setBounds(0, 350, 300, 50);
        button12.setBackground(Color.BLACK);
        button12.setForeground(Color.WHITE);
        button12.setActionCommand("PATH");
        button12.addActionListener(this);
        button12.setEnabled(false);
        panel05.add(button12);
        
        panel05.add(gp);
        
     // 対局成績表示
        JPanel panel06 = new JPanel();
        panel06.setLayout(null);
        panel06.setPreferredSize(new Dimension(200, 100));
        panel06.setBackground(new Color(255,255,255));
        
        JLabel label6 = new JLabel(ID+"さんの対局成績");
        label6.setFont(f2);
        label6.setBounds(0, 0, 300, 80);
        panel06.add(label6);
        
        label_late = new JLabel("");
        label_late.setFont(f);
        label_late.setBounds(105, 100, 300, 80);
        panel06.add(label_late);
        
        label_win = new JLabel("");
        label_win.setFont(f);
        label_win.setBounds(105, 150, 300, 80);
        panel06.add(label_win);
        
        label_lose = new JLabel("");
        label_lose.setFont(f);
        label_lose.setBounds(105, 200, 300, 80);
        panel06.add(label_lose);
        
        label_cast = new JLabel("");
        label_cast.setFont(f);
        label_cast.setBounds(105, 250, 300, 80);
        panel06.add(label_cast);
        
        JButton button12 = new JButton("戻る");
        button12.setBounds(0, 350, 300, 50);
        button12.setBackground(Color.BLACK);
        button12.setForeground(Color.WHITE);
        button12.setActionCommand("I");
        button12.addActionListener(this);
        panel06.add(button12);
        
      //パスワード再設定
        JPanel panel07 = new JPanel();
        panel07.setLayout(null);
        panel07.setPreferredSize(new Dimension(200, 100));
        panel07.setBackground(new Color(255,255,255));
        
        JLabel pwnew = new JLabel("新しいパスワード");
        pwnew.setBounds(10, 150, 300, 80);
        panel07.add(pwnew);
        
        JPasswordField pw_input_re = new JPasswordField(16);
        pw_input_re.setBounds(170, 175, 120, 30);
        panel07.add(pw_input_re);
        
        JButton button13 = new JButton("確定");
        button13.setBounds(0, 220, 300, 50);
        button13.setBackground(Color.BLACK);
        button13.setForeground(Color.WHITE);
        button13.setActionCommand("D");
        button13.addActionListener(this);
        panel07.add(button13);
        
        // CardLayout用パネル
        cardPanel = new JPanel();
        layout = new CardLayout();
        cardPanel.setLayout(layout);

        cardPanel.add(panel01, "1");
        cardPanel.add(panel02, "2");
        cardPanel.add(panel03, "3");
        cardPanel.add(panel04, "4");
        cardPanel.add(panel05, "5");
        cardPanel.add(panel06, "6");
        cardPanel.add(panel07, "7");

        // cardPanelとカード移動用ボタンをJFrameに配置
        Container contentPane = getContentPane();
        contentPane.add(cardPanel, BorderLayout.CENTER);
        
        tm = new Timer(3000, this);
        tm.setActionCommand("timer");
        
        
    }
    
    
    public void actionPerformed(ActionEvent e) {
    	try {
    	int locate_x,locate_y;
    	String cmd_enemy;
    	//JButton b = (JButton) e.getSource(); 
        String cmd = e.getActionCommand();
        ID = id_input.getText();
        
        switch(cmd) {
        case "A":
        	try {
				socket = new Socket("127.0.0.1", 3838);
				output = new PrintWriter(socket.getOutputStream());
	    		input = new Scanner(socket.getInputStream());
			} catch (UnknownHostException e1) {
				// TODO 自動生成された catch ブロック
				e1.printStackTrace();
			} catch (IOException e1) {
				// TODO 自動生成された catch ブロック
				e1.printStackTrace();
			}
        	layout.show(cardPanel, "4");
        	if(authentication(id_input.getText(),pw_input.getText())) {
        		uh_mes.setText("ようこそ"+ID+"さん");
        		layout.show(cardPanel, "4");
        	}else {
        		aut_mes.setText("IDまたはパスワードにエラーがあります");
    			layout.show(cardPanel, "1");
        	}
        	break;
        case "B":
        	layout.show(cardPanel, "2");
            break;
        	
        case "C":
        	layout.show(cardPanel, "3");
            break;
        	
        case "D":
        	if(reset(id_new_input.getText(),pw_new_input.getText(),Q_sec_input.getText())) {
        		layout.show(cardPanel, "1");
        	}else {
        		fault.setText("新規アカウントの作成に失敗しました");
        		layout.show(cardPanel, "2");
        	}
            break;
        	
        case "E":
        	if(re_authentication(id_renew_input.getText(),sq_input.getText())) {
        		layout.show(cardPanel, "7");
        	}else {
        		fault2.setText("ユーザー名または秘密の質問の解答が正しくありません");
        		layout.show(cardPanel, "3");
        	}
            break;
        	
        case "F":   //first:先攻  rear:後攻
        	String atk = search();
        	layout.show(cardPanel, "5");
        
        	if(atk.equals(Othello.FIRST)) {
        		// othelloに手番情報を伝達
        		othello.setColor(Othello.BRACK, Othello.WHITE);
        		othello.judge();
        		// 画面を更新
        		updateBoard(othello.getBoardState());
        		turn.setText("あなたの番です");
        		button11.setEnabled(true);
        		squareChange(true, othello.getBoardState());
        	}else {
        		// othelloに手番情報を伝達
        		othello.setColor(Othello.WHITE, Othello.BRACK);
        		// 画面を更新
        		turn.setText("相手の番です");
        		button11.setEnabled(false);
        		
        		// 相手の操作情報を受信
        		//cmd_enemy = input.nextLine();
        		cmd_enemy = "";
        		if(cmd_enemy.equals(Othello.RETIRE)) {
            		layout.show(cardPanel, "4");
            	}
        		else {
        			// ボタンの位置情報への変換
	        	    locate_x = Integer.parseInt(cmd_enemy)/10;
	            	locate_y = Integer.parseInt(cmd_enemy)%10;
	            	// 石の反転
	            	othello.reverse(Othello.WHITE,locate_x,locate_y);
	            	// 画面を更新
	            	layout.show(cardPanel, "5");
	            	othello.judge();
	        		updateBoard(othello.getBoardState());
	        		turn.setText("あなたの番です");
	        		squareChange(true, othello.getBoardState());
        		}	
        		
        	}
            break;
        	
        case "G":
        	result();
        	layout.show(cardPanel, "6");
            break;
        	
        case "H":
        	//output.println("retire");
        	layout.show(cardPanel, "4");
            break;
            
        case "I":
        	layout.show(cardPanel, "4");
            break;
        case "PATH":
        	output.println(cmd);
        	output.flush();
        	cmd_enemy = input.nextLine();
    		readServer(cmd_enemy);
    		break;
        	
            
        default:
        	if( e.getSource() == tm ) {
        		cmd_enemy = input.nextLine();
        		readServer(cmd_enemy);
        		tm.stop();
        	}else {
        		
            	// ボタンの位置情報への変換
            	locate_x = Integer.parseInt(cmd)/10;
            	locate_y = Integer.parseInt(cmd)%10;
            	// 石の反転
                othello.reverse(othello.getMyColor(),locate_x,locate_y);
                // 画面を更新
            	layout.show(cardPanel, "5");
            	updateBoard(othello.getBoardState());
            	button11.setEnabled(false);
            	squareChange(false, othello.getBoardState());
            	turn.setText("相手の番です");
            	
            	// サーバに操作情報を送信
            	output.println(cmd);
            	output.flush();
            	
            	// サーバから操作情報を受信
            	//cmd_enemy = input.nextLine();
            	tm.start();
        	}
        }
    	}catch(NoSuchElementException e1) {
    		layout.show(cardPanel, "1");
    		System.exit(0);
    	}
            	
            	
    }
    
    public void readServer(String cmd_enemy) {
    	int locate_x;
    	int locate_y;
    
    	if(cmd_enemy.equals(Othello.RETIRE)) {
    		layout.show(cardPanel, "4");
    	}else {
        	if(cmd_enemy.equals(Othello.PASS)) {
            	if(othello.judge() != 0) {
            		layout.show(cardPanel, "5");
            		turn.setText("あなたの番です");
                	squareChange(true, othello.getBoardState());
            			
            	}else {
            		turn.setText(othello.getOutcome());
            		try {
						TimeUnit.SECONDS.sleep(5);
					} catch (InterruptedException e) {
						// TODO 自動生成された catch ブロック
						e.printStackTrace();
					}
            		layout.show(cardPanel, "4");
            			
            	}
            		
            }else {
            	locate_x = Integer.parseInt(cmd_enemy)/10;
                locate_y = Integer.parseInt(cmd_enemy)%10;
                othello.reverse(othello.getEnemyColor(),locate_x,locate_y);
                if( othello.judge() == 0 ) {
                	button12.setEnabled(true);
                }else {
                	button12.setEnabled(false);
                }
                layout.show(cardPanel, "5");
                turn.setText("あなたの番です");
            	squareChange(true, othello.getBoardState());
            	updateBoard(othello.getBoardState());   	
                	
            }
        	
    	}
		
    }
    
    public boolean authentication(String ID, String PW) {
    	//output.println("1");
    	System.out.println(1);
    	output.println(ID);
    	output.flush();
    	output.println(PW);
    	output.flush();
    	String response = input.nextLine();
    	System.out.println(response);
    	return response.equals("1");
    }
    
    public boolean reset(String ID, String PW, String Q_sec) {
    	//output.println("2");
    	//output.println(ID);
    	//output.println(PW);
    	//output.println(Q_sec);
    	String response = "";//input.nextLine();
    	return response.equals("1");
    }
    
    public String  search() { //first:先攻  rear:後攻
    	//output.println("3");
    	output.println(ID);
    	output.flush();
    	String response = input.nextLine();
    	return response;
    }
    
    public void  result() {
    	//output.println("4");
    	//int win = Integer.parseInt(input.nextLine());
    	//int lose = Integer.parseInt(input.nextLine());
    	//int cast = Integer.parseInt(input.nextLine());
    	double late = (double)win/lose;
    	label_win.setText("勝ち数  "+win);
    	label_lose.setText("負け数  "+lose);
    	label_cast.setText("投了数  "+cast);
    	label_late.setText("勝率  "+late+"%");
    }
    
    public boolean re_authentication(String ID, String Q_sec) {
    	//output.println("5");
    	//output.println(ID);
    	//output.println(Q_sec);
    	String response = "";//input.nextLine();
    	return response.equals("1");
    }
    
    public void updateBoard(int[][] boardState) {
    	for(int i = 1; i < boardState.length-1; i++) {
    		for(int j = 1; j < boardState[i].length-1; j++) {
    			if(boardState[i][j] == Othello.BRACK) {
    				square[i-1][j-1].setBackground(Color.GREEN);
    				square[i-1][j-1].setIcon(brackStone);
    			}else if(boardState[i][j] == Othello.WHITE) {
    				square[i-1][j-1].setBackground(Color.GREEN);
    				square[i-1][j-1].setIcon(whiteStone);
    			}else if(boardState[i][j] == Othello.EMPTY) {
    				square[i-1][j-1].setBackground(Color.GREEN);
    			}else if(boardState[i][j] == Othello.OK) {
    				square[i-1][j-1].setBackground(Color.RED);
    			}
    		}
    	}
    }
    
    public void squareChange(boolean flag, int[][] board) {
    	if( !flag ) {
	    	for(int i = 0; i < square.length; i++) {
	    		for(int j = 0; j < square[i].length; j++) {
	    			square[i][j].setEnabled(flag);
	    		}
	    	}
    	}else {
    		for(int i = 0; i < square.length; i++) {
	    		for(int j = 0; j < square[i].length; j++) {
	    			if( board[i+1][j+1] == Othello.OK ) {
	    				square[i][j].setEnabled(flag);
	    			}
	    		}
	    	}
    	}
	    	
    }
    
    
}
    
    
class Mul extends Thread {
    static int n = 1, m = 1;
    //String server = "127.0.0.1";
    //int port;
    Client frame = new Client("BorderLayoutDemo");
    //Socket socket;
    //PrintWriter output;
    //Scanner input;
    public void run() {
        try {
        	//socket = new Socket("127.0.0.1", 3838);
            //output = new PrintWriter(socket.getOutputStream());
        	//input = new Scanner(socket.getInputStream());
            frame.setTitle("Othello");
            frame.setSize(310, 485);
            frame.setLocationRelativeTo(null);
            //frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
            frame.setVisible(true);
        }
        catch(Exception ex) {
            ex.printStackTrace();
        }
    }
}

class Add extends Thread {
	static int n = 0, m = 1;

    public void run() {
        try {
            while(m<=10) {
                Thread.sleep(1000); 
            	n = n + m;
            	m++;
                //System.out.println("Add : "+n);
            }
        }
        catch(Exception ex) {
            ex.printStackTrace();
        }
        finally {
        }
    }
}


class Othello{
	
	public static final int BRACK = 1;
	public static final int WHITE = 0;
	public static final int EMPTY = 2;
	public static final int OUTER = -1;
	public static final int OK = -2;
	public static final String FIRST = "first";
	public static final String REAR = "rear";
	public static final String RETIRE = "retire";
	public static final String PASS = "pass";
	public static final String WIN = "勝ち";
	public static final String LOSE = "負け";
	public static final String DRAW = "引き分け";
	
	int[][] board = new int[10][10];
	int myColor;
	int enemyColor;
	int judgeResult = 0;
	
	Othello(){
		boardClear();
	}
	
	public void setColor(int myColor, int enemyColor) {
		this.myColor = myColor;
		this.enemyColor = enemyColor;
	}
	
	public int getMyColor() {
		return myColor;
	}
	
	public int getEnemyColor() {
		return enemyColor;
	}
	
	public void boardClear() {
		for(int i=0; i<board.length; i++) {
			for(int j = 0; j < board[i].length; j++) {
				if(i == 0 || i == 9 || j == 0 || j == 9 ) {
					board[i][j] = OUTER;
				}else if( (i==4 && j == 4) || (i==5 && j==5)) {
					board[i][j] = WHITE;
				}else if( (i==4 && j==5) || (i==5 && j==4)) {
					board[i][j] = BRACK;
				}else {
					board[i][j] = EMPTY;
				}
			}
		}
	}
	
	public int[][] getBoardState() {
		return board;
	}

	
	public void reverse(int color, int i, int j) {
		System.out.print(i + ", " + j);
    	System.out.println();
    	
    	
    	if(color+board[i+1][j] == 1) {
    		int xy = 2;
    		while(board[i+xy][j] != -1) {
    			if(color == board[i+xy][j]) {
    				for(int l = 1; l < xy; l++) {
    					board[i+l][j] = color;
    				}
    				break;
    			}
    			xy++;
    		}
    		
    	}
        
    	if(color+board[i-1][j] == 1) {
    		int xy = 2;
    		while(board[i-xy][j] != -1) {
    			if(color == board[i-xy][j]) {
    				for(int l = 1; l < xy; l++) {
    					board[i-l][j] = color;
    				}
    				break;
    			}
    			xy++;
    		}
    	}
    	
        if(color+board[i][j+1] == 1) {
        	int xy = 2;
    		while(board[i][j+xy] != -1) {
    			if(color == board[i][j+xy]) {
    				for(int l = 1; l < xy; l++) {
    					board[i][j+l] = color;
    				}
    				break;
    			}
    			xy++;
    		}
    	}
        
        if(color+board[i][j-1] == 1) {
        	int xy = 2;
    		while(board[i][j-xy] != -1) {
    			if(color == board[i][j-xy]) {
    				for(int l = 1; l < xy; l++) {
    					board[i][j-l] = color;
    				}
    				break;
    			}
    			xy++;
    		}
    	}
        
        if(color+board[i+1][j+1] == 1) {
        	int xy = 2;
    		while(board[i+xy][j+xy] != -1) {
    			if(color == board[i+xy][j+xy]) {
    				for(int l = 1; l < xy; l++) {
    					board[i+l][j+l] = color;
    				}
    				break;
    			}
    			xy++;
    		}
    	}
        
        if(color+board[i+1][j-1] == 1) {
        	int xy = 2;
    		while(board[i+xy][j-xy] != -1) {
    			if(color == board[i+xy][j-xy]) {
    				for(int l = 1; l < xy; l++) {
    					board[i+l][j-l] = color;
    				}
    				break;
    			}
    			xy++;
    		}
    	}
        
        if(color+board[i-1][j+1] == 1) {
        	int xy = 2;
    		while(board[i-xy][j+xy] != -1) {
    			if(color == board[i-xy][j+xy]) {
    				for(int l = 1; l < xy; l++) {
    					board[i-l][j+l] = color;
    				}
    				break;
    			}
    			xy++;
    		}
    	}
        
        if(color+board[i-1][j-1] == 1) {
        	int xy = 2;
    		while(board[i-xy][j-xy] != -1) {
    			if(color == board[i-xy][j-xy]) {
    				for(int l = 1; l < xy; l++) {
    					board[i-l][j-l] = color;
    				}
    				break;
    			}
    			xy++;
    		}
		}
        board[i][j] = color;
        
    	    	
    	if( color == myColor) {
    		redMarkClear();
    	}
        
        	
	}

	
	public int judge() {
    	for(int i = 1; i < board.length-1; i++) {
    		for(int j = 1; j < board.length-1; j++) {
    	    	if(board[i][j] == 2) {
    	    		if(myColor+board[i+1][j] == 1) {
        	    		int xy = 2;
        	    		while(board[i+xy][j] != -1) {
        	    			if(myColor == board[i+xy][j]) {
        	    				board[i][j] = OK;
        	    				judgeResult++;
        	    				break;
        	    			}
        	    			xy++;
        	    		}
        	    		
        	    	}
                    
        	    	if(myColor+board[i-1][j] == 1) {
        	    		int xy = 2;
        	    		while(board[i-xy][j] != -1) {
        	    			if(myColor == board[i-xy][j]) {
        	    				board[i][j] = OK;
        	    				judgeResult++;
        	    				break;
        	    			}
        	    			xy++;
        	    		}
        	    	}
        	    	
                    if(myColor+board[i][j+1] == 1) {
                    	int xy = 2;
        	    		while(board[i][j+xy] != -1) {
        	    			if(myColor == board[i][j+xy]) {
        	    				board[i][j] = OK;
        	    				judgeResult++;
        	    				break;
        	    			}
        	    			xy++;
        	    		}
        	    	}
                    
                    if(myColor+board[i][j-1] == 1) {
                    	int xy = 2;
        	    		while(board[i][j-xy] != -1) {
        	    			if(myColor == board[i][j-xy]) {
        	    				board[i][j] = OK;
        	    				judgeResult++;
        	    				break;
        	    			}
        	    			xy++;
        	    		}
        	    	}
                    
                    if(myColor+board[i+1][j+1] == 1) {
                    	int xy = 2;
        	    		while(board[i+xy][j+xy] != -1) {
        	    			if(myColor == board[i+xy][j+xy]) {
        	    				board[i][j] = OK;
        	    				judgeResult++;
        	    				break;
        	    			}
        	    			xy++;
        	    		}
        	    	}
                    
                    if(myColor+board[i+1][j-1] == 1) {
                    	int xy = 2;
        	    		while(board[i+xy][j-xy] != -1) {
        	    			if(myColor == board[i+xy][j-xy]) {
        	    				board[i][j] = OK;
        	    				judgeResult++;
        	    				break;
        	    			}
        	    			xy++;
        	    		}
        	    	}
                    
                    if(myColor+board[i-1][j+1] == 1) {
                    	int xy = 2;
        	    		while(board[i-xy][j+xy] != -1) {
        	    			if(myColor == board[i-xy][j+xy]) {
        	    				board[i][j] = OK;
        	    				judgeResult++;
        	    				break;
        	    			}
        	    			xy++;
        	    		}
        	    	}
                    
                    if(myColor+board[i-1][j-1] == 1) {
                    	int xy = 2;
        	    		while(board[i-xy][j-xy] != -1) {
        	    			if(myColor == board[i-xy][j-xy]) {
        	    				board[i][j] = OK;
        	    				judgeResult++;
        	    				break;
        	    			}
        	    			xy++;
        	    		}
    	    		}
    	    	}
            }	
        }
    	
    	return judgeResult;
    	
	}
	
	
	public int getJudgeResult() {
		return judgeResult;
	}
	
	public void redMarkClear() {
		for(int i = 1; i < board.length-1; i++) {
			for(int j = 0; j < board[i].length-1; j++ ) {
				if(board[i][j] == OK) {
					board[i][j] = EMPTY;
				}
			}
		}
	}
	
	public String getOutcome() {
		int myCount = 0;
		int enemyCount = 0;
		String outcome;
		
		for(int i = 1; i <= 8; i++) {
			for(int j = 1; j <= 8; j++) {
				if(board[i][j] == myColor) {
					myCount++;
				}else if(board[i][j] == enemyColor) {
					enemyCount++;
				}
			}
		}
		
		if(myCount > enemyCount ) {
			outcome = Othello.WIN;
		}else if(myCount < enemyCount) {
			outcome = Othello.LOSE;
		}else {
			outcome = Othello.DRAW;
		}
		
		return outcome;
	}
	
		
}

class WhiteStone implements Icon{
	
	@Override
	public void paintIcon(Component c, Graphics g, int x, int y) {
		g.setColor(Color.WHITE);
		g.fillOval(1, 1, 28, 28);
		
	}

	@Override
	public int getIconWidth() {
		
		return 28;
	}

	@Override
	public int getIconHeight() {
		return 28;
	}
	
}

class BrackStone implements Icon{

	@Override
	public void paintIcon(Component c, Graphics g, int x, int y) {
		g.setColor(Color.BLACK);
		g.fillOval(1, 1, 28, 28);
		
	}

	@Override
	public int getIconWidth() {
		return 28;
	}

	@Override
	public int getIconHeight() {
		return 28;
	}
	
}

