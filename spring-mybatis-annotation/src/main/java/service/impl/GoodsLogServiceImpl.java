package service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import bean.GoodsLog;
import mapper.GoodsLogMapper;
import service.GoodsLogService;

@Service
public class GoodsLogServiceImpl implements GoodsLogService{
	@Autowired
	private GoodsLogMapper goodsLogMapper;
	
	@Override
	@Transactional
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
