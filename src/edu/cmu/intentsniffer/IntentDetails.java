package edu.cmu.intentsniffer;

import java.util.ArrayList;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class IntentDetails extends Activity {


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.intent_details);



		Intent i = getIntent();
		
		String description = (String) i.getSerializableExtra("intentDetails");
		String temp = description.replace("{","").replace("}", "").replace("(", "").replace(")", "");
		String temp2 = temp.substring(temp.indexOf("extras")+7);

		String[] temp3 = temp2.split("\n");
		ListView listview =(ListView) findViewById(R.id.listView1);
		final ArrayList<String> list = new ArrayList<String>();
		for (int j = 0; j < temp3.length; ++j) {
		      list.add(temp3[j]);
		    }
		
		ArrayAdapter<String> arrayAdapter =      
		         new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, list);
		         listview.setAdapter(arrayAdapter);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.intent_details, menu);
		return true;
	}

}
