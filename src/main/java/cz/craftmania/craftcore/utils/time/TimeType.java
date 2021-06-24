package cz.craftmania.craftcore.utils.time;

public enum TimeType {
    MONTH, WEEK, DAY, HOUR, MINUTE;

    public static TimeType getTimeType(String str) {
        for (TimeType time : TimeType.values()) {
            if (time.toString().equalsIgnoreCase(str)) {
                return time;
            }
        }
        return null;
    }
}
