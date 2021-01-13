package br.com.core.service.impl;

import br.com.core.exception.ClassException;
import br.com.core.model.Client;
import br.com.core.service.ClientService;
import br.com.core.utils.Utils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.security.auth.login.CredentialException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ClientServiceImpl implements ClientService {
    private List<Client> lc;
    private final String token = "G6943LMReKj_kqdAVrAiPbpRloAfE1fqp0eVAJ-IChQcV-kv3gW-gBAzWztBEdFY";

    ClientServiceImpl() {
        lc = new ArrayList<>();
    }

    public ResponseEntity add(Client c) {
        if (findById(c.getId()) != null) {
            return new ResponseEntity("Cliente id: " + c.getId() + " já existe!", HttpStatus.FORBIDDEN);
        }
        try{
            if(Utils.isValidClient(c))
                lc.add(c);
        }catch (ClassException ex){
            return new ResponseEntity(ex.getMsg(), ex.getStatus());
        }
        return new ResponseEntity("Cliente criado com sucesso!", HttpStatus.OK);
    }


    public List<Client> getClients() {
        return this.lc;
    }

    public ResponseEntity findClient(Long id){
        Client c = findById(id);
        if(c != null)
            return new ResponseEntity(c,HttpStatus.OK);
        else
            return new ResponseEntity("Cliente id : " + id + " não encontrado." , HttpStatus.NOT_FOUND);
    }

    public Client findById(Long id) {
        for (Client c : this.getClients()) {
            if (c.getId().equals(id))
                return c;
        }
        return null;
    }

    public ResponseEntity updateClient(Client client) {
        try {
            for (int i = 0; i < this.lc.size(); i++) {
                if (this.lc.get(i).getId().equals(client.getId())) {
                    this.lc.set(i, client);
                    return new ResponseEntity(
                            "Cliente id: " + client.getId() + " editado com sucesso!",
                            HttpStatus.OK);
                }
            }

            return new ResponseEntity(
                    "Cliente id: " + client.getId() + " Não Encontrado ",
                    HttpStatus.NOT_FOUND);

        } catch (Exception e) {
            return new ResponseEntity(
                    "Erro ao editar cliente ",
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity deleteClient(Long id, String auth) {
        if (!auth.equals(this.token))
            return new ResponseEntity("Autenticação Inválida", HttpStatus.FORBIDDEN);
        if (this.lc.removeIf(c -> c.getId().equals(id)))
            return new ResponseEntity("Cliente deletado com sucesso!", HttpStatus.OK);
        else
            return new ResponseEntity("Nenhum cliente com id : " + id + " encontrado", HttpStatus.OK);
    }

    public ResponseEntity editOrCreat(Client c) {
        if (lc.stream().map(Client::getId).collect(Collectors.toList()).contains(c.getId())) {
            return updateClient(c);
        }
        lc.add(c);
        return new ResponseEntity("Cliente criado com sucesso!", HttpStatus.OK);
    }

    public ResponseEntity cleanLista(String auth) {
        if (!auth.equals(this.token))
            return new ResponseEntity("Autenticação Inválida", HttpStatus.FORBIDDEN);
        this.lc = new ArrayList<>();
        return new ResponseEntity("Todos os clientes foram deletados", HttpStatus.OK);
    }

}
