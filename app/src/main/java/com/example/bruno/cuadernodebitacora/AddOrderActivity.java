package com.example.bruno.cuadernodebitacora;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class AddOrderActivity extends AppCompatActivity {

    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mBookDatabaseReference;
    private ChildEventListener mChildEventListener;
    private ListView book_list;
    private BookAdapter bookAdapter;
    private static final String LOG_BOOK = "BOOKS";
    public static final String ORDER_DATA = "initialOrders";
    private Book bookSelected=null;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_order_activity);
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mBookDatabaseReference = mFirebaseDatabase.getReference().child(MainActivity.BOOKS_DATA);

        final List<Book> bookList = new ArrayList<>();
        bookAdapter = new BookAdapter(this,R.layout.item_book,bookList);
        book_list=findViewById(R.id.list_books);
        book_list.setAdapter(bookAdapter);
        mChildEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Book book = dataSnapshot.getValue(Book.class);
                book.setId(dataSnapshot.getKey());
                bookAdapter.add(book);
                Log.i(LOG_BOOK,"Book: "+book.toString());
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };
        mBookDatabaseReference.addChildEventListener(mChildEventListener);
        book_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                bookSelected = bookList.get(position);
                Log.i(LOG_BOOK,"Book position selected: "+position+" Book: "+bookSelected);
            }
        });

        findViewById(R.id.submit_order).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (bookSelected != null) {
                    mBookDatabaseReference = mFirebaseDatabase.getReference().child(ORDER_DATA);
                    InitialOrder order = new InitialOrder(
                            bookSelected.getId(),
                            bookSelected.getTitle(),
                            System.currentTimeMillis()
                    );
                    mBookDatabaseReference.push().setValue(order);
                    Toast.makeText(AddOrderActivity.this, "Order added: " + order.toString(), Toast.LENGTH_LONG).show();
                    Log.i(LOG_BOOK, "Order added: " + order.toString());
                    mBookDatabaseReference = mFirebaseDatabase.getReference().child(MainActivity.BOOKS_DATA);
                }else {
                    Toast.makeText(AddOrderActivity.this,"Select a book from the list",Toast.LENGTH_LONG).show();
                }
            }
        });
    }

}
