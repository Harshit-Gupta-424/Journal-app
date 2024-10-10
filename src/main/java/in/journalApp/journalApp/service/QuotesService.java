package in.journalApp.journalApp.service;

import in.journalApp.journalApp.apiResponse.QuoteApiResponse;
import in.journalApp.journalApp.cache.AppCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.util.Random;

@Service
public class QuotesService {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private Random random;

    @Autowired
    private AppCache appCache;

    public int generateIdForQuote(){
        int randId = random.nextInt(1454);
        return randId;
    }


    public QuoteApiResponse getQuote(){
        String finalApi = appCache.appCache.get(AppCache.keys.QUOTES_API.toString()) + generateIdForQuote();
        ResponseEntity<QuoteApiResponse> quoteResponse = restTemplate.exchange(finalApi, HttpMethod.GET, null, QuoteApiResponse.class);
        QuoteApiResponse body = quoteResponse.getBody();
        return body;
    }
}
