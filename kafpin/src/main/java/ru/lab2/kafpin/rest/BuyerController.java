package ru.lab2.kafpin.rest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import ru.lab2.kafpin.SearchForm;
import ru.lab2.kafpin.model.Buyer;
import ru.lab2.kafpin.repository.BuyerRepository;

import java.util.List;

@Controller
@RequestMapping("/buyers")
public class BuyerController {

    private final BuyerRepository buyerRepository;

    public BuyerController(BuyerRepository buyerRepository) {
        this.buyerRepository = buyerRepository;
    }

    @GetMapping("/main")
    public String mainPage(Model model) {
        List<Buyer> buyers = buyerRepository.findAll();
        model.addAttribute("buyers", buyers);
        model.addAttribute("searchForm", new SearchForm());
        return "main";
    }

    @PostMapping("/find")
    public String findBuyers(@ModelAttribute("searchForm") SearchForm searchForm, Model model) {
        String lastName = StringUtils.hasText(searchForm.getLastName()) ? searchForm.getLastName() : null;
        String city = StringUtils.hasText(searchForm.getCity()) ? searchForm.getCity() : null;
        String gender = StringUtils.hasText(searchForm.getGender()) ? searchForm.getGender() : null;
        String nationality = StringUtils.hasText(searchForm.getNationality()) ? searchForm.getNationality() : null;

        List<Buyer> buyers = buyerRepository.findWithFilters(lastName, city, gender, nationality);

        model.addAttribute("buyers", buyers);
        model.addAttribute("searchForm", searchForm);
        return "main";
    }

    @GetMapping("/create")
    public String createBuyerForm(Model model) {
        model.addAttribute("buyer", new Buyer());
        return "create_buyer";
    }

    @PostMapping("/create")
    public String saveBuyer(@ModelAttribute("buyer") Buyer buyer) {
        buyerRepository.save(buyer);
        return "redirect:/buyers/main";
    }

    @GetMapping("/details/{id}")
    public String buyerDetails(@PathVariable("id") Long id, Model model) {
        Buyer buyer = buyerRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Неверный ID: " + id));
        model.addAttribute("buyer", buyer);
        return "details";
    }

    @GetMapping("/delete/{id}")
    public String deleteBuyer(@PathVariable("id") Long id) {
        buyerRepository.deleteById(id);
        return "redirect:/buyers/main";
    }
}