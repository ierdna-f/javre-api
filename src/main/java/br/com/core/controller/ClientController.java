package br.com.core.controller;

import br.com.core.model.Client;
import br.com.core.service.impl.ClientServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import junit.framework.TestCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value="/api/cliente")
@Api(value = "Cliente API")
@CrossOrigin(origins = "http://localhost:8080")
public class ClientController extends TestCase {

    @Autowired
    private ClientServiceImpl clientServiceImpl;

    @GetMapping("/listar")
    @ApiOperation(value="Retorna todos os clientes")
    public List<Client> getClients() {
        return clientServiceImpl.getClients();
    }

    @GetMapping("/listar/{id}")
    @ApiOperation(value="Retorna um cliente específico")
    public ResponseEntity getClient(@PathVariable Long id) {
        return clientServiceImpl.findClient(id);
    }

    @PostMapping("/adicionar")
    @ApiOperation(value="Adiciona novos clientes")
    public ResponseEntity addClient(@Valid @RequestBody Client client) {
           return clientServiceImpl.add(client);
    }

    @DeleteMapping("/delete/{id}")
    @ApiOperation(value="Deleta um cliente")
    public ResponseEntity deleteClient(@Valid @RequestParam String token, @PathVariable Long id) {
        return clientServiceImpl.deleteClient(id, token);
    }

    @DeleteMapping("/delete/allClients")
    @ApiOperation(value="Deleta todos os clientes")
    public ResponseEntity deleteClients(@RequestParam String token) {
        return clientServiceImpl.cleanLista(token);
    }

    @PatchMapping("/editar")
    @ApiOperation(value="Edita um cliente")
    public ResponseEntity editClient(@Valid @RequestBody Client client) {
        return clientServiceImpl.updateClient(client);
    }

    @PutMapping("/editar")
    @ApiOperation(value="Edita ou cria um novo cliente caso ele não exista")
    public ResponseEntity editOrCreatClient(@Valid @RequestBody Client client) {
        return clientServiceImpl.editOrCreat(client);
    }


}
