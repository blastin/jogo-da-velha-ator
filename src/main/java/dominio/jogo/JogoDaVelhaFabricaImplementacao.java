package dominio.jogo;

import dominio.Ator;
import dominio.JogoDaVelha;
import dominio.JogoDaVelhaFabrica;

final class JogoDaVelhaFabricaImplementacao implements JogoDaVelhaFabrica {

    JogoDaVelhaFabricaImplementacao() {
    }

    @Override
    public JogoDaVelha construir(final Ator jogador, final Ator adversario) {
        return new JogoDaVelhaImpl(new AtorProxy(jogador), new AtorProxy(adversario));
    }

}
