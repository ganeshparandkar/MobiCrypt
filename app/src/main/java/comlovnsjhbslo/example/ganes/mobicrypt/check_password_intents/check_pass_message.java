package comlovnsjhbslo.example.ganes.mobicrypt.check_password_intents;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import comlovnsjhbslo.example.ganes.mobicrypt.R;
import comlovnsjhbslo.example.ganes.mobicrypt.Sms.MainActivity;
import comlovnsjhbslo.example.ganes.mobicrypt.Sms.Message_main;
import comlovnsjhbslo.example.ganes.mobicrypt.common.common;

public class check_pass_message extends AppCompatActivity {
    EditText Msgpassword;
    Button gotoMessage;
    String check_passmessage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_pass_message);


        DatabaseReference database = FirebaseDatabase.getInstance().getReference().child("user").child(common.currentUser.getPhoneNumber()).child("InAppPasswords");

        Msgpassword = (EditText) findViewById(R.id.checkpassmessage);
        gotoMessage = (Button) findViewById(R.id.btngotomessage);

        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                check_passmessage = (String) dataSnapshot.child("messagepass").getValue().toString();

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(getApplicationContext(), "Error!!", Toast.LENGTH_SHORT).show();
            }
        });
        gotoMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Msgpassword.getText().toString().equals(check_passmessage)){

                    Intent edtmessage = new Intent(check_pass_message.this,MainActivity.class);
                    startActivity(edtmessage);
                    finish();
                }
                else {

                    Toast.makeText(getApplicationContext(), "Incorrect Password!!", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}
