package coma.spring.dto;

import java.sql.Timestamp;

public class ChatDTO {
	private int chatSeq_parent; 
	private int seq; 
	private String content;
	private String writer;
	private Timestamp write_date;
	private int view_count; 
	
	public ChatDTO() {}	
	public ChatDTO(int chatSeq_parent,
			int seq, 
			String content,
			String writer, 
			Timestamp write_date, 
			int view_count) {
		this.chatSeq_parent = chatSeq_parent;
		this.seq = seq;
		this.content = content;
		this.writer = writer;
		this.write_date = write_date;
		this.view_count = view_count;
	}

	public int getChatSeq_parent() {
		return chatSeq_parent;
	}
	public void setChatSeq_parent(int chatSeq_parent) {
		this.chatSeq_parent = chatSeq_parent;
	}
	public int getSeq() {
		return seq;
	}
	public void setSeq(int seq) {
		this.seq = seq;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getWriter() {
		return writer;
	}
	public void setWriter(String writer) {
		this.writer = writer;
	}
	public Timestamp getWrite_date() {
		return write_date;
	}
	public void setWrite_date(Timestamp write_date) {
		this.write_date = write_date;
	}
	public int getView_count() {
		return view_count;
	}
	public void setView_count(int view_count) {
		this.view_count = view_count;
	}
}
