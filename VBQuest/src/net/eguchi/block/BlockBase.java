package net.eguchi.block;

import net.eguchi.vbquest.MainView;
import net.eguchi.vbquest.Map;
import net.eguchi.vbquest.SpriteObj;

public class BlockBase extends SpriteObj{


	protected boolean hitFlg;// ブロック接触したかどうか
	protected boolean stepFlg;// ブロック踏んだかどうか

	protected boolean breaked;// ブロック壊れたかどうか
	protected int breakCnt;	
	
	public BlockBase(MainView v ,double x, double y ,Map map) {
		// 親クラスのコンストラクタを実行する
		super( v, x, y, map );
		this.v= v;
		this.map = map;
		InitX = x;
		InitY = y;
		
		//初期化処理
		Init();
	}

	//初期化用メソッド
	protected void Init(){
		iX 		= InitX;
		iY 		= InitY;
	}

	public void onBitmap(int offsetX, int offsetY){}
	
	//プレイヤーに接触した場合の処理
	public void BoxHit(){}

	// 踏まれたとき
	public void EnemyJumpHit(){}
	
	//update
	public void update(){}
	



}
