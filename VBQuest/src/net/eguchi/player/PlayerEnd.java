package net.eguchi.player;

import net.eguchi.vbquest.Img;
import net.eguchi.vbquest.MainView;
import net.eguchi.vbquest.Map;
import android.graphics.Color;
import android.graphics.Rect;

public class PlayerEnd extends Player{

	public PlayerEnd(MainView v, double x, double y , Map map) {
		super(v, x, y, map);

		CutX 	= STAND;
		CutY 	= FRONT;		
		this.invtime = 0;
	}

	public void onBitmap(int offsetX, int offsetY) {

		v.getUtil().setColor(Color.argb(150,0,0,0));
		v.getUtil().fillRect(0,0,MainView.screenX ,MainView.screenY);


		Rect src = new Rect( iWidth/3 * CutX , 
				iHeight/4 * CutY , 
				iWidth/3*(CutX+1) , 
				iHeight /4 * (CutY+1) );

		Rect dst = new Rect
				((int) iX + offsetX ,
						(int) iY + offsetY, 
						(int) iX + offsetX + SIZE_X,
						(int) iY + offsetY + SIZE_Y);


		if ( invtime % 5 == 0) {CutY++;}	
		if ( CutY == 4) {CutY = 0;}
		if( invtime%2 == 0 ){v.drawBitmap(Img.player , src  , dst );}

	}

	public void update(){ 	
		iY -= 5;
		invtime++;
	}

}
