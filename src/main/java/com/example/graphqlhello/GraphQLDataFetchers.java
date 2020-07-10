package com.example.graphqlhello;

import com.google.common.collect.ImmutableMap;
import graphql.schema.DataFetcher;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * type 查询的实现逻辑
 *
 * @author tangbo
 * @date 2020/7/9 21:00
 */
public class GraphQLDataFetchers {
    private static List<Map<String, String>> books = Arrays.asList(
            ImmutableMap.of("id", "book-1",
                    "name", "Harry Potter and the Philosopher's Stone",
                    "pageCount", "223",
                    "authorId", "author-1"),
            ImmutableMap.of("id", "book-2",
                    "name", "Moby Dick",
                    "pageCount", "635",
                    "authorId", "author-2"),
            ImmutableMap.of("id", "book-3",
                    "name", "Interview with the vampire",
                    "pageCount", "371",
                    "authorId", "author-3")
    );

    private static List<Map<String, String>> authors = Arrays.asList(
            ImmutableMap.of("id", "author-1",
                    "firstName", "Joanne",
                    "lastName", "Rowling"),
            ImmutableMap.of("id", "author-2",
                    "firstName", "Herman",
                    "lastName", "Melville"),
            ImmutableMap.of("id", "author-3",
                    "firstName", "Anne",
                    "lastName", "Rice")
    );

    public static DataFetcher getBookById() {
        return environment -> {
            String id = environment.getArgument("id");
            return books.stream().filter(x -> x.get("id").equals(id)).findFirst()
                    .orElse(null);
        };
    }

    public static DataFetcher getAuthor() {
        return environment -> {
            Map<String, String> book = environment.getSource();
            return authors.stream().filter(x -> x.get("id").equals(book.get("authorId"))).findFirst().orElse(null);
        };
    }


    public static DataFetcher getPageCount() {
        return environment -> {
            Map<String, String> book = environment.getSource();
            return book.get("pageCount");
        };

    }
}
