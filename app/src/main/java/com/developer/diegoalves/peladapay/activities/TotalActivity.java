package com.developer.diegoalves.peladapay.activities;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;
import android.widget.Toast;

import com.developer.diegoalves.peladapay.R;
import com.developer.diegoalves.peladapay.database.PlayerRepository;
import com.developer.diegoalves.peladapay.database.ValuesRepository;

public class TotalActivity extends AppCompatActivity {

    TextView tvCurrent;
    TextView tvMeta;
    TextView tvStatus;
    TextView tvChange;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_total);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("andamento");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ValuesRepository valuesRepository = new ValuesRepository(this);
        if(valuesRepository.getValueMonth() == 0) {
            Toast.makeText(this, "Defina um valor para meta mensal", Toast.LENGTH_SHORT).show();
        }
        tvCurrent = (TextView) findViewById(R.id.tvCurrent);
        tvCurrent.setText(String.valueOf(valuesRepository.getCurrent()) + "0 R$");
        tvMeta = (TextView) findViewById(R.id.tvMeta);
        tvMeta.setText(String.valueOf(valuesRepository.getValueMonth() + "0 R$"));
        tvStatus = (TextView) findViewById(R.id.tvStatus);
        tvChange = (TextView) findViewById(R.id.tvChange);
        if (valuesRepository.getCurrent() <= valuesRepository.getValueMonth()) {
            double valor = valuesRepository.getValueMonth() - valuesRepository.getCurrent();
            tvStatus.setText(String.valueOf(valor) + "0 R$");
            tvChange.setText("FALTANDO PARA META");
            tvStatus.setTextColor(ContextCompat.getColor(this, R.color.red));
        } else {
            double valor = valuesRepository.getCurrent() - valuesRepository.getValueMonth();
            tvChange.setText("ALÉM DA META");
            tvStatus.setTextColor(ContextCompat.getColor(this, R.color.green));
            tvStatus.setText(String.valueOf(valor) + "0 R$");
        }

        PlayerRepository playerRepository = new PlayerRepository(this);
        int qtde = playerRepository.listAll().size();
        TextView text = (TextView) findViewById(R.id.text);
        text.setText("Valor alcançável "+ qtde + " jogadores");
        TextView alcancavel = (TextView) findViewById(R.id.valorAlcancavel);
        alcancavel.setText(String.valueOf(qtde * valuesRepository.getValuePlayer()) + "0 R$");
    }

}
