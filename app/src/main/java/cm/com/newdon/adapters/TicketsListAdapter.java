package cm.com.newdon.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

import cm.com.newdon.BottomBarActivity;
import cm.com.newdon.R;
import cm.com.newdon.classes.Post;
import cm.com.newdon.classes.Ticket;
import cm.com.newdon.common.CommonData;
import cm.com.newdon.common.OnPostSelectedListener;

/**
 * Created by Marina on 14.01.2017.
 */
public class TicketsListAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<Ticket> tickets;

    public TicketsListAdapter(Context context, ArrayList<Ticket> tickets) {
        this.context = context;
        this.tickets = tickets;
    }

    @Override
    public int getCount() {
        return tickets.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, final View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater)
                context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        RelativeLayout layout = (RelativeLayout) inflater.inflate(R.layout.lottery_ticket, parent, false);

        final Ticket ticket = tickets.get(position);

        TextView tvNumber = (TextView) layout.findViewById(R.id.tvTicketNumber);
        tvNumber.setText(ticket.getNumber());

        TextView tvStatus = (TextView) layout.findViewById(R.id.tvTicketStatus);
        ImageView imStatus = (ImageView) layout.findViewById(R.id.imStatus);
        GradientDrawable drawable = (GradientDrawable) imStatus.getBackground();
        switch (ticket.getStatus()) {
            case "exprired":
                tvStatus.setText(ticket.getStatus());
                tvStatus.setText("Win");
                int winColor = context.getResources().getColor(R.color.winColor);
                tvStatus.setTextColor(winColor);
                drawable.setColor(winColor);
                break;
            case "won":
                tvStatus.setText("Won");
                int expiredColor = context.getResources().getColor(R.color.expiredColor);
                tvStatus.setTextColor(expiredColor);
                drawable.setColor(expiredColor);
                break;
            default:
                imStatus.setVisibility(View.INVISIBLE);
        }

        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //clear the ArrayList and put only one post there
                Post ticketPost = CommonData.getInstance().findPostById(ticket.getPostId());
                if(ticketPost!=null){
                    CommonData.getInstance().getUserPosts().clear();
                    CommonData.getInstance().getUserPosts().add(ticketPost);
                    Intent intent = new Intent(context,BottomBarActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    intent.putExtra("postId", ticket.getPostId());
                    context.startActivity(intent);
                }
            }
        });

//        // TODO: 14.01.2017
//        change image

        return layout;
    }
}
