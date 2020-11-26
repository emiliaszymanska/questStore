package com.company.dao;

import com.company.model.Quest;
import com.company.model.StudentQuests;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class StudentQuestDao {

    private PreparedStatement preparedStatement;
    private ResultSet resultSet;
    private QuestDao questDao;

    private final Connector CONNECTOR;
    private final String byStudentId = " WHERE student_id = ?";

    String selectAllStatement = "SELECT * FROM quests INNER JOIN student_quests sq on quests.id = sq.quest_id ";

    public StudentQuestDao() {
        CONNECTOR = new Connector();
        questDao = new QuestDao();
    }

    public List<Quest> getQuestsByStudentId(String query, int id) {
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
        return getQuestsByStudentId(selectAllStatement + byStudentId, studentId);
    }

    public void insertQuestToList(StudentQuests studentQuests) {
        String insertStatement = "INSERT INTO student_quests (achievement_date, quest_id, student_id) VALUES (?, ?, ?)";

        try {
            CONNECTOR.connect();
            preparedStatement = CONNECTOR.connection.prepareStatement(insertStatement);
            preparedStatement.setDate(1, Date.valueOf(studentQuests.getDate()));
            preparedStatement.setInt(2, studentQuests.getQuest().getId());
            preparedStatement.setInt(3, studentQuests.getStudent().getId());
            preparedStatement.executeQuery();

            preparedStatement.close();
            CONNECTOR.connection.close();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}