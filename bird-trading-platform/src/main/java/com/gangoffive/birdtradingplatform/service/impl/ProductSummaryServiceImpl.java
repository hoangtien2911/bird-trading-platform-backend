package com.gangoffive.birdtradingplatform.service.impl;

import com.gangoffive.birdtradingplatform.entity.*;
import com.gangoffive.birdtradingplatform.repository.OrderDetailRepository;
import com.gangoffive.birdtradingplatform.repository.ProductRepository;
import com.gangoffive.birdtradingplatform.repository.ProductSummaryRepository;
import com.gangoffive.birdtradingplatform.repository.ReviewRepository;
import com.gangoffive.birdtradingplatform.service.ProductService;
import com.gangoffive.birdtradingplatform.service.ProductSummaryService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductSummaryServiceImpl implements ProductSummaryService {
    private final ProductSummaryRepository productSummaryRepository;
    private final ProductService productService;
    private final ReviewRepository reviewRepository;
    private final OrderDetailRepository orderDetailRepository;
    private final ProductRepository productRepository;
    public double updateProductStar(Product product) {
        double star = productService.CalculationRating(product.getOrderDetails());
        var productSummary = productSummaryRepository.findByProductId(product.getId()).orElse(new ProductSummary());
        productSummary.setStar(star);
        productSummary.setProduct(product);
        productSummaryRepository.save(productSummary);
        return star;
    }

    public int updateReviewTotal(Product product){
        List<Long> orderDetailIds = product
                                        .getOrderDetails()
                                                .stream().
                                                    map(reviewId -> reviewId.getId()).collect(Collectors.toList());
        int reviewTotal = reviewRepository.findAllByOrderDetailIdIn(orderDetailIds).get().size();
        var productSummary = productSummaryRepository.findByProductId(product.getId()).orElse(new ProductSummary());
        productSummary.setReviewTotal(reviewTotal);
        productSummary.setProduct(product);
        productSummaryRepository.save(productSummary);
        return reviewTotal;
    }

    public int updateTotalQuantityOrder(Product product){
        int totalQuantity = orderDetailRepository.findTotalQuantityByPId(product.getId()).orElse(0);
        var productSummary = productSummaryRepository.findByProductId(product.getId()).orElse(new ProductSummary());
        productSummary.setTotalQuantityOrder(totalQuantity);
        productSummary.setProduct(product);
        productSummaryRepository.save(productSummary);
        return totalQuantity;
    }

    public String updateCategory(Product product) {
        String category = product.getClass().getSimpleName();
        var productSummary = productSummaryRepository.findByProductId(product.getId()).orElse(new ProductSummary());
        productSummary.setCategory(category);
        productSummary.setProduct(product);
        productSummaryRepository.save(productSummary);
        return category;
    }

    @Transactional
    public boolean apply(Product product){
        this.updateReviewTotal(product);
        this.updateProductStar(product);
        this.updateTotalQuantityOrder(product);
        this.updateCategory(product);
        return true;
    }

    @Override
    public List<Long> getIdTopBird() {
        PageRequest page = PageRequest.of(0, 8, Sort.by(Sort.Direction.DESC, "star")
                .and(Sort.by(Sort.Direction.DESC, "totalQuantityOrder")));
        var listsProductSummary =  productSummaryRepository.
                                        findByCategory(new Bird().getClass().getSimpleName(), page);
        if(listsProductSummary.isPresent()){
            List<Long> listIdTopBird = listsProductSummary.get().stream()
                                        .map(productSummary -> productSummary.getProduct().getId()).toList();
            return listIdTopBird;
        }

        return null;
    }

    @Override
    public List<Long> getIdTopAccessories() {
        PageRequest page = PageRequest.of(0, 8, Sort.by(Sort.Direction.DESC, "star")
                .and(Sort.by(Sort.Direction.DESC, "totalQuantityOrder")));
        var listsProductSummary =  productSummaryRepository.
                findByCategory(new Accessory().getClass().getSimpleName(), page);
        if(listsProductSummary.isPresent()){
            List<Long> listIdTopAccessories = listsProductSummary.get().stream()
                    .map(productSummary -> productSummary.getProduct().getId()).toList();
            return listIdTopAccessories;
        }

        return null;
    }

    @Override
    public List<Long> getIdTopFood() {
        PageRequest page = PageRequest.of(0, 8, Sort.by(Sort.Direction.DESC, "star")
                .and(Sort.by(Sort.Direction.DESC, "totalQuantityOrder")));
        var listsProductSummary =  productSummaryRepository.
                findByCategory(new Food().getClass().getSimpleName(), page);
        if(listsProductSummary.isPresent()){
            List<Long> listIdTopFood = listsProductSummary.get().stream()
                    .map(productSummary -> productSummary.getProduct().getId()).toList();
            return listIdTopFood;
        }

        return null;
    }

}
