/*
 * 회원가입 화면 뷰
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

public class SignUpView extends JPanel {

	JPanel signUpPan = new JPanel(new GridLayout(5, 2));
	JButton btnSignUp = new JButton("회원가입");
	JButton btnIdCheck = new JButton("중복확인");
	
	JTextField tfId = new JTextField();
	JPasswordField tfPw = new JPasswordField(20);
	JPasswordField tfPwCheck = new JPasswordField(20);
	JTextField tfName = new JTextField();
	
	public SignUpView() {
		setLayout(new BorderLayout(10, 10));
		
		//컴포넌트 선언
		JLabel lblTitle = new JLabel("회 원 가 입");
		lblTitle.setHorizontalAlignment(JLabel.CENTER);
		lblTitle.setFont(new Font("", Font.BOLD, 15));
		
		JLabel lblId = new JLabel("아이디 : ");
		JLabel lblPw = new JLabel("비밀번호 : ");
		JLabel lblPwCheck = new JLabel("비밀번호 확인 : ");
		JLabel lblName = new JLabel("이름 : ");
		JLabel lblBlank = new JLabel("");
		lblId.setHorizontalAlignment(JLabel.RIGHT);
		lblPw.setHorizontalAlignment(JLabel.RIGHT);
		lblPwCheck.setHorizontalAlignment(JLabel.RIGHT);
		lblName.setHorizontalAlignment(JLabel.RIGHT);
		
		//컴포넌트 추가
		signUpPan.add(lblId); signUpPan.add(tfId);
		signUpPan.add(lblPw); signUpPan.add(tfPw);
		signUpPan.add(lblPwCheck); signUpPan.add(tfPwCheck);
		signUpPan.add(lblName); signUpPan.add(tfName);
		signUpPan.add(lblBlank); signUpPan.add(btnSignUp);
		
		add("North", lblTitle);
		add("Center", signUpPan);
	}
	
	
	public JButton getBtnSignUp() {
		return btnSignUp;
	}
	public JButton getBtnIdCheck() {
		return btnIdCheck;
	}


	public JTextField getTfId() {
		return tfId;
	}


	public JPasswordField getTfPw() {
		return tfPw;
	}


	public JTextField getTfName() {
		return tfName;
	}
	
}
