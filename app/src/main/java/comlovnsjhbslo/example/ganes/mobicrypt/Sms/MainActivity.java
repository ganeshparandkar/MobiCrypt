package comlovnsjhbslo.example.ganes.mobicrypt.Sms;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import comlovnsjhbslo.example.ganes.mobicrypt.R;
import comlovnsjhbslo.example.ganes.mobicrypt.common.common;


import android.content.ContentResolver;
        import android.content.Intent;
        import android.content.pm.PackageManager;
        import android.database.Cursor;
        import android.net.Uri;
        import android.support.v4.app.ActivityCompat;
        import android.support.v4.content.ContextCompat;
import android.util.Base64;
import android.view.View;
        import android.widget.AdapterView;
        import android.widget.ArrayAdapter;
        import android.widget.ListView;
        import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.security.MessageDigest;
import java.util.ArrayList;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class MainActivity extends AppCompatActivity {

    private static MainActivity inst;
    ArrayList<String> smsMessagesList = new ArrayList<String>();

    ArrayList<String> smsMessagesListFrom = new ArrayList<String>();
    ArrayList<String> smsMessagesListContent = new ArrayList<String>();
    ListView smsListView;
    ArrayAdapter arrayAdapter,arrayAdapterFrom,arrayAdapterContent;
    String msg_password;

    String outputstring;

    String AES = "AES";

    public static MainActivity instance() {
        return inst;
    }
    @Override
    public void onStart() {
        super.onStart();
        inst = this;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final DatabaseReference database = FirebaseDatabase.getInstance().getReference().child("user").child(common.currentUser.getPhoneNumber()).child("Messages");
        //Init Firebase
        FirebaseDatabase database1 = FirebaseDatabase.getInstance();
        final DatabaseReference table_user = database1.getReference("user");
        //


        smsListView = (ListView) findViewById(R.id.SMSList);
        arrayAdapterContent = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, smsMessagesListContent);
        arrayAdapterFrom = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, smsMessagesListFrom);
        arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, smsMessagesList);
        smsListView.setAdapter(arrayAdapter);

        table_user.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                msg_password = (String) dataSnapshot.child(common.currentUser.getPhoneNumber()).child("InAppPasswords").child("messagepass").getValue().toString();

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        // Add SMS Read Permision At Runtime
        // Todo : If Permission Is Not GRANTED
        if(ContextCompat.checkSelfPermission(getBaseContext(), "android.permission.READ_SMS") == PackageManager.PERMISSION_GRANTED) {

            // Todo : If Permission Granted Then Show SMS
            refreshSmsInbox();

        } else {
            // Todo : Then Set Permission
            final int REQUEST_CODE_ASK_PERMISSIONS = 123;
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{"android.permission.READ_SMS"}, REQUEST_CODE_ASK_PERMISSIONS);
        }

       smsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                final String Content,Heading;

                try {

                     Heading = smsMessagesListFrom.get(position);
                     Content = smsMessagesListContent.get(position);
                    outputstring = encrypt(smsMessagesListContent.get(position),msg_password);
                    String content = outputstring;

                    database.child(Heading).child("From").setValue(Heading);
                    database.child(Heading).child("content").setValue(content);
                    Intent intent = new Intent(getApplicationContext(),Message_main.class);
                    startActivity(intent);

                } catch (Exception e) {
                    e.printStackTrace();
                }


            }
        });



    }

    //encryption code
    private String encrypt(String Data, String msg_password) throws Exception{
        SecretKey key = generateKey(msg_password);
        Cipher c = Cipher.getInstance(AES);
        c.init(Cipher.ENCRYPT_MODE,key);
        byte[] encVal = c.doFinal(Data.getBytes());
        String encryptedValue = Base64.encodeToString(encVal, Base64.DEFAULT);
        return encryptedValue;
    }

    // common code for encryption and decryption key generation
    private SecretKey generateKey(String msg_password) throws Exception{
        final MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] bytes = msg_password.getBytes("UTF-8");
        digest.update(bytes, 0, bytes.length);
        byte[] key = digest.digest();
        SecretKey secretKeySpec =  new SecretKeySpec(key, "AES");
        return secretKeySpec;
    }

    public void refreshSmsInbox() {
        ContentResolver contentResolver = getContentResolver();
        Cursor smsInboxCursor = contentResolver.query(Uri.parse("content://sms/inbox"), null, null, null, null);
        int indexBody = smsInboxCursor.getColumnIndex("body");
        int indexAddress = smsInboxCursor.getColumnIndex("address");
        if (indexBody < 0 || !smsInboxCursor.moveToFirst()) return;
        arrayAdapter.clear();
        do {
            String From = smsInboxCursor.getString(indexAddress);
            String Content = smsInboxCursor.getString(indexBody);
            String str = "SMS From: " + smsInboxCursor.getString(indexAddress) +
                    "\n" + smsInboxCursor.getString(indexBody) + "\n";
            arrayAdapter.add(str);
            arrayAdapterFrom.add(From);
            arrayAdapterContent.add(Content);
        } while (smsInboxCursor.moveToNext());
    }

    public void updateList(final String smsMessage) {
        arrayAdapter.insert(smsMessage, 0);
        arrayAdapter.notifyDataSetChanged();
    }


    public void onItemClick(AdapterView<?> parent, View view, int pos, long id) {
        try {
            String[] smsMessages = smsMessagesList.get(pos).split("\n");
            String address = smsMessages[0];
            String smsMessage = "";
            for (int i = 1; i < smsMessages.length; ++i) {
                smsMessage = smsMessage + smsMessages[i];
            }

            String smsMessageStr = address + "\n";
            smsMessageStr += smsMessage;
            Toast.makeText(this, smsMessageStr, Toast.LENGTH_SHORT).show();

            Toast.makeText(this, address, Toast.LENGTH_SHORT).show();


        } catch (Exception e) {
            e.printStackTrace();
        }

        // Todo : Thanks For Watching...
    }

}
