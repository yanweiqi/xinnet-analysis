package com.xinnet.xa.collector.general.entity;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * 功能描述：带有主键生产策略的持久化实体对象
 * @author yanweiqi
 *
 */
public class IdEntity {

	@Id
	@GeneratedValue
	public Long id;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
}
