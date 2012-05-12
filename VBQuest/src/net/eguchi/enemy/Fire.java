package net.eguchi.enemy;

import net.eguchi.vbquest.Img;
import net.eguchi.vbquest.MainView;
import net.eguchi.vbquest.Map;
import net.eguchi.vbquest.SpriteObj;
import android.graphics.Rect;

public class Fire extends EnemyBase{

		
	public Fire(MainView v ,double x, double y ,Map map) {
		// 親クラスのコンストラクタを実行する
		super( v, x, y, map );
		this.v= v;
		this.map = map;

		//初期化処理
		Init();	

		//[ 2 ] 画像サイズ取得
		iWidth = v.getImgWidth(Img.fire);
		iHeight =  v.getImgHeight(Img.fire);
		
}
	
	//初期化用メソッド
	protected void Init(){	

		iX 		= InitX;
		iY 		= InitY;

		SIZE_X 		= MainView.bX*2-10;// 画像サイズ X
		SIZE_Y 		= ( MainView.bY*2 );// 画像サイズ Y	    		

		//ゆっくり落ちてくる
		vy = random.nextInt( 7 ) +5;

		CutX 	= 0;	
		CutY	= LEFT;

		//当たり判定修正
		leftX 	= 20;
		rightX 	= 20;
		upY 	= 20;
		
		ptn = 0;//パターン[行動分岐用]			    
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

		//アニメーション
		if ( animeCnt % 20 == 0) {CutX++;}	
		if (CutX == 3) {CutX = 0;}

		v.drawBitmap(Img.fire, src, dst);

	}
	

	//プレイヤーに接触した場合の処理
	@Override
	public void EnemyHit(){
				
		//ダメージ
		v.setHP(v.getHP()-8);

	}


	//こいつは無敵
	public void EnemyJumpHit(){}
	
	
	public void update(){

		

		//Y座標の更新処理----------------------------------------
		double newY = iY + vy;
		tile = map.getTileCollision(this, iX, newY , true );
		if (tile == null) {
			iY = newY;        
		} else {
			//タイルに衝突したとき。	
			SIZE_Y   -= MainView.bY/2;
			this.iY  += MainView.bY/2;
			if(SIZE_Y<0){//4フレームで消滅
				Init();
			}
		}

		//画面底に落ちたとき、初期化
		if( this.iY >= MainView.MAP_HEIGHT ){Init();}
        
	     enemyCnt++;//カウンター[敵の行動パターン作成用]	
	     animeCnt++;//アニメーション用カウンター

	}//update終了	

	
	public boolean BoxHit(SpriteObj obj){

		double sx = this.iX;
		double ex = this.iX+this.SIZE_X;	
		
		double sy = this.iY;
		double ey = this.iY+this.SIZE_Y;
	
			sx += leftX;//左[修正]
			ex -= rightX;//右[修正]
			
			sy += upY;
				
		return BoxHit(sx, sy, ex, ey,
				obj.getX(), obj.getY(), obj.getX()+obj.getSIZE_X(), obj.getY()+obj.getSIZE_Y());
	
	}

	
}

 