package com.souvc.weixin.util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ConnectException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;

import net.sf.json.JSONException;
import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.souvc.weixin.menu.Menu;
import com.souvc.weixin.pojo.Token;
import com.souvc.weixin.pojo.WeixinUserInfo;

/**
* ����: CommonUtil </br>
* ����: ͨ�ù����� </br>
* ������Ա�� souvc </br>
* ����ʱ�䣺  2015-9-30 </br>
* �����汾��V1.0  </br>
 */
public class CommonUtil {
    private static Logger log = LoggerFactory.getLogger(CommonUtil.class);

    // ƾ֤��ȡ��GET��
    public final static String token_url =
    		"https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET";

    // �˵�������POST�� ��100����/�죩
    public static String menu_create_url =
    		"https://api.weixin.qq.com/cgi-bin/menu/create?access_token=ACCESS_TOKEN";
    
    //�����û���ϸ��Ϣ
    public static String user_info_get_url = 
    		"https://api.weixin.qq.com/cgi-bin/user/info?access_token=ACCESS_TOKEN&openid=OPENID&lang=zh_CN";
    
    //��תҳ���ȡ�û�openid
    public static String snsapi_base_request_url = 
    		"https://open.weixin.qq.com/connect/oauth2/authorize?appid=APPID&redirect_uri=REQ_URL&response_type=code&scope=snsapi_userinfo&state=123#wechat_redirect";
    /**
     * �����˵�
     * 
     * @param menu �˵�ʵ��
     * @param accessToken ��Ч��access_token
     * @return 0��ʾ�ɹ�������ֵ��ʾʧ��
     */
    public static int createMenu(Menu menu, String accessToken) {
        int result = 0;
        // ƴװ�����˵���url
        String url = menu_create_url.replace("ACCESS_TOKEN", accessToken);
        // ���˵�����ת����json�ַ���
        String jsonMenu = JSONObject.fromObject(menu).toString();
        log.debug(jsonMenu);
        // ���ýӿڴ����˵�
        JSONObject jsonObject = httpsRequest(url, "POST", jsonMenu);
        if (null != jsonObject) {
            if (0 != jsonObject.getInt("errcode")) {
                result = jsonObject.getInt("errcode");
                log.error("�����˵�ʧ�� errcode:{} errmsg:{}", jsonObject.getInt("errcode"), jsonObject.getString("errmsg"));
            }
        }

        return result;
    }
    
    /**
     * ����https����
     * 
     * @param requestUrl �����ַ
     * @param requestMethod ����ʽ��GET��POST��
     * @param outputStr �ύ������
     * @return JSONObject(ͨ��JSONObject.get(key)�ķ�ʽ��ȡjson���������ֵ)
     */
    public static JSONObject httpsRequest(String requestUrl, String requestMethod, String outputStr) {
        JSONObject jsonObject = null;
        try {
            // ����SSLContext���󣬲�ʹ������ָ�������ι�������ʼ��
            TrustManager[] tm = { new MyX509TrustManager() };
            SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
            sslContext.init(null, tm, new java.security.SecureRandom());
            // ������SSLContext�����еõ�SSLSocketFactory����
            SSLSocketFactory ssf = sslContext.getSocketFactory();

            URL url = new URL(requestUrl);
            HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
            conn.setSSLSocketFactory(ssf);
            
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setUseCaches(false);
            // ��������ʽ��GET/POST��
            conn.setRequestMethod(requestMethod);

            // ��outputStr��Ϊnullʱ�������д����
            if (null != outputStr) {
                OutputStream outputStream = conn.getOutputStream();
                // ע������ʽ
                outputStream.write(outputStr.getBytes("UTF-8"));
                outputStream.close();
            }

            // ����������ȡ��������
            InputStream inputStream = conn.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String str = null;
            StringBuffer buffer = new StringBuffer();
            while ((str = bufferedReader.readLine()) != null) {
                buffer.append(str);
            }

            // �ͷ���Դ
            bufferedReader.close();
            inputStreamReader.close();
            inputStream.close();
            inputStream = null;
            conn.disconnect();
            jsonObject = JSONObject.fromObject(buffer.toString());
        } catch (ConnectException ce) {
            log.error("���ӳ�ʱ��{}", ce);
        } catch (Exception e) {
            log.error("https�����쳣��{}", e);
        }
        return jsonObject;
    }

