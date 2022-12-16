package com.goj.reggie.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.goj.reggie.dto.SetmealDto;
import com.goj.reggie.entity.Setmeal;
import com.goj.reggie.mapper.SetmealMapper;
import com.goj.reggie.service.CategoryService;
import com.goj.reggie.service.SetmealDishService;
import com.goj.reggie.service.SetmealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SetmealServiceImpl extends ServiceImpl<SetmealMapper, Setmeal> implements SetmealService{

    @Autowired
    private SetmealDishService setmealDishService;

    @Autowired
    private CategoryService categoryService;

    @Override
    public void addSetmeal(SetmealDto setmealDto) {

    }
}
