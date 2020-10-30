package com.getlinked;

import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyWorkActivity extends AppCompatActivity {

    LinearLayout pLinearlayout = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_work);

        pLinearlayout = findViewById(R.id.pLinearlayout);

        String professionalEmail = getIntent().getStringExtra("professionalEmail");
        String verified = getIntent().getStringExtra("verified");
        CallAPI(professionalEmail.split(":")[0]);
    }

    public void CallAPI(String professionalEmail)
    {
        new WebClient().getService().Getmyworks(professionalEmail).enqueue(new Callback<List<HireInfo>>() {
            @Override
            public void onResponse(Call<List<HireInfo>> call, Response<List<HireInfo>> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(getApplicationContext(), "Some error occurred", Toast.LENGTH_LONG).show();
                    return;
                }
                List<HireInfo> hireInfos = response.body();
                if(hireInfos.size() == 0)   {
                    TextView message = new TextView(getApplicationContext());
                    message.setText("No work to show");
                    message.setTextSize(25);
                    message.setTextColor(Color.WHITE);
                    pLinearlayout.addView(message);
                    return;
                }

                for(HireInfo h : hireInfos) {
                    if(h.getIsCompleted() == 0) {
                        LinearLayout ll = new LinearLayout(getApplicationContext());
                        ll.setOrientation(LinearLayout.VERTICAL);

                        ll.setPadding(10, 25, 10, 25);
                        ll.setBackgroundColor(Color.parseColor("#bfd774"));
                        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                        layoutParams.setMargins(0, 0, 0, 50);
                        ll.setLayoutParams(layoutParams);
                        ll.setOnLongClickListener(new View.OnLongClickListener() {
                            @Override
                            public boolean onLongClick(View v) {
                                AlertDialog alertDialog = new AlertDialog.Builder(MyWorkActivity.this)
                                        .setIcon(android.R.drawable.ic_dialog_alert)
                                        .setTitle("Mark task as done?")
                                        .setMessage("Click yes to mark this work done. If you mark this task done, then you wont be able to edit this again.")
                                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialogInterface, int i) {
                                                //yes- call api post to mark the work done for this professional
                                                setWorkDoneCallPostAPI(h.getId());
                                                //changing the background colour of the comleted work
                                                ll.setBackgroundColor(Color.parseColor("#ffcccb"));
                                                ll.setLongClickable(false);
                                            }
                                        })
                                        //negative button
                                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialogInterface, int i) {
                                                //no- nothing happens
                                            }
                                        })
                                        .show();
                                return false;
                            }
                        });
                        TextView tv1 = new TextView(getApplicationContext());
                        TextView tv2 = new TextView(getApplicationContext());
                        TextView tv3 = new TextView(getApplicationContext());

                        tv1.setTextSize(25);
                        tv2.setTextSize(25);
                        tv3.setTextSize(25);

                        tv1.setText("Client contact: " + h.getUserContact().trim());
                        tv2.setText("Appointment date: " + h.getHireDate().trim());
                        tv3.setText("Address: " + h.getHireaddress().trim());
                        ll.addView(tv1);
                        ll.addView(tv2);
                        ll.addView(tv3);
                        pLinearlayout.addView(ll);
                    }
//
                }
            }

            @Override
            public void onFailure(Call<List<HireInfo>> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Some error occurred", Toast.LENGTH_LONG).show();
            }
        });
    }

    public void setWorkDoneCallPostAPI(Long hireinfoId)    {
        new WebClient().getService().setWorkDone(hireinfoId).enqueue(new Callback<Integer>() {
            @Override
            public void onResponse(Call<Integer> call, Response<Integer> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(getApplicationContext(), "Some error occurred", Toast.LENGTH_LONG).show();
                    return;
                }
                int success = response.body();

            }

            @Override
            public void onFailure(Call<Integer> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Some error occurred", Toast.LENGTH_LONG).show();
            }
        });
    }
}