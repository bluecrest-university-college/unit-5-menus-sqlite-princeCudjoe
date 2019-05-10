package com.example.friendscom;

 import android.content.ClipData;
 import android.support.v4.view.MenuItemCompat;
 import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextMenu;
 import android.view.Menu;
 import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SearchView;
 import android.widget.SearchView.OnQueryTextListener;
 import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
 import java.util.logging.Handler;

public class MainActivity extends AppCompatActivity {
    EditText edtId, edtName, edtEmail;
    Button btnAdd, btnUpdate, btnDelete;


    ListView lstPersons;

    List<Person> data = new ArrayList();
    DataBaseHelper database;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        database = new DataBaseHelper(this);
        btnAdd = (Button) findViewById(R.id.btnAdd);
        btnUpdate = (Button) findViewById(R.id.btnUpdate);
        btnDelete = (Button) findViewById(R.id.btnDelete);


        lstPersons = (ListView) findViewById(R.id.list);

        edtId = (EditText) findViewById(R.id.edtId);
        edtEmail = (EditText) findViewById(R.id.edtEmail);
        edtName = (EditText) findViewById(R.id.edtName);


        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Person person = new Person(Integer.parseInt(edtId.getText().toString()), edtName.getText().toString(), edtEmail.getText().toString());
                database.addPerson(person);
                refreshData();

            }
        });
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Person person = new Person(Integer.parseInt(edtId.getText().toString()), edtName.getText().toString(), edtEmail.getText().toString());
                database.updatePerson(person);
                refreshData();

            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Person person = new Person(Integer.parseInt(edtId.getText().toString()), edtName.getText().toString(), edtEmail.getText().toString());
                database.deletePerson(person);
                refreshData();
            }
        });

        refreshData();

    }


    private void refreshData() {
        data = database.getAllPerson();
        PersonAdapter adapter = new PersonAdapter(MainActivity.this, data, edtId, edtName, edtEmail);
        lstPersons.setAdapter(adapter);



    }


    }






