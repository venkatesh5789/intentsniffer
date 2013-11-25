package edu.cmu.intentsniffer;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.content.Intent.FilterComparison;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class ListOfIntents extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.list_of_intents);
		
		final ListView listview = (ListView) findViewById(R.id.listOfIntents);
		
		Intent i = getIntent();
		
		Integer count = (Integer) i.getSerializableExtra("INTENTS_COUNT");
		
		final ArrayList<String> titles = new ArrayList<String>();
		final ArrayList<String> descriptions = new ArrayList<String>();
		
		for(int j = 0; j<count ; j++) {
			
			String comp = (String) i.getSerializableExtra("STORED_INTENTS_"+j);
			String time = comp.substring(comp.indexOf("Time - "));
			String act = comp.substring(comp.indexOf("act")+4, comp.indexOf("flg"));
			String flg = comp.substring(comp.indexOf("flg")+4,comp.indexOf("("));
			
			String toUse = "Intent: \n" + time  + "Action: "+act+"\nFlag: "+flg;
			Log.e("RUCHIR","##########"+flg);
			titles.add(toUse);
			descriptions.add((String) i.getSerializableExtra("STORED_INTENTS_DESCRIPTION_"+j));
		}
			
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, titles);
		listview.setAdapter(adapter);

		listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, final View view, int position, long id) {
				String description = descriptions.get(position);
				Intent intent = new Intent(getApplicationContext(), IntentDetails.class);
				intent.putExtra("intentDetails", description);
				startActivity(intent);
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.list_of_intents, menu);
		return true;
	}

}
