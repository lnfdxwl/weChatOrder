package com.wechat.order.controller.weChetAdminController;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * Created by zzy on 2017/12/17.
 */
@RestController
@RequestMapping("/weChat/admin")
@Slf4j
public class weChatAdminController {

    @GetMapping("/auth")
    public void auth(@RequestParam("code") String code){
        log.info("..................,code={}",code);
        String url="https://api.weixin.qq.com/sns/oauth2/access_token?appid=wx46c6689ca3ee41aa&secret=1531b68d48040348b3163a999f30e684&code="+code+"&grant_type=authorization_code";
        RestTemplate restTemplate=new RestTemplate();
        String respone= restTemplate.getForObject(url,String.class);
        log.info("respone={}",respone);

    }

}
