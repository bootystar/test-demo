package mapper;

import org.apache.ibatis.annotations.Insert;

import bean.GoodsLog;

public interface GoodsLogMapper {
	
	@Insert("insert into goods_log values(default,#{msg})")
	public int add(GoodsLog log);
	
}
