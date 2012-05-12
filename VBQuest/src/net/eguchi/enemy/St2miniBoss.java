package net.eguchi.enemy;

import net.eguchi.vbquest.Img;
import net.eguchi.vbquest.MainView;
import net.eguchi.vbquest.Map;
import android.graphics.Point;
import android.graphics.Rect;

public class St2miniBoss extends EnemyBase{

		
	public St2miniBoss(MainView v ,double x, double y ,Map map) {
		// �e�N���X�̃R���X�g���N�^�����s����
		super( v, x, y, map );
		this.v= v;
		this.map = map;
		InitX = x;
		InitY = y;
		Init();

		SIZE_X 		= MainView.bX;// �摜�T�C�Y X
		SIZE_Y 		= ( MainView.bY/2 );// �摜�T�C�Y Y
		iWidth = v.getImgWidth(Img.slime);
		iHeight =  v.getImgHeight(Img.slime);		
}
	
	//�������p���\�b�h
	protected void Init(){	
		iX 		= InitX;
		iY 		= InitY;	
		CutX 	= 0;	
		CutY	= LEFT;
	
		enemyCnt = 0;	//����p�J�E���g
		animeCnt = 0;	//�A�j���[�V�����J�E���g

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

		v.drawBitmap(Img.slime, src, dst);


	}
	

//�v���C���[�ɐڐG�����ꍇ�̏���
	@Override
	public void EnemyHit(){
				
		//�_���[�W
		v.setHP(v.getHP()-3);

	}

	// ���܂ꂽ�Ƃ�
	public void EnemyJumpHit(){

		super.EnemyJumpHit();
		
	      	//�o���l �l��
	      	v.setEXP(v.getEXP()+2);

	      	//�@���X�g����G���@��菜���@[����]		
			v.getEnemyList().remove(this);		
				
	}
	
	
	public void update(){
		vy += Map.GRAVITY;

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

			JUMP_SPEED	= random.nextInt( 15 ) +5; //�W�����v��
			SPEED = random.nextInt( 5 ) +1;//�̂�̂�

			if( random.nextInt( 30 ) == 0 ){
				enemyCnt = 0;
				ptn = ( random.nextInt( 1 )+1);//�����_���� 1 2 �ڍs     	
			} 
			break;

		case 1:
			
			SPEED =  random.nextInt( 12 ) +3;//�_�b�V��			

			if(enemyCnt > 30){
				vx = -vx;//���]2				
				ptn = 2;
			}
			break;

		case 2:        	
			SPEED = 0;//��~
			if(enemyCnt > 20 ){
				super.jump();   
				ptn = 0;
			}
			
			break;

		}

		enemyCnt++;//�J�E���^�[[�G�̍s���p�^�[���쐬�p]	
		animeCnt++;//�A�j���[�V�����p�J�E���^�[

	}//update�I��	


}

/**
 * �I�[�o�[���C�h���Ȃ���΁A
 * �e�N���X�̓��������
 * */
 