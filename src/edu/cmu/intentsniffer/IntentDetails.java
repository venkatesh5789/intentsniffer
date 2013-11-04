package edu.cmu.intentsniffer;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.widget.TextView;

public class IntentDetails extends Activity {
	private TextView mTextView = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.intent_details);

		mTextView = (TextView) findViewById(R.id.intentContents);

		Intent i = getIntent();
		String description = (String) i.getSerializableExtra("intentDetails");

		mTextView.setText(description);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.intent_details, menu);
		return true;
	}

}
