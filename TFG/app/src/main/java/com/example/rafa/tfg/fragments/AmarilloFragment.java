package com.example.rafa.tfg.fragments;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.rafa.tfg.R;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link AmarilloFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link AmarilloFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AmarilloFragment extends Fragment implements View.OnClickListener{

    private CardView card1,card2,card3,card4,card5;
    private int vale1,vale2,vale3,vale4,vale5 = 0;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public AmarilloFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AmarilloFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AmarilloFragment newInstance(String param1, String param2) {
        AmarilloFragment fragment = new AmarilloFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_amarillo, container, false);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        card1 = getView().findViewById(R.id.card1);
        card2 = getView().findViewById(R.id.card2);
        card3 = getView().findViewById(R.id.card3);
        card4 = getView().findViewById(R.id.card4);
        card5 = getView().findViewById(R.id.card5);

        // Add click listener to the cards
        card1.setOnClickListener(this);
        card2.setOnClickListener(this);
        card3.setOnClickListener(this);
        card4.setOnClickListener(this);
        card5.setOnClickListener(this);

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onClick(View v) {
        Intent i;

        switch(v.getId()){
            case R.id.card1:
                if(vale1 == 0) {
                    card1.setBackgroundColor(Color.parseColor("#8C7C4DFF"));
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
                    card2.setBackgroundColor(Color.parseColor("#8CFF4081"));
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
                    card3.setBackgroundColor(Color.parseColor("#8C00BFA5"));
                    //i = new Intent(PrincipalActivity.this,sensorActivity.class);
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
                    card4.setBackgroundColor(Color.parseColor("#8CFFB300"));
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
                    card5.setBackgroundColor(Color.parseColor("#8CFA0008"));
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

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}