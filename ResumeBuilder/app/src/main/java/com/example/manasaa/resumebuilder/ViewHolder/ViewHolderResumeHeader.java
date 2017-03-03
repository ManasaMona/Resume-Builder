package com.example.manasaa.resumebuilder.ViewHolder;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.manasaa.resumebuilder.R;

/**
 * Created by manasa.a on 02-03-2017.
 */

public class ViewHolderResumeHeader extends RecyclerView.ViewHolder {
    private static String TAG = ViewHolderResumeHeader.class.getSimpleName();
    public TextView userNameTxtView,userEmailTxtView;
    
    public ImageView userProfileURLImageView;
    public EditText userExperienceEditTxt;


    public ViewHolderResumeHeader(View itemView) {
        super(itemView);
        userEmailTxtView =(TextView) itemView.findViewById(R.id.user_emailID_txtView);
        userNameTxtView =(TextView) itemView.findViewById(R.id.user_name_txtView);
        userExperienceEditTxt = (EditText)itemView.findViewById(R.id.experience_editText);
        userProfileURLImageView = (ImageView) itemView.findViewById(R.id.userImage_imageView);
    }

    public ImageView getUserProfileURLImageView() {
        return userProfileURLImageView;
    }

    public void setUserProfileURLImageView(ImageView userProfileURLImageView) {
        this.userProfileURLImageView = userProfileURLImageView;
    }

    public TextView getUserNameTxtView() {
        return userNameTxtView;
    }

    public void setUserNameTxtView(TextView userNameTxtView) {
        this.userNameTxtView = userNameTxtView;
    }

    public TextView getUserEmailTxtView() {
        return userEmailTxtView;
    }

    public void setUserEmailTxtView(TextView userEmailTxtView) {
        this.userEmailTxtView = userEmailTxtView;
    }


    public EditText getUserExperienceEditTxt() {
        return userExperienceEditTxt;
    }

    public void setUserExperienceEditTxt(EditText userExperienceEditTxt) {
        this.userExperienceEditTxt = userExperienceEditTxt;
    }


}
