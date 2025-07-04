package utils;

import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * 
 * このコンソールアプリケーションは、いくつかのソートアルゴリズムの配列に対する性能を把握するために作成されました。
 * 
 * <p>利便性を考慮し、すべてのクラスは同じファイル内に追加されましたが、同一のパッケージに属する別々のファイルに容易に分割することも可能です。</p>
 * <p>ソートアルゴリズムは学術目的で、以下のウェブサイトから引用されています：</p>
 * <p>https://medium.com/@AlexanderObregon/introduction-to-sorting-algorithms-in-java-a-beginners-guide-db522047effb</p>
 * <p> 2025年6月19日に完成</p>
 * @author Motta Jaime  (モッタ ハイメ)
 */



/**
 * このJavadocには、メニューインターフェースクラスの説明が記載されています。
 * アプリケーションが提供する機能メニューとユーザーがやり取りするための基本的なメソッドが含まれています。
 * <p>
 * 目的は、コンソールアプリケーションにおけるユーザーメニューを作成するために必要なメソッドの基本的な標準を確立することです。
 * </p>
 */
interface menu {
	
	/**
	 * ユーザーとのやり取りを開始し、ユーザーが終了を選択するまでアプリケーションをアクティブな状態に保ちます。
	 */
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


/**
 * このJavadocには、抽象クラス「SortInt」の説明が記載されています。
 * <p>
 * これは抽象クラスであり、さまざまな種類のソートアルゴリズムを用いてソートされる配列で使用されるプロパティおよびメソッドを含んでおり、
 * 具象サブクラスによって実装されるべきメソッドも含まれています。
 * </p>
 */
abstract class SortInt{
	
    /**
     * T元の配列構成が変更されることなく保持されています。
     */
	private int[] initialArray;
	/**
	 * これは、ソート処理中に操作される元の配列のコピーです。
	 */
	protected int[] finalArray;
	/**
	 * これは、ソートアルゴリズムの実行中に配列に対して行われた変更の回数をカウントするためのカウンターです。
	 */
	public int movements = 0;
	/**
	 * アルゴリズムがソート処理を開始および終了する時刻を示す変数が含まれています。
	 */
	protected long startTime, elapsedTime;
	/**
	 * ソートを実行するために使用されたアルゴリズムの名称が含まれています。
	 */
	public final String name;
	
    /**
     * クラスのコンストラクターであり、
     * 初期配列、最終配列、およびアルゴリズム名を初期化します。
     *
     * @param int[] initialArray: 注文が行われる手配
     * @param String name: 整列アルゴリズムの名称
     */
	public SortInt(int[] initialArray, String name) {
		this.initialArray = copyArray(initialArray);
		this.finalArray = copyArray(initialArray);
		this.name = name;
	}
	
