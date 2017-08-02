package com.example.android.timemanagement.data;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.EditText;

import static com.example.android.timemanagement.data.DatabaseUtils.getDailyTask;
import static com.example.android.timemanagement.data.DatabaseUtils.getTask;

public class DbOptDao {
	private DBHelper helper = null;

	public DbOptDao(Context context) {
		// TODO Auto-generated constructor stub
		helper = new DBHelper(context);
	}
//	while(cursor.moveToNext()){
//		DataBean dataBean = new DataBean();
//		Long id = cursor.getLong(cursor.getColumnIndex(Contract.TABLE_TASK._ID));
//		String date = cursor.getString(cursor.getColumnIndex(Contract.TABLE_TASK.COLUMN_NAME_DATE));
//		Log.e("xxx", "date :"+ date);
//		String subjectTitle = cursor.getString(cursor.getColumnIndex(Contract.TABLE_TASK.COLUMN_NAME_SUBJECT_TITLE));
//		Log.e("xxx", "subject :"+ subjectTitle);
//		String projectTitle = cursor.getString(cursor.getColumnIndex(Contract.TABLE_TASK.COLUMN_NAME_PROJECT_TITLE));
//
//		int startTimeHour = cursor.getInt(cursor.getColumnIndex(Contract.TABLE_TASK.COLUMN_NAME_START_HOUR));
//		int startTimeMinute = cursor.getInt(cursor.getColumnIndex(Contract.TABLE_TASK.COLUMN_NAME_START_MINUTE));
//		String startTimeMidDay = cursor.getString(cursor.getColumnIndex(Contract.TABLE_TASK.COLUMN_NAME_START_MID_DAY));
//
//		int endTimeHour = cursor.getInt(cursor.getColumnIndex(Contract.TABLE_TASK.COLUMN_NAME_END_HOUR));
//		int endTimeMinute = cursor.getInt(cursor.getColumnIndex(Contract.TABLE_TASK.COLUMN_NAME_END_MINUTE));
//		String endTimeMidDay = cursor.getString(cursor.getColumnIndex(Contract.TABLE_TASK.COLUMN_NAME_END_MID_DAY));
//
//		int durationTimeMinutes = cursor.getInt(cursor.getColumnIndex(Contract.TABLE_TASK.COLUMN_NAME_TASK_TOTAL_MINUTES));
//
//		dataBean.setDate(date);
//		dataBean.setId(id);
//		dataBean.setSubjectTitle(subjectTitle);
//		dataBean.setProjectTitle(projectTitle);
//
//		dataBean.setStartTimeHour(startTimeHour);
//		dataBean.setStartTimeMinute(startTimeMinute);
//		dataBean.setStartTimeMidDay(startTimeMidDay);
//
//		dataBean.setEndTimeHour(endTimeHour);
//		dataBean.setEndTimeMinute(endTimeMinute);
//		dataBean.setEndTimeMidDay(endTimeMidDay);
//
//		dataBean.setDurationTimeMinutes(durationTimeMinutes);
//		finished = finished + durationTimeMinutes;
//	}rawQuery
	public void dimSearchByDate( String startdate, String enddate,List<DataBean> diaries){
//		try {
			SQLiteDatabase db = helper.getReadableDatabase();
		    Cursor cursor = getTask(db,startdate,enddate);
//			Cursor cursor = db.query("tasks", null, "date between '" + startdate + "'and  '" + enddate + "'", null, null, null, null);
			diaries.clear();
			while(cursor.moveToNext()) {
				DataBean dataBean = new DataBean();
				Long id = cursor.getLong(cursor.getColumnIndex(Contract.TABLE_TASK._ID));
				String date = cursor.getString(cursor.getColumnIndex(Contract.TABLE_TASK.COLUMN_NAME_DATE));
				Log.e("xxx", "date :"+ date);
				String subjectTitle = cursor.getString(cursor.getColumnIndex(Contract.TABLE_TASK.COLUMN_NAME_SUBJECT_TITLE));
				Log.e("xxx", "subject :"+ subjectTitle);
				String projectTitle = cursor.getString(cursor.getColumnIndex(Contract.TABLE_TASK.COLUMN_NAME_PROJECT_TITLE));

				int startTimeHour = cursor.getInt(cursor.getColumnIndex(Contract.TABLE_TASK.COLUMN_NAME_START_HOUR));
				int startTimeMinute = cursor.getInt(cursor.getColumnIndex(Contract.TABLE_TASK.COLUMN_NAME_START_MINUTE));
				String startTimeMidDay = cursor.getString(cursor.getColumnIndex(Contract.TABLE_TASK.COLUMN_NAME_START_MID_DAY));

				int endTimeHour = cursor.getInt(cursor.getColumnIndex(Contract.TABLE_TASK.COLUMN_NAME_END_HOUR));
				int endTimeMinute = cursor.getInt(cursor.getColumnIndex(Contract.TABLE_TASK.COLUMN_NAME_END_MINUTE));
				String endTimeMidDay = cursor.getString(cursor.getColumnIndex(Contract.TABLE_TASK.COLUMN_NAME_END_MID_DAY));

				int durationTimeMinutes = cursor.getInt(cursor.getColumnIndex(Contract.TABLE_TASK.COLUMN_NAME_TASK_TOTAL_MINUTES));

				dataBean.setDate(date);
				dataBean.setId(id);
				dataBean.setSubjectTitle(subjectTitle);
				dataBean.setProjectTitle(projectTitle);

				dataBean.setStartTimeHour(startTimeHour);
				dataBean.setStartTimeMinute(startTimeMinute);
				dataBean.setStartTimeMidDay(startTimeMidDay);

				dataBean.setEndTimeHour(endTimeHour);
				dataBean.setEndTimeMinute(endTimeMinute);
				dataBean.setEndTimeMidDay(endTimeMidDay);

				dataBean.setDurationTimeMinutes(durationTimeMinutes);
				diaries.add(dataBean);
			}
			cursor.close();
			db.close();
//		}catch(Exception e){
//
//		}
	}

