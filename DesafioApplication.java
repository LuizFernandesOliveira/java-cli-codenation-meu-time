import exceptions.CapitaoNaoInformadoException;
import exceptions.IdentificadorUtilizadoException;
import exceptions.JogadorNaoEncontradoException;
import exceptions.TimeNaoEncontradoException;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class DesafioMeuTimeApplication implements MeuTimeInterface {
    private final List<Time> times = new ArrayList<>();

    private Time buscarTimeById(Long id) {
        List<Time> timesById = times.stream().filter(
                time -> id.equals(time.getId())
        ).collect(Collectors.toList());
        return timesById.size() != 0 ? timesById.get(0) : null;
    }

    private Time buscarTimeByIdJogador(Long idJogador) {
        List<Time> timesById = times.stream().filter(
                time -> time.getJogadores().stream().filter(jogador -> jogador.getId().equals(idJogador)).count() == 1
        ).collect(Collectors.toList());
        return timesById.size() != 0 ? timesById.get(0) : null;
    }

    public void incluirTime(Long id, String nome, LocalDate dataCriacao, String corUniformePrincipal, String corUniformeSecundario) {
        Time time = buscarTimeById(id);
        if (time == null) {
            this.times.add(new Time(id, nome, dataCriacao, corUniformePrincipal, corUniformeSecundario));
        } else {
            throw new IdentificadorUtilizadoException();
        }
    }

    public void incluirJogador(Long id, Long idTime, String nome, LocalDate dataNascimento, Integer nivelHabilidade, BigDecimal salario) {
        Time time = buscarTimeById(idTime);
        if (time != null) {
            for (Jogador jogador : time.getJogadores()) {
                if (jogador.getId().equals(id)) {
                    throw new IdentificadorUtilizadoException();
                }
            }
            time.setJogadores(new Jogador(id, idTime, nome, dataNascimento, nivelHabilidade, salario));
        } else {
            throw new TimeNaoEncontradoException();
        }

    }

    public void definirCapitao(Long idJogador) {
        Time time = buscarTimeByIdJogador(idJogador);
        if (time != null) {
            time.definirCapitao(idJogador);
        } else {
            throw new JogadorNaoEncontradoException();
        }
    }

    public Long buscarCapitaoDoTime(Long idTime) {
        Time time = buscarTimeById(idTime);
        if (time != null) {
            if (time.getCapitao() != null) {
                return time.getCapitao().getId();
            }
            throw new CapitaoNaoInformadoException();
        }
        throw new TimeNaoEncontradoException();
    }

    public String buscarNomeJogador(Long idJogador) {
        for (Time time : this.times) {
            for (Jogador jogador : time.getJogadores()) {
                if (idJogador.equals(jogador.getId())) {
                    return jogador.getNome();
                }
            }
        }
        throw new JogadorNaoEncontradoException();
    }

    public String buscarNomeTime(Long idTime) {
        Time time = buscarTimeById(idTime);
        if (time != null) {
            return time.getNome();
        }
        throw new TimeNaoEncontradoException();
    }

    public List<Long> buscarJogadoresDoTime(Long idTime) {
        Time time = buscarTimeById(idTime);
        if (time != null) {
            return time.getIdJogadores();
        }
        throw new TimeNaoEncontradoException();
    }

    public Long buscarMelhorJogadorDoTime(Long idTime) {
        Time time = buscarTimeById(idTime);
        if (time != null) {
            return time.getMelhorJogador();
        }
        throw new TimeNaoEncontradoException();
    }

    public Long buscarJogadorMaisVelho(Long idTime) {
        Time time = buscarTimeById(idTime);
        if (time != null) {
            return time.getJogadorMaisVelho();
        }
        throw new TimeNaoEncontradoException();
    }

    public List<Long> buscarTimes() {
        List<Long> idsTime = new ArrayList<>();
        for (Time time : this.times) {
            idsTime.add(time.getId());
        }
        idsTime.sort(new Comparator<Long>() {
            @Override
            public int compare(Long time1, Long time2) {
                return time1.compareTo(time2);
            }
        });
        return idsTime;
    }

    public Long buscarJogadorMaiorSalario(Long idTime) {
        Time time = buscarTimeById(idTime);
        if (time != null) {
            return time.getJogadorMaiorSalario();
        }
        throw new TimeNaoEncontradoException();
    }

    public BigDecimal buscarSalarioDoJogador(Long idJogador) {
        Time time = buscarTimeByIdJogador(idJogador);
        if (time != null) {
            return time.getSalary(idJogador);
        }
        throw new JogadorNaoEncontradoException();
    }

    public List<Long> buscarTopJogadores(Integer top) {
        List<Long> melhores = new ArrayList<>();
        for (int index = 1; index <= top; index += 1) {
            for (Time time : this.times) {
                time.ordenaJogadoresPorNivelOuId();
                melhores.add(time.getJogadores().get(time.getJogadores().size() - index).getId());
            }
        }
        return melhores;
    }
}