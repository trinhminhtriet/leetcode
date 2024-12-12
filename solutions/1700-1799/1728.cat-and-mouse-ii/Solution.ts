type Grid = string[];
type Position = number;  // Type alias for representing positions

// Direction vectors for up, right, down, left movements
const directions: number[] = [0, 1, 0, -1, 0];

// Converts a 2D position to a unique 1D position
function hashPosition(i: number, j: number, n: number): Position {
    return i * n + j;
}

// Main function to check if the mouse can win
function canMouseWin(grid: Grid, catJump: number, mouseJump: number): boolean {
    const m: number = grid.length;
    const n: number = grid[0].length;
    let numFloors: number = 0;  // Number of non-wall cells
    let catPos: Position = 0;   // Cat's starting position
    let mousePos: Position = 0; // Mouse's starting position

    // Preprocess the grid to count non-wall cells and find cat and mouse positions
    for (let i = 0; i < m; i++) {
        for (let j = 0; j < n; j++) {
            if (grid[i][j] !== '#') numFloors++;
            if (grid[i][j] === 'C') catPos = hashPosition(i, j, n);
            else if (grid[i][j] === 'M') mousePos = hashPosition(i, j, n);
        }
    }

    // Memoization cache
    const memo: Map<string, boolean> = new Map<string, boolean>();

    // Dynamic programming function to determine the game state
    function dp(cat: Position, mouse: Position, turn: number): boolean {
        const key: string = `${cat},${mouse},${turn}`;
        if (memo.has(key)) return memo.get(key)!;

        // Base case: If all possible moves have been searched without a win, return false
        if (turn === numFloors * 2) {
            memo.set(key, false);
            return false;
        }

        let result: boolean = false;

        // Mouse's turn
        if (turn % 2 === 0) {
            let [i, j] = [Math.floor(mouse / n), mouse % n];
            for (let k = 0; k < 4; k++) {  // Each direction
                for (let jump = 0; jump <= mouseJump; jump++) {  // Possible jump lengths
                    let x = i + directions[k] * jump;
                    let y = j + directions[k + 1] * jump;
                    if (x < 0 || x >= m || y < 0 || y >= n || grid[x][y] === '#') break;  // Out of bounds or hit a wall
                    if (grid[x][y] === 'F') {
                        memo.set(key, true);
                        return true;  // Mouse wins as it reaches the food
                    }
                    result = dp(cat, hashPosition(x, y, n), turn + 1);
                    if (result) {
                        memo.set(key, true);
                        return true;  // Mouse finds a winning move
                    }
                }
            }
        }
        // Cat's turn
        else {
            let [i, j] = [Math.floor(cat / n), cat % n];
            for (let k = 0; k < 4; k++) {  // Each direction
                for (let jump = 0; jump <= catJump; jump++) {  // Possible jump lengths
                    let x = i + directions[k] * jump;
                    let y = j + directions[k + 1] * jump;
                    if (x < 0 || x >= m || y < 0 || y >= n || grid[x][y] === '#') break;  // Out of bounds or hit a wall
                    if (grid[x][y] === 'F' || hashPosition(x, y, n) === mouse) {
                        memo.set(key, false);
                        return false;  // Cat wins if it reaches the food or catches the mouse
                    }
                    result = !dp(hashPosition(x, y, n), mouse, turn + 1);
                    if (result) {
                        memo.set(key, true);
                        return true;  // Cat finds a move that does not lead to a loss
                    }
                }
            }
        }
      
        memo.set(key, result);
        return result;
    }

    // Initial call to the DP function to decide if the mouse can win
    return dp(catPos, mousePos, 0);
}