package Utils;

import Model.Actors.Student;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class SortListOfStudents {

    List<List<Student>> course = new ArrayList<List<Student>>();

    public List<List<Student>> sortStudents (List<List<Student>> students){
        for(int i=0;i<students.size();i++){
            for(int j=0;j<students.size()-i-1;j++){
                if( (students.get(j)).size() < (students.get(j+1)).size() ){
                    Collections.swap(students, j, j+1);
                }
            }
        }

        for (int i = 0; students.get(i).size()>0 ; i++){
            course.add(students.get(i));
        }

        return  course;
    }

}
