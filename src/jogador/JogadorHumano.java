package jogador;

import jogo.Marca;
import jogo.Tabuleiro;
import java.io.*;
import java.util.Objects;

public class JogadorHumano implements Jogador, Cloneable
{
    private Marca          marca     = null;
    private Tabuleiro      tabDoJogo = null;
    private BufferedReader teclado   = new BufferedReader
                                        (new InputStreamReader (System.in));

    public JogadorHumano (Tabuleiro tab) throws Exception
    {
        if (tab == null) throw new Exception("Tabuleiro não pode ser nulo");
        this.tabDoJogo = tab;

        System.out.print("Escolha sua marca (X/O): ");
        String entrada = teclado.readLine();
        if (entrada == null || entrada.isEmpty())
            throw new Exception("Nenhuma marca digitada");
        this.marca = new Marca(Character.toUpperCase(entrada.charAt(0)));
    }

    public JogadorHumano (Tabuleiro tab, Marca mrc) throws Exception
    {
        if (tab == null) throw new Exception("Tabuleiro não pode ser nulo");
        if (mrc == null) throw new Exception("Marca não pode ser nula");
        this.tabDoJogo = tab;
        this.marca     = mrc;
    }

    public Marca getMarca ()
    {
        return this.marca;
    }

    public void facaSuaJogada ()
    {
        boolean jogou = false;
        while (!jogou)
        {
            try
            {
                System.out.println(tabDoJogo.toString());
                System.out.print("Linha (0-2): ");
                int lin = Integer.parseInt(teclado.readLine().trim());
                System.out.print("Coluna (0-2): ");
                int col = Integer.parseInt(teclado.readLine().trim());

                if (tabDoJogo.haMarcaNaPosicao(lin, col)) {
                    System.out.println("Posição já ocupada!");
                    continue;
                }
                tabDoJogo.setMarcaNaPosicao(this.marca, lin, col);
                jogou = true;
            }
            catch (Exception e) {
                System.out.println("Entrada inválida. Tente novamente.");
            }
        }
    }

    public void vcGanhou () { System.out.println("Você ganhou!"); }
    public void vcPerdeu () { System.out.println("Você perdeu!"); }
    public void deuVelha () { System.out.println("Deu velha! Empate."); }

    public boolean querMaisUmaPartida ()
    {
        try
        {
            System.out.print("Jogar novamente? (s/n): ");
            String r = teclado.readLine().trim().toLowerCase();
            return r.startsWith("s");
        }
        catch (Exception e) { return false; }
    }

    @Override
    public String toString ()
    {
        return "JogadorHumano[" + marca + "]";
    }

    @Override
    public boolean equals (Object obj)
    {
        if (this == obj) return true;
        if (obj == null || this.getClass() != obj.getClass()) return false;
        JogadorHumano outro = (JogadorHumano) obj;
        return Objects.equals(this.marca, outro.marca)
            && Objects.equals(this.tabDoJogo, outro.tabDoJogo);
    }

    @Override
    public int hashCode ()
    {
        return Objects.hash(this.marca, this.tabDoJogo);
    }

    public JogadorHumano (JogadorHumano modelo) throws Exception
    {
        if (modelo == null) throw new Exception("Modelo não pode ser nulo");
        this.marca = (modelo.marca != null)
            ? new Marca(modelo.marca.getSimbolo()) : null;
        this.tabDoJogo = (modelo.tabDoJogo != null)
            ? new Tabuleiro(modelo.tabDoJogo) : null;
        this.teclado = new BufferedReader(new InputStreamReader(System.in));
    }

    @Override
    public Object clone ()
    {
        try { return new JogadorHumano(this); }
        catch (Exception e) { return null; }
    }
}