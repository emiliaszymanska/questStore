package com.company.dao;

import com.company.model.Quest;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class QuestDao extends Dao<Quest> {

    private QuestTypeDao questTypeDao;
    private List<Quest> allQuests;

    public QuestDao() {
        super("quests");
        this.questTypeDao = new QuestTypeDao();
        this.allQuests = new ArrayList<>();

        selectStatement = "SELECT * FROM quests WHERE id = ?";
        selectAllStatement = "SELECT * FROM quests";
        insertStatement = "INSERT INTO quests (name, description, reward, experience, quest_type_id) VALUES (?, ?, ?, " +
                "?, ?)";
        updateStatement = "UPDATE quests SET name = ?, description = ?, reward = ?, experience = ?, quest_type_id = ? " +
                "WHERE id = ?";
        deleteStatement = "DELETE FROM quests WHERE id = ?";
    }

    @Override
    Quest parseResultSet(ResultSet resultSet) throws SQLException {
        return getQuest(resultSet);
    }

    @Override
    List<Quest> getAllObjects(ResultSet resultSet) throws SQLException {
        while (resultSet.next()) {
            allQuests.add(getQuest(resultSet));
        }
        return allQuests;
    }

    @Override
    void specifyPreparedStatement(Quest quest) throws SQLException {
        preparedStatement.setString(1, quest.getName());
        preparedStatement.setString(2, quest.getDescription());
        preparedStatement.setInt(3, quest.getReward());
        preparedStatement.setInt(4, quest.getExperience());
        preparedStatement.setInt(5, quest.getType().getId());
    }

    @Override
    void addIdToUpdateStatement(Quest quest) throws SQLException {
        preparedStatement.setInt(6, quest.getId());
    }

    @Override
    void specifyDeleteStatement(Quest quest) throws SQLException {
        preparedStatement.setInt(1, quest.getId());
    }

    private Quest getQuest(ResultSet resultSet) throws SQLException {
        Quest quest = new Quest();
        quest.setId(resultSet.getInt("id"))
                .setName(resultSet.getString("name"))
                .setDescription(resultSet.getString("description"))
                .setReward(resultSet.getInt("reward"))
                .setExperience(resultSet.getInt("experience"))
                .setType(questTypeDao.getTypeById(resultSet.getInt("quest_type_id")));

        return quest;
    }
}
