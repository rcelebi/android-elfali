package org.tensorflow.demo;

import java.util.List;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


public class RecordListView extends ListActivity {
	private static final String TAG = RecordListView.class.getName();
	private EfficientAdapter adap;
	private List<Record> data;

	public void setData(List<Record> data) {
		this.data = data;
		if(data == null || data.size() == 0){
			Toast.makeText(getBaseContext(), "Liste bo�",
					Toast.LENGTH_SHORT).show();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		menu.add(0, 1, 0, "Listeyi G�ncele");

		return true;

	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (BuildConfig.DEBUG) {
			Log.d(TAG, "menu item " + item.getItemId());
		}
		// Handle item selection
		if (item.getItemId() == 1) {
			//
			adap.notifyDataSetChanged();
			Toast.makeText(getBaseContext(), "Liste G�ncellendi",
					Toast.LENGTH_SHORT).show();

		} else {
			return false;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setData(StartUp.dbhelper.selectAllRecords());

		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.listview_layout);
		adap = new EfficientAdapter(this);
		setListAdapter(adap);
	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		// TODO Auto-generated method stub
		super.onListItemClick(l, v, position, id);
		// Toast.makeText(this, "Click-" + String.valueOf(position),
		// Toast.LENGTH_SHORT).show();
	}

	public class EfficientAdapter extends BaseAdapter implements Filterable {
		private LayoutInflater mInflater;
		private Context context;

		public EfficientAdapter(Context context) {
			// Cache the LayoutInflate to avoid asking for a new one each time.
			mInflater = LayoutInflater.from(context);
			this.context = context;
		}

		/**
		 * Make a view to hold each row.
		 * 
		 * @see android.widget.ListAdapter#getView(int, View,
		 *      ViewGroup)
		 */
		public View getView(final int position, View convertView,
				ViewGroup parent) {
			// A ViewHolder keeps references to children views to avoid
			// unneccessary calls
			// to findViewById() on each row.
			ViewHolder holder;

			// When convertView is not null, we can reuse it directly, there is
			// no need
			// to reinflate it. We only inflate a new View when the convertView
			// supplied
			// by ListView is null.
			if (convertView == null) {
				convertView = mInflater.inflate(
						R.layout.listview_adaptor_content, null);

				// Creates a ViewHolder and store references to the two children
				// views
				// we want to bind data to.
				holder = new ViewHolder();
				holder.textLine = (TextView) convertView
						.findViewById(R.id.textLine);
				holder.icon = (ImageView) convertView.findViewById(R.id.icon);

				holder.textLine2 = (TextView) convertView
						.findViewById(R.id.textLine2);

				holder.textLine3 = (TextView) convertView
						.findViewById(R.id.textLine3);

				convertView.setOnClickListener(new OnClickListener() {

					public void onClick(View v) {
						Intent myIntent = new Intent(context, PalmView.class);
						Record r = data.get(position);
						myIntent.putExtra(Properties.INTENT_EXTRA_PALM_URL,
								r.getFotoURL() + Properties.HAND_PALM_IMAGE_EXT
										+ Properties.FILE_EXT);
						int []results=new int[] { r.getResult1(), r.getResult2(),
								r.getResult3(), r.getResult4() };
						myIntent.putExtra(
								Properties.INTENT_EXTRA_YORUM_RESULTS, results);
						
						context.startActivity(myIntent);

					}
				});

				convertView.setTag(holder);
			} else {
				// Get the ViewHolder back to get fast access to the TextView
				// and the ImageView.
				holder = (ViewHolder) convertView.getTag();
			}
			// ImageView thumb_image = (ImageView) convertView
			// .findViewById(R.id.icon); // thumb image

			Record itemPojo = data.get(position);

			if (BuildConfig.DEBUG) {
				Log.d(TAG, "setting record: " + position);
			}
			holder.textLine.setText(itemPojo.getDate().toLocaleString());
			holder.textLine2.setText(itemPojo.getFotoURL());

			// holder.textLine3.setText("aaa"+itemPojo.getResult1());
			//
			// Bitmap bm;
			// bm.

			holder.icon.setImageDrawable(Drawable.createFromPath(itemPojo
					.getFotoURL() ));

			// imageLoader.DisplayImage(, thumb_image);

			return convertView;
		}

		class ViewHolder {
			ImageView icon;
			TextView textLine;
			TextView textLine2;
			TextView textLine3;
			Button buttonLine;

		}

		public Filter getFilter() {
			// TODO Auto-generated method stub
			return null;
		}

		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return 0;
		}

		public int getCount() {
			// TODO Auto-generated method stub
			return data.size();
		}

		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return data.get(position);
		}

	}

}