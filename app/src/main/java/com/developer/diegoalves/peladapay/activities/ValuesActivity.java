package com.developer.diegoalves.peladapay.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;

import com.developer.diegoalves.peladapay.R;
import com.developer.diegoalves.peladapay.database.ValuesRepository;
import com.developer.diegoalves.peladapay.entities.Values;

public class ValuesActivity extends AppCompatActivity {

    String vStringM;
    String vStringP;
    ValuesRepository valuesRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_values);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Valores");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        final TextView textView = (TextView) findViewById(R.id.textView6);
        textView.setText("Definir valor mensal a ser pago");
        final TextView tvValue = (TextView) findViewById(R.id.textView7);
        TextView tvValuePlayer = (TextView) findViewById(R.id.tvValuePlayer);
        tvValuePlayer.setText("Valor a ser pago por jogador");
        final TextView tvValuePlayerdef = (TextView) findViewById(R.id.tvValuePlayerDef);

        TextView tvdef1 = (TextView) findViewById(R.id.textView9);
        TextView tvdef2 = (TextView) findViewById(R.id.textView10);


        valuesRepository = new ValuesRepository(this);


        SeekBar seekBar = (SeekBar) findViewById(R.id.seekBarValue);
        if(valuesRepository.getValueMonth() > 0) {
            Double val = valuesRepository.getValueMonth();
            vStringM = String.valueOf(val);
            tvValue.setText(vStringM + "0 RS");
        } else {
            tvdef1.setVisibility(View.INVISIBLE);
        }
        if(valuesRepository.getValuePlayer() > 0) {
            Double val = valuesRepository.getValuePlayer();
            vStringP = String.valueOf(val);
            tvValuePlayerdef.setText(vStringP + "0 RS");
        } else {
            tvdef2.setVisibility(View.INVISIBLE);
        }
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                int vInt = progress;
                vStringM = String.valueOf(vInt);
                tvValue.setText(vStringM +".00 RS");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                Values v = new Values();
                v.setCurrent(valuesRepository.getCurrent());
                v.setValueP(valuesRepository.getValuePlayer());
                v.setValueM(Double.valueOf(vStringM));
                valuesRepository.insertAndUpdate(v);
            }
        });

        // player value

        SeekBar seekBarP = (SeekBar) findViewById(R.id.seekBarPlayer);
        seekBarP.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                int vInt = progress;
                vStringP = String.valueOf(vInt);
                tvValuePlayerdef.setText(vStringP +".00 RS");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                Values v = new Values();
                v.setCurrent(valuesRepository.getCurrent());
                v.setValueP(Double.valueOf(vStringP));
                v.setValueM(valuesRepository.getValueMonth());
                valuesRepository.insertAndUpdate(v);
            }
        });

    }

}
