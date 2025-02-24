# 微信支付对接文档（适用于unity，android）

## 版本说明
| 产品名称(名称+版本号+时间戳) | 版本号 | 内容 | 更新日期 | 负责人 |
| --- | --- | --- | --- | --- |
| wepay_release_v1_1740016039623.aar | v1 | 新建工程 | 2025-02-20 | Clark |
|  |  |  |  |  |
|  |  |  |  |  |
|  |  |  |  |  |
## 包结构
![支付module结构代码](https://github.com/sprouts-clark/wepay/blob/main/imgs/structure.jpg)

## 接口说明
### 1、判断当前手机是否安装微信，微信版本是否支持微信支付，true 支持，false 不支持

```
boolean isPaySupported()
```

### 2、拉起支付 weChatPay，参数说明如下

```
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
```
## 接入说明
### 1、Unity接入说明
#### 引入aar包，把aar文件放到Plugins/Android目录下即可
#### c#代码中调用aar包中的事件
##### a、拉起支付

```
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
```

##### b、支付回调

```
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
```

### 2、java 接入说明


```
image.setOnClickListener {

    Log.d("TAG", "onCreate: " + WeChatPay(this).isPaySupported)
    WeChatPay(this).weChatPay(
        "wx36b2c130b322253f",
        "1900000109",
        "1101000000140415649af9fc314aa427",
        "1101000000140429eb40476f8896f4c9",
        "1398746574",
        "Sign=WXPay",
        "oR9d8PuhnIc+YZ8cBHFCwfgpaK9gd7vaRvkYD7rthRAZX+QBhcCYL21N7cHCTUxbQ+EAt6Uy+lwSN22f5YZvI45MLko8Pfso0jm46v5hqcVwrk6uddkGuT+Cdvu4WBqDzaDjnNa5UK3GfE1Wfl2gHxIIY5lLdUgWFts17D4WuolLLkiFZV+JSHMvH7eaLdT9N5GBovBwu5yYKUR7skR8Fu+LozcSqQixnlEZUfyE55feLOQTUYzLmR9pNtPbPsu6WVhbNHMS3Ss2+AehHvz+n64GDmXxbX++IOBvm2olHu3PsOUGRwhudhVf7UcGcunXt8cqNjKNqZLhLw4jqxDg==",
        ""
    )

}
```

 