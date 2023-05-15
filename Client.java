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
import java.io.PrintWriter;
import java.net.Socket;
import java.util.NoSuchElementException;
import java.util.Scanner;

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
    JLabel enemyLabel;
    JTextField id_renew_input ;
    JTextField sq_input;
    JPasswordField pw_input;
    JTextField id_new_input;
    JTextField pw_new_input;
    JTextField Q_sec_input;
    JLabel outcomeLabel;
    JButton button11;
    JButton button12;
    JButton[][] square = new JButton[8][8];
    Icon brackStone = new BrackStone();
    Icon whiteStone = new WhiteStone();
    Icon defaultIcon = new DefaultIcon();
    Icon redIcon = new RedIcon();
    Othello othello = new Othello();;
    Timer tm2;
    SaThread gameComThread;
   
    static Socket socket;
    static PrintWriter output;
    static Scanner input;
	static Client frame;
	
    public static void main(String[] args) {
    	
    	
    	try {
			socket = new Socket("127.0.0.1", 3838);
			output = new PrintWriter(socket.getOutputStream());
    		input = new Scanner(socket.getInputStream());
    		
			frame = new Client("BorderLayoutDemo");
			frame.setTitle("Othello");
	        frame.setSize(310, 485);
	        frame.setLocationRelativeTo(null);
	        frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
	        frame.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(0);
		}
    }

    public Client(String title)throws Exception {
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
        
        pw_input = new JPasswordField(16);
        pw_input.setBounds(170, 175, 120, 30);
        panel01.add(pw_input);
        
        JButton button1 = new JButton("ログイン");
        button1.setBounds(0, 220, 300, 50);
        button1.setBackground(Color.BLACK);
        button1.setForeground(Color.WHITE);
        button1.setActionCommand("LoginPage");
        button1.addActionListener(this);

        JButton button2 = new JButton("新規アカウント作成");
        button2.setBounds(0, 350, 300, 50);
        button2.setBackground(Color.BLACK);
        button2.setForeground(Color.WHITE);
        button2.setActionCommand("NewAccountPage");
        button2.addActionListener(this);

        JButton button3 = new JButton("パスワードを忘れた");
        button3.setBounds(0, 400, 300, 50);
        button3.setBackground(Color.BLACK);
        button3.setForeground(Color.WHITE);
        button3.setActionCommand("ForgetPassword");
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
        button4.setActionCommand("MakeAccount");
        button4.addActionListener(this);
        panel02.add(button4);

        JButton button5 = new JButton("ホームに戻る");
        button5.setBounds(0, 400, 300, 50);
        button5.setBackground(Color.BLACK);
        button5.setForeground(Color.WHITE);
        button5.setActionCommand("BackHome");
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
        
        JLabel sq = new JLabel("生年月日(例:1月1日は0101)");
        sq.setBounds(10, 150, 300, 80);
        panel03.add(sq);
        
        sq_input = new JTextField(16);
        sq_input.setBounds(170, 175, 120, 30);
        panel03.add(sq_input);
        
        JButton button6 = new JButton("パスワードを再設定する");
        button6.setBounds(0, 220, 300, 50);
        button6.setBackground(Color.BLACK);
        button6.setForeground(Color.WHITE);
        button6.setActionCommand("ResetPassword");
        button6.addActionListener(this);
        panel03.add(button6);
        
        JButton button7 = new JButton("ホームに戻る");
        button7.setBounds(0, 400, 300, 50);
        button7.setBackground(Color.BLACK);
        button7.setForeground(Color.WHITE);
        button7.setActionCommand("BackHome");
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
        button8.setActionCommand("START");
        button8.addActionListener(this);
        panel04.add(button8);
        
        JButton button9 = new JButton("対局成績を見る");
        button9.setBounds(0, 250, 300, 50);
        button9.setBackground(Color.BLACK);
        button9.setForeground(Color.WHITE);
        button9.setActionCommand("CheckRecord");
        button9.addActionListener(this);
        panel04.add(button9);
        
        JButton button10 = new JButton("ログアウト");
        button10.setBounds(0, 350, 300, 50);
        button10.setBackground(Color.BLACK);
        button10.setForeground(Color.WHITE);
        button10.setActionCommand("BackHome");
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
        
        enemyLabel = new JLabel("対戦相手 : "+name_enemy);
        enemyLabel.setBounds(110, 280, 300, 80);
        panel05.add(enemyLabel);
        
        button11 = new JButton("投了する");
        button11.setBounds(0, 400, 300, 50);
        button11.setBackground(Color.BLACK);
        button11.setForeground(Color.WHITE);
        button11.setActionCommand(Othello.RETIRE);
        button11.addActionListener(this);
        button11.setEnabled(false);
        panel05.add(button11);
        
        button12 = new JButton("パスする");
        button12.setBounds(0, 350, 300, 50);
        button12.setBackground(Color.BLACK);
        button12.setForeground(Color.WHITE);
        button12.setActionCommand(Othello.PASS);
        button12.addActionListener(this);
        button12.setEnabled(false);
        panel05.add(button12);
        
        outcomeLabel = new JLabel("");
        outcomeLabel.setFont(new Font(Font.DIALOG, Font.BOLD, 60));
        outcomeLabel.setOpaque(false);
        outcomeLabel.setForeground(Color.RED);
        outcomeLabel.setBounds(0, 50, 300, 200);
        outcomeLabel.setHorizontalAlignment(JLabel.CENTER);
        panel05.add(outcomeLabel);
        
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
        button12.setActionCommand("BackUserPage");
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
        
        tm2 = new Timer(5000, this);
        tm2.setActionCommand("GameEnd");
        tm2.setRepeats(false);
       
    }
    
    
    public void actionPerformed(ActionEvent e) {
    	
    	try {
    	int locate_x,locate_y;
        String cmd = e.getActionCommand();
        ID = id_input.getText();
        String outcome;
        
        switch(cmd) {
        case "LoginPage":
        	if(authentication(id_input.getText(),String.valueOf(pw_input.getPassword()))) {
        		uh_mes.setText("ようこそ "+ID+"さん");
        		layout.show(cardPanel, "4");
        	}else {
        		aut_mes.setText("IDまたはパスワードにエラーがあります");
    			layout.show(cardPanel, "1");
        	}
        	break;
        case "NewAccountPage":
        	layout.show(cardPanel, "2");
            break;
        	
        case "ForgetPassword":
        	layout.show(cardPanel, "3");
            break;
        	
        case "MakeAccount":
        	if(reset(id_new_input.getText(),pw_new_input.getText(),Q_sec_input.getText())) {
        		layout.show(cardPanel, "1");
        	}else {
        		fault.setText("新規アカウントの作成に失敗しました");
        		layout.show(cardPanel, "2");
        	}
            break;
        	
        case "ResetPassword":
        	if(re_authentication(id_renew_input.getText(),sq_input.getText())) {
        		layout.show(cardPanel, "7");
        	}else {
        		fault2.setText("ユーザー名または秘密の質問の解答が正しくありません");
        		layout.show(cardPanel, "3");
        	}
            break;
            
        case "BackHome":
        	layout.show(cardPanel, "1");
        	
        	break;
        	    
        case "CheckRecord":
        	result();
        	layout.show(cardPanel, "6");
            break;
            
        case "BackUserPage":
        	layout.show(cardPanel, "4");
            break;
            
        	    	
        case "START":   //first:先攻  rear:後攻
        	
        	String atk = search();
        	
            layout.show(cardPanel, "5");
            // 手番情報の判定
            if(atk.equals(Othello.FIRST)) {
            	// othelloに手番情報を伝達
            	othello.setColor(Othello.BRACK, Othello.WHITE);
            	othello.judge(othello.getMyColor());
            	// 画面を更新
            	updateBoard(othello.getMyColor());	
            }else {
            	// othelloに手番情報を伝達
            	othello.setColor(Othello.WHITE, Othello.BRACK);
            	// 画面を更新
            	turn.setText("相手の番です");
            	
            	// 相手のターン
            	//(new SaThread(frame, othello, input)).start();	
            	gameComThread = new SaThread(frame, othello, input);
            	gameComThread.start();
            }
            
            break;
            
        
        case Othello.RETIRE:
        	
        	//output.println("retire");
        	sendToOutcome(Othello.RETIRE);
        	outcomeLabel.setForeground(Color.BLUE);
        	outcomeLabel.setText("敗北");
        	tm2.start();
            break;
            
            
        case Othello.PASS:
        	// 相手がパスした直後かを判定
        	if( othello.getPassCheck() ) {
        		// パスした直後であればパスした旨と勝敗判定をサーバに送信
        		outcome = othello.getOutcome();
        		sendToCoordinate(cmd);
        		sendToOutcome(outcome);
        		// 勝敗メッセージを表示
        		if( outcome.equals(Othello.WIN)) {
            		outcomeLabel.setForeground(Color.RED);
            		outcomeLabel.setText("勝利");
            	}else if( outcome.equals(Othello.LOSE) ) {
            		outcomeLabel.setForeground(Color.BLUE);
            		outcomeLabel.setText("敗北");
            	}else {
            		outcomeLabel.setForeground(Color.RED);
            		outcomeLabel.setText("引き分け");
            	}
            	// ユーザが勝敗メッセージを確認するための遅延を入れて画面遷移
            	tm2.start();
        	}else {
        		// パスしたことを記録
        		othello.setPassCheck(true);
        		// パスした旨をサーバに送信
            	sendToCoordinate(Othello.PASS);
            	// 画面を更新
            	updateBoard(othello.getEnemyColor());
            	// 相手のターンの処理を開始
            	//(new SaThread(frame, othello, input)).start();
            	gameComThread = new SaThread(frame, othello, input);
            	gameComThread.start();
        	}
        	
    		break;
    		
        case "GameEnd":
        	tm2.stop();
        	gameComThread.join();
        	othello.boardClear();
        	squareClear();
        	layout.show(cardPanel, "4");
        	break;
    		
    	default:
    		
    		// パスフラグをfalseにする
    		othello.setPassCheck(false);
    		// ボタンの位置情報への変換
            locate_x = Integer.parseInt(cmd)/10;
            locate_y = Integer.parseInt(cmd)%10;
            // 石の反転
            othello.reverse(othello.getMyColor(),locate_x,locate_y);
            // 画面の更新
            updateBoard(othello.getEnemyColor());
            // 対局終了判定
            if( othello.judge(othello.getEnemyColor()) < 0) {
            	outcome = othello.getOutcome();
            	// 対局終了したらボタンの座標値と勝敗結果をサーバに送信
            	sendToCoordinate(cmd);
            	sendToOutcome(othello.getOutcome());
            	// 勝敗メッセージを表示
            	if( outcome.equals(Othello.WIN)) {
            		outcomeLabel.setForeground(Color.RED);
            		outcomeLabel.setText("勝利");
            	}else if( outcome.equals(Othello.LOSE) ) {
            		outcomeLabel.setForeground(Color.BLUE);
            		outcomeLabel.setText("敗北");
            	}else {
            		outcomeLabel.setForeground(Color.RED);
            		outcomeLabel.setText("引き分け");
            	}
            	// ユーザが勝敗メッセージを確認するための遅延を入れて画面遷移
            	tm2.start();
            }else {
            	//サーバに操作情報を送信
                sendToCoordinate(cmd);
                // 相手のターンの処理を開始
                //(new SaThread(frame, othello, input)).start();
                gameComThread = new SaThread(frame, othello, input);
            	gameComThread.start();
                
            }
            
        }
        
    	}catch(NoSuchElementException e1) {
    		layout.show(cardPanel, "1");
    	    
    		System.exit(0);
    		
    	} catch (InterruptedException e1) {
			// TODO 自動生成された catch ブロック
			e1.printStackTrace();
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
    	output.println("2");
    	output.println(ID);
    	output.println(PW);
    	output.println(Q_sec);
    	String response = input.nextLine();
    	return response.equals("1");
    }
    
    public String  search() { //first:先攻  rear:後攻
    	output.println("3");
    	output.println(ID);
    	output.flush();
    	String response = input.nextLine();
    	name_enemy = input.nextLine();
    	enemyLabel.setText("対戦相手 : "+name_enemy);
    	return response;
    }
    
    public void  result() {
    	output.println("4");
    	int win = Integer.parseInt(input.nextLine());
    	int lose = Integer.parseInt(input.nextLine());
    	int cast = Integer.parseInt(input.nextLine());
    	double late = (double)win/(win+lose+cast);
    	label_win.setText("勝ち数  "+win);
    	label_lose.setText("負け数  "+lose);
    	label_cast.setText("投了数  "+cast);
    	label_late.setText("勝率  "+late+" %");
    }
    
    public boolean re_authentication(String ID, String Q_sec) {
    	output.println("5");
    	output.println(ID);
    	output.println(Q_sec);
    	String response = input.nextLine();
    	return response.equals("1");
    }
    
    public void updateBoard(int nextTurn) {
    	int[][] boardState = othello.getBoardState();
    	boolean flag = false;
    	
    	if(nextTurn == othello.getMyColor()) {
    		flag = true;
    	}
    	
	    for(int i = 1; i < boardState.length-1; i++) {
	    	for(int j = 1; j < boardState[i].length-1; j++) {
	    		if(boardState[i][j] == Othello.BRACK) {
	    			//square[i-1][j-1].setBackground(Color.GREEN);
	    			square[i-1][j-1].setIcon(brackStone);
	    			square[i-1][j-1].setEnabled(false);
	    		}else if(boardState[i][j] == Othello.WHITE) {
	    			//square[i-1][j-1].setBackground(Color.GREEN);
	    			square[i-1][j-1].setIcon(whiteStone);
	    			square[i-1][j-1].setEnabled(false);
	    		}else if(boardState[i][j] == Othello.EMPTY) {
	    			//square[i-1][j-1].setBackground(Color.GREEN);
	    			square[i-1][j-1].setIcon(defaultIcon);
	    			square[i-1][j-1].setEnabled(false);
	    		}else if(boardState[i][j] == Othello.OK) {
	    			//square[i-1][j-1].setBackground(Color.RED);
	    			square[i-1][j-1].setIcon(redIcon);
	    			square[i-1][j-1].setEnabled(flag);
	    		}
	    	}
	    }
    	
    	
    	if(nextTurn == othello.getMyColor()) {
    		turn.setText("あなたの番です");
        	button11.setEnabled(true);
        	
    	}else if(nextTurn == othello.getEnemyColor()) {
    		turn.setText("相手の番です");
        	button11.setEnabled(false);
        	button12.setEnabled(false);
        	
    	}
    			
    }
    
    public void sendToOutcome(String message) {
    	if(message.equals(Othello.RETIRE)|| message.equals(Othello.LOSE) || message.equals(Othello.WIN) || message.equals(Othello.DRAW)){
    		output.println(message);
        	output.flush();
        	//output.println(ID);
        	//output.flush();
        	//output.println(Othello.LOSE);
        	//output.flush();
    	}
    }
    
    public void sendToCoordinate(String cmd) {
    	output.println(cmd);
    	output.flush();
    }
    
    public void enemyProcess(String cmd_enemy) throws InterruptedException,NoSuchElementException{
    	int locate_x;
    	int locate_y;
    	int judgeCount;
    	String outcome;
    
    	if(cmd_enemy.equals(Othello.RETIRE)) {
    		outcomeLabel.setForeground(Color.RED);
    		outcomeLabel.setText("勝利");
    		tm2.start();
    		
    	}else if(cmd_enemy.equals(Othello.PASS)) {
    		if( othello.getPassCheck() ) {
    			outcome = othello.getOutcome();
            	sendToOutcome(outcome);
            	if( outcome.equals(Othello.WIN)) {
            		outcomeLabel.setForeground(Color.RED);
            		outcomeLabel.setText("勝利");
            	}else if( outcome.equals(Othello.LOSE) ) {
            		outcomeLabel.setForeground(Color.BLUE);
            		outcomeLabel.setText("敗北");
            	}else {
            		outcomeLabel.setForeground(Color.RED);
            		outcomeLabel.setText("引き分け");
            	}
            	tm2.start();
    		}else {
    			othello.setPassCheck(true);
    			judgeCount = othello.judge(othello.getMyColor());
    			if(judgeCount == 0) {
        			turn.setText("あなたの番です");
        			button11.setEnabled(true);
        			button12.setEnabled(true);
        		}else {
        			updateBoard(othello.getMyColor());
        		}
    		}
    		
    	}else if( cmd_enemy == "ConnectError") {
    		outcomeLabel.setForeground(Color.RED);
    		outcomeLabel.setText("勝利");
    		tm2.start();
    		
    	}else {
    		othello.setPassCheck(false);
    		//　座標変換
    		locate_x = Integer.parseInt(cmd_enemy)/10;
            locate_y = Integer.parseInt(cmd_enemy)%10;
            // 石の反転
            othello.reverse(othello.getEnemyColor(),locate_x,locate_y);
            judgeCount = othello.judge(othello.getMyColor());
            if( judgeCount == 0 ) {
            	button12.setEnabled(true);
            	updateBoard(othello.getMyColor());
            }else if( judgeCount < 0 ){
            	outcome = othello.getOutcome();
            	updateBoard(othello.getMyColor());
            	sendToOutcome(outcome);
            	if( outcome.equals(Othello.WIN)) {
            		outcomeLabel.setForeground(Color.RED);
            		outcomeLabel.setText("勝利");
            	}else if( outcome.equals(Othello.LOSE) ) {
            		outcomeLabel.setForeground(Color.BLUE);
            		outcomeLabel.setText("敗北");
            	}else {
            		outcomeLabel.setForeground(Color.RED);
            		outcomeLabel.setText("引き分け");
            	}
            	tm2.start();
            }else {
            	updateBoard(othello.getMyColor());
            }
        	
    	}
    		
    }
    
    public void squareClear() {
    	for(int i = 0; i < square.length; i++) {
    		for(int j = 0; j < square.length; j++) {
    			square[i][j].setIcon(defaultIcon);
    			square[i][j].setEnabled(false);
    		}
    	}
    	
    	square[3][3].setIcon(whiteStone);
        square[4][4].setIcon(whiteStone);
        square[3][4].setIcon(brackStone);
        square[4][3].setIcon(brackStone);
    	
        
        button11.setEnabled(false);
        button12.setEnabled(false);
        
        turn.setText("");
        outcomeLabel.setText("");
    	
    	
    }
   
    
}

