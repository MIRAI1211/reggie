package com.goj.reggie.controller;


import com.goj.reggie.common.R;
import com.goj.reggie.dto.SetmealDto;
import com.goj.reggie.service.SetmealService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/setmeal")
public class SetmealController {

    @Autowired
    private SetmealService setmealService;

    @PostMapping
    private R<String> add(@RequestBody SetmealDto setmealDto){
        return R.success("添加成功");
    }
}
