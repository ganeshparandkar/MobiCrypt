package comlovnsjhbslo.example.ganes.mobicrypt.Adaptors;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.List;
import comlovnsjhbslo.example.ganes.mobicrypt.Notes.ListViewItem;
import comlovnsjhbslo.example.ganes.mobicrypt.R;
import comlovnsjhbslo.example.ganes.mobicrypt.common.common;
import comlovnsjhbslo.example.ganes.mobicrypt.Notes.viewNote;


public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {



  private List<ListViewItem> ListItems;
  private ValueEventListener context;
  private int position;

  private Context context1;
  String heading,content;
    public MyAdapter(List<ListViewItem> listItems, ValueEventListener context, Context context1) {
        ListItems = listItems;
        this.context = context;
        this.context1 = context1;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_style,viewGroup,false);
        return new ViewHolder(v);

    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int i) {

        final DatabaseReference database = FirebaseDatabase.getInstance().getReference().child("user").child(common.currentUser.getPhoneNumber());

        //position = i;

        holder.textViewHead.setText(ListItems.get(i).getHead());
        holder.textViewDesc.setText(ListItems.get(i).getDesc());


        holder.notelayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int i =holder.getAdapterPosition();
                heading = ListItems.get(i).getHead();
                content = ListItems.get(i).getDesc();
                Intent intent = new Intent(context1,viewNote.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("heading", heading);
                intent.putExtra("content",content);
                context1.startActivity(intent);
                             //to delete the note from firebase    // database.child("Notes").child(heading).removeValue();

            }
        });
    }

    @Override
    public int getItemCount() {
        return ListItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView textViewHead;
        public TextView textViewDesc;
        public LinearLayout notelayout;
        public ViewHolder(View itemView) {
            //do changes in this class while creating the instance
            super(itemView);

            textViewHead = (TextView) itemView.findViewById(R.id.notesHeading);
            textViewDesc = (TextView) itemView.findViewById(R.id.notesContent);
            notelayout = (LinearLayout) itemView.findViewById(R.id.notelayout);

        }
    }
}
