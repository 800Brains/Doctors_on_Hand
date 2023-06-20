package team.cybertronics.doctorsonhand;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class Chat extends AppCompatActivity {

    TextView name, user_status;
    EditText message_me;
    ImageView send;
    FirebaseAuth firebaseAuth;

    FirebaseFirestore db;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    RecyclerView recyclerView;

    //for checking if user have seen the message or not
    ValueEventListener valueEventListener;
    DatabaseReference userRefForSeen;

    ArrayList<Model_chat> model_chats;

    ArrayList<Model_Doctos> userList;
    List<Model_chat> chatList;
    Adapter_chat adapter_chat;
    ProgressDialog progressBar;
    FirebaseUser user;

    String myUid;
    String hisUid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        name = findViewById(R.id.namec);
        recyclerView = findViewById(R.id.chat_recycle_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        user_status = findViewById(R.id.user_status);
        message_me = findViewById(R.id.message_me);
        message_me.setTextColor(Color.BLACK);
        send = findViewById(R.id.send_message);
        recyclerView.setAdapter(adapter_chat);
        model_chats = new ArrayList<Model_chat>();
        adapter_chat = new Adapter_chat(getApplicationContext(), model_chats);
        progressBar = new ProgressDialog(this);

        //layout (linear layout) for recycle view
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setStackFromEnd(true);

        //recycleview properties
////        recyclerView.setHasFixedSize(true);
//        recyclerView.setLayoutManager(linearLayoutManager);

        Intent intent = getIntent();
        hisUid = intent.getStringExtra("hisUid");


        firebaseAuth = FirebaseAuth.getInstance();

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Doctors");

        checkUserStatus();

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //get text from edit text
                String get_message = message_me.getText().toString().trim();
                //check if text feild is empty
                if(TextUtils.isEmpty(get_message)){
                    //text empty
                    Toast.makeText(Chat.this, "Cannot send the empty feild", Toast.LENGTH_SHORT).show();
                }else {

                    send_message(get_message);
                }
            }
        });
        read_messages();
        seen_messages();
    }

    private void seen_messages() {
        userRefForSeen = FirebaseDatabase.getInstance().getReference("Chat");
        valueEventListener = userRefForSeen.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    Model_chat chat = ds.getValue(Model_chat.class);
                    if (chat.getReciever().equals(myUid) && chat.getSender().equals(hisUid)) {
                        HashMap<String, Object> hasSeenHashMap = new HashMap<>();
                        hasSeenHashMap.put("seen", true);
                        ds.getRef().updateChildren(hasSeenHashMap);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void read_messages() {
        chatList = new ArrayList<>();
        DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference("Chat");
        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                chatList.clear();
                for(DataSnapshot ds: dataSnapshot.getChildren()){
                    Model_chat chat = ds.getValue(Model_chat.class);
                    if(chat.getReciever().equals(myUid) && chat.getSender().equals(hisUid) ||
                            chat.getReciever().equals(hisUid) && chat.getSender().equals(myUid)){
                        chatList.add(chat);
                    }

                    //adapter
                    adapter_chat = new Adapter_chat(Chat.this, chatList);
                    adapter_chat.notifyDataSetChanged();

                    //set adapter to recycle view
                    recyclerView.setAdapter(adapter_chat);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void checkUserStatus(){
        //get current user
        FirebaseUser user = firebaseAuth.getCurrentUser();
        if(user != null){
            //user is signed in stay here
            //set email of logged in user
            //n.profile.setText (user.getEmail())
            myUid = user.getUid(); //currently signed in user uid

        }
        else {
            startActivity(new Intent(this,MainActivity.class));
            finish();
        }
    }
    public void send_message(String message){
        DatabaseReference databaseReference1 = FirebaseDatabase.getInstance().getReference();

        String timeStamp = String.valueOf(System.currentTimeMillis());

        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("sender", myUid);
        hashMap.put("reciever", hisUid);
        hashMap.put("message", message);
        hashMap.put("isSeen", false);
        hashMap.put("time", timeStamp);
        databaseReference1.child("Chats").push().setValue(hashMap);

        //reset edittext after sending message
        message_me.setText("");

        

//        db.collection("Chat").add(hashMap).
//                addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
//            @Override
//            public void onSuccess(DocumentReference documentReference) {
//                Toast.makeText(Chat.this, "chat saved", Toast.LENGTH_SHORT).show();
//            }
//        }).addOnFailureListener(new OnFailureListener() {
//                    @Override
//                    public void onFailure(@NonNull Exception e) {
//                        Toast.makeText(Chat.this, "Error Occured", Toast.LENGTH_SHORT).show();
//                    }
//                });


    }

    @Override
    protected void onStart() {
        checkUserStatus();
        super.onStart();
    }

    @Override
    protected void onPause() {
        userRefForSeen.removeEventListener(valueEventListener);
        super.onPause();
    }
}