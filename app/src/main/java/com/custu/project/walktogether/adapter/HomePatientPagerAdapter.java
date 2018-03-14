package com.custu.project.walktogether.adapter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import com.custu.project.walktogether.AddPatientFragment;
import com.custu.project.walktogether.AddPatientQRCodeFragment;
import com.custu.project.walktogether.EvaluationHistoryFragment;
import com.custu.project.walktogether.ListNameFragment;
import com.custu.project.walktogether.ListNamePatientFragment;
import com.custu.project.walktogether.ProfileFragment;
import com.custu.project.walktogether.ProfilePatientFragment;
import com.custu.project.walktogether.SelectMissionFragment;
import com.custu.project.walktogether.data.Patient;

/**
 * Created by pannawatnokket on 6/3/2018 AD.
 */

public class HomePatientPagerAdapter extends FragmentPagerAdapter {
    private Patient patient;

    public HomePatientPagerAdapter(FragmentManager fm, Patient patient) {
        super(fm);
        this.patient = patient;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new ProfilePatientFragment();
            case 1:
                return new ListNamePatientFragment();
            case 2:
                return new SelectMissionFragment();
            case 3:
                Bundle bundle = new Bundle();
                bundle.putLong("idPatient", patient.getId());
                EvaluationHistoryFragment historyEvaluationFragment = new EvaluationHistoryFragment();
                historyEvaluationFragment.setArguments(bundle);
                return historyEvaluationFragment;
            case 4:
                return new ProfilePatientFragment();
            default:
                return null;
        }

    }
    @Override
    public int getCount() {
        return 5;
    }
}
