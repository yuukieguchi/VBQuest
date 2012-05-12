package net.eguchi.enemy;

import net.eguchi.vbquest.Img;
import net.eguchi.vbquest.MainView;
import net.eguchi.vbquest.Map;
import net.eguchi.vbquest.Sound;
import net.eguchi.vbquest.SpriteObj;
import android.graphics.Rect;

public class St1Boss extends EnemyBase{

	//キツネ
	public St1Boss(MainView v, double x, double y, Map map) {
		super(v, x, y, map);
		this.map = map;
		this.iX = x;
		this.iY = y;
		Init();

		SIZE_X 		= MainView.bX*4; // 画像サイズ X
		SIZE_Y 		= MainView.bY*5; // 画像サイズ Y
		iWidth  = v.getImgWidth(Img.St1boss);
		iHeight = v.getImgHeight(Img.St1boss);
	}
	
	//初期化用メソッド
	protected void Init(){
		iX 		= InitX;
		iY 		= InitY;	
		CutX 	= 0;	
		CutY	= LEFT;
		SPEED 		=  8; //移動速度	
		ptn = 0;//パターン[行動分岐用]			    
	    enemyCnt = 0;	//動作用カウント
	    animeCnt = 0;	//アニメーションカウント
	    invtime	 = 0;	//無敵カウント	
	    
	    enemyLife	 	= 3;// 敵 HP
	}


	public void onBitmap(int offsetX, int offsetY){

		Rect src = new Rect(iWidth/3 * CutX , 
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

		//アニメーション
		if ( animeCnt % 20 == 0) {CutX++;}	
		if (CutX == 3) {CutX = 0;}

		//衝突したとき、点滅
		if(invtime%2 == 0){	v.drawBitmap(Img.St1boss, src, dst);}
	
		
	}
	//プレイヤーに接触した場合の処理
	@Override
	public void EnemyHit(){

		//敵が攻撃を受けていない状態であれば、ダメージを受ける
		if(this.invtime==0){
			v.setHP(v.getHP()-8);
			//敵が攻撃を受けている状態ではプレイヤーは点滅しない	
		}else{
			p.setInvTime(0);
		}
		
	}

	// ボス、踏まれたとき
	public void EnemyJumpHit(){

		//敵、無敵中でなければライフ -1
		if(invtime==0){

			super.EnemyJumpHit();

			enemyLife--;
			invtime = 1;
		}		


		//敵 死亡時
		if( enemyLife <= 0 ){
						
			//リストから　踏んだ敵を　取り除く[消滅]		
			v.getEnemyList().remove(this);		

			//効果音
			v.getSound(Sound.bossend);
			
			//経験値獲得
			v.setEXP(v.getEXP()+16);
						
			//ボス倒したので、STAGE 1 クリアー
			MainView.stageClearFlg = true;
		}

	}


	public void update(){
		super.update();
		
		JUMP_SPEED	=  ( random.nextInt( 20 ) +1 );; //ジャンプ力

        //□□行動パターン□□-------------------------- 
        //0:移動中 1:停止中 2:ジャンプ 3:ダッシュ
        switch(ptn){
        case 0:
        	//歩く処理
        	SPEED = 5;
        	//他行動への移行
        	if( random.nextInt( 20 ) == 0 ){
        		enemyCnt = 0;
        		ptn = ( random.nextInt( 2 )+1 );// 1 2 3 どれか     	
        	} 
        	break;
        case 1:

        	SPEED = 15;//ダッシュ
        	if( enemyCnt > 15){
        		enemyCnt = 0;
        		ptn = ( random.nextInt( 1 ) + 2 );// 2 か 3
        	}     	            	
        	break;

        case 2:
        	//ジャンプする
        	super.jump();
        	ptn = 0;//通常状態に戻る
        	break; 	
        
        case 3:
        	//停止
        	SPEED = 0;
        	//歩くへの戻り
        	if( enemyCnt > 10){
        		ptn = 0;
        	}        	
        	break;
        }//--------------------------------------------------
                
		
	}

	
	@Override
	public boolean CheckInMap(){
		return true;

	}

	@Override
	public boolean BoxHit(SpriteObj obj){
		double sx = this.iX;
		double ex = this.iX+this.SIZE_X;
		//当たり判定修正
		if(CutY == RIGHT){
			sx += 5;
		}else{
			ex -= 5;
		}
		return BoxHit(sx, this.iY, ex, this.iY+this.SIZE_Y,
				obj.getX(), obj.getY(), obj.getX()+obj.getSIZE_X(), obj.getY()+obj.getSIZE_Y());
	}



}