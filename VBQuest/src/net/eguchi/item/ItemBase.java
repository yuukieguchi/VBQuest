package net.eguchi.item;

import net.eguchi.player.Player;
import net.eguchi.vbquest.MainView;
import net.eguchi.vbquest.Map;
import net.eguchi.vbquest.SpriteObj;

public class ItemBase extends SpriteObj{

	protected int itemCnt;//カウンター
	protected Player p;//プレイヤー
	
	public ItemBase(MainView v, double x, double y, Map map) {
		super(v, x, y, map);
		
		p = v.getPlayer();
		itemCnt = 0;

	}
	
	//プレイヤーに接触した場合の処理
	public void ItemHit(){	}


	public void update(){		
		//カウンター
		itemCnt++;
	}
}
