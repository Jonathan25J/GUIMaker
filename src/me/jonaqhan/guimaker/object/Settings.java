package me.jonaqhan.guimaker.object;

import java.util.List;

public class Settings {
	public String title;
	public int rows;
	public List<String> commands;
	public String message;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public int getRows() {
		return rows;
	}

	public void setRows(int rows) {
		this.rows = rows;
	}

	public List<String> getCommands() {
		return commands;
	}

	public void setCommands(List<String> commands) {
		this.commands = commands;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Settings(String title, int rows, List<String> commands, String message) {
		super();
		this.title = title;
		this.rows = rows;
		this.commands = commands;
		this.message = message;
	}

}
