with works_compiled as (select id as id, (points/authors) as value from articles_tableka order by id), authors_compiled as (select A.Article_ID as id, B.Author as author from authorship_tableka A right join authors_tableka B on A.author = B.author), combined as (select coalesce(A.value, 0) as val, A.id as id, B.author as author, ROW_NUMBER() OVER (PARTITION BY author ORDER BY coalesce(A.value, 0) DESC) AS rank from works_compiled A right join authors_compiled B on A.id = B.id) select author, sum(val) as val from combined where rank <= 4 group by author;


