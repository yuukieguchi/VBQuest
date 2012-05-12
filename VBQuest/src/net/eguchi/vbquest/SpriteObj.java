package net.eguchi.vbquest;

import java.util.Random;

import android.graphics.Point;

public class SpriteObj {

	protected double InitX,InitY;

	protected Point tile;

	protected int SIZE_X,SIZE_Y;

	protected int SPEED 		;
	protected int JUMP_SPEED 	;
	protected double iX,iY;

	protected int iWidth,iHeight;

	protected int CutX,CutY; 

	protected double vx,vy;

	protected double inr;

	protected final int  LEFT  = 0,
			RIGHT = 1;

	protected boolean onGround,	
	forceJump;

	protected Map map;   
	protected MainView v; 

	protected Random random;			
	protected int invtime;
	protected int animeCnt;


	public SpriteObj(MainView v ,double x, double y ,Map map) {

		this.v = v;
		this.map = map;
		this.iX = x;
		this.iY = y;


		random = new Random();

	}

	public void stop() {}

	public void accelL() {}


	public void accelR() {}

	public void jump(){}


	public void update(){ 

		vy += Map.GRAVITY;

		int newX = (int) (iX + vx);
		tile = map.getTileCollision(this, newX, iY);
		if (tile == null) {
			iX = newX;
		} else {
			if ( vx > 0) { 
				iX = Map.tilesToPixelsX(tile.x) - SIZE_X;            

			} else if (vx < 0) {
				iX = Map.tilesToPixelsX(tile.x + 1);  
			}
			vx = 0;
		}

		int newY = (int) (iY + vy);
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
				iY = Map.tilesToPixelsY( tile.y +1 );
				vy = 0;
			}
		}
	}

	public void onBitmap(int offsetX, int offsetY) {}

	public void setX(double x){
		iX = x;
	}

	public int getX(){
		return (int)iX; 
	}


	public void setY(double y){
		iY = y;
	}

	public int getY(){
		return (int)iY;
	}


	public int getWidth(){
		return iWidth;
	}

	public int getSIZE_X(){
		return SIZE_X;
	}

	public int getDir(){
		return CutY;
	}

	public int getHeight(){
		return iHeight;
	}

	public int getSIZE_Y(){
		return SIZE_Y;
	}


	public boolean GFlg(){
		return onGround;
	}

	public double getVX(){
		return vx;
	}

	public double getVY(){
		return vy;
	}

	public boolean getOnG(){
		return	onGround;
	} 	


	public boolean BoxHit(SpriteObj obj){
		return BoxHit(this.iX, this.iY, this.iX+this.SIZE_X, this.iY+this.SIZE_Y,
				obj.iX, obj.iY, obj.iX+obj.SIZE_X, obj.iY+obj.SIZE_Y);
	}


	public boolean BoxHit(double x11 , double y11 , double x12 , double y12 , 
			double x21 , double y21 , double x22 , double y22) {
		return BoxHit((int) x11 , (int) y11 , (int) x12 , (int) y12 , 
				(int) x21 , (int) y21 , (int) x22 , (int) y22);
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
	}

	public boolean CheckInMap(){
		if ( -v.getOffsetX() - SIZE_X < this.iX  && - v.getOffsetX() + MainView.screenX > this.iX) {
			return true;			
		}
		return false;
	}

}
