package com.yuhan.chat.db;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.naming.NamingException;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class test extends JFrame {
	JButton btnAdd, btnDelete, btnCheck, btnCheck2;
	JLabel lbl;
	ChatVo vo = new ChatVo();

	public test() {
		setLayout(new FlowLayout());
		btnAdd = new JButton("추가");
		btnDelete = new JButton("제거");
		btnCheck = new JButton("확인");
		btnCheck2 = new JButton("비번확인");
		lbl = new JLabel("확인");

		add(btnAdd);
		add(btnDelete);
		add(btnCheck);
		add(btnCheck2);
		add(lbl);

		btnAdd.addActionListener(ls);
		btnDelete.addActionListener(dls);
		btnCheck.addActionListener(cls);
		btnCheck2.addActionListener(c2ls);

		setTitle("test");
		setBounds(150, 100, 500, 650);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
	}

	ActionListener ls = new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent e) {
			vo.setId("test");
			vo.setPw("test");
			vo.setName("test");

			ChatDto dao = new ChatDto();
			dao.insert(vo);
		}
	};

	ActionListener dls = new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent e) {
			vo.setId("test");

			ChatDto dao = new ChatDto();
			dao.delete(vo);
		}
	};

	ActionListener cls = new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent e) {
			ChatDto dao = new ChatDto();

			try {
				if (dao.exists("metalmin")) {
					lbl.setText("존재하는 아이디입니다.");
				} else {
					lbl.setText("존재하지 않는 아이디입니다.");
				}
			} catch (NamingException e1) {
				e1.printStackTrace();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
	};

	ActionListener c2ls = new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent e) {
			ChatDto dto = new ChatDto();

			try {
				if (dto.login("test", "test") != 2) {
					lbl.setText("비밀번호가 일치합니다.");
				} else
					lbl.setText("비밀번호가 일치하지 않습니다.");
			} catch (SQLException e1) {
				e1.printStackTrace();
			} catch (NamingException e1) {
				e1.printStackTrace();
			}
		}
	};

	public static void main(String[] args) {
		new test();
	}
}
