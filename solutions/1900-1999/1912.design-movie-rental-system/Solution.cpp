#include <vector>
#include <unordered_map>
#include <set>

using namespace std;

// Defining a comparator for the set so that we can keep the entries sorted based on price first, and then shop.
struct MovieComparator {
    bool operator() (const tuple<int, int, int>& a, const tuple<int, int, int>& b) const {
        if (get<0>(a) != get<0>(b))
            return get<0>(a) < get<0>(b);
        if (get<1>(a) != get<1>(b))
            return get<1>(a) < get<1>(b);
        return get<2>(a) < get<2>(b);
    }
};

class MovieRentingSystem {
private:
    unordered_map<int, set<pair<int, int>>> unrentedMovies; // {movie: set of (price, shop)}
    unordered_map<pair<int, int>, int, hash<pair<int,int>>> priceLookup; // {(shop, movie): price}
    set<tuple<int, int, int>, MovieComparator> rentedMovies; // set of (price, shop, movie)

public:
    MovieRentingSystem(int n, vector<vector<int>>& entries) {
        for (const auto& e : entries) {
            int shop = e[0], movie = e[1], price = e[2];
            unrentedMovies[movie].insert({price, shop});
            priceLookup[{shop, movie}] = price;
        }
    }

    vector<int> search(int movie) {
        vector<int> shops;
        for (const auto& [price, shop] : unrentedMovies[movie]) {
            if (shops.size() >= 5) break; // Only interested in the 5 cheapest options
            shops.push_back(shop);
        }
        return shops;
    }

    void rent(int shop, int movie) {
        int price = priceLookup[{shop, movie}];
        unrentedMovies[movie].erase({price, shop});
        rentedMovies.insert(make_tuple(price, shop, movie));
    }

    void drop(int shop, int movie) {
        int price = priceLookup[{shop, movie}];
        unrentedMovies[movie].insert({price, shop});
        rentedMovies.erase(make_tuple(price, shop, movie));
    }

    vector<vector<int>> report() {
        vector<vector<int>> cheapestRentedMovies;
        for (const auto& [price, shop, movie] : rentedMovies) {
            if (cheapestRentedMovies.size() >= 5) break; // Report only the 5 cheapest rented movies
            cheapestRentedMovies.push_back({shop, movie});
        }
        return cheapestRentedMovies;
    }
};

// Usage example:
// MovieRentingSystem obj(n, entries);
// vector<int> availableShops = obj.search(movie);
// obj.rent(shop, movie);
// obj.drop(shop, movie);
// vector<vector<int>> report = obj.report();