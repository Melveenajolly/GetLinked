package com.getlinked;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.TypedArray;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Multipart;

public class RegisterProfessionalActivity extends AppCompatActivity implements
        AdapterView.OnItemSelectedListener {
    String secret_key = "AIzaSyBz5UcW0U8KGSa3tAa039I7vtCy_hld14Y";
    String[] categories = { "Salon", "Hair stylist", "Electrician", "Plumber", "Painter",
    "Fitness trainer", "Dog walker", "Carpenter"};
    EditText fname, lname, pnumber, email, pwrod, rpword, hnumber, street, city, county, postcode;
    Button openimage1, openimage2, reg;
    public static final int PICK_IMAGE = 1;
    public static final int PICK_CERT= 2;
    String PPfileextension = "", certfileextension = "";
    JSONObject mainObject = null;
    String encodedPP = "", encodedCert = "";
    int ok1 = 0, ok2 = 0, success1 = 0, success2 = 0;
    String categoryName="";
    int categoryId = 0;
    Spinner spinner;
    private FusedLocationProviderClient fusedLocationClient;
    String lat, lng;
    File file1, file2;
    String FilePathStr, FilePathStr1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_professional);

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(RegisterProfessionalActivity.this);

        fname = findViewById(R.id.firstname);
        lname = findViewById(R.id.lastname);
        pnumber = findViewById(R.id.Phone);
        email = findViewById(R.id.Email);
        pwrod = findViewById(R.id.Password);
        rpword = findViewById(R.id.RepeatPassword);
        hnumber = findViewById(R.id.HouseNumber);
        street = findViewById(R.id.Streetname);
        city = findViewById(R.id.City);
        county = findViewById(R.id.County);
        postcode = findViewById(R.id.Postcode);

        openimage1 = findViewById(R.id.openimage1);
        openimage2 = findViewById(R.id.openimage2);
        reg = findViewById(R.id.reg);

        spinner = findViewById(R.id.spinner);
        spinner.setOnItemSelectedListener(this);
        //Creating the ArrayAdapter instance having the country list
        ArrayAdapter aa = new ArrayAdapter(this,android.R.layout.simple_spinner_item, categories);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(aa);


        openimage1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                photoPickerIntent.setType("image/*");
                startActivityForResult(photoPickerIntent, PICK_IMAGE);
            }
        });

        openimage2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                photoPickerIntent.setType("image/*");
                startActivityForResult(photoPickerIntent, PICK_CERT);
            }
        });

        reg.setOnClickListener(new View.OnClickListener() {
            //we do two things- get location lat and lng from address, adn then call webapi tp register professional.
            //we call that api from inside the success of geocoding api call
            @Override
            public void onClick(View v) {
                if(ok1 == 1 && ok2 == 1) {
                    if(fname.getText().toString().trim().isEmpty() ||
                            lname.getText().toString().trim().isEmpty() ||
                            pnumber.getText().toString().trim().isEmpty() ||
                            email.getText().toString().trim().isEmpty() ||
                            pwrod.getText().toString().trim().isEmpty() ||
                            hnumber.getText().toString().trim().isEmpty() ||
                            street.getText().toString().trim().isEmpty() ||
                            city.getText().toString().trim().isEmpty() ||
                            county.getText().toString().trim().isEmpty() ||
                            postcode.getText().toString().trim().isEmpty())    {
                        if(pwrod.getText().toString().trim().equals(""))    {
                            Toast.makeText(getApplicationContext(), "Password cannot be empty/whitespace", Toast.LENGTH_SHORT).show();
                        }   else    {
                            Toast.makeText(getApplicationContext(), "Please check all your inputs and try again", Toast.LENGTH_SHORT).show();
                        }
                    }   else    {
                        if(rpword.getText().toString().equals(pwrod.getText().toString()))  {
                            if(pwrod.getText().toString().length() < 5 || pwrod.getText().toString().length() > 15) {
                                Toast.makeText(getApplicationContext(), "Password should be between 5 and 15 chars in length", Toast.LENGTH_SHORT).show();
                            }   else    {
                                if(email.getText().toString().trim().split("@").length != 2)    {
                                    Toast.makeText(getApplicationContext(), "Please check email", Toast.LENGTH_SHORT).show();
                                }   else    {
                                    reg.setText("Fetching location coordinates..");
                                    GetMyLocation(hnumber.getText().toString() + ", " + street.getText().toString() + ", " +
                                            city.getText().toString() + ", " + county.getText().toString() + ", " + postcode.getText().toString());
                                }
                            }
                        }   else    {
                            Toast.makeText(getApplicationContext(), "Password mismatch", Toast.LENGTH_SHORT).show();
                        }
                    }
                }   else {
                    Toast.makeText(getApplicationContext(), "Please upload profile pic and certificate image", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    public void GetMyLocation(String address) {
            new MapsWebClient().getService().GetMyLocationFromGoogle(address, secret_key).enqueue(new Callback<JsonObject>() {
                @Override
                public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                    if (!response.isSuccessful()) {
                        Toast.makeText(getApplicationContext(), response.code(), Toast.LENGTH_LONG).show();
                        reg.setText("COMPLETE REGISTRATION");
                    }   else    {
                        try {
                            JSONObject mainObject = new JSONObject(response.body().toString());
                            JSONObject y = mainObject.getJSONArray("results")
                                    .getJSONObject(0)
                                    .getJSONObject("geometry")
                                    .getJSONObject("location");
                            lat = y.getString("lat");
                            lng = y.getString("lng");
                        } catch (JSONException e) {
                            Toast.makeText(getApplicationContext(), "Some error occurred", Toast.LENGTH_LONG ).show();
                            reg.setText("COMPLETE REGISTRATION");
                        }
//                        Toast.makeText(getApplicationContext(), "Lat: " + lat + ", Lng: " + lng, Toast.LENGTH_LONG).show();
                        reg.setText("Registering.. please wait");
                        //register post
                        CallAPIAndRegisterProfessional(
                                FilePathStr, FilePathStr1,
                                fname.getText().toString().trim(), lname.getText().toString().trim(), pnumber.getText().toString().trim(),
                                email.getText().toString().trim(), pwrod.getText().toString().trim(),
                                hnumber.getText().toString().trim(), street.getText().toString().trim(),
                                city.getText().toString().trim(), county.getText().toString().trim(), postcode.getText().toString().trim()
                        );
                    }
                }

                @Override
                public void onFailure(Call<JsonObject> call, Throwable t) {
                    Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG).show();
                }
            });
    }

    //Performing action onItemSelected and onNothing selected
    @Override
    public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long id) {
        categoryName = categories[position];
        categoryId = position + 1;
    }
    @Override
    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub
    }

    //invoked when startActivityResult()
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE) {
            try {
                //get the location of teh current chosen image
                final Uri imageUri = data.getData();
                file1 = new File(imageUri.getPath());
                //get file name
                String[] filePath = {MediaStore.Images.Media.DATA};
                Cursor c = getContentResolver().query(imageUri, filePath,
                        null, null, null);
                c.moveToFirst();
                int columnIndex = c.getColumnIndex(filePath[0]);
                FilePathStr = c.getString(columnIndex);
                c.close();

                //find file extension
                FilePathStr = FilePathStr.substring(FilePathStr.lastIndexOf(".") + 1);
                if(FilePathStr.equals("jpeg") || FilePathStr.equals("jpg") || FilePathStr.equals("png"))  {
                    //get byte array if image
                    final InputStream imageStream = getContentResolver().openInputStream(imageUri);
                    final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();

                    //compress bitmap to bytearray output stream
                    selectedImage.compress(Bitmap.CompressFormat.JPEG,30,baos);

                    //convertthis byte array op stream to byte array
                    byte[] b = baos.toByteArray();
                    String encImage = Base64.encodeToString(b, Base64.DEFAULT);

                    //save base64 encoded string in encidedPP variable
                    encodedPP = encImage;
                    ok1 = 1;
                    openimage1.setText("Profile photo selected OK");
                }   else    {
                    Toast.makeText(getApplicationContext(), "File extension not valid", Toast.LENGTH_SHORT).show();
                    ok1 = 0;
                }
            }
            catch (Exception e) {

            }

        }
        else if(requestCode == PICK_CERT)   {
            try {
                final Uri imageUri = data.getData();
                file2 = new File(imageUri.getPath());
                String[] filePath = {MediaStore.Images.Media.DATA};
                Cursor c = getContentResolver().query(imageUri, filePath,
                        null, null, null);
                c.moveToFirst();
                int columnIndex = c.getColumnIndex(filePath[0]);
                FilePathStr1 = c.getString(columnIndex);
                c.close();

                FilePathStr1 = FilePathStr1.substring(FilePathStr1.lastIndexOf(".") + 1);
                if(FilePathStr1.equals("jpeg") || FilePathStr1.equals("jpg") || FilePathStr1.equals("png"))  {
                    final InputStream imageStream1 = getContentResolver().openInputStream(imageUri);
                    final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream1);
                    ByteArrayOutputStream baos1 = new ByteArrayOutputStream();
                    selectedImage.compress(Bitmap.CompressFormat.JPEG,30,baos1);
                    byte[] b = baos1.toByteArray();
                    String encImage = Base64.encodeToString(b, Base64.DEFAULT);
                    encodedCert = encImage;
                    ok2 = 1;
                    openimage2.setText("Certificate selected OK");
                }   else    {
                    Toast.makeText(getApplicationContext(), "File extension not valid", Toast.LENGTH_SHORT).show();
                    ok2 = 0;
                }
//
            }
            catch (Exception e) {

            }

        }
    }

    private void CallAPIAndRegisterProfessional(
            String ppfileextension, String certfileextension,
            String fname, String lname, String pnumber, String email, String pwrod,
            String hnumber, String street, String city, String county, String postcode
    ) {
        try {
            new WebClient().getService().register1(
                    ppfileextension, certfileextension,
                    fname, lname, Long.parseLong(pnumber), email, pwrod, categoryId,
                    hnumber, street,
                    city, county, postcode, categoryName, lat, lng
            ).enqueue(new Callback<Integer>() {
                @Override
                public void onResponse(Call<Integer> call, Response<Integer> response) {
                    if (!response.isSuccessful()) {
                        Toast.makeText(getApplicationContext(), "Some error occurred", Toast.LENGTH_LONG).show();
                        reg.setText("COMPLETE REGISTRATION");
                    } else {
                        success1 = response.body();
                        //professional is registerd now in db, but images are not yet uploaded, so doing them
                        UploadImages(email);
                    }
                }

                @Override
                public void onFailure(Call<Integer> call, Throwable t) {
                    Toast.makeText(getApplicationContext(), "Failed: Some error occurred", Toast.LENGTH_LONG).show();
                    reg.setText("COMPLETE REGISTRATION");
                }
            });
        } catch (Exception e)   {
            Toast.makeText(getApplicationContext(), "Please check your inputs", Toast.LENGTH_LONG).show();
            reg.setText("COMPLETE REGISTRATION");
        }
    }

    private void UploadImages(String email)
     {
        try {
            JSONObject encodedImages = new JSONObject();
            encodedImages.put("image1", encodedPP);
            encodedImages.put("image2", encodedCert);

            new WebClient().getService().register2(
                    encodedImages, email
            ).enqueue(new Callback<Integer>() {
                @Override
                public void onResponse(Call<Integer> call, Response<Integer> response) {
                    if (!response.isSuccessful()) {
                        Toast.makeText(getApplicationContext(), response.code(), Toast.LENGTH_LONG).show();
                        return;
                    }

                    success2 = response.body();

                    //success 1 is the result we got from registering professional
                    //success2 is the result we get now from uploading images
                    if(success1 == 1 && success2 == 1)    {
                        Toast.makeText(getApplicationContext(), "Registration successful", Toast.LENGTH_LONG).show();
                        startActivity(new Intent(RegisterProfessionalActivity.this, SigninActivity.class));
                        finish();   //to clear off this activity from activity stack- this might result in freeing up the memory
                    }   else    {
                        Toast.makeText(getApplicationContext(), "Registration not success", Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onFailure(Call<Integer> call, Throwable t) {
                    Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG).show();
                }
            });
        } catch (Exception e)   {
            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }
}