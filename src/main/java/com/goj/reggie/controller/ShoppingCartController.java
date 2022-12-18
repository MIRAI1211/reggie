package com.goj.reggie.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.goj.reggie.common.R;
import com.goj.reggie.entity.ShoppingCart;
import com.goj.reggie.service.ShoppingCartService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/shoppingCart")
public class ShoppingCartController {

    @Autowired
    private ShoppingCartService shoppingCartService;


    @PostMapping("/add")
    private R<ShoppingCart> add(@RequestBody ShoppingCart shoppingCart, HttpSession session){
        shoppingCart.setUserId((long)session.getAttribute("user"));
        LambdaQueryWrapper<ShoppingCart> lqw=new LambdaQueryWrapper<>();
        lqw.eq(ShoppingCart::getUserId,session.getAttribute("user"));
        if (shoppingCart.getDishId()!=null){
            lqw.eq(ShoppingCart::getDishId,shoppingCart.getDishId());
        }else{
            lqw.eq(ShoppingCart::getSetmealId,shoppingCart.getSetmealId());
        }
        ShoppingCart one = shoppingCartService.getOne(lqw);
        if (one==null){
            shoppingCart.setNumber(1);
            shoppingCartService.save(shoppingCart);
        }else {
            one.setNumber(one.getNumber()+1);
            shoppingCartService.updateById(one);
        }
        return R.success(shoppingCart);
    }

    @GetMapping("/list")
    private R<List<ShoppingCart>> getList(HttpSession session){
        LambdaQueryWrapper<ShoppingCart> lqw=new LambdaQueryWrapper<>();
        lqw.eq(ShoppingCart::getUserId,session.getAttribute("user"));
        List<ShoppingCart> list = shoppingCartService.list(lqw);
        return R.success(list);
    }

    @PostMapping("/sub")
    private R<ShoppingCart> sub(@RequestBody ShoppingCart shoppingCart,HttpSession session){
        LambdaQueryWrapper<ShoppingCart> lqw=new LambdaQueryWrapper<>();
        lqw.eq(ShoppingCart::getUserId,session.getAttribute("user"));
        lqw.eq(shoppingCart.getDishId()!=null,ShoppingCart::getDishId,shoppingCart.getDishId());
        lqw.eq(shoppingCart.getSetmealId()!=null,ShoppingCart::getSetmealId,shoppingCart.getSetmealId());
        ShoppingCart one = shoppingCartService.getOne(lqw);
        if (one.getNumber()==1){
            shoppingCartService.removeById(lqw);
        }else {
            one.setNumber(one.getNumber()-1);
            shoppingCartService.updateById(one);
        }
        return R.success(one);
    }

    @DeleteMapping("/clean")
    private R<String> clean(HttpSession session){
        LambdaQueryWrapper<ShoppingCart> lqw=new LambdaQueryWrapper<>();
        lqw.eq(ShoppingCart::getUserId,session.getAttribute("user"));
        shoppingCartService.remove(lqw);
        return  R.success("清空完毕");
    }
}
