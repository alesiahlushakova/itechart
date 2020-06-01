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
                      id int primary key,
                      first_name nvarchar(50),
                      last_name nvarchar(50),
                      role enum('STUDENT', 'LECTURER')
);
create table training_course (
                                 id int primary key,
                                 name nvarchar(50),
                                 teacher_id int,
                                 constraint training_course_fk foreign key(teacher_id) references user(id)
);

create table exam (
                      id int primary key,
                      date date,
                      teacher_id int,
                      training_course_id int,
                      constraint exam_course_fk foreign key(teacher_id) references user(id),
                      constraint exam_fk foreign key(training_course_id) references training_course(id)
);


create table student_result(

                            student_id int  ,
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
                            id int primary key,
                            student_id int,
                            result int,
                            exam_id int,
                            teacher_id int,
                            constraint ress_exam_fk foreign key(exam_id) references exam(id),
                                constraint traininfg_course_fk foreign key(teacher_id) references user(id),
                            constraint res11s1_fk foreign key(student_id) references user(id)

);

-- 1
select first_name, last_name from user
inner join exam_result er on user.id = er.student_id
where role = 'STUDENT' and  result>2 order by result desc;

-- 2
select count(*) from user
inner join exam_result er on user.id = er.student_id
where role = 'STUDENT' and  result=4 or result=5;

-- 3
select count(*) from student_result sr
                        left join exam_result er on sr.student_id = er.student_id
          where er.student_id is null
;
-- 4
select avg(result) as avg
from student_result
    inner join training_course on student_result.training_course_id=training_course.id
where training_course.name='Системы управления базами данных';
-- 5
select first_name, last_name from user
inner join student_result er on user.id = er.student_id
inner  join training_course on er.training_course_id=training_course.id
where role = 'STUDENT' and result<3 and   training_course.name='Теория графов';

-- 6

SELECT teacher_id
FROM training_course
GROUP BY teacher_id
HAVING COUNT(teacher_id) > 1;


-- 7
SELECT student_id, last_name
FROM exam_result er
         inner join user on user.id = er.student_id
GROUP BY student_id,role
HAVING COUNT(student_id) > 1 and role='STUDENT';

-- 8

SELECT  first_name, last_name, max(result) over (partition by student_id )
FROM exam_result er
         inner join user on user.id = er.student_id
 limit 5;
 -- 9
select last_name,  sum(result)/count(result) as avg
from exam_result er
         inner join user on user.id = er.student_id
         limit 1
-- 10
