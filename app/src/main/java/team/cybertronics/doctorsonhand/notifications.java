package team.cybertronics.doctorsonhand;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.menu.MenuAdapter;
import androidx.appcompat.view.menu.MenuView;
import androidx.core.view.MenuItemCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.SearchView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class notifications extends AppCompatActivity {

    Adapter_Doctors adapter_doctors;
    ArrayList<Model_Doctos> model_doctos;
    FirebaseFirestore db;
    FirebaseAuth firebaseAuth;

    ProgressDialog progressBar;
    Menu menu;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notifications);
        RecyclerView recyclerView = findViewById(R.id.doctors_recycle_view);
        model_doctos = new ArrayList<Model_Doctos>();
        adapter_doctors = new Adapter_Doctors(getApplicationContext(), model_doctos);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.setAdapter(adapter_doctors);
        progressBar = new ProgressDialog(this);
        db = FirebaseFirestore.getInstance();
        Onload();
    }

    public void Onload() {
        model_doctos.clear();
        progressBar.show();
        db.collection("Doctors")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {

                    @Override
                    public void onEvent(@Nullable QuerySnapshot value,
                                        @Nullable FirebaseFirestoreException e) {
                        if (e != null) {
                            //Log.w(TAG, "Listen failed.", e);
                            return;
                        }
                        if (value != null) {


                            for (QueryDocumentSnapshot doc : value) {

                                Model_Doctos model_doctos1 = doc.toObject(Model_Doctos.class);
                                model_doctos.add(model_doctos1);
                            }

                            // circularProgressIndicator.setVisibility(View.GONE);

                            adapter_doctors.notifyDataSetChanged();
                            progressBar.dismiss();
                        } else {
                            progressBar.dismiss();
                            //circularProgressIndicator.setVisibility(View.GONE);
                            Toast.makeText(getApplicationContext(), "No data found in Database", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}
