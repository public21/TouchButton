package com.example.touchbutton;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.touchbutton.ScaleButton.OnClickListener;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		((ScaleButton)findViewById(R.id.button)).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				Toast.makeText(MainActivity.this, "µã»÷ÊÂ¼þ", Toast.LENGTH_LONG).show();
			}
		}); 
		
	}

}
