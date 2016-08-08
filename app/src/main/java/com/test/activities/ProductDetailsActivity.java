package com.test.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.test.R;
import com.test.listeners.OnSwipeTouchListener;
import com.test.model.ProductItem;
import com.test.model.ProductItemsList;
import com.test.utility.Constants;
import com.test.utility.ImageLoader;

import java.util.ArrayList;

public class ProductDetailsActivity extends BaseActivity {
    ArrayList<ProductItem> products = new ArrayList<ProductItem> ();
    int selectedItem = 0;
    public static class ViewHolder {
        public TextView productName;
        public TextView Price;
        public TextView InStock;
        public TextView ReviewCount;
        public TextView productDescription;
        public ImageView ProductImage;
        public ImageView RatingStar1;
        public ImageView RatingStar2;
        public ImageView RatingStar3;
        public ImageView RatingStar4;
        public ImageView RatingStar5;
    }
    ViewHolder viewHolder;
    ImageLoader imageLoader;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_productdetails);
        Intent intent  =  getIntent();
        products =  (ArrayList<ProductItem>)intent.getSerializableExtra("DATA");
        selectedItem = intent.getIntExtra("SELECTEDITEM",0);
        imageLoader = new ImageLoader(getApplicationContext());
        viewHolder = new ViewHolder();
        viewHolder.productName = (TextView) findViewById(R.id.ProductName);
        viewHolder.Price = (TextView) findViewById(R.id.Price);
        viewHolder.InStock = (TextView) findViewById(R.id.InStock);
        viewHolder.ReviewCount = (TextView) findViewById(R.id.ReviewCount);
        viewHolder.productDescription = (TextView) findViewById(R.id.ProductDescription);
        viewHolder.ProductImage = (ImageView) findViewById(R.id.ProductImage);
        viewHolder.RatingStar1 = (ImageView) findViewById(R.id.RatingStar1);
        viewHolder.RatingStar2 = (ImageView) findViewById(R.id.RatingStar2);
        viewHolder.RatingStar3 = (ImageView) findViewById(R.id.RatingStar3);
        viewHolder.RatingStar4 = (ImageView) findViewById(R.id.RatingStar4);
        viewHolder.RatingStar5 = (ImageView) findViewById(R.id.RatingStar5);

        loadDetails();
        ScrollView scrollView = (ScrollView) findViewById(R.id.ProductDetailsScrollView);
        scrollView.setOnTouchListener(new OnSwipeTouchListener(ProductDetailsActivity.this) {

            public void onSwipeRight() {

                if(selectedItem>0) {
                    selectedItem--;
                    loadDetails();
                }

            }
            public void onSwipeLeft() {
                if(products.size()>(selectedItem+1)) {
                    selectedItem++;
                    loadDetails();
                }
            }

        });
    }
    void loadDetails()
    {
        viewHolder.productName.setText(products.get(selectedItem).getProductName());
        viewHolder.Price.setText(products.get(selectedItem).getPrice());
        viewHolder.InStock.setText(String.valueOf(products.get(selectedItem).getInStock()));
        viewHolder.ReviewCount.setText("("+products.get(selectedItem).getReviewCount()+")");
        viewHolder.productDescription.setText(products.get(selectedItem).getLongDescription());

        imageLoader.displayImage(products.get(selectedItem), viewHolder.ProductImage);

        //TODO: Need to change icons in thread
        {
            float rating_float  = products.get(selectedItem).getReviewRating();
            int  rating_int = Integer.valueOf((int)rating_float);
            switch(rating_int)
            {
                case Constants.FIVESTAR:
                {
                    viewHolder.RatingStar5.setImageResource(R.drawable.star_gold);
                    viewHolder.RatingStar4.setImageResource(R.drawable.star_gold);
                    viewHolder.RatingStar3.setImageResource(R.drawable.star_gold);
                    viewHolder.RatingStar2.setImageResource(R.drawable.star_gold);
                    viewHolder.RatingStar1.setImageResource(R.drawable.star_gold);
                }
                break;
                case Constants.FOURSTAR:
                {
                    viewHolder.RatingStar4.setImageResource(R.drawable.star_gold);
                    viewHolder.RatingStar3.setImageResource(R.drawable.star_gold);
                    viewHolder.RatingStar2.setImageResource(R.drawable.star_gold);
                    viewHolder.RatingStar1.setImageResource(R.drawable.star_gold);
                    if(rating_float>rating_int)
                    {
                        viewHolder.RatingStar5.setImageResource(R.drawable.star_semiwhite);
                    }
                }
                break;
                case Constants.THREESTAR:
                {
                    viewHolder.RatingStar3.setImageResource(R.drawable.star_gold);
                    viewHolder.RatingStar2.setImageResource(R.drawable.star_gold);
                    viewHolder.RatingStar1.setImageResource(R.drawable.star_gold);
                    if(rating_float>rating_int)
                    {
                        viewHolder.RatingStar4.setImageResource(R.drawable.star_semiwhite);
                    }
                }
                break;
                case Constants.TWOSTAR:
                {
                    viewHolder.RatingStar2.setImageResource(R.drawable.star_gold);
                    viewHolder.RatingStar1.setImageResource(R.drawable.star_gold);
                    if(rating_float>rating_int)
                    {
                        viewHolder.RatingStar3.setImageResource(R.drawable.star_semiwhite);
                    }
                }
                break;
                case Constants.ONESTAR:
                {
                    viewHolder.RatingStar1.setImageResource(R.drawable.star_gold);
                    if(rating_float>rating_int)
                    {
                        viewHolder.RatingStar2.setImageResource(R.drawable.star_semiwhite);
                    }
                }
                break;
            }
        }
    }
}
