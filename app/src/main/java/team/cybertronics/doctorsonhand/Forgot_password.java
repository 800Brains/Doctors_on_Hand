package team.cybertronics.doctorsonhand;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class Forgot_password extends AppCompatActivity {

    EditText email;

    Button btn_submit;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        email = findViewById(R.id.femail);
        btn_submit = findViewById(R.id.submit);
        mAuth = FirebaseAuth.getInstance();



        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String get_email = email.getText().toString();
                beginRecovery(get_email);
            }
        });
    }

    public void beginRecovery(String get_email){
        mAuth.sendPasswordResetEmail(get_email).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(Forgot_password.this, "Email Sent", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(Forgot_password.this, "Failed....", Toast.LENGTH_SHORT).show();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(Forgot_password.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

}
