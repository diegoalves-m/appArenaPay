package com.developer.diegoalves.peladapay.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.developer.diegoalves.peladapay.R;
import com.developer.diegoalves.peladapay.database.PlayerRepository;
import com.developer.diegoalves.peladapay.database.ValuesRepository;
import com.developer.diegoalves.peladapay.entities.Player;

public class RegisterActivity extends AppCompatActivity {

    EditText etName;
    String nome;
    Player player;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("add");
        setSupportActionBar(toolbar);

        etName = (EditText) findViewById(R.id.etName);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    public boolean dataValid() {
        nome = etName.getText().toString();
        boolean valid = true;
        if(nome.isEmpty()) {
            valid = false;
            etName.setError("Campo necessário");
            etName.setFocusable(true);
        }

        return valid;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        ValuesRepository rep = new ValuesRepository(this);
        if (id == android.R.id.home) {
            if(dataValid()) {
                PlayerRepository repository = new PlayerRepository(this);
                player = new Player();
                player.setName(nome);
                player.setAmountPaid(rep.getValuePlayer());
                player.setIsPaid(0);
                repository.insertAndUpdate(player);
                Toast.makeText(this, "SALVO", Toast.LENGTH_SHORT).show();
                finish();
            }
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
