package net.eguchi.vbquest;

import android.view.KeyEvent;

public class KeyCode{
		
	public static final int KEY_NONE = 0x00000000; 
	public static final int KEY_UP = 0x00000001; 
	public static final int KEY_DOWN = 0x00000002;
	public static final int KEY_LEFT = 0x00000004; 
	public static final int KEY_RIGHT = 0x00000008;
	public static final int KEY_0 = 0x00000010; 
	public static final int KEY_1 = 0x00000020; 
	public static final int KEY_2 = 0x00000040;
	public static final int KEY_3 = 0x00000080; 
	public static final int KEY_4 = 0x00000100; 
	public static final int KEY_5 = 0x00000200; 
	public static final int KEY_6 = 0x00000400;
	public static final int KEY_7 = 0x00000800; 
	public static final int KEY_8 = 0x00001000;
	public static final int KEY_9 = 0x00002000;
	public static final int KEY_SPACE = 0x00004000; 
	public static final int KEY_ENTER = 0x00008000; 
	
	public static int myKey;

	public KeyCode(MainView v) {
		myKey = KEY_NONE;

	}

	public boolean onKeyDown(int keyCode, KeyEvent msg ) {
	
		
		switch (keyCode) {
		case KeyEvent.KEYCODE_DPAD_UP:
			myKey |= KEY_UP;
			return true;
		case KeyEvent.KEYCODE_DPAD_DOWN:
			myKey |= KEY_DOWN;
			return true;
		case KeyEvent.KEYCODE_DPAD_LEFT:
			myKey |= KEY_LEFT;
			return true;
		case KeyEvent.KEYCODE_DPAD_RIGHT:
			myKey |= KEY_RIGHT;
			return true;
		case KeyEvent.KEYCODE_0:
			myKey |= KEY_0;
			return true;
		case KeyEvent.KEYCODE_1:
			myKey |= KEY_1;
			return true;
		case KeyEvent.KEYCODE_2:
			myKey |= KEY_2;
			return true;
		case KeyEvent.KEYCODE_3:
			myKey |= KEY_3;
			return true;
		case KeyEvent.KEYCODE_4:
			myKey |= KEY_4;
			return true;
		case KeyEvent.KEYCODE_5:
			myKey |= KEY_5;
			return true;
		case KeyEvent.KEYCODE_6:
			myKey |= KEY_6;
			return true;
		case KeyEvent.KEYCODE_7:
			myKey |= KEY_7;
			return true;
		case KeyEvent.KEYCODE_8:
			myKey |= KEY_8;
			return true;
		case KeyEvent.KEYCODE_9:
			myKey |= KEY_9;
			return true;
		case KeyEvent.KEYCODE_SPACE:
			myKey |= KEY_SPACE;
			return true;
		case KeyEvent.KEYCODE_ENTER:
			myKey |= KEY_ENTER;
			return true;
		}
		return false;
	}


	public boolean onKeyUp(int keyCode, KeyEvent msg) {
		switch (keyCode) {
		case KeyEvent.KEYCODE_DPAD_UP:
			myKey &= ~KEY_UP;
			return true;
		case KeyEvent.KEYCODE_DPAD_DOWN:
			myKey &= ~KEY_DOWN;
			return true;
		case KeyEvent.KEYCODE_DPAD_LEFT:
			myKey &= ~KEY_LEFT;
			return true;
		case KeyEvent.KEYCODE_DPAD_RIGHT:
			myKey &= ~KEY_RIGHT;
			return true;
		case KeyEvent.KEYCODE_0:
			myKey &= ~KEY_0;
			return true;
		case KeyEvent.KEYCODE_1:
			myKey &= ~KEY_1;
			return true;
		case KeyEvent.KEYCODE_2:
			myKey &= ~KEY_2;
			return true;
		case KeyEvent.KEYCODE_3:
			myKey &= ~KEY_3;
			return true;
		case KeyEvent.KEYCODE_4:
			myKey &= ~KEY_4;
			return true;
		case KeyEvent.KEYCODE_5:
			myKey &= ~KEY_5;
			return true;
		case KeyEvent.KEYCODE_6:
			myKey &= ~KEY_6;
			return true;
		case KeyEvent.KEYCODE_7:
			myKey &= ~KEY_7;
			return true;
		case KeyEvent.KEYCODE_8:
			myKey &= ~KEY_8;
			return true;
		case KeyEvent.KEYCODE_9:
			myKey &= ~KEY_9;
			return true;
		case KeyEvent.KEYCODE_SPACE:
			myKey &= ~KEY_SPACE;
			return true;
		case KeyEvent.KEYCODE_ENTER:
			myKey &= ~KEY_ENTER;
			return true;
		}
		return false;
	}

	public boolean checkKey(int keyCode) {
		if ((myKey & keyCode) != 0) {
			return true;
		}
		return false;
	}
}
