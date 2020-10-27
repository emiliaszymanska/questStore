package com.company.service;

import com.company.dao.QuestDao;
import com.company.exceptions.ObjectNotFoundException;
import com.company.model.Quest;
import com.company.model.QuestType;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

class QuestServiceTest {

    @Test
    public void testAddingQuest() throws JsonProcessingException, ObjectNotFoundException {
        QuestDao questDao = Mockito.mock(QuestDao.class);
        ObjectMapper mapper = Mockito.mock(ObjectMapper.class);

        Map<String, String> map = new HashMap<>();
        map.put("reward", "1000");
        map.put("experience", "100");
        map.put("type", "MODERATE");

        QuestService questService = new QuestService(questDao, mapper);
        questService.addQuest(map);

        assertAll(
                () -> Mockito.verify(questDao, Mockito.times(1)).insert(Mockito.any()),
                () -> Mockito.verify(mapper, Mockito.times(1)).writeValueAsString(Mockito.any())
        );
    }

    @Test
    public void testUpdateQuest() throws ObjectNotFoundException, JsonProcessingException {
        QuestDao questDao = Mockito.mock(QuestDao.class);
        ObjectMapper mapper = Mockito.mock(ObjectMapper.class);

        Map<String, String> map = new HashMap<>();
        map.put("id", "16");
        map.put("name", "test1");
        map.put("description", "test1");
        map.put("reward", "1000");
        map.put("experience", "100");
        map.put("type", "EASY");

        Quest quest = new Quest();
        Mockito.when(questDao.getById(16)).thenReturn(quest);
        Quest expected = new Quest(0, "test1", "test1", 1000, 100, QuestType.EASY);

        QuestService questService = new QuestService(questDao, mapper);
        questService.updateQuest(map);

        assertAll(
                () -> Mockito.verify(questDao, Mockito.times(1)).update(quest),
                () -> Mockito.verify(mapper, Mockito.times(1)).writeValueAsString(quest),
                () -> assertEquals(expected, quest)
        );
    }

    @Test
    public void testDeleteQuest() throws ObjectNotFoundException, JsonProcessingException {
        QuestDao questDao = Mockito.mock(QuestDao.class);
        ObjectMapper mapper = Mockito.mock(ObjectMapper.class);

        Map<String, String> map = new HashMap<>();
        map.put("id", "15");

        QuestService questService = new QuestService(questDao, mapper);
        questService.deleteQuest(map);

        assertAll(
                () -> Mockito.verify(questDao, Mockito.times(1)).delete(Mockito.any()),
                () -> Mockito.verify(mapper, Mockito.times(1)).writeValueAsString(Mockito.any())
        );
    }

    @Test
    public void testGetAll() throws ObjectNotFoundException, JsonProcessingException {
        QuestDao questDao = Mockito.mock(QuestDao.class);
        ObjectMapper mapper = Mockito.mock(ObjectMapper.class);

        QuestService questService = new QuestService(questDao, mapper);
        questService.getAll();

        assertAll(
                () -> Mockito.verify(questDao, Mockito.times(1)).getAll()
        );
    }

    @Test
    public void testGetById() throws ObjectNotFoundException, JsonProcessingException {
        QuestDao questDao = Mockito.mock(QuestDao.class);
        ObjectMapper mapper = Mockito.mock(ObjectMapper.class);

        QuestService questService = new QuestService(questDao, mapper);
        questService.getById(Mockito.anyInt());

        assertAll(
                () -> Mockito.verify(questDao, Mockito.times(1)).getById(Mockito.anyInt())
        );
    }
}