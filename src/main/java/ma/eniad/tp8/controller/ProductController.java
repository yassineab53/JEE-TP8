package ma.eniad.tp8.controller;


import ma.eniad.tp8.model.Product;
import ma.eniad.tp8.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/products")
public class ProductController {
    @Autowired
    private ProductRepository productRepository;

    @GetMapping
    public String listProducts(Model model) {
        model.addAttribute("products", productRepository.findAll());
        return "product-list";
    }

    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("product", new Product());
        return "product-form";
    }

    @PostMapping("/add")
    public String addProduct(/*@Valid*/ @ModelAttribute("product") Product product, BindingResult result) {
        if (result.hasErrors()) {
            return "product-form";
        }
        productRepository.save(product);
        return "redirect:/products";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        Product product = productRepository.findById(Math.toIntExact(id))
                .orElseThrow(() -> new IllegalArgumentException("Invalid product ID: " + id));
        model.addAttribute("product", product);
        return "product-form";
    }

    @PostMapping("/edit/{id}")
    public String updateProduct(@PathVariable Long id, /*@Valid */ @ModelAttribute("product") Product product, BindingResult result) {
        if (result.hasErrors()) {
            return "product-form";
        }
        product.setId(id);
        productRepository.save(product);
        return "redirect:/products";
    }

    @GetMapping("/delete/{id}")
    public String deleteProduct(@PathVariable Long id) {
        productRepository.deleteById(Math.toIntExact(id));
        return "redirect:/products";
    }
}
