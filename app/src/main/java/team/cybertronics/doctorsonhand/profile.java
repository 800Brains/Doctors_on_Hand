package team.cybertronics.doctorsonhand;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;


public class profile extends AppCompatActivity {
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    ImageView add_photo;
    TextView first_name, last_name, phone_number, email, date_of_birth, gender;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        add_photo = findViewById(R.id.add_photo);
        first_name = findViewById(R.id.pfirst_name);
        last_name = findViewById(R.id.plast_name);
        phone_number = findViewById(R.id.pphone_number);
        email = findViewById(R.id.pemail);
        date_of_birth = findViewById(R.id.pdate_of_birth);
        gender = findViewById(R.id.pgender);
        mAuth = FirebaseAuth.getInstance();

        get_profile();
    }

    public void get_profile(){

        RelativeLayout relativeLayout = (RelativeLayout) findViewById(R.id.relativelayout); // Root ViewGroup in which you want to add textviews

        db.collection("Profile").whereEqualTo("email", mAuth.getCurrentUser().getEmail())
        .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
//                            first_name = new TextView(getApplicationContext()); // Prepare textview object programmatically
                            first_name.setText(document.getString("first_name"));
//                            relativeLayout.addView(first_name); // Add to your ViewGroup using this method
//                            last_name = new TextView(getApplicationContext()); // Prepare textview object programmatically
                            last_name.setText(document.getString("last_name"));
//                            relativeLayout.addView(last_name); // Add to your ViewGroup using this method
//                            email = new TextView(getApplicationContext()); // Prepare textview object programmatically
                            email.setText(document.getString("email"));
//                            relativeLayout.addView(email); // Add to your ViewGroup using this method
//                            phone_number = new TextView(getApplicationContext()); // Prepare textview object programmatically
                            phone_number.setText(document.getString("phone_number"));
//                            relativeLayout.addView(phone_number); // Add to your ViewGroup using this method
//                            gender = new TextView(getApplicationContext()); // Prepare textview object programmatically
                            gender.setText(document.getString("gender"));
//                            relativeLayout.addView(gender); // Add to your ViewGroup using this method
//                            date_of_birth = new TextView(getApplicationContext()); // Prepare textview object programmatically
                            date_of_birth.setText(document.getString("date_of_birth"));
//                            relativeLayout.addView(date_of_birth); // Add to your ViewGroup using this method
//                            TextView tv7 = new TextView(getApplicationContext()); // Prepare textview object programmatically
//                            tv7.setText(document.getString("user_id") + i);
//                            tv7.setId(i + 5);
//                            lLayout.addView(tv7); // Add to your ViewGroup using this method
//                            TextView tv8 = new TextView(getApplicationContext()); // Prepare textview object programmatically
//                            tv8.setText(document.getString("date joined") + i);
//                            tv8.setId(i + 5);
//                            lLayout.addView(tv8); // Add to your ViewGroup using this method
//                        }

                    }
//                        else {
////                        Log.d(TAG, "No such document");
//                    }
                } else {
//                    Log.d(TAG, "get failed with ", task.getException());
                }
            }
        });
    }
}
