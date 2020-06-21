package com.maniraghu.flashchatnewfirebase.ui.dashboard.SmilePlease;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProviders;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.maniraghu.flashchatnewfirebase.BaseFragment;
import com.maniraghu.flashchatnewfirebase.R;
import com.maniraghu.flashchatnewfirebase.ui.profile.UserInformation;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class Smile extends BaseFragment {

    private static final int PICK_IMAGE_REQUEST = 1;

    private Button mButtonChooseImage;
    private Button mButtonUpload;

    private EditText mEditTextFileName;
    private ImageView mImageView;
    private ProgressBar mProgressBar;
    private AutoCompleteTextView mtagUser;

    private Uri mImageUri;
    private SmileViewModel mViewModel;
    private StorageTask mUploadTask;
    private FirebaseAuth mAuth;
    private FirebaseUser user;
    private String uId,username,time;
    private StorageReference mStorageRef;
    private DatabaseReference mDatabaseRef,mUserDatabase,mTaggedUsersDatabase;
    private Calendar calendar;
    private SimpleDateFormat dateFormat;
    private ArrayList<String> taggedUsers;
    public static Smile newInstance() {
        return new Smile();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.smile_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(SmileViewModel.class);

        mButtonChooseImage = getActivity().findViewById(R.id.button_choose_image);
        mButtonUpload = getActivity().findViewById(R.id.button_upload);

        mEditTextFileName = getActivity().findViewById(R.id.edit_text_file_name);
        mImageView = getActivity().findViewById(R.id.image_view);
        mProgressBar = getActivity().findViewById(R.id.progress_bar);
        mtagUser = getActivity().findViewById(R.id.smile_please_tag_user);
        taggedUsers=new ArrayList<>();

        mTaggedUsersDatabase=FirebaseDatabase.getInstance().getReference("Users");
        mTaggedUsersDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot user:dataSnapshot.getChildren()) {
                    UserInformation uInfo = user.getValue(UserInformation.class);
                    if (uInfo.getUsername() != null) taggedUsers.add(uInfo.getUsername());
                }
                ArrayAdapter<String> actvAdapter=new ArrayAdapter<>(getActivity(),android.R.layout.simple_list_item_1,taggedUsers);
                mtagUser.setAdapter(actvAdapter);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        mStorageRef = FirebaseStorage.getInstance().getReference("uploads");
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("uploads");
        mAuth=FirebaseAuth.getInstance();
        user=mAuth.getCurrentUser();
        uId=user.getUid();
        mUserDatabase=FirebaseDatabase.getInstance().getReference().child("Users").child(uId);
        calendar=Calendar.getInstance();
        dateFormat=new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        time=dateFormat.format(calendar.getTime());
        mUserDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                username=dataSnapshot.child("username").getValue().toString();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        mButtonChooseImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFileChooser();
            }
        });

        mButtonUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mUploadTask != null && mUploadTask.isInProgress()) {
                    Toast.makeText(getActivity().getApplicationContext(), "Upload in progress", Toast.LENGTH_SHORT).show();
                } else {
                    uploadFile();
                }
            }
        });

    }

    private void openFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == getActivity().RESULT_OK
                && data != null && data.getData() != null) {
            mImageUri = data.getData();

            Picasso.with(getActivity().getApplication()).load(mImageUri).into(mImageView);
        }
    }

    private String getFileExtension(Uri uri) {
        ContentResolver cR = getActivity().getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }

    private void uploadFile() {
        if (mImageUri != null) {
            final StorageReference fileReference = mStorageRef.child(System.currentTimeMillis()
                    + "." + getFileExtension(mImageUri));

            mUploadTask = fileReference.putFile(mImageUri)
                    .addOnSuccessListener( new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    mProgressBar.setProgress(0);
                                }
                            }, 500);
                            fileReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                               @Override
                               public void onSuccess(Uri uri) {
                                   Uri downloadUrl = uri;
                                   //Do what you want with the url
                                   Toast.makeText(getActivity().getApplicationContext(), "Upload successful", Toast.LENGTH_LONG).show();
                                   String uploadId = mDatabaseRef.push().getKey();
                                   Upload upload = new Upload(mEditTextFileName.getText().toString().trim(),
                                           uri.toString(),username,uId,time,uploadId,mtagUser.getText().toString());

                                   mDatabaseRef.child(uploadId).setValue(upload);
                                   if(mActivity!=null) mActivity.navigateToFragment(new SmilePleaseViewer(),null);
                               }
                           });

                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(getActivity().getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
                            mProgressBar.setProgress((int) progress);
                        }
                    });
        } else {
            Toast.makeText(getActivity().getApplicationContext(), "No file selected", Toast.LENGTH_SHORT).show();
        }
    }
    private void openImagesActivity() {
        Intent intent = new Intent(getActivity().getApplication(), ImagesActivity.class);
        startActivity(intent);
    }
}