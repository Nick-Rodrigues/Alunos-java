package com.example;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;

public class PrimaryController {

    // Atributos
    @FXML
    ListView<Aluno> lstAlunos;
    @FXML
    TextField txtNome;
    @FXML
    TextField txtTurma;
    @FXML
    TextField txtRm;
    @FXML
    RadioButton rdbCrescente;
    @FXML
    RadioButton rdbDecrescente;

    // Collections - ArrayList
    // private String[] nomes; // <- arrays primitivos
    // <String> - generics (como são chamados)
    private ArrayList<Aluno> alunos = new ArrayList<>(); // Chamando o construtor para criar um arraylist vazio

    // Metodos
    public void adicionarAluno() {
        var aluno = new Aluno(txtNome.getText(), txtTurma.getText(), Integer.valueOf(txtRm.getText()));
        alunos.add(aluno);

        //Conectar com o banco
        final String HOST = "auth-db719.hstgr.io";
        final String PORT = "3306";
        final String DATABASE = "u553405907_fiap";
        final String USER = "u553405907_fiap";
        final String PASS = "u553405907_FIAP";
        final String URL = "jdbc:mysql://" + HOST + ":" + PORT + "/" + DATABASE; // JDBC URL
        // jdbc:oracle:thin:@oracle.fiap.com.br:1521:ORCL -- Endereço oracle fiap

        try {
            var con = DriverManager.getConnection(URL, USER, PASS);
            //statement
            // var instrucao = con.createStatement();
            // var sql = "INSERT INTO alunos (nome, turma, rm) VALUES ('"+ aluno.nome() + "', '"+ aluno.turma() +"', "+ aluno.rm() +" )";
            // instrucao.execute(sql);
            var sql = "INSERT INTO alunos (nome, turma, rm) VALUES (?,?,? )";
            var instrucao = con.prepareStatement(sql);
            instrucao.setString(1, aluno.nome());
            instrucao.setString(2, aluno.turma());
            instrucao.setInt(3, aluno.rm());
            instrucao.executeUpdate();

            Alert alert = new Alert(AlertType.INFORMATION, "Cadastrado com sucesso");
            alert.show();

            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        //Executar o insert

        //Desconectar desse banco


        // alunos.add(txtNome.getText());
        txtNome.clear();
        txtTurma.clear();
        txtRm.clear();
        mostrarAluno();
    }

    public void mostrarAluno() {
        final String HOST = "auth-db719.hstgr.io";
        final String PORT = "3306";
        final String DATABASE = "u553405907_fiap";
        final String USER = "u553405907_fiap";
        final String PASS = "u553405907_FIAP";
        final String URL = "jdbc:mysql://" + HOST + ":" + PORT + "/" + DATABASE; // JDBC URL

        try {
            var con = DriverManager.getConnection(URL, USER, PASS);
            
            var sql = "SELECT * FROM alunos ORDER BY nome";
            var instrucao = con.prepareStatement(sql);
            var dados = instrucao.executeQuery();

            while(dados.next()){
                var aluno = new Aluno(
                    dados.getString("nome"),
                    dados.getString("turma"),
                    dados.getInt("rm")
                );
                
                lstAlunos.getItems().add(aluno);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void ordenar() {

        // SOLID
        if (rdbCrescente.isSelected()) {
            // lambda axpression (java) - arrow function (js)
            // Ordem alfabética A - Z
            alunos.sort((a1, a2) -> a1.nome().compareToIgnoreCase(a2.nome()));
        }
        if (rdbDecrescente.isSelected()) {
            // Ordem alfabética Z - A
            alunos.sort((a1, a2) -> a2.nome().compareToIgnoreCase(a1.nome()));
        }
    }

    public void apagarAluno() {
        // Pegar o aluno da Selecionado na lista
        var aluno = lstAlunos.getSelectionModel().getSelectedItem();
        
        // Apagar o aluno do array
        alunos.remove(aluno);
        
        // Atualizar a tela
        mostrarAluno();

        // Mostrar um alerta na tela
        Alert alert = new Alert(AlertType.INFORMATION, "Aluno(a) " + aluno + " apagado com sucesso!");
        alert.show();
    }

}
