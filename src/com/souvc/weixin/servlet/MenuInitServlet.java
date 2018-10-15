package com.souvc.weixin.servlet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

public class MenuInitServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	private static Logger log = LoggerFactory.getLogger(InitServlet.class);
	
	@Override
	public void init() throws ServletException {
		
		//log.info("Enter MenuInitServlet init");
		try {
			Class clz = Class.forName("com.souvc.weixin.main.MenuManager");
			Object obj = clz.newInstance();
			Method mtd = clz.getDeclaredMethod("main", String[].class);
			mtd.invoke(obj, (Object)null);
		} catch (Exception e) {
			// TODO: handle exception
			log.error(e.toString());
		}
		
	}
}
