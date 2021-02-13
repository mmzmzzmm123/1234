package com.ruoyi.common.utils.bookmarkhtml;



public class HtmlName implements Cloneable,Comparable<HtmlName>{

	private String id; //id
	private String parentId; //父级id
	private String title; //标题
	private String description; //简介
	private String state;//状态 0目录 1书签
	private String url;//书签地址
	private String urls;//官网


	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getUrls() {
		return urls;
	}

	public void setUrls(String urls) {
		this.urls = urls;
	}

	public HtmlName() {
		super();
	}
	public HtmlName(String id, String parentId, String title, String description, String state,String url) {
		super();
		this.id = id;
		this.parentId = parentId;
		this.title = title;
		this.description = description;
		this.state = state;
		this.url = url;
	}

	@Override
	public String toString() {
		return "HtmlName{" +
				"id='" + id + '\'' +
				", parentId='" + parentId + '\'' +
				", title='" + title + '\'' +
				", description='" + description + '\'' +
				", state='" + state + '\'' +
				", url='" + url + '\'' +
				", urls='" + urls + '\'' +
				'}';
	}

	@Override
	protected HtmlName clone() throws CloneNotSupportedException {
		return (HtmlName) super.clone();
	}

	@Override
	public int compareTo(HtmlName o) {
		return  Integer.parseInt(this.getId()) - Integer.parseInt(o.getId());
	}


}
