package in.journalApp.journalApp.repository;


import in.journalApp.journalApp.entity.JournalEnrty;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface JournalEntryRepository extends MongoRepository<JournalEnrty, ObjectId> {
}
