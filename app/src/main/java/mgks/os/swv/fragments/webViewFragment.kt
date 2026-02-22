package com.codingtrolling.browser

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.CookieManager
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment
import com.YOURAPP.R

class WebViewFragment(
    private val url: String,
    private val incognito: Boolean = false
) : Fragment() {

    private lateinit var webView: WebView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_webview, container, false)
        webView = view.findViewById(R.id.webView)

        // WebView setup
        webView.webViewClient = WebViewClient()
        webView.settings.javaScriptEnabled = true
        webView.settings.domStorageEnabled = true

        // Dark mode support
        if (WebSettings.FORCE_DARK_ON == 1) {
            webView.settings.forceDark = WebSettings.FORCE_DARK_ON
        }

        // Incognito / private mode
        if (incognito) {
            webView.settings.apply {
                setAppCacheEnabled(false)
                saveFormData = false
            }
            webView.apply {
                clearHistory()
                clearCache(true)
                clearFormData()
                CookieManager.getInstance().removeAllCookies(null)
                CookieManager.getInstance().flush()
            }
        }

        // Load URL
        webView.loadUrl(url)

        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        // Clean up WebView to prevent memory leaks
        webView.stopLoading()
        webView.webViewClient = null
        webView.destroy()
    }
}
