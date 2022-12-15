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

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

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
        lqwDish.like(name!=null,Dish::getName,name);
        Page pageDish=new Page<>(page,pageSize);
        dishService.page(pageDish, lqwDish);
        List<Dish> records = pageDish.getRecords();
        List<DishDto> dtos=new ArrayList<>();
        return R.success(pageDish);
    }
}
