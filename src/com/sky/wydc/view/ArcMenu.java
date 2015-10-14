package com.sky.wydc.view;

import com.sky.wydc.R;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;

public class ArcMenu extends ViewGroup implements OnClickListener {

	private static final int POS_LEFT_TOP = 0;
	private static final int POS_LEFT_BUTTOM = 1;
	private static final int POS_RIGHT_TOP = 2;
	private static final int POS_RIGHT_BUTTOM = 3;

	private static final int STATUS_CLOSE = 0;
	private static final int STATUS_OPEN = 1;

	private int mPosition = POS_RIGHT_BUTTOM;
	private int mRadius = 0;
	private int mStatus = STATUS_CLOSE;
	private View mCButton;
	private OnMenuItemClickListener mMenuItemClickListener;

	public void setOnMenuItemClickListener(OnMenuItemClickListener l) {
		this.mMenuItemClickListener = l;
	}

	public ArcMenu(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		mRadius = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
				100, getResources().getDisplayMetrics());

		TypedArray typedArray = context.getTheme().obtainStyledAttributes(
				attrs, R.styleable.ArcMenu, defStyle, 0);
		mPosition = typedArray.getInt(R.styleable.ArcMenu_position,
				POS_RIGHT_BUTTOM);
		mRadius = (int) typedArray.getDimension(R.styleable.ArcMenu_radius,
				mRadius);
		typedArray.recycle();
	}

	public ArcMenu(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public ArcMenu(Context context) {
		this(context, null, 0);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		int count = getChildCount();
		for (int i = 0; i < count; i++) {
			measureChild(getChildAt(i), widthMeasureSpec, heightMeasureSpec);
		}
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
	}

	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		if (changed) {
			layoutButton();
		}

	}

	private void layoutButton() {
		int count = getChildCount();
		mCButton = getChildAt(count - 1);
		int width = mCButton.getMeasuredWidth();
		int height = mCButton.getMeasuredHeight();
		int l = width;
		int t = height;
		switch (mPosition) {
		case POS_LEFT_TOP:

			break;

		case POS_LEFT_BUTTOM:
			// t = getMeasuredHeight() - height;
			t/=2;
			t = getMeasuredHeight() - height - height / 2;
			break;

		case POS_RIGHT_TOP:
			l = getMeasuredWidth() - width;
			break;

		case POS_RIGHT_BUTTOM:
			l = getMeasuredWidth() - width;
			t = getMeasuredHeight() - height;
			break;

		}

		mCButton.layout(l, t, l + width, t + height);
		mCButton.setClickable(true);
		mCButton.setFocusable(true);
		mCButton.setOnClickListener(this);

		for (int i = 0; i < count - 1; i++) {
			View childView = getChildAt(i);
			childView.layout(l, t, l + width, t + height);
			childView.setVisibility(View.GONE);
			final int pos = i;
			childView.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					clickItemAnim(pos);
					changeStatus();
					if (mMenuItemClickListener != null) {
						mMenuItemClickListener.onItemClick(v, pos);
					}
				}
			});
		}
	}

	public interface OnMenuItemClickListener {
		void onItemClick(View view, int position);
	}

	@Override
	public void onClick(View v) {
		rotateCButton();
		toggleMenu();
	}

	private void toggleMenu() {
		int itemCount = getChildCount() - 1;
		for (int i = 0; i < itemCount; i++) {
			final View child = getChildAt(i);
			child.setVisibility(View.VISIBLE);
			child.setClickable(true);
			child.setFocusable(true);
			int x = (int) (mRadius * Math
					.sin(Math.PI / 2 / (itemCount - 1) * i));
			int y = (int) (mRadius * Math
					.cos(Math.PI / 2 / (itemCount - 1) * i));

			int xflag = 1;
			int yflag = 1;
			if (mPosition == POS_RIGHT_TOP || mPosition == POS_RIGHT_BUTTOM) {
				xflag = -1;
			}
			if (mPosition == POS_LEFT_BUTTOM || mPosition == POS_RIGHT_BUTTOM) {
				yflag = -1;
			}
			ObjectAnimator transXAnimator = null;
			ObjectAnimator transYAnimator = null;
			ObjectAnimator rotationAnimator = null;
			AnimatorSet animatorSet = new AnimatorSet();
			if (mStatus == STATUS_CLOSE) {
				transXAnimator = ObjectAnimator.ofFloat(child, "translationX",
						0, x * xflag);
				transYAnimator = ObjectAnimator.ofFloat(child, "translationY",
						0, y * yflag);

			} else {
				transXAnimator = ObjectAnimator.ofFloat(child, "translationX",
						x * xflag, 0);
				transYAnimator = ObjectAnimator.ofFloat(child, "translationY",
						y * yflag, 0);
			}
			rotationAnimator = ObjectAnimator.ofFloat(child, "rotation", 0,
					720f);
			animatorSet.playTogether(transXAnimator, transYAnimator,
					rotationAnimator);
			animatorSet.setDuration(300);

			animatorSet.setStartDelay(25 * i);
			animatorSet.addListener(new AnimatorListenerAdapter() {
				@Override
				public void onAnimationEnd(Animator animation) {
					if (mStatus == STATUS_CLOSE) {
						child.setVisibility(View.GONE);
						child.setClickable(false);
						child.setFocusable(false);
					}
					super.onAnimationEnd(animation);
				}
			});
			animatorSet.start();

		}
		changeStatus();

	}

	private void clickItemAnim(int pos) {
		int itemCount = getChildCount() - 1;
		for (int i = 0; i < itemCount; i++) {

			final View childView = getChildAt(i);
			AnimatorSet animatorSet = null;
			if (i != pos) {
				animatorSet = scaleSmallAnim(childView);
			} else {
				animatorSet = scaleBigAnim(childView);
			}
			animatorSet.addListener(new AnimatorListenerAdapter() {
				@Override
				public void onAnimationEnd(Animator animation) {
					childView.setScaleX(1.0f);
					childView.setScaleY(1.0f);
					childView.setAlpha(1.0f);
					childView.setX(0f);
					childView.setTranslationX(0f);
					childView.setTranslationY(0f);
					if (mStatus == STATUS_CLOSE) {
						childView.setVisibility(View.GONE);
					}
					super.onAnimationEnd(animation);
				}
			});
			animatorSet.start();
			childView.setClickable(false);
			childView.setFocusable(false);

		}

	}

	private AnimatorSet scaleBigAnim(final View v) {
		ObjectAnimator scaleXAnimator = ObjectAnimator.ofFloat(v, "scaleX",
				2.0f, 1.0f);
		ObjectAnimator scaleYAnimator = ObjectAnimator.ofFloat(v, "scaleY",
				2.0f, 1.0f);
		ObjectAnimator alphaAnimator = ObjectAnimator.ofFloat(v, "alpha", 1.0f,
				0f);
		AnimatorSet animatorSet = new AnimatorSet();
		animatorSet.playTogether(scaleXAnimator, scaleYAnimator, alphaAnimator);
		animatorSet.setDuration(500);

		return animatorSet;
	}

	private AnimatorSet scaleSmallAnim(View v) {
		ObjectAnimator scaleXAnimator = ObjectAnimator.ofFloat(v, "scaleX",
				1.0f, 0f);
		ObjectAnimator scaleYAnimator = ObjectAnimator.ofFloat(v, "scaleY",
				1.0f, 0f);
		ObjectAnimator alphaAnimator = ObjectAnimator.ofFloat(v, "alpha", 1.0f,
				0f);
		AnimatorSet animatorSet = new AnimatorSet();
		animatorSet.playTogether(scaleXAnimator, scaleYAnimator, alphaAnimator);
		animatorSet.setDuration(500);
		return animatorSet;
	}

	private void changeStatus() {
		mStatus = (mStatus == STATUS_CLOSE ? STATUS_OPEN : STATUS_CLOSE);
	}

	private void rotateCButton() {
		ObjectAnimator anim = ObjectAnimator.ofFloat(mCButton, "rotation", 0,
				360f);
		anim.setDuration(500);
		anim.start();
	}

}
