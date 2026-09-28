package ru.lab2.kafpin;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.lab2.kafpin.repository.BuyerRepository;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/buyers")
public class BuyerController {

    private final BuyerRepository buyerRepository;

    public BuyerController(BuyerRepository buyerRepository) {
        this.buyerRepository = buyerRepository;
    }

    @GetMapping("/main")
    public String mainPage(Model model) {
        List<Buyer> allBuyers = (List<Buyer>) buyerRepository.findAll();
        model.addAttribute("buyers", allBuyers);
        return "main";
    }

    @GetMapping("/details/{id}")
    public String detailsPage(Model model, @PathVariable("id") Long id) {
        Optional<Buyer> optionalBuyer = buyerRepository.findById(id);
        if (optionalBuyer.isEmpty()) {
            return "redirect:/buyers/main";
        }
        model.addAttribute("selectedBuyer", optionalBuyer.get());
        return "details";
    }

    @GetMapping("/create")
    public String createForm(Model model) {
        model.addAttribute("buyer", new Buyer());
        return "create_buyer";
    }

    @PostMapping("/create")
    public String createBuyer(@ModelAttribute Buyer buyer) {
        buyerRepository.save(buyer);
        return "redirect:/buyers/main";
    }

    @GetMapping("/update/{id}")
    public String editForm(Model model, @PathVariable("id") Long id) {
        Optional<Buyer> optionalBuyer = buyerRepository.findById(id);
        if (optionalBuyer.isEmpty()) {
            return "redirect:/buyers/main";
        }
        model.addAttribute("buyer", optionalBuyer.get());
        return "edit_buyer";
    }

    @PostMapping("/update")
    public String updateBuyer(@ModelAttribute Buyer buyer) {
        if (buyerRepository.existsById(buyer.getId())) {
            buyerRepository.save(buyer);
        }
        return "redirect:/buyers/main";
    }

    @GetMapping("/delete/{id}")
    public String deleteBuyer(@PathVariable("id") Long id) {
        if (buyerRepository.existsById(id)) {
            buyerRepository.deleteById(id);
        }
        return "redirect:/buyers/main";
    }
}
