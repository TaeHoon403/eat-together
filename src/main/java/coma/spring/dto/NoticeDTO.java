package coma.spring.dto;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;

public class NoticeDTO {
	private int seq;
	private String title;
	private String contents;
	private int importance;
	private Timestamp write_date;
	private String attachment;
	private int view_count;
	
private String sDate;
	
	public String getsDate() {
		long write_date = this.write_date.getTime(); // 글 작성 시점
		long current_date = System.currentTimeMillis(); // 현재 시점
		
		long gapTime = (current_date - write_date)/1000; //밀리(1000)세컨드니까 1000으로 나눠야 초(sec)가 나옴
		
		
		if(gapTime<60) { //글이 작성된 갭타임이 60초보다 작은 경우
			return "방금전";
		}else if(gapTime < 300) {
			return "5분 이내";
		}else if(gapTime < 3600) {
			return "1시간 이내";
		}else if(gapTime < 86400) {
			return "24시간 이내";
		}else {
			
			  SimpleDateFormat format1 = new SimpleDateFormat ("yyyy-MM-dd"); 
			  String date =	 format1.format(write_date);
			 
			return date;
		}
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
	public int getImportance() {
		return importance;
	}
	public void setImportance(int importance) {
		this.importance = importance;
	}
	public Timestamp getWrite_date() {
		return write_date;
	}
	public void setWrite_date(Timestamp write_date) {
		this.write_date = write_date;
	}
	public String getAttachment() {
		return attachment;
	}
	public void setAttachment(String attachment) {
		this.attachment = attachment;
	}
	public int getView_count() {
		return view_count;
	}
	public void setView_count(int view_count) {
		this.view_count = view_count;
	}
	public NoticeDTO(int seq, String title, String contents, int importance, Timestamp write_date, String attachment,
			int view_count) {
		super();
		this.seq = seq;
		this.title = title;
		this.contents = contents;
		this.importance = importance;
		this.write_date = write_date;
		this.attachment = attachment;
		this.view_count = view_count;
		this.sDate = new SimpleDateFormat("yyyy-MM-dd").format(write_date);
	}
	public NoticeDTO() {
		super();
		// TODO Auto-generated constructor stub
	}
}
