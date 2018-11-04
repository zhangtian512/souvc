package com.souvc.weixin.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.souvc.weixin.service.CoreService;
import com.souvc.weixin.util.SignUtil;


public class CoreServlet extends HttpServlet {

    private static final long serialVersionUID = 4323197796926899691L;

    /**
     * ȷ����������΢�ŷ�����
     */
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
    	/*System.out.println("enter doGet");
    	String outPath = "D:\\out.log";
		System.setOut(new PrintStream(new FileOutputStream(outPath, true)));*/
        // ΢�ż���ǩ��
        String signature = request.getParameter("signature");
        // ʱ���
        String timestamp = request.getParameter("timestamp");
        // �����
        String nonce = request.getParameter("nonce");
        // ����ַ���
        String echostr = request.getParameter("echostr");

        PrintWriter out = response.getWriter();
        
        // ͨ������signature���������У�飬��У��ɹ���ԭ������echostr����ʾ����ɹ����������ʧ��
        if (SignUtil.checkSignature(signature, timestamp, nonce)) {
        	System.out.println("Authentication success!");
            out.print(echostr);
        }
        else {
        	System.out.println("Authentication failed!");
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
        	System.out.println("�������󷵻ش���");
        	return;
        }
        System.out.println(respXml);
        
        // ��Ӧ��Ϣ
        PrintWriter out = response.getWriter();
        out.print(respXml);
        out.close();
    }

}