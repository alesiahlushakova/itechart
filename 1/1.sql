create database university;
use university;


drop table  training_course;
drop table  exam;
drop table exam_result;
drop table user;
select * from student_result;
select * from training_course;
select * from exam;


create table user(
                      id int auto_increment primary key,
                      first_name nvarchar(50),
                      last_name nvarchar(50),
                      role enum('STUDENT', 'LECTURER')
);



create table training_course (
                                 id int auto_increment primary key,
                                 name nvarchar(50),
                                 teacher_id int,
                                 constraint training_course_fk foreign key(teacher_id) references user(id)
);

create table exam (
                      id int auto_increment primary key,
                      date date,
                      teacher_id int,
                      training_course_id int,
                      constraint exam_course_fk foreign key(teacher_id) references user(id),
                      constraint exam_fk foreign key(training_course_id) references training_course(id)
);



create table student_result(

                            student_id   int auto_increment auto_increment  ,
                            result int,
                            date date,
                            exam_id int,
                            training_course_id int  ,
                            primary key (student_id,training_course_id),
                            constraint res2_fk foreign key(training_course_id) references training_course(id),
                            constraint res111_fk foreign key(student_id) references user(id),
                            constraint res_exam_fk foreign key(exam_id) references exam(id)
);



create table exam_result(
                            id int auto_increment primary key,
                            student_id int,
                            result int,
                            exam_id int,
                            teacher_id int,
                            constraint ress_exam_fk foreign key(exam_id) references exam(id),
                                constraint traininfg_course_fk foreign key(teacher_id) references user(id),
                            constraint res11s1_fk foreign key(student_id) references user(id)

);


-- 1 Выбрать имена и фамилии студентов, успешно сдавших экзамен, упорядоченных по результату экзамена (отличники первые в результате)
select  distinct first_name, last_name from user
inner join exam_result er on user.id = er.student_id
where role = 'STUDENT' and  result>2 order by result desc;

-- 2 Посчитать количество студентов, успешно сдавших экзамен на 4 и 5
select count(*) from user
inner join exam_result er on user.id = er.student_id
where role = 'STUDENT' and  result=4 or result=5;

-- 3 Посчитать количество студентов, сдавших экзамен “автоматом” (нет записи в таблице exam_result но есть положительный результат в таблице student_result) 
select count(*) from student_result sr
                        left join exam_result er on sr.student_id = er.student_id
          where er.student_id is null and er.exam_id is null
;
-- 4 Посчитать средний балл студентов по предмету с наименованием “Системы управления базами данных” 
select avg(result) as avg
from student_result
    inner join training_course on student_result.training_course_id=training_course.id
where training_course.name='Системы управления базами данных';
-- 5 Выбрать имена и фамилии студентов, не сдававших экзамен по предмету “Теория графов” (2 вида запроса)
select first_name, last_name from user
inner join student_result er on user.id = er.student_id
inner  join training_course on er.training_course_id=training_course.id
where role = 'STUDENT' and result<3 and   training_course.name='Теория графов';

-- 6 Выбрать идентификатор преподавателей, читающих лекции по больше чем по 2 предметам

SELECT teacher_id
FROM training_course
GROUP BY teacher_id
HAVING COUNT(teacher_id) > 2;


-- 7 Выбрать идентификатор и фамилии студентов, пересдававших хотя бы 1 предмет (2 и более записи в exam_result)
SELECT student_id, last_name
FROM exam_result er
         inner join user on user.id = er.student_id
GROUP BY student_id,role
HAVING COUNT(student_id) > 1 and role='STUDENT';

-- 8 Вывести имена и фамилии 5 студентов с максимальными оценками


 SELECT distinct  first_name, last_name, max(result) over (partition by student_id ) as max_mark
FROM exam_result er
         inner join user on user.id = er.student_id
         order by result desc
 limit 5;

 -- 9 Вывести фамилию преподавателя, у которого наилучшие результаты по его предметам
select distinct last_name,  avg(result)  over (partition by teacher_id ) as avg
from exam_result er
         inner join user on user.id = er.teacher_id
         order by result desc
         limit 1
-- 10 Вывести успеваемость студентов по годам по предмету “Математическая статистика” 
select first_name, last_name, result, EXTRACT(YEAR FROM date) as year
from student_result er
         inner join user on user.id = er.student_id
        where where role = 'STUDENT' and training_course.name='Математическая статистика'
         
         

