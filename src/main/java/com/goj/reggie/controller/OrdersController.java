package com.goj.reggie.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.goj.reggie.common.R;
import com.goj.reggie.entity.Orders;
import com.goj.reggie.service.OrdersService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/order")
public class OrdersController {

    @Autowired
    private OrdersService ordersService;

    @PostMapping("/submit")
    private R<String> submit(@RequestBody Orders orders){
        ordersService.save(orders);
        return R.success("支付成功");
    }

    @GetMapping("userPage")
    private R<Page> queryPage(Integer page, Integer pageSize){
        Page<Orders> ordersPage=new Page<>(page,pageSize);
        ordersService.page(ordersPage);
        return  R.success(ordersPage);
    }
}
