package net.eguchi.demo_scene;

import net.eguchi.vbquest.Img;
import net.eguchi.vbquest.MainView;
import android.graphics.Color;

public class OpeningView extends MainDemo{
	
	// コンストラクタ
	public OpeningView(MainView v) {
		super(v);

		// ■　主人公
		iX = MainView.bX;
		iY = MainView.bY * 6;
		ivx 	= 1;//移動量

		iCutX 	= STAND;
		iCutY 	= RIGHT;	

		// ■　ヒロイン
		hX = MainView.screenX;
		hY = MainView.bY * 6;
		hvx = 1;//移動量

		hCutX = STAND;
		hCutY = LEFT;		
		
		// ■　敵
		eX = MainView.screenX;
		eY = MainView.bY * 4;
		evx = 2;//移動量

		ECutX = STAND;
		ECutY = LEFT;				
			
	}


	@Override
	public boolean DemoPlay() {
		super.DemoPlay();
		
		// ■demo背景
		view.drawBitmapEasy(Img.openingback, view.getImgWidth(Img.openingback), 
				view.getImgHeight(Img.openingback), 0, 0, MainView.screenX , MainView.screenY);
				
		//キャラの描画
		demoPlayer();
		demoPrincess();
		demoBoss();

		//全体の文字サイズ
		g.setFontSize(20);
		
		//------最初------ プレイヤー右に移動してくる
		iX +=ivx;// 右に移動

		//アニメーション条件
			if (demoCnt % 10 == 0) {iCutX++;}//アニメーション	
			if ( iCutX == 3 ){iCutX = STAND;}//アニメーションループ

		if(iX >= MainView.bX * 4 && STEP < 50 ){
			ivx = 0;//P停止			
			iCutX = STAND;//気をつけ		
		}
				
		
		if( STEP >= 5 &&  STEP <= 9){
			Comment();					
			view.FaceUp(Img.player, iW, iH, 0, FRONT, 0);//主人公アップ[左]
			g.drawString(MainView.you, telopX, name);		
			if(STEP>7)g.drawString("・・・姫、姫はいますか。", telopX,telop1);
			g.drawString("", telopX, telop2);
			g.drawString("", telopX, telop3);			
		}
		

		if(STEP >= 10){
			hX -= hvx;//姫、左に移動してくる		
			if (demoCnt % 10 == 0) {hCutX++;}
			if ( hCutX == 3 ){hCutX = STAND;}
		}

		if(hX <= MainView.bX * 10){
			hvx = 0;//姫停止
			hCutX = STAND;//停止
		}
		
		if( STEP >= 10 &&  STEP <= 15){
			Comment();		
			view.FaceUp(Img.princess, hW, hH, 0, FRONT, 2);//姫アップ[右]
			g.drawString( MainView.hime, telopX, name);			
			if(STEP >= 11)g.drawString("あら、これはこれは", telopX,telop1);
			if(STEP >= 12)g.drawString(""+MainView.you+"様。", telopX, telop2);
			g.drawString("", telopX, telop3);								
		}
		
		if( STEP >= 16 &&  STEP <= 20){
			Comment();		
			view.FaceUp(Img.player, iW, iH, 0, FRONT, 0);//主人公アップ[左]
			g.drawString(MainView.you, telopX, name);		
			if(STEP >= 17)g.drawString("姫、隣国の王様がお待ちです、", telopX,telop1);
			if(STEP >= 18)g.drawString("私がお供しますので、", telopX, telop2);
			if(STEP >= 19)g.drawString("早速向かいましょう。", telopX, telop3);						
		}
		
		if( STEP >= 21 &&  STEP <= 25){			
			Comment();		
			view.FaceUp(Img.princess, hW, hH, 0, FRONT, 2);//姫アップ[右]
			g.drawString(MainView.hime, telopX, name);		
			if(STEP >= 22)g.drawString("貴方がお供してくれるとは心強いわ。", telopX,telop1);
			if(STEP >= 23)g.drawString("早速向かいましょう。", telopX, telop2);
			g.drawString("", telopX, telop3);							
		}


		// 「敵」 出現
		if( STEP >= 25 ) {
			if (demoCnt % 5 == 0) {ECutX++;}
			if ((ECutX + 1) % 3 == 0) {ECutX = 0;}
			eX -= evx;						
		}		

		// ex = 0  敵停止
		if ( eX <= MainView.bX * 12 ) {
			evx = 0;//停止
			ECutX = STAND;
		}	
		
		//姫ビックリ！
		if( STEP == 30 || STEP ==31 ){
			view.drawIcon( 1 , 3 , hX ,hY );			
			//プレイヤーも
			view.drawIcon( 1 , 3 , iX ,iY );
		}
		
				
		if( STEP >= 31 &&  STEP <= 35){			
			Comment();		
			view.FaceUp(Img.enemy000, eW, eH, 0, FRONT, 2);//敵アップ[右]
			g.drawString(MainView.satan, telopX, name);		
			if(STEP >= 32)g.drawString("私は魔王"+MainView.satan+"だ。", telopX,telop1);
			if(STEP >= 33)g.drawString("姫はさらっていくぞ。", telopX, telop2);
			g.drawString("", telopX, telop3);						
			hCutY = RIGHT;//姫右向き
		}
		
		
		
		if( STEP >=36 &&  STEP <= 40){			
			Comment();					
			view.FaceUp(Img.princess, hW, hH, 0, FRONT, 2);//姫アップ[右]
			g.drawString(MainView.hime, telopX, name);					
			if(STEP >= 37)g.drawString("キャアアアアアア!!!" +"", telopX,telop1);
			g.drawString("", telopX, telop2);
			g.drawString("", telopX, telop3);						
		
		}
		
		// 40~46 敵と姫が 消滅
		if( STEP >= 42 &&  STEP <= 44){

			if(demoCnt%5 ==0){	//5フレームごとにフラッシュ
				if(flash == 0 ){flash = 255;}
				//フラッシュ
				g.setColor(Color.argb(flash,255,255,0));
				g.fillRect(0, 0, MainView.screenX, MainView.screenY);			
				flash = 0;
			}			
		}
		
		//ビックリ！
		if( STEP==44 || STEP==45 ){
			hX = MainView.screenX;
			eX = MainView.screenX;
			//アイコン
			view.drawIcon( 1 , 3 , iX ,iY );	
		}
				
		if( STEP >=45 &&  STEP <= 50){			
			Comment();					
			view.FaceUp(Img.player, iW, iH, 0, FRONT, 0);//主人公アップ[左]
			g.drawString(MainView.you, telopX, name);		
			if(STEP >= 46)g.drawString("しまった・・・!!", telopX,telop1);
			if(STEP >= 47)g.drawString("ええい、うかうかしてられない、", telopX, telop2);
			if(STEP >= 48)g.drawString("姫を助けに行かなければ・・!!", telopX, telop3);
		}
					
		if(STEP >= 51){ iX += 10; }//プレイヤー右に高速移動		


		if( 52 <= STEP &&  STEP <= 57){			
			
			g.setColor(Color.argb( fadeout,0,0,0) );
			g.fillRect(0, 0, MainView.screenX, MainView.screenY);

			g.setColor(Color.WHITE );
			g.setFontSize(25);
			g.drawString("こうして"+MainView.you+"は・・・" , telopX,telop1);
			g.drawString("姫を助けるため、", telopX, telop2);
			g.drawString("長い旅へ向かうのであった。", telopX, telop3);
			g.drawString("", telopX, telop3);		
			fadeout +=3;
		}
		
		if(	fadeout >=255 ){fadeout =255;}
		
		if( STEP >= 58){
			g.setColor(Color.argb( fadeout,0,0,0) );
			g.fillRect(0, 0, MainView.screenX, MainView.screenY);
			g.setColor(Color.WHITE );
			g.setFontSize(25);			
			g.drawString("第１章～旅立ち～", telopX,telop2);
			
		}

		if( STEP >= 60 || SkipButton() == true ){			
			return true;
		}	
						
		return false;//上の条件でdemo終了	

	}	
		
}
