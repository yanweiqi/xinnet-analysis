package com.xinnet.xa.collector.saletrack.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xinnet.xa.collector.saletrack.dao.UserGoodsOrderDao;
import com.xinnet.xa.collector.saletrack.entity.UserGoodsOrderPo;
import com.xinnet.xa.collector.saletrack.service.IUserGoodsOrderService;
import com.xinnet.xa.collector.saletrack.vo.BusinessOperationType;
import com.xinnet.xa.core.vo.UserGoodsOrderDetailVo;

@Service("userGoodsOrderService")
public class UserGoodsOrderService implements IUserGoodsOrderService{

	private final static Logger logger = Logger.getLogger(UserGoodsOrderService.class);
	
	@Autowired
	private UserGoodsOrderDao userGoodsOrderDao;
	
	@Override
	public  void batchInsert(Set<UserGoodsOrderPo> set) throws Exception{
		/**
		 * 由于多线程原因，保存之前必须检测该对象是否存在，数据存在不在保存，做更新操作
		 */
		Set<UserGoodsOrderPo> updateSet = new LinkedHashSet<UserGoodsOrderPo>();
		for(UserGoodsOrderPo vpo: set){
			if(StringUtils.isNotBlank(vpo.getShopCartGoodsCookieId())){
				UserGoodsOrderPo po = userGoodsOrderDao.findBy(vpo.getShopCartGoodsCookieId());
				if(null != po ) {
					updateSet.add(vpo);
					set.remove(vpo);
				}
				else{
					convertUpdateUserGoodsOrder(vpo, vpo);
					logger.info("Insert UserGoodsOrderPo " + vpo);
				}
			}
		}
		try {
			userGoodsOrderDao.save(set);		
		} catch (IllegalArgumentException e) {
			logger.error("批量保存失败，失败原因:"+e.getMessage(),e);
		}
		if(updateSet.size() > 0){
		    logger.info("有 "+updateSet.size()+" 个进入购物车动作延迟！");
			batchUpdate(updateSet);//假如进入购物车的动作后到，更新进入购物车的操作
		}		
	}

	@Override
	public synchronized void batchUpdate(Set<UserGoodsOrderPo> set) throws Exception{
		if(null != set){
			List<UserGoodsOrderPo> UserGoodsOrderPos = new ArrayList<UserGoodsOrderPo>();
			for (UserGoodsOrderPo userGoodsOrder : set) {
				if(StringUtils.isNotBlank(userGoodsOrder.getShopCartGoodsCookieId())){
				   logger.info("Update action，BusinessOperationType"+ userGoodsOrder.getBusinessOperationType());	
				   UserGoodsOrderPo po = userGoodsOrderDao.findBy(userGoodsOrder.getShopCartGoodsCookieId());   
				   if(null != po){
					   logger.info("Update before UserGoodsOrderPo:"+po);
					   convertUpdateUserGoodsOrder(userGoodsOrder, po);	
					   logger.info("Updata UserGoodsOrderPo:"+po);
					   UserGoodsOrderPos.add(po);		   
				   }
				   else{
					   logger.warn("WARN...! " + userGoodsOrder);
					   userGoodsOrderDao.save(userGoodsOrder);
				   }
				}
				else{
					logger.warn("Update UserGoodsOrderPo Entity then GoodsCookieId and ShopCartCookieId is not null.");
				}
			}
			logger.info("正在 Update "+ UserGoodsOrderPos.size() +" UserGoodsOrderPo");
			try {
				 userGoodsOrderDao.save(UserGoodsOrderPos);
			} catch (IllegalArgumentException e) {
				logger.error("批量更新失败，失败原因："+e.getMessage(),e);
			}
		}
	}

