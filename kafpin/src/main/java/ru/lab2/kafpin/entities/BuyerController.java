package ru.lab2.kafpin.entities;

import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.lab2.kafpin.Address;
import ru.lab2.kafpin.Buyer;
import ru.lab2.kafpin.PaymentInfo;
import ru.lab2.kafpin.repository.AddressRepository;
import ru.lab2.kafpin.repository.BuyerRepository;
import ru.lab2.kafpin.repository.PaymentInfoRepository;
import ru.lab2.kafpin.SearchForm;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/buyers")
public class BuyerController {

    private final BuyerRepository buyerRepository;
    private final AddressRepository addressRepository;
    private final PaymentInfoRepository paymentInfoRepository;

    public BuyerController(BuyerRepository buyerRepository,
                           AddressRepository addressRepository,
                           PaymentInfoRepository paymentInfoRepository) {
        this.buyerRepository = buyerRepository;
        this.addressRepository = addressRepository;
        this.paymentInfoRepository = paymentInfoRepository;
    }

    @GetMapping("/main")
    public String mainPage(Model model) {
        model.addAttribute("buyers", buyerRepository.findAll());
        model.addAttribute("searchForm", new SearchForm());
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
        Buyer buyer = new Buyer();
        buyer.setPaymentInfo(new PaymentInfo()); // чтобы *{paymentInfo.x} не упал
        model.addAttribute("buyer", buyer);
        model.addAttribute("addresses", addressRepository.findAll());   // ← список адресов
        return "create_buyer";
    }

    @PostMapping("/create")
    public String createBuyer(@ModelAttribute("buyer") @Valid Buyer buyer,
                              BindingResult result,
                              Model model) {
        if (result.hasErrors()) {
            model.addAttribute("addresses", addressRepository.findAll());
            return "create_buyer";
        }
        buyerRepository.save(buyer);
        return "redirect:/buyers/main";
    }

    @GetMapping("/update/{id}")
    public String editForm(Model model, @PathVariable("id") Long id) {
        Optional<Buyer> optionalBuyer = buyerRepository.findById(id);
        if (optionalBuyer.isEmpty()) {
            return "redirect:/buyers/main";
        }
        Buyer buyer = optionalBuyer.get();
        if (buyer.getPaymentInfo() == null) {
            buyer.setPaymentInfo(new PaymentInfo());
        }
        model.addAttribute("buyer", buyer);
        model.addAttribute("addresses", addressRepository.findAll());   // ← список адресов
        return "edit_buyer";
    }

    @PostMapping("/update")
    public String updateBuyer(@ModelAttribute("buyer") @Valid Buyer buyer,
                              BindingResult result,
                              Model model) {
        if (result.hasErrors()) {
            model.addAttribute("addresses", addressRepository.findAll());
            return "edit_buyer";
        }
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

    @PostMapping("/find")
    public String findBuyers(@ModelAttribute("searchForm") SearchForm form,
                             Model model) {
        List<Buyer> result;
        if (form.getLastName() != null && !form.getLastName().isBlank()) {
            result = buyerRepository.findByLastNameContainsIgnoreCase(form.getLastName());
        } else if (form.getCity() != null && !form.getCity().isBlank()) {
            result = buyerRepository.findByAddressCityIgnoreCase(form.getCity());
        } else if (form.getGender() != null && !form.getGender().isBlank()
                && form.getNationality() != null && !form.getNationality().isBlank()) {
            result = buyerRepository.findByGenderAndNationalityIgnoreCase(
                    form.getGender(), form.getNationality());
        } else {
            result = (List<Buyer>) buyerRepository.findAll();
        }
        model.addAttribute("buyers", result);
        model.addAttribute("searchForm", form);
        return "main";
    }
}