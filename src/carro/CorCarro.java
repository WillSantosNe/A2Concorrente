package carro;

public enum CorCarro {
	RED, GREEN, BLUE;

	public static CorCarro proximaCor(int index) {
        return values()[index % values().length];
    }
}
