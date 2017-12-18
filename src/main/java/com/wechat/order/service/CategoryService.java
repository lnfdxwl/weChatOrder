package com.wechat.order.service;

import com.wechat.order.dataobject.ProductCategory;

import java.util.List;

/**
 * Created by zzy on 2017/12/13.
 */
public interface CategoryService {
    ProductCategory  findOne (Integer categoryId);

    List<ProductCategory> findAll();

    List<ProductCategory> findByCategoryTypeIn(List<Integer> categoryTypeList);

    ProductCategory save(ProductCategory productCategory);
}
