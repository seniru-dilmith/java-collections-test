import java.util.*;

public class CollectionPerformanceTest {
    private static final int NUM_ELEMENTS = 100_000; // Reduce the number of elements
    private static final int NUM_TESTS = 100; // Reduce the number of tests
    private static final int MAX_VALUE = 99_999; // Reduce the maximum value for elements

    public static void main(String[] args) {
        Random random = new Random();

        testSet("HashSet", new HashSet<>(), random);
        testSet("TreeSet", new TreeSet<>(), random);
        testSet("LinkedHashSet", new LinkedHashSet<>(), random);

        testCollection("ArrayList", new ArrayList<>(), random);
        testCollection("LinkedList", new LinkedList<>(), random);
        testCollection("ArrayDeque", new ArrayDeque<>(), random);
        testCollection("PriorityQueue", new PriorityQueue<>(), random);

        testMap("HashMap", new HashMap<>(), random);
        testMap("TreeMap", new TreeMap<>(), random);
        testMap("LinkedHashMap", new LinkedHashMap<>(), random);
    }

    private static void testCollection(String name, Collection<Integer> collection, Random random) {

        // loading regular collections
        for (int i = 0; i < NUM_ELEMENTS; i++) {
            collection.add(random.nextInt(MAX_VALUE + 1));
        }

        long addTime = addForCollection(collection, random);  // time for add operation
        long removeTime = removeForCollection(collection, random);  // time for remove operation
        long containsTime = containsForCollection(collection, random);  // time for contains operation
        long clearTime = clearForCollection(collection, random);  // time for clear operation

        printResults(name, addTime, removeTime, containsTime, clearTime);
    }

    private static void testSet(String name, Collection<Integer> set, Random random) {
        // loading sets
        for (int i = 0; i < NUM_ELEMENTS; i++) {
            set.add(random.nextInt(NUM_ELEMENTS + 1));
        }

        long addTime = addForCollection(set, random);  // time for add operation
        long removeTime = removeForCollection(set, random);  // time for remove operation
        long containsTime = containsForCollection(set, random);  // time for contains operation
        long clearTime = clearForCollection(set, random);  // time for clear operation

        printResults(name, addTime, removeTime, containsTime, clearTime);
    }

    private static void testMap(String name, Map<Integer, Integer> map, Random random) {

        // loading maps
        for (int i = 0; i < NUM_ELEMENTS; i++) {
            map.put(i, random.nextInt(MAX_VALUE + 1));
        }

        long addTime = addForMap(map, random);  // time for add operation
        long removeTime = removeForMap(map, random);  // time for remove operation
        long containsTime = containsForMap(map, random);  // time for contains operation
        long clearTime = clearForMap(map, random);  // time for clear operation

        printResults(name, addTime, removeTime, containsTime, clearTime);
    }

    private static long clearForMap(Map<Integer, Integer> map, Random random) {
        long totalTime = 0;
        for (int i = 0; i < NUM_TESTS; i++) {

            long startTime = System.nanoTime();
            map.clear();
            long endTime = System.nanoTime();

            for (int j = 0; j < NUM_ELEMENTS; j++) {
                map.put(j, random.nextInt(MAX_VALUE + 1));
            }

            totalTime += (endTime - startTime);
        }
        return totalTime;
    }

    private static long containsForMap(Map<Integer, Integer> map, Random random) {
        long totalTime = 0;
        for (int i = 0; i < NUM_TESTS; i++) {
            int randomValue = random.nextInt(MAX_VALUE + 1);

            long startTime = System.nanoTime();
            map.containsKey(randomValue);
            long endTime = System.nanoTime();

            totalTime += (endTime - startTime);
        }
        return totalTime;
    }

    private static long removeForMap(Map<Integer, Integer> map, Random random) {
        long totalTime = 0;
        for (int i = 0; i < NUM_TESTS; i++) {
            int randomValue = random.nextInt(MAX_VALUE + 1);

            long startTime = System.nanoTime();
            var result = map.remove(randomValue);
            long endTime = System.nanoTime();
            if (result != null) map.put(randomValue, randomValue);

            totalTime += (endTime - startTime);
        }
        return totalTime;
    }

    private static long addForMap(Map<Integer, Integer> map, Random random) {
        long totalTime = 0;
        for (int i = 0; i < NUM_TESTS; i++) {
            int randomValue = random.nextInt(MAX_VALUE + 1);

            long startTime = System.nanoTime();
            map.put(NUM_ELEMENTS, randomValue);
            long endTime = System.nanoTime();
            map.remove(NUM_ELEMENTS);

            totalTime += (endTime - startTime);
        }
        return totalTime;
    }

    private static long clearForCollection(Collection<Integer> collection, Random random) {
        long totalTime = 0;
        for (int i = 0; i < NUM_TESTS; i++) {

            long startTime = System.nanoTime();
            collection.clear();
            long endTime = System.nanoTime();

            for (int j = 0; j < NUM_ELEMENTS; j++) {
                collection.add(random.nextInt(MAX_VALUE + 1));
            }

            totalTime += (endTime - startTime);
        }
        return totalTime;
    }

    private static long containsForCollection(Collection<Integer> collection, Random random) {
        long totalTime = 0;
        for (int i = 0; i < NUM_TESTS; i++) {
            int randomValue = random.nextInt(MAX_VALUE + 1);

            long startTime = System.nanoTime();
            collection.contains(randomValue);
            long endTime = System.nanoTime();

            totalTime += (endTime - startTime);
        }
        return totalTime;
    }

    private static long addForCollection(Collection<Integer> collection, Random random) {
        long totalTime = 0;
        for (int i = 0; i < NUM_TESTS; i++) {
            int randomValue = random.nextInt(MAX_VALUE + 1);

            long startTime = System.nanoTime();
            collection.add(randomValue);
            long endTime = System.nanoTime();
            collection.add(randomValue);

            totalTime += (endTime - startTime);
        }
        return totalTime;
    }

    private static long removeForCollection(Collection<Integer> collection, Random random) {
        long totalTime;
        totalTime = 0;
        for (int i = 0; i < NUM_TESTS; i++) {
            int randomValue = random.nextInt(MAX_VALUE + 1);

            long startTime = System.nanoTime();
            var result = collection.remove(NUM_TESTS - 1);
            long endTime = System.nanoTime();
            if (result) collection.add(randomValue);

            totalTime += (endTime - startTime);
        }
        return totalTime;
    }

    private static void printResults(String name, long addTotalTime, long removeTotalTime, long containsTotalTime, long clearTotalTime) {
        double averageAddTime = (double) addTotalTime / NUM_TESTS;
        double averageContainsTime = (double) containsTotalTime / NUM_TESTS;
        double averageRemoveTime = (double) removeTotalTime / NUM_TESTS;
        double averageClearTime = (double) clearTotalTime / NUM_TESTS;

        System.out.println("++++++ " + name + " ++++++");
        System.out.println("Add operation : " + averageAddTime + " ns");
        System.out.println("Contains operation : " + averageContainsTime + " ns");
        System.out.println("Remove operation : " + averageRemoveTime + " ns");
        System.out.println("Clear operation : " + averageClearTime + " ns");
        System.out.println();
    }

}