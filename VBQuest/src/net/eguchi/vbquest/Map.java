package net.eguchi.vbquest;


import java.io.ByteArrayOutputStream;
import java.io.InputStream;

import net.eguchi.block.BreakBlock;
import net.eguchi.enemy.Bat;
import net.eguchi.enemy.Cowman;
import net.eguchi.enemy.Devil;
import net.eguchi.enemy.Fire;
import net.eguchi.enemy.Goblin;
import net.eguchi.enemy.Snake;
import net.eguchi.enemy.St2miniBoss;
import net.eguchi.enemy.St3miniBoss;
import net.eguchi.enemy.St4Boss;
import net.eguchi.enemy.St2Boss;
import net.eguchi.enemy.St3Boss;
import net.eguchi.enemy.St1Boss;
import net.eguchi.enemy.St5Boss;
import net.eguchi.item.Coin;
import net.eguchi.item.RecoveryItem;
import net.eguchi.item.TimeInc;
import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Point;
import android.graphics.Rect;

public class Map {
		
    public static final double GRAVITY = 1.4;
	public static int mapArrayX,mapArrayY;
	
	private String[] str;
	
    private MainView view;

    private char map[][];
	
public Map( MainView v ) {

		view = v;		
		if( view.getSTAGE()==MainView.STAGE1 ){Read( "map01.txt" );}
		if( view.getSTAGE()==MainView.STAGE2 ){Read( "map02.txt" );}
		if( view.getSTAGE()==MainView.STAGE3 ){Read( "map03.txt" );}
		if( view.getSTAGE()==MainView.STAGE4 ){Read( "map04.txt" );}
		if( view.getSTAGE()==MainView.STAGE5 ){Read( "map05.txt" );}
		
		mapArrayY = Integer.parseInt(str[0]);
		mapArrayX = Integer.parseInt(str[1]);
	
		map = new char [mapArrayY][mapArrayX];
	
		for (int i = 0 ; i < mapArrayY; i++) {
			for (int s = 0 ; s <  mapArrayX ; s++) {
				map[i][s] =  str[ i + 2 ].charAt(s);	
			}
		}
}


public void drawBlock(int offsetX, int offsetY) {
	
        int firstTileX = pixelsToTilesX(-offsetX);
        int lastTileX = firstTileX + pixelsToTilesX(MainView.screenX) + 1;
        lastTileX = Math.min(lastTileX, mapArrayX);

        int firstTileY = pixelsToTilesY(-offsetY);
        int lastTileY = firstTileY + pixelsToTilesY(MainView.screenY) +1;
        lastTileY = Math.min(lastTileY, mapArrayY);
		  
		for (int i = firstTileY; i < lastTileY; i++) {
			for (int j = firstTileX; j < lastTileX; j++) {
            	Rect src = new Rect();
             	Rect dst = new Rect();
             	
				dst.set(tilesToPixelsX(j) + offsetX ,
						tilesToPixelsY(i) + offsetY,
						tilesToPixelsX(j) + offsetX + MainView.bX,
						tilesToPixelsY(i) + offsetY + MainView.bY ); 
             	              	
             	switch (map[i][j]) {

             	case 'B' : 
             		src.set( view.getImgWidth(Img.block)/2 *0 , view.getImgHeight(Img.block)/3 *0 , view.getImgWidth(Img.block)/2 *1 , view.getImgHeight(Img.block)/3 *1 );             		
             		view.drawBitmap(Img.block, src, dst);
           		break;

             	case 'L' : 
             		src.set( view.getImgWidth(Img.block)/2 *1 , view.getImgHeight(Img.block)/3 *0 , view.getImgWidth(Img.block)/2 *2 , view.getImgHeight(Img.block)/3 *1 );             		
             		view.drawBitmap(Img.block, src, dst);
             	break;
             	
             	case 'F' :
             		src.set( view.getImgWidth(Img.block)/2 *0 , view.getImgHeight(Img.block)/3 *1 , view.getImgWidth(Img.block) /2 *1, view.getImgHeight(Img.block)/3 *2 );             		
             		view.drawBitmap(Img.block, src, dst);
             	break;
             	
              	case 'H' :
             		src.set( view.getImgWidth(Img.block)/2 *1 , view.getImgHeight(Img.block)/3 *2 , view.getImgWidth(Img.block)/2 *2 , view.getImgHeight(Img.block)/3 *3 );             		
             		view.drawBitmap(Img.block, src, dst);
             	break;
             	
            	case 'N' :
             		src.set( 0, 0, view.getImgWidth(Img.maruta) , view.getImgHeight(Img.maruta) );             		
             		view.drawBitmap(Img.maruta, src, dst);
             	break;
             	
             	case 'M' :
             		src.set( view.getImgWidth(Img.block)/2 *1 , view.getImgHeight(Img.block)/3 *1 , view.getImgWidth(Img.block)/2 *2 , view.getImgHeight(Img.block)/3 *2 );             		
             		view.drawBitmap(Img.block, src, dst);
             	break;
                   	
             	}
             }
           }
        			
}


public Point getTileCollision(SpriteObj spriteObj, double newX, double newY) {
	return getTileCollision(spriteObj, newX, newY, false );
}

public Point getTileCollision(SpriteObj spriteObj, double newX, double newY, boolean blockHitFlg ) {
    newX = Math.ceil(newX);
    newY = Math.ceil(newY);
    
    double fromX = Math.min(spriteObj.getX(), newX);
    double fromY = Math.min(spriteObj.getY(), newY);
    double toX = Math.max(spriteObj.getX(), newX);
    double toY = Math.max(spriteObj.getY(), newY);
    
    int fromTileX = pixelsToTilesX(fromX);
    int fromTileY = pixelsToTilesY(fromY);
    int toTileX = pixelsToTilesX(toX + spriteObj.SIZE_X - 1);
    int toTileY = pixelsToTilesY(toY + spriteObj.SIZE_Y - 1);

    for (int x = fromTileX; x <= toTileX; x++) {
        for (int y = fromTileY; y <= toTileY; y++) {
            if (x < 0 || x >= mapArrayX) {
                return new Point(x, y);
            }
            if (y < 0 || y >= mapArrayY) {
                return new Point(x, y);
            }
            if (map[y][x] == 'B' 
            	|| map[y][x] == 'L' 
            			|| map[y][x] == 'F' 
            				|| map[y][x] == 'H'
            					|| map[y][x] == 'N') {
                return new Point(x, y);
            }                       
           
            if (map[y][x] == 'W') {
            	view.getBlockList().add(new BreakBlock( view , tilesToPixelsX(x), tilesToPixelsY(y) ,this ) );
            	return new Point(x, y);
            }                       
            
            if ((map[y][x] == 'M') && !blockHitFlg) {
                return new Point(x, y);
            }                       
          
        }
    }
    return null;
}



public void initBlock(MainView v){

	for (int i = 0; i < mapArrayY; i++) {
		for (int j = 0; j < mapArrayX; j++) {

			switch (map[i][j]) {

			case 'K':
			v.getBlockList().add(new BreakBlock( v , tilesToPixelsX(j), tilesToPixelsY(i) ,this ) );      	             	           		
			break;         	

			}
		}
	}
}

public void initItem(MainView v){
	
	for (int i = 0; i < mapArrayY; i++) {
		for (int j = 0; j < mapArrayX; j++) {
			  	              	
         	switch (map[i][j]) {
        	
         	case 'C':
         		v.getItemList().add(new Coin( v , tilesToPixelsX(j), tilesToPixelsY(i) ,this ) );
         		break;         	
        	case 'A':
         		v.getItemList().add(new RecoveryItem( v , tilesToPixelsX(j), tilesToPixelsY(i) ,this ) );
         		break;   	
        	case 'J':
        		v.getItemList().add(new TimeInc( v , tilesToPixelsX(j), tilesToPixelsY(i) ,this ) );
        		break;   	
         	}         	

		}
	}
}

public void initEnemy(MainView v){
	
	for (int i = 0; i < mapArrayY; i++) {
		for (int j = 0; j < mapArrayX; j++) {
			switch (map[i][j]) {
								
			case 'S':
				new Snake( v , tilesToPixelsX(j), tilesToPixelsY(i) ,this ) ;      	  
				break;

			case 'G':
				new Goblin( v , tilesToPixelsX(j), tilesToPixelsY(i) ,this ) ;     	             	           		
				break;

			case 'X':
				new St2miniBoss( v , tilesToPixelsX(j), tilesToPixelsY(i) ,this ) ;     	             	           			
				break;

			case 'T':
				new St3miniBoss( v , tilesToPixelsX(j), tilesToPixelsY(i) ,this ) ;     	             	           			
				break;

			case 'D':
				new Devil( v , tilesToPixelsX(j), tilesToPixelsY(i) ,this ) ;     	             	           		
				break;

			case 'E':
				new Cowman( v , tilesToPixelsX(j), tilesToPixelsY(i) ,this ) ;     	             	           		
				break;

			case 'Z':
				new Fire( v , tilesToPixelsX(j), tilesToPixelsY(i) ,this ) ;     	             	           		
				break;

			case 'R':
				new Bat( v , tilesToPixelsX(j), tilesToPixelsY(i) ,this ) ;     	             	           		
				break;

			case '1':
				new St1Boss( v , tilesToPixelsX(j), tilesToPixelsY(i) ,this ) ;  
				break;

			case '2':
				new St2Boss( v , tilesToPixelsX(j), tilesToPixelsY(i) ,this ) ;  
				break;

			case '3':
				new St3Boss( v , tilesToPixelsX(j), tilesToPixelsY(i) ,this ) ;  
				break;

			case '4':
				new St4Boss( v , tilesToPixelsX(j), tilesToPixelsY(i) ,this ) ;  
				break;

			case '5':
				new St5Boss( v , tilesToPixelsX(j), tilesToPixelsY(i) ,this ) ;  
				break;

			}

		}
	}
}

public static int pixelsToTilesX(double pixels) {
	return (int)Math.floor(pixels / MainView.bX);
}

public static int pixelsToTilesY(double pixels) {
    return (int)Math.floor(pixels / MainView.bY);
}

public static int tilesToPixelsX(int tiles) {
    return tiles * MainView.bX;
}

public static int tilesToPixelsY(int tiles) {
    return tiles * MainView.bY;
}

public static int MapArrayX(){
	return mapArrayX;
}

public static int MapArrayY(){
	return mapArrayY;	
}

	public void Read(String fileName) {
		String text = null;

		try {
			text = file2str(view.getContext(), fileName);
		} catch (Exception e) {
			e.printStackTrace();
		}

		str = text.split("\r\n");
	}

	private String file2str(Context context, String fileName) throws Exception {
		byte[] w = file2data(context, fileName);

		return new String(w);
	}

	private byte[] file2data(Context context, String fileName) throws Exception {
		int size;
		byte[] w = new byte[1024];
		InputStream in = null;
		ByteArrayOutputStream out = null;
		try {
			AssetManager as = view.getResources().getAssets();
			in = as.open(fileName); 

			out = new ByteArrayOutputStream();
			while (true) {
				size = in.read(w);
				if (size <= 0)
					break;
				out.write(w, 0, size);
			}

			out.close();
			in.close();

			return out.toByteArray();
		} catch (Exception e) {
			try {
				if (in != null)
					in.close();
				if (out != null)
					out.close();
			} catch (Exception e2) {
			}
			throw e;
		}
	}
}
