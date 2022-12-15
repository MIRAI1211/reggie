package com.goj.reggie.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.goj.reggie.entity.Dish;
import com.goj.reggie.entity.DishDto;

public interface DishService extends IService<Dish> {
    void addDish(DishDto dishDto);
}
