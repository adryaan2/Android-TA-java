package com.example.tankapp.ui.tankolas_felvetel;

import androidx.lifecycle.ViewModelProvider;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.tankapp.MainActivity;
import com.example.tankapp.R;
import com.example.tankapp.data.DatabaseHelper;
import com.example.tankapp.data.models.TavolsagModel;
import com.example.tankapp.data.models.UrmertekModel;
import com.example.tankapp.data.models.UzemanyagModel;
import com.example.tankapp.data.models.ValutaModel;
import com.example.tankapp.databinding.FragmentTankolasFelvetelBinding;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;

public class TankolasFelvetelFragment extends Fragment {
    private FragmentTankolasFelvetelBinding binding;
    private Button btn_date, btn_mentes;
    private  DatePickerDialog.OnDateSetListener dateSetListener;
    private ArrayAdapter<String> adapter;
    private Spinner tavolsagSpinner, uzemanyagSpinner, urmertekSpinner, valutaSpinner;
    private EditText txt_tavolsag, txt_uzemanyagMennyiseg, txt_uzemanyagEgysegar;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        TankolasFelvetelViewModel tankolasFelvetelViewModel = new ViewModelProvider(this).get(TankolasFelvetelViewModel.class);

        binding = FragmentTankolasFelvetelBinding.inflate(inflater, container, false);
        MainActivity.getContext().hideUjtankolasBtn();
        View root = binding.getRoot();

        TextView txt_rendsz = root.findViewById(R.id.carNumPlate);
        txt_rendsz.setText(MainActivity.aktivJarmu.getRendszam());

        btn_date = root.findViewById(R.id.setDateTimeButton);
        btn_mentes = root.findViewById(R.id.saveRefuellingButton);
        /**
         *  A btn_date dátumkiválasztó gombra kattintáskor meghívandó dátumkiválasztó interfész-definíciója.
         */

        /**
         * Spinnerek feltöltése.
         */
        ArrayList<TavolsagModel> tavolsagModels = DatabaseHelper.getInstance(MainActivity.getContext()).getTavolsagok();
        ArrayList<UzemanyagModel> uzemanyagModels = DatabaseHelper.getInstance(MainActivity.getContext()).getUzemanyagok();
        ArrayList<UrmertekModel> urmertekModels = DatabaseHelper.getInstance(MainActivity.getContext()).getUrmertekek();
        ArrayList<ValutaModel> valutaModels = DatabaseHelper.getInstance(MainActivity.getContext()).getValutak();
        ArrayList<String> tavolsagElemek = new ArrayList<>();
        ArrayList<String> uzemanyagElemek = new ArrayList<>();
        ArrayList<String> urmertekElemek = new ArrayList<>();
        ArrayList<String> valutaElemek = new ArrayList<>();

        for (int i = 0; i < tavolsagModels.size(); i++) {
            tavolsagElemek.add(tavolsagModels.get(i).getTavolsag());
        }
        adapter = new ArrayAdapter<>(MainActivity.getContext(), android.R.layout.simple_spinner_item, tavolsagElemek) ;
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        tavolsagSpinner = (Spinner)root.findViewById(R.id.distanceUnitSpinner);
        tavolsagSpinner.setAdapter(adapter);

        for (int i = 0; i < uzemanyagModels.size(); i++) {
            uzemanyagElemek.add(uzemanyagModels.get(i).getMegnev());
        }
        adapter = new ArrayAdapter<>(MainActivity.getContext(), android.R.layout.simple_spinner_item, uzemanyagElemek);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        uzemanyagSpinner = (Spinner)root.findViewById(R.id.fuelTypeSpinner);
        uzemanyagSpinner.setAdapter(adapter);

        for (int i = 0; i < urmertekModels.size(); i++) {
            urmertekElemek.add(urmertekModels.get(i).getUrmertek());
        }
        adapter = new ArrayAdapter<>(MainActivity.getContext(), android.R.layout.simple_spinner_item, urmertekElemek);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        urmertekSpinner = (Spinner)root.findViewById(R.id.fuelUnitTypeSpinner);
        urmertekSpinner.setAdapter(adapter);

        for (int i = 0; i < valutaModels.size(); i++) {
            valutaElemek.add(valutaModels.get(i).getValuta());
        }
        adapter = new ArrayAdapter<>(MainActivity.getContext(), android.R.layout.simple_spinner_item, valutaElemek);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        valutaSpinner = (Spinner)root.findViewById(R.id.fuelCurrencySpinner);
        valutaSpinner.setAdapter(adapter);

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
                String date = "";

