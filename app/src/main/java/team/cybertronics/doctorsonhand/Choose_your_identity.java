package team.cybertronics.doctorsonhand;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class Choose_your_identity extends AppCompatActivity {

    ImageView care_seeker, care_provider;

    Button careprovider_registerbtn, careprovider_loginbtn, careseeker_registerbtn, careseeker_loginbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_your_identity);
        care_seeker = findViewById(R.id.care_seeker);
        care_provider = findViewById(R.id.care_provider);
        careprovider_registerbtn = findViewById(R.id.careprovider_registerbtn);
        careprovider_loginbtn = findViewById(R.id.careprovider_loginbtn);
        careseeker_loginbtn = findViewById(R.id.careseeker_loginbtn);
        careseeker_registerbtn = findViewById(R.id.careseeker_registerbtn);


        care_seeker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                careseeker_registerbtn.setVisibility(View.VISIBLE);
                careseeker_loginbtn.setVisibility(View.VISIBLE);
//                Intent intent = new Intent (Choose_your_identity.this, register.class);
//                startActivity(intent);
//                finish();
            }
        });

        care_provider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                careprovider_registerbtn.setVisibility(View.VISIBLE);
                careprovider_loginbtn.setVisibility(View.VISIBLE);
//                Intent intent = new Intent (Choose_your_identity.this, care_provider_register.class);
//                startActivity(intent);
////                finish();
            }
        });
        careseeker_registerbtn.setVisibility(View.GONE);
        careseeker_loginbtn.setVisibility(View.GONE);
        careprovider_registerbtn.setVisibility(View.GONE);
        careprovider_loginbtn.setVisibility(View.GONE);

        careprovider_registerbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent (Choose_your_identity.this, care_provider_register.class);
                startActivity(intent);
            }
        });

        careprovider_loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent (Choose_your_identity.this, login.class);
                startActivity(intent);
            }
        });

        careseeker_registerbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent (Choose_your_identity.this, register.class);
                startActivity(intent);
            }
        });

        careseeker_loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent (Choose_your_identity.this, login.class);
                startActivity(intent);
            }
        });
   }
}