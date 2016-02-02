package com.pabhinav.zsrv.zstacklistview;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

/**
 * This class is used to listen to scroll events for {@link RecyclerView}.
 *
 * <p>
 *     On listening to scroll events, first visible item is detected.
 *     if the position of current first visible item is greater than
 *     the position of first visible item previously saved, then this
 *     means that user is scrolling upwards, and one needs to make sure
 *     that new item which is going to reach the topmost position of
 *     list show pop in of stack effect and for doing this, item at
 *     first position is hidden and mask tile, which was in the background,
 *     becomes visible.
 * </p>
 * <p>
 *     Similarly, if position of current first visible item is lesser
 *     than the position of first visible item previously saved, then
 *     this means user is scrolling downwards, and in order to show
 *     pop out of stack effect, item at first position is made visible.
 * </p>
 *
 * @author pabhinav (pabhinav@iitrpr.ac.in)
 */
public class MyRecyclerOnScrollListener extends RecyclerView.OnScrollListener{

    /**
     * Keeps the older value of {@link LinearLayoutManager#findFirstVisibleItemPosition()}
     * method's returned value, and also is updated on scroll detection, whenever difference is
     * found with the new value.
     */
    private int positionDelta = -1;

    /**
     * {@link LinearLayoutManager} object for vertical aligning list items.
     */
    private LinearLayoutManager mLinearLayoutManager;

    /**
     * {@link RecyclerView} object used here to get child at specified position in the list.
     */
    private RecyclerView mRecyclerView;

    /**
     * View holder for masked tile view present in the background of first list view item.
     */
    private MaskedTileDataObjectHolder mMaskedTileDataObjectHolder;

    /**
     * {@link Context} of the calling Activity.
     */
    private Context mContext;

    /**
     * Constructor for this class.
     *
     * @param linearLayoutManager {@link LinearLayoutManager} for vertical aligning list items
     * @param recyclerView {@link RecyclerView} used in this class for getting child at specified
     *                                         position in the list.
     * @param context {@link Context} of the calling Activity.
     */
    public MyRecyclerOnScrollListener(LinearLayoutManager linearLayoutManager, RecyclerView recyclerView, Context context){
        this.mLinearLayoutManager = linearLayoutManager;
        this.mRecyclerView = recyclerView;
        this.mMaskedTileDataObjectHolder = new MaskedTileDataObjectHolder(context);
        this.mContext = context;
    }

    /**
     * View Holder static class for masked tile view present in the background of the first list view item.
     *
     * @author pabhinav (pabhinav@iitrpr.ac.in)
     */
    public static class MaskedTileDataObjectHolder {

        /**
         * {@link TextView} used for describing list item.
         */
        TextView maskedNameTextView, maskedLocationTextView, maskedTimePassedTextView, maskedNamePlateTextView;

        /**
         * Root {@link View} inflated from layout representing list item.
         */
        View maskedItemView;

        /**
         * Constructor initializing View elements
         *
         * @param context the {@link Context} object of the calling activity.
         */
        public MaskedTileDataObjectHolder(Context context){
            maskedItemView = ((Activity)context).findViewById(R.id.masked_tile);
            maskedNameTextView = (TextView)((Activity)context).findViewById(R.id.masked_name_text_view);
            maskedLocationTextView = (TextView)((Activity)context).findViewById(R.id.masked_place);
            maskedTimePassedTextView = (TextView) ((Activity)context).findViewById(R.id.masked_time_passed_out);
            maskedNamePlateTextView = (TextView) ((Activity)context).findViewById(R.id.masked_name_plate_text_view);
        }
    }

