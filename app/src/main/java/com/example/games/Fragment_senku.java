package com.example.games;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridLayout;

import androidx.annotation.Nullable;

public class Fragment_senku extends Fragment {
    //Variables
    GridLayout gridLayout;

    Button button_volver;
    Button[][] matriz_button = new Button[7][7];
    //Oncreate
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.layout_senku, container, false);

        gridLayout = view.findViewById(R.id.panel_senku);
        generateButtons();

        //Boton de volver
        button_volver = view.findViewById(R.id.button_volver);
        configureButton();



        return view;
    }

    private void configureButton(){
        button_volver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getActivity() instanceof MainActivity){
                    MainActivity mainActivity = (MainActivity) getActivity();
                    mainActivity.replaceFragment(new Fragment_menu());
                }
            }
        });
    }

    private void generateButtons(){
        int numRows = 7;
        int numColumns = 7;
        for (int row = 0; row < numRows; row++) {
            for (int column = 0; column < numColumns; column++) {
                Button button = new Button(getContext());
                //Config Button
                GridLayout.LayoutParams params = new GridLayout.LayoutParams();
                params.width = 0;
                params.height = 0;
                params.rowSpec = GridLayout.spec(row, 1f);
                params.columnSpec = GridLayout.spec(column, 1f);
                params.setMargins(2, 2, 2, 2);
                button.setLayoutParams(params);
                button.setBackgroundResource(R.drawable.button_senku);
                button.setTextSize(30);
                //OnClick
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        deselect_All();
                        button.setBackgroundResource(R.drawable.button_senku_on);
                    }
                });
                //Addition
                matriz_button[row][column] = button;
                gridLayout.addView(button);
            }
        }
    }

    private void deselect_All(){
        for (int row = 0; row < 7; row++) {
            for (int column = 0; column < 7; column++) {
                Button button = matriz_button[row][column];
                button.setBackgroundResource(R.drawable.button_senku);
            }
        }
    }
}
