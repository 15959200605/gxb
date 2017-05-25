package com.gxb.web.util;

import java.util.Map;

import org.apache.log4j.Logger;
import cn.jpush.api.ErrorCodeEnum;
import cn.jpush.api.JPushClient;
import cn.jpush.api.MessageResult;
/**
  *@说明：发送推送
  *@创建：作者:yxy		创建时间：2013-9-4
  *@param  
  *@修改历史：
  *		[序号](yxy	2013-9-4)<修改说明>
 */
public class JPushClientUtil {
	public static Logger log = Logger.getLogger(JPushClientUtil.class.getName());
	private static JPushClient jpush = new JPushClient("fbba2b3d8ea54fc5c46886cd","35c73589e7c1ae85ed5c0dcd");
	/**
	 * 根据别名发送推送
	 * @param sendNo 自增数字
	 * @param msgTitle 消息标题
	 * @param msgContent 消息内容  必填
	 * @param msgContentType 消息内容类型
	 * @param extra 附加参数
	 * @param remindFlag 是否自定义消息
	 * @return
	 */
	public static boolean jPushMessage(int sendNo,String alias,String msgTitle,String msgContent,Map<String, Object> extra,String remindFlag){
		jpush.setEnableSSL(true);
		MessageResult msgResult = null;
		if("1".equals(remindFlag)){//自定义消息
			msgResult = jpush.sendCustomMessageWithAlias(sendNo, alias, msgTitle, msgContent, "2", extra);
		}else{//通知
			msgResult = jpush.sendNotificationWithAlias(sendNo, alias, msgTitle, msgContent, 2, extra);
		}
		if (null != msgResult) {
		    if (msgResult.getErrcode() == ErrorCodeEnum.NOERROR.value()) {
		        return true;
		    } else {
		    	log.error("推送错误提示代码:"+msgResult.getErrcode()+"推送错误提示信息:"+msgResult.getErrmsg());
		    	return false;
		    }
		} else {
			log.error("推送异常:未推送");
		    return false;
		}
	}
}
