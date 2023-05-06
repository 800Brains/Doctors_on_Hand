package team.cybertronics.doctorsonhand;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class Splash extends AppCompatActivity {
    Handler handler;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        handler = new Handler();
        handler.postDelayed(new Runnable(){
            @Override
            public void run(){
                Intent intent = new Intent (Splash.this, Choose_your_identity.class);
               // Intent intent = new Intent (Splash.this, Choose_your_identity.class);
                startActivity(intent);
                finish();
            }
                            }, 3000);

    }
}