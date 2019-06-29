package com.mycompany.myapp;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.widget.TextView;
import android.view.*;
import android.widget.*;
import android.os.*;
import android.view.animation.*;

public class MainActivity extends Activity {
	private TextView grivaty;
	private SensorManager sensorMgr = null;
	private float x,y,z;
	ThreeDLayout layout ;
	boolean aaa=false;
	int chux,chuy;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
hide();
        initUI();
		 layout = (ThreeDLayout) findViewById(R.id.mainThreeDLayout1);

		layout.setTouchable(false);

		layout.setTouchMode(ThreeDLayout.MODE_BOTH_X_Y);
		LinearLayout imageView = (LinearLayout) findViewById(R.id.mainLinearLayout1);
		//动画
		Animation animation = AnimationUtils.loadAnimation(this, R.drawable.an);
		LinearInterpolator lin = new LinearInterpolator();//设置动画匀速运动
		animation.setInterpolator(lin);
		imageView.startAnimation(animation);
    }
	public void hide(){
		if (getActionBar() != null){
			getActionBar().hide();
		}

		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
			//透明状态栏
			getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
		}
		if (Build.VERSION.SDK_INT > 11 && Build.VERSION.SDK_INT < 19) { // lower api
			View v = this.getWindow().getDecorView();
			v.setSystemUiVisibility(View.GONE);
		} else if (Build.VERSION.SDK_INT >= 19) {
			//for new api versions.
			View decorView = getWindow().getDecorView();
			int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
				| View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
			decorView.setSystemUiVisibility(uiOptions);
		}
	}
    private void initUI() {
		// TODO Auto-generated method stub
    	grivaty = (TextView)findViewById(R.id.grivaty);
    	//通过服务得到传感器管理对象
    	sensorMgr = (SensorManager) getSystemService(SENSOR_SERVICE);
    	//得到重力传感器实例
    	//TYPE_ACCELEROMETER 加速度传感器(重力传感器)类型。 
		//TYPE_ALL 描述所有类型的传感器。 
		//TYPE_GYROSCOPE 陀螺仪传感器类型 
		//TYPE_LIGHT 光传感器类型 
		//TYPE_MAGNETIC_FIELD 恒定磁场传感器类型。 
		//TYPE_ORIENTATION 方向传感器类型。 
		//TYPE_PRESSURE 描述一个恒定的压力传感器类型 
		//TYPE_PROXIMITY 常量描述型接近传感器 
		//TYPE_TEMPERATURE 温度传感器类型描述 
        final Sensor sensor = sensorMgr.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);       
        SensorEventListener lsn = new SensorEventListener() {
            @SuppressWarnings("deprecation")
            //传感器获取值改变时响应此函数
            public void onSensorChanged(SensorEvent e) {
                x = e.values[SensorManager.DATA_X];
                y = e.values[SensorManager.DATA_Y];
                z = e.values[SensorManager.DATA_Z];
				if(aaa){
					chuy=((int)e.values[SensorManager.DATA_X])*10;
					chux=((int)e.values[SensorManager.DATA_Y])*10;
					aaa=true;
				}
				
                // getsensor();
                grivaty.setText("x=" +(int) x + "  y=" +(int) y + "  z=" + (int)z );//左右x值，左正右负，前后位y值，前负后正
				float xx=(x)*10;
				float yy=(y)*10;
	
		
				layout.setcamx(yy/5*4);
				layout.setcamy(xx/5*4);
		layout.setTranslationX(xx*10);
				layout.setTranslationY(yy*10);
            }

            public void onAccuracyChanged(Sensor s, int accuracy) {

            }
        };


        //注册listener，第三个参数是检测的精确度
        sensorMgr.registerListener(lsn, sensor, SensorManager.SENSOR_DELAY_GAME);                  
	} 
	public void p(View v){
		Toast.makeText(this,"",10).show();
	}
}
