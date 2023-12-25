package com.dao;

import org.apache.ibatis.jdbc.SQL;

import java.util.List;
import java.util.Map;

public class MenuDaoProvider {

    public String selectMenuByRid(Map<String,Object> data) {
        SQL sql = new SQL().SELECT("distinct mid").FROM("menu_role").WHERE("rid");

        String string = "in(";
        //得到list
        //忽视警告
        @SuppressWarnings("unchecked")
        List<Integer> rids = (List<Integer>) data.get("list");
        for (Integer rid : rids) {
            string += rid;
            string += ",";
        }
        //去掉最后那个逗号
        string = string.substring(0, string.length() - 1);
        string += ")";
        return sql.toString() + string;
    }


    public String selectMenuByMid(Map<String, Object> data) {
        SQL sql = new SQL().SELECT("*").FROM("menu").WHERE("id");

        String string = "in(";
        //得到多个mid
        List<Integer> mids = (List<Integer>) data.get("list");
        for (Integer mid : mids) {
            string += mid;
            string += ",";
        }

        string = string.substring(0, string.length() - 1);
        string += ")";
        return sql.toString() + string;
    }


}
