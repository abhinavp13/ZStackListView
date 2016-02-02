/*
 * Copyright (C) 2016 Abhinav Puri
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.pabhinav.zsrv.zstacklistview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * This is the launcher activity for this app.
 * <p>
 * Uses {@link RecyclerView} for listing some data. Its compatible
 * from minimum android api level 14 to latest android version.
 * This covers more than 96% android devices.
 * </p>
 *
 * @author pabhinav (pabhinav@iitrpr.ac.in)
 */
public class MainActivity extends AppCompatActivity {

    /**
     * {@link RecyclerView} used to populate a list of items.
     * This view will also be passed to its {@link MyRecyclerViewAdapter},
     * which is an adapter for this view, where this view is used
     * to manipulate its listed item's visibilities.
     * <p>
     *     It uses Jake Wharton's {@link ButterKnife} library to
     *     initialize itself.
     *     @see <a href = "http://jakewharton.github.io/butterknife/">
     *         Butterknife Library</a>
     * </p>
     */
    @Bind(R.id.my_recycler_view)
    RecyclerView mRecyclerView;

    /**
     * Layout Manager used to populate {@link RecyclerView} is
     * {@link LinearLayoutManager}, whose object will also be used
     * in adapter for detecting child item views visibility.
     */
    LinearLayoutManager mLayoutManager;

    /**
     * The adapter for {@link RecyclerView}.
     * Custom adapter is implemented and attached with {@link RecyclerView}
     */
    RecyclerView.Adapter mAdapter;

    /**
     * Called when the activity is starting.
     *
     * <p>It initializes {@link RecyclerView} and attach to its customized
     * {@link MyRecyclerViewAdapter}, also uses {@link ButterKnife},
     * a library by JakeWharton.
     *
     * @param savedInstanceState If the activity is being re-initialized after
     *     previously being shut down then this Bundle contains the data it most
     *     recently supplied in {@link #onSaveInstanceState}.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        /**
         * {@link RecyclerView} can perform several optimizations if it can know
         * in advance that changes in adapter content cannot change the size of the
         * {@link RecyclerView} itself.
         *
         * <p>Since our {@link RecyclerView} have fixed size, this attribute can
         * be set to true.</p>
         */
        mRecyclerView.setHasFixedSize(true);

        /**
         * {@link LinearLayoutManager} creates a vertical list of tiles. Initialize
         * it with {@link Context} object.
         */
        mLayoutManager = new LinearLayoutManager(this);

        /**
         * Attach {@link LinearLayoutManager} to our {@link RecyclerView}.
         */
        mRecyclerView.setLayoutManager(mLayoutManager);

        /**
         * Initailize {@link MyRecyclerViewAdapter} custom adapter for our {@link RecyclerView}.
         * Custom Adapter requires {@link Context} object, List of item's data,
         * {@link LinearLayoutManager} object and {@link RecyclerView} itself.
         */
        mAdapter = new MyRecyclerViewAdapter(this,generateData(), mLayoutManager);

        /**
         * Attach {@link MyRecyclerViewAdapter} custom adapter with our {@link RecyclerView}.
         */
        mRecyclerView.setAdapter(mAdapter);

        /**
         * Listen to scroll events using {@link android.support.v7.widget.RecyclerView.OnScrollListener}
         * for {@link RecyclerView}.
         */
        mRecyclerView.addOnScrollListener(new MyRecyclerOnScrollListener(mLayoutManager, mRecyclerView, this));
    }

    /**
     * Called after {@link #onRestoreInstanceState}, {@link #onRestart}, or
     * {@link #onPause}, for the activity to start interacting with the user.
     * This is a place where click listeners have been added to each
     * {@link RecyclerView}'s clickable items.
     */
    @Override
    public void onResume(){
        super.onResume();

        /**
         * {@link RecyclerView} has its each child item's click
         * event captured here. One can set setOnItemClickListener
         * on its attached adapter.
         */
        ((MyRecyclerViewAdapter) mAdapter).setOnItemClickListener(
            new MyRecyclerViewAdapter.MyClickListener() {
                @Override
                public void onItemClick(int position, View v) {

                    /** Just logging click events **/
                    Log.d("Item Pressed : ", " Position : " + position);
                }
            }
        );
    }

    /**
     * Dummy data generator.
     * <p>
     *     This simply fills in some randomly captured data, and returns the list.
     *     It makes use of {@link DummyData} and {@link DataObject} classes.
     * </p>
     *
     * @return {@link ArrayList} of {@link DataObject} objects.
     */
    public ArrayList<DataObject> generateData(){

        /** Reset all static counters before populating a fresh new dummy list **/
        DummyData.resetAllCounters();

        ArrayList<DataObject> dataObjects = new ArrayList<>();
        for(int i = 0; i<20; i++){

            /** Get the dummy parameters and inflate {@link DataObject} object **/
            DataObject dataObject = new DataObject(DummyData.dummyName(), DummyData.dummyLocation(), DummyData.dummyTimePassed());
            /** Add each object to the array list **/
            dataObjects.add(dataObject);

        }

        /** Return the populated dummy list **/
        return dataObjects;
    }


}
