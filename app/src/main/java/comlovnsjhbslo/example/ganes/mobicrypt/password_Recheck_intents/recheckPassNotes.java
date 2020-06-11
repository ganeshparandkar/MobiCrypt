package comlovnsjhbslo.example.ganes.mobicrypt.password_Recheck_intents;

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

import comlovnsjhbslo.example.ganes.mobicrypt.Notes.Notes_Main;
import comlovnsjhbslo.example.ganes.mobicrypt.Notes.edtNotes;
import comlovnsjhbslo.example.ganes.mobicrypt.R;
import comlovnsjhbslo.example.ganes.mobicrypt.check_password_intents.checkPassNotes;
import comlovnsjhbslo.example.ganes.mobicrypt.common.common;

public class recheckPassNotes extends AppCompatActivity {

    EditText recheckpassnotes;
    Button recheckgotonotes;
    String recheck_pass;
    String notecontent,notehead;
    DatabaseReference database = FirebaseDatabase.getInstance().getReference().child("user").child(common.currentUser.getPhoneNumber()).child("InAppPasswords");



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recheck_pass_notes);
        
        recheckgotonotes =(Button) findViewById(R.id.recheckgotonotes);
        recheckpassnotes = (EditText) findViewById(R.id.recheckpassnotes);

        Intent intent = getIntent();
        notecontent =intent.getStringExtra("setcontent");
        notehead = intent.getStringExtra("setheading");
        //finish



        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                recheck_pass = (String) dataSnapshot.child("notespass").getValue().toString();

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(getApplicationContext(), "Error!!", Toast.LENGTH_SHORT).show();
            }
        });


        recheckgotonotes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(recheckpassnotes.getText().toString().equals(recheck_pass)){
                    Intent edtnote = new Intent(recheckPassNotes.this,edtNotes.class);
                    edtnote.putExtra("setheading", notehead);
                    edtnote.putExtra("setcontent",notecontent);
                    startActivity(edtnote);
                    finish();
                }
                else {

                    Toast.makeText(getApplicationContext(), "Incorrect Password!!", Toast.LENGTH_SHORT).show();
                }
            }
        });
        
        
    }
}
