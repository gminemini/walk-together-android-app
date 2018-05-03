package com.custu.project.walktogether.adapter;

/**
 * Created by pannawatnokket on 9/3/2018 AD.
 */

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.custu.project.project.walktogether.R;
import com.custu.project.walktogether.data.mission.HistoryMission;
import com.custu.project.walktogether.util.PicassoUtil;

import java.util.ArrayList;

public class ListHistoryMissionAdapter extends BaseAdapter {
    private ArrayList<HistoryMission> historyMissionArrayList;
    private Context mContext;

    public ListHistoryMissionAdapter(Context mContext, ArrayList<HistoryMission> historyMissionArrayList) {
        this.mContext = mContext;
        this.historyMissionArrayList = historyMissionArrayList;
    }

    @Override
    public int getCount() {
        return historyMissionArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        LayoutInflater mInflater =
                (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (view == null)
            view = mInflater.inflate(R.layout.list_history_mission, viewGroup, false);

        ImageView imageView = view.findViewById(R.id.image);
        PicassoUtil.getInstance().setImage(mContext, historyMissionArrayList.get(position).getPatientGame().getMap().getImage(), imageView);

        TextView place = view.findViewById(R.id.name_place);
        place.setText(historyMissionArrayList.get(position).getPatientGame().getMap().getNamePlace());

        TextView score = view.findViewById(R.id.result_score);
        score.setText(String.valueOf(historyMissionArrayList.get(position).getPatientGame().getResultScore()));

        TextView time = view.findViewById(R.id.time);
        time.setText(historyMissionArrayList.get(position).getHistoryDate());

        return view;
    }
}
