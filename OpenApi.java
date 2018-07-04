package com.xforceplus.utils;

import java.util.HashMap;
import java.util.Map;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
/**
 * Created by chencong on 2018/3/13.
 */

public class OpenApi {
    //	public static final String xforceIp = "http://114.55.61.115:8090";//演示环境地址
    //	public static final String xforceIp = "http://api-z-t.xforceplus.com";//测试环境地址
    public static final String xforceIp = "https://api.xforceplus.com";//正式环境地址

    //测试环境
//	private static final String corp_id = "openapi_athena_p";
//	private static final String corp_secret = "311C204C4b3d573Ebb0a3443BAc02445";

    //演示环境
//	private static final String corp_id = "demo";
//	private static final String corp_secret = "311C204C4b3d573Ebb0a3443BAc02445";

    //正式环境
    private static final String corp_id = "";
    private static final String corp_secret = "";

    /**
     * 获取token
     * @return token
     */
    public static String getLoginToken(){
        String token = "";
        try {
            HttpClient httpClient = HttpClients.createDefault();

            HttpPost httpPost = new HttpPost(xforceIp + "/security/access-token?corp_id=" + corp_id + "&corp_secret=" + corp_secret);

            httpPost.setHeader("content-type", "application/json");
            HttpResponse response = httpClient.execute(httpPost);

            HttpEntity entity = response.getEntity();

            String tokens = EntityUtils.toString(entity);

            Map map = CommonTools.writeJsonToMap(tokens);
            token = map.get("result").toString();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return token;
    }

    //发送认证
    public String openAuth(){
        //获取对象
        Map auth = new HashMap();
        auth.put("taxNo", invoice.getTaxNo());
        auth.put("invoiceCode", invoice.getInvoiceCode());
        auth.put("invoiceNo", invoice.getInvoiceNo());
        auth.put("paperDrewDate", invoice.getPaperDrewDate());
        auth.put("taxPeriod", taxPeriod);
        auth.put("xcode", "xincheng");

        String token = this.getLoginToken();

        HttpClient httpClient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(xforceIp + "/auth/request?access_token=" + token);
        StringEntity s = new StringEntity(CommonTools.writeMapToJson(auth));
        httpPost.setEntity(s);
        httpPost.setHeader("content-type", "application/json");
        HttpResponse response = httpClient.execute(httpPost);

        HttpEntity entity = response.getEntity();

        String authMain = EntityUtils.toString(entity);

        return authMain;
    }
}
