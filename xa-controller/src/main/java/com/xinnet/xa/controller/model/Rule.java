package com.xinnet.xa.controller.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="monitor_rule")
public class Rule {
	@Id
	@Column(name="ruleName")
	private String ruleName;
	@Column(name="ruleValue")
	private String ruleValue;
	
	
	public String getRuleName() {
		return ruleName;
	}
	public void setRuleName(String ruleName) {
		this.ruleName = ruleName;
	}
	public String getRuleValue() {
		return ruleValue;
	}
	public void setRuleValue(String ruleValue) {
		this.ruleValue = ruleValue;
	}
	
	
	
	
	 

}
