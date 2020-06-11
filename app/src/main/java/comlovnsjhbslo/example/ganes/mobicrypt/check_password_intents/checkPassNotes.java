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

import comlovnsjhbslo.example.ganes.mobicrypt.Notes.Notes_Main;
import comlovnsjhbslo.example.ganes.mobicrypt.R;
import comlovnsjhbslo.example.ganes.mobicrypt.common.common;
import comlovnsjhbslo.example.ganes.mobicrypt.Notes.edtNotes;

public class checkPassNotes extends AppCompatActivity {

    EditText notepassword;
    Button gotoNotes;
    String check_pass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_pass_notes);

        //Init Firebase
        //code to retrieve the password of notes///
        DatabaseReference database = FirebaseDatabase.getInstance().getReference().child("user").child(common.currentUser.getPhoneNumber()).child("InAppPasswords");

        notepassword = (EditText) findViewById(R.id.checkpassnotes);
        gotoNotes = (Button) findViewById(R.id.btngotonotes);

        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                check_pass = (String) dataSnapshot.child("notespass").getValue().toString();

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(getApplicationContext(), "Error!!", Toast.LENGTH_SHORT).show();
            }
        });
        /////////////
        gotoNotes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(notepassword.getText().toString().equals(check_pass)){
                    Intent edtnote = new Intent(checkPassNotes.this,Notes_Main.class);
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
