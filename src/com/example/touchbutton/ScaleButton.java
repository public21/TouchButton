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
 * 控件点击放大，移动恢复，点击放大消失 ,仿新浪微博按钮控件
 * 
 * @author cy
 * 
 */
@SuppressLint("NewApi") 
public class ScaleButton extends Button {
	// 上一次触摸位置
	private int mLastX, mLastY;
	// 回调接口监听
	private OnClickListener mListener;
	
	// 标记是否移动，移动后恢复原始大小
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
			// 如果已经移动恢复原始大小则不执行动画，不回调监听接口
			if(!flag){
				cancelAnimator();
			}
			break;
		}
		return super.onTouchEvent(event);
	}
	
	/**
	 * 按下时执行的动画
	 */
	private void downAnimaotr(){
		/**
		 * 放大1.5倍
		 */
		ObjectAnimator anim1 = ObjectAnimator.ofFloat(this, "ScaleX", 1.5f);
		ObjectAnimator anim2 = ObjectAnimator.ofFloat(this, "ScaleY", 1.5f);
		AnimatorSet animSet = new AnimatorSet();
		animSet.play(anim1).with(anim2);
		animSet.setDuration(300);
		animSet.start();
	}
	
	/**
	 * 移动是执行的动画
	 */
	private void moveAnimator(){
		/**
		 * 恢复大小动画
		 */
		ObjectAnimator anim1 = ObjectAnimator.ofFloat(this, "ScaleX", 1.0f);
		ObjectAnimator anim2 = ObjectAnimator.ofFloat(this, "ScaleY", 1.0f);
		AnimatorSet animSet = new AnimatorSet();
		animSet.play(anim1).with(anim2);
		animSet.setDuration(300);
		animSet.start();
	}
	
	/**
	 * 离开执行动画
	 */
	private void cancelAnimator(){
		/**
		 * 透明度变为0 ，放大2倍
		 */
		ObjectAnimator anim1 = ObjectAnimator.ofFloat(this, "Alpha", 1.0f, 0.0f);
		ObjectAnimator anim2 = ObjectAnimator.ofFloat(this, "ScaleX", 2.0f);
		ObjectAnimator anim3 = ObjectAnimator.ofFloat(this, "ScaleY", 2.0f);
		AnimatorSet animSet = new AnimatorSet();
		animSet.play(anim1).with(anim2);
		animSet.play(anim2).with(anim3);
		animSet.setDuration(300);
		animSet.start();
		// 动画结束监听
		animSet.addListener(new AnimatorListener() {
			@Override
			public void onAnimationStart(Animator animation) {
			}
			
			@Override
			public void onAnimationRepeat(Animator animation) {
			}
			
			@Override
			public void onAnimationEnd(Animator animation) {
				// 回调事件
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
	 * 设置监听事件
	 */
	public void setOnClickListener(OnClickListener listener) {
		this.mListener = listener;
	}

	interface OnClickListener {
		public void onClick(View view);
	}

}
