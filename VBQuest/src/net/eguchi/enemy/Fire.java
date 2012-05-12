package net.eguchi.enemy;

import net.eguchi.vbquest.Img;
import net.eguchi.vbquest.MainView;
import net.eguchi.vbquest.Map;
import net.eguchi.vbquest.SpriteObj;
import android.graphics.Rect;

public class Fire extends EnemyBase{

		
	public Fire(MainView v ,double x, double y ,Map map) {
		// �e�N���X�̃R���X�g���N�^�����s����
		super( v, x, y, map );
		this.v= v;
		this.map = map;

		//����������
		Init();	

		//[ 2 ] �摜�T�C�Y�擾
		iWidth = v.getImgWidth(Img.fire);
		iHeight =  v.getImgHeight(Img.fire);
		
}
	
	//�������p���\�b�h
	protected void Init(){	

		iX 		= InitX;
		iY 		= InitY;

		SIZE_X 		= MainView.bX*2-10;// �摜�T�C�Y X
		SIZE_Y 		= ( MainView.bY*2 );// �摜�T�C�Y Y	    		

		//������藎���Ă���
		vy = random.nextInt( 7 ) +5;

		CutX 	= 0;	
		CutY	= LEFT;

		//�����蔻��C��
		leftX 	= 20;
		rightX 	= 20;
		upY 	= 20;
		
		ptn = 0;//�p�^�[��[�s������p]			    
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

		//�A�j���[�V����
		if ( animeCnt % 20 == 0) {CutX++;}	
		if (CutX == 3) {CutX = 0;}

		v.drawBitmap(Img.fire, src, dst);

	}
	

	//�v���C���[�ɐڐG�����ꍇ�̏���
	@Override
	public void EnemyHit(){
				
		//�_���[�W
		v.setHP(v.getHP()-8);

	}


	//�����͖��G
	public void EnemyJumpHit(){}
	
	
	public void update(){

		

		//Y���W�̍X�V����----------------------------------------
		double newY = iY + vy;
		tile = map.getTileCollision(this, iX, newY , true );
		if (tile == null) {
			iY = newY;        
		} else {
			//�^�C���ɏՓ˂����Ƃ��B	
			SIZE_Y   -= MainView.bY/2;
			this.iY  += MainView.bY/2;
			if(SIZE_Y<0){//4�t���[���ŏ���
				Init();
			}
		}

		//��ʒ�ɗ������Ƃ��A������
		if( this.iY >= MainView.MAP_HEIGHT ){Init();}
        
	     enemyCnt++;//�J�E���^�[[�G�̍s���p�^�[���쐬�p]	
	     animeCnt++;//�A�j���[�V�����p�J�E���^�[

	}//update�I��	

	
	public boolean BoxHit(SpriteObj obj){

		double sx = this.iX;
		double ex = this.iX+this.SIZE_X;	
		
		double sy = this.iY;
		double ey = this.iY+this.SIZE_Y;
	
			sx += leftX;//��[�C��]
			ex -= rightX;//�E[�C��]
			
			sy += upY;
				
		return BoxHit(sx, sy, ex, ey,
				obj.getX(), obj.getY(), obj.getX()+obj.getSIZE_X(), obj.getY()+obj.getSIZE_Y());
	
	}

	
}

 