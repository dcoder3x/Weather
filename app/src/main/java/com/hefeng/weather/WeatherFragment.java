package com.hefeng.weather;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.CursorAdapter;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.hefeng.weather.db.AppSQLiteHelper;
import com.hefeng.weather.db.WeatherContract;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link WeatherFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link WeatherFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class WeatherFragment extends Fragment {

    public static final String TAG = "WeatherFragment";



    // TODO: Rename and change types of parameters


    private OnFragmentInteractionListener mListener;
    private ListView mListView;
    private SQLiteDataManager mSQLiteDataManager;
    private Cursor mCursor;
    private String mCityId;

    public WeatherFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @return A new instance of fragment WeatherFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static WeatherFragment newInstance(String param1) {
        WeatherFragment fragment = new WeatherFragment();
        Bundle args = new Bundle();
        args.putString(MainActivity.BUNDLE_INFO, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        if (getArguments() != null) {
            mCityId = getArguments().getString(MainActivity.BUNDLE_INFO);
        }
        mSQLiteDataManager = new SQLiteDataManager(getContext());
        new InsertAndQueryWeather().execute();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_weather, container, false);
        mListView = (ListView) v.findViewById(R.id.weather_list);
        mListView.setAdapter(new WeatherInfoAdapter(getContext(), mCursor));
        return v;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.wea_fm_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Bundle bundle = new Bundle();
        bundle.putString(MainActivity.BUNDLE_FROM, TAG);
        switch (item.getItemId()) {
            case R.id.add_city :
                onButtonPressed(bundle);
                return true;
            case R.id.settings :
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Bundle bundle) {
        if (mListener != null) {
            mListener.onFragmentInteraction(bundle);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Bundle bundle);
    }

    private class WeatherInfoAdapter extends CursorAdapter {


        public WeatherInfoAdapter(Context context, Cursor cursor) {
            super(context, cursor, 0);
        }
        @Override
        public View newView(Context context, Cursor cursor, ViewGroup parent) {

            View v = getLayoutInflater(null).inflate(R.layout.weather_list_item, null);
            return v;
        }

        @Override
        public void bindView(View view, Context context, Cursor cursor) {
            if (cursor == null || cursor.isAfterLast())
                return;
            TextView textView1 = (TextView) view.findViewById(R.id.weather_item_city_name);
            TextView textView2 = (TextView) view.findViewById(R.id.weather_item_city_txt);
            TextView textView3 = (TextView) view.findViewById(R.id.weather_item_city_tmp);
            TextView textView4 = (TextView) view.findViewById(R.id.weather_item_city_dir);

            textView1.setText(cursor.getString(cursor.getColumnIndex(WeatherContract.WeaColumns.CITY_NAME)));
            textView2.setText("天气:" + cursor.getString(cursor.getColumnIndex(WeatherContract.WeaColumns.TXT)));
            textView3.setText("温度:" + cursor.getString(cursor.getColumnIndex(WeatherContract.WeaColumns.TMP)));
            textView4.setText("风向:" + cursor.getString(cursor.getColumnIndex(WeatherContract.WeaColumns.DIR)));
            cursor.moveToNext();
        }

    }

    private class InsertAndQueryWeather extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {
            if (mCityId != null && !mCityId.isEmpty()) {
                if (mSQLiteDataManager.insertWeatherInfo(mCityId))
                    mSQLiteDataManager.changeIsAdded(mCityId);
            }
            mCursor = mSQLiteDataManager.queryWeather();
            return null;
        }

        @Override
        protected void onProgressUpdate(Void... values) {

        }

        @Override
        protected void onPostExecute(Void aVoid) {
            if (mCursor != null) {
                mListView.setAdapter(new WeatherInfoAdapter(getContext(), mCursor));
            } else {
                Toast.makeText(getContext(), "no weather info got!", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
