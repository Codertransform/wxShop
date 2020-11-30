package com.mikes.shop.utils;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.mikes.shop.entity.AccessToken;
import com.mikes.shop.entity.Tag;
import com.mikes.shop.entity.User;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class WeiXinUtils {
    //从微信后台拿到APPID和APPSECRET 并封装为常量
    private static final String APPID = "wx1bf42cc747b376e9";
    private static final String APPSECRET = "5a6bcf7d68743e053a838ea090869366";
    private static final String ACCESS_TOKEN_URL = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET";
    private static final String GET_USER_URL = "https://api.weixin.qq.com/cgi-bin/user/info?access_token=ACCESS_TOKEN&openid=OPENID&lang=zh_CN";
//    private static final String GET_USER_TAG_URL = "https://api.weixin.qq.com/cgi-bin/tags/get?access_token=ACCESS_TOKEN";

    /**
     * 编写Get请求的方法。但没有参数传递的时候，可以使用Get请求
     *
     * @param url 需要请求的URL
     * @return 将请求URL后返回的数据，转为JSON格式，并return
     */
    public static JsonObject doGetStr(String url) throws ClientProtocolException, IOException {
        HttpClient client = HttpClientBuilder.create().build();//获取DefaultHttpClient请求
        HttpGet httpGet = new HttpGet(url);//HttpGet将使用Get方式发送请求URL
        JsonObject jsonObject = null;
        HttpResponse response = client.execute(httpGet);//使用HttpResponse接收client执行httpGet的结果
        HttpEntity entity = response.getEntity();//从response中获取结果，类型为HttpEntity
        if(entity != null){
            String result = EntityUtils.toString(entity,"UTF-8");//HttpEntity转为字符串类型
            jsonObject = new JsonParser().parse(result).getAsJsonObject();//字符串类型转为JSON类型
        }
        return jsonObject;
    }

    /**
     * 编写Post请求的方法。当我们需要参数传递的时候，可以使用Post请求
     *
     * @param url 需要请求的URL
     * @param outStr  需要传递的参数
     * @return 将请求URL后返回的数据，转为JSON格式，并return
     */
    public static JsonObject doPostStr(String url,String outStr) throws ClientProtocolException, IOException {
        HttpClient client = HttpClientBuilder.create().build();//获取DefaultHttpClient请求
        HttpPost httpost = new HttpPost(url);//HttpPost将使用Get方式发送请求URL
        JsonObject jsonObject = null;
        httpost.setEntity(new StringEntity(outStr,"UTF-8"));//使用setEntity方法，将我们传进来的参数放入请求中
        HttpResponse response = client.execute(httpost);//使用HttpResponse接收client执行httpost的结果
        String result = EntityUtils.toString(response.getEntity(),"UTF-8");//HttpEntity转为字符串类型
        jsonObject = new JsonParser().parse(result).getAsJsonObject();//字符串类型转为JSON类型
        return jsonObject;
    }

    /**
     * 获取AccessToken
     * @return 返回拿到的access_token及有效期
     */
    public static AccessToken getAccessToken() throws ClientProtocolException, IOException{
        AccessToken token = new AccessToken();
        String url = ACCESS_TOKEN_URL.replace("APPID", APPID).replace("APPSECRET", APPSECRET);//将URL中的两个参数替换掉
        JsonObject jsonObject = doGetStr(url);//使用刚刚写的doGet方法接收结果
        if(jsonObject!=null){ //如果返回不为空，将返回结果封装进AccessToken实体类
            token.setAccessToken(jsonObject.get("access_token").getAsString());//取出access_token
            token.setExpiresIn(jsonObject.get("expires_in").getAsInt());//取出access_token的有效期
        }
        return token;
    }

    /**
     * 获取用户信息
     * @param accesstoken   凭证
     * @param openid    openid
     * @return
     * @throws IOException
     */
    public static User getUser(String accesstoken,String openid) throws IOException {
        User user = new User();
        String url = GET_USER_URL.replace("ACCESS_TOKEN",accesstoken).replace("OPENID",openid);
        JsonObject jsonObject = doGetStr(url);
        if (jsonObject != null) {
            user.setSubscribe(jsonObject.get("subscribe").getAsCharacter());
            user.setOpenid(jsonObject.get("openid").getAsString());
            user.setNickname(jsonObject.get("nickname").getAsString());
            user.setSex(jsonObject.get("sex").getAsCharacter());
            user.setCity(jsonObject.get("city").getAsString());
            user.setCountry(jsonObject.get("country").getAsString());
            user.setProvince(jsonObject.get("province").getAsString());
            user.setLanguage(jsonObject.get("language").getAsString());
            user.setHeadimgurl(jsonObject.get("headimgurl").getAsString());
            user.setSubscribe_time(jsonObject.get("subscribe_time").getAsString());
            if (jsonObject.get("unionid") != null){
                user.setUnionid(jsonObject.get("unionid").getAsString());
            }
            user.setRemark(jsonObject.get("remark").getAsString());
            user.setGroupid(jsonObject.get("groupid").getAsString());
            JsonArray elements = jsonObject.getAsJsonArray("tagid_list");
            List<Tag> tags = new ArrayList<>();
            for (JsonElement ele : elements) {
                Tag tag = new Tag();
                tag.setId(ele.getAsString());
                tags.add(tag);
            }
            user.setTagid_list(tags);
            user.setSubscribe_scene(jsonObject.get("subscribe_scene").getAsString());
        }
        return user;
    }

    /*public static Tag getUserTag(String accesstoken) throws IOException {
        Tag tag = new Tag();
        String url = GET_USER_TAG_URL.replace("ACCESS_TOKEN",accesstoken);
        JsonObject jsonObject = doGetStr(url);
        if (jsonObject != null){
            tag.setId(jsonObject.get("id").getAsString());
            tag.setName(jsonObject.get("name").getAsString());
        }
        return tag;
    }*/
}
