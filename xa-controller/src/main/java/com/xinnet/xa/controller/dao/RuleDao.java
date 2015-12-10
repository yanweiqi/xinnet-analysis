package com.xinnet.xa.controller.dao;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.xinnet.xa.controller.model.Rule;

public interface RuleDao extends PagingAndSortingRepository<Rule, String>,JpaSpecificationExecutor<Rule>{
	@Query("select t from Rule t where t.ruleName=?1")
	public Rule getRuleByName(String ruleName);

}
