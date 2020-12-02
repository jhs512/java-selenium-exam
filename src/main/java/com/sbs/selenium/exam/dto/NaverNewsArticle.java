package com.sbs.selenium.exam.dto;

public class NaverNewsArticle {
	private String channel;
	private String code;
	private String title;
	private String summary;
	private String thumbUrl;

	public NaverNewsArticle(String channel, String code, String title, String summary, String thumbUrl) {
		this.channel = channel;
		this.code = code;
		this.title = title;
		this.summary = summary;
		this.thumbUrl = thumbUrl;
	}

	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public String getThumbUrl() {
		return thumbUrl;
	}

	public void setThumbUrl(String thumbUrl) {
		this.thumbUrl = thumbUrl;
	}

	@Override
	public String toString() {
		return "NaverNewsArticle [channel=" + channel + ", code=" + code + ", title=" + title + ", summary=" + summary
				+ ", thumbUrl=" + thumbUrl + "]";
	}

}
