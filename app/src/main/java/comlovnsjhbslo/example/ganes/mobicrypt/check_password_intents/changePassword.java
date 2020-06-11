package comlovnsjhbslo.example.ganes.mobicrypt.check_password_intents;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.PagerSnapHelper;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import comlovnsjhbslo.example.ganes.mobicrypt.R;
import comlovnsjhbslo.example.ganes.mobicrypt.common.common;
import comlovnsjhbslo.example.ganes.mobicrypt.model.Passwords;

public class changePassword extends AppCompatActivity {

    EditText notespassword,imagepassword,messagepassword,contactpassword;
    Button btnSave,btnCancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        //Init Firebase
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference table_user = database.getReference("user");
        //

        notespassword= (EditText) findViewById(R.id.notepass);
        imagepassword = (EditText) findViewById(R.id.imagepass);   /// Shriyash code ignore this                                  // initializing variables
        messagepassword = (EditText) findViewById(R.id.messagepass);
        contactpassword = (EditText) findViewById(R.id.contactpass);  /// Shriyash code ignore this                           //
        btnSave = (Button) findViewById(R.id.savebtn);
        btnCancel = (Button) findViewById(R.id.cancelbtn);

        final DatabaseReference data = FirebaseDatabase.getInstance().getReference().child("user").child(common.currentUser.getPhoneNumber());

        final Passwords password = new Passwords(notespassword.getText().toString(),contactpassword.getText().toString(),messagepassword.getText().toString(),imagepassword.getText().toString());
        common.currentUserPassword = password;

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                common.currentUserPassword.setContactpass(contactpassword.getText().toString());
                common.currentUserPassword.setMessagepass(messagepassword.getText().toString());
                common.currentUserPassword.setImagepass(imagepassword.getText().toString());
                common.currentUserPassword.setNotespass(notespassword.getText().toString());

                table_user.child(common.currentUser.getPhoneNumber()).child("InAppPasswords").setValue(password);

                Toast.makeText(getApplicationContext(),"Password Saved", Toast.LENGTH_SHORT).show();
            }
        });

    }

}
