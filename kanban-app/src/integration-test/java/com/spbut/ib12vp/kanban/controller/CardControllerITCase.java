package com.spbut.ib12vp.kanban.controller;

import com.spbut.ib12vp.kanban.model.Card;
import com.spbut.ib12vp.kanban.repository.KanbanRepository;
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
public class CardControllerITCase extends CommonITCase {

    private String baseURL;

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private KanbanRepository kanbanRepository;

    @Before
    public void setUp(){
        baseURL = "http://localhost:" + port;
    }

    @Test
    public void whenGetAllTasks_thenReceiveSingleTask(){

        //given
        Card card = saveSingleTask();

        //when
        ResponseEntity<List<Card>> response = this.restTemplate.exchange(
                baseURL + "tasks/",
                HttpMethod.GET,
                new HttpEntity<>(new HttpHeaders()),
                new ParameterizedTypeReference<List<Card>>() {});

        //then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody().size() >= 1);
    }

    @Test
    public void whenGetSingleTaskById_thenReceiveSingleTask(){

        //given
        Card card = saveSingleTask();

        //when
        ResponseEntity<Card> response = this.restTemplate.exchange(
                baseURL + "tasks/" + card.getId(),
                HttpMethod.GET,
                new HttpEntity<>(new HttpHeaders()),
                Card.class);

        //then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(card, response.getBody());
    }

    @Test
    public void whenPostSingleTask_thenItIsStoredInDb(){

        //given
        Card card = createSingleTask();

        //when
        ResponseEntity<Card> response = this.restTemplate.exchange(
                baseURL + "tasks/",
                HttpMethod.POST,
                new HttpEntity<>(convertTaskToDTO(card), new HttpHeaders()),
                Card.class);

        //then
        assertEquals(HttpStatus.CREATED, response.getStatusCode());

            // check response Task
        Card responseCard = response.getBody();
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
    public void whenPostSingleTaskWithKanbanAssignment_thenItIsStoredInDb(){

        //given
        Card card = createSingleTask();
        saveSingleRandomKanban();

        //when
        ResponseEntity<Card> response = this.restTemplate.exchange(
                baseURL + "tasks/",
                HttpMethod.POST,
                new HttpEntity<>(convertTaskToDTO(card), new HttpHeaders()),
                Card.class);

        //then
        assertEquals(HttpStatus.CREATED, response.getStatusCode());

        // check response Task
        Card responseCard = response.getBody();
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
    public void whenPutSingleTask_thenItIsUpdated(){

        //given
        Card card = saveSingleTask();
        card.setName(card.getName() + " Updated");

        //when
        ResponseEntity<Card> response = this.restTemplate.exchange(
                baseURL + "tasks/" + card.getId(),
                HttpMethod.PUT,
                new HttpEntity<>(convertTaskToDTO(card), new HttpHeaders()),
                Card.class);

        //then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(card.getName(), findTaskInDbById(card.getId()).get().getName());
    }

    @Test
    public void whenDeleteSingleTaskById_thenItIsDeletedFromDb(){

        //given
        Card card = saveSingleTask();

        //when
        ResponseEntity<String> response = this.restTemplate.exchange(
                baseURL + "tasks/" + card.getId(),
                HttpMethod.DELETE,
                new HttpEntity<>(new HttpHeaders()),
                String.class);

        //then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(String.format("Task with id: %d was deleted", card.getId()), response.getBody());
        assertFalse(findTaskInDbById(card.getId()).isPresent());
    }
}
