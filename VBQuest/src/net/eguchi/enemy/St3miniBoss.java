package net.eguchi.enemy;

import net.eguchi.vbquest.Img;
import net.eguchi.vbquest.MainView;
import net.eguchi.vbquest.Map;
import android.graphics.Point;
import android.graphics.Rect;

public class St3miniBoss extends EnemyBase{


	public St3miniBoss(MainView v, double x, double y, Map map) {
		super(v, x, y, map);
		this.map = map;
		this.iX = x;
		this.iY = y;
		Init();
		
		SIZE_X 		= MainView.bX; // �摜�T�C�Y X
		SIZE_Y 		= MainView.bY; // �摜�T�C�Y Y
		iWidth = v.getImgWidth(Img.st3boss);
		iHeight =  v.getImgHeight(Img.st3boss);
	
				
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
		if ( animeCnt % 3 == 0) {CutX++;}	
		if (CutX == 3) {CutX = 0;}

		
		//�G�ƏՓ˂����Ƃ��A�v���C���[���_��
		if(invtime %2 == 0){	v.drawBitmap(Img.st3boss, src, dst);}

		
	}
	//�v���C���[�ɐڐG�����ꍇ�̏���
	@Override
	public void EnemyHit(){

		//�G���U�����󂯂Ă��Ȃ���Ԃł���΁A�_���[�W���󂯂�
		if(this.invtime==0){
			v.setHP(v.getHP()-5);
			//�G���U�����󂯂Ă����Ԃł̓v���C���[�͓_�ł��Ȃ�	
		}else{
			p.setInvTime(0);
		}
		
	}

	// �{�X�A���܂ꂽ�Ƃ�
	public void EnemyJumpHit(){

		super.EnemyJumpHit();
			//���X�g����@���񂾓G���@��菜��[����]		
			v.getEnemyList().remove(this);								

			//�o���l �l��
			v.setEXP(v.getEXP()+4);

	}

	
	public void update(){
		//�����͏�ɕ����Ă���̂ŁA�d�͂��|���Ȃ��悤�ɂ���
		//�ړ�����
		if(CutY == LEFT){
			vx = -SPEED;//���Ɉړ�
		}else{
			vx = SPEED;//�E�Ɉړ�
		}   

		//X���W�̍X�V����----------------------------------------
		double newX = iX + vx;
		Point tile = map.getTileCollision(this, newX, iY ,true);
		if (tile == null) {
			iX = newX;
		} else {
			if (vx > 0) {
				iX = Map.tilesToPixelsX(tile.x) - SIZE_X;
			} else if (vx < 0) {
				iX = Map.tilesToPixelsX(tile.x + 1);
			}
			vx = -vx;//�Փˎ��A�����I�ɔ��][�}�G�ɋ���]
		}

		//Y���W�̍X�V����----------------------------------------
		double newY = iY + vy;
		tile = map.getTileCollision(this, iX, newY , true );
		if (tile == null) {
			iY = newY;        
			onGround = false;
		} else {
			if (vy > 0) {
				iY = Map.tilesToPixelsY(tile.y) - SIZE_Y;
				vy = 0;
				onGround = true;
			} else if (vy < 0) {
				iY = Map.tilesToPixelsY(tile.y + 1);
				vy = 0;
			}

		}

		switch(ptn){

		case 0:		

			SPEED = random.nextInt( 5 ) +1;//�̂�̂�

			if( random.nextInt( 30 ) == 0 ){
				enemyCnt = 0;
				ptn = 1;// 1 �ڍs     	
			} 
			break;

		case 1:

			SPEED =  random.nextInt( 12 ) + 5;//�_�b�V��			

			if(enemyCnt > 50){
				ptn = 0;
			}
			break;
		}
	
	
		enemyCnt++;//�J�E���^�[[�G�̍s���p�^�[���쐬�p]	
		animeCnt++;//�A�j���[�V�����p�J�E���^�[

		//��ʒ�ɗ������Ƃ��A������
	     if( this.iY >= MainView.MAP_HEIGHT - SIZE_Y ){Init();}		
	
	}
	
	@Override
	public boolean CheckInMap(){		
		return true;
	}

}