package com.codingtroing.browser.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.CookieManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.trollfaces.mybrowser.R;

public class WebViewFragment extends Fragment {

    private String url;
    private boolean incognito;
    private WebView webView;

    public WebViewFragment(String url, boolean incognito) {
        this.url = url;
        this.incognito = incognito;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_webview, container, false);
        webView = view.findViewById(R.id.webView);

        webView.setWebViewClient(new WebViewClient());
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setDomStorageEnabled(true);

        // Dark Mode
        webView.getSettings().setForceDark(WebSettings.FORCE_DARK_ON);

        // Incognito / private mode
        if (incognito) {
            webView.clearHistory();
            webView.clearCache(true);
            webView.clearFormData();
            CookieManager.getInstance().removeAllCookies(null);
            CookieManager.getInstance().flush();
        }

        webView.loadUrl(url);

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        webView.stopLoading();
        webView.setWebViewClient(null);
        webView.destroy();
    }
}
