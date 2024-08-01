package com.example.lab8;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import androidx.fragment.app.Fragment;

public class ListFragment extends Fragment {
    interface OnFragmentSendDataListener {
        void onSendData(String data);
    }

    private OnFragmentSendDataListener fragmentSendDataListener;
    String[] pics = {"nirvana", "shut", "metallica"};
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            fragmentSendDataListener = (OnFragmentSendDataListener)
                    context;
        }
        catch (ClassCastException e) {
            throw new ClassCastException(context.toString());
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list, container, false);
        ListView groupList = view.findViewById(R.id.list);
        ArrayAdapter<String> adapter = new ArrayAdapter(getContext(), android.R.layout.simple_list_item_1, pics);
        groupList.setAdapter(adapter);
        groupList.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                String selectedItem = (String)parent.getItemAtPosition(position);
                fragmentSendDataListener.onSendData(selectedItem);
            }
        });
        return view;
    }
}

