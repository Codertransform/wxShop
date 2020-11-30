package com.mikes.shop.controller;

import com.mikes.shop.entity.AccessToken;
import com.mikes.shop.entity.MsgEntity;
import com.mikes.shop.entity.User;
import com.mikes.shop.entity.VoiceEntity;
import com.mikes.shop.service.AccessTokenService;
import com.mikes.shop.service.MsgService;
import com.mikes.shop.service.UserService;
import com.mikes.shop.service.VoiceMsgService;
import com.mikes.shop.utils.CheckUtils;
import com.mikes.shop.utils.MsgUtils;
import com.mikes.shop.utils.WeiXinUtils;
import org.dom4j.DocumentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.Map;

@RestController
@RequestMapping(value = "/check")
public class CheckController {

    @Autowired
    private MsgService msgService;

    @Autowired
    private VoiceMsgService voiceMsgService;

    @Autowired
    private AccessTokenService accessTokenService;

    @Autowired
    private UserService userService;

    @GetMapping(value = {"","/"})
    public String check(HttpServletRequest request) throws IOException {
        String signature = request.getParameter("signature");
        String timestamp = request.getParameter("timestamp");
        String nonce = request.getParameter("nonce");
        String echostr = request.getParameter("echostr");
        if (CheckUtils.checkSignatrue(signature,timestamp,nonce)){
            return echostr;
        }
        System.out.println("这不是微信发来的消息");
        return null;
    }

    @PostMapping(value = {"","/"})
    public String index(HttpServletRequest request) throws UnsupportedEncodingException {
        request.setCharacterEncoding("UTF-8");
        String str = null;
        try {
            //将request请求，传到Message工具类的转换方法中，返回接收到的Map对象
            Map<String,String> map = MsgUtils.xmlToMap(request);
            //从集合中获取各个节点的内容
            String ToUserName = map.get("ToUserName");
            String FromUserName = map.get("FromUserName");
            String CreateTime = map.get("CreateTime");
            String MsgType = map.get("MsgType");
            AccessToken token = accessTokenService.checkAccessToken();
            switch (MsgType) {
                case "text": {
                    User user = WeiXinUtils.getUser(token.getAccessToken(), FromUserName);
                    String Content = map.get("Content");
                    msgService.save(ToUserName, FromUserName, CreateTime, MsgType, Content, map.get("MsgId"));
                    MsgEntity msg = new MsgEntity();
                    msg.setToUserName(FromUserName);
                    msg.setFromUserName(ToUserName);
                    msg.setCreateTime(new Date().getTime());
                    msg.setMsgType("text");
                    msg.setContent("您好，" + user.getNickname() + "\n您想要找的商品为：" + Content);
                    msg.setMsgId(map.get("MsgId"));
                    str = MsgUtils.textToXml(msg);        //调用Message工具类，将对象转为XML字符串
                    break;
                }
                case "voice": {
                    User user = WeiXinUtils.getUser(token.getAccessToken(), FromUserName);
                    String MediaId = map.get("MediaId");
                    String Format = map.get("Format");
                    String Recognition = map.get("Recognition");
                    VoiceEntity voice = new VoiceEntity();
                    voice.setToUserName(ToUserName);
                    voice.setFromUserName(FromUserName);
                    voice.setCreateTime(new Date().getTime());
                    voice.setMsgType(MsgType);
                    voice.setFormat(Format);
                    voice.setMediaId(MediaId);
                    voice.setRecognition(Recognition);
                    voice.setMsgId(map.get("MsgId"));
                    voiceMsgService.save(voice);
                    MsgEntity msg = new MsgEntity();
                    msg.setToUserName(FromUserName);
                    msg.setFromUserName(ToUserName);
                    msg.setCreateTime(new Date().getTime());
                    msg.setMsgType("text");
                    msg.setContent("您好，" + user.getNickname() + "\n您说的是：" + Recognition);
                    msg.setMsgId(map.get("MsgId"));
                    str = MsgUtils.textToXml(msg);
                    break;
                }
                case "event": {
                    if ("subscribe".equals(map.get("Event"))) {
                        User user = WeiXinUtils.getUser(token.getAccessToken(), FromUserName);
                        userService.save(user);
                        MsgEntity msg = new MsgEntity();
                        msg.setToUserName(FromUserName);
                        msg.setFromUserName(ToUserName);
                        msg.setCreateTime(new Date().getTime());
                        msg.setMsgType("text");
                        msg.setContent("您好，" + user.getNickname() + "\n欢迎来到Mikes的生活");
                        str = MsgUtils.textToXml(msg);
                    }
                    break;
                }
            }
        } catch (DocumentException | IOException e) {
            e.printStackTrace();
        }
        return str;
    }
}
