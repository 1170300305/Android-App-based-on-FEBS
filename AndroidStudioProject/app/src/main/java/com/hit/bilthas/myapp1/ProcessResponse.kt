package com.hit.bilthas.myapp1

import org.json.JSONObject

class ProcessResponse {

    /**
     * 处理用户登录数据
     * 用户返回的类型
     * {"type":"user_login", "data":"true/false", "error":""}
     */
    fun userLogin(res:String): String?{
        //解析JSON
        try {
            val mLoginData = JSONObject(res)

            return when(mLoginData.optString("type","")){
                "user_login" -> {
                    if (mLoginData.optString("data") == "true"){
                        "Success";
                    }else{
                        mLoginData.optString("error")
                    }
                }
                ""->{
                    null
                }
                else -> null
            }
        }catch (throwable: Throwable){
            return null
        }
    }

    /**
     * 处理返回的试题数据
     * {"type": "paper_content", "data": ""}
     */
    fun paperQuery(res:String) : String?{
        //解析JSON
        val paperData = JSONObject(res)

        return when(paperData.optString("type","")){
            "paper_content" -> {
                paperData.optString("data")
            }
            "paper_answer"->{
                paperData.optString("data")
            }
            else -> null
        }
    }
}