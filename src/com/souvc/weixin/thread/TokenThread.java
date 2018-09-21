package com.souvc.weixin.thread;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.souvc.weixin.pojo.Token;
import com.souvc.weixin.util.CommonUtil;
import com.souvc.weixin.util.TokenUtil;

public class TokenThread implements Runnable {
    private static Logger log = LoggerFactory.getLogger(TokenThread.class);
    // �������û�Ψһƾ֤
    public static String appid = "";
    // �������û�Ψһƾ֤��Կ
    public static String appsecret = "";
    public static Token accessToken = null;

    public void run() {
    	
    	log.info("Enter TokenThread run");
    	Thread.currentThread().setName("Access_Token_Obtain_Task");
        while (true) {
            try {
                accessToken = CommonUtil.getToken(appid, appsecret);
                if (null != accessToken) {
                    //���ô洢�����ݿ�
                    TokenUtil.saveToken(accessToken);
                    log.info("��ȡaccess_token�ɹ���pidname:{} pidno:{} ��Чʱ��{}�� token:{} ",
                    		Thread.currentThread().getName(),
                    		Thread.currentThread().getId(),
                    		accessToken.getExpiresIn(),
                    		accessToken.getAccessToken());
                    // ����7000��
                    Thread.sleep((accessToken.getExpiresIn() - 200)*1000);
                } else {
                    // ���access_tokenΪnull��60����ٻ�ȡ
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