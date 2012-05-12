package net.eguchi.enemy;

import net.eguchi.vbquest.Img;
import net.eguchi.vbquest.MainView;
import net.eguchi.vbquest.Map;
import net.eguchi.vbquest.Sound;
import net.eguchi.vbquest.SpriteObj;
import android.graphics.Point;
import android.graphics.Rect;

public class St3Boss extends EnemyBase{


	public St3Boss(MainView v, double x, double y, Map map) {
		super(v, x, y, map);
		this.map = map;
		this.iX = x;
		this.iY = y;
		Init();
		
		SIZE_X 		= MainView.bX*4; // 画像サイズ X
		SIZE_Y 		= MainView.bY*4; // 画像サイズ Y
		iWidth = v.getImgWidth(Img.st3boss);
		iHeight =  v.getImgHeight(Img.st3boss);
	
				
	}

	//初期化用メソッド
	protected void Init(){
		iX 		= InitX;
		iY 		= InitY;	
		CutX 	= 0;	
		CutY	= LEFT;

		leftX 	= 15;
		rightX 	= 15;
		upY 	= 5;//上方判定修正

		ptn 	 = 0;//パターン[行動分岐用]			    
	    enemyCnt = 0;	//動作用カウント
	    animeCnt = 0;	//アニメーションカウント
	    invtime	 = 0;	//無敵カウント	
	    enemyLife = 5;// 敵 HP	
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
		if(invtime %2 == 0){v.drawBitmap(Img.st3boss, src, dst);}

		
	}

	//プレイヤーに接触した場合の処理
	@Override
	public void EnemyHit(){

		//敵が攻撃を受けていない状態であれば、ダメージを受ける
		if(this.invtime==0){
			v.setHP(v.getHP()-10);
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

			//ちょっと落ちる
			this.iY +=30;

			new St3miniBoss( v ,this.iX , this.iY ,map) ;

			invtime = 1;
		}		


		//画面底に落としたとき(か、ライフがなくなったら)、勝ち
		if( enemyLife <= 0  || this.iY  >= MainView.MAP_HEIGHT - this.SIZE_Y - MainView.bY *2  ){

			//リストから　踏んだ敵を　取り除く[消滅]		
			v.getEnemyList().remove(this);		

			//経験値獲得
			v.setEXP(v.getEXP()+18);

			//ボス倒したので、STAGE クリアー
			MainView.stageClearFlg = true;

			//効果音
			v.getSound(Sound.bossend);

		}

	}

	//常に浮いているので、重力を掛けないようにする
	public void update(){

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
		enemyCnt++;//カウンター[敵の行動パターン作成用]	
		animeCnt++;//アニメーション用カウンター

	      
        //ボスが攻撃を受けた場合、一定時間無敵
		if( invtime > 0 ){ invtime++; }
		if( invtime >= 40 ){ invtime = 0; }
		
	
		//□□行動パターン□□-------------------------- 
        //0:移動中 1:停止中 2:ジャンプ 3:ダッシュ
        switch(ptn){
        case 0:
        	//移動処理
        	SPEED = random.nextInt( 15 ) +3 ;

        	if( random.nextInt( 30 ) == 0 ){
        		enemyCnt = 0;
        		ptn = ( random.nextInt( 3 ) +1 );//1 2 3 4 へ     	
        	} 
        	break;

        case 1:
        	SPEED = random.nextInt( 3 ) +1;//のんびり

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
        	
        	SPEED = 0;
        	if( enemyCnt > 30){      		
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