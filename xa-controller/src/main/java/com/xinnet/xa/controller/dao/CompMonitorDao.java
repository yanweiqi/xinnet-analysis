package com.xinnet.xa.controller.dao;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.xinnet.xa.controller.model.CompMonitor;
import com.xinnet.xa.controller.model.IpAndPortTransferId;

public interface CompMonitorDao extends PagingAndSortingRepository<CompMonitor, IpAndPortTransferId>,JpaSpecificationExecutor<CompMonitor>{

}
