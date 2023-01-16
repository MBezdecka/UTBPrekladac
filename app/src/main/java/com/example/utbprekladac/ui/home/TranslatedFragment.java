package com.example.utbprekladac.ui.home;

import static android.content.Context.MODE_PRIVATE;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.utbprekladac.MyApiEndpointInterface;

import com.example.utbprekladac.R;
import com.example.utbprekladac.databinding.FragmentTranslatedBinding;
import com.example.utbprekladac.pojo.ApiResponse;
import com.example.utbprekladac.pojo.ResponseData;
import com.example.utbprekladac.MainActivity;
import com.squareup.moshi.Moshi;

import java.io.FileOutputStream;
import java.io.OutputStreamWriter;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.moshi.MoshiConverterFactory;
public class TranslatedFragment extends Fragment implements AdapterView.OnItemSelectedListener {

    public static final String BASE_URL = "https://api.mymemory.translated.net/";
    private FragmentTranslatedBinding binding;

    private MyApiEndpointInterface apiService;


    String LangF= "en";
    String LangT= "cs";

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        TranslatedViewModel translatedViewModel =
                new ViewModelProvider(this).get(TranslatedViewModel.class);
        Moshi moshi = new Moshi.Builder().build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(MoshiConverterFactory.create(moshi))
                .build();

        apiService = retrofit.create(MyApiEndpointInterface.class);

        binding = FragmentTranslatedBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        return  binding.getRoot();


    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.progressBar.setVisibility(View.GONE);

        Spinner spinner_F = view.findViewById(R.id.spinnerF);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.lang, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_F.setAdapter(adapter);
        spinner_F.setOnItemSelectedListener((AdapterView.OnItemSelectedListener) this);
        spinner_F.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                LangF = parent.getItemAtPosition(position).toString();
                Toast.makeText(parent.getContext(), LangF, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        Spinner spinner_T = view.findViewById(R.id.spinnerT);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_T.setAdapter(adapter);
        spinner_T.setOnItemSelectedListener((AdapterView.OnItemSelectedListener) this);
        spinner_T.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                LangT = parent.getItemAtPosition(position).toString();
                Toast.makeText(parent.getContext(), LangT, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        binding.btnTranslate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                binding.progressBar.setVisibility(View.VISIBLE);
                getTranslation();

            }
        });
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    private void getTranslation(){
        String q = binding.txtinput.getText().toString();


        String langpair = LangF+"|"+LangT;

        Call<ApiResponse> call = apiService.getResponse(q, langpair);
        call.enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                int statusCode = response.code();
                ApiResponse data = response.body();
                ResponseData responseData = data.responseData;

                Log.v("MYAPP", data.responseData.translatedText);
                binding.txtOutput.setText(data.responseData.translatedText);
                binding.progressBar.setVisibility(View.GONE);
                try {
                FileOutputStream fileout=getActivity().openFileOutput("history.txt", MODE_PRIVATE);
                OutputStreamWriter outputWriter=new OutputStreamWriter(fileout);
                outputWriter.write(data.toString());
                outputWriter.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {
                // Log error here since request failed
            }
        });
    }


}