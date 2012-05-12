package net.eguchi.player;

import java.util.ArrayList;

import net.eguchi.enemy.EnemyBase;
import net.eguchi.item.ItemBase;
import net.eguchi.vbquest.Img;
import net.eguchi.vbquest.MainView;
import net.eguchi.vbquest.Map;
import net.eguchi.vbquest.Sound;
import net.eguchi.vbquest.SpriteObj;
import android.graphics.Rect;

public class Player extends SpriteObj{
	protected final int jump_tuning = 5;
	protected int playerCnt;
	protected final int 	STAND  = 0,
			MOVE   = 1,
			DASH   = 2;

	protected final int 	LEFT   = 0,
			RIGHT  = 1,
			FRONT  = 2,
			BACK   = 3;

	PlayerEnd end;

	public Player(MainView v ,double x, double y ,Map map) {
		super(v, x, y, map);

		this.map = map;
		this.iX = x;
		this.iY = y;

		//	SIZE_X 		= (int)(((double)(MainView.bX)/10)*7);
		SIZE_X		= MainView.bX;
		//	SIZE_Y 		= (int)(((double)(MainView.bY)/10)*9);
		SIZE_Y 		= MainView.bY;

		SPEED 		= 8;
		JUMP_SPEED	= 20; 
		vx 			= 0;
		vy 			= 0;

		inr 		= 0;

		onGround 	= false;
		forceJump 	= false;

		CutX 	= STAND;
		CutY 	= RIGHT;

		iWidth 	= v.getImgWidth(Img.player);
		iHeight = v.getImgHeight(Img.player);

		this.invtime = 0;
		playerCnt = 0;
	}

	public void stop() {
		vx 	= 0;
	}

	public void accelL() {
		CutY  =  LEFT;
		vx 	 = -SPEED;
	}

	public void accelR() {
		CutY  = RIGHT;
		vx 	 = SPEED;				
	}

	public void jump() {

		if ( onGround ) {
			if(MainView.upTouch ){
				vy = -(JUMP_SPEED);
			}		    	
			else if(MainView.halfupTouch ){
				vy = -( JUMP_SPEED - jump_tuning );
			}		    	
			onGround = false;
			forceJump = false;
			v.getSound(Sound.jump);
		}			
	}

	public void Forcejump() {
		if (  forceJump  ) {
			vy = - (JUMP_SPEED - jump_tuning);			
			v.getSound(Sound.forcejump);

			onGround = false;
			forceJump = false;
		}			
	}

	public void update(){ 
		super.update();

		ArrayList<ItemBase> ilist = v.getItemList();		
		for(int i = 0 ; i < ilist.size() ; i++){
			if(ilist.get(i).BoxHit((SpriteObj)this)){
				ilist.get(i).ItemHit();
			}
		}

		ArrayList<EnemyBase> elist = v.getEnemyList();		
		for(int i = 0 ; i < elist.size() ; i++){
			if( elist.get(i).BoxHit((SpriteObj)this) && this.invtime == 0 ){				
				//if(  onGround == false && vy > 0 ){
				if(  this.JumpHit(elist.get(i)) ){
					elist.get(i).EnemyJumpHit();
				}else{
					this.invtime = 1;
					elist.get(i).EnemyHit();
				}
			}
		}
		if(invtime == 1){v.getSound(Sound.damege);}
		if( this.invtime > 0 ){ this.invtime++; }
		if( this.invtime >= 20 ){this.invtime = 0;}

		if( vx == 0 ){
			CutX = STAND; 
		}else if( onGround==false ){
			CutX = DASH;
		}			
		if( Math.abs(vx) > 0 && onGround == true ){
			if ( playerCnt % 5 == 0) {CutX++;}
			if (CutX == 3) {CutX = STAND;}
		}							


		if ( getY() >= MainView.MAP_HEIGHT - SIZE_Y || v.getHP() <= 0 ||
				(MainView.lastTime <= 0 && MainView.stageClearFlg == false) ) {					

			v.setPLAY_SCENE(MainView.GAMEOVER);
			v.setPlayer(new PlayerEnd(v, iX, iY , map));

			if( v.getLIFE() == 1 ){
				v.getSound(Sound.gameover);
			}else{
				v.getSound(Sound.over);
			}
		}
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

		if(invtime%2 == 0){	v.drawBitmap(Img.player , src  , dst );}

		playerCnt++;
	}	

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

	/**
	 * @param forceJump The forceJump to set.
	 */
	public void setForceJump(boolean forceJump) {
		this.forceJump = forceJump;
	}

	public boolean JumpHit(EnemyBase enemy){
		if( this.vy > 10 ){		
			if( this.iY < enemy.getY() && onGround == false){
				return true;
			}
		}else{
			if( vy > 0 &&this.iY + (SIZE_Y/2) < enemy.getY() && onGround == false){
				return true;		
			}
		}
		return false;
	}
	/**
	 * @param set SIZE_X
	 * */
	public void setInvTime(int x){
		this.invtime = x;
	}	
}
