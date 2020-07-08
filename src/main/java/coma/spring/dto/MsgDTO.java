package coma.spring.dto;

import java.sql.Timestamp;

public class MsgDTO {
	private int msg_seq;
	private String msg_sender;
	private String msg_receiver;
	private String msg_title;
	private String msg_text;
	private Timestamp msg_date;
	private int msg_view;
	private int sender_del;
	private int receiver_del;
	
	public MsgDTO() {};
	public MsgDTO(int msg_seq,String msg_sender,String msg_receiver,String msg_title,String msg_text,Timestamp msg_date,int msg_view,int sender_del,int receiver_del) {
		super();
		this.msg_seq=msg_seq;
		this.msg_sender=msg_sender;
		this.msg_receiver=msg_receiver;
		this.msg_title=msg_title;
		this.msg_text=msg_text;
		this.msg_date=msg_date;
		this.msg_view=msg_view;
		this.sender_del=sender_del;
		this.receiver_del=receiver_del;
	}
	public int getMsg_seq() {
		return msg_seq;
	}
	public void setMsg_seq(int msg_seq) {
		this.msg_seq = msg_seq;
	}
	public String getMsg_sender() {
		return msg_sender;
	}
	public void setMsg_sender(String msg_sender) {
		this.msg_sender = msg_sender;
	}
	public String getMsg_receiver() {
		return msg_receiver;
	}
	public void setMsg_receiver(String msg_receiver) {
		this.msg_receiver = msg_receiver;
	}
	public String getMsg_title() {
		return msg_title;
	}
	public void setMsg_title(String msg_title) {
		this.msg_title = msg_title;
	}
	public String getMsg_text() {
		return msg_text;
	}
	public void setMsg_text(String msg_text) {
		this.msg_text = msg_text;
	}
	public Timestamp getMsg_date() {
		return msg_date;
	}
	public void setMsg_date(Timestamp msg_date) {
		this.msg_date = msg_date;
	}
	public int getMsg_view() {
		return msg_view;
	}
	public void setMsg_view(int msg_view) {
		this.msg_view = msg_view;
	}
	public int getSender_del() {
		return sender_del;
	}
	public void setSender_del(int sender_del) {
		this.sender_del = sender_del;
	}
	public int getReceiver_del() {
		return receiver_del;
	}
	
	public void setReceiver_del(int receiver_del) {
		this.receiver_del = receiver_del;
	}
	
}