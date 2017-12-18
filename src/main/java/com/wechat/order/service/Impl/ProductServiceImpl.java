package com.wechat.order.service.Impl;

import com.wechat.order.dataobject.ProductInfo;
import com.wechat.order.dto.CartDTO;
import com.wechat.order.enums.ProductStatusEnum;
import com.wechat.order.enums.ResultEnum;
import com.wechat.order.exception.SellException;
import com.wechat.order.repository.ProductInfoRepository;
import com.wechat.order.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by zzy on 2017/12/14.
 */


@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductInfoRepository productInfoRepository;
    @Override
    public ProductInfo findOne(String productId) {
        return productInfoRepository.findOne(productId);
    }

    @Override
    public List<ProductInfo> findByProductIdIn(List<String> productIds) {
        return productInfoRepository.findByProductIdIn(productIds);
    }

    @Override
    public List<ProductInfo> findUpAll() {
        return productInfoRepository.findByProductStatus(ProductStatusEnum.UP.getCode());
    }

    @Override
    public Page<ProductInfo> findAll(Pageable pageable) {
        return productInfoRepository.findAll(pageable);
    }

    @Override
    public ProductInfo save(ProductInfo productInfo) {
        return productInfoRepository.save(productInfo);
    }

    @Override
    @Transactional
    public void increaseStock(List<CartDTO> cartDTOList) {
      for (CartDTO dto:cartDTOList){
          ProductInfo productInfo=findOne(dto.getProductId());
          if(productInfo==null){
              throw  new SellException(ResultEnum.PRODUCT_NOT_EXIST);
          }
          Integer result=productInfo.getProductStock()+dto.getProductQuantity();
          productInfo.setProductStock(result);
          productInfoRepository.save(productInfo);
      }
    }

    @Override
    @Transactional
    public void decreaseStock(List<CartDTO> cartDTOList) {
        for (CartDTO cartDTO:cartDTOList){
            ProductInfo productInfo= productInfoRepository.findOne(cartDTO.getProductId());
            if(productInfo==null){
                throw  new SellException(ResultEnum.PRODUCT_NOT_EXIST);
            }
            Integer result=productInfo.getProductStock()-cartDTO.getProductQuantity();
            if(result<0){
                throw  new SellException(ResultEnum.PRODUCT_STOCK_ERROR);
            }
            productInfo.setProductStock(result);
            productInfoRepository.save(productInfo);
        }
    }
}
