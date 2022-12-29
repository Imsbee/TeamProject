package com.yuhan.chat.controller;

import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;	

public class ChatController extends JFrame  {
	private testLoginView tv;
	private testMainView tv2;
	private testSignupView tv3;
	private JButton BtnLogin;
	private JButton BtnSignup;
	private Socket socket;
	private ObjectInputStream reader = null;
	private ObjectOutputStream writer = null;
	Container c;
	
	public ChatController() {
		tv = new testLoginView();
		
		BtnLogin = tv.getBtnLogin();
		BtnLogin.addActionListener(btnLoginL);
		
		BtnSignup = tv.getBtnSignup();
		BtnSignup.addActionListener(btnSignupL);
		
		tv.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				// System.exit(0);
				try {
					// InfoDTO dto = new InfoDTO(nickName,Info.EXIT);
					InfoDTO dto = new InfoDTO();
					dto.setCommand(Info.EXIT);
					writer.writeObject(dto); // 역슬러쉬가 필요가 없음
					writer.flush();
				} catch (IOException io) {
					io.printStackTrace();
				}
			}
		});
		
	}
	//로그인
	ActionListener btnLoginL = new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			String tv_input_id = tv.getInput_id().getText().toString();
			String tv_input_pw = tv.getInput_pw().getText().toString();
			
			if (tv_input_id.equals("")) { // 만약 값이 입력되지 않았을 때 창이 꺼짐
				System.out.println("ID가 입력되지 않았습니다.");
			}
			if (tv_input_pw.equals("")) {
				System.out.println("pw가 입력되지 않았습니다.");
			}
			else {
				String serverIP = "172.18.7.178"; //Server주소
				try {
					socket = new Socket(serverIP, 9999);
					reader = new ObjectInputStream(socket.getInputStream());
					writer = new ObjectOutputStream(socket.getOutputStream());
					System.out.println("전송 준비 완료!");
					// 에러 발생
				} catch (UnknownHostException e1) {
					System.out.println("서버를 찾을 수 없습니다.");
					e1.printStackTrace();
					System.exit(0);
				} catch (IOException e1) {
					System.out.println("서버와 연결이 안되었습니다.");
					e1.printStackTrace();
					System.exit(0);
				}
				try {
					// 서버로 로그인 보내기
					InfoDTO dto = new InfoDTO();
					dto.setCommand(Info.LOGIN);
					dto.setId(tv_input_id);
					dto.setPw(tv_input_pw);
					writer.writeObject(dto);
					writer.flush();
					System.out.println("로그인 성공");
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				
				tv.dispose();
				tv2 = new testMainView();
			}
			
		}
	};
	//회원가입
	ActionListener btnSignupL = new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			tv.dispose();
			tv3 = new testSignupView();
			
			JButton BtnSignup = tv3.getBtnSignup();
			BtnSignup.addActionListener(btnLoginL);
			
		}
	};

	
	public static void main(String[] args) {
		new ChatController();
	}
	
	
}
