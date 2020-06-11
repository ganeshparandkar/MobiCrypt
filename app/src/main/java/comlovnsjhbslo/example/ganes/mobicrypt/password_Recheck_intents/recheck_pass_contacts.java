package comlovnsjhbslo.example.ganes.mobicrypt.password_Recheck_intents;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.security.MessageDigest;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import comlovnsjhbslo.example.ganes.mobicrypt.Contacts.Main_Contacts;
import comlovnsjhbslo.example.ganes.mobicrypt.R;
import comlovnsjhbslo.example.ganes.mobicrypt.Sms.Message_main;
import comlovnsjhbslo.example.ganes.mobicrypt.common.common;

public class recheck_pass_contacts extends AppCompatActivity {

    EditText recheckpassContacts;
    Button recheckgotoContacts;
    String recheck_pass;

    String outputstring;

    String AES = "AES";

    final DatabaseReference table_user = FirebaseDatabase.getInstance().getReference().child("user").child(common.currentUser.getPhoneNumber());
    //String ContactName,ContactNumber;

    private static final String TAG = Main_Contacts.class.getSimpleName();
    private static final int REQUEST_CODE_PICK_CONTACTS = 1;
    private Uri uriContact;
    private String contactID;
    private String contact_name,contact_number;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recheck_pass_contacts);

        recheckgotoContacts =(Button) findViewById(R.id.btngotocontacts);
        recheckpassContacts = (EditText) findViewById(R.id.checkpasscontacts);

            table_user.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    recheck_pass = (String) dataSnapshot.child("InAppPasswords").child("contactpass").getValue().toString();
                }
                @Override
                public void onCancelled(DatabaseError databaseError) {
                }
            });

        recheckgotoContacts.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(recheckpassContacts.getText().toString().equals(recheck_pass)){
                        Toast.makeText(getApplicationContext(), "select contacts to encrypt!!", Toast.LENGTH_SHORT).show();

                        startActivityForResult(new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI), REQUEST_CODE_PICK_CONTACTS);

                    }
                    else{
                        Toast.makeText(getApplicationContext(), "Incorrect Password!!", Toast.LENGTH_SHORT).show();

                    }
                }
            });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE_PICK_CONTACTS && resultCode == RESULT_OK) {
            Log.d(TAG, "Response: " + data.toString());
            uriContact = data.getData();


            retrieveContactNumber();


        }
    }


    private void retrieveContactNumber() {

        String contactNumber = null;

        // getting contacts ID
        Cursor cursorID = getContentResolver().query(uriContact,
                new String[]{ContactsContract.Contacts._ID},
                null, null, null);

        if (cursorID.moveToFirst()) {

            contactID = cursorID.getString(cursorID.getColumnIndex(ContactsContract.Contacts._ID));
        }

        cursorID.close();


        // Using the contact ID now we will get contact phone number
        Cursor cursorPhone = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                new String[]{ContactsContract.CommonDataKinds.Phone.NUMBER},

                ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ? AND " +
                        ContactsContract.CommonDataKinds.Phone.TYPE + " = " +
                        ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE,

                new String[]{contactID},
                null);

        if (cursorPhone.moveToFirst()) {
            contactNumber = cursorPhone.getString(cursorPhone.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
        }

        cursorPhone.close();




        String contactName = null;

        // querying contact data store
        Cursor cursor = getContentResolver().query(uriContact, null, null, null, null);

        if (cursor.moveToFirst()) {

            // DISPLAY_NAME = The display name for the contact.
            // HAS_PHONE_NUMBER =   An indicator of whether this contact has at least one phone number.

            contactName = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
        }

        cursor.close();


        final String Content,Heading;

        contact_name = contactName;
        contact_number = contactNumber;
        try {

            Heading = contactName;
            Content = contactNumber;
            outputstring = encrypt(Content,recheck_pass);
            String content = outputstring;
            table_user.child("Contacts").child(contactName).child("Contact_Name").setValue(contact_name);
            table_user.child("Contacts").child(contact_name).child("Contact_Number").setValue(content);

        } catch (Exception e) {
            e.printStackTrace();
        }
      /*  contact_name = contactName;
        contact_number = contactNumber;

        table_user.child("Contacts").child(contactName).child("Contact_Name").setValue(contact_name);
        table_user.child("Contacts").child(contact_name).child("Contact_Number").setValue(contact_number);
        Toast.makeText(this,"", Toast.LENGTH_SHORT).show();
        //Toast.makeText(this, "Contact Number:" + contactNumber, Toast.LENGTH_SHORT).show();
*/


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


}
