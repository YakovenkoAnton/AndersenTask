INSERT INTO feedback (id, description, date) VALUES (1, 'test_Descriprion_1', '2021-08-01');

INSERT INTO team(id) VALUES (1);

INSERT INTO public.project(
    id, name, customer, duration, methodology, project_manager_id, team_id)
VALUES (1, 'firstProject', 'customer', 5, 'method', null, 1);

INSERT INTO public.project(
    id, name, customer, duration, methodology, project_manager_id, team_id)
VALUES (2, 'secondProject', 'customer2', 50, 'method2', null, 1);


INSERT INTO employee(id, name, surname, midname, email, phone, birthdate, experience, employment_date, project_id, developer_level, english_level, skype, feedback_id, team_id)
VALUES (1, 'testName', 'surname', 'midname', 'email', 'phone', '2021-08-01', 5, '2021-08-02', 1, 'J1', 'englishlevel', 'skype', 1, 1);

INSERT INTO employee(id, name, surname, midname, email, phone, birthdate, experience, employment_date, project_id, developer_level, english_level, skype, feedback_id, team_id)
VALUES (2, 'testName2', 'surname', 'midname', 'email', 'phone', '2021-08-01', 10, '2021-08-02', 2, 'J2', 'enlishlevel2', 'skype2', 1, 1);

UPDATE public.project SET project_manager_id=1;
