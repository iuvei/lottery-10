package com.wintv.lottery.cms.vo;

public class ArticleVO {
	    private long articleId;
		private String title;
		private String content;
		private int order;
		private String tag;
		private String pubTime;
		private String origin;
		private long click;
		private int categoryId;
		public long getArticleId() {
			return articleId;
		}
		public void setArticleId(long articleId) {
			this.articleId = articleId;
		}
		public String getTitle() {
			return title;
		}
		public void setTitle(String title) {
			this.title = title;
		}
		public String getContent() {
			return content;
		}
		public void setContent(String content) {
			this.content = content;
		}
		public int getOrder() {
			return order;
		}
		public void setOrder(int order) {
			this.order = order;
		}
		public String getTag() {
			return tag;
		}
		public void setTag(String tag) {
			this.tag = tag;
		}
		public String getPubTime() {
			return pubTime;
		}
		public void setPubTime(String pubTime) {
			this.pubTime = pubTime;
		}
		public String getOrigin() {
			return origin;
		}
		public void setOrigin(String origin) {
			this.origin = origin;
		}
		public long getClick() {
			return click;
		}
		public void setClick(long click) {
			this.click = click;
		}
		public int getCategoryId() {
			return categoryId;
		}
		public void setCategoryId(int categoryId) {
			this.categoryId = categoryId;
		}

}
