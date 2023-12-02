package com.example.games;

import android.app.Fragment;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.GridLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.Random;

public class Fragment_2048 extends Fragment implements GestureDetector.OnGestureListener{
    //Variables
    GestureDetector gestureDetector;
    Button[][] matriz_button = new Button[4][4];
    Button_2048[][] matriz_number = new Button_2048[4][4];
    Button button_volver;
    Button button_newGame;

    //Oncreate
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.layout_2048, container, false);
        //Meter todos los botones en un array
        GridLayout gridLayout = view.findViewById(R.id.panel_2048);
        for (int i = 0; i < gridLayout.getRowCount(); i++){
            for (int j=0; j < gridLayout.getColumnCount(); j++){
                String str_id = "boton_"+i+"_"+j;
                int buttonId = getResources().getIdentifier(str_id, "id", getActivity().getPackageName());
                Button button = view.findViewById(buttonId);
                matriz_button[i][j] = button;
            }
        }
        for (int row=0; row<4; row++){
            for (int column=0; column<4; column++){
                matriz_number[row][column] = new Button_2048(getContext());
            }
        }

        //Botones
        button_volver = view.findViewById(R.id.button_volver);
        button_newGame = view.findViewById(R.id.button_newgame);
        Log.d("Botons", "Movimiento");
        configureButtons();

        //Generar los 2 primeros numeros
        generate_Random_2();
        generate_Random_2();
        reloadMatriz();

        //Gesture detector
        gestureDetector = new GestureDetector(getActivity(), this);
        view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return gestureDetector.onTouchEvent(event);
            }
        });

        return view;
    }

    private void configureButtons(){
        button_newGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setAll0();
                generate_Random_2();
                generate_Random_2();
                reloadMatriz();
            }
        });

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

    private void generate_Random_2(){
        boolean bucle = true;
        while (bucle){
            Random random = new Random();
            int row = random.nextInt(4);
            int column = random.nextInt(4);
            System.out.println(row+","+column);
            Button_2048 button2048 = matriz_number[row][column];

            if (button2048.getNumber().equals("0")){
                button2048.setNumber("2");
                bucle = false;
            }
        }
    }

    private void setAll0(){
        for (int row=0; row<4; row++){
            for (int column=0; column<4; column++){
                Button_2048 button2048 = matriz_number[row][column];
                button2048.setNumber("0");
            }
        }
    }

    //Plasmar los numeros en los botones
    private void reloadMatriz(){
        for (int row=0; row<4; row++){
            for (int column=0; column<4; column++){
                Button button = matriz_button[row][column];
                Button_2048 button2048 = matriz_number[row][column];

                button.setBackgroundDrawable(button2048.getDrawable());
                button.setText(button2048.getNumber());
            }
        }
    }

    //Gesture Detector
    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        float distanceX = e2.getX() - e1.getX();
        float distanceY = e2.getY() - e1.getY();
        //Sensibilidad
        int swipeThreshold = 100;

        if (Math.abs(distanceX) > Math.abs(distanceY)) {
            // Deslizamiento horizontal
            if (Math.abs(distanceX) > swipeThreshold && Math.abs(velocityX) > swipeThreshold) {
                if (distanceX > 0) {
                    //Derecha
                    for (int row = 0; row < 4; row++) {
                        for (int column = 2; column >= 0; column--) {
                            if (!matriz_number[row][column].getNumber().equals("0")) {
                                int currentColumn = column;
                                while (currentColumn + 1 < 4 && matriz_number[row][currentColumn + 1].getNumber().equals("0")) {
                                    matriz_number[row][currentColumn + 1].setNumber(matriz_number[row][currentColumn].getNumber());
                                    matriz_number[row][currentColumn].setNumber("0");
                                    currentColumn++;
                                }

                                if (currentColumn + 1 < 4 && matriz_number[row][currentColumn + 1].getNumber().equals(matriz_number[row][currentColumn].getNumber())) {
                                    // Multiplicar
                                    matriz_number[row][currentColumn + 1].multiply();
                                    matriz_number[row][currentColumn].setNumber("0");
                                }
                            }
                        }
                    }
                } else {
                    //Izquierda
                    for (int row = 0; row < 4; row++) {
                        for (int column = 1; column < 4; column++) {
                            if (!matriz_number[row][column].getNumber().equals("0")) {
                                int currentColumn = column;
                                while (currentColumn - 1 >= 0 && matriz_number[row][currentColumn - 1].getNumber().equals("0")) {
                                    matriz_number[row][currentColumn - 1].setNumber(matriz_number[row][currentColumn].getNumber());
                                    matriz_number[row][currentColumn].setNumber("0");
                                    currentColumn--;
                                }

                                if (currentColumn - 1 >= 0 && matriz_number[row][currentColumn - 1].getNumber().equals(matriz_number[row][currentColumn].getNumber())) {
                                    // Multiplicar
                                    matriz_number[row][currentColumn - 1].multiply();
                                    matriz_number[row][currentColumn].setNumber("0");
                                }
                            }
                        }
                    }
                }
            }
        } else {
            // Deslizamiento vertical
            if (Math.abs(distanceY) > swipeThreshold && Math.abs(velocityY) > swipeThreshold) {
                if (distanceY > 0) {
                    //Abajo
                    for (int column = 0; column < 4; column++) {
                        for (int row = 2; row >= 0; row--) {
                            if (!matriz_number[row][column].getNumber().equals("0")) {
                                int currentRow = row;
                                while (currentRow + 1 < 4 && matriz_number[currentRow + 1][column].getNumber().equals("0")) {
                                    matriz_number[currentRow + 1][column].setNumber(matriz_number[currentRow][column].getNumber());
                                    matriz_number[currentRow][column].setNumber("0");
                                    currentRow++;
                                }

                                if (currentRow + 1 < 4 && matriz_number[currentRow + 1][column].getNumber().equals(matriz_number[currentRow][column].getNumber())) {
                                    // Multiplicar
                                    matriz_number[currentRow + 1][column].multiply();
                                    matriz_number[currentRow][column].setNumber("0");
                                }
                            }
                        }
                    }
                } else {
                    //Arriba
                    for (int column = 0; column < 4; column++) {
                        for (int row = 1; row < 4; row++) {
                            if (!matriz_number[row][column].getNumber().equals("0")) {
                                int currentRow = row;
                                while (currentRow - 1 >= 0 && matriz_number[currentRow - 1][column].getNumber().equals("0")) {
                                    matriz_number[currentRow - 1][column].setNumber(matriz_number[currentRow][column].getNumber());
                                    matriz_number[currentRow][column].setNumber("0");
                                    currentRow--;
                                }

                                if (currentRow - 1 >= 0 && matriz_number[currentRow - 1][column].getNumber().equals(matriz_number[currentRow][column].getNumber())) {
                                    // Multiplicar
                                    matriz_number[currentRow - 1][column].multiply();
                                    matriz_number[currentRow][column].setNumber("0");
                                }
                            }
                        }
                    }
                }
            }
        }
        generate_Random_2();
        reloadMatriz();
        return true;
    }

    @Override
    public boolean onDown(@NonNull MotionEvent e) {
        return true;
    }

    @Override
    public void onShowPress(@NonNull MotionEvent e) {

    }

    @Override
    public boolean onSingleTapUp(@NonNull MotionEvent e) {
        return true;
    }

    @Override
    public boolean onScroll(@Nullable MotionEvent e1, @NonNull MotionEvent e2, float distanceX, float distanceY) {
        return false;
    }

    @Override
    public void onLongPress(@NonNull MotionEvent e) {

    }
}
