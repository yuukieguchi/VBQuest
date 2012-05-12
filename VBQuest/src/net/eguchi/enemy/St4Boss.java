package net.eguchi.enemy;

import net.eguchi.vbquest.Img;
import net.eguchi.vbquest.MainView;
import net.eguchi.vbquest.Map;
import net.eguchi.vbquest.Sound;
import net.eguchi.vbquest.SpriteObj;
import android.graphics.Rect;

public class St4Boss extends EnemyBase{


	public St4Boss(MainView v, double x, double y, Map map) {
		super(v, x, y, map);
		this.map = map;
		this.iX = x;
		this.iY = y;
		Init();

		SIZE_X 		= MainView.bX*4; // �摜�T�C�Y X
		SIZE_Y 		= MainView.bY*5; // �摜�T�C�Y Y
		iWidth = v.getImgWidth(Img.st4boss);
		iHeight =  v.getImgHeight(Img.st4boss);
	
	}
	
	//�������p���\�b�h
	protected void Init(){
		iX 		= InitX;
		iY 		= InitY;	
		CutX 	= 0;	
		CutY	= LEFT;
		
		//�����蔻��C��
		leftX 	= 10;
		rightX 	= 10;
		upY 	= 1;//�������H
		
		ptn = 0;//�p�^�[��[�s������p]			    
	    enemyCnt = 0;	//����p�J�E���g
	    animeCnt = 0;	//�A�j���[�V�����J�E���g
	    invtime	 = 0;	//���G�J�E���g	
	    
	    enemyLife	 	= 5;	// �GHP
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

		//�G�ƏՓ˂����Ƃ��A�v���C���[���_��
		if(invtime%3 == 0){	v.drawBitmap(Img.st4boss, src, dst);}
	
		
	}
	//�v���C���[�ɐڐG�����ꍇ�̏���
	@Override
	public void EnemyHit(){

		//�G���U�����󂯂Ă��Ȃ���Ԃł���΁A�_���[�W���󂯂�
		if(this.invtime==0){
			v.setHP(v.getHP()-18);
			//�G���U�����󂯂Ă����Ԃł̓v���C���[�͓_�ł��Ȃ�	
		}else{
			p.setInvTime(0);
		}
		
	}

	// �{�X�A���܂ꂽ�Ƃ�
	public void EnemyJumpHit(){

		//�G�A���G���łȂ���΃��C�t -1
		if(invtime==0){
			super.EnemyJumpHit();;

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
			v.setEXP(v.getEXP()+18);
			
			//�{�X�|�����̂ŁASTAGE 1 �N���A�[
			MainView.stageClearFlg = true;
		}

	}


	public void update(){
		super.update();

		JUMP_SPEED	= random.nextInt( 10 ) +10; //�W�����v��

		//�� �{�X�̍s���p�^�[�� 
  
        if(ptn == 0){
        	//��������
        	SPEED = random.nextInt( 10 )+1;        	
        	//���s���ւ̈ڍs
            if( random.nextInt( 40 ) == 0 ){
            	enemyCnt = 0;
            	ptn = ( random.nextInt( 3 )+1 );//1 2      	
            } 
        }

        //
        else if(ptn == 1){
        	
        	SPEED = 0;//��~
        	if( enemyCnt > 30){
        		//�n�C�W�����v
        		JUMP_SPEED	= 30;
        		super.jump();
        		enemyCnt = 0;
        		ptn = 0;//2 3   
        	}
        }
        //
        
        else if(ptn == 2){
        	//�����_���W�����v��
    		super.jump();
    		ptn = 0;
        }
        
        //�_����
        else if(ptn == 3){
        	//���鏈��
        	SPEED = 25;

        	//�����ւ̖߂�
        	if( enemyCnt > 10){
        		vx = -vx;
        		ptn = 0;
        	}
        }
        
        enemyCnt++;
        
		//�{�X���U�����󂯂��ꍇ�A��莞�Ԗ��G
		if( invtime > 0 ){ invtime++; }
		//���̃t���[���Ԗ��G
		if( invtime >= 40 ){invtime = 0;}

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