class SaThread extends Thread {
	Client client;
	Othello othello;
	CardLayout layout;
	JPanel cardPanel;
	String message;
	Scanner accept;
	
	SaThread(Client frame, Othello othello, Scanner accept){
		client = frame;
		this.othello = othello;
		
		this.accept = accept;
	}
	
	
	public void run() {
		try {
			message = accept.nextLine();
			
			
				
			client.enemyProcess(message);
		} catch (InterruptedException e) {
				// TODO 自動生成された catch ブロック
				
				e.printStackTrace();
		}catch (NoSuchElementException e1) {
				System.exit(0);
		}
	}
	
	
	
}
    


class Othello{
	
	public static final int BRACK = 1;
	public static final int WHITE = 0;
	public static final int EMPTY = 2;
	private static final int OUTER = -1;
	public static final int OK = 3;
	public static final String FIRST = "first";
	public static final String REAR = "rear";
	public static final String RETIRE = "retire";
	public static final String PASS = "pass";
	public static final String WIN = "win";
	public static final String LOSE = "lose";
	public static final String DRAW = "draw";
	
	private int[][] board = new int[10][10];
	private int myColor;
	private int enemyColor;
	private boolean passCheck = false;
	private int[] di = {-1, -1, 0, 1, 1,  1,  0, -1};
	private int[] dj = { 0,  1, 1, 1, 0, -1, -1, -1};
	
