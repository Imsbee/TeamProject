package com.yuhan.chat.server;

import java.net.Socket;
import java.sql.SQLException;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.List;

import javax.naming.NamingException;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import com.yuhan.chat.db.*;
import com.yuhan.chat.server.InfoDTO.Info;

class ChatServerObject {
   private static int port = 9998;
   ServerSocket mainSocket;
   List<ChatHandlerObject> mainList;
   ServerSocket[] serverSocket = new ServerSocket[9999];
   List<ChatHandlerObject>[] list = new List[9999];
   ArrayList<ChatVo> chatVo;

   public ChatServerObject() {
      try {
         mainSocket = new ServerSocket(9999);
         userList();
         System.out.println("서버 준비 완료");

         mainList = new ArrayList<ChatHandlerObject>();
         while (true) {
            Socket socket = mainSocket.accept();
            ChatHandlerObject handler = new ChatHandlerObject(socket, mainList); // 스레드를 생성한 것이랑 동일함! 떄문에 시자해주어야
            handler.start(); // 스레드 시작- 스레드 실행
            mainList.add(handler); // 핸들러를 담음( 이 리스트의 개수가 클라이언트의 갯수!!)
         } // while
      } catch (IOException e) {
         e.printStackTrace();
      }

   }

   public static void main(String[] args) {
      new ChatServerObject();
   }

   public ArrayList<ChatVo> userList() {

      ChatDto chatDTO = new ChatDto();
      chatVo = chatDTO.select();

      return chatVo;
   }

   public void ChatServer() {
      try {
         serverSocket[port] = new ServerSocket(port);
         System.out.println("서버 준비 완료");
         File file = new File("log" + port + ".txt");
         if (!file.exists()) {
            file.createNewFile();
         }
         list[port] = new ArrayList<ChatHandlerObject>();
         while (true) {
            Socket socket = serverSocket[port].accept();
            ChatHandlerObject handler = new ChatHandlerObject(socket, list[port]); // 스레드를 생성한 것이랑 동일함! 떄문에 시자해주어야
            handler.start(); // 스레드 시작- 스레드 실행
            list[port].add(handler); // 핸들러를 담음( 이 리스트의 개수가 클라이언트의 갯수!!)
         } // while
      } catch (IOException e) {
         e.printStackTrace();
      }
   };

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
         String nickName;
         try {
            while (true) {
               dto = (InfoDTO) reader.readObject();
               nickName = dto.getNickName();
               
               // System.out.println("배열 크기:"+ar.length);
               // 사용자가 접속을 끊었을 경우. 프로그램을 끝내서는 안되고 남은 사용자들에게 퇴장메세지를 보내줘야 한다.
               if (dto.getCommand() == Info.EXIT) {
                  InfoDTO sendDto = new InfoDTO();
                  // 나가려고 exit를 보낸 클라이언트에게 답변 보내기
                  sendDto.setCommand(Info.EXIT);
                  writer.writeObject(sendDto);
                  writer.flush();

                  reader.close();
                  writer.close();
                  socket.close();
                  // 남아있는 클라이언트에게 퇴장메세지 보내기
                  list.remove(this);

                  sendDto.setCommand(Info.SEND);
                  sendDto.setMessage(nickName + "님 퇴장하였습니다");
                  broadcast(sendDto);
                  break;
               } else if (dto.getCommand() == Info.JOIN) {
                  InfoDTO sendDto = new InfoDTO();
                  File file = new File("log" + port + ".txt");
                  FileReader freader = new FileReader(file);
                  String logs = "";
                  int idx = 0;
                  while ((idx = freader.read()) != -1) {
                     logs += (char) idx;
                  }
                  sendDto.setCommand(Info.SEND);
                  sendDto.setMessage(nickName + "님 입장하였습니다");
                  broadcast(sendDto);
               } else if (dto.getCommand() == Info.BOOM) {
                  InfoDTO sendDto = new InfoDTO();
                  sendDto.setCommand(Info.SEND);
                  sendDto.setMessage("채팅창이 닫혔습니다.");
                  writer.writeObject(sendDto);
                  writer.flush();
                  serverSocket[reader.readInt()].close();
               } else if (dto.getCommand() == Info.LOGIN) {
                  InfoDTO sendDto = new InfoDTO();
                  ChatDto chatDTO = new ChatDto();
                  switch (chatDTO.login(dto.getId(), dto.getPw())) {

                  case 1: {
                     sendDto.setLogin(false);
                     sendDto.setCommand(Info.LOGIN);
                     System.out.println("로그인에 실패했습니다.");
                     writer.writeObject(sendDto);
                     writer.flush();
                     break;
                  }
                  case 2: {
                     sendDto.setLogin(false);
                     sendDto.setCommand(Info.LOGIN);
                     System.out.println("로그인에 실패했습니다.");
                     writer.writeObject(sendDto);
                     writer.flush();
                     break;
                  }
                  case 0: {
                     sendDto.setLogin(true);
                     sendDto.setCommand(Info.LOGIN);
                     sendDto.setId(dto.getId());
                     writer.writeObject(sendDto);
                     writer.flush();
                     System.out.println("로그인에 성공했습니다.");
                     break;
                  }
                  }
               } else if (dto.getCommand() == Info.SIGNUP) {
                  ChatDto chatDTO = new ChatDto();
                  if (chatDTO.exists(dto.getId())) {
                     writer.writeBoolean(false);
                     writer.flush();
                     System.out.println("회원가입에 실패했습니다.");
                  } else {
                     ChatVo vo = new ChatVo();
                     vo.setId(dto.getId());
                     vo.setPw(dto.getPw());
                     vo.setName(dto.getNickName());
                     chatDTO.insert(vo);
                     writer.writeBoolean(true);
                     writer.flush();
                     System.out.println("회원가입에 성공했습니다.");
                  }

               } else if (dto.getCommand() == Info.ROOMCREATE) {
                  InfoDTO sendDto = new InfoDTO();
                  if (port != 8089) {
                     port -= 1;

                  } else {
                     port = 9998;
                  }
                  ChatServer();
                  sendDto.setPort(port);
                  sendDto.setCommand(Info.ROOMCREATE);
                  writer.writeObject(sendDto);
                  writer.flush();
                  System.out.println("방생성이 완료되었습니다.");

               } else if (dto.getCommand() == Info.SEND) {
                  InfoDTO sendDto = new InfoDTO();
                  sendDto.setCommand(Info.SEND);
                  sendDto.setMessage("[" + nickName + "]" + dto.getMessage());
                  File file = new File("log" + port + ".txt");
                  FileWriter Fwriter = null;
                  try {
                     Fwriter = new FileWriter(file, true);
                     Fwriter.write("[" + dto.getNickName() + "]" + dto.getMessage() + "\n");
                     Fwriter.flush();
                  } catch (IOException e) {
                     e.printStackTrace();
                  } finally {
                     try {
                        if (Fwriter != null)
                           Fwriter.close();
                     } catch (IOException e) {
                        e.printStackTrace();
                     }
                  }
                  broadcast(sendDto);
               }
            } // while

         } catch (IOException e) {
            e.printStackTrace();
         } catch (ClassNotFoundException e) {
            e.printStackTrace();
         } catch (NamingException e) {
            e.printStackTrace();
         } catch (SQLException e) {
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

}