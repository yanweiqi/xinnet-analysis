package com.xinnet.xa.controller.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.xinnet.xa.controller.model.Component;
import com.xinnet.xa.controller.model.IpAndPortTransferId;

@Repository
public interface ComponentDao extends PagingAndSortingRepository<Component, IpAndPortTransferId>,JpaSpecificationExecutor<Component>{
	@Query("select t from Component t where t.state=?1")
	public List<Component> getComponentsByState(int state); 
	
	 
}
