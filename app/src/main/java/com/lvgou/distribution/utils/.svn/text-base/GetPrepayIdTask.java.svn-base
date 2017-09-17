package com.lvgou.distribution.utils;

import java.io.StringReader;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.xmlpull.v1.XmlPullParser;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.provider.DocumentsContract.Root;
import android.util.Log;
import android.util.Xml;

import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;


/**
 * Created by Administrator on 2017/4/12.
 * 微信支付
 */

public class GetPrepayIdTask extends AsyncTask<Void, Void, Map<String, String>> {
    /**
     * 微信支付业务：入参app_id
     */
    public static final String WXAPPID = "wx384a0e20d6d4b6bd";
    public static final String MCH_ID = "1318307201";
    public static final String API_KEY = "lkjoIJDF09239jfPFUsdf33j002390gm";
    private StringBuffer sb = new StringBuffer();
    private Map<String, String> resultunifiedorder;
    private PayReq req = new PayReq();
    private IWXAPI msgApi;
    private Context c;
    private String price;
    /**
     * 订单号
     */
    private String orderNo;
    /*商品描述交易字段格式根据不同的应用场景按照以下格式：
    APP——需传入应用市场上的APP名字-实际商品名称，天天爱消除-游戏充值。*/

    /**
     * 多个订单
     */
    private String orderNos;

    public GetPrepayIdTask(Context c, String price, String orderNo, String orderNos) {
        msgApi = WXAPIFactory.createWXAPI(c, null);
        this.c = c;
        this.price = price;
        this.orderNo = orderNo;
        this.orderNos = orderNos;
    }

    private ProgressDialog dialog;

    @Override
    protected void onPreExecute() {
        dialog = ProgressDialog.show(c, "弹窗提示", "正在加载");
    }

    @Override
    protected void onPostExecute(Map<String, String> result) {
        if (dialog != null) {
            dialog.dismiss();
        }

        sb.append("prepay_id\n" + result.get("prepay_id") + "\n\n");

        resultunifiedorder = result;
        genPayReq();
        msgApi.registerApp(WXAPPID);
        msgApi.sendReq(req);
    }

    @Override
    protected void onCancelled() {
        super.onCancelled();
    }

    @Override
    protected Map<String, String> doInBackground(Void... params) {

        String url = String.format("https://api.mch.weixin.qq.com/pay/unifiedorder");
        String entity = genProductArgs();

        byte[] buf = Util.httpPost(url, entity);

        String content = new String(buf);
        Log.e("orion", content);
        Map<String, String> xml = decodeXml(content);

        return xml;
    }

    private void genPayReq() {

        req.appId = WXAPPID;
        req.partnerId = MCH_ID;
        //req.prepayId=Constants.API_KEY;
        req.prepayId = resultunifiedorder.get("prepay_id");
        req.packageValue = "Sign=WXPay";
        req.nonceStr = genNonceStr();
        req.timeStamp = String.valueOf(genTimeStamp());

        List<NameValuePair> signParams = new LinkedList<NameValuePair>();
        signParams.add(new BasicNameValuePair("appid", req.appId));
        // signParams.add(new BasicNameValuePair("appkey", Constants.API_KEY));
        signParams.add(new BasicNameValuePair("noncestr", req.nonceStr));
        signParams.add(new BasicNameValuePair("package", req.packageValue));
        signParams.add(new BasicNameValuePair("partnerid", req.partnerId));
        signParams.add(new BasicNameValuePair("prepayid", req.prepayId));
        signParams.add(new BasicNameValuePair("timestamp", req.timeStamp));

        req.sign = genAppSign(signParams);

        sb.append("sign\n" + req.sign + "\n\n");

        Log.e("orion", signParams.toString());
        // req.appId = Constants.APP_ID;
        // req.partnerId = Constants.MCH_ID;
        // req.prepayId="wx20150928191247ec2bfe36120916857122";
        // req.packageValue = "Sign=WXPay";
        // req.nonceStr="lPLaOjotrEy9MYx3";
        // req.sign="10DD9C41A6857B4C4AEA98E24605925C";
        // req.timeStamp="1443437278";
    }

    private String genAppSign(List<NameValuePair> params) {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < params.size(); i++) {
            sb.append(params.get(i).getName());
            sb.append('=');
            sb.append(params.get(i).getValue());
            sb.append('&');
        }
        sb.append("key=");
        sb.append(API_KEY);

