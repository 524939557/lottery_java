package com.homeene.req;


public class ReqRedPack {
	private String nonce_str;
	private String sign;
    private String mch_billno;
    private String mch_id;
    private String wxappid;
    private String send_name;
    private String re_openid;
    private Integer total_amount;
    private Integer total_num;
    private String wishing;
    private String client_ip;
    private String act_name;
    private String remark;


	
	
	public String getNonce_str() {
		return nonce_str;
	}



	
	public void setNonce_str(String nonce_str) {
		this.nonce_str = nonce_str;
	}



	
	public String getSign() {
		return sign;
	}



	
	public void setSign(String sign) {
		this.sign = sign;
	}



	
	public String getMch_billno() {
		return mch_billno;
	}



	
	public void setMch_billno(String mch_billno) {
		this.mch_billno = mch_billno;
	}



	
	public String getMch_id() {
		return mch_id;
	}



	
	public void setMch_id(String mch_id) {
		this.mch_id = mch_id;
	}



	
	public String getWxappid() {
		return wxappid;
	}



	
	public void setWxappid(String wxappid) {
		this.wxappid = wxappid;
	}



	
	public String getSend_name() {
		return send_name;
	}



	
	public void setSend_name(String send_name) {
		this.send_name = send_name;
	}



	
	public String getRe_openid() {
		return re_openid;
	}



	
	public void setRe_openid(String re_openid) {
		this.re_openid = re_openid;
	}



	
	public Integer getTotal_amount() {
		return total_amount;
	}



	
	public void setTotal_amount(Integer total_amount) {
		this.total_amount = total_amount;
	}



	
	public Integer getTotal_num() {
		return total_num;
	}



	
	public void setTotal_num(Integer total_num) {
		this.total_num = total_num;
	}



	
	public String getWishing() {
		return wishing;
	}



	
	public void setWishing(String wishing) {
		this.wishing = wishing;
	}



	
	public String getClient_ip() {
		return client_ip;
	}



	
	public void setClient_ip(String client_ip) {
		this.client_ip = client_ip;
	}



	
	public String getAct_name() {
		return act_name;
	}



	
	public void setAct_name(String act_name) {
		this.act_name = act_name;
	}



	
	public String getRemark() {
		return remark;
	}



	
	public void setRemark(String remark) {
		this.remark = remark;
	}


	public ReqRedPack(String nonce_str, String mch_billno, String mch_id, String wxappid, String send_name,
			String re_openid, Integer total_amount, Integer total_num, String wishing, String client_ip,
			String act_name, String remark) {
		super();
		this.nonce_str = nonce_str;
		this.mch_billno = mch_billno;
		this.mch_id = mch_id;
		this.wxappid = wxappid;
		this.send_name = send_name;
		this.re_openid = re_openid;
		this.total_amount = total_amount;
		this.total_num = total_num;
		this.wishing = wishing;
		this.client_ip = client_ip;
		this.act_name = act_name;
		this.remark = remark;
	}

	public ReqRedPack() {
		super();
		// TODO Auto-generated constructor stub
	}




	public String toString(){
        String result=
                "<xml>" +
                    "<nonce_str><![CDATA["+this.nonce_str+"]]></nonce_str>" +
                    "<sign><![CDATA["+this.sign+"]]></sign>" +
                    "<mch_billno><![CDATA["+this.mch_billno+"]]></mch_billno>" +
                    "<mch_id><![CDATA["+this.mch_id+"]]></mch_id>" +
                    "<wxappid><![CDATA["+this.wxappid+"]]></wxappid>" +
                    "<send_name><![CDATA["+this.send_name+"]]></send_name>" +
                    "<re_openid><![CDATA["+this.re_openid+"]]></re_openid>" +
                    "<total_amount><![CDATA["+this.total_amount+"]]></total_amount>" +
                    "<total_num><![CDATA[1]]></total_num>" +
                    "<wishing><![CDATA["+this.wishing+"]]></wishing>" +
                    "<client_ip><![CDATA["+this.client_ip+"]]></client_ip>" +
                    "<act_name><![CDATA["+this.act_name+"]]></act_name>" +
                    "<remark><![CDATA["+this.remark+"]]></remark>" +
                "</xml>";
        return result;
    }

}
