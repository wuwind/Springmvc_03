package com.wuwind.controller;

import com.wuwind.annotation.Controller;
import com.wuwind.annotation.Qualifier;
import com.wuwind.annotation.RequestMapping;
import com.wuwind.service.MyService;

import java.util.Map;

@Controller("wuwindController")
public class WuwindController {
    @Qualifier("myServiceImpl")
    MyService myService;

    @RequestMapping("add")
    int add(Map map){
        System.out.println("WuwindController add");
        myService.add(map);
        return 0;

    }


}
