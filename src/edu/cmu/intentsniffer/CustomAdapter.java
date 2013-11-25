package edu.cmu.intentsniffer;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import android.content.Context;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;

public class CustomAdapter extends ArrayAdapter<String> implements Filterable {
	private ArrayList<String> titles;
	private ArrayList<String> allTitles = new ArrayList<String>();

	public CustomAdapter(Context context, int resource, ArrayList<String> titles) {
		super(context, resource, titles);
		this.titles = titles;
		insertTitles(titles);
	}

	private void insertTitles(ArrayList<String> titles) {
		Iterator<String> iterator = titles.iterator();
		while(iterator.hasNext()) {
			allTitles.add(iterator.next());
		}
		
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
