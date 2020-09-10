package kitamuralab;
import java.util.ArrayList;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class json {
    public static void main(String[] args) throws JsonProcessingException {

        Model model = new Model();
        model.person = new Person();
        model.person.firstName = "ジョン";
        model.person.lastName = "ドゥ";
        model.person.address = "ニューヨーク";
        model.person.pets = new ArrayList<Pet>();
        Pet pet1 = new Pet();
        pet1.type = "犬";
        pet1.name = "ジョリー";
        model.person.pets.add(pet1);
        Pet pet2 = new Pet();
        pet2.type = "猫";
        pet2.name = "グリザベラ";
        model.person.pets.add(pet2);
        Pet pet3 = new Pet();
        pet3.type = "魚";
        pet3.name = "ニモ";
        model.person.pets.add(pet3);

        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(model);
        System.out.println(json);
    }
}