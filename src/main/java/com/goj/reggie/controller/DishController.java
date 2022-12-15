package com.goj.reggie.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.goj.reggie.common.R;
import com.goj.reggie.entity.Dish;
import com.goj.reggie.entity.DishDto;
import com.goj.reggie.service.DishService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/dish")
public class DishController {

    @Autowired
    private DishService dishService;

    @PostMapping
    private R<String> add(@RequestBody DishDto dishDto){
        dishService.addDish(dishDto);
        return R.success("添加成功");
    }

    @GetMapping("/page")
    private R<Page> getPage(int page, int pageSize, String name){
        LambdaQueryWrapper<Dish> lqwDish=new LambdaQueryWrapper<>();
        LambdaQueryWrapper<DishDto> lqwDto=new LambdaQueryWrapper<>();
        lqwDish.like(name!=null,Dish::getName,name);
        Page page1=new Page<>(page,pageSize);
        Page page2 = dishService.page(page1, lqwDish);
        return R.success(page2);
    }
}
