package team.cybertronics.doctorsonhand;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class register extends AppCompatActivity {

    Button btn_register;
    EditText first_name, email, phone_number, date_of_birth, password, confirm_password, last_name;
    TextView account, sfname, semail, sphone_number, sdob, spassword, scpassword, slname;
    ImageButton google, facebook, twitter;
    Spinner gender;
    private FirebaseAuth mAuth;
    FirebaseFirestore db ;
    // ...

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        btn_register = findViewById(R.id.register);
        last_name = findViewById(R.id.rlastname);
        last_name.setTextColor(Color.BLACK);
        first_name = findViewById(R.id.rfullname);
        first_name.setTextColor(Color.BLACK);
        email = findViewById(R.id.remail);
        email.setTextColor(Color.BLACK);
        phone_number = findViewById(R.id.rphonenumber);
        phone_number.setTextColor(Color.BLACK);
        date_of_birth = findViewById(R.id.rdateofbirth);
        date_of_birth.setTextColor(Color.BLACK);
        password = findViewById(R.id.rpassword);
        password.setTextColor(Color.BLACK);
        confirm_password = findViewById(R.id.rconfirmpassword);
        confirm_password.setTextColor(Color.BLACK);
        account = findViewById(R.id.alreadyhaveanaccount);
        google = findViewById(R.id.google);
        facebook = findViewById(R.id.facebook);
        twitter = findViewById(R.id.twitter);
        sfname = findViewById(R.id.status_fullname);
        slname = findViewById(R.id.status_lastname);
        semail = findViewById(R.id.status_email);
        sphone_number = findViewById(R.id.status_phone_number);
        sdob = findViewById(R.id.status_dob);
        spassword = findViewById(R.id.status_password);
        scpassword = findViewById(R.id.status_cpassword);
        gender = findViewById(R.id.gender);


        mAuth = FirebaseAuth.getInstance();
        db= FirebaseFirestore.getInstance();


        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//                Intent intent = new Intent (register.this, Homepage.class);
//                startActivity(intent);
//                finish();
                String get_first_name = first_name.getText().toString();
                String get_last_name = last_name.getText().toString();
                String get_email = email.getText().toString();
                String get_phone_number = phone_number.getText().toString();
                String get_date_of_birth = date_of_birth.getText().toString();
                String get_password = password.getText().toString();
                String get_confirm_password = confirm_password.getText().toString();

                if (get_first_name.isEmpty()){
                    sfname.setText("The field is empty");
                    sfname.setTextColor(Color.RED);

                if(get_last_name.isEmpty()){
                    slname.setText("This feild is empty");
                    slname.setTextColor(Color.RED);
                }

                }if (get_email.isEmpty()){
                    semail.setText("The field is empty");
                    semail.setTextColor(Color.RED);
                }if (get_date_of_birth.isEmpty()){
                    sdob.setText("The field is empty");
                    sdob.setTextColor(Color.RED);
                }if (get_phone_number.isEmpty()){
                    sphone_number.setText("The field is empty");
                    sphone_number.setTextColor(Color.RED);
                }if (get_password.isEmpty()){
                    spassword.setText("The field is empty");
                    spassword.setTextColor(Color.RED);
                }if (get_confirm_password.isEmpty()){
                    scpassword.setText("The field is empty");
                    scpassword.setTextColor(Color.RED);
//                }
//                if (get_password != get_confirm_password){
//                    scpassword.setText("Error Incorrect password");
//                    scpassword.setTextColor(Color.RED);
                }else{
                    sfname.setVisibility(View.GONE);
                    slname.setVisibility(View.GONE);
                    semail.setVisibility(View.GONE);
                    sdob.setVisibility(View.GONE);
                    sphone_number.setVisibility(View.GONE);
                    spassword.setVisibility(View.GONE);
                    scpassword.setVisibility(View.GONE);


                    // CONNECTION TO THE CLOUD GOES HERE
                    mAuth.createUserWithEmailAndPassword(get_email, get_password)
                            .addOnCompleteListener(register.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(register.this, "Authentication successful.",
                                                Toast.LENGTH_SHORT).show();
                                        try {
                                            store_profile( get_first_name,  get_last_name,  get_email,  get_phone_number,  get_date_of_birth,  "gender" );

                                        }catch (Exception e)
                                        {
                                            Toast.makeText(register.this, e.getMessage(),
                                                    Toast.LENGTH_SHORT).show();
                                        }
                                                  } else {
                                        // If sign in fails, display a message to the user.
                                        Log.w(TAG, "createUserWithEmail:failure", task.getException());
                                        Toast.makeText(register.this,  task.getException().toString(),
                                                Toast.LENGTH_LONG).show();
                                        // updateUI(null);
                                    }
                                }
                            });


              }
            }
        });


        account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent (register.this, login.class);
                startActivity(intent);
//                finish();
            }
        });
//SPINNER
        Spinner spinner = (Spinner) findViewById(R.id.gender);
// Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.gender_array, android.R.layout.simple_spinner_item);
// Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
        spinner.setAdapter(adapter);
    }
    // spinner color
    public void onItemSelected(AdapterView<?> parent, View view, int spinner, long gender)
    {
        //Change the selected item's text color
        ((TextView) view).setTextColor(Color.BLACK);
    }

    // cloud
    public void store_profile(String first_name, String last_name, String email, String phone_number, String dob, String gender ){
        // Create a new user with a first and last name
        Map<String, Object> user = new HashMap<>();
        user.put("user_id", UUID.randomUUID().toString());
        user.put("first_name", first_name);
        user.put("last_name", last_name);
        user.put("email", email);
        user.put("phone_number", phone_number);
        user.put("date_of_birth", dob);
        user.put("gender", gender);
        user.put("date", FieldValue.serverTimestamp());

        // Add a new document with a generated ID
        db.collection("Profile")
                .add(user)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Toast.makeText(register.this,  "Account Successfully created",
                                Toast.LENGTH_LONG).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(register.this,  "error occured",
                                Toast.LENGTH_LONG).show();
                    }
                });
    }
}