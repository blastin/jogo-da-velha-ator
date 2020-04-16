package dominio.jogo;

import dominio.*;

import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;

final class JogoDaVelhaImpl implements JogoDaVelha {

    private final Ator[] atores;

    private final Peca primeiro;

    private final Peca segundo;

    JogoDaVelhaImpl(final Ator jogador, final Ator adversario) {

        final int primeiroValorRandom = ThreadLocalRandom.current().nextInt(0, 1);

        final int segundoValorRandom = (primeiroValorRandom + 1) % 2;

        atores = new Ator[]{jogador, adversario};

        primeiro = new PecaJogavel(primeiroValorRandom);

        atores[primeiroValorRandom].receberPeca(primeiro);

        segundo = new PecaJogavel(segundoValorRandom);

        atores[segundoValorRandom].receberPeca(segundo);

    }

    @Override
    public final void iniciar() {

        final Ator primeiroJogador = atores[primeiro.valor()];

        final Ator segundoJogador = atores[segundo.valor()];

        final Tabuleiro tabuleiro = new TabuleiroImpl();

        while (true) {

            primeiroJogador.jogada(tabuleiro);

            final Optional<Resultado> resultadoPrimeiroAtor = tabuleiro.resultadoJogo();

            if (Objects.requireNonNull(resultadoPrimeiroAtor).isPresent()) {
                jogoFinalizado(resultadoPrimeiroAtor.get(), primeiroJogador, segundoJogador);
                break;
            }

            segundoJogador.jogada(tabuleiro);

            final Optional<Resultado> resultadoSegundoAtor = tabuleiro.resultadoJogo();

            if (Objects.requireNonNull(resultadoSegundoAtor).isPresent()) {
                jogoFinalizado(resultadoSegundoAtor.get(), segundoJogador, primeiroJogador);
                break;
            }

        }

    }

    private void jogoFinalizado(final Resultado resultado, final Ator primeiro, final Ator segundo) {
        primeiro.receberResultado(resultado);
        segundo.receberResultado(resultado);
    }

}
