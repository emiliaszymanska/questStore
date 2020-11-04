package com.company.dao;

import com.company.model.Quest;
import com.company.model.StudentQuests;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class StudentQuestDao {

    private QuestTypeDao questTypeDao;
    private UserDao userDao;
    private QuestDao questDao;
    private PreparedStatement preparedStatement;
    private ResultSet resultSet;

    String selectAllStatement = "SELECT * FROM quests INNER JOIN student_quests sq on quests.id = sq.quest_id ";
    String insertStatement = "INSERT INTO student_quests (achievement_date, quest_id, student_id) VALUES (?, ?, ?)";

    private final Connector CONNECTOR;
    private final String insertTransaction = "";
    private final String byStudentId = " WHERE student_id = ?";

    public StudentQuestDao() {
        this.userDao = new UserDao();
        this.questTypeDao = new QuestTypeDao();
        this.questDao = new QuestDao();
        CONNECTOR = new Connector();
    }

    public List<Quest> getQuestById(String query, int id) {
        List<Quest> questList = new ArrayList<>();

        try {
            CONNECTOR.connect();
            preparedStatement = CONNECTOR.connection.prepareStatement(query);
            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                questList.add(questDao.getQuest(resultSet));
                questDao.getQuest(resultSet);
            }

            resultSet.close();
            preparedStatement.close();
            CONNECTOR.connection.close();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return questList;
    }

    public List<Quest> getByUserId(int studentId) {
        return getQuestById(selectAllStatement + byStudentId, studentId);
    }
}
