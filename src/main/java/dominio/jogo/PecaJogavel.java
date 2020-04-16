package dominio.jogo;

import dominio.Peca;

import java.util.Objects;

final class PecaJogavel implements Peca {

    private final int valorPeca;

    PecaJogavel(final int valorPeca) {
        this.valorPeca = valorPeca;
    }

    @Override
    public int valor() {
        return valorPeca;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final PecaJogavel that = (PecaJogavel) o;
        return valorPeca == that.valorPeca;
    }

    @Override
    public int hashCode() {
        return Objects.hash(valorPeca);
    }

}
