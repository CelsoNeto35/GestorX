package br.com.gestorx.api.service;

import br.com.gestorx.api.model.Cliente;
import br.com.gestorx.api.repository.ClienteRepository;
import br.com.gestorx.api.Dto.ClienteDto;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClienteService {
@Autowired
    private ClienteRepository clienteRepository;

    // Lista todos os colaboradores convertidos para DTO
    public List<ClienteDto> listaCliente() {
        List<Cliente> clientes = clienteRepository.findAll();
        List<ClienteDto> clienteDtos = new ArrayList<>();

        for (Cliente cliente : clientes) {
            ClienteDto dto = new ClienteDto();
        dto.setId(cliente.getId());
        dto.setNomeCompletoRazaoSocial(cliente.getNomeCompletoRazaoSocial());
        dto.setNomeFantasia(cliente.getNomeFantasia());
        dto.setCnpjCpf(cliente.getCnpjCpf());
        dto.setRgInscricaoEstadual(cliente.getRgInscricaoEstadual());
        dto.setDataNascimentoCadastro(cliente.getDataNascimentoCadastro());
        dto.setTelefone(cliente.getTelefone());
        dto.setEmail(cliente.getEmail());
        dto.setCep(cliente.getCep());
        dto.setRuaLogradouro(cliente.getRuaLogradouro());
        dto.setNumero(cliente.getNumero());
        dto.setComplemento(cliente.getComplemento());
        dto.setBairro(cliente.getBairro());
        dto.setCidade(cliente.getCidade());
        dto.setEstado(cliente.getEstado());
        dto.setPais(cliente.getPais());

            clienteDtos.add(dto);
        }

        return clienteDtos;
    }

    // Cadastra colaborador validando ID, CPF e Email
    public boolean cadastrarCliente(ClienteDto cadastro) {

        // Verifica se ID já existe (opcional para atualizações)
        if (cadastro.getId() != null) {
            Optional<Cliente> optionalCliente = clienteRepository.findById(cadastro.getId());
            if (optionalCliente.isPresent()) {
                return false;
            }
        }
        if (clienteRepository.findBycnpjCpf(cadastro.getCnpjCpf()).isPresent()) {
            return false; 
        }
        if (clienteRepository.findByEmail(cadastro.getEmail()).isPresent()) {
            return false; 
        }

        // Cria novo colaborador
    Cliente cliente = new Cliente();

        cliente.setNomeCompletoRazaoSocial(cadastro.getNomeCompletoRazaoSocial());
        cliente.setNomeFantasia(cadastro.getNomeFantasia());
        cliente.setCnpjCpf(cadastro.getCnpjCpf());
        cliente.setRgInscricaoEstadual(cadastro.getRgInscricaoEstadual());
        cliente.setDataNascimentoCadastro(cadastro.getDataNascimentoCadastro());
        cliente.setTelefone(cadastro.getTelefone());
        cliente.setEmail(cadastro.getEmail());
        cliente.setCep(cadastro.getCep());
        cliente.setRuaLogradouro(cadastro.getRuaLogradouro());
        cliente.setNumero(cadastro.getNumero());
        cliente.setComplemento(cadastro.getComplemento());
        cliente.setBairro(cadastro.getBairro());
        cliente.setCidade(cadastro.getCidade());
        cliente.setEstado(cadastro.getEstado());
        cliente.setPais(cadastro.getPais());

        clienteRepository.save(cliente);
        return true;

    }
    public boolean excluirCliente(Long id){
        System.out.println("id:" + id);
        Optional<Cliente> optionalCliente = clienteRepository.findById(id);
        if (!optionalCliente.isPresent()){
            return false;
        }
        clienteRepository.delete(optionalCliente.get());
        return true;
    }
    public ClienteDto buscarClientePorId(Long id) {
    Optional<Cliente> optionalCliente = clienteRepository.findById(id);
ClienteDto clienteDto = new ClienteDto();

if (optionalCliente.isPresent()) {
    Cliente cliente = optionalCliente.get();
    clienteDto.setId(cliente.getId());
    clienteDto.setNomeCompletoRazaoSocial(cliente.getNomeCompletoRazaoSocial());
    clienteDto.setNomeFantasia(cliente.getNomeFantasia());
    clienteDto.setCnpjCpf(cliente.getCnpjCpf());
    clienteDto.setRgInscricaoEstadual(cliente.getRgInscricaoEstadual());
    clienteDto.setDataNascimentoCadastro(cliente.getDataNascimentoCadastro());
    clienteDto.setTelefone(cliente.getTelefone());
    clienteDto.setEmail(cliente.getEmail());
    clienteDto.setCep(cliente.getCep());
    clienteDto.setRuaLogradouro(cliente.getRuaLogradouro());
    clienteDto.setNumero(cliente.getNumero());
    clienteDto.setComplemento(cliente.getComplemento());
    clienteDto.setBairro(cliente.getBairro());
    clienteDto.setCidade(cliente.getCidade());
    clienteDto.setEstado(cliente.getEstado());
    clienteDto.setPais(cliente.getPais());
} else {
    clienteDto.setId(0L); // Ou null, se preferir
}

return clienteDto;

}
    public boolean atualizarCliente(Long id, ClienteDto dados) {
    Optional<Cliente> optionalCliente = clienteRepository.findById(id);

    if (!optionalCliente.isPresent()) {
        return false; // Cliente não encontrado
    }

    Cliente cliente = optionalCliente.get();
    // Não altere o ID, pois é a chave primária
    cliente.setNomeCompletoRazaoSocial(dados.getNomeCompletoRazaoSocial());
    cliente.setNomeFantasia(dados.getNomeFantasia());
    cliente.setCnpjCpf(dados.getCnpjCpf());
    cliente.setRgInscricaoEstadual(dados.getRgInscricaoEstadual());
    cliente.setDataNascimentoCadastro(dados.getDataNascimentoCadastro());
    cliente.setTelefone(dados.getTelefone());
    cliente.setEmail(dados.getEmail());
    cliente.setCep(dados.getCep());
    cliente.setRuaLogradouro(dados.getRuaLogradouro());
    cliente.setNumero(dados.getNumero());
    cliente.setComplemento(dados.getComplemento());
    cliente.setBairro(dados.getBairro());
    cliente.setCidade(dados.getCidade());
    cliente.setEstado(dados.getEstado());
    cliente.setPais(dados.getPais());

    clienteRepository.save(cliente);
    return true;
}
}
