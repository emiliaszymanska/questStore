package com.company.service;

import com.company.dao.StudentDao;
import com.company.dao.TransactionDao;
import com.company.exceptions.ObjectNotFoundException;
import com.company.model.Transaction;
import com.company.model.user.Student;
import com.company.model.user.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public class StudentService {

    private StudentDao studentDao;
    private ObjectMapper mapper;

    public StudentService() {
        this.studentDao = new StudentDao();
        this.mapper = new ObjectMapper();
    }

    public String updateStudent(Map<String, String> formData, UUID uuid) throws ObjectNotFoundException, JsonProcessingException {
        User student = studentDao.getBySessionId(uuid);

        populateStudentObjectWithData(formData, student);
        studentDao.update(student);
        System.out.println(student.toString());

        return mapper.writeValueAsString(student);
    }

    public String updateStudent(Map<String, String> formData) throws ObjectNotFoundException, JsonProcessingException {
        int id = Integer.parseInt(formData.get("id"));
        User student = studentDao.getById(id);

        populateStudentObjectWithData(formData, student);
        studentDao.update(student);
        System.out.println(student.toString());

        return mapper.writeValueAsString(student);
    }

    public String removeStudent(Map<String, String> formData) throws ObjectNotFoundException, JsonProcessingException {
        int id = Integer.parseInt(formData.get("id"));
        User student = studentDao.getById(id);

        System.out.println(student.toString());
        studentDao.delete(student);
        studentDao.deleteAdditionalStudentData((Student) student);

        return mapper.writeValueAsString(student);
    }

    private User populateStudentObjectWithData(Map<String, String> formData, User student) {
        student.setFirstName(formData.get("firstName"))
                .setLastName(formData.get("lastName"))
                .setEmail(formData.get("email"))
                .setPhoneNumber(formData.get("phoneNumber"));
        return student;
    }

    public String getStudentBalanceAndExperience(UUID uuid) throws ObjectNotFoundException, JsonProcessingException {
        User student = studentDao.getBySessionId(uuid);
        student = studentDao.getStudentByIdWithAdditionalData(student.getId());

        return mapper.writeValueAsString(student);
    }

    public String getStudentProfile(UUID uuid) throws ObjectNotFoundException, JsonProcessingException {
        User student = studentDao.getBySessionId(uuid);

        return mapper.writeValueAsString(student);
    }

    public String getStudentStore(UUID uuid) throws ObjectNotFoundException {
        User student = studentDao.getBySessionId(uuid);

        return "store";
    }

    public String getStudentQuests(UUID uuid) throws ObjectNotFoundException {
        User student = studentDao.getBySessionId(uuid);

        return "quest";
    }

    public String getStudentWallet(UUID uuid) throws ObjectNotFoundException, JsonProcessingException {
        TransactionDao transactionDao = new TransactionDao();
        User student = studentDao.getBySessionId(uuid);
        List<Transaction> transactions = transactionDao.getTransactionsByStudentId(student.getId());

        return mapper.writeValueAsString(transactions);
    }
}
