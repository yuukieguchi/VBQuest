package net.eguchi.vbquest;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

public class VBQuest extends Activity {

	/*
	 * Create on 2010/10/22
	 */	

	/**
	 * 
	 * @author eguchi
	 * 
	 * */	

	@Override
	public void onCreate(Bundle icicle) {
		super.onCreate(icicle);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

		setContentView(new MainView(this));
	}


	@Override  
	protected void onSaveInstanceState(Bundle outState) {  
		super.onSaveInstanceState(outState);  
		//	  outState.putInt("STAGE", MainView.STAGE_NUM);  
		//	  outState.putInt("LV", MainView.LV);  

	}  


	@Override  
	protected void onRestoreInstanceState(Bundle savedInstanceState) {  
		super.onRestoreInstanceState(savedInstanceState);  

		//	  age = savedInstanceState.getInt("ageKey", -1);  

	} 

}

