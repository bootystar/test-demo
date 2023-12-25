package com.dao;

import com.bean.Menu;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MenuDao {

    @Select("select rid from user_role where account = #{account}")
    public List<Integer> selectRidByAccount(String account);

    @SelectProvider(type=MenuDaoProvider.class,method="selectMenuByRid")
    public List<Integer> selectMenuByRid(List<Integer> rids);

    @SelectProvider(type=MenuDaoProvider.class,method="selectMenuByMid")
    public List<Menu> selectMenuByMid(List<Integer> midList);


}
