package com.hefeng.weather;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.hefeng.weather.model.City;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link AddCityFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link AddCityFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddCityFragment extends Fragment {

    private static final String TAG = "AddCityFragment";
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
    private CityInfoManager mCityInfoManager;
    private ArrayList<City> mCities;
    private EditText mSearchEt;
    private Button mSearchBtn;
    private ListView mSearchResultLv;

    public AddCityFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AddCityFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AddCityFragment newInstance(String param1, String param2) {
        AddCityFragment fragment = new AddCityFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        mCityInfoManager = new CityInfoManager(getContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_add_city, container, false);
        mSearchResultLv = (ListView) v.findViewById(R.id.search_result_lv);
        mSearchResultLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });

        mSearchEt = (EditText) v.findViewById(R.id.search_et);
        mSearchBtn = (Button) v.findViewById(R.id.search_btn);
        mSearchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String search = mSearchEt.getText().toString();
                mCities = mCityInfoManager.getSearchResult(search);
                if (mCities == null) {
                    mSearchResultLv.setAdapter(null);
                    Toast.makeText(getContext(), "get nothing!", Toast.LENGTH_SHORT).show();
                } else {
                    mSearchResultLv.setAdapter(new ResultListAdapter(getContext(), mCities));
                    Toast.makeText(getContext(), mCities.size()+ "!", Toast.LENGTH_SHORT).show();
                }
            }
        });
        return v;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
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
        void onFragmentInteraction(Uri uri);
    }

    /**
     * Search Result ListView adapter.
     */
    private class ResultListAdapter extends BaseAdapter {
        private Context mContext;
        private ArrayList<City> mArrayList;

        public ResultListAdapter(Context context, ArrayList<City> arrayList) {
            mContext = context;
            mArrayList = arrayList;
        }

        @Override
        public int getCount() {
            return mArrayList.size();
        }

        @Override
        public Object getItem(int position) {
            return mArrayList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = getLayoutInflater(null).inflate(R.layout.city_list_item, null);
            }
            City city = (City) getItem(position);
            TextView cityName = (TextView) convertView.findViewById(R.id.city_name_tv);
            TextView superiorName = (TextView) convertView.findViewById(R.id.superior_name_tv);
            cityName.setText(city.getCityZh());
            superiorName.setText(city.getLeaderZh() + "," + city.getProvinceZh());
            return convertView;
        }
    }
}
