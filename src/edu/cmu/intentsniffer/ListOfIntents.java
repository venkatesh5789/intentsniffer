package edu.cmu.intentsniffer;

import java.util.ArrayList;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;

public class ListOfIntents extends Activity {
	private SearchView searchView;
	private CustomAdapter adapter;
	private ListView listView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.list_of_intents);

		listView = (ListView) findViewById(R.id.listOfIntents);

		Intent i = getIntent();
		Integer count = (Integer) i.getSerializableExtra("INTENTS_COUNT");

		final ArrayList<String> titles = new ArrayList<String>();
		final ArrayList<String> descriptions = new ArrayList<String>();

		for(int j = 0; j<count ; j++) {
			titles.add((String) i.getSerializableExtra("STORED_INTENTS_"+j));
			descriptions.add((String) i.getSerializableExtra("STORED_INTENTS_DESCRIPTION_"+j));
		}

		adapter = new CustomAdapter(this,
				android.R.layout.simple_list_item_1, titles);
		listView.setAdapter(adapter);

		listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

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
		MenuItem searchViewItem = menu.findItem(R.id.menu_search);
		searchView = (SearchView) searchViewItem.getActionView();

		searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener( ) {
			@Override
			public boolean onQueryTextChange( String newText ) {
				adapter.getFilter().filter(newText);
				return false;
			}

			@Override
			public boolean onQueryTextSubmit(String query) {
				adapter.getFilter().filter(query);
				return true;		
			}
		});
		return true;
	}
}


