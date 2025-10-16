/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.gestorx.api.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import br.com.gestorx.api.Dto.LoginDto;
import br.com.gestorx.api.model.Usuario;
import br.com.gestorx.api.Dto.UsuarioDto;
import br.com.gestorx.api.repository.UsuarioRepository;
import org.springframework.stereotype.Service;

/**
 *
 * @author cneto
 */
@Service
public class UsuarioService {
    @Autowired
    private UsuarioRepository repository;

    /**
     * Valida login do usuário com base em e-mail e senha.
     */
    public boolean validarLogin(LoginDto loginDto) {
        Optional<Usuario> optionalUsuario = repository.findByEmail(loginDto.getEmail());

        if (!optionalUsuario.isPresent()) {
            return false;
        }

        Usuario usuario = optionalUsuario.get();
        return usuario.getSenha().equals(loginDto.getSenha());
    }

    /**
     * Retorna lista de usuários convertida para DTO.
     */
    public List<UsuarioDto> listarUsuarios() {
        List<Usuario> usuarios = repository.findAll();
        List<UsuarioDto> usuariosDto = new ArrayList<>();

        for (Usuario usuario : usuarios) {
            UsuarioDto dto = new UsuarioDto();
            dto.setId(usuario.getId());
            dto.setNome(usuario.getNome());
            dto.setEmail(usuario.getEmail());
            usuariosDto.add(dto);
        }

        return usuariosDto;
    }
     public boolean cadastrarUsuario(UsuarioDto cadastro){
        
        Optional<Usuario> optionalUsuario = repository.findByEmail(cadastro.getEmail());
        
        if (optionalUsuario.isPresent()){
            return false;
        }
        
        Usuario usuario = new Usuario();
        usuario.setNome(cadastro.getNome());
        usuario.setEmail(cadastro.getEmail());
        usuario.setSenha(cadastro.getSenha());
        
        repository.save(usuario);
        
        return true;
    }
    
    public boolean excluirUsuario(Long id){
        
        System.out.println("id:" + id);
        
        Optional<Usuario> optionalUsuario = repository.findById(id);
        
        if (!optionalUsuario.isPresent()){
            return false;
        }
        
        repository.delete(optionalUsuario.get());
        
        return true;
        
    }
    
    public UsuarioDto obterUsuario(Long id){
        
        Optional<Usuario> optionalUsuario = repository.findById(id);
        
        UsuarioDto usuarioDto = new UsuarioDto();
        
        if (!optionalUsuario.isPresent()){            
            usuarioDto.setId(0L);
            return usuarioDto;
        }

        usuarioDto.setId(optionalUsuario.get().getId());                
        usuarioDto.setNome(optionalUsuario.get().getNome());
        usuarioDto.setEmail(optionalUsuario.get().getEmail());        
        
        return usuarioDto;
    }
    
    public boolean atualizarUsuario(Long id, UsuarioDto dados){
        
         Optional<Usuario> optionalUsuario = repository.findById(id);
         
         if(!optionalUsuario.isPresent()){
             return false;
         }
         
         Usuario usuario = optionalUsuario.get();
         usuario.setId(dados.getId());
         usuario.setNome(dados.getNome());
         usuario.setEmail(dados.getEmail());
         usuario.setSenha(dados.getSenha());
         
         
         repository.save(usuario);
                 
        return true;
    }
    
}
