package com.developer.diegoalves.peladapay.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import com.developer.diegoalves.peladapay.R;
import com.developer.diegoalves.peladapay.database.PlayerRepository;
import com.developer.diegoalves.peladapay.database.ValuesRepository;
import com.developer.diegoalves.peladapay.entities.Player;
import com.developer.diegoalves.peladapay.entities.Values;

public class PlayerActivity extends AppCompatActivity {

    Switch aSwitch;
    Player mPlayer;
    TextView simile;
    Values values;
    PlayerRepository playerRepository;
    ValuesRepository valuesRepository;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        simile = (TextView) findViewById(R.id.smile);
        if (getIntent() != null) {
            mPlayer = getIntent().getParcelableExtra("player");
            toolbar.setTitle(mPlayer.getName());
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        aSwitch = (Switch) findViewById(R.id.pay_switch);
        aSwitch.setTextOn("Pago");
        aSwitch.setTextOff("Não pago");
        if(mPlayer.getIsPaid() == 1) {
            simile.setText(":D");
            aSwitch.setChecked(true);
        } else {
            simile.setText(":(");
            aSwitch.setChecked(false);
        }
        values = new Values();
        playerRepository = new PlayerRepository(this);
        valuesRepository = new ValuesRepository(this);

        aSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    mPlayer.setIsPaid(1);
                    playerRepository.insertAndUpdate(mPlayer);
                    values.setCurrent(valuesRepository.getCurrent() + mPlayer.getAmountPaid());
                    values.setValueP(valuesRepository.getValuePlayer());
                    values.setValueM(valuesRepository.getValueMonth());
                    valuesRepository.insertAndUpdate(values);

                } else {
                    mPlayer.setIsPaid(0);
                    playerRepository.insertAndUpdate(mPlayer);
                    if (valuesRepository.getCurrent() >= valuesRepository.getValuePlayer()) {
                        values.setCurrent(valuesRepository.getCurrent() - valuesRepository.getValuePlayer());
                        values.setValueP(valuesRepository.getValuePlayer());
                        values.setValueM(valuesRepository.getValueMonth());
                    }
                    valuesRepository.insertAndUpdate(values);
                }
            }
        });
    }

}
