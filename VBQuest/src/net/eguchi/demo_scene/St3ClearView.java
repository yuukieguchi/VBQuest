package net.eguchi.demo_scene;

import net.eguchi.vbquest.Img;
import net.eguchi.vbquest.MainView;
import net.eguchi.vbquest.Sound;
import android.graphics.Color;

public class St3ClearView extends MainDemo{

	// コンストラクタ
	public St3ClearView(MainView v) {
		super(v);
		
		//プレイヤー
		iX 	= MainView.bX*4;
		iY 	= MainView.bY * 6;
		ivx	= 0;//移動
		iCutX 	= STAND;
		iCutY 	= RIGHT;

		//商人
		fX 	= MainView.bX *10;
		fY 	= MainView.bY * 6;
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
		view.drawBitmapEasy(Img.town, view.getImgWidth(Img.town), 
				view.getImgHeight(Img.town), 0, 0, MainView.screenX , MainView.screenY);
		
		//プレイヤー
		demoPlayer();	
		//商人
		demoMerchant(fCutX, fCutY, fX, fY);
				

		if(ivx > 0){
			if (demoCnt % 10 == 0) {iCutX++;}//アニメーション	
			if ( iCutX == 3 ){iCutX = STAND;}//アニメーションループ	
		}
		
		if( STEP <= 10){			
			g.setColor(Color.argb(fadein,0,0,0));
			g.fillRect(0, 0, MainView.screenX, MainView.screenY);		
			g.setColor(Color.argb(255,255,255,255));
			g.setFontSize(20);
			if(STEP>=3)g.drawString(MainView.you+"は、森を抜けました。", 10,telop1);
			if(STEP>=5)g.drawString("森を抜けると、", 10, telop2);
			if(STEP>=7)g.drawString("小さな村に辿り着きました。", 10, telop3);			
			
		}

		if( STEP >= 11 && STEP <=15){			
			g.setColor(Color.argb(fadein,0,0,0));
			g.fillRect(0, 0, MainView.screenX, MainView.screenY);		
			g.setColor(Color.argb(255,255,255,255));
			g.setFontSize(20);
			if(STEP>=13)g.drawString("村の中で、商人と出会いました。", 10,telop2);
			g.drawString("", 10, telop2);
			g.drawString("", 10, telop3);			
		}	
		
		
		if( STEP >= 16 ){fadein -= 5;}
		if( fadein <= 0 ){fadein = 0; }		


		if( STEP >= 18 &&  STEP <= 25){
			g.setFontSize(20);
		 	Comment();		
			view.FaceUp(Img.merchant, mW, mH, 1, 2, 2);//商人アップ
			g.drawString("商人", telopX, name);			
			if(STEP>=20)g.drawString("おう、兄ちゃん。", telopX,telop1);
			if(STEP>=22)g.drawString("魔王の城に行くんだってな。", telopX, telop2);
			if(STEP>=24)g.drawString("何か買って行くか？", telopX, telop3);	
			
		}
			
		if( STEP >= 26 &&  STEP <= 60){
			
			Shopping();	
			
			g.setFontSize(20);
			g.setColor(Color.BLACK);
			g.drawString("残り時間："+(60-STEP), telopX, name);
			g.setColor(Color.WHITE);
			g.drawString("残り時間："+(60-STEP), telopX, name);
								
			g.drawString("所有コイン数："+view.getMoney()+" G", telopX,telop1);
			g.drawString("HP："+view.getHP()+"/"+MainView.LifeMax[view.getLEVEL()], telopX,telop2);
			g.drawString("残りLIFE："+view.getLIFE(), telopX,telop3);

			//アイテム購入
			g.setColor(Color.WHITE);
		if( STEP<=30 )g.drawString("↓欲しいアイテムを、優しくタッチしてね", telopX, telop3+MainView.bY);					
			
			if(MainView.firstTouch && view.getTouch1Y() >= telop3+MainView.bY && view.getTouch1Y() <= telop3 + telop1){
				if(view.getMoney() <10 ){
					g.setColor(Color.RED);	
					g.drawString("[ 薬草 ]を買うにはお金が足りません。", telopX,telop3+MainView.bY);			
				}else if(view.getHP() == MainView.LifeMax[view.getLEVEL()]){
					g.setColor(Color.RED);	
					g.drawString("現在のHPは最大値です。", telopX,telop3+MainView.bY);								
				}else{
					view.setMoney(view.getMoney() -10 );//-20G
					view.setHP(view.getHP()+10);//10回復
					//ただし、全快の場合は最大までしか回復しない
					if(view.getHP() > MainView.LifeMax[view.getLEVEL()]){
						view.setHP(MainView.LifeMax[view.getLEVEL()] );			
					}
					g.setColor(Color.RED);	
					g.drawString("[ 薬草 ]を購入しました。", telopX,telop3+MainView.bY);			
					view.getSound(Sound.lvup);//効果音
				}
			}else if( MainView.firstTouch && view.getTouch1Y() >= telop3+telop2 && view.getTouch1Y() <= telop3+telop3 ){				
				if(view.getMoney() < 50){
					g.setColor(Color.RED);	
					g.drawString("[ 聖水 ]を買うにはお金が足りません。", telopX,telop3+MainView.bY);			
				}else{
					view.setMoney(view.getMoney() -50 );//-20G
					view.setLIFE(view.getLIFE() +1);//LIFE +1
					g.setColor(Color.RED);	
					g.drawString("[ 聖水 ]を購入しました。", telopX,telop3+MainView.bY);
					view.getSound(Sound.lvup);//効果音
				}	

			}	
			
			g.setColor(Color.WHITE);	
			g.drawString("[ 薬草 ] 10G ：HP１０回復", telopX, telop3+telop1);
			g.drawString("[ 聖水 ] 50G ：LIFEが１増える", telopX, telop3+telop3);			
		}


		if( STEP >= 61 &&  STEP <= 66){
			g.setFontSize(20);
		 	Comment();		
			view.FaceUp(Img.player, iW, iH, 0, 2, 0);//主人公アップ[左]
			g.drawString(MainView.you, telopX, name);			
			if(STEP>=62)g.drawString("ありがとうございました。", telopX,telop1);
			if(STEP>=64)g.drawString("急いでいるので、失礼します。", telopX, telop2);
						g.drawString("", telopX, telop3);			
		}
		
		
		if(STEP >= 67){
			iCutY =LEFT;
			iX -= ivx;
			ivx = 1;
		}//プレイヤー左に移動	

		
		if( STEP >= 67 &&  STEP <= 74){
			g.setFontSize(20);
		 	Comment();		
			view.FaceUp(Img.merchant, mW, mH, 1, 2, 2);//商人アップ
			g.drawString("商人", telopX, name);				
			if(STEP>=68)g.drawString("あの城に近づこうとしたのは", telopX,telop1);
			if(STEP>=70)g.drawString("お前さんが始めてだぜ。", telopX, telop2);
			if(STEP>=72)g.drawString("せいぜい気をつけるんだな。", telopX, telop3);			
		}
		
		
		if( STEP >= 75 && STEP <= 80){			
			g.setColor(Color.argb(fadeout,0,0,0));
			g.fillRect(0, 0, MainView.screenX, MainView.screenY);		
			g.setColor(Color.argb(255,255,255,255));
			g.setFontSize(25);
			if(STEP>=77)g.drawString("どうやらこの先は、", 10,telop1);
			if(STEP>=79)g.drawString("魔王の領土となっているようです。", 10, telop2);
			g.drawString("", 10, telop3);			
			fadeout+=3;			
		}

		
		if(fadeout >= 255 ){fadeout =255; }		
				
		if( STEP >= 81){
			g.setColor(Color.argb(255,0,0,0));
			g.fillRect(0, 0, MainView.screenX, MainView.screenY);		
			g.setColor(Color.argb(255,255,255,255));
			g.setFontSize(25);
			g.drawString("第４章　～魔王の巣窟～", telopX,telop2);
			
		}

		//次のステージへ
		if( STEP == 84 || SkipButton() ){ return true; }			

		return false;		
	}

	//コメントのフレーム用
	public void Shopping() {

		//☆名前入れるとこ

		g.setColor(Color.argb(160,255,255,255));
		g.fillRect( MainView.bX +5,10 , MainView.bX*6 , MainView.bY);				
		//黒で塗りつぶす
		g.setColor(Color.argb(160,0,0,0)); 
		g.fillRect( MainView.bX +5 +5 , 10 +5 , MainView.bX*6 -10 , MainView.bY -10);

				
		//☆☆コメントフレーム欄		
		// 周囲を白で塗りつぶす
		g.setColor(Color.argb(160,255,255,255));
		g.fillRect( MainView.bX ,MainView.bY , MainView.bX*14 ,MainView.bY *9);

		// コメント内を黒で塗りつぶす
		g.setColor(Color.argb(160,0,0,0)); 
		g.fillRect( MainView.bX +5 , MainView.bY +5 , MainView.bX*14 -10 , MainView.bY *9 -10);

		//次の描画文字のために白に設定
		g.setColor(Color.WHITE);
	}

}	

		

