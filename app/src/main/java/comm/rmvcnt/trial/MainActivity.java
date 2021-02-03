package comm.rmvcnt.trial;

import android.annotation.SuppressLint;
import android.app.DownloadManager;
import android.content.Context;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.CookieManager;
import android.webkit.DownloadListener;
import android.webkit.SslErrorHandler;
import android.webkit.URLUtil;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;



public class MainActivity extends AppCompatActivity {

  /*EditText name,number;
  Button store,retrieve;
  SharedPreferences sharedPreferences;*/

  WebView webView;


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @SuppressLint("JavascriptInterface")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        webView =  findViewById(R.id.webView);


//        webView = new WebView(this);
//        webView.setLayoutParams(new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

        WebSettings webSettings = webView.getSettings();

        webView.getSettings().setJavaScriptEnabled(true);
//        webView.
//        webView.getSettings().setUserAgentString("");
        webSettings.setLoadsImagesAutomatically(true);
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        webSettings.setSaveFormData(false);
        webSettings.setSupportZoom(true);
        webSettings.setGeolocationEnabled(true);
        webSettings.setAllowFileAccess(true);
        webSettings.setAllowFileAccessFromFileURLs(true);
        webSettings.setAllowUniversalAccessFromFileURLs(true);
        webSettings.setUseWideViewPort(true);
        webSettings.setDomStorageEnabled(true);

        String newUA= "Mozilla/5.0 (X11; U; Linux i686; en-US; rv:1.9.0.4) Gecko/20100101 Firefox/4.0";
        webSettings.setUserAgentString(newUA);


        webView.setDownloadListener(new DownloadListener() {
            @Override
            public void onDownloadStart(String url, String userAgent, String contentDisposition, String mimeType, long contentLength) {


                    DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));

                    request.setMimeType(mimeType);
                    String cookies = CookieManager.getInstance().getCookie(url);
                    request.addRequestHeader("cookie", cookies);
                    request.addRequestHeader("User-Agent", userAgent);
                    request.setDescription("Download");
                    request.setTitle(URLUtil.guessFileName(url, contentDisposition, mimeType));
                    request.allowScanningByMediaScanner();
                    request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
                    request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, URLUtil.guessFileName(url, contentDisposition, mimeType));
                    DownloadManager dm = (DownloadManager) getSystemService(DOWNLOAD_SERVICE);
                    assert dm != null;
                    dm.enqueue(request);
                    Toast.makeText(getApplicationContext(), "Downloading...", Toast.LENGTH_LONG).show();

            }
        });

        webSettings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        webView.setLayerType(View.LAYER_TYPE_HARDWARE, null);
        webView.setVerticalScrollBarEnabled(false);


        webView.setWebChromeClient(new WebChromeClient(){});


        webView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                Log.e("url5",url);
                webView.loadUrl(url);
                return true;
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                Log.e("url6",request.getUrl().toString());
                webView.loadUrl(request.getUrl().toString());
                return true;
            }

            @Override
            public void onLoadResource(WebView view, String url) {
                super.onLoadResource(view, url);

                if (url.contains("https://t.tiktok.com/api/item_list")){
                    Log.e("url1(userlist)",url);
                }
                if (url.contains("https://t.tiktok.com/api/item/detail/")){
                    Log.e("url1(video)",url.replace("t.tiktok.com","www.tiktok.com"));
                }

            }

            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                super.onReceivedSslError(view, handler, error);
                handler.proceed();
            }

        });

        webView.addJavascriptInterface(new MainActivity.WebViewJavaScriptInterface(this), "androidapp");
//        webView.loadUrl("https://www.tiktok.com/@kinaj...dave305/");
//        webView.loadUrl("https://www.tiktok.com/@kinaj...dave305/video/6832581416635682050");
        webView.loadUrl("https://vm.tiktok.com/J1kTgPr/");


//        SecretKey originalSecretKey = new SecretKeySpec("a%2*a!Ep71M@$aSD(P!".getBytes(), 0, "a%2*a!Ep71M@$aSD(P!".getBytes().length, "AES");


//        Log.e("decrypt_new",Util.decrypt("ou690qFmBwKuJMhXiWwxXnPeUttwuMNy1YenPQme1Uk=","ebaBhxzm5Dx0Y91yF3fv86eFQiAOnykKTHvucFmLlow=","YDpl3eeEiPjrSLlqQQruzg=="));

        try {
//            Log.e("encrypt",encoderfun(Util.encrypt("secureToken=getParticleSpoof".getBytes(),decoderfun("ebaBhxzm5Dx0Y91yF3fv86eFQiAOnykKTHvucFmLlow="),decoderfun("YDpl3eeEiPjrSLlqQQruzg=="))));
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("Error","hello"+e.getMessage());
        }

       /* byte[] IV = new byte[16];
        SecureRandom random;
        random = new SecureRandom();
        random.nextBytes(IV);

        try {

            KeyGenerator keyGenerator;
            SecretKey secretKey;
            keyGenerator = KeyGenerator.getInstance("AES");
            keyGenerator.init(256);
            secretKey = keyGenerator.generateKey();

            Log.e("secretKey",secretKey.getEncoded().length+"");

            Log.e("data",encoderfun(secretKey.getEncoded())+","+encoderfun(IV));

            byte[] Encryption = Util.encrypt("secureToken=getVideosSpoof".getBytes(),secretKey,IV);
            Log.e("encyption",encoderfun(Encryption));
            String Decryption = Util.decrypt(Encryption,secretKey,IV);
            Log.e("encyption",Decryption);
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("error",e.getMessage());
        }*/


    }


    public void scrolltoBottom(View view){
//        jsx-141865828 video-feed compact
        webView.loadUrl("javascript: window.scrollTo(0,0)");
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                webView.loadUrl("javascript: window.scrollTo(0,document.getElementsByClassName('video-feed compact')[0].scrollHeight)");
            }
        },150);


    }


    public class WebViewJavaScriptInterface {
        WebViewJavaScriptInterface(Context context) {
			/*public void print(final String data){
				runOnUiThread(() -> doWebViewPrint(data));
			}*/
        }
    }

}




