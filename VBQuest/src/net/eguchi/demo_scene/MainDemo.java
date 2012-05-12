package net.eguchi.demo_scene;

import net.eguchi.vbquest.GraphicsUtility;
import net.eguchi.vbquest.Img;
import net.eguchi.vbquest.MainView;
import android.graphics.Color;

public class MainDemo {

	protected MainView view;
	protected GraphicsUtility g;// グラフィック宣言
	
	//■ 主人公
	protected int iX,iY;//プレイヤーX座標
	protected int ivx,ivy;//プレイヤー移動量
	protected int iCutX,iCutY;//画像カット

	//向き Y
	protected final int	LEFT   = 0,
							RIGHT  = 1,
							FRONT  = 2,
							BACK   = 3;

	//向き X
	protected final int 	STAND = 0,
							MOVE  = 1,
							DASH  = 2;
	
	protected int iW,iH;//画像サイズ
	
	//■ ヒロイン
	protected int hX,hY;//ヒロイン X
	protected int hvx;//姫の移動量

	protected int hCutX,hCutY;//画像カット
	protected int hW,hH;//サイズ
		
	//■ 敵
	protected int eX,eY;//敵座標
	protected int evx;//敵の移動量

	protected int ECutX,ECutY;//画像カット

	protected int eW,eH;//サイズ

	//■妖精
	protected int fX,fY,mX,mY;//妖精、商人 
	protected int fvx;//移動量
	
	protected int fCutX,fCutY;//画像カット
	
	protected int fW,fH,mW,mH;//サイズ
		
	//画面効果
	protected int flash,//フラッシュ
					fadein,//フェードイン
					fadeout;//フェードアウト
	
	
	//■ セリフ
	protected final int name   = MainView.bY, //会話者の名前の位置	
						telopX = MainView.bX *2, //会話x座標
						telop1 = MainView.bY *2, //会話1行目
						telop2 = MainView.bY *3, //会話2行目
						telop3 = MainView.bY *4; //会話3行目

	protected int demoCnt,//ループカウンター
					STEP;//カウントを定数で割ったもの[進行用]
	
	public MainDemo(MainView v) {

		g = v.getUtil();
		view = v;	

		//自分
		iW = view.getImgWidth(Img.player);
		iH = view.getImgHeight(Img.player);		
		
		//◆主人公[画面外]
		iX = MainView.screenX;
		iY = MainView.screenY;
		
		//ヒロイン
		hW = view.getImgWidth(Img.princess);
		hH = view.getImgHeight(Img.princess);

		//◆ヒロイン[画面外]
		hX = MainView.screenX;
		hY = MainView.screenY;
		
		//敵
		eW = view.getImgWidth(Img.enemy000);
		eH = view.getImgHeight(Img.enemy000);		

		//◆敵[画面外]
		eX = MainView.screenX;
		eY = MainView.screenY;

		//妖精
		fW = view.getImgWidth(Img.fairy);
		fH = view.getImgHeight(Img.fairy);	

		//商人
		mW = view.getImgWidth(Img.merchant);
		mH = view.getImgHeight(Img.merchant);	


		flash = 0;//画面フラッシュ用

		fadein 	= 255;//フェードイン
		fadeout	=   0;//フェードアウト

		demoCnt = 0; //ループカウンター
		STEP = 0;

	}
	
	
	//demoシーンが終わるとtrueを返す
	public boolean DemoPlay(){

		demoCnt++;
		STEP = demoCnt / 20; //シーンの流れ	

		return true;
	}

	
	public boolean SkipButton(){	
				
		g.setFontSize(25);

		//確認用
//		g.setColor(Color.BLACK);
//		g.drawString("" + demoCnt, 10, 20);
//		g.drawString("" + STEP, 10, 40);
		//タイトルに戻る[点滅]
		if( ( demoCnt/5 )% 2 == 0 ){
			g.setColor(Color.BLUE);
			g.drawString("TOUCH! SKIP HERE", MainView.bX*7, MainView.bY);
			g.setColor(Color.argb(255,0,120,255));
			g.drawString("TOUCH! SKIP HERE", MainView.bX*7+2, MainView.bY+2);
		}

		//スキップボタンが押されたらデモ終了
		if(MainView.firstTouch && 
				view.getTouch1X() > MainView.bX *10 && view.getTouch1Y() < MainView.bY*2 ){
			return true;			 
		}


		return false;
	}	
	

	//主人公
	public void demoPlayer(){
		view.drawBitmapCharacter(Img.player, iW, iH, iCutX, iCutY, iX, iY, 
				MainView.bX *2 , MainView.bY*3);
				
	}	
	
	//姫
	public void demoPrincess(){
		view.drawBitmapCharacter(Img.princess, hW, hH, hCutX, hCutY, hX, hY, 
				MainView.bX *2 , MainView.bY *3 );

	}
	//ボス
	public void demoBoss(){
		view.drawBitmapCharacter(Img.enemy000, eW, eH, ECutX, ECutY, eX, eY,
				MainView.bX*4, MainView.bY*5);
	}			

	//妖精
	public void demoFairy(int w, int h, int x, int y){
		
		view.drawBitmapCharacter(Img.fairy,view.getImgWidth(Img.fairy),
				view.getImgHeight(Img.fairy),
				w,h,x,y, MainView.bX,MainView.bY *2
		);
	}			
	
	//商人
	public void demoMerchant(int w, int h, int x, int y){
		
		view.drawBitmapCharacter(Img.merchant,view.getImgWidth(Img.merchant),
				view.getImgHeight(Img.merchant),
				w,h,x,y, MainView.bX*2,MainView.bY *3
		);
	}			

	
	
	
	//コメントのフレーム用
	public void Comment() {

		//☆名前入れるとこ

		g.setColor(Color.argb(160,255,255,255));
		g.fillRect( MainView.bX +5,10 , MainView.bX*6 , MainView.bY);				
		//黒で塗りつぶす
		g.setColor(Color.argb(160,0,0,0)); 
		g.fillRect( MainView.bX +5 +5 , 10 +5 , MainView.bX*6 -10 , MainView.bY -10);

		
		
		//☆☆コメントフレーム欄		
		// 周囲を白で塗りつぶす
		g.setColor(Color.argb(160,255,255,255));
		g.fillRect( MainView.bX ,MainView.bY , MainView.bX*14 ,MainView.bY *4);

		// コメント内を黒で塗りつぶす
		g.setColor(Color.argb(160,0,0,0)); 
		g.fillRect( MainView.bX +5 , MainView.bY +5 , MainView.bX*14 -10 , MainView.bY *4 -10);

		//次の描画文字のために白に設定
		g.setColor(Color.WHITE);
	}
	
	
	
	
}