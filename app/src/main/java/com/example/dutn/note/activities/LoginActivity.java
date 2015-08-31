package com.example.dutn.note.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.example.dutn.note.R;

/**
 * Created by trandu on 21/08/2015.
 */
public class LoginActivity extends Activity {

    public LoginActivity() {
    }

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_login);
        startActivity(new Intent(LoginActivity.this, MainActivity.class));
        this.finish();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
