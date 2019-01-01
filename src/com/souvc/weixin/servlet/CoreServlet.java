package com.souvc.weixin.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.souvc.weixin.main.MenuManager;
import com.souvc.weixin.service.CoreService;
import com.souvc.weixin.util.SignUtil;



public class CoreServlet extends HttpServlet {
	
	private static Logger log = LoggerFactory.getLogger(CoreServlet.class);
    private static final long serialVersionUID = 4323197796926899691L;

    /**
     * ȷ����������΢�ŷ�����
     */
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        // ΢�ż���ǩ��
        String signature = request.getParameter("signature");
        // ʱ���
        String timestamp = request.getParameter("timestamp");
        // �����
        String nonce = request.getParameter("nonce");
        // ����ַ���
        String echostr = request.getParameter("echostr");
        
        log.info(signature);
        log.info(timestamp);
        log.info(nonce);
        log.info(echostr);

        PrintWriter out = response.getWriter();
        
        // ͨ������signature���������У�飬��У��ɹ���ԭ������echostr����ʾ����ɹ����������ʧ��
        if (SignUtil.checkSignature(signature, timestamp, nonce)) {
        	log.info("Authentication success!");
        	log.info(echostr);
        	out.print(echostr);
        }
        else {
        	log.error("Authentication failed!");
        }
        
        out.close();
        out = null;
    }

    /**
     * ����΢�ŷ�������������Ϣ
     */
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    	// ��Ϣ�Ľ��ա�������Ӧ
        // ��������Ӧ�ı��������ΪUTF-8����ֹ�������룩
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

        // ���ú���ҵ���������Ϣ��������Ϣ
        String respXml = CoreService.processRequest(request);
        if(respXml.equals("error")){
        	log.info("�������󷵻ش���");
        	return;
        }
        log.info(respXml);
        
        // ��Ӧ��Ϣ
        PrintWriter out = response.getWriter();
        out.print(respXml);
        out.close();
    }

}