package net.eguchi.item;

import net.eguchi.player.Player;
import net.eguchi.vbquest.MainView;
import net.eguchi.vbquest.Map;
import net.eguchi.vbquest.SpriteObj;

public class ItemBase extends SpriteObj{

	protected int itemCnt;//�J�E���^�[
	protected Player p;//�v���C���[
	
	public ItemBase(MainView v, double x, double y, Map map) {
		super(v, x, y, map);
		
		p = v.getPlayer();
		itemCnt = 0;

	}
	
	//�v���C���[�ɐڐG�����ꍇ�̏���
	public void ItemHit(){	}


	public void update(){		
		//�J�E���^�[
		itemCnt++;
	}
}
