package com.yuhan.chat.controller;

import java.util.*;
import java.io.*;

enum Info {
	JOIN, EXIT, SEND, LOGIN, SIGNUP
}

public class InfoDTO implements Serializable {
	private String nickName;
	private String message;
	private Info command;
	private String id;
	private String pw;
	private String ip;

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
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
