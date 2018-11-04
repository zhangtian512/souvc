package com.souvc.weixin.service;

import java.util.List;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.souvc.weixin.message.resp.Article;
import com.souvc.weixin.message.resp.NewsMessage;
import com.souvc.weixin.message.resp.TextMessage;
import com.souvc.weixin.util.MessageUtil;

public class CoreService {
    /**
     * 处理微信发来的请求
     * @param request
     * @return xml
     */
    public static String processRequest(HttpServletRequest request) {
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

            switch (msgType) {
			case MessageUtil.REQ_MESSAGE_TYPE_TEXT:
				if(msgContent.equals("乘客")){
					List<Article> ar_list = new ArrayList<Article>();
					
					Article articles = new Article();
					articles.setTitle("乘客页面说明");
					articles.setDescription("请点击图片进入乘客页面:");
					articles.setUrl("http://47.106.206.255/sfc/PassengerPage/"+fromUserName);
					articles.setPicUrl("http://47.106.206.255/souvc/img/test.png");
					
					Article articles2 = new Article();
					articles2.setTitle("跳转链接");
					articles2.setDescription("请点击进入乘客页面:");
					articles2.setUrl("http://47.106.206.255/sfc/PassengerPage/"+fromUserName);
					
					ar_list.add(articles);
					ar_list.add(articles2);
					newsMessage.setArticles(ar_list);
					newsMessage.setArticleCount(ar_list.size());
					
					respContent = "使用说明";
				}
				else if(msgContent.equals("司机")){
					List<Article> ar_list = new ArrayList<Article>();
					
					Article articles = new Article();
					articles.setTitle("司机页面");
					articles.setDescription("请点击图片进入司机页面:");
					articles.setUrl("http://47.106.206.255/sfc/DriverPage/"+fromUserName);
					articles.setPicUrl("http://47.106.206.255/souvc/img/test1.png");
					
					ar_list.add(articles);
					newsMessage.setArticles(ar_list);
					newsMessage.setArticleCount(ar_list.size());
					
					respContent = "使用说明";
				}
				else {
					List<Article> ar_list = new ArrayList<Article>();
					
					Article articles = new Article();
					articles.setTitle("使用说明");
					articles.setDescription("请阅读使用说明");
					articles.setPicUrl("http://47.106.206.255/souvc/img/test.png");
					articles.setUrl("http://47.106.206.255/sfc/PassengerPage/"+fromUserName);
					
					ar_list.add(articles);
					newsMessage.setArticles(ar_list);
					newsMessage.setArticleCount(ar_list.size());
					
					respContent = "使用说明";
				}
				
				break;
			case MessageUtil.REQ_MESSAGE_TYPE_IMAGE:
				respContent = "您发送的是图片消息！请输入\"司机\"或\"乘客\"的文字消息";
				break;
			case MessageUtil.REQ_MESSAGE_TYPE_VOICE:
				respContent = "您发送的是语音消息！请输入\"司机\"或\"乘客\"";
				break;
			case MessageUtil.REQ_MESSAGE_TYPE_VIDEO:
				respContent = "您发送的是视频消息！请输入\"司机\"或\"乘客\"";
				break;
			case MessageUtil.REQ_MESSAGE_TYPE_SHORTVIDEO:
				respContent = "您发送的是小视频消息！请输入\"司机\"或\"乘客\"";
				break;
			case MessageUtil.REQ_MESSAGE_TYPE_LOCATION:
				respContent = "您发送的是地理位置消息！请输入\"司机\"或\"乘客\"";
				break;
			case MessageUtil.REQ_MESSAGE_TYPE_LINK:
				respContent = "您发送的是链接消息！请输入\"司机\"或\"乘客\"";
				break;
			case MessageUtil.REQ_MESSAGE_TYPE_EVENT:
				// 事件类型
                String eventType = requestMap.get("Event");
				switch (eventType) {
				case MessageUtil.EVENT_TYPE_SUBSCRIBE:
					System.out.println("关注者的openid："+fromUserName);
						List<Article> ar_list = new ArrayList<Article>();
						
						Article articles = new Article();
						articles.setTitle("高新区顺风车使用说明");
						articles.setDescription("本公众号意在为西安高新区上班的广大公众提供一个平台，"
								+ "大家可以在这个平台上分享自己的上下班行程，"
								+ "可约短期顺风车，可约长期顺风车。\n"
								+ "由于资金有限，目前无法开通点击直接跳转至约车页面，"
								+ "大家可以在对话框输入\"乘客\"或者\"司机\"来获取链接，"
								+ "点击进入即可发布行程联系预约顺风车，如果关注的人多了，"
								+ "后续再为大家扩展更多方便大家使用的界面和功能。\n");
						articles.setPicUrl("http://47.106.206.255/souvc/img/explain2.png");
						
						ar_list.add(articles);
						newsMessage.setArticles(ar_list);
						newsMessage.setArticleCount(ar_list.size());
						
						respContent = "使用说明";
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
            
            if(respContent.equals("使用说明")){
            	//将图文消息转换成XML
            	respXml = MessageUtil.messageToXml(newsMessage);
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
