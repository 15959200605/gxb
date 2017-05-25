package com.gxb.web.util;

import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.control.GeneralControl;
import com.gxb.web.online.OnlineMessage;
import com.gxb.web.online.TokenServer;


/**
 * 基础相应类
 * @author hanwei
 *
 */
public class BaseWebService extends GeneralControl{
	public final static String ERROR_PARAM = "参数不完整";
	public Logger log = Logger.getLogger(BaseWebService.class.getName());

	public void sendWarm(HttpServletResponse response, String msg) {
		this.sendJsonResponse(response, "{\"state\":false,\"msg\":\"" + msg	+ "\"}");
	}

	public void sendException(HttpServletResponse response, Exception e) {
		this.log.error(e);
		this.sendJsonResponse(response, "{\"state\":false,\"msg\":\"操作出现异常(详细："
				+ e.getMessage() + ")\"}");
	}

	public boolean checkParam(HttpServletResponse response, Object... params) {
		for (Object object : params) {
			if (object instanceof String) {
				if (null == object || "".equals(object.toString().trim())) {
					this.sendJsonResponse(response,	"{\"state\":false,\"msg\":\"" + ERROR_PARAM+ "\"}");
					return false;
				}
			} else {
				if (null == object) {
					this.sendJsonResponse(response,"{\"state\":false,\"msg\":\"" + ERROR_PARAM+ "\"}");
					return false;
				}
			}
		}
		return true;
	}

	public boolean checkLoginState(HttpServletResponse response, String token) {
		OnlineMessage message = TokenServer.tokenCheck(token);
		if (message.isSuccess()) {
			return true;
		} else {
			sendWarm(response, message.getMessage());
			return false;
		}
	}

	public void sendSuccess(HttpServletResponse response, String msg) {
		this.sendJsonResponse(response, "{\"state\":true,\"msg\":\"" + msg+ "\"}");
	}
	/*public String getDateBase(String token){
		OnlineUser user = TokenServer.tokenCheck(token).getOnlineMember();
		return user==null?null:user.getDatabase();
	}*/
}
