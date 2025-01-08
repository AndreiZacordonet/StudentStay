package dev.studentstay.Documente.config;

import dev.studentstay.Documente.model.CereriEntity;
import dev.studentstay.Documente.model.Documente;
import dev.studentstay.Documente.repository.CereriRepository;
import dev.studentstay.Documente.repository.DocumenteRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DatabaseLoader {
    private static final Logger log = LoggerFactory.getLogger(DatabaseLoader.class);

//    @Bean
    CommandLineRunner initDatabase(
        CereriRepository cereriRepository,
        DocumenteRepository documenteRepository
    ) {
        return args -> {
            // populate the database with data
//            CereriEntity cerere1 = new CereriEntity(null, (long) 1, CereriEntity.TipCerere.CASATORIT);
//            CereriEntity cerere2 = new CereriEntity(null, (long) 2, CereriEntity.TipCerere.CAZARE);
//            CereriEntity cerere3 = new CereriEntity(null, (long) 3, CereriEntity.TipCerere.CAZARE);
//            CereriEntity cerere4 = new CereriEntity(null, (long) 4, CereriEntity.TipCerere.HANDICAP);
//
//            cereriRepository.save(cerere1);
//            cereriRepository.save(cerere2);
//            cereriRepository.save(cerere3);
//            cereriRepository.save(cerere4);

            Documente doc1 = new Documente(null, (long) 1, "files/undeva/numedocument.pdf", null, null);
            Documente doc2 = new Documente(null, (long) 1, "files/undeva/numedocument2.pdf", null, null);
            Documente doc3 = new Documente(null, (long) 2, "files/undeva/numedocument3.pdf", null, null);

            documenteRepository.save(doc1);
            documenteRepository.save(doc2);
            documenteRepository.save(doc3);
        };
    }

}
