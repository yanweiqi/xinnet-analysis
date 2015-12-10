package com.xinnet.xa.analyzer.web;

import java.nio.charset.Charset;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.xinnet.xa.analyzer.util.GetIpUtil;

/**
 * 类名称：IpController.java 
 * 类描述：
 * 创建人：杨旭[yangxu@xinnet.com]
 * 创建时间：2015-2-26 
 * @update:[变更日期YYYY-MM-DD][更改人姓名][变更描述]
 */
@Controller
public class IpController {
	//private Logger logger = Logger.getLogger(IpController.class);
	
	@RequestMapping(value = "/queryIpCity", method = RequestMethod.POST)
	public ResponseEntity<Object> queryIpCity(String ip,HttpServletRequest request){
		String city = null;
		if(StringUtils.isNotBlank(ip)){
			city = GetIpUtil.getIpAreaByTaoBao(ip);
		}
		return toResponseEntity(city);
	}
	
	protected ResponseEntity<Object> toResponseEntity(String result){
		HttpHeaders headers = new HttpHeaders();
		MediaType mediaType = new MediaType("text", "json", Charset.forName("UTF-8"));
		headers.setContentType(mediaType);
		ResponseEntity<Object> responseEntity = new ResponseEntity<Object>(result, headers, HttpStatus.OK);
		return responseEntity;
	}
}
