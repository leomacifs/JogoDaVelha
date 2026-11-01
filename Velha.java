import jogo.*;
import jogador.*;
import java.io.*;

// Ponto de entrada e controlador do jogo.
public class Velha
{
    public static void main (String args [])
    {
        Tabuleiro           tabDoJogo;

        Jogador             jogador  = null;
        JogadorHumano       jogadorH = null;
        JogadorAutomatico   jogadorA = null;

        AnalistaSituacional analista;

        boolean jogadorHumanoComeca = (int)(Math.random()*2) == 0;

        // Loop externo: repete partidas enquanto o usuário quiser.
        do
        {
            try
            {
                // Setup da partida.
                tabDoJogo = new Tabuleiro ();

                if (jogadorHumanoComeca)
                {
                    jogadorH = new JogadorHumano (tabDoJogo);
                    jogadorA = new JogadorAutomatico
                              (tabDoJogo,
                               new Marca(jogadorH.getMarca().getSimbolo()=='X'?'O':'X'));
                }
                else
                {
                    jogadorA = new JogadorAutomatico (tabDoJogo);
                    jogadorH = new JogadorHumano
                              (tabDoJogo,
                               new Marca(jogadorA.getMarca().getSimbolo()=='X'?'O':'X'));
                }

                analista = new AnalistaSituacional (tabDoJogo);

                // Loop interno: alterna turnos até o fim de jogo.
                jogador = jogadorHumanoComeca ? jogadorH : jogadorA;

                do
                {
                    jogador.facaSuaJogada();

                    if (jogador == jogadorH)
                        jogador = jogadorA;
                    else
                        jogador = jogadorH;
                }
                while (!analista.fimDeJogo());

                // Resultado da partida.
                Marca vencedor = analista.getMarcaDoVencedor();

                if (vencedor == null)
                    jogadorH.deuVelha ();
                else if (vencedor.equals(jogadorH.getMarca()))
                    jogadorH.vcGanhou ();
                else
                    jogadorH.vcPerdeu ();

                jogadorHumanoComeca = !jogadorHumanoComeca;
            }
            catch (Exception e)
            {
                System.out.println ("Erro inesperado: " + e.getMessage ());
            }
        }
        while (jogadorH.querMaisUmaPartida());
    }
}