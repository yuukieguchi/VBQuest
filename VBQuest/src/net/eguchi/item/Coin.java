package net.eguchi.item;

import android.graphics.Rect;
import net.eguchi.vbquest.Img;
import net.eguchi.vbquest.MainView;
import net.eguchi.vbquest.Map;
import net.eguchi.vbquest.Sound;

public class Coin extends ItemBase{
		
	
	public Coin(MainView v ,int x, int y ,Map map) {
		// 親クラスのコンストラクタを実行する
		super( v, x, y, map );

		this.v= v;
		this.map = map;
		this.iX = x;
	    this.iY = y;
			    
	    // 画像サイズ X 80%
		SIZE_X 		= (MainView.bX/10)*8;
		// 画像サイズ Y 90%
		SIZE_Y 		= (MainView.bY/10)*8;

		
		CutX = 0; //アニメーション用
	    		
		//[ 2 ] 画像サイズ取得
		iWidth = v.getImgWidth(Img.coin);
		iHeight =  v.getImgHeight(Img.coin);
	    
	    
}
	
	
	public void onBitmap(int offsetX, int offsetY){
		
		Rect src = new Rect();			
		Rect dst = new Rect
		((int) iX + offsetX ,
		 (int) iY + offsetY, 
		 (int) iX + offsetX + SIZE_X,
	     (int) iY + offsetY + SIZE_Y);
		
		
		src.set( iWidth/2 * CutX ,0, iWidth/2 *(CutX+1) , iHeight);
		
		//アニメーション
		if ( itemCnt % 30 == 0) { CutX++; }	
		if ( CutX == 2 ) { CutX = 0; }
		v.drawBitmap(Img.coin, src, dst);

		
	}
	
	//プレイヤーに接触した場合の処理
	@Override
	public void ItemHit(){
		
		//効果音
		v.getSound(Sound.coin);
		//リストから 取り除く
		v.getItemList().remove(this);
		
		//得点を増やす
		v.setMoney( v.getMoney()+1);
				
	}
	
	public void update(){
		super.update();
	}


}

/**
 * オーバーライドしなければ、
 * 親クラスの動作をする
 * */
 