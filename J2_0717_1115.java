import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.Timer;

public class J2 extends JFrame implements ActionListener{
    JPanel cardPanel;
    CardLayout layout;
    String username ="";
    String Myturn;
    String WLD[] = {"Win","Lose","Draw"};
    int player_num =4;
    int kanij_sum = 1006;
    int round = 5;
    int chips_current;
    int score_max = 31;
    Kanji kanji[] = new Kanji[kanij_sum];
    JTextField id_input;
    data_Player[] player = new data_Player[player_num];
    data_Player deeler = new data_Player("LAST","deeler",0);
    ImageIcon icon_learned;
    JLabel uh_mes;
    JLabel aut_mes;
    JLabel turn;
    JLabel fault;
    JLabel fault4;
    JLabel label_chips;
    JLabel label_roomname;
    JLabel label_search;
    JLabel[] label_result = new JLabel[player_num];
    JLabel[] label_name = new JLabel[player_num];
    JLabel[] label_point = new JLabel[player_num];
    JLabel[] label_chips_current = new JLabel[player_num];
    JLabel[] label_chips_bet = new JLabel[player_num];
    JLabel label_deeler_point;
    JLabel label_word;
    JLabel label_reading;
    JLabel label_radical;
    JLabel label_stroke;
    JLabel label_learned;
    JTextField id_renew_input ;
    JTextField sq_input;
    JTextField roomname_input;
    JPasswordField pw_input;
    JPasswordField pw_input_re;
    JTextField id_new_input;
    JTextField pw_new_input;
    JTextField Q_sec_input;
    JTextField search;
    JTextField chips_input;
    JButton button10;
    JButton button11;
    JButton button12;
    JButton button13;
    JButton button17;
    JButton button18;
    JButton button19;
    JButton button20;
    Timer tm2;
    
    public JLayeredPane LPane;
    CardsAni[][] ca = new CardsAni[6][6];//カードのアニメーション 一つ目の配列:0~3がプレイヤー、4がディーラー、5が山札 二つ目の配列:そのプレイヤーのn枚目のカード
    CardsTh[][] ct = new CardsTh[6][6];
   
    static Socket socket;
    static PrintWriter output;
    static Scanner input;
	static J2 frame;
	
    public static void main(String[] args) {
    	
    	
    	try {
			socket = new Socket("127.0.0.1", 3838);
			output = new PrintWriter(socket.getOutputStream());
    		input = new Scanner(socket.getInputStream());
    		
			frame = new J2("BorderLayoutDemo");
			frame.setTitle("漢字ブラックジャック");
	        frame.setSize(1270, 750);
	        frame.setResizable(false);
	        frame.setLocationRelativeTo(null);
	        frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
	        frame.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.print(1);;
			System.exit(0);
		}
    }

    public J2(String title)throws Exception {
    	super(title);
    	
    	Font f13 = new Font("Serif", Font.PLAIN, 13);
    	Font f20 = new Font("Serif", Font.PLAIN, 20);
    	Font f30 = new Font("Serif", Font.PLAIN, 30);
    	Font f45 = new Font("Serif", Font.PLAIN, 45);
    	Font f60 = new Font("Serif", Font.PLAIN, 60);
    	Font f180 = new Font("Serif", Font.PLAIN, 180);
    	
    	ImageIcon icon_background = new ImageIcon("漢字ブラックジャック背景.png");  //漢字ブラックジャック背景.png
        ImageIcon icon_title = new ImageIcon("漢字ブラックジャックタイトル.png");  //漢字ブラックジャックタイトル.png
        ImageIcon icon_password = new ImageIcon("パスワード.png");  //パスワード.png
        ImageIcon icon_username = new ImageIcon("ユーザー名.png");  //ユーザー名.png
        ImageIcon icon_battle = new ImageIcon("対戦.png");  //対戦.png
        ImageIcon icon_zukan = new ImageIcon("図鑑.png");  //図鑑.png
        ImageIcon icon_logout = new ImageIcon("ログアウト.png");  //ログアウト.png
        ImageIcon icon_card = new ImageIcon("トランプ背景.png");  //トランプ背景.png
        ImageIcon icon_button = new ImageIcon("ボタン.png");  //ボタン.png
        ImageIcon icon_background_battle = new ImageIcon("対戦背景.png");  //対戦背景.png
        ImageIcon icon_zukan_title = new ImageIcon("図鑑背景.png");  //図鑑背景.png
        icon_learned = new ImageIcon("学習済み.png");  //学習済み.png
        ImageIcon icon_room_name = new ImageIcon("ルーム名.png");  //ルーム名.png
        ImageIcon icon_title_battle = new ImageIcon("対戦タイトル.png");	//対戦タイトル.png	
        ImageIcon icon_rank = new ImageIcon("順位.png");  //順位.png
        		
        SetKanji1();
        SetKanji2();
        
        // ログインホーム
        JPanel panel01 = new JPanel();
        panel01.setLayout(null);
        panel01.setPreferredSize(new Dimension(1280, 750));
        
        JLabel label_background_1 = new JLabel(icon_background);
        label_background_1.setBounds(0, 0, 1280, 750);
        
        JLabel label_card_1 = new JLabel(icon_card);
        label_card_1.setBounds(0, 0, 1280, 750);
        
        JLabel label_title = new JLabel(icon_title);
        label_title.setBounds(0, -200, 1280, 750);
        
        JLabel label_password_1 = new JLabel(icon_password);
        label_password_1.setBounds(-85, -85, 1280, 750);
        
        JLabel label_username_1 = new JLabel(icon_username);
        label_username_1.setBounds(-85, -125, 1280, 750);
        
        aut_mes =new JLabel("");
        aut_mes.setFont(f13);
        aut_mes.setBounds(520, 290, 300, 80);
        
        id_input = new JTextField(16);
        id_input.setBounds(650, 225, 120, 30);
        panel01.add(id_input);
        
        pw_input = new JPasswordField(16);
        pw_input.setBounds(650, 275, 120, 30);
        panel01.add(pw_input);
        
        JButton button1 = new JButton();
        button1.setFont(f13);
        button1.setBounds(480, 360, 300, 50);
        button1.setBackground(Color.BLACK);
        button1.setForeground(Color.WHITE);
        button1.setActionCommand("LoginPage");
        button1.addActionListener(this);
        button1.setText("ログイン");
        button1.setHorizontalTextPosition(JButton.CENTER);
        button1.setIcon(icon_button);

        JButton button2 = new JButton();
        button2.setFont(f13);
        button2.setBounds(480, 470, 300, 50);
        button2.setBackground(Color.BLACK);
        button2.setForeground(Color.WHITE);
        button2.setActionCommand("NewAccountPage");
        button2.addActionListener(this);
        button2.setText("新規アカウント作成");
        button2.setHorizontalTextPosition(JButton.CENTER);
        button2.setIcon(icon_button);

        panel01.add(aut_mes);
        panel01.add(button1);
        panel01.add(button2);
        panel01.add(label_title);
        panel01.add(label_password_1);
        panel01.add(label_username_1);
        panel01.add(label_card_1);
        panel01.add(label_background_1);

        // 新規アカウント作成
        JPanel panel02 = new JPanel();
        panel02.setLayout(null);
        panel02.setPreferredSize(new Dimension(1280, 750));
        
        JLabel label_background_2 = new JLabel(icon_background);
        label_background_2.setBounds(0, 0, 1280, 750);
        
        JLabel label_card_2 = new JLabel(icon_card);
        label_card_2.setBounds(0, 0, 1280, 750);
        
        JLabel label_password_2 = new JLabel(icon_password);
        label_password_2.setBounds(-85, -180, 1280, 750);
        
        JLabel label_username_2 = new JLabel(icon_username);
        label_username_2.setBounds(-85, -230, 1280, 750);
        
        fault = new JLabel("");
        fault.setBounds(490,290, 300, 50);
        panel02.add(fault);
        
        id_new_input = new JTextField(16);
        id_new_input.setBounds(650, 125, 120, 30);
        panel02.add(id_new_input);
        
        pw_new_input = new JTextField(16);
        pw_new_input.setBounds(650, 175, 120, 30);
        panel02.add(pw_new_input);
        
        JButton button4 = new JButton();
        button4.setFont(f13);
        button4.setBounds(480, 350, 300, 50);
        button4.setBackground(Color.BLACK);
        button4.setForeground(Color.WHITE);
        button4.setActionCommand("MakeAccount");
        button4.addActionListener(this);
        button4.setText("新規アカウント作成");
        button4.setHorizontalTextPosition(JButton.CENTER);
        button4.setIcon(icon_button);
        panel02.add(button4);
        

        JButton button5 = new JButton();
        button5.setFont(f13);
        button5.setBounds(480, 460, 300, 50);
        button5.setBackground(Color.BLACK);
        button5.setForeground(Color.WHITE);
        button5.setActionCommand("BackHome");
        button5.addActionListener(this);
        button5.setText("ホームに戻る");
        button5.setHorizontalTextPosition(JButton.CENTER);
        button5.setIcon(icon_button);
        
        panel02.add(button5);
        panel02.add(label_password_2);
        panel02.add(label_username_2);
        panel02.add(label_card_2);
        panel02.add(label_background_2);
        
        // ユーザーホーム
        JPanel panel04 = new JPanel();
        panel04.setLayout(null);
        panel04.setPreferredSize(new Dimension(200, 100));
        panel04.setBackground(new Color(255,255,255));
        
        JLabel label_background_4 = new JLabel(icon_background);
        label_background_4.setBounds(0, 0, 1280, 750);
        
        JLabel label_card_4 = new JLabel(icon_card);
        label_card_4.setBounds(0, 0, 1280, 750);
        
        JLabel label_battle = new JLabel(icon_battle);
        label_battle.setBounds(-10, -20, 1280, 750);
        
        JLabel label_zukan = new JLabel(icon_zukan);
        label_zukan.setBounds(-410, 20, 1280, 750);
        
        JLabel label_logout = new JLabel(icon_logout);
        label_logout.setBounds(390, -140, 1280, 1000);
        
        uh_mes = new JLabel("");
        uh_mes.setBounds(10, 10, 1200, 80);
        uh_mes.setFont(f60);
        panel04.add(uh_mes);
        
        JButton button8 = new JButton("対戦する");
        button8.setFont(f13);
        button8.setBounds(480, 550, 300, 50);
        button8.setBackground(Color.BLACK);
        button8.setForeground(Color.WHITE);
        button8.setActionCommand("Battle");
        button8.addActionListener(this);
        button8.setText("対戦する");
        button8.setHorizontalTextPosition(JButton.CENTER);
        button8.setIcon(icon_button);
        panel04.add(button8);
        
        fault4 = new JLabel("");
        fault4.setBounds(0, 200, 300, 50);
        fault4.setForeground(Color.RED);
        fault4.setHorizontalAlignment(JLabel.CENTER);
        panel04.add(fault4);
        
        JButton button9 = new JButton("図鑑を見る");
        button9.setFont(f13);
        button9.setBounds(80, 550, 300, 50);
        button9.setBackground(Color.BLACK);
        button9.setForeground(Color.WHITE);
        button9.setActionCommand("CheckRecord");
        button9.addActionListener(this);
        button9.setText("図鑑を見る");
        button9.setHorizontalTextPosition(JButton.CENTER);
        button9.setIcon(icon_button);
        panel04.add(button9);
        
        button10 = new JButton("ログアウト");
        button10.setFont(f13);
        button10.setBounds(880, 550, 300, 50);
        button10.setBackground(Color.BLACK);
        button10.setForeground(Color.WHITE);
        button10.setActionCommand("BackHome");
        button10.addActionListener(this);
        button10.setText("ログアウト");
        button10.setHorizontalTextPosition(JButton.CENTER);
        button10.setIcon(icon_button);
        
        panel04.add(button10);
        panel04.add(label_battle);
        panel04.add(label_zukan);
        panel04.add(label_logout);
        panel04.add(label_card_4);
        panel04.add(label_background_4);
        
     // 対局画面
        LPane = new JLayeredPane();
        LPane.setBounds(0,0,1280,750);
        LPane.setBackground(new Color(255,255,255));
        
        JPanel panel05 = new JPanel();
        panel05.setLayout(null);
        panel05.setPreferredSize(new Dimension(200, 100));
        panel05.setBackground(new Color(255,255,255));
        panel05.setBounds(0,0,1280,750);
        
        JLabel label_background_battle = new JLabel(icon_background_battle);
        label_background_battle.setBounds(0, 0, 1280, 750);
        
        label_chips = new JLabel("");
        label_chips.setFont(f13);
        label_chips.setBounds(230, 548, 300, 80);
        label_chips.setForeground(Color.WHITE);
        panel05.add(label_chips);
        
        label_name[0] = new JLabel("");
        label_name[0].setFont(f20);
        label_name[0].setBounds(970, 490, 300, 80);
        label_name[0].setForeground(Color.WHITE);
        panel05.add(label_name[0]);
        
        label_name[1] = new JLabel("");
        label_name[1].setFont(f20);
        label_name[1].setBounds(690, 540, 300, 80);
        label_name[1].setForeground(Color.WHITE);
        panel05.add(label_name[1]);
        
        label_name[2] = new JLabel("");
        label_name[2].setFont(f20);
        label_name[2].setBounds(375, 540, 300, 80);
        label_name[2].setForeground(Color.WHITE);
        panel05.add(label_name[2]);
        
        label_name[3] = new JLabel("");
        label_name[3].setFont(f20);
        label_name[3].setBounds(70, 490, 300, 80);
        label_name[3].setForeground(Color.WHITE);
        panel05.add(label_name[3]);
        
        label_chips_current[0] = new JLabel("");
        label_chips_current[0].setFont(f20);
        label_chips_current[0].setBounds(1145, 490, 300, 80);
        label_chips_current[0].setForeground(Color.WHITE);
        panel05.add(label_chips_current[0]);
        
        label_chips_current[1] = new JLabel("");
        label_chips_current[1].setFont(f20);
        label_chips_current[1].setBounds(865, 540, 300, 80);
        label_chips_current[1].setForeground(Color.WHITE);
        panel05.add(label_chips_current[1]);
        
        label_chips_current[2] = new JLabel("");
        label_chips_current[2].setFont(f20);
        label_chips_current[2].setBounds(550, 540, 300, 80);
        label_chips_current[2].setForeground(Color.WHITE);
        panel05.add(label_chips_current[2]);
        
        label_chips_current[3] = new JLabel("");
        label_chips_current[3].setFont(f20);
        label_chips_current[3].setBounds(245,490, 300, 80);
        label_chips_current[3].setForeground(Color.WHITE);
        panel05.add(label_chips_current[3]);
        
        label_chips_bet[0] = new JLabel("");
        label_chips_bet[0].setFont(f20);
        label_chips_bet[0].setBounds(1149, 225, 300, 80);
        panel05.add(label_chips_bet[0]);
        
        label_chips_bet[1] = new JLabel("");
        label_chips_bet[1].setFont(f20);
        label_chips_bet[1].setBounds(846, 276, 300, 80);
        panel05.add(label_chips_bet[1]);
        
        label_chips_bet[2] = new JLabel("");
        label_chips_bet[2].setFont(f20);
        label_chips_bet[2].setBounds(556, 275, 300, 80);
        panel05.add(label_chips_bet[2]);
        
        label_chips_bet[3] = new JLabel("");
        label_chips_bet[3].setFont(f20);
        label_chips_bet[3].setBounds(261, 226, 300, 80);
        panel05.add(label_chips_bet[3]);
        
        label_point[0] = new JLabel("");
        label_point[0].setFont(f30);
        label_point[0].setBounds(1054, 194, 300, 80);
        label_point[0].setForeground(Color.WHITE);
        panel05.add(label_point[0]);
        
        label_point[1] = new JLabel("");
        label_point[1].setFont(f30);
        label_point[1].setBounds(749, 247, 300, 80);
        label_point[1].setForeground(Color.WHITE);
        panel05.add(label_point[1]);
        
        label_point[2] = new JLabel("");
        label_point[2].setFont(f30);
        label_point[2].setBounds(463, 248, 300, 80);
        label_point[2].setForeground(Color.WHITE);
        panel05.add(label_point[2]);
        
        label_point[3] = new JLabel("");
        label_point[3].setFont(f30);
        label_point[3].setBounds(167, 193, 300, 80);
        label_point[3].setForeground(Color.WHITE);
        panel05.add(label_point[3]);
        
        label_deeler_point = new JLabel("");
        label_deeler_point.setFont(f30);
        label_deeler_point.setBounds(605, 185, 300, 80);
        label_deeler_point.setForeground(Color.WHITE);
        panel05.add(label_deeler_point);
        
        chips_input = new JTextField(16);
        chips_input.setBounds(228, 550, 120, 30);
        panel05.add(chips_input);
        
        button11 = new JButton();
        button11.setFont(f13);
        button11.setBounds(480, 598, 300, 50);
        button11.setBackground(Color.BLACK);
        button11.setForeground(Color.WHITE);
        button11.setActionCommand("Hit");
        button11.addActionListener(this);
        button11.setEnabled(false);
        button11.setText("ヒット");
        button11.setHorizontalTextPosition(JButton.CENTER);
        button11.setIcon(icon_button);
        panel05.add(button11);
        
        button12 = new JButton();
        button12.setFont(f13);
        button12.setBounds(820, 598, 300, 50);
        button12.setBackground(Color.BLACK);
        button12.setForeground(Color.WHITE);
        button12.setActionCommand("Stand");
        button12.addActionListener(this);
        button12.setEnabled(false);
        button12.setText("スタンド");
        button12.setHorizontalTextPosition(JButton.CENTER);
        button12.setIcon(icon_button);
        panel05.add(button12);
        
        button13 = new JButton();
        button13.setFont(f13);
        button13.setBounds(140, 598, 300, 50);
        button13.setBackground(Color.BLACK);
        button13.setForeground(Color.WHITE);
        button13.setActionCommand("Bet");
        button13.addActionListener(this);
        button13.setEnabled(false);
        button13.setText("ベット");
        button13.setHorizontalTextPosition(JButton.CENTER);
        button13.setIcon(icon_button);
        panel05.add(button13);
        
        panel05.add(label_background_battle);
        
        panel05.add(label_background_battle);
        LPane.setLayer(panel05, 1);
        LPane.add(panel05);
       
     // 図鑑
        JPanel panel06 = new JPanel();
        panel06.setLayout(null);
        panel06.setPreferredSize(new Dimension(200, 100));
        panel06.setBackground(new Color(255,255,255));
        
        JLabel label_background_6 = new JLabel(icon_background);
        label_background_6.setBounds(0, 0, 1280, 750);
        
        label_learned = new JLabel();
        label_learned.setBounds(0, 0, 1280, 750);
        panel06.add(label_learned);
        
        JLabel label_zukan_title = new JLabel(icon_zukan_title);
        label_zukan_title.setBounds(0, 0, 1280, 750);
        
        JLabel label_card_6 = new JLabel(icon_card);
        label_card_6.setBounds(0, 0, 1280, 750);
        
        search = new JTextField(16);
        search.setBounds(570, 110, 120, 30);
        panel06.add(search);
        
        label_search = new JLabel("");
        label_search.setFont(f13);
        label_search.setBounds(540, 0, 280, 180);
        panel06.add(label_search);
        
        label_word = new JLabel("");
        label_word.setFont(f180);
        label_word.setBounds(405, 310, 180, 180);
        panel06.add(label_word);
        
        label_reading = new JLabel("");
        label_reading.setFont(f13);
        label_reading.setBounds(685, 195, 400, 200);
        panel06.add(label_reading);
        
        label_radical = new JLabel("");
        label_radical.setFont(f30);
        label_radical.setBounds(685, 315, 200, 200);
        panel06.add(label_radical);
        
        label_stroke = new JLabel("");
        label_stroke.setFont(f30);
        label_stroke.setBounds(685, 420, 200, 200);
        panel06.add(label_stroke);
        
        JButton button14 = new JButton();
        button14.setFont(f13);
        button14.setBounds(480, 150, 300, 50);
        button14.setBackground(Color.BLACK);
        button14.setForeground(Color.WHITE);
        button14.setActionCommand("Search");
        button14.addActionListener(this);
        button14.setText("検索");
        button14.setHorizontalTextPosition(JButton.CENTER);
        button14.setIcon(icon_button);
        
        JButton button15 = new JButton();
        button15.setFont(f13);
        button15.setBounds(480, 630, 300, 50);
        button15.setBackground(Color.BLACK);
        button15.setForeground(Color.WHITE);
        button15.setActionCommand("BackUserPage");
        button15.addActionListener(this);
        button15.setText("戻る");
        button15.setHorizontalTextPosition(JButton.CENTER);
        button15.setIcon(icon_button);
        
        panel06.add(button14);
        panel06.add(button15);
        panel06.add(label_zukan_title);
        panel06.add(label_card_6);
        panel06.add(label_background_6);
        
        //ルーム
        JPanel panel08 = new JPanel();
        panel08.setLayout(null);
        panel08.setPreferredSize(new Dimension(200, 100));
        panel08.setBackground(new Color(255,255,255));
        
        JLabel label_background_8 = new JLabel(icon_background);
        label_background_8.setBounds(0, 0, 1280, 750);
        
        JLabel label_title_battle = new JLabel(icon_title_battle);
        label_title_battle.setBounds(0, 0, 1280, 750);
        
        JLabel label_room_name = new JLabel(icon_room_name);
        label_room_name.setBounds(0, 0, 1280, 750);
        
        JLabel label_card_8 = new JLabel(icon_card);
        label_card_8.setBounds(0, 0, 1280, 750);
        
        roomname_input = new JTextField(16);
        roomname_input.setBounds(650, 115, 120, 30);
        panel08.add(roomname_input);
        
        label_roomname = new JLabel("");
        label_roomname.setFont(f13);
        label_roomname.setBounds(530, 120, 300, 80);
        panel08.add(label_roomname);
        
        button17 = new JButton();
        button17.setFont(f13);
        button17.setBounds(480, 250, 300, 50);
        button17.setBackground(Color.BLACK);
        button17.setForeground(Color.WHITE);
        button17.setActionCommand("MakeRoom");
        button17.addActionListener(this);
        button17.setEnabled(true);
        button17.setText("部屋を作る");
        button17.setHorizontalTextPosition(JButton.CENTER);
        button17.setIcon(icon_button);
        panel08.add(button17);
        
        button18 = new JButton();
        button18.setFont(f13);
        button18.setBounds(480, 360, 300, 50);
        button18.setBackground(Color.BLACK);
        button18.setForeground(Color.WHITE);
        button18.setActionCommand("EnterRoom");
        button18.addActionListener(this);
        button18.setEnabled(true);
        button18.setText("部屋に入る");
        button18.setHorizontalTextPosition(JButton.CENTER);
        button18.setIcon(icon_button);
        panel08.add(button18);
        
        button19 = new JButton();
        button19.setFont(f13);
        button19.setBounds(480, 580, 300, 50);
        button19.setBackground(Color.BLACK);
        button19.setForeground(Color.WHITE);
        button19.setActionCommand("BackUserHome");
        button19.addActionListener(this);
        button19.setEnabled(true);
        button19.setText("戻る");
        button19.setHorizontalTextPosition(JButton.CENTER);
        button19.setIcon(icon_button);
        panel08.add(button19);
        
        button20 = new JButton();
        button20.setFont(f13);
        button20.setBounds(480, 470, 300, 50);
        button20.setBackground(Color.BLACK);
        button20.setForeground(Color.WHITE);
        button20.setActionCommand("StartBattle");
        button20.addActionListener(this);
        button20.setEnabled(false);
        button20.setText("対戦開始");
        button20.setHorizontalTextPosition(JButton.CENTER);
        button20.setIcon(icon_button);
        panel08.add(button20);
        
        panel08.add(label_title_battle);
        panel08.add(label_room_name);
        panel08.add(label_card_8);
        panel08.add(label_background_8);
        
        //戦績
        JPanel panel09 = new JPanel();
        panel09.setLayout(null);
        panel09.setPreferredSize(new Dimension(200, 100));
        panel09.setBackground(new Color(255,255,255));
        
        JLabel label_background_9 = new JLabel(icon_background);
        label_background_9.setBounds(0, 0, 1280, 750);
        
        JLabel label_card_9 = new JLabel(icon_card);
        label_card_9.setBounds(0, 0, 1280, 750);
        
        JLabel label_rank = new JLabel(icon_rank);
        label_rank.setBounds(0, 0, 1280, 750);
        
        JButton button21 = new JButton();
        button21.setFont(f13);
        button21.setBounds(480, 645, 300, 50);
        button21.setBackground(Color.BLACK);
        button21.setForeground(Color.WHITE);
        button21.setActionCommand("BackUserHome");
        button21.addActionListener(this);
        button21.setEnabled(true);
        button21.setText("ホームに戻る");
        button21.setHorizontalTextPosition(JButton.CENTER);
        button21.setIcon(icon_button);
        panel09.add(button21);
        
        label_result[0] = new JLabel("");
        label_result[0].setBounds(330, 160, 600, 80);
        label_result[0].setFont(f45);
        panel09.add(label_result[0]);
        
        label_result[1] = new JLabel("");
        label_result[1].setBounds(330, 285, 600, 80);
        label_result[1].setFont(f45);
        panel09.add(label_result[1]);
        
        label_result[2] = new JLabel("");
        label_result[2].setBounds(330, 410, 600, 80);
        label_result[2].setFont(f45);
        panel09.add(label_result[2]);
        
        label_result[3] = new JLabel("");
        label_result[3].setBounds(330, 535, 600, 80);
        label_result[3].setFont(f45);
        panel09.add(label_result[3]);
        
        panel09.add(label_rank);
        panel09.add(label_card_9);
        panel09.add(label_background_9);
        
        // CardLayout用パネル
        cardPanel = new JPanel();
        layout = new CardLayout();
        cardPanel.setLayout(layout);

        cardPanel.add(panel01, "1");
        cardPanel.add(panel02, "2");
        cardPanel.add(panel04, "4");
        cardPanel.add(LPane, "5");
        cardPanel.add(panel06, "6");
        cardPanel.add(panel08, "8");
        cardPanel.add(panel09, "9");

        // cardPanelとカード移動用ボタンをJFrameに配置
        Container contentPane = getContentPane();
        contentPane.add(cardPanel, BorderLayout.CENTER);
        
        tm2 = new Timer(5000, this);
        tm2.setActionCommand("GameEnd");
        tm2.setRepeats(false);
       
    }
    
    
    public void actionPerformed(ActionEvent e) {
    	
    	try {
    	int index;
        String cmd = e.getActionCommand();
        String room_name;
        String word;
        aut_mes.setText("");
        fault.setText("");
        fault4.setText("");
        
        switch(cmd) {
        case "LoginPage":
        	if(authentication(id_input.getText(),String.valueOf(pw_input.getPassword()))) {
        		username = id_input.getText();
        		id_input.setText("");
        		pw_input.setText("");
        		aut_mes.setText("");
        		uh_mes.setText("ようこそ "+username+"さん");
        		layout.show(cardPanel, "4");
        	}else {
        		aut_mes.setText("IDまたはパスワードにエラーがあります");
    			layout.show(cardPanel, "1");
        	}
        	break;
        case "NewAccountPage":
        	layout.show(cardPanel, "2");
            break;
        	
        case "MakeAccount":
        	if( id_new_input.getText().equals("") || pw_new_input.getText().equals("")) {
        		fault.setText("必要事項が足りません");
        	}else {
        	
	        	if(reset(id_new_input.getText(),pw_new_input.getText())) {
	        		id_new_input.setText("");
	        		pw_new_input.setText("");
	        		fault.setText("");
	        		layout.show(cardPanel, "1");
	        	}else {
	        		fault.setText("新規アカウントの作成に失敗しました");
	        		layout.show(cardPanel, "2");
	        	}
        	}
            break;
        	
        case "BackHome":
        	if( e.getSource() == button10 ) {
        		output.println("7");
        		output.flush();
        	}
        	layout.show(cardPanel, "1");
        	break;
        	   
        case "CheckRecord":
        	layout.show(cardPanel, "6");
            break;
            
        case "Search":
        	word = search.getText();
        	if(word.equals("")) {
        		label_search.setText("検索する漢字を入力してください");
        	}else {
        		index = SearchKanji(word);
        		if(index == -1) {
        			label_search.setText("該当する漢字が見つかりません");
        		}else {
        			label_search.setText("");
        			label_word.setText(kanji[index].kanji);
        			label_reading.setText(String.join(",", kanji[index].reading));
        			label_radical.setText(kanji[index].radical);
        			label_stroke.setText(kanji[index].stroke+"画");
        			if(kanji[index].appearance) {
        				label_learned.setIcon(icon_learned);
        			}else {
        				label_learned.setIcon(null);
        			}
        		}
        	}
        	
        	break;
        
        case "BackUserPage":
        	search.setText("");
        	label_word.setText("");
			label_reading.setText("");
			label_radical.setText("");
			label_stroke.setText("");
        	layout.show(cardPanel, "4");
            break;
            
        	    	
        case "Battle":
        	label_roomname.setText("");
        	layout.show(cardPanel, "8");
        	break;
          
        case "MakeRoom":
        	button17.setEnabled(false);
        	button18.setEnabled(false);
        	button19.setEnabled(false);
        	output.println("4");
        	output.flush();
        	button20.setEnabled(true);
        	break;
        	
        case "EnterRoom":
        	button17.setEnabled(false);
        	button18.setEnabled(false);
        	button19.setEnabled(false);
        	room_name = roomname_input.getText();
        	
        	if(room_name.equals("")) {
        		label_roomname.setText("適切なルーム名を入力してください");
        		button17.setEnabled(true);
            	button18.setEnabled(true);
            	button19.setEnabled(true);
        	}else {
        		output.println("5");
        		output.flush();
            	output.println(room_name);
            	output.flush();
            	
            	cmd = input.nextLine();
            	if(cmd.equals("0")) {
            		label_roomname.setText("探した部屋が見つかりませんでした");
            	}else {
            		search();
            		layout.show(cardPanel, "5");
            	}
            	button17.setEnabled(true);
            	button18.setEnabled(true);
            	button19.setEnabled(true);
        	}
        	
        	break;
        	
        case "StartBattle":
        	button17.setEnabled(true);
        	button18.setEnabled(true);
        	button19.setEnabled(true);
        	button20.setEnabled(false);
        	output.println("6");
        	output.flush();
        	search();
        	layout.show(cardPanel, "5");
        	
        	ca[5][0] = new CardsAni(1,0);
 	        LPane.setLayer(ca[5][0], 2);      
 	        LPane.add(ca[5][0]);
 	        
 	        ct[5][0] = new CardsTh(0, ca[5][0]);
 	        ct[5][0].start();
        	
        	break;
        	
        case "BackUserHome":
        	layout.show(cardPanel, "4");
        	break;
       
        case "Bet":
        	String turn;
        	String response;
        	String chips;
        	String card1;
        	String card2;
        	
        	button13.setEnabled(false);
        	
        	chips = chips_input.getText();
        	
        	if(judge(chips)  && Integer.parseInt(chips) >= 0&& (Integer.parseInt(chips) <= player[Integer.parseInt(Myturn)].chips || Integer.parseInt(chips) <= 100)) {
        		label_chips.setText("");
        		output.println("7");
        		output.flush();
        		output.println(chips);
        		output.flush();
        		for(int i = 0; i < player_num; i++) {
        			turn = input.nextLine();
            		chips = input.nextLine();
            		card1 = input.nextLine();
            		card2 = input.nextLine();
            		set_data(turn, chips, card1, card2);
        		}
        		card1 = input.nextLine();
        		deeler.cardlist.add(card1);
        		System.out.println("deeler size in main" + deeler.cardlist.size() + deeler.cardlist);
        		calculate_point("-1");
        		
        		System.out.println(deeler.user_name);
    			System.out.println(kanji[Integer.parseInt(card1)].kanji);
    			
    			PreTh pt = new PreTh(ca, ct, LPane, player, deeler, kanji, "setup");
        		pt.start();
        	}else {
        		button13.setEnabled(true);
        		label_chips.setText("再入力してください");
        	}
        	
        	if(Myturn.equals("0")) {
        		button11.setEnabled(true);
        		button12.setEnabled(true);
        	}else {
        		while(true) {
        			turn = input.nextLine();
            		if(update_data(turn)) {
            			break;
            		}
        		}
        	}
        	
        	break;
        	
        case "Hit":
        	button11.setEnabled(false);
    		button12.setEnabled(false);
        	output.println("8");
        	output.flush();
        	while(true) {
    			turn = input.nextLine();
        		if(update_data(turn)) {
        			break;
        		}
    		}
        	
        	PreTh pt = new PreTh(ca, ct, LPane, player, deeler, kanji, "hit");
    		pt.start();
    		
        	break;
        	
        case "Stand":
        	button11.setEnabled(false);
    		button12.setEnabled(false);
        	output.println("9");
        	output.flush();
        	
        	
        	while(true) {
        		turn = input.nextLine();
        		System.out.println("stand turn " + turn);
            	if(turn.equals("-2")) {
            		calculate_chips();
            		System.out.println("when -2 deeler size stand" + deeler.cardlist.size() + deeler.cardlist);
            		PreTh pt1 = new PreTh(ca, ct, LPane, player, deeler, kanji, "hit");
            		pt1.start();

                	//register(Myturn);
                	response = input.nextLine();
//                	if(response.equals("fin")) {
//                		result();
//                		layout.show(cardPanel, "9");
//                		
//                		for(int i = 0; i < 20; i++) {
//                			LPane.remove(i);
//                		}
//                		
//                		for(int i = 0; i < 6; i++) {
//                			for(int j = 0; j < 6; j++) {
//                				ca[i][j] = new CardsAni(1,0);
//                				ct[i][j] = new CardsTh(2, ca[i][j]);
//                			}
//                		}
//                	}else {
//                		PreTh pt2 = new PreTh(ca, ct, LPane, player, deeler, kanji, "reset");
//                		pt2.start();
//                		
//                		button13.setEnabled(true);
//                		button11.setEnabled(false);
//                		button12.setEnabled(false);
//                	}
                	//clear_point();
            		break;
            	}else {
            		if(update_data(turn)) {
            			calculate_point(turn);
            			System.out.println("stand updated");
            			break;
            		}else {
            			calculate_point(turn);
            		}
            	}
        	}
        	if(!turn.equals("-2")) {
        		System.out.println("deeler size stand " + deeler.cardlist.size() + deeler.cardlist);
            	PreTh pt1 = new PreTh(ca, ct, LPane, player, deeler, kanji, "hit");
        		pt1.start();
        	}
        	
    		
        	break;	
        }
        
    	}catch(NoSuchElementException e1) {
    		layout.show(cardPanel, "1");
    	    
    		System.exit(0);
    		
    	} 
    }
    
