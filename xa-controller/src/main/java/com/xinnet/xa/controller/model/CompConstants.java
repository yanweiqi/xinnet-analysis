package com.xinnet.xa.controller.model;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="monitor_component_constants")
public class CompConstants {
	
	@EmbeddedId
	private IpAndPortTransferId id;
	
	@Column(name="queues")
	private String queues;
	
	@Column(name="manageListSize")
	private Integer manageListSize;
	
	@Column(name="types")
	private String types;
	
	@Column(name="saveThreadNum")
	private Integer saveThreadNum;

	public IpAndPortTransferId getId() {
		return id;
	}

	public void setId(IpAndPortTransferId id) {
		this.id = id;
	}

	public String getQueues() {
		return queues;
	}

	public void setQueues(String queues) {
		this.queues = queues;
	}

	public Integer getManageListSize() {
		return manageListSize;
	}

	public void setManageListSize(Integer manageListSize) {
		this.manageListSize = manageListSize;
	}

	public String getTypes() {
		return types;
	}

	public void setTypes(String types) {
		this.types = types;
	}

	public Integer getSaveThreadNum() {
		return saveThreadNum;
	}

	public void setSaveThreadNum(Integer saveThreadNum) {
		this.saveThreadNum = saveThreadNum;
	}
	
	

}
