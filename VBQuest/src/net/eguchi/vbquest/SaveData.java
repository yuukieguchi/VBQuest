package net.eguchi.vbquest;

import java.io.InputStream;
import java.io.OutputStream;

import net.eguchi.player.Player;
import android.content.Context;

public class SaveData {

	public static void intRead( MainView view, String fileName , boolean flg ) throws Exception {
		Player p= view.getPlayer();

		InputStream in = null; 
		try {
			in = view.getContext().openFileInput(fileName);
			view.setSAVE(in.read());
			if( flg ){				
				return;				
			}
			view.setSTAGE(in.read());
			view.setLEVEL(in.read());
			view.setLIFE(in.read());
			view.setHP(in.read());
			view.setEXP(in.read());
			view.setMoney(in.read());
			view.setTIME(in.read());
			p.setX( in.read() + (in.read() << 8) );
			p.setY(in.read() + (in.read() << 8) );
			view.setLoop(in.read() + (in.read() << 8) );		
			in.close();
			return;
		} catch (Exception e) {
			try {
				if (in != null)
					in.close();
			} catch (Exception e2) {
			}
			throw e;
		}
	}

	public static void intWrite(MainView view, String fileName , boolean flg) throws Exception {
		Player p= view.getPlayer();		
		OutputStream out = null;
		try {
			out = view.getContext().openFileOutput(fileName, Context.MODE_PRIVATE);
			out.write( view.getSAVE() );		

			if( flg ){
				return;
			}
			//			out.write(view.getSTAGE() >> 8);
			//			out.write(view.getSTAGE() >> 16);
			//			out.write(view.getSTAGE() >> 24);
			out.write( view.getSTAGE() );		
			out.write( view.getLEVEL() );
			out.write( view.getLIFE() );
			out.write( view.getHP() );			
			out.write( view.getEXP() );
			out.write( view.getMoney() );
			out.write( view.getTIME() );	
			out.write( p.getX() );
			out.write( p.getX() >>  8 );
			out.write( p.getY() );
			out.write( p.getY() >>  8 );
			out.write( view.getLoop() );
			out.write( view.getLoop() >>  8 );
			out.close();
		} catch (Exception e) {
			try {
				if (out != null)
					out.close();
			} catch (Exception e2) {
			}
			throw e;
		}
	}
}
