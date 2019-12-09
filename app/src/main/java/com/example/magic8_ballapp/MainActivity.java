package com.example.magic8_ballapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;


public class MainActivity extends AppCompatActivity {
    String predictionTexts = "";
    String predictions[] = new String[]{"It is certain",
            "It is decidedly so",
            "Without a doubt",
            "Yes definitely",
            "You may rely on it",
            "As I see it yes",
            "Most likely",
            "Outlook good",
            "Yes",
            "Signs point to yes",
            "Reply hazy try again",
            "Ask again later",
            "Better not tell you now",
            "Cannot predict now",
            "Concentrate and ask again",
            "Don't count on it",
            "My reply is no",
            "My sources say no",
            "Outlook not so good",
            "Very doubtful"}; //Array with 20 elements : 19
    float gyroInt = 0;
    boolean gyroBool = false;
    SensorManager mngr;
    Sensor gyro;
    SensorEventListener listener;
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final TextView prediction = (TextView)findViewById(R.id.predictionText);
        final Button predictionButton = (Button) findViewById(R.id.predictionButtons);
        final TextView gyrox = (TextView) findViewById(R.id.gyrox);
        mngr = (SensorManager) getSystemService(Context.SENSOR_SERVICE); //creates sensor manager variable
        gyro = mngr.getDefaultSensor(Sensor.TYPE_GYROSCOPE); //creates gyroscopic sensor in app
        predictionButton.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                setTextPrediction(); //randomly generates next line
                String S = getTextPrediction();
                prediction.setText(getTextPrediction()); //sets the prediction to the TextView
            }
        });
        listener = new SensorEventListener() //constantly checks for gyroscopic sensor
        {
            @Override
            public void onAccuracyChanged(Sensor gyro, int accuracy)
            {

            }

            @Override
            public void onSensorChanged(SensorEvent event)
            {
                if (event.values[0] > 4) // If the current position is more than total value of 4,set prediction.
                {
                    setTextPrediction();
                    String S = getTextPrediction();
                    prediction.setText(getTextPrediction());
                }



            }
        };
    }
    @Override
    protected void onResume()
    {
        super.onResume();
        if (listener != null){
            mngr.registerListener(listener, gyro, SensorManager.SENSOR_DELAY_NORMAL); //starts the gyroscopic listener
        }
    }
    protected void onStop()
    {
        super.onStop();
        if(listener != null)
        {
            mngr.unregisterListener(listener); // stops gyroscopic sensor on closing
        }

    }
    protected void onDestroy()
    {
        super.onDestroy();
        if(listener != null)
        {
            mngr.unregisterListener(listener); // stops gyroscopic sensor on closing
        }

    }


    public void setTextPrediction()
    {
        int min = 0;
        int max = 19;
        int range = max - min + 1;
        int rand = (int)(Math.random()*range)+min;
        predictionTexts = predictions[rand];
    }
    public String getTextPrediction()
    {
        return predictionTexts;
    }
}
