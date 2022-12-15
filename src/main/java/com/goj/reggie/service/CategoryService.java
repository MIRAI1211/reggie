package com.goj.reggie.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.goj.reggie.entity.Category;

public interface CategoryService extends IService<Category> {
    boolean remove(Long id);
}
