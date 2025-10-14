package sys.utility;

import java.util.List;
import java.util.Random;

public class IsolationTree {
    private static final Random random = new Random();

    public static double detectAnomaly(List<Double> data) {
        if (data == null || data.size() < 2) return 0.0;

        double mean = data.stream().mapToDouble(Double::doubleValue).average().orElse(0.0);
        double variance = data.stream().mapToDouble(x -> Math.pow(x - mean, 2)).sum() / data.size();
        double stddev = Math.sqrt(variance);

        double anomalyScore = 0.0;
        for (double x : data) {
            double deviation = Math.abs(x - mean);
            if (deviation > 3 * stddev) {
                anomalyScore += deviation / mean;
            }
        }
        return anomalyScore;
    }
}
