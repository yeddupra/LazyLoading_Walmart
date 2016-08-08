package com.test.activities;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.ResultReceiver;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;

import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.test.adapter.LazyLoadImageAdapter;
import com.test.model.ProductItemsList;
import com.test.network.TestIntentService;
import com.test.utility.Constants;

import java.net.HttpURLConnection;
import com.test.R;
/**
 * Created by pyeddula on 8/6/16.
 */
public class MainActivity extends BaseActivity {
	private  final String TAG = "MainActivity::";
	ListView list;
	LazyLoadImageAdapter adapter;
    public int pageNumber = 0;
	public ResultReceiver mReceiver = null;
    ProductItemsList productItemsList = new ProductItemsList();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mReceiver =  new ResultReceiver(new Handler()) {
            @Override
            protected void onReceiveResult(int resultCode, Bundle resultData) {
                int statusCode = resultData.getInt(Constants.STATUSCODE);
                if(statusCode== HttpURLConnection.HTTP_OK || statusCode== HttpURLConnection.HTTP_CREATED) {
                    final String response = resultData.getString(Constants.PAYLOAD);
                    Log.d(TAG, "successful");
                    if (response != null && response.isEmpty() == false) {
                        switch (resultCode) {
                            case Constants.DOWNLOAD_PRODUCT_LIST: {

                                try {
                                    Gson gson = new Gson();
                                    try
                                    {
                                        ProductItemsList TempProductItemsList = gson.fromJson(response, ProductItemsList.class);
                                        if (pageNumber > 0) {
                                            productItemsList.getProducts().addAll(TempProductItemsList.getProducts());
                                            adapter.notifyDataSetChanged();
                                        } else {
                                            productItemsList = TempProductItemsList;
                                            loadListView();
                                        }
                                    }
                                    catch(Exception e)
                                    {
                                        e.printStackTrace();
                                    }
                                    if (productItemsList.getTotalProducts() > (pageNumber * Constants.PAGE_SIZE)) {
                                        pageNumber++;
                                        if(pageNumber * Constants.PAGE_SIZE>productItemsList.getTotalProducts())
                                        {
                                            downloadProductList(Constants.WALMART_BASEURL + pageNumber + "/" +
                                                    (productItemsList.getTotalProducts()-((pageNumber-1) * Constants.PAGE_SIZE)));
                                        }
                                        else
                                        {
                                            downloadProductList(Constants.WALMART_BASEURL + pageNumber + "/" + Constants.PAGE_SIZE);
                                        }
                                    }
                                }
                                catch(Exception e)
                                {
                                    e.printStackTrace();
                                }
                                StopProgressBar();
                            }
                            break;
                            default: {

                            }
                            break;
                        }
                    }
                }
            }
        };


        loadListView();
        ShowProgressBar("Loading Items........");
        downloadProductList(Constants.WALMART_BASEURL+pageNumber+"/"+Constants.PAGE_SIZE);

    }
    void loadListView()
    {
        list = (ListView) findViewById(R.id.list);
        adapter = new LazyLoadImageAdapter(this, productItemsList);
        list.setAdapter(adapter);
    }
    @Override
    protected void onResume() {
        super.onResume();
    }
    void downloadProductList(String url)
    {
        Intent intent = new Intent(this,TestIntentService.class);
        intent.putExtra("receiver", mReceiver);
        intent.putExtra("URL",url);
        intent.putExtra(Constants.WEBSERVICE_REQUEST_TYPE, Constants.DOWNLOAD_PRODUCT_LIST);
        startService(intent);
    }
    @Override
    protected void onDestroy() {
        stopService(new Intent(this, TestIntentService.class));
        mReceiver = null;
    	list.setAdapter(null);
    	super.onDestroy();
    }

    public OnClickListener listener = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			adapter.imageLoader.clearCache();
			adapter.notifyDataSetChanged();
		}
	};
	
	public void onItemClick(int pos) {
        Intent intent  =  new Intent(this, ProductDetailsActivity.class);
        intent.putExtra("DATA", productItemsList.getProducts());
        intent.putExtra("SELECTEDITEM", pos);
		startActivity(intent);
	}
	

}
