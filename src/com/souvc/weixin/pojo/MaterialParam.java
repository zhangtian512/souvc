package com.souvc.weixin.pojo;

/*��ȡ�ز��б���ýӿ�����Ҫ�Ĳ���ʵ����**/
public class MaterialParam {
    
    private String type;//�زĵ����ͣ�ͼƬ��image������Ƶ��video�������� ��voice����ͼ�ģ�news��
    
    private int offset;//��ȫ���زĵĸ�ƫ��λ�ÿ�ʼ���أ�0��ʾ�ӵ�һ���ز� ����
    
    private int count;//�����زĵ�������ȡֵ��1��20֮��

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
    
}
