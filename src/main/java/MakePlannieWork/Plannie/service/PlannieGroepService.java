package MakePlannieWork.Plannie.service;

import MakePlannieWork.Plannie.model.Gebruiker;
import MakePlannieWork.Plannie.model.Groep;
import MakePlannieWork.Plannie.repository.GebruikerRepository;
import MakePlannieWork.Plannie.repository.GroepRepository;
import com.sun.xml.bind.v2.TODO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.Principal;
import java.util.Locale;
import java.util.Optional;
import java.util.Set;

@Service
public class PlannieGroepService {
    @Autowired
    private GroepRepository groepRepository;
    @Autowired
    private GebruikerRepository gebruikerRepository;
    @Autowired
    private PlannieGroepService plannieGroepService;
    @Autowired
    private PlannieMailingService mailingService;


    public Set<Groep> getLijstMetGroepenOpGebruikersnaam(String gebruikersnaam) {
        Gebruiker gebruiker = gebruikerRepository.findGebruikerByEmail(gebruikersnaam);
        return gebruiker.getGroepen();
    }

    public void voegGroepToe(Groep groep, Principal principal) {
        Gebruiker gebruiker = gebruikerRepository.findGebruikerByEmail(principal.getName());
        groep.getGroepsleden().add(gebruiker);
        groep.setAanmaker(gebruikerRepository.findGebruikerByEmail(principal.getName()).getGebruikersId());
        groepRepository.save(groep);
    }

    public Optional<Groep> findById(Integer id) {
        return groepRepository.findById(id);
    }

    public void voegGebruikerToeAanGroep(Integer uitgenodigdeGebruiker, Integer groepId) {
        Groep groep = groepRepository.findById(groepId).get();
        groep.getGroepsleden().add(gebruikerRepository.findById(uitgenodigdeGebruiker).get());
        groepRepository.save(groep);
    }

    public void verwijderGebruikerUitGroep(Integer id, Integer groepId) {
        Groep groep = groepRepository.findById(groepId).get();
        groep.getGroepsleden().remove(gebruikerRepository.findById(id).get());
        groepRepository.save(groep);
    }

    public void stuurUitnodigingPerEmail(String email, Integer groepId, String gebruikerUUID, HttpServletRequest request) throws MessagingException {
        Groep groep = groepRepository.findById(groepId).get();
        String URL = request.getScheme() + "://" + request.getServerName() + "/registreren?gebruikerUUID=" + gebruikerUUID;
        mailingService.sendEmail(email, "Hallo, u bent door " + gebruikerRepository.findGebruikerByGebruikersId(groep.getAanmaker()).getVoornaam() + " " + gebruikerRepository.findGebruikerByGebruikersId(groep.getAanmaker()).getAchternaam() + " " + " uitgenodigd voor de groep " + groep.getGroepsNaam() +
                ". Plannie maakt het groepen makkelijk om reizen te plannen. Klink op de volgende link om mee te doen: " + URL, "Uitnodiging voor Plannie");
    }

    public void saveImage(MultipartFile imageFile, Groep groep) throws Exception{
        String folder = "src/main/webapp/images/groep/";
        byte[] bytes = imageFile.getBytes();
        Path imagesPath = Paths.get(folder);
        Path huidigPath = Paths.get(folder + groep.getImagePath());
        Path rootPath = Paths.get(folder + "static/placeholder.png");
        Path path = Paths.get(folder + imageFile.getOriginalFilename());
        if (!rootPath.equals(huidigPath)) {
            Files.delete(huidigPath);
        }

        if (!Files.exists(imagesPath)) {
            Files.createDirectory(imagesPath);
        }
        if (Files.exists(path)) {

            path = Paths.get(folder+ "1" + imageFile.getOriginalFilename());
        }
        groep.setImagePath(imageFile.getOriginalFilename());
        groepRepository.save(groep);
        Files.write(path, bytes);
    }
}