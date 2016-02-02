package com.pabhinav.zsrv.zstacklistview;

/**
 * Data Model for {@link android.support.v7.widget.RecyclerView} items.
 *
 * @author pabhinav (pabhinav@iitrpr.ac.in)
 */
public class DataObject {

    /** String containing name of person **/
    private String name;

    /** String containing location of person **/
    private String location;

    /** String containing time passed till his last check in **/
    private String timePassed;

    /**
     * Constructor for this class.
     *
     * @param name of person
     * @param location of person
     * @param timePassed till his last check in
     */
    public DataObject(String name, String location, String timePassed){
        setName(name);
        setLocation(location);
        setTimePassed(timePassed);
    }

    /** Getter and Setters **/

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getTimePassed() {
        return timePassed;
    }

    public void setTimePassed(String timePassed) {
        this.timePassed = timePassed;
    }
}
