package com.souvc.weixin.pojo;

/*��ȡ�ز��б����ʵ����**/
public class Material {
    
    private String title;//ͼ����Ϣ�ı���
    
    private String thumb_media_id;//ͼ����Ϣ�ķ���ͼƬ�ز�id������������mediaID��
    
    private String author;//����
    
    private String digest;//ͼ����Ϣ��ժҪ�����е�ͼ����Ϣ����ժҪ����ͼ�Ĵ˴�Ϊ��
    
    private String content;//ͼ����Ϣ�ľ������ݣ�֧��HTML��ǩ����������2���ַ���С��1M���Ҵ˴���ȥ��JS
    
    private String url;//ͼ��ҳ��URL�����ߣ�����ȡ���б���ͼƬ�ز��б�ʱ�����ֶ���ͼƬ��URL
    
    private int show_cover_pic;//�Ƿ���ʾ���棬0Ϊfalse��������ʾ��1Ϊtrue������ʾ
    
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getThumb_media_id() {
        return thumb_media_id;
    }
    public void setThumb_media_id(String thumb_media_id) {
        this.thumb_media_id = thumb_media_id;
    }
    public String getAuthor() {
        return author;
    }
    public void setAuthor(String author) {
        this.author = author;
    }
    public String getDigest() {
        return digest;
    }
    public void setDigest(String digest) {
        this.digest = digest;
    }
    public String getContent() {
        return content;
    }
    public void setContent(String content) {
        this.content = content;
    }
    public String getUrl() {
        return url;
    }
    public void setUrl(String url) {
        this.url = url;
    }
    public int getShow_cover_pic() {
        return show_cover_pic;
    }
    public void setShow_cover_pic(int show_cover_pic) {
        this.show_cover_pic = show_cover_pic;
    }
    
}
