package com.example.utbprekladac.ui.history;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;


import com.example.utbprekladac.databinding.FragmentHistoryBinding;

import java.io.FileInputStream;
import java.io.InputStreamReader;

public class HistoryFragment extends Fragment {

    private FragmentHistoryBinding binding;
    EditText textmsg;
    static final int READ_BLOCK_SIZE = 100;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        historyViewModel historyViewModel =
                new ViewModelProvider(this).get(historyViewModel.class);

        binding = FragmentHistoryBinding .inflate(inflater, container, false);
        View root = binding.getRoot();
        binding.btnRead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try {
                    FileInputStream fileIn=getActivity().openFileInput("history.txt");
                    InputStreamReader InputRead= new InputStreamReader(fileIn);

                    char[] inputBuffer= new char[READ_BLOCK_SIZE];
                    String s="";
                    int charRead;

                    while ((charRead=InputRead.read(inputBuffer))>0) {
                        // char to string conversion
                        String readstring=String.copyValueOf(inputBuffer,0,charRead);
                        s +=readstring;
                    }
                    InputRead.close();
                    textmsg.setText(s);


                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });


        final TextView textView = binding.textView1;


        historyViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;

    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }



}

