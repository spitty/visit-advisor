/*
  Creating view for security realm
 */
create view user_group_view as
select u.name, u.password, g.name as group_name
  from user_table u
left join group_table g on (g.ID = u.GROUP_ID);

