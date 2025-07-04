package utilities;

// MOTTA

/**
 * 教室内の生徒の座席配置を管理するクラスです。
 * 生徒の初期配置と、新しい座席順序での生徒の再配置をシミュレートします。
 */
public class ClassRoom {

	/**
	 * プログラムのエントリポイントです。
	 * 生徒の配列を初期化し、初期の机の配置を設定し、
	 * その後、新しいルールに基づいて生徒を再配置します。
	 *
	 * @param args コマンドライン引数（使用されません）
	 */
	public static void main(String[] args) {

		// 生徒の初期リスト
		Student[] students = {
				new Student("みずかみ","水上  ",1),
				new Student("おおつか","大塚  ", 1),
				
				new Student("まつかわ","松川  ", 2),
				new Student("かわた","川田  ", 2),
				
				new Student("しおた","塩田  ", 3),
				new Student("かわい","河井  ", 3),
				
				new Student("まつい","松井  ", 4),
				new Student("にいおか","新岡  ", 4),
				
				new Student("やまだ","山田  ", 5),
				new Student("やまかど","山角  ", 5),
				
				new Student("みやざわ","宮澤  ", 6),
				new Student("かわぐち","川口  ", 6),
				
				new Student("もった","MOTTA ", 7),
				new Student("さとだて","里舘  ", 7),
				
				new Student("あつた","熱田  ", 8),
				new Student("おかだ","岡田  ", 8),
				
				new Student("ほりぐち","堀口  ", 9),
				new Student("ひぐち","樋口  ", 9),
		};
		int deskNumber = 9;

		// 初期配置の机の配列
		Desk[] room = new Desk[deskNumber];

		// 生徒を机に配置
		for(int i= 0, j=0; i < deskNumber; i++, j+=2) {
			room[i] = new Desk(i+1,students[j],students[j+1]);
			
		}		

		// 初期配置の表示
		Desk.printRoom(room);

		// 新しい座席順序のための机の配列を初期化
		Desk[] newRoomOrder = new Desk[deskNumber];
		for(int i= 0; i < deskNumber; i++) {
			newRoomOrder[i] = new Desk(i+1);
		}

		// 新しい座席順序で生徒を配置（両端から交互に）
		boolean turn = true;
		for(int i= 0, j=students.length-1; i <= j;) {
			if(turn) {
				// 先頭の生徒を新しい机に移動
				students[i].getDesk(newRoomOrder, room[students[i].deskId-1]);
				i++;
				turn = false;
			}
			else {
				// 末尾の生徒を新しい机に移動
				students[j].getDesk(newRoomOrder, room[students[j].deskId-1]);
				j--;
				turn = true;
			}
		}

	}

}

/**
 * 机を表すクラスです。各机には2つの椅子があり、生徒が座ることができます。
 */
class Desk {
	/** 机のID */
	int id;
	/** 机に座っている生徒の配列 */
	Student[] chairs;

	/**
	 * 指定されたIDと2人の生徒で机を初期化します。
	 * @param id 机のID
	 * @param a 机に座る最初の生徒
	 * @param b 机に座る2番目の生徒
	 */
	public Desk(int id, Student a, Student b) {
		this.id = id;
		this.chairs = new Student[2];
		this.chairs[0]=a;
		this.chairs[1]=b;
	}

	/**
	 * 指定されたIDで机を初期化します。椅子は最初は空です。
	 * @param id 机のID
	 */
	public Desk(int id) {
		this.id = id;
		this.chairs = new Student[2];

	}

	/**
	 * 机に空いている椅子があるかどうかをチェックします。
	 * @return 空いている椅子があればtrue、そうでなければfalse
	 */
	public boolean hasFreeChair() {
		if(chairs[0] == null || chairs[1] == null) {
			return true;
		}
		return false;

	}