    public boolean authentication(String UN, String PW) {
    	output.println("1");
    	output.flush();
    	output.println(UN);
    	output.flush();
    	output.println(PW);
    	output.flush();
    	String response = input.nextLine();
    	return response.equals("Success");
    }
    
    public boolean reset(String ID, String PW) {
    	output.println("2");
    	output.flush();
    	output.println(ID);
    	output.flush();
    	output.println(PW);
    	output.flush();
    	String response = input.nextLine();
    	return response.equals("Success");
    }
    
    public void  search() { 
    	button13.setEnabled(true);
    	Myturn = input.nextLine();
    	
    	for(int i = 0; i < player_num; i++) {
    		String turn = input.nextLine();
    		String name = input.nextLine();
    		player[i] = new data_Player(turn,name,100);
    		label_name[i].setText(player[i].user_name);
    		label_chips_current[i].setText(""+player[i].chips);
    	}
    }
    
    public int SearchKanji(String word) {
    	int judge = -1;
    	
    	for(int i = 0; i < kanij_sum; i++) {
    		if(kanji[i].kanji.equals(word)) {
    			judge = i;
    			break;
    		}
    	}
    	
    	return judge;
    }
    
    public void  set_data(String turn_str, String chips, String card1, String card2) {
    	int turn;
    	
    	turn = Integer.parseInt(turn_str);
    	
    	player[turn].chips_bet = Integer.parseInt(chips);
		player[turn].cardlist.add(card1);
		player[turn].cardlist.add(card2);
		label_chips_bet[turn].setText(""+player[turn].chips_bet);
		label_chips_current[turn].setText(""+(player[turn].chips-player[turn].chips_bet));
		calculate_point(turn_str);
		
		System.out.println(turn_str);
		System.out.println(player[turn].user_name);
		System.out.println(kanji[Integer.parseInt(card1)].kanji);
		System.out.println(kanji[Integer.parseInt(card2)].kanji);
		
    }
    
    public boolean update_data(String turn) {
    	int turn_current = Integer.parseInt(turn);
    	boolean turn_next = false;
    	String response;
    	
    	response = input.nextLine();
    	
    	if(turn.equals("-1")) {
    		deeler.updated = false;
    		deeler.cardlist.add(response);
    		System.out.println("deeler size in update" + deeler.cardlist.size() + deeler.cardlist);
    		
    		System.out.println(deeler.user_name);
			System.out.println(kanji[Integer.parseInt(response)].kanji);
    	}else {
    		if(response.equals("-1")) {
    			player[turn_current].stand =false;
    		}else {
    			player[turn_current].updated = false;
    			player[turn_current].cardlist.add(response);
    			calculate_point(turn);
    			
    			System.out.println(turn);
    			System.out.println(player[turn_current].user_name);
    			System.out.println(kanji[Integer.parseInt(response)].kanji);
    		}
    		
    		if(Myturn.equals(""+(turn_current+1)%4) && (player[0].stand || player[1].stand || player[2].stand || player[3].stand)) {
        		turn_next = true;
        		if(player[(turn_current+1)%4].point_sum < score_max && player[(turn_current+1)%4].stand) {
    				button11.setEnabled(true);
    	        	button12.setEnabled(true);
    			}else {
    				button12.setEnabled(true);
    			}
    		}
    	}
    	
    	
    	
    	return turn_next;
    }
    
    public boolean judge(String text) {
        boolean result = true;

          for(int i = 0; i < text.length(); i++) {
            if(Character.isDigit(text.charAt(i))) {
              continue;
            }else {
              result =false;
              break;
            }
          }
          return result;
       }
    
    public void register(String turn_str) {
    	int turn;
    	turn = Integer.parseInt(turn_str);
    	
    	for(int i = 0; i < player[turn].cardlist.size(); i++) {
    		kanji[Integer.parseInt(player[turn].cardlist.get(i))].appearance = true;
    	}
    	
    	for(int i = 0; i < player_num; i++) {
    		player[i].cardlist.removeAll(player[i].cardlist);
    	}
    	deeler.cardlist.removeAll(deeler.cardlist);
    }
    
    public void calculate_point(String turn_str) {
    	int turn;
    	
    	if(turn_str.equals("-1")) {
    		deeler.point_sum = 0;
    		for(int j = 0; j < deeler.cardlist.size(); j ++) {
    			
    			deeler.point_sum += kanji[Integer.parseInt(deeler.cardlist.get(j))].point;
    			label_deeler_point.setText(""+deeler.point_sum);
    			
    			
        	}
    	}else {
    		turn = Integer.parseInt(turn_str);
    		player[turn].point_sum = 0;
    		for(int j = 0; j < player[turn].cardlist.size(); j ++) {
    			player[turn].point_sum += kanji[Integer.parseInt(player[turn].cardlist.get(j))].point;
    			
        	}
    		label_point[turn].setText(""+player[turn].point_sum);
    		if(player[turn].point_sum > score_max) {
        	}
    	}
    }
    
    public void calculate_chips() {
    	String turn_str,wld_str,chips_str;
    	int turn,chips,wld;
    	for(int i = 0; i < player_num; i++) {
    		turn_str = input.nextLine();
    		wld_str = input.nextLine();
    		chips_str = input.nextLine();
    		turn = Integer.parseInt(turn_str);
    		chips = Integer.parseInt(chips_str);
    		wld = Integer.parseInt(wld_str);
    		
    		player[turn].chips = chips;
    		label_point[turn].setText(WLD[wld]);
    		label_chips_current[turn].setText(""+player[turn].chips);
    		label_chips_bet[turn].setText("0");
    		player[turn].stand = true;
    	}
    }
    
    public void clear_point() {
    	for(int i = 0; i < player_num; i++) {
    		label_point[i].setText("");
    	}
    	label_deeler_point.setText("");
    }
    
    public void result() {
    	String[] name = new String[player_num];
    	int[] chips = new int[player_num];
    	
    	for(int i = 0; i < player_num; i++) {
    		name[i] = player[i].user_name;
    		chips[i] = player[i].chips;
    	}
    	
    	for(int i = 0; i < player_num; i ++) {
    		for(int j = 0; j < player_num-1; j++){
    			if(chips[j] < chips[j+1]) {
    				swap(chips[j], chips[j+1], name[j],name[j+1]);
    			}
    		}
    	}
    	
    	for(int i = 0; i < player_num; i ++) {
    		label_result[i].setText(name[i]);
    	}
    	
    }
    
    public void swap(int num1, int num2, String str1, String str2) {
    	int tmp_num;
    	String tmp_str;
    	
    	tmp_num = num1;
    	num1 = num2;
    	num2 = tmp_num;
    	
    	tmp_str = str1;
    	str1 = str2;
    	str2 = tmp_str;
    }
   	
    public void sendToCoordinate(String cmd) {
    	output.println(cmd);
    	output.flush();
    }
    
