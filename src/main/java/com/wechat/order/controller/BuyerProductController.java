package com.wechat.order.controller;

import com.wechat.order.dataobject.ProductCategory;
import com.wechat.order.dataobject.ProductInfo;
import com.wechat.order.service.CategoryService;
import com.wechat.order.service.ProductService;
import com.wechat.order.service.buyerProduct.BuyerProductService;
import com.wechat.order.vo.ProductInfoVO;
import com.wechat.order.vo.ProductVO;
import com.wechat.order.vo.ResultVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by zzy on 2017/12/14.
 */
@RestController
@Slf4j
@RequestMapping("/buyer/product")
public class BuyerProductController {


    @Autowired
    private ProductService productService;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private BuyerProductService buyerProductService;

    @GetMapping("/list")
    public ResultVO list(){
        //查询上架商品
        List<ProductInfo> productInfoList=productService.findUpAll();
        //查询类目
        //传统方法弃用，使用精简方法，lambda表达式
        List<Integer> categoryTypeList=productInfoList.stream().map(e ->e.getCategoryType()).collect(Collectors.toList());
        List<ProductCategory> productCategoryList=  categoryService.findByCategoryTypeIn(categoryTypeList);
        //数据拼装
        List<ProductVO> productVOList=buyerProductService.productInfoFormat(productCategoryList,productInfoList);
        return ResultVO.resultBuild(0,"成功",productVOList);
    }
}
