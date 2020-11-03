package com.company.model.user;

import com.company.exceptions.ObjectNotFoundException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserFactoryTest {
    UserFactory userFactory = new UserFactory();


    @Test
    void create_ReturnUserOfTypeStudent_WhenTypeIdEquals3 () throws ObjectNotFoundException {
        //Arrange
        int typeId = 3;
        User expected = new Student.Builder().build();

        //Act
        User actual = userFactory.create(typeId);

        //Assert
        assertEquals(expected.getClass(), actual.getClass());
    }

    @Test
    void create_ReturnUserOfTypeMentor_WhenTypeIdEquals2 () throws ObjectNotFoundException {
        //Arrange
        int typeId = 2;
        User expected = new Mentor.Builder().build();

        //Act
        User actual = userFactory.create(typeId);

        //Assert
        assertEquals(expected.getClass(), actual.getClass());
    }

    @Test
    void create_ThrowObjectNotFoundException_WhenTypeIdIsNotProper () throws ObjectNotFoundException {
        //Arrange
        int typeId = 5;

        //Assert
        assertThrows(ObjectNotFoundException.class,
                () -> userFactory.create(typeId));
    }

}