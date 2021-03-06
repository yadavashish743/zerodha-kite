package com.zerodha.kite.domain;

import static com.zerodha.kite.contant.KiteChartConstant.BEARISH;
import static com.zerodha.kite.contant.KiteChartConstant.BULLISH;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Immutable;

import com.zerodha.kite.contant.KiteDateFormat;
import com.zerodha.kite.util.DateUtil;
import com.zerodha.kite.util.KiteNumberUtil;

@Entity
@Immutable
@Table(name = "ohlc")
public class OHLC implements Serializable {

	private static final long serialVersionUID = 5737185416814456006L;

	@Id
	@Column(name = "ohcl_id")
	private double ohlcId;

	@Column(name = "token_id")
	private double tokenId;

	@Column(name = "open")
	private double open;

	@Column(name = "high")
	private double high;

	@Column(name = "low")
	private double low;

	@Column(name = "close")
	private double close;

	@Column(name = "volume")
	private double volume;

	@Transient
	private Date chartTimeStamp;

	@Column(name = "charttime")
	private String timeStamp;

	@Column(name = "timeframe")
	private String timeframe;

	@Transient
	private List<Algorithm> algorithm = new ArrayList<Algorithm>();

	@Transient
	public List<String> pattern = new ArrayList<String>();

	public OHLC() {
	}

	public OHLC(OHLC ohlc) {
		if (chartTimeStamp == null) {
			this.open = ohlc.open;
			this.high = ohlc.high;
			this.low = ohlc.low;
		}
		this.close = ohlc.close;
		this.high = Math.max(high, ohlc.high);
		this.low = Math.min(low, ohlc.low);
		this.volume = this.volume + ohlc.volume;
	}

	public double getBody() {
		return KiteNumberUtil.round(close - open);
	}

	public double getUWick() {
		if (getBody() > 0) {
			return KiteNumberUtil.round(high - close);
		} else {
			return KiteNumberUtil.round(high - open);
		}
	}

	public double getLWick() {
		if (getBody() > 0) {
			return KiteNumberUtil.round(open - low);
		} else {
			return KiteNumberUtil.round(close - low);
		}
	}

	public double getProfit() {
		return KiteNumberUtil.round(high - low);
	}

	public double profitPercent() {
		return Math.abs(KiteNumberUtil.round((getProfit() / low) * 100));
	}

	public String getCandleType() {
		String candleType = null;
		if (getBody() > 0) {
			candleType = BULLISH;
		} else {
			candleType = BEARISH;
		}
		return candleType;
	}

	public double getOpen() {
		return open;
	}

	public void setOpen(double open) {
		this.open = open;
	}

	public double getHigh() {
		return high;
	}

	public void setHigh(double high) {
		this.high = high;
	}

	public double getLow() {
		return low;
	}

	public void setLow(double low) {
		this.low = low;
	}

	public double getClose() {
		return close;
	}

	public void setClose(double close) {
		this.close = close;
	}

	public double getVolume() {
		return volume;
	}

	public void setVolume(double volume) {
		this.volume = volume;
	}

	public Date getChartTimeStamp() {
		return chartTimeStamp;
	}

	public void setChartTimeStamp(String chartTimeStamp) {
		this.chartTimeStamp = DateUtil.getDate(chartTimeStamp, KiteDateFormat.DATE_CHART_TIMESTAMP_FORMAT);
	}

	public void addPattern(String pattern) {
		getPattern().add(pattern);
	}

	public List<String> getPattern() {
		return pattern;
	}

	public String pattern() {
		StringBuilder sb = new StringBuilder();
		for (String pattern : getPattern()) {
			sb.append(pattern + ",");
		}
		return sb.toString();
	}

	public String getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(String timeStamp) {
		this.timeStamp = timeStamp;
	}

	public List<Algorithm> getAlgorithm() {
		return algorithm;
	}

	public void setAlgorithm(List<Algorithm> algorithm) {
		this.algorithm = algorithm;
	}

	public void setPattern(List<String> pattern) {
		this.pattern = pattern;
	}

	public void addAlgo(Algorithm algo) {
		getAlgorithm().add(algo);
	}

	public String getTimeframe() {
		return timeframe;
	}
}
