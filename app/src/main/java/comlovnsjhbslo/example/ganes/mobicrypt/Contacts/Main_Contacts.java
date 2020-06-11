package comlovnsjhbslo.example.ganes.mobicrypt.Contacts;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;


import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import comlovnsjhbslo.example.ganes.mobicrypt.Adaptors.contactAdapter;
import comlovnsjhbslo.example.ganes.mobicrypt.Adaptors.smsAdaptor;
import comlovnsjhbslo.example.ganes.mobicrypt.R;
import comlovnsjhbslo.example.ganes.mobicrypt.Sms.ListViewMessages;
import comlovnsjhbslo.example.ganes.mobicrypt.Sms.MainActivity;
import comlovnsjhbslo.example.ganes.mobicrypt.Sms.Message_main;
import comlovnsjhbslo.example.ganes.mobicrypt.check_password_intents.check_pass_contacts;
import comlovnsjhbslo.example.ganes.mobicrypt.check_password_intents.check_pass_message;
import comlovnsjhbslo.example.ganes.mobicrypt.common.common;
import comlovnsjhbslo.example.ganes.mobicrypt.password_Recheck_intents.recheck_pass_contacts;

public class Main_Contacts extends AppCompatActivity {


    DatabaseReference table_user = FirebaseDatabase.getInstance().getReference().child("user").child(common.currentUser.getPhoneNumber());


    private RecyclerView recyclerViewcontact;
    private contactAdapter contactAdapter;
    private List<ListViewContacts> contact_listItems;
    ImageButton gotoContact;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main__contacts);
        DatabaseReference table_user1 = FirebaseDatabase.getInstance().getReference().child("user").child(common.currentUser.getPhoneNumber()).child("Contacts");




        gotoContact = (ImageButton) findViewById(R.id.gotocontacts);

        contact_listItems = new ArrayList<>();
        recyclerViewcontact = findViewById(R.id.contacts_recycler_view);
        recyclerViewcontact.setHasFixedSize(true);
        recyclerViewcontact.setLayoutManager(new LinearLayoutManager(this));

        gotoContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //startActivityForResult(new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI), REQUEST_CODE_PICK_CONTACTS);
               Intent i = new Intent(Main_Contacts.this,recheck_pass_contacts.class);
                startActivity(i);
            }
        });



               table_user1.addValueEventListener(new ValueEventListener() {
                   @Override
                   public void onDataChange(DataSnapshot dataSnapshot) {
                        String cname,cnumber;
                      // String cname = dataSnapshot.getValue().toString();
                      // Toast.makeText(getApplicationContext(),cname,Toast.LENGTH_SHORT).show();

                       for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {

                           cname = (String) postSnapshot.child("Contact_Name").getValue();
                           cnumber = (String) postSnapshot.child("Contact_Number").getValue();
                           contact_listItems.add(new ListViewContacts(cname, cnumber));
                       }


                      // contact_listItems.add(new ListViewContacts("cname", "cnumber"));
                       contactAdapter = new contactAdapter(contact_listItems, this,getApplicationContext());
                       recyclerViewcontact.setAdapter(contactAdapter);
                   }

                   @Override
                   public void onCancelled(DatabaseError databaseError) {

                   }
               });

       }
}

