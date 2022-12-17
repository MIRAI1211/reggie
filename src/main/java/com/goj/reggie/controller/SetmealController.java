package com.goj.reggie.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.goj.reggie.common.R;
import com.goj.reggie.dto.SetmealDto;
import com.goj.reggie.entity.Category;
import com.goj.reggie.entity.Setmeal;
import com.goj.reggie.entity.SetmealDish;
import com.goj.reggie.service.CategoryService;
import com.goj.reggie.service.SetmealDishService;
import com.goj.reggie.service.SetmealService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/setmeal")
public class SetmealController {

    @Autowired
    private SetmealService setmealService;

    @Autowired
    private SetmealDishService setmealDishService;

    @Autowired
    private CategoryService categoryService;

    @PostMapping
    private R<String> add(@RequestBody SetmealDto setmealDto){
        setmealService.save(setmealDto);
        List<SetmealDish> setmealDishes = setmealDto.getSetmealDishes();
        for (SetmealDish setmealDish : setmealDishes) {
            setmealDish.setSetmealId(setmealDto.getId());
            setmealDishService.save(setmealDish);
        }
        return R.success("添加成功");
    }

    @GetMapping("/page")
    private R<Page<SetmealDto>> page(Integer page,Integer pageSize,String name){
        Page<SetmealDto> query = setmealService.query(page, pageSize, name);
        return R.success(query);
    }

    @DeleteMapping
    private R<String> delete(@RequestParam List<Long> ids){
        setmealService.removeByIds(ids);
        return R.success("删除成功");
    }

    @GetMapping("{id}")
    private R<SetmealDto> updateOne(@PathVariable Long id){
        Setmeal setmeal = setmealService.getById(id);
        SetmealDto setmealDto=new SetmealDto();
        LambdaQueryWrapper<SetmealDish> lqw=new LambdaQueryWrapper<>();
        lqw.eq(SetmealDish::getSetmealId,id);
        List<SetmealDish> list = setmealDishService.list(lqw);
        BeanUtils.copyProperties(setmeal,setmealDto);
        setmealDto.setSetmealDishes(list);
        Category category = categoryService.getById(setmeal.getCategoryId());
        setmealDto.setCategoryName(category.getName());
        return R.success(setmealDto);
    }

    @PutMapping
    private R<String> update(@RequestBody SetmealDto setmealDto){
        setmealService.updateById(setmealDto);
        List<SetmealDish> setmealDishes = setmealDto.getSetmealDishes();
        for (SetmealDish setmealDish : setmealDishes) {
            setmealDishService.removeById(setmealDish);
            setmealDish.setSetmealId(setmealDto.getId());
            setmealDishService.updateById(setmealDish);
        }
        return R.success("更新成功");
    }

    @PostMapping("/status/{status}")
    private R<String> turn(@PathVariable Integer status,@RequestParam List<Long> ids){
        for (Long id : ids) {
            Setmeal setmeal=new Setmeal();
            setmeal.setStatus(status);
            setmeal.setId(id);
            setmealService.updateById(setmeal);
        }
        return R.success("操作成功");
    }

}
