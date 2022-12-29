package com.yuhan.chat.db;

public class ChatVo {
	static String id;
	static String pw;
	static String name;

	public static String getId() {
		return id;
	}

	public static void setId(String id) {
		ChatVo.id = id;
	}

	public static String getPw() {
		return pw;
	}

	public static void setPw(String pw) {
		ChatVo.pw = pw;
	}

	public static String getName() {
		return name;
	}

	public static void setName(String name) {
		ChatVo.name = name;
	}

	@Override
	public String toString() {
		return "ChatVo [getClass()=" + getClass() + ", hashCode()=" + hashCode() + ", toString()=" + super.toString()
				+ "]";
	}

}
