package com.goj.reggie.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.goj.reggie.dto.SetmealDto;
import com.goj.reggie.entity.Category;
import com.goj.reggie.entity.Setmeal;
import com.goj.reggie.mapper.SetmealMapper;
import com.goj.reggie.service.CategoryService;
import com.goj.reggie.service.SetmealDishService;
import com.goj.reggie.service.SetmealService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class SetmealServiceImpl extends ServiceImpl<SetmealMapper, Setmeal> implements SetmealService{

    @Autowired
    private SetmealDishService setmealDishService;

    @Autowired
    private CategoryService categoryService;

    @Override
    public Page<SetmealDto> query(Integer page, Integer pageSize, String name) {
        Page<Setmeal> pageSetmeal=new Page<>(page,pageSize);
        Page<SetmealDto> pageSetmealDto=new Page<>();
        LambdaQueryWrapper<Setmeal> lqw=new LambdaQueryWrapper<>();
        lqw.eq(name!=null,Setmeal::getName,name);
        this.page(pageSetmeal,lqw);
        BeanUtils.copyProperties(pageSetmeal,pageSetmealDto,"records");
        List<SetmealDto> setmealDtoList = pageSetmeal.getRecords().stream().map(setmeal -> {
            SetmealDto setmealDto = new SetmealDto();
            BeanUtils.copyProperties(setmeal, setmealDto);
            Category category = categoryService.getById(setmeal.getCategoryId());
            setmealDto.setCategoryName(category.getName());
            return setmealDto;
        }).collect(Collectors.toList());
        pageSetmealDto.setRecords(setmealDtoList);
        return pageSetmealDto;
    }
}
