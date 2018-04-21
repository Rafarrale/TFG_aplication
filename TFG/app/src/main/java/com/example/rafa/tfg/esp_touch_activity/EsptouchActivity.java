package com.example.rafa.tfg.esp_touch_activity;

import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.espressif.iot.esptouch.EsptouchTask;
import com.espressif.iot.esptouch.IEsptouchListener;
import com.espressif.iot.esptouch.IEsptouchResult;
import com.espressif.iot.esptouch.IEsptouchTask;
import com.espressif.iot.esptouch.task.__IEsptouchTask;
import com.espressif.iot.esptouch.util.EspAES;
import com.example.rafa.tfg.MainActivity;
import com.example.rafa.tfg.R;
import com.example.rafa.tfg.Registro;
import com.example.rafa.tfg.adapters.DispositivosAdapter;
import com.example.rafa.tfg.adapters.DispositivosDataAdapter;
import com.example.rafa.tfg.adapters.DispositivosDataAdapterAnade;
import com.example.rafa.tfg.fragments.DispositivosFragment;
import com.example.rafa.tfg.rest.RestImpl;
import com.example.rafa.tfg.rest.RestInterface;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;


public class EsptouchActivity extends AppCompatActivity implements OnClickListener {

    private static final String TAG = "EsptouchActivity";

    private TextView mTvApSsid;

    private EditText mEdtApPassword;

    private Button mBtnConfirm;
    private Button mBtnDisp;
    private EspWifiAdminSimple mWifiAdmin;
    private RecyclerView mDispositivosAnadeRecycler;
    private SwipeRefreshLayout swipeRefreshLayoutDispNuevo;
    private DispositivosDataAdapterAnade dispositivosDataAdapterAnade;


    private Spinner mSpinnerTaskCount;
    private IEsptouchListener myListener = new IEsptouchListener() {

        @Override
        public void onEsptouchResultAdded(final IEsptouchResult result) {
            onEsptoucResultAddedPerform(result);
        }
    };

    private EsptouchAsyncTask3 mTask;

