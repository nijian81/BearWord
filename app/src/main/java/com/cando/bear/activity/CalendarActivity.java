package com.cando.bear.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.Window;
import android.widget.CalendarView;
import android.widget.ImageView;

import com.cando.bear.R;
import com.cando.bear.utils.EventDecorator;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Executors;

public class CalendarActivity extends Activity implements View.OnClickListener {

    private Intent intent;
    private ImageView back;
    private MaterialCalendarView materialCalendarView;
    private View center,left,right;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.calendar);

        back = (ImageView) findViewById(R.id.back);
        back.setOnClickListener(this);
        materialCalendarView = (MaterialCalendarView) findViewById(R.id.calendarView);
        Date date = new Date(System.currentTimeMillis());
        center = (View)findViewById(R.id.center);
        center.bringToFront();
        left = (View)findViewById(R.id.left);
        left.bringToFront();
        right = (View)findViewById(R.id.right);
        right.bringToFront();
//        materialCalendarView.setSelectedDate(date);
        new ApiSimulator().executeOnExecutor(Executors.newSingleThreadExecutor());
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back:
                intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                //新的activity进入动画，旧的activity退出的动画
                overridePendingTransition(R.anim.enter_from_left, R.anim.exit_to_right);
                break;
        }
    }

    /**
     * Simulate an API call to show how to add decorators
     */
    private class ApiSimulator extends AsyncTask<Void, Void, List<CalendarDay>> {

        @Override
        protected List<CalendarDay> doInBackground(@NonNull Void... voids) {
            try {

            } catch (Exception e) {
                e.printStackTrace();
            }
            Calendar calendar = Calendar.getInstance();
            //比当前月份少一
//            calendar.add(Calendar.MONTH, -2);
            ArrayList<CalendarDay> dates = new ArrayList<>();
            for (int i = 0; i < 30; i++) {
                calendar.add(Calendar.DATE, i);
                CalendarDay day = CalendarDay.from(calendar);
                dates.add(day);
            }

            return dates;
        }

        @Override
        protected void onPostExecute(@NonNull List<CalendarDay> calendarDays) {
            super.onPostExecute(calendarDays);

            if (isFinishing()) {
                return;
            }

            materialCalendarView.addDecorator(new EventDecorator(Color.WHITE, calendarDays));
        }
    }

}
