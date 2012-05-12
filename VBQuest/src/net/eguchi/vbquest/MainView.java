package net.eguchi.vbquest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import net.eguchi.block.BlockBase;
import net.eguchi.demo_scene.EndingView;
import net.eguchi.demo_scene.MainDemo;
import net.eguchi.demo_scene.OpeningView;
import net.eguchi.demo_scene.OptionView;
import net.eguchi.demo_scene.St1ClearView;
import net.eguchi.demo_scene.St2ClearView;
import net.eguchi.demo_scene.St3ClearView;
import net.eguchi.demo_scene.St4ClearView;
import net.eguchi.enemy.EnemyBase;
import net.eguchi.item.ItemBase;
import net.eguchi.player.Player;
import net.eguchi.player.PlayerClear;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.Rect;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;


public class MainView extends SurfaceView implements SurfaceHolder.Callback,
Runnable {

	public static final int screenX	= 480,
			screenY	= 320;

	public static final int YOKO = 16,
			TATE = 10;

	public static final int bX = screenX / YOKO,
			bY = screenY / TATE;

	public static int MAP_WIDTH , MAP_HEIGHT;

	public static boolean	   stageClearFlg;
	public static int	       ClearCnt;

	private int PLAY_SCENE;
	public static final int TITLE 		 = 9999,
			PLAY_NOW	 = 9888,
			SAVE_NOW	 = 9777,
			START_DIALOG = 9666,
			LOAD_DIALOG  = 9555,
			LOAD_CANCEL  = 9444,
			OPENING 	 = 6666,
			GAMEOVER	 = 5555,
			OPTION 		 = 4444;

	public static int STAGE_NUM;
	public static final int  	STAGE1 		=  0,
			STAGE2 		=  1,
			STAGE3 		=  2,
			STAGE4 		=  3,
			STAGE5 		=  4;


	private int SAVE_DATA;
	private final int DATA_EMPTY =0,
			DATA_EXIST =1;

	private int 		LV,
	LVupCnt,
	HitPoint,
	EXP,
	LIFE;

	private final int HitPointBar = 20;

	public final static int[] 
			LifeMax  	= { 40, 60, 80,110,150,170,200,220,240 },
			EXPMax 		= { 20, 40, 52, 65, 81, 92,103,145,188 };


	public static final String   you = "アステカ",
			hime = "キャロル",
			satan = "ゴロドム";

	private SurfaceHolder holder;
	private Thread thread;
	private boolean isThreadrunning;

	private GraphicsUtility g;
	private Player			 p;
	private Map 			 map;
	private Sound 			 sound;
	private MainDemo 		 demo;

	public static KeyCode 		 key;

	private int loopCnt,
	EndCnt;
	
	
	// ===========================================================
	// ============== Variable for the TouchEvent ================
	// ===========================================================
	private HashMap<String,PointF> points = new HashMap<String,PointF>();
	private int touch1X, touch2X, touch1Y, touch2Y; 
	private int pointerCount;
	private int eventCode = -999;

	public static boolean firstTouch;
	public static boolean secondTouch;
	public static boolean rightTouch,leftTouch,upTouch,halfupTouch,downTouch;
	private int touchIndex;
	private int eventID;
	// ===========================================================

	private long t1 = 0;
	private long t2 = 0; 

	private int 		sColor,
	loadColor,
	optColor,
	titleCnt, 
	arrowY,
	fColor;

	private final int STARTselect  = bY *4;
	private final int LOADselect 	= bY *6;
	private final int OPTselect 	= bY *8;

	Paint paint = new Paint();
	Random rand;

	private final int[] STAGE_BACK = {
			Img.st1back,	
			Img.st2back,	
			Img.st3back,	
			Img.st4back,	
			Img.st5back,	
	};


	private final int BLOCK_NUM = 5;
	private ArrayList<BlockBase> blockList = 
			new ArrayList<BlockBase>(BLOCK_NUM);

	private final int ENEMY_NUM = 5;
	private ArrayList<EnemyBase> enemyList = 
			new ArrayList<EnemyBase>(ENEMY_NUM);

	private final int ITEM_NUM = 5;	
	private ArrayList<ItemBase> itemList = 
			new ArrayList<ItemBase>(ITEM_NUM);


	private static int offsetX;
	private static int offsetY;

	public static int scroll; 
	public static int money;

	public static int lastTime;
	public static int stagetime;

	private final int IMAGE_NUM = 33;
	private final Bitmap[] image = new Bitmap[IMAGE_NUM];



	public MainView(Context context) {
		super(context);
		paint.setAntiAlias(true);

		key = new KeyCode(this);

		Resources r = getResources();

		image[  0 ] = BitmapFactory.decodeResource(r, R.drawable.player01);
		image[  1 ] = BitmapFactory.decodeResource(r, R.drawable.princess);
		image[  2 ] = BitmapFactory.decodeResource(r, R.drawable.titleback);
		image[  3 ] = BitmapFactory.decodeResource(r, R.drawable.st1back);
		image[  4 ] = BitmapFactory.decodeResource(r, R.drawable.st5boss);
		image[  5 ] = BitmapFactory.decodeResource(r, R.drawable.snaken);
		image[  6 ] = BitmapFactory.decodeResource(r, R.drawable.slime);
		image[  7 ] = BitmapFactory.decodeResource(r, R.drawable.goblin);
		image[  8 ] = BitmapFactory.decodeResource(r, R.drawable.st1boss);
		image[  9 ] = BitmapFactory.decodeResource(r, R.drawable.block);
		image[ 10 ] = BitmapFactory.decodeResource(r, R.drawable.coin);
		image[ 11 ] = BitmapFactory.decodeResource(r, R.drawable.arrow);
		image[ 12 ] = BitmapFactory.decodeResource(r, R.drawable.recovery);
		image[ 13 ] = BitmapFactory.decodeResource(r, R.drawable.ballon);
		image[ 14 ] = BitmapFactory.decodeResource(r, R.drawable.break_block);
		image[ 15 ] = BitmapFactory.decodeResource(r, R.drawable.st2back);
		image[ 16 ] = BitmapFactory.decodeResource(r, R.drawable.st3back);
		image[ 17 ] = BitmapFactory.decodeResource(r, R.drawable.bat);
		image[ 18 ] = BitmapFactory.decodeResource(r, R.drawable.st4boss);
		image[ 19 ] = BitmapFactory.decodeResource(r, R.drawable.merchant);
		image[ 20 ] = BitmapFactory.decodeResource(r, R.drawable.st3boss);
		image[ 21 ] = BitmapFactory.decodeResource(r, R.drawable.st4back);
		image[ 22 ] = BitmapFactory.decodeResource(r, R.drawable.st5back);
		image[ 23 ] = BitmapFactory.decodeResource(r, R.drawable.openingback);
		image[ 24 ] = BitmapFactory.decodeResource(r, R.drawable.maruta);
		image[ 25 ] = BitmapFactory.decodeResource(r, R.drawable.fire);
		image[ 26 ] = BitmapFactory.decodeResource(r, R.drawable.devil);
		image[ 27 ] = BitmapFactory.decodeResource(r, R.drawable.cowman);
		image[ 28 ] = BitmapFactory.decodeResource(r, R.drawable.boss_slime);
		image[ 29 ] = BitmapFactory.decodeResource(r, R.drawable.town);
		image[ 30 ] = BitmapFactory.decodeResource(r, R.drawable.fairy);
		image[ 31 ] = BitmapFactory.decodeResource(r, R.drawable.endback);
		image[ 32 ] = BitmapFactory.decodeResource(r, R.drawable.clock);

		isThreadrunning = true;
		holder = getHolder();
		holder.addCallback(this);

		holder.setFixedSize(screenX,screenY );	

		g = new GraphicsUtility(holder);
		sound = new Sound(context);
		sound.seLoad();

		//		data = new SaveData(this);
		setFocusable(true);
		setFocusableInTouchMode(true);
	}


	public void surfaceCreated(SurfaceHolder holder) {
		thread = new Thread(this);
		thread.start();
	}

	public void surfaceChanged(SurfaceHolder holder, int format, int w, int h) {

	}

	public void surfaceDestroyed(SurfaceHolder holder) {

		sound.bgmStop();
		boolean retry = true;
		synchronized (this.holder) {
			isThreadrunning = false;
		}
		while (retry) {
			try {
				thread.join();
				retry = false;
			} catch (InterruptedException e) {
			}
		}
		thread = null;
	}


	/**
	 * TODO 
	 * 2012/02/12 マルチタッチ対応
	 * @author yeguchi
	 * @see  <a href="http://techbooster.org/android/application/2299/">MotionEventでマルチタッチを検出する</a>
	 * */
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// MotionEvent取得用変数
		eventCode =event.getAction();
		pointerCount = event.getPointerCount();
		touchIndex = (eventCode & MotionEvent.ACTION_POINTER_ID_MASK) >> MotionEvent.ACTION_POINTER_ID_SHIFT;
		eventID = event.getPointerId(touchIndex);
		
		// タップした座標を取得
		touch1X = (int) event.getX(touchIndex);
		touch1Y = (int) event.getY(touchIndex);
		
		touch2X = (int) event.getX(touchIndex++);
		touch2Y = (int) event.getY(touchIndex++);
		
		Log.e("touch1X",""+touch1X);
		Log.e("touch1Y",""+touch1Y);
		Log.e("touch2X",""+touch2X);
		Log.e("touch2Y",""+touch2Y);
		
		Log.e("eventCode",""+eventCode);
		Log.e("pointerCount",""+pointerCount);
		Log.e("touchIndex",""+touchIndex);
		Log.e("eventID",""+eventID);
		// 通知ポインタ数取得
/*		if (pointerCount<1) {
			return false;
		}
*/		
		// タップ位置をダブル型に変換
		touch1X *=  ( (double)screenX / (double)getWidth() );
		touch1Y *=  ( (double)screenY / (double)getHeight() );

		if (eventCode == MotionEvent.ACTION_DOWN) {
			firstTouch = true;
		}else if(eventCode == MotionEvent.ACTION_POINTER_DOWN ){
			// 1箇所をタップしている状態で2箇所目を押した場合
			secondTouch = true;
		}
		if (eventCode == MotionEvent.ACTION_UP) {
			// タップをすべて離した場合
			firstTouch = false;
//			secondTouch = false;
		}else if(eventCode == MotionEvent.ACTION_POINTER_UP){
			// 2箇所以上のうち、1箇所のみ離した場合
			secondTouch = false;
		}
		
		invalidate();
		return true;
	}

	
	public void run() {
		initAll();
		while (isThreadrunning) {
			t1 = System.currentTimeMillis();

			process();
			g.lock();
			GameView();
			g.unlock();
			t2 = System.currentTimeMillis();

			if (t2 - t1 < 33) { // 1000 / 30 = 33.3333
				try {
					Thread.sleep(33 - (t2 - t1));
				} catch (InterruptedException e) {
				}
			}
		}
	}



	//MEMO initAll	 
	public void initAll() {

		//��������������������������������������
		PLAY_SCENE	 	= TITLE;
		//��������������������������������������	
		demo = null;
		firstTouch  	= false;
		secondTouch  	= false;
		rightTouch 	= false;
		leftTouch 	= false;
		upTouch 	= false;
		halfupTouch = false;
		downTouch	= false;
		rand = new Random();
		EndCnt = 0;
		money	 	= 0;
		stageClearFlg 	= false;
		ClearCnt 		= 0;
		scroll 		= 0;
		sound.bgmPlay(Sound.opening);

		try {
			SaveData.intRead( this ,"load.txt" , true);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		initTitle();
	}

	public void initStates(){	
		LV 			=  0;
		LVupCnt 	=  0;
		HitPoint 	=  LifeMax[LV];
		EXP 		=  0;
		LIFE 		=  3;
	}

	public void initTitle(){
		arrowY 		=  STARTselect;
		fColor 		= 	0;
		sColor 		=	0;
		loadColor 	= 	0;
		optColor 	= 	0;
		titleCnt 	=   0;

	}

	public void initStage(){

		loopCnt = 0;		
		map = new Map(this);
		MAP_WIDTH = bX * Map.mapArrayX;
		MAP_HEIGHT = bY * Map.mapArrayY;

		// if(SAVE_DATA == DATA_EMPTY){ 
		switch(STAGE_NUM){
		case STAGE1: 	   
			p = new Player(this ,50,224 ,map);
			//	    	p = new Player(this , 2500,224 ,map);
			break;
		case STAGE2:	    
			p = new Player(this , 50, 500 ,map);
			break;	    
		case STAGE3:	    
			p = new Player(this ,50 , 480 ,map);
			break;
		case STAGE4:	    
			p = new Player(this ,50, 400 ,map);
			break;
		case STAGE5:	    
			p = new Player(this ,50, 500 ,map);
			break;	    	   
		}

		map.initItem(this);     
		map.initEnemy(this); 
		//map.initBlock(this); 
		stagetime 	= 100;	
		ClearCnt	=   0;
		EndCnt 		=   0;

		if( STAGE_NUM == STAGE5 ){sound.bgmPlay(Sound.laststage);}
	}


	public void process() {

		switch (PLAY_SCENE) {

		case TITLE:
			Title();
			break;

		case START_DIALOG:
			StartDialog();
			break;

		case LOAD_DIALOG:
			LoadDialog();
			break;

		case LOAD_CANCEL:
			LoadCancel();
			break;

		case PLAY_NOW:
			StagePlay();
			break;

		case SAVE_NOW:
			SaveDialog();
			break;

		case GAMEOVER:
			Gameover();
			break;
		}
	}


	public void Option(){
		ControlButton();
		if( touch1X>bX*6 && touch1Y < bY*2){
			initTitle();
			PLAY_SCENE = TITLE;
		}
	}

	public void StagePlay() {		
		lastTime = stagetime - ( loopCnt / 20 );
		p.update();	   			
		if(demo == null && stageClearFlg == false){
			ControlButton();					
			for(int i = 0 ; i< enemyList.size() ;i++){
				if(enemyList.get(i).CheckInMap()){
					enemyList.get(i).update();  		
				}
			}
			if (loopCnt++ > 999999) {loopCnt = 0;}
		}

		if( LV <= 8 &&  EXP >= EXPMax[LV] ){
			sound.sePlay(Sound.lvup);
			HitPoint += ( EXPMax[LV+1] - EXPMax[LV]);
			EXP 	= 0;			
			LV++;
			LVupCnt    = 1;
		}

		if( LVupCnt > 0 ){
			LVupCnt ++;	
			if( LVupCnt > 50){ 
				LVupCnt = 0;
			}
		}

		if( stageClearFlg == true ){
			setPlayer(new PlayerClear(this, p.getX(), p.getY(),map));
			ClearCnt += 3;			
			if(	ClearCnt >=255 ){
				ClearCnt =255;
				sound.bgmStop();
				getSound(Sound.fanfare);
				stageClearFlg=false;
				ListClear();
				StageClear();
			}
		}
	} // PLAYING END 

	public void SaveDialog( ){
		if(firstTouch){
			if( (touch1X > bX *2 && touch1X < bX *5) &&
					( touch1Y > bY*8 && touch1Y < bY*9 ) ){
				getSound(Sound.select);

				try {
					SAVE_DATA = DATA_EXIST;
					SaveData.intWrite( this ,"load.txt" , false);
					sound.bgmStop();					
					ListClear();
					PLAY_SCENE 	= TITLE;
				} catch (Exception e) {
					e.printStackTrace();
				}
			}else if( (touch1X >bX *10 && touch1X < bX * 13) &&
					(touch1Y>bY*8 && touch1Y<bY*9) ){
				PLAY_SCENE 	= PLAY_NOW;
			}
		}
	}

	public void StageClear(){
		switch(STAGE_NUM){
		case STAGE1:
			demo = new St1ClearView(this);		    
			//			demo = new EndingView(this);//�m�F�p		    				
			STAGE_NUM =STAGE2;	   
			break;

		case STAGE2:
			demo = new St2ClearView(this);		    			
			STAGE_NUM =STAGE3;	   
			break;

		case STAGE3:
			demo = new St3ClearView(this);		    
			STAGE_NUM =STAGE4;	   
			break;

		case STAGE4:
			demo = new St4ClearView(this);
			STAGE_NUM =STAGE5;	        
			break;

		case STAGE5:
			demo = new EndingView(this);
			initAll();
			break;	

		}
		initStage();				
	}


	public void ListClear(){
		itemList.clear();
		enemyList.clear();
		blockList.clear();			
	}

	/**
	 *  GAMEOVER
	 * @param GameOver
	 * */
	private void Gameover() {

		lastTime=0;	
		p.update();
		if(LIFE == 1){			
			if((touch1X > bX *4 && touch1X < bX *9) && ( touch1Y > bY*3 && touch1Y < bY*5 ) && firstTouch ){
				sound.bgmStop();
				ListClear();
				SAVE_DATA = DATA_EMPTY;
				try {
					SaveData.intWrite( this ,"load.txt" ,true);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				initAll();
			}

		}else if( EndCnt > 50 && LIFE > 1){				
			if(	 (touch1X > bX && touch1X < bX *8) && ( touch1Y > bY*3 && touch1Y < bY*4 ) && firstTouch ){								

				ListClear();
				LIFE--;
				HitPoint 	=  LifeMax[LV];
				PLAY_SCENE = PLAY_NOW;
				initStage();

			}else if( (touch1X > bX*8 && touch1X < bX *15) && ( touch1Y > bY*3 && touch1Y < bY*5 ) && firstTouch ){							
				sound.bgmStop();
				ListClear();
				SAVE_DATA = DATA_EMPTY;

				try {
					SaveData.intWrite( this ,"load.txt" ,true);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				initAll();
			}
		}
		EndCnt++;
	}


	/**
	 * @params 
	 * 2012/2/13：ジャンプ仕様変更
	 * ジャンプボタンをひとつに変更 → 移動時間によってジャンプ力が変わる
	 * */
	public void ControlButton(){
		
		if (firstTouch) {			
			if ( touch1X < bX *2 && touch1Y > bY *7 ) {
				p.accelL();
				leftTouch = true;
//				rightTouch = false; 
				if( secondTouch && getTouch1X() >= bX*(YOKO-2) && getTouch1Y() >= bY*8 ){
					upTouch = true; 
					p.jump();
				}
			}else if ( touch1X > ( bX*2 ) && touch1X < ( bX*4 ) && touch1Y > bY*7 ){
				p.accelR();				
				rightTouch = true; 
//				leftTouch = false;
				if(secondTouch && getTouch1X() >= bX*(YOKO-2) && getTouch1Y() >= bY*8 ){
					upTouch = true; 
					p.jump();
				}
			}
			if( ( getTouch1X() >= bX*(YOKO-2) && 
					( getTouch1Y() >= bY*3 && getTouch1Y() <= bY*6)  )){
				upTouch = true; 
				p.jump();
				if( secondTouch && touch1X < bX *2 && touch1Y > bY *7 ){
					p.accelL();
					leftTouch = true;
				}else if( secondTouch && touch1X > ( bX*2 ) && touch1X < ( bX*4 ) && touch1Y > bY*7 ){
					p.accelR();				
					rightTouch = true; 
				}
			}

			if( ( getTouch1X() >= bX*(YOKO-2) && getTouch1Y() >= bY*8 )){
				halfupTouch = true; 
//				upTouch = true; 
				p.jump();
				if( secondTouch && touch1X < bX *2 && touch1Y > bY *7 ){
					p.accelL();
					leftTouch = true;
				}else if( secondTouch && touch1X > ( bX*2 ) && touch1X < ( bX*4 ) && touch1Y > bY*7 ){
					p.accelR();				
					rightTouch = true; 
				}
			}
			
			
			// セーブボタンタップ時、セーブ画面に移動
			if( getTouch1X() >= bX*(YOKO-2) && getTouch1Y() <= bY ){
				PLAY_SCENE = SAVE_NOW;
			}
		} else {
			leftTouch	 = false; 
			rightTouch	 = false; 
			upTouch 	 = false; 
			halfupTouch  = false; 
			p.stop();
		}

		
		// ============== キーボード操作用 ===================
		if ( key.checkKey(KeyCode.KEY_RIGHT) ) {
			rightTouch = true;
			p.accelR();
		}
		if ( key.checkKey(KeyCode.KEY_LEFT) ) {			
			leftTouch = true;
			p.accelL();

		}
		if ( key.checkKey(KeyCode.KEY_UP) ) {
//			halfupTouch = true;
			upTouch = true;
			p.jump();			
		}

		if ( key.checkKey(KeyCode.KEY_ENTER) ) {
			upTouch = true;
			p.jump();			
		}

	}


	//@Override
	public boolean onKeyDown(int keyCode, KeyEvent msg ) {
		return key.onKeyDown(keyCode, msg);
	}

	//@Override
	public boolean onKeyUp(int keyCode, KeyEvent msg) {
		return key.onKeyUp(keyCode, msg);
	}

	/**
	 * MAIN VIEW 
	 * @param switch SCENE
	 * */
	public void GameView() {
		switch (PLAY_SCENE) {
		case TITLE:
		case START_DIALOG:
		case LOAD_DIALOG:		
		case LOAD_CANCEL:		
			TitleView();
			break;
		case OPENING:
			if(demo.DemoPlay()){
				demo = null;				
				PLAY_SCENE = PLAY_NOW;
				STAGE_NUM = STAGE1;
			}		
			break;			
		case OPTION:
			if(demo.DemoPlay()){
				demo = null;
				initTitle();
				PLAY_SCENE = TITLE;
			}
			break;			
		case PLAY_NOW:
		case SAVE_NOW:	
			StageView();
			break;
		case GAMEOVER:
			StageView();
			GameOverView();
			break;
		}
	}// MainView -----END------

	/**
	 *  ControlPanel
	 * */
	public void ControlPanel(){

		Rect src = new Rect();
		Rect dst = new Rect();
		if( leftTouch ){
			src.set(getImgWidth(Img.arrow)/2, 0, getImgWidth(Img.arrow) , getImgHeight(Img.arrow)/4 );
			dst.set( 0 , bY*7 ,bX*2 ,bY*TATE );
			drawBitmap(Img.arrow, src  , dst );
		}else{
			src.set(0, 0, getImgWidth(Img.arrow)/2 , getImgHeight(Img.arrow)/4 );
			dst.set( 0 , bY*7 ,bX*2 ,bY*TATE );
			drawBitmap(Img.arrow, src  , dst );			
		}		

		if( rightTouch ){
			src.set(  getImgWidth(Img.arrow)/2 , getImgHeight(Img.arrow)/4 ,getImgWidth(Img.arrow), (getImgHeight(Img.arrow)/4) *2 );
			dst.set(bX*2 , bY*7 ,bX*4 ,bY*TATE );
			drawBitmap(Img.arrow, src  , dst );	
		}else{
			src.set( 0 , getImgHeight(Img.arrow)/4 ,getImgWidth(Img.arrow)/2, (getImgHeight(Img.arrow)/4) *2 );
			dst.set(bX*2 , bY*7 ,bX*4 ,bY*TATE );
			drawBitmap(Img.arrow, src  , dst );				
		}

     	if( upTouch ){
			src.set(  getImgWidth(Img.arrow)/2 , (getImgHeight(Img.arrow)/4) *2 , getImgWidth(Img.arrow) , (getImgHeight(Img.arrow)/4) *3 );
			dst.set(bX*(YOKO-2) , bY*3 ,bX*YOKO ,bY*6  );		
			drawBitmap(Img.arrow, src  , dst );			
		}else{
			src.set( 0 , (getImgHeight(Img.arrow)/4) *2 , getImgWidth(Img.arrow)/2 , (getImgHeight(Img.arrow)/4) *3 );
			dst.set(bX*(YOKO-2) , bY*3 ,bX*YOKO ,bY*6  );		
			drawBitmap(Img.arrow, src  , dst );					
		}

		if( halfupTouch ){
			src.set(  getImgWidth(Img.arrow)/2 , (getImgHeight(Img.arrow)/4) *2 , getImgWidth(Img.arrow) , (getImgHeight(Img.arrow)/4) *3 );
			dst.set(bX*(YOKO-2) , bY*7 ,bX*YOKO ,bY*TATE  );		
			drawBitmap(Img.arrow, src  , dst );			
		}else{
			src.set( 0 , (getImgHeight(Img.arrow)/4) *2 , getImgWidth(Img.arrow)/2 , (getImgHeight(Img.arrow)/4) *3 );
			dst.set(bX*(YOKO-2) , bY*7 ,bX*YOKO ,bY*TATE  );		
			drawBitmap(Img.arrow, src  , dst );					
		}

	} //ControlPanel -----END------




	public void StStartLogo(){

		g.setFontSize(45);
		g.setColor(Color.BLACK);
		g.drawString( "STAGE"+(STAGE_NUM+1) , bX , bY*4 );
		g.setColor(Color.MAGENTA);// �\�ʕ`��
		g.drawString( "STAGE"+(STAGE_NUM+1) , bX +3 , bY*4 +3 );	

	}

	private void StageView() {

		if(demo != null){
			if(demo.DemoPlay()){
				demo = null;
			}
		}else{
			int bgImg = STAGE_BACK [ STAGE_NUM ];
			// STAGE BACK SCREEN[ 1 ]
			drawBitmapEasy(bgImg, getImgWidth(bgImg), getImgHeight(bgImg), 
					0, 0, screenX, screenY);
			MapView(); 
			States();
			if(PLAY_SCENE == SAVE_NOW){
				DialogView("","セーブしてタイトルに戻ります");
			}

			if(loopCnt < 50 ){ StStartLogo(); }	
			if( stageClearFlg == true ){ StClearLogo();	}

		}

	}//STAGE -----END------


	public void StClearLogo(){		

		g.setColor(Color.argb( ClearCnt,255,255,255) );					
		g.fillRect(0, 0, screenX,screenY);		

		if( (ClearCnt/3) %2 ==0 ){
			g.setFontSize(40);
			g.setColor(Color.BLACK);
			g.drawString( "STAGE "+ (STAGE_NUM+1) + " CLEAR" , 85 , 105 );
			g.setColor(Color.MAGENTA);
			g.drawString("STAGE "+ (STAGE_NUM+1) + " CLEAR" ,  80 , 100 );	
		}

	}

	public void States(){

		g.setColor(Color.argb(50,0,0,0) );
		g.fillRect( 0 , 0, screenX , bY*2 );

		g.setFontSize(25);				
		//LV
		if( LV < 8 ){
			g.setColor(Color.BLACK);
			g.drawString( "LV:"+ (LV+1) , 0 -2, 20 -2 );
			g.setColor(Color.WHITE);
			g.drawString( "LV:"+ (LV+1) , 0, 20 );

		}else{
			g.setColor(Color.WHITE);
			g.drawString( "LV:MAX" , 0 -2, 20 -2 );			
			g.setColor(Color.RED);
			g.drawString( "LV:MAX" , 0, 20 );			
		}

		if( LVupCnt > 0 && LVupCnt < 50 ){
			g.setFontSize(40);				
			if( ( (loopCnt)/3)%2 == 0){
				g.setColor(Color.argb(150,255,50, 0));
				g.drawString("LVUP!!" , 0, bY *3 );		
			}
		}

		g.setFontSize(20);
		g.setColor(Color.BLACK);
		g.drawString("EXP:" , 50 -2, 20 -2 );
		g.setColor(Color.WHITE);
		g.drawString("EXP:" , 50, 20 );


		if( LV < 8 ){
			g.setColor(Color.BLACK);
			g.drawString(EXP +"/"+ EXPMax[ LV ], 90-2, 20-2);
			g.setColor(Color.WHITE);
			g.drawString(EXP +"/"+ EXPMax[ LV ], 90, 20);
		}

		g.setFontSize(25);
		g.setColor(Color.BLACK);
		g.drawString("HP:" , 0-2, 50-2 );
		g.setColor(Color.WHITE);
		g.drawString("HP:" , 0, 50 );				

		g.setColor(Color.BLACK);
		g.fillRect( bX +10, bY, ( LifeMax[ LV ] ) , HitPointBar);				

		g.setColor(Color.argb(255,0,255,0));


		if( HitPoint < 20 ){g.setColor(Color.RED);}		
		g.fillRect( bX+10, bY, ( HitPoint ), HitPointBar);				


		if(HitPoint < 0){HitPoint=0;}		
		g.setColor(Color.BLACK);
		g.drawString( HitPoint +"/"+ LifeMax[LV]  , bX +20-2 , bY*2-2 );
		g.setColor(Color.WHITE);
		g.drawString( HitPoint +"/"+ LifeMax[LV]  , bX +20 , bY*2 );


		Rect src = new Rect(0,0,getImgWidth(Img.coin)/2,getImgHeight(Img.coin));
		Rect dst = new Rect	( 170 , 0 , 170 +20 , 0 +20);
		drawBitmap(Img.coin,src,dst);


		g.setFontSize(20);
		g.setColor(Color.BLACK);
		g.drawString( "x"+g.scoreNumber(money) , 190 -2, 20 -2);
		g.setColor(Color.WHITE);
		g.drawString( "x"+g.scoreNumber(money) , 190, 20 );


		g.setColor(Color.BLACK);	
		g.setColor(Color.WHITE);
		g.drawString("TIME:" , 255 , 20 );

		g.setColor(Color.argb(120,180,180,180));
		g.fillRect( 310 , 5, ( stagetime ) , bY/2);	


		g.setColor(Color.argb(150,0,0,230));
		if( lastTime < 20 ){g.setColor(Color.argb(150,255,0,0));}
		g.fillRect( 310 , 5, ( lastTime ), bY/2);			


		for(int x = 0 ; x < LIFE ; x++){			
			Rect src1 = new Rect(getImgWidth(Img.player)/3 * 0 ,
					getImgHeight(Img.player)/8 * 4 + 5, 
					getImgWidth(Img.player)/3* ( 0 + 1 ) , 
					getImgHeight(Img.player)/8 * ( 4 + 1 ) +5 );		
			Rect dst1 = new Rect	( 250+(x*30) , 25 , 250+(x*30) + 35 , 25 + 30 );

			drawBitmap(Img.player,src1,dst1);
		}


		if(loopCnt%2 == 0 ){
			g.setFontSize(20);
			g.setColor(Color.BLACK);
			g.drawString( "SAVE", bX*14 -2, 20 -2 );
			g.setColor(Color.WHITE);
			g.drawString( "SAVE", bX*14, 20 );
		}

		g.setFontSize(20);
		g.setColor(Color.WHITE);
		g.drawString("TIME:" , 255 , 20 );	

		//		g.setFontSize(25);		
		//		g.setColor(Color.BLACK);// �\�ʕ`��
		//		g.drawString("loop:"+g.scoreNumber(loopCnt) , bX*12 , 300 );	
		//
		//		g.setFontSize(20);
		//		g.drawString("vy:" + p.getVY() , 0, 100 );
		//		g.drawString("iX:" + p.getX() , 0, 100 );
		//		g.drawString("iY:" + p.getY() , 0, 125 );

		ControlPanel();
	}



	/**
	 * MAP VIEW
	 * @param screenX 480 , screenY 320
	 * */
	public void MapView(){

		offsetX = screenX / 2 - (int)p.getX();
		offsetX = Math.min(offsetX, 0);
		offsetX = Math.max(offsetX, screenX - ( MAP_WIDTH ) );

		offsetY = screenY / 2 - (int)p.getY();

		offsetY = Math.min(offsetY, 0);
		offsetY = Math.max(offsetY, screenY - ( MAP_HEIGHT ) );

		map.drawBlock(offsetX, offsetY);
		Coindraw(offsetX, offsetY);
		Enemydraw(offsetX, offsetY);
		p.onBitmap(offsetX, offsetY);

	}


	/**
	 * GAMEOVER VIEW
	 * @param GAMEOVER MainView
	 * */
	private void GameOverView() { 

		if( EndCnt > 50 ){

			if(LIFE == 1){
				g.setFontSize(30);
				g.setColor(Color.argb(255, 255, 0, 0));
				g.drawString("GAMEOVER", bX*4, bY*3);	
				g.setColor(Color.argb(255, 200, 200, 200));
				g.drawString("GAMEOVER", bX*4 +2, bY*3 +2);


				if( EndCnt%3 ==0 ){
					g.setFontSize(25);
					g.setColor(Color.BLUE);
					g.drawString("TOUCH HERE", bX*4 , ( bY * 4 ) );
					g.drawString("[RETURN TITLE]", bX*4 , ( bY * 5 ) );
				}
			}else{
				g.setFontSize(25);
				g.setColor(Color.argb(255, 255, 255, 255));
				g.drawString("CONTINUE", bX*5, bY*3);	
				g.setColor(Color.argb(255, 200, 200, 200));
				g.drawString("CONTINUE", bX*5 +2, bY*3 +2);

				g.setFontSize(30);
				//YES	
				g.setColor(Color.argb(255, 255, 255, 255));
				g.drawString("YES", bX*3, bY*4);
				g.setColor(Color.argb(255, 200, 200, 200));
				g.drawString("YES", bX*3+5, bY*4+5);
				//NO
				g.setColor(Color.argb(255, 255, 255, 255));
				g.drawString("NO", bX*10, bY*4);
				g.setColor(Color.argb(255, 200, 200, 200));
				g.drawString("NO", bX*10+5, bY*4+5);
			}	
		}

	}



	/**
	 * @param TITLE 
	 * */	
	private void Title() { // 

		if (fColor < 255) {
			fColor += 5;
			if(fColor >= 255)fColor = 255;
		}
		if (fColor == 255 && sColor <255) {
			sColor += 15;
			if(sColor>=255)sColor = 255;
		}
		if (sColor == 255 && loadColor< 255) {
			loadColor += 15;
			if(loadColor>=255)loadColor = 255;		
		}
		if (loadColor == 255 && optColor<255) {
			optColor += 15;
			if(optColor>=255)optColor = 255;
		}
		if(optColor == 255){
			if ( touch1Y > STARTselect -10 && touch1Y < STARTselect +10 && touch1X < bX*4 ) {arrowY = STARTselect;}				
			if ( touch1Y > LOADselect -10 && touch1Y < LOADselect +10 && touch1X < bX*4) {arrowY = LOADselect;}
			if ( touch1Y > OPTselect -10 && touch1Y < OPTselect +10 && touch1X < bX*4) {arrowY = OPTselect;}		

			if(firstTouch){
				if( arrowY == STARTselect && ( touch1Y > bY*3 && touch1Y < bY*4 && touch1X > bX*7 ) ){				
					PLAY_SCENE = START_DIALOG;
				}else if( arrowY == LOADselect  && ( touch1Y > bY*3 && touch1Y < bY*4 && touch1X > bX*7 )  ){				
					if( SAVE_DATA ==DATA_EMPTY )PLAY_SCENE = LOAD_CANCEL;
					if( SAVE_DATA ==DATA_EXIST )PLAY_SCENE = LOAD_DIALOG;
				}else if( arrowY == OPTselect && ( touch1Y > bY*3 && touch1Y < bY*4 && touch1X > bX*7 ) ){
					getSound(Sound.select);
					demo = new OptionView(this);
					PLAY_SCENE = OPTION;
				}					
			}	
		}	
	}
	/**
	 * TITLE��ʂ̏�� 
	 * @param TITLE 
	 * */
	private void TitleView() {
		drawBitmapEasy(
				Img.titleback, getImgWidth(Img.titleback), getImgHeight(Img.titleback), 0, 0, screenX, screenY );
		g.setFontSize(45);
		g.setColor(Color.argb(fColor,0,0,0));
		g.drawString("VAGABONDER QUEST", 20-3, bY*2 -2  );

		g.setFontSize(45);
		g.setColor( Color.argb(fColor,0,255,255) );
		g.drawString("VAGABONDER QUEST", 20 , bY*2 );

		g.setFontSize(30);

		if(arrowY == STARTselect ){
			g.setColor(Color.argb(sColor,0, 255 , 255));
		}else{
			g.setColor(Color.argb(sColor,0, 0 , 0));
		}				
		g.drawString("-START-", 20-3, STARTselect-1 );
		g.drawString("-START-", 20 , STARTselect);


		if( arrowY == LOADselect ){
			if(SAVE_DATA == DATA_EMPTY )g.setColor(Color.argb(loadColor,255, 0 , 0));
			if(SAVE_DATA == DATA_EXIST )g.setColor(Color.argb(loadColor,0, 255 , 255));
		}else{
			g.setColor(Color.argb(loadColor,0, 0 , 0));

			if(SAVE_DATA == DATA_EMPTY && loadColor>=255){
				g.setColor(Color.argb( ( 50 ) ,0, 0 , 0));
			}				
		}	

		g.drawString("-LOAD-", 20-3, LOADselect-1 );
		g.drawString("-LOAD-", 20 , LOADselect );

		if(arrowY == OPTselect ){
			g.setColor(Color.argb(optColor,0, 255 , 255 ));
		}else{
			g.setColor(Color.argb(optColor,0, 0 , 0));
		}				
		g.drawString("-OPTION-", 20-3, OPTselect-1 );
		g.drawString("-OPTION-", 20, OPTselect );


		if(optColor>= 255){
			if ( ( titleCnt/5 ) % 2 == 0 ) {
				g.setFontSize(40);
				g.setColor(Color.BLACK);
				g.drawString("[SELECT]", bX *8  , bY*3 +20);
				g.setColor(Color.BLUE);
				g.drawString("[SELECT]", bX *8 +3  , bY*3 +20 +3);
			}
		}

		if ( titleCnt > 100 ) {
			FaceUp(Img.player, getImgWidth(Img.player), getImgHeight(Img.player), 0, 1, 1);
			FaceUp(Img.princess, getImgWidth(Img.princess), getImgHeight(Img.princess), 0, 0, 2);
		}

		titleCnt++;

		if(PLAY_SCENE == START_DIALOG){DialogView("","セーブデータが初期化されますがよろしいですか");}
		if(PLAY_SCENE == LOAD_DIALOG){DialogView("","セーブデータから開始します");}
		if(PLAY_SCENE == LOAD_CANCEL){CancelView("キャンセルします");}

	}


	public void setSAVE(int x){
		SAVE_DATA = x;		
	}

	public int getSAVE(){
		return SAVE_DATA;		
	}

	public void setSTAGE(int x){
		STAGE_NUM = x;		
	}

	public int getSTAGE(){
		return STAGE_NUM;		
	}

	public void setLEVEL(int x){
		LV = x;		
	}

	public int getLEVEL(){
		return LV;		
	}

	public void setHP(int x){
		HitPoint = x;		
	}

	public int getHP(){
		return HitPoint;		
	}

	public void setEXP(int x){
		EXP = x;		
	}

	public int getEXP(){
		return EXP;		
	}

	public void setLIFE(int x){
		LIFE = x;		
	}

	public int getLIFE(){
		return LIFE;		
	}

	public void setTIME(int x){
		lastTime = x;		
	}

	public int getTIME(){
		return lastTime;		
	}

	public void setMoney(int x){
		money = x;	
	}

	public int getMoney(){
		return money;	
	}

	public void setLoop(int x){
		loopCnt = x;	
	}

	public int getLoop(){
		return loopCnt;	
	}

	public void setPLAY_SCENE(int x){
		PLAY_SCENE = x;		
	}

	public int getPLAY_SCENE(){
		return PLAY_SCENE;		
	}

	public GraphicsUtility getUtil() {
		return g;
	}

	public void drawBitmap(int ID,Rect src,Rect dst ){
		g.drawBitmap( image[ID], src, dst ); //�w�i�摜	
	}

	/**
	 * @param 3 x 4
	 * @return
	 * */
	public void drawBitmapCharacter(int ID, int Width , int Height , int w , int h , int x , int y , int SizeX , int SizeY ) {

		Rect src = new Rect(Width/3 * w , Height/4 * h , Width/3* ( w + 1 ) , Height/4 * ( h + 1 ) );		
		Rect dst = new Rect	( x , y , x + SizeX , y + SizeY );

		g.drawBitmap( image[ID],  src , dst );
	}	

	/**
	 * @param
	 * @return 
	 * */
	public void drawIcon(int w , int h , int x , int y ) {

		int iW = getImgWidth(Img.ballon);
		int iH = getImgHeight(Img.ballon);

		int bSizeX = bX*2;
		int bSizeY = bY*2;

		Rect src = new Rect(iW/2 * w , iH/4 * h , iW/2* ( w + 1 ) , iH/4 * ( h + 1 ) );		
		Rect dst = new Rect	();
		dst.set( x , y - bSizeY , x + bSizeX , y );

		g.drawBitmap( image[Img.ballon] ,  src , dst );

	}	


	/**
	 * @param 
	 * @return image , getWidth() , getHeight() , x[��W] , y[��W] , SizeX[�T�C�YX] , SizeY[�T�C�YY]
	 * */
	public void drawBitmapEasy(int ID,int Width , int Height , int x , int y , int SizeX , int SizeY  ) {

		Rect src = new Rect(0,0,Width,Height);
		Rect dst = new Rect	( x , y , x + SizeX , y + SizeY );
		g.drawBitmap( image[ID], src , dst  );
	}


	/**
	 * @param
	 * @return image,getWidth(),getHeight(),w[�摜���Ԗ�],h[�摜�c�Ԗ�],screenX[�Ԗ�]
	 * */
	public void FaceUp(int ID ,int Width , int Height , int w , int h , int x){

		Rect src = new Rect(Width/3 * w , Height/4 * h , 
				Width/3* ( w + 1 ) , Height /4 * ( h + 1 ) );

		Rect dst = new Rect	( MainView.screenX/3 * x ,MainView.screenY/3,
				MainView.screenX/3 * ( x + 1 ),( (MainView.screenY/3) * 4 ));

		g.drawBitmap( image[ID] , src , dst  );	
	}

	public int getImgWidth(int ID){
		return image[ID].getWidth();
	}

	public int getImgHeight(int ID){
		return image[ID].getHeight();
	}

	public int getTouch1X(){		
		return touch1X;
	}
	
	public int getTouch2X(){		
		return touch2X;
	}

	public int getTouch1Y(){		
		return touch1Y;
	}

	public int getTouch2Y(){		
		return touch2Y;
	}

	public void Coindraw(int offsetX ,int offsetY){
		for(int i = 0 ; i< itemList.size() ;i++){
			itemList.get(i).onBitmap(offsetX, offsetY);	
		}	
	}

	public ArrayList<ItemBase> getItemList(){
		return itemList;
	}

	public void Enemydraw(int offsetX ,int offsetY){
		for(int i = 0 ; i< enemyList.size() ;i++){
			enemyList.get(i).onBitmap(offsetX, offsetY);
		}	
	}

	public ArrayList<EnemyBase> getEnemyList(){		
		return enemyList;
	}

	public void Blockdraw(int offsetX ,int offsetY){				
		for(int i = 0 ; i< blockList.size() ;i++){
			blockList.get(i).onBitmap(offsetX, offsetY);	
		}	
	}


	public ArrayList<BlockBase> getBlockList(){		
		return blockList;
	}

	public void setPlayer(Player player){
		p = player;
	}

	public Player getPlayer(){
		return p;		
	}

	public void getSound(int soundID){
		sound.sePlay(soundID);
	}

	public void getBGM(int soundID){
		sound.bgmPlay(soundID);
	}

	public void bgmStop(){
		sound.bgmStop();
	}


	public int getOffsetX(){
		return offsetX;		
	}


	public int getOffsetY(){
		return offsetY;		
	}


	public void StartDialog( ){

		if(firstTouch){
			if( (touch1X > bX *2 && touch1X < bX *5) &&
					( touch1Y > bY*8 && touch1Y < bY*9 ) ){
				getSound(Sound.select);
				sound.bgmStop();

				STAGE_NUM 	= STAGE1;
				SAVE_DATA = DATA_EMPTY;
				try {
					SaveData.intWrite( this ,"load.txt" ,true);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				demo = new OpeningView(this);
				PLAY_SCENE = OPENING;				

				initStates();
				initStage();

			}else if( (touch1X >bX *10 && touch1X < bX * 13) &&
					(touch1Y>bY*8 && touch1Y<bY*9) ){
				PLAY_SCENE 	= TITLE;
			}
		}
	}


	public void LoadCancel( ){

		if(firstTouch){
			if( (touch1X > bX *2 && touch1X < bX *5) &&
					( touch1Y > bY*8 && touch1Y < bY*9 ) ){
				PLAY_SCENE 	= TITLE;
			}
		}
	}

	public void CancelView(String dialog){

		g.setColor(Color.argb(200, 0, 0, 0));
		g.fillRect(0, 0, screenX, screenY);

		g.setFontSize(20);
		g.setColor(Color.argb(255, 255, 0, 0));
		g.drawString(dialog, bX, bY*6);	
		g.setColor(Color.argb(255, 255,100, 0));
		g.drawString(dialog, bX+2, bY*6+2);

		//OK	
		g.setFontSize(30);
		g.setColor(Color.argb(255, 255, 255, 255));
		g.drawString("OK", bX*3, bY*9);
		g.setColor(Color.argb(255, 200, 200, 200));
		g.drawString("OK", bX*3 +3, bY*9 +3);
	}


	public void LoadDialog( ){
		if(firstTouch){
			if( (touch1X > bX *2 && touch1X < bX *5) &&
					( touch1Y > bY*8 && touch1Y < bY*9 ) ){
				try {					
					sound.bgmStop();
					getSound(Sound.select);
					initStage();
					SaveData.intRead( this ,"load.txt" ,false);
					SAVE_DATA = DATA_EMPTY;
					try{
						SaveData.intWrite( this ,"load.txt" ,true);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					PLAY_SCENE = PLAY_NOW;
				} catch (Exception e) {
					e.printStackTrace();
				}
			}else if( (touch1X >bX *10 && touch1X < bX * 13) &&
					(touch1Y>bY*8 && touch1Y<bY*9) ){
				PLAY_SCENE 	= TITLE;
			}
		}
	}


	public void DialogView( String dialog1 , String dialog2 ){
		g.setColor(Color.argb(200, 0, 0, 0));
		g.fillRect(0, 0, screenX, screenY);

		g.setFontSize(20);
		g.setColor(Color.argb(255, 255, 255, 255));
		g.drawString(dialog1, bX, bY*6);	
		g.setColor(Color.argb(255, 200, 200, 200));
		g.drawString(dialog1, bX+2, bY*6+2);

		g.setFontSize(20);
		g.setColor(Color.argb(255, 255, 255, 255));
		g.drawString(dialog2, bX, bY*7);	
		g.setColor(Color.argb(255, 200, 200, 200));
		g.drawString(dialog2, bX+2, bY*7+2);

		g.setFontSize(30);
		//YES	
		g.setColor(Color.argb(255, 255, 255, 255));
		g.drawString("YES", bX*3, bY*9);
		g.setColor(Color.argb(255, 200, 200, 200));
		g.drawString("YES", bX*3+3, bY*9+3);
		//NO
		g.setColor(Color.argb(255, 255, 255, 255));
		g.drawString("NO", bX*10, bY*9);
		g.setColor(Color.argb(255, 200, 200, 200));
		g.drawString("NO", bX*10+3, bY*9+3);
	}	
}
