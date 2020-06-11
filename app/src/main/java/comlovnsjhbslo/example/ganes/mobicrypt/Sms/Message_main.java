package comlovnsjhbslo.example.ganes.mobicrypt.Sms;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import comlovnsjhbslo.example.ganes.mobicrypt.Adaptors.smsAdaptor;
import comlovnsjhbslo.example.ganes.mobicrypt.Notes.ListViewItem;
import comlovnsjhbslo.example.ganes.mobicrypt.Notes.Notes_Main;
import comlovnsjhbslo.example.ganes.mobicrypt.Notes.edtNotes;
import comlovnsjhbslo.example.ganes.mobicrypt.R;
import comlovnsjhbslo.example.ganes.mobicrypt.check_password_intents.check_pass_message;
import comlovnsjhbslo.example.ganes.mobicrypt.common.common;

public class Message_main extends AppCompatActivity {

    ImageButton gotoMessage;
    private RecyclerView recyclerViewsms;
    private smsAdaptor sms_adapter;
    private List<ListViewMessages> sms_listItems;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_main);
        DatabaseReference table_user = FirebaseDatabase.getInstance().getReference().child("user").child(common.currentUser.getPhoneNumber()).child("Messages");



        sms_listItems = new ArrayList<>();
        recyclerViewsms = findViewById(R.id.msg_recycler_view);
        recyclerViewsms.setHasFixedSize(true);
        recyclerViewsms.setLayoutManager(new LinearLayoutManager(this));



        gotoMessage = (ImageButton) findViewById(R.id.gotomessage);
            gotoMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Message_main.this,check_pass_message.class);
                startActivity(i);
            }
        });

            table_user.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for(DataSnapshot postSnapshot: dataSnapshot.getChildren()) {

                        String head = (String) postSnapshot.child("From").getValue();
                        String Content = (String) postSnapshot.child("content").getValue();
                        sms_listItems.add(new ListViewMessages(head, Content));
                    }
                    sms_adapter = new smsAdaptor(sms_listItems,this,getApplicationContext());
                    recyclerViewsms.setAdapter(sms_adapter);
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

    }
}
