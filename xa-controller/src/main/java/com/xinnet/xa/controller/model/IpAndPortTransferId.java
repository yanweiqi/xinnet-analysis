package com.xinnet.xa.controller.model;

import java.io.Serializable;

import javax.persistence.Embeddable;

@Embeddable
public class IpAndPortTransferId implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -9008963107700251531L;
	private String ip;
	private Integer port;

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public Integer getPort() {
		return port;
	}

	public void setPort(Integer port) {
		this.port = port;
	}
	
	

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((ip == null) ? 0 : ip.hashCode());
		result = prime * result + ((port == null) ? 0 : port.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		IpAndPortTransferId other = (IpAndPortTransferId) obj;
		if (ip == null) {
			if (other.ip != null)
				return false;
		} else if (!ip.equals(other.ip))
			return false;
		if (port == null) {
			if (other.port != null)
				return false;
		} else if (!port.equals(other.port))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "IpAndPortTransferId [ip=" + ip + ", port=" + port + "]";
	}
	
	

}
