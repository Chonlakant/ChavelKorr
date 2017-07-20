package com.chavel.chavel.activity;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chavel.chavel.R;

public class SetPasswordActivity extends AppCompatActivity {

    TextView accout;
    LinearLayout login;
    LinearLayout sign_up;
    EditText ed_pass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_password);
        ed_pass = (EditText) findViewById(R.id.ed_pass);
        accout = (TextView) findViewById(R.id.accout);

        Drawable drawable = ed_pass.getBackground(); // get current EditText drawable
        drawable.setColorFilter(Color.GRAY, PorterDuff.Mode.SRC_ATOP); // change the drawable color

        if (Build.VERSION.SDK_INT > 16) {
            ed_pass.setBackground(drawable); // set the new drawable to EditText

        } else {
            ed_pass.setBackgroundDrawable(drawable);
        }


    }

    public static int getSum(int n) {
        int sum = 0;
        for (int i = 1; i <= n; i++) {
            sum += i;
            Log.e("sum", sum + "");
        }
        return sum;
    }
}
