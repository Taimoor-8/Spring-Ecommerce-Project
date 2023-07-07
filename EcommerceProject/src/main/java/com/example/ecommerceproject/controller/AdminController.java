package com.example.ecommerceproject.controller;


import com.example.ecommerceproject.dto.ProductDTO;
import com.example.ecommerceproject.model.Category;
import com.example.ecommerceproject.model.Product;
import com.example.ecommerceproject.services.CategoryService;
import com.example.ecommerceproject.services.ProductServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

@Controller
public class AdminController {

    public static String uploadDir = System.getProperty("user.dir") +
            "/src/main/resources/static/productImages";

//    Categories

    @Autowired
    CategoryService categoryService;

    @GetMapping("/admin")
    public String AdminHome(){
        return "adminHome";
    }

    @GetMapping("/admin/categories")
    public String getCategories(Model model){
        model.addAttribute("categories" , categoryService.getAllCategories());
        return "categories";
    }

    @GetMapping("/admin/categories/add")
    public String getCategoriesAdd(Model model){
        model.addAttribute("category" , new Category());
        return "categoriesAdd";
    }

    @PostMapping("/admin/categories/add")
    public String postCategoriesAdd(@ModelAttribute("category") Category category){
        categoryService.addCategory(category);
        return "redirect:/admin/categories";
    }

    @GetMapping("/admin/categories/delete/{id}")
    public String deleteCategory(@PathVariable int id) {
        categoryService.removeCategoryById(id);
        return "redirect:/admin/categories";
    }

    @GetMapping("/admin/categories/update/{id}")
    public String updateCategory(@PathVariable int id , Model model){
        Optional<Category> category = categoryService.getCategoryById(id);
        if (category.isPresent()){
            model.addAttribute("category" , category.get());
            return "categoriesAdd";
        }
        else {
            return "Error";
        }
    }

//    Products

    @Autowired
    ProductServices productServices;

    @GetMapping("/admin/products")
    public String getProduct(Model model){
        model.addAttribute("product" , productServices.getAllProducts());
        return "products";
    }

    @GetMapping("/admin/products/add")
    public String getProductsAdd(Model model){
        model.addAttribute("productDTO" , new ProductDTO());
        model.addAttribute("categories" , categoryService.getAllCategories());
        return "productsAdd";
    }

    @PostMapping("/admin/products/add")
    public String postProductAdd(@ModelAttribute("ProductDTO") ProductDTO productDTO ,
                                 @RequestParam ("productImage")MultipartFile file ,
                                 @RequestParam("imgName") String imgName) throws IOException {

        Product product = new Product();

        product.setId(productDTO.getId());
        product.setName(productDTO.getName());
        product.setPrice(productDTO.getPrice());
        product.setWeight(productDTO.getWeight());
        product.setDescription(productDTO.getDescription());
        product.setCategory(categoryService.getCategoryById(productDTO.getCategoryId()).get());

        String imageUUID;
        if (!file.isEmpty()){
            imageUUID = file.getOriginalFilename();
            Path fileNameAndPath = Paths.get(uploadDir , imageUUID);
            Files.write(fileNameAndPath , file.getBytes());
        }
        else{
            imageUUID = imgName;
        }
        product.setImageName(imageUUID);
        productServices.addProduct(product);

        return "redirect:/admin/products";
    }

    @GetMapping("/admin/product/delete/{id}")
    public String deleteProduct(@PathVariable long id){

        productServices.removeProductById(id);
        return "redirect:/admin/products";
    }

    @GetMapping("/admin/product/update/{id}")
    public String updateProduct(@PathVariable long id , Model model){

        Product product = productServices.getProductById(id).get();

        ProductDTO productDTO = new ProductDTO();

        productDTO.setName(product.getName());
        productDTO.setPrice(product.getPrice());
        productDTO.setDescription(product.getDescription());
        productDTO.setWeight(product.getWeight());
        productDTO.setId(product.getId());
        productDTO.setImageName(product.getImageName());
        productDTO.setCategoryId(product.getCategory().getId());

        model.addAttribute("categories" , categoryService.getAllCategories());
        model.addAttribute("productDTO" , productDTO);

        return "productsAdd";
    }
}
