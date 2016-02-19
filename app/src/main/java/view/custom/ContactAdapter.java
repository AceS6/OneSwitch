package view.custom;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.projeta.oneswitch.R;

import java.util.HashMap;

import data.Contact;

/**
 * Created by Alexis on 19/03/2015.
 */
public class ContactAdapter extends BaseAdapter {

    private HashMap<Integer, Contact> contacts;
    private Integer[] mKeys;
    private LayoutInflater inflater;

    public ContactAdapter(Context context, HashMap<Integer, Contact> contacts) {
        this.contacts = contacts;
        mKeys = contacts.keySet().toArray(new Integer[contacts.size()]);
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return this.contacts.size();
    }

    @Override
    public Contact getItem(int position) {
        return this.contacts.get(mKeys[position]);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view;

        Integer key = mKeys[position];
        Contact contact = getItem(position);

        if (convertView == null) {
            view = inflater.inflate(R.layout.contact_list_view, parent, false);
        }
        else {
            view = convertView;
        }

        TextView dateView = (TextView) view.findViewById(R.id.key_listView);
        dateView.setText( ""+ key );

        TextView titleView = (TextView) view.findViewById(R.id.name_listView);
        titleView.setText( contact.getName() );

        TextView descriptionView = (TextView) view.findViewById(R.id.number_listView);
        descriptionView.setText( contact.getNumber() );

        return view;
    }

    public void setContactTask(HashMap<Integer, Contact> contacts){
        this.contacts = contacts;
        this.notifyDataSetChanged();
    }
}
