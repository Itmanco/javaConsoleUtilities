package utils;


import java.util.InputMismatchException;
import java.util.Scanner;

interface menu {
	
	default void startMenu() {
		mainMenu();
		
		Scanner scan = new Scanner(System.in);
	    int menuOption = scan.nextInt();
	   
	    while(menuOption != -1) {
		    functionalities(menuOption, scan);
		    mainMenu();
		    menuOption = scan.nextInt();
	    }
	    scan.close();
	}
	
	/**
	 * 利用可能なさまざまな操作をコンソールに表示します
	 */
	abstract void mainMenu();
	
	/**
	 * メインメニューで選択した機能を実行する
	 * @param int メインメニューからオプションを選択
	 * @param Scanner コンソールを操作するために使用
	 */
	abstract void functionalities(int option, Scanner scan);
}

abstract class SortInt{
	int[] initialArray, finalArray;
	int movements = 0;
	long startTime, elapsedTime;
	final String name;
	
	static public String toString(int[] array) {
		String asnwer = "[ ";
		for(int item: array)
		{
			asnwer += String.format("　%4d ",item);
			asnwer += array[array.length-1] != item ? "|":" ]";
		}
		
		return asnwer;
	}
	static public String toString(int[] array, int a, int b) {
		String asnwer = "[ ";
		for(int i = 0; i < array.length; i++)
		{
			if(i == a){
				asnwer += String.format("> %4d ",array[i]);
			}else if( i == b){
				asnwer += String.format("< %4d ",array[i]);
			}else {
				asnwer += String.format("　%4d ",array[i]);
			}
			
			asnwer += array[array.length-1] != array[i] ? "|":" ]";
		}
		
		return asnwer;
	}
	public String toString() {
		String asnwer = "[ ";
		for(int item: this.initialArray)
		{
			asnwer += String.format("　%4d ",item);
			asnwer += this.initialArray[this.initialArray.length-1] != item ? "|":" ]";
		}
		
		return asnwer;
	}	
	public SortInt(int[] initialArray, String name) {
		this.initialArray = copyArray(initialArray);
		this.finalArray = copyArray(initialArray);
		this.name = name;
	}
	private int[] copyArray(int[] a) {
        int[] destinationInts = new int[a.length];
        System.arraycopy(a, 0, destinationInts, 0, a.length);
        return destinationInts;
	}
	
	public double setElapsedTime(long stopNanoTime) {
		this.elapsedTime = System.nanoTime() - this.startTime;
		return (double)elapsedTime / 1_000_000_000.0;
	}
	public double getElapsedTime() {
		return (double)elapsedTime / 1_000_000_000.0;
	}
	public void addMovement() {
		this.movements++;
	}
	abstract int[]  sort();
}

class QuickSort extends SortInt{


	public QuickSort(int[] initialArray) {
		super(initialArray, "QuickSort");
		System.out.println(toString()+"<-最初");
	}


	@Override
	int[] sort() {
		super.startTime = System.nanoTime();
		
		quickSort(finalArray, 0,finalArray.length-1);
		
		super.setElapsedTime(System.nanoTime());
		return finalArray;
	}
	
	public void quickSort(int[] arr, int low, int high) {
	    if (low < high) {
	        int pivotIndex = partition(arr, low, high);
	        quickSort(arr, low, pivotIndex - 1);
	        quickSort(arr, pivotIndex + 1, high);
	    }
	}

	public int partition(int[] arr, int low, int high) {
	    int pivot = arr[high];
	    int i = low - 1;
	    for (int j = low; j < high; j++) {
	        if (arr[j] < pivot) {
	            i++;
	            int temp = arr[i];
	            arr[i] = arr[j];
	            arr[j] = temp;
	            addMovement();
	    	    System.out.println(toString(arr,i,j));
	        }
	    }
	    int temp = arr[i + 1];
	    arr[i + 1] = arr[high];
	    arr[high] = temp;
	    addMovement();
	    System.out.println(toString(arr,i + 1,high));
	    return i + 1;
	}
}


class BubbleSort extends SortInt{

	public BubbleSort(int[] initialArray) {
		super(initialArray, "BubbleSort");
		System.out.println(toString()+"<-最初");	
	}

	@Override
	int[] sort() {
		super.startTime = System.nanoTime();
		
		bubbleSort(finalArray);
		
		super.setElapsedTime(System.nanoTime());
		return finalArray;
	}
	
	public void bubbleSort(int[] arr) {
	    int n = arr.length;
	    boolean swapped;
	    for (int i = 0; i < n - 1; i++) {
	        swapped = false;
	        for (int j = 0; j < n - 1 - i; j++) {
	            if (arr[j] > arr[j + 1]) {
	                int temp = arr[j];
	                arr[j] = arr[j + 1];
	                arr[j + 1] = temp;
	                swapped = true;
	                addMovement();
	        	    System.out.println(toString(arr,j,j+1));
	            }
	        }
	        if (!swapped) break;
	    }
	}
	
}


