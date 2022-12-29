package com.yuhan.chat.controller;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class testMainView extends JFrame{
	private JButton BtnLogin;
	
	public testMainView() {
		Container c = this.getContentPane();
		c.setLayout(new FlowLayout());
		
		
		BtnLogin = new JButton("방생성");
		
		c.add(BtnLogin);
		
		setBounds(300, 300, 300, 300);
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
	public JButton getBtnLogin() {
		return BtnLogin;
	}
	public void service() {
		
	}
}
