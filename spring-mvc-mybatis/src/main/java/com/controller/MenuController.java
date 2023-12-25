package com.controller;

import com.bean.Menu;
import com.bean.Teacher;
import com.service.MenuService;
import com.utils.ResponseResult;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@Controller
@RequestMapping("/menu")
public class MenuController {
    @Resource
    private MenuService menuService;

    @RequestMapping("/selectMenu")
    @ResponseBody
    public ResponseResult<List<Menu>> selectMenu(String account){
        List<Menu> menus = menuService.selectMenuByAccount(account);
        ResponseResult<List<Menu>> result=new ResponseResult<>();
        result.getResult(true);
        result.setData(menus);
        return  result;
    }


    //@RequestMapping(path="/find2/{acc}/{pwd}/{gender}",method=RequestMethod.GET)
    @GetMapping("/find2/{acc}/{pwd}/{gender}")
    @ResponseBody
    public ResponseResult<List<Menu>> findMenu2(
            @PathVariable("acc")String account,
            @PathVariable("pwd")String password,
            @PathVariable("gender")String sex) {

        System.out.println(account+","+password+","+sex);

        return null;
    }

    //@RequestMapping(path="/find3/{name}/{pwd}",method=RequestMethod.POST)
    @PostMapping("/find3/{name}/{pwd}")
    @ResponseBody
    public ResponseResult<List<Menu>> findMenu3(Teacher teacher) {

        System.out.println(teacher);

        return null;
    }
}
