package com.xinnet.xa.collector.general.service;

import java.util.Set;

/**
 * 功能描述：抽象业务接口
 * @author yanweiqi
 * @param <T>
 */
public interface IBusinessService<T>{
    /**
     * 功能描述：核心业务流程
     * @param list
     * @throws Exception
     */
	void processingData(Set<T> list) throws Exception;
}
