一、版本说明
产品名称(名称+版本号+时间戳)版本号修改内容更新日期负责人备注说明wepay_release_v1_1740016039623.aarv1新建工程2025-02-20Clark
二、包结构

三、接口说明
1、判断当前手机是否安装微信，微信版本是否支持微信支付，true 支持，false 不支持
boolean isPaySupported()
2、拉起支付 weChatPay，参数说明如下，
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

void weChatPay(String appId, String partnerId, String prepayId, String nonceStr, String timeStamp, String packageValue, String sign, String extData)
四、接入说明
Unity接入说明
1、引入aar包，把aar文件放到Plugins/Android目录下即可
2、c#代码中调用aar包中的事件 
a.拉起支付
 if (Application.platform == RuntimePlatform.Android)
        {
            // 获取 UnityPlayer 的当前活动
            AndroidJavaClass unityPlayer = new AndroidJavaClass("com.unity3d.player.UnityPlayer");
            AndroidJavaObject currentActivity = unityPlayer.GetStatic<AndroidJavaObject>("currentActivity");

            // 获取上下文
            androidContext = currentActivity.Call<AndroidJavaObject>("getApplicationContext");

            // 可以在这里使用 androidContext 进行后续操作
            Debug.Log("获取到 Android 上下文");
            //检测是否支持微信支付
            Log.d("TAG", "onCreate: " + WeChatPay(this).isPaySupported)
            //支付拉起
            WeChatPay(this).weChatPay("wx36b2c130b322253f","1900000109","1101000000140415649af9fc314aa427", "1101000000140429eb40476f8896f4c9","1398746574","Sign=WXPay","","")
            
        }
2.支付回调
支付module支付完成之后会发送支付回调
UnityPlayer.UnitySendMessage("SDKManager","PayCallBack",""+errCode);

枚举如下
int ERR_OK = 0;
int ERR_COMM = -1;
int ERR_USER_CANCEL = -2;
int ERR_SENT_FAILED = -3;
int ERR_AUTH_DENIED = -4;
int ERR_UNSUPPORT = -5;
int ERR_BAN = -6;
java接入说明，用到的时候再加。

五、注意，签名，appId 一定要保持一致，否则拉起会报错。
