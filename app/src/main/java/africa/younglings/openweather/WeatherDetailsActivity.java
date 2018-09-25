package africa.younglings.openweather;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;



public class WeatherDetailsActivity extends AppCompatActivity {

    public static TextView tv_cityName;
    public static TextView tv_temperature;
    public static TextView tv_date_time;
    public static TextView tv_wind;
    public static TextView tv_cloudiness;
    public static TextView tv_pressure;
    public static TextView tv_humidity;
    public static TextView tv_sunrise;
    public static TextView tv_sunset;
    public static TextView tv_geo_coords;
    public static ImageView imageView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_details);



        tv_cityName = (TextView) findViewById(R.id.tv_cityName);
        tv_temperature = (TextView) findViewById(R.id.tv_temperature);
        tv_date_time = (TextView) findViewById(R.id.tv_date_time);
        tv_wind = (TextView) findViewById(R.id.tv_wind);
        tv_cloudiness = (TextView) findViewById(R.id.tv_cloudiness);
        tv_pressure = (TextView) findViewById(R.id.tv_pressure);
        tv_humidity = (TextView) findViewById(R.id.tv_humidity);
        tv_sunrise = (TextView) findViewById(R.id.tv_sunrise);
        tv_sunset = (TextView) findViewById(R.id.tv_sunset);
        tv_geo_coords = (TextView) findViewById(R.id.tv_geo_coords);
        imageView = (ImageView) findViewById(R.id.imageView);

    }
}
