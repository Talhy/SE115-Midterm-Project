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
        return "DUMMY"; 
    }

    public static int consecutiveLossDays(String comm) { 
        return 1234; 
    }
    
    public static int daysAboveThreshold(String comm, int threshold) { 
        return 1234; 
    }

    public static int biggestDailySwing(int month) { 
        return 1234; 
    }
    
    public static String compareTwoCommodities(String c1, String c2) { 
        return "DUMMY is better by 1234"; 
    }
    
    public static String bestWeekOfMonth(int month) { 
        return "DUMMY"; 
    }

    public static void main(String[] args) {
        loadData();
        System.out.println("Data loaded – ready for queries");
    }
}