package com.example.touchbutton;

import android.animation.Animator;
import android.animation.Animator.AnimatorListener;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

/**
 * �ؼ�����Ŵ��ƶ��ָ�������Ŵ���ʧ ,������΢����ť�ؼ�
 * 
 * @author cy
 * 
 */
@SuppressLint("NewApi") 
public class ScaleButton extends Button {
	// ��һ�δ���λ��
	private int mLastX, mLastY;
	// �ص��ӿڼ���
	private OnClickListener mListener;
	
	// ����Ƿ��ƶ����ƶ���ָ�ԭʼ��С
	private boolean flag = false;

	public ScaleButton(Context context) {
		super(context, null);
	}

	public ScaleButton(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		int action = event.getAction();
		switch (action) {
		case MotionEvent.ACTION_DOWN:
			downAnimaotr(); 
			mLastX = (int) event.getX();
			mLastY = (int) event.getY();
			flag = false;
			break;
		case MotionEvent.ACTION_MOVE:
			int curX = (int) event.getX();
			int curY = (int) event.getY();
			if ((Math.abs(curY - mLastY) > 5 || Math.abs(curX - mLastX) > 5)
					&& !flag) {
				moveAnimator();
				flag = true;
			}
			mLastX = curX;
			mLastY = curY;
			break;
		case MotionEvent.ACTION_UP:
		case MotionEvent.ACTION_CANCEL:
			// ����Ѿ��ƶ��ָ�ԭʼ��С��ִ�ж��������ص������ӿ�
			if(!flag){
				cancelAnimator();
			}
			break;
		}
		return super.onTouchEvent(event);
	}
	
	/**
	 * ����ʱִ�еĶ���
	 */
	private void downAnimaotr(){
		/**
		 * �Ŵ�1.5��
		 */
		ObjectAnimator anim1 = ObjectAnimator.ofFloat(this, "ScaleX", 1.5f);
		ObjectAnimator anim2 = ObjectAnimator.ofFloat(this, "ScaleY", 1.5f);
		AnimatorSet animSet = new AnimatorSet();
		animSet.play(anim1).with(anim2);
		animSet.setDuration(300);
		animSet.start();
	}
	
	/**
	 * �ƶ���ִ�еĶ���
	 */
	private void moveAnimator(){
		/**
		 * �ָ���С����
		 */
		ObjectAnimator anim1 = ObjectAnimator.ofFloat(this, "ScaleX", 1.0f);
		ObjectAnimator anim2 = ObjectAnimator.ofFloat(this, "ScaleY", 1.0f);
		AnimatorSet animSet = new AnimatorSet();
		animSet.play(anim1).with(anim2);
		animSet.setDuration(300);
		animSet.start();
	}
	
	/**
	 * �뿪ִ�ж���
	 */
	private void cancelAnimator(){
		/**
		 * ͸���ȱ�Ϊ0 ���Ŵ�2��
		 */
		ObjectAnimator anim1 = ObjectAnimator.ofFloat(this, "Alpha", 1.0f, 0.0f);
		ObjectAnimator anim2 = ObjectAnimator.ofFloat(this, "ScaleX", 2.0f);
		ObjectAnimator anim3 = ObjectAnimator.ofFloat(this, "ScaleY", 2.0f);
		AnimatorSet animSet = new AnimatorSet();
		animSet.play(anim1).with(anim2);
		animSet.play(anim2).with(anim3);
		animSet.setDuration(300);
		animSet.start();
		// ������������
		animSet.addListener(new AnimatorListener() {
			@Override
			public void onAnimationStart(Animator animation) {
			}
			
			@Override
			public void onAnimationRepeat(Animator animation) {
			}
			
			@Override
			public void onAnimationEnd(Animator animation) {
				// �ص��¼�
				if(mListener != null){
					mListener.onClick(ScaleButton.this);
				}
			}
			
			@Override
			public void onAnimationCancel(Animator animation) {
			}
		});
	}

	/**
	 * ���ü����¼�
	 */
	public void setOnClickListener(OnClickListener listener) {
		this.mListener = listener;
	}

	interface OnClickListener {
		public void onClick(View view);
	}

}
