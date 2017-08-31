package com.antandbuffalo.birthdayreminder.modifytoday;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.NumberPicker;

import com.antandbuffalo.birthdayreminder.Constants;
import com.antandbuffalo.birthdayreminder.R;
import com.antandbuffalo.birthdayreminder.Util;

import java.lang.reflect.Field;

public class ModifyToday extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.modify_today);
        final SharedPreferences settings = Util.getSharedPreference();

        final NumberPicker numberPicker = (NumberPicker) findViewById(R.id.recentDaysToday);
        setNumberPickerTextColor(numberPicker, Color.WHITE);
        numberPicker.setWrapSelectorWheel(true);
        numberPicker.setMaxValue(10);
        numberPicker.setMinValue(0);

        int recentDays = settings.getInt(Constants.PREFERENCE_RECENT_DAYS_TODAY, 0);
        numberPicker.setValue(recentDays);

        ImageButton save = (ImageButton) findViewById(R.id.save);
        save.setBackgroundResource(R.drawable.save_button);

        ImageButton cancel = (ImageButton)findViewById(R.id.cancel);
        cancel.setBackgroundResource(R.drawable.cancel_button);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                settings.getInt(Constants.PREFERENCE_RECENT_DAYS_TODAY, 0);
                SharedPreferences.Editor editor = settings.edit();
                editor.putInt(Constants.PREFERENCE_RECENT_DAYS_TODAY, numberPicker.getValue());
                editor.commit();
                finish();
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public static boolean setNumberPickerTextColor(NumberPicker numberPicker, int color)
    {
        final int count = numberPicker.getChildCount();
        for(int i = 0; i < count; i++){
            View child = numberPicker.getChildAt(i);
            if(child instanceof EditText){
                try{
                    Field selectorWheelPaintField = numberPicker.getClass()
                            .getDeclaredField("mSelectorWheelPaint");
                    selectorWheelPaintField.setAccessible(true);
                    ((Paint)selectorWheelPaintField.get(numberPicker)).setColor(color);
                    ((EditText)child).setTextColor(color);
                    numberPicker.invalidate();
                    return true;
                }
                catch(NoSuchFieldException e){
                    Log.w("setNumberPickerTextColor", e);
                }
                catch(IllegalAccessException e){
                    Log.w("setNumberPickerTextColor", e);
                }
                catch(IllegalArgumentException e){
                    Log.w("setNumberPickerTextColor", e);
                }
            }
        }
        return false;
    }

}