package team.cybertronics.doctorsonhand;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;


public class Homepage extends AppCompatActivity {

    ImageView appointment,chat,home,profile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);
        appointment = findViewById(R.id.appointments);
        chat = findViewById(R.id.chat);
        home = findViewById(R.id.home);
        profile = findViewById(R.id.profile);




        // profile button
        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent (Homepage.this, profile.class);
                startActivity(intent);
//                finish();
            }
        });
    }


    // Google maps function
//    public void check_for_permission(Boolean ACCESS_FINE_LOCATION, Boolean ACCESS_COARSE_LOCATION){
//        ActivityResultLauncher<String[]> locationPermissionRequest =
//                registerForActivityResult(new ActivityResultContracts
//                                .RequestMultiplePermissions(), result -> {
//                            Boolean fineLocationGranted = result.getOrDefault(
//                                    Manifest.permission.ACCESS_FINE_LOCATION, false);
//                            Boolean coarseLocationGranted = result.getOrDefault(
//                                    Manifest.permission.ACCESS_COARSE_LOCATION,false);
//                            if (fineLocationGranted != null && fineLocationGranted) {
//                                // Precise location access granted.
//                            } else if (coarseLocationGranted != null && coarseLocationGranted) {
//                                // Only approximate location access granted.
//                            } else {
//                                // No location access granted.
//                            }
//                        }
//                );
//
//// ...
//
//// Before you perform the actual permission request, check whether your app
//// already has the permissions, and whether your app needs to show a permission
//// rationale dialog. For more details, see Request permissions.
//        locationPermissionRequest.launch(new String[] {
//                Manifest.permission.ACCESS_FINE_LOCATION,
//                Manifest.permission.ACCESS_COARSE_LOCATION
//        });
//
//
//    }
}
