package com.wechat.order.repository;

import com.wechat.order.dataobject.ProductInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by zzy on 2017/12/13.
 */
public interface ProductInfoRepository extends JpaRepository<ProductInfo,String> {

    /**
     * 根据商品状态查询
     * @param productStatus
     * @return
     */
    List<ProductInfo> findByProductStatus(Integer productStatus);

    /**
     * 根据商品ID查询LIST
     */
    List<ProductInfo> findByProductIdIn(List<String> productIds);

}
