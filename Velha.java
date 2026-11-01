import jogo.*;
import jogador.*;
import java.io.*;

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

        do
        {
            try
            {
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

                if (jogadorHumanoComeca)
                    jogador=jogadorH;
                else
                    jogador=jogadorA;

                do
                {
                    jogador.facaSuaJogada();

                    if (jogador==jogadorH)
                        jogador=jogadorA;
                    else
                        jogador=jogadorH;
                }
                while (!analista.fimDeJogo());

                //FIX: armazena o vencedor numa variável ANTES de chamar .equals()
                Marca vencedor = analista.getMarcaDoVencedor();

                if (vencedor == null)
                    jogadorH.deuVelha ();          // empate
                else if (vencedor.equals(jogadorH.getMarca()))
                    jogadorH.vcGanhou ();          // humano venceu
                else
                    jogadorH.vcPerdeu ();          // IA venceu

                jogadorHumanoComeca = !jogadorHumanoComeca;
            }
            catch (Exception e)
            {
                //FIX: não engolir exceção silenciosamente
                System.out.println ("Erro inesperado: " + e.getMessage ());
            }
        }
        while (jogadorH.querMaisUmaPartida());
    }
}