package service.impl;

import java.util.List;

import bean.Goods;
import bean.GoodsLog;
import lombok.Data;
import mapper.GoodsMapper;
import service.GoodsLogService;
import service.GoodsService;

public class GoodsServiceImpl implements GoodsService {
	private GoodsMapper goodsMapper;
	private GoodsLogService goodsLogService;

	@Override
	public List<Goods> selectGoods(Goods goods) {
		return goodsMapper.selectGoods(goods);
	}

	public GoodsMapper getGoodsMapper() {
		return goodsMapper;
	}

	public void setGoodsMapper(GoodsMapper goodsMapper) {
		this.goodsMapper = goodsMapper;
	}

	public GoodsServiceImpl(GoodsMapper goodsMapper) {
		super();
		this.goodsMapper = goodsMapper;
	}

	public GoodsLogService getGoodsLogService() {
		return goodsLogService;
	}

	public void setGoodsLogService(GoodsLogService goodsLogService) {
		this.goodsLogService = goodsLogService;
	}

	public GoodsServiceImpl() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public int updateGoods(Goods goods) {
		int i = goodsMapper.updateGoods(goods);
		GoodsLog goodLog=new GoodsLog();
		goodLog.setMsg("修改了商品类");
		goodsLogService.add(goodLog);
		System.out.println(1/0);
		return i;
	}

}
