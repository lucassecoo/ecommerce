package com.ecommerceApi.ecommerce.services;

import com.ecommerceApi.ecommerce.exceptions.categoria.CategoriaJaExistenteException;
import com.ecommerceApi.ecommerce.exceptions.categoria.CategoriaPossuiProdutosException;
import com.ecommerceApi.ecommerce.exceptions.usuario.CredenciaisInvalidasException;
import com.ecommerceApi.ecommerce.exceptions.categoria.RecursoNaoEncontradoException;
import com.ecommerceApi.ecommerce.mappers.CategoriaMapper;
import com.ecommerceApi.ecommerce.mappers.ProdutoMapper;
import com.ecommerceApi.ecommerce.models.categoria.Categoria;
import com.ecommerceApi.ecommerce.models.categoria.CategoriaCadastroDto;
import com.ecommerceApi.ecommerce.models.categoria.CategoriaResponseDTO;
import com.ecommerceApi.ecommerce.models.categoria.CategoriaUpdateDTO;
import com.ecommerceApi.ecommerce.models.produto.Produto;
import com.ecommerceApi.ecommerce.models.produto.ProdutoResponseDTO;
import com.ecommerceApi.ecommerce.repositories.CategoriaRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class CategoriaService {
    private final CategoriaRepository repository;
    private final CategoriaMapper categoriaMapper;
    private final ProdutoMapper produtoMapper;

    public void cadastrarCategoria(CategoriaCadastroDto dto){
        existeCategoria(dto.nome());
        Categoria categoria = categoriaMapper.toCategoria(dto);
        repository.save(categoria);
    }

    public Page<CategoriaResponseDTO> buscarTodosCategorias(Pageable pageable){
        return repository.findAll(pageable)
                .map(categoriaMapper::toCategoriaResponseDTO);
    }

    public CategoriaResponseDTO buscarCategoriaPorId(Long id){
        return repository.findById(id)
                .map(categoriaMapper::toCategoriaResponseDTO)
                .orElseThrow(() ->
                        new CredenciaisInvalidasException(
                                "Categoria não encontrado."));
    }

    public void atualizarCategoria(Long id, CategoriaUpdateDTO dto){
        Categoria categoria = repository.findById(id)
                .orElseThrow(() ->
                        new CredenciaisInvalidasException(
                                "Categoria não encontrado."));
        existeCategoria(categoria.getNome());

        categoria.setNome(dto.nome());
        categoria.setDescricao(dto.descricao());
        repository.save(categoria);
    }

    public void existeCategoria(String nome){
        if(repository.existsByNomeIgnoreCase(nome)){
            throw new CategoriaJaExistenteException("Categoria com nome já existente");
        }
    }

    public void excluirCategoria(Long id){
        Categoria categoria = repository.findById(id)
                .orElseThrow(() ->
                        new CredenciaisInvalidasException(
                                "Categoria não encontrado."));
        if(!categoria.getProdutos().isEmpty()){
            throw new CategoriaPossuiProdutosException("Categoria possui produtos vinculados. Impossivel excluir!");
        }
        repository.deleteById(id);
    }

    public List<ProdutoResponseDTO> buscarProdutosPorCategoria(Long id){
        List<Produto> produtos = repository.findCategoriaById(id);

        if (produtos.isEmpty()) {
            throw new RecursoNaoEncontradoException(
                    "Nenhum produto encontrado para esta categoria."
            );
        }

        return produtos.stream().map(produtoMapper::toProdutoReponseDTO)
                .toList();
    }
}
