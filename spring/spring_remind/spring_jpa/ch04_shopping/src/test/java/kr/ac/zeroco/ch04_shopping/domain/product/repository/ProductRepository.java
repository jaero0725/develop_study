package kr.ac.zeroco.ch04_shopping.domain.product.repository;

import kr.ac.zeroco.ch04_shopping.domain.product.entity.Product;
import kr.ac.zeroco.ch04_shopping.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long>  {
}
