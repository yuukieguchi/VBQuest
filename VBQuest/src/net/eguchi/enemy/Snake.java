package net.eguchi.enemy;

import net.eguchi.vbquest.Img;
import net.eguchi.vbquest.MainView;
import net.eguchi.vbquest.Map;
import android.graphics.Point;
import android.graphics.Rect;

public class Snake extends EnemyBase{


	public Snake(MainView v ,double x, double y ,Map map) {
		super( v, x, y, map );
		this.v= v;
		this.map = map;

		Init();

		SIZE_X 		= MainView.bX;
		SIZE_Y 		= ( MainView.bY/2 );
		iWidth = v.getImgWidth(Img.snake);
		iHeight =  v.getImgHeight(Img.snake);

	}

	protected void Init(){	
		iX 		= InitX;
		iY 		= InitY;
		CutX 	= 0;
		CutY	= LEFT;
		onGround 	= false;
		ptn = 0;
		enemyCnt = 0;
		animeCnt = 0;

	}

	public void onBitmap(int offsetX, int offsetY){

		Rect src = new Rect( iWidth/3 * CutX ,iHeight/2 * CutY ,  
				iWidth/3*(CutX+1) , iHeight/2*(CutY+1) );

		Rect dst = new Rect
				((int) iX + offsetX ,
						(int) iY + offsetY, 
						(int) iX + offsetX + SIZE_X,
						(int) iY + offsetY + SIZE_Y);

		if( vx > 0 ){ CutY = RIGHT;}
		if( vx < 0 ){ CutY = LEFT; }

		if ( animeCnt % 20 == 0) {CutX++;}	
		if (CutX == 3) {CutX = 0;}

		v.drawBitmap(Img.snake, src, dst);
	}

	@Override
	public void EnemyHit(){
		v.setHP(v.getHP()-6);
	}

	public void EnemyJumpHit(){
		super.EnemyJumpHit();

		v.setEXP(v.getEXP()+3);	      	
		v.getEnemyList().remove(this);		
	}

	public void update(){
		vy += Map.GRAVITY;
		SPEED 		= random.nextInt(  5 ) + 1;
		JUMP_SPEED	= random.nextInt( 10 ) + 5;

		if(CutY == LEFT){
			vx = -SPEED;
		}else{
			vx = SPEED;
		}   

		double newX = iX + vx;
		Point tile = map.getTileCollision(this, newX, iY);
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
		tile = map.getTileCollision(this, iX, newY);
		if (tile == null) {       	
			iY = newY;        
			onGround = false;
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


		switch(ptn){
		case 0:
			SPEED =  random.nextInt( 9 )+1 ;
			if( random.nextInt( 30 ) == 0 ){
				ptn = 1;    	
			} 
			break;
		case 1:
			super.jump();
			ptn = 0;
			break;
		}
		if( this.iY >= MainView.MAP_HEIGHT - SIZE_Y ){Init();}
	}
}

