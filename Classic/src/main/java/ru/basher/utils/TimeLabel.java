package ru.basher.utils;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Calendar;

@Getter
@AllArgsConstructor
public class TimeLabel {

    private final int hours;
    private final int minutes;
    private final int fullSeconds;

    public TimeLabel(String time) {
        String[] arr = time.split(":");
        this.hours = Integer.parseInt(arr[0]);
        this.minutes = Integer.parseInt(arr[1]);
        this.fullSeconds = this.hours * 3600 + this.minutes * 60;
    }

    public TimeLabel(long globalTimeInMills) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(globalTimeInMills);
        this.hours = calendar.get(Calendar.HOUR_OF_DAY);
        this.minutes = calendar.get(Calendar.MINUTE);
        this.fullSeconds = this.hours * 3600 + this.minutes * 60;
    }

    public int differenceInSeconds(TimeLabel timeLabel) {
        if (this.fullSeconds >= timeLabel.getFullSeconds()) {
            return this.fullSeconds - timeLabel.getFullSeconds();
        } else {
            int oneDay = 86400;
            return oneDay - (timeLabel.getFullSeconds() - this.fullSeconds);
        }
    }
}