    /**
     * ��ȡ�ӿڷ���ƾ֤
     * 
     * @param appid ƾ֤
     * @param appsecret ��Կ
     * @return
     */
    public static Token getToken(String appid, String appsecret) {
        Token token = null;
        String requestUrl = token_url.replace("APPID", appid).replace("APPSECRET", appsecret);
        // ����GET�����ȡƾ֤
        JSONObject jsonObject = httpsRequest(requestUrl, "GET", null);

        if (null != jsonObject) {
            try {
                token = new Token();
                token.setAccessToken(jsonObject.getString("access_token"));
                token.setExpiresIn(jsonObject.getInt("expires_in"));
            } catch (JSONException e) {
                token = null;
                // ��ȡtokenʧ��
                log.error("��ȡtokenʧ�� errcode:{} errmsg:{}", jsonObject.getInt("errcode"), jsonObject.getString("errmsg"));
            }
        }
        return token;
    }
    
    /**
     * ��ȡ�û���Ϣ
     * 
     * @param accessToken �ӿڷ���ƾ֤
     * @param openId �û���ʶ
     * @return WeixinUserInfo
     */
    public static WeixinUserInfo getUserInfo(String accessToken, String openId) {
    	
    	WeixinUserInfo weixinUserInfo = null;
    	
    	// ƴ�������ַ
    	String url = user_info_get_url.replace("ACCESS_TOKEN", accessToken).replace("OPENID", openId);
    	
    	// ��ȡ�û���Ϣ
        JSONObject jsonObject = CommonUtil.httpsRequest(url, "GET", null);
        
        if (null != jsonObject) {
            try {
                weixinUserInfo = new WeixinUserInfo();
                // �û��ı�ʶ
                weixinUserInfo.setOpenId(jsonObject.getString("openid"));
                // ��ע״̬��1�ǹ�ע��0��δ��ע����δ��עʱ��ȡ����������Ϣ
                weixinUserInfo.setSubscribe(jsonObject.getInt("subscribe"));
                // �û���עʱ��
                weixinUserInfo.setSubscribeTime(jsonObject.getString("subscribe_time"));
                // �ǳ�
                weixinUserInfo.setNickname(jsonObject.getString("nickname"));
                // �û����Ա�1�����ԣ�2��Ů�ԣ�0��δ֪��
                weixinUserInfo.setSex(jsonObject.getInt("sex"));
                // �û����ڹ���
                weixinUserInfo.setCountry(jsonObject.getString("country"));
                // �û�����ʡ��
                weixinUserInfo.setProvince(jsonObject.getString("province"));
                // �û����ڳ���
                weixinUserInfo.setCity(jsonObject.getString("city"));
                // �û������ԣ���������Ϊzh_CN
                weixinUserInfo.setLanguage(jsonObject.getString("language"));
                // �û�ͷ��
                weixinUserInfo.setHeadImgUrl(jsonObject.getString("headimgurl"));
            } catch (Exception e) {
                if (0 == weixinUserInfo.getSubscribe()) {
                    log.error("�û�{}��ȡ����ע", weixinUserInfo.getOpenId());
                } else {
                    int errorCode = jsonObject.getInt("errcode");
                    String errorMsg = jsonObject.getString("errmsg");
                    log.error("��ȡ�û���Ϣʧ�� errcode:{} errmsg:{}", errorCode, errorMsg);
                }
            }
        }
        
        return weixinUserInfo;
    }
    
    public static String GenSnsapiBaseUrl(String appid,String req_url){
    	return snsapi_base_request_url.replace("APPID", appid).replace("REQ_URL", req_url);
    }
}
