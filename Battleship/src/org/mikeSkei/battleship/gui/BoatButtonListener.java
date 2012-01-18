package org.mikeSkei.battleship.gui;

import org.mikeSkei.battleship.R;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;

public class BoatButtonListener implements OnClickListener {

	public void onClick(View v) {
		
		ImageButton button = (ImageButton) v;
		button.setImageResource(R.drawable.boat2pressed);
		
	}

}
