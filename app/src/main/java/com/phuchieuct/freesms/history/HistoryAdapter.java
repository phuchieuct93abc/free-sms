package com.phuchieuct.freesms.history;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.provider.ContactsContract;
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
        fromTxt.setText(getContactName(this.context, message.getReceiver()));
        messageTxt.setText(message.getContent());
        if(message.isStatus()){
            statusTxt.setText("Thành công");
            statusTxt.setTextColor(Color.GREEN);
        }else{
            statusTxt.setText("Thất bại");
            statusTxt.setTextColor(Color.RED);


        }



        return rowView;
    }
    private static String getContactName( Context context,String phoneNumber) {
        ContentResolver cr = context.getContentResolver();
        Uri uri = Uri.withAppendedPath(ContactsContract.PhoneLookup.CONTENT_FILTER_URI, Uri.encode(phoneNumber));
        Cursor cursor = cr.query(uri, new String[]{ContactsContract.PhoneLookup.DISPLAY_NAME}, null, null, null);
        if (cursor == null) {
            return null;
        }
        String contactName = null;
        if(cursor.moveToFirst()) {
            contactName = cursor.getString(cursor.getColumnIndex(ContactsContract.PhoneLookup.DISPLAY_NAME));
        }

        if(cursor != null && !cursor.isClosed()) {
            cursor.close();
        }

        return contactName;
    }
    public void setHistory(List<Message> messages){
        this.messages =messages;

    }
}
