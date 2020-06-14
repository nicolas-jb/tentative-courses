package Model.Course;

import Exceptions.QuantityCanNotBeLessThanOneInCourseCreation;
import Exceptions.StudentsAndProfessorsRequiredToCreateACourse;
import Model.Actors.Professor;
import Model.Actors.Student;
import Model.Level.Level;
import Utils.SortListOfStudents;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ExactCreation extends FactoryCreationStudent {
    private List<Course> course = new ArrayList<>();


    @Override
    public List<Course> createCourse(List<Student> students, List<Professor> professors, int quantity) {
        if (students == null || professors == null)
            throw new StudentsAndProfessorsRequiredToCreateACourse();
        if (quantity < 1)
            throw new QuantityCanNotBeLessThanOneInCourseCreation();


        for (int i = 0; professors.size() - i > 0; i++) {

            List<LocalDateTime> dateAvailable = professors.get(i).getDateFromAvailability();

            for (int j = 0; dateAvailable.size() - j > 0; j++) {

                LocalDateTime possibleDate = dateAvailable.get(j);

                List<Student> studentsWhoCanTakeTheCourse = students.stream().filter(
                        student -> student.canTakeTheCourse(possibleDate)).collect(Collectors.toList());

                //I separate students by modality
                List<List<Student>> groupStudent = this.obtainGroupOfStudentsSortedByQuantity(studentsWhoCanTakeTheCourse);

                List<Student> individualStudents = studentsWhoCanTakeTheCourse.stream().filter(
                        student -> !student.getModality().acceptAnotherStudent()).collect(Collectors.toList());


                //I give priority to groups, and I take the first because is ordered by quantity
                if (groupStudent.size() > 0) {
                    //
                    if (groupStudent.get(0).size() > 0 && groupStudent.get(0).size() < (quantity - 1)) {
                        course.add(new Course(professors.get(i), groupStudent.get(0),
                                groupStudent.get(0).get(0).getLevel(), possibleDate));

                        groupStudent.get(0).stream().forEach(student -> student.confirmAvailabilityByDate(possibleDate));
                        professors.get(i).confirmAvailabilityByDate(possibleDate);

                    } else if (groupStudent.get(0).size() > quantity) {
                        course.add(new Course(professors.get(i), groupStudent.get(0).subList(0, quantity),
                                groupStudent.get(0).get(0).getLevel(), possibleDate));

                        groupStudent.get(0).subList(0, quantity).stream().forEach(student ->
                                student.confirmAvailabilityByDate(possibleDate));
                        professors.get(i).confirmAvailabilityByDate(possibleDate);
                    }
                } else if (individualStudents.size() > 0) {
                    course.add(new Course(professors.get(i), individualStudents.subList(0, 1),
                            individualStudents.get(0).getLevel(), possibleDate));
                    individualStudents.get(0).confirmAvailabilityByDate(possibleDate);
                    professors.get(i).confirmAvailabilityByDate(possibleDate);
                }

            }
        }
        return course;
    }

    public List<List<Student>> obtainGroupOfStudentsSortedByQuantity(List<Student> studentsWhoCanTakeTheCourse) {
        //This is not effective if the levels scale but in reality there should be no others.
        List<Student> groupBegginers = studentsWhoCanTakeTheCourse.stream().filter(
                student -> student.getModality().acceptAnotherStudent() && student.getLevel() == Level.BEGGINER).collect(Collectors.toList());
        List<Student> groupPreInt = studentsWhoCanTakeTheCourse.stream().filter(
                student -> student.getModality().acceptAnotherStudent() && student.getLevel() == Level.PRE_INTERMEDIATE).collect(Collectors.toList());
        List<Student> groupInt = studentsWhoCanTakeTheCourse.stream().filter(
                student -> student.getModality().acceptAnotherStudent() && student.getLevel() == Level.INTERMEDIATE).collect(Collectors.toList());
        List<Student> groupUpInt = studentsWhoCanTakeTheCourse.stream().filter(
                student -> student.getModality().acceptAnotherStudent() && student.getLevel() == Level.UPPER_INTERMEDIATE).collect(Collectors.toList());
        List<Student> groupAdvanced = studentsWhoCanTakeTheCourse.stream().filter(
                student -> student.getModality().acceptAnotherStudent() && student.getLevel() == Level.ADVANCED).collect(Collectors.toList());

        List<List<Student>> auxiliarList = Arrays.asList(groupBegginers, groupPreInt, groupInt, groupUpInt, groupAdvanced);

        return new SortListOfStudents().sortStudents(auxiliarList);
    }


}
