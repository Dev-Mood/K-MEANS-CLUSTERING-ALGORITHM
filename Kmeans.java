import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class KMeansClustering {
    private int k;
    private int maxIterations;
    private List<DataPoint> dataPoints;
    private List<DataPoint> centroids;

    public KMeansClustering(int k, int maxIterations, List<DataPoint> dataPoints) {
        this.k = k;
        this.maxIterations = maxIterations;
        this.dataPoints = dataPoints;
        this.centroids = initializeCentroids();
    }

    private List<DataPoint> initializeCentroids() {
        List<DataPoint> centroids = new ArrayList<>();
        Random random = new Random();
        for (int i = 0; i < k; i++) {
            int index = random.nextInt(dataPoints.size());
            DataPoint centroid = dataPoints.get(index);
            centroids.add(centroid);
        }
        return centroids;
    }

    private int getClosestCentroid(DataPoint dataPoint) {
        int closestCentroid = 0;
        double minDistance = Double.MAX_VALUE;
        for (int i = 0; i < k; i++) {
            DataPoint centroid = centroids.get(i);
            double distance = dataPoint.getDistance(centroid);
            if (distance < minDistance) {
                minDistance = distance;
                closestCentroid = i;
            }
        }
        return closestCentroid;
    }

    public void run() {
        for (int iteration = 0; iteration < maxIterations; iteration++) {
            for (DataPoint dataPoint : dataPoints) {
                int closestCentroid = getClosestCentroid(dataPoint);
                dataPoint.setCluster(closestCentroid);
            }

            for (int i = 0; i < k; i++) {
                double sumX = 0, sumY = 0, count = 0;
                for (DataPoint dataPoint : dataPoints) {
                    if (dataPoint.getCluster() == i) {
                        sumX += dataPoint.getX();
                        sumY += dataPoint.getY();
                        count++;
                    }
                }
                if (count > 0) {
                    centroids.get(i).setX(sumX / count);
                    centroids.get(i).setY(sumY / count);
                }
            }
        }
    }

    public void printResults() {
        for (int i = 0; i < k; i++) {
            System.out.println("Cluster " + i + ":");
            for (DataPoint dataPoint : dataPoints) {
                if (dataPoint.getCluster() == i) {
                    System.out.println(dataPoint);
                }
            }
        }
    }
}
