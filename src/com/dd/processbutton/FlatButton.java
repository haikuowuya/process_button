package com.dd.processbutton;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.StateListDrawable;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.Button;

import com.roboo.process.button.R;

public class FlatButton extends Button
{
	private StateListDrawable mNormalDrawable;
	private StateListDrawable mProgressDrawable;
	private CharSequence mNormalText;
	private float mCornerRadius;
	private static final int DEFAULT_PRESSED_COLOR = 0xFFFF0000;
	private static final int DEFAULT_NORMAL_COLOR = 0xFF0000FF;
	protected static final int DEFAULT_PROGRESS_COLOR=0xFFAA6CC;
	protected static final int DEFAULT_COMPLETE_COLOR=0xFF99CC00;
	protected static final int DEFAULT_ERROR_COLOR=0xFFFF4444;

	public FlatButton(Context context, AttributeSet attrs, int defStyle)
	{
		super(context, attrs, defStyle);
 
		init(context, attrs);
	}

	public FlatButton(Context context, AttributeSet attrs)
	{
		this(context, attrs, 0);
	}

	public FlatButton(Context context)
	{
		this(context, null);
	}

	private void init(Context context, AttributeSet attrs)
	{
		mNormalDrawable = new StateListDrawable();
		mProgressDrawable = new StateListDrawable();
		if (attrs != null)
		{
			initAttributes(context, attrs);
		}
		mNormalText = getText().toString();
		setBackgroundCompat(mNormalDrawable);
	}

	private void initAttributes(Context context, AttributeSet attributeSet)
	{

		TypedArray attr = getTypedArray(context, attributeSet, R.styleable.FlatButton);
		if (attr == null)
		{
			return;
		}
		try
		{
			float defValue = getDimension(R.dimen.corner_radius);
			mCornerRadius = attr.getDimension(R.styleable.FlatButton_cornerRadius, defValue);
			mNormalDrawable.addState(new int[] { android.R.attr.state_pressed }, createPressedDrawable(attr));
			mNormalDrawable.addState(new int[] {}, createNormalDrawable(attr));
			mProgressDrawable.addState(new int[]{}, mProgressDrawable);
		}
		finally
		{
			attr.recycle();
		}
	}

	protected float getCornerRadius()
	{
		return mCornerRadius;
	}

	/***
	 * 按钮默认状态
	 * 
	 * @param attr
	 * @return
	 */
	private LayerDrawable createNormalDrawable(TypedArray attr)
	{
		GradientDrawable drawableTop = (GradientDrawable) new GradientDrawable().mutate();
		GradientDrawable drawableBottom = (GradientDrawable) new GradientDrawable().mutate();
		int colorPressed = attr.getColor(R.styleable.FlatButton_colorPressed, DEFAULT_PRESSED_COLOR);
		int colorNormal = attr.getColor(R.styleable.FlatButton_colorNormal, DEFAULT_NORMAL_COLOR);
 
		drawableTop.setShape(GradientDrawable.RECTANGLE);
		drawableTop.setCornerRadius(mCornerRadius);
		drawableTop.setColor(colorPressed);

		drawableBottom.setShape(GradientDrawable.RECTANGLE);
		drawableBottom.setCornerRadius(mCornerRadius);
		 
		drawableBottom.setColor(colorNormal);
		LayerDrawable drawableNormal = new LayerDrawable(new Drawable[]{drawableTop,drawableBottom});
		return drawableNormal;
	}

	/***
	 * 创建按钮按下时状态
	 * 
	 * @param attr
	 * @return
	 */
	private Drawable createPressedDrawable(TypedArray attr)
	{
		GradientDrawable drawablePressed = (GradientDrawable) new GradientDrawable().mutate();
		drawablePressed.setShape(GradientDrawable.RECTANGLE);
		drawablePressed.setCornerRadius(mCornerRadius);
		int colorPressed = attr.getColor(R.styleable.FlatButton_colorPressed, DEFAULT_PRESSED_COLOR);
		drawablePressed.setColor(colorPressed);
		return drawablePressed;
	}
	/***
	 * 创建按钮按下时状态
	 * 
	 * @param attr
	 * @return
	 */
	private Drawable createProgressDrawable(TypedArray attr)
	{
		GradientDrawable drawableProgress = (GradientDrawable) new GradientDrawable().mutate();
		drawableProgress.setShape(GradientDrawable.RECTANGLE);
		drawableProgress.setCornerRadius(mCornerRadius);
		int colorProgress = attr.getColor(R.styleable.FlatButton_colorPressed, DEFAULT_PROGRESS_COLOR);
		drawableProgress.setColor(colorProgress);
		return drawableProgress;
	}

	protected Drawable getDrawable(int id)
	{
		return getResources().getDrawable(id);
	}

	protected float getDimension(int id)
	{
		return getResources().getDimension(id);
	}

	protected int getColor(int id)
	{
		return getResources().getColor(id);
	}

	protected TypedArray getTypedArray(Context context, AttributeSet attributeSet, int[] attr)
	{
		return context.obtainStyledAttributes(attributeSet, attr, 0, 0);
	}

	public StateListDrawable getNormalDrawable()
	{
		return mNormalDrawable;
	}
	public StateListDrawable getPorgressDrawable()
	{
		return mProgressDrawable;
	}
	
	public CharSequence getNormalText()
	{
		return mNormalText;
	}

	/**
	 * Set the View's background. Masks the API changes made in Jelly Bean.
	 * 
	 * @param drawable
	 */
	@SuppressWarnings("deprecation")
	@SuppressLint("NewApi")
	public void setBackgroundCompat(Drawable drawable)
	{
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN)
		{
			setBackground(drawable);
		}
		else
		{
			setBackgroundDrawable(drawable);
		}
	}
}
