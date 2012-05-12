package net.eguchi.enemy;

import net.eguchi.vbquest.Img;
import net.eguchi.vbquest.MainView;
import net.eguchi.vbquest.Map;
import net.eguchi.vbquest.Sound;
import net.eguchi.vbquest.SpriteObj;
import android.graphics.Rect;

public class St4Boss extends EnemyBase{


	public St4Boss(MainView v, double x, double y, Map map) {
		super(v, x, y, map);
		this.map = map;
		this.iX = x;
		this.iY = y;
		Init();

		SIZE_X 		= MainView.bX*4; // 画像サイズ X
		SIZE_Y 		= MainView.bY*5; // 画像サイズ Y
		iWidth = v.getImgWidth(Img.st4boss);
		iHeight =  v.getImgHeight(Img.st4boss);
	
	}
	
	//初期化用メソッド
	protected void Init(){
		iX 		= InitX;
		iY 		= InitY;	
		CutX 	= 0;	
		CutY	= LEFT;
		
		//当たり判定修正
		leftX 	= 10;
		rightX 	= 10;
		upY 	= 1;//上方いる？
		
		ptn = 0;//パターン[行動分岐用]			    
	    enemyCnt = 0;	//動作用カウント
	    animeCnt = 0;	//アニメーションカウント
	    invtime	 = 0;	//無敵カウント	
	    
	    enemyLife	 	= 5;	// 敵HP
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

		//敵と衝突したとき、プレイヤーが点滅
		if(invtime%3 == 0){	v.drawBitmap(Img.st4boss, src, dst);}
	
		
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
			super.EnemyJumpHit();;

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
			v.setEXP(v.getEXP()+18);
			
			//ボス倒したので、STAGE 1 クリアー
			MainView.stageClearFlg = true;
		}

	}


	public void update(){
		super.update();

		JUMP_SPEED	= random.nextInt( 10 ) +10; //ジャンプ力

		//☆ ボスの行動パターン 
  
        if(ptn == 0){
        	//歩く処理
        	SPEED = random.nextInt( 10 )+1;        	
        	//他行動への移行
            if( random.nextInt( 40 ) == 0 ){
            	enemyCnt = 0;
            	ptn = ( random.nextInt( 3 )+1 );//1 2      	
            } 
        }

        //
        else if(ptn == 1){
        	
        	SPEED = 0;//停止
        	if( enemyCnt > 30){
        		//ハイジャンプ
        		JUMP_SPEED	= 30;
        		super.jump();
        		enemyCnt = 0;
        		ptn = 0;//2 3   
        	}
        }
        //
        
        else if(ptn == 2){
        	//ランダムジャンプ力
    		super.jump();
    		ptn = 0;
        }
        
        //棒立ち
        else if(ptn == 3){
        	//走る処理
        	SPEED = 25;

        	//歩くへの戻り
        	if( enemyCnt > 10){
        		vx = -vx;
        		ptn = 0;
        	}
        }
        
        enemyCnt++;
        
		//ボスが攻撃を受けた場合、一定時間無敵
		if( invtime > 0 ){ invtime++; }
		//このフレーム間無敵
		if( invtime >= 40 ){invtime = 0;}

	}

	
	@Override
	public boolean CheckInMap(){
		return true;

	}

	//ボス用[ 当たり判定の修正 ]
	@Override
	public boolean BoxHit(SpriteObj obj){

		double sx = this.iX;
		double ex = this.iX+this.SIZE_X;	
		
		double sy = this.iY;
		double ey = this.iY+this.SIZE_Y;

		//向き
		if(CutY == RIGHT){
			sx += leftX;//左[修正]
		}else{
			ex -= rightX;//右[修正]
		}
		sy += upY;//上[修正]
				
		return BoxHit(sx, sy, ex, ey,
				obj.getX(), obj.getY(), obj.getX()+obj.getSIZE_X(), obj.getY()+obj.getSIZE_Y());
	
	}




}