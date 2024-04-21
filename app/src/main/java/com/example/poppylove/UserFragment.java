package com.example.poppylove;

import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class UserFragment extends Fragment {

    private Button BtmLogout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_user, container, false);

        // Initialize the Button
        BtmLogout = view.findViewById(R.id.Logout);

        // Set the OnClickListener for the Button
        BtmLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Perform any action here when the button is clicked
                logoutUser();  // Assuming logoutUser() is a method that handles logout
            }
        });

        return view;
    }

    private void logoutUser() {
        // Create an Intent to start the LoginActivity
        Intent intent = new Intent(getActivity(), LoginActivity.class);

        // Optionally add flags if needed, e.g., to clear the task stack:
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

        // Start the LoginActivity
        startActivity(intent);

        // Optionally, if you want to finish the current activity as well
        getActivity().finish();
    }
}