    public void SetKanji1() {
    	kanji[0] = new Kanji("愛", "心", 13);
    	kanji[0].reading.add("アイ");

    	kanji[1] = new Kanji("悪", "心", 11);
    	kanji[1].reading.add("アク");
    	kanji[1].reading.add("オ");
    	kanji[1].reading.add("わる-い");

    	kanji[2] = new Kanji("圧", "土", 5);
    	kanji[2].reading.add("アツ");

    	kanji[3] = new Kanji("安", "宀", 6);
    	kanji[3].reading.add("アン");
    	kanji[3].reading.add("やす-い");

    	kanji[4] = new Kanji("案", "木", 10);
    	kanji[4].reading.add("アン");

    	kanji[5] = new Kanji("暗", "日", 13);
    	kanji[5].reading.add("アン");
    	kanji[5].reading.add("くら-い");

    	kanji[6] = new Kanji("以", "人", 5);
    	kanji[6].reading.add("イ");

    	kanji[7] = new Kanji("衣", "衣", 6);
    	kanji[7].reading.add("イ");
    	kanji[7].reading.add("ころも");

    	kanji[8] = new Kanji("位", "人", 7);
    	kanji[8].reading.add("イ");
    	kanji[8].reading.add("くらい");

    	kanji[9] = new Kanji("囲", "囗", 7);
    	kanji[9].reading.add("イ");
    	kanji[9].reading.add("かこ-む");
    	kanji[9].reading.add("かこ-う");

    	kanji[10] = new Kanji("医", "酉", 7);
    	kanji[10].reading.add("イ");

    	kanji[11] = new Kanji("委", "女", 8);
    	kanji[11].reading.add("イ");
    	kanji[11].reading.add("ゆだ-ねる");

    	kanji[12] = new Kanji("胃", "肉", 9);
    	kanji[12].reading.add("イ");

    	kanji[13] = new Kanji("異", "田", 11);
    	kanji[13].reading.add("イ");
    	kanji[13].reading.add("こと");

    	kanji[14] = new Kanji("移", "禾", 11);
    	kanji[14].reading.add("イ");
    	kanji[14].reading.add("うつ-る");
    	kanji[14].reading.add("うつ-す");

    	kanji[15] = new Kanji("意", "心", 13);
    	kanji[15].reading.add("イ");

    	kanji[16] = new Kanji("遺", "辵", 15);
    	kanji[16].reading.add("イ");

    	kanji[17] = new Kanji("域", "土", 11);
    	kanji[17].reading.add("イキ");

    	kanji[18] = new Kanji("育", "肉", 8);
    	kanji[18].reading.add("イク");
    	kanji[18].reading.add("そだ-つ");
    	kanji[18].reading.add("そだ-てる");
    	kanji[18].reading.add("はぐく-む");

    	kanji[19] = new Kanji("一", "一", 1);
    	kanji[19].reading.add("イチ");
    	kanji[19].reading.add("イツ");
    	kanji[19].reading.add("ひと");
    	kanji[19].reading.add("ひと-つ");

    	kanji[20] = new Kanji("引", "弓", 4);
    	kanji[20].reading.add("イン");
    	kanji[20].reading.add("ひ-く");
    	kanji[20].reading.add("ひ-ける");

    	kanji[21] = new Kanji("印", "卩", 6);
    	kanji[21].reading.add("イン");
    	kanji[21].reading.add("しるし");

    	kanji[22] = new Kanji("因", "囗", 6);
    	kanji[22].reading.add("イン");
    	kanji[22].reading.add("よ-る");

    	kanji[23] = new Kanji("員", "口", 10);
    	kanji[23].reading.add("イン");

    	kanji[24] = new Kanji("院", "阜", 10);
    	kanji[24].reading.add("イン");

    	kanji[25] = new Kanji("飲", "食", 12);
    	kanji[25].reading.add("イン");
    	kanji[25].reading.add("の-む");

    	kanji[26] = new Kanji("右", "口", 5);
    	kanji[26].reading.add("ウ");
    	kanji[26].reading.add("ユウ");
    	kanji[26].reading.add("みぎ");

    	kanji[27] = new Kanji("宇", "宀", 6);
    	kanji[27].reading.add("ウ");

    	kanji[28] = new Kanji("羽", "羽", 6);
    	kanji[28].reading.add("ウ");
    	kanji[28].reading.add("は");
    	kanji[28].reading.add("はね");

    	kanji[29] = new Kanji("雨", "雨", 8);
    	kanji[29].reading.add("ウ");
    	kanji[29].reading.add("あめ");

    	kanji[30] = new Kanji("運", "辵", 12);
    	kanji[30].reading.add("ウン");
    	kanji[30].reading.add("はこ-ぶ");

    	kanji[31] = new Kanji("雲", "雨", 12);
    	kanji[31].reading.add("ウン");
    	kanji[31].reading.add("くも");

    	kanji[32] = new Kanji("永", "水", 5);
    	kanji[32].reading.add("エイ");
    	kanji[32].reading.add("なが-い");

    	kanji[33] = new Kanji("泳", "水", 8);
    	kanji[33].reading.add("エイ");
    	kanji[33].reading.add("およ-ぐ");

    	kanji[34] = new Kanji("英", "艸", 8);
    	kanji[34].reading.add("エイ");

    	kanji[35] = new Kanji("映", "日", 9);
    	kanji[35].reading.add("エイ");
    	kanji[35].reading.add("うつ-る");
    	kanji[35].reading.add("うつ-す");
    	kanji[35].reading.add("は-える");

    	kanji[36] = new Kanji("栄", "木", 9);
    	kanji[36].reading.add("エイ");
    	kanji[36].reading.add("さか-える");
    	kanji[36].reading.add("は-え");
    	kanji[36].reading.add("は-える");

    	kanji[37] = new Kanji("営", "火", 12);
    	kanji[37].reading.add("エイ");
    	kanji[37].reading.add("いとな-む");

    	kanji[38] = new Kanji("衛", "行", 16);
    	kanji[38].reading.add("エイ");

    	kanji[39] = new Kanji("易", "日", 8);
    	kanji[39].reading.add("エキ");
    	kanji[39].reading.add("イ");
    	kanji[39].reading.add("やさ-しい");

    	kanji[40] = new Kanji("益", "皿", 10);
    	kanji[40].reading.add("エキ");

    	kanji[41] = new Kanji("液", "水", 11);
    	kanji[41].reading.add("エキ");

    	kanji[42] = new Kanji("駅", "馬", 14);
    	kanji[42].reading.add("エキ");

    	kanji[43] = new Kanji("円", "囗", 4);
    	kanji[43].reading.add("エン");
    	kanji[43].reading.add("まる-い");

    	kanji[44] = new Kanji("延", "廴", 8);
    	kanji[44].reading.add("エン");
    	kanji[44].reading.add("の-びる");
    	kanji[44].reading.add("の-べる");
    	kanji[44].reading.add("の-ばす");

    	kanji[45] = new Kanji("沿", "水", 8);
    	kanji[45].reading.add("エン");
    	kanji[45].reading.add("そ-う");

    	kanji[46] = new Kanji("園", "囗", 13);
    	kanji[46].reading.add("エン");
    	kanji[46].reading.add("その");

    	kanji[47] = new Kanji("遠", "辵", 13);
    	kanji[47].reading.add("エン");
    	kanji[47].reading.add("とお-い");

    	kanji[48] = new Kanji("塩", "鹵", 13);
    	kanji[48].reading.add("エン");
    	kanji[48].reading.add("しお");

    	kanji[49] = new Kanji("演", "水", 14);
    	kanji[49].reading.add("エン");

    	kanji[50] = new Kanji("王", "玉", 4);
    	kanji[50].reading.add("オウ");

    	kanji[51] = new Kanji("央", "大", 5);
    	kanji[51].reading.add("オウ");

    	kanji[52] = new Kanji("応", "心", 7);
    	kanji[52].reading.add("オウ");
    	kanji[52].reading.add("こた-える");

    	kanji[53] = new Kanji("往", "彳", 8);
    	kanji[53].reading.add("オウ");

    	kanji[54] = new Kanji("桜", "木", 10);
    	kanji[54].reading.add("オウ");
    	kanji[54].reading.add("さくら");

    	kanji[55] = new Kanji("横", "木", 15);
    	kanji[55].reading.add("オウ");
    	kanji[55].reading.add("よこ");

    	kanji[56] = new Kanji("屋", "尸", 9);
    	kanji[56].reading.add("オク");
    	kanji[56].reading.add("や");

    	kanji[57] = new Kanji("億", "人", 15);
    	kanji[57].reading.add("オク");

    	kanji[58] = new Kanji("音", "音", 9);
    	kanji[58].reading.add("オン");
    	kanji[58].reading.add("イン");
    	kanji[58].reading.add("おと");
    	kanji[58].reading.add("ね");

    	kanji[59] = new Kanji("恩", "心", 10);
    	kanji[59].reading.add("オン");

    	kanji[60] = new Kanji("温", "水", 12);
    	kanji[60].reading.add("オン");
    	kanji[60].reading.add("あたた-か");
    	kanji[60].reading.add("あたた-かい");
    	kanji[60].reading.add("あたた-まる");
    	kanji[60].reading.add("あたた-める");

    	kanji[61] = new Kanji("下", "一", 3);
    	kanji[61].reading.add("カ");
    	kanji[61].reading.add("ゲ");
    	kanji[61].reading.add("した");
    	kanji[61].reading.add("しも");
    	kanji[61].reading.add("もと");
    	kanji[61].reading.add("さ-げる");
    	kanji[61].reading.add("さ-がる");
    	kanji[61].reading.add("くだ-る");
    	kanji[61].reading.add("くだ-す");
    	kanji[61].reading.add("くだ-さる");
    	kanji[61].reading.add("お-ろす");
    	kanji[61].reading.add("お-りる");

    	kanji[62] = new Kanji("化", "匕", 4);
    	kanji[62].reading.add("カ");
    	kanji[62].reading.add("ケ");
    	kanji[62].reading.add("ば-ける");
    	kanji[62].reading.add("ば-かす");

    	kanji[63] = new Kanji("火", "火", 4);
    	kanji[63].reading.add("カ");
    	kanji[63].reading.add("ひ");

    	kanji[64] = new Kanji("加", "力", 5);
    	kanji[64].reading.add("カ");
    	kanji[64].reading.add("くわ-える");
    	kanji[64].reading.add("くわ-わる");

    	kanji[65] = new Kanji("可", "口", 5);
    	kanji[65].reading.add("カ");

    	kanji[66] = new Kanji("仮", "人", 6);
    	kanji[66].reading.add("カ");
    	kanji[66].reading.add("かり");

    	kanji[67] = new Kanji("何", "人", 7);
    	kanji[67].reading.add("カ");
    	kanji[67].reading.add("なに");

    	kanji[68] = new Kanji("花", "艸", 7);
    	kanji[68].reading.add("カ");
    	kanji[68].reading.add("はな");

    	kanji[69] = new Kanji("価", "人", 8);
    	kanji[69].reading.add("カ");
    	kanji[69].reading.add("あたい");

    	kanji[70] = new Kanji("果", "木", 8);
    	kanji[70].reading.add("カ");
    	kanji[70].reading.add("は-たす");
    	kanji[70].reading.add("は-てる");
    	kanji[70].reading.add("は-て");

    	kanji[71] = new Kanji("河", "水", 8);
    	kanji[71].reading.add("カ");
    	kanji[71].reading.add("かわ");

    	kanji[72] = new Kanji("科", "禾", 9);
    	kanji[72].reading.add("カ");

    	kanji[73] = new Kanji("夏", "夊", 10);
    	kanji[73].reading.add("カ");
    	kanji[73].reading.add("なつ");

    	kanji[74] = new Kanji("家", "宀", 10);
    	kanji[74].reading.add("カ");
    	kanji[74].reading.add("ケ");
    	kanji[74].reading.add("いえ");
    	kanji[74].reading.add("や");

    	kanji[75] = new Kanji("荷", "艸", 10);
    	kanji[75].reading.add("カ");
    	kanji[75].reading.add("に");

    	kanji[76] = new Kanji("貨", "貝", 11);
    	kanji[76].reading.add("カ");

    	kanji[77] = new Kanji("過", "辵", 12);
    	kanji[77].reading.add("カ");
    	kanji[77].reading.add("す-ぎる");
    	kanji[77].reading.add("す-ごす");
    	kanji[77].reading.add("あやま-つ");
    	kanji[77].reading.add("あやま-ち");

    	kanji[78] = new Kanji("歌", "欠", 14);
    	kanji[78].reading.add("カ");
    	kanji[78].reading.add("うた");
    	kanji[78].reading.add("うた-う");

    	kanji[79] = new Kanji("課", "言", 15);
    	kanji[79].reading.add("カ");

    	kanji[80] = new Kanji("我", "戈", 7);
    	kanji[80].reading.add("ガ");
    	kanji[80].reading.add("われ");
    	kanji[80].reading.add("わ");

    	kanji[81] = new Kanji("画", "田", 8);
    	kanji[81].reading.add("ガ");
    	kanji[81].reading.add("カク");

    	kanji[82] = new Kanji("芽", "艸", 8);
    	kanji[82].reading.add("ガ");
    	kanji[82].reading.add("め");

    	kanji[83] = new Kanji("賀", "貝", 12);
    	kanji[83].reading.add("ガ");

    	kanji[84] = new Kanji("回", "囗", 6);
    	kanji[84].reading.add("カイ");
    	kanji[84].reading.add("まわ-る");
    	kanji[84].reading.add("まわ-す");

    	kanji[85] = new Kanji("灰", "火", 6);
    	kanji[85].reading.add("カイ");
    	kanji[85].reading.add("はい");

    	kanji[86] = new Kanji("会", "曰", 6);
    	kanji[86].reading.add("カイ");
    	kanji[86].reading.add("エ");
    	kanji[86].reading.add("あ-う");

    	kanji[87] = new Kanji("快", "心", 7);
    	kanji[87].reading.add("カイ");
    	kanji[87].reading.add("こころよ-い");

    	kanji[88] = new Kanji("改", "攴", 7);
    	kanji[88].reading.add("カイ");
    	kanji[88].reading.add("あらた-める");
    	kanji[88].reading.add("あらた-まる");

    	kanji[89] = new Kanji("海", "水", 9);
    	kanji[89].reading.add("カイ");
    	kanji[89].reading.add("うみ");

    	kanji[90] = new Kanji("界", "田", 9);
    	kanji[90].reading.add("カイ");

    	kanji[91] = new Kanji("械", "木", 11);
    	kanji[91].reading.add("カイ");

    	kanji[92] = new Kanji("絵", "糸", 12);
    	kanji[92].reading.add("カイ");
    	kanji[92].reading.add("エ");

    	kanji[93] = new Kanji("開", "門", 12);
    	kanji[93].reading.add("カイ");
    	kanji[93].reading.add("ひら-く");
    	kanji[93].reading.add("ひら-ける");
    	kanji[93].reading.add("あ-く");
    	kanji[93].reading.add("あ-ける");

    	kanji[94] = new Kanji("階", "阜", 12);
    	kanji[94].reading.add("カイ");

    	kanji[95] = new Kanji("解", "角", 13);
    	kanji[95].reading.add("カイ");
    	kanji[95].reading.add("ゲ");
    	kanji[95].reading.add("と-く");
    	kanji[95].reading.add("と-かす");
    	kanji[95].reading.add("と-ける");

    	kanji[96] = new Kanji("貝", "貝", 7);
    	kanji[96].reading.add("かい");

    	kanji[97] = new Kanji("外", "夕", 5);
    	kanji[97].reading.add("ガイ");
    	kanji[97].reading.add("ゲ");
    	kanji[97].reading.add("そと");
    	kanji[97].reading.add("ほか");
    	kanji[97].reading.add("はず-す");
    	kanji[97].reading.add("はず-れる");

    	kanji[98] = new Kanji("害", "宀", 10);
    	kanji[98].reading.add("ガイ");

    	kanji[99] = new Kanji("街", "行", 12);
    	kanji[99].reading.add("ガイ");
    	kanji[99].reading.add("まち");

    	kanji[100] = new Kanji("各", "口", 6);
    	kanji[100].reading.add("カク");
    	kanji[100].reading.add("おのおの");

    	kanji[101] = new Kanji("角", "角", 7);
    	kanji[101].reading.add("カク");
    	kanji[101].reading.add("かど");
    	kanji[101].reading.add("つの");

    	kanji[102] = new Kanji("拡", "手", 8);
    	kanji[102].reading.add("カク");

    	kanji[103] = new Kanji("革", "革", 9);
    	kanji[103].reading.add("カク");
    	kanji[103].reading.add("かわ");

    	kanji[104] = new Kanji("格", "木", 10);
    	kanji[104].reading.add("カク");

    	kanji[105] = new Kanji("覚", "見", 12);
    	kanji[105].reading.add("カク");
    	kanji[105].reading.add("おぼ-える");
    	kanji[105].reading.add("さ-ます");
    	kanji[105].reading.add("さ-める");

    	kanji[106] = new Kanji("閣", "門", 14);
    	kanji[106].reading.add("カク");

    	kanji[107] = new Kanji("確", "石", 15);
    	kanji[107].reading.add("カク");
    	kanji[107].reading.add("たし-か");
    	kanji[107].reading.add("たし-かめる");

    	kanji[108] = new Kanji("学", "子", 8);
    	kanji[108].reading.add("ガク");
    	kanji[108].reading.add("まな-ぶ");

    	kanji[109] = new Kanji("楽", "木", 13);
    	kanji[109].reading.add("ガク");
    	kanji[109].reading.add("ラク");
    	kanji[109].reading.add("たの-しい");
    	kanji[109].reading.add("たの-しむ");

    	kanji[110] = new Kanji("額", "頁", 18);
    	kanji[110].reading.add("ガク");
    	kanji[110].reading.add("ひたい");

    	kanji[111] = new Kanji("活", "水", 9);
    	kanji[111].reading.add("カツ");

    	kanji[112] = new Kanji("割", "刀", 12);
    	kanji[112].reading.add("カツ");
    	kanji[112].reading.add("わ-る");
    	kanji[112].reading.add("わり");
    	kanji[112].reading.add("わ-れる");
    	kanji[112].reading.add("さ-く");

    	kanji[113] = new Kanji("株", "木", 10);
    	kanji[113].reading.add("かぶ");

    	kanji[114] = new Kanji("干", "干", 3);
    	kanji[114].reading.add("カン");
    	kanji[114].reading.add("ほ-す");
    	kanji[114].reading.add("ひ-る");

    	kanji[115] = new Kanji("刊", "刀", 5);
    	kanji[115].reading.add("カン");

    	kanji[116] = new Kanji("完", "宀", 7);
    	kanji[116].reading.add("カン");

    	kanji[117] = new Kanji("官", "宀", 8);
    	kanji[117].reading.add("カン");

    	kanji[118] = new Kanji("巻", "卩", 9);
    	kanji[118].reading.add("カン");
    	kanji[118].reading.add("ま-く");
    	kanji[118].reading.add("まき");

    	kanji[119] = new Kanji("看", "目", 9);
    	kanji[119].reading.add("カン");

    	kanji[120] = new Kanji("寒", "宀", 12);
    	kanji[120].reading.add("カン");
    	kanji[120].reading.add("さむ-い");

    	kanji[121] = new Kanji("間", "門", 12);
    	kanji[121].reading.add("カン");
    	kanji[121].reading.add("ケン");
    	kanji[121].reading.add("あいだ");
    	kanji[121].reading.add("ま");

    	kanji[122] = new Kanji("幹", "干", 13);
    	kanji[122].reading.add("カン");
    	kanji[122].reading.add("みき");

    	kanji[123] = new Kanji("感", "心", 13);
    	kanji[123].reading.add("カン");

    	kanji[124] = new Kanji("漢", "水", 13);
    	kanji[124].reading.add("カン");

    	kanji[125] = new Kanji("慣", "心", 14);
    	kanji[125].reading.add("カン");
    	kanji[125].reading.add("な-れる");
    	kanji[125].reading.add("な-らす");

    	kanji[126] = new Kanji("管", "竹", 14);
    	kanji[126].reading.add("カン");
    	kanji[126].reading.add("くだ");

    	kanji[127] = new Kanji("関", "門", 14);
    	kanji[127].reading.add("カン");
    	kanji[127].reading.add("せき");
    	kanji[127].reading.add("かか-わる");

    	kanji[128] = new Kanji("館", "食", 16);
    	kanji[128].reading.add("カン");
    	kanji[128].reading.add("やかた");

    	kanji[129] = new Kanji("簡", "竹", 18);
    	kanji[129].reading.add("カン");

    	kanji[130] = new Kanji("観", "見", 18);
    	kanji[130].reading.add("カン");

    	kanji[131] = new Kanji("丸", "丶", 3);
    	kanji[131].reading.add("ガン");
    	kanji[131].reading.add("まる");
    	kanji[131].reading.add("まる-い");
    	kanji[131].reading.add("まる-める");

    	kanji[132] = new Kanji("岸", "山", 8);
    	kanji[132].reading.add("ガン");
    	kanji[132].reading.add("きし");

    	kanji[133] = new Kanji("岩", "山", 8);
    	kanji[133].reading.add("ガン");
    	kanji[133].reading.add("いわ");

    	kanji[134] = new Kanji("眼", "目", 11);
    	kanji[134].reading.add("ガン");
    	kanji[134].reading.add("まなこ");

    	kanji[135] = new Kanji("顔", "頁", 18);
    	kanji[135].reading.add("ガン");
    	kanji[135].reading.add("かお");

    	kanji[136] = new Kanji("願", "頁", 19);
    	kanji[136].reading.add("ガン");
    	kanji[136].reading.add("ねが-う");

    	kanji[137] = new Kanji("危", "卩", 6);
    	kanji[137].reading.add("キ");
    	kanji[137].reading.add("あぶ-ない");
    	kanji[137].reading.add("あや-うい");
    	kanji[137].reading.add("あや-ぶむ");

    	kanji[138] = new Kanji("机", "木", 6);
    	kanji[138].reading.add("キ");
    	kanji[138].reading.add("つくえ");

    	kanji[139] = new Kanji("気", "气", 6);
    	kanji[139].reading.add("キ");
    	kanji[139].reading.add("ケ");

    	kanji[140] = new Kanji("希", "巾", 7);
    	kanji[140].reading.add("キ");

    	kanji[141] = new Kanji("汽", "水", 7);
    	kanji[141].reading.add("キ");

    	kanji[142] = new Kanji("季", "子", 8);
    	kanji[142].reading.add("キ");

    	kanji[143] = new Kanji("紀", "糸", 9);
    	kanji[143].reading.add("キ");

    	kanji[144] = new Kanji("記", "言", 10);
    	kanji[144].reading.add("キ");
    	kanji[144].reading.add("しる-す");

    	kanji[145] = new Kanji("起", "走", 10);
    	kanji[145].reading.add("キ");
    	kanji[145].reading.add("お-きる");
    	kanji[145].reading.add("お-こる");
    	kanji[145].reading.add("お-こす");

    	kanji[146] = new Kanji("帰", "止", 10);
    	kanji[146].reading.add("キ");
    	kanji[146].reading.add("かえ-る");
    	kanji[146].reading.add("かえ-す");

    	kanji[147] = new Kanji("基", "土", 11);
    	kanji[147].reading.add("キ");
    	kanji[147].reading.add("もと");
    	kanji[147].reading.add("もとい");

    	kanji[148] = new Kanji("寄", "宀", 11);
    	kanji[148].reading.add("キ");
    	kanji[148].reading.add("よ-る");
    	kanji[148].reading.add("よ-せる");

    	kanji[149] = new Kanji("規", "見", 11);
    	kanji[149].reading.add("キ");

    	kanji[150] = new Kanji("喜", "口", 12);
    	kanji[150].reading.add("キ");
    	kanji[150].reading.add("よろこ-ぶ");

    	kanji[151] = new Kanji("揮", "手", 12);
    	kanji[151].reading.add("キ");

    	kanji[152] = new Kanji("期", "月", 12);
    	kanji[152].reading.add("キ");

    	kanji[153] = new Kanji("貴", "貝", 12);
    	kanji[153].reading.add("キ");
    	kanji[153].reading.add("たっと-い");
    	kanji[153].reading.add("とうと-い");
    	kanji[153].reading.add("たっと-ぶ");
    	kanji[153].reading.add("とうと-ぶ");

    	kanji[154] = new Kanji("旗", "方", 14);
    	kanji[154].reading.add("キ");
    	kanji[154].reading.add("はた");

    	kanji[155] = new Kanji("器", "口", 15);
    	kanji[155].reading.add("キ");
    	kanji[155].reading.add("うつわ");

    	kanji[156] = new Kanji("機", "木", 16);
    	kanji[156].reading.add("キ");
    	kanji[156].reading.add("はた");

    	kanji[157] = new Kanji("技", "手", 7);
    	kanji[157].reading.add("ギ");
    	kanji[157].reading.add("わざ");

    	kanji[158] = new Kanji("義", "羊", 13);
    	kanji[158].reading.add("ギ");

    	kanji[159] = new Kanji("疑", "疋", 14);
    	kanji[159].reading.add("ギ");
    	kanji[159].reading.add("うたが-う");

    	kanji[160] = new Kanji("議", "言", 20);
    	kanji[160].reading.add("ギ");

    	kanji[161] = new Kanji("客", "宀", 9);
    	kanji[161].reading.add("キャク");
    	kanji[161].reading.add("カク");

    	kanji[162] = new Kanji("逆", "辵", 9);
    	kanji[162].reading.add("ギャク");
    	kanji[162].reading.add("さか");
    	kanji[162].reading.add("さか-らう");

    	kanji[163] = new Kanji("九", "乙", 2);
    	kanji[163].reading.add("キュウ");
    	kanji[163].reading.add("ク");
    	kanji[163].reading.add("ここの");
    	kanji[163].reading.add("ここの-つ");

    	kanji[164] = new Kanji("久", "丿", 3);
    	kanji[164].reading.add("キュウ");
    	kanji[164].reading.add("ひさ-しい");

    	kanji[165] = new Kanji("弓", "弓", 3);
    	kanji[165].reading.add("キュウ");
    	kanji[165].reading.add("ゆみ");

    	kanji[166] = new Kanji("旧", "臼", 5);
    	kanji[166].reading.add("キュウ");

    	kanji[167] = new Kanji("休", "人", 6);
    	kanji[167].reading.add("キュウ");
    	kanji[167].reading.add("やす-む");
    	kanji[167].reading.add("やす-まる");
    	kanji[167].reading.add("やす-める");

    	kanji[168] = new Kanji("吸", "口", 6);
    	kanji[168].reading.add("キュウ");
    	kanji[168].reading.add("す-う");

    	kanji[169] = new Kanji("求", "水", 7);
    	kanji[169].reading.add("キュウ");
    	kanji[169].reading.add("もと-める");

    	kanji[170] = new Kanji("究", "穴", 7);
    	kanji[170].reading.add("キュウ");
    	kanji[170].reading.add("きわ-める");

    	kanji[171] = new Kanji("泣", "水", 8);
    	kanji[171].reading.add("キュウ");
    	kanji[171].reading.add("な-く");

    	kanji[172] = new Kanji("急", "心", 9);
    	kanji[172].reading.add("キュウ");
    	kanji[172].reading.add("いそ-ぐ");

    	kanji[173] = new Kanji("級", "糸", 9);
    	kanji[173].reading.add("キュウ");

    	kanji[174] = new Kanji("宮", "宀", 10);
    	kanji[174].reading.add("キュウ");
    	kanji[174].reading.add("グウ");
    	kanji[174].reading.add("みや");

    	kanji[175] = new Kanji("救", "攴", 11);
    	kanji[175].reading.add("キュウ");
    	kanji[175].reading.add("すく-う");

    	kanji[176] = new Kanji("球", "玉", 11);
    	kanji[176].reading.add("キュウ");
    	kanji[176].reading.add("たま");

    	kanji[177] = new Kanji("給", "糸", 12);
    	kanji[177].reading.add("キュウ");

    	kanji[178] = new Kanji("牛", "牛", 4);
    	kanji[178].reading.add("ギュウ");
    	kanji[178].reading.add("うし");

    	kanji[179] = new Kanji("去", "厶", 5);
    	kanji[179].reading.add("キョ");
    	kanji[179].reading.add("コ");
    	kanji[179].reading.add("さ-る");

    	kanji[180] = new Kanji("居", "尸", 8);
    	kanji[180].reading.add("キョ");
    	kanji[180].reading.add("い-る");

    	kanji[181] = new Kanji("挙", "手", 10);
    	kanji[181].reading.add("キョ");
    	kanji[181].reading.add("あ-げる");
    	kanji[181].reading.add("あ-がる");

    	kanji[182] = new Kanji("許", "言", 11);
    	kanji[182].reading.add("キョ");
    	kanji[182].reading.add("ゆる-す");

    	kanji[183] = new Kanji("魚", "魚", 11);
    	kanji[183].reading.add("ギョ");
    	kanji[183].reading.add("うお");
    	kanji[183].reading.add("さかな");

    	kanji[184] = new Kanji("漁", "水", 14);
    	kanji[184].reading.add("ギョ");
    	kanji[184].reading.add("リョウ");

    	kanji[185] = new Kanji("共", "八", 6);
    	kanji[185].reading.add("キョウ");
    	kanji[185].reading.add("とも");

    	kanji[186] = new Kanji("京", "亠", 8);
    	kanji[186].reading.add("キョウ");
    	kanji[186].reading.add("ケイ");

    	kanji[187] = new Kanji("供", "人", 8);
    	kanji[187].reading.add("キョウ");
    	kanji[187].reading.add("そな-える");
    	kanji[187].reading.add("とも");

    	kanji[188] = new Kanji("協", "十", 8);
    	kanji[188].reading.add("キョウ");

    	kanji[189] = new Kanji("胸", "肉", 10);
    	kanji[189].reading.add("キョウ");
    	kanji[189].reading.add("むね");

    	kanji[190] = new Kanji("強", "弓", 11);
    	kanji[190].reading.add("キョウ");
    	kanji[190].reading.add("ゴウ");
    	kanji[190].reading.add("つよ-い");
    	kanji[190].reading.add("つよ-まる");
    	kanji[190].reading.add("つよ-める");
    	kanji[190].reading.add("し-いる");

    	kanji[191] = new Kanji("教", "攴", 11);
    	kanji[191].reading.add("キョウ");
    	kanji[191].reading.add("おし-える");
    	kanji[191].reading.add("おそ-わる");

    	kanji[192] = new Kanji("郷", "邑", 11);
    	kanji[192].reading.add("キョウ");
    	kanji[192].reading.add("ゴウ");

    	kanji[193] = new Kanji("境", "土", 14);
    	kanji[193].reading.add("キョウ");
    	kanji[193].reading.add("さかい");

    	kanji[194] = new Kanji("橋", "木", 16);
    	kanji[194].reading.add("キョウ");
    	kanji[194].reading.add("はし");

    	kanji[195] = new Kanji("鏡", "金", 19);
    	kanji[195].reading.add("キョウ");
    	kanji[195].reading.add("かがみ");

    	kanji[196] = new Kanji("競", "立", 20);
    	kanji[196].reading.add("キョウ");
    	kanji[196].reading.add("ケイ");
    	kanji[196].reading.add("きそ-う");
    	kanji[196].reading.add("せ-る");

    	kanji[197] = new Kanji("業", "木", 13);
    	kanji[197].reading.add("ギョウ");
    	kanji[197].reading.add("ゴウ");
    	kanji[197].reading.add("わざ");

    	kanji[198] = new Kanji("曲", "曰", 6);
    	kanji[198].reading.add("キョク");
    	kanji[198].reading.add("ま-がる");
    	kanji[198].reading.add("ま-げる");

    	kanji[199] = new Kanji("局", "尸", 7);
    	kanji[199].reading.add("キョク");

    	kanji[200] = new Kanji("極", "木", 12);
    	kanji[200].reading.add("キョク");
    	kanji[200].reading.add("ゴク");
    	kanji[200].reading.add("きわ-める");
    	kanji[200].reading.add("きわ-まる");
    	kanji[200].reading.add("きわ-み");

    	kanji[201] = new Kanji("玉", "玉", 5);
    	kanji[201].reading.add("ギョク");
    	kanji[201].reading.add("たま");

    	kanji[202] = new Kanji("均", "土", 7);
    	kanji[202].reading.add("キン");

    	kanji[203] = new Kanji("近", "辵", 7);
    	kanji[203].reading.add("キン");
    	kanji[203].reading.add("ちか-い");

    	kanji[204] = new Kanji("金", "金", 8);
    	kanji[204].reading.add("キン");
    	kanji[204].reading.add("コン");
    	kanji[204].reading.add("かね");

    	kanji[205] = new Kanji("勤", "力", 12);
    	kanji[205].reading.add("キン");
    	kanji[205].reading.add("つと-める");
    	kanji[205].reading.add("つと-まる");

    	kanji[206] = new Kanji("筋", "竹", 12);
    	kanji[206].reading.add("キン");
    	kanji[206].reading.add("すじ");

    	kanji[207] = new Kanji("禁", "示", 13);
    	kanji[207].reading.add("キン");

    	kanji[208] = new Kanji("銀", "金", 14);
    	kanji[208].reading.add("ギン");

    	kanji[209] = new Kanji("区", "匸", 4);
    	kanji[209].reading.add("ク");

    	kanji[210] = new Kanji("句", "口", 5);
    	kanji[210].reading.add("ク");

    	kanji[211] = new Kanji("苦", "艸", 8);
    	kanji[211].reading.add("ク");
    	kanji[211].reading.add("くる-しい");
    	kanji[211].reading.add("くる-しむ");
    	kanji[211].reading.add("くる-しめる");
    	kanji[211].reading.add("にが-い");
    	kanji[211].reading.add("にが-る");

    	kanji[212] = new Kanji("具", "八", 8);
    	kanji[212].reading.add("グ");

    	kanji[213] = new Kanji("空", "穴", 8);
    	kanji[213].reading.add("クウ");
    	kanji[213].reading.add("そら");
    	kanji[213].reading.add("あ-く");
    	kanji[213].reading.add("あ-ける");
    	kanji[213].reading.add("から");

    	kanji[214] = new Kanji("君", "口", 7);
    	kanji[214].reading.add("クン");
    	kanji[214].reading.add("きみ");

    	kanji[215] = new Kanji("訓", "言", 10);
    	kanji[215].reading.add("クン");

    	kanji[216] = new Kanji("軍", "車", 9);
    	kanji[216].reading.add("グン");

    	kanji[217] = new Kanji("郡", "邑", 10);
    	kanji[217].reading.add("グン");

    	kanji[218] = new Kanji("群", "羊", 13);
    	kanji[218].reading.add("グン");
    	kanji[218].reading.add("む-れる");
    	kanji[218].reading.add("む-れ");

    	kanji[219] = new Kanji("兄", "儿", 5);
    	kanji[219].reading.add("ケイ");
    	kanji[219].reading.add("あに");

    	kanji[220] = new Kanji("形", "彡", 7);
    	kanji[220].reading.add("ケイ");
    	kanji[220].reading.add("ギョウ");
    	kanji[220].reading.add("かた");
    	kanji[220].reading.add("かたち");

    	kanji[221] = new Kanji("系", "糸", 7);
    	kanji[221].reading.add("ケイ");

    	kanji[222] = new Kanji("径", "彳", 8);
    	kanji[222].reading.add("ケイ");

    	kanji[223] = new Kanji("係", "人", 9);
    	kanji[223].reading.add("ケイ");
    	kanji[223].reading.add("かか-る");
    	kanji[223].reading.add("かかり");

    	kanji[224] = new Kanji("型", "土", 9);
    	kanji[224].reading.add("ケイ");
    	kanji[224].reading.add("かた");

    	kanji[225] = new Kanji("計", "言", 9);
    	kanji[225].reading.add("ケイ");
    	kanji[225].reading.add("はか-る");
    	kanji[225].reading.add("はか-らう");

    	kanji[226] = new Kanji("経", "糸", 11);
    	kanji[226].reading.add("ケイ");
    	kanji[226].reading.add("キョウ");
    	kanji[226].reading.add("へ-る");

    	kanji[227] = new Kanji("敬", "攴", 12);
    	kanji[227].reading.add("ケイ");
    	kanji[227].reading.add("うやま-う");

    	kanji[228] = new Kanji("景", "日", 12);
    	kanji[228].reading.add("ケイ");

    	kanji[229] = new Kanji("軽", "車", 12);
    	kanji[229].reading.add("ケイ");
    	kanji[229].reading.add("かる-い");
    	kanji[229].reading.add("かろ-やか");

    	kanji[230] = new Kanji("警", "言", 19);
    	kanji[230].reading.add("ケイ");

    	kanji[231] = new Kanji("芸", "艸", 7);
    	kanji[231].reading.add("ゲイ");

    	kanji[232] = new Kanji("劇", "刀", 15);
    	kanji[232].reading.add("ゲキ");

    	kanji[233] = new Kanji("激", "水", 16);
    	kanji[233].reading.add("ゲキ");
    	kanji[233].reading.add("はげ-しい");

    	kanji[234] = new Kanji("欠", "缶", 4);
    	kanji[234].reading.add("ケツ");
    	kanji[234].reading.add("か-ける");
    	kanji[234].reading.add("か-く");

    	kanji[235] = new Kanji("穴", "穴", 5);
    	kanji[235].reading.add("ケツ");
    	kanji[235].reading.add("あな");

    	kanji[236] = new Kanji("血", "血", 6);
    	kanji[236].reading.add("ケツ");
    	kanji[236].reading.add("ち");

    	kanji[237] = new Kanji("決", "水", 7);
    	kanji[237].reading.add("ケツ");
    	kanji[237].reading.add("き-める");
    	kanji[237].reading.add("き-まる");

    	kanji[238] = new Kanji("結", "糸", 12);
    	kanji[238].reading.add("ケツ");
    	kanji[238].reading.add("むす-ぶ");
    	kanji[238].reading.add("ゆ-う");
    	kanji[238].reading.add("ゆ-わえる");

    	kanji[239] = new Kanji("潔", "水", 15);
    	kanji[239].reading.add("ケツ");
    	kanji[239].reading.add("いさぎよ-い");

    	kanji[240] = new Kanji("月", "月", 4);
    	kanji[240].reading.add("ゲツ");
    	kanji[240].reading.add("ガツ");
    	kanji[240].reading.add("つき");

    	kanji[241] = new Kanji("犬", "犬", 4);
    	kanji[241].reading.add("ケン");
    	kanji[241].reading.add("いぬ");

    	kanji[242] = new Kanji("件", "人", 6);
    	kanji[242].reading.add("ケン");

    	kanji[243] = new Kanji("見", "見", 7);
    	kanji[243].reading.add("ケン");
    	kanji[243].reading.add("み-る");
    	kanji[243].reading.add("み-える");
    	kanji[243].reading.add("み-せる");

    	kanji[244] = new Kanji("券", "刀", 8);
    	kanji[244].reading.add("ケン");

    	kanji[245] = new Kanji("建", "廴", 9);
    	kanji[245].reading.add("ケン");
    	kanji[245].reading.add("た-てる");
    	kanji[245].reading.add("た-つ");

    	kanji[246] = new Kanji("研", "石", 9);
    	kanji[246].reading.add("ケン");
    	kanji[246].reading.add("と-ぐ");

    	kanji[247] = new Kanji("県", "糸", 9);
    	kanji[247].reading.add("ケン");

    	kanji[248] = new Kanji("健", "人", 11);
    	kanji[248].reading.add("ケン");
    	kanji[248].reading.add("すこ-やか");

    	kanji[249] = new Kanji("険", "阜", 11);
    	kanji[249].reading.add("ケン");
    	kanji[249].reading.add("けわ-しい");

    	kanji[250] = new Kanji("検", "木", 12);
    	kanji[250].reading.add("ケン");

    	kanji[251] = new Kanji("絹", "糸", 13);
    	kanji[251].reading.add("ケン");
    	kanji[251].reading.add("きぬ");

    	kanji[252] = new Kanji("権", "木", 15);
    	kanji[252].reading.add("ケン");

    	kanji[253] = new Kanji("憲", "心", 16);
    	kanji[253].reading.add("ケン");

    	kanji[254] = new Kanji("験", "馬", 18);
    	kanji[254].reading.add("ケン");

    	kanji[255] = new Kanji("元", "儿", 4);
    	kanji[255].reading.add("ゲン");
    	kanji[255].reading.add("ガン");
    	kanji[255].reading.add("もと");

    	kanji[256] = new Kanji("言", "言", 7);
    	kanji[256].reading.add("ゲン");
    	kanji[256].reading.add("ゴン");
    	kanji[256].reading.add("い-う");
    	kanji[256].reading.add("こと");

    	kanji[257] = new Kanji("限", "阜", 9);
    	kanji[257].reading.add("ゲン");
    	kanji[257].reading.add("かぎ-る");

    	kanji[258] = new Kanji("原", "厂", 10);
    	kanji[258].reading.add("ゲン");
    	kanji[258].reading.add("はら");

    	kanji[259] = new Kanji("現", "玉", 11);
    	kanji[259].reading.add("ゲン");
    	kanji[259].reading.add("あらわ-れる");
    	kanji[259].reading.add("あらわ-す");

    	kanji[260] = new Kanji("減", "水", 12);
    	kanji[260].reading.add("ゲン");
    	kanji[260].reading.add("へ-る");
    	kanji[260].reading.add("へ-らす");

    	kanji[261] = new Kanji("源", "水", 13);
    	kanji[261].reading.add("ゲン");
    	kanji[261].reading.add("みなもと");

    	kanji[262] = new Kanji("厳", "口", 17);
    	kanji[262].reading.add("ゲン");
    	kanji[262].reading.add("おごそ-か");
    	kanji[262].reading.add("きび-しい");

    	kanji[263] = new Kanji("己", "己", 3);
    	kanji[263].reading.add("コ");
    	kanji[263].reading.add("キ");
    	kanji[263].reading.add("おのれ");

    	kanji[264] = new Kanji("戸", "戶", 4);
    	kanji[264].reading.add("コ");
    	kanji[264].reading.add("と");

    	kanji[265] = new Kanji("古", "口", 5);
    	kanji[265].reading.add("コ");
    	kanji[265].reading.add("ふる-い");
    	kanji[265].reading.add("ふる-す");

    	kanji[266] = new Kanji("呼", "口", 8);
    	kanji[266].reading.add("コ");
    	kanji[266].reading.add("よ-ぶ");

    	kanji[267] = new Kanji("固", "囗", 8);
    	kanji[267].reading.add("コ");
    	kanji[267].reading.add("かた-める");
    	kanji[267].reading.add("かた-まる");
    	kanji[267].reading.add("かた-い");

    	kanji[268] = new Kanji("故", "攴", 9);
    	kanji[268].reading.add("コ");
    	kanji[268].reading.add("ゆえ");

    	kanji[269] = new Kanji("個", "人", 10);
    	kanji[269].reading.add("コ");

    	kanji[270] = new Kanji("庫", "广", 10);
    	kanji[270].reading.add("コ");

    	kanji[271] = new Kanji("湖", "水", 12);
    	kanji[271].reading.add("コ");
    	kanji[271].reading.add("みずうみ");

    	kanji[272] = new Kanji("五", "二", 4);
    	kanji[272].reading.add("ゴ");
    	kanji[272].reading.add("いつ");
    	kanji[272].reading.add("いつ-つ");

    	kanji[273] = new Kanji("午", "十", 4);
    	kanji[273].reading.add("ゴ");

    	kanji[274] = new Kanji("後", "彳", 9);
    	kanji[274].reading.add("ゴ");
    	kanji[274].reading.add("コウ");
    	kanji[274].reading.add("のち");
    	kanji[274].reading.add("うし-ろ");
    	kanji[274].reading.add("あと");
    	kanji[274].reading.add("おく-れる");

    	kanji[275] = new Kanji("語", "言", 14);
    	kanji[275].reading.add("ゴ");
    	kanji[275].reading.add("かた-る");
    	kanji[275].reading.add("かた-らう");

    	kanji[276] = new Kanji("誤", "言", 14);
    	kanji[276].reading.add("ゴ");
    	kanji[276].reading.add("あやま-る");

    	kanji[277] = new Kanji("護", "言", 20);
    	kanji[277].reading.add("ゴ");

    	kanji[278] = new Kanji("口", "口", 3);
    	kanji[278].reading.add("コウ");
    	kanji[278].reading.add("ク");
    	kanji[278].reading.add("くち");

    	kanji[279] = new Kanji("工", "工", 3);
    	kanji[279].reading.add("コウ");
    	kanji[279].reading.add("ク");

    	kanji[280] = new Kanji("公", "八", 4);
    	kanji[280].reading.add("コウ");
    	kanji[280].reading.add("おおやけ");

    	kanji[281] = new Kanji("功", "力", 5);
    	kanji[281].reading.add("コウ");

    	kanji[282] = new Kanji("広", "广", 5);
    	kanji[282].reading.add("コウ");
    	kanji[282].reading.add("ひろ-い");
    	kanji[282].reading.add("ひろ-まる");
    	kanji[282].reading.add("ひろ-める");
    	kanji[282].reading.add("ひろ-がる");
    	kanji[282].reading.add("ひろ-げる");

    	kanji[283] = new Kanji("交", "亠", 6);
    	kanji[283].reading.add("コウ");
    	kanji[283].reading.add("まじ-わる");
    	kanji[283].reading.add("まじ-える");
    	kanji[283].reading.add("ま-じる");
    	kanji[283].reading.add("ま-ざる");
    	kanji[283].reading.add("ま-ぜる");
    	kanji[283].reading.add("か-う");
    	kanji[283].reading.add("か-わす");

    	kanji[284] = new Kanji("光", "儿", 6);
    	kanji[284].reading.add("コウ");
    	kanji[284].reading.add("ひか-る");
    	kanji[284].reading.add("ひかり");

    	kanji[285] = new Kanji("向", "口", 6);
    	kanji[285].reading.add("コウ");
    	kanji[285].reading.add("む-く");
    	kanji[285].reading.add("む-ける");
    	kanji[285].reading.add("む-かう");
    	kanji[285].reading.add("む-こう");

    	kanji[286] = new Kanji("后", "口", 6);
    	kanji[286].reading.add("コウ");

    	kanji[287] = new Kanji("好", "女", 6);
    	kanji[287].reading.add("コウ");
    	kanji[287].reading.add("この-む");
    	kanji[287].reading.add("す-く");

    	kanji[288] = new Kanji("考", "老", 6);
    	kanji[288].reading.add("コウ");
    	kanji[288].reading.add("かんが-える");

    	kanji[289] = new Kanji("行", "行", 6);
    	kanji[289].reading.add("コウ");
    	kanji[289].reading.add("ギョウ");
    	kanji[289].reading.add("い-く");
    	kanji[289].reading.add("ゆ-く");
    	kanji[289].reading.add("おこな-う");

    	kanji[290] = new Kanji("孝", "子", 7);
    	kanji[290].reading.add("コウ");

    	kanji[291] = new Kanji("効", "力", 8);
    	kanji[291].reading.add("コウ");
    	kanji[291].reading.add("き-く");

    	kanji[292] = new Kanji("幸", "干", 8);
    	kanji[292].reading.add("コウ");
    	kanji[292].reading.add("さいわ-い");
    	kanji[292].reading.add("さち");
    	kanji[292].reading.add("しあわ-せ");

    	kanji[293] = new Kanji("厚", "厂", 9);
    	kanji[293].reading.add("コウ");
    	kanji[293].reading.add("あつ-い");

    	kanji[294] = new Kanji("皇", "白", 9);
    	kanji[294].reading.add("コウ");
    	kanji[294].reading.add("オウ");

    	kanji[295] = new Kanji("紅", "糸", 9);
    	kanji[295].reading.add("コウ");
    	kanji[295].reading.add("べに");
    	kanji[295].reading.add("くれない");

    	kanji[296] = new Kanji("候", "人", 10);
    	kanji[296].reading.add("コウ");
    	kanji[296].reading.add("そうろう");

    	kanji[297] = new Kanji("校", "木", 10);
    	kanji[297].reading.add("コウ");

    	kanji[298] = new Kanji("耕", "耒", 10);
    	kanji[298].reading.add("コウ");
    	kanji[298].reading.add("たがや-す");

    	kanji[299] = new Kanji("航", "舟", 10);
    	kanji[299].reading.add("コウ");

    	kanji[300] = new Kanji("降", "阜", 10);
    	kanji[300].reading.add("コウ");
    	kanji[300].reading.add("お-りる");
    	kanji[300].reading.add("お-ろす");
    	kanji[300].reading.add("ふ-る");

    	kanji[301] = new Kanji("高", "高", 10);
    	kanji[301].reading.add("コウ");
    	kanji[301].reading.add("たか-い");
    	kanji[301].reading.add("たか");
    	kanji[301].reading.add("たか-まる");
    	kanji[301].reading.add("たか-める");

    	kanji[302] = new Kanji("康", "广", 11);
    	kanji[302].reading.add("コウ");

    	kanji[303] = new Kanji("黄", "黃", 11);
    	kanji[303].reading.add("コウ");
    	kanji[303].reading.add("オウ");
    	kanji[303].reading.add("き");

    	kanji[304] = new Kanji("港", "水", 12);
    	kanji[304].reading.add("コウ");
    	kanji[304].reading.add("みなと");

    	kanji[305] = new Kanji("鉱", "金", 13);
    	kanji[305].reading.add("コウ");

    	kanji[306] = new Kanji("構", "木", 14);
    	kanji[306].reading.add("コウ");
    	kanji[306].reading.add("かま-える");
    	kanji[306].reading.add("かま-う");

    	kanji[307] = new Kanji("興", "臼", 16);
    	kanji[307].reading.add("コウ");
    	kanji[307].reading.add("キョウ");
    	kanji[307].reading.add("おこ-る");
    	kanji[307].reading.add("おこ-す");

    	kanji[308] = new Kanji("鋼", "金", 16);
    	kanji[308].reading.add("コウ");
    	kanji[308].reading.add("はがね");

    	kanji[309] = new Kanji("講", "言", 17);
    	kanji[309].reading.add("コウ");

    	kanji[310] = new Kanji("号", "虍", 5);
    	kanji[310].reading.add("ゴウ");

    	kanji[311] = new Kanji("合", "口", 6);
    	kanji[311].reading.add("ゴウ");
    	kanji[311].reading.add("ガッ");
    	kanji[311].reading.add("あ-う");
    	kanji[311].reading.add("あ-わす");
    	kanji[311].reading.add("あ-わせる");

    	kanji[312] = new Kanji("告", "口", 7);
    	kanji[312].reading.add("コク");
    	kanji[312].reading.add("つ-げる");

    	kanji[313] = new Kanji("谷", "谷", 7);
    	kanji[313].reading.add("コク");
    	kanji[313].reading.add("たに");

    	kanji[314] = new Kanji("刻", "刀", 8);
    	kanji[314].reading.add("コク");
    	kanji[314].reading.add("きざ-む");

    	kanji[315] = new Kanji("国", "囗", 8);
    	kanji[315].reading.add("コク");
    	kanji[315].reading.add("くに");

    	kanji[316] = new Kanji("黒", "黑", 11);
    	kanji[316].reading.add("コク");
    	kanji[316].reading.add("くろ");
    	kanji[316].reading.add("くろ-い");

    	kanji[317] = new Kanji("穀", "禾", 14);
    	kanji[317].reading.add("コク");

    	kanji[318] = new Kanji("骨", "骨", 10);
    	kanji[318].reading.add("コツ");
    	kanji[318].reading.add("ほね");

    	kanji[319] = new Kanji("今", "人", 4);
    	kanji[319].reading.add("コン");
    	kanji[319].reading.add("キン");
    	kanji[319].reading.add("いま");

    	kanji[320] = new Kanji("困", "囗", 7);
    	kanji[320].reading.add("コン");
    	kanji[320].reading.add("こま-る");

    	kanji[321] = new Kanji("根", "木", 10);
    	kanji[321].reading.add("コン");
    	kanji[321].reading.add("ね");

    	kanji[322] = new Kanji("混", "水", 11);
    	kanji[322].reading.add("コン");
    	kanji[322].reading.add("ま-じる");
    	kanji[322].reading.add("ま-ざる");
    	kanji[322].reading.add("ま-ぜる");
    	kanji[322].reading.add("こ-む");

    	kanji[323] = new Kanji("左", "工", 5);
    	kanji[323].reading.add("サ");
    	kanji[323].reading.add("ひだり");

    	kanji[324] = new Kanji("査", "木", 9);
    	kanji[324].reading.add("サ");

    	kanji[325] = new Kanji("砂", "石", 9);
    	kanji[325].reading.add("サ");
    	kanji[325].reading.add("シャ");
    	kanji[325].reading.add("すな");

    	kanji[326] = new Kanji("差", "工", 10);
    	kanji[326].reading.add("サ");
    	kanji[326].reading.add("さ-す");

    	kanji[327] = new Kanji("座", "广", 10);
    	kanji[327].reading.add("ザ");
    	kanji[327].reading.add("すわ-る");

    	kanji[328] = new Kanji("才", "手", 3);
    	kanji[328].reading.add("サイ");

    	kanji[329] = new Kanji("再", "冂", 6);
    	kanji[329].reading.add("サイ");
    	kanji[329].reading.add("ふたた-び");

    	kanji[330] = new Kanji("災", "火", 7);
    	kanji[330].reading.add("サイ");
    	kanji[330].reading.add("わざわ-い");

    	kanji[331] = new Kanji("妻", "女", 8);
    	kanji[331].reading.add("サイ");
    	kanji[331].reading.add("つま");

    	kanji[332] = new Kanji("採", "手", 11);
    	kanji[332].reading.add("サイ");
    	kanji[332].reading.add("と-る");

    	kanji[333] = new Kanji("済", "水", 11);
    	kanji[333].reading.add("サイ");
    	kanji[333].reading.add("す-む");
    	kanji[333].reading.add("す-ます");

    	kanji[334] = new Kanji("祭", "示", 11);
    	kanji[334].reading.add("サイ");
    	kanji[334].reading.add("まつ-る");
    	kanji[334].reading.add("まつ-り");

    	kanji[335] = new Kanji("細", "糸", 11);
    	kanji[335].reading.add("サイ");
    	kanji[335].reading.add("ほそ-い");
    	kanji[335].reading.add("ほそ-る");
    	kanji[335].reading.add("こま-か");
    	kanji[335].reading.add("こま-かい");

    	kanji[336] = new Kanji("菜", "艸", 11);
    	kanji[336].reading.add("サイ");
    	kanji[336].reading.add("な");

    	kanji[337] = new Kanji("最", "曰", 12);
    	kanji[337].reading.add("サイ");
    	kanji[337].reading.add("もっと-も");

    	kanji[338] = new Kanji("裁", "衣", 12);
    	kanji[338].reading.add("サイ");
    	kanji[338].reading.add("た-つ");
    	kanji[338].reading.add("さば-く");

    	kanji[339] = new Kanji("際", "阜", 14);
    	kanji[339].reading.add("サイ");
    	kanji[339].reading.add("きわ");

    	kanji[340] = new Kanji("在", "土", 6);
    	kanji[340].reading.add("ザイ");
    	kanji[340].reading.add("あ-る");

    	kanji[341] = new Kanji("材", "木", 7);
    	kanji[341].reading.add("ザイ");

    	kanji[342] = new Kanji("財", "貝", 10);
    	kanji[342].reading.add("ザイ");

    	kanji[343] = new Kanji("罪", "网", 13);
    	kanji[343].reading.add("ザイ");
    	kanji[343].reading.add("つみ");

    	kanji[344] = new Kanji("作", "人", 7);
    	kanji[344].reading.add("サク");
    	kanji[344].reading.add("サ");
    	kanji[344].reading.add("つく-る");

    	kanji[345] = new Kanji("昨", "日", 9);
    	kanji[345].reading.add("サク");

    	kanji[346] = new Kanji("策", "竹", 12);
    	kanji[346].reading.add("サク");

    	kanji[347] = new Kanji("冊", "冂", 5);
    	kanji[347].reading.add("サツ");
    	kanji[347].reading.add("サク");

    	kanji[348] = new Kanji("札", "木", 5);
    	kanji[348].reading.add("サツ");
    	kanji[348].reading.add("ふだ");

    	kanji[349] = new Kanji("刷", "刀", 8);
    	kanji[349].reading.add("サツ");
    	kanji[349].reading.add("す-る");

    	kanji[350] = new Kanji("殺", "殳", 10);
    	kanji[350].reading.add("サツ");
    	kanji[350].reading.add("ころ-す");

    	kanji[351] = new Kanji("察", "宀", 14);
    	kanji[351].reading.add("サツ");

    	kanji[352] = new Kanji("雑", "隹", 14);
    	kanji[352].reading.add("ザツ");
    	kanji[352].reading.add("ゾウ");

    	kanji[353] = new Kanji("皿", "皿", 5);
    	kanji[353].reading.add("さら");

    	kanji[354] = new Kanji("三", "一", 3);
    	kanji[354].reading.add("サン");
    	kanji[354].reading.add("み");
    	kanji[354].reading.add("み-つ");
    	kanji[354].reading.add("みっ-つ");

    	kanji[355] = new Kanji("山", "山", 3);
    	kanji[355].reading.add("サン");
    	kanji[355].reading.add("やま");

    	kanji[356] = new Kanji("参", "厶", 8);
    	kanji[356].reading.add("サン");
    	kanji[356].reading.add("まい-る");

    	kanji[357] = new Kanji("蚕", "虫", 10);
    	kanji[357].reading.add("サン");
    	kanji[357].reading.add("かいこ");

    	kanji[358] = new Kanji("産", "生", 11);
    	kanji[358].reading.add("サン");
    	kanji[358].reading.add("う-む");
    	kanji[358].reading.add("う-まれる");
    	kanji[358].reading.add("うぶ");

    	kanji[359] = new Kanji("散", "攴", 12);
    	kanji[359].reading.add("サン");
    	kanji[359].reading.add("ち-る");
    	kanji[359].reading.add("ち-らす");
    	kanji[359].reading.add("ち-らかす");
    	kanji[359].reading.add("ち-らかる");

    	kanji[360] = new Kanji("算", "竹", 14);
    	kanji[360].reading.add("サン");

    	kanji[361] = new Kanji("酸", "酉", 14);
    	kanji[361].reading.add("サン");
    	kanji[361].reading.add("す-い");

    	kanji[362] = new Kanji("賛", "貝", 15);
    	kanji[362].reading.add("サン");

    	kanji[363] = new Kanji("残", "歹", 10);
    	kanji[363].reading.add("ザン");
    	kanji[363].reading.add("のこ-る");
    	kanji[363].reading.add("のこ-す");

    	kanji[364] = new Kanji("士", "士", 3);
    	kanji[364].reading.add("シ");

    	kanji[365] = new Kanji("子", "子", 3);
    	kanji[365].reading.add("シ");
    	kanji[365].reading.add("ス");
    	kanji[365].reading.add("こ");

    	kanji[366] = new Kanji("支", "支", 4);
    	kanji[366].reading.add("シ");
    	kanji[366].reading.add("ささ-える");

    	kanji[367] = new Kanji("止", "止", 4);
    	kanji[367].reading.add("シ");
    	kanji[367].reading.add("と-まる");
    	kanji[367].reading.add("と-める");

    	kanji[368] = new Kanji("氏", "氏", 4);
    	kanji[368].reading.add("シ");
    	kanji[368].reading.add("うじ");

    	kanji[369] = new Kanji("仕", "人", 5);
    	kanji[369].reading.add("シ");
    	kanji[369].reading.add("つか-える");

    	kanji[370] = new Kanji("史", "口", 5);
    	kanji[370].reading.add("シ");

    	kanji[371] = new Kanji("司", "口", 5);
    	kanji[371].reading.add("シ");

    	kanji[372] = new Kanji("四", "囗", 5);
    	kanji[372].reading.add("シ");
    	kanji[372].reading.add("よ");
    	kanji[372].reading.add("よ-つ");
    	kanji[372].reading.add("よっ-つ");
    	kanji[372].reading.add("よん");

    	kanji[373] = new Kanji("市", "巾", 5);
    	kanji[373].reading.add("シ");
    	kanji[373].reading.add("いち");

    	kanji[374] = new Kanji("矢", "矢", 5);
    	kanji[374].reading.add("シ");
    	kanji[374].reading.add("や");

    	kanji[375] = new Kanji("死", "歹", 6);
    	kanji[375].reading.add("シ");
    	kanji[375].reading.add("し-ぬ");

    	kanji[376] = new Kanji("糸", "糸", 6);
    	kanji[376].reading.add("シ");
    	kanji[376].reading.add("いと");

    	kanji[377] = new Kanji("至", "至", 6);
    	kanji[377].reading.add("シ");
    	kanji[377].reading.add("いた-る");

    	kanji[378] = new Kanji("志", "心", 7);
    	kanji[378].reading.add("シ");
    	kanji[378].reading.add("こころざ-す");
    	kanji[378].reading.add("こころざし");

    	kanji[379] = new Kanji("私", "禾", 7);
    	kanji[379].reading.add("シ");
    	kanji[379].reading.add("わたくし");
    	kanji[379].reading.add("わたし");

    	kanji[380] = new Kanji("使", "人", 8);
    	kanji[380].reading.add("シ");
    	kanji[380].reading.add("つか-う");

    	kanji[381] = new Kanji("始", "女", 8);
    	kanji[381].reading.add("シ");
    	kanji[381].reading.add("はじ-める");
    	kanji[381].reading.add("はじ-まる");

    	kanji[382] = new Kanji("姉", "女", 8);
    	kanji[382].reading.add("シ");
    	kanji[382].reading.add("あね");

    	kanji[383] = new Kanji("枝", "木", 8);
    	kanji[383].reading.add("シ");
    	kanji[383].reading.add("えだ");

    	kanji[384] = new Kanji("姿", "女", 9);
    	kanji[384].reading.add("シ");
    	kanji[384].reading.add("すがた");

    	kanji[385] = new Kanji("思", "心", 9);
    	kanji[385].reading.add("シ");
    	kanji[385].reading.add("おも-う");

    	kanji[386] = new Kanji("指", "手", 9);
    	kanji[386].reading.add("シ");
    	kanji[386].reading.add("ゆび");
    	kanji[386].reading.add("さ-す");

    	kanji[387] = new Kanji("師", "巾", 10);
    	kanji[387].reading.add("シ");

    	kanji[388] = new Kanji("紙", "糸", 10);
    	kanji[388].reading.add("シ");
    	kanji[388].reading.add("かみ");

    	kanji[389] = new Kanji("視", "見", 11);
    	kanji[389].reading.add("シ");

    	kanji[390] = new Kanji("詞", "言", 12);
    	kanji[390].reading.add("シ");

    	kanji[391] = new Kanji("歯", "齒", 12);
    	kanji[391].reading.add("シ");
    	kanji[391].reading.add("は");

    	kanji[392] = new Kanji("試", "言", 13);
    	kanji[392].reading.add("シ");
    	kanji[392].reading.add("こころ-みる");
    	kanji[392].reading.add("ため-す");

    	kanji[393] = new Kanji("詩", "言", 13);
    	kanji[393].reading.add("シ");

    	kanji[394] = new Kanji("資", "貝", 13);
    	kanji[394].reading.add("シ");

    	kanji[395] = new Kanji("飼", "食", 13);
    	kanji[395].reading.add("シ");
    	kanji[395].reading.add("か-う");

    	kanji[396] = new Kanji("誌", "言", 14);
    	kanji[396].reading.add("シ");

    	kanji[397] = new Kanji("示", "示", 5);
    	kanji[397].reading.add("ジ");
    	kanji[397].reading.add("シ");
    	kanji[397].reading.add("しめ-す");

    	kanji[398] = new Kanji("字", "子", 6);
    	kanji[398].reading.add("ジ");
    	kanji[398].reading.add("あざ");

    	kanji[399] = new Kanji("寺", "寸", 6);
    	kanji[399].reading.add("ジ");
    	kanji[399].reading.add("てら");

    	kanji[400] = new Kanji("次", "欠", 6);
    	kanji[400].reading.add("ジ");
    	kanji[400].reading.add("シ");
    	kanji[400].reading.add("つ-ぐ");
    	kanji[400].reading.add("つぎ");

    	kanji[401] = new Kanji("耳", "耳", 6);
    	kanji[401].reading.add("ジ");
    	kanji[401].reading.add("みみ");

    	kanji[402] = new Kanji("自", "自", 6);
    	kanji[402].reading.add("ジ");
    	kanji[402].reading.add("シ");
    	kanji[402].reading.add("みずか-ら");

    	kanji[403] = new Kanji("似", "人", 7);
    	kanji[403].reading.add("ジ");
    	kanji[403].reading.add("に-る");

    	kanji[404] = new Kanji("児", "儿", 7);
    	kanji[404].reading.add("ジ");

    	kanji[405] = new Kanji("事", "亅", 8);
    	kanji[405].reading.add("ジ");
    	kanji[405].reading.add("こと");

    	kanji[406] = new Kanji("治", "水", 8);
    	kanji[406].reading.add("ジ");
    	kanji[406].reading.add("チ");
    	kanji[406].reading.add("おさ-める");
    	kanji[406].reading.add("おさ-まる");
    	kanji[406].reading.add("なお-る");
    	kanji[406].reading.add("なお-す");

    	kanji[407] = new Kanji("持", "手", 9);
    	kanji[407].reading.add("ジ");
    	kanji[407].reading.add("も-つ");

    	kanji[408] = new Kanji("時", "日", 10);
    	kanji[408].reading.add("ジ");
    	kanji[408].reading.add("とき");

    	kanji[409] = new Kanji("辞", "辛", 13);
    	kanji[409].reading.add("ジ");
    	kanji[409].reading.add("や-める");

    	kanji[410] = new Kanji("磁", "石", 14);
    	kanji[410].reading.add("ジ");

    	kanji[411] = new Kanji("式", "弋", 6);
    	kanji[411].reading.add("シキ");

    	kanji[412] = new Kanji("識", "言", 19);
    	kanji[412].reading.add("シキ");

    	kanji[413] = new Kanji("七", "一", 2);
    	kanji[413].reading.add("シチ");
    	kanji[413].reading.add("なな");
    	kanji[413].reading.add("なな-つ");

    	kanji[414] = new Kanji("失", "大", 5);
    	kanji[414].reading.add("シツ");
    	kanji[414].reading.add("うしな-う");

    	kanji[415] = new Kanji("室", "宀", 9);
    	kanji[415].reading.add("シツ");
    	kanji[415].reading.add("むろ");

    	kanji[416] = new Kanji("質", "貝", 15);
    	kanji[416].reading.add("シツ");
    	kanji[416].reading.add("シチ");

    	kanji[417] = new Kanji("実", "宀", 8);
    	kanji[417].reading.add("ジツ");
    	kanji[417].reading.add("み");
    	kanji[417].reading.add("みの-る");

    	kanji[418] = new Kanji("写", "宀", 5);
    	kanji[418].reading.add("シャ");
    	kanji[418].reading.add("うつ-す");
    	kanji[418].reading.add("うつ-る");

    	kanji[419] = new Kanji("社", "示", 7);
    	kanji[419].reading.add("シャ");
    	kanji[419].reading.add("やしろ");

    	kanji[420] = new Kanji("車", "車", 7);
    	kanji[420].reading.add("シャ");
    	kanji[420].reading.add("くるま");

    	kanji[421] = new Kanji("舎", "舌", 8);
    	kanji[421].reading.add("シャ");

    	kanji[422] = new Kanji("者", "老", 8);
    	kanji[422].reading.add("シャ");
    	kanji[422].reading.add("もの");

    	kanji[423] = new Kanji("射", "寸", 10);
    	kanji[423].reading.add("シャ");
    	kanji[423].reading.add("い-る");

    	kanji[424] = new Kanji("捨", "手", 11);
    	kanji[424].reading.add("シャ");
    	kanji[424].reading.add("す-てる");

    	kanji[425] = new Kanji("謝", "言", 17);
    	kanji[425].reading.add("シャ");
    	kanji[425].reading.add("あやま-る");

    	kanji[426] = new Kanji("尺", "尸", 4);
    	kanji[426].reading.add("シャク");

    	kanji[427] = new Kanji("借", "人", 10);
    	kanji[427].reading.add("シャク");
    	kanji[427].reading.add("か-りる");

    	kanji[428] = new Kanji("若", "艸", 8);
    	kanji[428].reading.add("ジャク");
    	kanji[428].reading.add("わか-い");
    	kanji[428].reading.add("も-しくは");

    	kanji[429] = new Kanji("弱", "弓", 10);
    	kanji[429].reading.add("ジャク");
    	kanji[429].reading.add("よわ-い");
    	kanji[429].reading.add("よわ-る");
    	kanji[429].reading.add("よわ-まる");
    	kanji[429].reading.add("よわ-める");

    	kanji[430] = new Kanji("手", "手", 4);
    	kanji[430].reading.add("シュ");
    	kanji[430].reading.add("て");

    	kanji[431] = new Kanji("主", "丶", 5);
    	kanji[431].reading.add("シュ");
    	kanji[431].reading.add("ぬし");
    	kanji[431].reading.add("おも");

    	kanji[432] = new Kanji("守", "宀", 6);
    	kanji[432].reading.add("シュ");
    	kanji[432].reading.add("まも-る");
    	kanji[432].reading.add("も-り");

    	kanji[433] = new Kanji("取", "又", 8);
    	kanji[433].reading.add("シュ");
    	kanji[433].reading.add("と-る");

    	kanji[434] = new Kanji("首", "首", 9);
    	kanji[434].reading.add("シュ");
    	kanji[434].reading.add("くび");

    	kanji[435] = new Kanji("酒", "酉", 10);
    	kanji[435].reading.add("シュ");
    	kanji[435].reading.add("さけ");

    	kanji[436] = new Kanji("種", "禾", 14);
    	kanji[436].reading.add("シュ");
    	kanji[436].reading.add("たね");

    	kanji[437] = new Kanji("受", "又", 8);
    	kanji[437].reading.add("ジュ");
    	kanji[437].reading.add("う-ける");
    	kanji[437].reading.add("う-かる");

    	kanji[438] = new Kanji("授", "手", 11);
    	kanji[438].reading.add("ジュ");
    	kanji[438].reading.add("さず-ける");
    	kanji[438].reading.add("さず-かる");

    	kanji[439] = new Kanji("樹", "木", 16);
    	kanji[439].reading.add("ジュ");

    	kanji[440] = new Kanji("収", "攴", 4);
    	kanji[440].reading.add("シュウ");
    	kanji[440].reading.add("おさ-める");
    	kanji[440].reading.add("おさ-まる");

    	kanji[441] = new Kanji("州", "巛", 6);
    	kanji[441].reading.add("シュウ");
    	kanji[441].reading.add("す");

    	kanji[442] = new Kanji("周", "口", 8);
    	kanji[442].reading.add("シュウ");
    	kanji[442].reading.add("まわ-り");

    	kanji[443] = new Kanji("宗", "宀", 8);
    	kanji[443].reading.add("シュウ");
    	kanji[443].reading.add("ソウ");

    	kanji[444] = new Kanji("拾", "手", 9);
    	kanji[444].reading.add("シュウ");
    	kanji[444].reading.add("ジュウ");
    	kanji[444].reading.add("ひろ-う");

    	kanji[445] = new Kanji("秋", "禾", 9);
    	kanji[445].reading.add("シュウ");
    	kanji[445].reading.add("あき");

    	kanji[446] = new Kanji("修", "人", 10);
    	kanji[446].reading.add("シュウ");
    	kanji[446].reading.add("おさ-める");
    	kanji[446].reading.add("おさ-まる");

    	kanji[447] = new Kanji("終", "糸", 11);
    	kanji[447].reading.add("シュウ");
    	kanji[447].reading.add("お-わる");
    	kanji[447].reading.add("お-える");

    	kanji[448] = new Kanji("習", "羽", 11);
    	kanji[448].reading.add("シュウ");
    	kanji[448].reading.add("なら-う");

    	kanji[449] = new Kanji("週", "辵", 11);
    	kanji[449].reading.add("シュウ");

    	kanji[450] = new Kanji("就", "尢", 12);
    	kanji[450].reading.add("シュウ");
    	kanji[450].reading.add("つ-く");
    	kanji[450].reading.add("つ-ける");

    	kanji[451] = new Kanji("衆", "血", 12);
    	kanji[451].reading.add("シュウ");

    	kanji[452] = new Kanji("集", "隹", 12);
    	kanji[452].reading.add("シュウ");
    	kanji[452].reading.add("あつ-まる");
    	kanji[452].reading.add("あつ-める");
    	kanji[452].reading.add("つど-う");

    	kanji[453] = new Kanji("十", "十", 2);
    	kanji[453].reading.add("ジュウ");
    	kanji[453].reading.add("ジッ");
    	kanji[453].reading.add("とお");
    	kanji[453].reading.add("と");

    	kanji[454] = new Kanji("住", "人", 7);
    	kanji[454].reading.add("ジュウ");
    	kanji[454].reading.add("す-む");
    	kanji[454].reading.add("す-まう");

    	kanji[455] = new Kanji("重", "里", 9);
    	kanji[455].reading.add("ジュウ");
    	kanji[455].reading.add("チョウ");
    	kanji[455].reading.add("え");
    	kanji[455].reading.add("おも-い");
    	kanji[455].reading.add("かさ-ねる");
    	kanji[455].reading.add("かさ-なる");

    	kanji[456] = new Kanji("従", "彳", 10);
    	kanji[456].reading.add("ジュウ");
    	kanji[456].reading.add("したが-う");
    	kanji[456].reading.add("したが-える");

    	kanji[457] = new Kanji("縦", "糸", 16);
    	kanji[457].reading.add("ジュウ");
    	kanji[457].reading.add("たて");

    	kanji[458] = new Kanji("祝", "示", 9);
    	kanji[458].reading.add("シュク");
    	kanji[458].reading.add("いわ-う");

    	kanji[459] = new Kanji("宿", "宀", 11);
    	kanji[459].reading.add("シュク");
    	kanji[459].reading.add("やど");
    	kanji[459].reading.add("やど-る");
    	kanji[459].reading.add("やど-す");

    	kanji[460] = new Kanji("縮", "糸", 17);
    	kanji[460].reading.add("シュク");
    	kanji[460].reading.add("ちぢ-む");
    	kanji[460].reading.add("ちぢ-まる");
    	kanji[460].reading.add("ちぢ-める");
    	kanji[460].reading.add("ちぢ-れる");
    	kanji[460].reading.add("ちぢ-らす");

    	kanji[461] = new Kanji("熟", "火", 15);
    	kanji[461].reading.add("ジュク");
    	kanji[461].reading.add("う-れる");

    	kanji[462] = new Kanji("出", "凵", 5);
    	kanji[462].reading.add("シュツ");
    	kanji[462].reading.add("で-る");
    	kanji[462].reading.add("だ-す");

    	kanji[463] = new Kanji("述", "辵", 8);
    	kanji[463].reading.add("ジュツ");
    	kanji[463].reading.add("の-べる");

    	kanji[464] = new Kanji("術", "行", 11);
    	kanji[464].reading.add("ジュツ");

    	kanji[465] = new Kanji("春", "日", 9);
    	kanji[465].reading.add("シュン");
    	kanji[465].reading.add("はる");

    	kanji[466] = new Kanji("純", "糸", 10);
    	kanji[466].reading.add("ジュン");

    	kanji[467] = new Kanji("順", "頁", 12);
    	kanji[467].reading.add("ジュン");

    	kanji[468] = new Kanji("準", "水", 13);
    	kanji[468].reading.add("ジュン");

    	kanji[469] = new Kanji("処", "几", 5);
    	kanji[469].reading.add("ショ");

    	kanji[470] = new Kanji("初", "刀", 7);
    	kanji[470].reading.add("ショ");
    	kanji[470].reading.add("はじ-め");
    	kanji[470].reading.add("はじ-めて");
    	kanji[470].reading.add("はつ");
    	kanji[470].reading.add("うい");
    	kanji[470].reading.add("そ-める");

    	kanji[471] = new Kanji("所", "戶", 8);
    	kanji[471].reading.add("ショ");
    	kanji[471].reading.add("ところ");

    	kanji[472] = new Kanji("書", "曰", 10);
    	kanji[472].reading.add("ショ");
    	kanji[472].reading.add("か-く");

    	kanji[473] = new Kanji("暑", "日", 12);
    	kanji[473].reading.add("ショ");
    	kanji[473].reading.add("あつ-い");

    	kanji[474] = new Kanji("署", "网", 13);
    	kanji[474].reading.add("ショ");

    	kanji[475] = new Kanji("諸", "言", 15);
    	kanji[475].reading.add("ショ");

    	kanji[476] = new Kanji("女", "女", 3);
    	kanji[476].reading.add("ジョ");
    	kanji[476].reading.add("ニョ");
    	kanji[476].reading.add("おんな");
    	kanji[476].reading.add("め");

    	kanji[477] = new Kanji("助", "力", 7);
    	kanji[477].reading.add("ジョ");
    	kanji[477].reading.add("たす-ける");
    	kanji[477].reading.add("たす-かる");
    	kanji[477].reading.add("すけ");

    	kanji[478] = new Kanji("序", "广", 7);
    	kanji[478].reading.add("ジョ");

    	kanji[479] = new Kanji("除", "阜", 10);
    	kanji[479].reading.add("ジョ");
    	kanji[479].reading.add("のぞ-く");

    	kanji[480] = new Kanji("小", "小", 3);
    	kanji[480].reading.add("ショウ");
    	kanji[480].reading.add("ちい-さい");
    	kanji[480].reading.add("こ");
    	kanji[480].reading.add("お");

    	kanji[481] = new Kanji("少", "小", 4);
    	kanji[481].reading.add("ショウ");
    	kanji[481].reading.add("すく-ない");
    	kanji[481].reading.add("すこ-し");

    	kanji[482] = new Kanji("招", "手", 8);
    	kanji[482].reading.add("ショウ");
    	kanji[482].reading.add("まね-く");

    	kanji[483] = new Kanji("承", "手", 8);
    	kanji[483].reading.add("ショウ");
    	kanji[483].reading.add("うけたまわ-る");

    	kanji[484] = new Kanji("松", "木", 8);
    	kanji[484].reading.add("ショウ");
    	kanji[484].reading.add("まつ");

    	kanji[485] = new Kanji("昭", "日", 9);
    	kanji[485].reading.add("ショウ");

    	kanji[486] = new Kanji("将", "寸", 10);
    	kanji[486].reading.add("ショウ");

    	kanji[487] = new Kanji("消", "水", 10);
    	kanji[487].reading.add("ショウ");
    	kanji[487].reading.add("き-える");
    	kanji[487].reading.add("け-す");

    	kanji[488] = new Kanji("笑", "竹", 10);
    	kanji[488].reading.add("ショウ");
    	kanji[488].reading.add("わら-う");
    	kanji[488].reading.add("え-む");

    	kanji[489] = new Kanji("唱", "口", 11);
    	kanji[489].reading.add("ショウ");
    	kanji[489].reading.add("とな-える");

    	kanji[490] = new Kanji("商", "口", 11);
    	kanji[490].reading.add("ショウ");
    	kanji[490].reading.add("あきな-う");

    	kanji[491] = new Kanji("章", "立", 11);
    	kanji[491].reading.add("ショウ");

    	kanji[492] = new Kanji("勝", "力", 12);
    	kanji[492].reading.add("ショウ");
    	kanji[492].reading.add("か-つ");
    	kanji[492].reading.add("まさ-る");

    	kanji[493] = new Kanji("焼", "火", 12);
    	kanji[493].reading.add("ショウ");
    	kanji[493].reading.add("や-く");
    	kanji[493].reading.add("や-ける");

    	kanji[494] = new Kanji("証", "言", 12);
    	kanji[494].reading.add("ショウ");

    	kanji[495] = new Kanji("象", "豕", 12);
    	kanji[495].reading.add("ショウ");
    	kanji[495].reading.add("ゾウ");

    	kanji[496] = new Kanji("傷", "人", 13);
    	kanji[496].reading.add("ショウ");
    	kanji[496].reading.add("きず");
    	kanji[496].reading.add("いた-む");
    	kanji[496].reading.add("いた-める");

    	kanji[497] = new Kanji("照", "火", 13);
    	kanji[497].reading.add("ショウ");
    	kanji[497].reading.add("て-る");
    	kanji[497].reading.add("て-らす");
    	kanji[497].reading.add("て-れる");

    	kanji[498] = new Kanji("障", "阜", 14);
    	kanji[498].reading.add("ショウ");
    	kanji[498].reading.add("さわ-る");

    	kanji[499] = new Kanji("賞", "貝", 15);
    	kanji[499].reading.add("ショウ");

    	kanji[500] = new Kanji("上", "一", 3);
    	kanji[500].reading.add("ジョウ");
    	kanji[500].reading.add("うえ");
    	kanji[500].reading.add("かみ");
    	kanji[500].reading.add("あ-げる");
    	kanji[500].reading.add("あ-がる");
    	kanji[500].reading.add("のぼ-る");
    	kanji[500].reading.add("のぼ-せる");
    	kanji[500].reading.add("のぼ-す");
    }
    
