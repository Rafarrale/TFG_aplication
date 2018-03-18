package com.example.rafa.tfg.fragments;

import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.rafa.tfg.R;
import com.example.rafa.tfg.adapters.SeccionesAdapter;
import com.example.rafa.tfg.clases.Utilidades;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ContenedorFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ContenedorFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ContenedorFragment extends Fragment {


    private OnFragmentInteractionListener mListener;

    private AppBarLayout appBar;
    private TabLayout pestañas;
    private ViewPager viewPager;
    View vista;

    public ContenedorFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ContenedorFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ContenedorFragment newInstance(String param1, String param2) {
        ContenedorFragment fragment = new ContenedorFragment();
        Bundle args = new Bundle();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        vista = inflater.inflate(R.layout.fragment_cotenedor, container, false);


        if(Utilidades.rotacion == 0){
            View parent = (View) container.getParent();
            if (appBar == null || Utilidades.regresaDisp){
                Utilidades.regresaDisp = false;
                appBar = parent.findViewById(R.id.appBar);
                pestañas = new TabLayout(getActivity());
                pestañas.setTabTextColors(Color.parseColor("#FFFFFFFF"),Color.parseColor("#FFFFFFFF"));
                appBar.addView(pestañas);

                viewPager = vista.findViewById(R.id.idViewPagerInformacion);
                llenarViewPager(viewPager);

                viewPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener(){
                    @Override
                    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                        super.onPageScrolled(position, positionOffset, positionOffsetPixels);
                    }
                });
                pestañas.setupWithViewPager(viewPager);
                pestañas.setTabGravity(TabLayout.GRAVITY_FILL);

            }else{
                Utilidades.rotacion = 1;
            }
        }



        return vista;
    }

    private void llenarViewPager(ViewPager viewPager) {
        SeccionesAdapter adapter = new SeccionesAdapter(getFragmentManager());
        adapter.addFragment(new AmarilloFragment(),"Control");
        adapter.addFragment(new GreenFragment(),"Historial Uso");
        adapter.addFragment(new RojoFragment(),"Cámaras");

        viewPager.setAdapter(adapter);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if(Utilidades.rotacion == 0){
            appBar.removeView(pestañas);
        }

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
