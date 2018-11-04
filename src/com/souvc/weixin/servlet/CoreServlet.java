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
     * 确认请求来自微信服务器
     */
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
    	/*System.out.println("enter doGet");
    	String outPath = "D:\\out.log";
		System.setOut(new PrintStream(new FileOutputStream(outPath, true)));*/
        // 微信加密签名
        String signature = request.getParameter("signature");
        // 时间戳
        String timestamp = request.getParameter("timestamp");
        // 随机数
        String nonce = request.getParameter("nonce");
        // 随机字符串
        String echostr = request.getParameter("echostr");

        PrintWriter out = response.getWriter();
        
        // 通过检验signature对请求进行校验，若校验成功则原样返回echostr，表示接入成功，否则接入失败
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
     * 处理微信服务器发来的消息
     */
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    	// 消息的接收、处理、响应
        // 将请求、响应的编码均设置为UTF-8（防止中文乱码）
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

        // 调用核心业务类接收消息、处理消息
        String respXml = CoreService.processRequest(request);
        if(respXml.equals("error")){
        	System.out.println("处理请求返回错误");
        	return;
        }
        System.out.println(respXml);
        
        // 响应消息
        PrintWriter out = response.getWriter();
        out.print(respXml);
        out.close();
    }

}