package in.journalApp.journalApp.repository;


import in.journalApp.journalApp.entity.AppConfigEntity;
import in.journalApp.journalApp.entity.JournalEnrty;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface AppConfigRepository extends MongoRepository<AppConfigEntity, ObjectId> {
}
