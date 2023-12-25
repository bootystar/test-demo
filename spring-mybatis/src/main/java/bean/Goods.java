package bean;

import java.math.BigDecimal;
import java.util.Date;

public class Goods {
    private int id;					//主键id
    private String name;			//商品名
    private String goodsno;			//商品编号
    private String author;			//作者
    private String publisher;		//出版社
    private String pubtime;			//发布时间
    private int categoryid;			//类别id
    private String description;		//详情
    private String image;			//商品图片
    private int stock;				//库存
    private BigDecimal marketprice;	//原价
    private BigDecimal salesprice;	//销售价
    private BigDecimal score;		//评分
    private int remarknums;			//评论数
    private Date uptime;			//上架日期
    private int salenums;			//销量
    private String newest;			//是否为新品
    private String hot;				//是否为热卖商品
    private String status;			//商品状态
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getGoodsno() {
		return goodsno;
	}
	public void setGoodsno(String goodsno) {
		this.goodsno = goodsno;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public String getPublisher() {
		return publisher;
	}
	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}
	public String getPubtime() {
		return pubtime;
	}
	public void setPubtime(String pubtime) {
		this.pubtime = pubtime;
	}
	public int getCategoryid() {
		return categoryid;
	}
	public void setCategoryid(int categoryid) {
		this.categoryid = categoryid;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public int getStock() {
		return stock;
	}
	public void setStock(int stock) {
		this.stock = stock;
	}
	public BigDecimal getMarketprice() {
		return marketprice;
	}
	public void setMarketprice(BigDecimal marketprice) {
		this.marketprice = marketprice;
	}
	public BigDecimal getSalesprice() {
		return salesprice;
	}
	public void setSalesprice(BigDecimal salesprice) {
		this.salesprice = salesprice;
	}
	public BigDecimal getScore() {
		return score;
	}
	public void setScore(BigDecimal score) {
		this.score = score;
	}
	public int getRemarknums() {
		return remarknums;
	}
	public void setRemarknums(int remarknums) {
		this.remarknums = remarknums;
	}
	public Date getUptime() {
		return uptime;
	}
	public void setUptime(Date uptime) {
		this.uptime = uptime;
	}
	public int getSalenums() {
		return salenums;
	}
	public void setSalenums(int salenums) {
		this.salenums = salenums;
	}
	public String getNewest() {
		return newest;
	}
	public void setNewest(String newest) {
		this.newest = newest;
	}
	public String getHot() {
		return hot;
	}
	public void setHot(String hot) {
		this.hot = hot;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Goods(int id, String name, String goodsno, String author, String publisher, String pubtime, int categoryid,
			String description, String image, int stock, BigDecimal marketprice, BigDecimal salesprice,
			BigDecimal score, int remarknums, Date uptime, int salenums, String newest, String hot, String status) {
		super();
		this.id = id;
		this.name = name;
		this.goodsno = goodsno;
		this.author = author;
		this.publisher = publisher;
		this.pubtime = pubtime;
		this.categoryid = categoryid;
		this.description = description;
		this.image = image;
		this.stock = stock;
		this.marketprice = marketprice;
		this.salesprice = salesprice;
		this.score = score;
		this.remarknums = remarknums;
		this.uptime = uptime;
		this.salenums = salenums;
		this.newest = newest;
		this.hot = hot;
		this.status = status;
	}
	public Goods() {
		super();
	}
	
	
	
    
    
    
	
}
