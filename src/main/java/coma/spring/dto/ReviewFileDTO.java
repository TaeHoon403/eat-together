package coma.spring.dto;

public class ReviewFileDTO {
	private int seq;
	private String sysname;
	private String oriname;
	private int parent_seq;
	public ReviewFileDTO() {}
	public ReviewFileDTO(int seq, String sysname, String oriname, int parent_seq) {
		this.seq = seq;
		this.sysname = sysname;
		this.oriname = oriname;
		this.parent_seq = parent_seq;
	}
	public int getSeq() {
		return seq;
	}
	public void setSeq(int seq) {
		this.seq = seq;
	}
	public String getSysname() {
		return sysname;
	}
	public void setSysname(String sysname) {
		this.sysname = sysname;
	}
	public String getOriname() {
		return oriname;
	}
	public void setOriname(String oriname) {
		this.oriname = oriname;
	}
	public int getParent_seq() {
		return parent_seq;
	}
	public void setParent_seq(int parent_seq) {
		this.parent_seq = parent_seq;
	}
	
}
