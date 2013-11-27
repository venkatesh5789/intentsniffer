package edu.cmu.intentsniffer;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

public class CustomAdapter extends ArrayAdapter<String> implements Filterable {
	private ArrayList<String> titles;
	private ArrayList<String> allTitles = new ArrayList<String>();
	int resource;
	Context context;
	public LayoutInflater inflater;
	
	public CustomAdapter(Context context, int resource, ArrayList<String> titles) {
		super(context, resource, titles);
		this.titles = titles;
		this.context =context;
		this.resource = resource;
		insertTitles(titles);
		this.inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	private void insertTitles(ArrayList<String> titles) {
		Iterator<String> iterator = titles.iterator();
		while(iterator.hasNext()) {
			allTitles.add(iterator.next());
		}
		
	}
	
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
    	
    	View v = super.getView(position, convertView, parent);
    	String myString = getItem(position);
        if(myString.contains("com.android")){
        	((TextView)v).setTextColor(Color.YELLOW);
        
    }
        else if(myString.contains("com.sec")){
        	((TextView)v).setTextColor(Color.RED);
        
    }
        else if(myString.contains("Telephony") || myString.contains(".os.")){
        	((TextView)v).setTextColor(Color.MAGENTA);
        
    }
        else {
        	((TextView)v).setTextColor(Color.GREEN);
        }
		return v;
    }

	
	

	@Override
	public Filter getFilter() {
		return new Filter() {

			@Override
			protected FilterResults performFiltering(CharSequence constraint) {
				String constraintString = constraint.toString();
				FilterResults results = new FilterResults();
				
				// We implement here the filter logic
				if (constraint == null || constraint.length() == 0) {
					// No filter implemented we return the full list
					results.values = allTitles;
					results.count = allTitles.size();
				}
				else {
					// We perform filtering operation
					final ArrayList<String> filteredTitles = new ArrayList<String>();
					final ArrayList<String> localTitles = new ArrayList<String>();
					localTitles.addAll(allTitles);
					
					for (String t : localTitles) {
						if (t.toLowerCase().contains(constraintString.toLowerCase()))
							filteredTitles.add(t);
					}

					results.values = filteredTitles;
					results.count = filteredTitles.size();

				}
				return results;
			}

			@SuppressWarnings("unchecked")
			@Override
			protected void publishResults(CharSequence constraint, FilterResults results) {
				// Now we have to inform the adapter about the new list filtered
				if (results.count == 0)
					notifyDataSetInvalidated();
				else {
					final ArrayList<String> filteredItems = (ArrayList<String>) results.values;
					notifyDataSetChanged();
					clear();
					Iterator<String> iterator = filteredItems.iterator();
					
					while(iterator.hasNext()) {
						String title = iterator.next();
						add(title);
					}
				}
			}

		};
	}
}
