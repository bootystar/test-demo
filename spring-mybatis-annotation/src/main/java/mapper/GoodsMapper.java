package mapper;

import java.util.List;

import bean.Goods;

public interface GoodsMapper {
	
	List<Goods> selectGoods(Goods goods);

	int updateGoods(Goods goods);
	
}
