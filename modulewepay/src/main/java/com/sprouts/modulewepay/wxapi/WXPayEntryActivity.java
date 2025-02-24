package com.sprouts.modulewepay.wxapi;

/**
 * Created by Clark on 2025/2/19.
 */

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.sprouts.modulewepay.WeChatPay;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.unity3d.player.UnityPlayer;

public class WXPayEntryActivity extends Activity implements IWXAPIEventHandler {

    private IWXAPI api;//得到api id
    private String orderId;//订单号

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        api = WXAPIFactory.createWXAPI(this, WeChatPay.APPID);
        api.handleIntent(getIntent(), this);

    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        api.handleIntent(intent, this);
    }

    @Override
    public void onReq(BaseReq baseReq) {
    }

    /**
     * 得到支付结果回调
     */
    @Override
    public void onResp(BaseResp resp) {
        int errCode = resp.errCode;
        switch (errCode) {
            case -2:
                Toast.makeText(this,"支付取消",Toast.LENGTH_LONG).show();
                break;
            default:
                Toast.makeText(this,"支付错误,errCode "+errCode,Toast.LENGTH_LONG).show();
                break;
        }
        UnityPlayer.UnitySendMessage("SDKManager","PayCallBack",""+errCode);

        finish();//这里重要，如果没有 finish（）；将留在微信支付后的界面.


    }


}