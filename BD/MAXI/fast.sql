-- unique d->e
WITH unique_help0 AS (
  SELECT DISTINCT src, tgt
  FROM e
),
-- d->e (max4)
help0 AS (
	SELECT src, tgt
	FROM (
		SELECT src, tgt, ROW_NUMBER() OVER (PARTITION BY src ORDER BY tgt) AS rn
		FROM unique_help0
	) AS ranked_edges
	WHERE rn <= 30
),

-- unique c->d->e
unique_help1 AS (
	SELECT DISTINCT one.src, two.tgt
	FROM e one JOIN help0 two ON one.tgt = two.src
),
-- c->d->e
help1 AS (
	SELECT src, tgt
	FROM (
		SELECT src, tgt, ROW_NUMBER() OVER (PARTITION BY src ORDER BY tgt) AS rn
		FROM unique_help1
	) AS ranked_edges
	WHERE rn <= 4
),

-- unique b->c->d->e
unique_help2 AS (
	SELECT DISTINCT one.src, two.tgt
	FROM e one JOIN help1 two ON one.tgt = two.src
),
-- b->c->d->e
help2 AS (
	SELECT src, tgt
	FROM (
		SELECT src, tgt, ROW_NUMBER() OVER (PARTITION BY src ORDER BY tgt) AS rn
		FROM unique_help2
	) AS ranked_edges
	WHERE rn <= 4
),

-- unique a->b->c->d->e
unique_help3 AS (
	SELECT DISTINCT one.src, two.tgt
	FROM e one JOIN help2 two ON one.tgt = two.src
),
-- a->b->c->d->e
help3 AS (
	SELECT src, tgt
	FROM (
		SELECT src, tgt, ROW_NUMBER() OVER (PARTITION BY src ORDER BY tgt) AS rn
		FROM unique_help3
	) AS ranked_edges
	WHERE rn <= 4
),

result AS (
	SELECT xd.src
	FROM help3 xd 
	GROUP BY xd.src
	HAVING COUNT(DISTINCT xd.tgt) >= 4
)

SELECT *	-- COUNT(*)
FROM result;

