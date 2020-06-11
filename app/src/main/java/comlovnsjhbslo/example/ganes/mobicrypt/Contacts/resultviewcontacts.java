package comlovnsjhbslo.example.ganes.mobicrypt.Contacts;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import comlovnsjhbslo.example.ganes.mobicrypt.R;
import comlovnsjhbslo.example.ganes.mobicrypt.common.common;

public class resultviewcontacts extends AppCompatActivity {

    ImageButton deletecontact;
    ImageButton callButton;
    String name,number;
    TextView contactNumber,contactName;
    final DatabaseReference table_user = FirebaseDatabase.getInstance().getReference().child("user").child(common.currentUser.getPhoneNumber());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resultviewcontacts);

        deletecontact = (ImageButton) findViewById(R.id.deletecontact1);
        contactName = (TextView) findViewById(R.id.contact_name1);
        contactNumber = (TextView) findViewById(R.id.contact_number1);
        callButton = (ImageButton) findViewById(R.id.callcontact);


        Intent i = getIntent();
        name = i.getStringExtra("contact_name");
        number = i.getStringExtra("contact_number");


        contactName.setText(name);
        contactNumber.setText(number);

        callButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:"+number));
                startActivity(intent);
            }
        });

        deletecontact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                table_user.child("Contacts").child(contactName.getText().toString()).removeValue();
                Intent i = new Intent(getApplicationContext(),Main_Contacts.class);
                startActivity(i);

            }
        });




    }
}
