package ch.appbrew.wichtelapp;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MeineWunschlisteFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MyWishlistFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
public class MeineWunschlisteFragment extends Fragment {
    private ArrayList<MyWishListItem> myWishList;
    private RecyclerView mRecyclerView;
    private MyWishListAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private Button buttonInsert;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public MeineWunschlisteFragment() {
    public MyWishlistFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MeineWunschlisteFragment.
     */
    public static MeineWunschlisteFragment newInstance(String param1, String param2) {
        MeineWunschlisteFragment fragment = new MeineWunschlisteFragment();
        Bundle args = new Bundle();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_meine_wunschliste,
                container, false);
        createExampleList();
        buildRecyclerView(view);
        setButtons(view);
        return view;
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

    public void buildRecyclerView(View view) {
        mRecyclerView = view.findViewById(R.id.recyclerView);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getActivity());
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
    public void setButtons(View view) {
        buttonInsert = view.findViewById(R.id.button_insert);
        buttonInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = 0;
                insertItem(position);
            }
        });
    }
}