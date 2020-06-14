package Model.Course;

import Model.Actors.Professor;
import Model.Actors.Student;


import java.util.List;

abstract class FactoryCreationStudent {
    public abstract List<Course> createCourse (List<Student> students, List<Professor> professors, int quantity);

}
