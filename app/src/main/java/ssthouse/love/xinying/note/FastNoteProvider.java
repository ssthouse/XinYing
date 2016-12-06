package ssthouse.love.xinying.note;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import ssthouse.love.xinying.R;

/**
 * Created by ssthouse on 2015/12/9.
 */
public class FastNoteProvider extends AppWidgetProvider {

    /**
     * @param context          上下文
     * @param appWidgetManager widget manager
     */
    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        initWidget(context, appWidgetManager, appWidgetIds);
    }

    @Override
    public void onEnabled(Context context) {
        super.onEnabled(context);
        updateWidget(context);
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
        updateWidget(context);
    }

    private void initWidget(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        RemoteViews rvs = new RemoteViews(context.getPackageName(), R.layout.widget_provider);
        setRvClickListener(context, rvs);
        setRvText(context, rvs);
        appWidgetManager.updateAppWidget(appWidgetIds, rvs);
    }

    private void updateWidget(Context context) {
        ComponentName componentName = new ComponentName(context, FastNoteProvider.class);
        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.widget_provider);
        setRvText(context, remoteViews);
        setRvClickListener(context, remoteViews);
        AppWidgetManager.getInstance(context).updateAppWidget(componentName, remoteViews);
    }

    private void setRvClickListener(Context context, RemoteViews rvs) {
        Intent intent = new Intent(context, FastNoteActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);
        rvs.setOnClickPendingIntent(R.id.id_ll_fast_note, pendingIntent);
    }

    private void setRvText(Context context, RemoteViews rvs) {
        rvs.setTextViewText(R.id.id_tv_fast_note, FastNoteConfigUtil.getInstance(context).getNote());
        rvs.setTextColor(R.id.id_tv_fast_note, FastNoteConfigUtil.getInstance(context).getColor());
    }
}
