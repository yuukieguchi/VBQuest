package net.eguchi.vbquest;


import android.content.Context;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;

import java.io.IOException;
import java.util.HashMap;
import android.media.AudioManager;
import android.media.SoundPool;

public class Sound {
	private Context context; 
	private MediaPlayer musicPlayer;
	private MediaListener listener;
	private float vol = 0.2f;
	
	
	public static final int coin 		= 0,
							fanfare 	= 1,
							damege 		= 2,
							lvup 		= 3,
							recovery 	= 4,
							bossend 	= 5,
							jump 		= 6,
							forcejump	= 7,
							select		= 8,
							gameover	= 9,
							over		=10;
	
	
	public static final int[] seResTbl = {R.raw.coin,
											 R.raw.fanfare,
											 R.raw.damege,
											 R.raw.lvup,
											 R.raw.recovery,
											 R.raw.bossend,
											 R.raw.jump,
											 R.raw.forcejump,
											 R.raw.select,
											 R.raw.bgm_gameover,
											 R.raw.life0,
	};
	
	private static SoundPool soundPool;
	private static HashMap<Integer, Integer> soundPoolMap;
	public static final int SE_MAX = seResTbl.length;

	
	public static final int 	opening		= 0,
								laststage 	= 1,
								ending 		= 2;

	
	public Sound(Context context) {
		this.context = context;
		listener = new MediaListener();
	}

	public void bgmPlay(int no) {
		
		switch (no) {
		case opening:
			musicPlayer = MediaPlayer.create(context, R.raw.rpg_making);
			break;
		case laststage:
			musicPlayer = MediaPlayer.create(context, R.raw.laststage);
			break;
		case 2:
			musicPlayer = MediaPlayer.create(context, R.raw.ending);
			break;
		}
		
		musicPlayer.setOnCompletionListener(listener);
		try {
			musicPlayer.prepare();
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		musicPlayer.setVolume(vol, vol);
		musicPlayer.start();
	}

	public void bgmStop() {
		if (musicPlayer != null ) {
			musicPlayer.stop();
			musicPlayer.setOnCompletionListener(null);
			musicPlayer.release();
			musicPlayer = null;
		}
	}

	public void setVolume(int volume) {
		vol = volume / 50.0f;
		if (musicPlayer != null) {
			musicPlayer.setVolume(vol, vol);
		}
	}

	public void seLoad() {
		soundPool = new SoundPool(SE_MAX, AudioManager.STREAM_MUSIC, 100);
		soundPoolMap = new HashMap<Integer, Integer>();
		for (int i = 0; i < SE_MAX; i++) {
			soundPoolMap.put(i, soundPool.load(context, seResTbl[i], 1));
		}
		System.gc();
	}

	public void sePlay(int no) {
		soundPool.play(soundPoolMap.get(no), vol, vol, 1, 0, 1.0f);
	}

	private class MediaListener implements OnCompletionListener {
		public void onCompletion(MediaPlayer mp) {
			mp.seekTo(0);
			mp.start();
		}
	}
}
