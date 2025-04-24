package carro;

public enum TipoVeiculo {
	SUV, SEDAN;

    public static TipoVeiculo proximoTipo(int index) {
        return values()[index % values().length];
    }
}
