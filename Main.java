// Main.java — Students version
import java.io.*;
import java.util.*;

public class Main {
    static final int MONTHS = 12;
    static final int DAYS = 28;
    static final int COMMS = 5;
    static String[] commodities = {"Gold", "Oil", "Silver", "Wheat", "Copper"};
    static String[] months = {"January","February","March","April","May","June",
                              "July","August","September","October","November","December"};

    // profits[month][day][commodity]
    static int[][][] profits = new int[MONTHS][DAYS][COMMS];
    // ======== REQUIRED METHOD LOAD DATA (Students fill this) ========
    public static void loadData() {
        for (int m = 0; m < MONTHS; m++) {
            String fileName = "Data_Files/" + months[m] + ".txt";
            BufferedReader br = null;

            try {
                br = new BufferedReader(new FileReader(fileName));
                String line;

                while ((line = br.readLine()) != null) {
                    String[] parts = line.split(",");
                    if (parts.length != 3) continue;

                    int day;
                    int profit;

                    try {
                        day = Integer.parseInt(parts[0].trim()) - 1;
                        profit = Integer.parseInt(parts[2].trim());
                    } catch (Exception e) {
                        continue;
                    }

                    if (day < 0 || day >= DAYS) continue;

                    int cIndex = -1;
                    for (int c = 0; c < COMMS; c++) {
                        if (commodities[c].equals(parts[1].trim())) {
                            cIndex = c;
                            break;
                        }
                    }
                    if (cIndex == -1) continue;

                    profits[m][day][cIndex] += profit;
                }

            } catch (Exception e) {
                // robust: do nothing
            } finally {
                try {
                    if (br != null) br.close();
                } catch (Exception e) {
                    // ignore
                }
            }
        }
    }


    // ======== 10 REQUIRED METHODS (Students fill these) ========

    public static String mostProfitableCommodityInMonth(int month) {
        if (month < 0 || month >= MONTHS) return "INVALID_MONTH";

        int bestSum = Integer.MIN_VALUE;
        int bestIndex = -1;

        for (int c = 0; c < COMMS; c++) {
            int sum = 0;
            for (int d = 0; d < DAYS; d++) {
                sum += profits[month][d][c];
            }
            if (sum > bestSum) {
                bestSum = sum;
                bestIndex = c;
            }
        }
        return commodities[bestIndex] + " " + bestSum;
    }

    public static int totalProfitOnDay(int month, int day) {
        if (month < 0 || month >= MONTHS || day < 1 || day > DAYS)
            return -99999;

        int sum = 0;
        for (int c = 0; c < COMMS; c++) {
            sum += profits[month][day - 1][c];
        }
        return sum;
    }

    public static int commodityProfitInRange(String commodity, int from, int to) {
        if (from < 1 || to > DAYS || from > to) return -99999;

        int cIndex = -1;
        for (int c = 0; c < COMMS; c++) {
            if (commodities[c].equals(commodity)) {
                cIndex = c;
                break;
            }
        }
        if (cIndex == -1) return -99999;

        int sum = 0;
        for (int m = 0; m < MONTHS; m++) {
            for (int d = from - 1; d <= to - 1; d++) {
                sum += profits[m][d][cIndex];
            }
        }
        return sum;
    }

    public static int bestDayOfMonth(int month) {
        if (month < 0 || month >= MONTHS) return -1;

        int bestDay = 1;
        int bestSum = Integer.MIN_VALUE;

        for (int d = 0; d < DAYS; d++) {
            int sum = 0;
            for (int c = 0; c < COMMS; c++) {
                sum += profits[month][d][c];
            }
            if (sum > bestSum) {
                bestSum = sum;
                bestDay = d + 1;
            }
        }
        return bestDay;
    }
    
    public static String bestMonthForCommodity(String comm) {
        int cIndex = -1;
        for (int c = 0; c < COMMS; c++) {
            if (commodities[c].equals(comm)) {
                cIndex = c;
                break;
            }
        }
        if (cIndex == -1) return "INVALID_COMMODITY";

        int bestMonth = 0;
        int bestSum = Integer.MIN_VALUE;

        for (int m = 0; m < MONTHS; m++) {
            int sum = 0;
            for (int d = 0; d < DAYS; d++) {
                sum += profits[m][d][cIndex];
            }
            if (sum > bestSum) {
                bestSum = sum;
                bestMonth = m;
            }
        }
        return months[bestMonth];
    }

    public static int consecutiveLossDays(String comm) {
        int cIndex = -1;
        for (int c = 0; c < COMMS; c++) {
            if (commodities[c].equals(comm)) {
                cIndex = c;
                break;
            }
        }
        if (cIndex == -1) return -1;

        int maxStreak = 0;
        int current = 0;

        for (int m = 0; m < MONTHS; m++) {
            for (int d = 0; d < DAYS; d++) {
                if (profits[m][d][cIndex] < 0) {
                    current++;
                    if (current > maxStreak) maxStreak = current;
                } else {
                    current = 0;
                }
            }
        }
        return maxStreak;
    }
    
    public static int daysAboveThreshold(String comm, int threshold) {
        int cIndex = -1;
        for (int c = 0; c < COMMS; c++) {
            if (commodities[c].equals(comm)) {
                cIndex = c;
                break;
            }
        }
        if (cIndex == -1) return -1;

        int count = 0;
        for (int m = 0; m < MONTHS; m++) {
            for (int d = 0; d < DAYS; d++) {
                if (profits[m][d][cIndex] > threshold) count++;
            }
        }
        return count;
    }

    public static int biggestDailySwing(int month) {
        if (month < 0 || month >= MONTHS) return -99999;

        int prev = 0;
        for (int c = 0; c < COMMS; c++) {
            prev += profits[month][0][c];
        }

        int maxSwing = 0;
        for (int d = 1; d < DAYS; d++) {
            int curr = 0;
            for (int c = 0; c < COMMS; c++) {
                curr += profits[month][d][c];
            }
            int diff = Math.abs(curr - prev);
            if (diff > maxSwing) maxSwing = diff;
            prev = curr;
        }
        return maxSwing;
    }
    
    public static String compareTwoCommodities(String c1, String c2) {
        int i1 = -1, i2 = -1;

        for (int i = 0; i < COMMS; i++) {
            if (commodities[i].equals(c1)) i1 = i;
            if (commodities[i].equals(c2)) i2 = i;
        }
        if (i1 == -1 || i2 == -1) return "INVALID_COMMODITY";

        int sum1 = 0, sum2 = 0;
        for (int m = 0; m < MONTHS; m++) {
            for (int d = 0; d < DAYS; d++) {
                sum1 += profits[m][d][i1];
                sum2 += profits[m][d][i2];
            }
        }

        if (sum1 > sum2) return c1 + " is better by " + (sum1 - sum2);
        if (sum2 > sum1) return c2 + " is better by " + (sum2 - sum1);
        return "Equal";
    }
    
    public static String bestWeekOfMonth(int month) {if (month < 0 || month >= MONTHS) return "INVALID_MONTH";

        int bestWeek = 1;
        int bestSum = Integer.MIN_VALUE;

        for (int w = 0; w < 4; w++) {
            int sum = 0;
            for (int d = w * 7; d < w * 7 + 7; d++) {
                for (int c = 0; c < COMMS; c++) {
                    sum += profits[month][d][c];
                }
            }
            if (sum > bestSum) {
                bestSum = sum;
                bestWeek = w + 1;
            }
        }
        return "Week " + bestWeek;
    }

    public static void main(String[] args) {
        loadData();
        System.out.println("Data loaded – ready for queries");
    }
}