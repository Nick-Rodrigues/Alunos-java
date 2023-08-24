package com.example.data;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.example.model.Aluno;

public class AlunoDao {
    private final String HOST = "auth-db719.hstgr.io";
    private final String PORT = "3306";
    private final String DATABASE = "u553405907_fiap";
    private final String USER = "u553405907_fiap";
    private final String PASS = "u553405907_FIAP";
    final String URL = "jdbc:mysql://" + HOST + ":" + PORT + "/" + DATABASE;

    public void inserir(Aluno aluno) throws Exception{
        
        var connection = DriverManager.getConnection(URL, USER, PASS);
        var instrucao = connection.createStatement();
        instrucao.execute("INSERT INTO alunos (nome, turma, rm) VALUE ('Joao', '1PG', 00000)");
        connection.close();

        
    }
    
    public List<Aluno> listarTodos() throws SQLException{
        var alunos = new ArrayList<Aluno>();

        final String HOST = "auth-db719.hstgr.io";
        final String PORT = "3306";
        final String DATABASE = "u553405907_fiap";
        final String USER = "u553405907_fiap";
        final String PASS = "u553405907_FIAP";
        final String URL = "jdbc:mysql://" + HOST + ":" + PORT + "/" + DATABASE; // JDBC URL

            var con = DriverManager.getConnection(URL, USER, PASS);
            var sql = "SELECT * FROM alunos ORDER BY nome";
            var instrucao = con.prepareStatement(sql);
            var dados = instrucao.executeQuery();

            while(dados.next()){
                var aluno = new Aluno(
                    dados.getInt("id"),
                    dados.getString("nome"),
                    dados.getString("turma"),
                    dados.getInt("rm")
                );
                
                alunos.add(aluno);
            }
            con.close();

            return alunos;
    }

    public void apagar(Aluno aluno) throws SQLException{
        var con = DriverManager.getConnection(URL, USER, PASS);
        var sql = "DELETE FROM alunos WHERE id = ?";
        var instrucao = con.prepareStatement(sql);
        instrucao.setInt(1, aluno.id());
        instrucao.executeUpdate();
        con.close();
    }
}
