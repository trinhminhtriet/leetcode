type CounterOperations = {
    increment: () => number;
    decrement: () => number;
    reset: () => number;
};

// The current value of the counter
let currentValue: number;

// Initialize the counter with a specific value
function initializeCounter(initialValue: number): void {
    currentValue = initialValue;
}

// Increment the counter and return the new value
function incrementCounter(): number {
    return ++currentValue;
}

// Decrement the counter and return the new value
function decrementCounter(): number {
    return --currentValue;
}

// Reset the counter to the initial value and return it
function resetCounter(initialValue: number): number {
    currentValue = initialValue;
    return currentValue;
}

// Create a counter with the given initial value and return an object with operation methods
function createCounter(initialValue: number): CounterOperations {
    initializeCounter(initialValue);
    return {
        increment: incrementCounter,
        decrement: decrementCounter,
        reset: () => resetCounter(initialValue),
    };
}
