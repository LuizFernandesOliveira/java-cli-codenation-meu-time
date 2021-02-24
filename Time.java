import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Time {
    private Long id;
    private String nome;
    private LocalDate dataCriacao;
    private Jogador capitao = null;
    private String corUniformePrincipal;
    private String corUniformeSecundario;
    private List<Jogador> jogadores = new ArrayList<>();

    public Time(Long id, String nome, LocalDate dataCriacao, String corUniformePrincipal, String corUniformeSecundario) {
        setId(id);
        setNome(nome);
        setDataCriacao(dataCriacao);
        setCorUniformePrincipal(corUniformePrincipal);
        setCorUniformeSecundario(corUniformeSecundario);
    }

    public Jogador getCapitao() {
        return capitao;
    }

    public void setCapitao(Jogador capitao) {
        this.capitao = capitao;
    }

    public List<Jogador> getJogadores() {
        return jogadores;
    }

    public List<Long> getIdJogadores() {
        List<Long> ids = new ArrayList<>();
        for (Jogador jogador : this.jogadores){
            ids.add(jogador.getId());
        }
        return ids;
    }

    public void ordenaJogadoresPorNivelOuId() {
        this.jogadores.sort(new Comparator<Jogador>() {
            @Override
            public int compare(Jogador jogador1, Jogador jogador2) {
                int result = jogador1.getNivelHabilidade().compareTo(jogador2.getNivelHabilidade());
                return result != 0 ? result : jogador1.getId().compareTo(jogador1.getId()) * -1;
            }
        });
    }

    public Long getMelhorJogador() {
        this.ordenaJogadoresPorNivelOuId();
        return this.jogadores.get(this.jogadores.size() - 1).getId();
    }

    public Long getJogadorMaisVelho() {
        Long maisVelho = 0l;
        LocalDate velho = LocalDate.now();
        for (Jogador jogador : this.jogadores){
            if (jogador.getDataNascimento().isBefore(velho)) {
                maisVelho = jogador.getId();
                velho = jogador.getDataNascimento();
            }
        }
        return maisVelho;
    }

    public Long getJogadorMaiorSalario() {
        Long maiorSalario = 0l;
        BigDecimal salario = new BigDecimal(0);
        for (Jogador jogador : this.jogadores){
            if (jogador.getSalario().compareTo(salario) == 1) {
                maiorSalario = jogador.getId();
                salario = jogador.getSalario();
            }
        }
        return maiorSalario;
    }

    public BigDecimal getSalary(Long idJogador) {
        BigDecimal salary = new BigDecimal(0);
        for (Jogador jogador : this.jogadores) {
            if (jogador.getId().equals(idJogador)) {
                salary = jogador.getSalario();
            }
        }
        return salary;
    }

    public void setJogadores(Jogador jogador) {
        this.jogadores.add(jogador);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setDataCriacao(LocalDate dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    public void setCorUniformePrincipal(String corUniformePrincipal) {
        this.corUniformePrincipal = corUniformePrincipal;
    }

    public void setCorUniformeSecundario(String corUniformeSecundario) {
        this.corUniformeSecundario = corUniformeSecundario;
    }

    public void definirCapitao(Long idJogador) {
        for (Jogador jogador : this.jogadores) {
            if (jogador.getId().equals(idJogador)) {
                this.setCapitao(jogador);
            }
        }
    }
}