    /**
     * 受け取った配列のディープコピーを作成し、新しい配列を返すメソッド
     *
     * @param int[] a: コピー対象の手配
     * @return int[] 再配置
     */
	private int[] copyArray(int[] a) {
        int[] destinationInts = new int[a.length];
        System.arraycopy(a, 0, destinationInts, 0, a.length);
        return destinationInts;
	}
    /**
     * ナノ秒単位の時刻を受け取り、処理開始からの経過時間を計算し、結果を秒単位で返す
     *
     * @param long stopNanoTime: ソートが完了するまでにかかった時間（ナノ秒単位）.
     * @return double ソート開始からの経過時間の計算 「秒」
     */
	public double setElapsedTime(long stopNanoTime) {
		this.elapsedTime = System.nanoTime() - this.startTime;
		return (double)elapsedTime / 1_000_000_000.0;
	}
    /**
     * ナノ秒単位の時刻を受け取り、処理開始からの経過時間を計算し、結果を秒単位で返す
     *
     * @param long stopNanoTime: ソートが完了するまでにかかった時間（ナノ秒単位）.
     * @return double ソート開始からの経過時間の計算 「秒」
     */
	public String getElapsedTime() {
		double val = (double)elapsedTime / 1_000_000_000.0;
		if(val < 60) {
			return String.format("%.3fsec", val);
		} else {
			return String.format("%.3fmin", val/60);
		}
	}
    /**
     * 移動回数カウンターを増加させる
     */
	public void addMovement() {
		this.movements++;
	}
    /**
     * 整数型の配列を受け取り、その値を1行に15個ずつ並べた形式の文字列を返します。
     *
     * @param int[] array: 文字列へ変換対象の整数配列）.
     * @return String 配列の値を文字列形式に整えたもの
     */
	static public String toString(int[] array) {
		String answer = "|";
		
		for(int i = 0; i< array.length; i++)
		{
			answer += String.format("%4d ",array[i]);
			answer += (i+1)%15==0 ? "\n" : "";
			answer += array.length-1 != i ? "|":"";
		}
		return answer;
	}
    /**
     * 整数型の配列を受け取り、その値を1行に15個ずつ並べた形式の文字列を返します。
     *
     * @param int[] array: 文字列へ変換対象の整数配列）.
     * @param int a: 値が交換されている配列内の位置
     * @param int b: 値が交換されている配列内の位置
     * @return String 配列の値を文字列形式に整えたもの
     */
	static public String toString(int[] array, int a, int b) {
		String answer = "";
		for(int i = 0; i < array.length; i++)
		{
			if(i == a){
				answer += String.format("|>%4d ",array[i]);
			}else if( i == b){
				answer += String.format("|<%4d ",array[i]);
			}else {
				answer += String.format("| %4d ",array[i]);
			}
			answer += (i+1)%15==0 ? "\n" : "";
		}
		answer +=  "\n";
		return answer;
	}
    /**
	 * インスタンスに保持されている初期配列を取得し、
	 * 配列の値を1行あたり15個ずつ区切って整形した文字列を返します。
     * @return String: 1行15個の値で構成された配列の文字列表現
     */
	public String toStringInitial() {
		String answer = "|";
		for(int i = 0; i< initialArray.length; i++)
		{
			answer += String.format("%4d ",initialArray[i]);
			answer += i%15==0 ? "\n" : "";
			answer += initialArray.length-1 != i ? "|":"";
		}
		
		return answer;
	}	
	/**
	 * ソート結果の配列を受け取り、
	 * 配列の値を1行あたり15個ずつ区切って整形した文字列を返します。
	 *
	 * @return String: 1行15個の値で構成された配列の文字列表現
	 */
	public String toStringFinal() {
		String answer = "|";
		for(int i = 0; i< finalArray.length; i++)
		{
			answer += String.format("%4d ",finalArray[i]);
			answer += i%15==0 ? "\n" : "";
			answer += finalArray.length-1 != i ? "|":"";
		}
		
		return answer;
	}
	
    /**
     * 本メソッドは、初期配列を整列する抽象メソッドです。
     * このメソッドには、サブクラスによる具体的な実装が求められます。
     * ソートの具体的な処理内容は、サブクラスごとに異なります
     *
     * @return int[] ソート済みの配列を返します（この配列はクラスのインスタンス化時に定義されました）
     */
	abstract int[]  sort();
}


/**
 * 抽象クラス {@code SortInt} のサブクラスです。
 * クイックソート（QuickSort）アルゴリズムを実装しています。
 */
class QuickSort extends SortInt{

	/**
	 * QuickSort クラスのインスタンスを生成します。
	 * 指定された初期配列を使用して、親クラス {@code SortInt} を初期化し、
	 * ソートアルゴリズム名として「QuickSort」を設定します。
	 *
	 * @param initialArray ソート対象となる初期配列
	 */
	public QuickSort(int[] initialArray) {
		super(initialArray, "QuickSort");
		System.out.println(toString()+"<-最初");
	}

	/**
	 * Sorts the array using the QuickSort algorithm.
	 * Records the start time and calculates the elapsed time after sorting is complete.
	 *
	 * @return The sorted array
	 */
	@Override
	int[] sort() {
		super.startTime = System.nanoTime();
		
		quickSort(finalArray, 0,finalArray.length-1);
		
		super.setElapsedTime(System.nanoTime());
		return finalArray;
	}
	
