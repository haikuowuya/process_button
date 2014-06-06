package com.dd.processbutton;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.drawable.GradientDrawable;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.AttributeSet;

import com.roboo.process.button.R;

public abstract class ProcessButton extends FlatButton
{
	private int mProgress;
	private static final int MIN_PROGRESS = 0;
	private static final int MAX_PROGRESS = 100;

	private GradientDrawable mProgressDrawable;
	private GradientDrawable mCompleteDrawable;
	private GradientDrawable mErrorDrawable;

	private CharSequence mLoadingText;
	private CharSequence mCompleteText;
	private CharSequence mErrorText;

	public ProcessButton(Context context, AttributeSet attrs, int defStyle)
	{
		super(context, attrs, defStyle);
		init(context, attrs);
	}

	public ProcessButton(Context context, AttributeSet attrs)
	{
		this(context, attrs, 0);
	}

	public ProcessButton(Context context)
	{
		this(context, null);
	}

	private void init(Context context, AttributeSet attrs)
	{
		mProgressDrawable = (GradientDrawable) new GradientDrawable().mutate();
		mProgressDrawable.setShape(GradientDrawable.RECTANGLE);
		mProgressDrawable.setColor(DEFAULT_PROGRESS_COLOR);
		mProgressDrawable.setCornerRadius(getCornerRadius());

		mCompleteDrawable = (GradientDrawable) new GradientDrawable().mutate();
		mCompleteDrawable.setShape(GradientDrawable.RECTANGLE);
		mCompleteDrawable.setColor(DEFAULT_COMPLETE_COLOR);
		mCompleteDrawable.setCornerRadius(getCornerRadius());

		mErrorDrawable = (GradientDrawable) new GradientDrawable().mutate();
		mErrorDrawable.setShape(GradientDrawable.RECTANGLE);
		mErrorDrawable.setColor(DEFAULT_ERROR_COLOR);
		mErrorDrawable.setCornerRadius(getCornerRadius());
		if (attrs != null)
		{
			initAttributes(context, attrs);
		}
	}

	private void initAttributes(Context context, AttributeSet attributeSet)
	{
		TypedArray attr = getTypedArray(context, attributeSet, R.styleable.ProcessButton);

		if (attr == null)
		{
			return;
		}
		try
		{
			mLoadingText = attr.getString(R.styleable.ProcessButton_textProgress);
			mCompleteText = attr.getString(R.styleable.ProcessButton_textComplete);
			mErrorText = attr.getString(R.styleable.ProcessButton_textError);
			int colorProgress = attr.getColor(R.styleable.ProcessButton_colorProgress, DEFAULT_PROGRESS_COLOR);
			mProgressDrawable.setColor(colorProgress);

			int colorComplete = attr.getColor(R.styleable.ProcessButton_colorComplete, DEFAULT_COMPLETE_COLOR);
			mCompleteDrawable.setColor(colorComplete);

			int colorError = attr.getColor(R.styleable.ProcessButton_colorError, DEFAULT_ERROR_COLOR);
			mErrorDrawable.setColor(colorError);
		}
		finally
		{
			attr.recycle();
		}
	}

	public void setProgress(int progress)
	{
		mProgress = progress;
		if (mProgress == MIN_PROGRESS)
		{
			onNormalState();
		}
		else if (mProgress == MAX_PROGRESS)
		{
			onCompleteState();
		}
		else if (mProgress < MIN_PROGRESS)
		{
			onErrorState();
		}
		else
		{
			onProgress();
		}
		invalidate();
	}

	protected void onErrorState()
	{
		if (getErrorText() != null)
		{
			setText(getErrorText());
		}
		setBackgroundCompat(getErrorDrawable());
	}

	protected void onProgress()
	{
		if (getLoadingText() != null)
		{
			setText(getLoadingText());
		}
		setBackgroundCompat(getProgressDrawable());
	}

	protected void onCompleteState()
	{
		if (getCompleteText() != null)
		{
			setText(getCompleteText());
		}
		setBackgroundCompat(getCompleteDrawable());
	}

	protected void onNormalState()
	{
		if (getNormalText() != null)
		{
			setText(getNormalText());
		}
		setBackgroundCompat(getNormalDrawable());
	}

	@Override
	protected void onDraw(Canvas canvas)
	{
		if (mProgress > MIN_PROGRESS && mProgress < MAX_PROGRESS)
		{
			drawProgress(canvas);
		}
		super.onDraw(canvas);
	}

	public abstract void drawProgress(Canvas canvas);

	public int getProgress()
	{
		return mProgress;
	}

	public int getMaxProgress()
	{
		return MAX_PROGRESS;
	}

	public int getMinProgress()
	{
		return MIN_PROGRESS;
	}

	public GradientDrawable getProgressDrawable()
	{
		return mProgressDrawable;
	}

	public GradientDrawable getCompleteDrawable()
	{
		return mCompleteDrawable;
	}

	public CharSequence getLoadingText()
	{
		return mLoadingText;
	}

	public CharSequence getCompleteText()
	{
		return mCompleteText;
	}

	public void setProgressDrawable(GradientDrawable progressDrawable)
	{
		mProgressDrawable = progressDrawable;
	}

	public void setCompleteDrawable(GradientDrawable completeDrawable)
	{
		mCompleteDrawable = completeDrawable;
	}

	public void setLoadingText(CharSequence loadingText)
	{
		mLoadingText = loadingText;
	}

	public void setCompleteText(CharSequence completeText)
	{
		mCompleteText = completeText;
	}

	public GradientDrawable getErrorDrawable()
	{
		return mErrorDrawable;
	}

	public void setErrorDrawable(GradientDrawable errorDrawable)
	{
		mErrorDrawable = errorDrawable;
	}

	public CharSequence getErrorText()
	{
		return mErrorText;
	}

	public void setErrorText(CharSequence errorText)
	{
		mErrorText = errorText;
	}

	@Override
	public Parcelable onSaveInstanceState()
	{
		Parcelable superState = super.onSaveInstanceState();
		SavedState savedState = new SavedState(superState);
		savedState.mProgress = mProgress;

		return savedState;
	}

	@Override
	public void onRestoreInstanceState(Parcelable state)
	{
		if (state instanceof SavedState)
		{
			SavedState savedState = (SavedState) state;
			mProgress = savedState.mProgress;
			super.onRestoreInstanceState(savedState.getSuperState());
			setProgress(mProgress);
		}
		else
		{
			super.onRestoreInstanceState(state);
		}
	}

	/**
	 * A {@link android.os.Parcelable} representing the {@link com.dd.processbutton.ProcessButton}'s
	 * state.
	 */
	public static class SavedState extends BaseSavedState
	{
		private int mProgress;

		public SavedState(Parcelable parcel)
		{
			super(parcel);
		}

		private SavedState(Parcel in)
		{
			super(in);
			mProgress = in.readInt();
		}

		@Override
		public void writeToParcel(Parcel out, int flags)
		{
			super.writeToParcel(out, flags);
			out.writeInt(mProgress);
		}

		public static final Creator<SavedState> CREATOR = new Creator<SavedState>()
		{

			@Override
			public SavedState createFromParcel(Parcel in)
			{
				return new SavedState(in);
			}

			@Override
			public SavedState[] newArray(int size)
			{
				return new SavedState[size];
			}
		};
	}
}
