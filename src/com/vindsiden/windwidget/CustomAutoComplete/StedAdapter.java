package com.vindsiden.windwidget.CustomAutoComplete;

import android.content.Context;
import android.widget.ArrayAdapter;
import com.vindsiden.windwidget.model.Sted;

import java.util.List;

/**
 * Created by Daniel on 02.02.14.
 */
public class StedAdapter extends ArrayAdapter<Sted> {

    public StedAdapter(Context context, int resource) {
        super(context, resource);
    }

    public StedAdapter(Context context, int resource, int textViewResourceId) {
        super(context, resource, textViewResourceId);
    }

    public StedAdapter(Context context, int resource, Sted[] objects) {
        super(context, resource, objects);
    }

    public StedAdapter(Context context, int resource, int textViewResourceId, Sted[] objects) {
        super(context, resource, textViewResourceId, objects);
    }

    public StedAdapter(Context context, int resource, List<Sted> objects) {
        super(context, resource, objects);
    }

    public StedAdapter(Context context, int resource, int textViewResourceId, List<Sted> objects) {
        super(context, resource, textViewResourceId, objects);
    }
}
