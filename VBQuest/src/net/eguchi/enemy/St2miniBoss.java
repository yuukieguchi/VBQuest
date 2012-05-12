package net.eguchi.enemy;

import net.eguchi.vbquest.Img;
import net.eguchi.vbquest.MainView;
import net.eguchi.vbquest.Map;
import android.graphics.Point;
import android.graphics.Rect;

public class St2miniBoss extends EnemyBase{

		
	public St2miniBoss(MainView v ,double x, double y ,Map map) {
		// 親クラスのコンストラクタを実行する
		super( v, x, y, map );
		this.v= v;
		this.map = map;
		InitX = x;
		InitY = y;
		Init();

		SIZE_X 		= MainView.bX;// 画像サイズ X
		SIZE_Y 		= ( MainView.bY/2 );// 画像サイズ Y
		iWidth = v.getImgWidth(Img.slime);
		iHeight =  v.getImgHeight(Img.slime);		
}
	
	//初期化用メソッド
	protected void Init(){	
		iX 		= InitX;
		iY 		= InitY;	
		CutX 	= 0;	
		CutY	= LEFT;
	
		enemyCnt = 0;	//動作用カウント
		animeCnt = 0;	//アニメーションカウント

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

		v.drawBitmap(Img.slime, src, dst);


	}
	

//プレイヤーに接触した場合の処理
	@Override
	public void EnemyHit(){
				
		//ダメージ
		v.setHP(v.getHP()-3);

	}

	// 踏まれたとき
	public void EnemyJumpHit(){

		super.EnemyJumpHit();
		
	      	//経験値 獲得
	      	v.setEXP(v.getEXP()+2);

	      	//　リストから敵を　取り除く　[消滅]		
			v.getEnemyList().remove(this);		
				
	}
	
	
	public void update(){
		vy += Map.GRAVITY;

		//移動処理
		if(CutY == LEFT){
			vx = -SPEED;//左に移動
		}else{
			vx = SPEED;//右に移動
		}   

		//X座標の更新処理----------------------------------------
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
			vx = -vx;//衝突時、自動的に反転[凡敵に共通]
		}

		//Y座標の更新処理----------------------------------------
		double newY = iY + vy;
		tile = map.getTileCollision(this, iX, newY , true );
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

			JUMP_SPEED	= random.nextInt( 15 ) +5; //ジャンプ力
			SPEED = random.nextInt( 5 ) +1;//のろのろ

			if( random.nextInt( 30 ) == 0 ){
				enemyCnt = 0;
				ptn = ( random.nextInt( 1 )+1);//ランダムで 1 2 移行     	
			} 
			break;

		case 1:
			
			SPEED =  random.nextInt( 12 ) +3;//ダッシュ			

			if(enemyCnt > 30){
				vx = -vx;//反転2				
				ptn = 2;
			}
			break;

		case 2:        	
			SPEED = 0;//停止
			if(enemyCnt > 20 ){
				super.jump();   
				ptn = 0;
			}
			
			break;

		}

		enemyCnt++;//カウンター[敵の行動パターン作成用]	
		animeCnt++;//アニメーション用カウンター

	}//update終了	


}

/**
 * オーバーライドしなければ、
 * 親クラスの動作をする
 * */
 