package com.example.rafa.tfg;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;

public class PrincipalActivity extends AppCompatActivity implements View.OnClickListener{

    private CardView card1,card2,card3,card4,card5;
    private int vale1,vale2,vale3,vale4,vale5 = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);
        //definiendo cards
        card1 = findViewById(R.id.card1);
        card2 = findViewById(R.id.card2);
        card3 = findViewById(R.id.card3);
        card4 = findViewById(R.id.card4);
        card5 = findViewById(R.id.card5);

        // Add click listener to the cards
        card1.setOnClickListener(this);
        card2.setOnClickListener(this);
        card3.setOnClickListener(this);
        card4.setOnClickListener(this);
        card5.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        Intent i;

        switch(v.getId()){
            case R.id.card1:
                if(vale1 == 0) {
                    card1.setBackgroundColor(Color.parseColor("#AF7C4DFF"));
                    card2.setBackgroundColor(Color.parseColor("#FFFFFFFF"));
                    card3.setBackgroundColor(Color.parseColor("#FFFFFFFF"));
                    card4.setBackgroundColor(Color.parseColor("#FFFFFFFF"));
                    card5.setBackgroundColor(Color.parseColor("#FFFFFFFF"));
                    vale1 = 1;
                    vale2 = 0;
                    vale3 = 0;
                    vale4 = 0;
                    vale5 = 0;
                }else{
                    card1.setBackgroundColor(Color.parseColor("#FFFFFFFF"));
                    vale1 = 0;
                }
                //i = new Intent(this,MisDatosActivity.class);
                //startActivity(i);
                break;

            case R.id.card2:

                if(vale2 == 0) {
                    card2.setBackgroundColor(Color.parseColor("#AFFF4081"));
                    card1.setBackgroundColor(Color.parseColor("#FFFFFFFF"));
                    card3.setBackgroundColor(Color.parseColor("#FFFFFFFF"));
                    card4.setBackgroundColor(Color.parseColor("#FFFFFFFF"));
                    card5.setBackgroundColor(Color.parseColor("#FFFFFFFF"));
                    vale2 = 1;
                    vale1 = 0;
                    vale3 = 0;
                    vale4 = 0;
                    vale5 = 0;
                }else{
                    card2.setBackgroundColor(Color.parseColor("#FFFFFFFF"));
                    vale2 = 0;
                }

                /*
                i = new Intent(this,);
                startActivity(i);
                */
                break;
            case R.id.card3:

                if(vale3 == 0) {
                    card3.setBackgroundColor(Color.parseColor("#AF00BFA5"));
                    card2.setBackgroundColor(Color.parseColor("#FFFFFFFF"));
                    card1.setBackgroundColor(Color.parseColor("#FFFFFFFF"));
                    card4.setBackgroundColor(Color.parseColor("#FFFFFFFF"));
                    card5.setBackgroundColor(Color.parseColor("#FFFFFFFF"));
                    vale3 = 1;
                    vale1 = 0;
                    vale2 = 0;
                    vale4 = 0;
                    vale5 = 0;
                }else{
                    card3.setBackgroundColor(Color.parseColor("#FFFFFFFF"));
                    vale3 = 0;
                }

                /*
                i = new Intent(this,);
                startActivity(i);
                */
                break;
            case R.id.card4:

                if(vale4 == 0) {
                    card4.setBackgroundColor(Color.parseColor("#AFFFB300"));
                    card2.setBackgroundColor(Color.parseColor("#FFFFFFFF"));
                    card3.setBackgroundColor(Color.parseColor("#FFFFFFFF"));
                    card5.setBackgroundColor(Color.parseColor("#FFFFFFFF"));
                    card1.setBackgroundColor(Color.parseColor("#FFFFFFFF"));
                    vale4 = 1;
                    vale1 = 0;
                    vale3 = 0;
                    vale2 = 0;
                    vale5 = 0;
                }else{
                    card4.setBackgroundColor(Color.parseColor("#FFFFFFFF"));
                    vale4 = 0;
                }

                /*
                i = new Intent(this,);
                startActivity(i);
                */
                break;
            case R.id.card5:

                if(vale5 == 0) {
                    card5.setBackgroundColor(Color.parseColor("#AFFA0008"));
                    card2.setBackgroundColor(Color.parseColor("#FFFFFFFF"));
                    card3.setBackgroundColor(Color.parseColor("#FFFFFFFF"));
                    card4.setBackgroundColor(Color.parseColor("#FFFFFFFF"));
                    card1.setBackgroundColor(Color.parseColor("#FFFFFFFF"));
                    vale5 = 1;
                    vale1 = 0;
                    vale3 = 0;
                    vale4 = 0;
                    vale2 = 0;
                }else{
                    card5.setBackgroundColor(Color.parseColor("#FFFFFFFF"));
                    vale5 = 0;
                }
                /*
                i = new Intent(this,);
                startActivity(i);
                */
                break;
            default:
                break;
        }
    }
}

