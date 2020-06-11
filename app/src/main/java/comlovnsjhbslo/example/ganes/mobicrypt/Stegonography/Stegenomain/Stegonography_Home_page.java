package comlovnsjhbslo.example.ganes.mobicrypt.Stegonography.Stegenomain;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import comlovnsjhbslo.example.ganes.mobicrypt.R;

public class Stegonography_Home_page extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stegonography__home_page);


        Button encode = (Button) findViewById(R.id.encode_button);
        Button decode = (Button) findViewById(R.id.decode_button);

        encode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), Stegeno_encyptImage.class));
            }
        });

        decode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), Stegeno_decryptImage.class));
            }
        });


    }
}
