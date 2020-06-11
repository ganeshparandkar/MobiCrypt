package comlovnsjhbslo.example.ganes.mobicrypt.Adaptors;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.database.ValueEventListener;

import java.util.List;

import comlovnsjhbslo.example.ganes.mobicrypt.Notes.viewNote;
import comlovnsjhbslo.example.ganes.mobicrypt.R;
import comlovnsjhbslo.example.ganes.mobicrypt.Sms.ListViewMessages;
import comlovnsjhbslo.example.ganes.mobicrypt.Sms.viewmessage;

public class smsAdaptor extends RecyclerView.Adapter<smsAdaptor.ViewHolder> {


    private List<ListViewMessages> sms_ListItems;
    private ValueEventListener context;
    private Context context1;
    String heading,content;

    public smsAdaptor(List<ListViewMessages> listItems, ValueEventListener context,Context context1) {
        sms_ListItems = listItems;
        this.context = context;
        this.context1 = context1;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.smslist_style,viewGroup,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int i) {
        holder.textViewHead.setText(sms_ListItems.get(i).getsms_head());
        holder.textViewDesc.setText(sms_ListItems.get(i).getsms_desc());
        holder.msg_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int i =holder.getAdapterPosition();
                heading = sms_ListItems.get(i).getsms_head();
                content = sms_ListItems.get(i).getsms_desc();
                Intent viewsms = new Intent(context1,viewmessage.class);
                viewsms.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                viewsms.putExtra("smsheading", heading);
                viewsms.putExtra("smscontent",content);
                context1.startActivity(viewsms);


                //take firebase heading n content
                //pass those to another intent
            }
        });


    }

    @Override
    public int getItemCount() {
        return sms_ListItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView textViewHead;
        public TextView textViewDesc;
        public LinearLayout msg_layout;
        public ViewHolder(View itemView) {
            //do changes in this class while creating the instance
            super(itemView);

            textViewHead = (TextView) itemView.findViewById(R.id.smslistHeading);
            textViewDesc = (TextView) itemView.findViewById(R.id.smslistContent);
            msg_layout = (LinearLayout) itemView.findViewById(R.id.smslistlayout);

        }
    }
}