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

/**
 * Created by Vahedi on 8/5/17.
 */

public class ProjectAdapter extends RecyclerView.Adapter<ProjectAdapter.ProjectViewHolder> {
    private Context context;
    private int mNumberItems;
    private Cursor cursor;
    private ProjectAdapter.ItemClickListener listener;
    private String TAG = "projectAdapter";

    public interface ItemClickListener {
        void onItemClick(int pos, int projectId, String projectName);
    }

    public ProjectAdapter(Cursor cursor, ProjectAdapter.ItemClickListener listener) {
        this.cursor = cursor;
        this.listener = listener;
    }

    @Override
    public ProjectViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        int layoutIdForListItem = R.layout.project_list_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate( layoutIdForListItem , parent , shouldAttachToParentImmediately);
        ProjectViewHolder viewHolder = new ProjectViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ProjectViewHolder holder, int position) {
        holder.bind( position );
    }

    @Override
    public int getItemCount() {
        return cursor.getCount();
    }

    class ProjectViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView itemProjectName;
        ImageView excelExportButton;

        String projectName;
        int projectId;
        String numberOfTask;


        public ProjectViewHolder(View itemView){
            super(itemView);

            itemProjectName = (TextView) itemView.findViewById(R.id.project_name);
            excelExportButton = (ImageView) itemView.findViewById(R.id.excel_export_button);

            //itemView.setOnClickListener(this);
            excelExportButton.setOnClickListener(this);

        }

        void bind(int listIndex){
            //itemProjectName.setText( "Project Name" );
            cursor.moveToPosition(listIndex);

            projectName = cursor.getString(cursor.getColumnIndex(Contract.TABLE_PROJECT.COLUMN_NAME_TITLE));
            numberOfTask = cursor.getString(cursor.getColumnIndex(Contract.TABLE_PROJECT.COLUMN_NAME_NUMBER_OF_TASKS));
            projectId = cursor.getInt(cursor.getColumnIndex(Contract.TABLE_PROJECT._ID));

            if (numberOfTask.equals("1") || numberOfTask.equals("0")){
                itemProjectName.setText(projectName + " ( " + numberOfTask + " task )");
            }else {
                itemProjectName.setText(projectName + " ( " + numberOfTask + " tasks )");
            }
        }

        @Override
        public void onClick(View v) {
            int pos = getAdapterPosition();
            listener.onItemClick(pos, projectId, projectName);
        }
    }
}
