package com.hit.bilthas.myapp1

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebResourceRequest
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import kotlinx.android.synthetic.main.activity_web.*

/**
 * 显示网页
 */
class WebActivity : AppCompatActivity() {

    var userName:String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_web)
        userName = this.intent.getStringExtra("user_name")

        web_browser.settings.defaultTextEncodingName = "utf-8"
        web_browser.settings.javaScriptEnabled = true
        web_browser.settings.cacheMode = WebSettings.LOAD_NO_CACHE

//        web_browser.webViewClient = object : WebViewClient(){
//            override fun shouldOverrideUrlLoading(
//                view: WebView?,
//                request: WebResourceRequest?
//            ): Boolean {
//                view?.loadUrl("https://www.baidu.com")
//                return true
//            }
//        }
//
//        web_browser.loadUrl("https://www.baidu.com")
        button.setOnClickListener {
            web_browser.loadUrl(url.text.toString())
        }
    }
}