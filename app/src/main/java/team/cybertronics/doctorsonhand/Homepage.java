package team.cybertronics.doctorsonhand;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;


public class Homepage extends AppCompatActivity {

    ImageView appointment,chat,home,profile,findhospitals;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);
        appointment = findViewById(R.id.appointments);
        chat = findViewById(R.id.chat);
        home = findViewById(R.id.home);
        profile = findViewById(R.id.profile);
        findhospitals = findViewById(R.id.findhospitals);




        // profile button
        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent (Homepage.this, profile.class);
                startActivity(intent);
//                finish();
            }
        });

        findhospitals.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String x = null;
                String y = null;
                String uri = "geo:"+ x + "," + y +"?q=hospitals+near+me";
                startActivity(new Intent(android.content.Intent.ACTION_VIEW, Uri.parse(uri)));
            }
        });

    }

}
