package com.wechat.order.service;

import com.wechat.order.dataobject.ProductInfo;
import com.wechat.order.dto.CartDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Created by zzy on 2017/12/14.
 */
public interface ProductService {

    ProductInfo findOne(String productId);

    List<ProductInfo> findByProductIdIn(List<String> productIds);
    /**
     * 查询所有在架的商品列表
     * @return
     */
    List<ProductInfo> findUpAll();

    Page<ProductInfo> findAll(Pageable pageable);

    ProductInfo save(ProductInfo productInfo);

    //加库存
    void increaseStock(List<CartDTO> cartDTOList);
    //减库存
   void decreaseStock(List<CartDTO> cartDTOList);
}
