package com.pabhinav.zsrv.zstacklistview;

/**
 * Dummy data holding class.
 * It has some static methods for fetching some dummy intities.
 *
 * @author pabhinav (pabhinav@iitrpr.ac.in)
 */
public class DummyData {

    /** Counters **/
    private static int counterForLocation;
    private static int counterForName;
    private static int counterForTimePassed;

    /** Initializing counters **/
    static {
        counterForLocation = 0;
        counterForName = 0;
        counterForTimePassed = 0;
    }

    /**
     * Used to fetch some random locations in the world.
     *
     * @return Country name.
     */
    public static String dummyLocation(){
        checkCounterForOverflow();
        String locations[] = new String [] {
                "France",
                "London",
                "USA",
                "Canada",
                "Italy",
                "South America",
                "Australia",
                "South Africa",
                "Hungary",
                "Portugal",
                "India",
                "Singapore",
                "China",
                "Japan",
                "Korea",
                "Hong Kong",
                "Antartica",
                "Russia",
                "Germany",
                "Sri Lanka"
        };
        return locations[counterForLocation++];
    }

    /**
     * Used to fetch some dummy time passed, till last user login.
     *
     * @return time passed till last login.
     */
    public static String dummyTimePassed(){
        checkCounterForOverflow();
        String timePassed[] = new String [] {
                "10min ago",
                "1min ago",
                "2min ago",
                "long time ago",
                "5min ago",
                "Now",
                "yesterday",
                "2days ago",
                "5days ago",
                "1month ago",
                "2month ago",
                "5month ago",
                "1year ago",
                "2year ago",
                "5year ago",
                "1min ago",
                "2min ago",
                "long time ago",
                "5min ago",
                "Now"
        };
        return timePassed[counterForTimePassed++];
    }

    /**
     * Used to fetch name of person.
     *
     * @return name of a person.
     */
    public static String dummyName(){
        checkCounterForOverflow();
        String names[] = new String[] {
                "Zlatan Ibrahimovic",
                "Lionel Messi",
                "Joan Kim",
                "Billy Foster",
                "Patrick Vierra",
                "Marie Crawfrod",
                "Sean White",
                "Christian White",
                "Bruce Brown",
                "Ashley Martinez",
                "Jennifer Guerro",
                "Didier Drogba",
                "Robbin Johnson",
                "Abhinav Puri",
                "Steve Thomas",
                "Quarentino Jeorge",
                "Klevtov Whiskey",
                "Nurik Roman",
                "Cattie Pack",
                "Ellie Johnson"
        };
        return names[counterForName++];
    }

    /**
     * Resets all counters to its initial starting value.
     */
    public static void resetAllCounters(){
        counterForTimePassed = 0;
        counterForLocation = 0;
        counterForName = 0;
    }

    /**
     * If counter overflow, i.e., crosses the index of dummy
     * list data, then need to begin again, which gives it
     * cyclic property.
     */
    private static void checkCounterForOverflow(){
        if(counterForName >= 20){
            counterForName = 0;
        } else if(counterForTimePassed >= 20){
            counterForTimePassed = 0;
        } else if(counterForLocation >= 20){
            counterForLocation = 0;
        }
    }

}
