package comlovnsjhbslo.example.ganes.mobicrypt.Contacts;

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

import comlovnsjhbslo.example.ganes.mobicrypt.R;
import comlovnsjhbslo.example.ganes.mobicrypt.check_password_intents.check_pass_contacts;
import comlovnsjhbslo.example.ganes.mobicrypt.common.common;
import comlovnsjhbslo.example.ganes.mobicrypt.password_Recheck_intents.recheck_pass_contacts;

public class viewcontacts extends AppCompatActivity {

    TextView setNumber,setName;

    Button decryptContact;
    String contact_password;
    String AES = "AES",outputstring;
    String contactname,contactnumber;


    final DatabaseReference table_user = FirebaseDatabase.getInstance().getReference().child("user").child(common.currentUser.getPhoneNumber());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewcontacts);


        decryptContact = (Button) findViewById(R.id.decrypt_contact);
        setName = (TextView) findViewById(R.id.contact_name);
        setNumber = (TextView) findViewById(R.id.contact_number);

        Intent intent = getIntent();
        Intent i = getIntent();
        contactname =i.getStringExtra("contact_name");
        contactnumber = i.getStringExtra("contact_number");
        setName.setText(contactname);
        setNumber.setText(contactnumber);



        table_user.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                contact_password = (String) dataSnapshot.child("InAppPasswords").child("contactpass").getValue(); //working
                //Toast.makeText(getApplicationContext(),contact_password,Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {}
        });





        decryptContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    outputstring = decrypt(contactnumber,contact_password);
                } catch (Exception e) {

                }

                Intent intent = new Intent(viewcontacts.this,check_pass_contacts.class);
                intent.putExtra("contact_name", contactname);
                intent.putExtra("contact_number",outputstring);
                intent.putExtra("contact_password",contact_password);
                startActivity(intent);

            }
        });

    }   //decryption code
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