package main.smart.bus.activity.adapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

public class AutoAdapter<T> extends BaseAdapter implements Filterable {
	private List<T> mObjects;
	private int mResource;
	private ArrayFilter mFilter;
	private int mDropDownResource;
	private Context mContext;
	private int mFieldId = 0;
	private LayoutInflater mInflater;
	private final Object mLock = new Object();
	private ArrayList<T> mOriginalValues;

	public AutoAdapter(Context context, int textViewResourceId) {
		init(context, textViewResourceId, 0, new ArrayList<T>());
	}

	public AutoAdapter(Context context, int resource, int textViewResourceId) {
		init(context, resource, textViewResourceId, new ArrayList<T>());
	}

	public AutoAdapter(Context context, int textViewResourceId, T[] objects) {
		init(context, textViewResourceId, 0, Arrays.asList(objects));
	}

	public AutoAdapter(Context context, int resource, int textViewResourceId,
			T[] objects) {
		init(context, resource, textViewResourceId, Arrays.asList(objects));
	}

	public AutoAdapter(Context context, int textViewResourceId, List<T> objects) {
		init(context, textViewResourceId, 0, objects);
	}

	public AutoAdapter(Context context, int resource, int textViewResourceId,
			List<T> objects) {
		init(context, resource, textViewResourceId, objects);
	}

	private void init(Context context, int resource, int textViewResourceId,List<T> objects) {
		mContext = context;
		mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		mResource = mDropDownResource = resource;
		mObjects = objects;
		mFieldId = textViewResourceId;
	}

	@Override
	public int getCount() {
		return mObjects.size();
	}

	@Override
	public T getItem(int position) {
		return mObjects.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		return createViewFromResource(position, convertView, parent, mResource);
	}

	@Override
	public Filter getFilter() {
		if (mFilter == null) {
			mFilter = new ArrayFilter();
		}
		return mFilter;
	}
	public int getPosition(T item) {
		return mObjects.indexOf(item);
	}
	private View createViewFromResource(int position, View convertView,ViewGroup parent, int resource) {
		View view;
		TextView text;
		if (convertView == null) {
			view = mInflater.inflate(resource, parent, false);
		} else {
			view = convertView;
		}
		try {
			if (mFieldId == 0) {
				text = (TextView) view;
			} else {
				text = (TextView) view.findViewById(mFieldId);
			}
		} catch (ClassCastException e) {
			Log.e("ArrayAdapter","You must supply a resource ID for a TextView");
			throw new IllegalStateException("ArrayAdapter requires the resource ID to be a TextView", e);
		}
		T item = getItem(position);
		if (item instanceof CharSequence) {
			text.setText((CharSequence) item);
		} else {
			text.setText(item.toString());
		}
		text.setTextSize(20);
		return view;
	}

	private class ArrayFilter extends Filter {
		@Override
		protected FilterResults performFiltering(CharSequence prefix) {
			FilterResults results = new FilterResults();
			if (mOriginalValues == null) {
				synchronized (mLock) {
					mOriginalValues = new ArrayList<T>(mObjects);
				}
			}
			if (prefix == null || prefix.length() == 0) {
				synchronized (mLock) {
					ArrayList<T> list = new ArrayList<T>(mOriginalValues);
					results.values = list;
					results.count = list.size();
				}
			} else {
				String prefixString = prefix.toString().toLowerCase();
				final ArrayList<T> values = mOriginalValues;
				final int count = values.size();
				final ArrayList<T> newValues = new ArrayList<T>(count);
				for (int i = 0; i < count; i++) {
					final T value = values.get(i);
					final String valueText = value.toString().toLowerCase();
					if (valueText.startsWith(prefixString)) {
						newValues.add(value);
					} else {
						final String[] words = valueText.split(" ");
						final int wordCount = words.length;
						for (int k = 0; k < wordCount; k++) {
							// if (words[k].startsWith(prefixString)) {
							if (words[k].indexOf(prefixString) != -1) {
								newValues.add(value);
								break;
							}
						}
					}
				}
				results.values = newValues;
				results.count = newValues.size();
			}
			return results;
		}

		@Override
		protected void publishResults(CharSequence constraint,FilterResults results) {
			mObjects = (List<T>) results.values;
			if (results.count > 0) {
				notifyDataSetChanged();
			} else {
				notifyDataSetInvalidated();
			}
		}
	}
}
