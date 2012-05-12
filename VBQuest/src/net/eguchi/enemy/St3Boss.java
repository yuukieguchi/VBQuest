package net.eguchi.enemy;

import net.eguchi.vbquest.Img;
import net.eguchi.vbquest.MainView;
import net.eguchi.vbquest.Map;
import net.eguchi.vbquest.Sound;
import net.eguchi.vbquest.SpriteObj;
import android.graphics.Point;
import android.graphics.Rect;

public class St3Boss extends EnemyBase{


	public St3Boss(MainView v, double x, double y, Map map) {
		super(v, x, y, map);
		this.map = map;
		this.iX = x;
		this.iY = y;
		Init();
		
		SIZE_X 		= MainView.bX*4; // �摜�T�C�Y X
		SIZE_Y 		= MainView.bY*4; // �摜�T�C�Y Y
		iWidth = v.getImgWidth(Img.st3boss);
		iHeight =  v.getImgHeight(Img.st3boss);
	
				
	}

	//�������p���\�b�h
	protected void Init(){
		iX 		= InitX;
		iY 		= InitY;	
		CutX 	= 0;	
		CutY	= LEFT;

		leftX 	= 15;
		rightX 	= 15;
		upY 	= 5;//�������C��

		ptn 	 = 0;//�p�^�[��[�s������p]			    
	    enemyCnt = 0;	//����p�J�E���g
	    animeCnt = 0;	//�A�j���[�V�����J�E���g
	    invtime	 = 0;	//���G�J�E���g	
	    enemyLife = 5;// �G HP	
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
		if(invtime %2 == 0){v.drawBitmap(Img.st3boss, src, dst);}

		
	}

	//�v���C���[�ɐڐG�����ꍇ�̏���
	@Override
	public void EnemyHit(){

		//�G���U�����󂯂Ă��Ȃ���Ԃł���΁A�_���[�W���󂯂�
		if(this.invtime==0){
			v.setHP(v.getHP()-10);
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

			//������Ɨ�����
			this.iY +=30;

			new St3miniBoss( v ,this.iX , this.iY ,map) ;

			invtime = 1;
		}		


		//��ʒ�ɗ��Ƃ����Ƃ�(���A���C�t���Ȃ��Ȃ�����)�A����
		if( enemyLife <= 0  || this.iY  >= MainView.MAP_HEIGHT - this.SIZE_Y - MainView.bY *2  ){

			//���X�g����@���񂾓G���@��菜��[����]		
			v.getEnemyList().remove(this);		

			//�o���l�l��
			v.setEXP(v.getEXP()+18);

			//�{�X�|�����̂ŁASTAGE �N���A�[
			MainView.stageClearFlg = true;

			//���ʉ�
			v.getSound(Sound.bossend);

		}

	}

	//��ɕ����Ă���̂ŁA�d�͂��|���Ȃ��悤�ɂ���
	public void update(){

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
		enemyCnt++;//�J�E���^�[[�G�̍s���p�^�[���쐬�p]	
		animeCnt++;//�A�j���[�V�����p�J�E���^�[

	      
        //�{�X���U�����󂯂��ꍇ�A��莞�Ԗ��G
		if( invtime > 0 ){ invtime++; }
		if( invtime >= 40 ){ invtime = 0; }
		
	
		//�����s���p�^�[������-------------------------- 
        //0:�ړ��� 1:��~�� 2:�W�����v 3:�_�b�V��
        switch(ptn){
        case 0:
        	//�ړ�����
        	SPEED = random.nextInt( 15 ) +3 ;

        	if( random.nextInt( 30 ) == 0 ){
        		enemyCnt = 0;
        		ptn = ( random.nextInt( 3 ) +1 );//1 2 3 4 ��     	
        	} 
        	break;

        case 1:
        	SPEED = random.nextInt( 3 ) +1;//�̂�т�

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
        	
        	SPEED = 0;
        	if( enemyCnt > 30){      		
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