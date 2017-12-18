package com.wechat.order.service.buyerProduct;

import com.wechat.order.dataobject.ProductCategory;
import com.wechat.order.dataobject.ProductInfo;
import com.wechat.order.vo.ProductVO;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by zzy on 2017/12/14.
 */

public interface BuyerProductService {
    List<ProductVO> productInfoFormat(List<ProductCategory> productCategoryList, List<ProductInfo> productInfoList);
}
