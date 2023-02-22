public enum MyType {
	START('S'), END('E'), WALL('X'), EMPTY('O');

	public int vrednost;

	private MyType(int vr) {
		this.vrednost = vr;
	}
}
