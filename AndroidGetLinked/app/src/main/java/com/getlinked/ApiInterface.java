package com.getlinked;
import android.content.res.TypedArray;

import com.google.gson.JsonObject;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.jetbrains.annotations.PropertyKey;
import org.json.JSONObject;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;

import java.util.HashMap;
import java.util.List;

import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiInterface {

    //5 nearby professional request
    @GET("professionals/{category_id}")
    Call<List<Professionals>> getProfessionals(@Path("category_id") String category_id, @Query("lat1") String lat, @Query("lng1") String lon);

    //login request
    @GET("login2")
    Call<Integer> validateSignin(@Query("username") String username, @Query("password") String password);

    //my works request
    @GET("hireinfo/{professionalEmail}")
    Call<List<HireInfo>> Getmyworks(@Path("professionalEmail") String professionalEmail);

    //my hires request
    @GET("getmyhires")
    Call<List<Professionals>> GetMyHires(@Query("username") String username);

    //profile pic request
    @GET("getimage")
    Call<JsonObject> Getimage(@Query("professionalEmail") String professionalEmail);

    //complete work request
    @POST("setworkdone")
    Call<Integer> setWorkDone(@Query("hireinfoId") Long hireinfoId);

    //fetch reviews request
    @GET("getreview")
    Call<List<Reviews>> GetReviews(@Query("professionalEmail") String professionalEmail);

    //add review request
    @POST("setreview")
    Call<Integer> SetReviews(@Query("professionalEmail") String professionalEmail,
                                   @Query("rating") float rating,
                                   @Query("comment") String comment,
                             @Query("username") String username);
    //sign up request
    @POST("signup2")
    @FormUrlEncoded
    Call<Integer> signup(@Field("email") String email,
                         @Field("plainPassword") String plainPassword,
                         @Field("repeatPassword") String repeatPassword,
                         @Field("password") String password,
                         @Field("address") String address);


    //register new professional review
    @POST("professional")
    @FormUrlEncoded
    Call<Integer> register1(
                        @Field("photoExtension") String photoExtension,
                        @Field("certExtension") String certExtension,
                        @Field("firstname") String firstname,
                        @Field("lastname") String lastname,
                        @Field("phonenumber") Long phonenumber,
                        @Field("email") String email,
                        @Field("password") String password,
                        @Field("categoryid") int categoryid,
                        @Field("housenumber") String housenumber,
                        @Field("streetname") String streetname,
                        @Field("city") String city,
                        @Field("county") String county,
                        @Field("postcode") String postcode,
                        @Field("categoryname") String categoryname,
                        @Field("lat") String lat,
                        @Field("lng") String lng
    );

    //upload image and certificate request
    @POST("postImage2")
    Call<Integer> register2(
            @Body JSONObject images,
            @Query("professionalEmail") String professionalEmail
    );

    //add a new hireinfo request
    @POST("hireInfo")
    @FormUrlEncoded
    Call<Integer> hireApi(
              @Field("cardexpiry") String cardexpiry,
              @Field("cardnumber") String cardnumber,
              @Field("category") String category,
              @Field("cvv") String cvv,
              @Field("hireDate") String hiredate,
              @Field("hireaddress") String hireaddress,
              @Field("username") String useremail,
              @Field("professionalid") String professionalid,
              @Field("professionalContact") String professionalContact,
              @Field("userContact") String userContact
            );

}