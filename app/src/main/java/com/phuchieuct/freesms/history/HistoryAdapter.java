package com.phuchieuct.freesms.history;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.phuchieuct.freesms.R;

import java.util.List;


public class HistoryAdapter extends ArrayAdapter<Message> {
    Context context;
    List<Message> messages;
    public HistoryAdapter(Context context, int resource, List<Message> objects) {
        super(context, resource, objects);

        this.context = context;
        this.messages = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View rowView = inflater.inflate(R.layout.list_history, parent, false);
        TextView fromTxt = (TextView) rowView.findViewById(R.id.from);
        TextView messageTxt = (TextView) rowView.findViewById(R.id.message);
        TextView statusTxt = (TextView) rowView.findViewById(R.id.status);
Message message = messages.get(position);
        fromTxt.setText(message.getReceiver());
        messageTxt.setText(message.getContent());
        if(message.isStatus()){
            statusTxt.setText("Thành công");
        }else{
            statusTxt.setText("Thất bại");

        }



        return rowView;
    }
    public void setHistory(List<Message> messages){
        this.messages =messages;

    }
}
