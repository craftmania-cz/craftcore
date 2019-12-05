package cz.craftmania.craftcore.spigot.utils.time;

import cz.craftmania.craftcore.spigot.Main;
import cz.craftmania.craftcore.spigot.events.time.*;
import cz.craftmania.craftcore.spigot.internal.ServerData;
import cz.craftmania.craftcore.spigot.utils.CoreLogger;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.TemporalField;
import java.time.temporal.WeekFields;
import java.util.Locale;

public class TimeChecker {

    /**
     * The instance.
     */
    static TimeChecker instance = new TimeChecker();

    /**
     * Gets the single instance of TimeChecker.
     *
     * @return single instance of TimeChecker
     */
    public static TimeChecker getInstance() {
        return instance;
    }

    /**
     * Instantiates a new time checker.
     */
    private TimeChecker() {
    }

    public void forceChanged(TimeType time) {
        forceChanged(time, true);
    }

    public void forceChanged(TimeType time, boolean fake) {
        PreDateChangedEvent preDateChanged = new PreDateChangedEvent(time);
        preDateChanged.setFake(fake);
        Main.getInstance().getServer().getPluginManager().callEvent(preDateChanged);
        if (time.equals(TimeType.WEEK)) {
            Main.getCoreLogger().info("Vyzadana zmena casu: " + time.toString());
            WeekChangeEvent weekChange = new WeekChangeEvent();
            weekChange.setFake(fake);
            Main.getInstance().getServer().getPluginManager().callEvent(weekChange);
        } else if (time.equals(TimeType.MONTH)) {
            Main.getCoreLogger().info("Vyzadana zmena casu: " + time.toString());
            MonthChangeEvent monthChange = new MonthChangeEvent();
            monthChange.setFake(fake);
            Main.getInstance().getServer().getPluginManager().callEvent(monthChange);
        }

        DateChangedEvent dateChanged = new DateChangedEvent(time);
        dateChanged.setFake(fake);
        Main.getInstance().getServer().getPluginManager().callEvent(dateChanged);
    }

    public LocalDateTime getTime() {
        return LocalDateTime.now().plusHours(Main.getInstance().getTimeHourOffSet());
    }

    public boolean hasMinuteChanged() {
        int prevMinute = ServerData.getInstance().getPrevMinute();
        int minute = getTime().getMinute();
        ServerData.getInstance().setPrevMinute(minute);
        if (prevMinute == -1) {
            return false;
        }
        if (prevMinute != minute) {
            MinuteChangedEvent minuteChange = new MinuteChangedEvent(minute);
            minuteChange.setFake(false);
            Main.getInstance().getServer().getPluginManager().callEvent(minuteChange);
            return true;
        }
        return false;
    }

    public boolean hasHourChanged() {
        int prevHour = ServerData.getInstance().getPrevHour();
        int hour = getTime().getHour();
        ServerData.getInstance().setPrevHour(hour);
        if (prevHour == -1) {
            return false;
        }
        if (prevHour != hour) {
            HourChangedEvent hourChange = new HourChangedEvent(hour);
            hourChange.setFake(false);
            Main.getInstance().getServer().getPluginManager().callEvent(hourChange);
            return true;
        }
        return false;
    }

    /**
     * Checks for day changed.
     *
     * @return true, if successful
     */
    public boolean hasDayChanged() {
        int prevDay = ServerData.getInstance().getPrevDay();
        int day = getTime().getDayOfMonth();
        ServerData.getInstance().setPrevDay(day);
        if (prevDay == -1) {
            return false;
        }
        if (prevDay != day) {
            DayChangeEvent dayChange = new DayChangeEvent(day);
            dayChange.setFake(false);
            Main.getInstance().getServer().getPluginManager().callEvent(dayChange);
            return true;
        }
        return false;
    }

    /**
     * Checks for month changed.
     *
     * @return true, if successful
     */
    public boolean hasMonthChanged() {
        String prevMonth = ServerData.getInstance().getPrevMonth();
        String month = getTime().getMonth().toString();
        ServerData.getInstance().setPrevMonth(month);
        return !prevMonth.equals(month);

    }

    public boolean hasTimeOffSet() {
        return Main.getInstance().getTimeHourOffSet() != 0;
    }

    /**
     * Checks for week changed.
     *
     * @return true, if successful
     */
    public boolean hasWeekChanged() {
        int prevDate = ServerData.getInstance().getPrevWeekDay();
        LocalDateTime date = getTime();
        TemporalField woy = WeekFields.of(Locale.FRANCE).weekOfWeekBasedYear();
        int weekNumber = date.get(woy);
        ServerData.getInstance().setPrevWeekDay(weekNumber);
        if (prevDate == -1) {
            return false;
        }
        if (weekNumber != prevDate) {
            return true;
        }
        return false;
    }

    /**
     * Update.
     */
    public void update() {
        if (hasTimeOffSet()) {
            //plugin.extraDebug(getTime().getHour() + ":" + getTime().getMinute());
        }

        boolean minuteChanged = false;
        boolean hourChanged = false;
        boolean dayChanged = false;
        boolean weekChanged = false;
        boolean monthChanged = false;

        if (hasMinuteChanged()) {
            minuteChanged = true;
        }
        if (hasHourChanged()) {
            hourChanged = true;
        }
        if (hasDayChanged()) {
            dayChanged = true;
        }
        if (hasWeekChanged()) {
            weekChanged = true;
        }
        if (hasMonthChanged()) {
            monthChanged = true;
        }

        if (minuteChanged) {
            forceChanged(TimeType.MINUTE, false);
        }
        if (hourChanged) {
            forceChanged(TimeType.HOUR, false);
        }
        if (dayChanged) {
            forceChanged(TimeType.DAY, false);
        }
        if (weekChanged) {
            forceChanged(TimeType.WEEK, false);
        }
        if (monthChanged) {
            forceChanged(TimeType.MONTH, false);
        }

    }
}
