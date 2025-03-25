
WITH result AS (
	SELECT e.src
	FROM e e, e f, e g, e h
	WHERE e.tgt = f.src AND f.tgt = g.src AND g.tgt = h.src
	GROUP BY e.src
	HAVING COUNT(DISTINCT h.tgt) >= 4
)

SELECT COUNT(*)
FROM result;