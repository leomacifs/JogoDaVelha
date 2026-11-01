package jogador;

import jogo.Marca;
import jogo.Tabuleiro;
import java.io.*;
import java.util.Objects;

// Jogador controlado pelo usuário via teclado.
public class JogadorHumano implements Jogador, Cloneable
{
    private Marca          marca     = null;
    private Tabuleiro      tabDoJogo = null;
    private BufferedReader teclado   = new BufferedReader
                                        (new InputStreamReader (System.in));

    // === Construtores ===

    public JogadorHumano (Tabuleiro tab) throws Exception
    {
        if (tab == null) throw new Exception("Tabuleiro não pode ser nulo");
        this.tabDoJogo = tab;

        System.out.print("Escolha sua marca (X/O): ");
        String entrada = teclado.readLine();
        if (entrada == null || entrada.trim().isEmpty())
            throw new Exception("Nenhuma marca digitada");
        this.marca = new Marca(Character.toUpperCase(entrada.trim().charAt(0)));
    }

    public JogadorHumano (Tabuleiro tab, Marca mrc) throws Exception
    {
        if (tab == null) throw new Exception("Tabuleiro não pode ser nulo");
        if (mrc == null) throw new Exception("Marca não pode ser nula");
        this.tabDoJogo = tab;
        this.marca     = mrc;
    }

    // === Contrato Jogador ===

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

                int lin = leDigito("Linha (0-2): ");
                int col = leDigito("Coluna (0-2): ");

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

    // === Validação de entrada ===

    // Aceita exatamente um dígito entre '0' e '2'.
    private int leDigito (String prompt) throws Exception
    {
        System.out.print(prompt);
        String entrada = teclado.readLine();

        if (entrada == null)  throw new Exception("Nenhum caractere digitado");

        String s = entrada.trim();
        if (s.isEmpty())      throw new Exception("Nenhum caractere digitado");
        if (s.length() != 1)  throw new Exception("Digite APENAS um caractere (0, 1 ou 2)");

        char c = s.charAt(0);
        if (c < '0' || c > '2')
            throw new Exception("Apenas 0, 1 ou 2 são aceitos");

        return c - '0';
    }

    // === Mensagens de fim de jogo ===

    public void vcGanhou () { System.out.println("Você ganhou!"); }
    public void vcPerdeu () { System.out.println("Você perdeu!"); }
    public void deuVelha () { System.out.println("Deu velha! Empate."); }

    // === Repetição de partida ===

    public boolean querMaisUmaPartida ()
    {
        while (true)
        {
            try
            {
                char c = leSimNao("Jogar novamente? (s/n): ");

                if (c == 's') return true;
                if (c == 'n') return false;
            }
            catch (Exception e)
            {
                System.out.println("Resposta inválida. Digite apenas 's' (sim) ou 'n' (não).");
            }
        }
    }

    // Aceita exatamente 's' ou 'n' (case-insensitive).
    private char leSimNao (String prompt) throws Exception
    {
        System.out.print(prompt);
        String entrada = teclado.readLine();

        if (entrada == null)  throw new Exception("Nenhum caractere digitado");

        String s = entrada.trim();
        if (s.isEmpty())      throw new Exception("Nenhum caractere digitado");
        if (s.length() != 1)  throw new Exception("Digite APENAS um caractere (s ou n)");

        char c = Character.toLowerCase(s.charAt(0));
        if (c != 's' && c != 'n')
            throw new Exception("Apenas 's' ou 'n' são aceitos");

        return c;
    }

    // === Object ===

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

    // === Cópia ===

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