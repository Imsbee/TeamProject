/*
 * 메인 뷰 : 유저리스트, 채팅방리스트, 방만들기 버튼, 나가기(로그아웃) 
 */

package com.yuhan.chat.view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import com.yuhan.chat.db.ChatDto;

public class MainView extends JPanel {

	JPanel mainPan = new JPanel(new GridLayout(2, 1));
	JButton btnRoomMake, btnExit;
	JTextArea taUserList, taRoomList;

	public MainView() {
		setLayout(new BorderLayout(5, 5));

		// 버튼 추가할 패널
		JPanel panBtn = new JPanel(new FlowLayout());
		btnRoomMake = new JButton("방만들기");
		btnExit = new JButton("나가기");

		panBtn.add(btnRoomMake);
		panBtn.add(btnExit);

		// 유저리스트 패널
		ChatDto dto = new ChatDto();
		ArrayList<String> list = new ArrayList<>();
		list = dto.select_name();
		JPanel panUserList = new JPanel(new BorderLayout());
		JLabel lblUserList = new JLabel("회원 목록");
		taUserList = new JTextArea();
		JScrollPane scroll1 = new JScrollPane(taUserList);
		scroll1.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		for (int i = 0; i < list.size(); i++) {
			taUserList.append(list.get(i).toString() + "\n");
		}

		panUserList.add("North", lblUserList);
		panUserList.add("Center", scroll1);

		// 채팅방 리스트 패널
		JPanel panRoomList = new JPanel(new BorderLayout());
		JLabel lblRoomList = new JLabel("채팅방 목록");
		taRoomList = new JTextArea();
		JScrollPane scroll2 = new JScrollPane(taRoomList);
		scroll2.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

		panRoomList.add("North", lblRoomList);
		panRoomList.add("Center", scroll2);

		// 전체 패널에 추가
		mainPan.add(panUserList);
		mainPan.add(panRoomList);

		add("North", panBtn);
		add("Center", mainPan);
	}

	public JButton getBtnRoomMake() {
		return btnRoomMake;
	}

	public JButton getBtnExit() {
		return btnExit;
	}

}