                /**
                 * Ennek a logikának a segítségével mindenképp 2 karakter hosszú lesz a hónap és a nap.
                 * Ha alapvetően csak egy karakter hosszú lenne, akkor hozzáfűz elé egy 0-át. (pl: 4 helyett 04)
                 * Melyre a DateTimeFormatter.ofPattern() metódusában megadott yyyy/MM/dd formátum miatt volt szükség.
                 */
                if (month < 10 ) {
                    if (dayOfMonth < 10) {
                        date = year + "/0" + month + "/0" + dayOfMonth;
                    }
                    else {
                        date = year + "/0" + month + "/" + dayOfMonth;
                    }
                }
                else {
                    if (dayOfMonth < 10) {
                        date = year + "/" + month + "/0" + dayOfMonth;
                    }
                    else {
                        date = year + "/" + month + "/" + dayOfMonth;
                    }
                }

                btn_date.setText(date);
            }
        };

        btn_mentes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean hozzaadhatoE = true;
                TextView textViewAlertDatum = root.findViewById(R.id.dateAlertText);
                TextView textViewAlertDistance = root.findViewById(R.id.distanceAlertText);
                TextView textViewAlertUzemanyagMennyiseg = root.findViewById(R.id.fuelQuantityAlertText);
                TextView textViewAlertUzemanyagEgysegar = root.findViewById(R.id.fuelPriceAlertText);
                textViewAlertDatum.setVisibility(View.GONE);
                textViewAlertDistance.setVisibility(View.GONE);
                textViewAlertUzemanyagMennyiseg.setVisibility(View.GONE);
                textViewAlertUzemanyagEgysegar.setVisibility(View.GONE);

                /**
                 * Hibakezelés arra az esetre, ha nem tölti ki valamelyik beviteli mezőt vagy a dátumot.
                 * A beviteli mezőkbe csak számot lehet beleírni.
                 */
                txt_tavolsag = root.findViewById(R.id.distanceInputField);
                if (txt_tavolsag.getText().toString().equals("")) {
                    textViewAlertDistance.setVisibility(View.VISIBLE);
                    hozzaadhatoE = false;
                }

                txt_uzemanyagMennyiseg = root.findViewById(R.id.fuelQuantityInputField);
                if (txt_uzemanyagMennyiseg.getText().toString().equals("")) {
                    textViewAlertUzemanyagMennyiseg.setVisibility(View.VISIBLE);
                    hozzaadhatoE = false;
                }

                txt_uzemanyagEgysegar = root.findViewById(R.id.fuelPriceInputField);
                if (txt_uzemanyagEgysegar.getText().toString().equals("")) {
                    textViewAlertUzemanyagEgysegar.setVisibility(View.VISIBLE);
                    hozzaadhatoE = false;
                }

                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
                LocalDate date = LocalDate.now();
                try {
                    date = LocalDate.parse(btn_date.getText(), formatter);
                }
                catch (Exception e) {
                    textViewAlertDatum.setVisibility(View.VISIBLE);
                    hozzaadhatoE = false;
                }

                if (hozzaadhatoE) {
                    DatabaseHelper.getInstance(MainActivity.getContext()).addTankolasok(date.toEpochDay(), MainActivity.aktivJarmu.getAutoId(), Integer.parseInt(String.valueOf(txt_tavolsag.getText())), (int)tavolsagSpinner.getSelectedItemId() + 1, Float.parseFloat(String.valueOf(txt_uzemanyagEgysegar.getText())), (int)valutaSpinner.getSelectedItemId() + 1, Float.parseFloat(String.valueOf(txt_uzemanyagMennyiseg.getText())), (int)uzemanyagSpinner.getSelectedItemId() + 1, (int)urmertekSpinner.getSelectedItemId() + 1);
                    Navigation.findNavController(view).navigate(R.id.nav_kezdo);
                }
            }
        });

        return root;
    }

    @Override
    public void onPause() {
        super.onPause();
        hideKeyboardFrom(getContext(),getView());
    }

    @Override
    public void onDestroyView(){
        super.onDestroyView();
        binding = null;
    }

    public static void hideKeyboardFrom(Context context, View view) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }


}