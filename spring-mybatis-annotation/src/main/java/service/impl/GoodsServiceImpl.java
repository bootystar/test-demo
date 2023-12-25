package service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import bean.Goods;
import bean.GoodsLog;
import lombok.Data;
import mapper.GoodsMapper;
import service.GoodsLogService;
import service.GoodsService;

@Service
public class GoodsServiceImpl implements GoodsService {
	@Autowired
	private GoodsMapper goodsMapper;
	@Autowired
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
	}

	@Override
	//transactional注解用于事务控制,注解内可以设置相应的事务隔离级别
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public int updateGoods(Goods goods) {
		int i = goodsMapper.updateGoods(goods);
		GoodsLog goodLog = new GoodsLog();
		goodLog.setMsg("修改了商品类");
		goodsLogService.add(goodLog);
		System.out.println(1 / 0);
		return i;
	}

}
