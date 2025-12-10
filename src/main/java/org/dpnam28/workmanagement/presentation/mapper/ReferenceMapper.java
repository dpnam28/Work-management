package org.dpnam28.workmanagement.presentation.mapper;

import org.dpnam28.workmanagement.domain.entity.ClassSubject;
import org.dpnam28.workmanagement.domain.entity.Faculty;
import org.dpnam28.workmanagement.domain.entity.Major;
import org.dpnam28.workmanagement.domain.entity.Plan;
import org.dpnam28.workmanagement.domain.entity.Semester;
import org.dpnam28.workmanagement.domain.entity.Student;
import org.dpnam28.workmanagement.domain.entity.Subject;
import org.dpnam28.workmanagement.domain.entity.Task;
import org.dpnam28.workmanagement.domain.entity.Teacher;
import org.dpnam28.workmanagement.domain.entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ReferenceMapper {
    default Faculty toFaculty(Long id) {
        if (id == null) {
            return null;
        }
        Faculty faculty = new Faculty();
        faculty.setId(id);
        return faculty;
    }

    default Major toMajor(Long id) {
        if (id == null) {
            return null;
        }
        Major major = new Major();
        major.setId(id);
        return major;
    }

    default User toUser(Long id) {
        if (id == null) {
            return null;
        }
        User user = new User();
        user.setId(id);
        return user;
    }

    default Teacher toTeacher(Long id) {
        if (id == null) {
            return null;
        }
        Teacher teacher = new Teacher();
        teacher.setId(id);
        return teacher;
    }

    default Student toStudent(Long id) {
        if (id == null) {
            return null;
        }
        Student student = new Student();
        student.setId(id);
        return student;
    }

    default Subject toSubject(Long id) {
        if (id == null) {
            return null;
        }
        Subject subject = new Subject();
        subject.setId(id);
        return subject;
    }

    default Semester toSemester(Long id) {
        if (id == null) {
            return null;
        }
        Semester semester = new Semester();
        semester.setId(id);
        return semester;
    }

    default ClassSubject toClassSubject(Long id) {
        if (id == null) {
            return null;
        }
        ClassSubject classSubject = new ClassSubject();
        classSubject.setId(id);
        return classSubject;
    }

    default Plan toPlan(Long id) {
        if (id == null) {
            return null;
        }
        Plan plan = new Plan();
        plan.setId(id);
        return plan;
    }

    default Task toTask(Long id) {
        if (id == null) {
            return null;
        }
        Task task = new Task();
        task.setId(id);
        return task;
    }
}
