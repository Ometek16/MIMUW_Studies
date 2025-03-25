
-- e0 
WITH e0 AS (
	SELECT one.tgt, COUNT(*) as ways
	FROM e one
	WHERE one.src=0
	GROUP BY one.tgt
	ORDER BY one.tgt
),

-- e0 -> f
e0f AS (
	SELECT two.tgt, SUM(ways) as ways
	FROM e0 one, e two
	WHERE one.tgt=two.src
	GROUP BY two.tgt
	ORDER BY two.tgt
),

-- f -> g
fg AS (
	SELECT two.tgt, SUM(ways) as ways
	FROM e0f one, e two
	WHERE one.tgt=two.src
	GROUP BY two.tgt
	ORDER BY two.tgt
),

-- g -> h
gh AS (
	SELECT two.tgt, SUM(ways) as ways
	FROM fg one, e two
	WHERE one.tgt=two.src
	GROUP BY two.tgt
	ORDER BY two.tgt
),

-- h -> i
hi AS (
	SELECT two.tgt, SUM(ways) as ways
	FROM gh one, e two
	WHERE one.tgt=two.src
	GROUP BY two.tgt
	ORDER BY two.tgt
)

-- wynik
SELECT SUM(ways) as count
FROM hi;

-- ans =  1196049140