    private BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action == null) {
                return;
            }

            switch (action) {
                case WifiManager.NETWORK_STATE_CHANGED_ACTION:
                    NetworkInfo ni = intent.getParcelableExtra(WifiManager.EXTRA_NETWORK_INFO);
                    if (ni != null && !ni.isConnected()) {
                        if (mTask != null) {
                            mTask.cancelEsptouch();
                            mTask = null;
                            new AlertDialog.Builder(EsptouchActivity.this)
                                    .setMessage("Wifi desconectado o cambiado")
                                    .setNegativeButton(android.R.string.cancel, null)
                                    .show();
                        }
                    }
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.esptouch_activity);

        mWifiAdmin = new EspWifiAdminSimple(this);
        mTvApSsid = findViewById(R.id.tvApSssidConnected);
        mEdtApPassword = findViewById(R.id.edtApPassword);
        mBtnConfirm = findViewById(R.id.btnConfirm);
        mBtnDisp = findViewById(R.id.btnMostrarDisp);
        mBtnConfirm.setOnClickListener(this);
        initSpinner();

        TextView versionTV = findViewById(R.id.version_tv);
        versionTV.setText(IEsptouchTask.ESPTOUCH_VERSION);

        IntentFilter filter = new IntentFilter(WifiManager.NETWORK_STATE_CHANGED_ACTION);
        registerReceiver(mReceiver, filter);

        /** AlertDialog para agregar dispositivos*/
        mBtnDisp.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                    android.app.AlertDialog.Builder alertBuilder = new android.app.AlertDialog.Builder(EsptouchActivity.this);
                    View mView = getLayoutInflater().inflate(R.layout.anade_dispositivos, null);
                    mDispositivosAnadeRecycler = mView.findViewById(R.id.recyclerDispositivosAnade);
                    swipeRefreshLayoutDispNuevo = mView.findViewById(R.id.swipe_refresh_layout_dispositivos_nuevos);
                    swipeRefreshLayoutDispNuevo.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                        @Override
                        public void onRefresh() {
                            swipeRefreshLayoutDispNuevo.setRefreshing(true);
                            DispDataTaskNuevosDispositivos dispDataTaskNuevosDispositivos = new DispDataTaskNuevosDispositivos();
                            dispDataTaskNuevosDispositivos.execute();
                        }
                    });

                    alertBuilder.setView(mView);
                    android.app.AlertDialog alert = alertBuilder.create();

                    dispositivosDataAdapterAnade = new DispositivosDataAdapterAnade(EsptouchActivity.this, new ArrayList<DispositivosAdapter>());
                    mDispositivosAnadeRecycler.setAdapter(dispositivosDataAdapterAnade);
                    mDispositivosAnadeRecycler.setLayoutManager(new LinearLayoutManager(EsptouchActivity.this, LinearLayoutManager.VERTICAL, false));

                    dispositivosDataAdapterAnade.setOnItemClickListener(new DispositivosDataAdapter.OnItemClickListener() {
                        @Override
                        public void onItemClick(DispositivosAdapter clickedAppointment) {
                            Toast.makeText(EsptouchActivity.this, "Añadimos el disp" + clickedAppointment.get_id(), Toast.LENGTH_SHORT).show();
                        }
                    });
                    DispDataTaskNuevosDispositivos dispDataTaskNuevosDispositivos = new DispDataTaskNuevosDispositivos();
                    dispDataTaskNuevosDispositivos.execute();
                    alert.show();
            }
        });
    }

    private void initSpinner() {
        mSpinnerTaskCount = findViewById(R.id.spinnerTaskResultCount);
        int[] spinnerItemsInt = getResources().getIntArray(R.array.taskResultCount);
        int length = spinnerItemsInt.length;
        Integer[] spinnerItemsInteger = new Integer[length];
        for (int i = 0; i < length; i++) {
            spinnerItemsInteger[i] = spinnerItemsInt[i];
        }
        ArrayAdapter<Integer> adapter = new ArrayAdapter<Integer>(this,
                android.R.layout.simple_list_item_1, spinnerItemsInteger);
        mSpinnerTaskCount.setAdapter(adapter);
        mSpinnerTaskCount.setSelection(1);
    }

    @Override
    protected void onResume() {
        super.onResume();
        // display the connected ap's ssid
        String apSsid = mWifiAdmin.getWifiConnectedSsid();
        if (apSsid != null) {
            mTvApSsid.setText(apSsid);
        } else {
            mTvApSsid.setText("");
        }
        // check whether the wifi is connected
        boolean isApSsidEmpty = TextUtils.isEmpty(apSsid);
        mBtnConfirm.setEnabled(!isApSsidEmpty);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        unregisterReceiver(mReceiver);
    }

    @Override
    public void onClick(View v) {
        if (v == mBtnConfirm) {
            String apSsid = mTvApSsid.getText().toString();
            String apPassword = mEdtApPassword.getText().toString();
            String apBssid = mWifiAdmin.getWifiConnectedBssid();
            String taskResultCountStr = Integer.toString(mSpinnerTaskCount
                    .getSelectedItemPosition());
            if (__IEsptouchTask.DEBUG) {
                Log.d(TAG, "mBtnConfirm is clicked, mEdtApSsid = " + apSsid
                        + ", " + " mEdtApPassword = " + apPassword);
            }
            if(mTask != null) {
                mTask.cancelEsptouch();
            }
            mTask = new EsptouchAsyncTask3();
            mTask.execute(apSsid, apBssid, apPassword, taskResultCountStr);
        }
    }

    private void onEsptoucResultAddedPerform(final IEsptouchResult result) {
        runOnUiThread(new Runnable() {

            @Override
            public void run() {
                String text = result.getBssid() + " esta conectado al wifi";
                Toast.makeText(EsptouchActivity.this, text,
                        Toast.LENGTH_LONG).show();
            }

        });
    }

    private class EsptouchAsyncTask3 extends AsyncTask<String, Void, List<IEsptouchResult>> {

        // without the lock, if the user tap confirm and cancel quickly enough,
        // the bug will arise. the reason is follows:
        // 0. task is starting created, but not finished
        // 1. the task is cancel for the task hasn't been created, it do nothing
        // 2. task is created
        // 3. Oops, the task should be cancelled, but it is running
        private final Object mLock = new Object();
        private ProgressDialog mProgressDialog;
        private IEsptouchTask mEsptouchTask;

        public void cancelEsptouch() {
            cancel(true);
            if (mProgressDialog != null) {
                mProgressDialog.dismiss();
            }
            if (mEsptouchTask != null) {
                mEsptouchTask.interrupt();
            }
        }

        @Override
        protected void onPreExecute() {
            mProgressDialog = new ProgressDialog(EsptouchActivity.this);
            mProgressDialog
                    .setMessage("La aplicación esta configurando los dispositivos, por favor espere ...");
            mProgressDialog.setCanceledOnTouchOutside(false);
            mProgressDialog.setOnCancelListener(new OnCancelListener() {
                @Override
                public void onCancel(DialogInterface dialog) {
                    synchronized (mLock) {
                        if (__IEsptouchTask.DEBUG) {
                            Log.i(TAG, "progress dialog is canceled");
                        }
                        if (mEsptouchTask != null) {
                            mEsptouchTask.interrupt();
                        }
                    }
                }
            });
            mProgressDialog.setButton(DialogInterface.BUTTON_POSITIVE,
                    "Esperando...", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    });
            mProgressDialog.show();
            mProgressDialog.getButton(DialogInterface.BUTTON_POSITIVE)
                    .setEnabled(false);
        }

        @Override
        protected List<IEsptouchResult> doInBackground(String... params) {
            int taskResultCount = -1;
            synchronized (mLock) {
                // !!!NOTICE
                String apSsid = mWifiAdmin.getWifiConnectedSsidAscii(params[0]);
                String apBssid = params[1];
                String apPassword = params[2];
                String taskResultCountStr = params[3];
                taskResultCount = Integer.parseInt(taskResultCountStr);
                boolean useAes = false;
                if (useAes) {
                    byte[] secretKey = "1234567890123456".getBytes(); // TODO modify your own key
                    EspAES aes = new EspAES(secretKey);
                    mEsptouchTask = new EsptouchTask(apSsid, apBssid, apPassword, aes, EsptouchActivity.this);
                } else {
                    mEsptouchTask = new EsptouchTask(apSsid, apBssid, apPassword, null, EsptouchActivity.this);
                }
                mEsptouchTask.setEsptouchListener(myListener);
            }
            List<IEsptouchResult> resultList = mEsptouchTask.executeForResults(taskResultCount);
            return resultList;
        }

        @Override
        protected void onPostExecute(List<IEsptouchResult> result) {
            mProgressDialog.getButton(DialogInterface.BUTTON_POSITIVE)
                    .setEnabled(true);
            mProgressDialog.getButton(DialogInterface.BUTTON_POSITIVE).setText(
                    "OK");
            if (result == null) {
                mProgressDialog.setMessage("La tarea falló");
                return;
            }

            IEsptouchResult firstResult = result.get(0);
            // check whether the task is cancelled and no results received
            if (!firstResult.isCancelled()) {
                int count = 0;
                // max results to be displayed, if it is more than maxDisplayCount,
                // just show the count of redundant ones
                final int maxDisplayCount = 5;
                // the task received some results including cancelled while
                // executing before receiving enough results
                if (firstResult.isSuc()) {
                    StringBuilder sb = new StringBuilder();
                    for (IEsptouchResult resultInList : result) {
                        sb.append("Asociado con éxito, bssid = "
                                + resultInList.getBssid()
                                + ",InetAddress = "
                                + resultInList.getInetAddress()
                                .getHostAddress() + "\n");
                        count++;
                        if (count >= maxDisplayCount) {
                            break;
                        }
                    }
                    if (count < result.size()) {
                        sb.append("\nHay " + (result.size() - count)
                                + " mas resultado(s) sin mostrarse\n");
                    }
                    mProgressDialog.setMessage(sb.toString());
                } else {
                    mProgressDialog.setMessage("La operacion falló");
                }
            }
        }
    }

    public class DispDataTaskNuevosDispositivos extends AsyncTask<Void,Void,List<DispositivosAdapter>>{

        @Override
        protected List<DispositivosAdapter> doInBackground(Void... voids) {
            List<DispositivosAdapter> res = new ArrayList<>();
            RestInterface rest = RestImpl.getRestInstance();
            Call<List<DispositivosAdapter>> response = rest.getTodosDispositivosNuevos();

            try{
                Response<List<DispositivosAdapter>> resp = response.execute();
                if(resp.isSuccessful()){
                    res = resp.body();
                }
            }catch(IOException e){

            }

            return res;
        }

        @Override
        protected void onPostExecute(List<DispositivosAdapter> dispositivosAdapters) {
            super.onPostExecute(dispositivosAdapters);
            dispositivosDataAdapterAnade.swapItems(dispositivosAdapters);
            swipeRefreshLayoutDispNuevo.setRefreshing(false);
        }

        @Override
        protected void onCancelled(List<DispositivosAdapter> dispositivosAdapters) {
            super.onCancelled(dispositivosAdapters);
            Toast.makeText(EsptouchActivity.this, "No se ha podido recuperar la información de los dispositivos", Toast.LENGTH_SHORT).show();
        }
    }
}
