package com.example.android.timemanagement.adapters;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.example.android.timemanagement.R;
import com.example.android.timemanagement.data.Contract;

import java.util.List;

import com.example.android.timemanagement.R;
import com.example.android.timemanagement.data.Contract;

/**
 * Created by Vahedi on 8/6/17.
 */

public class SubjectAdapter extends RecyclerView.Adapter<SubjectAdapter.ProjectViewHolder> {


    private Context context;
    private Cursor cursor;
    private SubjectAdapter.ItemClickListener listener;
    private String TAG = "projectAdapter";





    public interface ItemClickListener {
        void onItemClick(int pos, int subjectId, String subjectName);
    }


    public SubjectAdapter(Cursor cursor, SubjectAdapter.ItemClickListener listener) {
        this.cursor = cursor;
        this.listener = listener;
    }

    @Override
    public SubjectAdapter.ProjectViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        int layoutIdForListItem = R.layout.subject_list_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate( layoutIdForListItem , parent , shouldAttachToParentImmediately);
        SubjectAdapter.ProjectViewHolder viewHolder = new SubjectAdapter.ProjectViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(SubjectAdapter.ProjectViewHolder holder, int position) {
        holder.bind( position );
    }

    @Override
    public int getItemCount() {
        return cursor.getCount();
    }

    class ProjectViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView itemSubjectName;
        ImageView excelExportButton;

        String subjectName;
        int subjectId;
        String numberOfTask;


        public ProjectViewHolder(View itemView){
            super(itemView);

            itemSubjectName = (TextView) itemView.findViewById(R.id.subject_name);
            excelExportButton = (ImageView) itemView.findViewById(R.id.excel_export_button);

            //itemView.setOnClickListener(this);
            excelExportButton.setOnClickListener(this);

        }

        void bind(int listIndex){;
            cursor.moveToPosition(listIndex);

            subjectName = cursor.getString(cursor.getColumnIndex(Contract.TABLE_SUBJECT.COLUMN_NAME_TITLE));
            numberOfTask = cursor.getString(cursor.getColumnIndex(Contract.TABLE_SUBJECT.COLUMN_NAME_NUMBER_OF_TASKS));
            subjectId = cursor.getInt(cursor.getColumnIndex(Contract.TABLE_SUBJECT._ID));

            if(numberOfTask.equals("0")){
                if (excelExportButton.getVisibility()==View.VISIBLE)
                    excelExportButton.setVisibility(View.INVISIBLE);

            }else {
                if(numberOfTask.equals("1")){
                    itemSubjectName.setText(subjectName + " ( " + numberOfTask + " task )");
                }
                else{
                    itemSubjectName.setText(subjectName + " ( " + numberOfTask + " tasks )");
                }

                if (excelExportButton.getVisibility()==View.INVISIBLE)
                    excelExportButton.setVisibility(View.VISIBLE);
            }
        }

        @Override
        public void onClick(View v) {
            int pos = getAdapterPosition();
            listener.onItemClick(pos, subjectId, subjectName);
        }
    }
}
