package com.homeene.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.homeene.dao.GrossProfitMapper;
import com.homeene.model.GrossProfit;
import com.homeene.utils.ReadExcel;

@Service
public class BiService {
	@Resource
	private GrossProfitMapper grossProfitMapper;

	public List<GrossProfit> getGrossProfit(String year) {
		return grossProfitMapper.selectAll();
	}

	public String uploadGrossProfit(MultipartFile file) {
		// TODO Auto-generated method stub
		String result = "";
		// 创建处理EXCEL的类
		ReadExcel readExcel = new ReadExcel();
		// 解析excel，获取上传的事件单
		List<GrossProfit> resultList = readExcel.getExcelInfo(file);
		// 至此已经将excel中的数据转换到list里面了,接下来就可以操作list,可以进行保存到数据库,或者其他操作,
		// 和你具体业务有关,这里不做具体的示范
		if (resultList != null && !resultList.isEmpty()) {
			resultList.forEach(e -> grossProfitMapper.insert(e));
			result = "上传成功";
		} else {
			result = "上传失败";
		}
		return result;
	}

}
