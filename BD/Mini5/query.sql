
-- ((e.0 -> (f -> g)) -> (h -> i))

-- f->g, h->i
WITH sasiedzi AS (
	SELECT one.src, two.tgt
	FROM e one, e two
	WHERE one.tgt=two.src
),
-- e.0
starts AS (
	SELECT src, tgt
	FROM e
	WHERE src=0
),
-- e.0 -> (f -> g)
sasiedzi3 AS (
	SELECT one.src, two.tgt
	FROM starts one, sasiedzi two
	WHERE one.tgt=two.src
),
-- ((e.0 -> (f -> g)) -> (h -> i))
wynik AS (
	SELECT one.src, two.tgt
	FROM sasiedzi3 one, sasiedzi two
	WHERE one.tgt=two.src
)

-- wynik
SELECT count(*)  
FROM wynik;

-- ans =  1196049140
-- slow :c