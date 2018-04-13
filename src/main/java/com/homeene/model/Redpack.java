package com.homeene.model;

import java.util.Date;

public class Redpack {
    private Integer id;

    private String mchBillno;

    private String mchId;

    private String wxappid;

    private String reOpenid;

    private Integer totalAmount;

    private Integer totalNum;
    
    private Date createTime;

    private String returnCode;
    
    private Integer status;
    
    private Integer myGameId;
    
    private Integer share;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMchBillno() {
        return mchBillno;
    }

    public void setMchBillno(String mchBillno) {
        this.mchBillno = mchBillno;
    }

    public String getMchId() {
        return mchId;
    }

    public void setMchId(String mchId) {
        this.mchId = mchId;
    }

    public String getWxappid() {
        return wxappid;
    }

    public void setWxappid(String wxappid) {
        this.wxappid = wxappid;
    }

    public String getReOpenid() {
        return reOpenid;
    }

    public void setReOpenid(String reOpenid) {
        this.reOpenid = reOpenid;
    }

    public Integer getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Integer totalAmount) {
        this.totalAmount = totalAmount;
    }

    public Integer getTotalNum() {
        return totalNum;
    }

    public void setTotalNum(Integer totalNum) {
        this.totalNum = totalNum;
    }

    public String getReturnCode() {
        return returnCode;
    }

    public void setReturnCode(String returnCode) {
        this.returnCode = returnCode;
    }

	
	public Date getCreateTime() {
		return createTime;
	}

	
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	
	public Integer getStatus() {
		return status;
	}

	
	public void setStatus(Integer status) {
		this.status = status;
	}

	
	public Integer getMyGameId() {
		return myGameId;
	}

	
	public void setMyGameId(Integer myGameId) {
		this.myGameId = myGameId;
	}

	
	public Integer getShare() {
		return share;
	}

	
	public void setShare(Integer share) {
		this.share = share;
	}
    
    
}