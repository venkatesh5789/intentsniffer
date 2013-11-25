package edu.cmu.intentsniffer;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;

public class CustomAdapter extends ArrayAdapter<String> implements Filterable {
	private ArrayList<String> titles;
	private int resource;
	private Context context;

	public CustomAdapter(Context context, int resource, ArrayList<String> titles) {
		super(context, resource, titles);
		this.titles = titles;
		this.resource = resource;
		this.context = context;
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
					results.values = titles;
					results.count = titles.size();
				}
				else {
					// We perform filtering operation
					List<String> nTitles = new ArrayList<String>();

					for (String t : titles) {
						if (t.toLowerCase().contains(constraintString.toLowerCase()))
							nTitles.add(t);
					}

					results.values = nTitles;
					results.count = nTitles.size();

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
					titles.clear();
					titles.addAll((ArrayList<String>) results.values);
					notifyDataSetChanged();
				}
			}

		};
	}
}
