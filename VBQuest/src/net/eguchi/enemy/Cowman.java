package net.eguchi.enemy;

import net.eguchi.vbquest.Img;
import net.eguchi.vbquest.MainView;
import net.eguchi.vbquest.Map;
import android.graphics.Point;
import android.graphics.Rect;

public class Cowman extends EnemyBase{
			
	public Cowman(MainView v ,double x, double y ,Map map) {
		// 親クラスのコンストラクタを実行する
		super( v, x, y, map );
		this.v= v;
		this.map = map;
		this.iX = x;
		this.iY = y;

		//初期化処理
		Init();	

		SIZE_X 		= MainView.bX;// 画像サイズ X
		SIZE_Y 		= MainView.bY;// 画像サイズ Y
		//[ 2 ] 画像サイズ取得
		iWidth 	= v.getImgWidth(Img.cowman);
		iHeight = v.getImgHeight(Img.cowman);

	}
	
	//初期化用メソッド
	protected void Init(){	
		iX 		= InitX;
		iY 		= InitY;	
		CutX 	= 0;	
		CutY	= LEFT;

		SPEED 		=  5; //移動速度
		JUMP_SPEED	= 10; //ジャンプ力
	
		ptn = 0;//パターン[行動分岐用]			    
	    enemyCnt   = 0;	//動作用カウント
	    animeCnt   = 0;	//アニメーションカウント
	    invtime	   = 0;	//無敵カウント			
		enemyLife  = 2;// 敵2回踏まなきゃだめ
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

		//アニメーション
		if(invtime%2 == 0){v.drawBitmap(Img.cowman, src, dst);}

	}


	//プレイヤーに接触した場合の処理
	@Override
	public void EnemyHit(){

		//敵が攻撃を受けていない状態であれば、ダメージを受ける
		if(this.invtime==0){
			v.setHP( v.getHP()-7 );
			//敵が攻撃を受けている状態ではプレイヤーは点滅しない	
		}else{
			p.setInvTime(0);
		}

	}


	// 踏まれたとき
	public void EnemyJumpHit(){

		//敵、無敵中でなければライフ -1
		if(invtime==0){
			//踏むとプレイヤーは再ジャンプ
			p.setForceJump(true);
			//飛ぶ
			p.Forcejump();
			enemyLife--;
			invtime = 1;
			
			this.SPEED = 14;//敵、加速する
		}		


		//敵 死亡時
		if( enemyLife <= 0 ){
						
			//リストから　踏んだ敵を　取り除く[消滅]		
			v.getEnemyList().remove(this);		

			//経験値獲得
			v.setEXP( v.getEXP() +6 );
		}
			
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
			/**落ちそうになった時、自動的に反転*/
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

	      //攻撃を受けた場合、一定時間無敵
		if( invtime > 0 ){ invtime++; }
		if( invtime >= 40 ){invtime = 0;}
		
		
		
		
		enemyCnt++;//カウンター[敵の行動パターン作成用]	
		animeCnt++;//アニメーション用カウンター

		//画面底に落ちたとき、初期化
	     if( this.iY >= MainView.MAP_HEIGHT - SIZE_Y ){Init();}

	     
	     
	     
	}//update終了	


}
