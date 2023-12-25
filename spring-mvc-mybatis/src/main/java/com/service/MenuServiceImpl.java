package com.service;

import com.bean.Menu;
import com.dao.MenuDao;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class MenuServiceImpl implements MenuService {
    @Resource
    private MenuDao menuDao;

    @Override
    public List<Menu> selectMenuByAccount(String account) {
        List<Integer> rid = menuDao.selectRidByAccount(account);
        List<Integer> mid = menuDao.selectMenuByRid(rid);
        List<Menu> menus = menuDao.selectMenuByMid(mid);
        return menus;
    }
}
