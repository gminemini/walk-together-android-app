package com.custu.project.walktogether;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.baoyz.widget.PullRefreshLayout;
import com.custu.project.project.walktogether.R;
import com.custu.project.walktogether.adapter.PatientAdapter;
import com.custu.project.walktogether.data.Caretaker;
import com.custu.project.walktogether.data.Patient;
import com.custu.project.walktogether.manager.ConnectServer;
import com.custu.project.walktogether.model.CaretakerModel;
import com.custu.project.walktogether.model.PatientModel;
import com.custu.project.walktogether.network.callback.OnDataSuccessListener;
import com.custu.project.walktogether.util.BasicActivity;
import com.custu.project.walktogether.util.ConfigService;
import com.custu.project.walktogether.util.ErrorDialog;
import com.custu.project.walktogether.util.UserManager;
import com.google.gson.JsonObject;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Collections;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.ResponseBody;
import retrofit2.Retrofit;

public class HomeCaretakerActivity extends Activity implements BasicActivity, View.OnClickListener {
    private SwipeMenuListView listView;
    private PullRefreshLayout pullRefreshLayout;
    private ProgressDialog progressDialog;
    private SwipeMenuCreator creator;
    private TextView logout;

    private ArrayList<Patient> patientArrayList;
    private Caretaker caretaker;

    OnDataSuccessListener caretakerListener = new OnDataSuccessListener() {
        @Override
        public void onResponse(JsonObject object, Retrofit retrofit) {
            int status = object.get("status").getAsInt();
            if (status == 200) {
                caretaker = CaretakerModel.getInstance().getCaretaker(object);
                setDataUser();
            } else {
                ErrorDialog.getInstance().showDialog(HomeCaretakerActivity.this, object.get("message").getAsString());
            }
        }

        @Override
        public void onBodyError(ResponseBody responseBodyError) {

        }

        @Override
        public void onBodyErrorIsNull() {

        }

        @Override
        public void onFailure(Throwable t) {

        }
    };

    OnDataSuccessListener patientListener = new OnDataSuccessListener() {
        @Override
        public void onResponse(JsonObject object, Retrofit retrofit) {
            int status = object.get("status").getAsInt();
            if (status == 200) {
                pullRefreshLayout.setRefreshing(false);
                patientArrayList = PatientModel.getInstance().getListPatients(object);
                Collections.reverse(patientArrayList);
                setUI();
                setListener();
            } else {
                pullRefreshLayout.setRefreshing(false);
                ErrorDialog.getInstance().showDialog(HomeCaretakerActivity.this, object.get("message").getAsString());
            }
        }

        @Override
        public void onBodyError(ResponseBody responseBodyError) {

        }

        @Override
        public void onBodyErrorIsNull() {

        }

        @Override
        public void onFailure(Throwable t) {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homecaretaker);
        initValue();
        getDataUser();
        initProgressDialog();
        SwipeMenuCreator();
        getData();
    }

    @Override
    public void initValue() {
        caretaker = UserManager.getInstance(HomeCaretakerActivity.this).getCaretaker();
    }

    private void getDataUser() {
        ConnectServer.getInstance().get(caretakerListener, ConfigService.CARETAKER +
                +caretaker.getId());
    }

    private void SwipeMenuCreator() {
        creator = new SwipeMenuCreator() {
            @Override
            public void create(SwipeMenu menu) {
                SwipeMenuItem deleteItem = new SwipeMenuItem(
                        getApplicationContext());
                deleteItem.setTitle("ลบ");
                deleteItem.setTitleSize(16);
                deleteItem.setTitleColor(Color.WHITE);
                deleteItem.setBackground(R.color.colorDelete);
                deleteItem.setWidth((int) dp2px());
                menu.addMenuItem(deleteItem);
            }
        };
    }

    private float dp2px() {
        float scale = getResources().getDisplayMetrics().density;
        return 80 * scale + 0.5f;
    }

    private void setListener() {
        pullRefreshLayout.setOnRefreshListener(new PullRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getData();
            }
        });

        listView.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
                switch (index) {
                    case 0:
                        deletePatient(patientArrayList.get(index).getPatientNumber(), caretaker.getId(), position);
                        break;
                }
                return false;
            }
        });

        logout.setOnClickListener(this);
    }

    private void deletePatient(String patientNumber, Long caretakerId, final int index) {
        ConnectServer
                .getInstance()
                .delete(new OnDataSuccessListener() {
                            @Override
                            public void onResponse(JsonObject object, Retrofit retrofit) {
                                int status = object.get("status").getAsInt();
                                if (status == 200) {
                                    patientArrayList.remove(index);
                                    setUI();
                                } else {
                                    ErrorDialog.getInstance().showDialog(HomeCaretakerActivity.this, object.get("message").getAsString());
                                }
                            }

                            @Override
                            public void onBodyError(ResponseBody responseBodyError) {

                            }

                            @Override
                            public void onBodyErrorIsNull() {

                            }

                            @Override
                            public void onFailure(Throwable t) {

                            }
                        }, ConfigService.MATCHING
                                + ConfigService.MATCHING_REMOVE_PATIENT
                                + caretakerId
                                + ConfigService.MATCHING_PATIENT_NUMBER
                                + patientNumber
                );

    }

    @Override
    public void setUI() {
        logout = findViewById(R.id.logout);
        listView = findViewById(R.id.list_patient);
        PatientAdapter patientAdapter = new PatientAdapter(getApplicationContext(), patientArrayList);
        listView.setMenuCreator(creator);
        listView.setAdapter(patientAdapter);
    }

    public void setDataUser() {
        TextView nameTextView = findViewById(R.id.name);
        CircleImageView circleImageView = findViewById(R.id.image);
        nameTextView.setText("คุณ" + caretaker.getFirstName() + " " + caretaker.getLastName());

        if (caretaker.getImage() != null) {
            Picasso.with(HomeCaretakerActivity.this).invalidate(ConfigService.BASE_URL_IMAGE + caretaker.getImage());
            Picasso.with(HomeCaretakerActivity.this)
                    .load(ConfigService.BASE_URL_IMAGE + caretaker.getImage())
                    .placeholder(R.drawable.avatar)
                    .error(R.drawable.avatar)
                    .memoryPolicy(MemoryPolicy.NO_CACHE)
                    .networkPolicy(NetworkPolicy.NO_CACHE)
                    .into(circleImageView);
        } else {
            Picasso.with(HomeCaretakerActivity.this)
                    .load(R.drawable.avatar)
                    .placeholder(R.drawable.avatar)
                    .error(R.drawable.avatar)
                    .memoryPolicy(MemoryPolicy.NO_CACHE)
                    .networkPolicy(NetworkPolicy.NO_CACHE)
                    .into(circleImageView);
        }

    }

    @Override
    public void getData() {
        pullRefreshLayout = findViewById(R.id.refresh_layout);
        pullRefreshLayout.setRefreshing(true);
        ConnectServer.getInstance().get(patientListener, ConfigService.MATCHING +
                ConfigService.MATCHING_PATIENT_UNDER_CARETAKER + caretaker.getId());
    }

    @Override
    public void initProgressDialog() {
        progressDialog = new ProgressDialog(HomeCaretakerActivity.this);
        progressDialog.setTitle(getString(R.string.loading));
        progressDialog.setCanceledOnTouchOutside(false);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.cancel();
        }
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();

        switch (id) {
            case R.id.logout:
                UserManager.getInstance(HomeCaretakerActivity.this).removeCaretaker();
                Intent intent = new Intent(HomeCaretakerActivity.this, LoginActivity.class);
                startActivity(intent);
                break;
        }
    }
}