	public List<String> getAllProjectNames(){
		List<String> projects = new ArrayList<String>();
		SQLiteDatabase db=helper.getReadableDatabase();
		Cursor cursor = db.query(
				Contract.TABLE_PROJECT.TABLE_NAME,
				null,
				null,
				null,
				null,
				null,
				null
		);
		while(cursor.moveToNext()){
			String name = cursor.getString(cursor.getColumnIndex(Contract.TABLE_PROJECT.COLUMN_NAME_TITLE));
			projects.add(name);
		}
		cursor.close();
		db.close();
		return projects;
	}

	public List<String> getAllSubjectNames(){
		List<String> subjects = new ArrayList<String>();
		SQLiteDatabase db=helper.getReadableDatabase();
		Cursor cursor = db.query(
				Contract.TABLE_SUBJECT.TABLE_NAME,
				null,
				null,
				null,
				null,
				null,
				null
		);
		while(cursor.moveToNext()){
			String name = cursor.getString(cursor.getColumnIndex(Contract.TABLE_SUBJECT.COLUMN_NAME_TITLE));
			subjects.add(name);
		}
		cursor.close();
		db.close();
		return subjects;
	}

	public HashMap<String,List<DataBean>> getNeedProjectData(List<DataBean> targets){
		HashMap<String,List<DataBean>> map = new HashMap<String,List<DataBean>>();
		List<String> projectNames = getAllProjectNames();
		for(int index = 0;index<projectNames.size(); index++){
			List<DataBean> tmps = new ArrayList<DataBean>();
            for(DataBean tmp:targets){
				Log.e("xxx","-----tmp.getProjectTitle()-----"+tmp.getProjectTitle());
				Log.e("xxx","-----projectNames.get(index)-----"+projectNames.get(index));
				if(tmp.getProjectTitle().equals(projectNames.get(index))){
					tmps.add(tmp);
				}
			}
			Log.e("xxx","-----projectNames.get(index)-----"+projectNames.get(index));
			Log.e("xxx","----------");
			map.put(projectNames.get(index),tmps);
		}
		return map;
	}

	public HashMap<String,List<DataBean>> getNeedSubjectData(List<DataBean> targets){
		HashMap<String,List<DataBean>> map = new HashMap<String,List<DataBean>>();
		List<String> subjectNames = getAllSubjectNames();
		for(int index = 0;index<subjectNames.size(); index++){
			List<DataBean> tmps = new ArrayList<DataBean>();
			for(DataBean tmp:targets){
				if(tmp.getSubjectTitle().equals(subjectNames.get(index))){
					tmps.add(tmp);
				}
			}
			map.put(subjectNames.get(index),tmps);
		}
		return map;
	}

