package net.eguchi.block;

import net.eguchi.vbquest.MainView;
import net.eguchi.vbquest.Map;
import net.eguchi.vbquest.SpriteObj;

public class BlockBase extends SpriteObj{


	protected boolean hitFlg;// �u���b�N�ڐG�������ǂ���
	protected boolean stepFlg;// �u���b�N���񂾂��ǂ���

	protected boolean breaked;// �u���b�N��ꂽ���ǂ���
	protected int breakCnt;	
	
	public BlockBase(MainView v ,double x, double y ,Map map) {
		// �e�N���X�̃R���X�g���N�^�����s����
		super( v, x, y, map );
		this.v= v;
		this.map = map;
		InitX = x;
		InitY = y;
		
		//����������
		Init();
	}

	//�������p���\�b�h
	protected void Init(){
		iX 		= InitX;
		iY 		= InitY;
	}

	public void onBitmap(int offsetX, int offsetY){}
	
	//�v���C���[�ɐڐG�����ꍇ�̏���
	public void BoxHit(){}

	// ���܂ꂽ�Ƃ�
	public void EnemyJumpHit(){}
	
	//update
	public void update(){}
	



}
