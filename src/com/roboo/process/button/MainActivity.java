package com.roboo.process.button;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MainActivity extends ListActivity
{

	public static void actionMain(Activity activity)
	{
		activity.startActivity(new Intent(activity, MainActivity.class));
	}
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		System.out.println(" MainActivity onCreate");
		String[] items = getResources().getStringArray(R.array.sample_list);

		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, items);
		setListAdapter(adapter);
	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id)
	{
		switch (position)
		{
		case 0:
			startSignInActivity(false);
			break;
		case 1:
			startSignInActivity(true);
			break;
		case 2:
			startMessageActivity();
			break;
		case 3:
			startUploadActivity();
			break;
		case 4:
			startStateSampleActivity();
			break;
		case 5:
			startTestActivity();
			break;
		}
	}

	private void startTestActivity()
	{
		TestActivity.actionTest(this);
	}

	private void startStateSampleActivity()
	{
		Intent intent = new Intent(this, StateSampleActivity.class);
		startActivity(intent);
	}

	private void startUploadActivity()
	{
		Intent intent = new Intent(this, UploadActivity.class);
		startActivity(intent);
	}

	private void startSignInActivity(boolean isEndlessMode)
	{
		Intent intent = new Intent(this, SignInActivity.class);
		intent.putExtra(SignInActivity.EXTRAS_ENDLESS_MODE, isEndlessMode);
		startActivity(intent);
	}

	private void startMessageActivity()
	{
		Intent intent = new Intent(this, MessageActivity.class);
		startActivity(intent);
	}

	@Override
	protected void onStart()
	{

		System.out.println(" MainActivity  onStart");
		super.onStart();
	}

	@Override
	protected void onRestart()
	{
		System.out.println(" MainActivity  onRestart");

		super.onRestart();
	}

	@Override
	protected void onResume()
	{
		System.out.println(" MainActivity  onResume");

		super.onResume();
	}

	@Override
	protected void onPause()
	{
		System.out.println(" MainActivity  onPause");

		super.onPause();
	}

	@Override
	protected void onStop()
	{

		System.out.println(" MainActivity  onStop");
		super.onStop();
	}

	@Override
	protected void onDestroy()
	{
		System.out.println(" MainActivity  onDestroy");

		super.onDestroy();
	}
}
