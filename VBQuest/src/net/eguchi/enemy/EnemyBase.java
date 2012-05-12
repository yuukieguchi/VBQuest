package net.eguchi.enemy;

import android.graphics.Point;
import net.eguchi.player.Player;
import net.eguchi.vbquest.MainView;
import net.eguchi.vbquest.Map;
import net.eguchi.vbquest.SpriteObj;

public class EnemyBase extends SpriteObj{

	protected int enemyLife; //敵のHP
	protected Player p;//プレイヤー
		
    protected int ptn;//敵の行動パターン
    protected int leftX;//左　当たり判定修正
    protected int rightX;//右　当たり判定修正
    protected int upY;//上　当たり判定修正
	
    protected int animeCnt;//カウンター
	protected int enemyCnt;//カウンター
	
	public EnemyBase(MainView v, double x, double y, Map map) {
		super(v, x, y, map);
		v.getEnemyList().add(this);		
		p = v.getPlayer();		
		//初期化値を設定
		InitX = x;
		InitY = y;		
		//初期化処理
		Init();					
	}
	
	//初期化用メソッド
	protected void Init(){
		iX 		= InitX;
		iY 		= InitY;	
		CutX 	= 0;	
		CutY	= LEFT;
		onGround = false;//最初は空中とみなす
		ptn = 0;//パターン[行動分岐用]			    
	    enemyCnt = 0;	//動作用カウント
	    animeCnt = 0;	//アニメーションカウント
	    invtime	 = 0;	//無敵カウント		
	}
		
	// 踏まれたとき[各々で違い]
	public void EnemyJumpHit(){		
		
		//踏むとプレイヤーは再ジャンプ
		p.setForceJump(true);
		//飛ぶ
		p.Forcejump();
		
	}
	
	
	//プレイヤーに接触した場合の処理
	public void EnemyHit(){}

	
	//共通
	public void jump(){
		if (onGround) {
			vy = -JUMP_SPEED;// 上向きに速度を加える
			onGround = false;//空中
		}	
	}
	

//アップデートの詳細はスプライトクラスに明記
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
		enemyCnt++;//カウンター[敵の行動パターン作成用]	
		animeCnt++;//アニメーション用カウンター

	      
        //ボスが攻撃を受けた場合、一定時間無敵
		if( invtime > 0 ){ invtime++; }
		if( invtime >= 40 ){ invtime = 0; }
		
		//画面底に落ちたとき、初期化
	     if( this.iY >= MainView.MAP_HEIGHT - SIZE_Y ){Init();}

	}
			
	//基本的にボス用[パターンの設定]
	public void Pattern(){	}
	

	//無敵状態
	public void Invisible(){
		
				
		
	}
	

}
