package com.souvc.weixin.main;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.souvc.weixin.menu.Button;
import com.souvc.weixin.menu.CommonButton;
import com.souvc.weixin.menu.ComplexButton;
import com.souvc.weixin.menu.Menu;
import com.souvc.weixin.menu.ViewButton;
import com.souvc.weixin.pojo.Token;
import com.souvc.weixin.util.CommonUtil;
import com.thoughtworks.xstream.converters.reflection.SelfStreamingInstanceChecker;

public class MenuManager {
	
	public final static String appid = "wx0c521940064c1f5f";
	
	private static Logger log = LoggerFactory.getLogger(MenuManager.class);

    public static void main(String[] args) {
    	
    	//log.info("Enter MenuManager main");
        // 第三方用户唯一凭证
        String appId = "wx0c521940064c1f5f";
        // 第三方用户唯一凭证密钥
        String appSecret = "0fdc248fd764ad1c963b72b312eb335d";

        // 调用接口获取access_token
        Token at = CommonUtil.getToken(appId, appSecret);

        if (null != at) {
            // 调用接口创建菜单
            int result = CommonUtil.createMenu(getMenu(), at.getAccessToken());

            // 判断菜单创建结果
            if (0 == result)
                log.info("菜单创建成功！");
            else
                log.info("菜单创建失败，错误码：" + result);
        }
        else {
        	log.error("获取Token失败");
        }
    }

    /**
     * 组装菜单数据
     * 
     * @return
     */
    private static Menu getMenu() {

        CommonButton btn31 = new CommonButton();
        btn31.setName("使用说明");
        btn31.setType("click");
        btn31.setKey("31");

        CommonButton btn32 = new CommonButton();
        btn32.setName("联系我");
        btn32.setType("click");
        btn32.setKey("32");

        CommonButton btn33 = new CommonButton();
        btn33.setName("意见与建议");
        btn33.setType("click");
        btn33.setKey("33");

        
        /**
         * 微信：  mainBtn1,mainBtn2,mainBtn3底部的三个一级菜单。
         */
        //String url_driver = EncodeUrl("http://47.106.206.255/sfc/DriverPage");
        //String final_url_driver = CommonUtil.GenSnsapiBaseUrl(appid,url_driver);
        //System.out.println(final_url_driver);
        ViewButton mainBtn1 = new ViewButton();
        mainBtn1.setName("我是司机");
        mainBtn1.setType("view");
        //mainBtn1.setUrl(final_url_driver);
        mainBtn1.setUrl("http://47.106.206.255/sfc/DriverPage");
        //一级下有4个子菜单
        //mainBtn1.setSub_button(new CommonButton[] { btn11, btn12, btn13, btn14 });

        //String url_pass = EncodeUrl("http://47.106.206.255/sfc/PassengerPage");
        //String final_url_pass = CommonUtil.GenSnsapiBaseUrl(appid,url_pass);
        //System.out.println(final_url_pass);
        ViewButton mainBtn2 = new ViewButton();
        mainBtn2.setName("我是乘客");
        mainBtn2.setType("view");
        //mainBtn2.setUrl(final_url_pass);
        mainBtn2.setUrl("http://47.106.206.255/sfc/PassengerPage");
        //mainBtn2.setSub_button(new CommonButton[] { btn21, btn22, btn23, btn24, btn25 });

        
        ComplexButton mainBtn3 = new ComplexButton();
        mainBtn3.setName("更多服务");
        mainBtn3.setSub_button(new CommonButton[] { btn31, btn32, btn33 });

        
        /**
         * 封装整个菜单
         */
        Menu menu = new Menu();
        menu.setButton(new Button[] { mainBtn1, mainBtn2, mainBtn3 });

        return menu;
    }
    
    private static String EncodeUrl(String input){
    	String url_pass = new String();
    	try {
    		url_pass = URLEncoder.encode(input,"utf-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return url_pass;
    }
}
