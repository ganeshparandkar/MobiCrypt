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
import com.google.firebase.database.ValueEventListener;
import java.util.List;

import comlovnsjhbslo.example.ganes.mobicrypt.Contacts.ListViewContacts;
import comlovnsjhbslo.example.ganes.mobicrypt.Contacts.viewcontacts;
import comlovnsjhbslo.example.ganes.mobicrypt.R;

public class contactAdapter extends RecyclerView.Adapter<contactAdapter.ViewHolder> {



    private List<ListViewContacts> contacts_ListItems;
    private ValueEventListener context;
    private Context context1;
    String Name,Number;
   // DatabaseReference database = FirebaseDatabase.getInstance().getReference().child("user").child(common.currentUser.getPhoneNumber());

    public contactAdapter(List<ListViewContacts> listItems, ValueEventListener context, Context context1) {
        contacts_ListItems = listItems;
        this.context = context;
        this.context1 = context1;
    }


    @NonNull
    @Override
    public contactAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.contactlist_style,viewGroup,false);
        return new contactAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final contactAdapter.ViewHolder holder, int i) {
        holder.contactname.setText(contacts_ListItems.get(i).getContact_name());
        holder.contactnumber.setText(contacts_ListItems.get(i).getContact_number());
        holder.contact_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int i =holder.getAdapterPosition();
                Name = contacts_ListItems.get(i).getContact_name();
                Number = contacts_ListItems.get(i).getContact_number();

                Intent viewcontact = new Intent(context1,viewcontacts.class);
                viewcontact.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                viewcontact.putExtra("contact_name", Name);
                viewcontact.putExtra("contact_number",Number);
                context1.startActivity(viewcontact);

            }
        });

    }

    @Override
    public int getItemCount() {
        return contacts_ListItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView contactname;
        public TextView contactnumber;
        public LinearLayout contact_layout;
        public ViewHolder(View itemView) {
            //do changes in this class while creating the instance
            super(itemView);

            contactname = (TextView) itemView.findViewById(R.id.contactlistHeading);
            contactnumber = (TextView) itemView.findViewById(R.id.contactlistContent);
            contact_layout = (LinearLayout) itemView.findViewById(R.id.contactlistlayout);

        }
    }

}
