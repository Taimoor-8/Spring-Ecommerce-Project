package com.example.ecommerceproject.controller;

import com.example.ecommerceproject.global.GlobalData;
import com.example.ecommerceproject.model.Product;
import com.example.ecommerceproject.services.ProductServices;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class cartController {

    @Autowired
    ProductServices productServices;

    @GetMapping("/addToCart/{id}")
    public String addToCart(@PathVariable long id){
        GlobalData.cart.add(productServices.getProductById(id).get());
        return "redirect:/shop";
    }

    @GetMapping("/cart")
    public String getCart(Model model){

        model.addAttribute("cart" , GlobalData.cart);
        model.addAttribute("total" , GlobalData.cart.stream()
                .mapToDouble(Product::getPrice).sum());
        model.addAttribute("cartCount" , GlobalData.cart.size());

        return "cart";
    }

    @GetMapping("/cart/removeItem/{index}")
    public String removeFromCart(@PathVariable int index){
        GlobalData.cart.remove(index);
        return "redirect:/cart";
    }

    @GetMapping("/checkout")
    public String checkout(Model model){
        model.addAttribute("total" , GlobalData.cart.stream()
                .mapToDouble(Product::getPrice).sum());
        return "checkout";
    }
}
