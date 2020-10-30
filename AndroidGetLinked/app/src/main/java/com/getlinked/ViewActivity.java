package com.getlinked;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ViewActivity extends AppCompatActivity {
    String pEmail = "", category = "", pContact = "";
    int pId = 0;
    ImageView imageView;
    List<Reviews> reviews = null;
    RatingBar ratingBar;
    float tot = 0f;
    LinearLayout linearLayout;
    EditText reviewcomment;
    Button addreviewbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);
        ratingBar = findViewById(R.id.ratingBar);
        TextView name, categoryName, description;
        reviewcomment = findViewById(R.id.reviewcomment);
        Button hireButton;
        pEmail = getIntent().getStringExtra("professionalEmail");
        linearLayout = findViewById(R.id.usercomments);
        imageView = findViewById(R.id.image);
        name = findViewById(R.id.name);
        categoryName = findViewById(R.id.categoryName);
        description = findViewById(R.id.description);
        hireButton = findViewById(R.id.hireButton);
        addreviewbtn = findViewById(R.id.addreviewbtn);

        //add a new review comment
        addreviewbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!reviewcomment.getText().toString().trim().isEmpty())    {
                    //Call API to add rating and comment
                    SetReviewForProfessional(
                            pEmail,
                            ratingBar.getRating(),
                            reviewcomment.getText().toString().trim(),
                            SigninActivity.loggedinusername);
                }   else    {
                    Toast.makeText(getApplicationContext(), "Please enter a comment", Toast.LENGTH_SHORT).show();
                }
            }
        });

        List<Professionals> professionals  = MainActivity.GetProfessionalDetails();
        for(Professionals p : professionals)   {
            if(p.getEmail().equals(pEmail)) {
                //set textview values
                name.setText(p.getFirstname() + " " + p.getLastname());
                categoryName.setText(p.getCategoryname());
                description.setText(p.getHousenumber() + ", " + p.getStreetname() + ", " + p.getCity() + ", " + p.getCounty());
                pId = p.getId();
                category = p.getCategoryname();
                pContact = p.getPhonenumber();
                GetImageBitmap(p.getEmail());
                GetReviewsForProfessional(pEmail);
                break;
            }
        }

        hireButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (pEmail == "") {
                    Toast.makeText(getApplicationContext(), "Some error occurred", Toast.LENGTH_SHORT).show();
                } else {
                    Intent intent = new Intent(getApplicationContext(), HireActivity.class);
                    intent.putExtra("pEmail", pEmail);
                    intent.putExtra("pId", pId);
                    intent.putExtra("category", category);
                    intent.putExtra("pContact", pContact);
                    startActivity(intent);
                }
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }

    public void GetImageBitmap(String professionalEmail){
        try {
            new WebClient().getService().Getimage(professionalEmail).enqueue(new Callback<JsonObject>() {
                @Override
                public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                    if (!response.isSuccessful()) {
                        Toast.makeText(getApplicationContext(), response.code(), Toast.LENGTH_LONG).show();
                        return;
                    }
                    try {
                        JSONObject mainObject = new JSONObject(response.body().toString());
                        String imageEncodedString = mainObject.getString("value");
                        byte[] decodedString = Base64.decode(imageEncodedString, Base64.DEFAULT);
                        Bitmap bmp = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                        imageView.setImageBitmap(bmp);
                    } catch (JSONException e) {
                        Toast.makeText(getApplicationContext(), "Some error occured", Toast.LENGTH_LONG ).show();
                    }
                }

                @Override
                public void onFailure(Call<JsonObject> call, Throwable t) {
                    Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG).show();
                }
            });
        } catch (Exception e)   {
            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    public void GetReviewsForProfessional(String professionalEmail) {
        new WebClient().getService().GetReviews(professionalEmail).enqueue(new Callback<List<Reviews>>() {
            @Override
            public void onResponse(Call<List<Reviews>> call, Response<List<Reviews>> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(getApplicationContext(), response.code(), Toast.LENGTH_LONG).show();
                    return;
                }
                reviews = response.body();

                int c = 0;
                if(reviews.size() > 0)    {
                    for(Reviews r : reviews)    {
                        tot += r.getRating();
                        TextView textView = new TextView(getApplicationContext());
                        textView.setText(r.getComment());
                        textView.setTextSize(20);
                        linearLayout.addView(textView);
                        c++;
                    }

                    tot = tot / c;
                }

                ratingBar.setRating(tot);
            }

            @Override
            public void onFailure(Call<List<Reviews>> call, Throwable t) {
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    public void SetReviewForProfessional(String professionalEmail, float rating, String comment, String username)  {
        new WebClient().getService().SetReviews(professionalEmail, rating, comment, username).enqueue(new Callback<Integer>() {
            @Override
            public void onResponse(Call<Integer> call, Response<Integer> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(getApplicationContext(), response.code(), Toast.LENGTH_LONG).show();
                    return;
                }
                if(response.body() == 1)    {
                    TextView view = new TextView(getApplicationContext());
                    view.setTextSize(20);
                    view.setText(comment);
                    view.setPadding(5, 5, 5, 5);
                    linearLayout.addView(view);
                    reviewcomment.setText("");

                }   else    {
                    Toast.makeText(getApplicationContext(), "Something went wrong", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Integer> call, Throwable t) {
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}