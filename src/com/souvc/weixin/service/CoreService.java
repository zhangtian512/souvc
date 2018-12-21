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
	// 第三方用户唯一凭证
    public static String appid = "wx0c521940064c1f5f";
    // 第三方用户唯一凭证密钥
    public static String appsecret = "0fdc248fd764ad1c963b72b312eb335d";
    /**
     * 处理微信发来的请求
     * @param request
     * @return xml
     */
    public static String processRequest(HttpServletRequest request) {
    	
    	log.info("enter processRequest");
        // xml格式的消息数据
        String respXml = null;
        // 默认返回的文本消息内容
        String respContent = "未知的消息类型！";
        try {
            // 调用parseXml方法解析请求消息
            Map<String, String> requestMap = MessageUtil.parseXml(request);
            // 发送方帐号
            String fromUserName = requestMap.get("FromUserName");
            // 开发者微信号
            String toUserName = requestMap.get("ToUserName");
            // 消息类型
            String msgType = requestMap.get("MsgType");
            //消息内容
            String msgContent = requestMap.get("Content");

            // 回复文本消息
            TextMessage textMessage = new TextMessage();
            textMessage.setToUserName(fromUserName);
            textMessage.setFromUserName(toUserName);
            textMessage.setCreateTime(new Date().getTime());
            textMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_TEXT);
            //回复图文消息
            NewsMessage newsMessage = new NewsMessage();
            newsMessage.setToUserName(fromUserName);
            newsMessage.setFromUserName(toUserName);
            newsMessage.setCreateTime(new Date().getTime());
            newsMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_NEWS);

            log.info("processRequest msgType:"+msgType);
            log.info("processRequest msgContent:"+msgContent);
            switch (msgType) {
			case MessageUtil.REQ_MESSAGE_TYPE_TEXT:
				if(msgContent.equals("发布")){
					List<Article> ar_list = new ArrayList<Article>();
					
					Article articles = new Article();
					articles.setTitle("主页");
					articles.setDescription("请点击图片进入主页:");
					articles.setUrl("http://47.106.206.255/e_sfc/user/MainPage");
					articles.setPicUrl("http://47.106.206.255/souvc/img/passenger.jpg");

					ar_list.add(articles);
					newsMessage.setArticles(ar_list);
					newsMessage.setArticleCount(ar_list.size());
					
					respContent = "图文消息";
				}
				else {
					respContent = "本公众号意在为西安高新区上班的广大公众提供一个平台，"
							+ "大家可以在这个平台上分享自己的上下班行程，"
							+ "可约短期顺风车，可约长期顺风车。\n"
							+ "由于资金有限，目前无法开通点击直接跳转至约车页面，"
							+ "请您先点击菜单中的：\n"
							+ "\"使用说明\"按钮\n"
							+"获取使用攻略\n或直接在对话框输入\n"
							+"\"发布\"\n"
							+"来获取主页链接，然后用手机浏览器打开即可\n";
				}
				
				break;
			case MessageUtil.REQ_MESSAGE_TYPE_IMAGE:
				respContent = "您发送的是图片消息！请输入\"发布\"";
				break;
			case MessageUtil.REQ_MESSAGE_TYPE_VOICE:
				respContent = "您发送的是语音消息！请输入\"发布\"";
				break;
			case MessageUtil.REQ_MESSAGE_TYPE_VIDEO:
				respContent = "您发送的是视频消息！请输入\"发布\"";
				break;
			case MessageUtil.REQ_MESSAGE_TYPE_SHORTVIDEO:
				respContent = "您发送的是小视频消息！请输入\"发布\"";
				break;
			case MessageUtil.REQ_MESSAGE_TYPE_LOCATION:
				respContent = "您发送的是地理位置消息！请输入\"发布\"";
				break;
			case MessageUtil.REQ_MESSAGE_TYPE_LINK:
				respContent = "您发送的是链接消息！请输入\"发布\"";
				break;
			case MessageUtil.REQ_MESSAGE_TYPE_EVENT:
				// 事件类型
                String eventType = requestMap.get("Event");
				switch (eventType) {
				case MessageUtil.EVENT_TYPE_SUBSCRIBE:
					log.info("关注者的openid："+fromUserName);
					respContent = "本公众号意在为西安高新区上班的广大公众提供一个平台，"
							+ "大家可以在这个平台上分享自己的上下班行程，"
							+ "可约短期顺风车，可约长期顺风车。\n"
							+ "由于资金有限，目前无法开通点击直接跳转至约车页面，"
							+ "请您先点击菜单中的：\n"
							+ "\"使用说明\"按钮\n"
							+"获取使用攻略\n或直接在对话框输入\n"
							+"\"发布\"\n"
							+"来获取主页链接，然后用手机浏览器打开即可\n";
					break;
				case MessageUtil.EVENT_TYPE_UNSUBSCRIBE:
					// TODO 取消订阅后用户不会再收到公众账号发送的消息，因此不需要回复
					System.out.println("取消关注者的openid："+fromUserName);
					break;
				case MessageUtil.EVENT_TYPE_SCAN:
					// TODO 处理扫描带参数二维码事件
					
					break;
				case MessageUtil.EVENT_TYPE_LOCATION:
					// TODO 处理上报地理位置事件
					break;
				case MessageUtil.EVENT_TYPE_CLICK:
					// TODO 处理菜单点击事件
					//respContent = "鼠标点击事件";
					break;
				case MessageUtil.EVENT_TYPE_VIEW:
					// TODO 处理菜单点击事件
					//respContent = "鼠标跳转事件";
					break;
				default:
					break;
				}
				break;
			default:
				respContent = "未知的消息类型";
				break;
			}
            
            if(respContent.equals("图文消息")){
            	//将图文消息转换成XML
            	respXml = MessageUtil.messageToXml(newsMessage);
            	respXml = XMLNodeOpetation.DelCDATAinXML(respXml, "CreateTime");
            	respXml = XMLNodeOpetation.DelCDATAinXML(respXml, "ArticleCount");
            }
            else if(respContent.equals("未知的消息类型")){
            	return "error";
            }
            else{
            	// 设置文本消息的内容
                textMessage.setContent(respContent);
                // 将文本消息对象转换成xml
                respXml = MessageUtil.messageToXml(textMessage);
            }
            

        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return respXml;
    }
}
