package controller;

import br.com.caelum.vraptor.Controller;
import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Result;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import javax.servlet.RequestDispatcher;
import model.Dependente;
import model.Pessoa;
import persistencia.DependenteDao;
import persistencia.PessoaDao;

@Controller
public class IndexController {

    @Inject
    private Result result;

    @Path(value = "/", priority = Path.HIGHEST)
    public void index() {
        PessoaDao pessoaDao = new PessoaDao();
        List<Pessoa> pessoas = pessoaDao.listAll();
        result.include("pessoas", pessoas);
    }

    @Get
    public void formAdicionarPessoa() {
    }

    @Post
    public void adicionarPessoa(String nome, String sobrenome) {
        PessoaDao pessoaDao = new PessoaDao();
        Pessoa novaPessoa = new Pessoa(0, nome, sobrenome, new ArrayList<Dependente>());
        pessoaDao.save(novaPessoa);
        result.forwardTo(this).index();
    }

    @Path(value = "/index/excluir/{id}")
    public void excluirPessoa(Long id) {
        if (id != null) {
            PessoaDao pessoaDao = new PessoaDao();
            pessoaDao.delete(id);
            
        }
        result.forwardTo(this).index();
    }
    
    @Path(value="/index/excluirTodosDependentes/{id}")
    public void excluirTodosDependentes(Long id) {
        if (id != null) {
            PessoaDao pessoaDao = new PessoaDao();
            Pessoa pessoa = pessoaDao.getById(id);
            List<Dependente> dependentes = pessoa.getDependentes();
            DependenteDao dependenteDao = new DependenteDao();
            for (Dependente dependente : dependentes) {
                dependenteDao.delete(dependente.getId());
            }
            
        }
        result.forwardTo(this).index();
    }
    
    @Path("/index/formEditarPessoa/{id}")
    public void formEditarPessoa(Long id){
        if (id != null) {
            PessoaDao pessoaDao = new PessoaDao();
            Pessoa pessoa = pessoaDao.getById(id);
            result.include("nome", pessoa.getNome());
            result.include("sobrenome", pessoa.getSobrenome());
            result.include("id", pessoa.getId());
        }
    }
    
    @Post
    public void editarPessoa(String nome, String sobrenome, Long pessoaId){
        if (pessoaId != null) {
            PessoaDao pessoaDao = new PessoaDao();
            
            pessoaDao.edit(pessoaId, nome, sobrenome);
        }
        result.forwardTo(this).index();
    }
}
