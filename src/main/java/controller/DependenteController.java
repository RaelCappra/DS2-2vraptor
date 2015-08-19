package controller;

import br.com.caelum.vraptor.Controller;
import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.interceptor.IncludeParameters;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import javax.servlet.RequestDispatcher;
import model.Dependente;
import model.Pessoa;
import persistencia.DependenteDao;
import persistencia.PessoaDao;

@Controller
public class DependenteController {

    @Inject
    private Result result;

    @Path("dependente/lista/{pessoaId}")
    public void lista(Long pessoaId) {
        if (pessoaId != null) {
            PessoaDao pessoaDao = new PessoaDao();
            Pessoa pessoa = pessoaDao.getById(pessoaId);
            List<Dependente> dependentes;
            if(pessoa != null){
                dependentes = pessoa.getDependentes();
            }else{
               dependentes = new ArrayList<>();
            }
            result.include("dependentes", dependentes);
            result.include("pessoa", pessoa);
        }
    }

    @Path("/dependente/formAdicionarDependente/{pessoaId}")
    public void formAdicionarDependente(Long pessoaId) {
        result.include("pessoaId", pessoaId);
    }

    @Post
    public void adicionarDependente(Long pessoaId, String nome, String sobrenome) {
        if (pessoaId != null) {
            DependenteDao dependenteDao = new DependenteDao();
            Dependente novoDependente = new Dependente(0, nome, sobrenome, pessoaId);
            dependenteDao.save(novoDependente);

        }
        result.forwardTo(this).lista(pessoaId);
    }

    @Path("/dependente/formAdicionarDependente/{pessoaId}/{dependenteId}")
    public void formEditarDependente(Long pessoaId, Long dependenteId) {
        if (pessoaId != null && dependenteId != null) {
            PessoaDao pessoaDao = new PessoaDao();
            Pessoa pessoa = pessoaDao.getById(pessoaId);

            DependenteDao dependenteDao = new DependenteDao();
            Dependente dependente = dependenteDao.getById(dependenteId);
            result.include("pessoa", pessoa);
            result.include("dependente", dependente);
        }
    }

    @Post
    public void editarDependente(Long pessoaId, Long dependenteId, String nome, String sobrenome) {
        if (dependenteId != null) {
            DependenteDao dependenteDao = new DependenteDao();

            dependenteDao.edit(dependenteId, nome, sobrenome);
        }
        result.forwardTo(this).lista(pessoaId);
    }

    @Get
    @Path("/dependente/excluirDependente/{pessoaId}/{dependenteId}")
    public void excluirDependente(Long dependenteId, Long pessoaId) {
        if (dependenteId != null) {
            DependenteDao dependenteDao = new DependenteDao();
            dependenteDao.delete(dependenteId);
            result.forwardTo(this).lista(pessoaId);
        }
    }

    @Post
    public void excluirDependentesSelecionados(long[] dependenteSelecionado, Long pessoaId) {
        if (dependenteSelecionado != null) {
            DependenteDao dependenteDao = new DependenteDao();
            for (long id : dependenteSelecionado) {
                dependenteDao.delete(id);
            }
            result.forwardTo(this).lista(pessoaId);
        }
    }
}
