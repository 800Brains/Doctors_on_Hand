package team.cybertronics.doctorsonhand;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class login extends AppCompatActivity {
    Button btn_login;
    EditText email, password;
    TextView  laccount, status, forgotpassword;
    CheckBox showpassword;
    private FirebaseAuth mAuth;
    ProgressBar progressBar;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        btn_login = findViewById(R.id.signin);
        email = findViewById(R.id.semail);
        password = findViewById(R.id.spassword);
        showpassword = findViewById(R.id.showpassword);
        status = findViewById(R.id.status);
        laccount = findViewById(R.id.laccount);
        forgotpassword = findViewById(R.id.forgot_password);
        mAuth = FirebaseAuth.getInstance();
        progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(View.INVISIBLE);

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressBar.setVisibility(View.VISIBLE);
                String get_email = email.getText().toString();
                String get_password = password.getText().toString();
                if (!get_email.contains("@")) {
                    Toast.makeText(getApplicationContext(), "The use entered @", Toast.LENGTH_LONG).show();
                }if(get_password.length()<4){
                    status.setText("The password is weak");
                    status.setTextColor(Color.RED);
                    progressBar.setVisibility(View.INVISIBLE);
                }else{
//                    Toast.makeText(getApplicationContext(),"Valid email", Toast.LENGTH_LONG).show();
//                    status.setText("The passord is valid");
//                    status.setTextColor(Color.GREEN);
//                    status.setVisibility(View.GONE);
                    login(get_email,get_password);
                }

            }
        });
        laccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent (login.this, register.class);
                startActivity(intent);
//                finish();
            }
        });
        showpassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(showpassword.isChecked()){
                    password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }else{
                    password.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
            }
        });
        forgotpassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent (login.this, Forgot_password.class);
                startActivity(intent);
//                finish();
            }
        });
    }


 public void login(String get_email,String get_password){

             mAuth.signInWithEmailAndPassword(get_email, get_password).addOnCompleteListener(login.this, new OnCompleteListener<AuthResult>() {
                 @Override
                 public void onComplete(@NonNull Task<AuthResult> task) {
                     if (task.isSuccessful()) {
                         Toast.makeText(login.this, "Authentication successful.",
                                 Toast.LENGTH_SHORT).show();
                         Intent intent = new Intent (login.this, Homepage.class);
                            startActivity(intent);
                            finish();
                     } else {
                         // If sign in fails, display a message to the user.
                         Log.w(TAG, "createUserWithEmail:failure", task.getException());
                         Toast.makeText(login.this,  task.getException().toString(),
                                 Toast.LENGTH_LONG).show();
                         progressBar.setVisibility(View.INVISIBLE);
                         // updateUI(null);
                     }
                 }
             }).addOnFailureListener(login.this, new OnFailureListener() {
                 @Override
                 public void onFailure(@NonNull Exception e) {
                     Toast.makeText(login.this,  e.toString(),
                             Toast.LENGTH_LONG).show();
                     progressBar.setVisibility(View.INVISIBLE);
                 }
             });
    }









}