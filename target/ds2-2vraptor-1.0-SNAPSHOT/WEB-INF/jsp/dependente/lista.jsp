<%-- 
    Document   : listagem_dependentes
    Created on : 22/05/2015, 17:07:36
    Author     : Aluno
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib  uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <c:import url="../lib/header.jsp"/>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Listagem de dependentes</title>
    </head>
    <body>
        <div class="container-fluid">
            <h3>Dependentes de ${pessoa.nome}</h3>
<c:if test="${!(empty dependentes)}">
            <form id="form" method="POST" action="${linkTo[DependenteController].excluirDependentesSelecionados}">
</c:if>
                <table class="table-condensed table-hover">
                    <thead>
                        <tr>
                            <th>Nome</th>
                            <th>Sobrenome</th>
                            <th>Excluir</th>
                            <th>Editar</th>
                            <th><span class="glyphicon glyphicon-check"/></th>
                        </tr>
                    </thead>
                    <c:forEach items="${dependentes}" var="dependente">
                        <tr>
                            <td>${dependente.nome}</td> 
                            <td>${dependente.sobrenome}</td> 
                            <td align="center"><a href="${linkTo[DependenteController].excluirDependente(pessoa.id, dependente.id)}">
                                    <span class="glyphicon glyphicon-trash" title="Excluir"/></a></a></td>
                            <td align="center"><a href="${linkTo[DependenteController].formEditarDependente(pessoa.id, dependente.id)}">
                                    <span class="glyphicon glyphicon-pencil" title="Editar"/></a></td>
                            <td><input type="checkbox" name="dependenteSelecionado" value="${dependente.id}"></td>
                        </tr>
                    </c:forEach>
                </table>
<c:if test="${!(empty dependentes)}">
                <input type="hidden" name="pessoaId" value="${pessoa.id}">
                <button class="btn btn-danger" type="submit" >
                    <span class="glyphicon glyphicon-trash" title="Excluir"/>
                    <span>Excluir selecionados</span>
                </button>
            </form>
            
                <script type="text/javascript">
                    $('#form').validate({
                        rules: {
                            dependenteSelecionado:{
                                required: true
                            }
                        }
                    });
                </script>
</c:if>

            <a href="${linkTo[DependenteController].formAdicionarDependente(pessoa.id)}">
                <button class="btn btn-success" type="submit" >
                    <span class="glyphicon glyphicon-plus" title="Adicionar"/>
                    <span>Adicionar dependente</span>
                </button>

            </a>
            
            <a href="${linkTo[IndexController].index()}">
                <button class="btn btn-success" type="submit" >
                    <span class="glyphicon glyphicon-backward" title="Voltar"/>
                    <span>Voltar</span>
                </button>

            </a>
        </div>
    </body>

</html>
