package utils;


import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Collections;
import java.util.InputMismatchException;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

/**
 * 
 * 数値を10進数から2進数および16進数に変換するコンソールアプリケーション
 * 
 * <p>このクラスは、アプリケーション全体と、異なる単位間で値を変換するために使用されるユーティリティメソッドを処理します。</p>
 * <p> 2025年6月1日に完成</p>
 * @author Motta Jaime  (モッタ ハイメ)
 */

public class NumericBaseConvertions {
	
	static String LOG_FILE_NAME = "numericBaseConvertionsLog.txt";

	public static void main(String[] args) {
	
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
	public static void mainMenu() {
		System.out.println("\n------------MENU----------------");
		System.out.println("やりたいこと選んで:");
		System.out.println("[ 1 ] - 10進数を2進数に変換する");
		System.out.println("[ 2 ] - 2進数を10進数に変換する");
		System.out.println("[ 3 ] - 2進数を16進数に変換する");
		System.out.println("[ 4 ] - 16進数を10進数に変換する");
		System.out.println("[ 5 ] - 10進数を16進数に変換する");
		System.out.println("[ 6 ] - ログを開く");
		System.out.println("[ 7 ] - ログを削除する");
		System.out.println("[-1 ] - 終了する");
	}
	
	/**
	 * メインメニューで選択した機能を実行する
	 * @param int メインメニューからオプションを選択
	 * @param Scanner コンソールを操作するために使用
	 */
	public static void functionalities(int option, Scanner scan) {
		switch(option) {
		case 1:
			decToBin(scan);
			break;
		case 2:
			binToDec(scan);
			break;
		case 3:
			binToHex(scan);
			break;
		case 4:
			hexToDec(scan);
			break;
		case 5:
			decToHex(scan);
			break;
		case 6:
			openLog(scan);
			break;
		case 7:
			cleanLog();
			break;
		}
		
	}

	/**
	 * コンソールから整数である必要がある 10 進数値を読み取り、それをバイナリに変換して、結果をコンソールに出力します。
	 * @param Scanner コンソールを操作するために使用
	 * @return void
	 */
	public static void decToBin(Scanner scan) {
		
		int dividend = -1, initial = -1;
	
		boolean continueInput = true;

		do {
			try{
				System.out.println("10進数を入力してください");
				System.out.println("10進数は0から9999999の範囲でなければなりません。");
				
				initial = dividend = scan.nextInt();
				if(initial >= 0 && initial <= 9999999)
					continueInput = false;
			}
			catch (InputMismatchException ex) {
				System.out.println("この操作には 10 進数の数値を入力する必要があります。小数でもう一度試してください。");				
				scan.nextLine();
			}
		}while (continueInput);
	

        System.out.println("始めましょう...");
		int remainder = 0, quotient = -1;
		LinkedList<String> result = new LinkedList<String>();
		String padding = " ";
		
		if(dividend > -1) {
			while(quotient != 0) {
				quotient = getIntegerFromDouble(dividend / 2);
				remainder = dividend - quotient * 2;
				System.out.println("2)   "+dividend+ padding.repeat(getPadding(dividend,remainder))+remainder);
				result.add(""+remainder);
				dividend = quotient;
			}
		}
		String answer = binaryFormating(result, true);
		writeInLog("10進数 [ "+ initial +" ] --> 2進数 [ "+answer + " ]\n");
		System.out.println(answer);

		pause();
	       
	}
	
	/**
	 * コンソールから 2 進数を読み取り、それを 10 進数に変換して、結果をコンソールに出力します。
	 * @param Scanner コンソールを操作するために使用
	 * @return void
	 */
	public static void binToDec(Scanner scan) {
		
		String binaryNumber = "";
		boolean continueInput = true;
		scan.nextLine();

		do {
			System.out.println("2進数を入力してください");
			binaryNumber = scan.nextLine();
			if(isBinary(binaryNumber)) {
				continueInput = false;
			}
			else {
				System.out.println("この操作には 2進数の数値を入力する必要があります。小数でもう一度試してください。");
				scan.nextLine();
			}
			
		}while (continueInput);
		
		LinkedList<String> binaryNumberList = new LinkedList<String>();
		System.out.println("始めましょう...");

		for (char c : binaryNumber.toCharArray()) {
			binaryNumberList.add(""+c);
		}
		
		String initial = binaryFormating(binaryNumberList, false);
		System.out.println(initial);
		int temp = 0, answer = 0;
		String answerDetails = "";
		for(int i=0; i<binaryNumberList.size(); i++) {
			
			if(binaryNumberList.get(i).contains("1")) {
				temp = binaryNumberList.size()-1-i;
				answerDetails += answerDetails == "" ? "2^" + temp : " + 2^" + temp;
				answer += java.lang.Math.pow(2,temp);
			}
		}
		writeInLog("2進数  [ "+ initial +" ] --> 10進数 [ "+answer + " ]\n");
		System.out.println(answerDetails+"  = "+answer);
		pause();
	}
	
	/**
	 * コンソールから 2 進数を読み取り、それを 16 進数に変換して、結果をコンソールに出力します。
	 * @param Scanner コンソールを操作するために使用
	 * @return void
	 */
	public static void binToHex(Scanner scan) {
		
		String binaryNumber = "";
		
		boolean continueInput = true;
		scan.nextLine();
		do {
			System.out.println("2進数を入力してください");
			binaryNumber = scan.nextLine();
			if(isBinary(binaryNumber)) {
				continueInput = false;
			}
			else {
				System.out.println("この操作には 2進数の数値を入力する必要があります。小数でもう一度試してください。");
				scan.nextLine();
			}
			
		}while (continueInput);
		
		LinkedList<String> binaryNumberList = new LinkedList<String>();
		System.out.println("始めましょう...");

		for (char c : binaryNumber.toCharArray()) {
			binaryNumberList.add(""+c);
		}
		String initial = binaryFormating(binaryNumberList, false);
		System.out.println(initial);
		int temp = 0, answerLocal = 0;
		String answerDetails = "";
		List<String> localList;
		for(int i=0; i<binaryNumberList.size(); i+=4) {
			localList = binaryNumberList.subList(i, i+4);
			for(int i2 = 0; i2 < localList.size(); i2++) {
				if(localList.get(i2).contains("1")) {
					temp = 3-i2;
					answerLocal += java.lang.Math.pow(2,temp);
				}
			}
			answerDetails += answerLocal >= 10 ? getHexValue(answerLocal):answerLocal;
			answerLocal = 0;
		}
		writeInLog("2進数  [ "+ initial +" ] --> 16進数 [ "+answerDetails + " ]\n");
		System.out.println(answerDetails);
		pause();
	}
	
	/**
	 * コンソールから 16 進数を読み取り、それを 10 進数に変換して、結果をコンソールに出力します。
	 * @param Scanner コンソールを操作するために使用
	 * @return void
	 */
	public static void hexToDec(Scanner scan) {
		
		String hexaNumber = "";
		
		boolean continueInput = true;
		scan.nextLine();

		do {
			System.out.println("16進数を入力してください");
			hexaNumber = scan.nextLine();
			if(isHexadecimal(hexaNumber)) {
				continueInput = false;
			}
			else {
				System.out.println("この操作には 16進数の数値を入力する必要があります。小数でもう一度試してください。");
				scan.nextLine();
			}
			
		}while (continueInput);
		
		LinkedList<String> hexaNumberList = new LinkedList<String>();
		System.out.println("始めましょう...");

		for (char c : hexaNumber.toCharArray()) {
			hexaNumberList.add(""+c);
		}

		int temp = 0, tempDec = 0, answer = 0;
		String answerDetails = "";
		for(int i=0; i<hexaNumberList.size(); i++) {
			temp = hexaNumberList.size()-i-1;
			tempDec = getDecimal(hexaNumberList.get(i));
			answerDetails += answerDetails == "" ? tempDec+"*16^" + temp : " + "+tempDec+"*16^" + temp;
			answer += java.lang.Math.pow(16,temp)*tempDec;
		}
		writeInLog("16進数 [ "+ hexaNumber +" ] --> 10進数 [ "+answer + " ]\n");
		System.out.println(answerDetails+"  = "+answer);
		
		pause();
	}
	
	/**
	 * コンソールから 10 進数を読み取り、それを 16 進数に変換して、結果をコンソールに出力します。
	 * @param Scanner コンソールを操作するために使用
	 * @return void
	 */
	public static void decToHex(Scanner scan) {
		
		int dividend = -1, initial = -1;
	
		boolean continueInput = true;
		scan.nextLine();

		do {
			try{
				System.out.println("10進数を入力してください");
				initial = dividend = scan.nextInt();
				continueInput = false;
			}
			catch (InputMismatchException ex) {
				System.out.println("この操作には 10 進数の数値を入力する必要があります。小数でもう一度試してください。");
				scan.nextLine();
			}
		}while (continueInput);
	       
        System.out.println("始めましょう...");
		int remainder = 0, quotient = -1;
		LinkedList<String> result = new LinkedList<String>();
		String padding = " ";
		
		if(dividend > -1) {
			while(quotient != 0) {
				quotient = getIntegerFromDouble(dividend / 16);
				remainder = dividend - quotient * 16;
				System.out.println("16)   "+dividend+ padding.repeat(getPadding(dividend,remainder))+remainder);
				result.add("" + (remainder < 10 ? remainder : getHexValue(remainder) ));
				dividend = quotient;
			}
		}
		
		String answer = hexadecimalFormating(result);
		writeInLog("10進数 [ "+ initial +" ] --> 16進数 [ "+answer + " ]\n");
		System.out.println(answer);

		pause();
	}
	
	/**
	 * ログファイルを開き、最後のログ削除以降に行われた操作をコンソールに出力します。
	 * @return void
	 */
	public static void openLog(Scanner scan) {
		try {
				String log = Files.readString(Paths.get(LOG_FILE_NAME));
				
				System.out.print("\n".repeat(2)+"---------  ログ ---------\n"+log);
				pause();
			
        } catch (IOException e) {
            e.printStackTrace();
        }
	}
	
	/**
	 * レジストリの新しいエントリを受け取り、ファイルが存在する場合はそれを追加し、そうでない場合は新しいファイルを作成します。
	 * @return void
	 */
	public static void writeInLog(String newEntry) {
		try {
			if((new File(LOG_FILE_NAME)).exists())
			{
				Files.write(Paths.get(LOG_FILE_NAME), newEntry.getBytes(), StandardOpenOption.APPEND);
			} else {
				Files.write(Paths.get(LOG_FILE_NAME), newEntry.getBytes(), StandardOpenOption.CREATE);
			}
        } catch (IOException e) {
            e.printStackTrace();
        }
	}
	
	/**
	 * 履歴操作ログを空にする。
	 * @return void
	 */
	public static void cleanLog() {
		try {
			if((new File(LOG_FILE_NAME)).exists())
			{
				Files.write(Paths.get(LOG_FILE_NAME), "".getBytes());
			} 
        } catch (IOException e) {
            e.printStackTrace();
        }
	}
	
	/**
	 * 操作の結果を出力した後、コンソールに情報を保持するために使用される関数。
	 * @return void
	 */
    public static void pause() {
 //       System.out.println("Press Enter to continue...");
        System.out.println("続行するには押してください。。。");
        try {
            System.in.read();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
	
	/**
	 * double入力を受け取り、その整数部分を返す
	 * @param double input
	 * @return integer 倍精度入力の整数部分
	 */
    public static int getIntegerFromDouble(double input) {
	    String doubleAsString = String.valueOf(input);
	    int indexOfDecimal = doubleAsString.indexOf(".");
	    return Integer.valueOf(doubleAsString.substring(0, indexOfDecimal));
    }
    
	/**
	 * 結果を印刷するときに使用する空きスペースの必要数を計算します。
	 * @param int
	 * @param int
	 * @return int, 15 - a - b の操作を行った後、15 文字を埋めるのに必要なスペースの数
	 */
    public static int getPadding(int a, int b) {
    	int lenghtA = Integer.toString(a).length();
    	int lenghtB = Integer.toString(b).length();
    	return 15 - (lenghtA-lenghtB);
    }
    
	/**
	 * @param LinkedList<String> フォーマットする2進数を含むリスト。数値は昇順または降順で格納できます。
	 * @param boolean リストが昇順の場合はtrue、そうでない場合はfalse
	 * @return String バイナリ値
	 */
    public static String binaryFormating(LinkedList<String> inputValue, boolean reverse) {
    	int quotient = getIntegerFromDouble(inputValue.size() / 4);
    	int remainder = inputValue.size() - quotient * 4;
    	String answer = "";
    	    	
    	if(reverse) {
        	for(int i= remainder; i < 4; i++) {
        		inputValue.add("0");
        	}
    		Collections.reverse(inputValue);
    	}
    	else {
        	for(int i= remainder; i < 4; i++) {
        		inputValue.addFirst("0");
        	}
    	}
    	
    	
    	for(int i = 0; i < inputValue.size(); i++) {
    		if (i > 0 && i%4 == 0) {
    			answer += " " + inputValue.get(i);
    		} else {
    			answer += inputValue.get(i);
    		}
    	}
    	return answer;
    }

	/**
	 * @param LinkedList<String> フォーマットする 16 進数を含むリスト。数字は昇順です。
	 * @return String 16進数値
	 */
    public static String hexadecimalFormating(LinkedList<String> inputValue) {
    	String answer = "";
    	
    	Collections.reverse(inputValue);
    	
    	for(String o: inputValue) {
    		answer += o;
    	}
    	
    	return answer;
    }
    
	/**
	 * 16進文字を受け取り、その値を10進形式で返します。
	 * @param String 16進文字
	 * @return int 小数値
	 */
    public static int getDecimal(String option) {
        int answer = 0;
        
        switch(option.toUpperCase()) { 
        case "0": answer = 0; break;
        case "1": answer = 1; break;
        case "2": answer = 2; break;
        case "3": answer = 3; break;
        case "4": answer = 4; break;
        case "5": answer = 5; break;
        case "6": answer = 6; break;
        case "7": answer = 7; break;
        case "8": answer = 8; break;
        case "9": answer = 9; break;
        case "A": answer = 10; break;
        case "B": answer = 11; break;
        case "C": answer = 12; break;
        case "D": answer = 13; break;
        case "E": answer = 14; break;
        case "F": answer = 15; break;
        }
        
        return answer;
    }

	/**
	 * 10進数値を受け取り、その16進数値を返します
	 * @param int 10進文字
	 * @return String 16進数
	 */
    public static String getHexValue(int value) {
    	String answer = "";
    	
    	switch(value) {
    	case 10:
			answer = "A";
			break;
		case 11:
			answer = "B";
			break;
		case 12:
			answer = "C";
			break;
		case 13:
			answer = "D";
			break;
		case 14:
			answer = "E";
			break;
		case 15:
			answer = "F";
			break;
		}
    	
    	return answer;
    }

	/**
	 * 文字列を受け取り、それが2進数であるかどうかを確認します。
	 * @param String 2進文字
	 * @return boolean 受信した数値が2進数の場合はtrue、そうでない場合はfalse
	 */
    public static boolean isBinary(String str) {
        return str.matches("^[01]+$");
    }
    
	/**
	 * 文字列を受け取り、それが16進数であるかどうかを確認します。
	 * @param String 16進文字
	 * @return boolean 受信した数値が16進数の場合はtrue、そうでない場合はfalse
	 */
    public static boolean isHexadecimal(String str) {
        return str.matches("^[0123456789ABCDEFabcdef]+$");
    }

}
