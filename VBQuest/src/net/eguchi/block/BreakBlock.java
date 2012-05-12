package net.eguchi.block;

import net.eguchi.vbquest.Img;
import net.eguchi.vbquest.MainView;
import net.eguchi.vbquest.Map;
import android.graphics.Rect;

public class BreakBlock extends BlockBase{
	
	public BreakBlock(MainView v ,double x, double y ,Map map) {
		// �e�N���X�̃R���X�g���N�^�����s����
		super( v, x, y, map );
		this.v= v;
		this.map = map;
		//�������l��ݒ�
		InitX = x;
		InitY = y;
		
		//����������
		Init();
	    	    
		SIZE_X 		= MainView.bX;// �摜�T�C�Y X
		SIZE_Y 		= MainView.bY;// �摜�T�C�Y Y
	    		 
	    //�摜�����p
		CutX 	= 0;
		CutY 	= 0;
	    		

		//[ 2 ] �摜�T�C�Y�擾
		iWidth = v.getImgWidth(Img.break_block);
		iHeight =  v.getImgHeight(Img.break_block);

		stepFlg = false;
		breaked = false; 

		breakCnt = 0;	//���ł܂ł̃J�E���g

	}
	
	//�������p���\�b�h
	protected void Init(){
		iX 		= InitX;
		iY 		= InitY;
	}

public void onBitmap(int offsetX, int offsetY){
		
		Rect src = new Rect();
			
		Rect dst = new Rect
		((int) iX + offsetX ,
		 (int) iY + offsetY, 
		 (int) iX + offsetX + SIZE_X,
	     (int) iY + offsetY + SIZE_Y);
			
		//30 [ 1 ]
		if(breakCnt < 30) CutY=0;
		//30~60 [ 2 ]
		if(breakCnt > 30 && breakCnt < 60 ) CutY =1;
		//60~90 [ 3 ]���ꂩ��	
		if(breakCnt >60 && breakCnt < 90) CutY =2;

		if(breakCnt < 90){
		}
		src.set( 0 ,iHeight/3 * CutY,iWidth , iHeight/3*(CutY+1) );		
		v.drawBitmap(Img.break_block, src, dst);
}
	

//�v���C���[�ɐڐG�����ꍇ�̏���
public void BlockHit(){
	
	//���X�g���� ��菜��
	v.getBlockList().remove(this);		
			
	}

	// ���񂾂Ƃ�
	public void BlockUnderHit(){
	      			
	}
	
	
	public void update(){
		
		//�ڐG�����ꍇ�B
		if(stepFlg == true){
			breakCnt++;			
		}

		if( breakCnt >= 90 ){
			//�u���b�N���܂����B
			breaked = true;
		}

        
	}//update�I��	
	
	
	//��ꂽ���ǂ���
	public boolean Breaked(){
		return breaked;
	}

}
