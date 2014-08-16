package com.the9pointers.simpleflashlight;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.hardware.Camera.Parameters;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.ToggleButton;

public class MainActivity extends Activity {

	private Camera camera;
	private ToggleButton button;
	private final Context context=this;
	
	
    @Override
	protected void onStop() {
		super.onStop();
		if(camera!=null){
			camera.release();
		}
	}

	@Override
    protected void onCreate(Bundle savedInstanceState) {	
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button=(ToggleButton) findViewById(R.id.toggleButton);
        final PackageManager pm=context.getPackageManager();
        if(!isCameraSupported(pm)){
        	AlertDialog alertDialog=new AlertDialog.Builder(context).create();
        	alertDialog.setTitle("No Camera!");
        	alertDialog.setMessage("This device doesn't support Camera.");
        	alertDialog.setButton(RESULT_OK, "OK",new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface arg0, int arg1) {
				Log.e("Error","Device doesn't support Camera");
				}
			});
        	alertDialog.show();
        }
        camera=Camera.open();
    }

	public void onToggleClicked(View view){
		PackageManager pm=context.getPackageManager();
		final Parameters p=camera.getParameters();
		if(isFlashSupported(pm)){
			boolean on=((ToggleButton)view).isChecked();
			if(on){
				Log.i("Info","Flash turned On");
				p.setFlashMode(Parameters.FLASH_MODE_TORCH);
				camera.setParameters(p);
				camera.stopPreview();
			} else {
			Log.i("Info","Flash turned off");
			p.setFlashMode(Parameters.FLASH_MODE_OFF);
			camera.setParameters(p);
			camera.stopPreview();
		}
	} else {
			button.setChecked(false);
			AlertDialog alertDialog =new AlertDialog.Builder(context).create();
			alertDialog.setTitle("No Camera Flash!");
        	alertDialog.setMessage("This device's Camera doesn't support Flash.");
        	alertDialog.setButton(RESULT_OK, "OK",new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface arg0, int arg1) {
				Log.e("Error","Camera doesn't support Flash");
				}
			});
        	alertDialog.show();
        }
			
		}
	
	private boolean isFlashSupported(PackageManager packagemanager){
		if(packagemanager.hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH)){
			return true;
		}
		return false;
	}
	
	private boolean isCameraSupported(PackageManager packagemanager){
		if(packagemanager.hasSystemFeature(PackageManager.FEATURE_CAMERA)){
			return true;
		}
		return false;
	}
	
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
    
}
