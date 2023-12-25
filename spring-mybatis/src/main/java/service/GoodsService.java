package service;

import java.util.List;

import bean.Goods;

public interface GoodsService {

	List<Goods> selectGoods (Goods goods); 
	
	int updateGoods(Goods goods);
}
