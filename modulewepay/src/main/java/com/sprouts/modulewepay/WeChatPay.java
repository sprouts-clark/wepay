package com.sprouts.modulewepay;

import android.content.Context;
import android.util.Log;

import com.tencent.mm.opensdk.constants.Build;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

/**
 * Created by Clark on 2025/2/19.
 */
public class WeChatPay {
    /**
     * The Context.
     */
    public Context context;
    /**
     * The constant APPID.
     */
//wx05c33a61e6f4c26a zhuge
    //wx36b2c130b322253f
    public static String APPID = "wx36b2c130b322253f";
    private IWXAPI api;

    /**
     * Instantiates a new We chat pay.
     *
     * @param mContext the m context
     */
    public WeChatPay(Context mContext) {
        context = mContext;

    }


    /**
     * We chat pay.
     *
     * @param appId        the app id
     * @param partnerId    the partner id
     * @param prepayId     the prepay id
     * @param nonceStr     the nonce str
     * @param timeStamp    the time stamp
     * @param packageValue the package value
     * @param sign         the sign
     * @param extData      the ext data
     */
    public void weChatPay(String appId, String partnerId, String prepayId, String nonceStr,
                          String timeStamp, String packageValue, String sign, String extData) {
        PayReq req = new PayReq();
        req.appId = appId;
        req.partnerId = partnerId;
        req.prepayId = prepayId;
        req.packageValue = packageValue;
        req.nonceStr = nonceStr;
        req.timeStamp = timeStamp;
        req.sign = sign;
        req.extData = extData;
        // 在支付之前，如果应用没有注册到微信，应该先调用IWXMsg.registerApp将应用注册到微信
        api = WXAPIFactory.createWXAPI(context, APPID, false);
        api.registerApp(APPID);
        api.sendReq(req);

    }

    /**
     * Is pay supported boolean.
     *
     * @return the boolean
     */
    public boolean isPaySupported() {
        api = WXAPIFactory.createWXAPI(context, APPID, false);
        Log.d("clark", "isPaySupported:" + api.getWXAppSupportAPI()+" -- "+Build.PAY_SUPPORTED_SDK_INT);
        boolean isPaySupported = api.getWXAppSupportAPI() >= Build.PAY_SUPPORTED_SDK_INT;
        return isPaySupported;
    }

}
