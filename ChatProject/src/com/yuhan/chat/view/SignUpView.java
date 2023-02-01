/*
 * 회원가입 화면 뷰 (뒤로가기 추가)
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

	JButton btnSignUp = new JButton("회원가입");
	JButton btnIdCheck = new JButton("중복확인");
	JButton btnBack = new JButton("뒤로가기");
	JTextField tfId = new JTextField();
	JPasswordField tfPw = new JPasswordField(20);
	JPasswordField tfPwCheck = new JPasswordField(20);
	JTextField tfName = new JTextField();
	
	
	public SignUpView() {
		setLayout(null);
		
		//회원가입
		JLabel lblTitle = new JLabel("회 원 가 입");
		lblTitle.setHorizontalAlignment(JLabel.CENTER);
		lblTitle.setFont(new Font("맑은 고딕", Font.BOLD, 20));
		lblTitle.setBounds(115, 60, 120, 50);
		
		//아이디, 중복확인버튼
		JLabel lblId = new JLabel("아이디 : ");
		lblId.setHorizontalAlignment(JLabel.RIGHT);
		lblId.setBounds(45, 140, 50, 30);
		tfId.setBounds(95, 140, 120, 30);
		btnIdCheck.setBounds(220, 140, 85, 30);
		
		
		//비밀번호, 비밀번호 확인
		JLabel lblPw = new JLabel("비밀번호 : ");
		lblPw.setHorizontalAlignment(JLabel.RIGHT);
		lblPw.setBounds(60, 175, 80, 30);
		tfPw.setBounds(140, 175, 150, 30);
		
		JLabel lblPwCheck = new JLabel("비밀번호 확인 : ");
		lblPwCheck.setHorizontalAlignment(JLabel.RIGHT);
		lblPwCheck.setBounds(40, 210, 100, 30);
		tfPwCheck.setBounds(140, 210, 150, 30);
		
		
		//이름
		JLabel lblName = new JLabel("이름 : ");
		lblName.setHorizontalAlignment(JLabel.RIGHT);
		lblName.setBounds(60, 245, 80, 30);
		tfName.setBounds(140, 245, 150, 30);
		
		
		//회원가입, 뒤로가기 버튼
		btnSignUp.setBounds(115, 280, 85 ,30);
		btnBack.setBounds(205, 280, 85, 30);
		
		
		
		//컴포넌트 추가
		add(lblTitle);
		add(lblId); add(tfId); add(btnIdCheck);
		add(lblPw); add(tfPw);
		add(lblPwCheck); add(tfPwCheck);
		add(lblName); add(tfName);
		add(btnSignUp); add(btnBack);

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
