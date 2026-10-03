package com.ecommerceApi.ecommerce.services;

import com.ecommerceApi.ecommerce.Specification.ProdutoSpecification;
import com.ecommerceApi.ecommerce.exceptions.categoria.CategoriaDesconhecidaException;
import com.ecommerceApi.ecommerce.exceptions.produto.ProdutoNaoEncontradoException;
import com.ecommerceApi.ecommerce.exceptions.produto.QuantidadeInvalidaException;
import com.ecommerceApi.ecommerce.mappers.ProdutoMapper;
import com.ecommerceApi.ecommerce.models.categoria.Categoria;
import com.ecommerceApi.ecommerce.models.produto.*;
import com.ecommerceApi.ecommerce.repositories.CategoriaRepository;
import com.ecommerceApi.ecommerce.repositories.ProdutoRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@AllArgsConstructor
public class ProdutoService {
    private final ProdutoRepository repository;
    private final ProdutoMapper mapper;
    private final CategoriaRepository categoriaRepository;

    @Transactional
    public void cadastrarProduto(ProdutoCadastroDTO dto){
        Produto produto = mapper.toProduto(dto);
        Categoria categoria = categoriaRepository.findById(dto.categoriaId())
                .orElseThrow(() -> new CategoriaDesconhecidaException("Categoria não encontrada"));

        produto.setCategoria(categoria);
        repository.save(produto);
    }

    @Transactional
    public ProdutoResponseDTO buscarProdutoPorId(Long id){
        return repository.findById(id)
                .map(mapper::toProdutoReponseDTO)
                .orElseThrow(() ->
                        new ProdutoNaoEncontradoException(
                                "Produto não encontrado."));
    }

    @Transactional(readOnly = true)
    public List<ProdutoResponseDTO> buscarProdutosRecentes() {

        return repository
                .findTop10ByAtivoTrueOrderByCriadoEmDesc()
                .stream()
                .map(mapper::toProdutoReponseDTO)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<ProdutoResponseDTO> buscarProdutosRelacionados(Long produtoId) {

        Produto produto = repository.findById(produtoId)
                .orElseThrow(() ->
                        new ProdutoNaoEncontradoException(
                                "Produto não encontrado."
                        ));

        return repository
                .findTop8ByCategoriaIdAndIdNotAndAtivoTrue(
                        produto.getCategoria().getId(),
                        produtoId
                )
                .stream()
                .map(mapper::toProdutoReponseDTO)
                .toList();
    }

    @Transactional(readOnly = true)
    public Page<ProdutoResponseDTO> filtrarProdutos(

            ProdutoFiltroDTO filtro,

            Pageable pageable
    ){
        Specification<Produto> specification =

                Specification.where(
                                ProdutoSpecification.nomeContem(filtro.nome())
                        )
                        .and(
                                ProdutoSpecification.categoria(filtro.categoriaId())
                        )
                        .and(
                                ProdutoSpecification.precoMin(filtro.precoMin())
                        )
                        .and(
                                ProdutoSpecification.precoMax(filtro.precoMax())
                        )
                        .and(
                                ProdutoSpecification.ativo(filtro.ativo())
                        );

        return repository
                .findAll(specification, pageable)
                .map(mapper::toProdutoReponseDTO);
    }

    @Transactional
    public void atualizarEstoque(Long produtoId, Integer estoque) {

        Produto produto = repository.findById(produtoId)
                .orElseThrow(() ->
                        new ProdutoNaoEncontradoException(
                                "Produto não encontrado."
                        ));

        produto.setEstoque(estoque);

        repository.save(produto);
    }

    @Transactional
    public void atualizarProduto(Long id, ProdutoAtualizacaoDTO dto){
        Produto produto = repository.findById(id)
                .orElseThrow(() ->
                        new ProdutoNaoEncontradoException(
                                "Produto não encontrado."));

        mapper.updateProdutoFromDto(dto, produto);
        repository.save(produto);
    }

    @Transactional
    public void desativarProduto(Long id){
        Produto produto = repository.findById(id)
                .orElseThrow(() ->
                        new ProdutoNaoEncontradoException(
                                "Produto não encontrado."));
        produto.setAtivo(false);
        repository.save(produto);
    }

    @Transactional
    public void ativarProduto(Long id){
        Produto produto = repository.findById(id)
                .orElseThrow(() ->
                        new ProdutoNaoEncontradoException(
                                "Produto não encontrado."));
        produto.setAtivo(true);
        repository.save(produto);
    }

    public void validarQuantidadeProduto(Long produtoId, Integer quantidade){
        Produto produto = repository.findById(produtoId)
                .orElseThrow(() ->
                        new ProdutoNaoEncontradoException(
                                "Produto não encontrado."));
        if(produto.getEstoque() < quantidade){
            throw new QuantidadeInvalidaException("Quantidade ultrapassou do disponível. Disponíveis: " + produto.getEstoque());
        }
    }

    public void validarProdutoAtivo(Long produtoId){
        Produto produto = repository.findById(produtoId)
                .orElseThrow(() ->
                        new ProdutoNaoEncontradoException(
                                "Produto não encontrado."));
        if(!produto.getAtivo()){
            throw new QuantidadeInvalidaException("Produto está inativo");
        }
    }

    public void removerEstoqueProduto(Produto produto, Integer quantidade){
        if (produto.getEstoque() < quantidade) {
            throw new QuantidadeInvalidaException("Estoque insuficiente.");
        }
        produto.setEstoque(produto.getEstoque() - quantidade);
    }

    public void adicionarEstoqueProduto(Long produtoId, Integer quantidade){
        Produto produto = repository.findById(produtoId)
                .orElseThrow(() ->
                        new ProdutoNaoEncontradoException(
                                "Produto não encontrado."));
        produto.setEstoque(produto.getEstoque() + quantidade);
    }
}
