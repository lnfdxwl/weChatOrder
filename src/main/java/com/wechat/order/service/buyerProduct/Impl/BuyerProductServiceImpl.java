package com.wechat.order.service.buyerProduct.Impl;

import com.wechat.order.dataobject.ProductCategory;
import com.wechat.order.dataobject.ProductInfo;
import com.wechat.order.service.buyerProduct.BuyerProductService;
import com.wechat.order.vo.ProductInfoVO;
import com.wechat.order.vo.ProductVO;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zzy on 2017/12/14.
 */
@Service
public class BuyerProductServiceImpl implements BuyerProductService {
    @Override
    public List<ProductVO> productInfoFormat(List<ProductCategory> productCategoryList, List<ProductInfo> productInfoList) {
        List<ProductVO> productVOList=new ArrayList<>();
        for (ProductCategory productCategory:productCategoryList) {
            ProductVO productVO=new ProductVO();
            BeanUtils.copyProperties(productCategory,productVO);
            List<ProductInfoVO> productInfoVOList=new ArrayList<>();
            for (ProductInfo productInfo:productInfoList){
                if(productInfo.getCategoryType().equals(productCategory.getCategoryType())){
                    ProductInfoVO productInfoVO=new ProductInfoVO();
                    BeanUtils.copyProperties(productInfo,productInfoVO);
                    productInfoVOList.add(productInfoVO);
                }
            }
            productVO.setProductInfoVOList(productInfoVOList);
            productVOList.add(productVO);
        }
        return productVOList;
    }
}
