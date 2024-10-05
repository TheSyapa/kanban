package com.spbut.ib12vp.kanban.controller;

import com.spbut.ib12vp.kanban.model.Card;
import com.spbut.ib12vp.kanban.model.Project;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ProjectControllerITCase extends CommonITCase {

    private String baseURL;

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Before
    public void setUp(){
        baseURL = "http://localhost:" + port;
    }

    @Test
    public void whenGetAllKanbans_thenReceiveSingleKanban(){

        //given
        saveSingleRandomKanban();

        //when
        ResponseEntity<List<Project>> response = this.restTemplate.exchange(
                                                baseURL + "kanbans/",
                                                    HttpMethod.GET,
                                                    new HttpEntity<>(new HttpHeaders()),
                                                    new ParameterizedTypeReference<List<Project>>() {});

        //then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody().size() >= 1);
    }

    @Test
    public void whenGetSingleKanbanById_thenReceiveSingleKanban(){

        //given
        Project project = saveSingleRandomKanban();

        //when
        ResponseEntity<Project> response = this.restTemplate.exchange(
                baseURL + "kanbans/" + project.getId(),
                HttpMethod.GET,
                new HttpEntity<>(new HttpHeaders()),
                Project.class);

        //then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(project.getId(), response.getBody().getId());
        assertEquals(project.getName(), response.getBody().getName());
    }

    @Test
    public void whenGetAllTasksForKanbanById_thenReceiveTasksList(){

        //given
        Project project = saveSingleKanbanWithOneTask();

        //when
        ResponseEntity<List<Card>> response = this.restTemplate.exchange(
                baseURL + "kanbans/" + project.getId() + "/tasks/",
                HttpMethod.GET,
                new HttpEntity<>(new HttpHeaders()),
                new ParameterizedTypeReference<List<Card>>() {});

        //then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(project.getCards().get(0).getId(), response.getBody().get(0).getId());
        assertEquals(project.getCards().get(0).getName(), response.getBody().get(0).getName());
        assertEquals(project.getCards().get(0).getDescription(), response.getBody().get(0).getDescription());
        assertEquals(project.getCards().get(0).getColor(), response.getBody().get(0).getColor());
    }

    @Test
    public void whenGetSingleKanbanByName_thenReceiveSingleKanban(){

        //given
        Project project = saveSingleRandomKanban();

        //when
        ResponseEntity<Project> response = this.restTemplate.exchange(
                baseURL + "kanbans?title=" + project.getName(),
                HttpMethod.GET,
                new HttpEntity<>(new HttpHeaders()),
                Project.class);

        //then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(project.getId(), response.getBody().getId());
        assertEquals(project.getName(), response.getBody().getName());
    }

    @Test
    public void whenPostSingleKanban_thenItIsStoredInDb(){

        //given
        Project project = createSingleKanban();

        //when
        ResponseEntity<Project> response = this.restTemplate.exchange(
                baseURL + "kanbans/",
                HttpMethod.POST,
                new HttpEntity<>(convertKanbanToDTO(project), new HttpHeaders()),
                Project.class);

        //then
        assertEquals(HttpStatus.CREATED, response.getStatusCode());

            // check response Kanban
        Project responseProject = response.getBody();
        assertNotNull(responseProject.getId());
        assertEquals(project.getName(), responseProject.getName());

            // check Kanban saved in db
        Project savedProject = findKanbanInDbById(responseProject.getId()).get();
        assertEquals(project.getName(), savedProject.getName());
    }

    @Test
    public void whenPostSingleTaskToAlreadyCreatedKanban_thenItIsStoredInDbAndAssignedToKanban(){

        //given
        Project project = saveSingleRandomKanban();
        Card card = createSingleTask();

        //when
        ResponseEntity<Project> response = this.restTemplate.exchange(
                baseURL + "kanbans/" + project.getId() + "/tasks/",
                HttpMethod.POST,
                new HttpEntity<>(convertTaskToDTO(card), new HttpHeaders()),
                Project.class);

        //then
        assertEquals(HttpStatus.CREATED, response.getStatusCode());

        // check response Kanban
        Project responseProject = response.getBody();
        assertNotNull(responseProject.getId());
        assertEquals(project.getName(), responseProject.getName());
        assertTrue(responseProject.getCards().size() == 1);

        Card responseCard = responseProject.getCards().get(0);
        // check response Task
        assertNotNull(responseCard.getId());
        assertEquals(card.getName(), responseCard.getName());
        assertEquals(card.getDescription(), responseCard.getDescription());
        assertEquals(card.getColor(), responseCard.getColor());
        Assert.assertEquals(card.getStatus(), responseCard.getStatus());

        // check saved Task in db
        Card savedCard = findTaskInDbById(responseCard.getId()).get();
        assertEquals(responseCard.getId(), savedCard.getId());
        assertEquals(card.getName(), savedCard.getName());
        assertEquals(card.getDescription(), savedCard.getDescription());
        assertEquals(card.getColor(), savedCard.getColor());
        Assert.assertEquals(card.getStatus(), savedCard.getStatus());
    }


    @Test
    public void whenPutSingleKanban_thenItIsUpdated(){

        //given
        Project project = saveSingleRandomKanban();
        project.setName(project.getName() + " Updated");

        //when
        ResponseEntity<Project> response = this.restTemplate.exchange(
                baseURL + "kanbans/" + project.getId(),
                HttpMethod.PUT,
                new HttpEntity<>(convertKanbanToDTO(project), new HttpHeaders()),
                Project.class);

        //then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(project.getName(), findKanbanInDbById(project.getId()).get().getName());
    }

    @Test
    public void whenDeleteSingleKanbanById_thenItIsDeletedFromDb(){

        //given
        Project project = saveSingleRandomKanban();

        //when
        ResponseEntity<String> response = this.restTemplate.exchange(
                baseURL + "kanbans/" + project.getId(),
                HttpMethod.DELETE,
                new HttpEntity<>(new HttpHeaders()),
                String.class);

        //then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(String.format("Kanban with id: %d was deleted", project.getId()), response.getBody());
        assertFalse(findKanbanInDbById(project.getId()).isPresent());
    }
}
