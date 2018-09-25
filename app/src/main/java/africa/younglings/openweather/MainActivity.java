package africa.younglings.openweather;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextPaint;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    public static EditText et_city_name;
    TextView error_message;
    Button btn_get_weather;

    String city_name = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        et_city_name = (EditText) findViewById(R.id.et_city_name);
        error_message=(TextView)findViewById(R.id.error_message);
        btn_get_weather = (Button) findViewById(R.id.btn_get_weather);

        btn_get_weather.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                city_name = et_city_name.getText().toString();
                if (city_name.equals("")) {

                    error_message.setHint("Please enter city name");
                } else {
                    WeatherService process = new WeatherService();
                    process.execute();
                    Intent weather_details = new Intent(MainActivity.this, WeatherDetailsActivity.class);
                    startActivity(weather_details);
                }
            }
        });


    }
}
