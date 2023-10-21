package cn.itcast.pojo;

import java.math.BigDecimal;

public class TAvgpvNum {
	private String datestr;// 日期
	private BigDecimal avgpvnum;// 平均PV数量

	public String getDatestr() {
		return datestr;
	}

	public void setDatestr(String datestr) {
		this.datestr = datestr == null ? null : datestr.trim();
	}

	public BigDecimal getAvgpvnum() {
		return avgpvnum;
	}

	public void setAvgpvnum(BigDecimal avgpvnum) {
		this.avgpvnum = avgpvnum;
	}
}