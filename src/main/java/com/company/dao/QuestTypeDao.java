package com.company.dao;

import com.company.model.QuestType;

import java.sql.ResultSet;
import java.sql.SQLException;

public class QuestTypeDao extends TypeDao<QuestType> {

    public QuestTypeDao() {
        super("quest_types");

        selectTypeStatement = "SELECT * FROM quest_types WHERE id = ?";
    }

    @Override
    QuestType parseResultSet(ResultSet resultSet) throws SQLException {
        return QuestType.get(resultSet.getInt("id"));
    }
}
