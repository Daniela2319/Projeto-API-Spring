package dio.digital.gof.Service;

import dio.digital.gof.model.Cliente;

/**
 * Interface que define o padrão <b>Strategy</b> no domínio de cliente. Com isso, se
 * necessário, podemos ter multiplas implementações dessa mesma interface.
 *
 * @author Falvorjr aluna Daniela
 */


public interface ClienteService {

    Iterable<Cliente> buscaTodos();

    Cliente buscaPorId(Long id);

    void inserir(Cliente cliente);

    void atualizar(Long id, Cliente cliente);

    void deletar(Long id);
}
