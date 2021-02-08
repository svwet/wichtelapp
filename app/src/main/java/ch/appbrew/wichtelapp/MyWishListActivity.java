package ch.appbrew.wichtelapp;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;


public class MyWishListActivity extends AppCompatActivity {
    private ArrayList<MyWishListItem> myWishList;
    private RecyclerView mRecyclerView;
    private MyWishListAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private Button buttonInsert;
    //private Button buttonRemove;
    //private EditText editTextInsert;
    //private EditText editTextRemove;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mywishlist);
        createExampleList();
        buildRecyclerView();
        setButtons();
    }
    public void insertItem(int position) {
        myWishList.add(position, new MyWishListItem(R.drawable.ic_user, "New Item At Position" + position, "This is Line 2"));
        mAdapter.notifyItemInserted(position);
    }
    public void removeItem(int position) {
        myWishList.remove(position);
        mAdapter.notifyItemRemoved(position);
    }
    public void changeItem(int position, String text) {
        myWishList.get(position).changeProductName(text);
        mAdapter.notifyItemChanged(position);
    }
    public void createExampleList() {
        myWishList = new ArrayList<>();
        myWishList.add(new MyWishListItem(R.drawable.ic_user, "Line 1", "Line 2"));
        myWishList.add(new MyWishListItem(R.drawable.ic_gruppe, "Line 3", "Line 4"));
        myWishList.add(new MyWishListItem(R.drawable.ic_benutzereinstellung, "Line 5", "Line 6"));
    }
    public void buildRecyclerView() {
        mRecyclerView = findViewById(R.id.recyclerView);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mAdapter = new MyWishListAdapter(myWishList);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new MyWishListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                changeItem(position, "Clicked");
            }
            @Override
            public void onDeleteClick(int position) {
                removeItem(position);
            }
        });
    }
    public void setButtons() {
        buttonInsert = findViewById(R.id.button_insert);
        //buttonRemove = findViewById(R.id.button_remove);
        //editTextInsert = findViewById(R.id.edittext_insert);
        //editTextRemove = findViewById(R.id.edittext_remove);
        //editTextRemove = findViewById(R.id.edittext_remove);
        buttonInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = 0;//Integer.parseInt(editTextInsert.getText().toString());
                insertItem(position);
            }
        });
//        buttonRemove.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                int position = Integer.parseInt(editTextRemove.getText().toString());
//                removeItem(position);
//            }
//        });
    }
}