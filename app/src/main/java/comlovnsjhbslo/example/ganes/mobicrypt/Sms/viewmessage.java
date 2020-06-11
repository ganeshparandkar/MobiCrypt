package comlovnsjhbslo.example.ganes.mobicrypt.Sms;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
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

import comlovnsjhbslo.example.ganes.mobicrypt.Notes.Notes_Main;
import comlovnsjhbslo.example.ganes.mobicrypt.Notes.viewNote;
import comlovnsjhbslo.example.ganes.mobicrypt.R;
import comlovnsjhbslo.example.ganes.mobicrypt.common.common;
import comlovnsjhbslo.example.ganes.mobicrypt.password_Recheck_intents.recheckPassNotes;
import comlovnsjhbslo.example.ganes.mobicrypt.password_Recheck_intents.recheck_pass_message;

public class viewmessage extends AppCompatActivity {

    TextView setcontent,setHeading;
    Button editmsg;
    String message_password;
    String AES = "AES",outputstring;
    String msghead,msgcontent;

    final DatabaseReference table_user = FirebaseDatabase.getInstance().getReference().child("user");

    final DatabaseReference deletedatabase = FirebaseDatabase.getInstance().getReference().child("user").child(common.currentUser.getPhoneNumber());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewmessage);


        setHeading = (TextView) findViewById(R.id.msg_heading);
        setcontent = (TextView) findViewById(R.id.msg_Content);
        editmsg = (Button) findViewById(R.id.edit_msg);

        Intent i = getIntent();
        msgcontent =i.getStringExtra("smscontent");
        msghead = i.getStringExtra("smsheading");
        setcontent.setText(msgcontent);
        setHeading.setText(msghead);




        table_user.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                message_password = (String) dataSnapshot.child(common.currentUser.getPhoneNumber()).child("InAppPasswords").child("messagepass").getValue().toString();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        editmsg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    outputstring = decrypt(msgcontent,message_password);
                } catch (Exception e) {

                }

                Intent intent = new Intent(viewmessage.this,recheck_pass_message.class);
                intent.putExtra("setheading", msghead);
                intent.putExtra("setcontent",outputstring);
                startActivity(intent);
            }
        });

    }

    //decryption code
    private String decrypt(String outputstring, String Password) throws Exception {
        SecretKey key = generateKey(Password);
        Cipher c = Cipher.getInstance(AES);
        c.init(Cipher.DECRYPT_MODE,key);
        byte[] decodeValue = Base64.decode(outputstring, Base64.DEFAULT);
        byte[] decValue = c.doFinal(decodeValue);
        String decryptedValue = new String(decValue);
        return decryptedValue;
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
