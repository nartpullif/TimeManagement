package com.example.android.timemanagement.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.timemanagement.R;
import com.example.android.timemanagement.adapters.TaskAdapter;
import com.example.android.timemanagement.data.Contract;
import com.example.android.timemanagement.data.DBHelper;
import com.example.android.timemanagement.data.Item;
import com.example.android.timemanagement.data.ShowBean;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import android.widget.RadioGroup.OnCheckedChangeListener;
import static com.example.android.timemanagement.data.DatabaseUtils.getDailyTask;
import static com.example.android.timemanagement.data.DatabaseUtils.removeTask;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import com.example.android.timemanagement.data.DbOptDao;

public class TotalActivity extends Activity implements OnCheckedChangeListener {

    SegmentedRadioGroup segmentText;
    private RecyclerView rv_tasks;
    private static final int TYPE_CONTENT = 0;
    private static final int TYPE_HEAD = 1;

    private List<ShowBean> list = new ArrayList<ShowBean>();
    private MyAdapter adapter;

    private int curIndex = 0;
    DbOptDao db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_total);
        db = new DbOptDao(TotalActivity.this);

        segmentText = (SegmentedRadioGroup) findViewById(R.id.segment_text);
        segmentText.setOnCheckedChangeListener(this);
        rv_tasks = (RecyclerView)findViewById(R.id.rv_tasks);
        adapter = new MyAdapter();
        rv_tasks.setLayoutManager(new LinearLayoutManager(this));
        rv_tasks.setAdapter(adapter);

    }

    @Override
    protected void onResume() {
        updateDataShow();
        super.onResume();
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        if (group == segmentText) {
            if (checkedId == R.id.button_one) {
                curIndex = 0;
            } else if (checkedId == R.id.button_two) {
                curIndex = 1;
            } else if (checkedId == R.id.button_three) {
                curIndex = 2;
            }
        }
        updateDataShow();
    }

    public void updateDataShow(){
        switch(curIndex){
            case 0:
                list.clear();
                list.add(db.getDayAll());
                list.add(db.getDayAllSubjects());
                list.add(db.getDayAllProjects());
                adapter.notifyDataSetChanged();
                break;
            case 1:
                list.clear();
                list.add(db.getWeekAll());
                list.add(db.getWeekAllSubjects());
                list.add(db.getWeekAllProjects());
                adapter.notifyDataSetChanged();
                break;
            case 2:
                list.clear();
                list.add(db.getMonthAll());
                list.add(db.getMonthAllSubjects());
                list.add(db.getMonthAllProjects());
                adapter.notifyDataSetChanged();
                break;
        }
    }

    private class MyAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

        private TextView textView_content;

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            LayoutInflater mInflater = LayoutInflater.from(TotalActivity.this);
            switch (viewType) {
                case TYPE_CONTENT:
                    ViewGroup vImage = (ViewGroup) mInflater.inflate(R.layout.layout_content, parent, false);
                    ViewContentHolder vhImage = new ViewContentHolder(vImage);
                    return vhImage;
                case TYPE_HEAD:
                    ViewGroup vGroup = (ViewGroup) mInflater.inflate(R.layout.layout_head, parent, false);
                    ViewHeadHolder vhGroup = new ViewHeadHolder(vGroup);
                    return vhGroup;
            }
            return null;
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            int count = 0;
            for (int i = 0; i < list.size(); i++) {
                ShowBean bean = list.get(i);
                List<Item> dataList = bean.getData();
                if (position == count) {
                    ViewHeadHolder vGroup = (ViewHeadHolder) holder;
                    vGroup.textView_head.setText(bean.getTitle());
                }
                count++;

                for (int j = 0; j < dataList.size(); j++) {
                    if (position == count) {
                        ViewContentHolder vhContext = (ViewContentHolder) holder;
                        double all = 0;
                        double subjectAll=0;
                        double projectAll =0;
                        switch(curIndex){
                            case 0:
                                vhContext.tv_target.setText(String.valueOf(8));

                                all = 0;
                                subjectAll=0;
                                projectAll =0;

                                all = 8*60;
                                ShowBean sb = db.getDayAllSubjects();
                                for(int in=0;in<sb.getData().size();in++){
                                    subjectAll = subjectAll+Double.parseDouble(sb.getData().get(in).getFinished());
                                }

                                ShowBean sb1 = db.getDayAllProjects();
                                for(int in=0;in<sb1.getData().size();in++){
                                    projectAll = projectAll+Double.parseDouble(sb1.getData().get(in).getFinished());
                                }
                                break;
                            case 1:
                                vhContext.tv_target.setText(String.valueOf(8*7));
//                                list.clear();
//                                list.add(db.getWeekAll());
//                                list.add(db.getWeekAllSubjects());
//                                list.add(db.getWeekAllProjects());
//                                adapter.notifyDataSetChanged();

                                all = 0;
                                subjectAll=0;
                                projectAll =0;

                                all = 8*60*7;
                                ShowBean sb2 = db.getWeekAllSubjects();
                                for(int in=0;in<sb2.getData().size();in++){
                                    subjectAll = subjectAll+Double.parseDouble(sb2.getData().get(in).getFinished());
                                }

                                ShowBean sb3 = db.getWeekAllProjects();
                                for(int in=0;in<sb3.getData().size();in++){
                                    projectAll = projectAll+Double.parseDouble(sb3.getData().get(in).getFinished());
                                }

                                break;
                            case 2:
                                vhContext.tv_target.setText(String.valueOf(8*30));

                                all = 0;
                                subjectAll=0;
                                projectAll =0;

                                all = 8*60*30;
                                ShowBean sb4 = db.getMonthAllSubjects();
                                for(int in=0;in<sb4.getData().size();in++){
                                    subjectAll = subjectAll+Double.parseDouble(sb4.getData().get(in).getFinished());
                                }

                                ShowBean sb5 = db.getMonthAllProjects();
                                for(int in=0;in<sb5.getData().size();in++){
                                    projectAll = projectAll+Double.parseDouble(sb5.getData().get(in).getFinished());
                                }
                                break;
                        }

                        Log.e("xxx","bean---"+bean.getTitle());
                        vhContext.textView_content.setText(dataList.get(j).getName());

                        Double finished = Double.parseDouble(dataList.get(j).getFinished());
                        int alfinished =  Integer.parseInt(dataList.get(j).getFinished());
                        String durationTime = String.format("%d:%02d", alfinished/60, alfinished%60);
                        vhContext.tv_finished.setText(durationTime);

                        if(bean.getTitle().toLowerCase().contains("subjects")){
                            vhContext.tv_rate.setText((int)(finished/subjectAll * 100)+"%");
                            vhContext.pb.setProgress((int)(finished/subjectAll * 100));
                        }else  if(bean.getTitle().toLowerCase().contains("projects")){
                            vhContext.tv_rate.setText((int)(finished/projectAll * 100)+"%");
                            vhContext.pb.setProgress((int)(finished/projectAll * 100));
                        }else
                        {
                            vhContext.tv_rate.setText((int)(finished/all * 100)+"%");
                            vhContext.pb.setProgress((int)(finished/all * 100));
                        }

                        if(!bean.getTitle().toLowerCase().contains("/")){

                            vhContext.ll_target.setVisibility(View.GONE);

                        }else{

                            vhContext.ll_target.setVisibility(View.VISIBLE);
                        }
                    }
                    count++;
                }
            }
        }

        @Override
        public int getItemViewType(int position) {
            int count = 0;
            for (int i = 0; i < list.size(); i++) {
                ShowBean bean = list.get(i);
                List<Item> dataList = bean.getData();
                if (position == count) {
                    return TYPE_HEAD;
                }
                count++;
                for (int j = 0; j < dataList.size(); j++) {
                    if (position == count) {
                        return TYPE_CONTENT;
                    }
                    count++;
                }
            }
            return 0;
        }

        @Override
        public int getItemCount() {
            int count = list.size();
            for (int i = 0; i < list.size(); i++) {
                ShowBean bean = list.get(i);
                List<Item> dataList = bean.getData();
                count += dataList.size();
            }
            return count;
        }

        public class ViewHeadHolder extends RecyclerView.ViewHolder {
            public View rootView;
            public TextView textView_head;

            public ViewHeadHolder(View rootView) {
                super(rootView);
                this.rootView = rootView;
                this.textView_head = (TextView) rootView.findViewById(R.id.textView_head);
            }

        }

        public class ViewContentHolder extends RecyclerView.ViewHolder {
            public View rootView;
            public TextView textView_content;
            public TextView tv_finished;
            public TextView tv_rate;
            private ProgressBar pb;
            private View ll_target;
            private TextView tv_target;

            public ViewContentHolder(View rootView) {
                super(rootView);
                this.rootView = rootView;
                this.textView_content = (TextView) rootView.findViewById(R.id.textView_content);
                this.tv_finished = (TextView) rootView.findViewById(R.id.tv_finished);
                this.tv_rate = (TextView) rootView.findViewById(R.id.tv_rate);
                this.pb = (ProgressBar) rootView.findViewById(R.id.pb);
                this.ll_target =  rootView.findViewById(R.id.ll_target);
                this.tv_target =  (TextView) rootView.findViewById(R.id.tv_target);
            }

        }
    }
}
