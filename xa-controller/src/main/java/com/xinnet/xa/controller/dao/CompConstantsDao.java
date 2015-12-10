package com.xinnet.xa.controller.dao;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.xinnet.xa.controller.model.CompConstants;
import com.xinnet.xa.controller.model.IpAndPortTransferId;

public interface CompConstantsDao extends PagingAndSortingRepository<CompConstants, IpAndPortTransferId>,JpaSpecificationExecutor<CompConstants>{

}
