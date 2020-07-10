package com.example.graphqlhello;

import graphql.GraphQL;
import graphql.schema.GraphQLSchema;
import graphql.schema.idl.*;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;

/**
 * @author tangbo
 * @date 2020/7/9 20:07
 */
@Component
public class GraphQLConfig {

    @Bean
    public GraphQL graphQL() throws IOException {
        File schemaConfig = new ClassPathResource("schema.graphql").getFile();
        //dsl
        TypeDefinitionRegistry typeDefinitionRegistry = new SchemaParser().parse(schemaConfig);
        GraphQLSchema graphQLSchema = new SchemaGenerator().makeExecutableSchema(typeDefinitionRegistry, buildWriting());
        return GraphQL.newGraphQL(graphQLSchema).build();
    }


    private RuntimeWiring buildWriting() {
        //dsl 的实现注册
        return RuntimeWiring.newRuntimeWiring()
                .type(
                        TypeRuntimeWiring.newTypeWiring("Query")
                                .dataFetcher("bookById", GraphQLDataFetchers.getBookById()))
                .type(
                        TypeRuntimeWiring.newTypeWiring("Book")
                                .dataFetcher("author", GraphQLDataFetchers.getAuthor()))
                .type(TypeRuntimeWiring.newTypeWiring("Author"))
                .build();
    }


}
