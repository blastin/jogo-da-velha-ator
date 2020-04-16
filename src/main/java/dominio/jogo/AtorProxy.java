package dominio.jogo;

import dominio.*;

import java.util.Optional;

final class AtorProxy implements Ator {

    private final Ator ator;

    private Jogadas jogadas;

    private Peca peca;

    AtorProxy(final Ator ator) {
        this.ator = ator;
    }

    @Override
    public void jogada(final Jogadas jogadasDisponiveis) {
        if (jogadas == null) jogadas = new JogadasProxy(jogadasDisponiveis, peca);
        ator.jogada(jogadas);
    }

    @Override
    public void receberResultado(final Resultado resultado) {
        ator.receberResultado(resultado);
    }

    @Override
    public void receberPeca(final Peca peca) {
        this.peca = peca;
        ator.receberPeca(peca);
    }

    private static class JogadasProxy implements Jogadas {

        private final Jogadas jogadas;
        private final Peca pecaExigida;

        private ColexaoProxy posicoesProxy;

        private JogadasProxy(final Jogadas jogadas, final Peca pecaExigida) {
            this.jogadas = jogadas;
            this.pecaExigida = pecaExigida;
        }

        @Override
        public Optional<Posicao> jogadaAdversario() {
            return jogadas.jogadaAdversario();
        }

        @Override
        public Colecao<Posicao> posicoes() {
            if (posicoesProxy == null) posicoesProxy = new ColexaoProxy(jogadas.posicoes(), pecaExigida);
            return posicoesProxy;
        }

    }

    private static class ColexaoProxy implements Colecao<Posicao> {

        private final Colecao<Posicao> colecao;

        private final Peca pecaExigida;

        private ColexaoProxy(final Colecao<Posicao> colecao, final Peca pecaExigida) {
            this.colecao = colecao;
            this.pecaExigida = pecaExigida;
        }

        @Override
        public Optional<Posicao> obter(final int x, final int y) {

            final Optional<Posicao> possivelPosicao = colecao.obter(x, y);

            return possivelPosicao.map(posicao -> new PosicaoProxy(posicao, pecaExigida));

        }

    }

    private static class PosicaoProxy implements Posicao {

        private final Posicao posicao;

        private final Peca pecaExigida;

        private PosicaoProxy(final Posicao posicao, final Peca pecaExigida) {
            this.posicao = posicao;
            this.pecaExigida = pecaExigida;
        }

        @Override
        public void jogar(final Peca peca) {

            if (!pecaExigida.equals(peca))
                throw new IllegalArgumentException("Ator não encaminhou peca que ele recebeu");

            posicao.jogar(peca);

        }

    }
}
