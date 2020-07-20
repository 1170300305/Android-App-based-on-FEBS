package com.hit.bilthas.myapp1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.util.Log
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_paper.*

class PaperActivity : AppCompatActivity(), HttpResponse {

    var mHandler = object: Handler(){
        override fun handleMessage(msg: Message) {
            if (msg.what == 0){
                content.setText(msg.obj.toString())
            }
            if (msg.what == 1){
                content.setText(msg.obj.toString())
            }
            if (msg.what == 2){
                answer.setText(msg.obj.toString())
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_paper)


        query.setOnClickListener {
            val paperRequest = "{\"paperId\":\""+ paperId.text.toString()+"\", \"commit\":\"\"}"
            Log.d("hello", "发送的试卷请求是:$paperRequest")
            Http.post_send(this, "http://73138238.cpolar.io/mpaper", paperRequest)
        }

        commit.setOnClickListener {
            val commitRequest = "{\"paperId\":\""+ paperId.text.toString()+"\", \"commit\":\""+answer.text.toString()+"\"}"
            Log.d("hello", "发送的答案请求是:$commitRequest")
            Http.post_send(this, "http://73138238.cpolar.io/mpaper", commitRequest)
        }
    }

    override fun getResponse(content: String?) {
        Log.d("hello", "THIS IS Paper CALLBACK:$content")
        var paperResult = ProcessResponse().paperQuery(content!!)


        if(paperResult == null){
            //试题内容错误
            var mMessage = Message()
            mMessage.what = 0
            mMessage.obj = "试题内容返回错误"
            mHandler?.sendMessage(mMessage)
        }else if(paperResult == "true" || paperResult == "false"){
            //答案
            var mMessage = Message()
            mMessage.what = 2
            mMessage.obj = paperResult
            mHandler?.sendMessage(mMessage)
        }else{
            //成功
            var mMessage = Message()
            mMessage.what = 1
            mMessage.obj = paperResult
            mHandler?.sendMessage(mMessage)
        }
    }

    override fun errorMsg() {
        var mMessage = Message()
        mMessage.what = 0
        mMessage.obj = "网络错误"
        mHandler?.sendMessage(mMessage)
    }
}