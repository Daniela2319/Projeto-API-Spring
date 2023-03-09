package dio.digital.gof.Service.impl;

import dio.digital.gof.Service.ClienteService;
import dio.digital.gof.Service.ViaCepService;
import dio.digital.gof.model.Cliente;
import dio.digital.gof.model.ClienteRepository;
import dio.digital.gof.model.Endereco;
import dio.digital.gof.model.EnderecoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Implementação da <b>Strategy</b> {@link ClienteService}, a qual pode ser
 * injetada pelo Spring (via {@link "Autowired"}). como isso, como essa classe é
 * {@link Service}, ela será tratada como um <b>Singleton</b>.
 *
 * @author Falvojr aluna Daniela
 */

@Service
public class ClienteServiceImpl implements ClienteService {

    //Singleton: Injetar os componentes do Spring com @Autowired.
    @Autowired
    private ClienteRepository clienteRepository;
    @Autowired
    private EnderecoRepository enderecoRepository;
    @Autowired
    private ViaCepService viaCepService;

    //Strategy: Implementar os métodos definidos na interface.
    // facade: Abstrair integrações com subsistemas, provendo uma interface simples.

    @Override
    public Iterable<Cliente> buscaTodos() {
        // Busca todos os Clientes
        return clienteRepository.findAll();
    }

    @Override
    public Cliente buscaPorId(Long id) {
        //Busca Cliente por Id.
        Optional<Cliente> cliente = clienteRepository.findById(id);
        return cliente.get();
    }

    @Override
    public void inserir(Cliente cliente) {
        //verificar se o Endereco do Cliente já existe (pelo CEP).
        salvarClienteComCep(cliente);
    }

    @Override
    public void atualizar(Long id, Cliente cliente) {
        // Buscar Cliente por Id, caso exista:
        Optional<Cliente> clienteBd = clienteRepository.findById(id);
        if (clienteBd.isPresent()){
            salvarClienteComCep(cliente);
        }
    }

    @Override
    public void deletar(Long id) {
        // Deletar Cliente por Id
        clienteRepository.deleteById(id);
    }

    private void salvarClienteComCep(Cliente cliente){
        //verificar se o Endereco do Cliente já existe (pelo CEP).
        String cep = cliente.getEndereco().getCep();
        Endereco endereco = enderecoRepository.findById(cep).orElseGet(()->{
            //Caso não exista, integrar com o ViaCEP e persistir o retorno.

            Endereco novoEndereco = viaCepService.consultaCep(cep);
            enderecoRepository.save(novoEndereco);
            return novoEndereco;
        });
        cliente.setEndereco(endereco);
        //Inserir Cliente, vinculando o Endereco (novo ou existente).
        clienteRepository.save(cliente);
    }
}
