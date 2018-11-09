package com.example.bruno.cuadernodebitacora;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class DeliverOrderActivity extends AppCompatActivity {

    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mOrderDatabaseReference;
    private ChildEventListener mChildEventListener;
    private ListView order_list;
    private OrderAdapter orderAdapter;
    private static final String LOG_ORDER = "ORDERS";
    public static final String DELIVERED_DATA = "deliveredOrders";
    private InitialOrder orderSelected = null;
    private int positionSelected;
    public DeliveredOrder deliveredOrder = new DeliveredOrder();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.deliver_order_activity);
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mOrderDatabaseReference = mFirebaseDatabase.getReference().child(AddOrderActivity.ORDER_DATA);

        final List<InitialOrder> orderList = new ArrayList<>();
        final List<String> orderId = new ArrayList<>();
        orderAdapter = new OrderAdapter(this, R.layout.item_order, orderList);
        order_list = findViewById(R.id.list_orders);
        order_list.setAdapter(orderAdapter);
        mChildEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                InitialOrder order = dataSnapshot.getValue(InitialOrder.class);
                orderId.add(dataSnapshot.getKey());
                orderAdapter.add(order);
                Log.i(LOG_ORDER, "Order IDs: " + orderId + " last order added: " + order.toString());
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
                orderAdapter.remove(orderSelected);
                orderId.remove(orderSelected);
                orderSelected = null;
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };
        mOrderDatabaseReference.addChildEventListener(mChildEventListener);
        order_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                orderSelected = orderList.get(position);
                positionSelected = position;
                Log.i(LOG_ORDER, "Order position selected: " + positionSelected + " Order: " + orderSelected);
            }
        });


        findViewById(R.id.deliver_order).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (orderSelected != null) {
                    mOrderDatabaseReference = mFirebaseDatabase.getReference().child(DELIVERED_DATA);
                    deliveredOrder.setBook_id(orderSelected.getBook_id());
                    deliveredOrder.setOrder_id(orderId.get(positionSelected));
                    deliveredOrder.setBook_title(orderSelected.getBook_title());
                    deliveredOrder.setOrder_started_date_milis(orderSelected.getDate_milis());
                    deliveredOrder.setOrder_delivered_date_milis(System.currentTimeMillis());
                    mOrderDatabaseReference.push().setValue(deliveredOrder);
                    Toast.makeText(DeliverOrderActivity.this, "Order finished: " + deliveredOrder.toString(), Toast.LENGTH_LONG).show();
                    Log.i(LOG_ORDER, "Order delivered: " + deliveredOrder.toString());
                    mOrderDatabaseReference = mFirebaseDatabase.getReference().child(AddOrderActivity.ORDER_DATA);
                    DatabaseReference current = mOrderDatabaseReference.child(orderId.get(positionSelected));
                    current.removeValue();
                    deliveredOrder.setIssue_description(null);
                    deliveredOrder.setIssue_photo_url(null);
                } else {
                    Toast.makeText(DeliverOrderActivity.this, "Select an order from the list", Toast.LENGTH_LONG).show();
                }
            }
        });
        findViewById(R.id.submit_issue).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new IssueDialogFragment().show(getFragmentManager(), "ALERT DIALOG");
            }
        });
    }


}
