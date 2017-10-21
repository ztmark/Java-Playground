package com.github.ztmark.handler;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.common.session.WxSessionManager;
import me.chanjar.weixin.mp.api.WxMpMessageHandler;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.kefu.WxMpKefuMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;

/**
 * @author Mark
 * @date 2017/10/19
 */
@Component
public class WechatMsgHandler implements WxMpMessageHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(WechatMsgHandler.class);

    @Override
    public WxMpXmlOutMessage handle(WxMpXmlMessage wxMessage, Map<String, Object> context, WxMpService wxMpService, WxSessionManager sessionManager) throws WxErrorException {
        LOGGER.info("WechatMsgHandler.handle {}", wxMessage);
        System.out.println(wxMessage);
        final String openId = wxMessage.getFromUser();
        final WxMpKefuMessage msg = WxMpKefuMessage.TEXT().toUser(openId).content("welcome to ztmark").build();
        final boolean b = wxMpService.getKefuService().sendKefuMessage(msg);
        if (b) {
            System.out.println("success send msg back");
        }
        return null;
    }
}