        this.sb.append("sign str\n" + sb.toString() + "\n\n");
        String appSign = MD5.getMessageDigest(sb.toString().getBytes()).toUpperCase();
        Log.e("orionAppSign", appSign);
        return appSign;
    }

    private String genProductArgs() {
        StringBuffer xml = new StringBuffer();

        try {
            String nonceStr = genNonceStr();

            xml.append("</xml>");
            List<NameValuePair> packageParams = new LinkedList<NameValuePair>();
            packageParams.add(new BasicNameValuePair("appid", WXAPPID));
            packageParams.add(new BasicNameValuePair("body", orderNo));
            packageParams.add(new BasicNameValuePair("mch_id", MCH_ID));
            packageParams.add(new BasicNameValuePair("nonce_str", nonceStr));
            packageParams.add(new BasicNameValuePair("notify_url", "http://agent.quygt.com/tenpay/tenpaynotify"));
//            packageParams.add(new BasicNameValuePair("notify_url", "http://lvgounet.oicp.net:24561/tenpay/tenpaynotify"));
            // 订单
//            packageParams.add(new BasicNameValuePair("out_trade_no", genOutTradNo(orderNos)));
            packageParams.add(new BasicNameValuePair("out_trade_no", orderNos));
            // 订单价格
//            long total_fee = (long) (1);
            // 订单
            packageParams.add(new BasicNameValuePair("spbill_create_ip", "127.0.0.1"));
            packageParams.add(new BasicNameValuePair("total_fee", price));
//            Log.e("sjdfajsadf", "==============="+price );
//            packageParams.add(new BasicNameValuePair("total_fee", "1"));
            packageParams.add(new BasicNameValuePair("trade_type", "APP"));

            String sign = genPackageSign(packageParams);
            packageParams.add(new BasicNameValuePair("sign", sign));

            String xmlstring = toXml(packageParams);
            return new String(xmlstring.toString().getBytes(), "ISO8859-1");//这句加上就可以了吧xml转码下
//            return xmlstring;

        } catch (Exception e) {
            // Log.e(TAG, "genProductArgs fail, ex = " + e.getMessage());
            return null;
        }

    }

    public Map<String, String> decodeXml(String content) {

        try {
            Map<String, String> xml = new HashMap<String, String>();
            XmlPullParser parser = Xml.newPullParser();
            parser.setInput(new StringReader(content));
            int event = parser.getEventType();
            while (event != XmlPullParser.END_DOCUMENT) {

                String nodeName = parser.getName();
                switch (event) {
                    case XmlPullParser.START_DOCUMENT:

                        break;
                    case XmlPullParser.START_TAG:

                        if ("xml".equals(nodeName) == false) {
                            // 实例化student对象
                            xml.put(nodeName, parser.nextText());
                        }
                        break;
                    case XmlPullParser.END_TAG:
                        break;
                }
                event = parser.next();
            }

            return xml;
        } catch (Exception e) {
            Log.e("orion", e.toString());
        }
        return null;

    }

    private String genNonceStr() {
        Random random = new Random();
        return MD5.getMessageDigest(String.valueOf(random.nextInt(10000)).getBytes());
    }

    private long genTimeStamp() {
        return System.currentTimeMillis() / 1000;
    }

    /**
     * 生成签名
     */

    private String genPackageSign(List<NameValuePair> params) {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < params.size(); i++) {
            sb.append(params.get(i).getName());
            sb.append('=');
            sb.append(params.get(i).getValue());
            sb.append('&');
        }
        sb.append("key=");
        sb.append(API_KEY);

        String packageSign = MD5.getMessageDigest(sb.toString().getBytes())
                .toUpperCase();
        Log.e("orionStringBuilder", sb.toString());
        Log.e("orionAppMd5", packageSign);
        return packageSign;
    }

    private String toXml(List<NameValuePair> params) {
        StringBuilder sb = new StringBuilder();
        sb.append("<xml>");
        for (int i = 0; i < params.size(); i++) {
            sb.append("<" + params.get(i).getName() + ">");

            sb.append(params.get(i).getValue());
            sb.append("</" + params.get(i).getName() + ">");
        }
        sb.append("</xml>");

        Log.e("orionsdsd", sb.toString());//new String(sb2.toString().getBytes(), "ISO8859-1")
        return sb.toString();
    }

    /**
     * 订单号信息
     *
     * @return
     */
    private String genOutTradNo(String orderNos) {
        // Random random = new Random();
        return MD5.getMessageDigest(String.valueOf(orderNos).getBytes());
    }
}