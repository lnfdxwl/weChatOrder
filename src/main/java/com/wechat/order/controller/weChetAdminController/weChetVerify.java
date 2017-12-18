package com.wechat.order.controller.weChetAdminController;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by zzy on 2017/12/17.
 */
@RestController
@RequestMapping("/wc/verify/admin")
@Slf4j
public class weChetVerify {

    @GetMapping("/token")
    public String token(HttpServletRequest request){

        String signature=request.getParameter("signature");
        String timestamp=request.getParameter("timestamp");
        String nonce=request.getParameter("nonce");
        String echostr=request.getParameter("echostr");

        log.info("signature={},timestamp={},nonce={},echostr={}",signature,timestamp,nonce,echostr);
        return echostr;
    }
}