    /**
     * Callback method to be invoked when RecyclerView's scroll state changes.
     *
     * @param recyclerView The RecyclerView whose scroll state has changed.
     * @param newState     The updated scroll state.
     */
    @Override
    public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
        super.onScrollStateChanged(recyclerView, newState);
    }

    /**
     * Callback method to be invoked when the RecyclerView has been scrolled.
     * This will be called after the scroll has completed.
     *
     * <p>
     *     Always make child at index 0, invisible.
     *
     *     Exception :
     *     If one is on the top of {@link RecyclerView}, ie, can't
     *     scroll up more, then make child at index 0, visible.
     *     This is done so because, mask tile view is below the
     *     list, and when first list item is not visible, the shadow
     *     of second list item can be seen on the mask tile. So, in
     *     order to show list tiles all on same elevation (when no
     *     more scrolling up can be done), make first list item visible.
     * </p>
     *
     * <p>
     *      Important :
     *      Make sure that the child view which is going to get
     *      invisible has its alpha set to 0, instead of setting
     *      its visibility to View.INVISIBLE.
     * </p>
     *
     * <p>
     *      Reason :
     *      If we set visibility to View.INVISIBLE, then we won't
     *      be able to receive click events on this child view.
     * </p>
     *
     * @param recyclerView The RecyclerView which scrolled.
     * @param dx The amount of horizontal scroll.
     * @param dy The amount of vertical scroll.
     */
    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);

        /** There has to difference between older and newer first visible item position to show stack effect **/
        if(Math.abs(positionDelta - mLinearLayoutManager.findFirstVisibleItemPosition()) > 0) {

            /**
             * Detect user scrolling down, and make item visible at the index just next
             * to top most, ie, at index = 1.
             */
            if (mLinearLayoutManager.findFirstVisibleItemPosition() < positionDelta) {

                /** Need to make item tile at second index visible **/
                View v = mRecyclerView.getChildAt(1);
                v.setAlpha(1);
            }

            /** Get the child which is made invisible, always is the first visible item in {@link RecyclerView} **/
            MyRecyclerViewAdapter.DataObjectHolder childDataObjectHolder = (MyRecyclerViewAdapter.DataObjectHolder) mRecyclerView.getChildViewHolder(mRecyclerView.getChildAt(0));

            /** Imitate to background mask tile view **/
            imitateMaskedTileViewWithTileView(childDataObjectHolder);
        }

        /**
         * Detect when top of {@link RecyclerView} has reached.
         * Now, no more up scrolling can be done.
         * <p>
         *     To detect when no more up scrolling can be done,
         *
         *     Two conditions need to be followed  :
         *
         *          1) The difference between the top of child at index (not position) = 0
         *             and the top of the parent (which is the whole {@link RecyclerView})
         *             is zero.
         *
         *          2) The very first visible item has position (not index) = 0.
         * </p>
         */
        if(mRecyclerView.getChildAt(0).getTop() == 0 && mLinearLayoutManager.findFirstVisibleItemPosition() == 0){
            /** Since no more scrolling up can be done, exception case is reached and now first item has to be visible. **/
            mRecyclerView.getChildAt(0).setAlpha(1);
        } else {
            /** Not the exception case, make first item invisible **/
            mRecyclerView.getChildAt(0).setAlpha(0);
        }

        /** update the postionDelta value to newly visible child **/
        positionDelta = mLinearLayoutManager.findFirstVisibleItemPosition();
    }

    /**
     * This method exactly imitates masked tile view with the first list item.
     * <p>
     *      Before using this function, it is made sure that first list item is not
     *      visible anymore, and mask tile view is visible.
     * </p>
     *
     * @param dataObjectHolder The {@link com.pabhinav.zsrv.zstacklistview.MyRecyclerViewAdapter.DataObjectHolder}
     *                         object representing view type at first list item position, used to imitate its view's
     *                         value to masked view in the background.
     */
    public void imitateMaskedTileViewWithTileView(final MyRecyclerViewAdapter.DataObjectHolder dataObjectHolder){

        /** Copy {@link TextView} text to masked tile view's corresponding {@link TextView} **/
        mMaskedTileDataObjectHolder.maskedNameTextView.setText(dataObjectHolder.nameTextView.getText());
        mMaskedTileDataObjectHolder.maskedNamePlateTextView.setText(dataObjectHolder.namePlateTextView.getText());
        mMaskedTileDataObjectHolder.maskedTimePassedTextView.setText(dataObjectHolder.timePassedTextView.getText());
        mMaskedTileDataObjectHolder.maskedLocationTextView.setText(dataObjectHolder.locationTextView.getText());

        /** Get the name plate letter character **/
        String namePlateLetter = mMaskedTileDataObjectHolder.maskedNamePlateTextView.getText().toString().toUpperCase().substring(0,1);

        /** Color name plate character holder {@link TextView} background**/
        CircleDrawable circleDrawable = new CircleDrawable(mContext.getResources().getColor(LetterColorMapping.letterToColorIdMapping.get(namePlateLetter)));

        /** Set the colored background **/
        mMaskedTileDataObjectHolder.maskedNamePlateTextView.setBackgroundDrawable(circleDrawable);

    }
}
