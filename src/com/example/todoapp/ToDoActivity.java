package com.example.todoapp;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.commons.io.FileUtils;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class ToDoActivity extends Activity {
	
	private ArrayList<String> todoItems;
	private ArrayAdapter<String> todoAdapter;
	private ListView lvItems;
	private EditText etEditText;
	private Button btnAdd;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_to_do);

        lvItems = (ListView) findViewById(R.id.lvItems);
        etEditText = (EditText) findViewById(R.id.etNewItem);
        btnAdd = (Button) findViewById(R.id.btnAdd);
        readItems();
        todoAdapter = new ArrayAdapter<String>(getBaseContext(), android.R.layout.simple_list_item_1, todoItems);
        lvItems.setAdapter(todoAdapter);
        setupListViewListener();
        
        
        
    }

    private void setupListViewListener() {
    	lvItems.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> adapter, View item, int pos, long id) {
				
				final int REQUEST_CODE = 20;
				CharSequence csItemTextCharSeq = todoItems.get(pos);
				// first parameter is the context, second is the class of the activity to launch
				Intent i = new Intent(ToDoActivity.this, EditItemActivity.class);
				i.putExtra("itemText", csItemTextCharSeq);
				i.putExtra("itemPosition", pos);
				startActivityForResult(i, REQUEST_CODE); // brings up the second activity
				
			}
    });
    	
		lvItems.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> adapter, View item, int pos, long id) {
				
				todoItems.remove(pos);
				todoAdapter.notifyDataSetChanged(); // makes sure that the adapter doesn't whine about change.
				writeItems();
				return true;
			}
			
		});
		
	}

	private void readItems() {
		
		File filesDir = getFilesDir();
		File todoFile = new File(filesDir, "todo.txt");
		try {
			todoItems = new ArrayList<String>(FileUtils.readLines(todoFile));
		} catch (IOException e) {
			todoItems = new ArrayList<String>();
		}

	}
	
	private void writeItems() {
		
		File filesDir = getFilesDir();
		File todoFile = new File(filesDir, "todo.txt");
		try {
			FileUtils.writeLines(todoFile, todoItems);
		} catch (IOException e){
			e.printStackTrace();
		}

	}
    
    public void onAddedItem(View v) {
    	
    	String itemText = etEditText.getText().toString();
    	if(!itemText.isEmpty()){
        	todoAdapter.add(itemText);
        	etEditText.setText("");
        	writeItems();
		}else{
			etEditText.setError("You can't do nothing!"); // fancy error message for edit texts
		}
    }

	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.to_do, menu);
        return true;
    }

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	  
	  if (resultCode == 40 && requestCode == 20) {
		  String itemText = data.getExtras().getString("itemText");// Extract text value from result extras
		  Integer returnedPosition = data.getExtras().getInt("itemPosition");
		  todoItems.set(returnedPosition, itemText);
		  todoAdapter.notifyDataSetChanged(); // makes sure that the adapter doesn't whine about change.
		  writeItems();
	  }
	} 
	
}
