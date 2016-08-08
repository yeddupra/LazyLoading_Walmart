package com.test.network;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.os.ResultReceiver;
import android.util.Log;

import com.test.utility.Constants;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.security.KeyStore;
import java.security.cert.X509Certificate;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;

/**
 * Created by pyeddula on 8/6/16.
 */
public class TestIntentService extends IntentService {
    private final String TAG = "TestIntentService::";
    ResultReceiver receiver;
    int requestType = 0;
    private int statusCode;
    private int TIMEOUT_MILLISEC = 1000*60;
    private String responseBody;

    Bundle resultData = null;
    public TestIntentService() {
        super("TestIntentService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Log.d(TAG, "onHandleIntent entry");
        receiver = intent.getParcelableExtra("receiver");
        String url = intent.getStringExtra("URL");
        String requestPayload = intent.getStringExtra(Constants.PAYLOAD);
        requestType = intent.getIntExtra(Constants.WEBSERVICE_REQUEST_TYPE, 0);
        resultData = null;
        responseBody = "";
        try {
            switch (requestType) {
               /* case Constants.DOWNLOAD_PRODUCT_LIST:
                    postData(url, requestPayload);
                    break;*/
                case Constants.DOWNLOAD_PRODUCT_LIST:
                {
                    getData(url);
                }
                break;
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally
        {
            try {
                if (receiver != null) {
                    resultData.putInt(Constants.WEBSERVICE_REQUEST_TYPE, requestType);
                    receiver.send(requestType, resultData);
                }
            }catch(Exception e)
            {
                e.printStackTrace();
            }
        }
        Log.d(TAG, "onHandleIntent exit");
    }

    public void postData(String serverUrl,String requestPayload){
        Log.d(TAG,"serverUrl: " + serverUrl);
        Log.d(TAG,"requestPayload: " + requestPayload);
        resultData = new Bundle();
        try {
            HttpsURLConnection httpsURLConnection = getURLConnection(serverUrl,"POST");
            httpsURLConnection.setDoOutput(true);
            httpsURLConnection.setDoInput(true);
            OutputStream os = httpsURLConnection.getOutputStream();
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
            writer.write(requestPayload);
            writer.flush();
            writer.close();
            os.close();

            statusCode = httpsURLConnection.getResponseCode();

            responseBody =  convertInputStreamToString(httpsURLConnection);

            resultData.putString(Constants.PAYLOAD, responseBody);
            resultData.putInt(Constants.STATUSCODE, statusCode);
            Log.d(TAG,"ResponseBody: " + responseBody);
            httpsURLConnection.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void getData(String serverUrl)//,String requestPayload)
    {
        Log.d(TAG,"Request Url: " + serverUrl);
        resultData = new Bundle();
        try {
            HttpsURLConnection httpsURLConnection = getURLConnection(serverUrl,"GET");

            httpsURLConnection.setDoInput(true);
            httpsURLConnection.setConnectTimeout(TIMEOUT_MILLISEC);
            httpsURLConnection.setReadTimeout(TIMEOUT_MILLISEC);
            statusCode = httpsURLConnection.getResponseCode();
            responseBody = convertInputStreamToString(httpsURLConnection);


            resultData.putString(Constants.PAYLOAD, responseBody);
            resultData.putInt(Constants.STATUSCODE, statusCode);
            Log.d(TAG,"ResponseBody: " + responseBody);
            httpsURLConnection.disconnect();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private  String convertInputStreamToString(HttpsURLConnection httpsURLConnection) throws IOException {
        String line;
        String result = "";
        try {
            BufferedReader bufferedReader = new BufferedReader( new InputStreamReader(httpsURLConnection.getInputStream()));//,"UTF-8"));

            while((line = bufferedReader.readLine()) != null)
                result += line;
            /*String[] escapeCharacters = { "&gt;", "&lt;", "&amp;", "&quot;", "&apos;" };
            String[] onReadableCharacter = {">", "<", "&", "\"", "'"};
            for (int i = 0; i < escapeCharacters.length; i++) {
                result = result.replace(escapeCharacters[i], onReadableCharacter[i]);
            }*/

        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
    public HttpsURLConnection getURLConnection(String url,String requestMethod) {
        try {
            SSLTrustManager sslTrustManager = new SSLTrustManager();
            URL siteUrl = new URL(url);
            HttpsURLConnection conn = null;
            conn =  (HttpsURLConnection) siteUrl.openConnection();
            conn.setDefaultSSLSocketFactory(sslTrustManager.GetSocketFactory());
            conn.setUseCaches(false);
            conn.setRequestMethod(requestMethod);
            String str = conn.getRequestMethod();
            //Utility.printLog("RequestMethod(): " + str);
            conn.setConnectTimeout(TIMEOUT_MILLISEC);
            conn.setReadTimeout(TIMEOUT_MILLISEC);
            return  conn;
        } catch (Exception e) {
            return null;
        }
    }

    class SSLTrustManager {
        private X509TrustManager x509TrustManager;
        public SSLTrustManager() {
            try {
                TrustManagerFactory tmf = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
                tmf.init((KeyStore) null);
                TrustManager[] trustManagers = tmf.getTrustManagers();
                this.x509TrustManager = (X509TrustManager) trustManagers[0];
            } catch (Exception ex) {
            }
        }

        public javax.net.ssl.SSLSocketFactory GetSocketFactory() {
            try {
                TrustManager[] byPassTrustManagers = new TrustManager[]{new X509TrustManager() {

                    public X509Certificate[] getAcceptedIssuers() {
                        return new X509Certificate[0];
                    }

                    public void checkClientTrusted(X509Certificate[] chain, String authType) {
                    }

                    public void checkServerTrusted(X509Certificate[] chain, String authType) {
                    }
                }};

                SSLContext sslContext = SSLContext.getInstance("SSL");
                sslContext.init(null, byPassTrustManagers, new java.security.SecureRandom());
                javax.net.ssl.SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();
                return sslSocketFactory;
            } catch (Exception ex) {
                return null;
            }
        }
    }
}

