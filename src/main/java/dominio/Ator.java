package dominio;

public interface Ator {

    void jogada(final Jogadas jogadasDisponiveis);

    void receberResultado(final Resultado resultado);

    void receberPeca(final Peca peca);

}
