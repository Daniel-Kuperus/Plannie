package MakePlannieWork.Plannie.controller;

import MakePlannieWork.Plannie.model.Gebruiker;
import MakePlannieWork.Plannie.model.Groep;
import MakePlannieWork.Plannie.service.PlannieGroepService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import MakePlannieWork.Plannie.repository.GebruikerRepository;

import java.security.Principal;
import java.util.Set;

@Controller
public class GebruikerController {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private GebruikerRepository gebruikerRepository;

    @Autowired
    private PlannieGroepService plannieGroepService;

    @GetMapping({"/index" , "/"})
    String index(Model model) {
        model.addAttribute("loginForm", new Gebruiker());
        return "index";
    }

    @GetMapping("/registreren")
    public String registreren(Model model) {
        model.addAttribute("registratieFormulier", new Gebruiker());
        return "gebruikerNieuw";
    }

    @PostMapping("/registreren")
    public String nieuweGebruiker(@ModelAttribute("registratieformulier") Gebruiker gebruiker, Model model, BindingResult result) {
        if (result.hasErrors() || !gebruiker.getWachtwoord().equals(gebruiker.getTrancientWachtwoord())) {
            return "gebruikerNieuw";
        } else {
            gebruiker.setWachtwoord(passwordEncoder.encode(gebruiker.getWachtwoord()));
            gebruikerRepository.save(gebruiker);
            model.addAttribute("loginForm", new Gebruiker());
            return "index";
        }
    }

    @GetMapping("/gebruikerDetail")
    public String gebruikerDetail(Model model, Principal principal) {
        model.addAttribute("currentUser", gebruikerRepository.findGebruikerByEmail(principal.getName()));
        Set<Groep> groepen = plannieGroepService.getLijstMetGroepenOpGebruikersnaam(principal.getName());
        model.addAttribute("lijstMetGroepen", groepen);
        return "gebruikerDetail";
    }
}
