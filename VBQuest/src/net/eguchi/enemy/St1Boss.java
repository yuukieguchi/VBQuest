package net.eguchi.enemy;

import net.eguchi.vbquest.Img;
import net.eguchi.vbquest.MainView;
import net.eguchi.vbquest.Map;
import net.eguchi.vbquest.Sound;
import net.eguchi.vbquest.SpriteObj;
import android.graphics.Rect;

public class St1Boss extends EnemyBase{

	//�L�c�l
	public St1Boss(MainView v, double x, double y, Map map) {
		super(v, x, y, map);
		this.map = map;
		this.iX = x;
		this.iY = y;
		Init();

		SIZE_X 		= MainView.bX*4; // �摜�T�C�Y X
		SIZE_Y 		= MainView.bY*5; // �摜�T�C�Y Y
		iWidth  = v.getImgWidth(Img.St1boss);
		iHeight = v.getImgHeight(Img.St1boss);
	}
	
	//�������p���\�b�h
	protected void Init(){
		iX 		= InitX;
		iY 		= InitY;	
		CutX 	= 0;	
		CutY	= LEFT;
		SPEED 		=  8; //�ړ����x	
		ptn = 0;//�p�^�[��[�s������p]			    
	    enemyCnt = 0;	//����p�J�E���g
	    animeCnt = 0;	//�A�j���[�V�����J�E���g
	    invtime	 = 0;	//���G�J�E���g	
	    
	    enemyLife	 	= 3;// �G HP
	}


	public void onBitmap(int offsetX, int offsetY){

		Rect src = new Rect(iWidth/3 * CutX , 
				iHeight/2 * CutY , 
				iWidth/3*(CutX+1) , 
				iHeight /2 * (CutY+1) );

		Rect dst = new Rect
		((int) iX + offsetX ,
				(int) iY + offsetY, 
				(int) iX + offsetX + SIZE_X,
				(int) iY + offsetY + SIZE_Y);
	
		if( vx > 0 ){ CutY = RIGHT;}
		if( vx < 0 ){ CutY = LEFT; }

		//�A�j���[�V����
		if ( animeCnt % 20 == 0) {CutX++;}	
		if (CutX == 3) {CutX = 0;}

		//�Փ˂����Ƃ��A�_��
		if(invtime%2 == 0){	v.drawBitmap(Img.St1boss, src, dst);}
	
		
	}
	//�v���C���[�ɐڐG�����ꍇ�̏���
	@Override
	public void EnemyHit(){

		//�G���U�����󂯂Ă��Ȃ���Ԃł���΁A�_���[�W���󂯂�
		if(this.invtime==0){
			v.setHP(v.getHP()-8);
			//�G���U�����󂯂Ă����Ԃł̓v���C���[�͓_�ł��Ȃ�	
		}else{
			p.setInvTime(0);
		}
		
	}

	// �{�X�A���܂ꂽ�Ƃ�
	public void EnemyJumpHit(){

		//�G�A���G���łȂ���΃��C�t -1
		if(invtime==0){

			super.EnemyJumpHit();

			enemyLife--;
			invtime = 1;
		}		


		//�G ���S��
		if( enemyLife <= 0 ){
						
			//���X�g����@���񂾓G���@��菜��[����]		
			v.getEnemyList().remove(this);		

			//���ʉ�
			v.getSound(Sound.bossend);
			
			//�o���l�l��
			v.setEXP(v.getEXP()+16);
						
			//�{�X�|�����̂ŁASTAGE 1 �N���A�[
			MainView.stageClearFlg = true;
		}

	}


	public void update(){
		super.update();
		
		JUMP_SPEED	=  ( random.nextInt( 20 ) +1 );; //�W�����v��

        //�����s���p�^�[������-------------------------- 
        //0:�ړ��� 1:��~�� 2:�W�����v 3:�_�b�V��
        switch(ptn){
        case 0:
        	//��������
        	SPEED = 5;
        	//���s���ւ̈ڍs
        	if( random.nextInt( 20 ) == 0 ){
        		enemyCnt = 0;
        		ptn = ( random.nextInt( 2 )+1 );// 1 2 3 �ǂꂩ     	
        	} 
        	break;
        case 1:

        	SPEED = 15;//�_�b�V��
        	if( enemyCnt > 15){
        		enemyCnt = 0;
        		ptn = ( random.nextInt( 1 ) + 2 );// 2 �� 3
        	}     	            	
        	break;

        case 2:
        	//�W�����v����
        	super.jump();
        	ptn = 0;//�ʏ��Ԃɖ߂�
        	break; 	
        
        case 3:
        	//��~
        	SPEED = 0;
        	//�����ւ̖߂�
        	if( enemyCnt > 10){
        		ptn = 0;
        	}        	
        	break;
        }//--------------------------------------------------
                
		
	}

	
	@Override
	public boolean CheckInMap(){
		return true;

	}

	@Override
	public boolean BoxHit(SpriteObj obj){
		double sx = this.iX;
		double ex = this.iX+this.SIZE_X;
		//�����蔻��C��
		if(CutY == RIGHT){
			sx += 5;
		}else{
			ex -= 5;
		}
		return BoxHit(sx, this.iY, ex, this.iY+this.SIZE_Y,
				obj.getX(), obj.getY(), obj.getX()+obj.getSIZE_X(), obj.getY()+obj.getSIZE_Y());
	}



}