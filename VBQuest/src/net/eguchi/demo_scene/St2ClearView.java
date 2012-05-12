package net.eguchi.demo_scene;

import net.eguchi.vbquest.Img;
import net.eguchi.vbquest.MainView;
import android.graphics.Color;

public class St2ClearView extends MainDemo{

	// コンストラクタ
	public St2ClearView(MainView v) {
		super(v);
		
		//プレイヤー
		iX 	= MainView.bX*4;
		iY 	= MainView.bY * 6;
		ivx	= 0;//移動
		iCutX 	= STAND;
		iCutY 	= RIGHT;

		//妖精
		fX 	= MainView.bX *10;
		fY 	= MainView.bY * 7;
		fvx	= 2;//移動
		fCutX 	= STAND;
		fCutY 	= LEFT;
		
		demoCnt = 0; //ループカウンター
		STEP 	= 0;
	}


	@Override
	public boolean DemoPlay() {
		super.DemoPlay();
						
		// ■背景[次のステージの背景
		view.drawBitmapEasy(Img.st3back, view.getImgWidth(Img.st3back), 
				view.getImgHeight(Img.st3back), 0, 0, MainView.screenX , MainView.screenY);
		
		demoPlayer();	
		demoFairy(fCutX, fCutY, fX, fY);
				

		if(ivx > 0){
			if (demoCnt % 10 == 0) {iCutX++;}//アニメーション	
			if ( iCutX == 3 ){iCutX = STAND;}//アニメーションループ	
		}
		
		if( STEP <= 10){			
			g.setColor(Color.argb(fadein,0,0,0));
			g.fillRect(0, 0, MainView.screenX, MainView.screenY);		
			g.setColor(Color.argb(255,255,255,255));
			g.setFontSize(20);
			if(STEP>=3)g.drawString(MainView.you+"は、平野を抜け、", 10,telop1);
			if(STEP>=5)g.drawString("暗い暗い森の中へと", 10, telop2);
			if(STEP>=7)g.drawString("進んで行きました。", 10, telop3);			
			
		}

		if( STEP >= 11 && STEP <=18){			
			g.setColor(Color.argb(fadein,0,0,0));
			g.fillRect(0, 0, MainView.screenX, MainView.screenY);		
			g.setColor(Color.argb(255,255,255,255));
			g.setFontSize(20);
			if(STEP>=13)g.drawString("森の中で、森の妖精と出会いました。", 10,telop1);
			if(STEP>=15)g.drawString("何でも、この妖精は、魔王の城の場所を", 10, telop2);
			if(STEP>=17)g.drawString("知っているというのです・・・。", 10, telop3);			
		}	
		
		if( STEP >= 18 ){fadein -= 5;}
		if( fadein <= 0 ){fadein = 0; }		


		if( STEP >= 21 &&  STEP <= 26){
			g.setFontSize(20);
		 	Comment();		
			view.FaceUp(Img.player, iW, iH, 0, 2, 0);//主人公アップ[左]
			g.drawString(MainView.you, telopX, name);			
			if(STEP>=23)g.drawString("君は魔王の城の場所を", telopX,telop1);
			if(STEP>=25)g.drawString("知っているんだね。", telopX, telop2);
						g.drawString("", telopX, telop3);			
		}
	

		if( STEP >= 27 &&  STEP <= 35){
			g.setFontSize(20);
		 	Comment();		
			view.FaceUp(Img.fairy, fW, fH, 2, 2, 2);//妖精アップ[左]
			g.drawString("妖精", telopX, name);			
			if(STEP>=29)g.drawString("知っています。魔王の城は...この森を抜け", telopX,telop1);
			if(STEP>=31)g.drawString("はるか先の、燃える大地を", telopX, telop2);
			if(STEP>=33)g.drawString("抜けた先にあります。", telopX, telop3);			
		}
		
		if( STEP >= 36 &&  STEP <= 41){
			g.setFontSize(20);
		 	Comment();		
			view.FaceUp(Img.player, iW, iH, 0, 2, 0);//主人公アップ[左]
			g.drawString(MainView.you, telopX, name);			
			if(STEP>=38)g.drawString("ありがとう、妖精さん。", telopX,telop1);
			if(STEP>=40)g.drawString("僕は先を急ぐとするよ。", telopX, telop2);
						g.drawString("", telopX, telop3);			
		}
		

		if( STEP >= 43 &&  STEP <= 50){
			g.setFontSize(20);
		 	Comment();		
			view.FaceUp(Img.fairy, fW, fH, 2, 2, 2);//妖精アップ[左]
			g.drawString("妖精", telopX, name);			
			if(STEP>=45)g.drawString("わかりました。", telopX,telop1);
			if(STEP>=47)g.drawString("この森には、危険が沢山あります。", telopX, telop2);
			if(STEP>=49)g.drawString("お気をつけて...。", telopX, telop3);			
		}
				
		if(STEP >= 49){
			iX += ivx;
			ivx = 5;
		}//プレイヤー右に移動	
		
		
		if( STEP >= 51 &&  STEP <= 54){
			g.setFontSize(20);
		 	Comment();		
			view.FaceUp(Img.fairy, fW, fH, 2, 2, 2);//妖精アップ[左]
			g.drawString("妖精", telopX, name);			
			if(STEP>=52)g.drawString("・・・・・", telopX,telop1);
			if(STEP>=53)g.drawString("どうか、お気をつけて・・・。", telopX, telop2);
						g.drawString("", telopX, telop3);			
		}

		
		if( STEP >= 55 && STEP <= 62){			
			g.setColor(Color.argb(fadeout,0,0,0));
			g.fillRect(0, 0, MainView.screenX, MainView.screenY);		
			g.setColor(Color.argb(255,255,255,255));
			g.setFontSize(25);
			if(STEP>=57)g.drawString("どうやらこの森は、", 10,telop1);
			if(STEP>=59)g.drawString("多くの蜂の住処になっているようです。", 10, telop2);
			if(STEP>=61)g.drawString(MainView.you + "は無事に進めるのでしょうか・・・", 10, telop3);			
			fadeout+=3;			
		}

		if(fadeout >= 255 ){fadeout =255; }		
				
		if( STEP >= 63){
			g.setColor(Color.argb(255,0,0,0));
			g.fillRect(0, 0, MainView.screenX, MainView.screenY);		
			g.setColor(Color.argb(255,255,255,255));
			g.setFontSize(25);
			g.drawString("第３章　～森の女王蜂～", telopX,telop2);
			
		}

		//次のステージへ
		if( STEP == 65 || SkipButton() ){ return true; }			

		return false;		
	}
}	

		

