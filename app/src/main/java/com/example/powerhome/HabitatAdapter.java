package com.example.powerhome;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class HabitatAdapter extends ArrayAdapter<Habitat> {

    private final LayoutInflater inflater;

    public HabitatAdapter(@NonNull Context context, @NonNull List<Habitat> data) {
        super(context, 0, data);
        inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View row = convertView;
        if (row == null) {
            row = inflater.inflate(R.layout.item_habitat, parent, false);
        }

        Habitat r = getItem(position);
        if (r == null) return row;
        TextView tvName = row.findViewById(R.id.tvName);
        TextView tvEquip = row.findViewById(R.id.tvEquip);
        TextView tvFloor = row.findViewById(R.id.tvFloorNumber);
        LinearLayout iconsContainer = row.findViewById(R.id.iconsContainer);

        tvName.setText(r.name);
        tvFloor.setText(String.valueOf(r.floor));

        int count = r.getEquipmentCount();
        String equipLabel = count <= 1 ? "1 équipement" : (count + " équipements");
        tvEquip.setText(equipLabel);

        iconsContainer.removeAllViews();
        for (Appliance app : (r.appliances == null ? java.util.Collections.<Appliance>emptyList() : r.appliances)) {
            ImageView iv = new ImageView(getContext());
            int size = (int) (18 * getContext().getResources().getDisplayMetrics().density);
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(size, size);
            lp.setMarginEnd((int) (10 * getContext().getResources().getDisplayMetrics().density));
            iv.setLayoutParams(lp);
            iv.setImageResource(app.iconResId);
            iv.setAlpha(0.65f);
            iconsContainer.addView(iv);
        }

        row.setOnClickListener(v -> showCustomDialog(r));

        return row;
    }

    @SuppressLint("SetTextI18n")
    private void showCustomDialog(Habitat habitat) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        View dialogView = inflater.inflate(R.layout.dialog_resident_detail, null);
        builder.setView(dialogView);

        AlertDialog dialog = builder.create();

        if (dialog.getWindow() != null) {
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }

        TextView tvDialogName = dialogView.findViewById(R.id.dialogTvName);
        TextView tvDialogFloor = dialogView.findViewById(R.id.dialogTvFloor);
        TextView tvDialogArea = dialogView.findViewById(R.id.dialogTvArea);
        LinearLayout appliancesContainer = dialogView.findViewById(R.id.dialogAppliancesContainer);
        Button btnClose = dialogView.findViewById(R.id.btnCloseDialog);

        tvDialogName.setText(habitat.name);
        tvDialogFloor.setText("Étage : " + habitat.floor);
        tvDialogArea.setText("Surface : " + habitat.area + " m²");

        appliancesContainer.removeAllViews();
        for (Appliance app : (habitat.appliances == null ? java.util.Collections.<Appliance>emptyList() : habitat.appliances)) {
            // 1. Le conteneur de la ligne (RelativeLayout)
            RelativeLayout itemLayout = new RelativeLayout(getContext());
            itemLayout.setPadding(0, 15, 0, 15);

            // 2. L'icône (alignée à gauche du parent)
            ImageView iv = new ImageView(getContext());
            iv.setId(View.generateViewId()); // On génère un ID pour que le texte puisse se placer par rapport à elle
            iv.setImageResource(app.iconResId);
            iv.setColorFilter(Color.parseColor("#555555"));

            int iconSize = (int) (24 * getContext().getResources().getDisplayMetrics().density);
            RelativeLayout.LayoutParams lpIcon = new RelativeLayout.LayoutParams(iconSize, iconSize);
            lpIcon.addRule(RelativeLayout.ALIGN_PARENT_START);
            lpIcon.addRule(RelativeLayout.CENTER_VERTICAL);
            iv.setLayoutParams(lpIcon);

            TextView tvWatts = new TextView(getContext());
            tvWatts.setId(View.generateViewId());
            tvWatts.setText(app.wattage + " W");
            tvWatts.setTextSize(16f);
            tvWatts.setTextColor(Color.parseColor("#2E7D32"));
            tvWatts.setTypeface(null, android.graphics.Typeface.BOLD);

            RelativeLayout.LayoutParams lpWatts = new RelativeLayout.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            lpWatts.addRule(RelativeLayout.ALIGN_PARENT_END); // C'est ici que l'alignement à droite se fait
            lpWatts.addRule(RelativeLayout.CENTER_VERTICAL);
            tvWatts.setLayoutParams(lpWatts);

            // 4. Le nom (entre l'icône et la puissance)
            TextView tvName = new TextView(getContext());
            tvName.setText(app.name);
            tvName.setTextSize(16f);
            tvName.setTextColor(Color.BLACK);

            RelativeLayout.LayoutParams lpName = new RelativeLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            lpName.addRule(RelativeLayout.END_OF, iv.getId());      // À droite de l'icône
            lpName.addRule(RelativeLayout.START_OF, tvWatts.getId()); // S'arrête avant les Watts
            lpName.addRule(RelativeLayout.CENTER_VERTICAL);
            tvName.setPadding(20, 0, 20, 0); // Espace interne
            tvName.setLayoutParams(lpName);

            // Ajout des vues au RelativeLayout
            itemLayout.addView(iv);
            itemLayout.addView(tvWatts);
            itemLayout.addView(tvName);

            // Ajout de la ligne au conteneur du dialogue
            appliancesContainer.addView(itemLayout);
        }

        btnClose.setOnClickListener(v -> dialog.dismiss());

        dialog.show();
    }
}