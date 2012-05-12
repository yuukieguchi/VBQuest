package net.eguchi.item;

import net.eguchi.vbquest.Img;
import net.eguchi.vbquest.MainView;
import net.eguchi.vbquest.Map;
import net.eguchi.vbquest.Sound;
import android.graphics.Rect;

public class RecoveryItem extends ItemBase{
			
public RecoveryItem(MainView v ,int x, int y ,Map map) {
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
			iWidth = v.getImgWidth(Img.recover);
			iHeight =  v.getImgHeight(Img.recover);
			
			this.invtime = 0;	//アイコン表示用
				    
	}
		
		
		public void onBitmap(int offsetX, int offsetY){
			
			Rect src = new Rect( 0,0, iWidth , iHeight);			
			Rect dst = new Rect
			((int) iX + offsetX ,
			 (int) iY + offsetY, 
			 (int) iX + offsetX + SIZE_X,
		     (int) iY + offsetY + SIZE_Y);
					
			v.drawBitmap(Img.recover, src, dst);

			if(invtime >0){v.drawIcon(0, 2, (int)p.getX(),(int)p.getY());}
		}
		
		//プレイヤーに接触した場合の処理
		@Override
		public void ItemHit(){

			/**（注）HPが全快状態でなければ、これら全ての処理を行う*/	
			if( v.getHP() !=  MainView.LifeMax[v.getLEVEL()] ){//---------

				//まず最初にリストからアイテムを取り除く（何度も処理させないために）
				v.getItemList().remove(this);

				//プレイヤーのHP回復			
				v.setHP(v.getHP()+10);
				
				//効果音
				v.getSound(Sound.recovery);
				
				//ただし、全快の場合は最大までしか回復しない
				if(v.getHP() > MainView.LifeMax[v.getLEVEL()]){
					v.setHP(MainView.LifeMax[v.getLEVEL()] );			
				}

				//このアイテムを取得した場合、一定時間アイコン表示
				if(invtime < 50){
					this.invtime++;						
				}else{
					invtime = 0;//初期化
				}		
			}//-------------------------------------------------------------

		}
		
		
		public void update(){
			super.update();
		}
		
}