	public static String StringData() {
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
		return sdf.format(new Date());
	}

	/**
	 * 获取今天往后一周的日期（几月几号）
	 */
	public static String getSevendate() {
		List<String > dates = new ArrayList<String>();
		final Calendar c = Calendar.getInstance();

		int mYear = c.get(Calendar.YEAR);// 获取当前年份
		int mMonth = c.get(Calendar.MONTH);// 获取当前月份
		int mDay = c.get(Calendar.DAY_OF_MONTH)+7;// 获取当前日份的日期号码
		c.set(Calendar.YEAR, mYear);
		c.set(Calendar.MONTH, mMonth);
		c.set(Calendar.DAY_OF_MONTH, mDay);
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");

		return sdf.format(c.getTime());
	}

	/**
	 * 获取今天往后一月的日期（几月几号）
	 */
	public static String getMonthdate() {
		List<String > dates = new ArrayList<String>();
		final Calendar c = Calendar.getInstance();

		int mYear = c.get(Calendar.YEAR);// 获取当前年份
		int mMonth = c.get(Calendar.MONTH) + 1;// 获取当前月份
		int mDay = c.get(Calendar.DAY_OF_MONTH);// 获取当前日份的日期号码
		c.set(Calendar.YEAR, mYear);
		c.set(Calendar.MONTH, mMonth);
		c.set(Calendar.DAY_OF_MONTH, mDay);
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");

		return sdf.format(c.getTime());
	}

    public void getWeeksAll(){
		List<DataBean> diaries = new ArrayList<DataBean>();
		String start = StringData();
		String end = getSevendate();
		dimSearchByDate(start,end,diaries);
	}

	public ShowBean getDayAll(){
		ShowBean sb = new ShowBean();
		String currentDate = StringData();
		SQLiteDatabase db=helper.getReadableDatabase();
		SimpleDateFormat dbDateFormat = new SimpleDateFormat("MM/dd/yyyy");
		Cursor cursor = getDailyTask(db,currentDate);

		int finished = 0;
		while(cursor.moveToNext()){
			DataBean dataBean = new DataBean();
			Long id = cursor.getLong(cursor.getColumnIndex(Contract.TABLE_TASK._ID));
			String date = cursor.getString(cursor.getColumnIndex(Contract.TABLE_TASK.COLUMN_NAME_DATE));
			Log.e("xxx", "date :"+ date);
			String subjectTitle = cursor.getString(cursor.getColumnIndex(Contract.TABLE_TASK.COLUMN_NAME_SUBJECT_TITLE));
			Log.e("xxx", "subject :"+ subjectTitle);
			String projectTitle = cursor.getString(cursor.getColumnIndex(Contract.TABLE_TASK.COLUMN_NAME_PROJECT_TITLE));

			int startTimeHour = cursor.getInt(cursor.getColumnIndex(Contract.TABLE_TASK.COLUMN_NAME_START_HOUR));
			int startTimeMinute = cursor.getInt(cursor.getColumnIndex(Contract.TABLE_TASK.COLUMN_NAME_START_MINUTE));
			String startTimeMidDay = cursor.getString(cursor.getColumnIndex(Contract.TABLE_TASK.COLUMN_NAME_START_MID_DAY));

			int endTimeHour = cursor.getInt(cursor.getColumnIndex(Contract.TABLE_TASK.COLUMN_NAME_END_HOUR));
			int endTimeMinute = cursor.getInt(cursor.getColumnIndex(Contract.TABLE_TASK.COLUMN_NAME_END_MINUTE));
			String endTimeMidDay = cursor.getString(cursor.getColumnIndex(Contract.TABLE_TASK.COLUMN_NAME_END_MID_DAY));

			int durationTimeMinutes = cursor.getInt(cursor.getColumnIndex(Contract.TABLE_TASK.COLUMN_NAME_TASK_TOTAL_MINUTES));

			dataBean.setDate(date);
			dataBean.setId(id);
			dataBean.setSubjectTitle(subjectTitle);
			dataBean.setProjectTitle(projectTitle);

			dataBean.setStartTimeHour(startTimeHour);
			dataBean.setStartTimeMinute(startTimeMinute);
			dataBean.setStartTimeMidDay(startTimeMidDay);

			dataBean.setEndTimeHour(endTimeHour);
			dataBean.setEndTimeMinute(endTimeMinute);
			dataBean.setEndTimeMidDay(endTimeMidDay);

			dataBean.setDurationTimeMinutes(durationTimeMinutes);
			finished = finished + durationTimeMinutes;
		}
		cursor.close();
		db.close();
		sb.setTitle(currentDate);
		Item item = new Item();
		item.setName("Days Total");
		item.setFinished(String.valueOf(finished));
		List<Item> items = new ArrayList<Item>();
		items.add(item);
		sb.setData(items);
		return sb;
	}

