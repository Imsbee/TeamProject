package com.yuhan.chat.view;

import javax.swing.JFrame;

public class viewtest extends JFrame {

	MainView mainV = new MainView();
	LoginView logV = new LoginView();
	SignUpView signV = new SignUpView();
	ChatRoomView chatV = new ChatRoomView();
	
	public viewtest() {
		add(chatV);
		//add(mainV);
		//add(logV);
		//add(signV);
		
		
		setBounds(100, 100, 350, 500);
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
	
	public static void main(String[] args) {
		new viewtest();
	}
	

}
