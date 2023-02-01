/*
 * 채팅방 내부 뷰  :  나가기 버튼, 참여자 목록 보여주기, 채팅 입력
 */

package com.yuhan.chat.view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class ChatRoomView extends JPanel {

	JPanel chatRoomPan = new JPanel(new GridLayout(2, 1));
	JButton btnExit, btnSend;
	JTextArea taUserList, taChat;
	JTextField tfChat;

	public ChatRoomView() {
		setLayout(new BorderLayout(5, 5));

		btnExit = new JButton("나가기");

		// 채팅 참여 인원 패널
		JPanel panUser = new JPanel(new BorderLayout());
		JLabel lblUser = new JLabel("참여 인원");
		taUserList = new JTextArea();
		JScrollPane scroll1 = new JScrollPane(taUserList);
		scroll1.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

		panUser.add("North", lblUser);
		panUser.add("Center", scroll1);

		// 채팅 패널
		JPanel panChat = new JPanel(new BorderLayout());
		JLabel lblChat = new JLabel("채팅");
		taChat = new JTextArea();
		JScrollPane scroll2 = new JScrollPane(taChat);
		scroll2.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

		panChat.add("North", lblChat);
		panChat.add("Center", scroll2);

		// 채팅 내용 입력 패널
		JPanel panChat2 = new JPanel(new FlowLayout());
		tfChat = new JTextField(23);
		btnSend = new JButton("전송");

		panChat2.add(tfChat);
		panChat2.add(btnSend);

		// 전체 패널에 추가
		chatRoomPan.add(panUser);
		chatRoomPan.add(panChat);

		add("North", btnExit);
		add("Center", chatRoomPan);
		add("South", panChat2);

	}

}
