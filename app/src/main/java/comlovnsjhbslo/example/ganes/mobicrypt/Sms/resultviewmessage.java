package comlovnsjhbslo.example.ganes.mobicrypt.Sms;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import comlovnsjhbslo.example.ganes.mobicrypt.R;
import comlovnsjhbslo.example.ganes.mobicrypt.common.common;

public class resultviewmessage extends AppCompatActivity {
    TextView setresultcontent,setresultHeading;
    ImageButton deleteresultmessages;
    final DatabaseReference deletedatabase = FirebaseDatabase.getInstance().getReference().child("user").child(common.currentUser.getPhoneNumber());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resultviewmessage);

        setresultcontent = (TextView) findViewById(R.id.resultmsg_Content);
        setresultHeading = (TextView) findViewById(R.id.resultmsg_heading);
        deleteresultmessages = (ImageButton) findViewById(R.id.resultdeletemsg);


        Intent result = getIntent();
        final String heading = result.getStringExtra("setheading");
        String content = result.getStringExtra("setcontent");
        setresultHeading.setText(heading);
        setresultcontent.setText(content);

        deleteresultmessages.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deletedatabase.child("Messages").child(heading).removeValue();
                Intent abc = new Intent(resultviewmessage.this,Message_main.class);
                startActivity(abc);
            }
        });

    }
}
