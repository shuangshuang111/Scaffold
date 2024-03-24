package com.integration.scaffold.relationaldataaccess.mysql.common;

import com.integration.scaffold.relationaldataaccess.mysql.dto.UserAddressBookDto;
import org.springframework.data.domain.Page;

import java.util.List;

public class PageResult <T>{
    // todo 这里是在百度借鉴的，并没有在重量级的文档上看到，以后会做修改 不清楚它是否要实现序列化接口，所以未实现，并且没有重写equals和hashCode方法
    private Integer total;
    private Integer resp_code;
    private String resp_msg;
    private List<T> datas;

    public static <T> PageResult<T> success(long total, List<T> list) {
        return new PageResult(Math.toIntExact(total), CodeEnum.SUCCESS.getCode(), CodeEnum.SUCCESS.getMessage(), list);
    }

    public static <T> PageResult<T> success(Integer total, List<T> list) {
        return new PageResult(total, CodeEnum.SUCCESS.getCode(), CodeEnum.SUCCESS.getMessage(), list);
    }

    public static <T> PageResult<T> success(Integer total, List<T> list,String msg) {
        return new PageResult(total, CodeEnum.SUCCESS.getCode(), msg, list);
    }

    public static <T> PageResult<T> fail( String msg) {
        return new PageResult(CodeEnum.FAILURE.getCode(), msg);
    }

    public static <T> PageResult<T> fail( ) {
        return new PageResult(CodeEnum.FAILURE.getCode(), CodeEnum.FAILURE.getMessage());
    }



    public Integer getTotal() {
        return this.total;
    }

    public Integer getResp_code() {
        return this.resp_code;
    }

    public String getResp_msg() {
        return this.resp_msg;
    }

    public List<T> getDatas() {
        return this.datas;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public void setResp_code(Integer resp_code) {
        this.resp_code = resp_code;
    }

    public void setResp_msg(String resp_msg) {
        this.resp_msg = resp_msg;
    }

    public void setDatas(List<T> datas) {
        this.datas = datas;

    }

    public PageResult() {
    }

    public PageResult(Integer resp_code, String resp_msg) {
        this.resp_code = resp_code;
        this.resp_msg = resp_msg;
    }

    public PageResult(Integer total, Integer resp_code, String resp_msg, List<T> datas) {
        this.total = total;
        this.resp_code = resp_code;
        this.resp_msg = resp_msg;
        this.datas = datas;
    }



}
