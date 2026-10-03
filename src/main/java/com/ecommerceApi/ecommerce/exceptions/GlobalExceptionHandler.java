package com.ecommerceApi.ecommerce.exceptions;

import com.ecommerceApi.ecommerce.exceptions.categoria.CategoriaDesconhecidaException;
import com.ecommerceApi.ecommerce.exceptions.categoria.CategoriaJaExistenteException;
import com.ecommerceApi.ecommerce.exceptions.categoria.CategoriaPossuiProdutosException;
import com.ecommerceApi.ecommerce.exceptions.categoria.RecursoNaoEncontradoException;
import com.ecommerceApi.ecommerce.exceptions.endereco.EnderecoNaoEncontradoException;
import com.ecommerceApi.ecommerce.exceptions.pedidos.CarrinhoVazioException;
import com.ecommerceApi.ecommerce.exceptions.pedidos.ImpossivelAlterarStatusPedidoException;
import com.ecommerceApi.ecommerce.exceptions.pedidos.PedidoNaoEncontradoException;
import com.ecommerceApi.ecommerce.exceptions.produto.ProdutoInativoException;
import com.ecommerceApi.ecommerce.exceptions.produto.ProdutoNaoEncontradoException;
import com.ecommerceApi.ecommerce.exceptions.produto.QuantidadeInvalidaException;
import com.ecommerceApi.ecommerce.exceptions.usuario.CredenciaisInvalidasException;
import com.ecommerceApi.ecommerce.exceptions.usuario.EmailJaCadastradoException;
import com.ecommerceApi.ecommerce.models.pedido.Pedido;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(EmailJaCadastradoException.class)
    public ResponseEntity<String> tratarEmailJaCadastrado(
            EmailJaCadastradoException ex) {

        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(ex.getMessage());
    }

    @ExceptionHandler(CredenciaisInvalidasException.class)
    public ResponseEntity<String> tratarCredenciais(
            CredenciaisInvalidasException ex) {

        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(ex.getMessage());
    }

    @ExceptionHandler(QuantidadeInvalidaException.class)
    public ResponseEntity<String> tratarQuantidadeInvalida(
            QuantidadeInvalidaException ex) {

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ex.getMessage());
    }

    @ExceptionHandler(ProdutoNaoEncontradoException.class)
    public ResponseEntity<String> tratarProdutoNaoEncontrado(
            ProdutoNaoEncontradoException ex) {

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(ex.getMessage());
    }


    @ExceptionHandler(CategoriaDesconhecidaException.class)
    public ResponseEntity<String> tratarCategoriaDesconhecida(
            CategoriaDesconhecidaException ex) {

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(ex.getMessage());
    }

    @ExceptionHandler(CategoriaJaExistenteException.class)
    public ResponseEntity<String> tratarCategoriaJaExistente(
            CategoriaJaExistenteException ex) {

        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(ex.getMessage());
    }

    @ExceptionHandler(CategoriaPossuiProdutosException.class)
    public ResponseEntity<String> tratarCategoriaPossuiProdutos(
            CategoriaPossuiProdutosException ex) {

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ex.getMessage());
    }

    @ExceptionHandler(RecursoNaoEncontradoException.class)
    public ResponseEntity<String> tratarRecursoNaoEncontrado(
            RecursoNaoEncontradoException ex) {

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(ex.getMessage());
    }

    @ExceptionHandler(CarrinhoVazioException.class)
    public ResponseEntity<String> tratarCarrinhoVazio(
            CarrinhoVazioException ex) {

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ex.getMessage());
    }

    @ExceptionHandler(PedidoNaoEncontradoException.class)
    public ResponseEntity<String> tratarPedidoNaoEncontrado(
            PedidoNaoEncontradoException ex) {

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(ex.getMessage());
    }

    @ExceptionHandler(ImpossivelAlterarStatusPedidoException.class)
    public ResponseEntity<String> tratarImpossivelAlterarStatusPedido(
            ImpossivelAlterarStatusPedidoException ex) {

        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(ex.getMessage());
    }

    @ExceptionHandler(ProdutoInativoException.class)
    public ResponseEntity<String> tratarProdutoInativo(
            ProdutoInativoException ex) {

        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(ex.getMessage());
    }

    @ExceptionHandler(EnderecoNaoEncontradoException.class)
    public ResponseEntity<String> tratarEnderecoNaoEncontrado(
            EnderecoNaoEncontradoException ex) {

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(ex.getMessage());
    }
}