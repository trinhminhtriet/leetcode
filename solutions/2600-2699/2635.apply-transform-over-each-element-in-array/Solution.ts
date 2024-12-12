function map(inputArray: number[], transformFn: (value: number, index: number) => number): number[] {
    const resultArray: number[] = [];
  
    for (let i = 0; i < inputArray.length; i++) {
        resultArray[i] = transformFn(inputArray[i], i);
    }
  
    return resultArray;
}