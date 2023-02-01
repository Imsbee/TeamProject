package com.yuhan.chat.controller;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;
import java.io.*;


import com.yuhan.chat.server.*;
import com.yuhan.chat.view.ChatRoomView;

class ChatObject extends JFrame implements ActionListener, Runnable {
	private JTextArea output;
	private JTextField input;
	private JButton sendBtn, exitBtn;
	private Socket socket;
	private ObjectInputStream reader = null;
	private ObjectOutputStream writer = null;
	// private String msg;
	// private InfoDTO dto;
	private String nickName;
	private int port;
	private ChatRoomView room;

	public ChatObject(int port) {
		this.port = port;
		
		room = new ChatRoomView();
		
		output = room.getTaChat();
		input = room.getTfChat();
		sendBtn = room.getBtnSend();
		exitBtn = room.getBtnExit();
		
		/*
		// 센터에 TextArea만들기
		output = new JTextArea();
		output.setFont(new Font("맑은 고딕", Font.BOLD, 15));
		output.setEditable(false);
		JScrollPane scroll = new JScrollPane(output);
		scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS); // 항상 스크롤바가 세로로 떠있음

		// 하단에 버튼과 TextArea넣기
		JPanel bottom = new JPanel();
		bottom.setLayout(new BorderLayout());
		input = new JTextField();

		sendBtn = new JButton("보내기");

		bottom.add("Center", input); // 센터에 붙이기
		bottom.add("East", sendBtn); // 동쪽에 붙이기
		// container에 붙이기
		Container c = this.getContentPane();
		c.add("Center", scroll); // 센터에 붙이기
		c.add("South", bottom); // 남쪽에 붙이기*/
		// 윈도우 창 설정
		exitBtn.addActionListener(Lexit);
		add(room);
		setBounds(300, 300, 350, 500);
		setVisible(true);
		
		// 윈도우 이벤트

		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				// System.exit(0);
				try {
					// InfoDTO dto = new InfoDTO(nickName,Info.EXIT);
					InfoDTO dto = new InfoDTO();
					dto.setNickName(nickName);
					dto.setCommand(InfoDTO.Info.EXIT);
					writer.writeObject(dto); // 역슬러쉬가 필요가 없음
					writer.flush();
				} catch (IOException io) {
					io.printStackTrace();
				}
			}
		});
	}

	public void service(String myname) {
		// 닉네임 받기
		String serverIP = "172.18.7.179"; //Server주소
		nickName = myname;
		try {
			socket = new Socket(serverIP, port);
			reader = new ObjectInputStream(socket.getInputStream());
			writer = new ObjectOutputStream(socket.getOutputStream());
			System.out.println("전송 준비 완료!");

			// 에러 발생
		} catch (UnknownHostException e) {
			System.out.println("서버를 찾을 수 없습니다.");
			e.printStackTrace();
			System.exit(0);
		} catch (IOException e) {
			System.out.println("서버와 연결이 안되었습니다.");
			e.printStackTrace();
			System.exit(0);
		}
		try {
			// 서버로 닉네임 보내기
			InfoDTO dto = new InfoDTO();
			dto.setCommand(InfoDTO.Info.JOIN);
			dto.setNickName(nickName);
			writer.writeObject(dto); // 역슬러쉬가 필요가 없음
			writer.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}

		// 스레드 생성
		Thread t = new Thread(this);
		t.start();
		input.addActionListener(this);
		sendBtn.addActionListener(this); // 멕션 이벤트 추가
	}

	@Override
	public void run() {
		// 서버로부터 데이터 받기
		InfoDTO dto = null;
		while (true) {
			try {
				dto = (InfoDTO) reader.readObject();
				if (dto.getCommand() == InfoDTO.Info.EXIT) { // 서버로부터 내 자신의 exit를 받으면 종료됨
					reader.close();
					writer.close();
					socket.close();
					System.exit(0);
				} else if (dto.getCommand() == InfoDTO.Info.SEND) {
					output.append(dto.getMessage() + "\n");

					int pos = output.getText().length();
					output.setCaretPosition(pos);
				}
			} catch (SocketException e) {
				break;
			} catch (IOException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		}

	}
	

	@Override
	public void actionPerformed(ActionEvent e) {
		try {
			// 서버로 보냄
			// JTextField값을 서버로보내기
			// 버퍼 비우기
			String msg = input.getText();
			InfoDTO dto = new InfoDTO();
			// dto.setNickName(nickName);
			if (e.getActionCommand().equals("나가기")) {
				
			} else {
				dto.setCommand(InfoDTO.Info.SEND);
				dto.setMessage(msg);
				dto.setNickName(nickName);
			}
			writer.writeObject(dto);
			writer.flush();
			input.setText("");

		} catch (IOException io) {
			io.printStackTrace();
		}

	}
	
	ActionListener Lexit = new ActionListener() {
		
		@Override
		public void actionPerformed(ActionEvent e) {
			try {
				reader.close();
				writer.close();
				socket.close();
				dispose();
				
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	};
	
}
