package br.com.gestorx.api.service;

import br.com.gestorx.api.model.Fornecedor;
import br.com.gestorx.api.repository.FornecedorRepository;
import br.com.gestorx.api.Dto.FornecedorDto;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FornecedorService {

    @Autowired
    private FornecedorRepository fornecedorRepository;

    // Lista todos os fornecedores convertidos para DTO
    public List<FornecedorDto> listaFornecedor() {
        List<Fornecedor> fornecedores = fornecedorRepository.findAll();
        List<FornecedorDto> fornecedorDtos = new ArrayList<>();

        for (Fornecedor fornecedor : fornecedores) {
            FornecedorDto dto = new FornecedorDto();

            dto.setId(fornecedor.getId());
            dto.setRazaoSocial(fornecedor.getRazaoSocial());
            dto.setNomeFantasia(fornecedor.getNomeFantasia());
            dto.setCnpj(fornecedor.getCnpj());
            dto.setInsEstadual(fornecedor.getInsEstadual());
            dto.setDataDeCadastro(fornecedor.getDataDeCadastro());
            dto.setTelefone(fornecedor.getTelefone());
            dto.setEmail(fornecedor.getEmail());
            dto.setCep(fornecedor.getCep());
            dto.setRuaLogradouro(fornecedor.getRuaLogradouro());
            dto.setNumero(fornecedor.getNumero());
            dto.setComplemento(fornecedor.getComplemento());
            dto.setBairro(fornecedor.getBairro());
            dto.setCidade(fornecedor.getCidade());
            dto.setEstado(fornecedor.getEstado());
            dto.setPais(fornecedor.getPais());

            fornecedorDtos.add(dto);
        }

        return fornecedorDtos;
    }

    // Cadastra fornecedor validando apenas CNPJ
    public boolean cadastrarFornecedor(FornecedorDto cadastro) {

        if (fornecedorRepository.findByCnpj(cadastro.getCnpj()).isPresent()) {
            return false; // Já existe fornecedor com esse CNPJ
        }

        Fornecedor fornecedor = new Fornecedor();

        fornecedor.setRazaoSocial(cadastro.getRazaoSocial());
        fornecedor.setNomeFantasia(cadastro.getNomeFantasia());
        fornecedor.setCnpj(cadastro.getCnpj());
        fornecedor.setInsEstadual(cadastro.getInsEstadual());
        fornecedor.setDataDeCadastro(cadastro.getDataDeCadastro());
        fornecedor.setTelefone(cadastro.getTelefone());
        fornecedor.setEmail(cadastro.getEmail());
        fornecedor.setCep(cadastro.getCep());
        fornecedor.setRuaLogradouro(cadastro.getRuaLogradouro());
        fornecedor.setNumero(cadastro.getNumero());
        fornecedor.setComplemento(cadastro.getComplemento());
        fornecedor.setBairro(cadastro.getBairro());
        fornecedor.setCidade(cadastro.getCidade());
        fornecedor.setEstado(cadastro.getEstado());
        fornecedor.setPais(cadastro.getPais());

        fornecedorRepository.save(fornecedor);
        return true;
    }

    // Excluir fornecedor
    public boolean excluirFornecedor(Long id) {
        Optional<Fornecedor> optionalFornecedor = fornecedorRepository.findById(id);

        if (!optionalFornecedor.isPresent()) {
            return false;
        }

        fornecedorRepository.delete(optionalFornecedor.get());
        return true;
    }

    // Buscar fornecedor por ID
    public FornecedorDto buscarFornecedorPorId(Long id) {
        Optional<Fornecedor> optionalFornecedor = fornecedorRepository.findById(id);
        FornecedorDto fornecedorDto = new FornecedorDto();

        if (optionalFornecedor.isPresent()) {
            Fornecedor fornecedor = optionalFornecedor.get();

            fornecedorDto.setId(fornecedor.getId());
            fornecedorDto.setRazaoSocial(fornecedor.getRazaoSocial());
            fornecedorDto.setNomeFantasia(fornecedor.getNomeFantasia());
            fornecedorDto.setCnpj(fornecedor.getCnpj());
            fornecedorDto.setInsEstadual(fornecedor.getInsEstadual());
            fornecedorDto.setDataDeCadastro(fornecedor.getDataDeCadastro());
            fornecedorDto.setTelefone(fornecedor.getTelefone());
            fornecedorDto.setEmail(fornecedor.getEmail());
            fornecedorDto.setCep(fornecedor.getCep());
            fornecedorDto.setRuaLogradouro(fornecedor.getRuaLogradouro());
            fornecedorDto.setNumero(fornecedor.getNumero());
            fornecedorDto.setComplemento(fornecedor.getComplemento());
            fornecedorDto.setBairro(fornecedor.getBairro());
            fornecedorDto.setCidade(fornecedor.getCidade());
            fornecedorDto.setEstado(fornecedor.getEstado());
            fornecedorDto.setPais(fornecedor.getPais());

        } else {
            fornecedorDto.setId(0L);
        }

        return fornecedorDto;
    }

    // Buscar fornecedor por CNPJ
    public FornecedorDto buscarFornecedorPorCnpj(String cnpj) {
        Optional<Fornecedor> optionalFornecedor = fornecedorRepository.findByCnpj(cnpj);
        FornecedorDto dto = new FornecedorDto();

        if (optionalFornecedor.isPresent()) {
            Fornecedor fornecedor = optionalFornecedor.get();

            dto.setId(fornecedor.getId());
            dto.setRazaoSocial(fornecedor.getRazaoSocial());
            dto.setNomeFantasia(fornecedor.getNomeFantasia());
            dto.setCnpj(fornecedor.getCnpj());
            dto.setInsEstadual(fornecedor.getInsEstadual());
            dto.setDataDeCadastro(fornecedor.getDataDeCadastro());
            dto.setTelefone(fornecedor.getTelefone());
            dto.setEmail(fornecedor.getEmail());
            dto.setCep(fornecedor.getCep());
            dto.setRuaLogradouro(fornecedor.getRuaLogradouro());
            dto.setNumero(fornecedor.getNumero());
            dto.setComplemento(fornecedor.getComplemento());
            dto.setBairro(fornecedor.getBairro());
            dto.setCidade(fornecedor.getCidade());
            dto.setEstado(fornecedor.getEstado());
            dto.setPais(fornecedor.getPais());
        }

        return dto;
    }

    // Atualizar fornecedor
    public boolean atualizarFornecedor(Long id, FornecedorDto dados) {
        Optional<Fornecedor> optionalFornecedor = fornecedorRepository.findById(id);

        if (!optionalFornecedor.isPresent()) {
            return false;
        }

        Fornecedor fornecedor = optionalFornecedor.get();

        fornecedor.setRazaoSocial(dados.getRazaoSocial());
        fornecedor.setNomeFantasia(dados.getNomeFantasia());
        fornecedor.setCnpj(dados.getCnpj());
        fornecedor.setInsEstadual(dados.getInsEstadual());
        fornecedor.setDataDeCadastro(dados.getDataDeCadastro());
        fornecedor.setTelefone(dados.getTelefone());
        fornecedor.setEmail(dados.getEmail());
        fornecedor.setCep(dados.getCep());
        fornecedor.setRuaLogradouro(dados.getRuaLogradouro());
        fornecedor.setNumero(dados.getNumero());
        fornecedor.setComplemento(dados.getComplemento());
        fornecedor.setBairro(dados.getBairro());
        fornecedor.setCidade(dados.getCidade());
        fornecedor.setEstado(dados.getEstado());
        fornecedor.setPais(dados.getPais());

        fornecedorRepository.save(fornecedor);
        return true;
    }
}
