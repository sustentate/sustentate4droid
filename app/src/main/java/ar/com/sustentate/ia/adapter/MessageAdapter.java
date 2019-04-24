package ar.com.sustentate.ia.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import ar.com.sustentate.ia.R;
import ar.com.sustentate.ia.models.AssistanceRequest;
import ar.com.sustentate.ia.models.Message;

/**
 * Created by emzas on 30/10/2018.
 */

public class MessageAdapter extends BaseAdapter{

    List<Message> messages = new ArrayList<>();
    Context context;

    public MessageAdapter(Context context) {
        this.context = context;
    }

    public void add(Message message) {
        this.messages.add(message);
        notifyDataSetChanged();
    }
    @Override
    public int getCount() { return messages.size();}

    @Override
    public Object getItem(int i) {return messages.get(i);}

    @Override
    public long getItemId(int i) {return i;}

    @Override
    public View getView(int i, View convertView, ViewGroup parent) {
        MessageViewHolder holder = new MessageViewHolder();
        LayoutInflater messageInflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        Message message = messages.get(i);

        if (message.getClass() == AssistanceRequest.class){
            convertView = messageInflater.inflate(R.layout.my_message, null);
            holder.messageBody = (TextView) convertView.findViewById(R.id.message_body);
            holder.messageBody.setText(message.getSentence());
            convertView.setTag(holder);
        }else {
            convertView = messageInflater.inflate(R.layout.their_message, null);
            holder.avatar = (View) convertView.findViewById(R.id.avatar);
            holder.name = (TextView) convertView.findViewById(R.id.name);
            holder.messageBody = (TextView) convertView.findViewById(R.id.message_body);
            holder.name.setText("Carla");
            holder.messageBody.setText(message.getSentence());
            convertView.setTag(holder);
        }

        return convertView;
    }

    public class MessageViewHolder {
        public View avatar;
        public TextView name;
        public TextView messageBody;
    }
}
