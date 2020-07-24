package com.jonata.swapi.resources;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jonata.swapi.model.Planet;

@ExtendWith({ RestDocumentationExtension.class, SpringExtension.class })
@SpringBootTest
@AutoConfigureRestDocs(outputDir = "target/generated-snippets")
public class PlanetResourceTest {
    
    @Autowired
	private WebApplicationContext context;
	
	private MockMvc mockMvc;

	@BeforeEach
    public void setUp(WebApplicationContext webApplicationContext,
                      RestDocumentationContextProvider restDocumentation) {

        this.mockMvc = MockMvcBuilders
                .webAppContextSetup(webApplicationContext)
                .apply(documentationConfiguration(restDocumentation))
				.build();
	}
	
	@Test
    public void testInsertPlanet() throws Exception {
	    Planet planet = new Planet(null, "Vênus", "Gasoso");
		String planetsJson = new ObjectMapper().writeValueAsString(planet);
		System.out.println(planetsJson);
        mockMvc.perform(post("/planets")
                .content(planetsJson)
                .contentType("application/json")).andDo(print())
                .andExpect(status().isCreated())
                .andDo(print());
    }

    @Test
    public void testFindAll() throws Exception {
           mockMvc.perform(get("/planets")
                   .contentType("application/json")).andDo(print())
                   .andExpect(status().isOk())
                   .andDo(document("{methodName}",
                           preprocessRequest(prettyPrint()),
                           preprocessResponse(prettyPrint())));
    }
}