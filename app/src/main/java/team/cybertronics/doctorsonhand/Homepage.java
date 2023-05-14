package team.cybertronics.doctorsonhand;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Homepage extends AppCompatActivity {

    ImageView setting,chat,home,profile,findhospitals,note;
    TextView fullname,header_name;

    private FirebaseAuth mAuth;
    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);
        setting = findViewById(R.id.settings);
        chat = findViewById(R.id.chat);
        home = findViewById(R.id.home);
        profile = findViewById(R.id.profile);
        findhospitals = findViewById(R.id.findhospitals);
        note = findViewById(R.id.noti);
        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();


        user_details();

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

        note.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent (Homepage.this, notifications.class);
                startActivity(intent);
            }
        });

    }
    public void user_details()
    {
        db.collection("Profile")
                .whereEqualTo("email", mAuth.getCurrentUser().getEmail())
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                fullname = findViewById(R.id.user_name);
                                header_name = findViewById(R.id.header_name);
                                fullname.setText(document.getString("first_name")+" "+document.getString("last_name"));
                                header_name.setText("Hi "  + document.getString("last_name"));
                            }
                        } else {
                          //  Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });
    }
}