class InsertionSort extends SortInt{

	public InsertionSort(int[] initialArray) {
		super(initialArray, "InsertionSort");
		System.out.println(toString()+"<-最初");
	}

	@Override
	int[] sort() {
		super.startTime = System.nanoTime();
		
		insertionSort(finalArray);
		
		super.setElapsedTime(System.nanoTime());
		return finalArray;
	}
	
	public void insertionSort(int[] arr) {
	    int n = arr.length;
	    for (int i = 1; i < n; i++) {
	        int key = arr[i];
	        int j = i - 1;
	        while (j >= 0 && arr[j] > key) {
	            arr[j + 1] = arr[j];
	            j--;
	            addMovement();
	    	    System.out.println(toString(arr,j + 1,j));
	        }
	        arr[j + 1] = key;
	    }
	}
}

class SelectionSort extends SortInt{

	public SelectionSort(int[] initialArray) {
		super(initialArray, "SelectionSort");
		System.out.println(toString()+"<-最初");
	}

	@Override
	int[] sort() {
		super.startTime = System.nanoTime();
		
		selectionSort(finalArray);
		
		super.setElapsedTime(System.nanoTime());
		return finalArray;
	}
	
	public void selectionSort(int[] arr) {
	    int n = arr.length;
	    for (int i = 0; i < n - 1; i++) {
	        int minIndex = i;
	        for (int j = i + 1; j < n; j++) {
	            if (arr[j] < arr[minIndex]) {
	                minIndex = j;
	            }
	        }
	        int temp = arr[minIndex];
	        arr[minIndex] = arr[i];
	        arr[i] = temp;
            addMovement();
    	    System.out.println(toString(arr,minIndex,i));
	    }
	}
}

class MergeSort extends SortInt{

	public MergeSort(int[] initialArray) {
		super(initialArray, "MergeSort");
		System.out.println(toString()+"<-最初");
	}

	@Override
	int[] sort() {
		super.startTime = System.nanoTime();
		
		mergeSort(finalArray,0,finalArray.length-1);
		
		super.setElapsedTime(System.nanoTime());
		return finalArray;
	}
	
	public void mergeSort(int[] arr, int left, int right) {
	    if (left < right) {
	        int mid = (left + right) / 2;
	        mergeSort(arr, left, mid);
	        mergeSort(arr, mid + 1, right);
	        merge(arr, left, mid, right);
	    }
	}

	public void merge(int[] arr, int left, int mid, int right) {
	    int n1 = mid - left + 1;
	    int n2 = right - mid;

	    int[] leftArray = new int[n1];
	    int[] rightArray = new int[n2];

	    for (int i = 0; i < n1; i++) {
	        leftArray[i] = arr[left + i];
	        addMovement();
    	    System.out.println(toString(arr,i,left + i));
	    }
	    for (int i = 0; i < n2; i++) {
	        rightArray[i] = arr[mid + 1 + i];
	        addMovement();
    	    System.out.println(toString(arr,i,mid + 1 + i));
	    }

	    int i = 0, j = 0, k = left;
	    while (i < n1 && j < n2) {
	        if (leftArray[i] <= rightArray[j]) {
	            arr[k++] = leftArray[i++];
	            addMovement();
	    	    System.out.println(toString(arr,k++,i++));
	        } else {
	            arr[k++] = rightArray[j++];
	        }
	    }

	    while (i < n1) {
	        arr[k++] = leftArray[i++];
	        addMovement();
    	    System.out.println(toString(arr,k++,i++));
	    }
	    while (j < n2) {
	        arr[k++] = rightArray[j++];
	        addMovement();
    	    System.out.println(toString(arr,k++,j++));
	    }
	}
}

public class LetsSort implements menu{
	static int size = 0, range = 0, sortingTypes = 5;
	static int[] array; 
	static double[] times = new double[sortingTypes];
	
	public static void main(String[] args) {

		LetsSort ls = new LetsSort();
		ls.startMenu();
	}
	

