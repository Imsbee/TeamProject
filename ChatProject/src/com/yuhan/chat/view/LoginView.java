/*
 * 로그인 화면 뷰
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

	JPanel loginPan = new JPanel(new GridLayout(5, 5, 10, 10));
	JButton btnLogin = new JButton("로그인");
	JButton btnSignup = new JButton("회원가입");
	
	JTextField tfId = new JTextField();
	JPasswordField tfPw = new JPasswordField();
	
	public LoginView() {
		setLayout(new BorderLayout(10, 10));
		
		//로그인 패널에 추가할 컴포넌트들
		JLabel lblTitle = new JLabel("로 그 인");
		lblTitle.setHorizontalAlignment(JLabel.CENTER);
		lblTitle.setFont(new Font("", Font.BOLD, 15));
		
		JLabel lblId = new JLabel("아이디 : ");
		JLabel lblPw = new JLabel("비밀번호 : ");
		JLabel lblBlank = new JLabel("");
		JLabel lblBlank2 = new JLabel("");
		
		lblId.setHorizontalAlignment(JLabel.RIGHT);
		lblPw.setHorizontalAlignment(JLabel.RIGHT);
		
		
		//패널에 컴포넌트 추가
		loginPan.add(lblId); loginPan.add(tfId);
		loginPan.add(lblPw); loginPan.add(tfPw);
		loginPan.add(lblBlank); loginPan.add(btnLogin);
		loginPan.add(lblBlank2); loginPan.add(btnSignup);
		
		add("North", lblTitle);
		add("Center", loginPan);
		
		
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


	//다른 클래스에서 btnLogin 버튼에 접근 가능하게 함
	public JButton getBtnLogin() {
		return btnLogin;
	}
}
