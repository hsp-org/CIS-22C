public class NaturalMergeSorter {
	public int getSortedRunLength(int[] array, int arrayLength, int startIndex) {
		if (startIndex < 0 || startIndex >= arrayLength) {
	        return 0;
	    }
		int length = 1;
		while (startIndex + length < arrayLength && array[startIndex + length - 1] <= array[startIndex + length]) {
	        length++;
	    }
		return length;
	}

	public void naturalMergeSort(int[] array, int arrayLength) {
	   boolean needsSorting = true;
	   while (needsSorting) {
	        needsSorting = false;
	        int i = 0;
	        while (i < arrayLength) {
	            int firstRunLength = getSortedRunLength(array, arrayLength, i);
	            if (firstRunLength == arrayLength) {
	                return;
	            }
	            int secondRunStart = i + firstRunLength;
	            if (secondRunStart < arrayLength) {
	                needsSorting = true;
	                int secondRunLength = getSortedRunLength(array, arrayLength, secondRunStart);
	                merge(array, i, i + firstRunLength - 1, secondRunStart + secondRunLength - 1);
	                i = secondRunStart + secondRunLength;
	            } else {
	                i = arrayLength;
	            }
	        }
	    }
	}
	
	public void merge(int[] numbers, int leftFirst, int leftLast, 
	   int rightLast) {
		int mergedSize = rightLast - leftFirst + 1;
		int[] mergedNumbers = new int[mergedSize];
		int mergePos = 0;
		int leftPos = leftFirst;
		int rightPos = leftLast + 1;
      
      // Add smallest element from left or right partition to merged numbers
		while (leftPos <= leftLast && rightPos <= rightLast) {
			if (numbers[leftPos] <= numbers[rightPos]) {
				mergedNumbers[mergePos] = numbers[leftPos];
				leftPos++;
			}
			else {
				mergedNumbers[mergePos] = numbers[rightPos];
				rightPos++;
			}
			mergePos++;
		}
      
      // If left partition isn't empty, add remaining elements to mergedNumbers
		while (leftPos <= leftLast) {
			mergedNumbers[mergePos] = numbers[leftPos];
			leftPos++;
			mergePos++;
		}
      
      // If right partition isn't empty, add remaining elements to mergedNumbers
		while (rightPos <= rightLast) {
			mergedNumbers[mergePos] = numbers[rightPos];
			rightPos++;
			mergePos++;
		}
      
      // Copy merged numbers back to numbers
		for (mergePos = 0; mergePos < mergedSize; mergePos++) {
			numbers[leftFirst + mergePos] = mergedNumbers[mergePos];
		}
      
      // Free temporary array
		mergedNumbers = null;
	}
}