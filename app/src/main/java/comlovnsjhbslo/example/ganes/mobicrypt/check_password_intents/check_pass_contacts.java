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

import comlovnsjhbslo.example.ganes.mobicrypt.Contacts.resultviewcontacts;
import comlovnsjhbslo.example.ganes.mobicrypt.R;
import comlovnsjhbslo.example.ganes.mobicrypt.common.common;

public class check_pass_contacts extends AppCompatActivity {
    EditText contactpassword;
    Button gotoContacts;
    String check_passcontact;

    String contactname,contactnumber;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_pass_contacts);



        Intent i = getIntent();
        contactname =i.getStringExtra("contact_name");
        contactnumber = i.getStringExtra("contact_number");
        check_passcontact = i.getStringExtra("contact_password");




        contactpassword = (EditText) findViewById(R.id.checkpasscontacts);
        gotoContacts = (Button) findViewById(R.id.btngotocontacts);



        gotoContacts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(contactpassword.getText().toString().equals(check_passcontact)){
                   Intent show_contact = new Intent(check_pass_contacts.this,resultviewcontacts.class);
                   show_contact.putExtra("contact_name",contactname);
                   show_contact.putExtra("contact_number",contactnumber);
                   startActivity(show_contact);
                   finish();
                }
                else {

                    Toast.makeText(getApplicationContext(), "Incorrect Password!!", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}
