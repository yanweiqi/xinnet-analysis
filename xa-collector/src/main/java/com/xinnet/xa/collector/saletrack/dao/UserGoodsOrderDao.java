package com.xinnet.xa.collector.saletrack.dao;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.xinnet.xa.collector.saletrack.entity.UserGoodsOrderPo;


@Repository
public interface UserGoodsOrderDao extends PagingAndSortingRepository<UserGoodsOrderPo, Long> ,JpaSpecificationExecutor<UserGoodsOrderPo>{
	
	@Query("select t1 from UserGoodsOrderPo t1 where t1.shopCartGoodsCookieId = ?1")
	public UserGoodsOrderPo findBy(String goods_cookie_id);

}
