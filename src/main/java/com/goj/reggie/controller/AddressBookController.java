package com.goj.reggie.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.goj.reggie.common.R;
import com.goj.reggie.entity.AddressBook;
import com.goj.reggie.service.AddressBookService;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/addressBook")
public class AddressBookController {

    @Autowired
    private AddressBookService addressBookService;

    @PostMapping
    private R<String> add(@RequestBody AddressBook addressBook,HttpSession session){
        addressBook.setUserId((long)session.getAttribute("user"));
        addressBookService.save(addressBook);
        return R.success("添加地址成功");
    }

    @PutMapping("/default")
    private R<String> defailtAddress(@RequestBody AddressBook addressBook, HttpSession session){
        LambdaUpdateWrapper<AddressBook> luw=new LambdaUpdateWrapper<>();
        luw.set(AddressBook::getIsDefault,0);
        luw.eq(AddressBook::getUserId,session.getAttribute("user"));
        addressBookService.update(luw);
        addressBook.setIsDefault(1);
        addressBookService.updateById(addressBook);
        return R.success("设置成功");
    }

    @GetMapping("/{id}")
    private R<AddressBook> queryOne(@PathVariable Long id){
        AddressBook addressBook = addressBookService.getById(id);
        if (addressBook != null) {
            return R.success(addressBook);
        } else {
            return R.error("没有找到该对象");
        }
    }

    @GetMapping("/default")
    private R<AddressBook> queryDefault(HttpSession session){
        LambdaQueryWrapper<AddressBook> lqw=new LambdaQueryWrapper<>();
        lqw.eq(AddressBook::getIsDefault,1);
        lqw.eq(AddressBook::getUserId,session.getAttribute("user"));
        AddressBook addressBook = addressBookService.getOne(lqw);
        return R.success(addressBook);
    }

    @GetMapping("/list")
    private R<List<AddressBook>> getAll(HttpSession session){
        LambdaQueryWrapper<AddressBook> lqw=new LambdaQueryWrapper<>();
        lqw.eq(AddressBook::getUserId,session.getAttribute("user"));
        List<AddressBook> list = addressBookService.list(lqw);
        return R.success(list);
    }

    @DeleteMapping
    private R<String> deleteByIds(@RequestParam List<Long> ids){
        addressBookService.removeByIds(ids);
        return R.success("删除成功");
    }

    @PutMapping
    private R<String> update(@RequestBody AddressBook addressBook){
        addressBookService.updateById(addressBook);
        return R.success("修改成功");
    }

}
