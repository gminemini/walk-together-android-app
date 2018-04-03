package com.custu.project.walktogether;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.baoyz.widget.PullRefreshLayout;
import com.custu.project.project.walktogether.R;
import com.custu.project.walktogether.data.Patient;
import com.custu.project.walktogether.manager.ConnectServer;
import com.custu.project.walktogether.model.PatientModel;
import com.custu.project.walktogether.network.callback.OnDataSuccessListener;
import com.custu.project.walktogether.util.ConfigService;
import com.custu.project.walktogether.util.DataFormat;
import com.custu.project.walktogether.util.DialogUtil;
import com.custu.project.walktogether.util.NetworkUtil;
import com.custu.project.walktogether.util.PicassoUtil;
import com.custu.project.walktogether.util.UserManager;
import com.custu.project.walktogether.util.lib.DialogQrCode;
import com.google.gson.JsonObject;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.ResponseBody;
import retrofit2.Retrofit;

public class ProfilePatientDetailFragment extends Fragment {
    private View view;
    private CircleImageView imageView;
    private TextView name;
    private TextView tell;
    private TextView occupation;
    private TextView number;
    private TextView email;
    private ImageView dismissImageView;
    private FragmentActivity context;
    private Patient patient;

    public ProfilePatientDetailFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        this.context = (FragmentActivity) context;
        super.onAttach(context);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.profile_detail, container, false);
        setUI();
        getData();
        return view;
    }

    @SuppressLint("SetTextI18n")
    private void setUI() {
        imageView = view.findViewById(R.id.image_profile);
        name = view.findViewById(R.id.name);
        tell = view.findViewById(R.id.tell);
        occupation = view.findViewById(R.id.occupation);
        email = view.findViewById(R.id.email);
        number = view.findViewById(R.id.number);
        dismissImageView = view.findViewById(R.id.dismiss);
        dismissImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                context.finish();
            }
        });
    }

    private void initValue() {
        PicassoUtil.getInstance().setImageProfile(context, patient.getImage(), imageView);
        name.setText(patient.getTitleName()
                + ""
                + patient.getFirstName()
                + " "
                + patient.getLastName());
        tell.setText(patient.getTell());
        email.setText(patient.getEmail());
        number.setText(patient.getPatientNumber());
        occupation.setText(DataFormat.getInstance().validateData(patient.getOccupation()));
    }

    public void getData() {
        if (getArguments() != null) {
            patient = DataFormat.getInstance().getGsonParser().fromJson(getArguments().getString("patient"), Patient.class);
        }
        initValue();
    }
}
