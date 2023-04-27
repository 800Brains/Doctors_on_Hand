package team.cybertronics.doctorsonhand;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    Button btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


//        btn = findViewById(R.id.som);
        btn.setOnClickListener(v ->{
            Toast.makeText(getApplicationContext(), "Hey!!! SOMTO", Toast.LENGTH_LONG).show();
        });
    }
}