package net.eguchi.enemy;

import android.graphics.Point;
import net.eguchi.player.Player;
import net.eguchi.vbquest.MainView;
import net.eguchi.vbquest.Map;
import net.eguchi.vbquest.SpriteObj;

public class EnemyBase extends SpriteObj{

	protected int enemyLife; //�G��HP
	protected Player p;//�v���C���[
		
    protected int ptn;//�G�̍s���p�^�[��
    protected int leftX;//���@�����蔻��C��
    protected int rightX;//�E�@�����蔻��C��
    protected int upY;//��@�����蔻��C��
	
    protected int animeCnt;//�J�E���^�[
	protected int enemyCnt;//�J�E���^�[
	
	public EnemyBase(MainView v, double x, double y, Map map) {
		super(v, x, y, map);
		v.getEnemyList().add(this);		
		p = v.getPlayer();		
		//�������l��ݒ�
		InitX = x;
		InitY = y;		
		//����������
		Init();					
	}
	
	//�������p���\�b�h
	protected void Init(){
		iX 		= InitX;
		iY 		= InitY;	
		CutX 	= 0;	
		CutY	= LEFT;
		onGround = false;//�ŏ��͋󒆂Ƃ݂Ȃ�
		ptn = 0;//�p�^�[��[�s������p]			    
	    enemyCnt = 0;	//����p�J�E���g
	    animeCnt = 0;	//�A�j���[�V�����J�E���g
	    invtime	 = 0;	//���G�J�E���g		
	}
		
	// ���܂ꂽ�Ƃ�[�e�X�ňႢ]
	public void EnemyJumpHit(){		
		
		//���ނƃv���C���[�͍ăW�����v
		p.setForceJump(true);
		//���
		p.Forcejump();
		
	}
	
	
	//�v���C���[�ɐڐG�����ꍇ�̏���
	public void EnemyHit(){}

	
	//����
	public void jump(){
		if (onGround) {
			vy = -JUMP_SPEED;// ������ɑ��x��������
			onGround = false;//��
		}	
	}
	

//�A�b�v�f�[�g�̏ڍׂ̓X�v���C�g�N���X�ɖ��L
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
		enemyCnt++;//�J�E���^�[[�G�̍s���p�^�[���쐬�p]	
		animeCnt++;//�A�j���[�V�����p�J�E���^�[

	      
        //�{�X���U�����󂯂��ꍇ�A��莞�Ԗ��G
		if( invtime > 0 ){ invtime++; }
		if( invtime >= 40 ){ invtime = 0; }
		
		//��ʒ�ɗ������Ƃ��A������
	     if( this.iY >= MainView.MAP_HEIGHT - SIZE_Y ){Init();}

	}
			
	//��{�I�Ƀ{�X�p[�p�^�[���̐ݒ�]
	public void Pattern(){	}
	

	//���G���
	public void Invisible(){
		
				
		
	}
	

}
