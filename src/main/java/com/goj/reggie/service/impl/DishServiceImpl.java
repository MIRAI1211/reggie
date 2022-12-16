package com.goj.reggie.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.goj.reggie.entity.Dish;
import com.goj.reggie.dto.DishDto;
import com.goj.reggie.entity.DishFlavor;
import com.goj.reggie.mapper.DishMapper;
import com.goj.reggie.service.DishFlavorService;
import com.goj.reggie.service.DishService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class DishServiceImpl extends ServiceImpl<DishMapper, Dish> implements DishService {

@Autowired
private DishFlavorService dishFlavorService;

    @Override
    @Transactional
    public void addDish(DishDto dishDto) {
        this.save(dishDto);
        List<DishFlavor> flavors=dishDto.getFlavors();
        Long dishId=dishDto.getId();
        for (DishFlavor flavor : flavors) {
            flavor.setDishId(dishId);
            dishFlavorService.save(flavor);
        }
    }

    @Override
    public void updateDish(DishDto dishDto) {
        this.updateById(dishDto);
        List<DishFlavor> flavors=dishDto.getFlavors();
        for (DishFlavor flavor : flavors) {
            dishFlavorService.updateById(flavor);
        }
    }
}
