package com.getlinked;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyHires extends AppCompatActivity {
    LinearLayout pLinearlayout = null;
    ArrayList<String> hProfessionalNames,
//            hProfessionalAddress,
            hProfessionalCategory, hProfessionalContact, hHireAddress, hHireDate;

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_hires);
        pLinearlayout = findViewById(R.id.pLinearlayout);
        hProfessionalNames = getIntent().getStringArrayListExtra("hProfessionalNames");
        hHireAddress = getIntent().getStringArrayListExtra("hHireAddress");
        hHireDate = getIntent().getStringArrayListExtra("hHireDate");
//        hProfessionalAddress = getIntent().getStringArrayListExtra("hProfessionalAddress");
        hProfessionalCategory = getIntent().getStringArrayListExtra("hProfessionalCategory");
        hProfessionalContact = getIntent().getStringArrayListExtra("hProfessionalContact");

        for(int i=0; i<hProfessionalNames.size(); i++)  {
            LinearLayout ll = new LinearLayout(getApplicationContext());
            ll.setOrientation(LinearLayout.VERTICAL);

            ll.setPadding(10, 25, 10, 25);
            ll.setBackgroundColor(Color.parseColor("#bfd774"));
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            layoutParams.setMargins(0, 0, 0, 50);
            ll.setLayoutParams(layoutParams);

            TextView tv1 = new TextView(getApplicationContext());
            TextView tv2 = new TextView(getApplicationContext());
            TextView tv3 = new TextView(getApplicationContext());
            TextView tv4 = new TextView(getApplicationContext());
//            TextView tv5 = new TextView(getApplicationContext());
            TextView tv6 = new TextView(getApplicationContext());

            tv1.setTextSize(20);
            tv2.setTextSize(20);
            tv3.setTextSize(18);
            tv4.setTextSize(18);
//            tv5.setTextSize(18);
            tv6.setTextSize(18);

            tv1.setText("Name: " + hProfessionalNames.get(i));
            tv2.setText("Category: " + hProfessionalCategory.get(i));
            tv3.setText("Hire address: " + hHireAddress.get(i));
            tv4.setText("Hire date: " + hHireDate.get(i));
//            tv5.setText("Professional's address: " + hProfessionalAddress.get(i));
            tv6.setText("Contact: " + hProfessionalContact.get(i));
            ll.addView(tv1);
            ll.addView(tv2);
            ll.addView(tv3);
            ll.addView(tv4);
//            ll.addView(tv5);
            ll.addView(tv6);
            pLinearlayout.addView(ll);
        }

    }
}