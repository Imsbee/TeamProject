package com.yuhan.chat.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ConnectException;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.xml.stream.events.StartDocument;

import com.yuhan.chat.db.*;
import com.yuhan.chat.view.*;
import com.yuhan.chat.server.*;

public class ChatController extends JFrame{
	private LoginView tv;
	private MainView tv2;
	private SignUpView tv3;
	private Socket socket;
	private ObjectInputStream reader = null;
	private ObjectOutputStream writer = null;
	private String myname = null;
	
	
	//로그인 뷰
	public ChatController() {
		tv = new LoginView();
		add(tv);
		
		JButton BtnLogin = tv.getBtnLogin();
		BtnLogin.addActionListener(btnLoginL);
		
		JButton BtnSignup = tv.getBtnSignup();
		BtnSignup.addActionListener(btnSignupL);
		
		setBounds(300, 300, 300, 300);
		setVisible(true);
		
		ChatReaderThread CRT = new ChatReaderThread();
		CRT.start();
		
	}
	
	class ChatReaderThread extends Thread
	{
		@Override
		public void run() {
			InfoDTO dto = null;
			System.out.println("쓰레드 시작");
			while(true) {
				try {
					dto = (InfoDTO) reader.readObject();
					if(dto.getCommand()==InfoDTO.Info.LOGIN) {
						if(dto.isLogin()) {
							tv.setVisible(false);
							ChatMainController();
							myname=dto.getNickName();
						}
					}
					else if(dto.getCommand()==InfoDTO.Info.ROOMCREATE) {
						
						System.out.println("방생성");
						/*
						ChatObject chatsub = new ChatObject(dto.getPort());
						chatsub.service(myname);*/
						
					}
				} catch (SocketException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				} catch (NullPointerException e) {
					dto = null;
				}
			}
		}
	}
	
	public void ChatMainController() {
		tv2 = new MainView();
		add(tv2);
		
		JButton BtnRoomCreate = tv2.getBtnRoomMake();
		BtnRoomCreate.addActionListener(btnRoomCreateL);
		
		JButton BtnExit = tv2.getBtnExit();
		BtnExit.addActionListener(btnExitL);
		setVisible(true);
	}
	
	ActionListener btnRoomCreateL = new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			try {
				System.out.println("클라 : 방생성 요청");
				
				InfoDTO dto = new InfoDTO();
				dto.setCommand(InfoDTO.Info.ROOMCREATE);
				writer.writeObject(dto);
				writer.flush();
				
				/*
				ChatObject chatsub = new ChatObject(9997);
				chatsub.service(myname);*/
				
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	};	
	
	public void ChatSignupController() {
		tv3 = new SignUpView();
		add(tv3);
		
		
		JButton BtnSignup = tv3.getBtnSignUp();
		BtnSignup.addActionListener(btnSignupLL);
		setVisible(true);
	};
	
	public void ChatUser() {
		
	}
	
	ActionListener btnExitL = new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			try {
				reader.close();
				writer.close();
				socket.close();
				System.exit(0);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			
		}
	};
	
	ActionListener btnLoginL = new ActionListener() {
		public int port = 0;
		@Override
		public void actionPerformed(ActionEvent e) {
			String tv_input_id = tv.getTfId().getText().toString();
			String tv_input_pw = tv.getTfPw().getText().toString();
			
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
					writer = new ObjectOutputStream(socket.getOutputStream());
					reader = new ObjectInputStream(socket.getInputStream());
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
					dto.setCommand(InfoDTO.Info.LOGIN);
					dto.setId(tv_input_id);
					dto.setPw(tv_input_pw);
					writer.writeObject(dto);
					writer.flush();
				} catch (IOException e1) {
					e1.printStackTrace();
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		}
	};
	
	ActionListener btnSignupLL = new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			String id = tv3.getTfId().getText().toString();
			String pw = tv3.getTfPw().getText().toString();
			String name = tv3.getTfName().getText().toString();
			
			if (id.equals("")) { // 만약 값이 입력되지 않았을 때 창이 꺼짐
				System.out.println("ID가 입력되지 않았습니다.");
			}
			if (pw.equals("")) {
				System.out.println("pw가 입력되지 않았습니다.");
			}
			if (name.equals("")) {
				System.out.println("name가 입력되지 않았습니다.");
			}
			else {
				String serverIP = "172.18.7.178"; //Server주소
				try {
					socket = new Socket(serverIP, 9999);
					writer = new ObjectOutputStream(socket.getOutputStream());
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
					// 서버로 회원가입 보내기
					InfoDTO dto = new InfoDTO();
					dto.setCommand(InfoDTO.Info.SIGNUP);
					dto.setId(id);
					dto.setPw(pw);
					dto.setNickName(name);
					writer.writeObject(dto);
					writer.flush();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				try {
					while(true) {
						reader = new ObjectInputStream(socket.getInputStream());
						if(reader.readBoolean() == true) {
							System.out.println("클라 : 회원가입 성공");
							tv3.setVisible(false);
							tv.setVisible(true);
							break;
						}
						else if(reader.readBoolean() == false) {
							System.out.println("클라 : 회원가입 실패");
							break;
						}
						else {
						}
					}
				}catch (EOFException e1) {
					e1.printStackTrace();
				}catch (NullPointerException e1) {
					e1.printStackTrace();
				}catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		}
	};
	//
	
	//회원가입 뷰
	ActionListener btnSignupL = new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			tv.setVisible(false);
			//tv.dispose();
			ChatSignupController();
		}
		
	};
	
	WindowAdapter winCloseL = new WindowAdapter() {
		public void windowClosed(WindowEvent e) {
			try {
				ObjectInputStream reader = null;
				ObjectOutputStream writer = null;
				
				InfoDTO dto = new InfoDTO();
				dto.setCommand(InfoDTO.Info.EXIT);
				writer.writeObject(dto); // 역슬러쉬가 필요가 없음
				writer.flush();
			} catch (IOException io) {
				io.printStackTrace();
			}
		};
	};
	
	public static void main(String[] args) {
		new ChatController();
	}

	
	
	
}
