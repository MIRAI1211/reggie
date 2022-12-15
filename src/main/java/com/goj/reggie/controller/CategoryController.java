package com.goj.reggie.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.goj.reggie.common.R;
import com.goj.reggie.entity.Category;
import com.goj.reggie.entity.Dish;
import com.goj.reggie.service.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @PostMapping
    private R<String> addCategory(@RequestBody Category category) {
        categoryService.save(category);
        return R.success("新增成功");
    }

    @GetMapping("/page")
    private R<Page> page(int page, int pageSize) {
        Page page1 = new Page<>(page, pageSize);
        Page page2 = categoryService.page(page1);
        return R.success(page2);
    }

    @DeleteMapping
    private R<String> delete(Long id) {
        boolean result = categoryService.remove(id);
        if (result) {
            return R.success("删除成功");
        }
        return R.error("该项数据无法被删除");
    }

    @PutMapping
    private R<String> update(@RequestBody Category category) {
        categoryService.updateById(category);
        return R.success("修改成功");
    }

    @GetMapping("/list")
    private R<List<Category>> query(Integer type) {
        LambdaQueryWrapper<Category> lqw = new LambdaQueryWrapper<>();
        lqw.eq(type != null, Category::getType, type);
        lqw.orderByAsc(Category::getSort).orderByAsc(Category::getCreateTime);
        List<Category> list = categoryService.list(lqw);
        return R.success(list);
    }
}
