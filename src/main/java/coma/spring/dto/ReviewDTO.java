package coma.spring.dto;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;

public class ReviewDTO {
	private int seq;
	private String id;
	private String content;
	private int rating;
	private Timestamp write_date;
	private int parent_seq;
	private String sdate;
	public ReviewDTO() {}
	public ReviewDTO(int seq, String id, String content, int rating, Timestamp write_date, int parent_seq) {
		this.seq = seq;
		this.id = id;
		this.content = content;
		this.rating = rating;
		this.write_date = write_date;
		this.parent_seq = parent_seq;
		this.sdate = new SimpleDateFormat("YYYY-MM-dd").format(write_date);
	}
	public int getSeq() {
		return seq;
	}
	public void setSeq(int seq) {
		this.seq = seq;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public int getRating() {
		return rating;
	}
	public void setRating(int rating) {
		this.rating = rating;
	}
	public Timestamp getWrite_date() {
		return write_date;
	}
	public void setWrite_date(Timestamp write_date) {
		this.write_date = write_date;
	}
	public int getParent_seq() {
		return parent_seq;
	}
	public void setParent_seq(int parent_seq) {
		this.parent_seq = parent_seq;
	}
	public String getSdate() {
		long write_date = this.write_date.getTime(); // 글 작성 시점
		long current_date = System.currentTimeMillis(); // 현재 시점
		long gapTime = (current_date - write_date)/1000; // 초단위
		if(gapTime < 60) {
			return "방금 전";
		}else if(gapTime < 3600) {
			return gapTime / 60 + "분 전";
		}else if(gapTime < 86400) {
			return gapTime / 3600 + "시간 전";
		}else {
			return new SimpleDateFormat("YYYY-MM-dd").format(write_date);
		}
	}
	public void setSdate(String sdate) {
		this.sdate = sdate;
	}
}
