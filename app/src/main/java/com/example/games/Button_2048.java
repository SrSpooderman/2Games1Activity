package com.example.games;
import static java.security.AccessController.getContext;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.widget.Button;

public class Button_2048{
    String number;
    Drawable drawable;
    Context context;

    public Button_2048(Context context){
        this.context = context;
        number = "0";
        drawable = context.getDrawable(R.drawable.button_2048);
    }
    public void reload_Draw(){

    }

    public void multiply(){
        int i = Integer.parseInt(number) * 2;
        number = String.valueOf(i);
        reload_Draw();
    }

    public String getNumber() {
        return number;
    }

    public Drawable getDrawable() {
        return drawable;
    }

    public void setNumber(String number) {
        this.number = number;
        reload_Draw();
    }
}
