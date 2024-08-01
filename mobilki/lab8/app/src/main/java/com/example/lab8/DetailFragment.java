package com.example.lab8;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

public class DetailFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup
            container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_detail, container, false);
    }
    public void setSelectedItem(String selectedItem) {
        TextView desc = getView().findViewById(R.id.detailsText);
        ImageView photo = getView().findViewById(R.id.photo);
        switch (selectedItem) {
            case "shut":
                desc.setText(R.string.shut);
                photo.setImageResource(R.drawable.shut);
                break;
            case "metallica":
                desc.setText(R.string.metallica);
                photo.setImageResource(R.drawable.metallica);
                break;
            case "nirvana":
                desc.setText(R.string.nirvana);
                photo.setImageResource(R.drawable.nirvana);
                break;
        }
    }
}

