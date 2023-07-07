package com.example.ecommerceproject.controller;

import com.example.ecommerceproject.global.GlobalData;
import com.example.ecommerceproject.services.CategoryService;
import com.example.ecommerceproject.services.ProductServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class HomeController {

    @Autowired
    CategoryService categoryService;

    @Autowired
    ProductServices productServices;

    @GetMapping({"/" , "/home"})
    public String home(Model model){
        model.addAttribute("cartCount" , GlobalData.cart.size());
        return "index";
    }

    @GetMapping("/shop")
    public String shop(Model model){
        model.addAttribute("categories" , categoryService.getAllCategories());
        model.addAttribute("products" , productServices.getAllProducts());
        model.addAttribute("cartCount" , GlobalData.cart.size());
        return "shop";
    }

    @GetMapping("/shop/category/{id}")
    public String shopById(Model model , @PathVariable int id){
        model.addAttribute("categories" , categoryService.getAllCategories());
        model.addAttribute("products" , productServices.getAllProductByCategoryId(id));
        model.addAttribute("cartCount" , GlobalData.cart.size());
        return "shop";
    }

    @GetMapping("/shop/viewProduct/{id}")
    public String viewProductById(@PathVariable Long id , Model model){
        model.addAttribute("product" , productServices.getProductById(id).get());
        model.addAttribute("cartCount" , GlobalData.cart.size());
        return "viewProduct";
    }
}
