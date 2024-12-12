import java.util.*;

class MovieRentingSystem {
    // A map to hold unrented movies with each movie's price and shop info.
    private TreeMap<Integer, TreeSet<int[]>> unrentedMovies;
  
    // A map to track the price for each shop and movie combination.
    private Map<Integer, Integer> priceLookup;
  
    // A SortedSet to hold rented movies with their price, shop, and movie info.
    private TreeSet<int[]> rentedMovies;

    public MovieRentingSystem(int n, int[][] entries) {
        unrentedMovies = new TreeMap<>();
        priceLookup = new HashMap<>();
        rentedMovies = new TreeSet<>(
            (a, b) -> {
                if (a[0] != b[0]) return a[0] - b[0]; // First sort by price
                if (a[1] != b[1]) return a[1] - b[1]; // Then by shop
                return a[2] - b[2]; // Finally by movie
            }
        );
      
        for (int[] entry : entries) {
            int shop = entry[0];
            int movie = entry[1];
            int price = entry[2];
          
            int[] shopPricePair = new int[]{price, shop};
            unrentedMovies.computeIfAbsent(movie, x -> new TreeSet<>(
                (a, b) -> {
                    if (a[0] != b[0]) return a[0] - b[0]; // Sort by price
                    return a[1] - b[1]; // Then by shop
                }
            )).add(shopPricePair);
          
            priceLookup.put(shop * 10001 + movie, price); // Composite key to map uniquely to every shop and movie combination
        }
    }

    // Return a list of shops that have the specified movie among the 5 cheapest options.
    public List<Integer> search(int movie) {
        List<Integer> shops = new ArrayList<>();
        if (!unrentedMovies.containsKey(movie)) return shops;
      
        for (int[] pair : unrentedMovies.get(movie).headSet(new int[]{Integer.MAX_VALUE, Integer.MAX_VALUE}, true)) {
            shops.add(pair[1]);
            if (shops.size() == 5) break;
        }
      
        return shops;
    }

    // Rent a movie by removing it from the unrented list and adding it to the rented list.
    public void rent(int shop, int movie) {
        int price = priceLookup.get(shop * 10001 + movie);
        unrentedMovies.get(movie).remove(new int[]{price, shop});
        rentedMovies.add(new int[]{price, shop, movie});
    }

    // Drop off a rented movie by moving it back from the rented list to the unrented list.
    public void drop(int shop, int movie) {
        int price = priceLookup.get(shop * 10001 + movie);
        unrentedMovies.computeIfAbsent(movie, x -> new TreeSet<>(
            Comparator.comparingInt((int[] arr) -> arr[0]).thenComparingInt(arr -> arr[1])
        )).add(new int[]{price, shop});
        rentedMovies.remove(new int[]{price, shop, movie});
    }

    // Report the 5 cheapest rented movies as a list of [shop, movie] pairs.
    public List<List<Integer>> report() {
        List<List<Integer>> result = new ArrayList<>();
        for (int[] info : rentedMovies) {
            List<Integer> pair = new ArrayList<>();
            pair.add(info[1]);
            pair.add(info[2]);
            result.add(pair);
            if (result.size() == 5) break;
        }
      
        return result;
    }
}

// Usage example:
// MovieRentingSystem obj = new MovieRentingSystem(n, entries);
// List<Integer> availableShops = obj.search(movie);
// obj.rent(shop, movie);
// obj.drop(shop, movie);
// List<List<Integer>> report = obj.report();
