package me.kevinkang.timecapsule.activities;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.graphics.drawable.shapes.Shape;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

import me.kevinkang.timecapsule.R;
import me.kevinkang.timecapsule.TimeCapsule;
import me.kevinkang.timecapsule.data.models.Capsule;

/**
 * Created by Work on 10/15/2016.
 */

public class CapsuleAdapter extends RecyclerView.Adapter<CapsuleAdapter.ViewHolder> {

    private final static String LOG_TAG = CapsuleAdapter.class.getSimpleName();

    private List<Capsule> dataset;

    private SwipeRefreshLayout refreshLayout;
    private Handler handler;
    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView nameView;
        public TextView messageView;
        public ImageView attachmentView;
        public RelativeLayout capsuleLayout;

        public ViewHolder(View v) {
            super(v);

            capsuleLayout = (RelativeLayout) v.findViewById(R.id.capsule_row_layout);
            attachmentView = (ImageView) v.findViewById(R.id.capsule_image);
            nameView = (TextView) v.findViewById(R.id.capsule_first_line);
            messageView = (TextView) v.findViewById(R.id.capsule_second_line);
        }
    }

    public void add(int position, Capsule capsule) {
        dataset.add(position, capsule);
        notifyItemInserted(position);
    }

    public void remove(Capsule capsule) {
        int position = dataset.indexOf(capsule);
        dataset.remove(position);
        notifyItemRemoved(position);
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public CapsuleAdapter(List<Capsule> myDataset) {
        dataset = myDataset;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public CapsuleAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                        int viewType) {
        handler = new Handler();
        // create a new view
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.capsule_row, parent, false);
        // set the view's size, margins, paddings and layout parameters
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        Capsule capsule = dataset.get(position);

        holder.nameView.setText(capsule.getName());
        holder.messageView.setText(capsule.getMessage());

        Resources resources = holder.attachmentView.getResources();

        ShapeDrawable oval = new ShapeDrawable (new OvalShape());
        oval.setIntrinsicHeight (44);
        oval.setIntrinsicWidth (44);

        if(position / 4.0 <= 0.25) {
            oval.getPaint().setColor(resources.getColor(R.color.lightestColor));
        } else if (position / 4.0 <= 0.5) {
            oval.getPaint().setColor(resources.getColor(R.color.lighterColor));
        } else if (position / 4.0 <= 0.75) {
            oval.getPaint().setColor(resources.getColor(R.color.darkerColor));
        } else {
            oval.getPaint().setColor(resources.getColor(R.color.darkestColor));
        }

        holder.attachmentView.setImageDrawable(oval);

        // load capsule
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return dataset.size();
    }

}
