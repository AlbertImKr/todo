package me.albert.todo;

import static org.springframework.restdocs.restassured.RestAssuredRestDocumentation.documentationConfiguration;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;
import jakarta.validation.constraints.NotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.context.annotation.Import;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.operation.preprocess.OperationRequestPreprocessor;
import org.springframework.restdocs.operation.preprocess.OperationResponsePreprocessor;
import org.springframework.restdocs.operation.preprocess.Preprocessors;

@Import(TestcontainersConfiguration.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ExtendWith(RestDocumentationExtension.class)
public abstract class TodoAcceptanceTest {

    @LocalServerPort
    public int port;
    @Autowired
    protected DatabaseCleaner databaseCleanup;
    protected RequestSpecification spec;

    public static @NotNull OperationRequestPreprocessor prettyPrintRequest() {
        return Preprocessors.preprocessRequest(Preprocessors.prettyPrint());
    }

    public static @NotNull OperationResponsePreprocessor prettyPrintResponse() {
        return Preprocessors.preprocessResponse(Preprocessors.prettyPrint());
    }

    @BeforeEach
    public void setUp(RestDocumentationContextProvider restDocumentation) {
        this.spec = new RequestSpecBuilder()
                .addFilter(documentationConfiguration(restDocumentation))
                .build();
        spec.port(port);
        RestAssured.port = port;
        databaseCleanup.execute();
    }
}
