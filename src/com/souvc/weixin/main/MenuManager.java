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
        // �������û�Ψһƾ֤
        String appId = "wx0c521940064c1f5f";
        // �������û�Ψһƾ֤��Կ
        String appSecret = "0fdc248fd764ad1c963b72b312eb335d";

        // ���ýӿڻ�ȡaccess_token
        Token at = CommonUtil.getToken(appId, appSecret);

        if (null != at) {
            // ���ýӿڴ����˵�
            int result = CommonUtil.createMenu(getMenu(), at.getAccessToken());

            // �жϲ˵��������
            if (0 == result)
                log.info("�˵������ɹ���");
            else
                log.info("�˵�����ʧ�ܣ������룺" + result);
        }
        else {
        	log.error("��ȡTokenʧ��");
        }
    }

    /**
     * ��װ�˵�����
     * 
     * @return
     */
    private static Menu getMenu() {

        CommonButton btn31 = new CommonButton();
        btn31.setName("ʹ��˵��");
        btn31.setType("click");
        btn31.setKey("31");

        CommonButton btn32 = new CommonButton();
        btn32.setName("��ϵ��");
        btn32.setType("click");
        btn32.setKey("32");

        CommonButton btn33 = new CommonButton();
        btn33.setName("����뽨��");
        btn33.setType("click");
        btn33.setKey("33");

        
        /**
         * ΢�ţ�  mainBtn1,mainBtn2,mainBtn3�ײ�������һ���˵���
         */
        //String url_driver = EncodeUrl("http://47.106.206.255/sfc/DriverPage");
        //String final_url_driver = CommonUtil.GenSnsapiBaseUrl(appid,url_driver);
        //System.out.println(final_url_driver);
        ViewButton mainBtn1 = new ViewButton();
        mainBtn1.setName("����˾��");
        mainBtn1.setType("view");
        //mainBtn1.setUrl(final_url_driver);
        mainBtn1.setUrl("http://47.106.206.255/sfc/DriverPage");
        //һ������4���Ӳ˵�
        //mainBtn1.setSub_button(new CommonButton[] { btn11, btn12, btn13, btn14 });

        //String url_pass = EncodeUrl("http://47.106.206.255/sfc/PassengerPage");
        //String final_url_pass = CommonUtil.GenSnsapiBaseUrl(appid,url_pass);
        //System.out.println(final_url_pass);
        ViewButton mainBtn2 = new ViewButton();
        mainBtn2.setName("���ǳ˿�");
        mainBtn2.setType("view");
        //mainBtn2.setUrl(final_url_pass);
        mainBtn2.setUrl("http://47.106.206.255/sfc/PassengerPage");
        //mainBtn2.setSub_button(new CommonButton[] { btn21, btn22, btn23, btn24, btn25 });

        
        ComplexButton mainBtn3 = new ComplexButton();
        mainBtn3.setName("�������");
        mainBtn3.setSub_button(new CommonButton[] { btn31, btn32, btn33 });

        
        /**
         * ��װ�����˵�
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
