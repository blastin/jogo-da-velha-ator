package dominio.jogo;

import dominio.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Optional;

public class JogoDaVelhaTest {

    private final JogoDaVelhaFabrica jogoDaVelhaFabrica = new JogoDaVelhaFabricaImplementacao();

    @Test
    public void jogoViciadoDiagonalEsquerda() {

        final JogadorParaTeste jogador = new JogadorParaTeste(new ModeloJogada() {

            private int x = 0;

            private int y = 0;

            @Override
            public int x() {
                return x++;

            }

            @Override
            public int y() {
                return y++;
            }

        });

        resultado(jogador);

    }

    @Test
    public void jogoViciadoDiagonalDireita() {

        final JogadorParaTeste jogador = new JogadorParaTeste(new ModeloJogada() {

            private int x = 0;

            private int y = 2;

            @Override
            public int x() {
                return x++;
            }

            @Override
            public int y() {
                return y--;
            }

        });

        resultado(jogador);

    }

    @Test
    public void jogoViciadoPrimeiraLinha() {

        final JogadorParaTeste jogador = new JogadorParaTeste(new ModeloJogada() {

            private int y = 0;

            @Override
            public int x() {
                return 0;

            }

            @Override
            public int y() {
                return y++;
            }

        });

        resultado(jogador);

    }

    @Test
    public void jogoViciadoSegundaLinha() {

        final JogadorParaTeste jogador = new JogadorParaTeste(new ModeloJogada() {

            private int y = 0;

            @Override
            public int x() {
                return 1;

            }

            @Override
            public int y() {
                return y++;
            }

        });

        resultado(jogador);

    }

    @Test
    public void jogoViciadoTerceiraLinha() {

        final JogadorParaTeste jogador = new JogadorParaTeste(new ModeloJogada() {

            private int y = 0;

            @Override
            public int x() {
                return 2;

            }

            @Override
            public int y() {
                return y++;
            }

        });

        resultado(jogador);

    }

    @Test
    public void jogoViciadoPrimeiraColuna() {

        final JogadorParaTeste jogador = new JogadorParaTeste(new ModeloJogada() {

            private int x = 0;

            @Override
            public int x() {
                return x++;

            }

            @Override
            public int y() {
                return 0;
            }

        });

        resultado(jogador);

    }

    @Test
    public void jogoViciadoSegundaColuna() {

        final JogadorParaTeste jogador = new JogadorParaTeste(new ModeloJogada() {

            private int x = 0;

            @Override
            public int x() {
                return x++;

            }

            @Override
            public int y() {
                return 1;
            }

        });

        resultado(jogador);

    }

    @Test
    public void jogoViciadoTerceiraColuna() {

        final JogadorParaTeste jogador = new JogadorParaTeste(new ModeloJogada() {

            private int x = 0;

            @Override
            public int x() {
                return x++;

            }

            @Override
            public int y() {
                return 2;
            }

        });

        resultado(jogador);

    }

    @Test
    public void jogoViciadoEmpate() {

        final JogadorParaTeste jogador = new JogadorParaTeste(new ModeloJogada() {

            private final int[][] posicoes = {{0, 0}, {0, 2}, {1, 2}, {2, 0}, {2, 1}};

            private int indice = 0;

            @Override
            public int x() {
                return posicoes[indice][0];
            }

            @Override
            public int y() {
                return posicoes[indice++][1];
            }

        });

        final JogadorParaTeste adversario = new JogadorParaTeste(new ModeloJogada() {

            private final int[][] posicoes = {{0, 1}, {1, 0}, {1, 1}, {2, 2}};

            private int indice = 0;

            @Override
            public int x() {
                return posicoes[indice][0];
            }

            @Override
            public int y() {
                return posicoes[indice++][1];
            }

        });

        final JogoDaVelha jogoDaVelha = jogoDaVelhaFabrica.construir(jogador, adversario);

        jogoDaVelha.iniciar();

        Assertions.assertTrue(jogador.empatou(), "jogador deve ter empatado");

        Assertions.assertTrue(adversario.empatou(), "adversario deve ter empatado");

    }

    private void resultado(final JogadorParaTeste jogador) {

        final AdversarioParaTeste adversario = new AdversarioParaTeste();

        final JogoDaVelha jogoDaVelha = jogoDaVelhaFabrica.construir(jogador, adversario);

        jogoDaVelha.iniciar();

        Assertions.assertTrue(jogador.ganhou(), "jogador deve ganhar");

        Assertions.assertTrue(adversario.perdeu(), "adversario deve perder");

    }

    private static class JogadorParaTeste implements Ator {

        private final ModeloJogada modeloJogada;

        private Peca peca;

        private boolean ganhou;
        private boolean empatou;

        private JogadorParaTeste(final ModeloJogada modeloJogada) {
            this.modeloJogada = modeloJogada;
            ganhou = false;
            empatou = false;
        }

        @Override
        public void jogada(final Jogadas jogadasDisponiveis) {

            final int x = modeloJogada.x();
            final int y = modeloJogada.y();

            final Optional<Posicao> possivelPosicao = jogadasDisponiveis.posicoes().obter(x, y);

            if (possivelPosicao.isPresent()) {
                Posicao posicao = possivelPosicao.get();
                posicao.jogar(peca);
            }

        }

        @Override
        public void receberResultado(final Resultado resultado) {
            ganhou = resultado.relatorio().equals(Relatorio.GANHOU) && resultado.peca().equals(peca);
            empatou = resultado.relatorio().equals(Relatorio.EMPATOU);
        }

        @Override
        public void receberPeca(final Peca peca) {
            this.peca = peca;
        }

        boolean ganhou() {
            return ganhou;
        }

        boolean empatou() {
            return empatou;
        }

    }

    private static class AdversarioParaTeste implements Ator {

        private boolean perdeu;
        private Peca peca;

        private AdversarioParaTeste() {
            perdeu = false;
        }

        @Override
        public void jogada(final Jogadas jogadasDisponiveis) {
        }

        @Override
        public void receberResultado(final Resultado resultado) {
            perdeu = resultado.relatorio().equals(Relatorio.GANHOU) && !resultado.peca().equals(peca);
        }

        @Override
        public void receberPeca(final Peca peca) {
            this.peca = peca;
        }

        boolean perdeu() {
            return perdeu;
        }

    }

    private interface ModeloJogada {

        int x();

        int y();

    }
}
