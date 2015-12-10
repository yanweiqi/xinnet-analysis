package com.xinnet.xa.collector.saletrack.vo;

import java.util.Comparator;

import org.apache.commons.lang.StringUtils;

import com.xinnet.xa.core.vo.UserGoodsOrderDetailVo;

public class UserGoodsOrderComparator implements Comparator<UserGoodsOrderDetailVo> {

	
	@Override
	public int compare(UserGoodsOrderDetailVo vo1, UserGoodsOrderDetailVo vo2) {
		if(StringUtils.isNotBlank(vo1.getUserCookieId()) && StringUtils.isNotBlank(vo2.getUserCookieId())){
			int index = vo1.getUserCookieId().compareTo(vo2.getUserCookieId());
		    if(index != 0){
		    	return index > 0 ? 3:-1;
		    }		
		    else{
		    	index = vo1.getBusinessOperationType().compareTo(vo2.getBusinessOperationType());
		    	if(index != 0){
		    	   return index > 0 ? 2:-2;
		    	}
		    	else{
			    	index = vo1.getTimeSequence().compareTo(vo2.getTimeSequence());	    		
		    		if(index != 0){
		    		   return index > 0 ? 1:-3;	
		    		}
		    	}
		    }
			return 0;
		}
		else{
			int index = vo1.getBusinessOperationType().compareTo(vo2.getBusinessOperationType());
	    	if(index != 0){
	    	   return index > 0 ? 2:-1;
	    	}
	    	else{
		    	index = vo1.getTimeSequence().compareTo(vo2.getTimeSequence());	    		
	    		if(index != 0){
	    		   return index > 1 ? 1:-2;	
	    		}
	    	}
			return 0;			
		}
	}

}
