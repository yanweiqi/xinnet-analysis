package com.xinnet.xa.controller.model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="monitor_component_register")
public class Component {
	
	@EmbeddedId
	private IpAndPortTransferId id;
	@Column(name="comp_type")
	private String type;
	@Column(name="description")
	private String description;
	@Column(name="state")
     private Integer state;
	@Column(name="registerTime")
	private Timestamp registerTime;
    
	
	public Timestamp getRegisterTime() {
		return registerTime;
	}

	public void setRegisterTime(Timestamp registerTime) {
		this.registerTime = registerTime;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public IpAndPortTransferId getId() {
		return id;
	}

	public void setId(IpAndPortTransferId id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	
	
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String createCompUrl(){
		return "http://"+id.getIp()+":"+id.getPort()+"/"+type;
	}

	@Override
	public String toString() {
		return "Component [id=" + id + ", type=" + type + ", description="
				+ description + "]";
	}
	
	

}
