package com.yuhan.chat.controller;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;
import java.io.*;

class ChatClientObject extends JFrame implements ActionListener, Runnable {
	private JTextArea output;
	private JTextField input;
	private JButton sendBtn;
	private Socket socket;
	private ObjectInputStream reader = null;
	private ObjectOutputStream writer = null;
	// private String msg;
	// private InfoDTO dto;
	private String nickName;

	public ChatClientObject() {
		// ���Ϳ� TextArea�����
		output = new JTextArea();
		output.setFont(new Font("���� ���", Font.BOLD, 15));
		output.setEditable(false);
		JScrollPane scroll = new JScrollPane(output);
		scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS); // �׻� ��ũ�ѹٰ� ���η� ������

		// �ϴܿ� ��ư�� TextArea�ֱ�
		JPanel bottom = new JPanel();
		bottom.setLayout(new BorderLayout());
		input = new JTextField();

		sendBtn = new JButton("������");

		bottom.add("Center", input); // ���Ϳ� ���̱�
		bottom.add("East", sendBtn); // ���ʿ� ���̱�
		// container�� ���̱�
		Container c = this.getContentPane();
		c.add("Center", scroll); // ���Ϳ� ���̱�
		c.add("South", bottom); // ���ʿ� ���̱�
		// ������ â ����
		setBounds(300, 300, 300, 300);
		setVisible(true);

		// ������ �̺�Ʈ

		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				// System.exit(0);
				try {
					// InfoDTO dto = new InfoDTO(nickName,Info.EXIT);
					InfoDTO dto = new InfoDTO();
					dto.setNickName(nickName);
					dto.setCommand(Info.EXIT);
					writer.writeObject(dto); // ���������� �ʿ䰡 ����
					writer.flush();
				} catch (IOException io) {
					io.printStackTrace();
				}
			}
		});
	}

	public void service() {
		// ���� IP �Է¹ޱ�
		// String serverIP = JOptionPane.showInputDialog(this, "����IP��
		// �Է��ϼ���","����IP",JOptionPane.INFORMATION_MESSAGE);
		String serverIP = JOptionPane.showInputDialog(this, "����IP�� �Է��ϼ���", ""); // �⺻������ ������ ���� �ԷµǾ� ���� ��
		if (serverIP == null || serverIP.length() == 0) { // ���� ���� �Էµ��� �ʾ��� �� â�� ����
			System.out.println("���� IP�� �Էµ��� �ʾҽ��ϴ�.");
			System.exit(0);
		}
		// �г��� �ޱ�
		nickName = JOptionPane.showInputDialog(this, "�г����� �Է��ϼ���", "�г���", JOptionPane.INFORMATION_MESSAGE);
		if (nickName == null || nickName.length() == 0) {
			nickName = "guest";
		}
		try {
			socket = new Socket("172.18.7.177", 9995);

			reader = new ObjectInputStream(socket.getInputStream());
			writer = new ObjectOutputStream(socket.getOutputStream());
			System.out.println("���� �غ� �Ϸ�!");

			// ���� �߻�
		} catch (UnknownHostException e) {
			System.out.println("������ ã�� �� �����ϴ�.");
			e.printStackTrace();
			System.exit(0);
		} catch (IOException e) {
			System.out.println("������ ������ �ȵǾ����ϴ�.");
			e.printStackTrace();
			System.exit(0);
		}
		try {
			// ������ �г��� ������
			InfoDTO dto = new InfoDTO();
			dto.setCommand(Info.JOIN);
			dto.setNickName(nickName);
			writer.writeObject(dto); // ���������� �ʿ䰡 ����
			writer.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}

		// ������ ����
		Thread t = new Thread(this);
		t.start();
		input.addActionListener(this);
		sendBtn.addActionListener(this); // �߼� �̺�Ʈ �߰�
	}

	public static void main(String[] args) {
		new ChatClientObject().service();
	}

	@Override
	public void run() {
		// �����κ��� ������ �ޱ�
		InfoDTO dto = null;
		while (true) {
			try {
				dto = (InfoDTO) reader.readObject();
				if (dto.getCommand() == Info.EXIT) { // �����κ��� �� �ڽ��� exit�� ������ �����
					reader.close();
					writer.close();
					socket.close();
					System.exit(0);
				} else if (dto.getCommand() == Info.SEND) {
					output.append(dto.getMessage() + "\n");

					int pos = output.getText().length();
					output.setCaretPosition(pos);
				}
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
			// ������ ����
			// JTextField���� �����κ�����
			// ���� ����
			String msg = input.getText();
			InfoDTO dto = new InfoDTO();
			// dto.setNickName(nickName);
			if (msg.equals("exit")) {
				dto.setCommand(Info.EXIT);
			} else {
				dto.setCommand(Info.SEND);
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
}
