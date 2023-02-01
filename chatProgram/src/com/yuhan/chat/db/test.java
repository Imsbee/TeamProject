package com.yuhan.chat.db;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.naming.NamingException;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import com.mysql.cj.x.protobuf.MysqlxDatatypes.Array;

public class test extends JFrame {
	JTextField jtf1, jtf2, jtf3;
	JTextArea area;
	JPanel p1, p2;
	JButton btnAdd, btnDelete, btnCheck, btnCheck2, btnselect;
	JLabel lbl;
	ChatVo vo = new ChatVo();
	String id, pw, name;
	ArrayList<ChatVo> list;

	public test() {
		setLayout(new BorderLayout());
		area = new JTextArea("");
		jtf1 = new JTextField(10);
		jtf2 = new JTextField(10);
		jtf3 = new JTextField(10);
		btnAdd = new JButton("추가");
		btnDelete = new JButton("제거");
		btnCheck = new JButton("확인");
		btnCheck2 = new JButton("비번확인");
		btnselect = new JButton("출력");
		lbl = new JLabel("확인");
		p1 = new JPanel();
		p2 = new JPanel();

		p1.add(jtf1);
		p1.add(jtf2);
		p1.add(jtf3);

		p2.add(btnAdd);
		p2.add(btnDelete);
		p2.add(btnCheck);
		p2.add(btnCheck2);
		p2.add(btnselect);

		add("North", p1);
		add("Center", p2);
		add("South", area);

		btnAdd.addActionListener(ls);
		btnDelete.addActionListener(dls);
		btnCheck.addActionListener(cls);
		btnCheck2.addActionListener(c2ls);
		btnselect.addActionListener(sls);

		setTitle("test");
		setBounds(150, 100, 500, 650);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
	}

	ActionListener ls = new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent e) {
			id = jtf1.getText();
			pw = jtf2.getText();
			name = jtf3.getText();

			vo.setId(id);
			vo.setPw(pw);
			vo.setName(name);

			ChatDto dao = new ChatDto();

			dao.insert(vo);
		}
	};

	ActionListener dls = new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent e) {
			id = jtf1.getText();

			vo.setId(id);

			ChatDto dao = new ChatDto();
			dao.delete(vo);
		}
	};

	ActionListener cls = new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent e) {
			ChatDto dao = new ChatDto();

			try {
				if (dao.exists("test")) {
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
				if (dto.login("test", "test") == 1)
					lbl.setText("존재하지 않는 아이디입니다.");
				else if (dto.login("test", "test") == 2) {
					lbl.setText("비밀번호가 일치하지 않습니다.");
				} else
					lbl.setText("비밀번호가 일치합니다.");
			} catch (SQLException e1) {
				e1.printStackTrace();
			} catch (NamingException e1) {
				e1.printStackTrace();
			}
		}
	};

	ActionListener sls = new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			ChatDto dto = new ChatDto();
			String str = "";
			ArrayList<String> list = new ArrayList<>();

			try {
				str = dto.getName("test");
			} catch (NamingException e1) {
				e1.printStackTrace();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}

			area.setText("");
			area.append("닉네임\t" + "\n");
			area.append("-------------------------------------------\n");
			
			list = dto.select_name();

			for (int i = 0; i < list.size(); i++) {
				area.append(list.get(i).toString() + "\n");
			}
		}
	};

	public static void main(String[] args) {
		new test();
	}
}
