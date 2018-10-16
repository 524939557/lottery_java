package com.homeene.controller;

import java.io.UnsupportedEncodingException;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.homeene.model.MyAward;
import com.homeene.model.Redpack;
import com.homeene.req.ReqRedPack;
import com.homeene.common.Constants;
import com.homeene.model.Game;
import com.homeene.service.AwardService;
import com.homeene.service.CookieService;
import com.homeene.service.GameService;
import com.homeene.service.MyAwardService;
import com.homeene.service.RedpackService;
import com.homeene.utils.ClientCustomSSL;
import com.homeene.utils.DateUtils;
import com.homeene.utils.Dom4jUtils;
import com.homeene.utils.HttpUtils;
import com.homeene.utils.MsgDigest;
import com.homeene.utils.RandomUtils;
import com.riversoft.weixin.pay.redpack.RedPacks;
import com.riversoft.weixin.pay.redpack.bean.RedPackRequest;
import com.riversoft.weixin.pay.redpack.bean.RedPackResponse;

@RestController
@RequestMapping(value = "/lottery")
public class AwardController {

	@Resource
	private MyAwardService myAwardService;

	@Resource
	private AwardService awardService;

	@Resource
	private CookieService cookieservice;

	@Resource
	private GameService gameservice;
	@Resource
	private RedpackService redpackService;

	/**
	 * 我的所有卡
	 * @param req
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping(value = "/myCard", method = RequestMethod.GET)
	public List<MyAward> selectMyAward(HttpServletRequest req) throws UnsupportedEncodingException {
		Game u=cookieservice.cookieToUser(req);
		List<MyAward> result=myAwardService.selectMyAward(u.getUserid());
		result.forEach(ma->System.out.println(ma.getAwardId()+":"+ma.getTotal()));
		return result;
	}

	/**
	 * 我是否集齐21张卡
	 * @param req
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@GetMapping(value = "/mylottery")
	public Game getLotteryUser(HttpServletRequest req) throws UnsupportedEncodingException {
		Game u = cookieservice.cookieToUser(req);
		return gameservice.selectLotteryByUserId(u.getId());
	}
	
	/**
	 * 发红包 
	 * @throws Exception 
	 */
	

    @GetMapping("/sendRedPack")
    public Map<String, Object> sendRedPack(HttpServletRequest request, HttpServletResponse response) throws Exception{
    	Game u = cookieservice.cookieToUser(request);
    	Integer redpackAmount = (int) (RandomUtils.getRandom() * 100);
        Integer totalAmount=redpackService.getTotalAmount();
        Integer amount = redpackAmount + totalAmount;
        boolean fundReceived=false;
        if(amount > Constants.TotalAmount && redpackAmount < Constants.TotalAmount){
        	redpackAmount= Constants.TotalAmount - redpackAmount;
        }
        Redpack red=redpackService.selectByOpenId(u.getUserid());
        boolean checkday=false;
        if(red!=null) {
        	checkday=DateUtils.checkDay(red.getCreateTime());
        }
        if (u.getCollect() == 1 && u.getActive() == true && checkday) {
//            String url = "https://api.mch.weixin.qq.com/mmpaymkttransfers/sendredpack";
            String client_ip = HttpUtils.getClientIP(request);
//           
            String mch_billno = MsgDigest.getRandomStringByLength(28);
//            ReqRedPack redPack =new ReqRedPack(nonce_str, mch_billno,
//                    Constants.MCH_ID, Constants.APP_ID, Constants.name, u.getUserid(), redpackAmount,
//                    1, Constants.wishing, client_ip, Constants.act_name, Constants.remark);
//            String sign = MsgDigest.getSign(redPack);
//            redPack.setSign(sign);
//            String result = ClientCustomSSL.ssl(url, redPack.toString());
//            System.out.println("sendRedPack======response====" + result);
//            try {
//                Map map = Dom4jUtils.readStringXmlOut(result);
//                if (map.get("return_code").equals("SUCCESS") && map.get("result_code").equals("SUCCESS")) {
//                	Redpack redpack = new Redpack();
//					redpack.setMchBillno(map.get("mch_billno").toString());
//					redpack.setMchId(map.get("mch_id").toString());
//					redpack.setCreateTime(new Date());
//					redpack.setWxappid(map.get("wxappid").toString());
//					redpack.setReOpenid( map.get("re_openid") .toString());
//					redpack.setTotalAmount((int)map.get("total_amount"));
//					redpack.setReturnCode(map.get("result_code").toString());
//					redpack.setStatus(1);
//                    redpack.setMyGameId(u.getId());
//                    redpackService.insert(redpack);
//                    u.setActive(false);
//                    gameservice.update(u);
//                }
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
        	RedPackRequest redPackRequest = new RedPackRequest();
        	redPackRequest.setActivityName(Constants.act_name);
        	redPackRequest.setAmount(redpackAmount);
        	redPackRequest.setBillNumber(mch_billno);
        	redPackRequest.setNumber(1);
        	redPackRequest.setOpenId(u.getUserid());
        	redPackRequest.setRemark(Constants.remark);
        	redPackRequest.setWishing(Constants.wishing);
        	redPackRequest.setClientIp(client_ip);
        	redPackRequest.setSendName(Constants.name);

        	RedPackResponse redPackResponse = RedPacks.defaultRedPacks().sendSingle(redPackRequest);
        	
        	Redpack redpack = new Redpack();
			redpack.setMchBillno(redPackResponse.getBillNumber());
			redpack.setMchId(Constants.MCH_ID);
			redpack.setCreateTime(new Date());
			redpack.setWxappid(Constants.APP_ID);
			redpack.setReOpenid(redPackResponse.getOpenId());
			redpack.setTotalAmount(redPackResponse.getAmount());
			redpack.setReturnCode(redPackResponse.getResultCode());
			redpack.setStatus(1);
            redpack.setMyGameId(u.getId());
            redpackService.insert(redpack);
            u.setActive(false);
            gameservice.update(u);
        } else {
        	fundReceived=true;
        	redpackAmount=0;
        }
        Map<String,Object> result=new HashMap<>();
        result.put("fundReceived", fundReceived);
        result.put("redpackAmount", redpackAmount);
        return result;
    }
	
	/**
	 * 集齐21张卡的人
	 * @return
	 */
	@RequestMapping(value="/lotteryUsers",method=RequestMethod.GET)
	public List<Game> getLotteryUsers(){
		
		return gameservice.selectByCollect(1);
	}
}
