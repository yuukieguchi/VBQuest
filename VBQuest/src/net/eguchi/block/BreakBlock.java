package net.eguchi.block;

import net.eguchi.vbquest.Img;
import net.eguchi.vbquest.MainView;
import net.eguchi.vbquest.Map;
import android.graphics.Rect;

public class BreakBlock extends BlockBase{
	
	public BreakBlock(MainView v ,double x, double y ,Map map) {
		// 親クラスのコンストラクタを実行する
		super( v, x, y, map );
		this.v= v;
		this.map = map;
		//初期化値を設定
		InitX = x;
		InitY = y;
		
		//初期化処理
		Init();
	    	    
		SIZE_X 		= MainView.bX;// 画像サイズ X
		SIZE_Y 		= MainView.bY;// 画像サイズ Y
	    		 
	    //画像分割用
		CutX 	= 0;
		CutY 	= 0;
	    		

		//[ 2 ] 画像サイズ取得
		iWidth = v.getImgWidth(Img.break_block);
		iHeight =  v.getImgHeight(Img.break_block);

		stepFlg = false;
		breaked = false; 

		breakCnt = 0;	//消滅までのカウント

	}
	
	//初期化用メソッド
	protected void Init(){
		iX 		= InitX;
		iY 		= InitY;
	}

public void onBitmap(int offsetX, int offsetY){
		
		Rect src = new Rect();
			
		Rect dst = new Rect
		((int) iX + offsetX ,
		 (int) iY + offsetY, 
		 (int) iX + offsetX + SIZE_X,
	     (int) iY + offsetY + SIZE_Y);
			
		//30 [ 1 ]
		if(breakCnt < 30) CutY=0;
		//30~60 [ 2 ]
		if(breakCnt > 30 && breakCnt < 60 ) CutY =1;
		//60~90 [ 3 ]崩れかけ	
		if(breakCnt >60 && breakCnt < 90) CutY =2;

		if(breakCnt < 90){
		}
		src.set( 0 ,iHeight/3 * CutY,iWidth , iHeight/3*(CutY+1) );		
		v.drawBitmap(Img.break_block, src, dst);
}
	

//プレイヤーに接触した場合の処理
public void BlockHit(){
	
	//リストから 取り除く
	v.getBlockList().remove(this);		
			
	}

	// 踏んだとき
	public void BlockUnderHit(){
	      			
	}
	
	
	public void update(){
		
		//接触した場合。
		if(stepFlg == true){
			breakCnt++;			
		}

		if( breakCnt >= 90 ){
			//ブロック壊れました。
			breaked = true;
		}

        
	}//update終了	
	
	
	//壊れたかどうか
	public boolean Breaked(){
		return breaked;
	}

}
