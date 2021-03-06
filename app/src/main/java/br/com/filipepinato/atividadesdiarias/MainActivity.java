package br.com.filipepinato.atividadesdiarias;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private EditText etNome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etNome = (EditText) findViewById(R.id.etNome);
    }

    public void login(View view) {
        if(etNome.getText().toString().equals("")) {
            Toast.makeText(this, "Por favor, coloque um nome", Toast.LENGTH_SHORT).show();
        } else {
            Intent i = new Intent(this, AtividadesActivity.class);
            i.putExtra("NOME", etNome.getText().toString());
            startActivity(i);
        }
    }
}
