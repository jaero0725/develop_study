package kr.ac.zeroco.ch04_shopping.domain.product.service;

import kr.ac.zeroco.ch04_shopping.domain.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;

}
