/*
 * 로그인 화면 뷰 : 아이디/pw입력, 로그인/회원가입 버튼
 */

package com.yuhan.chat.view;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class LoginView extends JPanel {

	JButton btnLogin;
	JButton btnSignup;
	JTextField tfId = new JTextField();
	JPasswordField tfPw = new JPasswordField();
	
	
	public LoginView() {
		setLayout(null);
		
		//로그인
		JLabel lblTitle = new JLabel("로 그 인");
		lblTitle.setFont(new Font("맑은 고딕", Font.BOLD, 20));
		lblTitle.setHorizontalAlignment(JLabel.CENTER);
		lblTitle.setBounds(125, 70, 100, 50);
		
		
		//아이디, 패스워드
		JLabel lblId = new JLabel("아이디 :  ");
		lblId.setHorizontalAlignment(JLabel.RIGHT);
		lblId.setBounds(60, 140, 80, 30);
		tfId.setBounds(140, 140, 150, 30);
		
		JLabel lblPw = new JLabel("비밀번호 : ");
		lblPw.setHorizontalAlignment(JLabel.RIGHT);
		lblPw.setBounds(60, 175, 80, 30);
		tfPw.setBounds(140, 175, 150, 30);
		
		
		//버튼
		btnLogin = new JButton("로그인");
		btnLogin.setBounds(205, 220, 85, 25);
		btnSignup = new JButton("회원가입");
		btnSignup.setBounds(205, 250, 85, 25);
		
		
		//패널에 컴포넌트 추가
		add(lblTitle);
		add(lblId); add(tfId);
		add(lblPw); add(tfPw);
		add(btnLogin);
		add(btnSignup);
		
	}
	
	
	public JTextField getTfId() {
		return tfId;
	}
	public JPasswordField getTfPw() {
		return tfPw;
	}
	public JButton getBtnSignup() {
		return btnSignup;
	}
	public JButton getBtnLogin() {
		return btnLogin;
	}
}