	/**
	 * QuickSort アルゴリズムに基づいて、指定された範囲の配列を再帰的にソートします。
	 *
	 * @param arr ソート対象の配列
	 * @param low ソート範囲の開始インデックス
	 * @param high ソート範囲の終了インデックス
	 */
	public void quickSort(int[] arr, int low, int high) {
	    if (low < high) {
	        int pivotIndex = partition(arr, low, high);
	        quickSort(arr, low, pivotIndex - 1);
	        quickSort(arr, pivotIndex + 1, high);
	    }
	}
	/**
	 * QuickSort アルゴリズムにおけるパーティション操作を行います。
	 * 指定された配列範囲内でピボットより小さい要素を前方に移動させ、
	 * ピボットの正しい位置を決定してそのインデックスを返します。
	 *
	 * @param arr ソート対象の配列
	 * @param low パーティションの開始インデックス
	 * @param high パーティションの終了インデックス（ピボット要素の位置）
	 * @return ピボット要素を配置した位置のインデックス
	 */
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

/**
 * {@code SortInt} クラスを継承した BubbleSort クラスです。
 * バブルソートアルゴリズムを用いて整数配列をソートします。
 * ソート処理の過程で、交換回数をカウントし、視覚的な出力を行います。
 */
class BubbleSort extends SortInt{
	/**
     * BubbleSort クラスのインスタンスを生成します。
     * 指定された初期配列を使用して、親クラス {@code SortInt} を初期化し、
     * ソートアルゴリズム名として「BubbleSort」を設定します。
     *
     * @param initialArray ソート対象となる初期配列
     */
	public BubbleSort(int[] initialArray) {
		super(initialArray, "BubbleSort");
		System.out.println(toString()+"<-最初");	
	}
    /**
     * バブルソートアルゴリズムを用いて配列をソートします。
     * ソート開始時刻を記録し、完了後に経過時間を計測します。
     *
     * @return ソートされた配列
     */
	@Override
	int[] sort() {
		super.startTime = System.nanoTime();
		
		bubbleSort(finalArray);
		
		super.setElapsedTime(System.nanoTime());
		return finalArray;
	}
    /**
     * バブルソートアルゴリズムを実行します。
     * 隣接する要素を比較して、必要に応じて交換を行いながら、
     * 配列を昇順に整列させます。
     * 各交換操作ごとに {@code addMovement()} を呼び出してカウントを行い、
     * 状態をコンソールに出力します。
     *
     * @param arr ソート対象の配列
     */
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

/**
 * {@code SortInt} クラスを継承した InsertionSort クラスです。
 * 挿入ソートアルゴリズムを用いて整数配列をソートします。
 * ソート処理の過程で、移動回数をカウントし、状態をコンソールに出力します。
 */
class InsertionSort extends SortInt{
    /**
     * InsertionSort クラスのインスタンスを生成します。
     * 指定された初期配列を使用して、親クラス {@code SortInt} を初期化し、
     * ソートアルゴリズム名として「InsertionSort」を設定します。
     *
     * @param initialArray ソート対象となる初期配列
     */
	public InsertionSort(int[] initialArray) {
		super(initialArray, "InsertionSort");
		System.out.println(toString()+"<-最初");
	}
    /**
     * 挿入ソートアルゴリズムを用いて配列をソートします。
     * ソート開始時刻を記録し、完了後に経過時間を計測します。
     *
     * @return ソートされた配列
     */
	@Override
	int[] sort() {
		super.startTime = System.nanoTime();
		
		insertionSort(finalArray);
		
		super.setElapsedTime(System.nanoTime());
		return finalArray;
	}
    /**
     * 挿入ソートアルゴリズムを実行します。
     * 未ソート部分の要素を1つずつ適切な位置に挿入しながら、
     * 配列を昇順に整列させます。
     * 各要素の移動ごとに {@code addMovement()} を呼び出してカウントし、
     * 状態をコンソールに出力します。
     *
     * @param arr ソート対象の配列
     */
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

/**
 * {@code SortInt} クラスを継承した SelectionSort クラスです。
 * 選択ソートアルゴリズムを用いて整数配列をソートします。
 * ソート処理の過程で、交換回数をカウントし、配列の状態をコンソールに出力します。
 */
class SelectionSort extends SortInt{
    /**
     * SelectionSort クラスのインスタンスを生成します。
     * 指定された初期配列を使用して、親クラス {@code SortInt} を初期化し、
     * ソートアルゴリズム名として「SelectionSort」を設定します。
     *
     * @param initialArray ソート対象となる初期配列
     */
	public SelectionSort(int[] initialArray) {
		super(initialArray, "SelectionSort");
		System.out.println(toString()+"<-最初");
	}
    /**
     * 選択ソートアルゴリズムを用いて配列をソートします。
     * ソート開始時刻を記録し、完了後に経過時間を計測します。
     *
     * @return ソートされた配列
     */
	@Override
	int[] sort() {
		super.startTime = System.nanoTime();
		
		selectionSort(finalArray);
		
		super.setElapsedTime(System.nanoTime());
		return finalArray;
	}
    /**
     * 選択ソートアルゴリズムを実行します。
     * 未ソート部分から最小の要素を選び、先頭の要素と交換しながら
     * 配列を昇順に整列させます。
     * 各交換操作ごとに {@code addMovement()} を呼び出してカウントし、
     * 配列の状態をコンソールに出力します。
     *
     * @param arr ソート対象の配列
     */
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

/**
 * {@code SortInt} クラスを継承した MergeSort クラスです。
 * マージソートアルゴリズムを用いて整数配列をソートします。
 * ソート処理の過程で要素の移動回数をカウントし、状態をコンソールに出力します。
 */
class MergeSort extends SortInt{
    /**
     * MergeSort クラスのインスタンスを生成します。
     * 指定された初期配列を使用して親クラス {@code SortInt} を初期化し、
     * ソートアルゴリズム名として「MergeSort」を設定します。
     *
     * @param initialArray ソート対象となる初期配列
     */
	public MergeSort(int[] initialArray) {
		super(initialArray, "MergeSort");
		System.out.println(toString()+"<-最初");
	}

