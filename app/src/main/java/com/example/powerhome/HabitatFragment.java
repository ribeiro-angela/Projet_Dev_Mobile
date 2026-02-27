package com.example.powerhome;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.koushikdutta.ion.Ion;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class HabitatFragment extends Fragment {

    private HabitatAdapter adapter;
    private ProgressBar progress;

    public HabitatFragment() {}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        if (getActivity() != null) {
            getActivity().setTitle("Habitats");
        }

        View root = inflater.inflate(R.layout.fragment_habitat, container, false);
        ListView listView = root.findViewById(R.id.listHabitats);
        progress = root.findViewById(R.id.progressHabitats);

        adapter = new HabitatAdapter(requireContext(), new ArrayList<>());
        listView.setAdapter(adapter);

        // TP5 (Service Web) : si le serveur n'est pas dispo, on retombe sur des données locales.
        loadHabitats();

        return root;
    }

    private void loadHabitats() {
        progress.setVisibility(View.VISIBLE);

        String url = ApiConfig.HABITATS_URL;
        if (url == null || url.trim().isEmpty()) {
            setLocalData();
            return;
        }

        Ion.with(this)
                .load(url)
                .asString()
                .setCallback((e, result) -> {
                    progress.setVisibility(View.GONE);

                    if (e != null || result == null) {
                        setLocalData();
                        Toast.makeText(requireContext(), "Serveur indisponible → données locales", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    try {
                        // On tente d'abord : JSON = tableau de habitats
                        Type listType = new TypeToken<List<Habitat>>() {}.getType();
                        List<Habitat> habitats = new Gson().fromJson(result, listType);

                        if (habitats == null || habitats.isEmpty()) {
                            setLocalData();
                            return;
                        }

                        adapter.clear();
                        adapter.addAll(habitats);
                        adapter.notifyDataSetChanged();

                    } catch (Exception ex) {
                        setLocalData();
                    }
                });
    }

    private void setLocalData() {
        progress.setVisibility(View.GONE);

        // Données locales (TP3 : listes + adaptateur personnalisé)
        List<Habitat> habitats = new ArrayList<>();

        habitats.add(new Habitat(
                "Gaëtan Leclair",
                1,
                50.0,
                Arrays.asList(
                        new Appliance("Fridge", R.drawable.ic_fridge, 120),
                        new Appliance("Laundry", R.drawable.ic_laundry, 500),
                        new Appliance("Iron", R.drawable.ic_iron, 1000),
                        new Appliance("Tap", R.drawable.ic_tap, 30)
                )
        ));

        habitats.add(new Habitat(
                "Cédric Boudet",
                1,
                35.0,
                Arrays.asList(
                        new Appliance("Fridge", R.drawable.ic_fridge, 120)
                )
        ));

        habitats.add(new Habitat(
                "Gaylord Thibodeaux",
                2,
                44.5,
                Arrays.asList(
                        new Appliance("Laundry", R.drawable.ic_laundry, 500),
                        new Appliance("Tap", R.drawable.ic_tap, 30)
                )
        ));

        adapter.clear();
        adapter.addAll(habitats);
        adapter.notifyDataSetChanged();
    }
}
