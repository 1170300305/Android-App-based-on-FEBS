package com.hit.bilthas.myapp1

import android.util.Log
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
import java.io.IOException
import java.util.concurrent.TimeUnit

/**
 * 访问网络
 */
object Http {

    val mOkHttpClient = OkHttpClient()
    var mResponse:HttpResponse? = null

    /**
     * 发送请求
     * post, get
     */
    /**
     * 接受的参数：url, 数据, 数据的格式
     */
    fun post_send(
        res: HttpResponse?,
        url:String,
        postData:String="",
        mContentType:String="application/x-form-urlencoded"
    ){
        mResponse = res
        //协程
        GlobalScope.launch {
            //回调方法
            val cb:Callback = object : Callback{
                override fun onFailure(call: Call, e: IOException) {
                    Log.d("hello", "网络请求失败")
                    mResponse?.errorMsg()
                }

                override fun onResponse(call: Call, response: Response) {
                    Log.d("hello", "返回成功")
                    val r = response.body?.string().toString()
                    //解析返回的内容
//                    var mJSONObject = JSONObject(r)

//                    mJSONObject = mJSONObject.optJSONObject("data")
//                    val quality = mJSONObject.optString("quality")
//                    val wendu = mJSONObject.optString("wendu","")
//                    Log.d("Http", quality)
//                    Log.d("Http", wendu)
                    Log.d("hello", r)
                    mResponse?.getResponse(r)
                }
            }

            mOkHttpClient.newBuilder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(5, TimeUnit.SECONDS)

            mOkHttpClient.newCall(
                Request.Builder()
                    .url(url)
                    .post(postData.toRequestBody(("application/json; charset=utf-8").toMediaType()))
                    .header("Content-Type",mContentType)
                    .build()
            ).enqueue(cb)

        }
    }













}