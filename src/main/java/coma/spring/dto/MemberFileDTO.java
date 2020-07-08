package coma.spring.dto;

import org.springframework.web.multipart.MultipartFile;

public class MemberFileDTO {
	private int seq;
	private String sysname;
	private String oriname;
	private String parent_id;
	private MultipartFile file;
	
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
	public String getParent_id() {
		return parent_id;
	}
	public void setParent_id(String parent_id) {
		this.parent_id = parent_id;
	}
	public MultipartFile getFile() {
		return file;
	}
	public void setFile(MultipartFile file) {
		this.file = file;
	}
	public MemberFileDTO(int seq, String sysname, String oriname, String parent_id, MultipartFile file) {
		super();
		this.seq = seq;
		this.sysname = sysname;
		this.oriname = oriname;
		this.parent_id = parent_id;
		this.file = file;
	}
	public MemberFileDTO() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	
	
}
