package net.eguchi.demo_scene;

import net.eguchi.vbquest.Img;
import net.eguchi.vbquest.MainView;
import net.eguchi.vbquest.Sound;
import android.graphics.Color;

public class EndingView extends MainDemo{

	// コンストラクタ
	public EndingView(MainView v) {
		super(v);

		// ■　主人公
		iX = MainView.bX*4;
		iY = MainView.bY * 6;
		ivx 	= 1;//移動量

		iCutX 	= STAND;
		iCutY 	= RIGHT;	

		// ■　ヒロイン
		hX = MainView.bX*10;
		hY = MainView.bY * 6;
		hvx = 3;//移動量

		hCutX = STAND;
		hCutY = LEFT;		
		
		// ■　敵
		eX = MainView.bX*10;
		eY = MainView.bY * 4;
		evx = 2;//移動量

		ECutX = STAND;
		ECutY = LEFT;				

		demoCnt = 0; //ループカウンター
		STEP = 0;
	}
	
	@Override
	public boolean DemoPlay() {
		super.DemoPlay();
				
		
		if( STEP >= 0 ){		
			// ■ボス倒したあとの背景
			view.drawBitmapEasy(Img.endback, view.getImgWidth(Img.endback), 
					view.getImgHeight(Img.endback), 0, 0, MainView.screenX , MainView.screenY);
		}
		
		if(STEP >= 44){
			// ■お城の背景
			view.drawBitmapEasy(Img.openingback, view.getImgWidth(Img.openingback), 
					view.getImgHeight(Img.openingback), 0, 0, MainView.screenX , MainView.screenY);
		}
		
		
		if( STEP >= 25 )demoPrincess();		
		demoPlayer();
		if( STEP <= 18 )demoBoss();

		g.setFontSize(20);

		if( STEP <= 7){			
			g.setColor(Color.argb(fadein,0,0,0));
			g.fillRect(0, 0, MainView.screenX, MainView.screenY);		
			g.setColor(Color.argb(255,255,255,255));
			g.setFontSize(25);
			if(STEP>=2)g.drawString(MainView.you+"は、死闘の末、", telopX,telop1);
			if(STEP>=4)g.drawString("魔王を倒しました。", telopX, telop2);
			g.drawString("", telopX, telop3);			
		}
				
		if( STEP >= 8 ){
			fadein-=3;			
			if(fadein <= 0 ){fadein = 0; }		
		}
	
		if( STEP >= 9 &&  STEP <= 15){			
			Comment();		
			view.FaceUp(Img.enemy000, eW, eH, 0, FRONT, 2);//敵アップ[右]
			g.drawString(MainView.satan, telopX, name);		
			if(STEP >= 11)g.drawString("ばかな・・・・。", telopX,telop1);
			if(STEP >= 12)g.drawString("この私がやられた・・だと・・・。", telopX, telop2);
			if(STEP >= 14)g.drawString("ぐああああああ・・・!!", telopX, telop3);
		}

		
		if( STEP >= 16 &&  STEP <= 18){
			if(demoCnt%5 ==0){	//5フレームごとにフラッシュ
				if(flash == 0 ){flash = 255;}
				//フラッシュ
				g.setColor(Color.argb(flash,255,255,0));
				g.fillRect(0, 0, MainView.screenX, MainView.screenY);			
				flash = 0;
			}			
		}
		
//STEP = demoCnt / 20; //シーンの流れ

		//ボスが消えたその瞬間、効果音
		if(demoCnt== 18*20)view.getSound(Sound.bossend);
		if(demoCnt== 19*20)view.getBGM(Sound.ending);
		
		if( STEP >= 20 &&  STEP <= 24){
			g.setFontSize(20);
		 	Comment();		
			view.FaceUp(Img.player, iW, iH, 0, 2, 0);//主人公アップ[左]
			g.drawString(MainView.you, telopX, name);			
			if(STEP>=22)g.drawString("姫・・・姫・・・！！！", telopX,telop1);		
		}
		
		
		if( STEP >= 27 &&  STEP <= 30){			
			Comment();		
			view.FaceUp(Img.princess, hW, hH, 0, FRONT, 2);//姫アップ[右]
			g.drawString(MainView.hime, telopX, name);		
			if(STEP >= 28)g.drawString(MainView.you+"・・・！！", telopX,telop1);	

			if (demoCnt % 10 == 0) {hCutX++;}
			if ( hCutX == 3 ){hCutX = STAND;}		
		}
	
		if(STEP ==30)hX -= hvx*2;
		
		if( STEP >= 31 &&  STEP <= 37){
			g.setFontSize(20);
		 	Comment();		
			view.FaceUp(Img.player, iW, iH, 0, 2, 0);//主人公アップ[左]
			g.drawString(MainView.you, telopX, name);			
			if(STEP>=33)g.drawString("本当に無事で良かった・・・！", telopX,telop1);		
			if(STEP>=35)g.drawString("さあ、私たちの城に戻りましょう！", telopX,telop2);		
		}
	

		if( STEP >=38 &&  STEP <= 44){						
			g.setColor(Color.argb( fadeout,0,0,0) );
			g.fillRect(0, 0, MainView.screenX, MainView.screenY);
			g.setColor(Color.WHITE );
			g.setFontSize(20);
			if(STEP>=40)g.drawString("こうして"+MainView.you+"と"+MainView.hime+"は・・" , telopX,telop1);
			if(STEP>=42)g.drawString("自分たちのお城へと帰って行きました。", telopX, telop2);
			fadeout +=3;//暗くしていく
		}

		if(	STEP<=58 && fadeout > 255 ){fadeout = 255;}//黒で固定

		if( STEP >= 45 && STEP <= 55) { fadeout-=3; }//明るくしていく		
		
		if(	fadeout < 0 ){fadeout = 0;}
		
		if( STEP >= 50 &&  STEP <= 55){			
			Comment();		
			view.FaceUp(Img.princess, hW, hH, 0, FRONT, 2);//姫アップ[右]
			g.drawString(MainView.hime, telopX, name);		
			if(STEP >= 52)g.drawString(MainView.you+"・・、本当にありがとう。", telopX,telop1);
			if(STEP >= 54)g.drawString("私、あなたのことが・・・", telopX,telop2);		
		}

		if( STEP == 55 || STEP == 56 ){
			hX-=ivx;
			if (demoCnt % 10 == 0) {hCutX++;}
			if ( hCutX == 3 ){hCutX = STAND;}		
		}
				
		if( STEP >= 55){view.drawIcon( 0 , 2 , hX ,hY );}		
		if( STEP >= 58 &&  STEP <= 61){						
			g.setColor(Color.argb( fadeout,0,0,0) );
			g.fillRect(0, 0, MainView.screenX, MainView.screenY);
			g.setColor(Color.WHITE );
			g.setFontSize(20);
			if(STEP>=40)g.drawString("こうして"+MainView.you+"と"+MainView.hime+"は・・" , telopX,telop1);
			if(STEP>=42)g.drawString("末永く幸せに暮らしましたとさ。", telopX, telop2);
			fadeout +=3;
		}
		
		if(	STEP >=58 && fadeout > 200 ){fadeout = 200;}//黒で固定
		
		if(STEP >= 62 && STEP <= 68 ){
			g.setColor(Color.argb( fadeout,0,0,0) );
			g.fillRect(0, 0, MainView.screenX, MainView.screenY);
			g.setColor(Color.WHITE );
			g.setFontSize(20);
			if(STEP >= 64 )g.drawString("Sound:ユウラボ8bitサウンド工房　様", telopX, telop1);
			if(STEP >= 66 )g.drawString("Graphics：First Seed Material　様", telopX, telop2);
			fadeout +=3;
		}
		
		if(STEP >= 69){
			g.setColor(Color.argb( fadeout,0,0,0) );
			g.fillRect(0, 0, MainView.screenX, MainView.screenY);
			g.setColor(Color.WHITE );
			g.setFontSize(30);
			g.drawString("～おしまい～", telopX*2, telop2);
			fadeout +=3;
		}
		
		//タイトルへ
		if( STEP >= 70 ){
			if( SkipButton() || STEP>=85 ){
				// BGM停止
				view.bgmStop();	
				return true; }			
		}		
				
		return false;		
	}
}	

		