    /**
     * マージソートアルゴリズムを用いて配列をソートします。
     * ソート開始時刻を記録し、完了後に経過時間を計測します。
     *
     * @return ソートされた配列
     */
	@Override
	int[] sort() {
		super.startTime = System.nanoTime();
		
		mergeSort(finalArray,0,finalArray.length-1);
		
		super.setElapsedTime(System.nanoTime());
		return finalArray;
	}
	
    /**
     * 配列の指定範囲を再帰的に分割し、マージソートを実行します。
     *
     * @param arr ソート対象の配列
     * @param left 範囲の開始インデックス
     * @param right 範囲の終了インデックス
     */
	public void mergeSort(int[] arr, int left, int right) {
	    if (left < right) {
	        int mid = (left + right) / 2;
	        mergeSort(arr, left, mid);
	        mergeSort(arr, mid + 1, right);
	        merge(arr, left, mid, right);
	    }
	}

    /**
     * 2つのソート済み部分配列をマージし、1つのソート済み配列に統合します。
     * マージの過程で要素の移動をカウントし、配列の状態をコンソールに出力します。
     *
     * @param arr ソート対象の配列
     * @param left 部分配列の開始インデックス
     * @param mid 部分配列の中間インデックス
     * @param right 部分配列の終了インデックス
     */
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
	            arr[k] = leftArray[i];
	            addMovement();
	    	    System.out.println(toString(arr,k,i));
	    	    k++;i++;
	        } else {
	            arr[k++] = rightArray[j++];
	        }
	    }

	    while (i < n1) {
	        arr[k] = leftArray[i];
	        addMovement();
    	    System.out.println(toString(arr,k,i));
    	    k++;
    	    i++;
	    }
	    while (j < n2) {
	        arr[k] = rightArray[j];
	        addMovement();
    	    System.out.println(toString(arr,k,j));
    	    k++;
    	    j++;
	    }
	}
}

/**
 * {@code LetsSort} クラスは、さまざまな配列ソートアルゴリズムを実行するための
 * コンソールベースのアプリケーションを提供します。
 * ユーザーは配列のサイズと値の範囲を設定し、複数のソートアルゴリズムを試すことができます。
 * 各ソートの実行時間と移動回数も表示します。
 * <p>
 * このクラスは{@code menu}インターフェースを実装しており、アプリケーションのメインメニューと
 * 機能の管理を担当します。
 * </p>

 * @author Motta Jaime  (モッタ ハイメ)
 * @version 1.0
 * @since <p> 2025年6月19日に完成</p>
 * @see menu
 * @see QuickSort
 * @see BubbleSort
 * @see InsertionSort
 * @see SelectionSort
 * @see MergeSort
 */
public class LetsSort implements menu{
	/** 配列の現在のサイズ */
	static int size = 0;
	/** 配列要素の最大範囲（0からこの値まで） */
	static int range = 0;
	 /** 利用可能なソートアルゴリズムの数 */
	static int sortingTypes = 5;
	/** ソート操作の対象となる配列 */
	static int[] array; 
	/** 各ソートアルゴリズムの最終実行時間を格納する配列 */
	static String[] times = new String[sortingTypes];

    /**
     * プログラムのエントリポイントです。
     * {@code LetsSort}のインスタンスを生成し、アプリケーションのメニューを開始します。
     *
     * @param args コマンドライン引数（このアプリケーションでは使用しません）
     */
	public static void main(String[] args) {

		LetsSort ls = new LetsSort();
		ls.startMenu();
	}
	
