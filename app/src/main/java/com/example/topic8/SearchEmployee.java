package com.example.topic8;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.topic8.api.EmployeeAPI;
import com.example.topic8.model.Employee;
import com.example.topic8.url.URL;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SearchEmployee extends AppCompatActivity {
    Button btnSearch;
    EditText etSearchId;
    TextView empRecord;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_employee);
        btnSearch = findViewById(R.id.btnSearch);
        etSearchId = findViewById(R.id.etSearchId);
        empRecord = findViewById(R.id.empRecord);

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loaddata();
            }
        });

    }

    private void loaddata(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(URL.base_url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        EmployeeAPI employeeAPI = retrofit.create(EmployeeAPI.class);
        Call<Employee> listcall = employeeAPI.getEmployeeByID(Integer.parseInt(etSearchId.getText().toString()));

        listcall.enqueue(new Callback<Employee>() {
            @Override
            public void onResponse(Call<Employee> call, Response<Employee> response) {
                String content = "";
                content += "ID: " + response.body().getId() + "\n";
                content += "employee_name : " + response.body().getEmployee_name() + "\n";
                content += "employee_salary : " + response.body().getEmployee_salary() + "\n";
                content += "employee_age : " + response.body().getEmployee_age() + "\n";
                content += "profile_image : " + response.body().getProfile_image() + "\n";
                empRecord.append(content);
            }


            @Override
            public void onFailure(Call<Employee> call, Throwable t) {

            }
        });

    }
}
