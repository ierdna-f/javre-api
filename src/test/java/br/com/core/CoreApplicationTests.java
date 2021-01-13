package br.com.core;

import br.com.core.model.Client;
import br.com.core.service.impl.ClientServiceImpl;
import junit.framework.TestCase;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class CoreApplicationTests extends TestCase {

    @Autowired
    private ClientServiceImpl service;
    private final String token = "G6943LMReKj_kqdAVrAiPbpRloAfE1fqp0eVAJ-IChQcV-kv3gW-gBAzWztBEdFY";

    private Client c1 = new Client(1L, "321.165.400-38", "teste-alelo@gmail.com", 20);
    private Client c2 = new Client(2L, "600.681.110-37", "teste@gmail.com", 34);
    private Client c3 = new Client(3L, "231.080.890-30", "teste@alelo.com", 99);

    /*
        #####       IMPORTANTE      #####
        ATENÇÃO, COMO É UM AMBIENTE DE TESTES EM MEMÓRIA É PRECISO CARREGAR O MÉTODO TESTADDCLIENT()
        ANTES DE QUALQUER MÉTODO, PARA QUE INSIRA CLIENTS VÁLIDOS EM MEMÓRIA PARA QUE APÓS ISSO SEJAM TESTADOS
     */


    /*
        Adicionando 3 clients mockados como request de uma api externa
     */
    @Test
    public void testAddClient() {
        assertEquals(service.add(c1).getStatusCode(), HttpStatus.OK);
        assertEquals(service.add(c2).getStatusCode(), HttpStatus.OK);
        assertEquals(service.add(c3).getStatusCode(), HttpStatus.OK);
    }

    /*
        Forçando a inserção de usuários que já existem em memória para que seja bloqueado
     */
    @Test
    public void testAddClientForDuplicateClients() {
        c1 = new Client(1L, "138.196.000-60", "teste-alelo@gmail.com", 20);
        c2 = new Client(2L, "738.398.320-39", "teste@gmail.com", 34);
        c3 = new Client(3L, "313.596.630-53", "teste@alelo.com", 99);
        assertEquals(service.add(c1).getStatusCode(), HttpStatus.FORBIDDEN);
        assertEquals(service.add(c2).getStatusCode(), HttpStatus.FORBIDDEN);
        assertEquals(service.add(c3).getStatusCode(), HttpStatus.FORBIDDEN);
    }

    /*
        Testando o método que recupera apenas 1 cliente
     */
    @Test
    public void testGetClient() {
        Client client = service.findById(4L);
        assertEquals("650.531.550-03", client.getCpf());
        assertEquals(50, client.getIdade());
        assertEquals("new-teste@alelo.com", client.getEmail());
    }

    /*
        Verificando que realmente possuem 2 clientes na lista após adicionarmos 3 no total e deletarmos 1
        no método testDeleteClient()
     */
    @Test
    public void testGetClients() {
        assertEquals(3, service.getClients().size());
        assertEquals(2L, service.getClients().get(0).getId(), 0);
        assertEquals(3L, service.getClients().get(1).getId(), 0);
        assertEquals(4L, service.getClients().get(2).getId(), 0);
    }

    /*
        Verificando que o cliente existe, depois deletando esse cliente e verificando que ele n existe mais
     */
    @Test
    public void testDeleteClient() {
        assertEquals(1L, service.getClients().get(0).getId(), 0);
        service.deleteClient(1L, token);
        assertEquals(2L, service.getClients().get(0).getId(), 0);
    }


    /*
        Test para edição de clientes, no começo do método vou verificar todas as infos do cliente para que estejam corretas
     */
    @Test
    public void testEditClient() {
        assertEquals(2L, service.getClients().get(0).getId(), 0);
        assertEquals("600.681.110-37", service.getClients().get(0).getCpf());
        assertEquals("teste@gmail.com", service.getClients().get(0).getEmail());
        assertEquals(34, service.getClients().get(0).getIdade());

        //Adicionando cliente correto e cliente que não existe e verificando que os dois retornam os status corretos
        Client client = new Client(2L, "499.164.260-40", "teste@gmail.com", 20);
        assertEquals(service.updateClient(client).getStatusCode(), HttpStatus.OK);
        assertEquals("499.164.260-40", service.getClients().get(0).getCpf());

        Client clientFail = new Client();
        clientFail.setId(4L);
        assertEquals(service.updateClient(clientFail).getStatusCode(), HttpStatus.NOT_FOUND);

        //Verificando tratativa de exception no método
        assertEquals(service.updateClient(null).getStatusCode(), HttpStatus.INTERNAL_SERVER_ERROR);

    }

    @Test
    public void testEditOrCreatClient() {
        assertEquals(3L, service.getClients().get(1).getId(), 0);
        assertEquals("231.080.890-30", service.getClients().get(1).getCpf());
        assertEquals("teste@alelo.com", service.getClients().get(1).getEmail());
        assertEquals(99, service.getClients().get(1).getIdade());

        //Como o cliente já existe ele será editado do objeto em memória
        Client client = new Client(3L, "298.167.060-32", "teste-alelo@gmail.com", 20);
        assertEquals(service.editOrCreat(client).getStatusCode(), HttpStatus.OK);
        assertEquals("298.167.060-32", service.getClients().get(1).getCpf());

        assertEquals(2, service.getClients().size());

        Client newClient = new Client(4L, "650.531.550-03", "new-teste@alelo.com", 50);
        //Caso o cliente a ser editado não exista ele será criado
        assertEquals(service.editOrCreat(newClient).getStatusCode(), HttpStatus.OK);
        assertEquals(4L, service.getClients().get(2).getId(), 0);
        assertEquals("650.531.550-03", service.getClients().get(2).getCpf());
        assertEquals(3, service.getClients().size());

    }


}

