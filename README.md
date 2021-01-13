# javre-api
Simple api in java for costumer registration;

Tecnologias utilizadas Spring(Spring web, Spring Boot Dev Tools e Lombok) Swagger, RestAPI e por fim JUnit;

Criei uma lógica para criação edição deleção e listagem de clientes para qualquer determinado tipo de 'empresa', fiz algumas validações de erro de usuário, 
utilizei conceitos de interface em java, de exception, tentei ser o mais explicativo possível com nomes de métodos e comentários, procurei utilizar 
boas práticas de programção e ser o mais objetivo possível.

Como o intuito eram testes feitos em memória sem um repositório, desenhei os testes de forma ordenada, onde inicialmente
é necessário criar clientes em memória rodando o testAddClient() {Pode ser alterado o objeto a ser criado respeitando as regras de id e CPF}
após a criação dos objetos em memória todos os outros testes rodam normalmente, como era pra ser testes mockados eu implementei uma ordem de execução
alfabética, nesse caso se a ordem não for respeitada acarretará em erro nos testes, pois os valores estão todos mockados.

Quaisquer dúvidas meu e-mail ou linkedim estão na documentação do Swagger