package net.eguchi.player;

import net.eguchi.vbquest.Img;
import net.eguchi.vbquest.MainView;
import net.eguchi.vbquest.Map;
import android.graphics.Rect;

public class PlayerClear extends Player{

	public PlayerClear(MainView v, double x, double y , Map map) {
		super(v, x, y, map);

		CutX 	= STAND;
		CutY 	= FRONT;		
	}

	public void onBitmap(int offsetX, int offsetY) {

		Rect src = new Rect( iWidth/3 * CutX , 
				iHeight/4 * CutY , 
				iWidth/3*(CutX+1) , 
				iHeight /4 * (CutY+1) );

		Rect dst = new Rect
				((int) iX + offsetX ,
						(int) iY + offsetY, 
						(int) iX + offsetX + SIZE_X,
						(int) iY + offsetY + SIZE_Y);

		v.drawBitmap(Img.player , src  , dst );

	}


	public void update(){

		vy += Map.GRAVITY*2;

		double newY = iY + vy;
		tile = map.getTileCollision(this, iX, newY);
		if (tile == null) {       	
			iY = newY;        
			onGround = false;
		} else {
			if (vy > 0) {
				iY = Map.tilesToPixelsY(tile.y) - SIZE_Y;
				onGround = true;            
				vy = 0;
			} else if (vy < 0) {
				iY = Map.tilesToPixelsY(tile.y + 1);
				vy = 0;
			}
		}
	}


}
