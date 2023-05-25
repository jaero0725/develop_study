package kr.ac.zeroco.ch04_shopping.domain.product.controller;

import kr.ac.zeroco.ch04_shopping.domain.product.entity.Product;
import kr.ac.zeroco.ch04_shopping.domain.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/product")
public class ProductController {
    private final ProductService productService;

    /* *
     * 상품 등록 기능
     */
    @PostMapping
    public ResponseEntity<Product> registerProduct(@RequestBody Product product){

    }

    /* *
     * 상품 수정 기능
     */
    @PutMapping("/{productId}")
    public ResponseEntity<Product> registerProduct(@RequestBody Product product){

    }

    /* *
     * 상품 전체 조회 기능
     */
    @GetMapping
    public ResponseEntity<Product> registerProduct(@RequestBody Product product){

    }
}
