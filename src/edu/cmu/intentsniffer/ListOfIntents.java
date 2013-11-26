package edu.cmu.intentsniffer;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import android.os.Bundle;
import android.os.Environment;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.Intent.FilterComparison;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

@SuppressLint("NewApi")
public class ListOfIntents extends Activity {
	private SearchView searchView;
	private CustomAdapter adapter;
	private ListView listView;
	private Button report;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.list_of_intents);

		listView = (ListView) findViewById(R.id.listOfIntents);
		report = (Button) findViewById(R.id.Report);
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
		
		report.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String state = Environment.getExternalStorageState();
				if(Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)){
					Toast.makeText(ListOfIntents.this, "READ ONLY", Toast.LENGTH_SHORT).show();
				}
				else if(!Environment.MEDIA_MOUNTED.equals(state)){
					Toast.makeText(ListOfIntents.this, "No SD CARD", Toast.LENGTH_SHORT).show();
				}
				else{
					File root = new File(Environment.getExternalStorageDirectory(),"Report");
					if(!root.exists()){
						root.mkdirs();
					}
					File report = new File(root,"report.txt");
					try {
						FileWriter writer = new FileWriter(report);
						for(int i = 0; i < titles.size(); i++){
							writer.append(titles.get(i));
							writer.flush();
						}
						writer.close();
						Toast.makeText(ListOfIntents.this, "Saved", Toast.LENGTH_SHORT).show();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					
				}
			}
		});
	}

	@SuppressLint("NewApi")
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


