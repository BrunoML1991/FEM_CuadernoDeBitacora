package com.example.bruno.cuadernodebitacora;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;


import java.util.List;

public class BookAdapter extends ArrayAdapter {

    public BookAdapter(Context context, int resource, List<Book> objects) {
        super(context, resource, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = ((Activity) getContext()).getLayoutInflater().inflate(R.layout.item_book, parent, false);
        }
        Book book = (Book) getItem(position);
        TextView title = convertView.findViewById(R.id.title_list_item);
        TextView author = convertView.findViewById(R.id.author_list_item);

        title.setText(book.getTitle());
        author.setText(book.getAuthor());

        return convertView;
    }
}
