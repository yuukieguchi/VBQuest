package net.eguchi.item;

import android.graphics.Rect;
import net.eguchi.vbquest.Img;
import net.eguchi.vbquest.MainView;
import net.eguchi.vbquest.Map;
import net.eguchi.vbquest.Sound;

public class Coin extends ItemBase{
		
	
	public Coin(MainView v ,int x, int y ,Map map) {
		// �e�N���X�̃R���X�g���N�^�����s����
		super( v, x, y, map );

		this.v= v;
		this.map = map;
		this.iX = x;
	    this.iY = y;
			    
	    // �摜�T�C�Y X 80%
		SIZE_X 		= (MainView.bX/10)*8;
		// �摜�T�C�Y Y 90%
		SIZE_Y 		= (MainView.bY/10)*8;

		
		CutX = 0; //�A�j���[�V�����p
	    		
		//[ 2 ] �摜�T�C�Y�擾
		iWidth = v.getImgWidth(Img.coin);
		iHeight =  v.getImgHeight(Img.coin);
	    
	    
}
	
	
	public void onBitmap(int offsetX, int offsetY){
		
		Rect src = new Rect();			
		Rect dst = new Rect
		((int) iX + offsetX ,
		 (int) iY + offsetY, 
		 (int) iX + offsetX + SIZE_X,
	     (int) iY + offsetY + SIZE_Y);
		
		
		src.set( iWidth/2 * CutX ,0, iWidth/2 *(CutX+1) , iHeight);
		
		//�A�j���[�V����
		if ( itemCnt % 30 == 0) { CutX++; }	
		if ( CutX == 2 ) { CutX = 0; }
		v.drawBitmap(Img.coin, src, dst);

		
	}
	
	//�v���C���[�ɐڐG�����ꍇ�̏���
	@Override
	public void ItemHit(){
		
		//���ʉ�
		v.getSound(Sound.coin);
		//���X�g���� ��菜��
		v.getItemList().remove(this);
		
		//���_�𑝂₷
		v.setMoney( v.getMoney()+1);
				
	}
	
	public void update(){
		super.update();
	}


}

/**
 * �I�[�o�[���C�h���Ȃ���΁A
 * �e�N���X�̓��������
 * */
 