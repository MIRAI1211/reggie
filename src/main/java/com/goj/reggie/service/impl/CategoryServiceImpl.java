package com.goj.reggie.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.goj.reggie.entity.Category;
import com.goj.reggie.entity.Dish;
import com.goj.reggie.entity.Setmeal;
import com.goj.reggie.mapper.CategoryMapper;
import com.goj.reggie.mapper.DishMapper;
import com.goj.reggie.mapper.SetmealMapper;
import com.goj.reggie.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {

    @Autowired
    private DishMapper dishMapper;

    @Autowired
    private SetmealMapper setmealMapper;

    @Autowired
    private CategoryMapper categoryMapper;

    @Override
    public boolean remove(Long id) {
        LambdaQueryWrapper<Dish> lqwDish=new LambdaQueryWrapper<>();
        lqwDish.eq(Dish::getCategoryId,id);
        List<Dish> dishes = dishMapper.selectList(lqwDish);
        LambdaQueryWrapper<Setmeal> lqwSetmeal=new LambdaQueryWrapper<>();
        lqwSetmeal.eq(Setmeal::getCategoryId,id);
        List<Setmeal> setmeals = setmealMapper.selectList(lqwSetmeal);
        if(dishes.size() != 0 || setmeals.size() != 0){
            return false;
        }
        categoryMapper.deleteById(id);
        return true;
    }
}
