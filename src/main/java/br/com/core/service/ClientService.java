package br.com.core.service;

import br.com.core.model.Client;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ClientService {

    ResponseEntity add(Client c);

    List<Client> getClients();

    Client findById(Long id);

    ResponseEntity updateClient(Client client);

    ResponseEntity deleteClient(Long id, String auth);

    ResponseEntity editOrCreat(Client c);

    ResponseEntity cleanLista(String auth);

}
