package hw_4;

import java.util.*;
import java.util.stream.Collectors;

public class StreamTask2 {
    public static void main(String[] args) {
        List<Student> students = Arrays.asList(
                new Student("Student1", Map.of("Math", 90, "Physics", 85)),
                new Student("Student2", Map.of("Math", 95, "Physics", 88)),
                new Student("Student3", Map.of("Math", 88, "Chemistry", 92)),
                new Student("Student4", Map.of("Physics", 78, "Chemistry", 85))
        );

        /*
        * - Создайте коллекцию студентов, где каждый студент
          * содержит информацию о предметах, которые он изучает, и его оценках по этим предметам.
          - Используйте Parallel Stream для обработки данных и создания Map,
          * где ключ - предмет, а значение - средняя оценка по всем студентам.
          - Выведите результат: общую Map со средними оценками по всем предметам.
          * */

        System.out.println(students.parallelStream()
                .flatMap(student -> student.getGrades().entrySet().stream())
                .collect(Collectors.groupingBy(Map.Entry::getKey,
                        Collectors.averagingDouble(Map.Entry::getValue))));
    }

    static class Student {
        private final String name;
        private final Map<String, Integer> grades;

        public Student(String name, Map<String, Integer> grades) {
            this.name = name;
            this.grades = grades;
        }

        public Map<String, Integer> getGrades() {
            return grades;
        }

        @Override
        public String toString() {
            return String.format("Student name = %s, grades = %s}", name, grades);
        }
    }
}

