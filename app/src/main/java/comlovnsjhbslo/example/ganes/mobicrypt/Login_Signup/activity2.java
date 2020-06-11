package comlovnsjhbslo.example.ganes.mobicrypt.Login_Signup;

import android.app.ProgressDialog;
import android.content.Intent;
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

import comlovnsjhbslo.example.ganes.mobicrypt.MainPage;
import comlovnsjhbslo.example.ganes.mobicrypt.R;
import comlovnsjhbslo.example.ganes.mobicrypt.common.common;
import comlovnsjhbslo.example.ganes.mobicrypt.model.User;


                    // Login Activity

public class activity2 extends AppCompatActivity
{
    EditText edtPhone,edtPassword;
    Button login;
    FirebaseDatabase database;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activity2);

        edtPhone = (EditText)findViewById(R.id.edtPhone);
        edtPassword = (EditText)findViewById(R.id.edtPassword);
        login = (Button)findViewById(R.id.button);

        //Init Firebase
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference table_user = database.getReference("user");


        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final ProgressDialog mDialog = new ProgressDialog(activity2.this);
                mDialog.setMessage("Please Wait...");
                mDialog.show();
                table_user.addValueEventListener(new ValueEventListener() {


                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        //check if user not exist in database
                        if(dataSnapshot.child(edtPhone.getText().toString()).exists()) {
                            // get user information
                            mDialog.dismiss();
                            User user = dataSnapshot.child(edtPhone.getText().toString()).getValue(User.class);
                            if (user.getPassword().equals(edtPassword.getText().toString())) {

                                Intent MainPage = new Intent( activity2.this, comlovnsjhbslo.example.ganes.mobicrypt.MainPage.class);
                                common.currentUser = user;
                                startActivity(MainPage);
                                finish();
                                //Toast.makeText(getApplicationContext(), "Sign in Successfullly !", Toast.LENGTH_SHORT).show();
                                user.setPhoneNumber(edtPhone.getText().toString());


                           } else {
                                Toast.makeText(getApplicationContext(), "Incorrect Password", Toast.LENGTH_SHORT).show();

                            }
                        }
                        else
                        {
                            mDialog.dismiss();
                            Toast.makeText(activity2.this, "user not exist please sign up first", Toast.LENGTH_SHORT).show();
                        }

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }
        });
    }
}
