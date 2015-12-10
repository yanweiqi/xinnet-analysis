package com.xinnet.xa.collector.saletrack.service;

import java.util.Set;

import com.xinnet.xa.collector.general.service.IBusinessService;
import com.xinnet.xa.collector.saletrack.entity.UserGoodsOrderPo;
import com.xinnet.xa.core.vo.UserGoodsOrderDetailVo;


/**
 * 功能描述：用户购买商品订单转化业务接口
 * @author yanweiqi
 */
public interface IUserGoodsOrderService extends IBusinessService<UserGoodsOrderDetailVo> {
	
	/**
	 * 功能描述：批量保存
	 * @param collection
	 */
	void batchInsert(Set<UserGoodsOrderPo> set) throws Exception;
	
	/**
	 * 功能描述：批量更新
	 * @param collection
	 */
	void batchUpdate(Set<UserGoodsOrderPo> set) throws Exception;
		
}