	public ShowBean getDayAllProjects(){
		ShowBean sb = new ShowBean();
		String currentDate = StringData();
		SQLiteDatabase db=helper.getReadableDatabase();
		SimpleDateFormat dbDateFormat = new SimpleDateFormat("MM/dd/yyyy");
		Cursor cursor = getDailyTask(db, currentDate);

		List<DataBean> targets = new ArrayList<DataBean>();
		while(cursor.moveToNext()){
			DataBean dataBean = new DataBean();
			Long id = cursor.getLong(cursor.getColumnIndex(Contract.TABLE_TASK._ID));
			String date = cursor.getString(cursor.getColumnIndex(Contract.TABLE_TASK.COLUMN_NAME_DATE));
			String subjectTitle = cursor.getString(cursor.getColumnIndex(Contract.TABLE_TASK.COLUMN_NAME_SUBJECT_TITLE));
			String projectTitle = cursor.getString(cursor.getColumnIndex(Contract.TABLE_TASK.COLUMN_NAME_PROJECT_TITLE));

			int startTimeHour = cursor.getInt(cursor.getColumnIndex(Contract.TABLE_TASK.COLUMN_NAME_START_HOUR));
			int startTimeMinute = cursor.getInt(cursor.getColumnIndex(Contract.TABLE_TASK.COLUMN_NAME_START_MINUTE));
			String startTimeMidDay = cursor.getString(cursor.getColumnIndex(Contract.TABLE_TASK.COLUMN_NAME_START_MID_DAY));

			int endTimeHour = cursor.getInt(cursor.getColumnIndex(Contract.TABLE_TASK.COLUMN_NAME_END_HOUR));
			int endTimeMinute = cursor.getInt(cursor.getColumnIndex(Contract.TABLE_TASK.COLUMN_NAME_END_MINUTE));
			String endTimeMidDay = cursor.getString(cursor.getColumnIndex(Contract.TABLE_TASK.COLUMN_NAME_END_MID_DAY));

			int durationTimeMinutes = cursor.getInt(cursor.getColumnIndex(Contract.TABLE_TASK.COLUMN_NAME_TASK_TOTAL_MINUTES));

			dataBean.setDate(date);
			dataBean.setId(id);
			dataBean.setSubjectTitle(subjectTitle);
			dataBean.setProjectTitle(projectTitle);

			dataBean.setStartTimeHour(startTimeHour);
			dataBean.setStartTimeMinute(startTimeMinute);
			dataBean.setStartTimeMidDay(startTimeMidDay);

			dataBean.setEndTimeHour(endTimeHour);
			dataBean.setEndTimeMinute(endTimeMinute);
			dataBean.setEndTimeMidDay(endTimeMidDay);

			dataBean.setDurationTimeMinutes(durationTimeMinutes);
			targets.add(dataBean);
		}
		cursor.close();
		db.close();

		sb.setTitle("Projects");
		List<Item> items = new ArrayList<Item>();
		HashMap<String,List<DataBean>> maps =  getNeedProjectData(targets);
		for (String key : maps.keySet()) {
//			System.out.println("key= "+ key + " and value= " + map.get(key));
			Item item = new Item();
			item.setName(key);

			List<DataBean> dataBeens = maps.get(key);
			int finished = 0;
			for(int index=0;index<dataBeens.size();index++){
				finished = finished + dataBeens.get(index).getDurationTimeMinutes();
			}
			item.setFinished(String.valueOf(finished));
			items.add(item);
		}
		sb.setData(items);
		return sb;
	}

