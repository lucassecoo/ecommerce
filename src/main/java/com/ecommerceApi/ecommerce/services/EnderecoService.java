package com.ecommerceApi.ecommerce.services;

import com.ecommerceApi.ecommerce.exceptions.endereco.EnderecoNaoEncontradoException;
import com.ecommerceApi.ecommerce.mappers.EnderecoMapper;
import com.ecommerceApi.ecommerce.models.endereco.AtualizarEnderecoDTO;
import com.ecommerceApi.ecommerce.models.endereco.CadastrarEnderecoDTO;
import com.ecommerceApi.ecommerce.models.endereco.Endereco;
import com.ecommerceApi.ecommerce.models.endereco.EnderecoDTO;
import com.ecommerceApi.ecommerce.models.usuario.Usuario;
import com.ecommerceApi.ecommerce.repositories.EnderecoRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@AllArgsConstructor
@Service
public class EnderecoService {
    private final EnderecoRepository enderecoRepository;
    private final EnderecoMapper enderecoMapper;
    private final AuthService authService;

    @Transactional
    public EnderecoDTO criarEndereco(CadastrarEnderecoDTO cadastrarEnderecoDTO) {
        Usuario usuario = authService.getUsuarioAutenticado();
        Endereco endereco = enderecoMapper.toEntity(cadastrarEnderecoDTO);
        endereco.setUsuario(usuario);
        definirEnderecoPrincipal(usuario, endereco, cadastrarEnderecoDTO.getPrincipal());
        endereco.setCep(
                endereco.getCep().replaceAll("\\D", "")
        );
        enderecoRepository.save(endereco);
        return enderecoMapper.toDTO(endereco);
    }

    @Transactional(readOnly = true)
    public Page<EnderecoDTO> buscarTodosEnderecos(Pageable pageable) {
        Usuario usuario = authService.getUsuarioAutenticado();

        return enderecoRepository.findAllByUsuarioIdOrderByPrincipalDescIdAsc(usuario.getId(), pageable)
                .map(enderecoMapper::toDTO);
    }

    @Transactional(readOnly = true)
    public EnderecoDTO buscarEnderecoPorId(Long id) {
        Endereco endereco = buscarEnderecoDoUsuario(id);
        return enderecoMapper.toDTO(endereco);
    }

    @Transactional
    public EnderecoDTO atualizarEndereco(AtualizarEnderecoDTO atualizarEnderecoDTO, Long id) {
        Endereco endereco = buscarEnderecoDoUsuario(id);
        if (Boolean.TRUE.equals(atualizarEnderecoDTO.getPrincipal())) {
            removerEnderecoPrincipalDoUsuario();
        }
        enderecoMapper.updateEntityFromDto(atualizarEnderecoDTO, endereco);

        return enderecoMapper.toDTO(endereco);
    }

    @Transactional
    public void deletarEndereco(Long id) {
        Endereco endereco = buscarEnderecoDoUsuario(id);
        boolean eraPrincipal = endereco.getPrincipal();

        enderecoRepository.delete(endereco);

        if (eraPrincipal) {
            Usuario usuario = authService.getUsuarioAutenticado();
            enderecoRepository
                    .findFirstByUsuarioId(usuario.getId())
                    .ifPresent(e -> e.setPrincipal(true));
        }
    }

    @Transactional
    public void atualizarEnderecoPrincipal(Long id) {
        Endereco endereco = buscarEnderecoDoUsuario(id);
        removerEnderecoPrincipalDoUsuario();
        endereco.setPrincipal(true);
    }

    public Endereco buscarEnderecoEntityPorId(Long enderecoId){
        return buscarEnderecoDoUsuario(enderecoId);
    }

    private Endereco buscarEnderecoDoUsuario(Long enderecoId) {
        Usuario usuario = authService.getUsuarioAutenticado();

        return enderecoRepository
                .findByIdAndUsuarioId(enderecoId, usuario.getId())
                .orElseThrow(() -> new EnderecoNaoEncontradoException(
                        "Endereço não encontrado."
                ));
    }

    private void removerEnderecoPrincipalDoUsuario() {
        Usuario usuario = authService.getUsuarioAutenticado();

        usuario.getEnderecos()
                .forEach(e -> e.setPrincipal(false));
    }

    private void definirEnderecoPrincipal(
            Usuario usuario,
            Endereco endereco,
            Boolean principal) {

        if (usuario.getEnderecos().isEmpty()) {
            endereco.setPrincipal(true);
            return;
        }

        if (Boolean.TRUE.equals(principal)) {
            removerEnderecoPrincipalDoUsuario();
            endereco.setPrincipal(true);
        }
    }

}
