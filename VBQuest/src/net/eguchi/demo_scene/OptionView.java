package net.eguchi.demo_scene;

import net.eguchi.vbquest.Img;
import net.eguchi.vbquest.MainView;
import android.graphics.Color;

public class OptionView extends MainDemo {

	// コンストラクタ
	public OptionView(MainView v) {
		super(v);

		// ■　主人公
		iX = MainView.bX * 6;
		iY = MainView.bY * 7+20;
		iCutX = 1;
		iCutY = FRONT;
		ivx = 1;// 移動量
		ivy = 3;// 移動量

	}

	// オプション
	@Override
	public boolean DemoPlay() {
		super.DemoPlay();
			
		// バックスクリーン
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, MainView.screenX, MainView.screenY);
		g.setColor(Color.argb(160, 255, 255, 255));
		g.fillRect(10, 10, MainView.screenX - 20, MainView.screenY - 20);
		g.setColor(Color.argb(180, 0, 0, 0));
		g.fillRect(20, 20, MainView.screenX - 40, MainView.screenY - 40);

		
//		if (demoCnt % 30 == 0) {iCutX++;}//アニメーション	
		if (iCutX == 3) {iCutX = 0;}//アニメーション

		// 主人公
		view.drawBitmapCharacter(Img.player, iW, iH, iCutX, iCutY, iX, iY,
				MainView.bX * 2, MainView.bY * 2);

		
		// 操作キーを描画
		view.ControlPanel();

		// テロップスタート
		g.setFontSize(20);
		g.setColor(Color.WHITE);
		if (STEP >= 1) {
			g.drawString("【操作説明】", MainView.bX, MainView.bY * 2);
			iCutY = FRONT;//正面向き 
		}

		if (STEP >= 2) {		
			g.drawString("左矢印を押すと、左移動", MainView.bX, MainView.bY * 3);
			iCutY = LEFT;//左向き				
			if (STEP <= 3) {
				MainView.leftTouch = true;
				iX -= ivx*2;//左移動

				if (demoCnt % 10 == 0) {iCutX++;}//アニメーション	
			}
		}

		if (STEP >= 4) {
			g.drawString("右矢印を押すと、右移動", MainView.bX, MainView.bY * 4);
			MainView.leftTouch = false;
			iCutY = RIGHT;// 右向き
			if (STEP <= 5) {
				MainView.rightTouch = true;
				iX += ivx*2;// 右移動

				if (demoCnt % 10 == 0) {iCutX++;}//アニメーション	
			}
		}

		//ジャンプ
		if (STEP >= 6) {
			g.drawString("上矢印を押すと、ジャンプ！", MainView.bX, MainView.bY * 5);
			iCutY = RIGHT;// 正面向き
			MainView.rightTouch = false;

			if (STEP <= 7) {
				iCutX = DASH;
				MainView.halfupTouch = true;
				iY -= ivy;
			}

		}
		//落下
		if (STEP >= 8 && STEP <= 9) {
			MainView.upTouch = false;
			iCutX = DASH;// アニメしない
			iY += ivy;
		}
		
		
		if (STEP >= 10) {
			g.drawString("マルチタッチがうまくきかない時があるので...", MainView.bX, MainView.bY * 6);
			iCutY = RIGHT;// 右向き

			if (STEP <= 11) {
				MainView.rightTouch = true;
				iX += ivx;//右移動
				view.drawIcon(0, 0, iX, iY);// アイコン[もじゃ]

				if (demoCnt % 10 == 0) {iCutX++;}//アニメーション	
			}
		}

		if (STEP >= 11) {
			g.drawString("移動中にジャンプするときは一度指を離してね", MainView.bX,
					MainView.bY * 7);
			iCutY = RIGHT;// 右向き
			
			if (STEP <= 12) {// 斜めジャンプ
				MainView.rightTouch = false;
				MainView.upTouch = true;
				iCutX = DASH;
				iX += ivx;
				iY -= ivy;
			
			}
		}

		if (STEP >= 12) {
			g.drawString("空中でも離してから左右に移動して下さいね", MainView.bX, MainView.bY * 8);
			iCutY = RIGHT;// 右向き
			
			if (STEP <= 13) {
				MainView.upTouch = false;
				MainView.rightTouch = true;
				iCutX = DASH;
				iX += ivx;
				iY += ivy;
			}
		}

		if (STEP >= 14) {
			g.drawString("それでは、ごゆるりとお楽しみ下さい^_^", MainView.bX, MainView.bY * 9);
			MainView.rightTouch = false;
			iCutY = FRONT;//正面			
			if (demoCnt % 10 == 0) {iCutX++;}//この後、アニメーション	
		}

		if (STEP >= 17) {
			MainView.rightTouch = true;
			iCutY = RIGHT;// 右向き
			iX += ivx;// 右に捌けていく
		
		}

		// スキップボタン
		if (SkipButton()) {
			//ボタンを初期化
			MainView.leftTouch = false;
			MainView.rightTouch = false;
			MainView.upTouch = false;			
			return true;
		}

		return false;
	} // optionView -----END------

}