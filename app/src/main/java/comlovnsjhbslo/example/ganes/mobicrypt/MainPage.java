package comlovnsjhbslo.example.ganes.mobicrypt;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;

import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.Toast;

import comlovnsjhbslo.example.ganes.mobicrypt.Contacts.Main_Contacts;
import comlovnsjhbslo.example.ganes.mobicrypt.Login_Signup.Homepage;
import comlovnsjhbslo.example.ganes.mobicrypt.Notes.Notes_Main;
import comlovnsjhbslo.example.ganes.mobicrypt.Sms.MainActivity;
import comlovnsjhbslo.example.ganes.mobicrypt.Sms.Message_main;
import comlovnsjhbslo.example.ganes.mobicrypt.Stegonography.Stegenomain.Stegonography_Home_page;
import comlovnsjhbslo.example.ganes.mobicrypt.check_password_intents.checkPassNotes;
import comlovnsjhbslo.example.ganes.mobicrypt.check_password_intents.check_pass_contacts;
import comlovnsjhbslo.example.ganes.mobicrypt.check_password_intents.check_pass_message;
import comlovnsjhbslo.example.ganes.mobicrypt.common.common;
import comlovnsjhbslo.example.ganes.mobicrypt.main_menuModels.edtSetting;
import comlovnsjhbslo.example.ganes.mobicrypt.main_menuModels.edtimages;


public class MainPage extends AppCompatActivity implements View.OnClickListener{
    private CardView notescard,messagescard,imagecard,contactcard,settingcard,thinkcard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);
        notescard = (CardView) findViewById(R.id.notes);
        messagescard = (CardView) findViewById(R.id.message);
        imagecard = (CardView) findViewById(R.id.image);
        contactcard = (CardView) findViewById(R.id.contact);
        settingcard = (CardView) findViewById(R.id.setting);
        thinkcard = (CardView) findViewById(R.id.think);

        notescard.setOnClickListener(this);
        messagescard.setOnClickListener(this);
        imagecard.setOnClickListener(this);
        contactcard.setOnClickListener(this);
        settingcard.setOnClickListener(this);
        thinkcard.setOnClickListener(this);

    }



    @Override
    public void onClick(View v) {
        Intent i;

        switch (v.getId()) {
            case R.id.notes: i = new Intent(this,Notes_Main.class); startActivity(i); break;
            case R.id.message: i = new Intent(this,Message_main.class);startActivity(i);break;
            case R.id.image: i = new Intent(this,Stegonography_Home_page.class);startActivity(i);break;
            case R.id.contact: i = new Intent(this,Main_Contacts.class);startActivity(i);  break;
            case R.id.setting: i = new Intent(this,edtSetting.class);startActivity(i); break;
            case R.id.think: i = new Intent(this,Homepage.class);startActivity(i); finish();  break;
        }
    }
}