	public ShowBean getDayAllSubjects(){
		ShowBean sb = new ShowBean();
		String currentDate = StringData();
		SQLiteDatabase db=helper.getReadableDatabase();
		SimpleDateFormat dbDateFormat = new SimpleDateFormat("MM/dd/yyyy");
		Cursor cursor = getDailyTask(db, currentDate);

		List<DataBean> targets = new ArrayList<DataBean>();
		while(cursor.moveToNext()){
			DataBean dataBean = new DataBean();
			Long id = cursor.getLong(cursor.getColumnIndex(Contract.TABLE_TASK._ID));
			String date = cursor.getString(cursor.getColumnIndex(Contract.TABLE_TASK.COLUMN_NAME_DATE));
			String subjectTitle = cursor.getString(cursor.getColumnIndex(Contract.TABLE_TASK.COLUMN_NAME_SUBJECT_TITLE));
			String projectTitle = cursor.getString(cursor.getColumnIndex(Contract.TABLE_TASK.COLUMN_NAME_PROJECT_TITLE));

			int startTimeHour = cursor.getInt(cursor.getColumnIndex(Contract.TABLE_TASK.COLUMN_NAME_START_HOUR));
			int startTimeMinute = cursor.getInt(cursor.getColumnIndex(Contract.TABLE_TASK.COLUMN_NAME_START_MINUTE));
			String startTimeMidDay = cursor.getString(cursor.getColumnIndex(Contract.TABLE_TASK.COLUMN_NAME_START_MID_DAY));

			int endTimeHour = cursor.getInt(cursor.getColumnIndex(Contract.TABLE_TASK.COLUMN_NAME_END_HOUR));
			int endTimeMinute = cursor.getInt(cursor.getColumnIndex(Contract.TABLE_TASK.COLUMN_NAME_END_MINUTE));
			String endTimeMidDay = cursor.getString(cursor.getColumnIndex(Contract.TABLE_TASK.COLUMN_NAME_END_MID_DAY));

			int durationTimeMinutes = cursor.getInt(cursor.getColumnIndex(Contract.TABLE_TASK.COLUMN_NAME_TASK_TOTAL_MINUTES));

			dataBean.setDate(date);
			dataBean.setId(id);
			dataBean.setSubjectTitle(subjectTitle);
			dataBean.setProjectTitle(projectTitle);

			dataBean.setStartTimeHour(startTimeHour);
			dataBean.setStartTimeMinute(startTimeMinute);
			dataBean.setStartTimeMidDay(startTimeMidDay);

			dataBean.setEndTimeHour(endTimeHour);
			dataBean.setEndTimeMinute(endTimeMinute);
			dataBean.setEndTimeMidDay(endTimeMidDay);

			dataBean.setDurationTimeMinutes(durationTimeMinutes);
			targets.add(dataBean);
		}
		cursor.close();
		db.close();

		sb.setTitle("Subjects");
		List<Item> items = new ArrayList<Item>();
		HashMap<String,List<DataBean>> maps =  getNeedSubjectData(targets);
		for (String key : maps.keySet()) {
//			System.out.println("key= "+ key + " and value= " + map.get(key));
			Item item = new Item();
			item.setName(key);

			List<DataBean> dataBeens = maps.get(key);
			int finished = 0;
			for(int index=0;index<dataBeens.size();index++){
				finished = finished + dataBeens.get(index).getDurationTimeMinutes();
			}
			item.setFinished(String.valueOf(finished));
			items.add(item);
		}
		sb.setData(items);
		return sb;
	}

	//*****************************************************************************************************************************//
	public ShowBean getWeekAll(){
		List<DataBean> diaries = new ArrayList<DataBean>();
		String start = StringData();
		Log.e("xxx","start="+start);
		String end = getSevendate();
		Log.e("xxx","end="+end);
		dimSearchByDate(start,end,diaries);

		ShowBean sb = new ShowBean();
		sb.setTitle(start +"-"+ end);
		int finished = 0;
		for(int i=0;i<diaries.size();i++){
			finished = finished + diaries.get(i).getDurationTimeMinutes();
		}
		Item item = new Item();
		item.setName("Weeks Total");
		item.setFinished(String.valueOf(finished));
		List<Item> items = new ArrayList<Item>();
		items.add(item);
		sb.setData(items);
		return sb;
	}

