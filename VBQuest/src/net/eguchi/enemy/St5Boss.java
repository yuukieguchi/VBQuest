package net.eguchi.enemy;

import net.eguchi.vbquest.Img;
import net.eguchi.vbquest.MainView;
import net.eguchi.vbquest.Map;
import net.eguchi.vbquest.SpriteObj;
import android.graphics.Rect;

public class St5Boss extends EnemyBase{


	public St5Boss(MainView v, double x, double y, Map map) {
		super(v, x, y, map);
		this.map = map;
		this.iX = x;
		this.iY = y;
		Init();

		SIZE_X 		= MainView.bX*4; // �摜�T�C�Y X
		SIZE_Y 		= MainView.bY*5; // �摜�T�C�Y Y
		iWidth = v.getImgWidth(Img.enemy000);
		iHeight =  v.getImgHeight(Img.enemy000);
	
	}
	
	//�������p���\�b�h
	protected void Init(){
		iX 		  = InitX;
		iY 		  = InitY;	
		CutX 	  = 0;	
		CutY	  = LEFT;
		ptn 	  = 0;//�p�^�[��[�s������p]			    
	    enemyCnt  = 0;	//����p�J�E���g
	    animeCnt  = 0;	//�A�j���[�V�����J�E���g
	    invtime	  = 0;	//���G�J�E���g	
	    
	    enemyLife = 6;	// �GHP
	}
	


	public void onBitmap(int offsetX, int offsetY){

		Rect src = new Rect(iWidth/3 * CutX , 
				iHeight/4 * CutY , 
				iWidth/3*(CutX+1) , 
				iHeight /4 * (CutY+1) );

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
		if(invtime%3 == 0){	v.drawBitmap(Img.enemy000, src, dst);}
	
		
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
			super.EnemyJumpHit();

			enemyLife--;
			invtime = 1;
		}		


		//�G ���S��
		if( enemyLife <= 0 ){
						
			//���X�g����@���񂾓G���@��菜��[����]		
			v.getEnemyList().remove(this);		

			//�Ō�̃{�X�|�����̂ŁA�G���f�B���O��
			MainView.stageClearFlg = true;
		}

	}


	public void update(){
		super.update();
		
		JUMP_SPEED	= random.nextInt( 5 ) +1; //�W�����v��


        //�� �{�X�̍s���p�^�[�� 
        //0:�l�����@1:���s�� 
  
        if(ptn == 0){
        	//��������
        	SPEED = random.nextInt( 8 ) +1;
        	
        	//���s���ւ̈ڍs
            if( random.nextInt( 30 ) == 0 ){
            	enemyCnt = 0;
            	ptn = ( random.nextInt( 2 )+1 );// 1 2 3     	
            } 
        }

        else if(ptn == 1){
        	//
        	SPEED = 0;
        	
        	if( enemyCnt > 30){
        		vx = -vx;
        		enemyCnt = 0;
        		ptn = ( random.nextInt( 1 )+2 );// 2 3 
        	}
        }
        
        //
        else if(ptn == 2){
        	//�W�����v����
        	super.jump();
        	ptn = 0;
        }
        //�_����
        else if(ptn == 3){
        	//���鏈��
        	SPEED = 20;

        	//�����ւ̖߂�
        	if( enemyCnt > 40){
        		ptn = 0;
        	}
        }
        enemyCnt++;

	}

	
	@Override
	public boolean CheckInMap(){
		return true;

	}

	@Override
	public boolean BoxHit(SpriteObj obj){
		double sx = this.iX;
		double ex = this.iX+this.SIZE_X;
		//����������
		if(CutY == RIGHT){
			sx += 10;
		}else{
			ex -= 10;
		}
		return BoxHit(sx, this.iY, ex, this.iY+this.SIZE_Y,
				obj.getX(), obj.getY(), obj.getX()+obj.getSIZE_X(), obj.getY()+obj.getSIZE_Y());
	}



}