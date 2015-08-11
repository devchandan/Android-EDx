package hk.ust.cse.comp107x.simplechatclient;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by muppala on 4/5/15.
 */
public class MyArrayAdapter extends ArrayAdapter<Message> {
    private final Context context;
    private final ArrayList<Message> messages;

    public MyArrayAdapter(Context context, ArrayList<Message> messages) {
        super(context, R.layout.message, messages);
        this.context = context;
        this.messages = messages;
    }

    // This method constructs the ListItem's view from the data obtained
    // from the Message object. This will be called by ListView while it
    // is being drawn.
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        // TODO

        View messageView;

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        messageView = inflater.inflate(R.layout.message, parent, false);

        TextView msgView =(TextView) messageView.findViewById(R.id.messageTextView);
        msgView.setText(messages.get(position).getMessage());
        TextView timeView =(TextView) messageView.findViewById(R.id.timeTextView);
        timeView.setText(messages.get(position).getTime());

        return messageView;

    }
}