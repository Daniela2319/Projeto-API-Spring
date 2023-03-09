package dio.digital.gof.controller;

import dio.digital.gof.Service.ClienteService;
import dio.digital.gof.model.Cliente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 *
 * Esse {@link RestController} representa nossa <b>Facade</b>, pois abstrai toda a
 * complexidade de integrações (Banco da Dados H2 e API do ViaCEP) em uma
 * interface simples e coesa (API REST).
 *
 * @author Falvojr aluna Daniela
 */


@RestController
@RequestMapping("clientes")
public class ClienteRestController{

    @Autowired
    private ClienteService clienteService;

    @GetMapping
    public ResponseEntity<Iterable<Cliente>> buscaTodos(){
        return ResponseEntity.ok(clienteService.buscaTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Cliente> buscarPorId(@PathVariable Long id){
        return ResponseEntity.ok(clienteService.buscaPorId(id));
    }

    @PostMapping
    public ResponseEntity<Cliente> inserir(@RequestBody Cliente cliente){
        clienteService.inserir(cliente);
        return ResponseEntity.ok(cliente);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Cliente> atualizar(@PathVariable Long id, @RequestBody Cliente cliente){
        clienteService.atualizar(id, cliente);
        return ResponseEntity.ok(cliente);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleter(@PathVariable Long id){
        clienteService.deletar(id);
        return ResponseEntity.ok().build();
    }


}
