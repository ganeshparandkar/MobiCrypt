package comlovnsjhbslo.example.ganes.mobicrypt.Notes;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.security.MessageDigest;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import comlovnsjhbslo.example.ganes.mobicrypt.R;
import comlovnsjhbslo.example.ganes.mobicrypt.common.common;


public class edtNotes extends AppCompatActivity {

    EditText edtContent,edtHeading;
    Button createNote;
    EditText edtPhone;

    ImageButton deletenote;
    Toolbar mtoolbar;
    String outputstring;
    String notes_password;

String notecontent,notehead;
    String AES = "AES";



    @Override
    public void setSupportActionBar(@Nullable Toolbar toolbar) {
        super.setSupportActionBar(toolbar);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edt_notes);
        edtContent = (EditText) findViewById(R.id.Notes_Content);
        edtHeading = (EditText) findViewById(R.id.note_heading);
        createNote = (Button) findViewById(R.id.Create_note);
        deletenote = (ImageButton) findViewById(R.id.deletenote);
        edtPhone = (EditText) findViewById(R.id.edtPhone);
        mtoolbar = (Toolbar) findViewById(R.id.Notestoolbar);

       // setSupportActionBar(mtoolbar);


        //edit note function
        Intent intent = getIntent();
        notecontent =intent.getStringExtra("setcontent");
        notehead = intent.getStringExtra("setheading");

        edtContent.setText(notecontent);
        edtHeading.setText(notehead);
        //finish



        //Init Firebase
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference table_user = database.getReference("user");
        //
        deletenote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                table_user.child(common.currentUser.getPhoneNumber()).child("Notes").child(notehead).removeValue();
                Intent abc = new Intent(edtNotes.this,Notes_Main.class);
                startActivity(abc);

            }
        });
        table_user.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                notes_password = (String) dataSnapshot.child(common.currentUser.getPhoneNumber()).child("InAppPasswords").child("notespass").getValue().toString();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        createNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String Content,Heading;

                try {
                    Heading = edtHeading.getText().toString();
                    outputstring = encrypt(edtContent.getText().toString(),notes_password);
                    Content = outputstring;

                    table_user.child(common.currentUser.getPhoneNumber()).child("Notes").child(Heading).child("Title").setValue(Heading);
                    table_user.child(common.currentUser.getPhoneNumber()).child("Notes").child(Heading).child("content").setValue(Content);

                } catch (Exception e) {
                    e.printStackTrace();
                }

                Intent i = new Intent(edtNotes.this,Notes_Main.class);
                startActivity(i);
                finish();

            }
        });

    }


    //encryption code
    private String encrypt(String Data, String notes_password) throws Exception{
        SecretKey key = generateKey(notes_password);
        Cipher c = Cipher.getInstance(AES);
        c.init(Cipher.ENCRYPT_MODE,key);
        byte[] encVal = c.doFinal(Data.getBytes());
        String encryptedValue = Base64.encodeToString(encVal, Base64.DEFAULT);
        return encryptedValue;
    }

    // common code for encryption and decryption key generation
    private SecretKey generateKey(String notes_password) throws Exception{
        final MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] bytes = notes_password.getBytes("UTF-8");
        digest.update(bytes, 0, bytes.length);
        byte[] key = digest.digest();
        SecretKey secretKeySpec =  new SecretKeySpec(key, "AES");
        return secretKeySpec;
    }


}
