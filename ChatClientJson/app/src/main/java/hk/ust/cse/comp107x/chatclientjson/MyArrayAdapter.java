package hk.ust.cse.comp107x.chatclientjson;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by muppala on 4/5/15.
 */
public class MyArrayAdapter extends ArrayAdapter<Contacts.FriendInfo> {
    private final Context context;
    private final List<Contacts.FriendInfo> friendInfoArrayList;

    public MyArrayAdapter(Context context, List<Contacts.FriendInfo> friendInfoArrayList) {
        super(context, R.layout.friend_item, friendInfoArrayList);
        this.context = context;
        this.friendInfoArrayList = friendInfoArrayList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

            View friendInfoView;

            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            // Change the layout based on who the message is from
            friendInfoView = inflater.inflate(R.layout.friend_item, parent, false);
            TextView friendName = (TextView) friendInfoView.findViewById(R.id.friendName);
            friendName.setText(friendInfoArrayList.get(position).name);
            TextView statusMsg = (TextView) friendInfoView.findViewById(R.id.statusMsg);
            statusMsg.setText(friendInfoArrayList.get(position).statusMsg);

            return friendInfoView;
    }
}
