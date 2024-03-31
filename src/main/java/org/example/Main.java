package org.example;

import com.sun.jdi.connect.spi.Connection;
import org.example.dao.GroupDao;
import org.example.dao.StudentDao;
import org.example.dao.impl.GroupDaoImpl;
import org.example.dao.impl.StudentDaoImpl;
import org.example.db.ConnectionManager;
import org.example.entity.Groups;
import org.example.entity.Student;

import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws SQLException {
        Groups group = new Groups(2,"philosophical", 10);
        Student student = new Student("zaza", 162, 4);

        StudentDao studentDao = new StudentDaoImpl();

        GroupDao groupDao = new GroupDaoImpl();

//        groupDao.save(group);
//        groupDao.delete(2);

//        System.out.println(groupDao.get(2));
        System.out.println(studentDao.getAll());
//        System.out.println(groupDao.getAll());

    }
}