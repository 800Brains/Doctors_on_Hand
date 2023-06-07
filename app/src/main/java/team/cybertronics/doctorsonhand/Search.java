package team.cybertronics.doctorsonhand;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.MenuItemCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import androidx.appcompat.widget.SearchView;
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
import java.util.List;

public class Search extends AppCompatActivity {
    Adapter_Doctors adapter_doctors;
    ArrayList<Model_Doctos> model_doctos;
    FirebaseFirestore db;
    FirebaseAuth firebaseAuth;


    RecyclerView recycleView;
    Adapter_Doctors Adapter_Doctors;
    Model_Doctos model_doctos1;
    List<Model_Doctos> userList;


    ProgressDialog progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        RecyclerView recyclerView = findViewById(R.id.doctors_recycle_view1);
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
//@Override
//    public boolean onCreateOptionsMenu(Menu menu){
//
//        MenuInflater inflater = getMenuInflater();
//        inflater.inflate(R.menu.menu_main,menu);
//        return true;
//    }
    @Override
    public boolean  onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
    //search view
    MenuItem item = menu.findItem(R.id.action_search);

    SearchView searchView = (SearchView) MenuItemCompat.getActionView(item);

    //search view listener
    searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
        @Override
        public boolean onQueryTextSubmit(String s) {
            //called when user preses search button on the key board
            //if search is not empty then search
            if (!TextUtils.isEmpty(s.trim())) {
                //search text contains text, search it
                Search2(s);
            } else {
                //search text empty, get all users
                //getAllUsers();
            }
            return false;
        }

        @Override
        public boolean onQueryTextChange(String s) {
            // called when user preses any simple key
            if (!TextUtils.isEmpty(s.trim())) {
                //search text contains text, search it
                searchUsers(s);
            } else {
                return false;
            }
            //fragment_doctors.super.onCreateOptionsMenu(menu, inflater);
            return false;
        }

        public void Search2(String query)
        {
            db.collection("Doctors")
                    .whereEqualTo("specialization",query)
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                   // Log.d(TAG, document.getId() + " => " + document.getData());
                                    Toast.makeText(Search.this, document.getData().toString(), Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Toast.makeText(Search.this, "Empty fields", Toast.LENGTH_SHORT).show();
                            }
                        }
       });
        }
        public void searchUsers(String query) {
            //get current user
            FirebaseUser fuser = FirebaseAuth.getInstance().getCurrentUser();
            // get path of data called doctors
            DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Doctors");
            // get all data from the path
            ref.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for (DataSnapshot ds : snapshot.getChildren()) {
                        Model_Doctos model_doctos1 = ds.getValue(Model_Doctos.class);

                        //get all searched users except the one signed im
                        if (model_doctos1.getUid().equals(fuser.getUid())) {
                            userList.clear();
                            if (model_doctos1.getSpecialization().toLowerCase().contains(query.toLowerCase())) {
                                userList.contains(model_doctos1.getSpecialization());
                            } userList.add(model_doctos1);
                        }

                    }
                    //adapter
                    Adapter_Doctors = new Adapter_Doctors((Context) getApplicationContext(), (ArrayList<Model_Doctos>) userList);
                    Adapter_Doctors.notifyDataSetChanged();
                    //set adapter to recycle view
                    recycleView.setAdapter(Adapter_Doctors);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }
    });

        return   super.onCreateOptionsMenu(menu);
    }

   @Override
   public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId()) {
            case R.id.action_search:
                Toast.makeText(getApplicationContext(), "Searching for doctors", Toast.LENGTH_SHORT).show();
                return true;


        } return (super.onOptionsItemSelected(item));

    }


}