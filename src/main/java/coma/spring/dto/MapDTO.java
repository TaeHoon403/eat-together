package coma.spring.dto;

public class MapDTO {
	private int seq;
	private String name;
	private String address;
	private String road_address;
	private String category;
	private double lat;
	private double lng;
	private double rating_avg;
	private String phone;
	private String place_url;
	private int place_id;
	public MapDTO(int seq, String name, String address, String road_address, String category, double lat, double lng,
			double rating_avg, String phone, String place_url, int place_id) {
		this.seq = seq;
		this.name = name;
		this.address = address;
		this.road_address = road_address;
		this.category = category;
		this.lat = lat;
		this.lng = lng;
		this.rating_avg = rating_avg;
		this.phone = phone;
		this.place_url = place_url;
		this.place_id = place_id;
	}
	public MapDTO() {
	}
	public int getSeq() {
		return seq;
	}
	public void setSeq(int seq) {
		this.seq = seq;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getRoad_address() {
		return road_address;
	}
	public void setRoad_address(String road_address) {
		this.road_address = road_address;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public double getLat() {
		return lat;
	}
	public void setLat(double lat) {
		this.lat = lat;
	}
	public double getLng() {
		return lng;
	}
	public void setLng(double lng) {
		this.lng = lng;
	}
	public double getRating_avg() {
		return rating_avg;
	}
	public void setRating_avg(double rating_avg) {
		this.rating_avg = rating_avg;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getPlace_url() {
		return place_url;
	}
	public void setPlace_url(String place_url) {
		this.place_url = place_url;
	}
	public int getPlace_id() {
		return place_id;
	}
	public void setPlace_id(int place_id) {
		this.place_id = place_id;
	}
	
}
