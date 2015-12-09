package com.pressthatbutton.thirtysecondsmash;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AllHighScoresFragment extends Fragment {

    public Button btnAllToMain;
    public Button btnAllToOwn;
    public ListFragmentInteractionListener listFragmentInteractionListener;

    public AllHighScoresFragment() {
        // Required empty public constructor
    }

//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        // Inflate the layout for this fragment
//        View view = inflater.inflate(R.layout.fragment_all_high_scores);
//
//        ListView listView = (ListView)view.findViewById(R.id.all_score_list_view);
//
//        List<String> places = Arrays.asList("wer","werr", "oer");
//
//        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1,places);
//        listView.setAdapter(arrayAdapter);
//        return view;
//    }

    public interface ListFragmentInteractionListener {
        // TODO: Update argument type and name
        public void itemClicked(String city);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        listFragmentInteractionListener = (ListFragmentInteractionListener)context;
    }
}
