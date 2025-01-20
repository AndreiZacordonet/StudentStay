package ProiectPAWCamine.Config;

import ProiectPAWCamine.Entity.CamineEntity;
import ProiectPAWCamine.Repository.CamineRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class CamineDataLoader implements CommandLineRunner {

    private final CamineRepository camineRepository;

    public CamineDataLoader(CamineRepository camineRepository) {
        this.camineRepository = camineRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        if (camineRepository.count() == 0) {
            camineRepository.save(new CamineEntity(
                    "Camin 1",
                    "Campus Tudor Vladimirescu Iasi",
                    3,
                    150,
                    150,
                    "Etaj 1: 50 camere, Etaj 2: 50 camere, Etaj 3: 50 camere",
                    "/path/document1.pdf",
                    "Camin1",
                    "Situat în inima Campusului Tudor Vladimirescu, acest cămin oferă cazare modernă, ideală pentru studenți. Camere spațioase și facilități excelente."
            ));
            camineRepository.save(new CamineEntity(
                    "Camin 2",
                    "Campus Tudor Vladimirescu Iasi",
                    4,
                    200,
                    250,
                    "Etaj 1: 50 camere, Etaj 2: 50 camere, Etaj 3: 50 camere, Etaj 4: 50 camere",
                    "/path/document2.pdf",
                    "Camin2",
                    "Un cămin modern cu patru etaje, perfect pentru studenți care caută confort și proximitate de facilitățile universitare."
            ));
            camineRepository.save(new CamineEntity(
                    "Camin 3",
                    "Campus Tudor Vladimirescu Iasi",
                    5,
                    250,
                    500,
                    "Etaj 1: 50 camere, Etaj 2: 50 camere, Etaj 3: 50 camere, Etaj 4: 50 camere, Etaj 5: 50 camere",
                    "/path/document3.pdf",
                    "Camin3",
                    "Acest cămin cu cinci etaje asigură spații generoase, camere bine echipate și un mediu excelent pentru studenți."
            ));
            camineRepository.save(new CamineEntity(
                    "Camin 4",
                    "Campus Tudor Vladimirescu Iasi",
                    2,
                    215,
                    300,
                    "Etaj 1: 108 camere, Etaj 2: 107 camere",
                    "/path/document4.pdf",
                    "Camin4",
                    "Oferind două etaje, căminul este situat în apropierea centrului campusului, fiind ideal pentru studenți ce caută acces rapid la universitate."
            ));
            camineRepository.save(new CamineEntity(
                    "Camin 5",
                    "Campus Tudor Vladimirescu Iasi",
                    4,
                    200,
                    600,
                    "Etaj 1: 50 camere, Etaj 2: 50 camere, Etaj 3: 50 camere, Etaj 4: 50 camere",
                    "/path/document5.pdf",
                    "Camin5",
                    "Un cămin cu patru etaje și camere moderne, perfect pentru grupuri de studenți, având facilități de top și locație centrală."
            ));
            camineRepository.save(new CamineEntity(
                    "Camin 6",
                    "Campus Tudor Vladimirescu Iasi",
                    3,
                    200,
                    500,
                    "Etaj 1: 67 camere, Etaj 2: 67 camere, Etaj 3: 66 camere",
                    "/path/document6.pdf",
                    "Camin6",
                    "Acest cămin cu trei etaje oferă camere bine iluminate, spațioase și condiții excelente pentru studenții din campus."
            ));
            camineRepository.save(new CamineEntity(
                    "Camin 7",
                    "Campus Tudor Vladimirescu Iasi",
                    4,
                    200,
                    400,
                    "Etaj 1: 50 camere, Etaj 2: 50 camere, Etaj 3: 50 camere, Etaj 4: 50 camere",
                    "/path/document7.pdf",
                    "Camin7",
                    "Un cămin cu patru etaje ce combină confortul cu proximitatea față de sălile de curs și biblioteca universitară."
            ));
            camineRepository.save(new CamineEntity(
                    "Camin 8",
                    "Campus Tudor Vladimirescu Iasi",
                    5,
                    250,
                    600,
                    "Etaj 1: 50 camere, Etaj 2: 50 camere, Etaj 3: 50 camere, Etaj 4: 50 camere, Etaj 5: 50 camere",
                    "/path/document8.pdf",
                    "Camin8",
                    "Cel mai mare cămin din campus, cu facilități moderne și camere ideale pentru grupuri de studenți, distribuite pe cinci etaje."
            ));
            camineRepository.save(new CamineEntity(
                    "Camin 9",
                    "Campus Tudor Vladimirescu Iasi",
                    3,
                    150,
                    600,
                    "Etaj 1: 50 camere, Etaj 2: 50 camere, Etaj 3: 50 camere",
                    "/path/document9.pdf",
                    "Camin9",
                    "Cu trei etaje, căminul oferă cazare modernă, spații generoase și acces ușor la facilități universitare."
            ));
            camineRepository.save(new CamineEntity(
                    "Camin 10",
                    "Campus Tudor Vladimirescu Iasi",
                    3,
                    150,
                    600,
                    "Etaj 1: 50 camere, Etaj 2: 50 camere, Etaj 3: 50 camere",
                    "/path/document10.pdf",
                    "Camin10",
                    "Camin modern și bine întreținut, ideal pentru studenți, cu acces rapid la toate resursele campusului universitar."
            ));

            System.out.println("Baza de date a fost populată cu 10 cămine.");
        }
    }
}