	private synchronized void convertUpdateUserGoodsOrder(UserGoodsOrderPo userGoodsOrder, UserGoodsOrderPo userGoodsOrderPo) {
	    Integer operationType = Integer.valueOf(userGoodsOrder.getBusinessOperationType()) ;
	    //1添加购物车
	    if(operationType == BusinessOperationType.GOODSADDSHOPCART.getIndex()){
	       userGoodsOrderPo.setAddShopCartCurrentUrl(userGoodsOrder.getAddShopCartCurrentUrl());
	       userGoodsOrderPo.setShopCartGoodsStatus(BusinessOperationType.GOODSADDSHOPCART.getMark());
	       if(null!=userGoodsOrder.getAddShopCartDateTime()) userGoodsOrderPo.setAddShopCartDateTime(userGoodsOrder.getAddShopCartDateTime());
	    }
		//2移除购物车
		if(operationType == BusinessOperationType.GOODSREMOVESHOPCART.getIndex()){
		   userGoodsOrderPo.setShopCartGoodsStatus(BusinessOperationType.GOODSREMOVESHOPCART.getMark());
		}
		//3进入购物车
        if(operationType == BusinessOperationType.INTOSHOPCART.getIndex()){
        	if(StringUtils.isNotBlank(userGoodsOrder.getHyCode())) userGoodsOrderPo.setHyCode(userGoodsOrder.getHyCode());
        	if(null!=userGoodsOrder.getGoodsMoney())               userGoodsOrderPo.setGoodsMoney(userGoodsOrder.getGoodsMoney());
        	if(null!=userGoodsOrder.getShopCartOrderTotalMoney())  userGoodsOrderPo.setShopCartOrderTotalMoney(userGoodsOrder.getShopCartOrderTotalMoney());
        	if(StringUtils.isNotBlank(userGoodsOrder.getGoodsBuyLong()))     userGoodsOrderPo.setGoodsBuyLong(userGoodsOrder.getGoodsBuyLong());
        	if(StringUtils.isNotBlank(userGoodsOrder.getShopCartCookieId())) userGoodsOrderPo.setShopCartCookieId(userGoodsOrder.getShopCartCookieId());
        	if(StringUtils.isNotBlank(userGoodsOrder.getGoodsBuyType()))     userGoodsOrderPo.setGoodsBuyType(userGoodsOrder.getGoodsBuyType());
        	if(StringUtils.isNotBlank(userGoodsOrder.getUserCookieId()))     userGoodsOrderPo.setUserCookieId(userGoodsOrder.getUserCookieId());
        	if(null!=userGoodsOrder.getGoodsNumber())                  userGoodsOrderPo.setGoodsNumber(userGoodsOrder.getGoodsNumber());
 		    if(StringUtils.isNotBlank(userGoodsOrder.getGoodsName()))  userGoodsOrderPo.setGoodsName(userGoodsOrder.getGoodsName());
 		    if(StringUtils.isNotBlank(userGoodsOrder.getShopCartGoodsCookieId())) userGoodsOrderPo.setShopCartGoodsCookieId(userGoodsOrder.getShopCartGoodsCookieId());
 		    if(null!= userGoodsOrder.getGoodsBuyTimes()) userGoodsOrderPo.setGoodsBuyTimes(userGoodsOrder.getGoodsBuyTimes());
 		    userGoodsOrderPo.setShopCartGoodsStatus(BusinessOperationType.INTOSHOPCART.getMark());
        }
		//4购物车商品结算
		if(operationType == BusinessOperationType.SHOPCARTSETTLEMENT.getIndex()){
			if(null!=userGoodsOrder.getShopCartSettleDateTime())  userGoodsOrderPo.setShopCartSettleDateTime(userGoodsOrder.getShopCartSettleDateTime()); 
			if(StringUtils.isNotBlank(userGoodsOrder.getHyCode())) userGoodsOrderPo.setHyCode(userGoodsOrder.getHyCode());
			if(StringUtils.isNotBlank(userGoodsOrder.getShopCartOrderCode()))userGoodsOrderPo.setShopCartOrderCode(userGoodsOrder.getShopCartOrderCode()); 
			userGoodsOrderPo.setShopCartGoodsStatus(BusinessOperationType.SHOPCARTSETTLEMENT.getMark());
		}	
		//5订单结算
		if(operationType == BusinessOperationType.ORDERPAYMENTTRACK.getIndex()){
		   if(StringUtils.isNotBlank(userGoodsOrder.getFavorableCode()))      userGoodsOrderPo.setFavorableCode(userGoodsOrder.getFavorableCode());
		   if(StringUtils.isNotBlank(userGoodsOrder.getGoodsCode()))          userGoodsOrderPo.setGoodsCode(userGoodsOrder.getGoodsCode());
		   if(null!=userGoodsOrder.getOrderSuccessPayDateTime())              userGoodsOrderPo.setOrderSuccessPayDateTime(userGoodsOrder.getOrderSuccessPayDateTime());
		   if(StringUtils.isNotBlank(userGoodsOrder.getShopCartOrderCode()))  userGoodsOrderPo.setShopCartOrderCode(userGoodsOrder.getShopCartOrderCode());
		   if(StringUtils.isNotBlank(userGoodsOrder.getGoodsOrderCode()))	  userGoodsOrderPo.setGoodsOrderCode(userGoodsOrder.getGoodsOrderCode());
		   if(StringUtils.isNotBlank(userGoodsOrder.getOrderPayType()))       userGoodsOrderPo.setOrderPayType(userGoodsOrder.getOrderPayType());
		   if(StringUtils.isNotBlank(userGoodsOrder.getOrderPayBillNumber())) userGoodsOrderPo.setOrderPayBillNumber(userGoodsOrder.getOrderPayBillNumber());
		   if(StringUtils.isNotBlank(userGoodsOrder.getGoodsServiceCode()))   userGoodsOrderPo.setGoodsServiceCode(userGoodsOrder.getGoodsServiceCode());
		   if(StringUtils.isNotBlank(userGoodsOrder.getGoodsOrderStatus()))   userGoodsOrderPo.setGoodsOrderStatus(userGoodsOrder.getGoodsOrderStatus());
		   userGoodsOrderPo.setShopCartGoodsStatus(BusinessOperationType.ORDERPAYMENTTRACK.getMark());
		}
	}


