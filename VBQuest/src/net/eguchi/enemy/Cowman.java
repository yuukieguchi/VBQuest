package net.eguchi.enemy;

import net.eguchi.vbquest.Img;
import net.eguchi.vbquest.MainView;
import net.eguchi.vbquest.Map;
import android.graphics.Point;
import android.graphics.Rect;

public class Cowman extends EnemyBase{
			
	public Cowman(MainView v ,double x, double y ,Map map) {
		// �e�N���X�̃R���X�g���N�^�����s����
		super( v, x, y, map );
		this.v= v;
		this.map = map;
		this.iX = x;
		this.iY = y;

		//����������
		Init();	

		SIZE_X 		= MainView.bX;// �摜�T�C�Y X
		SIZE_Y 		= MainView.bY;// �摜�T�C�Y Y
		//[ 2 ] �摜�T�C�Y�擾
		iWidth 	= v.getImgWidth(Img.cowman);
		iHeight = v.getImgHeight(Img.cowman);

	}
	
	//�������p���\�b�h
	protected void Init(){	
		iX 		= InitX;
		iY 		= InitY;	
		CutX 	= 0;	
		CutY	= LEFT;

		SPEED 		=  5; //�ړ����x
		JUMP_SPEED	= 10; //�W�����v��
	
		ptn = 0;//�p�^�[��[�s������p]			    
	    enemyCnt   = 0;	//����p�J�E���g
	    animeCnt   = 0;	//�A�j���[�V�����J�E���g
	    invtime	   = 0;	//���G�J�E���g			
		enemyLife  = 2;// �G2�񓥂܂Ȃ��Ⴞ��
	}
	

	public void onBitmap(int offsetX, int offsetY){

		Rect src = new Rect( iWidth/3 * CutX , 
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

		if (animeCnt% 20 == 0) {CutX++;}	
		if (CutX == 3) {CutX = 0;}

		//�A�j���[�V����
		if(invtime%2 == 0){v.drawBitmap(Img.cowman, src, dst);}

	}


	//�v���C���[�ɐڐG�����ꍇ�̏���
	@Override
	public void EnemyHit(){

		//�G���U�����󂯂Ă��Ȃ���Ԃł���΁A�_���[�W���󂯂�
		if(this.invtime==0){
			v.setHP( v.getHP()-7 );
			//�G���U�����󂯂Ă����Ԃł̓v���C���[�͓_�ł��Ȃ�	
		}else{
			p.setInvTime(0);
		}

	}


	// ���܂ꂽ�Ƃ�
	public void EnemyJumpHit(){

		//�G�A���G���łȂ���΃��C�t -1
		if(invtime==0){
			//���ނƃv���C���[�͍ăW�����v
			p.setForceJump(true);
			//���
			p.Forcejump();
			enemyLife--;
			invtime = 1;
			
			this.SPEED = 14;//�G�A��������
		}		


		//�G ���S��
		if( enemyLife <= 0 ){
						
			//���X�g����@���񂾓G���@��菜��[����]		
			v.getEnemyList().remove(this);		

			//�o���l�l��
			v.setEXP( v.getEXP() +6 );
		}
			
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
			/**���������ɂȂ������A�����I�ɔ��]*/
			vx = -vx;
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

	      //�U�����󂯂��ꍇ�A��莞�Ԗ��G
		if( invtime > 0 ){ invtime++; }
		if( invtime >= 40 ){invtime = 0;}
		
		
		
		
		enemyCnt++;//�J�E���^�[[�G�̍s���p�^�[���쐬�p]	
		animeCnt++;//�A�j���[�V�����p�J�E���^�[

		//��ʒ�ɗ������Ƃ��A������
	     if( this.iY >= MainView.MAP_HEIGHT - SIZE_Y ){Init();}

	     
	     
	     
	}//update�I��	


}
