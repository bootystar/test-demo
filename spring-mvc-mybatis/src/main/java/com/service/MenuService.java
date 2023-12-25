package com.service;

import com.bean.Menu;

import java.util.List;

public interface MenuService {
    public List<Menu> selectMenuByAccount(String account);
}
