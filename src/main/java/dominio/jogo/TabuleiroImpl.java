package dominio.jogo;

import dominio.*;

import java.util.Objects;
import java.util.Optional;

final class TabuleiroImpl implements Tabuleiro {

    private final PosicaoTabuleiro[][] posicoes;

    private PosicaoTabuleiro ultimaJogada;

    private final int quantidadePosicoesMaximas;

    private int quantidadePosicoes;

    TabuleiroImpl() {

        quantidadePosicoesMaximas = 9;

        quantidadePosicoes = 0;

        posicoes = new PosicaoTabuleiro[3][3];

        for (int x = 0; x < 3; x++) {
            for (int y = 0; y < 3; y++) {
                posicoes[x][y] = new PosicaoTabuleiro();
            }
        }

    }

    private Optional<Resultado> gerarRelatorioGanhador(final int celula) {
        return Optional.of(new ResultadoJogoDaVelha(new PecaJogavel(celula), Relatorio.GANHOU));
    }

    private Optional<Resultado> gerarRelatorioEmpate(final int celula) {
        return Optional.of(new ResultadoJogoDaVelha(new PecaJogavel(celula), Relatorio.EMPATOU));
    }

    private boolean posicoesFechadas(final PosicaoTabuleiro a, final PosicaoTabuleiro b, final PosicaoTabuleiro c) {
        return (a.fechada() && b.fechada() && c.fechada()) && (a.equals(b) && b.equals(c));
    }

    private boolean jogoEmpatou() {
        return quantidadePosicoes == quantidadePosicoesMaximas;
    }

    @Override
    public Optional<Resultado> resultadoJogo() {

        if (posicoesFechadas(posicoes[0][0], posicoes[0][1], posicoes[0][2])) {
            return gerarRelatorioGanhador(posicoes[0][0].celula);
        } else if (posicoesFechadas(posicoes[1][0], posicoes[1][1], posicoes[1][2])) {
            return gerarRelatorioGanhador(posicoes[1][0].celula);
        } else if (posicoesFechadas(posicoes[2][0], posicoes[2][1], posicoes[2][2])) {
            return gerarRelatorioGanhador(posicoes[2][0].celula);
        } else if (posicoesFechadas(posicoes[0][0], posicoes[1][0], posicoes[2][0])) {
            return gerarRelatorioGanhador(posicoes[0][0].celula);
        } else if (posicoesFechadas(posicoes[0][1], posicoes[1][1], posicoes[2][1])) {
            return gerarRelatorioGanhador(posicoes[0][1].celula);
        } else if (posicoesFechadas(posicoes[0][2], posicoes[1][2], posicoes[2][2])) {
            return gerarRelatorioGanhador(posicoes[0][2].celula);
        } else if (posicoesFechadas(posicoes[0][0], posicoes[1][1], posicoes[2][2])) {
            return gerarRelatorioGanhador(posicoes[0][0].celula);
        } else if (posicoesFechadas(posicoes[0][2], posicoes[1][1], posicoes[2][0])) {
            return gerarRelatorioGanhador(posicoes[0][2].celula);
        } else if (jogoEmpatou()) {
            return gerarRelatorioEmpate(posicoes[0][0].celula);
        }

        return Optional.empty();

    }

    @Override
    public Optional<Posicao> obter(final int x, final int y) {

        if (x < 0 || x >= 3 || y < 0 || y >= 3) {
            return Optional.empty();
        }

        final PosicaoTabuleiro posicaoTabuleiro = posicoes[x][y];

        if (posicaoTabuleiro.fechada()) {
            return Optional.empty();
        }

        ultimaJogada = posicaoTabuleiro;

        quantidadePosicoes = quantidadePosicoes + 1;

        return Optional.of(posicaoTabuleiro);

    }

    @Override
    public Optional<Posicao> jogadaAdversario() {

        if (!ultimaJogada.fechada()) ultimaJogada = null;

        return Optional.ofNullable(ultimaJogada);

    }

    @Override
    public Colecao<Posicao> posicoes() {
        return this;
    }

    private static class PosicaoTabuleiro implements Posicao {

        private static final int VAZIA = Integer.MIN_VALUE;

        private int celula;

        private PosicaoTabuleiro() {
            celula = VAZIA;
        }

        @Override
        public void jogar(final Peca peca) {
            celula = Optional.ofNullable(peca).orElse(() -> VAZIA).valor();
        }

        boolean fechada() {
            return celula != VAZIA;
        }

        @Override
        public boolean equals(final Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            final PosicaoTabuleiro that = (PosicaoTabuleiro) o;
            return celula == that.celula;
        }

        @Override
        public int hashCode() {
            return Objects.hash(celula);
        }
    }
}