	/**
	 * 生徒を空いている椅子に座らせます。
	 * @param a 座らせる生徒
	 */
	public void seatStudent(Student a) {
		if(chairs[0] == null) {
			chairs[0] = a;
		} else {
			chairs[1] = a;
		}
	}

	/**
	 * 机の文字列表現を返します。机のIDと座っている生徒の名前が含まれます。
	 * @return 机の文字列表現
	 */
	@Override
	public String toString() {
		if(chairs[0] != null && chairs[1] != null)
			return String.format("[%d] %s  %s  ",this.id,chairs[0].name, chairs[1].name);
		else if(chairs[0] == null && chairs[1] == null)
			return String.format("[%d] %s  %s  ",this.id,"-o.o  ", "-o.o  "); // 両方空の場合
		else if(chairs[0] == null)
			return String.format("[%d] %s  %s  ",this.id,"-o.o  ", chairs[1].name); // 最初の椅子が空の場合
		else
			return String.format("[%d] %s  %s  ",this.id,chairs[0].name, "-o.o  "); // 2番目の椅子が空の場合
	}

	/**
	 * 指定された生徒の隣に座っている生徒を返します。
	 * @param st 基準となる生徒
	 * @return 隣に座っている生徒。もし誰も隣にいない場合はnull。
	 */
	public Student getNeighbor(Student st) {
		if(chairs[0] == st) {
			return chairs[1];
		}
		return chairs[0];
	}

	/**
	 * 机が完全に空いているかどうかをチェックします。
	 * @return 両方の椅子が空であればtrue、そうでなければfalse
	 */
	public boolean isEmply() {
		if(chairs[0] == null && chairs[1] == null)
			return true;
		return false;
	}

	/**
	 * 教室の現在の座席配置をコンソールに出力します。
	 * @param room 机の配列
	 */
	static public void printRoom(Desk[] room) {

		for(int i= 0; i< room.length; i++) {
			if(i!=0 && i%2 == 0) System.out.println(); // 2つの机ごとに改行
			System.out.printf("  %s  ",room[i]);
		}
		System.out.println(); // 最後の行の後に改行を追加
	}

}

/**
 * 生徒を表すクラスです。生徒の名前と現在の机のIDを保持します。
 */
class Student {
	/** 生徒の名前（ひらがな） */
	public String nameHiragana;
	/** 生徒の名前 */
	public String name;
	/** 生徒が現在座っている机のID */
	public int deskId;

	/**
	 * 指定された情報で生徒を初期化します。
	 * @param nameHiragana 生徒の名前（ひらがな）
	 * @param name 生徒の名前
	 * @param deskId 生徒が座っている机の初期ID
	 */
	public Student(String nameHiragana, String name,int deskId) {
		super();
		this.nameHiragana = nameHiragana;
		this.name = name;
		this.deskId =deskId; 
	}	

	/**
	 * 生徒を新しい机に移動させます。
	 * 現在の机のIDとは異なる、空いている机をランダムに選択し、
	 * かつ現在の隣人とは異なる隣人を持つ机に座ります。
	 *
	 * @param newRoom 新しい座席配置を表す机の配列
	 * @param current 生徒が現在座っている机
	 */
	public void getDesk(Desk[] newRoom, Desk current) {
		int index;

		while(true)
		{
			// 新しい机のインデックスをランダムに選択
			index = (int)(Math.random()*newRoom.length);
			// 選択された机が現在の机と異なり、かつ空いている椅子がある場合
			if(index+1!= this.deskId && newRoom[index].hasFreeChair()) {
				// 新しい机の隣人が現在の隣人と同じではない場合
				if(newRoom[index].getNeighbor(this) != current.getNeighbor(this)) {
					newRoom[index].seatStudent(this); // 生徒を座らせる

					System.out.println("\n　・・・・・・　処理　・・・・・・　");
					Desk.printRoom(newRoom); // 新しい配置を表示
					break; // ループを終了
				}								
			}			
		}
	}		
}
