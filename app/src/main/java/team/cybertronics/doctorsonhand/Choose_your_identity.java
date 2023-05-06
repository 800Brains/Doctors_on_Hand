package team.cybertronics.doctorsonhand;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class Choose_your_identity extends AppCompatActivity {

    ImageView care_seeker, care_provider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_your_identity);
        care_seeker = findViewById(R.id.care_seeker);
        care_provider = findViewById(R.id.care_provider);

        care_seeker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent (Choose_your_identity.this, register.class);
                startActivity(intent);
//                finish();
            }
        });

        care_provider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent (Choose_your_identity.this, care_provider_register.class);
                startActivity(intent);
//                finish();
            }
        });
    }

}