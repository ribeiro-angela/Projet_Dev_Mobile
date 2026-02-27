package com.example.powerhome;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ApplianceFragment extends Fragment {

    public ApplianceFragment() {}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        if (getActivity() != null) {
            getActivity().setTitle("Appliances");
        }

        View root = inflater.inflate(R.layout.fragment_appliance, container, false);
        ListView listView = root.findViewById(R.id.listAppliances);

        // Simple liste "globale" (peut être remplacée par des données serveur TP5)
        List<String> items = new ArrayList<>(Arrays.asList(
                "Fridge - 120 W",
                "Laundry - 500 W",
                "Iron - 1000 W",
                "Tap - 30 W"
        ));

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                requireContext(),
                android.R.layout.simple_list_item_1,
                items
        );
        listView.setAdapter(adapter);

        return root;
    }
}
