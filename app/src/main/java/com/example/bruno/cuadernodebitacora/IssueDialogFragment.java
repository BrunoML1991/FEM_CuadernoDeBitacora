package com.example.bruno.cuadernodebitacora;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class IssueDialogFragment extends android.app.DialogFragment {

    DeliverOrderActivity activity;
    // btb Firebase storage variables
    private FirebaseStorage mFirebaseStorage;
    private StorageReference mChatPhotosStorageReference;

    private static final int RC_PHOTO_PICKER = 1;

    @Override
    public Dialog onCreateDialog(final Bundle savedInstanceState) {
        mFirebaseStorage = FirebaseStorage.getInstance();
        mChatPhotosStorageReference = mFirebaseStorage.getReference().child("issue_photos");

        activity = (DeliverOrderActivity) getActivity();
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        LayoutInflater inflater = activity.getLayoutInflater();
        View v = inflater.inflate(R.layout.issue_dialog_fragment, null);
        builder.setView(v);

        v.findViewById(R.id.issue_image).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/jpeg");
                intent.putExtra(Intent.EXTRA_LOCAL_ONLY, true);
                startActivityForResult(Intent.createChooser(intent, "Complete action using"), RC_PHOTO_PICKER);
            }
        });
        final EditText issue_text = v.findViewById(R.id.edit_issue);
        v.findViewById(R.id.submit_issue_fragment).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.deliveredOrder.setIssue_description(issue_text.getText().toString());
                Log.i("BML", issue_text.getText().toString());
                IssueDialogFragment.this.getDialog().cancel();
            }
        });

        return builder.create();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_PHOTO_PICKER && resultCode == Activity.RESULT_OK) {
            Uri selectedImageUri = data.getData();
            Log.i(MainActivity.LOG_TAG, selectedImageUri.toString());
            StorageReference photoRef = mChatPhotosStorageReference.child(selectedImageUri.getLastPathSegment());
            photoRef.putFile(selectedImageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Task<Uri> urlTask = taskSnapshot.getMetadata().getReference().getDownloadUrl();
                    while (!urlTask.isSuccessful()) ;
                    Uri downloadUrl = urlTask.getResult();
                    activity.deliveredOrder.setIssue_photo_url(downloadUrl.toString());
                    Log.i(MainActivity.LOG_TAG, "Image url: " + activity.deliveredOrder.getIssue_photo_url());
                }
            });
        }
    }

}
