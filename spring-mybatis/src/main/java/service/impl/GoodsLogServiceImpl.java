package service.impl;

import bean.GoodsLog;
import mapper.GoodsLogMapper;
import service.GoodsLogService;

public class GoodsLogServiceImpl implements GoodsLogService{
	private GoodsLogMapper goodsLogMapper;
	
	@Override
	public int add(GoodsLog log) {
		goodsLogMapper.add(log);
		return 0;
	}

	public GoodsLogMapper getGoodsLogMapper() {
		return goodsLogMapper;
	}

	public void setGoodsLogMapper(GoodsLogMapper goodsLogMapper) {
		this.goodsLogMapper = goodsLogMapper;
	}
	
	

	
	
}