	@Override
	public void mainMenu() {
		
		if(size < 1 || range == 0) {
			System.out.println("配列のパラメーター設定を開始すべきです。");
			System.out.println("\n------------MENU----------------");
			System.out.println("やりたいこと選んで:");
			System.out.println("[ 1 ] - 配列のサイズを設定。      "+(size>0?("[実際の:   "+size+"]"):""));
			System.out.println("[ 2 ] - 配列値の範囲を設定します。"+(range>0?("[実際の:   "+range+"]"):""));
			System.out.println("[-1 ] - 終了する");
		}else {
			System.out.println("\n------------MENU----------------");//現在の配列を表示
			System.out.println("やりたいこと選んで:");
			System.out.printf("[ 1 ] - 配列のサイズを設定。            [実際の:     %5d]\n",size);
			System.out.printf("[ 2 ] - 配列値の範囲を設定します。      [実際の:  0->%5d]\n",range);
			System.out.println("[ 3 ] - 現在の配列を表示");
			System.out.println("[ 4 ] - QuickSort    を使って並べ替える"+(times[0]>0?String.format(" [最後の実行:%.3f sec]",times[0]):""));
			System.out.println("[ 5 ] - BubbleSort   を使って並べ替える"+(times[1]>0?String.format(" [最後の実行:%.3f sec]",times[1]):""));
			System.out.println("[ 6 ] - InsertionSortを使って並べ替える"+(times[2]>0?String.format(" [最後の実行:%.3f sec]",times[2]):""));
			System.out.println("[ 7 ] - SelectionSortを使って並べ替える"+(times[3]>0?String.format(" [最後の実行:%.3f sec]",times[3]):""));
			System.out.println("[ 8 ] - MergeSort    を使って並べ替える"+(times[4]>0?String.format(" [最後の実行:%.3f sec]",times[3]):""));
			System.out.println("[-1 ] - 終了する");
		}
	}

	@Override
	public void functionalities(int option, Scanner scan) {
		switch(option) {
		case 1:
			setArraySize(scan);
			break;
		case 2:
			setArrayRange(scan);
			break;
		case 3:
			showCurrentArray();
			break;
		case 4:
			useQuickSort();
			break;
		case 5:
			useBubbleSort();
			break;
		case 6:
			useInsertionSort();
			break;
		case 7:
			useSelectionSort();
			break;
		case 8:
			useMergeSort();
			break;
		}
		
	}


	private void useQuickSort() {
		QuickSort s = new QuickSort(array);
		s.sort();
		times[0] = s.getElapsedTime();
		System.out.printf("%s: Time %.3fsec - Movements %4d",s.name,s.getElapsedTime(),s.movements);
		
	}
	
	private void useBubbleSort() {
		BubbleSort s = new BubbleSort(array);
		s.sort();
		times[1] = s.getElapsedTime();
		System.out.printf("%s: Time %.3fsec - Movements %4d",s.name,s.getElapsedTime(),s.movements);
		
	}
	
	private void useInsertionSort() {
		InsertionSort s = new InsertionSort(array);
		s.sort();
		times[2] = s.getElapsedTime();
		System.out.printf("%s: Time %.3fsec - Movements %4d",s.name,s.getElapsedTime(),s.movements);
		
	}
	
	private void useSelectionSort() {
		SelectionSort s = new SelectionSort(array);
		s.sort();
		times[3] = s.getElapsedTime();
		System.out.printf("%s: Time %.3fsec - Movements %4d",s.name,s.getElapsedTime(),s.movements);
	}
	

	private void useMergeSort() {
		MergeSort s = new MergeSort(array);
		s.sort();
		times[4] = s.getElapsedTime();
		System.out.printf("%s: Time %.3fsec - Movements %4d",s.name,s.getElapsedTime(),s.movements);
		
	}


	/**
	 * 
	 * @param Scanner コンソールを操作するために使用
	 * @return void
	 */
	public static void setArraySize(Scanner scan) {
		boolean continueInput = true;
		int value = 0;
		do {
			try{
				System.out.println("配列のサイズを入力してください。[0->一万]");
				System.out.println("ただし、プロセッサが小さい場合は、上限が1000または10000になる可能性があることに留意してください。");
				value = scan.nextInt();
				if(value > 0 && value <= 10000) {
					continueInput = false;
					size = value;
				}
			}
			catch (InputMismatchException ex) {
				System.out.println("配列の範囲は1から10000の間にしてください。");
				scan.nextLine();
			}
		}while (continueInput);
		
		if(size > 0 && range > 0) {
			array = new int[size];
			times = new double[sortingTypes];
			for(int i = 0; i < array.length; i++) {
				array[i] = (int)(Math.random()*range);
			}
		}
	       
	}
	
	/**
	 * 
	 * @param Scanner コンソールを操作するために使用
	 * @return void
	 */
	public static void setArrayRange(Scanner scan) {
		boolean continueInput = true;
		int value = 0;
		do {
			try{
				System.out.println("配列値の範囲を入力してください。");
				value = scan.nextInt();
				if(value > 0 ) {
					continueInput = false;
					range = value;
				}
			}
			catch (InputMismatchException ex) {
				System.out.println("入力は0より大きい値でなければなりません。");
				scan.nextLine();
			}
		}while (continueInput);
		
		if(size > 0 && range > 0) {
			array = new int[size];
			times = new double[sortingTypes];
			for(int i = 0; i < array.length; i++) {
				array[i] = (int)(Math.random()*range);
			}
		}
	}
	
	private void showCurrentArray() {
		System.out.println(SortInt.toString(array));
	}
}

