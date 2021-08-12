package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.Fragment;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class HelpActivity extends Fragment {

    View view;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_help, container, false);
        TextView tv = view.findViewById(R.id.txthelp);
        tv.setText(Html.fromHtml("<br><br>" +
                "Click on the left arrow on top for the menu.<br><br>" +
                "<u><b>Main Screen (All/Male/Female Heroes):</b></u> <br> Click on the pictures to get full details in the .<br>"  +
                "Click on the star to add to favourites.<br><br>" +
                "<u><b>Favourites section:</b></u> Click on the send button to send details and images of your favourite heroes to other platforms<br><br>" +
                "<u><b>General :</b></u> You can search by Id or Name"
                 ));
        return view;
    }
}