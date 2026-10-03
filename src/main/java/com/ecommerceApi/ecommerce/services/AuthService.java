package com.ecommerceApi.ecommerce.services;

import com.ecommerceApi.ecommerce.exceptions.usuario.CredenciaisInvalidasException;
import com.ecommerceApi.ecommerce.exceptions.usuario.EmailJaCadastradoException;
import com.ecommerceApi.ecommerce.models.role.Role;
import com.ecommerceApi.ecommerce.models.usuario.LoginResponseDTO;
import com.ecommerceApi.ecommerce.models.usuario.Usuario;
import com.ecommerceApi.ecommerce.models.usuario.UsuarioCadastroDto;
import com.ecommerceApi.ecommerce.models.usuario.UsuarioLoginDTO;
import com.ecommerceApi.ecommerce.repositories.UsuarioRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AuthService {
    private final UsuarioRepository repository;
    private final BCryptPasswordEncoder bcrypt;
    private final TokenService tokenService;

    public void cadastrarUsuario(UsuarioCadastroDto dto){
        if (repository.existsByEmail(dto.email())) {
            throw new EmailJaCadastradoException("Email já cadastrado");
        }
        Usuario usuario = new Usuario(dto);
        String senhaHash = bcrypt.encode(dto.senha());
        usuario.setSenhaHash(senhaHash);
        usuario.setRole(Role.USER);
        repository.save(usuario);
    }

    public LoginResponseDTO login(UsuarioLoginDTO dto){
        Usuario usuario = repository.getUsuarioByEmail(dto.email());
        if (usuario == null) {
            throw new CredenciaisInvalidasException("Usuário não encontrado.");
        }
        if (!bcrypt.matches(dto.senha(), usuario.getSenhaHash())) {
            throw new CredenciaisInvalidasException("Senha inválida.");
        }
        String token = tokenService.gerarToken(usuario);
        return new LoginResponseDTO(token);
    }

    public Usuario getUsuarioAutenticado() {

        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        String email = authentication.getName();

        return repository.findByEmail(email)
                .orElseThrow(() ->
                        new CredenciaisInvalidasException("Usuário não encontrado"));
    }
}
