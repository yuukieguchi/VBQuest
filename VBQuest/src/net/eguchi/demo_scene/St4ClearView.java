package net.eguchi.demo_scene;

import net.eguchi.vbquest.Img;
import net.eguchi.vbquest.MainView;
import android.graphics.Color;

public class St4ClearView extends MainDemo{

	// コンストラクタ
	public St4ClearView(MainView v) {
		super(v);
		
		//プレイヤー
		iX 	= -MainView.bX;
		iY 	= MainView.bY * 7;
		ivx	= 2;//移動
		iCutX 	= STAND;
		iCutY 	= RIGHT;
		demoCnt = 0; //ループカウンター
		STEP 	= 0;
	}
	
	@Override
	public boolean DemoPlay() {
		super.DemoPlay();
				
		// ■背景
		view.drawBitmapEasy(Img.st4back, view.getImgWidth(Img.st4back), 
				view.getImgHeight(Img.st4back), 0, 0, MainView.screenX , MainView.screenY);
		
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
			if(STEP>=2)g.drawString("長い道のりを経て、", 10,telop1);
			if(STEP>=4)g.drawString(MainView.you+"はついに魔王の城へと", 10, telop2);
			if(STEP>=6)g.drawString("たどり着きました。", 10, telop3);			
			
		}				
		
		if( STEP >= 8 ){fadein -= 5;}
		if(fadein <= 0 ){fadein = 0; }		


		if(STEP >= 8 && STEP <= 14){ iX += ivx; }

		if( STEP >= 12 &&  STEP <= 18){
			g.setFontSize(20);
		 	Comment();		
			view.FaceUp(Img.player, iW, iH, 0, 2, 0);//主人公アップ[左]
			g.drawString(MainView.you, telopX, name);			
			if(STEP>=14)g.drawString("ついにここまで来た・・・。", telopX,telop1);
			if(STEP>=16)g.drawString("姫・・・すぐに向かいます。", telopX, telop2);
			if(STEP>=18)g.drawString("どうか、ご無事で・・・", telopX, telop3);			
			ivx = 0;//P停止			
			iCutX = STAND;//気をつけ
			iCutY = BACK;//遠くを見る
		}
		
		if(STEP >= 19){
			iX += ivx;
			iCutY = RIGHT;//
			ivx = 7;
			
		}//プレイヤー右に移動	
	
		
		if( STEP >= 20 && STEP <= 30){			
			g.setColor(Color.argb(fadeout,0,0,0));
			g.fillRect(0, 0, MainView.screenX, MainView.screenY);		
			g.setColor(Color.argb(255,255,255,255));
			g.setFontSize(20);
			if(STEP>=22)g.drawString("さあ、物語はいよいよ", 10,telop1);
			if(STEP>=24)g.drawString("最終局面を迎えます。", 10, telop2);
			if(STEP>=26)g.drawString(MainView.you + "は姫を", 10, telop3);
			if(STEP>=28)g.drawString("救うことができるでしょうか。", 10, telop3+ (telop1/2) );
			fadeout+=3;			
		}

		if(fadeout >= 255 ){fadeout =255; }		
				
		if( STEP >= 31){
			g.setColor(Color.argb(255,0,0,0));
			g.fillRect(0, 0, MainView.screenX, MainView.screenY);		
			g.setColor(Color.argb(255,255,255,255));
			g.setFontSize(25);
			g.drawString("第５章　～魔王との死闘～", telopX,telop2);
			
		}

		//次のステージへ
		if( STEP == 34 || SkipButton() ){ return true; }			

		return false;		
	}
}	

		