    /**
     * メインメニューを表示します。
     * 配列のサイズと値の範囲が設定されているかによって、異なるメニューオプションを表示します。
     */
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
			System.out.printf("[ 2 ] - 配列値の範囲を設定します。       [実際の:  0->%5d]\n",range);
			System.out.println("[ 3 ] - 現在の配列を表示");
			System.out.println("[ 4 ] - QuickSort    を使って並べ替える"+(times[0]!=null?String.format(" [最後の実行:%s]",times[0]):""));
			System.out.println("[ 5 ] - BubbleSort   を使って並べ替える"+(times[1]!=null?String.format(" [最後の実行:%s]",times[1]):""));
			System.out.println("[ 6 ] - InsertionSortを使って並べ替える"+(times[2]!=null?String.format(" [最後の実行:%s]",times[2]):""));
			System.out.println("[ 7 ] - SelectionSortを使って並べ替える"+(times[3]!=null?String.format(" [最後の実行:%s]",times[3]):""));
			System.out.println("[ 8 ] - MergeSort    を使って並べ替える"+(times[4]!=null?String.format(" [最後の実行:%s]",times[4]):""));
			System.out.println("[-1 ] - 終了する");
		}
	}

    /**
     * ユーザーが選択したメニューオプションに応じた機能を実行します。
     *
     * @param option ユーザーが選択したメニュー番号。
     * @param scan   ユーザーからのコンソール入力を読み取るための{@code Scanner}オブジェクト。
     */
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

    /**
     * QuickSortアルゴリズムを使用して現在の配列を並べ替えます。
     * ソート時間と移動回数をコンソールに表示し、最終実行時間を記録します。
     */
	private void useQuickSort() {
		QuickSort s = new QuickSort(array);
		s.sort();
		times[0] = s.getElapsedTime();
		System.out.printf("%s: Time %s - Movements %4d",s.name,s.getElapsedTime(),s.movements);
	}
	
    /**
     * BubbleSortアルゴリズムを使用して現在の配列を並べ替えます。
     * ソート時間と移動回数をコンソールに表示し、最終実行時間を記録します。
     */
	private void useBubbleSort() {
		BubbleSort s = new BubbleSort(array);
		s.sort();
		times[1] = s.getElapsedTime();
		System.out.printf("%s: Time %s - Movements %4d",s.name,s.getElapsedTime(),s.movements);
	}
	
    /**
     * InsertionSortアルゴリズムを使用して現在の配列を並べ替えます。
     * ソート時間と移動回数をコンソールに表示し、最終実行時間を記録します。
     */
	private void useInsertionSort() {
		InsertionSort s = new InsertionSort(array);
		s.sort();
		times[2] = s.getElapsedTime();
		System.out.printf("%s: Time %s - Movements %4d",s.name,s.getElapsedTime(),s.movements);
	}
	
    /**
     * SelectionSortアルゴリズムを使用して現在の配列を並べ替えます。
     * ソート時間と移動回数をコンソールに表示し、最終実行時間を記録します。
     */
	private void useSelectionSort() {
		SelectionSort s = new SelectionSort(array);
		s.sort();
		times[3] = s.getElapsedTime();
		System.out.printf("%s: Time %s - Movements %4d",s.name,s.getElapsedTime(),s.movements);
	}
	
    /**
     * MergeSortアルゴリズムを使用して現在の配列を並べ替えます。
     * ソート時間と移動回数をコンソールに表示し、最終実行時間を記録します。
     */
	private void useMergeSort() {
		MergeSort s = new MergeSort(array);
		s.sort();
		times[4] = s.getElapsedTime();
		System.out.printf("%s: Time %s - Movements %4d",s.name,s.getElapsedTime(),s.movements);
		
	}


    /**
     * ユーザーからの入力に基づいて配列のサイズを設定します。
     * サイズは1から10,000の範囲でなければなりません。
     * 新しいサイズと範囲が有効な場合、新しいランダムな配列が初期化され、過去のソート時間はリセットされます。
     *
     * @param scan ユーザーからのコンソール入力を読み取るための{@code Scanner}オブジェクト。
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
			times = new String[sortingTypes];
			for(int i = 0; i < array.length; i++) {
				array[i] = (int)(Math.random()*range);
			}
		}
	       
	}
	
    /**
     * ユーザーからの入力に基づいて配列要素のランダムな範囲を設定します。
     * 範囲は0より大きい値でなければなりません。
     * 新しいサイズと範囲が有効な場合、新しいランダムな配列が初期化され、過去のソート時間はリセットされます。
     *
     * @param scan ユーザーからのコンソール入力を読み取るための{@code Scanner}オブジェクト。
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
			times = new String[sortingTypes];
			for(int i = 0; i < array.length; i++) {
				array[i] = (int)(Math.random()*range);
			}
		}
	}
	
    /**
     * 現在の配列の内容をコンソールに表示します。
     * 配列が初期化されていない場合は、何も表示されません。
     */
	private void showCurrentArray() {
		System.out.println(SortInt.toString(array));
	}
}

