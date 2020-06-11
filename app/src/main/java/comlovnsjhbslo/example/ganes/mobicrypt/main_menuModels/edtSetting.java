package comlovnsjhbslo.example.ganes.mobicrypt.main_menuModels;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import comlovnsjhbslo.example.ganes.mobicrypt.R;
import comlovnsjhbslo.example.ganes.mobicrypt.check_password_intents.changePassword;

public class edtSetting extends AppCompatActivity {

    Button setpassword,contactus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edt_setting);
        setpassword = (Button) findViewById(R.id.changepass);
        contactus = (Button) findViewById(R.id.contactus);

        contactus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"developers are not available. Please try again Later",Toast.LENGTH_SHORT).show();
            }
        });

        setpassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent( edtSetting.this,changePassword.class);
                startActivity(i);
            }
        });
    }
}
