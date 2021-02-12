package com.reet.util;

import com.opencsv.bean.CsvToBeanBuilder;
import com.reet.domain.Item;
import com.reet.domain.Rule;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.List;

public class TestUtil {

    public static List<Item> getItems(String path) throws FileNotFoundException {

        final List<Item> items = new CsvToBeanBuilder(new FileReader(path))
                .withType(Item.class)
                .build()
                .parse();
        return items;
    }

    public static List<Rule> getRules(String path) throws FileNotFoundException {
        List<Rule> rules = new CsvToBeanBuilder(new FileReader(path))
                .withType(Rule.class)
                .build()
                .parse();
        return rules;
    }
}
