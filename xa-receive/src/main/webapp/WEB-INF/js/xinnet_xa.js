	if (navigator.cookieEnabled){
		var  xa_api = "http://analysis.xinnet.com/receive/track.do";
		var traker = new xaTraker();
		 var  userCode  = getCookieData("userCode");
		var  flag  = getCookieData("flag");
			if(userCode =="" ||userCode ==null){
			traker.traceTest("xinnet.com","110");
			}else{
				if(flag ==""){
					traker.loginIn("xinnet.com","110",userCode);
					addCookieData("flag",1,0);
				}else{
					traker.traceTest("xinnet.com","110");
				}
			}
	}else{
		 var  xa_api = "http://analysis.xinnet.com/receive/track.do";
			var traker = new xaTraker();
			var  flag  = getCookieData("flag");
				if(userCode =="" ||userCode ==null){
				traker.traceTest("xinnet.com","110");
				}else{
					if(flag ==""){
						traker.loginIn("xinnet.com","110",userCode);
						addCookieData("flag",1,0);
					}else{
						traker.traceTest("xinnet.com","110");
					}
				}
	}
	function xaTraker(){
		var isInit = false;
		var monitor = true;
		var visitor = 0;

		var xa_img = null;
		var xa_frame = null;
		var xa_form = null;
		var xa_input = null;

		var xa_touch = "L";
		var xa_ptype = "N";
		var itime = null;
		var vtime = null;

		var xa_tenantID = "";
		var xa_domain = document.domain;
		var currentUrl	= window.location.href;
		var xa_preref = document.referrer;
		var last_query = null;
		var newVisitor =0;
		(function() {
			
			itime  = new Date().getTime();
			var pageno = getCookieData("pageno");
			var frontUrl = getCookieData("frontUrl");
			frontUrl = decodeURIComponent(frontUrl);
			addCookieData("_page",frontUrl,0);
			if(pageno === ""){
				pageno = 1;
				addCookieData("pageno",pageno,720); // 30day
				addCookieData("frontUrl",currentUrl,24);
			}else{
				if(frontUrl == currentUrl ){
					addCookieData("pageno",pageno,720);
					addCookieData("frontUrl",currentUrl,24);
				}else{
					pageno = parseInt(pageno)+1;
					addCookieData("pageno",pageno,720);
					addCookieData("frontUrl",currentUrl,24);
				}
			}
			var uid = getCookieData("uid");
			if(uid === ""){
				uid = makeUID();
				newVisitor = 1;
				addCookieData("uid",uid,10000);
			}
			var sid = getCookieData("sId");
			if(sid === ""){
				sid = makeUID();
				addCookieData("sId",sid,0); // 会话
			}
		})();
		this.traceTest = function(domain,tenantID) {  // 普通发送L
			
			isInit =  checkPage(currentUrl);
			if(isInit === true)
			return;
			xa_domain = domain;
			xa_tenantID = tenantID ;
			this.checkDomain();
			var query = new Array();
			query.push("operationType="+"L");
			query.push("domain=" + xa_domain);   // liweichao
			query.push("tenantID=" + xa_tenantID);
			query.push("userId="+getCookieData("uid"));
			query.push("prevUrl=" + escape(xa_preref));
			query.push("currentUrl="+currentUrl);
			query.push("jsSessionId="+getCookieData("sId"));
			query.push("pageNumber="+getCookieData("pageno"));
			query.push("newVisitor="+newVisitor);
			this.last_query = query;
			
			doGet(xa_api,query.join("&"));

		};
		this.loginIn = function(domain,tenantID,act) {  // 登录动作成功 发送O
			xa_domain = domain;
			xa_tenantID = tenantID ;
			this.checkDomain();

			var query = new Array();
			query.push("operationData="+act);
			query.push("operationType="+"O");
			query.push("domain=" + xa_domain);   // liweichao
			query.push("tenantID=" + xa_tenantID);
			query.push("userId="+getCookieData("uid"));
			query.push("prevUrl=" + escape(xa_preref));
			query.push("currentUrl="+currentUrl);
			query.push("jsSessionId="+getCookieData("sId"));
			query.push("pageNumber="+getCookieData("pageno"));
			query.push("newVisitor="+newVisitor);
			this.last_query = query;
			doGet(xa_api,query.join("&"));
		};
		this.checkDomain = function (){
			if(xa_domain == '' || xa_domain == null){
				xa_domain =  document.domain;
			}
		};
		window.onbeforeunload = function(e) {

			var evt = e ? e : (window.event ? window.event : null);
			var thisPage=false;
			 if(!thisPage){
					var lastTime = new Date().getTime();
					vtime = lastTime - itime;
					var sid = getCookieData("sId");
					
					if(vtime > 1800000){
						addCookieData("sId","",0);
					}
					var query = new Array();
					query.push("operationType="+"V");
					query.push("domain=" + "xinnet.com");   // liweichao
					query.push("tenantID=" + "110");
					query.push("vtime=" + vtime);
					query.push("userId="+getCookieData("uid"));
					query.push("prevUrl=" + escape(xa_preref));
					query.push("currentUrl="+currentUrl);
					query.push("jsSessionId="+sid);
					query.push("pageNumber="+getCookieData("pageno"));
					this.last_query = query;
					doGet(xa_api,query.join("&"));
			 }
		};
	}

	function addCookieData(name,value,expired){
		var tmp = name + "=" + encodeURIComponent(value) + "; Path=/;";
		if (expired > 0) {
			var date = new Date();
			date.setTime(date.getTime() + expired * 3600000);
			tmp = tmp + " expires=" + date.toGMTString();
		}
		document.cookie = tmp;
	}

	function getCookieData(name) {
		var d = new RegExp("(^|;)[ ]*" + name + "[ ]*=[ ]*[^;]+", "i");
		if (document.cookie.match(d)) {
			var tmp = document.cookie.match(d)[0];
			var pos = tmp.indexOf("=");
	  		if (pos > 0){
	  			return tmp.substring(pos + 1).replace(/^\s+|\s+$/g, "");
	  		}
		}
		else{
			return "";
		}
	}

	function doGet(url, query) {
		try {
			checkDOM("get");
            query =query.replace("#","-");
			traker.xa_visitor = 0;
			traker.xa_img.src = url + "?" + query;
			traker.last_query = null;
		}
		catch (err) {
		}
	}
	/**
	 * Uid 客户端浏览器id
	 * 
	 * @return
	 */
	function makeUID() {
		var s = [];
		var hexDigits = "0123456789ABCDEF";
		for ( var i = 0; i < 32; i++) {
			s[i] = hexDigits.substr(Math.floor(Math.random() * 0x10), 1);
		}
		s[12] = "4";
		s[16] = hexDigits.substr((s[16] & 0x3) | 0x8, 1);

		var uuid = s.join("");
		return uuid;
	}
	function checkDOM(method) {
		if (method === "get") {
			if (traker.xa_img != undefined)
				return;
			traker.xa_img = document.createElement('img');
			traker.xa_img.src = "";
		}
	}
	function checkPage(currentUrl){
		var _flag = false;
		var _page = getCookieData("_page");
		_page = decodeURIComponent(_page);
		if(_page == currentUrl){
			_flag = true;
		}
		return _flag;
	}