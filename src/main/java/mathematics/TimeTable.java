package mathematics;

import java.util.*;

public class TimeTable {


    public static void main(String[] args) {
        List<Activity> activities = new ArrayList<>();
        Activity activity1 = new Activity(1, 4);
        Activity activity2 = new Activity(3, 5);
        Activity activity3 = new Activity(1, 6);
        Activity activity4 = new Activity(5, 7);
        Activity activity5 = new Activity(3, 8);
        Activity activity6 = new Activity(5, 9);
        Activity activity7 = new Activity(6, 10);
        Activity activity8 = new Activity(8,11);
        Activity activity9 = new Activity(8, 12);
        Activity activity10 = new Activity(2, 13);
        Activity activity11 = new Activity(12, 14);

        activities.add(activity1);
        activities.add(activity2);
        activities.add(activity3);
        activities.add(activity4);
        activities.add(activity5);
        activities.add(activity6);
        activities.add(activity7);
        activities.add(activity8);
        activities.add(activity9);
        activities.add(activity10);
        activities.add(activity11);

        Activity beforeFirst = new Activity(0,0);
        Activity afterLast = new Activity(15, 15);

        int result = countMaximumActivities(activities, beforeFirst, afterLast);
        System.out.println(result);
    }

    public static class Activity implements Comparable<Activity> {
        int startingTime;
        int endingTime;

        public Activity(int startingTime, int endingTime) {
            this.startingTime = startingTime;
            this.endingTime = endingTime;
        }

        public int getStartingTime() {
            return startingTime;
        }

        public int getEndingTime() {
            return endingTime;
        }

        public boolean isBetween (Activity first, Activity last) {
            if (this.getStartingTime() > first.getEndingTime() && this.getEndingTime() < last.getStartingTime()) {
                return true;
            }
            return false;
        }

        @Override
        public int compareTo(Activity o) {
            return this.endingTime - o.endingTime;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Activity activity = (Activity) o;
            return startingTime == activity.startingTime && endingTime == activity.endingTime;
        }

        @Override
        public int hashCode() {
            return Objects.hash(startingTime, endingTime);
        }
    }

    public static class Interval {
        Activity beforeFirst;
        Activity afterLast;

        public Interval(Activity beforeFirst, Activity afterLast) {
            this.beforeFirst = beforeFirst;
            this.afterLast = afterLast;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Interval interval = (Interval) o;
            return Objects.equals(beforeFirst, interval.beforeFirst) && Objects.equals(afterLast, interval.afterLast);
        }

        @Override
        public int hashCode() {
            return Objects.hash(beforeFirst, afterLast);
        }
    }

    public static int countMaximumActivities (List<Activity> allActivities, Activity beforeFirst, Activity afterLast) {
        Collections.sort(allActivities);

        Map<Interval, Integer> results = new HashMap<>();
        Interval interval = new Interval(beforeFirst, afterLast);


        if (results.containsKey(interval)) {
            return results.get(interval);
        } else if (countNumberOfInternalActivities(allActivities, beforeFirst, afterLast) == 0) {
            return 0;
        } else if (countNumberOfInternalActivities(allActivities, beforeFirst, afterLast) == 1) {
            return 1;
        } else {

            List<Integer> intermediateResults = new ArrayList<>();
            for (int k = 0; k<allActivities.size(); k++) {


                List<Activity> leftActivities = new ArrayList<>();
                for (int i = 0; i<k; i++) {
                    if (allActivities.get(i).isBetween(beforeFirst, allActivities.get(k))) {
                        leftActivities.add(allActivities.get(i));
                    }
                }


                List<Activity> rightActivities = new ArrayList<>();
                for (int i = k; i<allActivities.size(); i++) {
                    if (allActivities.get(i).isBetween(allActivities.get(k), afterLast)) {
                        rightActivities.add(allActivities.get(i));
                    }
                }


                int leftMaximum = countMaximumActivities(leftActivities, beforeFirst, allActivities.get(k));
                int rightMaximum = countMaximumActivities(rightActivities, allActivities.get(k), afterLast);
                int result = leftMaximum + rightMaximum + 1;
                intermediateResults.add(result);
            }
            int maximumActivities = Collections.max(intermediateResults);
            results.put(interval, maximumActivities);
            return maximumActivities;
        }
    }

    public static int countNumberOfInternalActivities (List<Activity> activities, Activity beforeFirst, Activity afterLast) {
        int counter = 0;
        if (activities.isEmpty()) {
            return 0;
        }
        else {
            for (Activity activity: activities) {
            if (activity.getStartingTime() > beforeFirst.getEndingTime() && activity.getEndingTime() < afterLast.getStartingTime()) {
                counter++;
            }
        }
        return counter;
        }
    }
}
