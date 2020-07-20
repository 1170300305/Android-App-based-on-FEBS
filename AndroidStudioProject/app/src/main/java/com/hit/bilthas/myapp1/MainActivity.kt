package com.hit.bilthas.myapp1

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
//import com.amap.api.location.AMapLocation
//import com.amap.api.location.AMapLocationClient
//import com.amap.api.location.AMapLocationClientOption
//import com.amap.api.location.AMapLocationListener
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity(), HttpResponse
//    , AMapLocationListener
{

    /**
     * 实现接口的一种方式：比较适合小型的回调
     */
//    val mHttpResponse = object: HttpResponse{
//        override fun getResponse(content: String?) {
//            Log.d("hello", "THIS IS CALLBACK:$content")
//            //input_name.setText(content)
//        }
//    }

    var mHandler = object: Handler(){
        override fun handleMessage(msg: Message) {
            if (msg.what == 0){
                tips.setText(msg.obj.toString())
            }
            if (msg.what == 1){
                tips.setText(msg.obj.toString())
            }
        }
    }
    /**
     * 加载布局文件
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        

        //发送网络测试请求
//        Http.post_send(this,"http://t.weather.sojson.com/api/weather/city/101020100")

//        //声明AMapLocationClient类对象
//        //声明AMapLocationClient类对象
//        var mLocationClient: AMapLocationClient? = null
//        //声明定位回调监听器
//        //声明定位回调监听器
//        val mLocationListener = this
//        //初始化定位
//        //初始化定位
//        mLocationClient = AMapLocationClient(applicationContext)
//        //设置定位回调监听
//        //设置定位回调监听
//        mLocationClient.setLocationListener(mLocationListener)
//
//
//        //声明AMapLocationClientOption对象
//        //声明AMapLocationClientOption对象
//        var mLocationOption: AMapLocationClientOption? = null
//        //初始化AMapLocationClientOption对象
//        //初始化AMapLocationClientOption对象
//        mLocationOption = AMapLocationClientOption()
//
//        val option = AMapLocationClientOption()
//        /**
//         * 设置定位场景，目前支持三种场景（签到、出行、运动，默认无场景）
//         */
//        /**
//         * 设置定位场景，目前支持三种场景（签到、出行、运动，默认无场景）
//         */
//        option.locationPurpose = AMapLocationClientOption.AMapLocationPurpose.SignIn
//        mLocationClient.setLocationOption(option)
//        //设置场景模式后最好调用一次stop，再调用start以保证场景模式生效
//        mLocationClient.stopLocation()
//        mLocationClient.startLocation()
//
//
//        //给定位客户端对象设置定位参数
//        mLocationClient.setLocationOption(mLocationOption);
//        //启动定位
//        mLocationClient.startLocation();

        login.setOnClickListener {

            //先清空tips
            tips.setText("")

            //发送用户登录请求
            //{"user_name":"", "user_password":""}
            val mLoginRequest = "{\"user_name\":\""+input_name.text.toString()+"\", \"user_password\":\""+input_password.text.toString()+"\"}"
            Log.d("hello", "发送的网络请求是:"+mLoginRequest)
            Http.post_send(this,"http://73138238.cpolar.io/mlogin", mLoginRequest)
            //startActivity(Intent(this,WebActivity::class.java).putExtra("user_name", "1025995956"))
//            Log.d("hit", input_name.text.toString()+"/"+input_password.text.toString())

            /**
             * 保存用户名密码
             */

//            savePreferences("username", input_name.text.toString())
//            savePreferences("password", input_password.text.toString())

            /**
             * 给指定的App发送广播
             */
//            var mIntent = Intent()
//            mIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
//            mIntent.action = "com.quark.browser"
//            this.sendBroadcast(mIntent)
        }


    }

    /**
     * 关闭Activity调用的方法
     */
    override fun onDestroy() {
        super.onDestroy()
        Log.d("hello", "activity destroyed")

    }

    /**
     * 保存数据到preferences
     */
    private fun savePreferences(key:String, value:String){
        val mSharedPreferences = getSharedPreferences("com.hit.bilthas.myapp1", 0)
        val mEditor = mSharedPreferences?.edit()
        mEditor?.putString(key, value)
        mEditor?.apply()
    }



    /**
     * 读取数据库当中的数据
     */
    fun readData(){
//        mRealm?.beginTransaction()
//        var result = mRealm?.where(UserInfo::class.java)?.findAll
//        result?.forEach{
//
//        }
//        mRealm?.commitTransaction()
    }

    override fun getResponse(content: String?) {
        Log.d("hello", "THIS IS CALLBACK:$content")
        var loginResult = ProcessResponse().userLogin(content!!)


        if (loginResult == "Success"){
            //登录成功
            startActivity(Intent(this, PaperActivity::class.java))
        }else if(loginResult == null){
            //网络错误
            var mMessage = Message()
            mMessage.what = 1
            mMessage.obj = "网络错误"
            mHandler?.sendMessage(mMessage)
        }else{
            //登录失败
            var mMessage = Message()
            mMessage.what = 1
            mMessage.obj = loginResult
            mHandler?.sendMessage(mMessage)
        }
    }

    override fun errorMsg() {
        var mMessage = Message()
        mMessage.what = 1
        mMessage.obj = "网络错误"
        mHandler?.sendMessage(mMessage)
    }

//    override fun onLocationChanged(p0: AMapLocation?) {
//        if (p0 != null) {
//            if (p0.getErrorCode() == 0) {
//                //可在其中解析amapLocation获取相应内容。
//                Log.d("Location", "获取成功")
//                Log.d("Location", p0.address)
//            }else {
//                //定位失败时，可通过ErrCode（错误码）信息来确定失败的原因，errInfo是错误信息，详见错误码表。
//                Log.d("AmapError","location Error, ErrCode:"
//                        + p0.errorCode + ", errInfo:"
//                        + p0.errorInfo);
//            }
//        }
//    }

}