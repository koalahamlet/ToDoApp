package com.example.todoapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class EditItemActivity extends Activity {
	private EditText etEditItem; 
	private Button btnSave;
	
	private Integer itemPosition;
	private String itemText; 
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit_item);
		etEditItem = (EditText) findViewById(R.id.etEditItem);
        btnSave = (Button) findViewById(R.id.btnSave);
        itemText = getIntent().getStringExtra("itemText");
        itemPosition = getIntent().getIntExtra("itemPosition", 0);
         
        etEditItem.setText(itemText);
	}

	
	public void onSubmit(View v){
		
		itemText = etEditItem.getText().toString();
		
		if(!itemText.isEmpty()){
		
			final int resultOK = 40;
			// FirstActivity, launching an activity for a result
			Intent i = new Intent();
			i.putExtra("itemText", itemText); // pass arbitrary data to launched activity
			i.putExtra("itemPosition", itemPosition);
			setResult(resultOK, i);
			finish();
		}else{
			etEditItem.setError("You can't do nothing!"); // fancy error message for edit texts
		}
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.edit_item, menu);
		return true;
	}

}
