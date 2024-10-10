package in.journalApp.journalApp.service;

import in.journalApp.journalApp.entity.User;
import in.journalApp.journalApp.repository.JournalEntryRepository;
import in.journalApp.journalApp.entity.JournalEnrty;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class JournalEntryService {

    @Autowired
    public JournalEntryRepository journalEntryRepository;

    @Autowired
    public UserService userService;

    @Transactional
    public void updatedJournalEntry(JournalEnrty journalEntry, String userName){
        try{
            User user = userService.getUser(userName);
            journalEntry.setDate(LocalDateTime.now());
            JournalEnrty savedEntry = journalEntryRepository.save(journalEntry);
            user.getJournalEntries().add(savedEntry);
            userService.saveUser(user);
        }
        catch(Exception e){
            System.out.println(e);
            throw new Error("Some error occured while creating an user");
        }
    }

    public void updatedJournalEntry(JournalEnrty journalEntry){
        journalEntryRepository.save(journalEntry);
    }

    public List<JournalEnrty> getAllJournalEntries(){
        return journalEntryRepository.findAll();
    }

    public Optional<JournalEnrty> getJournalEntry(ObjectId myId){
        return journalEntryRepository.findById(myId);
    }

    @Transactional
    public Optional<JournalEnrty> deleteJournalEntry(ObjectId myId, String userName){
        try{
            User user = userService.getUser(userName);
            boolean removed = user.getJournalEntries().removeIf(x -> x.getId().equals(myId));
            Optional<JournalEnrty> deletedEntry = journalEntryRepository.findById(myId);
            if (removed) {
                journalEntryRepository.deleteById(myId);
                userService.saveUser(user);
                return deletedEntry;
            }
        }
        catch(Exception e)
        {
            System.out.println(e);
            throw new RuntimeException("Some error occurred durng deleting the entry");
        }
        return Optional.empty();
    }

}
