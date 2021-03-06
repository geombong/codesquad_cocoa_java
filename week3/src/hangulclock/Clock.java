package hangulclock;

import hangulclock.resource.HourHangul;
import hangulclock.resource.MinuteHangul;
import shell.Shell;

import java.io.IOException;
import java.time.LocalTime;
import java.util.Timer;
import java.util.TimerTask;

public class Clock {
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_RED = "\033[1;31m";
    Shell shell = new Shell();

    public void clockStart() throws IOException {
        Timer timer = new Timer();
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                try {
                    runClock();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };
        timer.schedule(timerTask, 0, 60000);
        shell.Start();
    }

    private void runClock() throws IOException {
        String[][] clock =
                {{"한", "두", "세", "네", "다", "섯"},
                        {"여", "섯", "일", "곱", "여", "덟"},
                        {"아", "홉", "열", "한", "두", "시"},
                        {"자", "이", "삼", "사", "오", "십"},
                        {"정", "일", "이", "삼", "사", "육"},
                        {"오", "오", "칠", "팔", "구", "분"}};
        HourHangul[] hourHangul = HourHangul.values();
        MinuteHangul[] minuteHangul = MinuteHangul.values();
        LocalTime localTime = LocalTime.now();
        long hour = localTime.getHour();
        long minute = localTime.getMinute();
        long hourValue = hour % 12;

        createHour(clock, hourValue, hourHangul);
        createMinute(clock, minute, minuteHangul);
        midnight(clock, hour);
        printClock(clock);
    }

    private void createHour(String[][] clock, long hourValue, HourHangul[] hourHangul) {
        clock[2][5] = ANSI_RED + "시" + ANSI_RESET;
        for (HourHangul hangul : hourHangul) {
            if (hourValue == hangul.getHour()) {
                clock[hangul.getFirstIndex()][hangul.getLastIndex()] = ANSI_RED + hangul.getHangul() + ANSI_RESET;
            }
        }
    }

    private void createMinute(String[][] clock, long minute, MinuteHangul[] minuteHangul) {
        if (minute != 0) {
            clock[5][5] = ANSI_RED + "분" + ANSI_RESET;
        }
        long digit1 = minute % 10;
        long digit10 = minute - digit1;
        for (MinuteHangul hangul : minuteHangul) {
            if (minute > 10) {
                clock[3][5] = ANSI_RED + "십" + ANSI_RESET;
                if (digit10 == hangul.getMinute()) {
                    clock[hangul.getFirstIndex()][hangul.getLastIndex()] = ANSI_RED + hangul.getHangul() + ANSI_RESET;
                }
                if (digit1 == hangul.getMinute()) {
                    clock[hangul.getFirstIndex()][hangul.getLastIndex()] = ANSI_RED + hangul.getHangul() + ANSI_RESET;
                }
            }
            if (minute < 10) {
                if (digit1 == hangul.getMinute()) {
                    clock[hangul.getFirstIndex()][hangul.getLastIndex()] = ANSI_RED + hangul.getHangul() + ANSI_RESET;
                }
            }
        }
    }

    private void printClock(String[][] clock) {
        System.out.println();
        System.out.println("====한글시계====");
        for (String[] chars : clock) {
            for (String aChar : chars) {
                System.out.print(aChar + " ");
            }
            System.out.println();
        }
        System.out.println();
    }

    private void midnight(String[][] clock, long hour) {
        if (hour == 12) {
            clock[4][0] = ANSI_RED + "정" + ANSI_RESET;
            clock[5][0] = ANSI_RED + "오" + ANSI_RESET;
        }
        if (hour == 24) {
            clock[3][0] = ANSI_RED + "자" + ANSI_RESET;
            clock[4][0] = ANSI_RED + "정" + ANSI_RESET;
        }
    }
}