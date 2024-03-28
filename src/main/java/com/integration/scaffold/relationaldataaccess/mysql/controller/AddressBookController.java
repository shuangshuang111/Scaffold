package com.integration.scaffold.relationaldataaccess.mysql.controller;

import com.integration.scaffold.relationaldataaccess.mysql.common.PageResult;
import com.integration.scaffold.relationaldataaccess.mysql.common.Result;
import com.integration.scaffold.relationaldataaccess.mysql.dto.UserAddressBookDto;
import com.integration.scaffold.relationaldataaccess.mysql.entity.AddressBook;
import com.integration.scaffold.relationaldataaccess.mysql.service.AddressBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/addressBook")
public class AddressBookController {
    /*
     业务含义不大，主要是集成mysql的测试
    *  以地址管理为例，写一套关于mysql操作的增删改查
    * 在此业务中，有三个表 一个是用户表，一个是地址管理表，地址管理表中记录了所有员工的地址信息，每个用户都可以有多个地址
    *
    * 新增地址信息（直接有个地址类型可以选择即可）
    * 修改地址信息（包括地址信息失效）
    * 根据id查询地址信息（
    * 查询某用户的全部地址
    * 删除地址信息（按照ID删除，默认只会修改状态而不是真的删除）
    * 查询某雇员的所有信息 分页查询
    * 查询某用户的所有信息（这里只做两张表的关联，判断写SQL是否会出问题，生产上基本也不会有查询三张表的情况） 分页查询和不分页查询
    *
    * */
    // todo 没有做参数校验  异常处理  AOP代理  日志记录，获取当前用户 以后可以加上

    @Autowired
    private AddressBookService addressBookService;

    @PostMapping("/save")
    public Result<AddressBook> save(@RequestBody AddressBook addressBook) {
        return Result.success(addressBookService.save(addressBook));
    }

    @GetMapping("/{id}")
    public Result<AddressBook> get(@PathVariable Long id) {
        AddressBook addressBook = addressBookService.getById(id);
        return Result.success(addressBook);
    }


    /**
     * 查询指定用户的全部地址
     */
    @GetMapping("/list/{userId}")
    public Result<List<AddressBook>> listByUserId(@PathVariable Long userId) {

        // TODO 这里是直接写得SQL，难道它本身没有这样的方法吗？
        //SQL:select * from address_book where user_id = ? order by update_time desc
        return Result.success(addressBookService.listByUserId(userId));
    }

    /**
     * 根据ID删除地址信息
     */
    @DeleteMapping("/{id}")
    public Result<AddressBook> deleteById(@PathVariable Long id) {
        addressBookService.deleteById(id);
        return Result.success();
    }

    /**
     * 根据id修改地址信息
     */
    @PutMapping("/update")
    public Result<AddressBook> update(@RequestBody AddressBook addressBook) {
        // 第一次调试的时候   2024-03-24T19:07:10.108+08:00  WARN 18112 --- [Scaffold] [nio-8080-exec-7] .w.s.m.s.DefaultHandlerExceptionResolver : Resolved [org.springframework.http.converter.HttpMessageNotReadableException:
        // JSON parse error: Cannot construct instance of `com.integration.scaffold.relationaldataaccess.mysql.entity.AddressBook` (although at least one Creator exists): no String-argument constructor/factory
        // method to deserialize from String value ('id')]  -------postman发请求的JSON格式写错了
        return Result.success(addressBookService.save(addressBook));
    }


    /**
     * 根据userid此用户的所有地址信息
     */
    @GetMapping("/UserInfo/{userId}")
    public Result<UserAddressBookDto> getAllUserInfo(@PathVariable Long userId) {
        return Result.success(addressBookService.getAllUserInfo(userId));
    }

    /**
     * 根据userid此用户的所有地址信息 需要分页
     */
    @GetMapping("/UserInfoByPage/{userId}/{pageNum}/{pageSize}")
    public PageResult<AddressBook> getUserInfoByPage(@PathVariable Long userId, @PathVariable int pageNum, @PathVariable int pageSize) {
        Page<AddressBook> addressBookPage = addressBookService.getUserInfoByPage(userId, pageNum, pageSize);
        long count = addressBookPage.getTotalElements();
        List<AddressBook> addressBooklist = addressBookPage.getContent();
        return PageResult.success(count, addressBooklist);
    }

    /**
     * 查询所有的收货人
     */
    @GetMapping("/consignee")
    public Result<List<String>> getAllConsignee() {
        return Result.success(addressBookService.getAllConsignee());
    }


}
