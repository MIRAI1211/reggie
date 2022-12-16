package com.goj.reggie.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.goj.reggie.common.R;
import com.goj.reggie.entity.Category;
import com.goj.reggie.entity.Dish;
import com.goj.reggie.dto.DishDto;
import com.goj.reggie.service.CategoryService;
import com.goj.reggie.service.DishService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/dish")
public class DishController {

    @Autowired
    private DishService dishService;

    @Autowired
    private CategoryService categoryService;

    @PostMapping
    private R<String> add(@RequestBody DishDto dishDto){
        dishService.addDish(dishDto);
        return R.success("添加成功");
    }

    @GetMapping("/page")
    private R<Page> getPage(int page, int pageSize, String name){
        LambdaQueryWrapper<Dish> lqwDish=new LambdaQueryWrapper<>();
        lqwDish.like(name!=null,Dish::getName,name);
        lqwDish.orderByAsc(Dish::getPrice);
        Page pageDish=new Page<>(page,pageSize);
        Page pageDto=new Page<>();
        dishService.page(pageDish, lqwDish);
        BeanUtils.copyProperties(pageDish,pageDto,"Records");
        List<Dish> records = pageDish.getRecords();
        List<DishDto> dishDtos = records.stream().map(dish -> {
            DishDto dto = new DishDto();
            BeanUtils.copyProperties(dish, dto);
            Category category = categoryService.getById(dish.getCategoryId());
            dto.setCategoryName(category.getName());
            return dto;
        }).collect(Collectors.toList());
        pageDto.setRecords(dishDtos);
        return R.success(pageDto);
    }

    @GetMapping("{id}")
    private R<DishDto> getOne(@PathVariable Long id){
        Dish dish = dishService.getById(id);
        Category category = categoryService.getById(dish.getCategoryId());
        DishDto dishDto=new DishDto();
        BeanUtils.copyProperties(dish,dishDto);
        dishDto.setCategoryName(category.getName());
        return R.success(dishDto);
    }

    @PutMapping
    private R<String> update(@RequestBody DishDto dishDto){
        dishService.updateDish(dishDto);
        return R.success("修改成功");
    }

    @DeleteMapping
    private R<String> delete(@RequestParam List<Long> ids){
        dishService.removeByIds(ids);
        return R.success("删除成功");
    }

    @PostMapping("/status/{status}")
    private R<String> turn(@PathVariable Integer status,@RequestParam List<Long> ids){
        for (Long id : ids) {
            Dish dish=new Dish();
            dish.setId(id);
            dish.setStatus(status);
            dishService.updateById(dish);
        }
        return R.success("操作成功");
    }

    @GetMapping("/list")
    private R<List<Dish>> getList(Long categoryId){
        LambdaQueryWrapper<Dish> lqw=new LambdaQueryWrapper<>();
        lqw.eq(Dish::getCategoryId,categoryId);
        List<Dish> list = dishService.list(lqw);
        return R.success(list);
    }
}
