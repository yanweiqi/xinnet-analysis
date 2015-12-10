package com.xinnet.xa.analyzer.util;


public class TaoBaoIpData {
	private String ip;
	private String country;
	private String area;
	private String region;
	private String city;
	private String county;
	private String isp;
	private String country_id;
	private String area_id;
	private String region_id;
	private String city_id;
	private String county_id;
	private String isp_id;
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getArea() {
		return area;
	}
	public void setArea(String area) {
		this.area = area;
	}
	public String getRegion() {
		return region;
	}
	public void setRegion(String region) {
		this.region = region;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getCounty() {
		return county;
	}
	public void setCounty(String county) {
		this.county = county;
	}
	public String getIsp() {
		return isp;
	}
	public void setIsp(String isp) {
		this.isp = isp;
	}
	public String getCountry_id() {
		return country_id;
	}
	public void setCountry_id(String country_id) {
		this.country_id = country_id;
	}
	public String getArea_id() {
		return area_id;
	}
	public void setArea_id(String area_id) {
		this.area_id = area_id;
	}
	public String getRegion_id() {
		return region_id;
	}
	public void setRegion_id(String region_id) {
		this.region_id = region_id;
	}
	public String getCity_id() {
		return city_id;
	}
	public void setCity_id(String city_id) {
		this.city_id = city_id;
	}
	public String getCounty_id() {
		return county_id;
	}
	public void setCounty_id(String county_id) {
		this.county_id = county_id;
	}
	public String getIsp_id() {
		return isp_id;
	}
	public void setIsp_id(String isp_id) {
		this.isp_id = isp_id;
	}
	
	public String getXaCounty(){
		if(!country.equals("中国"))
			return country;
		return region;
	}
	@Override
	public String toString() {
		return "TaoBaoIpData [ip=" + ip + ", country=" + country + ", area="
				+ area + ", region=" + region + ", city=" + city + ", county="
				+ county + ", isp=" + isp + ", country_id=" + country_id
				+ ", area_id=" + area_id + ", region_id=" + region_id
				+ ", city_id=" + city_id + ", county_id=" + county_id
				+ ", isp_id=" + isp_id + "]";
	}
	
	

}
