package in.journalApp.journalApp.controller;

import in.journalApp.journalApp.entity.JournalEnrty;
import in.journalApp.journalApp.entity.User;
import in.journalApp.journalApp.service.JournalEntryService;
import in.journalApp.journalApp.service.UserService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/journal")
public class JournalEntryController {
        
    @Autowired
    private JournalEntryService journalEntryService;

    @Autowired
    private UserService userService;

    @GetMapping("/getAllEntries")
    public ResponseEntity<List<JournalEnrty>> getAllJournalEntriesForUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        User user = userService.getUser(userName);
        List<JournalEnrty> allEntries = user.getJournalEntries();
        if (!allEntries.isEmpty()) {
            return new ResponseEntity<>(allEntries, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/getJournalById/{journalId}")
    public ResponseEntity<JournalEnrty> getJournalEntryByIdForUser(@PathVariable ObjectId journalId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        User user = userService.getUser(userName);
        List<JournalEnrty> foundEntry = user.getJournalEntries().stream().filter(x -> x.getId().equals(journalId)).collect(Collectors.toList());
        if (!foundEntry.isEmpty()) {
            Optional<JournalEnrty> journalEntry = journalEntryService.getJournalEntry(journalId);
            if(journalEntry.isPresent()) {
                return new ResponseEntity<>(journalEntry.get(), HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/createJournal")
    public ResponseEntity<JournalEnrty> createJournalEntry(@RequestBody JournalEnrty myEntry) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String userName = authentication.getName();
            journalEntryService.updatedJournalEntry(myEntry, userName);
            return new ResponseEntity<>(myEntry, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/deleteJournalById/{journalId}")
    public ResponseEntity<JournalEnrty> deleteJournalEntry(@PathVariable ObjectId journalId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        Optional<JournalEnrty> foundEntry = journalEntryService.deleteJournalEntry(journalId, userName);
        return foundEntry.map(journalEnrty -> new ResponseEntity<>(journalEnrty, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PutMapping("/updateJournalEntry/{journalId}")
    public ResponseEntity<JournalEnrty> updateJournalEntry( @PathVariable ObjectId journalId, @RequestBody JournalEnrty newEntry) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        User user = userService.getUser(userName);
        List<JournalEnrty> foundEntry = user.getJournalEntries().stream().filter(x -> x.getId().equals(journalId)).collect(Collectors.toList());
        if(!foundEntry.isEmpty()){
            Optional<JournalEnrty> journalEntry = journalEntryService.getJournalEntry(journalId);
            if(journalEntry.isPresent()) {
                JournalEnrty oldEntry = journalEntry.get();
                oldEntry.setTitle(newEntry.getTitle() != null ? newEntry.getTitle() : oldEntry.getTitle());
                oldEntry.setContent(newEntry.getContent() != null ? newEntry.getContent() : oldEntry.getContent());
                journalEntryService.updatedJournalEntry(oldEntry);
                return new ResponseEntity<>(oldEntry, HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(newEntry, HttpStatus.NOT_FOUND);
    }
}
