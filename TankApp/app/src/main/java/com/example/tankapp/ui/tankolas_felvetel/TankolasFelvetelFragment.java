package com.example.tankapp.ui.tankolas_felvetel;

import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;

import android.app.DatePickerDialog;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import com.example.tankapp.R;
import com.example.tankapp.databinding.FragmentTankolasFelvetelBinding;
import java.util.Calendar;

public class TankolasFelvetelFragment extends Fragment {
    private FragmentTankolasFelvetelBinding binding;
    private Button btn_date;
    private  DatePickerDialog.OnDateSetListener dateSetListener;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        TankolasFelvetelViewModel tankolasFelvetelViewModel = new ViewModelProvider(this).get(TankolasFelvetelViewModel.class);

        binding = FragmentTankolasFelvetelBinding.inflate(inflater, container, false);

        View root = binding.getRoot();
        btn_date = root.findViewById(R.id.setDateTimeButton);
        /**
         *  A btn_date dátumkiválasztó gombra kattintáskor meghívandó dátumkiválasztó interfész-definíciója.
         */
        btn_date.setOnClickListener(new View.OnClickListener() {
            /**
             * Az onCLick metódus akkor hívódik, ha egy nézetre kattintottak.
             * @param v Az adott nézet amire kattintottak.
             */
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                /**
                 * Új dátumválasztó párbeszédpanelt hoz létre a megadott dátumhoz.
                 */
                DatePickerDialog dialog = new DatePickerDialog(getContext(), android.R.style.Theme_DeviceDefault_Dialog, dateSetListener,year,month,day);
                dialog.show();
            }
        });

        /**
         * A DatePickerDialog.OnDateSetListener(), amely jelzi, hogy a felhasználó befejezte a dátum kiválasztását.
         * Kiválasztás után módosítja btn_date gomb szövegét a kiválasztott dátumra.
         * @param view  A párbeszédpanelhez tartozó kiválasztó
         * @param year  A kiválasztott év
         * @param month  A kiválasztott hónap (0-11 a Calendar#MONTH kompatibilitásból adódóan)
         * @param dayOfMonth  Az adott hónap kiválasztott napja (1-31, hónaptól függően)
         */
        dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month = month + 1;
                String date = year + "/" + month + "/" + dayOfMonth;
                btn_date.setText(date);
            }
        };

        return root;
    }

    @Override
    public void onDestroyView(){
        super.onDestroyView();
        binding = null;
    }


}