package com.codegym.controller;

import com.codegym.model.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.Optional;

@Controller
@RequestMapping("customers")
public class CustomerController {
    @Autowired
    private com.codegym.service.ICustomerService ICustomerService;

    @GetMapping
    public ModelAndView showList() {
        ModelAndView modelAndView = new ModelAndView("customers/list");
        Iterable<Customer> customers = ICustomerService.findAll();
        modelAndView.addObject("customers", customers);
        return modelAndView;
    }

    @GetMapping("{id}")
    public ModelAndView showInformation(@PathVariable Long id) {
        Optional<Customer> customer = ICustomerService.findById(id);
        if (!customer.isPresent()){
            return new ModelAndView("error-404");
        }
        ModelAndView modelAndView = new ModelAndView("customers/info");
        modelAndView.addObject("customer", customer.get());
        return modelAndView;
    }

    @PostMapping
    public String updateCustomer(Customer customer) {
        ICustomerService.save(customer);
        return "redirect:/customers";
    }
}
