package team.cybertronics.doctorsonhand;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;


public class profile extends AppCompatActivity {
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        get_profile();
    }

    public void get_profile(){

        LinearLayout lLayout = (LinearLayout) findViewById(R.id.linearlayout2); // Root ViewGroup in which you want to add textviews

        DocumentReference docRef = db.collection("Profile").document("ov192UBsNiWeLxAiL7M4");
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
//                        Log.d(TAG, "DocumentSnapshot data: " + document.getData());
                        Toast.makeText(profile.this,  document.getString("email"),
                                Toast.LENGTH_LONG).show();
                        for (int i = 0; i < 5; i++) {
                            TextView tv1 = new TextView(getApplicationContext()); // Prepare textview object programmatically
                            tv1.setText(document.getString("first_name") + i);
                            tv1.setId(i + 5);
                            lLayout.addView(tv1); // Add to your ViewGroup using this method
                            TextView tv2 = new TextView(getApplicationContext()); // Prepare textview object programmatically
                            tv2.setText(document.getString("last_name") + i);
                            tv2.setId(i + 5);
                            lLayout.addView(tv2); // Add to your ViewGroup using this method
                            TextView tv3 = new TextView(getApplicationContext()); // Prepare textview object programmatically
                            tv3.setText(document.getString("email") + i);
                            tv3.setId(i + 5);
                            lLayout.addView(tv3); // Add to your ViewGroup using this method
                            TextView tv4 = new TextView(getApplicationContext()); // Prepare textview object programmatically
                            tv4.setText(document.getString("phone_number") + i);
                            tv4.setId(i + 5);
                            lLayout.addView(tv4); // Add to your ViewGroup using this method
                            TextView tv5 = new TextView(getApplicationContext()); // Prepare textview object programmatically
                            tv5.setText(document.getString("gender") + i);
                            tv5.setId(i + 5);
                            lLayout.addView(tv5); // Add to your ViewGroup using this method
                            TextView tv6 = new TextView(getApplicationContext()); // Prepare textview object programmatically
                            tv6.setText(document.getString("date_of_birth") + i);
                            tv6.setId(i + 5);
                            lLayout.addView(tv6); // Add to your ViewGroup using this method
                            TextView tv7 = new TextView(getApplicationContext()); // Prepare textview object programmatically
                            tv7.setText(document.getString("user_id") + i);
                            tv7.setId(i + 5);
                            lLayout.addView(tv7); // Add to your ViewGroup using this method
                            TextView tv8 = new TextView(getApplicationContext()); // Prepare textview object programmatically
                            tv8.setText(document.getString("date joined") + i);
                            tv8.setId(i + 5);
                            lLayout.addView(tv8); // Add to your ViewGroup using this method
                        }

                    } else {
//                        Log.d(TAG, "No such document");
                    }
                } else {
//                    Log.d(TAG, "get failed with ", task.getException());
                }
            }
        });
    }
}