package com.goj.reggie.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.goj.reggie.dto.SetmealDto;
import com.goj.reggie.entity.Setmeal;

public interface SetmealService extends IService<Setmeal> {
    Page<SetmealDto> query(Integer page,Integer pageSize,String name);
}
