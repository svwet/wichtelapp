package ch.appbrew.wichtelapp.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ch.appbrew.wichtelapp.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link WichtelWishlistFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class WichtelWishlistFragment extends Fragment {

    public WichtelWishlistFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment WichtelwunschlisteFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static WichtelWishlistFragment newInstance(String param1, String param2) {
        WichtelWishlistFragment fragment = new WichtelWishlistFragment();
        Bundle args = new Bundle();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_wichtelwunschliste, container, false);
    }
}