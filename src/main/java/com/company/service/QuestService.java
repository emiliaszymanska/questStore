package com.company.service;

import com.company.dao.QuestDao;
import com.company.exceptions.ObjectNotFoundException;
import com.company.model.Quest;
import com.company.model.QuestType;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Map;

public class QuestService {

    private QuestDao questDao;
    private ObjectMapper mapper;
    private Quest quest;

    public QuestService() {
        this.questDao = new QuestDao();
        this.mapper = new ObjectMapper();
    }

    public QuestService(QuestDao questDao, ObjectMapper mapper) {
        this.questDao = questDao;
        this.mapper = mapper;
    }

    public String getAll() throws ObjectNotFoundException, JsonProcessingException {
        return mapper.writeValueAsString(questDao.getAll());
    }

    public String getById(int id) throws ObjectNotFoundException, JsonProcessingException {
        return mapper.writeValueAsString(questDao.getById(id));
    }

    public String addQuest(Map<String, String> formData) throws ObjectNotFoundException, JsonProcessingException {
        quest = createQuest(formData);
        questDao.insert(quest);
        return mapper.writeValueAsString(quest);
    }

    public String updateQuest(Map<String, String> formData) throws ObjectNotFoundException, JsonProcessingException {
        quest = questDao.getById(Integer.parseInt(formData.get("id")));
        getQuestData(formData, quest);
        questDao.update(quest);
        return mapper.writeValueAsString(quest);
    }

    public String deleteQuest(Map<String, String> formData) throws ObjectNotFoundException, JsonProcessingException {
        quest = questDao.getById(Integer.parseInt(formData.get("id")));
        questDao.delete(quest);
        return mapper.writeValueAsString(quest);
    }

    public Quest createQuest(Map<String, String> formData) {
        quest = new Quest();
        return getQuestData(formData, quest);
    }

    private Quest getQuestData(Map<String, String> formData, Quest quest) {
        quest.setName(formData.get("name"))
                .setDescription(formData.get("description"))
                .setReward(Integer.parseInt(formData.get("reward")))
                .setExperience(Integer.parseInt(formData.get("experience")))
                .setType(QuestType.valueOf(formData.get("type")));
        return quest;
    }

}
