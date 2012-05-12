package net.eguchi.item;

import net.eguchi.vbquest.Img;
import net.eguchi.vbquest.MainView;
import net.eguchi.vbquest.Map;
import net.eguchi.vbquest.Sound;
import android.graphics.Rect;

public class TimeInc extends ItemBase{
			
public TimeInc(MainView v ,int x, int y ,Map map) {
			// �e�N���X�̃R���X�g���N�^�����s����
			super( v, x, y, map );

			this.v= v;
			this.map = map;
			this.iX = x;
		    this.iY = y;
				    
		    // �摜�T�C�Y X 80%
			SIZE_X 		= MainView.bX;
			// �摜�T�C�Y Y 
			SIZE_Y 		= MainView.bY;
		    		
			//[ 2 ] �摜�T�C�Y�擾
			iWidth = v.getImgWidth(Img.clock);
			iHeight =  v.getImgHeight(Img.clock);
			
			this.invtime = 0;	//�A�C�R���\���p
				    
	}
		
		
		public void onBitmap(int offsetX, int offsetY){
			
			Rect src = new Rect( 0,0, iWidth , iHeight);			
			Rect dst = new Rect
			((int) iX + offsetX ,
			 (int) iY + offsetY, 
			 (int) iX + offsetX + SIZE_X,
		     (int) iY + offsetY + SIZE_Y);
					
			v.drawBitmap(Img.clock, src, dst);

			if(invtime >0){v.drawIcon(0, 2, (int)p.getX(),(int)p.getY());}
		}
		
		//�v���C���[�ɐڐG�����ꍇ�̏���
		@Override
		public void ItemHit(){

				//�܂��ŏ��Ƀ��X�g����A�C�e������菜���i���x�����������Ȃ����߂Ɂj
				v.getItemList().remove(this);

				//�������ԉ�			
				v.setLoop( v.getLoop()-300 );

				//���ʉ�
				v.getSound(Sound.recovery);
				
				//�������AloopCnt��100�ȉ��ɂ͂Ȃ�Ȃ��i�J�n���S�\�������Ȃ��j
				if( v.getLoop() < 100){
					v.setLoop(100);
				}

		}
		
		
		public void update(){
			super.update();
		}
		
}
