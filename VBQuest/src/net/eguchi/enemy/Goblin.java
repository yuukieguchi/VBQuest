package net.eguchi.enemy;

import net.eguchi.vbquest.Img;
import net.eguchi.vbquest.MainView;
import net.eguchi.vbquest.Map;
import android.graphics.Point;
import android.graphics.Rect;

public class Goblin extends EnemyBase{

	public Goblin(MainView v ,double x, double y ,Map map) {
		super( v, x, y, map );
		this.v= v;
		this.map = map;
		this.iX = x;
		this.iY = y;

		Init();	
		SIZE_X 		= MainView.bX;
		SIZE_Y 		= MainView.bY;		 

		iWidth 	= v.getImgWidth(Img.goblin);
		iHeight = v.getImgHeight(Img.goblin);

	}

	protected void Init(){	
		iX 		= InitX;
		iY 		= InitY;	
		CutX 	= 0;	
		CutY	= LEFT;
		SPEED 		=  3;

		ptn = 0;
		enemyCnt = 0;
		animeCnt = 0;

	}

	public void onBitmap(int offsetX, int offsetY){

		Rect src = new Rect( iWidth/3 * CutX , 
				iHeight/2 * CutY , 
				iWidth/3*(CutX+1) , 
				iHeight /2 * (CutY+1) );			
		Rect dst = new Rect
				((int) iX + offsetX ,
						(int) iY + offsetY, 
						(int) iX + offsetX + SIZE_X,
						(int) iY + offsetY + SIZE_Y);

		if( vx > 0 ){ CutY = RIGHT;}
		if( vx < 0 ){ CutY = LEFT; }

		if (animeCnt% 20 == 0) {CutX++;}	
		if (CutX == 3) {CutX = 0;}

		v.drawBitmap(Img.goblin, src, dst);
	}

	@Override
	public void EnemyHit(){
		v.setHP(v.getHP()-5);
	}

	public void EnemyJumpHit(){
		super.EnemyJumpHit();

		v.setEXP(v.getEXP()+3);
		v.getEnemyList().remove(this);		
	}



	public void update(){
		vy += Map.GRAVITY;

		if(CutY == LEFT){
			vx = -SPEED;
		}else{
			vx = SPEED;
		}   

		double newX = iX + vx;
		Point tile = map.getTileCollision(this, newX, iY ,true);
		if (tile == null) {
			iX = newX;
		} else {
			if (vx > 0) {
				iX = Map.tilesToPixelsX(tile.x) - SIZE_X;
			} else if (vx < 0) {
				iX = Map.tilesToPixelsX(tile.x + 1);
			}
			vx = -vx;
		}

		double newY = iY + vy;
		tile = map.getTileCollision(this, iX, newY , true );
		if (tile == null) {
			vx = -vx;
		} else {
			if (vy > 0) {
				iY = Map.tilesToPixelsY(tile.y) - SIZE_Y;
				vy = 0;
				onGround = true;
			} else if (vy < 0) {
				iY = Map.tilesToPixelsY(tile.y + 1);
				vy = 0;
			}

		}
		enemyCnt++;
		animeCnt++;
		if( this.iY >= MainView.MAP_HEIGHT - SIZE_Y ){Init();}
	}
}
