package comlovnsjhbslo.example.ganes.mobicrypt.password_Recheck_intents;

import android.content.Intent;
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

import comlovnsjhbslo.example.ganes.mobicrypt.Notes.edtNotes;
import comlovnsjhbslo.example.ganes.mobicrypt.R;
import comlovnsjhbslo.example.ganes.mobicrypt.Sms.resultviewmessage;
import comlovnsjhbslo.example.ganes.mobicrypt.Sms.viewmessage;
import comlovnsjhbslo.example.ganes.mobicrypt.common.common;

public class recheck_pass_message extends AppCompatActivity {


    EditText recheckpassmessage;
    Button recheckgotomessage;
    String recheck_pass;
    String messagecontent,messagehead;

    final DatabaseReference table_user = FirebaseDatabase.getInstance().getReference().child("user");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recheck_pass_message);

        recheckpassmessage = (EditText) findViewById(R.id.recheckpassmessage);
        recheckgotomessage = (Button) findViewById(R.id.btngotomessage);

        Intent intent = getIntent();
        messagecontent =intent.getStringExtra("setcontent");
        messagehead = intent.getStringExtra("setheading");

        table_user.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                recheck_pass = (String) dataSnapshot.child(common.currentUser.getPhoneNumber()).child("InAppPasswords").child("messagepass").getValue().toString();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        recheckgotomessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(recheckpassmessage.getText().toString().equals(recheck_pass)){
                    Intent viewmsg = new Intent(recheck_pass_message.this,resultviewmessage.class);
                    viewmsg.putExtra("setheading", messagehead);
                    viewmsg.putExtra("setcontent",messagecontent);
                    startActivity(viewmsg);
                    finish();
                }
                else {

                    Toast.makeText(getApplicationContext(), "Incorrect Password!!", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }
}
