package com.xinnet.xa.core.vo;

import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * 功能描述：经过序列化的抽象VO实体
 * @author yanweiqi
 *
 */
public abstract class AbstractEntityVo implements java.io.Serializable{

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = -8977708032141300452L;

	protected Long id;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	@Override
	public int hashCode() {
		return HashCodeBuilder.reflectionHashCode(this);
	}
	
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}
