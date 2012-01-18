package org.mikeSkei.battleship;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

public class ImageAdapter extends BaseAdapter {
	private Context mContext;
	private ImageAdapter img = null;

	
	 public ImageAdapter(Context c) {
	        mContext = c;
	 }


	public int getCount() {
		 return mThumbIds.length;
	}

	public Integer getItem(int position) {
		return mThumbIds[position];
	}

	public long getItemId(int position) {
		return 0;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		 ImageView imageView;
	        if (convertView == null) {  // if it's not recycled, initialize some attributes
	            imageView = new ImageView(mContext);
	        } else {
	            imageView = (ImageView) convertView;
	        }

	        imageView.setImageResource(mThumbIds[position]);
	        return imageView;
	}
	
	
	
	 
	
    private Integer[] mThumbIds = {
          R.drawable.square4,R.drawable.square4,R.drawable.square4,R.drawable.square4,
          R.drawable.square4,R.drawable.square4,R.drawable.square4,R.drawable.square4,
          R.drawable.square4,R.drawable.square4,R.drawable.square4,R.drawable.square4,
          R.drawable.square4,R.drawable.square4,R.drawable.square4,R.drawable.square4,
          R.drawable.square4,R.drawable.square4,R.drawable.square4,R.drawable.square4,
          R.drawable.square4,R.drawable.square4,R.drawable.square4,R.drawable.square4,
          R.drawable.square4,R.drawable.square4,R.drawable.square4,R.drawable.square4,
          R.drawable.square4,R.drawable.square4,R.drawable.square4,R.drawable.square4,
          R.drawable.square4,R.drawable.square4,R.drawable.square4,R.drawable.square4,
          R.drawable.square4,R.drawable.square4,R.drawable.square4,R.drawable.square4,
          R.drawable.square4,R.drawable.square4,R.drawable.square4,R.drawable.square4,
          R.drawable.square4,R.drawable.square4,R.drawable.square4,R.drawable.square4,
          R.drawable.square4,R.drawable.square4,R.drawable.square4,R.drawable.square4,
          R.drawable.square4,R.drawable.square4,R.drawable.square4,R.drawable.square4,
          R.drawable.square4,R.drawable.square4,R.drawable.square4,R.drawable.square4,
          R.drawable.square4,R.drawable.square4,R.drawable.square4,R.drawable.square4,
          R.drawable.square4,R.drawable.square4,R.drawable.square4,R.drawable.square4,
          R.drawable.square4,R.drawable.square4,R.drawable.square4,R.drawable.square4,
          R.drawable.square4,R.drawable.square4,R.drawable.square4,R.drawable.square4,
          R.drawable.square4,R.drawable.square4,R.drawable.square4,R.drawable.square4,
          R.drawable.square4,R.drawable.square4,R.drawable.square4,R.drawable.square4,
          R.drawable.square4,R.drawable.square4,R.drawable.square4,R.drawable.square4,
          R.drawable.square4,R.drawable.square4,R.drawable.square4,R.drawable.square4,
          R.drawable.square4,R.drawable.square4,R.drawable.square4,R.drawable.square4,
          R.drawable.square4,R.drawable.square4,R.drawable.square4,R.drawable.square4,
          R.drawable.square4,R.drawable.square4,R.drawable.square4,R.drawable.square4,
          R.drawable.square4,R.drawable.square4,R.drawable.square4,R.drawable.square4,
          R.drawable.square4,R.drawable.square4,R.drawable.square4,R.drawable.square4
            
    };
    
// 11 * 11


	public void setItem(int i, int colorResource) {
		mThumbIds[i] = colorResource;		
	}


}
