package com.xinnet.xa.controller.dao;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.xinnet.xa.controller.model.CompExceptionMessage;

public interface CompExceptionMessageDao extends PagingAndSortingRepository<CompExceptionMessage, Integer>,JpaSpecificationExecutor<CompExceptionMessage>{

}
