package br.com.core.model;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import org.intellij.lang.annotations.Pattern;
import org.jetbrains.annotations.NotNull;

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

    @NonNull
    private Long id;

    @NotNull
    @Pattern("([0-9]{2}[\\.]?[0-9]{3}[\\.]?[0-9]{3}[\\/]?[0-9]{4}[-]?[0-9]{2})|([0-9]{3}[\\.]?[0-9]{3}[\\.]?[0-9]{3}[-]?[0-9]{2})\n")
    private String cpf;


    private String email;
    private int idade;

}
