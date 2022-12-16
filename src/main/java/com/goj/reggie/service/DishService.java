package com.goj.reggie.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.goj.reggie.entity.Dish;
import com.goj.reggie.dto.DishDto;

public interface DishService extends IService<Dish> {
    void addDish(DishDto dishDto);


    void updateDish(DishDto dishDto);
}
