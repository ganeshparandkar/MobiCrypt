package comlovnsjhbslo.example.ganes.mobicrypt.Login_Signup;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import comlovnsjhbslo.example.ganes.mobicrypt.R;
import comlovnsjhbslo.example.ganes.mobicrypt.model.User;

                            // Signup Activity

public class activity1 extends AppCompatActivity {


    EditText edtPhone,editPassword,edtName;
    TextView heading_mobicrypt;
    EditText editEmail; /// Shriyash code ignore this

    Button signUpButton;
    public int phone;



    @SuppressLint({"WrongViewCast", "CutPasteId"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activity1);

        edtName = (EditText) findViewById(R.id.edtName);
        edtPhone = (EditText) findViewById(R.id.edtPhone);   /// Shriyash code ignore this                                  // initializing variables
        editPassword = (EditText) findViewById(R.id.edtPassword);
        edtPhone = (EditText) findViewById(R.id.edtPhone);  /// Shriyash code ignore this                           //
        signUpButton = (Button) findViewById(R.id.btnSignUp);

        heading_mobicrypt = (TextView) findViewById(R.id.heading_Mobicrypt);



        //Init Firebase
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference table_user = database.getReference("user");
        //

        signUpButton.setOnClickListener(new View.OnClickListener() {


                @Override
                public void onClick (View v){
                final ProgressDialog mDialog = new ProgressDialog(activity1.this);
                mDialog.setMessage("Please Wait...");
                mDialog.show();

                final ValueEventListener valueEventListener = table_user.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        //check if already userphone
                        if (dataSnapshot.child(edtPhone.getText().toString()).exists()) {
                            mDialog.dismiss();
                            Toast.makeText(activity1.this, "Phone number already exist!!", Toast.LENGTH_SHORT).show();
                        } else {
                            mDialog.dismiss();

                            User user = new User(edtName.getText().toString(), editPassword.getText().toString(), edtPhone.getText().toString());
                            table_user.child(edtPhone.getText().toString()).setValue(user);
                            Toast.makeText(activity1.this, "Sign up successful!!", Toast.LENGTH_SHORT).show();
                            finish();


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
