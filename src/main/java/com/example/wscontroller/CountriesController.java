package com.example.wscontroller;

import com.example.domain.Country;
import com.example.domain.Currency;
import com.example.repo.CountriesRepository;
import com.example.springsoap.gen.GetCountryRequest;
import com.example.springsoap.gen.GetCountryResponse;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import java.util.Collections;

@Endpoint
public class CountriesController {
    public static final String NAMESPACE_URI = "http://www.example.com/springsoap/gen";
    CountriesRepository countriesRepository;

    @Autowired
    public CountriesController(CountriesRepository countriesRepository) {
        this.countriesRepository = countriesRepository;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getCountryRequest")
    @ResponsePayload
    public GetCountryResponse getCountry(@RequestPayload GetCountryRequest request) {
        Iterable<Country> countries = countriesRepository.findAll();
        if (!countries.iterator().hasNext()) {
            init();
        }
        GetCountryResponse response = new GetCountryResponse();
        com.example.springsoap.gen.Country countrySOAP = null;
        for (Country country : countries) {
//            Country country = countriesRepository.findByName(request.getName();
            if (country.getName().equals(request.getName())) {
                countrySOAP = convertForSoap(country);
                break;
            }
        }
        response.setCountry(countrySOAP);
        return response;
    }

    private void init() {
        Country spain = new Country();
        spain.setName("Spain");
        spain.setCapital("Madrid");
        spain.setCurrencies(Collections.singleton(Currency.EUR));
        spain.setPopulation(46704314);
        countriesRepository.save(spain);

        Country poland = new Country();
        poland.setName("Poland");
        poland.setCapital("Warsaw");
        poland.setCurrencies(Collections.singleton(Currency.PLN));
        poland.setPopulation(38186860);
        countriesRepository.save(poland);

        Country uk = new Country();
        uk.setName("United Kingdom");
        uk.setCapital("London");
        uk.setCurrencies(Collections.singleton(Currency.GBP));
        uk.setPopulation(63705000);
        countriesRepository.save(uk);

        Country germany = new Country();
        germany.setName("Germany");
        germany.setCapital("Berlin");
        germany.setCurrencies(Collections.singleton(Currency.EUR));
        germany.setPopulation(63705000);
        countriesRepository.save(germany);

    }

    private com.example.springsoap.gen.Country convertForSoap(Country country) {
        com.example.springsoap.gen.Country countrySOAP = null;
        if (country != null) {
            countrySOAP = new com.example.springsoap.gen.Country();
            countrySOAP.setName(country.getName());
            countrySOAP.setCapital(country.getCapital());
            countrySOAP.setPopulation(country.getPopulation());
            com.example.springsoap.gen.Currency currencySOAP = com.example.springsoap.gen.Currency.EUR;
            for (Currency currency : country.getCurrencies()) {
                currencySOAP = com.example.springsoap.gen.Currency.valueOf(currency.name());
            }
            countrySOAP.setCurrency(currencySOAP);
        }
        return countrySOAP;
    }
}
