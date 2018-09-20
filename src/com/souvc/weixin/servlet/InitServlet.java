package com.souvc.weixin.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.souvc.weixin.thread.TokenThread;

public class InitServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static Logger log = LoggerFactory.getLogger(InitServlet.class);
    

    public void init() throws ServletException {
        // ��ȡweb.xml�����õĲ���
        TokenThread.appid = getInitParameter("appid");
        TokenThread.appsecret = getInitParameter("appsecret");

        log.info("weixin api appid:{}", TokenThread.appid);
        log.info("weixin api appsecret:{}", TokenThread.appsecret);

        // δ����appid��appsecretʱ������ʾ
        if ("".equals(TokenThread.appid) || "".equals(TokenThread.appsecret)) {
            log.error("appid and appsecret configuration error, please check carefully.");
        } else {
            // ������ʱ��ȡaccess_token���߳�
            new Thread(new TokenThread()).start();
        }
    }
}
