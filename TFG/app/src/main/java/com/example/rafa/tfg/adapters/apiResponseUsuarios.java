package com.example.rafa.tfg.adapters;

import java.util.List;

/**
 * Created by Rafael on 13/03/2018.
 */

public class apiResponseUsuarios {
    private List<usuAdapter> results;

    public apiResponseUsuarios(List<usuAdapter> results) {
        this.results = results;
    }

    public List<usuAdapter> getResults() {
        return results;
    }
}
