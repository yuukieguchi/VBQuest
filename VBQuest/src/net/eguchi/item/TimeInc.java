package net.eguchi.item;

import net.eguchi.vbquest.Img;
import net.eguchi.vbquest.MainView;
import net.eguchi.vbquest.Map;
import net.eguchi.vbquest.Sound;
import android.graphics.Rect;

public class TimeInc extends ItemBase{
			
public TimeInc(MainView v ,int x, int y ,Map map) {
			// 親クラスのコンストラクタを実行する
			super( v, x, y, map );

			this.v= v;
			this.map = map;
			this.iX = x;
		    this.iY = y;
				    
		    // 画像サイズ X 80%
			SIZE_X 		= MainView.bX;
			// 画像サイズ Y 
			SIZE_Y 		= MainView.bY;
		    		
			//[ 2 ] 画像サイズ取得
			iWidth = v.getImgWidth(Img.clock);
			iHeight =  v.getImgHeight(Img.clock);
			
			this.invtime = 0;	//アイコン表示用
				    
	}
		
		
		public void onBitmap(int offsetX, int offsetY){
			
			Rect src = new Rect( 0,0, iWidth , iHeight);			
			Rect dst = new Rect
			((int) iX + offsetX ,
			 (int) iY + offsetY, 
			 (int) iX + offsetX + SIZE_X,
		     (int) iY + offsetY + SIZE_Y);
					
			v.drawBitmap(Img.clock, src, dst);

			if(invtime >0){v.drawIcon(0, 2, (int)p.getX(),(int)p.getY());}
		}
		
		//プレイヤーに接触した場合の処理
		@Override
		public void ItemHit(){

				//まず最初にリストからアイテムを取り除く（何度も処理させないために）
				v.getItemList().remove(this);

				//制限時間回復			
				v.setLoop( v.getLoop()-300 );

				//効果音
				v.getSound(Sound.recovery);
				
				//ただし、loopCntは100以下にはならない（開始ロゴ表示させない）
				if( v.getLoop() < 100){
					v.setLoop(100);
				}

		}
		
		
		public void update(){
			super.update();
		}
		
}