	@Override
	public synchronized void processingData(Set<UserGoodsOrderDetailVo> userGoodsOrderDetailVos) throws Exception{
		Map<String, LinkedHashSet<UserGoodsOrderPo>> userGoodsOrderPoMap = getUserGoodsOrderPoMap(userGoodsOrderDetailVos);

		if(null != userGoodsOrderPoMap.get(BusinessOperationType.GOODSADDSHOPCART.getOpertionType())) {
			LinkedHashSet<UserGoodsOrderPo> list = userGoodsOrderPoMap.get(BusinessOperationType.GOODSADDSHOPCART.getOpertionType()); 
			logger.info("准备批量保存 "+list.size()+" UserGoodsOrderPo");
			for (UserGoodsOrderPo userGoodsOrderPo : list) {
				userGoodsOrderPo.setTimeSequence(new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date(Long.parseLong(userGoodsOrderPo.getTimeSequence()))));
				logger.info(userGoodsOrderPo);
			}
			batchInsert(list);
		}
		if(null != userGoodsOrderPoMap.get(BusinessOperationType.UPDATEGOODSORDERSHOPCART.getOpertionType())) {
			LinkedHashSet<UserGoodsOrderPo> list = userGoodsOrderPoMap.get(BusinessOperationType.UPDATEGOODSORDERSHOPCART.getOpertionType());
			logger.info("准备批量更新 "+list.size()+" 个UserGoodsOrderPo");
			for (UserGoodsOrderPo userGoodsOrderPo : list) {
				userGoodsOrderPo.setTimeSequence(new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date(Long.parseLong(userGoodsOrderPo.getTimeSequence()))));
				logger.info(userGoodsOrderPo);
			}
			batchUpdate(list);
		}
	}
	
    /**
     * 功能描述：构造Map对象，把传入Set进行Map拆分，对象UserGoodsOrderPo对数据的插入、更新<br>
     *           1、key1值GOODSADDSHOPCART.getOpertionType()，value存储插入对象。<br>
     *           2、key2值UPDATEGOODSORDERSHOPCART.getOpertionType()，value存储更新对象。
     *           
     * @param userGoodsOrderDetailVos
     * @return
     */
    private  Map<String,LinkedHashSet<UserGoodsOrderPo>> getUserGoodsOrderPoMap(Set<UserGoodsOrderDetailVo> userGoodsOrderDetailVos ) {  
		Map<String, LinkedHashSet<UserGoodsOrderPo>> userGoodsOrderPoMap = new HashMap<String, LinkedHashSet<UserGoodsOrderPo>>();	
       for (UserGoodsOrderDetailVo userGoodsOrderDetailVo : userGoodsOrderDetailVos) {
	       	int businessOperationType = Integer.parseInt(userGoodsOrderDetailVo.getBusinessOperationType());  
	       	logger.info("businessOperationType:"+businessOperationType+","+userGoodsOrderDetailVo.getBusinessOperationType());
	        //商品添加购物车
	       	if( businessOperationType== BusinessOperationType.GOODSADDSHOPCART.getIndex()){ 
	       		if(null != userGoodsOrderPoMap.get(BusinessOperationType.GOODSADDSHOPCART.getOpertionType())){
	       		   LinkedHashSet<UserGoodsOrderPo> set = userGoodsOrderPoMap.get(BusinessOperationType.GOODSADDSHOPCART.getOpertionType());
	       		   set.add(convertUserGoodsOrderDetailVoTo(userGoodsOrderDetailVo));
	       		}
	       		else{
	       			LinkedHashSet<UserGoodsOrderPo> userGoodsOrderPos  = new LinkedHashSet<UserGoodsOrderPo>();
	       			userGoodsOrderPos.add(convertUserGoodsOrderDetailVoTo(userGoodsOrderDetailVo));
	       			userGoodsOrderPoMap.put(BusinessOperationType.GOODSADDSHOPCART.getOpertionType(), userGoodsOrderPos);
	       		}
	       	}
	       	else{
	       		if(null != userGoodsOrderPoMap.get(BusinessOperationType.UPDATEGOODSORDERSHOPCART.getOpertionType())){
	       			LinkedHashSet<UserGoodsOrderPo> list = userGoodsOrderPoMap.get(BusinessOperationType.UPDATEGOODSORDERSHOPCART.getOpertionType());
	       			list.add(convertUserGoodsOrderDetailVoTo(userGoodsOrderDetailVo));
	       		}
	       		else{
	       			LinkedHashSet<UserGoodsOrderPo> userGoodsOrderPos  = new LinkedHashSet<UserGoodsOrderPo>();
	       			userGoodsOrderPos.add(convertUserGoodsOrderDetailVoTo(userGoodsOrderDetailVo));
	       			userGoodsOrderPoMap.put(BusinessOperationType.UPDATEGOODSORDERSHOPCART.getOpertionType(), userGoodsOrderPos);
	       		}
	       	}
		}
       return userGoodsOrderPoMap;         
    }
    
 	private  UserGoodsOrderPo convertUserGoodsOrderDetailVoTo(UserGoodsOrderDetailVo userGoodsOrderDetailVo){
		UserGoodsOrderPo userGoodsOrderPo = new UserGoodsOrderPo();
		BeanUtils.copyProperties(userGoodsOrderDetailVo, userGoodsOrderPo);
		/**
		 * BeanUtils不转化跨类型的属性
		 */
		try {
			if(StringUtils.isNotBlank( userGoodsOrderDetailVo.getAddShopCartDateTime() ))userGoodsOrderPo.setAddShopCartDateTime( new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse( userGoodsOrderDetailVo.getAddShopCartDateTime() ));
			if(StringUtils.isNotBlank( userGoodsOrderDetailVo.getGoodsBuyTimes())) userGoodsOrderPo.setGoodsBuyTimes( Integer.parseInt(userGoodsOrderDetailVo.getGoodsBuyTimes()));
			if(StringUtils.isNotBlank( userGoodsOrderDetailVo.getGoodsMoney() ))userGoodsOrderPo.setGoodsMoney(Double.valueOf(userGoodsOrderDetailVo.getGoodsMoney() ));
			if(StringUtils.isNotBlank( userGoodsOrderDetailVo.getGoodsNumber() ))userGoodsOrderPo.setGoodsNumber( Integer.parseInt(userGoodsOrderDetailVo.getGoodsNumber() ));
			if(StringUtils.isNotBlank( userGoodsOrderDetailVo.getShopCartOrderTotalMoney() ))userGoodsOrderPo.setShopCartOrderTotalMoney( Double.valueOf(userGoodsOrderDetailVo.getShopCartOrderTotalMoney() ));
			if(StringUtils.isNotBlank( userGoodsOrderDetailVo.getOrderSuccessPayDateTime() ))userGoodsOrderPo.setOrderSuccessPayDateTime(new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse(userGoodsOrderDetailVo.getOrderSuccessPayDateTime() ));
			if(StringUtils.isNotBlank( userGoodsOrderDetailVo.getShopCartSettleDateTime() ))userGoodsOrderPo.setShopCartSettleDateTime( new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse( userGoodsOrderDetailVo.getShopCartSettleDateTime() ));
			if(StringUtils.isNotBlank(userGoodsOrderDetailVo.getAddShopCartCurrentUrl())){
			   String addShopCartCurrentUrl = userGoodsOrderDetailVo.getAddShopCartCurrentUrl();
			   addShopCartCurrentUrl = addShopCartCurrentUrl.length()>254?addShopCartCurrentUrl.substring(0, 254):addShopCartCurrentUrl;
			   userGoodsOrderPo.setAddShopCartCurrentUrl(addShopCartCurrentUrl);
			}
		} catch (ParseException e) {
			logger.error("类型转化失败：",e);
		}
		return userGoodsOrderPo; 
	}

}
