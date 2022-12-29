package com.yuhan.chat.controller;

import java.net.Socket;
import java.net.SocketException;
import java.util.List;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

class ChatHandlerObject extends Thread // 처리해주는 곳(소켓에 대한 정보가 담겨있는 곳. 소켓을 처리함)
{
	private ObjectInputStream reader;
	private ObjectOutputStream writer;
	private Socket socket;
	private List<ChatHandlerObject> list;

	// 생성자
	public ChatHandlerObject(Socket socket, List<ChatHandlerObject> list) throws IOException {

		this.socket = socket;
		this.list = list;
		writer = new ObjectOutputStream(socket.getOutputStream());
		reader = new ObjectInputStream(socket.getInputStream());
		// 순서가 뒤바뀌면 값을 입력받지 못하는 상황이 벌어지기 때문에 반드시 writer부터 생성시켜주어야 함!!!!!!

	}

	public void run() {
		InfoDTO dto = null;
		String nickname;
		String id;
		String pw;
		
		try {
			while (true) {
				dto = (InfoDTO) reader.readObject();

				// System.out.println("배열 크기:"+ar.length);
				// 사용자가 접속을 끊었을 경우. 프로그램을 끝내서는 안되고 남은 사용자들에게 퇴장메세지를 보내줘야 한다.
				if (dto.getCommand() == Info.EXIT) {
					System.out.println("서버 : 사용자 종료");
					reader.close();
					writer.close();
					socket.close();
					list.remove(this);
					break;
				}else if (dto.getCommand() == Info.LOGIN) {
					id = dto.getId();
					pw = dto.getPw();
					//db연결 ↓
					System.out.println("서버 :id <= "+id);
					System.out.println("서버 :pw <= "+pw);
					System.out.println("서버 : id,pw 전달");
				}else if (dto.getCommand() == Info.SIGNUP) {
					id = dto.getId();
					pw = dto.getPw();
					nickname = dto.getNickName();
					//db연결
					
					System.out.println("서버 :id <= "+id);
					System.out.println("서버 :pw <= "+pw);
					System.out.println("서버 :nick <= "+nickname);
					System.out.println("서버 : id,pw,nick 전달");
				}
			} // while
		} catch (SocketException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

	}

	// 다른 클라이언트에게 전체 메세지 보내주기
		public void broadcast(InfoDTO sendDto) throws IOException {
			for (ChatHandlerObject handler : list) {
				handler.writer.writeObject(sendDto); // 핸들러 안의 writer에 값을 보내기
				handler.writer.flush(); // 핸들러 안의 writer 값 비워주기
			}
		}
}