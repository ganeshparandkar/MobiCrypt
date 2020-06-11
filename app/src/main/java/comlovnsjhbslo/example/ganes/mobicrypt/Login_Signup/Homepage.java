package comlovnsjhbslo.example.ganes.mobicrypt.Login_Signup;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import comlovnsjhbslo.example.ganes.mobicrypt.R;
public class Homepage extends AppCompatActivity {


    Button login;
    Button SignUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);

        login = (Button) findViewById(R.id.Login);
        SignUp = (Button) findViewById(R.id.signup);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


        SignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });

        SignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent activity1 = new Intent( Homepage.this, comlovnsjhbslo.example.ganes.mobicrypt.Login_Signup.activity1.class);
                startActivity(activity1);
                finish();
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent activity2 = new Intent( Homepage.this, comlovnsjhbslo.example.ganes.mobicrypt.Login_Signup.activity2.class);
                startActivity(activity2);
                finish();
            }
        });


    }
}
