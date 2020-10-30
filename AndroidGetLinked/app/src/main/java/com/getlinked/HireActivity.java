package com.getlinked;

import androidx.appcompat.app.AppCompatActivity;
import android.text.format.DateFormat;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HireActivity extends AppCompatActivity {
    EditText address, contact, card_number, cvv;
    DatePicker hireDate, cardexpiry;
    TimePicker timepickerhiredate;
    int pid = 0;
    String pContact = "";
    String category= "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hire);
        Button confirmandhire = findViewById(R.id.confirmandhire);
        String email = getIntent().getStringExtra("pEmail");
        pid = getIntent().getIntExtra("pId", -1);
        pContact = getIntent().getStringExtra("pContact");
         category = getIntent().getStringExtra("category");


//        List<Professionals> professionals = MainActivity.GetProfessionalDetails();
        address = findViewById(R.id.address);
        contact = findViewById(R.id.contact);
        card_number = findViewById(R.id.card_number);
        cvv = findViewById(R.id.cvv);
        hireDate = findViewById(R.id.hireDate);
        cardexpiry = findViewById(R.id.cardexpiry);
        timepickerhiredate = findViewById(R.id.timepickerhiredate);

        confirmandhire.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String addr = address.getText().toString();
                String userContact = contact.getText().toString();
                String cardnumber = card_number.getText().toString();
                String cvv_card = cvv.getText().toString();

                int day = hireDate.getDayOfMonth();
                int month = hireDate.getMonth() + 1;
                int year = hireDate.getYear();
                //creating hiredate includes time
                String hiredate = year + "-" + month + "-" + day + "T " + timepickerhiredate.getCurrentHour() + ":" + timepickerhiredate.getCurrentMinute();
                //cardexpirydate
                String cardexpirydate = cardexpiry.getYear() + "-" + (cardexpiry.getMonth() + 1) + "-" + cardexpiry.getDayOfMonth() + " 00:00:00";

                //get current year, day, month, hour, min etc
                int currentYear = Integer.parseInt((String) DateFormat.format("yyyy", Calendar.getInstance().getTime()));
                int currentDay = Integer.parseInt((String) DateFormat.format("dd", Calendar.getInstance().getTime()));
                int monthNumber=  Integer.parseInt((String) DateFormat.format("MM", Calendar.getInstance().getTime()));
                int currentHour = Integer.parseInt((String)DateFormat.format("HH", Calendar.getInstance().getTime()));
                int currentMin = Integer.parseInt(((String)DateFormat.format("mm", Calendar.getInstance().getTime())));
                SimpleDateFormat myFormat = new SimpleDateFormat("dd MM yyyy");

                //hiredate in dd mm yyyy format- hasnt yet converted
                String inputString1 = day + " " + month + " " + year;
                //current date in dd mm yyyy format- hasnt yet converted
                String inputString2 = currentDay + " " + monthNumber + " " + currentYear;
                //card expiry date is also converted to this format- hasnt yet converted
                String cardExpiryDateCheck = cardexpiry.getDayOfMonth() + " " + (cardexpiry.getMonth() + 1) + " " + cardexpiry.getYear();
                Date date1, date2, date3cardExpiry, todayWithTimeForTestingHourDifference, todayWithTime;
                long diff1 = 0, daysDiff1 = 0, diffHours = 0;
                long diff2 = 0, daysDiff2 = 0;
                try {
                    date1 = myFormat.parse(inputString1);   //hire date
                    date2 = myFormat.parse(inputString2);   //current date
                    date3cardExpiry = myFormat.parse(cardExpiryDateCheck);  //card expiry date

                    //if hiredate is today, then time should be atleast 1 hour ahead
                    SimpleDateFormat format1 = new SimpleDateFormat("dd MM yyyy HH:mm:ss");

                    //this is hire date in dd MM yyyy HH:mm:ss format
                    todayWithTimeForTestingHourDifference = format1.parse(inputString1 + " " + timepickerhiredate.getCurrentHour() + ":" + timepickerhiredate.getCurrentMinute() + ":00");
                    todayWithTime = format1.parse(inputString2 + " " + currentHour + ":" + currentMin + ":00");
                    diffHours = (todayWithTimeForTestingHourDifference.getTime() - todayWithTime.getTime()) / (60 * 60 * 1000) % 24;

                    //checking if hire date is in past- get diff1 in milliseconds
                    diff1 = date1.getTime() - date2.getTime() ;
                    //convert diff1 to number of days.
                    daysDiff1 = TimeUnit.DAYS.convert(diff1, TimeUnit.MILLISECONDS);

                    //checking if card expiry date is in past
                    diff2 = date3cardExpiry.getTime() - date2.getTime();
                    daysDiff2 = TimeUnit.DAYS.convert(diff2, TimeUnit.MILLISECONDS);

                } catch (ParseException e) {
                    e.printStackTrace();
                }

                if(addr.trim().isEmpty() || userContact.trim().isEmpty() || cardnumber.trim().isEmpty()
                || cvv_card.trim().isEmpty())   {
                    Toast.makeText(getApplicationContext(), "Please enter valid data for all fields", Toast.LENGTH_SHORT).show();
                }  else if(daysDiff1 < 0 || daysDiff2 < 0) {
                    Toast.makeText(getApplicationContext(), "Date cant be in past", Toast.LENGTH_SHORT).show();
                }   else if((daysDiff1 == 0) && (diffHours <= 0))   {
                    Toast.makeText(getApplicationContext(), "Please select a different time, at least one hour ahead of now (" + diffHours + ")", Toast.LENGTH_LONG).show();
                } else if(daysDiff1 > 60)   {
                    Toast.makeText(getApplicationContext(), "Hiredate must be within 60 days from today", Toast.LENGTH_LONG).show();
                }
                        else {
                            //send request to web api
                            new WebClient().getService().hireApi(cardexpirydate, cardnumber, category, cvv_card, hiredate,
                                    addr, SigninActivity.loggedinusername, pid + "", pContact, userContact).enqueue(new Callback<Integer>() {
                                @Override
                                public void onResponse(Call<Integer> call, Response<Integer> response) {
                                    if (!response.isSuccessful()) {
                                        Toast.makeText(getApplicationContext(), "error", Toast.LENGTH_LONG).show();
                                    }
                                    int success = response.body();
                                    if (success == 1) {
                                        Toast.makeText(getApplicationContext(), "Successfully Hired", Toast.LENGTH_LONG).show();
                                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                        startActivity(intent);
                                    } else {
                                        Toast.makeText(getApplicationContext(), "Some error occurred", Toast.LENGTH_LONG).show();
                                    }
                                }

                                @Override
                                public void onFailure(Call<Integer> call, Throwable t) {
                                    Toast.makeText(getApplicationContext(), "Some error occurred", Toast.LENGTH_LONG).show();
                                }
                            });
                        }
                    }
        });
    }
}