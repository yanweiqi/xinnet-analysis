package com.xinnet.xa.controller.dao;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.xinnet.xa.controller.model.TomcatMonitor;

public interface TomcatMonitorDao extends PagingAndSortingRepository<TomcatMonitor, Integer>,JpaSpecificationExecutor<TomcatMonitor>{

}
