package net.eguchi.demo_scene;

import net.eguchi.vbquest.Img;
import net.eguchi.vbquest.MainView;
import android.graphics.Color;

public class St1ClearView extends MainDemo{

	// コンストラクタ
	public St1ClearView(MainView v) {
		super(v);
		
		//プレイヤー
		iX 	= -MainView.bX;
		iY 	= MainView.bY * 6;
		ivx	= 2;//移動
		iCutX 	= STAND;
		iCutY 	= RIGHT;
		demoCnt = 0; //ループカウンター
		STEP 	= 0;
	}

	@Override
	public boolean DemoPlay() {
		super.DemoPlay();
						
		// ■背景[次のステージの背景
		view.drawBitmapEasy(Img.st2back, view.getImgWidth(Img.st2back), 
				view.getImgHeight(Img.st2back), 0, 0, MainView.screenX , MainView.screenY);
		
		demoPlayer();
				

		if(ivx > 0){
			if (demoCnt % 10 == 0) {iCutX++;}//アニメーション	
			if ( iCutX == 3 ){iCutX = STAND;}//アニメーションループ	
		}
		
		

		if( STEP <= 8){			
			g.setColor(Color.argb(fadein,0,0,0));
			g.fillRect(0, 0, MainView.screenX, MainView.screenY);		
			g.setColor(Color.argb(255,255,255,255));
			g.setFontSize(25);
			if(STEP>=2)g.drawString(MainView.you+"は、姫を探しに出発しました。", 10,telop1);
			if(STEP>=4)g.drawString("あてもなく歩いていると、", 10, telop2);
			if(STEP>=6)g.drawString("広い荒野へと出ました。", 10, telop3);			
			
		}				
		
		if( STEP >= 8 ){fadein -= 5;}
		if(fadein <= 0 ){fadein = 0; }		


		if(STEP >= 8 && STEP <= 11){ iX += ivx; }

		if( STEP >= 12 &&  STEP <= 18){
			g.setFontSize(20);
		 	Comment();		
			view.FaceUp(Img.player, iW, iH, 0, 2, 0);//主人公アップ[左]
			g.drawString(MainView.you, telopX, name);			
			if(STEP>=14)g.drawString("姫・・どこへ行ってしまったんだ・・。", telopX,telop1);
			if(STEP>=16)g.drawString("こんなところにいるはずは・・・", telopX, telop2);
			if(STEP>=18)g.drawString("いや、ここも探してみよう。", telopX, telop3);			
			ivx = 0;//P停止			
			iCutX = STAND;//気をつけ					
		}
		
		if(STEP >= 19){
			iX += ivx;
			ivx = 5;
		}//プレイヤー右に移動	
	
		
		if( STEP >= 20 && STEP <= 28){			
			g.setColor(Color.argb(fadeout,0,0,0));
			g.fillRect(0, 0, MainView.screenX, MainView.screenY);		
			g.setColor(Color.argb(255,255,255,255));
			g.setFontSize(25);
			if(STEP>=22)g.drawString("なんでもこの平野には、", 10,telop1);
			if(STEP>=24)g.drawString("不思議な魔物が潜んでいるそうです。", 10, telop2);
			if(STEP>=26)g.drawString(MainView.you + "は無事に進めるのでしょうか・・。", 10, telop3);			
			fadeout+=3;			
		}

		if(fadeout >= 255 ){fadeout =255; }		
				
		if( STEP >= 29){
			g.setColor(Color.argb(255,0,0,0));
			g.fillRect(0, 0, MainView.screenX, MainView.screenY);		
			g.setColor(Color.argb(255,255,255,255));
			g.setFontSize(25);
			g.drawString("第２章　～平原のスライム～", telopX,telop2);
			
		}

		//次のステージへ
		if( STEP == 32 || SkipButton() ){ return true; }			

		return false;		
	}
}	

		

