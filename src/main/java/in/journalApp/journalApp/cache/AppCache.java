package in.journalApp.journalApp.cache;

import in.journalApp.journalApp.entity.AppConfigEntity;
import in.journalApp.journalApp.repository.AppConfigRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class AppCache {

    public enum keys{
        QUOTES_API;
    }


    public Map<String, String> appCache;

    @Autowired
    private AppConfigRepository appConfigRepository;

    @PostConstruct
    public void init(){
        appCache = new HashMap<>();
        List<AppConfigEntity> all = appConfigRepository.findAll();
        for (AppConfigEntity appConfigEntity : all){
            appCache.put(appConfigEntity.getKey(), appConfigEntity.getValue());
        }
    }

}
