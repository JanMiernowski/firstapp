package pl.sda.finalapp.users;

import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class RegistrationValidator {
    //todo dopisanie walidacji dla reszty pól
    Map<String, String> isValid(RegistrationUserDto dto){
        Map<String, String> errors = new HashMap<>();

        if(dto.getFirstName() == null || !dto.getFirstName().matches("^[A-Z][a-z]{2,}$")){
            errors.put("firstNameError", "Imię jest wymagane. Przynajmniej 3 znaki oraz pierwsza duża, reszta małe.");
        }
        if(!dto.getLastName().matches("^[A-Z][a-z]{2,}(-[A-Z][a-z]{2,})?$")){
            errors.put("lastNameError", "Nazwisko jest wymagane. Przynajmniej 3 znaki oraz pierwsza duża, reszta małe\n" +
                    "\t+ dopuszczenie \"-\" i drugiego członu z dużej litery");
        }
        if(!dto.getEMail().matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$")){
            errors.put("eMailError", "Zły format adresu email");
        }
        if(!dto.getPassword().matches("^(?!.*\\s).{10,20}$")){
            errors.put("passwordError","Hasło jest wymagane. Musi zawierać od 10 do 20 znaków.");
        }
        if(!dto.getCity().matches("^[a-zA-Z]{2,}$")){
            errors.put("cityError","Podanie nazwy miasta jest wymagane");
        }
        if(!dto.getCountry().matches("^[a-zA-Z]{2,}$")){
            errors.put("countryError","Podanie nazwy miasta jest wymagane");
        }
        if(!dto.getZipCode().matches("")){
            errors.put("zipError","Zły format. Kod pocztowy powinien mieć format 12-345.\n");
        }
        if(!dto.getStreet().matches("")){
            errors.put("streetError","Podanie nazwy ulicy jest wymagane");
        }
        if(!dto.getBirthDate().matches("Zły format. Data urodzin powinna być podana w formacie RRRR-MM-DD\n")){
            errors.put("birthDateError","");
        }
        if(!dto.getPesel().matches("")){
            errors.put("peselError","Zły format. Numer PESEL powinien składać się z 11 cyfr.\n");
        }
        if(!dto.getPhone().matches("")){
            errors.put("phoneError","Zły format. Numer telefonu powinien składać się z 9 cyfr.\n" +
                    "\t-> możliwa opcja z \"+48\" na początku\",\n" +
                    "\txxx xxx xxx (spacje) lub xxx-xxx-xxx");
        }


        return errors;
    }

}
