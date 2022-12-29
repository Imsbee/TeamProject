package com.yuhan.chat.controller;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class testLoginView extends JFrame{
	private JTextField input_id;
	private JTextField input_pw;
	private JButton BtnLogin;
	private JButton BtnSignup;
	
	public testLoginView() {
		Container c = this.getContentPane();
		c.setLayout(new FlowLayout());
		
		input_id = new JTextField(10);
		input_pw = new JTextField(10);
		
		BtnLogin = new JButton("로그인");
		BtnSignup = new JButton("회원가입");
		
		
		c.add(input_id);
		c.add(input_pw);
		c.add(BtnLogin);
		c.add(BtnSignup);
		
		setBounds(300, 300, 300, 300);
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
	
	public JButton getBtnSignup() {
		return BtnSignup;
	}

	public JTextField getInput_id() {
		return input_id;
	}
	public JTextField getInput_pw() {
		return input_pw;
	}
	public JButton getBtnLogin() {
		return BtnLogin;
	}
	public void service() {
		
	}
}
