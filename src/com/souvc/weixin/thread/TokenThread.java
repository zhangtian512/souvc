package com.souvc.weixin.thread;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.souvc.weixin.pojo.Token;
import com.souvc.weixin.util.CommonUtil;
import com.souvc.weixin.util.TokenUtil;

public class TokenThread implements Runnable {
    private static Logger log = LoggerFactory.getLogger(TokenThread.class);
    // 第三方用户唯一凭证
    public static String appid = "";
    // 第三方用户唯一凭证密钥
    public static String appsecret = "";
    public static Token accessToken = null;

    public void run() {
    	
    	log.info("Enter TokenThread run");
    	Thread.currentThread().setName("Access_Token_Obtain_Task");
        while (true) {
            try {
                accessToken = CommonUtil.getToken(appid, appsecret);
                if (null != accessToken) {
                    //调用存储到数据库
                    TokenUtil.saveToken(accessToken);
                    log.info("获取access_token成功，pidname:{} pidno:{} 有效时长{}秒 token:{} ",
                    		Thread.currentThread().getName(),
                    		Thread.currentThread().getId(),
                    		accessToken.getExpiresIn(),
                    		accessToken.getAccessToken());
                    // 休眠7000秒
                    Thread.sleep((accessToken.getExpiresIn() - 200)*1000);
                } else {
                    // 如果access_token为null，60秒后再获取
                	log.error("Get access_token failed try again after 60secs");
                    Thread.sleep(60 * 1000);
                }
            } catch (InterruptedException e) {
                try {
                    Thread.sleep(60 * 1000);
                } catch (InterruptedException e1) {
                    log.error("{}", e1);
                }
                log.error("{}", e);
            }
        }
    }
}