package cn.itcast.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import cn.itcast.mapper.TAvgpvNumMapper;
import cn.itcast.pojo.AvgToPageBean;
import cn.itcast.pojo.TAvgpvNum;
import cn.itcast.service.AvgPvService;

@Service
public class AvgPvServiceImpl implements AvgPvService {

	@Autowired
	private TAvgpvNumMapper mapper;

	@Override
	public String getAvgPvNumByDates(String startDate, String endDate) {
		
		//调用查询方法
		List<TAvgpvNum> lists = mapper.selectByDate(startDate, endDate);
		// 数组大小
		int size = 7;
		//保存日期数据
		String[] dates = new String[size];
		//保存人均浏览页面数据
		double[] datas = new double[size];
		int i = 0;
		for (TAvgpvNum tAvgpvNum : lists) {
			dates[i] = tAvgpvNum.getDatestr();
			datas[i] = tAvgpvNum.getAvgpvnum().doubleValue();
			i++;
		}
		//定义AvgToPageBean对象，用于前台页面展示
		AvgToPageBean bean = new AvgToPageBean();
		bean.setDates(dates);
		bean.setData(datas);
		//Jackson提供的类，用于把对象转换成Json字符串
		ObjectMapper om = new ObjectMapper();
		String beanJson = null;
		try {
			beanJson = om.writeValueAsString(bean);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		//返回Json格式的对象
		return beanJson;
	}

}
