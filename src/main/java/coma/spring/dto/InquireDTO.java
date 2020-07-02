package coma.spring.dto;

import java.sql.Timestamp;

public class InquireDTO {
	private int seq;
	private String category;
	private String writer;
	private String title;
	private String contents;
	private Timestamp write_date;
	
	public InquireDTO() {
		super();
	}
	
	public InquireDTO(int seq, String category, String writer, String title, String contents, Timestamp write_date) {
		super();
		this.seq = seq;
		this.category = category;
		this.writer = writer;
		this.title = title;
		this.contents = contents;
		this.write_date = write_date;
	}
	
	public int getSeq() {
		return seq;
	}
	public void setSeq(int seq) {
		this.seq = seq;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getWriter() {
		return writer;
	}
	public void setWriter(String writer) {
		this.writer = writer;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContents() {
		return contents;
	}
	public void setContents(String contents) {
		this.contents = contents;
	}
	public Timestamp getWrite_date() {
		return write_date;
	}
	public void setWrite_date(Timestamp write_date) {
		this.write_date = write_date;
	}
}
