package com.example.domain;

import javax.persistence.*;
import java.util.Set;

@Entity
public class Country {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @ElementCollection(targetClass = Currency.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "currency", joinColumns = @JoinColumn(name = "country_id"))
    @Enumerated(EnumType.STRING)
    private Set<Currency> currencies;

    public Country() {
    }

    public Country(String name, int population, String capital) {
        this.name = name;
        this.population = population;
        this.capital = capital;
    }

    private String name;
    private int population;
    private String capital;

    public String getName() {
        return name;
    }

    public void setName(String value) {
        this.name = value;
    }

    public int getPopulation() {
        return population;
    }

    public void setPopulation(int value) {
        this.population = value;
    }

    public String getCapital() {
        return capital;
    }

    public void setCapital(String value) {
        this.capital = value;
    }

    public Set<Currency> getCurrencies() {
        return currencies;
    }

    public void setCurrencies(Set<Currency> currencies) {
        this.currencies = currencies;
    }
}
