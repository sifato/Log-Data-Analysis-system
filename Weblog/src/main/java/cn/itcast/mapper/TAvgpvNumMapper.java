package cn.itcast.mapper;

import java.util.List;

import cn.itcast.pojo.TAvgpvNum;

public interface TAvgpvNumMapper {

	public List<TAvgpvNum> selectByDate(String startDate, String endDate);

}