	public ShowBean getWeekAllProjects(){
		List<DataBean> diaries = new ArrayList<DataBean>();
		String start = StringData();
		String end = getSevendate();
		dimSearchByDate(start,end,diaries);

		ShowBean sb = new ShowBean();

		sb.setTitle("Projects");
		List<Item> items = new ArrayList<Item>();
		HashMap<String,List<DataBean>> maps =  getNeedProjectData(diaries);
		for (String key : maps.keySet()) {
//			System.out.println("key= "+ key + " and value= " + map.get(key));
			Item item = new Item();
			item.setName(key);

			List<DataBean> dataBeens = maps.get(key);
			int finished = 0;
			for(int index=0;index<dataBeens.size();index++){
				finished = finished + dataBeens.get(index).getDurationTimeMinutes();
			}
			item.setFinished(String.valueOf(finished));
			items.add(item);
		}
		sb.setData(items);
		return sb;
	}

	public ShowBean getWeekAllSubjects(){
		List<DataBean> diaries = new ArrayList<DataBean>();
		String start = StringData();
		String end = getSevendate();
		dimSearchByDate(start,end,diaries);

		ShowBean sb = new ShowBean();


		sb.setTitle("Subjects");
		List<Item> items = new ArrayList<Item>();
		HashMap<String,List<DataBean>> maps =  getNeedSubjectData(diaries);
		for (String key : maps.keySet()) {
//			System.out.println("key= "+ key + " and value= " + map.get(key));
			Item item = new Item();
			item.setName(key);

			List<DataBean> dataBeens = maps.get(key);
			int finished = 0;
			for(int index=0;index<dataBeens.size();index++){
				finished = finished + dataBeens.get(index).getDurationTimeMinutes();
			}
			item.setFinished(String.valueOf(finished));
			items.add(item);
		}
		sb.setData(items);
		return sb;
	}

	//***************************************************** months ************************************************************************//
	public ShowBean getMonthAll(){
		List<DataBean> diaries = new ArrayList<DataBean>();
		String start = StringData();
		String end = getMonthdate();
		dimSearchByDate(start,end,diaries);

		ShowBean sb = new ShowBean();
		sb.setTitle(start +"-"+ end);
		int finished = 0;
		for(int i=0;i<diaries.size();i++){
			finished = finished + diaries.get(i).getDurationTimeMinutes();
		}
		Item item = new Item();
		item.setName("Months Total");
		item.setFinished(String.valueOf(finished));
		List<Item> items = new ArrayList<Item>();
		items.add(item);
		sb.setData(items);
		return sb;
	}

	public ShowBean getMonthAllProjects(){
		List<DataBean> diaries = new ArrayList<DataBean>();
		String start = StringData();
		String end = getMonthdate();
		dimSearchByDate(start,end,diaries);

		ShowBean sb = new ShowBean();

		sb.setTitle("Projects");
		List<Item> items = new ArrayList<Item>();
		HashMap<String,List<DataBean>> maps =  getNeedProjectData(diaries);
		for (String key : maps.keySet()) {
//			System.out.println("key= "+ key + " and value= " + map.get(key));
			Item item = new Item();
			item.setName(key);

			List<DataBean> dataBeens = maps.get(key);
			int finished = 0;
			for(int index=0;index<dataBeens.size();index++){
				finished = finished + dataBeens.get(index).getDurationTimeMinutes();
			}
			item.setFinished(String.valueOf(finished));
			items.add(item);
		}
		sb.setData(items);
		return sb;
	}

	public ShowBean getMonthAllSubjects(){
		List<DataBean> diaries = new ArrayList<DataBean>();
		String start = StringData();
		String end = getMonthdate();
		dimSearchByDate(start,end,diaries);

		ShowBean sb = new ShowBean();

		sb.setTitle("Subjects");
		List<Item> items = new ArrayList<Item>();
		HashMap<String,List<DataBean>> maps =  getNeedSubjectData(diaries);
		for (String key : maps.keySet()) {
//			System.out.println("key= "+ key + " and value= " + map.get(key));
			Item item = new Item();
			item.setName(key);

			List<DataBean> dataBeens = maps.get(key);
			int finished = 0;
			for(int index=0;index<dataBeens.size();index++){
				finished = finished + dataBeens.get(index).getDurationTimeMinutes();
			}
			item.setFinished(String.valueOf(finished));
			items.add(item);
		}
		sb.setData(items);
		return sb;
	}

}
