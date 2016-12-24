package powerrangers.zordon;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.lang.reflect.Array;

public class devices extends AppCompatActivity {

    private ListView lv;

    private String[] Places = {"keuken", "slaapkamer", "berging", "badkamer"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_devices);



        /*Intent intent = new Intent(getBaseContext(), devices.class);
        intent.putExtra("lijst", Places);
        startActivity(intent);
*/
        lv = (ListView) findViewById(R.id.lv);

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,
                Places);
        lv.setAdapter(arrayAdapter);
    }

    public void back(View view) {
        startActivity(new Intent(devices.this, MainActivity.class));
    }
}
