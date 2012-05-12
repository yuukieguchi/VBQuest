package net.eguchi.enemy;

import net.eguchi.vbquest.Img;
import net.eguchi.vbquest.MainView;
import net.eguchi.vbquest.Map;
import net.eguchi.vbquest.Sound;
import net.eguchi.vbquest.SpriteObj;
import android.graphics.Rect;

public class St2Boss extends EnemyBase{


	public St2Boss(MainView v, double x, double y, Map map) {
		super(v, x, y, map);
		this.map = map;
		this.iX = x;
		this.iY = y;
		Init();
		SIZE_X 		= MainView.bX*6; // �摜�T�C�Y X
		SIZE_Y 		= MainView.bY*4; // �摜�T�C�Y Y
		iWidth = v.getImgWidth(Img.boss_slime);
		iHeight =  v.getImgHeight(Img.boss_slime);
	
	}

	//�������p���\�b�h
	protected void Init(){
		iX 			= InitX;
		iY 			= InitY;	
		CutX	 	= 0;	
		CutY		= LEFT;
		
		leftX = 20;
		rightX =20;
		upY		 =10;
		
		ptn = 0;//�p�^�[��[�s������p]			    
	    enemyCnt = 0;	//����p�J�E���g
	    animeCnt = 0;	//�A�j���[�V�����J�E���g
	    invtime	 = 0;	//���G�J�E���g		
	
	    enemyLife = 7;// �G HP	
	}
	
	public void onBitmap(int offsetX, int offsetY){

		Rect src = new Rect( iWidth/3 * CutX , 0 , 
				 iWidth/3*(CutX+1) , iHeight );

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

		//�G�ƏՓ˂����Ƃ��A�v���C���[���_��
		if(invtime%2 == 0){	v.drawBitmap(Img.boss_slime, src, dst);}

		
	}
	//�v���C���[�ɐڐG�����ꍇ�̏���
	@Override
	public void EnemyHit(){

		//�G���U�����󂯂Ă��Ȃ���Ԃł���΁A�_���[�W���󂯂�
		if(this.invtime==0){
			v.setHP(v.getHP()-9);
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

			//�s���`�̎����
			for(int i =0; i< ( 7 - enemyLife) ; i++){
				new St2miniBoss( v ,this.iX , this.iY +MainView.bY ,map) ; 
			}
			
		//���ނقǏ������Ȃ��Ă���
			SIZE_X -= MainView.bX/2;
			SIZE_Y -= MainView.bY/3 ;
		

			invtime = 1;
		}		


		//�G ���S��
		if( enemyLife <= 0 ){
			//���X�g����@���񂾓G���@��菜��[����]		
			v.getEnemyList().remove(this);	
			
			//�o���l�l��
			v.setEXP(v.getEXP()+20);
			
			//�{�X�|�����̂ŁASTAGE �N���A�[
			MainView.stageClearFlg = true;
			
			//���ʉ�
			v.getSound(Sound.bossend);

		}

	}


	public void update(){
		super.update();
  
         //�����s���p�^�[������-------------------------- 
        //0:�ړ��� 1:��~�� 2:�W�����v 3:�_�b�V��
        switch(ptn){
        case 0:
        	//��������
        	SPEED = random.nextInt( 3 ) +3 ;

        	if( random.nextInt( 30 ) == 0 ){
        		enemyCnt = 0;
        		ptn = ( random.nextInt( 4 ) +1 );//�����_���� 1 2 3 4 �ֈڍs     	
        	} 
        	break;

        case 1:
        	SPEED = 20;//�_�b�V��
        	if( enemyCnt > 20){
        		enemyCnt = 0;
        		ptn = random.nextInt( 1 )+2;// 2 3
        	}     	            	
        	break;

        case 2:
        	vx = -vx;
        	ptn = 0;//�ʏ��Ԃɖ߂�
        	break; 	
 
        case 3:
        	SPEED = random.nextInt( 15 )+2;
        	if( enemyCnt > 30){
        		enemyCnt = 0;
        		ptn = 0;
        	}        	
        	break;
        	
        case 4:
        	SPEED = 0;//��~ >> 0�ֈڍs
        	JUMP_SPEED = 15;
        	if( enemyCnt > 50){
        		super.jump();
        		enemyCnt = 0;
        		ptn = 0;
        	}        	
        	break;
        }//--------------------------------------------------

        
  
	}
	
	
	@Override
	public boolean CheckInMap(){		
		return true;
	}

	//�{�X�p[ �����蔻��̏C�� ]
	@Override
	public boolean BoxHit(SpriteObj obj){

		double sx = this.iX;
		double ex = this.iX+this.SIZE_X;	
		
		double sy = this.iY;
		double ey = this.iY+this.SIZE_Y;

		//����
		if(CutY == RIGHT){
			sx += leftX;//��[�C��]
		}else{
			ex -= rightX;//�E[�C��]
		}
		sy += upY;//��[�C��]
				
		return BoxHit(sx, sy, ex, ey,
				obj.getX(), obj.getY(), obj.getX()+obj.getSIZE_X(), obj.getY()+obj.getSIZE_Y());
	
	}

}