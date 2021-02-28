package ch.appbrew.wichtelapp;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AddItemToWishlistFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddItemToWishlistFragment extends Fragment {

    private ArrayList<MyWishListItem>addItem;


    private EditText editProductName;
    private EditText editProductDescr;
    private int IMAGE_CAPTURE_CODE = 1001;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private ImageView viewImage;
    private Button addSnap;
    private Button addToList;

    private EditText pushProductName;
    private EditText pushProductDescription;
    private EditText pushProductPicture;

    private FirebaseAuth auth;
    private FirebaseFirestore database;

    public AddItemToWishlistFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AddItemToWishlist.
     */
    // TODO: Rename and change types and number of parameters
    public static AddItemToWishlistFragment newInstance(String param1, String param2) {
        AddItemToWishlistFragment fragment = new AddItemToWishlistFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
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
        View view = inflater.inflate(R.layout.fragment_add_item_to_wishlist,
                container, false);

        setButton(view);

        return view;
    }

    public void setButton (View view){
        addToList = view.findViewById(R.id.btnInsertPic);
        addSnap = view.findViewById(R.id.btnTakeSnap);

        addSnap.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                selectImage();
            }

        });
        addToList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pushFirebase();

            }
        });



    }










    public boolean onCreateOptionMenu(Menu menu){

        getActivity().getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    private void selectImage(){

        viewImage=(ImageView) getView().findViewById(R.id.viewImage);

        final CharSequence[] options = { "Take Photo", "Choose from Gallery", "Cancel"};
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Add Photo");
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (options[item].equals("Take Photo")){
                    Uri imageUri;
                    ContentValues values = new ContentValues();
                    values.put(MediaStore.Images.Media.TITLE, "New Picture");
                    values.put(MediaStore.Images.Media.DESCRIPTION, "From the Camera");
                    imageUri = getActivity().getApplicationContext().getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
                    Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                    startActivityForResult(cameraIntent, IMAGE_CAPTURE_CODE);
                }
                else if(options[item].equals("Choose from Gallery")){
                    Intent intent = new Intent(Intent.ACTION_PICK,MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(intent, 2);
                }
                else if(options[item].equals("Cancel")){
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == RESULT_OK){
            if (requestCode == 1){
                File f = new File(Environment.getExternalStorageDirectory().toString());
                for(File temp : f.listFiles()){
                    if (temp.getName().equals("temp.jpg")){
                        f = temp;
                        break;
                    }
                }
                try {
                    Bitmap bitmap;
                    BitmapFactory.Options bitmapOptions = new BitmapFactory.Options();
                    bitmap = BitmapFactory.decodeFile(f.getAbsolutePath(), bitmapOptions);
                    viewImage.setImageBitmap(bitmap);
                    String path = android.os.Environment.getExternalStorageDirectory()

                            + File.separator
                            + "Phoenix" + File.separator + "default";
                    f.delete();
                    OutputStream outFile = null;
                    File file = new File ( path, String.valueOf(System.currentTimeMillis())+ ".jpg");
                        outFile = new FileOutputStream(file);
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 85, outFile);
                        outFile.flush();
                        outFile.close();
                } catch (Exception e){
                    e.printStackTrace();
                }

            }
            else if(requestCode == 2){
                Uri selectImage = data.getData();
                String[] filePath = {MediaStore.Images.Media.DATA};
                Cursor c = getActivity().getContentResolver().query(selectImage,filePath, null, null, null);
                c.moveToFirst();
                int columnIndex = c.getColumnIndex(filePath[0]);
                String picturePath = c.getString(columnIndex);
                c.close();
                Bitmap thumbnail = (BitmapFactory.decodeFile(picturePath));
                Log.w("path of image from gallery......******************.........",picturePath + "");
                viewImage.setImageBitmap(thumbnail);

            }
        }
    }

    public void pushFirebase(){
        auth = FirebaseAuth.getInstance();
        database = FirebaseFirestore.getInstance();
        auth.getCurrentUser();
        final String email = auth.getCurrentUser().getEmail();
        DocumentReference docRef = database.collection("MeineWunschliste").document(email);

        pushProductName = (EditText) getView().findViewById(R.id.insProductName);
        pushProductDescription = (EditText) getView().findViewById(R.id.insProductDescription);

        //After finishing selectImage() implementation, change to R.id.insProductImage and implement Base64 convert!!
        pushProductPicture = (EditText) getView().findViewById(R.id.insProductDescription);


                MyWishListItem pushNewItem = new MyWishListItem(pushProductName.getText().toString(), pushProductDescription.getText().toString(), pushProductPicture.getText().toString());
                docRef.set(pushNewItem).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(getActivity().getApplicationContext(),"Done", Toast.LENGTH_LONG).show();

                            NavHostFragment.findNavController(AddItemToWishlistFragment.this)
                                    .navigate(R.id.action_addItemToWishlist_to_fragment_meineWunschliste);

                        }
                        else{
                            Toast.makeText(getActivity().getApplicationContext(), "Not working",  Toast.LENGTH_LONG).show();

                        }

                    }
                });
    }


}