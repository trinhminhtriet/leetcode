type RoundResults = [number, number]; // Tuple type for holding the earliest and latest rounds.

// Decorator-like function to simulate memoization in TypeScript (Python's functools.lru_cache equivalent).
function memoize(fn: (...args: any[]) => RoundResults): (...args: any[]) => RoundResults {
  const cache: Map<string, RoundResults> = new Map();
  return function(...args: any[]): RoundResults {
    const key = JSON.stringify(args); // Serialize arguments array to use as a cache key.
    if (cache.has(key)) return cache.get(key)!;
    const result = fn(...args);
    cache.set(key, result);
    return result;
  };
}

// Global recursive function for dynamic programming with memoization.
const dp: (...args: any[]) => RoundResults = memoize(function (leftIndex: number, rightIndex: number, numPlayers: number): RoundResults {
  // Base case: if both players are about to meet or have already met.
  if (leftIndex >= rightIndex) return [1, 1];

  let earliestRound: number = Infinity;
  let latestRound: number = -Infinity;

  // Iterate over all possible new positions for players after matching.
  for (let i = 1; i <= leftIndex; i++) {
    for (let j = leftIndex - i + 1; j <= rightIndex - i; j++) {
      // Calculate the sum of the new positions.
      let sum = i + j;

      // Ignore invalid matchings based on the constraints.
      if (!(leftIndex + rightIndex - Math.floor(numPlayers / 2) <= sum && sum <= Math.floor((numPlayers + 1) / 2))) continue;

      let [newEarliest, newLatest] = dp(i, j, Math.floor((numPlayers + 1) / 2));
      earliestRound = Math.min(earliestRound, newEarliest + 1);
      latestRound = Math.max(latestRound, newLatest + 1);
    }
  }

  return [earliestRound, latestRound];
});

// Global function to find the earliest and latest rounds where two players could meet.
function earliestAndLatest(n: number, firstPlayer: number, secondPlayer: number): RoundResults {
  // Adjust indices based on player positions and invoke the memoized dynamic programming function.
  return dp(firstPlayer, n - secondPlayer + 1, n);
}
