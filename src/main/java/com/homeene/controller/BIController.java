package com.homeene.controller;

import java.time.LocalDate;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.homeene.model.GrossProfit;
import com.homeene.service.BiService;

@RestController
@RequestMapping("/bi")
public class BIController {
	@Resource
	private BiService biService;
	
	@RequestMapping(value = "/upload", method = RequestMethod.POST)
	public String upload(@RequestParam(value = "file", required = false) MultipartFile file, HttpServletRequest request,
			HttpServletResponse response) {
		String result = biService.uploadGrossProfit(file);
		return result;
	}
	
	@RequestMapping(value="/getGrossProfit",method=RequestMethod.GET)
	public List<GrossProfit>  getGrossProfit() {
		LocalDate ld=LocalDate.now();
		List<GrossProfit> result=biService.getGrossProfit(ld.getYear()+"");
		return result;     
	}
}
