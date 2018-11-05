package com.example.bruno.cuadernodebitacora;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bruno.cuadernodebitacora.pojos.Result;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.List;

import com.example.bruno.cuadernodebitacora.pojos.ApiResponse;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String API_BASE_URL = "https://api.nytimes.com/svc/books/v3/";
    public static final String LOG_TAG = "BML";
    private FirebaseAuth mFirebaseAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;

    // btb Firebase database variables
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mMessagesDatabaseReference;

    private static final int RC_SIGN_IN = 2018;

    private TextView tvRespuesta;

    private BookRESTAPIService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mMessagesDatabaseReference = mFirebaseDatabase.getReference().child("booksData");
        findViewById(R.id.logoutButton).setOnClickListener(this);
        this.userAuthentication();

    }

    public void userAuthentication() {
        //Firebase
        mFirebaseAuth = FirebaseAuth.getInstance();
        mAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // user is signed in
                } else {
                    // user is signed out
                    /*startActivityForResult(
                            AuthUI.getInstance().
                                    createSignInIntentBuilder().
                                    setAvailableProviders(Arrays.asList(
                                            new AuthUI.IdpConfig.GoogleBuilder().build(),
                                            new AuthUI.IdpConfig.EmailBuilder().build()
                                    )).
                                    setIsSmartLockEnabled(!BuildConfig.DEBUG , true ).
                                    build(),
                            RC_SIGN_IN);*/
                    startActivityForResult(
                            new Intent(MainActivity.this, AuthenticationUser.class),
                            RC_SIGN_IN
                    );
                }
            }
        };
    }

    @Override
    protected void onPause() {
        super.onPause();
        mFirebaseAuth.removeAuthStateListener(mAuthStateListener);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mFirebaseAuth.addAuthStateListener(mAuthStateListener);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            if (resultCode == RESULT_OK) {
                Log.i(LOG_TAG, "onActivityResult " + getString(R.string.sign_in));
            } else if (resultCode == RESULT_CANCELED) {
                Log.i(LOG_TAG, "onActivityResult " + getString(R.string.signed_cancelled));
                finish();
            }
        }
    }

    public void getResource(View v) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(API_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        apiService = retrofit.create(BookRESTAPIService.class);
        obtenerLibros();
    }

    public void obtenerLibros() {

        Call<ApiResponse> call_async = apiService.getCountryByName();
        List<Result> bookList;

        // Asíncrona
        call_async.enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                ApiResponse apiResponse = response.body();
                if (null != apiResponse) {
                    List<Result> books = apiResponse.getResults();
                    Log.i(LOG_TAG, "obtenerLibros => respuesta= " + books.toString());
                    MainActivity.this.updateFirebaseDB(books);
                } else {
                    Log.i(LOG_TAG, "No se ha conseguido descargar la información");
                }
            }

            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {
                Toast.makeText(
                        getApplicationContext(),
                        "ERROR: " + t.getMessage(),
                        Toast.LENGTH_LONG
                ).show();
                Log.e(LOG_TAG, t.getMessage());
            }
        });

    }

    public void updateFirebaseDB(List<Result> books) {
        mMessagesDatabaseReference.removeValue();
        for (int i = 0; i < books.size(); i++) {
            mMessagesDatabaseReference.push().setValue(new Book(books.get(i).getTitle(), books.get(i).getAuthor()));
        }
    }

    @Override
    public void onClick(View v) {
        mFirebaseAuth.signOut();
        Log.i(LOG_TAG, getString(R.string.sign_out));
    }
}