    public void SetKanji2() {
    	kanji[501] = new Kanji("条", "木", 7);
    	kanji[501].reading.add("ジョウ");

    	kanji[502] = new Kanji("状", "犬", 7);
    	kanji[502].reading.add("ジョウ");

    	kanji[503] = new Kanji("乗", "丿", 9);
    	kanji[503].reading.add("ジョウ");
    	kanji[503].reading.add("の-る");
    	kanji[503].reading.add("の-せる");

    	kanji[504] = new Kanji("城", "土", 9);
    	kanji[504].reading.add("ジョウ");
    	kanji[504].reading.add("しろ");

    	kanji[505] = new Kanji("常", "巾", 11);
    	kanji[505].reading.add("ジョウ");
    	kanji[505].reading.add("つね");
    	kanji[505].reading.add("とこ");

    	kanji[506] = new Kanji("情", "心", 11);
    	kanji[506].reading.add("ジョウ");
    	kanji[506].reading.add("なさ-け");

    	kanji[507] = new Kanji("場", "土", 12);
    	kanji[507].reading.add("ジョウ");
    	kanji[507].reading.add("ば");

    	kanji[508] = new Kanji("蒸", "艸", 13);
    	kanji[508].reading.add("ジョウ");
    	kanji[508].reading.add("む-す");
    	kanji[508].reading.add("む-れる");
    	kanji[508].reading.add("む-らす");

    	kanji[509] = new Kanji("色", "色", 6);
    	kanji[509].reading.add("ショク");
    	kanji[509].reading.add("シキ");
    	kanji[509].reading.add("いろ");

    	kanji[510] = new Kanji("食", "食", 9);
    	kanji[510].reading.add("ショク");
    	kanji[510].reading.add("く-う");
    	kanji[510].reading.add("く-らう");
    	kanji[510].reading.add("た-べる");

    	kanji[511] = new Kanji("植", "木", 12);
    	kanji[511].reading.add("ショク");
    	kanji[511].reading.add("う-える");
    	kanji[511].reading.add("う-わる");

    	kanji[512] = new Kanji("織", "糸", 18);
    	kanji[512].reading.add("ショク");
    	kanji[512].reading.add("シキ");
    	kanji[512].reading.add("お-る");

    	kanji[513] = new Kanji("職", "耳", 18);
    	kanji[513].reading.add("ショク");

    	kanji[514] = new Kanji("心", "心", 4);
    	kanji[514].reading.add("シン");
    	kanji[514].reading.add("こころ");

    	kanji[515] = new Kanji("申", "田", 5);
    	kanji[515].reading.add("シン");
    	kanji[515].reading.add("もう-す");

    	kanji[516] = new Kanji("臣", "臣", 7);
    	kanji[516].reading.add("シン");
    	kanji[516].reading.add("ジン");

    	kanji[517] = new Kanji("身", "身", 7);
    	kanji[517].reading.add("シン");
    	kanji[517].reading.add("み");

    	kanji[518] = new Kanji("信", "人", 9);
    	kanji[518].reading.add("シン");

    	kanji[519] = new Kanji("神", "示", 9);
    	kanji[519].reading.add("シン");
    	kanji[519].reading.add("ジン");
    	kanji[519].reading.add("かみ");

    	kanji[520] = new Kanji("真", "目", 10);
    	kanji[520].reading.add("シン");
    	kanji[520].reading.add("ま");

    	kanji[521] = new Kanji("針", "金", 10);
    	kanji[521].reading.add("シン");
    	kanji[521].reading.add("はり");

    	kanji[522] = new Kanji("深", "水", 11);
    	kanji[522].reading.add("シン");
    	kanji[522].reading.add("ふか-い");
    	kanji[522].reading.add("ふか-まる");
    	kanji[522].reading.add("ふか-める");

    	kanji[523] = new Kanji("進", "辵", 11);
    	kanji[523].reading.add("シン");
    	kanji[523].reading.add("すす-む");
    	kanji[523].reading.add("すす-める");

    	kanji[524] = new Kanji("森", "木", 12);
    	kanji[524].reading.add("シン");
    	kanji[524].reading.add("もり");

    	kanji[525] = new Kanji("新", "斤", 13);
    	kanji[525].reading.add("シン");
    	kanji[525].reading.add("あたら-しい");
    	kanji[525].reading.add("あら-た");
    	kanji[525].reading.add("にい");

    	kanji[526] = new Kanji("親", "見", 16);
    	kanji[526].reading.add("シン");
    	kanji[526].reading.add("おや");
    	kanji[526].reading.add("した-しい");
    	kanji[526].reading.add("した-しむ");

    	kanji[527] = new Kanji("人", "人", 2);
    	kanji[527].reading.add("ジン");
    	kanji[527].reading.add("ニン");
    	kanji[527].reading.add("ひと");

    	kanji[528] = new Kanji("仁", "人", 4);
    	kanji[528].reading.add("ジン");

    	kanji[529] = new Kanji("図", "囗", 7);
    	kanji[529].reading.add("ズ");
    	kanji[529].reading.add("ト");
    	kanji[529].reading.add("はか-る");

    	kanji[530] = new Kanji("水", "水", 4);
    	kanji[530].reading.add("スイ");
    	kanji[530].reading.add("みず");

    	kanji[531] = new Kanji("垂", "土", 8);
    	kanji[531].reading.add("スイ");
    	kanji[531].reading.add("た-れる");
    	kanji[531].reading.add("た-らす");

    	kanji[532] = new Kanji("推", "手", 11);
    	kanji[532].reading.add("スイ");
    	kanji[532].reading.add("お-す");

    	kanji[533] = new Kanji("数", "攴", 13);
    	kanji[533].reading.add("スウ");
    	kanji[533].reading.add("かず");
    	kanji[533].reading.add("かぞ-える");

    	kanji[534] = new Kanji("寸", "寸", 3);
    	kanji[534].reading.add("スン");

    	kanji[535] = new Kanji("世", "一", 5);
    	kanji[535].reading.add("セイ");
    	kanji[535].reading.add("セ");
    	kanji[535].reading.add("よ");

    	kanji[536] = new Kanji("正", "止", 5);
    	kanji[536].reading.add("セイ");
    	kanji[536].reading.add("ショウ");
    	kanji[536].reading.add("ただ-しい");
    	kanji[536].reading.add("ただ-す");
    	kanji[536].reading.add("まさ");

    	kanji[537] = new Kanji("生", "生", 5);
    	kanji[537].reading.add("セイ");
    	kanji[537].reading.add("ショウ");
    	kanji[537].reading.add("い-きる");
    	kanji[537].reading.add("い-かす");
    	kanji[537].reading.add("い-ける");
    	kanji[537].reading.add("う-まれる");
    	kanji[537].reading.add("う-む");
    	kanji[537].reading.add("お-う");
    	kanji[537].reading.add("は-える");
    	kanji[537].reading.add("は-やす");
    	kanji[537].reading.add("き");
    	kanji[537].reading.add("なま");

    	kanji[538] = new Kanji("成", "戈", 6);
    	kanji[538].reading.add("セイ");
    	kanji[538].reading.add("な-る");
    	kanji[538].reading.add("な-す");

    	kanji[539] = new Kanji("西", "襾", 6);
    	kanji[539].reading.add("セイ");
    	kanji[539].reading.add("サイ");
    	kanji[539].reading.add("にし");

    	kanji[540] = new Kanji("声", "耳", 7);
    	kanji[540].reading.add("セイ");
    	kanji[540].reading.add("こえ");

    	kanji[541] = new Kanji("制", "刀", 8);
    	kanji[541].reading.add("セイ");

    	kanji[542] = new Kanji("性", "心", 8);
    	kanji[542].reading.add("セイ");
    	kanji[542].reading.add("ショウ");

    	kanji[543] = new Kanji("青", "靑", 8);
    	kanji[543].reading.add("セイ");
    	kanji[543].reading.add("あお");
    	kanji[543].reading.add("あお-い");

    	kanji[544] = new Kanji("政", "攴", 9);
    	kanji[544].reading.add("セイ");
    	kanji[544].reading.add("まつりごと");

    	kanji[545] = new Kanji("星", "日", 9);
    	kanji[545].reading.add("セイ");
    	kanji[545].reading.add("ほし");

    	kanji[546] = new Kanji("省", "目", 9);
    	kanji[546].reading.add("セイ");
    	kanji[546].reading.add("ショウ");
    	kanji[546].reading.add("かえり-みる");
    	kanji[546].reading.add("はぶ-く");

    	kanji[547] = new Kanji("清", "水", 11);
    	kanji[547].reading.add("セイ");
    	kanji[547].reading.add("きよ-い");
    	kanji[547].reading.add("きよ-まる");
    	kanji[547].reading.add("きよ-める");

    	kanji[548] = new Kanji("盛", "皿", 11);
    	kanji[548].reading.add("セイ");
    	kanji[548].reading.add("も-る");
    	kanji[548].reading.add("さか-る");
    	kanji[548].reading.add("さか-ん");

    	kanji[549] = new Kanji("晴", "日", 12);
    	kanji[549].reading.add("セイ");
    	kanji[549].reading.add("は-れる");
    	kanji[549].reading.add("は-らす");

    	kanji[550] = new Kanji("勢", "力", 13);
    	kanji[550].reading.add("セイ");
    	kanji[550].reading.add("いきお-い");

    	kanji[551] = new Kanji("聖", "耳", 13);
    	kanji[551].reading.add("セイ");

    	kanji[552] = new Kanji("誠", "言", 13);
    	kanji[552].reading.add("セイ");
    	kanji[552].reading.add("まこと");

    	kanji[553] = new Kanji("精", "米", 14);
    	kanji[553].reading.add("セイ");

    	kanji[554] = new Kanji("製", "衣", 14);
    	kanji[554].reading.add("セイ");

    	kanji[555] = new Kanji("静", "靑", 14);
    	kanji[555].reading.add("セイ");
    	kanji[555].reading.add("しず");
    	kanji[555].reading.add("しず-か");
    	kanji[555].reading.add("しず-まる");
    	kanji[555].reading.add("しず-める");

    	kanji[556] = new Kanji("整", "攴", 16);
    	kanji[556].reading.add("セイ");
    	kanji[556].reading.add("ととの-える");
    	kanji[556].reading.add("ととの-う");

    	kanji[557] = new Kanji("税", "禾", 12);
    	kanji[557].reading.add("ゼイ");

    	kanji[558] = new Kanji("夕", "夕", 3);
    	kanji[558].reading.add("セキ");
    	kanji[558].reading.add("ゆう");

    	kanji[559] = new Kanji("石", "石", 5);
    	kanji[559].reading.add("セキ");
    	kanji[559].reading.add("いし");

    	kanji[560] = new Kanji("赤", "赤", 7);
    	kanji[560].reading.add("セキ");
    	kanji[560].reading.add("あか");
    	kanji[560].reading.add("あか-い");
    	kanji[560].reading.add("あか-らむ");
    	kanji[560].reading.add("あか-らめる");

    	kanji[561] = new Kanji("昔", "日", 8);
    	kanji[561].reading.add("セキ");
    	kanji[561].reading.add("むかし");

    	kanji[562] = new Kanji("席", "巾", 10);
    	kanji[562].reading.add("セキ");

    	kanji[563] = new Kanji("責", "貝", 11);
    	kanji[563].reading.add("セキ");
    	kanji[563].reading.add("せ-める");

    	kanji[564] = new Kanji("積", "禾", 16);
    	kanji[564].reading.add("セキ");
    	kanji[564].reading.add("つ-む");
    	kanji[564].reading.add("つ-もる");

    	kanji[565] = new Kanji("績", "糸", 17);
    	kanji[565].reading.add("セキ");

    	kanji[566] = new Kanji("切", "刀", 4);
    	kanji[566].reading.add("セツ");
    	kanji[566].reading.add("き-る");
    	kanji[566].reading.add("き-れる");

    	kanji[567] = new Kanji("折", "手", 7);
    	kanji[567].reading.add("セツ");
    	kanji[567].reading.add("お-る");
    	kanji[567].reading.add("おり");
    	kanji[567].reading.add("お-れる");

    	kanji[568] = new Kanji("接", "手", 11);
    	kanji[568].reading.add("セツ");
    	kanji[568].reading.add("つ-ぐ");

    	kanji[569] = new Kanji("設", "言", 11);
    	kanji[569].reading.add("セツ");
    	kanji[569].reading.add("もう-ける");

    	kanji[570] = new Kanji("雪", "雨", 11);
    	kanji[570].reading.add("セツ");
    	kanji[570].reading.add("ゆき");

    	kanji[571] = new Kanji("節", "竹", 13);
    	kanji[571].reading.add("セツ");
    	kanji[571].reading.add("ふし");

    	kanji[572] = new Kanji("説", "言", 14);
    	kanji[572].reading.add("セツ");
    	kanji[572].reading.add("と-く");

    	kanji[573] = new Kanji("舌", "舌", 6);
    	kanji[573].reading.add("ゼツ");
    	kanji[573].reading.add("した");

    	kanji[574] = new Kanji("絶", "糸", 12);
    	kanji[574].reading.add("ゼツ");
    	kanji[574].reading.add("た-える");
    	kanji[574].reading.add("た-やす");
    	kanji[574].reading.add("た-つ");

    	kanji[575] = new Kanji("千", "十", 3);
    	kanji[575].reading.add("セン");
    	kanji[575].reading.add("ち");

    	kanji[576] = new Kanji("川", "巛", 3);
    	kanji[576].reading.add("セン");
    	kanji[576].reading.add("かわ");

    	kanji[577] = new Kanji("先", "儿", 6);
    	kanji[577].reading.add("セン");
    	kanji[577].reading.add("さき");

    	kanji[578] = new Kanji("宣", "宀", 9);
    	kanji[578].reading.add("セン");

    	kanji[579] = new Kanji("専", "寸", 9);
    	kanji[579].reading.add("セン");
    	kanji[579].reading.add("もっぱ-ら");

    	kanji[580] = new Kanji("泉", "水", 9);
    	kanji[580].reading.add("セン");
    	kanji[580].reading.add("いずみ");

    	kanji[581] = new Kanji("浅", "水", 9);
    	kanji[581].reading.add("セン");
    	kanji[581].reading.add("あさ-い");

    	kanji[582] = new Kanji("洗", "水", 9);
    	kanji[582].reading.add("セン");
    	kanji[582].reading.add("あら-う");

    	kanji[583] = new Kanji("染", "木", 9);
    	kanji[583].reading.add("セン");
    	kanji[583].reading.add("そ-める");
    	kanji[583].reading.add("そ-まる");
    	kanji[583].reading.add("し-みる");
    	kanji[583].reading.add("し-み");

    	kanji[584] = new Kanji("船", "舟", 11);
    	kanji[584].reading.add("セン");
    	kanji[584].reading.add("ふね");

    	kanji[585] = new Kanji("戦", "戈", 13);
    	kanji[585].reading.add("セン");
    	kanji[585].reading.add("いくさ");
    	kanji[585].reading.add("たたか-う");

    	kanji[586] = new Kanji("銭", "金", 14);
    	kanji[586].reading.add("セン");
    	kanji[586].reading.add("ぜに");

    	kanji[587] = new Kanji("線", "糸", 15);
    	kanji[587].reading.add("セン");

    	kanji[588] = new Kanji("選", "辵", 15);
    	kanji[588].reading.add("セン");
    	kanji[588].reading.add("えら-ぶ");

    	kanji[589] = new Kanji("全", "入", 6);
    	kanji[589].reading.add("ゼン");
    	kanji[589].reading.add("まった-く");
    	kanji[589].reading.add("すべ-て");

    	kanji[590] = new Kanji("前", "刀", 9);
    	kanji[590].reading.add("ゼン");
    	kanji[590].reading.add("まえ");

    	kanji[591] = new Kanji("善", "口", 12);
    	kanji[591].reading.add("ゼン");
    	kanji[591].reading.add("よ-い");

    	kanji[592] = new Kanji("然", "火", 12);
    	kanji[592].reading.add("ゼン");
    	kanji[592].reading.add("ネン");

    	kanji[593] = new Kanji("祖", "示", 9);
    	kanji[593].reading.add("ソ");

    	kanji[594] = new Kanji("素", "糸", 10);
    	kanji[594].reading.add("ソ");
    	kanji[594].reading.add("ス");

    	kanji[595] = new Kanji("組", "糸", 11);
    	kanji[595].reading.add("ソ");
    	kanji[595].reading.add("く-む");
    	kanji[595].reading.add("くみ");

    	kanji[596] = new Kanji("早", "日", 6);
    	kanji[596].reading.add("ソウ");
    	kanji[596].reading.add("はや-い");
    	kanji[596].reading.add("はや-まる");
    	kanji[596].reading.add("はや-める");

    	kanji[597] = new Kanji("争", "爪", 6);
    	kanji[597].reading.add("ソウ");
    	kanji[597].reading.add("あらそ-う");

    	kanji[598] = new Kanji("走", "走", 7);
    	kanji[598].reading.add("ソウ");
    	kanji[598].reading.add("はし-る");

    	kanji[599] = new Kanji("奏", "大", 9);
    	kanji[599].reading.add("ソウ");
    	kanji[599].reading.add("かな-でる");

    	kanji[600] = new Kanji("相", "目", 9);
    	kanji[600].reading.add("ソウ");
    	kanji[600].reading.add("ショウ");
    	kanji[600].reading.add("あい");

    	kanji[601] = new Kanji("草", "艸", 9);
    	kanji[601].reading.add("ソウ");
    	kanji[601].reading.add("くさ");

    	kanji[602] = new Kanji("送", "辵", 9);
    	kanji[602].reading.add("ソウ");
    	kanji[602].reading.add("おく-る");

    	kanji[603] = new Kanji("倉", "人", 10);
    	kanji[603].reading.add("ソウ");
    	kanji[603].reading.add("くら");

    	kanji[604] = new Kanji("巣", "巛", 11);
    	kanji[604].reading.add("ソウ");
    	kanji[604].reading.add("す");

    	kanji[605] = new Kanji("窓", "穴", 11);
    	kanji[605].reading.add("ソウ");
    	kanji[605].reading.add("まど");

    	kanji[606] = new Kanji("創", "刀", 12);
    	kanji[606].reading.add("ソウ");
    	kanji[606].reading.add("つく-る");

    	kanji[607] = new Kanji("装", "衣", 12);
    	kanji[607].reading.add("ソウ");
    	kanji[607].reading.add("ショウ");
    	kanji[607].reading.add("よそお-う");

    	kanji[608] = new Kanji("想", "心", 13);
    	kanji[608].reading.add("ソウ");

    	kanji[609] = new Kanji("層", "尸", 14);
    	kanji[609].reading.add("ソウ");

    	kanji[610] = new Kanji("総", "糸", 14);
    	kanji[610].reading.add("ソウ");

    	kanji[611] = new Kanji("操", "手", 16);
    	kanji[611].reading.add("ソウ");
    	kanji[611].reading.add("みさお");
    	kanji[611].reading.add("あやつ-る");

    	kanji[612] = new Kanji("造", "辵", 10);
    	kanji[612].reading.add("ゾウ");
    	kanji[612].reading.add("つく-る");

    	kanji[613] = new Kanji("像", "人", 14);
    	kanji[613].reading.add("ゾウ");

    	kanji[614] = new Kanji("増", "土", 14);
    	kanji[614].reading.add("ゾウ");
    	kanji[614].reading.add("ま-す");
    	kanji[614].reading.add("ふ-える");
    	kanji[614].reading.add("ふ-やす");

    	kanji[615] = new Kanji("蔵", "艸", 15);
    	kanji[615].reading.add("ゾウ");
    	kanji[615].reading.add("くら");

    	kanji[616] = new Kanji("臓", "肉", 19);
    	kanji[616].reading.add("ゾウ");

    	kanji[617] = new Kanji("束", "木", 7);
    	kanji[617].reading.add("ソク");
    	kanji[617].reading.add("たば");

    	kanji[618] = new Kanji("足", "足", 7);
    	kanji[618].reading.add("ソク");
    	kanji[618].reading.add("あし");
    	kanji[618].reading.add("た-りる");
    	kanji[618].reading.add("た-る");
    	kanji[618].reading.add("た-す");

    	kanji[619] = new Kanji("則", "刀", 9);
    	kanji[619].reading.add("ソク");

    	kanji[620] = new Kanji("息", "心", 10);
    	kanji[620].reading.add("ソク");
    	kanji[620].reading.add("いき");

    	kanji[621] = new Kanji("速", "辵", 10);
    	kanji[621].reading.add("ソク");
    	kanji[621].reading.add("はや-い");
    	kanji[621].reading.add("はや-める");
    	kanji[621].reading.add("はや-まる");
    	kanji[621].reading.add("すみ-やか");

    	kanji[622] = new Kanji("側", "人", 11);
    	kanji[622].reading.add("ソク");
    	kanji[622].reading.add("がわ");

    	kanji[623] = new Kanji("測", "水", 12);
    	kanji[623].reading.add("ソク");
    	kanji[623].reading.add("はか-る");

    	kanji[624] = new Kanji("族", "方", 11);
    	kanji[624].reading.add("ゾク");

    	kanji[625] = new Kanji("属", "尸", 12);
    	kanji[625].reading.add("ゾク");

    	kanji[626] = new Kanji("続", "糸", 13);
    	kanji[626].reading.add("ゾク");
    	kanji[626].reading.add("つづ-く");
    	kanji[626].reading.add("つづ-ける");

    	kanji[627] = new Kanji("卒", "十", 8);
    	kanji[627].reading.add("ソツ");

    	kanji[628] = new Kanji("率", "玄", 11);
    	kanji[628].reading.add("ソツ");
    	kanji[628].reading.add("リツ");
    	kanji[628].reading.add("ひき-いる");

    	kanji[629] = new Kanji("存", "子", 6);
    	kanji[629].reading.add("ソン");
    	kanji[629].reading.add("ゾン");

    	kanji[630] = new Kanji("村", "木", 7);
    	kanji[630].reading.add("ソン");
    	kanji[630].reading.add("むら");

    	kanji[631] = new Kanji("孫", "子", 10);
    	kanji[631].reading.add("ソン");
    	kanji[631].reading.add("まご");

    	kanji[632] = new Kanji("尊", "寸", 12);
    	kanji[632].reading.add("ソン");
    	kanji[632].reading.add("たっと-い");
    	kanji[632].reading.add("とうと-い");
    	kanji[632].reading.add("たっと-ぶ");
    	kanji[632].reading.add("とうと-ぶ");

    	kanji[633] = new Kanji("損", "手", 13);
    	kanji[633].reading.add("ソン");
    	kanji[633].reading.add("そこ-なう");
    	kanji[633].reading.add("そこ-ねる");

    	kanji[634] = new Kanji("他", "人", 5);
    	kanji[634].reading.add("タ");
    	kanji[634].reading.add("ほか");

    	kanji[635] = new Kanji("多", "夕", 6);
    	kanji[635].reading.add("タ");
    	kanji[635].reading.add("おお-い");

    	kanji[636] = new Kanji("打", "手", 5);
    	kanji[636].reading.add("ダ");
    	kanji[636].reading.add("う-つ");

    	kanji[637] = new Kanji("太", "大", 4);
    	kanji[637].reading.add("タイ");
    	kanji[637].reading.add("タ");
    	kanji[637].reading.add("ふと-い");
    	kanji[637].reading.add("ふと-る");

    	kanji[638] = new Kanji("対", "寸", 7);
    	kanji[638].reading.add("タイ");
    	kanji[638].reading.add("ツイ");

    	kanji[639] = new Kanji("体", "骨", 7);
    	kanji[639].reading.add("タイ");
    	kanji[639].reading.add("テイ");
    	kanji[639].reading.add("からだ");

    	kanji[640] = new Kanji("待", "彳", 9);
    	kanji[640].reading.add("タイ");
    	kanji[640].reading.add("ま-つ");

    	kanji[641] = new Kanji("退", "辵", 9);
    	kanji[641].reading.add("タイ");
    	kanji[641].reading.add("しりぞ-く");
    	kanji[641].reading.add("しりぞ-ける");

    	kanji[642] = new Kanji("帯", "巾", 10);
    	kanji[642].reading.add("タイ");
    	kanji[642].reading.add("お-びる");
    	kanji[642].reading.add("おび");

    	kanji[643] = new Kanji("貸", "貝", 12);
    	kanji[643].reading.add("タイ");
    	kanji[643].reading.add("か-す");

    	kanji[644] = new Kanji("隊", "阜", 12);
    	kanji[644].reading.add("タイ");

    	kanji[645] = new Kanji("態", "心", 14);
    	kanji[645].reading.add("タイ");

    	kanji[646] = new Kanji("大", "大", 3);
    	kanji[646].reading.add("ダイ");
    	kanji[646].reading.add("タイ");
    	kanji[646].reading.add("おお");
    	kanji[646].reading.add("おお-きい");
    	kanji[646].reading.add("おお-いに");

    	kanji[647] = new Kanji("代", "人", 5);
    	kanji[647].reading.add("ダイ");
    	kanji[647].reading.add("タイ");
    	kanji[647].reading.add("か-わる");
    	kanji[647].reading.add("か-える");
    	kanji[647].reading.add("よ");
    	kanji[647].reading.add("しろ");

    	kanji[648] = new Kanji("台", "至", 5);
    	kanji[648].reading.add("ダイ");
    	kanji[648].reading.add("タイ");

    	kanji[649] = new Kanji("第", "竹", 11);
    	kanji[649].reading.add("ダイ");

    	kanji[650] = new Kanji("題", "頁", 18);
    	kanji[650].reading.add("ダイ");

    	kanji[651] = new Kanji("宅", "宀", 6);
    	kanji[651].reading.add("タク");

    	kanji[652] = new Kanji("達", "辵", 12);
    	kanji[652].reading.add("タツ");

    	kanji[653] = new Kanji("担", "手", 8);
    	kanji[653].reading.add("タン");
    	kanji[653].reading.add("かつ-ぐ");
    	kanji[653].reading.add("にな-う");

    	kanji[654] = new Kanji("単", "口", 9);
    	kanji[654].reading.add("タン");

    	kanji[655] = new Kanji("炭", "火", 9);
    	kanji[655].reading.add("タン");
    	kanji[655].reading.add("すみ");

    	kanji[656] = new Kanji("探", "手", 11);
    	kanji[656].reading.add("タン");
    	kanji[656].reading.add("さぐ-る");
    	kanji[656].reading.add("さが-す");

    	kanji[657] = new Kanji("短", "矢", 12);
    	kanji[657].reading.add("タン");
    	kanji[657].reading.add("みじか-い");

    	kanji[658] = new Kanji("誕", "言", 15);
    	kanji[658].reading.add("タン");

    	kanji[659] = new Kanji("団", "囗", 6);
    	kanji[659].reading.add("ダン");

    	kanji[660] = new Kanji("男", "田", 7);
    	kanji[660].reading.add("ダン");
    	kanji[660].reading.add("ナン");
    	kanji[660].reading.add("おとこ");

    	kanji[661] = new Kanji("段", "殳", 9);
    	kanji[661].reading.add("ダン");

    	kanji[662] = new Kanji("断", "斤", 11);
    	kanji[662].reading.add("ダン");
    	kanji[662].reading.add("た-つ");
    	kanji[662].reading.add("ことわ-る");

    	kanji[663] = new Kanji("暖", "日", 13);
    	kanji[663].reading.add("ダン");
    	kanji[663].reading.add("あたた-か");
    	kanji[663].reading.add("あたた-かい");
    	kanji[663].reading.add("あたた-まる");
    	kanji[663].reading.add("あたた-める");

    	kanji[664] = new Kanji("談", "言", 15);
    	kanji[664].reading.add("ダン");

    	kanji[665] = new Kanji("地", "土", 6);
    	kanji[665].reading.add("チ");
    	kanji[665].reading.add("ジ");

    	kanji[666] = new Kanji("池", "水", 6);
    	kanji[666].reading.add("チ");
    	kanji[666].reading.add("いけ");

    	kanji[667] = new Kanji("知", "矢", 8);
    	kanji[667].reading.add("チ");
    	kanji[667].reading.add("し-る");

    	kanji[668] = new Kanji("値", "人", 10);
    	kanji[668].reading.add("チ");
    	kanji[668].reading.add("ね");
    	kanji[668].reading.add("あたい");

    	kanji[669] = new Kanji("置", "网", 13);
    	kanji[669].reading.add("チ");
    	kanji[669].reading.add("お-く");

    	kanji[670] = new Kanji("竹", "竹", 6);
    	kanji[670].reading.add("チク");
    	kanji[670].reading.add("たけ");

    	kanji[671] = new Kanji("築", "竹", 16);
    	kanji[671].reading.add("チク");
    	kanji[671].reading.add("きず-く");

    	kanji[672] = new Kanji("茶", "艸", 9);
    	kanji[672].reading.add("チャ");
    	kanji[672].reading.add("サ");

    	kanji[673] = new Kanji("着", "羊", 12);
    	kanji[673].reading.add("チャク");
    	kanji[673].reading.add("き-る");
    	kanji[673].reading.add("き-せる");
    	kanji[673].reading.add("つ-く");
    	kanji[673].reading.add("つ-ける");

    	kanji[674] = new Kanji("中", "丨", 4);
    	kanji[674].reading.add("チュウ");
    	kanji[674].reading.add("なか");

    	kanji[675] = new Kanji("仲", "人", 6);
    	kanji[675].reading.add("チュウ");
    	kanji[675].reading.add("なか");

    	kanji[676] = new Kanji("虫", "虫", 6);
    	kanji[676].reading.add("チュウ");
    	kanji[676].reading.add("むし");

    	kanji[677] = new Kanji("宙", "宀", 8);
    	kanji[677].reading.add("チュウ");

    	kanji[678] = new Kanji("忠", "心", 8);
    	kanji[678].reading.add("チュウ");

    	kanji[679] = new Kanji("注", "水", 8);
    	kanji[679].reading.add("チュウ");
    	kanji[679].reading.add("そそ-ぐ");

    	kanji[680] = new Kanji("昼", "日", 9);
    	kanji[680].reading.add("チュウ");
    	kanji[680].reading.add("ひる");

    	kanji[681] = new Kanji("柱", "木", 9);
    	kanji[681].reading.add("チュウ");
    	kanji[681].reading.add("はしら");

    	kanji[682] = new Kanji("著", "艸", 11);
    	kanji[682].reading.add("チョ");
    	kanji[682].reading.add("あらわ-す");
    	kanji[682].reading.add("いちじる-しい");

    	kanji[683] = new Kanji("貯", "貝", 12);
    	kanji[683].reading.add("チョ");

    	kanji[684] = new Kanji("丁", "一", 2);
    	kanji[684].reading.add("チョウ");
    	kanji[684].reading.add("テイ");

    	kanji[685] = new Kanji("庁", "广", 5);
    	kanji[685].reading.add("チョウ");

    	kanji[686] = new Kanji("兆", "儿", 6);
    	kanji[686].reading.add("チョウ");
    	kanji[686].reading.add("きざ-す");
    	kanji[686].reading.add("きざ-し");

    	kanji[687] = new Kanji("町", "田", 7);
    	kanji[687].reading.add("チョウ");
    	kanji[687].reading.add("まち");

    	kanji[688] = new Kanji("長", "長", 8);
    	kanji[688].reading.add("チョウ");
    	kanji[688].reading.add("なが-い");

    	kanji[689] = new Kanji("帳", "巾", 11);
    	kanji[689].reading.add("チョウ");

    	kanji[690] = new Kanji("張", "弓", 11);
    	kanji[690].reading.add("チョウ");
    	kanji[690].reading.add("は-る");

    	kanji[691] = new Kanji("頂", "頁", 11);
    	kanji[691].reading.add("チョウ");
    	kanji[691].reading.add("いただ-く");
    	kanji[691].reading.add("いただき");

    	kanji[692] = new Kanji("鳥", "鳥", 11);
    	kanji[692].reading.add("チョウ");
    	kanji[692].reading.add("とり");

    	kanji[693] = new Kanji("朝", "月", 12);
    	kanji[693].reading.add("チョウ");
    	kanji[693].reading.add("あさ");

    	kanji[694] = new Kanji("腸", "肉", 13);
    	kanji[694].reading.add("チョウ");

    	kanji[695] = new Kanji("潮", "水", 15);
    	kanji[695].reading.add("チョウ");
    	kanji[695].reading.add("しお");

    	kanji[696] = new Kanji("調", "言", 15);
    	kanji[696].reading.add("チョウ");
    	kanji[696].reading.add("しら-べる");
    	kanji[696].reading.add("ととの-う");
    	kanji[696].reading.add("ととの-える");

    	kanji[697] = new Kanji("直", "目", 8);
    	kanji[697].reading.add("チョク");
    	kanji[697].reading.add("ジキ");
    	kanji[697].reading.add("ただ-ちに");
    	kanji[697].reading.add("なお-す");
    	kanji[697].reading.add("なお-る");

    	kanji[698] = new Kanji("賃", "貝", 13);
    	kanji[698].reading.add("チン");

    	kanji[699] = new Kanji("追", "辵", 9);
    	kanji[699].reading.add("ツイ");
    	kanji[699].reading.add("お-う");

    	kanji[700] = new Kanji("通", "辵", 10);
    	kanji[700].reading.add("ツウ");
    	kanji[700].reading.add("とお-る");
    	kanji[700].reading.add("とお-す");
    	kanji[700].reading.add("かよ-う");

    	kanji[701] = new Kanji("痛", "疒", 12);
    	kanji[701].reading.add("ツウ");
    	kanji[701].reading.add("いた-い");
    	kanji[701].reading.add("いた-む");
    	kanji[701].reading.add("いた-める");

    	kanji[702] = new Kanji("低", "人", 7);
    	kanji[702].reading.add("テイ");
    	kanji[702].reading.add("ひく-い");
    	kanji[702].reading.add("ひく-める");
    	kanji[702].reading.add("ひく-まる");

    	kanji[703] = new Kanji("弟", "弓", 7);
    	kanji[703].reading.add("テイ");
    	kanji[703].reading.add("おとうと");

    	kanji[704] = new Kanji("定", "宀", 8);
    	kanji[704].reading.add("テイ");
    	kanji[704].reading.add("ジョウ");
    	kanji[704].reading.add("さだ-める");
    	kanji[704].reading.add("さだ-まる");
    	kanji[704].reading.add("さだ-か");

    	kanji[705] = new Kanji("底", "广", 8);
    	kanji[705].reading.add("テイ");
    	kanji[705].reading.add("そこ");

    	kanji[706] = new Kanji("庭", "广", 10);
    	kanji[706].reading.add("テイ");
    	kanji[706].reading.add("にわ");

    	kanji[707] = new Kanji("停", "人", 11);
    	kanji[707].reading.add("テイ");

    	kanji[708] = new Kanji("提", "手", 12);
    	kanji[708].reading.add("テイ");
    	kanji[708].reading.add("さ-げる");

    	kanji[709] = new Kanji("程", "禾", 12);
    	kanji[709].reading.add("テイ");
    	kanji[709].reading.add("ほど");

    	kanji[710] = new Kanji("的", "白", 8);
    	kanji[710].reading.add("テキ");
    	kanji[710].reading.add("まと");

    	kanji[711] = new Kanji("笛", "竹", 11);
    	kanji[711].reading.add("テキ");
    	kanji[711].reading.add("ふえ");

    	kanji[712] = new Kanji("適", "辵", 14);
    	kanji[712].reading.add("テキ");

    	kanji[713] = new Kanji("敵", "攴", 15);
    	kanji[713].reading.add("テキ");
    	kanji[713].reading.add("かたき");

    	kanji[714] = new Kanji("鉄", "金", 13);
    	kanji[714].reading.add("テツ");

    	kanji[715] = new Kanji("天", "大", 4);
    	kanji[715].reading.add("テン");
    	kanji[715].reading.add("あめ");

    	kanji[716] = new Kanji("典", "八", 8);
    	kanji[716].reading.add("テン");

    	kanji[717] = new Kanji("店", "广", 8);
    	kanji[717].reading.add("テン");
    	kanji[717].reading.add("みせ");

    	kanji[718] = new Kanji("点", "黑", 9);
    	kanji[718].reading.add("テン");

    	kanji[719] = new Kanji("展", "尸", 10);
    	kanji[719].reading.add("テン");

    	kanji[720] = new Kanji("転", "車", 11);
    	kanji[720].reading.add("テン");
    	kanji[720].reading.add("ころ-がる");
    	kanji[720].reading.add("ころ-げる");
    	kanji[720].reading.add("ころ-がす");
    	kanji[720].reading.add("ころ-ぶ");

    	kanji[721] = new Kanji("田", "田", 5);
    	kanji[721].reading.add("デン");
    	kanji[721].reading.add("た");

    	kanji[722] = new Kanji("伝", "人", 6);
    	kanji[722].reading.add("デン");
    	kanji[722].reading.add("つた-わる");
    	kanji[722].reading.add("つた-える");
    	kanji[722].reading.add("つた-う");

    	kanji[723] = new Kanji("電", "雨", 13);
    	kanji[723].reading.add("デン");

    	kanji[724] = new Kanji("徒", "彳", 10);
    	kanji[724].reading.add("ト");

    	kanji[725] = new Kanji("都", "邑", 11);
    	kanji[725].reading.add("ト");
    	kanji[725].reading.add("ツ");
    	kanji[725].reading.add("みやこ");

    	kanji[726] = new Kanji("土", "土", 3);
    	kanji[726].reading.add("ド");
    	kanji[726].reading.add("ト");
    	kanji[726].reading.add("つち");

    	kanji[727] = new Kanji("努", "力", 7);
    	kanji[727].reading.add("ド");
    	kanji[727].reading.add("つと-める");

    	kanji[728] = new Kanji("度", "广", 9);
    	kanji[728].reading.add("ド");
    	kanji[728].reading.add("たび");

    	kanji[729] = new Kanji("刀", "刀", 2);
    	kanji[729].reading.add("トウ");
    	kanji[729].reading.add("かたな");

    	kanji[730] = new Kanji("冬", "冫", 5);
    	kanji[730].reading.add("トウ");
    	kanji[730].reading.add("ふゆ");

    	kanji[731] = new Kanji("灯", "火", 6);
    	kanji[731].reading.add("トウ");
    	kanji[731].reading.add("ひ");

    	kanji[732] = new Kanji("当", "田", 6);
    	kanji[732].reading.add("トウ");
    	kanji[732].reading.add("あ-たる");
    	kanji[732].reading.add("あ-てる");

    	kanji[733] = new Kanji("投", "手", 7);
    	kanji[733].reading.add("トウ");
    	kanji[733].reading.add("な-げる");

    	kanji[734] = new Kanji("豆", "豆", 7);
    	kanji[734].reading.add("トウ");
    	kanji[734].reading.add("まめ");

    	kanji[735] = new Kanji("東", "木", 8);
    	kanji[735].reading.add("トウ");
    	kanji[735].reading.add("ひがし");

    	kanji[736] = new Kanji("島", "山", 10);
    	kanji[736].reading.add("トウ");
    	kanji[736].reading.add("しま");

    	kanji[737] = new Kanji("討", "言", 10);
    	kanji[737].reading.add("トウ");
    	kanji[737].reading.add("う-つ");

    	kanji[738] = new Kanji("党", "黑", 10);
    	kanji[738].reading.add("トウ");

    	kanji[739] = new Kanji("湯", "水", 12);
    	kanji[739].reading.add("トウ");
    	kanji[739].reading.add("ゆ");

    	kanji[740] = new Kanji("登", "癶", 12);
    	kanji[740].reading.add("トウ");
    	kanji[740].reading.add("ト");
    	kanji[740].reading.add("のぼ-る");

    	kanji[741] = new Kanji("答", "竹", 12);
    	kanji[741].reading.add("トウ");
    	kanji[741].reading.add("こた-える");
    	kanji[741].reading.add("こた-え");

    	kanji[742] = new Kanji("等", "竹", 12);
    	kanji[742].reading.add("トウ");
    	kanji[742].reading.add("ひと-しい");

    	kanji[743] = new Kanji("統", "糸", 12);
    	kanji[743].reading.add("トウ");
    	kanji[743].reading.add("す-べる");

    	kanji[744] = new Kanji("糖", "米", 16);
    	kanji[744].reading.add("トウ");

    	kanji[745] = new Kanji("頭", "頁", 16);
    	kanji[745].reading.add("トウ");
    	kanji[745].reading.add("ズ");
    	kanji[745].reading.add("あたま");
    	kanji[745].reading.add("かしら");

    	kanji[746] = new Kanji("同", "口", 6);
    	kanji[746].reading.add("ドウ");
    	kanji[746].reading.add("おな-じ");

    	kanji[747] = new Kanji("動", "力", 11);
    	kanji[747].reading.add("ドウ");
    	kanji[747].reading.add("うご-く");
    	kanji[747].reading.add("うご-かす");

    	kanji[748] = new Kanji("堂", "土", 11);
    	kanji[748].reading.add("ドウ");

    	kanji[749] = new Kanji("童", "立", 12);
    	kanji[749].reading.add("ドウ");
    	kanji[749].reading.add("わらべ");

    	kanji[750] = new Kanji("道", "辵", 12);
    	kanji[750].reading.add("ドウ");
    	kanji[750].reading.add("みち");

    	kanji[751] = new Kanji("働", "人", 13);
    	kanji[751].reading.add("ドウ");
    	kanji[751].reading.add("はたら-く");

    	kanji[752] = new Kanji("銅", "金", 14);
    	kanji[752].reading.add("ドウ");

    	kanji[753] = new Kanji("導", "寸", 15);
    	kanji[753].reading.add("ドウ");
    	kanji[753].reading.add("みちび-く");

    	kanji[754] = new Kanji("特", "牛", 10);
    	kanji[754].reading.add("トク");

    	kanji[755] = new Kanji("得", "彳", 11);
    	kanji[755].reading.add("トク");
    	kanji[755].reading.add("え-る");
    	kanji[755].reading.add("う-る");

    	kanji[756] = new Kanji("徳", "彳", 14);
    	kanji[756].reading.add("トク");

    	kanji[757] = new Kanji("毒", "毋", 8);
    	kanji[757].reading.add("ドク");

    	kanji[758] = new Kanji("独", "犬", 9);
    	kanji[758].reading.add("ドク");
    	kanji[758].reading.add("ひと-り");

    	kanji[759] = new Kanji("読", "言", 14);
    	kanji[759].reading.add("ドク");
    	kanji[759].reading.add("トク");
    	kanji[759].reading.add("よ-む");

    	kanji[760] = new Kanji("届", "尸", 8);
    	kanji[760].reading.add("とど-ける");
    	kanji[760].reading.add("とど-く");

    	kanji[761] = new Kanji("内", "入", 4);
    	kanji[761].reading.add("ナイ");
    	kanji[761].reading.add("うち");

    	kanji[762] = new Kanji("南", "十", 9);
    	kanji[762].reading.add("ナン");
    	kanji[762].reading.add("みなみ");

    	kanji[763] = new Kanji("難", "隹", 18);
    	kanji[763].reading.add("ナン");
    	kanji[763].reading.add("かた-い");
    	kanji[763].reading.add("むずか-しい");

    	kanji[764] = new Kanji("二", "二", 2);
    	kanji[764].reading.add("ニ");
    	kanji[764].reading.add("ふた");
    	kanji[764].reading.add("ふた-つ");

    	kanji[765] = new Kanji("肉", "肉", 6);
    	kanji[765].reading.add("ニク");

    	kanji[766] = new Kanji("日", "日", 4);
    	kanji[766].reading.add("ニチ");
    	kanji[766].reading.add("ジツ");
    	kanji[766].reading.add("ひ");
    	kanji[766].reading.add("か");

    	kanji[767] = new Kanji("入", "入", 2);
    	kanji[767].reading.add("ニュウ");
    	kanji[767].reading.add("い-る");
    	kanji[767].reading.add("い-れる");
    	kanji[767].reading.add("はい-る");

    	kanji[768] = new Kanji("乳", "乙", 8);
    	kanji[768].reading.add("ニュウ");
    	kanji[768].reading.add("ちち");
    	kanji[768].reading.add("ち");

    	kanji[769] = new Kanji("任", "人", 6);
    	kanji[769].reading.add("ニン");
    	kanji[769].reading.add("まか-せる");
    	kanji[769].reading.add("まか-す");

    	kanji[770] = new Kanji("認", "言", 14);
    	kanji[770].reading.add("ニン");
    	kanji[770].reading.add("みと-める");

    	kanji[771] = new Kanji("熱", "火", 15);
    	kanji[771].reading.add("ネツ");
    	kanji[771].reading.add("あつ-い");

    	kanji[772] = new Kanji("年", "干", 6);
    	kanji[772].reading.add("ネン");
    	kanji[772].reading.add("とし");

    	kanji[773] = new Kanji("念", "心", 8);
    	kanji[773].reading.add("ネン");

    	kanji[774] = new Kanji("燃", "火", 16);
    	kanji[774].reading.add("ネン");
    	kanji[774].reading.add("も-える");
    	kanji[774].reading.add("も-やす");
    	kanji[774].reading.add("も-す");

    	kanji[775] = new Kanji("納", "糸", 10);
    	kanji[775].reading.add("ノウ");
    	kanji[775].reading.add("おさ-める");
    	kanji[775].reading.add("おさ-まる");

    	kanji[776] = new Kanji("能", "肉", 10);
    	kanji[776].reading.add("ノウ");

    	kanji[777] = new Kanji("脳", "肉", 11);
    	kanji[777].reading.add("ノウ");

    	kanji[778] = new Kanji("農", "辰", 13);
    	kanji[778].reading.add("ノウ");

    	kanji[779] = new Kanji("波", "水", 8);
    	kanji[779].reading.add("ハ");
    	kanji[779].reading.add("なみ");

    	kanji[780] = new Kanji("派", "水", 9);
    	kanji[780].reading.add("ハ");

    	kanji[781] = new Kanji("破", "石", 10);
    	kanji[781].reading.add("ハ");
    	kanji[781].reading.add("やぶ-る");
    	kanji[781].reading.add("やぶ-れる");

    	kanji[782] = new Kanji("馬", "馬", 10);
    	kanji[782].reading.add("バ");
    	kanji[782].reading.add("うま");

    	kanji[783] = new Kanji("拝", "手", 8);
    	kanji[783].reading.add("ハイ");
    	kanji[783].reading.add("おが-む");

    	kanji[784] = new Kanji("背", "肉", 9);
    	kanji[784].reading.add("ハイ");
    	kanji[784].reading.add("せ");
    	kanji[784].reading.add("せい");
    	kanji[784].reading.add("そむ-く");
    	kanji[784].reading.add("そむ-ける");

    	kanji[785] = new Kanji("肺", "肉", 9);
    	kanji[785].reading.add("ハイ");

    	kanji[786] = new Kanji("俳", "人", 10);
    	kanji[786].reading.add("ハイ");

    	kanji[787] = new Kanji("配", "酉", 10);
    	kanji[787].reading.add("ハイ");
    	kanji[787].reading.add("くば-る");

    	kanji[788] = new Kanji("敗", "攴", 11);
    	kanji[788].reading.add("ハイ");
    	kanji[788].reading.add("やぶ-れる");

    	kanji[789] = new Kanji("売", "貝", 7);
    	kanji[789].reading.add("バイ");
    	kanji[789].reading.add("う-る");
    	kanji[789].reading.add("う-れる");

    	kanji[790] = new Kanji("倍", "人", 10);
    	kanji[790].reading.add("バイ");

    	kanji[791] = new Kanji("梅", "木", 10);
    	kanji[791].reading.add("バイ");
    	kanji[791].reading.add("うめ");

    	kanji[792] = new Kanji("買", "貝", 12);
    	kanji[792].reading.add("バイ");
    	kanji[792].reading.add("か-う");

    	kanji[793] = new Kanji("白", "白", 5);
    	kanji[793].reading.add("ハク");
    	kanji[793].reading.add("ビャク");
    	kanji[793].reading.add("しろ");
    	kanji[793].reading.add("しろ-い");

    	kanji[794] = new Kanji("博", "十", 12);
    	kanji[794].reading.add("ハク");

    	kanji[795] = new Kanji("麦", "麥", 7);
    	kanji[795].reading.add("バク");
    	kanji[795].reading.add("むぎ");

    	kanji[796] = new Kanji("箱", "竹", 15);
    	kanji[796].reading.add("はこ");

    	kanji[797] = new Kanji("畑", "田", 9);
    	kanji[797].reading.add("はた");
    	kanji[797].reading.add("はたけ");

    	kanji[798] = new Kanji("八", "八", 2);
    	kanji[798].reading.add("ハチ");
    	kanji[798].reading.add("や");
    	kanji[798].reading.add("や-つ");
    	kanji[798].reading.add("やっ-つ");

    	kanji[799] = new Kanji("発", "癶", 9);
    	kanji[799].reading.add("ハツ");
    	kanji[799].reading.add("ホツ");

    	kanji[800] = new Kanji("反", "又", 4);
    	kanji[800].reading.add("ハン");
    	kanji[800].reading.add("そ-る");
    	kanji[800].reading.add("そ-らす");

    	kanji[801] = new Kanji("半", "十", 5);
    	kanji[801].reading.add("ハン");
    	kanji[801].reading.add("なか-ば");

    	kanji[802] = new Kanji("犯", "犬", 5);
    	kanji[802].reading.add("ハン");
    	kanji[802].reading.add("おか-す");

    	kanji[803] = new Kanji("判", "刀", 7);
    	kanji[803].reading.add("ハン");
    	kanji[803].reading.add("バン");

    	kanji[804] = new Kanji("坂", "土", 7);
    	kanji[804].reading.add("ハン");
    	kanji[804].reading.add("さか");

    	kanji[805] = new Kanji("板", "木", 8);
    	kanji[805].reading.add("ハン");
    	kanji[805].reading.add("バン");
    	kanji[805].reading.add("いた");

    	kanji[806] = new Kanji("版", "片", 8);
    	kanji[806].reading.add("ハン");

    	kanji[807] = new Kanji("班", "玉", 10);
    	kanji[807].reading.add("ハン");

    	kanji[808] = new Kanji("飯", "食", 12);
    	kanji[808].reading.add("ハン");
    	kanji[808].reading.add("めし");

    	kanji[809] = new Kanji("晩", "日", 12);
    	kanji[809].reading.add("バン");

    	kanji[810] = new Kanji("番", "田", 12);
    	kanji[810].reading.add("バン");

    	kanji[811] = new Kanji("比", "比", 4);
    	kanji[811].reading.add("ヒ");
    	kanji[811].reading.add("くら-べる");

    	kanji[812] = new Kanji("皮", "皮", 5);
    	kanji[812].reading.add("ヒ");
    	kanji[812].reading.add("かわ");

    	kanji[813] = new Kanji("否", "口", 7);
    	kanji[813].reading.add("ヒ");
    	kanji[813].reading.add("いな");

    	kanji[814] = new Kanji("批", "手", 7);
    	kanji[814].reading.add("ヒ");

    	kanji[815] = new Kanji("肥", "肉", 8);
    	kanji[815].reading.add("ヒ");
    	kanji[815].reading.add("こ-える");
    	kanji[815].reading.add("こえ");
    	kanji[815].reading.add("こ-やす");
    	kanji[815].reading.add("こ-やし");

    	kanji[816] = new Kanji("非", "非", 8);
    	kanji[816].reading.add("ヒ");

    	kanji[817] = new Kanji("飛", "飛", 9);
    	kanji[817].reading.add("ヒ");
    	kanji[817].reading.add("と-ぶ");
    	kanji[817].reading.add("と-ばす");

    	kanji[818] = new Kanji("秘", "示", 10);
    	kanji[818].reading.add("ヒ");
    	kanji[818].reading.add("ひ-める");

    	kanji[819] = new Kanji("悲", "心", 12);
    	kanji[819].reading.add("ヒ");
    	kanji[819].reading.add("かな-しい");
    	kanji[819].reading.add("かな-しむ");

    	kanji[820] = new Kanji("費", "貝", 12);
    	kanji[820].reading.add("ヒ");
    	kanji[820].reading.add("つい-やす");
    	kanji[820].reading.add("つい-える");

    	kanji[821] = new Kanji("美", "羊", 9);
    	kanji[821].reading.add("ビ");
    	kanji[821].reading.add("うつく-しい");

    	kanji[822] = new Kanji("備", "人", 12);
    	kanji[822].reading.add("ビ");
    	kanji[822].reading.add("そな-える");
    	kanji[822].reading.add("そな-わる");

    	kanji[823] = new Kanji("鼻", "鼻", 14);
    	kanji[823].reading.add("ビ");
    	kanji[823].reading.add("はな");

    	kanji[824] = new Kanji("必", "心", 5);
    	kanji[824].reading.add("ヒツ");
    	kanji[824].reading.add("かなら-ず");

    	kanji[825] = new Kanji("筆", "竹", 12);
    	kanji[825].reading.add("ヒツ");
    	kanji[825].reading.add("ふで");

    	kanji[826] = new Kanji("百", "白", 6);
    	kanji[826].reading.add("ヒャク");

    	kanji[827] = new Kanji("氷", "水", 5);
    	kanji[827].reading.add("ヒョウ");
    	kanji[827].reading.add("こおり");
    	kanji[827].reading.add("ひ");

    	kanji[828] = new Kanji("表", "衣", 8);
    	kanji[828].reading.add("ヒョウ");
    	kanji[828].reading.add("おもて");
    	kanji[828].reading.add("あらわ-す");
    	kanji[828].reading.add("あらわ-れる");

    	kanji[829] = new Kanji("俵", "人", 10);
    	kanji[829].reading.add("ヒョウ");
    	kanji[829].reading.add("たわら");

    	kanji[830] = new Kanji("票", "示", 11);
    	kanji[830].reading.add("ヒョウ");

    	kanji[831] = new Kanji("評", "言", 12);
    	kanji[831].reading.add("ヒョウ");

    	kanji[832] = new Kanji("標", "木", 15);
    	kanji[832].reading.add("ヒョウ");

    	kanji[833] = new Kanji("秒", "禾", 9);
    	kanji[833].reading.add("ビョウ");

    	kanji[834] = new Kanji("病", "疒", 10);
    	kanji[834].reading.add("ビョウ");
    	kanji[834].reading.add("や-む");
    	kanji[834].reading.add("やまい");

    	kanji[835] = new Kanji("品", "口", 9);
    	kanji[835].reading.add("ヒン");
    	kanji[835].reading.add("しな");

    	kanji[836] = new Kanji("貧", "貝", 11);
    	kanji[836].reading.add("ヒン");
    	kanji[836].reading.add("ビン");
    	kanji[836].reading.add("まず-しい");

    	kanji[837] = new Kanji("不", "一", 4);
    	kanji[837].reading.add("フ");
    	kanji[837].reading.add("ブ");

    	kanji[838] = new Kanji("夫", "大", 4);
    	kanji[838].reading.add("フ");
    	kanji[838].reading.add("おっと");

    	kanji[839] = new Kanji("父", "父", 4);
    	kanji[839].reading.add("フ");
    	kanji[839].reading.add("ちち");

    	kanji[840] = new Kanji("付", "人", 5);
    	kanji[840].reading.add("フ");
    	kanji[840].reading.add("つ-ける");
    	kanji[840].reading.add("つ-く");

    	kanji[841] = new Kanji("布", "巾", 5);
    	kanji[841].reading.add("フ");
    	kanji[841].reading.add("ぬの");

    	kanji[842] = new Kanji("府", "广", 8);
    	kanji[842].reading.add("フ");

    	kanji[843] = new Kanji("負", "貝", 9);
    	kanji[843].reading.add("フ");
    	kanji[843].reading.add("ま-ける");
    	kanji[843].reading.add("ま-かす");
    	kanji[843].reading.add("お-う");

    	kanji[844] = new Kanji("婦", "女", 11);
    	kanji[844].reading.add("フ");

    	kanji[845] = new Kanji("富", "宀", 12);
    	kanji[845].reading.add("フ");
    	kanji[845].reading.add("と-む");
    	kanji[845].reading.add("とみ");

    	kanji[846] = new Kanji("武", "止", 8);
    	kanji[846].reading.add("ブ");
    	kanji[846].reading.add("ム");

    	kanji[847] = new Kanji("部", "邑", 11);
    	kanji[847].reading.add("ブ");

    	kanji[848] = new Kanji("風", "風", 9);
    	kanji[848].reading.add("フウ");
    	kanji[848].reading.add("かぜ");

    	kanji[849] = new Kanji("服", "月", 8);
    	kanji[849].reading.add("フク");

    	kanji[850] = new Kanji("副", "刀", 11);
    	kanji[850].reading.add("フク");

    	kanji[851] = new Kanji("復", "彳", 12);
    	kanji[851].reading.add("フク");

    	kanji[852] = new Kanji("福", "示", 13);
    	kanji[852].reading.add("フク");

    	kanji[853] = new Kanji("腹", "肉", 13);
    	kanji[853].reading.add("フク");
    	kanji[853].reading.add("はら");

    	kanji[854] = new Kanji("複", "衣", 14);
    	kanji[854].reading.add("フク");

    	kanji[855] = new Kanji("仏", "人", 4);
    	kanji[855].reading.add("ブツ");
    	kanji[855].reading.add("ほとけ");

    	kanji[856] = new Kanji("物", "牛", 8);
    	kanji[856].reading.add("ブツ");
    	kanji[856].reading.add("モツ");
    	kanji[856].reading.add("もの");

    	kanji[857] = new Kanji("粉", "米", 10);
    	kanji[857].reading.add("フン");
    	kanji[857].reading.add("こ");
    	kanji[857].reading.add("こな");

    	kanji[858] = new Kanji("奮", "大", 16);
    	kanji[858].reading.add("フン");
    	kanji[858].reading.add("ふる-う");

    	kanji[859] = new Kanji("分", "刀", 4);
    	kanji[859].reading.add("ブン");
    	kanji[859].reading.add("フン");
    	kanji[859].reading.add("ブ");
    	kanji[859].reading.add("わ-ける");
    	kanji[859].reading.add("わ-かれる");
    	kanji[859].reading.add("わ-かる");
    	kanji[859].reading.add("わ-かつ");

    	kanji[860] = new Kanji("文", "文", 4);
    	kanji[860].reading.add("ブン");
    	kanji[860].reading.add("モン");
    	kanji[860].reading.add("ふみ");

    	kanji[861] = new Kanji("聞", "耳", 14);
    	kanji[861].reading.add("ブン");
    	kanji[861].reading.add("モン");
    	kanji[861].reading.add("き-く");
    	kanji[861].reading.add("き-こえる");

    	kanji[862] = new Kanji("平", "干", 5);
    	kanji[862].reading.add("ヘイ");
    	kanji[862].reading.add("ビョウ");
    	kanji[862].reading.add("たい-ら");
    	kanji[862].reading.add("ひら");

    	kanji[863] = new Kanji("兵", "八", 7);
    	kanji[863].reading.add("ヘイ");
    	kanji[863].reading.add("ヒョウ");

    	kanji[864] = new Kanji("並", "立", 8);
    	kanji[864].reading.add("ヘイ");
    	kanji[864].reading.add("なみ");
    	kanji[864].reading.add("なら-べる");
    	kanji[864].reading.add("なら-ぶ");
    	kanji[864].reading.add("なら-びに");

    	kanji[865] = new Kanji("陛", "阜", 10);
    	kanji[865].reading.add("ヘイ");

    	kanji[866] = new Kanji("閉", "門", 11);
    	kanji[866].reading.add("ヘイ");
    	kanji[866].reading.add("と-じる");
    	kanji[866].reading.add("と-ざす");
    	kanji[866].reading.add("し-める");
    	kanji[866].reading.add("し-まる");

    	kanji[867] = new Kanji("米", "米", 6);
    	kanji[867].reading.add("ベイ");
    	kanji[867].reading.add("マイ");
    	kanji[867].reading.add("こめ");

    	kanji[868] = new Kanji("別", "刀", 7);
    	kanji[868].reading.add("ベツ");
    	kanji[868].reading.add("わか-れる");

    	kanji[869] = new Kanji("片", "片", 4);
    	kanji[869].reading.add("ヘン");
    	kanji[869].reading.add("かた");

    	kanji[870] = new Kanji("辺", "辵", 5);
    	kanji[870].reading.add("ヘン");
    	kanji[870].reading.add("あた-り");
    	kanji[870].reading.add("べ");

    	kanji[871] = new Kanji("返", "辵", 7);
    	kanji[871].reading.add("ヘン");
    	kanji[871].reading.add("かえ-す");
    	kanji[871].reading.add("かえ-る");

    	kanji[872] = new Kanji("変", "言", 9);
    	kanji[872].reading.add("ヘン");
    	kanji[872].reading.add("か-わる");
    	kanji[872].reading.add("か-える");

    	kanji[873] = new Kanji("編", "糸", 15);
    	kanji[873].reading.add("ヘン");
    	kanji[873].reading.add("あ-む");

    	kanji[874] = new Kanji("弁", "辛", 5);
    	kanji[874].reading.add("ベン");

    	kanji[875] = new Kanji("便", "人", 9);
    	kanji[875].reading.add("ベン");
    	kanji[875].reading.add("ビン");
    	kanji[875].reading.add("たよ-り");

    	kanji[876] = new Kanji("勉", "力", 10);
    	kanji[876].reading.add("ベン");

    	kanji[877] = new Kanji("歩", "止", 8);
    	kanji[877].reading.add("ホ");
    	kanji[877].reading.add("ブ");
    	kanji[877].reading.add("ある-く");
    	kanji[877].reading.add("あゆ-む");

    	kanji[878] = new Kanji("保", "人", 9);
    	kanji[878].reading.add("ホ");
    	kanji[878].reading.add("たも-つ");

    	kanji[879] = new Kanji("補", "衣", 12);
    	kanji[879].reading.add("ホ");
    	kanji[879].reading.add("おぎな-う");

    	kanji[880] = new Kanji("母", "毋", 5);
    	kanji[880].reading.add("ボ");
    	kanji[880].reading.add("はは");

    	kanji[881] = new Kanji("墓", "土", 13);
    	kanji[881].reading.add("ボ");
    	kanji[881].reading.add("はか");

    	kanji[882] = new Kanji("暮", "日", 14);
    	kanji[882].reading.add("ボ");
    	kanji[882].reading.add("く-れる");
    	kanji[882].reading.add("く-らす");

    	kanji[883] = new Kanji("方", "方", 4);
    	kanji[883].reading.add("ホウ");
    	kanji[883].reading.add("かた");

    	kanji[884] = new Kanji("包", "勹", 5);
    	kanji[884].reading.add("ホウ");
    	kanji[884].reading.add("つつ-む");

    	kanji[885] = new Kanji("宝", "宀", 8);
    	kanji[885].reading.add("ホウ");
    	kanji[885].reading.add("たから");

    	kanji[886] = new Kanji("放", "攴", 8);
    	kanji[886].reading.add("ホウ");
    	kanji[886].reading.add("はな-す");
    	kanji[886].reading.add("はな-つ");
    	kanji[886].reading.add("はな-れる");
    	kanji[886].reading.add("ほう-る");

    	kanji[887] = new Kanji("法", "水", 8);
    	kanji[887].reading.add("ホウ");

    	kanji[888] = new Kanji("訪", "言", 11);
    	kanji[888].reading.add("ホウ");
    	kanji[888].reading.add("おとず-れる");
    	kanji[888].reading.add("たず-ねる");

    	kanji[889] = new Kanji("報", "土", 12);
    	kanji[889].reading.add("ホウ");
    	kanji[889].reading.add("むく-いる");

    	kanji[890] = new Kanji("豊", "豆", 13);
    	kanji[890].reading.add("ホウ");
    	kanji[890].reading.add("ゆた-か");

    	kanji[891] = new Kanji("亡", "亠", 3);
    	kanji[891].reading.add("ボウ");
    	kanji[891].reading.add("な-い");

    	kanji[892] = new Kanji("忘", "心", 7);
    	kanji[892].reading.add("ボウ");
    	kanji[892].reading.add("わす-れる");

    	kanji[893] = new Kanji("防", "阜", 7);
    	kanji[893].reading.add("ボウ");
    	kanji[893].reading.add("ふせ-ぐ");

    	kanji[894] = new Kanji("望", "月", 11);
    	kanji[894].reading.add("ボウ");
    	kanji[894].reading.add("モウ");
    	kanji[894].reading.add("のぞ-む");

    	kanji[895] = new Kanji("棒", "木", 12);
    	kanji[895].reading.add("ボウ");

    	kanji[896] = new Kanji("貿", "貝", 12);
    	kanji[896].reading.add("ボウ");

    	kanji[897] = new Kanji("暴", "日", 15);
    	kanji[897].reading.add("ボウ");
    	kanji[897].reading.add("あば-く");
    	kanji[897].reading.add("あば-れる");

    	kanji[898] = new Kanji("北", "匕", 5);
    	kanji[898].reading.add("ホク");
    	kanji[898].reading.add("きた");

    	kanji[899] = new Kanji("木", "木", 4);
    	kanji[899].reading.add("ボク");
    	kanji[899].reading.add("モク");
    	kanji[899].reading.add("き");

    	kanji[900] = new Kanji("牧", "牛", 8);
    	kanji[900].reading.add("ボク");
    	kanji[900].reading.add("まき");

    	kanji[901] = new Kanji("本", "木", 5);
    	kanji[901].reading.add("ホン");
    	kanji[901].reading.add("もと");

    	kanji[902] = new Kanji("毎", "毋", 6);
    	kanji[902].reading.add("マイ");

    	kanji[903] = new Kanji("妹", "女", 8);
    	kanji[903].reading.add("マイ");
    	kanji[903].reading.add("いもうと");

    	kanji[904] = new Kanji("枚", "木", 8);
    	kanji[904].reading.add("マイ");

    	kanji[905] = new Kanji("幕", "巾", 13);
    	kanji[905].reading.add("マク");
    	kanji[905].reading.add("バク");

    	kanji[906] = new Kanji("末", "木", 5);
    	kanji[906].reading.add("マツ");
    	kanji[906].reading.add("バツ");
    	kanji[906].reading.add("すえ");

    	kanji[907] = new Kanji("万", "艸", 3);
    	kanji[907].reading.add("マン");
    	kanji[907].reading.add("バン");

    	kanji[908] = new Kanji("満", "水", 12);
    	kanji[908].reading.add("マン");
    	kanji[908].reading.add("み-ちる");
    	kanji[908].reading.add("み-たす");

    	kanji[909] = new Kanji("未", "木", 5);
    	kanji[909].reading.add("ミ");

    	kanji[910] = new Kanji("味", "口", 8);
    	kanji[910].reading.add("ミ");
    	kanji[910].reading.add("あじ");
    	kanji[910].reading.add("あじ-わう");

    	kanji[911] = new Kanji("密", "宀", 11);
    	kanji[911].reading.add("ミツ");

    	kanji[912] = new Kanji("脈", "肉", 10);
    	kanji[912].reading.add("ミャク");

    	kanji[913] = new Kanji("民", "氏", 5);
    	kanji[913].reading.add("ミ");
    	kanji[913].reading.add("たみ");

    	kanji[914] = new Kanji("務", "力", 11);
    	kanji[914].reading.add("ム");
    	kanji[914].reading.add("つと-める");
    	kanji[914].reading.add("つと-まる");

    	kanji[915] = new Kanji("無", "火", 12);
    	kanji[915].reading.add("ム");
    	kanji[915].reading.add("ブ");
    	kanji[915].reading.add("な-い");

    	kanji[916] = new Kanji("夢", "夕", 13);
    	kanji[916].reading.add("ム");
    	kanji[916].reading.add("ゆめ");

    	kanji[917] = new Kanji("名", "口", 6);
    	kanji[917].reading.add("メイ");
    	kanji[917].reading.add("ミョウ");
    	kanji[917].reading.add("な");

    	kanji[918] = new Kanji("命", "口", 8);
    	kanji[918].reading.add("メイ");
    	kanji[918].reading.add("ミョウ");
    	kanji[918].reading.add("いのち");

    	kanji[919] = new Kanji("明", "日", 8);
    	kanji[919].reading.add("メイ");
    	kanji[919].reading.add("ミョウ");
    	kanji[919].reading.add("あ-かり");
    	kanji[919].reading.add("あか-るい");
    	kanji[919].reading.add("あか-るむ");
    	kanji[919].reading.add("あか-らむ");
    	kanji[919].reading.add("あき-らか");
    	kanji[919].reading.add("あ-ける");
    	kanji[919].reading.add("あ-く");
    	kanji[919].reading.add("あ-くる");
    	kanji[919].reading.add("あ-かす");

    	kanji[920] = new Kanji("迷", "辵", 9);
    	kanji[920].reading.add("メイ");
    	kanji[920].reading.add("まよ-う");

    	kanji[921] = new Kanji("盟", "皿", 13);
    	kanji[921].reading.add("メイ");

    	kanji[922] = new Kanji("鳴", "鳥", 14);
    	kanji[922].reading.add("メイ");
    	kanji[922].reading.add("な-く");
    	kanji[922].reading.add("な-る");
    	kanji[922].reading.add("な-らす");

    	kanji[923] = new Kanji("面", "面", 9);
    	kanji[923].reading.add("メン");
    	kanji[923].reading.add("おも");
    	kanji[923].reading.add("おもて");
    	kanji[923].reading.add("つら");

    	kanji[924] = new Kanji("綿", "糸", 14);
    	kanji[924].reading.add("メン");
    	kanji[924].reading.add("わた");

    	kanji[925] = new Kanji("模", "木", 14);
    	kanji[925].reading.add("モ");
    	kanji[925].reading.add("ボ");

    	kanji[926] = new Kanji("毛", "毛", 4);
    	kanji[926].reading.add("モウ");
    	kanji[926].reading.add("け");

    	kanji[927] = new Kanji("目", "目", 5);
    	kanji[927].reading.add("モク");
    	kanji[927].reading.add("め");

    	kanji[928] = new Kanji("門", "門", 8);
    	kanji[928].reading.add("モン");
    	kanji[928].reading.add("かど");

    	kanji[929] = new Kanji("問", "口", 11);
    	kanji[929].reading.add("モン");
    	kanji[929].reading.add("と-う");
    	kanji[929].reading.add("と-い");

    	kanji[930] = new Kanji("夜", "夕", 8);
    	kanji[930].reading.add("ヤ");
    	kanji[930].reading.add("よ");
    	kanji[930].reading.add("よる");

    	kanji[931] = new Kanji("野", "里", 11);
    	kanji[931].reading.add("ヤ");
    	kanji[931].reading.add("の");

    	kanji[932] = new Kanji("役", "彳", 7);
    	kanji[932].reading.add("ヤク");
    	kanji[932].reading.add("エキ");

    	kanji[933] = new Kanji("約", "糸", 9);
    	kanji[933].reading.add("ヤク");

    	kanji[934] = new Kanji("訳", "言", 11);
    	kanji[934].reading.add("ヤク");
    	kanji[934].reading.add("わけ");

    	kanji[935] = new Kanji("薬", "艸", 16);
    	kanji[935].reading.add("ヤク");
    	kanji[935].reading.add("くすり");

    	kanji[936] = new Kanji("由", "田", 5);
    	kanji[936].reading.add("ユ");
    	kanji[936].reading.add("ユウ");
    	kanji[936].reading.add("よし");

    	kanji[937] = new Kanji("油", "水", 8);
    	kanji[937].reading.add("ユ");
    	kanji[937].reading.add("あぶら");

    	kanji[938] = new Kanji("輸", "車", 16);
    	kanji[938].reading.add("ユ");

    	kanji[939] = new Kanji("友", "又", 4);
    	kanji[939].reading.add("ユウ");
    	kanji[939].reading.add("とも");

    	kanji[940] = new Kanji("有", "月", 6);
    	kanji[940].reading.add("ユウ");
    	kanji[940].reading.add("ウ");
    	kanji[940].reading.add("あ-る");

    	kanji[941] = new Kanji("勇", "力", 9);
    	kanji[941].reading.add("ユウ");
    	kanji[941].reading.add("いさ-む");

    	kanji[942] = new Kanji("郵", "邑", 11);
    	kanji[942].reading.add("ユウ");

    	kanji[943] = new Kanji("遊", "辵", 12);
    	kanji[943].reading.add("ユウ");
    	kanji[943].reading.add("あそ-ぶ");

    	kanji[944] = new Kanji("優", "人", 17);
    	kanji[944].reading.add("ユウ");
    	kanji[944].reading.add("やさ-しい");
    	kanji[944].reading.add("すぐ-れる");

    	kanji[945] = new Kanji("予", "豕", 4);
    	kanji[945].reading.add("ヨ");

    	kanji[946] = new Kanji("余", "食", 7);
    	kanji[946].reading.add("ヨ");
    	kanji[946].reading.add("あま-る");
    	kanji[946].reading.add("あま-す");

    	kanji[947] = new Kanji("預", "頁", 13);
    	kanji[947].reading.add("ヨ");
    	kanji[947].reading.add("あず-ける");
    	kanji[947].reading.add("あず-かる");

    	kanji[948] = new Kanji("幼", "幺", 5);
    	kanji[948].reading.add("ヨウ");
    	kanji[948].reading.add("おさな-い");

    	kanji[949] = new Kanji("用", "用", 5);
    	kanji[949].reading.add("ヨウ");
    	kanji[949].reading.add("もち-いる");

    	kanji[950] = new Kanji("羊", "羊", 6);
    	kanji[950].reading.add("ヨウ");
    	kanji[950].reading.add("ひつじ");

    	kanji[951] = new Kanji("洋", "水", 9);
    	kanji[951].reading.add("ヨウ");

    	kanji[952] = new Kanji("要", "襾", 9);
    	kanji[952].reading.add("ヨウ");
    	kanji[952].reading.add("かなめ");
    	kanji[952].reading.add("い-る");

    	kanji[953] = new Kanji("容", "宀", 10);
    	kanji[953].reading.add("ヨウ");

    	kanji[954] = new Kanji("葉", "艸", 12);
    	kanji[954].reading.add("ヨウ");
    	kanji[954].reading.add("は");

    	kanji[955] = new Kanji("陽", "阜", 12);
    	kanji[955].reading.add("ヨウ");

    	kanji[956] = new Kanji("様", "木", 14);
    	kanji[956].reading.add("ヨウ");
    	kanji[956].reading.add("さま");

    	kanji[957] = new Kanji("養", "食", 15);
    	kanji[957].reading.add("ヨウ");
    	kanji[957].reading.add("やしな-う");

    	kanji[958] = new Kanji("曜", "日", 18);
    	kanji[958].reading.add("ヨウ");

    	kanji[959] = new Kanji("浴", "水", 10);
    	kanji[959].reading.add("ヨク");
    	kanji[959].reading.add("あ-びる");
    	kanji[959].reading.add("あ-びせる");

    	kanji[960] = new Kanji("欲", "欠", 11);
    	kanji[960].reading.add("ヨク");
    	kanji[960].reading.add("ほっ-する");
    	kanji[960].reading.add("ほ-しい");

    	kanji[961] = new Kanji("翌", "羽", 11);
    	kanji[961].reading.add("ヨク");

    	kanji[962] = new Kanji("来", "人", 7);
    	kanji[962].reading.add("ライ");
    	kanji[962].reading.add("く-る");
    	kanji[962].reading.add("きた-る");
    	kanji[962].reading.add("きた-す");

    	kanji[963] = new Kanji("落", "艸", 12);
    	kanji[963].reading.add("ラク");
    	kanji[963].reading.add("お-ちる");
    	kanji[963].reading.add("お-とす");

    	kanji[964] = new Kanji("乱", "乙", 7);
    	kanji[964].reading.add("ラン");
    	kanji[964].reading.add("みだ-れる");
    	kanji[964].reading.add("みだ-す");

    	kanji[965] = new Kanji("卵", "卩", 7);
    	kanji[965].reading.add("ラン");
    	kanji[965].reading.add("たまご");

    	kanji[966] = new Kanji("覧", "見", 17);
    	kanji[966].reading.add("ラン");

    	kanji[967] = new Kanji("利", "刀", 7);
    	kanji[967].reading.add("リ");
    	kanji[967].reading.add("き-く");

    	kanji[968] = new Kanji("里", "里", 7);
    	kanji[968].reading.add("リ");
    	kanji[968].reading.add("さと");

    	kanji[969] = new Kanji("理", "玉", 11);
    	kanji[969].reading.add("リ");

    	kanji[970] = new Kanji("裏", "衣", 13);
    	kanji[970].reading.add("リ");
    	kanji[970].reading.add("うら");

    	kanji[971] = new Kanji("陸", "阜", 11);
    	kanji[971].reading.add("リク");

    	kanji[972] = new Kanji("立", "立", 5);
    	kanji[972].reading.add("リツ");
    	kanji[972].reading.add("た-つ");
    	kanji[972].reading.add("た-てる");

    	kanji[973] = new Kanji("律", "彳", 9);
    	kanji[973].reading.add("リツ");

    	kanji[974] = new Kanji("略", "田", 11);
    	kanji[974].reading.add("リャク");

    	kanji[975] = new Kanji("流", "水", 10);
    	kanji[975].reading.add("リュウ");
    	kanji[975].reading.add("なが-れる");
    	kanji[975].reading.add("なが-す");

    	kanji[976] = new Kanji("留", "田", 10);
    	kanji[976].reading.add("リュウ");
    	kanji[976].reading.add("と-める");
    	kanji[976].reading.add("と-まる");

    	kanji[977] = new Kanji("旅", "方", 10);
    	kanji[977].reading.add("リョ");
    	kanji[977].reading.add("たび");

    	kanji[978] = new Kanji("両", "入", 6);
    	kanji[978].reading.add("リョウ");

    	kanji[979] = new Kanji("良", "艮", 7);
    	kanji[979].reading.add("リョウ");
    	kanji[979].reading.add("よ-い");

    	kanji[980] = new Kanji("料", "斗", 10);
    	kanji[980].reading.add("リョウ");

    	kanji[981] = new Kanji("量", "里", 12);
    	kanji[981].reading.add("リョウ");
    	kanji[981].reading.add("はか-る");

    	kanji[982] = new Kanji("領", "頁", 14);
    	kanji[982].reading.add("リョウ");

    	kanji[983] = new Kanji("力", "力", 2);
    	kanji[983].reading.add("リョク");
    	kanji[983].reading.add("リキ");
    	kanji[983].reading.add("ちから");

    	kanji[984] = new Kanji("緑", "糸", 14);
    	kanji[984].reading.add("リョク");
    	kanji[984].reading.add("みどり");

    	kanji[985] = new Kanji("林", "木", 8);
    	kanji[985].reading.add("リン");
    	kanji[985].reading.add("はやし");

    	kanji[986] = new Kanji("輪", "車", 15);
    	kanji[986].reading.add("リン");
    	kanji[986].reading.add("わ");

    	kanji[987] = new Kanji("臨", "臣", 18);
    	kanji[987].reading.add("リン");
    	kanji[987].reading.add("のぞ-む");

    	kanji[988] = new Kanji("類", "頁", 18);
    	kanji[988].reading.add("ルイ");
    	kanji[988].reading.add("たぐ-い");

    	kanji[989] = new Kanji("令", "人", 5);
    	kanji[989].reading.add("レイ");

    	kanji[990] = new Kanji("礼", "示", 5);
    	kanji[990].reading.add("レイ");
    	kanji[990].reading.add("ライ");

    	kanji[991] = new Kanji("冷", "冫", 7);
    	kanji[991].reading.add("レイ");
    	kanji[991].reading.add("つめ-たい");
    	kanji[991].reading.add("ひ-える");
    	kanji[991].reading.add("ひ-や");
    	kanji[991].reading.add("ひ-やす");
    	kanji[991].reading.add("ひ-やかす");
    	kanji[991].reading.add("さ-める");
    	kanji[991].reading.add("さ-ます");

    	kanji[992] = new Kanji("例", "人", 8);
    	kanji[992].reading.add("レイ");
    	kanji[992].reading.add("たと-える");

    	kanji[993] = new Kanji("歴", "止", 14);
    	kanji[993].reading.add("レキ");

    	kanji[994] = new Kanji("列", "刀", 6);
    	kanji[994].reading.add("レツ");

    	kanji[995] = new Kanji("連", "辵", 10);
    	kanji[995].reading.add("レン");
    	kanji[995].reading.add("つら-なる");
    	kanji[995].reading.add("つら-ねる");
    	kanji[995].reading.add("つ-れる");

    	kanji[996] = new Kanji("練", "糸", 14);
    	kanji[996].reading.add("レン");
    	kanji[996].reading.add("ね-る");

    	kanji[997] = new Kanji("路", "足", 13);
    	kanji[997].reading.add("ロ");
    	kanji[997].reading.add("じ");

    	kanji[998] = new Kanji("老", "老", 6);
    	kanji[998].reading.add("ロウ");
    	kanji[998].reading.add("お-いる");
    	kanji[998].reading.add("ふ-ける");

    	kanji[999] = new Kanji("労", "力", 7);
    	kanji[999].reading.add("ロウ");

    	kanji[1000] = new Kanji("朗", "月", 10);
    	kanji[1000].reading.add("ロウ");
    	kanji[1000].reading.add("ほが-らか");

    	kanji[1001] = new Kanji("六", "八", 4);
    	kanji[1001].reading.add("ロク");
    	kanji[1001].reading.add("む");
    	kanji[1001].reading.add("む-つ");
    	kanji[1001].reading.add("むっ-つ");

    	kanji[1002] = new Kanji("録", "金", 16);
    	kanji[1002].reading.add("ロク");

    	kanji[1003] = new Kanji("論", "言", 15);
    	kanji[1003].reading.add("ロン");

    	kanji[1004] = new Kanji("和", "口", 8);
    	kanji[1004].reading.add("ワ");
    	kanji[1004].reading.add("わ-らぐ");
    	kanji[1004].reading.add("やわ-らげる");
    	kanji[1004].reading.add("なご-む");
    	kanji[1004].reading.add("なご-やか");

    	kanji[1005] = new Kanji("話", "言", 13);
    	kanji[1005].reading.add("ワ");
    	kanji[1005].reading.add("はな-す");
    	kanji[1005].reading.add("はなし");
    }
    
}
    
class data_Player{
	String turn;
	String user_name;
	String cmd;
	int chips;
	int chips_bet;
	int point_sum;
	boolean stand = true;
	boolean updated = true;
	List<String> cardlist = new ArrayList<>();
	
	data_Player(String turn, String user_name,int chips){
		this.turn = turn;
		this.user_name = user_name;
		this.chips = chips;
	}
}

class Kanji{
	String kanji;
	String radical;
	List<String> reading = new ArrayList<>();
	int stroke;
	int point;
	boolean appearance = false;
	
	Kanji(String kanji, String radical, int stroke){
		this.kanji = kanji;
		this.radical = radical;
		this.stroke = stroke;
		this.point = stroke;
		
		
	}
}