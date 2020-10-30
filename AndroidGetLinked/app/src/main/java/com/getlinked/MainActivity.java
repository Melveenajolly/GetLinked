package com.getlinked;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;

import java.io.Console;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private LocationManager lm;

    double lat = 0;
    double lng = 0;
    private FusedLocationProviderClient fusedLocationClient;
    //Array Lists for profeesionals, names, lats , lng, and emails
    static List<Professionals> professionals = null;
    static ArrayList<String> pNames = new ArrayList<>();
    static ArrayList<String> pLat = new ArrayList<>();
    static ArrayList<String> pLng = new ArrayList<>();
    static ArrayList<String> pEmail = new ArrayList<>();

    static ArrayList<String> hProfessionalNames = new ArrayList<>();
    static ArrayList<String> hHireAddress = new ArrayList<>();
    static ArrayList<String> hHireDate = new ArrayList<>();
    static ArrayList<String> hProfessionalCategory = new ArrayList<>();
    static ArrayList<String> hProfessionalContact = new ArrayList<>();
//    static ArrayList<String> hProfessionalAddress = new ArrayList<>();

    Button myhiresbtn;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(MainActivity.this);
        GetMyLocation();
//        lat=53.272477;
//        lng=-6.3682967;
        myhiresbtn = findViewById(R.id.viewmyhires);
    }

    @Override
    //when selecting any of the services
    public void onClick(View v) {
        //Check if user authenticated/signed in
        if (SigninActivity.authenticated == 0) {
            //taking to sign in activity
            Intent intentsignin = new Intent(MainActivity.this, SigninActivity.class);
            startActivity(intentsignin);
            finish();
        } else {
            if(v.getTag().toString().equals("myhires")) {
                //user clicked on myhires button
                myhiresbtn.setText("Loading..");
                CallAPIMyHires(SigninActivity.loggedinusername);
//                CallAPIMyHires("adiral@general.com");
            }   else    {
                String category_id = v.getTag().toString();
                if (pNames!=null)   {
                    //clearing old data
                    pNames.clear();
                    pLat.clear();
                    pLng.clear();
                    pEmail.clear();
                }   else    {
                    pNames = new ArrayList<String>();
                    pLat = new ArrayList<String>();
                    pLng = new ArrayList<String>();
                    pEmail = new ArrayList<String>();
                }
                //clearing professionals
                if(professionals != null)   {
                    professionals.clear();
                }
                //calling nearby professional API request
                callProffesionalAPI(category_id);
            }
        }
    }
    //tracking the current location of the user
    public void GetMyLocation() {
        //checking for permission
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, 101);
            return;
        }
        //retrieving the last known location using gps
        fusedLocationClient.getLastLocation()
                .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        // Got last known location. In some rare situations this can be null.
                        if (location != null) {
                            lat =  location.getLatitude();
                            lng = location.getLongitude();
                        }
                    }

                });
    }

    private void callProffesionalAPI(String category_id) {
        new WebClient().getService().getProfessionals(category_id, lat + "", lng + "").enqueue(new Callback<List<Professionals>>() {
            @Override
            public void onResponse(Call<List<Professionals>> call, Response<List<Professionals>> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(getApplicationContext(), "Some error occurred, please try again", Toast.LENGTH_LONG).show();
                    return;
                }
                Log.d("TAG", "onResponse: " + response.body());
                //collecting response body  to professionals
                professionals = response.body();


                int c = 0;
                if(professionals.size() > 0)    {
                    for(Professionals p : professionals)    {
                        //populating each list for map activity
                        pNames.add((p.getFirstname() + " " + p.getLastname()));
                        pLat.add(p.getLat());
                        pLng.add(p.getLng());
                        pEmail.add(p.getEmail());
                    }
                    //intent to maps activity
                    Intent intent = new Intent(MainActivity.this, MapsActivity.class);
                    intent.putExtra("lat", lat + "");
                    intent.putExtra("lng", lng + "");
                    intent.putExtra("category_id", category_id);
                    intent.putStringArrayListExtra("pNames", pNames);
                    intent.putStringArrayListExtra("pLat", pLat);
                    intent.putStringArrayListExtra("pLng", pLng);
                    intent.putStringArrayListExtra("pEmail", pEmail);
                    startActivity(intent);
                }
                else    {
                    //this is the case when no professional has registered for this category
                    pNames = null;
                    pLat = null;
                    pLng = null;
                    pEmail = null;

                    Toast.makeText(getApplicationContext(), "No professionals to show here", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Professionals>> call, Throwable t) {
//                textView.setText(t.getMessage());

                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    public static List<Professionals> GetProfessionalDetails()    {
        return professionals;
    }

    //My Hires api
    public void CallAPIMyHires(String username)
    {
        new WebClient().getService().GetMyHires(username.split(":")[0]).enqueue(new Callback<List<Professionals>>() {
            @Override
            public void onResponse(Call<List<Professionals>> call, Response<List<Professionals>> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(getApplicationContext(), "Some error occurred", Toast.LENGTH_LONG).show();
                }
                //clearing the old list
                hProfessionalNames.clear();
                hHireAddress.clear();
//                hProfessionalAddress.clear();
                hProfessionalCategory.clear();
                hHireDate.clear();
                hProfessionalContact.clear();

                myhiresbtn.setText("VIEW MY HIRES");
                //response body to professional model
                List<Professionals> professionals = response.body();
                //new intent to my hires
                Intent i1 = new Intent(MainActivity.this, MyHires.class);
                //iterating response professional list
                for(Professionals p : professionals)    {
                    //populating the list
                    hProfessionalNames.add(p.getFirstname() + " " + p.getLastname());
                    hHireAddress.add(p.getStreetname());
//                    hProfessionalAddress.add(p.getCity() + ", " + p.getCounty());
                    hProfessionalCategory.add(p.getCategoryname());
                    hHireDate.add(p.getPhotoName());    //the photoname field has been set as the hire date from server
                    hProfessionalContact.add(p.getPhonenumber());
                }

                //passing my hire data with intent to my hires
                i1.putStringArrayListExtra("hProfessionalNames", hProfessionalNames);
                i1.putStringArrayListExtra("hHireAddress", hHireAddress);   //new
//                i1.putStringArrayListExtra("hProfessionalAddress", hProfessionalAddress);
                i1.putStringArrayListExtra("hProfessionalCategory", hProfessionalCategory);
                i1.putStringArrayListExtra("hHireDate", hHireDate); //new
                i1.putStringArrayListExtra("hProfessionalContact", hProfessionalContact);
                startActivity(i1);
            }

            @Override
            public void onFailure(Call<List<Professionals>> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Some error occurred", Toast.LENGTH_LONG).show();
            }
        });
    }

}