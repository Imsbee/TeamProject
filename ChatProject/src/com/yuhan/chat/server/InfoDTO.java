package com.yuhan.chat.server;

import java.io.*;

public class InfoDTO implements Serializable {
	public enum Info {
		JOIN, EXIT, SEND, LOGIN, SIGNUP, ROOMCREATE, BOOM, EXPORTPORT
	}

	public String nickName;
	public String message;
	public Info command;
	public String id;
	public String pw;
	public int port;
	public boolean login;

	public boolean isLogin() {
		return login;
	}

	public void setLogin(boolean login) {
		this.login = login;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPw() {
		return pw;
	}

	public void setPw(String pw) {
		this.pw = pw;
	}

	public String getNickName() {
		return nickName;
	}

	public Info getCommand() {
		return command;
	}

	public String getMessage() {
		return message;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public void setCommand(Info command) {
		this.command = command;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
