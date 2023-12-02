package com.example.games;

import android.app.Fragment;
import android.view.View;
import android.widget.Button;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.Nullable;

public class Fragment_menu extends Fragment implements View.OnClickListener{
    //Variables
    private int button_2048_id;
    private int button_senku_id;

    //Oncreate
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.menu_principal, container, false);

        button_2048_id = R.id.button_2048;
        button_senku_id = R.id.button_senku;
        Button button2048 = view.findViewById(R.id.button_2048);
        Button buttonSenku = view.findViewById(R.id.button_senku);
        button2048.setOnClickListener(this);
        buttonSenku.setOnClickListener(this);

        return view;
    }

    //OnClick
    @Override
    public void onClick(View view){
        int buttonID = (int) view.getId();

        if (buttonID == button_2048_id){
            if (getActivity() instanceof MainActivity){
                MainActivity mainActivity = (MainActivity) getActivity();
                mainActivity.replaceFragment(new Fragment_2048());
            }
        } else if (buttonID == button_senku_id) {
            if (getActivity() instanceof MainActivity){
                MainActivity mainActivity = (MainActivity) getActivity();
                mainActivity.replaceFragment(new Fragment_senku());
            }
        }
    }
}
