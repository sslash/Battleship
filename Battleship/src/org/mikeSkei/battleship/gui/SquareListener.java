package org.mikeSkei.battleship.gui;

import org.mikeSkei.battleship.ImageAdapter;
import org.mikeSkei.battleship.R;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;

public class SquareListener implements OnItemClickListener {
	ImageAdapter imageAdapter;
	
	public SquareListener( ImageAdapter img) {
		imageAdapter = img;
	}
	
	public void onItemClick(AdapterView<?> arg0, View arg1, int pos, long id) {
		imageAdapter.setItem(pos, R.drawable.boat2);
		imageAdapter.notifyDataSetChanged();
		
	}


}
