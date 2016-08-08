package com.test.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.test.activities.MainActivity;
import com.test.R;
import com.test.model.ProductItemsList;
import com.test.utility.Constants;
import com.test.utility.ImageLoader;
/**
 * Created by pyeddula on 8/6/16.
 */
public class LazyLoadImageAdapter extends BaseAdapter implements OnClickListener{

	private Activity activity;
	private ProductItemsList productItemsList;
	private static LayoutInflater inflater = null;
	public ImageLoader imageLoader;
	
	public LazyLoadImageAdapter(Activity a,  ProductItemsList aProductItemsList ) {
		activity = a;
		productItemsList = aProductItemsList;
		
		inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		
		imageLoader = new ImageLoader(activity.getApplicationContext());
	}
	
	@Override
	public int getCount() {
		return productItemsList.getProducts().size();
	}

	@Override
	public Object getItem(int pos) {
		return pos;
	}

	@Override
	public long getItemId(int pos) {
		return pos;
	}

	public static class ViewHolder {
		public TextView productName;
		public TextView Price;
		public TextView InStock;
		public TextView ReviewCount;
		public ImageView ProductImage;
		public ImageView RatingStar1;
		public ImageView RatingStar2;
		public ImageView RatingStar3;
		public ImageView RatingStar4;
		public ImageView RatingStar5;
	}
	
	@Override
	public View getView(int pos, View convertView, ViewGroup parent) {
		
		View vi = convertView;
		ViewHolder holder;
		
		if(convertView == null) {
			
			vi = inflater.inflate(R.layout.listview_row, null);
			
			holder = new ViewHolder();
			
			holder.productName = (TextView) vi.findViewById(R.id.ProductName);
			holder.Price = (TextView) vi.findViewById(R.id.Price);
			holder.InStock = (TextView) vi.findViewById(R.id.InStock);
			holder.ReviewCount = (TextView) vi.findViewById(R.id.ReviewCount);

			holder.ProductImage = (ImageView) vi.findViewById(R.id.ProductImage);
			holder.RatingStar1 = (ImageView) vi.findViewById(R.id.RatingStar1);
			holder.RatingStar2 = (ImageView) vi.findViewById(R.id.RatingStar2);
			holder.RatingStar3 = (ImageView) vi.findViewById(R.id.RatingStar3);
			holder.RatingStar4 = (ImageView) vi.findViewById(R.id.RatingStar4);
			holder.RatingStar5 = (ImageView) vi.findViewById(R.id.RatingStar5);

			vi.setTag(holder);
			
		} else {
			holder = (ViewHolder) vi.getTag();
		}
		
		ImageView image = holder.ProductImage;

		holder.productName.setText(productItemsList.getProducts().get(pos).getProductName());
		holder.Price.setText(productItemsList.getProducts().get(pos).getPrice());
		holder.InStock.setText("Instock: "+productItemsList.getProducts().get(pos).getInStock());
		holder.ReviewCount.setText("("+productItemsList.getProducts().get(pos).getReviewCount()+")");
		imageLoader.displayImage(productItemsList.getProducts().get(pos), image);

		//TODO: Need to change icons in thread
		{
			float rating_float  = productItemsList.getProducts().get(pos).getReviewRating();
			int  rating_int = Integer.valueOf((int)rating_float);
			switch(rating_int)
			{
				case Constants.FIVESTAR:
				{
					holder.RatingStar5.setImageResource(R.drawable.star_gold);
                    holder.RatingStar4.setImageResource(R.drawable.star_gold);
                    holder.RatingStar3.setImageResource(R.drawable.star_gold);
                    holder.RatingStar2.setImageResource(R.drawable.star_gold);
                    holder.RatingStar1.setImageResource(R.drawable.star_gold);
				}
                break;
				case Constants.FOURSTAR:
				{
					holder.RatingStar4.setImageResource(R.drawable.star_gold);
                    holder.RatingStar3.setImageResource(R.drawable.star_gold);
                    holder.RatingStar2.setImageResource(R.drawable.star_gold);
                    holder.RatingStar1.setImageResource(R.drawable.star_gold);
					if(rating_float>rating_int)
					{
						holder.RatingStar5.setImageResource(R.drawable.star_semiwhite);
					}
				}
                break;
				case Constants.THREESTAR:
				{
					holder.RatingStar3.setImageResource(R.drawable.star_gold);
                    holder.RatingStar2.setImageResource(R.drawable.star_gold);
                    holder.RatingStar1.setImageResource(R.drawable.star_gold);
					if(rating_float>rating_int)
					{
						holder.RatingStar4.setImageResource(R.drawable.star_semiwhite);
					}
				}
                break;
				case Constants.TWOSTAR:
				{
					holder.RatingStar2.setImageResource(R.drawable.star_gold);
                    holder.RatingStar1.setImageResource(R.drawable.star_gold);
					if(rating_float>rating_int)
					{
						holder.RatingStar3.setImageResource(R.drawable.star_semiwhite);
					}
				}
                break;
				case Constants.ONESTAR:
				{
					holder.RatingStar1.setImageResource(R.drawable.star_gold);
					if(rating_float>rating_int)
					{
						holder.RatingStar2.setImageResource(R.drawable.star_semiwhite);
					}
				}
				break;
			}
		}
		vi.setOnClickListener(new OnItemClickListener(pos));
		
		return vi;
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
	}
	
	private class OnItemClickListener implements OnClickListener {

		private int position;
		
		OnItemClickListener(int pos) {
			position = pos;
		}
		
		@Override
		public void onClick(View v) {
			MainActivity sct = (MainActivity) activity;
			sct.onItemClick(position);
		}
	}
}
