package com.souvc.weixin.service;

import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.souvc.weixin.message.resp.TextMessage;
import com.souvc.weixin.util.MessageUtil;

public class CoreService {
    /**
     * ����΢�ŷ���������
     * @param request
     * @return xml
     */
    public static String processRequest(HttpServletRequest request) {
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

            // �ظ��ı���Ϣ
            TextMessage textMessage = new TextMessage();
            textMessage.setToUserName(fromUserName);
            textMessage.setFromUserName(toUserName);
            textMessage.setCreateTime(new Date().getTime());
            textMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_TEXT);

            switch (msgType) {
			case MessageUtil.REQ_MESSAGE_TYPE_TEXT:
				respContent = "�����͵����ı���Ϣ��";
				break;
			case MessageUtil.REQ_MESSAGE_TYPE_IMAGE:
				respContent = "�����͵���ͼƬ��Ϣ��";
				break;
			case MessageUtil.REQ_MESSAGE_TYPE_VOICE:
				respContent = "�����͵���������Ϣ��";
				break;
			case MessageUtil.REQ_MESSAGE_TYPE_VIDEO:
				respContent = "�����͵�����Ƶ��Ϣ��";
				break;
			case MessageUtil.REQ_MESSAGE_TYPE_SHORTVIDEO:
				respContent = "�����͵���С��Ƶ��Ϣ��";
				break;
			case MessageUtil.REQ_MESSAGE_TYPE_LOCATION:
				respContent = "�����͵��ǵ���λ����Ϣ��";
				break;
			case MessageUtil.REQ_MESSAGE_TYPE_LINK:
				respContent = "�����͵���������Ϣ��";
				break;
			case MessageUtil.REQ_MESSAGE_TYPE_EVENT:
				// �¼�����
                String eventType = requestMap.get("Event");
				switch (eventType) {
				case MessageUtil.EVENT_TYPE_SUBSCRIBE:
					respContent = "лл���Ĺ�ע��";
					break;
				case MessageUtil.EVENT_TYPE_UNSUBSCRIBE:
					// TODO ȡ�����ĺ��û��������յ������˺ŷ��͵���Ϣ����˲���Ҫ�ظ�
					break;
				case MessageUtil.EVENT_TYPE_SCAN:
					// TODO ����ɨ���������ά���¼�
					break;
				case MessageUtil.EVENT_TYPE_LOCATION:
					// TODO �����ϱ�����λ���¼�
					break;
				case MessageUtil.EVENT_TYPE_CLICK:
					// TODO ����˵�����¼�
					respContent = "������¼�";
					break;
				case MessageUtil.EVENT_TYPE_VIEW:
					// TODO ����˵�����¼�
					respContent = "�����ת�¼�";
					break;
				default:
					break;
				}
				break;
			default:
				respContent = "δ֪����Ϣ���ͣ�";
				break;
			}

            // �����ı���Ϣ������
            textMessage.setContent(respContent);
            // ���ı���Ϣ����ת����xml
            respXml = MessageUtil.messageToXml(textMessage);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return respXml;
    }
}
