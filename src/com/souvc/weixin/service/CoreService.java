package com.souvc.weixin.service;

import java.util.List;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.souvc.weixin.message.req.ImageMessage;
import com.souvc.weixin.message.resp.Article;
import com.souvc.weixin.message.resp.NewsMessage;
import com.souvc.weixin.message.resp.TextMessage;
import com.souvc.weixin.pojo.Material;
import com.souvc.weixin.pojo.Token;
import com.souvc.weixin.servlet.CoreServlet;
import com.souvc.weixin.util.CommonUtil;
import com.souvc.weixin.util.MessageUtil;
import com.souvc.weixin.util.XMLNodeOpetation;

public class CoreService {
	
	private static Logger log = LoggerFactory.getLogger(CoreService.class);
	// �������û�Ψһƾ֤
    public static String appid = "wx0c521940064c1f5f";
    // �������û�Ψһƾ֤��Կ
    public static String appsecret = "0fdc248fd764ad1c963b72b312eb335d";
    /**
     * ����΢�ŷ���������
     * @param request
     * @return xml
     */
    public static String processRequest(HttpServletRequest request) {
    	
    	log.info("enter processRequest");
        // xml��ʽ����Ϣ����
        String respXml = null;
        // Ĭ�Ϸ��ص��ı���Ϣ����
        String respContent = "δ֪����Ϣ���ͣ�";
        try {
            // ����parseXml��������������Ϣ
            Map<String, String> requestMap = MessageUtil.parseXml(request);
            // ���ͷ��ʺ�
            String fromUserName = requestMap.get("FromUserName");
            // ������΢�ź�
            String toUserName = requestMap.get("ToUserName");
            // ��Ϣ����
            String msgType = requestMap.get("MsgType");
            //��Ϣ����
            String msgContent = requestMap.get("Content");

            // �ظ��ı���Ϣ
            TextMessage textMessage = new TextMessage();
            textMessage.setToUserName(fromUserName);
            textMessage.setFromUserName(toUserName);
            textMessage.setCreateTime(new Date().getTime());
            textMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_TEXT);
            //�ظ�ͼ����Ϣ
            NewsMessage newsMessage = new NewsMessage();
            newsMessage.setToUserName(fromUserName);
            newsMessage.setFromUserName(toUserName);
            newsMessage.setCreateTime(new Date().getTime());
            newsMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_NEWS);

            log.info("processRequest msgType:"+msgType);
            log.info("processRequest msgContent:"+msgContent);
            switch (msgType) {
			case MessageUtil.REQ_MESSAGE_TYPE_TEXT:
				if(msgContent.equals("����")){
					List<Article> ar_list = new ArrayList<Article>();
					
					Article articles = new Article();
					articles.setTitle("��ҳ");
					articles.setDescription("����ͼƬ������ҳ:");
					articles.setUrl("http://47.106.206.255/e_sfc/user/MainPage");
					articles.setPicUrl("http://47.106.206.255/souvc/img/passenger.jpg");

					ar_list.add(articles);
					newsMessage.setArticles(ar_list);
					newsMessage.setArticleCount(ar_list.size());
					
					respContent = "ͼ����Ϣ";
				}
				else {
					respContent = "�����ں�����Ϊ�����������ϰ�Ĺ�����ṩһ��ƽ̨��"
							+ "��ҿ��������ƽ̨�Ϸ����Լ������°��г̣�"
							+ "��Լ����˳�糵����Լ����˳�糵��\n"
							+ "�����ʽ����ޣ�Ŀǰ�޷���ͨ���ֱ����ת��Լ��ҳ�棬"
							+ "�����ȵ���˵��еģ�\n"
							+ "\"ʹ��˵��\"��ť\n"
							+"��ȡʹ�ù���\n��ֱ���ڶԻ�������\n"
							+"\"����\"\n"
							+"����ȡ��ҳ���ӣ�Ȼ�����ֻ�������򿪼���\n";
				}
				
				break;
			case MessageUtil.REQ_MESSAGE_TYPE_IMAGE:
				respContent = "�����͵���ͼƬ��Ϣ��������\"����\"";
				break;
			case MessageUtil.REQ_MESSAGE_TYPE_VOICE:
				respContent = "�����͵���������Ϣ��������\"����\"";
				break;
			case MessageUtil.REQ_MESSAGE_TYPE_VIDEO:
				respContent = "�����͵�����Ƶ��Ϣ��������\"����\"";
				break;
			case MessageUtil.REQ_MESSAGE_TYPE_SHORTVIDEO:
				respContent = "�����͵���С��Ƶ��Ϣ��������\"����\"";
				break;
			case MessageUtil.REQ_MESSAGE_TYPE_LOCATION:
				respContent = "�����͵��ǵ���λ����Ϣ��������\"����\"";
				break;
			case MessageUtil.REQ_MESSAGE_TYPE_LINK:
				respContent = "�����͵���������Ϣ��������\"����\"";
				break;
			case MessageUtil.REQ_MESSAGE_TYPE_EVENT:
				// �¼�����
                String eventType = requestMap.get("Event");
				switch (eventType) {
				case MessageUtil.EVENT_TYPE_SUBSCRIBE:
					log.info("��ע�ߵ�openid��"+fromUserName);
					respContent = "�����ں�����Ϊ�����������ϰ�Ĺ�����ṩһ��ƽ̨��"
							+ "��ҿ��������ƽ̨�Ϸ����Լ������°��г̣�"
							+ "��Լ����˳�糵����Լ����˳�糵��\n"
							+ "�����ʽ����ޣ�Ŀǰ�޷���ͨ���ֱ����ת��Լ��ҳ�棬"
							+ "�����ȵ���˵��еģ�\n"
							+ "\"ʹ��˵��\"��ť\n"
							+"��ȡʹ�ù���\n��ֱ���ڶԻ�������\n"
							+"\"����\"\n"
							+"����ȡ��ҳ���ӣ�Ȼ�����ֻ�������򿪼���\n";
					break;
				case MessageUtil.EVENT_TYPE_UNSUBSCRIBE:
					// TODO ȡ�����ĺ��û��������յ������˺ŷ��͵���Ϣ����˲���Ҫ�ظ�
					System.out.println("ȡ����ע�ߵ�openid��"+fromUserName);
					break;
				case MessageUtil.EVENT_TYPE_SCAN:
					// TODO ����ɨ���������ά���¼�
					
					break;
				case MessageUtil.EVENT_TYPE_LOCATION:
					// TODO �����ϱ�����λ���¼�
					break;
				case MessageUtil.EVENT_TYPE_CLICK:
					// TODO ����˵�����¼�
					//respContent = "������¼�";
					break;
				case MessageUtil.EVENT_TYPE_VIEW:
					// TODO ����˵�����¼�
					//respContent = "�����ת�¼�";
					break;
				default:
					break;
				}
				break;
			default:
				respContent = "δ֪����Ϣ����";
				break;
			}
            
            if(respContent.equals("ͼ����Ϣ")){
            	//��ͼ����Ϣת����XML
            	respXml = MessageUtil.messageToXml(newsMessage);
            	respXml = XMLNodeOpetation.DelCDATAinXML(respXml, "CreateTime");
            	respXml = XMLNodeOpetation.DelCDATAinXML(respXml, "ArticleCount");
            }
            else if(respContent.equals("δ֪����Ϣ����")){
            	return "error";
            }
            else{
            	// �����ı���Ϣ������
                textMessage.setContent(respContent);
                // ���ı���Ϣ����ת����xml
                respXml = MessageUtil.messageToXml(textMessage);
            }
            

        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return respXml;
    }
}
