package net.eguchi.vbquest;


import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.view.SurfaceHolder;

public class GraphicsUtility {

	private SurfaceHolder holder;
	private Paint paint;
	private Canvas canvas;

	public GraphicsUtility(SurfaceHolder holder) {
		this.holder = holder;
		paint = new Paint();
		canvas = new Canvas();
		paint.setAntiAlias(true);

	}

	public void lock() {
		canvas = holder.lockCanvas();
	}

	public void unlock() {
		holder.unlockCanvasAndPost(canvas);
	}


	public void setColor(int color) {
		paint.setColor(color);
	}

	public void setFontSize(int fontSize) {
		paint.setTextSize(fontSize);
	}

	public int stringWidth(String string) {
		return (int) paint.measureText(string);
	}

	public void fillRect(int x, int y, int w, int h) {
		paint.setStyle(Paint.Style.FILL);
		canvas.drawRect(new Rect( x , y , (x + w) , (y + h) ), paint);

	}
	
	public void drawLine(int x1 ,int y1 , int x2 , int y2 ){
		canvas.drawLine(x1, y1, x2, y2, paint);
	}

	public void Triangle(int x1,int y1, int x2, int y2 , int x3 ,int y3){
		paint.setStyle(Paint.Style.FILL);
		Path path = new Path();
		 path.moveTo(x1 ,y1);
	     path.lineTo(x2 ,y2); 
	     path.lineTo(x3 ,y3);
	 }
	
	public void drawBitmap(Bitmap bitmap,Rect src , Rect dst ) {
		canvas.drawBitmap( bitmap , src , dst , null );
	}

	
	public void drawString(String string, int x, int y) {
		canvas.drawText( string , x , y , paint );
	}


	public int pixelsToTiles(double pixels) {

		return (int)Math.floor( pixels / MainView.bX );

	}

	public int tileToPixelX(int tiles) {
		return ( ( tiles * MainView.bX ) );
	}
	
	public int tileToPixelY(int tiles) {
		return ( ( tiles * MainView.bY )  );
	}

	public String scoreNumber(int num) {
		StringBuffer buf = new StringBuffer();
		int keta = ("" + num).length();
		int n = 3;

		for (int i = 0; i < (n - keta); i++) { 
			buf.append(0);
		}
		buf.append(num);
		return buf.toString();
	}

	
	public boolean BoxHit(int x11 , int y11 , int x12 , int y12 , 
			int x21 , int y21 , int x22 , int y22) {
		if (x11 > x22) {
			return (false);
		}
		if (x12 < x21) {
			return (false);
		}
		if (y11 > y22) {
			return (false);
		}
		if (y12 < y21) {
			return (false);
		}
		return (true);
	}// BoxHit
}