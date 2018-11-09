package com.example.bruno.cuadernodebitacora;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class OrderAdapter extends ArrayAdapter {

    public OrderAdapter(Context context, int resource, List<InitialOrder> objects) {
        super(context, resource, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = ((Activity) getContext()).getLayoutInflater().inflate(R.layout.item_order, parent, false);
        }
        InitialOrder order = (InitialOrder) getItem(position);
        TextView book_id = convertView.findViewById(R.id.order_id_list_item);
        TextView title = convertView.findViewById(R.id.title_list_item);

        title.setText(order.getBook_title());
        book_id.setText(order.getBook_id());

        return convertView;
    }

}
