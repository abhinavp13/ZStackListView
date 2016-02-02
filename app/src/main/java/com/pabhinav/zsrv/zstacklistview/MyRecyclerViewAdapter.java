package com.pabhinav.zsrv.zstacklistview;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * This class is the adapter for {@link RecyclerView}.
 * <p>
 *     This uses {@link android.support.v7.widget.RecyclerView.ViewHolder}
 *     object to initialize data model objects and render them on screen.
 * </p>
 * <p>
 *     It always make sure that the visibility of all list items are set
 *     to be visible, except for the case when the item at the top of list.
 * </p>
 * <p>
 *     It has been done this way, because {@link MainActivity} has a layout
 *     in which {@link android.widget.FrameLayout} is used, with two children,
 *     one {@link RecyclerView} and other a masked tile view, with exactly same
 *     size as that of each list item size, and placed exactly below the first
 *     list item. So, first item in {@link RecyclerView} is never made visible,
 *     instead of which the masked tile view, which is exactly below this first
 *     item is seen on screen.
 * </p>
 * <p>
 *     In simple word, the very first list item is never made visible, instead,
 *     a placeholder view (mask tile) just below first list item and exactly of
 *     same size, imitates this first list item, and is seen on screen.
 *     It has been done this way to show effect of stacking of tiles on first
 *     list item.
 * </p>
 *
 * @author pabhinav (pabhinav@iitrpr.ac.in)
 */
public class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.DataObjectHolder> {

    /**
     * {@link ArrayList} of {@link DataObject} data model objects.
     */
    private ArrayList<DataObject> mDataset;

    /**
     * Callback interface for listening to click events.
     */
    private static MyClickListener myClickListener;

    /**
     * {@link Context} of the calling activity.
     */
    private Context mContext;

    /**
     * {@link LinearLayoutManager} used for vertical alignment of list items.
     */
    private LinearLayoutManager mLinearLayoutManager;

    /**
     * Constructor for this class
     *
     * @param context of the calling activity
     * @param myDataset {@link ArrayList} of {@link DataObject} objects.
     * @param linearLayoutManager used for vertical alignment of list items.
     */
    public MyRecyclerViewAdapter(Context context, ArrayList<DataObject> myDataset, LinearLayoutManager linearLayoutManager) {
        mContext = context;
        mDataset = myDataset;
        mLinearLayoutManager = linearLayoutManager;
    }

    /**
     * Called when RecyclerView needs a new {@link android.support.v7.widget.RecyclerView.ViewHolder}
     * of the given type to represent an item.
     * <p>
     *      This new ViewHolder is constructed with a new View that can represent the items
     *      of the given type.
     * </p>
     *
     * @param parent The ViewGroup into which the new View will be added after it is bound to
     *               an adapter position.
     * @param viewType The view type of the new View.
     *
     * @return A new ViewHolder that holds a View of the
     *           {@link com.pabhinav.zsrv.zstacklistview.MyRecyclerViewAdapter.DataObjectHolder}
     *           view type.
     */
    @Override
    public DataObjectHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.tile, parent, false);
        return new DataObjectHolder(view);
    }

    /**
     * Called by RecyclerView to display the data at the specified position.
     * <p>
     *     This method also makes sure that the visibility of all list items are set
     *     to be visible, except for the case when the item at the top of list.
     *
     *     (The reason for doing this is explained in {@link MyRecyclerViewAdapter}
     *     and {@link MyRecyclerOnScrollListener} class javadoc)
     * </p>
     *
     * @param holder The {@link com.pabhinav.zsrv.zstacklistview.MyRecyclerViewAdapter.DataObjectHolder}
     *               which should be updated to represent the contents of the item at the given
     *               position in the data set.
     * @param position The position of the item within the adapter's data set.
     */
    @Override
    public void onBindViewHolder(DataObjectHolder holder, int position) {

        /** Get the name plate letter **/
        String namePlateLetter = mDataset.get(position).getName().substring(0,1).toUpperCase();

        /** Need to use {@link CircleDrawable} for rendering background of name plate letter holding {@link TextView} **/
        CircleDrawable circleDrawable = new CircleDrawable(mContext.getResources().getColor(LetterColorMapping.letterToColorIdMapping.get(namePlateLetter)));

        /** update holder view data **/
        holder.namePlateTextView.setText(namePlateLetter);
        holder.nameTextView.setText(String.format("%s%s", namePlateLetter, mDataset.get(position).getName().substring(1)));
        holder.locationTextView.setText(mDataset.get(position).getLocation());
        holder.timePassedTextView.setText(mDataset.get(position).getTimePassed());
        holder.namePlateTextView.setBackgroundDrawable(circleDrawable);

        /** Let all children become visible, except for the child with index position 0 **/
        int childIndex = position - mLinearLayoutManager.findFirstVisibleItemPosition();
        if (childIndex > 0) {
            holder.mItemView.setAlpha(1);
        }
    }

    /**
     * {@link android.support.v7.widget.RecyclerView.ViewHolder} static class,
     * used to describe item view in {@link RecyclerView}.
     *
     * @author pabhinav (pabhinav@iitrpr.ac.in)
     */
    public static class DataObjectHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        /**
         * {@link TextView} used for describing list item.
         */
        TextView nameTextView, locationTextView, timePassedTextView, namePlateTextView;

        /**
         * Root {@link View} inflated from layout representing list item.
         */
        View mItemView;

        /**
         * Constructor initializing View elements
         *
         * @param itemView the root {@link View} object.
         */
        public DataObjectHolder(View itemView) {
            super(itemView);

            mItemView = itemView;
            nameTextView = (TextView)itemView.findViewById(R.id.name_text_view);
            locationTextView = (TextView)itemView.findViewById(R.id.place);
            timePassedTextView = (TextView)itemView.findViewById(R.id.time_passed_out);
            namePlateTextView = (TextView)itemView.findViewById(R.id.name_plate_text_view);

            /** Need to register click events on main view **/
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

            /** Notify callback interface **/
            myClickListener.onItemClick(getPosition(), v);
        }
    }

    /**
     * Setter function for callback interface.
     *
     * @param myClickListener callback interface notifying click events captured.
     */
    public void setOnItemClickListener(MyClickListener myClickListener) {
        this.myClickListener = myClickListener;
    }

    /**
     * Addition of new item in {@link RecyclerView}.
     *
     * @param dataObj item model object to be added to list.
     * @param index position at which item is to be added.
     */
    public void addItem(DataObject dataObj, int index) {
        mDataset.add(dataObj);
        notifyItemInserted(index);
    }

    /**
     * Deletion of an existing item in {@link RecyclerView}.
     *
     * @param index position at which item is to be deleted.
     */
    public void deleteItem(int index) {
        mDataset.remove(index);
        notifyItemRemoved(index);
    }

    /**
     * Specifies number of items registered to {@link RecyclerView}
     *
     * @return number of items in this adapter.
     */
    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    /**
     * Callback Interface used to notify click events on list items.
     */
    public interface MyClickListener {
        void onItemClick(int position, View v);
    }

}
