package comlovnsjhbslo.example.ganes.mobicrypt.Notes;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import comlovnsjhbslo.example.ganes.mobicrypt.Adaptors.MyAdapter;
import comlovnsjhbslo.example.ganes.mobicrypt.R;
import comlovnsjhbslo.example.ganes.mobicrypt.common.common;

import android.app.LauncherActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageButton;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
public class Notes_Main extends AppCompatActivity {
    ImageButton gotoNote;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter<MyAdapter.ViewHolder> adapter;
    private List<ListViewItem> listItems;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes__main);

        DatabaseReference table_user = FirebaseDatabase.getInstance().getReference().child("user").child(common.currentUser.getPhoneNumber()).child("Notes");



        listItems = new ArrayList<>();
        recyclerView = findViewById(R.id.my_recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        gotoNote = (ImageButton) findViewById(R.id.gotonote);
        gotoNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Notes_Main.this,edtNotes.class);
                startActivity(i);
            }
        });

        table_user.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for(DataSnapshot postSnapshot: dataSnapshot.getChildren()) {

                    String head = (String) postSnapshot.child("Title").getValue();
                    String Content = (String) postSnapshot.child("content").getValue();
                    listItems.add(new ListViewItem(
                            head,
                           Content));
                }
                adapter = new MyAdapter(listItems,this,getApplicationContext());
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }


    }



