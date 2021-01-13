package br.com.core.model;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class Client {

    public Client() {
    }

    public Client(Long id, String cpf, String email, int idade) {
        this.id = id;
        this.cpf = cpf;
        this.email = email;
        this.idade = idade;
    }

    private Long id;
    private String cpf;
    private String email;
    private int idade;

}
