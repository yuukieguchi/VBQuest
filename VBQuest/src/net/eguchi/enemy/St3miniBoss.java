package net.eguchi.enemy;

import net.eguchi.vbquest.Img;
import net.eguchi.vbquest.MainView;
import net.eguchi.vbquest.Map;
import android.graphics.Point;
import android.graphics.Rect;

public class St3miniBoss extends EnemyBase{


	public St3miniBoss(MainView v, double x, double y, Map map) {
		super(v, x, y, map);
		this.map = map;
		this.iX = x;
		this.iY = y;
		Init();
		
		SIZE_X 		= MainView.bX; // 画像サイズ X
		SIZE_Y 		= MainView.bY; // 画像サイズ Y
		iWidth = v.getImgWidth(Img.st3boss);
		iHeight =  v.getImgHeight(Img.st3boss);
	
				
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
		if ( animeCnt % 3 == 0) {CutX++;}	
		if (CutX == 3) {CutX = 0;}

		
		//敵と衝突したとき、プレイヤーが点滅
		if(invtime %2 == 0){	v.drawBitmap(Img.st3boss, src, dst);}

		
	}
	//プレイヤーに接触した場合の処理
	@Override
	public void EnemyHit(){

		//敵が攻撃を受けていない状態であれば、ダメージを受ける
		if(this.invtime==0){
			v.setHP(v.getHP()-5);
			//敵が攻撃を受けている状態ではプレイヤーは点滅しない	
		}else{
			p.setInvTime(0);
		}
		
	}

	// ボス、踏まれたとき
	public void EnemyJumpHit(){

		super.EnemyJumpHit();
			//リストから　踏んだ敵を　取り除く[消滅]		
			v.getEnemyList().remove(this);								

			//経験値 獲得
			v.setEXP(v.getEXP()+4);

	}

	
	public void update(){
		//こいつは常に浮いているので、重力を掛けないようにする
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

			SPEED = random.nextInt( 5 ) +1;//のろのろ

			if( random.nextInt( 30 ) == 0 ){
				enemyCnt = 0;
				ptn = 1;// 1 移行     	
			} 
			break;

		case 1:

			SPEED =  random.nextInt( 12 ) + 5;//ダッシュ			

			if(enemyCnt > 50){
				ptn = 0;
			}
			break;
		}
	
	
		enemyCnt++;//カウンター[敵の行動パターン作成用]	
		animeCnt++;//アニメーション用カウンター

		//画面底に落ちたとき、初期化
	     if( this.iY >= MainView.MAP_HEIGHT - SIZE_Y ){Init();}		
	
	}
	
	@Override
	public boolean CheckInMap(){		
		return true;
	}

}