	Othello(){
		boardClear();
	}
	
	public void setColor(int myColor, int enemyColor) {
		this.myColor = myColor;
		this.enemyColor = enemyColor;	
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
	
	public int getMyColor() {
		return myColor;
	}
	
	public int getEnemyColor() {
		return enemyColor;
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

	
	public int judge(int nextColor) {
		int judgeResult = 0;
		boolean checkEmpty = false;
		int nextI;
		int nextJ;
		
		
    	for(int i = 1; i < board.length-1; i++) {
    		for(int j = 1; j < board.length-1; j++) {
    			if(board[i][j] == EMPTY) {
    				checkEmpty=true;
    				if(nextColor == myColor) {
    				for(int k = 0; k < 8; k++) {
    					nextI = i + di[k];
    					nextJ = j + dj[k];
    					if( myColor+board[nextI][nextJ] == 1) {
    						nextI += di[k];
    						nextJ += dj[k];
    						while(myColor+board[nextI][nextJ] == 1) {
        						nextI += di[k];
        						nextJ += dj[k];
        					}
        					if(myColor == board[nextI][nextJ]) {
        						board[i][j] = OK;
        						judgeResult++;
        					}
    					}
    					
    				}
    				}else {
    					break;
    				}
    			}
    	    		
    	    	
            }	
        }
    	
    	if( !checkEmpty ) {
    		return -10;
    	}else {
    		return judgeResult;
    	}
    	
    	
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
	
	public boolean getPassCheck() {
		return passCheck;
	}
	
	public void setPassCheck(boolean passCheck) {
		this.passCheck = passCheck;
	}
	
}

class WhiteStone implements Icon{
	
	@Override
	public void paintIcon(Component c, Graphics g, int x, int y) {
		g.setColor(Color.GREEN);
		g.fillRect(0, 0, 31, 31);
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
		g.setColor(Color.GREEN);
		g.fillRect(0, 0, 31, 31);
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

class DefaultIcon implements Icon{

	@Override
	public void paintIcon(Component c, Graphics g, int x, int y) {
		g.setColor(Color.GREEN);
		g.fillRect(0, 0, 31, 31);
	}

	@Override
	public int getIconWidth() {
		// TODO 自動生成されたメソッド・スタブ
		return 0;
	}

	@Override
	public int getIconHeight() {
		// TODO 自動生成されたメソッド・スタブ
		return 0;
	}
	
}

class RedIcon implements Icon{

	@Override
	public void paintIcon(Component c, Graphics g, int x, int y) {
		g.setColor(Color.RED);
		g.fillRect(0, 0, 31, 31);
	}

	@Override
	public int getIconWidth() {
		// TODO 自動生成されたメソッド・スタブ
		return 0;
	}

	@Override
	public int getIconHeight() {
		// TODO 自動生成されたメソッド・スタブ
		return 0;
	}
	
}

