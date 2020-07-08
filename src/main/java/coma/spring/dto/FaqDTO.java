package coma.spring.dto;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;

public class FaqDTO {
	private int seq;
	private String category;
	private String title;
	private String contents;
	private Timestamp write_date;
	private String sDate;
	
	
	public String getsDate() {
		return sDate;
	}
	public void setsDate(String sDate) {
		this.sDate = sDate;
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
	
	

	public FaqDTO() {
		super();
		// TODO Auto-generated constructor stub
	}
	public FaqDTO(int seq, String category, String title, String contents, Timestamp write_date, String sDate) {
		super();
		this.seq = seq;
		this.category = category;
		this.title = title;
		this.contents = contents;
		this.write_date = write_date;
		this.sDate = new SimpleDateFormat("yyyy-MM-dd").format(write_date);
	}
	
}
