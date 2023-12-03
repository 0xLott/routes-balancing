public class Stopwatch {
    public static long measure(Runnable algorithm) {
        long startTime = System.nanoTime();

        algorithm.run();

        long endTime = System.nanoTime();

        long executionTime = endTime - startTime;

        return executionTime;
    }
}
