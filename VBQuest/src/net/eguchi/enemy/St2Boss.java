package net.eguchi.enemy;

import net.eguchi.vbquest.Img;
import net.eguchi.vbquest.MainView;
import net.eguchi.vbquest.Map;
import net.eguchi.vbquest.Sound;
import net.eguchi.vbquest.SpriteObj;
import android.graphics.Rect;

public class St2Boss extends EnemyBase{


	public St2Boss(MainView v, double x, double y, Map map) {
		super(v, x, y, map);
		this.map = map;
		this.iX = x;
		this.iY = y;
		Init();
		SIZE_X 		= MainView.bX*6; // 画像サイズ X
		SIZE_Y 		= MainView.bY*4; // 画像サイズ Y
		iWidth = v.getImgWidth(Img.boss_slime);
		iHeight =  v.getImgHeight(Img.boss_slime);
	
	}

	//初期化用メソッド
	protected void Init(){
		iX 			= InitX;
		iY 			= InitY;	
		CutX	 	= 0;	
		CutY		= LEFT;
		
		leftX = 20;
		rightX =20;
		upY		 =10;
		
		ptn = 0;//パターン[行動分岐用]			    
	    enemyCnt = 0;	//動作用カウント
	    animeCnt = 0;	//アニメーションカウント
	    invtime	 = 0;	//無敵カウント		
	
	    enemyLife = 7;// 敵 HP	
	}
	
	public void onBitmap(int offsetX, int offsetY){

		Rect src = new Rect( iWidth/3 * CutX , 0 , 
				 iWidth/3*(CutX+1) , iHeight );

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
		if(invtime%2 == 0){	v.drawBitmap(Img.boss_slime, src, dst);}

		
	}
	//プレイヤーに接触した場合の処理
	@Override
	public void EnemyHit(){

		//敵が攻撃を受けていない状態であれば、ダメージを受ける
		if(this.invtime==0){
			v.setHP(v.getHP()-9);
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

			//ピンチの時大量
			for(int i =0; i< ( 7 - enemyLife) ; i++){
				new St2miniBoss( v ,this.iX , this.iY +MainView.bY ,map) ; 
			}
			
		//踏むほど小さくなっていく
			SIZE_X -= MainView.bX/2;
			SIZE_Y -= MainView.bY/3 ;
		

			invtime = 1;
		}		


		//敵 死亡時
		if( enemyLife <= 0 ){
			//リストから　踏んだ敵を　取り除く[消滅]		
			v.getEnemyList().remove(this);	
			
			//経験値獲得
			v.setEXP(v.getEXP()+20);
			
			//ボス倒したので、STAGE クリアー
			MainView.stageClearFlg = true;
			
			//効果音
			v.getSound(Sound.bossend);

		}

	}


	public void update(){
		super.update();
  
         //□□行動パターン□□-------------------------- 
        //0:移動中 1:停止中 2:ジャンプ 3:ダッシュ
        switch(ptn){
        case 0:
        	//歩く処理
        	SPEED = random.nextInt( 3 ) +3 ;

        	if( random.nextInt( 30 ) == 0 ){
        		enemyCnt = 0;
        		ptn = ( random.nextInt( 4 ) +1 );//ランダムで 1 2 3 4 へ移行     	
        	} 
        	break;

        case 1:
        	SPEED = 20;//ダッシュ
        	if( enemyCnt > 20){
        		enemyCnt = 0;
        		ptn = random.nextInt( 1 )+2;// 2 3
        	}     	            	
        	break;

        case 2:
        	vx = -vx;
        	ptn = 0;//通常状態に戻る
        	break; 	
 
        case 3:
        	SPEED = random.nextInt( 15 )+2;
        	if( enemyCnt > 30){
        		enemyCnt = 0;
        		ptn = 0;
        	}        	
        	break;
        	
        case 4:
        	SPEED = 0;//停止 >> 0へ移行
        	JUMP_SPEED = 15;
        	if( enemyCnt > 50){
        		super.jump();
        		enemyCnt = 0;
        		ptn = 0;
        	}        	
        	break;
        }//--------------------------------------------------

        
  
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