package net.eguchi.enemy;

import net.eguchi.vbquest.Img;
import net.eguchi.vbquest.MainView;
import net.eguchi.vbquest.Map;
import net.eguchi.vbquest.SpriteObj;
import android.graphics.Rect;

public class St5Boss extends EnemyBase{


	public St5Boss(MainView v, double x, double y, Map map) {
		super(v, x, y, map);
		this.map = map;
		this.iX = x;
		this.iY = y;
		Init();

		SIZE_X 		= MainView.bX*4; // 画像サイズ X
		SIZE_Y 		= MainView.bY*5; // 画像サイズ Y
		iWidth = v.getImgWidth(Img.enemy000);
		iHeight =  v.getImgHeight(Img.enemy000);
	
	}
	
	//初期化用メソッド
	protected void Init(){
		iX 		  = InitX;
		iY 		  = InitY;	
		CutX 	  = 0;	
		CutY	  = LEFT;
		ptn 	  = 0;//パターン[行動分岐用]			    
	    enemyCnt  = 0;	//動作用カウント
	    animeCnt  = 0;	//アニメーションカウント
	    invtime	  = 0;	//無敵カウント	
	    
	    enemyLife = 6;	// 敵HP
	}
	


	public void onBitmap(int offsetX, int offsetY){

		Rect src = new Rect(iWidth/3 * CutX , 
				iHeight/4 * CutY , 
				iWidth/3*(CutX+1) , 
				iHeight /4 * (CutY+1) );

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

		//敵と衝突したとき、プレイヤーが点滅
		if(invtime%3 == 0){	v.drawBitmap(Img.enemy000, src, dst);}
	
		
	}
	//プレイヤーに接触した場合の処理
	@Override
	public void EnemyHit(){

		//敵が攻撃を受けていない状態であれば、ダメージを受ける
		if(this.invtime==0){
			v.setHP(v.getHP()-18);
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

			//最後のボス倒したので、エンディングへ
			MainView.stageClearFlg = true;
		}

	}


	public void update(){
		super.update();
		
		JUMP_SPEED	= random.nextInt( 5 ) +1; //ジャンプ力


        //☆ ボスの行動パターン 
        //0:考え中　1:実行中 
  
        if(ptn == 0){
        	//歩く処理
        	SPEED = random.nextInt( 8 ) +1;
        	
        	//他行動への移行
            if( random.nextInt( 30 ) == 0 ){
            	enemyCnt = 0;
            	ptn = ( random.nextInt( 2 )+1 );// 1 2 3     	
            } 
        }

        else if(ptn == 1){
        	//
        	SPEED = 0;
        	
        	if( enemyCnt > 30){
        		vx = -vx;
        		enemyCnt = 0;
        		ptn = ( random.nextInt( 1 )+2 );// 2 3 
        	}
        }
        
        //
        else if(ptn == 2){
        	//ジャンプ処理
        	super.jump();
        	ptn = 0;
        }
        //棒立ち
        else if(ptn == 3){
        	//走る処理
        	SPEED = 20;

        	//歩くへの戻り
        	if( enemyCnt > 40){
        		ptn = 0;
        	}
        }
        enemyCnt++;

	}

	
	@Override
	public boolean CheckInMap(){
		return true;

	}

	@Override
	public boolean BoxHit(SpriteObj obj){
		double sx = this.iX;
		double ex = this.iX+this.SIZE_X;
		//向きを見る
		if(CutY == RIGHT){
			sx += 10;
		}else{
			ex -= 10;
		}
		return BoxHit(sx, this.iY, ex, this.iY+this.SIZE_Y,
				obj.getX(), obj.getY(), obj.getX()+obj.getSIZE_X(), obj.getY()+obj.getSIZE_Y());
	}



}