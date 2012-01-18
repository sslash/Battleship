package org.mikeSkei.battleship;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.Window;
import android.widget.Toast;

public class BattleshipActivity extends Activity {
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(new Panel(this, 8));
	}

	class Panel extends SurfaceView implements SurfaceHolder.Callback {
		private boolean notDrawed = true;
		private TutorialThread _thread;
		private ArrayList<GraphicObject> _graphics = new ArrayList<GraphicObject>();
		private Map<String, GraphicObject> boats = new HashMap<String, GraphicObject>();
		private int squareWidth;
		private int squareHeight;
		private int rows;
		private int columns;
		private int screenHeight;
		private int screenWidth;
		private int boatStartX;
		private int boatStartY;
		private int nBoats = 3; // should be adjustable
		private GraphicObject boatPressed = null;
		private boolean boatPicked = false;

		private int getColumnSize() {
			return screenHeight - (rows * squareHeight);
		}

		public Panel(Context context, int nrows) {
			super(context);
			this.rows = nrows;
			this.columns = rows;
			setInitLocations();
			createSquares();
			createBoats();

			getHolder().addCallback(this);
			_thread = new TutorialThread(getHolder(), this);
			setFocusable(true);
		}

		private void setInitLocations() {
			// Set square sizes
			Display display = getWindowManager().getDefaultDisplay();
			screenWidth = display.getWidth();
			screenHeight = display.getHeight();
			squareWidth = screenWidth / rows;
			squareHeight = squareWidth;

			// set boat sizes
			boatStartY = getColumnSize() + (screenHeight - getColumnSize()) / 2;
			boatStartX = screenWidth / 2;
		}

		private void createBoats() {

			for (int i = 0; i < nBoats; i++) {

				/* Decode the image */
				Bitmap bitmapOrg = BitmapFactory.decodeResource(getResources(),
						R.drawable.boat2);

				/* Create the graphic object and with its coordinates */
				GraphicObject graphicz = new GraphicObject(bitmapOrg, "boat:"
						+ i);
				int sWidth = graphicz.getGraphic().getWidth(); // Picture width
				int sHeight = graphicz.getGraphic().getHeight(); // Picture
																	// height
				graphicz.getCoordinates().setXstart(boatStartX);
				graphicz.getCoordinates().setYstart(boatStartY + i * sWidth);
				graphicz.getCoordinates().setYend(
						graphicz._bitmap.getHeight() + boatStartY + i * sWidth);
				graphicz.getCoordinates().setXend(
						graphicz._bitmap.getWidth() + boatStartX);

				boats.put(graphicz.getName(), graphicz);
				_graphics.add(graphicz);
			}
		}

		private void createSquares() {
			// create squares
			for (int y = 0; y < columns; y++)
				for (int i = 0; i < rows; i++) {

					// Scale image
					Bitmap bitmapOrg = BitmapFactory.decodeResource(
							getResources(), R.drawable.square4);
					int width = bitmapOrg.getWidth();
					int height = bitmapOrg.getHeight();

					// calculate the scale
					float scaleWidth = ((float) squareWidth) / width;
					float scaleHeight = scaleWidth;

					// createa matrix for the manipulation
					Matrix matrix = new Matrix();
					// resize the bit map
					matrix.postScale(scaleWidth, scaleHeight);

					// recreate the new Bitmap
					Bitmap resizedBitmap = Bitmap.createBitmap(bitmapOrg, 0, 0,
							width, height, matrix, true);
					GraphicObject graphicz = new GraphicObject(resizedBitmap,
							"square:" + y + ":" + i);
					int sWidth = graphicz.getGraphic().getWidth();
					int sHeight = graphicz.getGraphic().getHeight();

					// Set the coordinates of the image
					graphicz.getCoordinates().setXstart(i * sWidth);
					graphicz.getCoordinates().setYstart(y * sWidth);
					graphicz.getCoordinates().setXend(i * sWidth + sWidth);
					graphicz.getCoordinates().setYend(y * sWidth + sWidth);

					_graphics.add(graphicz);
				}

		}

		@Override
		public boolean onTouchEvent(MotionEvent event) {

			synchronized (_thread.getSurfaceHolder()) {
				if (event.getAction() == MotionEvent.ACTION_DOWN) {

					if (boatPicked) {
						GraphicObject gObj = getPressedObject(event.getX(),
								event.getY());
						
						if ( gObj != null ){
							gObj._bitmap = BitmapFactory.decodeResource(
									getResources(), R.drawable.boat2);
							//boatPressed = null;
							boatPicked = false;
					}

					// We have pressed a boat
					} else {
						GraphicObject gObj = getPressedObject(event.getX(),
								event.getY());
						if (gObj != null) {

							// Mark the boat
							gObj._bitmap = BitmapFactory.decodeResource(
									getResources(), R.drawable.boat2pressed);

							// Save the boat, so we know which boat we pressed
							// the last time
							boatPressed = gObj;
							boatPicked = true;
						}

						Context context = getApplicationContext();
						CharSequence text = "width = " + event.getX()
								+ " height = " + event.getY();
						int duration = Toast.LENGTH_LONG;

						Toast toast = Toast.makeText(context, text, duration);
						toast.show();
					}

					/*
					 * GraphicObject graphic = new
					 * GraphicObject(BitmapFactory.decodeResource
					 * (getResources(), R.drawable.square4));
					 * graphic.getCoordinates().setX((int) event.getX() -
					 * graphic.getGraphic().getWidth() / 2);
					 * graphic.getCoordinates().setY((int) event.getY() -
					 * graphic.getGraphic().getHeight() / 2);
					 * _graphics.add(graphic);
					 */

				}
				return true;
			}

			// return true;
		}

		private GraphicObject getPressedObject(float x, float y) {
			for (String g : boats.keySet()) {

				GraphicObject gr = boats.get(g);
				if (gr.checkIfCoordsWrap(x, y))
					return gr;

				/*
				 * Log.i("boat", "x = " +gr.getCoordinates().xstart + " y = " +
				 * gr._coordinates.ystart + "xsize = " +
				 * gr.getGraphic().getWidth() + "ysize = " +
				 * gr.getGraphic().getHeight() + " bitmap height = " +
				 * gr._bitmap.getHeight() + " bitmap width = " +
				 * gr._bitmap.getWidth() +
				 * 
				 * " xstart = " + gr.getCoordinates().xstart +" xend = " +
				 * gr.getCoordinates().xend + " ystart = " +
				 * gr.getCoordinates().ystart + " yend = " +
				 * gr.getCoordinates().yend);
				 */
			}

			return null;
		}

		@Override
		public void onDraw(Canvas canvas) {

			// canvas.drawColor(R.drawable.background);

			Bitmap _scratch = BitmapFactory.decodeResource(getResources(),
					R.drawable.background);
			canvas.drawBitmap(_scratch, 0, 0, null);
			Bitmap bitmap;
			GraphicObject.Coordinates coords;

			for (GraphicObject graphic : _graphics) {
				bitmap = graphic.getGraphic();
				coords = graphic.getCoordinates();
				canvas.drawBitmap(bitmap, coords.getXstart(),
						coords.getYstart(), null);
			}

		}

		// @Override
		public void surfaceChanged(SurfaceHolder holder, int format, int width,
				int height) {
			// TODO Auto-generated method stub
		}

		// @Override
		public void surfaceCreated(SurfaceHolder holder) {
			_thread.setRunning(true);
			_thread.start();
		}

		// @Override
		public void surfaceDestroyed(SurfaceHolder holder) {
			// simply copied from sample application LunarLander:
			// we have to tell thread to shut down & wait for it to finish, or
			// else
			// it might touch the Surface after we return and explode
			boolean retry = true;
			_thread.setRunning(false);
			while (retry) {
				try {
					_thread.join();
					retry = false;
				} catch (InterruptedException e) {
					// we will try it again and again...
				}
			}
		}
	}

	class TutorialThread extends Thread {
		private SurfaceHolder _surfaceHolder;
		private Panel _panel;
		private boolean _run = false;

		public TutorialThread(SurfaceHolder surfaceHolder, Panel panel) {
			_surfaceHolder = surfaceHolder;
			_panel = panel;
		}

		public void setRunning(boolean run) {
			_run = run;
		}

		public SurfaceHolder getSurfaceHolder() {
			return _surfaceHolder;
		}

		@Override
		public void run() {
			Canvas c;
			while (_run) {
				c = null;
				try {
					c = _surfaceHolder.lockCanvas(null);
					synchronized (_surfaceHolder) {
						_panel.onDraw(c);
					}
				} finally {
					// do this in a finally so that if an exception is thrown
					// during the above, we don't leave the Surface in an
					// inconsistent state
					if (c != null) {
						_surfaceHolder.unlockCanvasAndPost(c);
					}
				}
			}
		}
	}

	class GraphicObject {
		/**
		 * Contains the coordinates of the graphic.
		 */
		public class Coordinates {
			private int xstart;
			private int ystart;
			private int xend;
			private int yend;

			public int getXstart() {
				return xstart; // + _bitmap.getWidth() / 2;
			}

			public void setXstart(int value) {
				xstart = value;// - _bitmap.getWidth() / 2;
			}

			public int getYstart() {
				return ystart;// + _bitmap.getHeight() / 2;
			}

			public void setYstart(int value) {
				ystart = value;// - _bitmap.getHeight() / 2;
			}

			public int getXend() {
				return xend;
			}

			public void setXend(int xend) {
				this.xend = xend;
			}

			public int getYend() {
				return yend;
			}

			public void setYend(int yend) {
				this.yend = yend;
			}

			public String toString() {
				return "Coordinates: (" + xstart + "/" + ystart + ")";
			}
		}

		private Bitmap _bitmap;
		private Coordinates _coordinates;

		public String getName() {
			return name;
		}

		public boolean checkIfCoordsWrap(float x, float y) {
			if (x <= _coordinates.xend && x >= _coordinates.xstart
					&& y <= _coordinates.yend && y >= _coordinates.ystart)
				return true;

			return false;

		}

		public void setName(String name) {
			this.name = name;
		}

		/* name of the graphic object */
		private String name;

		public GraphicObject(Bitmap bitmap, String name) {
			_bitmap = bitmap;
			_coordinates = new Coordinates();
			this.name = name;
		}

		public Bitmap getGraphic() {
			return _bitmap;
		}

		public Coordinates getCoordinates() {
			return _coordinates;
		}
	}
}
