package cn.itcast.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.itcast.service.AvgPvService;


@Controller
public class IndexController {

	@Autowired
	private AvgPvService pvService;

	@RequestMapping("/index")
	public String showIndex() {
		return "index";
	}

	@RequestMapping(value = "/avgPvNum", produces = "application/json;charset=UTF-8")
	@ResponseBody
	public String getChart() {
		System.out.println("获取平均pv数据..");
		String data = pvService.getAvgPvNumByDates("2013-09-18", "2013-09-24");
		return data;
	}

}
