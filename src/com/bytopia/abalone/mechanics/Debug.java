package com.bytopia.abalone.mechanics;

public class Debug {

	public static void main(String[] args) throws Exception {
		System.out.println("start");
//		System.out.println(Cell.get(1, 1));
//		System.out.println(Cell.get(1, 1).shift(Direction.North));
//		for (int i = 1; i <= 9; i++)
//			for (int j = 1; j <= 9; j++) {
//				Cell c= Cell.get(i, j);
//				for (Direction d : Direction.values()) {
//					System.out.println(c.shift(d));
//				}
//			}
//		System.out.println("stop");
		ConsoleWatcher cw = new ConsoleWatcher();
		Game g = new Game(new ClassicLayout(), (byte)3, new AiCharlotte(), new AiCharlotte(), cw, (byte)0);
		cw.setGame(g);
//		android.os.Debug.startMethodTracing();
		g.start();
//		android.os.Debug.stopMethodTracing();
	}

	public static byte convDir(String s) {
		if (s.equals("NW"))
			return Direction.NorthWest;//	public static Cell convCell(String s) {
//		return new Cell((int) s.charAt(0) - (int) 'A' + 1, Integer
//		.parseInt(Character.toString(s.charAt(1))));
//}
		else if (s.equals("N"))
			return Direction.North;
		else if (s.equals("E"))
			return Direction.East;
		else if (s.equals("SE"))
			return Direction.SouthEast;
		else if (s.equals("S"))
			return Direction.South;
		else
			return Direction.West;
	}

//	public static Cell convCell(String s) {
//		return new Cell((int) s.charAt(0) - (int) 'A' + 1, Integer
//				.parseInt(Character.toString(s.charAt(1))));
//	}

	public static byte convSide(String s) {
		if (s.equals("W"))
			return Side.WHITE;
		else
			return Side.BLACK;
	}
}
